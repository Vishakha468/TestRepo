package allocator;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;


import supportlibraries.ExecutionParameters;
import supportlibraries.SeleniumReport;
import supportlibraries.TestParameters;
import supportlibraries.TimeStamp;

import com.cognizant.framework.FrameworkParameters;
import com.cognizant.framework.Util;

/**
 * Class to facilitate parallel execution of test scripts
 * @author Cognizant
 * @version 3.0
 * @since October 2011
 */
public class ParallelRunner implements Runnable {
	
	private FrameworkParameters frameworkParameters = FrameworkParameters.getInstance();
	private TestParameters testParameters;
	private Date startTime, endTime;
	private String testStatus;
	private SeleniumReport report;
	
	private ExecutionParameters execParameters;
	private String qmetryStatusUpdate = null;
	
	/**
	 * Constructor to initialize the details of the test case to be executed
	 * @param testParameters The {@link TestParameters} object (passed from the {@link Allocator})
	 * @param report The {@link SeleniumReport} object (passed from the {@link Allocator})
	 */
	public ParallelRunner(TestParameters testParameters, SeleniumReport report) {
		super();
		this.testParameters = testParameters;
		this.report = report;
		this.execParameters = testParameters.getExecutionParameters();
	//	this.qmetryHandler = new QMetryHandler(this.execParameters);
	}

	@Override
	public void run() {
		startTime = Util.getCurrentTime();
		testStatus = invokeTestScript();
		endTime = Util.getCurrentTime();
		
		String executionTime = Util.getTimeDifference(startTime, endTime);
		report.updateResultSummary(testParameters.getCurrentScenario(), testParameters.getCurrentTestcase(),
									testParameters.getCurrentTestDescription(), executionTime, testStatus);
		
		qmetryExecutionStatusUpdate();
		writeConsoleOutput();
	}

	/**
	 * Update Execution Status into QMetry
	 * @return
	 */
	private void qmetryExecutionStatusUpdate() {
		String timeStamp = TimeStamp.getInstance();
		String reportFileName = testParameters.getCurrentScenario() + "_" + testParameters.getCurrentTestcase();
		String reportPath = frameworkParameters.getRelativePath() + Util.getFileSeparator() + "Results"
				+ Util.getFileSeparator() + timeStamp;
		
		execParameters.setExecutionTimeStamp(timeStamp.replace('\\', '_'));
		execParameters.setErrorReportFileName(reportFileName);
		execParameters.setErrorReportFileLocation(reportPath + Util.getFileSeparator() + "HTML Results");
		execParameters.setErrorScreenshotFileLocation(reportPath + Util.getFileSeparator() + "Screenshots");
		//qmetryStatusUpdate = qmetryHandler.updateStatusToQMetry(testParameters.getQmetryTestcaseID(), testStatus);
	}
	
	private void writeConsoleOutput() {
		if (testStatus.equalsIgnoreCase("Passed")) {
			execParameters.incrementPassedCount();
		} else {
			execParameters.incrementFailedCount();
		}
		testParameters.setExecutionParameters(execParameters);
		System.out.println(testParameters.getCurrentScenario() + "/" + testParameters.getCurrentTestcase() + "		"
					+ testStatus + "		" + qmetryStatusUpdate + "		" + testParameters.getCurrentTestDescription());
	}
	
	private String invokeTestScript() {
		if (frameworkParameters.getStopExecution()) {
			testStatus = "Test Execution Aborted";
		} else {
			try {
				Class<?> driverScriptClass = Class.forName("supportlibraries.DriverScript");
				Object driverScript = driverScriptClass.newInstance();

				Field testParameters = driverScriptClass.getDeclaredField("testParameters");
				testParameters.setAccessible(true);
				testParameters.set(driverScript, this.testParameters);

				Method driveTestExecution = driverScriptClass.getMethod("driveTestExecution", (Class<?>[]) null);
				driveTestExecution.invoke(driverScript, (Object[]) null);

				Field testReport = driverScriptClass.getDeclaredField("report");
				testReport.setAccessible(true);
				SeleniumReport report = (SeleniumReport) testReport.get(driverScript);
				testStatus = report.getTestStatus();
			} catch (ClassNotFoundException e) {
				testStatus = "Reflection Error - ClassNotFoundException";
				e.printStackTrace();
			}  catch (IllegalArgumentException e) {
				testStatus = "Reflection Error - IllegalArgumentException";
				e.printStackTrace();
			} catch (InstantiationException e) {
				testStatus = "Reflection Error - InstantiationException";
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				testStatus = "Reflection Error - IllegalAccessException";
				e.printStackTrace();
			} catch (SecurityException e) {
				testStatus = "Reflection Error - SecurityException";
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				testStatus = "Reflection Error - NoSuchFieldException";
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				testStatus = "Reflection Error - NoSuchMethodException";
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				testStatus = "Failed";
				e.printStackTrace();
			}
		}
		return testStatus;
	}
}
