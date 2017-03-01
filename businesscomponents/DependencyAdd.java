package businesscomponents;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cognizant.framework.Status;
import componentgroups.GenericComponents;

import supportlibraries.ReusableLibrary;
import supportlibraries.ScriptHelper;
import uimap.TestObjects;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import uimap.ReportObjectidnetification;
import uimap.DependencyObject;
public class DependencyAdd extends ReusableLibrary 
{
	public DependencyAdd(ScriptHelper scriptHelper) 
	{
		super(scriptHelper);
		// TODO Auto-generated constructor stub
	}
	GenericComponents gen =new GenericComponents(scriptHelper);
	TestObjects tObj = new TestObjects(scriptHelper);
	ReportObjectidnetification rObj = new ReportObjectidnetification(scriptHelper);
	DependencyObject dObj = new DependencyObject(scriptHelper);
	
	public void login()
	{
	String PreURL = dataTable.getData("General_Data", "PreURL");
	driver.get(PreURL);
	tObj.getUserIdTextBox().clear();
	tObj.getUserIdTextBox().sendKeys(dataTable.getData("General_Data", "UserId"));
	tObj.getPasswordTextBox().sendKeys(dataTable.getData("General_Data", "Password"));
	tObj.getLoginButton().click();
	report.updateTestLog("Login", "Login to URL is successfully completed", Status.DONE);
	}
	public void navigationTillDependency() throws InterruptedException
	{
		if (driver.getTitle().contains("Files & Folders")) 
		{
			String[] foldersName = dataTable.getData("General_Data", "BeforeDependencyPath").split(";");
			int LengthFolder = foldersName.length;
			for (int folderLoop = 0; folderLoop < foldersName.length-1; folderLoop++)
			{
				driver.switchTo().frame(driver.findElement(By.id("TableFrame")));
				driver.switchTo().frame(driver.findElement(By.id("tableFrame")));
				
				if (gen.waitForElement(driver, By.linkText(foldersName[folderLoop]), 1000)) 
				{
					driver.findElement(By.linkText(foldersName[folderLoop])).click();
					report.updateTestLog("Report Navigation", "Navigation to this folder" + foldersName[folderLoop] + "is PASSED", Status.PASS);

				}
				else 
				{
					report.updateTestLog("Report Navigation", "Navigation to this folder" + foldersName[folderLoop] + "is failed", Status.FAIL);
				}
				driver.switchTo().defaultContent();
			}
			driver.switchTo().frame(driver.findElement(By.id("TableFrame")));
			driver.switchTo().frame(driver.findElement(By.id("tableFrame")));
			Thread.sleep(2000);
			//String reprtName = dataTable.getData("General_Data", "ReportName");
			
			/*driver.findElements(By.linkText(reprtName)).size();
			
			int s = dObj.getPreReportArrows().size();
			dObj.getPreReportArrows().get(s-1).click();*/
			
			dObj.getPositionsArrow().click();
			
			driver.switchTo().frame(driver.findElement(By.id("ItemPopUpMenuFrame")));
			
			dObj.getPropertiesLnk().click();
			Thread.sleep(1000);
			report.updateTestLog("Click Properties link", "Clicked on Properties Link", Status.PASS);
			driver.switchTo().defaultContent();
			driver.switchTo().frame(driver.findElement(By.id("TableFrame")));
			if (gen.waitForElement(driver, By.id("dependencies"), 100))
			{
				driver.findElement(By.id("dependencies")).click();
				report.updateTestLog("Dependecy tab", "Dependency Tab is clicked", Status.DONE);
				driver.switchTo().defaultContent();
				driver.switchTo().frame(driver.findElement(By.id("TableFrame")));
				//driver.switchTo().frame(driver.findElement(By.id("tableFrame")));
				Thread.sleep(1000);
				driver.switchTo().defaultContent();
				driver.switchTo().frame(driver.findElement(By.id("TableFrame")));
				//driver.switchTo().frame(driver.findElement(By.id("tableFrame")));
				driver.switchTo().frame(driver.findElement(By.id("filePropsfrm")));
				String WinHandleBefore = driver.getWindowHandle();
				dObj.getAddBtn().click();
				 for (String handle : driver.getWindowHandles()) 
				 {
				    driver.switchTo().window(handle);
				 }
				/*Set<String> WinHandle = driver.getWindowHandles();
				
				driver.switchTo().window(WinHandle[WinHandle.size()-1]);*/
				
				 
			//	System.out.println("Title is : " +driver.getTitle());
				driver.switchTo().defaultContent();
				dObj.getRootElemnt().get(0).click();
				String[] AfterDependfoldersName = dataTable.getData("General_Data", "AfterDependencyPath").split(";");
				
				for (int folderLoop_2 = 0; folderLoop_2 < AfterDependfoldersName.length-1; folderLoop_2++)
				{
					driver.switchTo().frame(driver.findElement(By.id("browserTableFrame")));
				//	driver.switchTo().frame(driver.findElement(By.id("tableFrame")));
					
					if (gen.waitForElement(driver, By.partialLinkText(AfterDependfoldersName[folderLoop_2]), 1000)) 
					{
						driver.findElement(By.partialLinkText(AfterDependfoldersName[folderLoop_2])).click();
						report.updateTestLog("Report Navigation", "Navigation to this folder" + AfterDependfoldersName[folderLoop_2] + "is PASSED", Status.PASS);

					}
					else 
					{
						report.updateTestLog("Report Navigation", "Navigation to this folder" + AfterDependfoldersName[folderLoop_2] + "is failed", Status.FAIL);
					}
					driver.switchTo().defaultContent();
				}
				driver.switchTo().frame(driver.findElement(By.id("browserTableFrame")));
				//driver.switchTo().frame(driver.findElement(By.id("tableFrame")));
				String rprtName = AfterDependfoldersName[AfterDependfoldersName.length-1];
				
				//body[@onunload='onUnload()']/div/div/div[" + i + "]/*/nobr
				String dyn_xpath = ".//*[@id='ItemsTable']//a[contains(text(),'"+rprtName+"')]";
			//	System.out.println("dynamic xpath is :" +dyn_xpath);
				List <WebElement> rprtCount = driver.findElements(By.xpath(dyn_xpath));
			//	System.out.println("Size is : " +rprtCount.size());
			//	System.out.println(rprtCount.get((rprtCount.size()-1)).getText());
				rprtCount.get((rprtCount.size()-1)).click();
			//System.out.println("pass1");
				Thread.sleep(2000);
				
				driver.switchTo().defaultContent();
				dObj.getBtnOK().click();
			//	driver.close();
				Thread.sleep(1000);
				 driver.switchTo().window(WinHandleBefore);
				// System.out.println("Final Window Title is: " +driver.getTitle());
				// driver.switchTo().defaultContent();
					driver.switchTo().frame(driver.findElement(By.id("TableFrame")));
				dObj.getBtnOKMainScreen().click();
				report.updateTestLog("Action Complete", "Ok Button is clicked", Status.PASS);
			}
			else
			{
				report.updateTestLog("Dependecy tab", "Dependency Tab is not visible on page", Status.FAIL);
			}
			
		}
		else
		{
			report.updateTestLog("Prereport Folders", "Pre Report folders pages is not opened", Status.FAIL);
		}
	}
	
	public void addDependency() throws InterruptedException
	{
		login();
		navigationTillDependency();
	}
	
}
