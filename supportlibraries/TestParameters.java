package supportlibraries;

import org.openqa.selenium.Platform;

import com.cognizant.framework.IterationOptions;

/**
 * Class to encapsulate various input parameters required for each test script
 * @author Cognizant
 * @browserVersion 3.0
 * @since October 2011
 */
public class TestParameters {
	
	private String currentScenario;
	private String currentTestcase;
	private String currentTestDescription;
	private IterationOptions iterationMode;
	private int startIteration = 1;
	private int endIteration = 1;
	private Browser browser;
	private String browserVersion;
	private Platform platform;
	private ExecutionParameters executionParameters = null;
	private String qmetryTestcaseID = null;
	
	/**
	 * @return the qmetryTestcaseID
	 */
	public String getQmetryTestcaseID() {
		return qmetryTestcaseID;
	}
	/**
	 * @param qmetryTestcaseID the qmetryTestcaseID to set
	 */
	public void setQmetryTestcaseID(String qmetryTestcaseID) {
		this.qmetryTestcaseID = qmetryTestcaseID;
	}
	/**
	 * Function to get the current test scenario/module
	 * @return The current test scenario/module
	 */
	public String getCurrentScenario() {
		return currentScenario;
	}
	/**
	 * Function to set the current test scenario/module
	 * @param currentScenario The current test scenario/module
	 */
	public void setCurrentScenario(String currentScenario) {
		this.currentScenario = currentScenario;
	}
	
	/**
	 * Function to get the current test case
	 * @return The current test case
	 */
	public String getCurrentTestcase() {
		return currentTestcase;
	}
	
	/**
	 * Function to set the current test case
	 * @param currentTestcase The current test case
	 */
	public void setCurrentTestcase(String currentTestcase) {
		this.currentTestcase = currentTestcase;
	}
	
	/**
	 * Function to get the description of the current test case
	 * @return The description of the current test case
	 */
	public String getCurrentTestDescription() {
		return currentTestDescription;
	}
	
	/**
	 * Function to set the description of the current test case
	 * @param currentTestDescription The description of the current test case
	 */
	public void setCurrentTestDescription(String currentTestDescription) {
		this.currentTestDescription = currentTestDescription;
	}

	/**
	 * Function to get the iteration mode
	 * @return The iteration mode
	 * @see IterationOptions
	 */
	public IterationOptions getIterationMode() {
		return iterationMode;
	}
	
	/**
	 * Function to set the iteration mode
	 * @param iterationMode The iteration mode
	 * @see IterationOptions
	 */
	public void setIterationMode(IterationOptions iterationMode) {
		this.iterationMode = iterationMode;
	}
	
	/**
	 * Function to get the start iteration
	 * @return The start iteration
	 */
	public int getStartIteration() {
		return startIteration;
	}
	
	/**
	 * Function to set the start iteration
	 * @param startIteration The start iteration (defaults to 1 if the input is <=0)
	 */
	public void setStartIteration(int startIteration) {
		if(startIteration > 0) {
			this.startIteration = startIteration;
		}
	}
	
	/**
	 * Function to get the end iteration
	 * @return The end iteration
	 */
	public int getEndIteration() {
		return endIteration;
	}
	
	/**
	 * Function to set the end iteration
	 * @param endIteration The end iteration (defaults to 1 if the input is <=0)
	 */
	public void setEndIteration(int endIteration) {
		if(endIteration > 0) {
			this.endIteration = endIteration;
		}
	}
	
	/**
	 * Function to get the browser for a specific test
	 * @return The browser
	 */
	public Browser getBrowser() {
		return browser;
	}
	/**
	 * Function to set the browser for a specific test
	 */
	public void setBrowser(Browser browser) {
		this.browser = browser;
	}
	
	/**
	 * Function to get the browserVersion for a specific test
	 * @return The browserVersion
	 */
	public String getBrowserVersion() {
		return browserVersion;
	}
	
	/**
	 * Function to set the browserVersion for a specific test
	 */
	public void setBrowserVersion(String version) {
		this.browserVersion = version;
	}
	
	/**
	 * Function to get the platform for a specific test
	 * @return The platform
	 */
	public Platform getPlatform() {
		return platform;
	}
	
	/**
	 * Function to set the platform for a specific test
	 */
	public void setPlatform(Platform platform) {
		this.platform = platform;
	}
	
	/**
	 * @return the executionParameters
	 */
	public ExecutionParameters getExecutionParameters() {
		return executionParameters;
	}
	
	/**
	 * @param executionParameters the executionParameters to set
	 */
	public void setExecutionParameters(ExecutionParameters executionParameters) {
		this.executionParameters = executionParameters;
	}
}
