package testscripts;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import supportlibraries.Browser;
import supportlibraries.DriverScript;
import supportlibraries.TestParameters;

import com.cognizant.framework.IterationOptions;

@RunWith(Parameterized.class)
public class UnitTestScripts extends DriverScript 
{
	private TestParameters _tParameters = new TestParameters();
	@Parameters
	public static Collection<Object[]> data() 
	{
				Object[][] data = new Object[][] 
				{ 
				{"ReportComparison", "Test_XML", IterationOptions.RunOneIterationOnly, Browser.chrome},
			//	{"ReportComparison", "Test_XML1", IterationOptions.RunOneIterationOnly, Browser.chrome},
			//    {"ReportComparison", "MRF400GNS_GNS_Weekly", IterationOptions.RunOneIterationOnly, Browser.chrome},
			//	{"ReportComparison", "Report_MRO219_UK_Weekly", IterationOptions.RunOneIterationOnly, Browser.chrome},
			//	{"ReportComparison", "Report_MRF451_MX_Monthly", IterationOptions.RunOneIterationOnly, Browser.chrome},
			//	{"ReportComparison", "Report_MRF451_UK_Daily", IterationOptions.RunOneIterationOnly, Browser.chrome}
				};
		return Arrays.asList(data);
	}
	//TC02_calculateGPA
	//TC01_Launching
	//TC03_Admissions_Financial_navigation
	public UnitTestScripts(String scenario, String tcName,IterationOptions iter, Browser browser) {
		_tParameters.setCurrentScenario(scenario);
		_tParameters.setCurrentTestcase(tcName);
		_tParameters.setIterationMode(iter);
		_tParameters.setBrowser(browser);
	}

	@Test
	public void testScripts() {
		testParameters = this._tParameters;
		driveTestExecution();
	}
}
