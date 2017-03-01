package allocator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import supportlibraries.*;

import com.cognizant.framework.ExcelDataAccess;
import com.cognizant.framework.FrameworkParameters;
import com.cognizant.framework.ReportSettings;
import com.cognizant.framework.ReportTheme;
import com.cognizant.framework.ReportThemeFactory;
import com.cognizant.framework.Settings;
import com.cognizant.framework.Util;
import com.cognizant.framework.ReportThemeFactory.Theme;
import com.cognizant.framework.IterationOptions;

/**
 * Class to manage the batch execution of test scripts within the framework
 * @author Cognizant
 * @version 3.0
 * @since October 2011
 */
public class Allocator {
	
	private static ArrayList<TestParameters> testInstancesToRun;
	private static SeleniumReport report;
	private static Properties properties;
	private static FrameworkParameters frameworkParameters = FrameworkParameters.getInstance();
	private static ExecutionParameters executionParam;
	
	private static Date startTime, endTime;
	private static ReportSettings reportSettings;
	private static String timeStamp;
	private static String reportPath;
	
	private static int totalPassed = 0;
	private static int totalFailed = 0;
	
	public static void main(String[] args) throws FileNotFoundException {
	
	
		setRelativePath();
		setExecutionParameters(args);
		
		initializeTestBatch();
		initializeSummaryReport();
		setupErrorLog();
		
		driveBatchExecution();
		wrapUp();
		System.exit(0); 
	}
	

	private static void setRelativePath() {
		String relativePath = new File(System.getProperty("user.dir")).getAbsolutePath();
		if(relativePath.contains("allocator")) {
			relativePath = new File(System.getProperty("user.dir")).getParent();
		}
		frameworkParameters.setRelativePath(relativePath);
	}
	
	private static void setExecutionParameters(String[] args) {
		executionParam = new ExecutionParameters();
		if (args != null && args.length > 0 && args[0] != null && !args[0].trim().equals("")) {
			executionParam.setRunConfiguration(args[0]);
		}
		if (executionParam.getRunConfiguration() == null || executionParam.getRunConfiguration().trim().equals("")) {
			properties = Settings.getInstance();
			executionParam.setRunConfiguration(properties.getProperty("RunConfiguration"));
		}
	}
	
	private static void initializeTestBatch() {
		startTime = Util.getCurrentTime();
		properties = Settings.getInstance();
		testInstancesToRun = getRunInfo(executionParam.getRunConfiguration());
		
	}
	
	private static void initializeSummaryReport() {
		frameworkParameters.setRunConfiguration(executionParam.getRunConfiguration());
		timeStamp = TimeStamp.getInstance();
		
		reportSettings = initializeReportSettings();
		ReportTheme reportTheme = ReportThemeFactory.getReportsTheme(Theme.valueOf(properties.getProperty("ReportsTheme")));
		report = new SeleniumReport(reportSettings, reportTheme);
		
		report.initializeReportTypes();
		report.initializeResultSummary();
		report.addResultSummaryHeading(reportSettings.getProjectName() + " - " +	" Automation Execution Result Summary");
		report.addResultSummarySubHeading("Date & Time", ": " + Util.getCurrentFormattedTime(properties.getProperty("DateFormatString")), "OnError", ": " + properties.getProperty("OnError"));
		report.addResultSummaryTableHeadings();
	}
	
	private static ReportSettings initializeReportSettings() {
		reportPath = frameworkParameters.getRelativePath() + Util.getFileSeparator() + "Results" + Util.getFileSeparator() + timeStamp;
		ReportSettings reportSettings = new ReportSettings(reportPath, "");
		
		reportSettings.setDateFormatString(properties.getProperty("DateFormatString"));
		reportSettings.setProjectName(properties.getProperty("ProjectName"));
		reportSettings.generateExcelReports = Boolean.parseBoolean(properties.getProperty("ExcelReport"));
		reportSettings.generateHtmlReports = Boolean.parseBoolean(properties.getProperty("HtmlReport"));
		return reportSettings;
	}
	
	private static void setupErrorLog() throws FileNotFoundException {
		String errorLogFile = reportPath + Util.getFileSeparator() + "ErrorLog.txt";
		System.setErr(new PrintStream(new FileOutputStream(errorLogFile)));
	}
	
