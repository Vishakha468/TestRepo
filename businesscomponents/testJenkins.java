package businesscomponents;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cognizant.framework.Status;

import supportlibraries.ReusableLibrary;
import supportlibraries.ScriptHelper;
public class testJenkins extends ReusableLibrary
{

	public testJenkins(ScriptHelper scriptHelper) {
		super(scriptHelper);
		// TODO Auto-generated constructor stub
	}
	public void launchURL()
	{
		String URL = dataTable.getData("General_Data", "URL");
		driver.get(URL);
		WebDriverWait wait = new WebDriverWait(driver,60);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("rt-logo")));
		if(driver.getTitle().equalsIgnoreCase("Meet Guru99"))
		report.updateTestLog("Launch URL", "URL is Launched Successfully", Status.PASS);
		else
			report.updateTestLog("Launch URL", "URL is Launched Successfully", Status.FAIL);
	}
}