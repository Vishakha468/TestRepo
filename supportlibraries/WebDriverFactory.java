package supportlibraries;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.cognizant.framework.FrameworkParameters;
import com.cognizant.framework.Settings;
//import org.openqa.selenium.htmlunit.HtmlUnitDriver;


/**
 * Factory for creating the Driver object based on the required browser
 * @author Cognizant
 * @version 3.0
 * @since October 2011
 */
public class WebDriverFactory {
	
	private static Properties properties;

	/**
	 * Function to return the appropriate {@link RemoteWebDriver} object based on the {@link Browser} passed
	 * @param browser The {@link Browser} to be used for the test execution
	 * @return The {@link RemoteWebDriver} object corresponding to the {@link Browser} specified
	 */
	public static RemoteWebDriver getDriver(Browser browser) {
		WebDriver driver = null;

		switch (browser) {
		case chrome:
			 DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			 capabilities.setCapability( "networkConnectionEnabled",true);
			 capabilities.setCapability( "browserConnectionEnabled",true);
			
			properties = Settings.getInstance();
			System.setProperty("webdriver.chrome.driver", properties.getProperty("ChromeDriverPath"));
			
			//System.setProperty("webdriver.chrome.logfile", "D:\\chromedriver.log");
			driver = new ChromeDriver(capabilities);
			//driver = new ChromeDriver(getChromeCapabilities());
			
			break;
		case firefox:
			driver = new FirefoxDriver(getFirefoxCapabilities());
			break;
		/*case htmlunit:
			driver = new HtmlUnitDriver();
			break;
*/		case iexplore:
			properties = Settings.getInstance();
			System.setProperty("webdriver.ie.driver", properties.getProperty("InternetExplorerDriverPath"));
			driver = new InternetExplorerDriver(getIECapabilities());
			break;

		}
		return (RemoteWebDriver)driver;
	}
	
	private static DesiredCapabilities getFirefoxCapabilities() {
		FirefoxProfile profile = new FirefoxProfile();
		profile.setPreference("browser.download.folderList", 2);
		profile.setPreference("browser.download.dir", getDownloadDirectory().getAbsolutePath());
		profile.setPreference("browser.helperApps.alwaysAsk.force", false);
		profile.setPreference("browser.download.manager.showWhenStarting", false);
		profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/vnd.ms-excel, application/ms-excel, application/x-excel, application/x-msexcel, application/excel");
		profile.setPreference("browser.download.useDownloadDir", "false");
		
		DesiredCapabilities capabilities = DesiredCapabilities.firefox();
		capabilities.setCapability(FirefoxDriver.PROFILE, profile);
		return capabilities;
	}
	
	private static DesiredCapabilities getChromeCapabilities() {
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("profile.default_content_settings.popups", 1);
		prefs.put("download.default_directory", getDownloadDirectory().getAbsolutePath());
		prefs.put("download.prompt_for_download", false);
		
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", prefs);
		options.addArguments("start-maximized");
		options.addArguments("disable-plugins");
		
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		capabilities.setCapability("ignore-certificate-errors", true);
		return capabilities;
	}
	
	private static DesiredCapabilities getIECapabilities() {
		DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
		capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, true);
		return capabilities;
	}

	private static File getDownloadDirectory() {
		File dir = new File(getDownloadDirectoryPath());
		dir.mkdir();
		return dir;
	}
	
	public static String getDownloadDirectoryPath() {
		String relativePath = FrameworkParameters.getInstance().getRelativePath();
		return relativePath + "\\Downloads";
	}
}