	private static ArrayList<TestParameters> getRunInfo(String sheetName) {
		ExcelDataAccess runManagerAccess = new ExcelDataAccess(frameworkParameters.getRelativePath(), "Run Manager");			
		runManagerAccess.setDatasheetName(sheetName);
		
		int nTestInstances = runManagerAccess.getLastRowNum();
		ArrayList<TestParameters> testInstancesToRun = new ArrayList<TestParameters>();
		
		for (int currentTestInstance = 1; currentTestInstance <= nTestInstances; currentTestInstance++) {
			String executeFlag = runManagerAccess.getValue(currentTestInstance, "Execute");
			
			if (executeFlag.equalsIgnoreCase("Yes")) {
				TestParameters testParameters = new TestParameters();
				
				testParameters.setExecutionParameters(executionParam);
				testParameters.setCurrentScenario(runManagerAccess.getValue(currentTestInstance, "Test_Scenario"));
				testParameters.setCurrentTestcase(runManagerAccess.getValue(currentTestInstance, "Test_Case"));
				testParameters.setCurrentTestDescription(runManagerAccess.getValue(currentTestInstance, "Description"));
				testParameters.setIterationMode(IterationOptions.valueOf(runManagerAccess.getValue(currentTestInstance, "Iteration_Mode")));
				
				String startIteration = runManagerAccess.getValue(currentTestInstance, "Start_Iteration");
				if (!startIteration.equals("")) {
					testParameters.setStartIteration(Integer.parseInt(startIteration));
				}
				String endIteration = runManagerAccess.getValue(currentTestInstance, "End_Iteration");
				if (!endIteration.equals("")) {
					testParameters.setEndIteration(Integer.parseInt(endIteration));
				}
				String browser = runManagerAccess.getValue(currentTestInstance, "Browser");
				if (!browser.equals("")) {
					testParameters.setBrowser(Browser.valueOf(browser));
				}
				String browserVersion = runManagerAccess.getValue(currentTestInstance, "Browser_Version");
				if (!browserVersion.equals("")) {
					testParameters.setBrowserVersion(browserVersion);
				}
				String platform = runManagerAccess.getValue(currentTestInstance, "Platform");
				if (!platform.equals("")) {
					testParameters.setPlatform(PlatformFactory.getPlatform(platform));
				}
				String qmetryID = runManagerAccess.getValue(currentTestInstance, "QMetryID");
				if (qmetryID != null && !qmetryID.equals("")) {
					testParameters.setQmetryTestcaseID(qmetryID);
				}
				testInstancesToRun.add(testParameters);
			}
		}
		
		return testInstancesToRun;
	}
	
	private static void driveBatchExecution() {
		int nThreads = Integer.parseInt(properties.getProperty("NumberOfThreads"));
		ExecutorService parallelExecutor = Executors.newFixedThreadPool(nThreads);
		
		for (int currentTestInstance = 0; currentTestInstance < testInstancesToRun.size() ; currentTestInstance++ ) {
			ParallelRunner testRunner =	new ParallelRunner(testInstancesToRun.get(currentTestInstance), report);
			parallelExecutor.execute(testRunner);
			if(frameworkParameters.getStopExecution()) {
				break;
			}
		}
		parallelExecutor.shutdown();
		while(!parallelExecutor.isTerminated()) {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void wrapUp() {
		endTime = Util.getCurrentTime();
		closeSummaryReport();
		copySummaryHtmlFileToRootFolder();
		writeConsoleMessageReport();
		
		String openReport = properties.getProperty("OpenSummaryReport");
		if (openReport != null && openReport.trim().equalsIgnoreCase("True")) {
			if (reportSettings.generateHtmlReports) {
				try {
					Runtime.getRuntime().exec("RunDLL32.EXE shell32.dll,ShellExec_RunDLL " + reportPath + "\\HTML Results\\Summary.Html");
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (reportSettings.generateExcelReports) {
				try {
					Runtime.getRuntime().exec("RunDLL32.EXE shell32.dll,ShellExec_RunDLL " + reportPath + "\\Excel Results\\Summary.xls");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private static void closeSummaryReport() {
		String totalExecutionTime = Util.getTimeDifference(startTime, endTime);
		report.addResultSummaryFooter(totalExecutionTime);
	}
	
	private static void copySummaryHtmlFileToRootFolder() {
		try {
			String sourceFile = frameworkParameters.getRelativePath()
					+ Util.getFileSeparator() + "Results" + Util.getFileSeparator() + timeStamp
					+ Util.getFileSeparator() + "HTML Results" + Util.getFileSeparator() + "Summary.html";
			String destinationFile = frameworkParameters.getRelativePath() + Util.getFileSeparator() + "Summary.html";
			
			Document document = Jsoup.parse(new File(sourceFile), "UTF-8");
			document.select("a").unwrap();
		//	FileUtils.writeStringToFile(new File(destinationFile), document.toString(), "UTF-8");
		} catch (Exception e) {
			System.out.println("Error in copying Summary.html file to root directory. " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	private static void writeConsoleMessageReport() {
		boolean overallExecStatus = isOverallStatusPassed();
		System.out.println("--------------------------------------------------------------------------------------------------------------");
		System.out.println("");
		System.out.println("TOTAL EXECUTED: " + (totalPassed + totalFailed) + "	====>	PASSED: " + totalPassed + " & FAILED: " + totalFailed);
		System.out.println("");
		if (!overallExecStatus) {
			System.exit(1);
		}
	}

	private static boolean isOverallStatusPassed() {
		if (testInstancesToRun != null && testInstancesToRun.size() > 0) {
			totalPassed = testInstancesToRun.get(0).getExecutionParameters().getTotalPassed();
			totalFailed = testInstancesToRun.get(0).getExecutionParameters().getTotalFailed();
			if (totalFailed == 0 && totalPassed != 0) {
				return true;
			}
		}
		return false;
	}
}
