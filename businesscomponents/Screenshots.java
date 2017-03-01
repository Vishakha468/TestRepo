package businesscomponents;


import static org.junit.Assert.assertTrue;

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
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import uimap.ReportObjectidnetification;
import uimap.TestObjects;

public class Screenshots extends ReusableLibrary {
	
	public Screenshots(ScriptHelper scriptHelper) {
		super(scriptHelper);
		// TODO Auto-generated constructor stub
	}
	
	TestObjects tObj = new TestObjects(scriptHelper);
	ReportObjectidnetification mObj = new ReportObjectidnetification(scriptHelper);
	
	public void getScreenshots() throws InterruptedException
	{
		String PreURL = dataTable.getData("General_Data", "PreURL");
		driver.get(PreURL);
		tObj.getUserIdTextBox().clear();
		tObj.getUserIdTextBox().sendKeys(dataTable.getData("General_Data", "UserId"));
		tObj.getPasswordTextBox().sendKeys(dataTable.getData("General_Data", "Password"));
		tObj.getLoginButton().click();
		if (driver.getTitle().contains("Files & Folders")) 
		{
			String[] foldersName = dataTable.getData("General_Data", "PreReportFolderName").split(";");
			int LengthFolder = foldersName.length;
			for (int folderLoop = 0; folderLoop < foldersName.length - 1; folderLoop++)
			{
				driver.switchTo().frame(driver.findElement(By.id("TableFrame")));
				driver.switchTo().frame(driver.findElement(By.id("tableFrame")));
				
				if (waitForElement(driver, By.linkText(foldersName[folderLoop]), 1000)) 
				{
					driver.findElement(By.linkText(foldersName[folderLoop])).click();
					report.updateTestLog("Report Navigation", "Navigation to this folder" + foldersName[folderLoop] + "is PASSED", Status.PASS);

				} else 
				{
					report.updateTestLog("Report Navigation", "Navigation to this folder" + foldersName[folderLoop] + "is failed", Status.FAIL);
				}
				driver.switchTo().defaultContent();
			}
			
			
			driver.switchTo().frame(driver.findElement(By.id("TableFrame")));
			driver.switchTo().frame(driver.findElement(By.id("tableFrame")));
			Thread.sleep(4000);
			final String PreReportName_xpath = "//a[contains(@href,'" + foldersName[(LengthFolder-1)] + "')]";
			int NumberReports = driver.findElements(By.xpath(PreReportName_xpath)).size();
			//System.out.println("Number of Reports :" +NumberReports);
			
			//reporting for report present or not
			driver.findElements(By.xpath(PreReportName_xpath)).get(NumberReports-1).click();
			
			
			driver.switchTo().defaultContent();
			driver.switchTo().frame(driver.findElement(By.id("TableFrame")));
			driver.switchTo().frame(driver.findElement(By.id("tableFrame")));
			String reportName = dataTable.getData("General_Data", "ReportName");
			ArrayList<String> tab = new ArrayList<String>(driver.getWindowHandles());
				driver.switchTo().window(tab.get(1));
			waitForElement(driver, By.id("toolbarframe"), 200);
				driver.switchTo().frame(driver.findElement(By.id("toolbarframe")));
				String no_Of_Pages = driver.findElement(By.xpath("//input[@name='editThis']/parent::td/following-sibling::td/font")).getText().trim();
				no_Of_Pages = no_Of_Pages.substring(3, no_Of_Pages.length());
				int no_OfPages = Integer.parseInt(no_Of_Pages);
				driver.switchTo().defaultContent();
				driver.switchTo().frame(driver.findElement(By.id("reportframe")));
				for (int l = 1; l <= no_OfPages; l++)
				{
					if(l<=3||l>no_OfPages-3)
						report.updateTestLog("Pre Report"+reportName, "Page No" +l+ "is captured", Status.PASS);
					driver.switchTo().defaultContent();
					driver.switchTo().frame(driver.findElement(By.id("toolbarframe")));
					driver.findElement(By.linkText("Next")).click();
					driver.switchTo().defaultContent();
					waitForElement(driver, By.id("reportframe"), 2000);
					driver.switchTo().frame(driver.findElement(By.id("reportframe")));
				}
	}
		else
			report.updateTestLog("Prereport Folders", "Pre Report folders pages is not opened", Status.FAIL);
		
		//post report code starts ....,.
		String PostURL = dataTable.getData("General_Data", "PostURL");
		driver.get(PostURL);

		tObj.getPostUserIdTextBox().clear();

		tObj.getPostUserIdTextBox().sendKeys(dataTable.getData("General_Data", "UserId"));
		tObj.getPostPasswordTextBox().sendKeys(dataTable.getData("General_Data", "Password"));
		tObj.getPostLoginButton().click();
		
		WebElement folderFrame = null;
		String[] postFolderName = dataTable.getData("General_Data", "PostReportFolderName").split(";");
		if (waitForElement(driver, By.id("acFileExplorer"), 4000)) 
		{
			//System.out.println("test1");
			for (int folders = 0; folders < (postFolderName.length-1); folders++) 
			{
				driver.switchTo().frame(driver.findElement(By.id("acFileExplorer")));
				folderFrame = driver.findElement(By.xpath("//frame[@name='main']"));
				driver.switchTo().frame(folderFrame);
				
				String dynaXpathFolder1 = "//a[contains(@href,'" + postFolderName[folders] + "')]";
				
				//if (waitForElement(driver, By.linkText(postFolderName[folders]), 1000)) 
				if (waitForElement(driver, By.xpath(dynaXpathFolder1), 1000)) 
				{
					driver.findElement(By.linkText(postFolderName[folders])).click();
					driver.switchTo().defaultContent();
				} 
				else 
					report.updateTestLog("Post Report Folder Navigation", "Post Report Folder Navigation" + folderFrame + "Has failed", Status.FAIL);
			}
			
			driver.switchTo().frame(driver.findElement(By.id("acFileExplorer")));
			folderFrame = driver.findElement(By.xpath("//frame[@name='main']"));
			driver.switchTo().frame(folderFrame);
			String postReportName = dataTable.getData("General_Data", "PostReportName");	
			if (waitForElement(driver, By.linkText(postReportName), 1000)) 
			{
				driver.findElement(By.linkText(postReportName)).click();
				driver.switchTo().defaultContent();
				driver.switchTo().frame(driver.findElement(By.id("acFileExplorer")));
				folderFrame = driver.findElement(By.xpath("//frame[@name='main']"));
				driver.switchTo().frame(folderFrame);
				driver.switchTo().frame(driver.findElement(By.id("toolbarframe")));
				String no_Of_Pages = driver.findElement(By.xpath("//input[@name='editThis']/parent::td/following-sibling::td/font")).getText().trim();
				no_Of_Pages = no_Of_Pages.substring(3, no_Of_Pages.length());
				int no_OfPages = Integer.parseInt(no_Of_Pages);
				driver.switchTo().defaultContent();
				for (int i = 1; i <= no_OfPages; i++) 
				{
					driver.switchTo().frame(driver.findElement(By.id("acFileExplorer")));
					folderFrame = driver.findElement(By.xpath("//frame[@name='main']"));
					driver.switchTo().frame(folderFrame);
					driver.switchTo().frame(driver.findElement(By.id("reportframe")));
					if( i<=3||i>no_OfPages-3)
						report.updateTestLog("Post Report "+postReportName, "Page No" +i+ " is captured successfully", Status.PASS);
					driver.switchTo().defaultContent();
					driver.switchTo().frame(driver.findElement(By.id("acFileExplorer")));
					folderFrame = driver.findElement(By.xpath("//frame[@name='main']"));
					driver.switchTo().frame(folderFrame);
					driver.switchTo().frame(driver.findElement(By.id("toolbarframe")));
					
					driver.findElement(By.linkText("Next")).click();
					driver.switchTo().defaultContent();
					waitForElement(driver, By.id("acFileExplorer"), 2000);
				}
			}
			else 
				report.updateTestLog("Post Report Navigation", "Not able to find Report Folder", Status.FAIL);
			
		}
		else 
			report.updateTestLog("Post Report Folder Navigation", "Not able to find Post Report Folder structure", Status.FAIL);
	}
	
	public boolean waitForElement(WebDriver driver, By by, int time) {
		boolean flag;
		try {
			driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		//	assertTrue((isElementPresent(driver, by)));
			WebDriverWait wait = new WebDriverWait(driver, time);
			WebElement myElement = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
			if (myElement != null)
				flag = true;
			else
				flag = false;

		} catch (Exception e) {
			System.out.println(e.toString());
			flag = false;
		}
		return flag;
	}
	
	
	
	public void getScreenshotsNew() throws InterruptedException
	{
		String PreURL = dataTable.getData("General_Data", "PreURL");
		driver.get(PreURL);
		/*tObj.getUserIdTextBox().clear();
		tObj.getUserIdTextBox().sendKeys(dataTable.getData("General_Data", "UserId"));
		tObj.getPasswordTextBox().sendKeys(dataTable.getData("General_Data", "Password"));
		tObj.getLoginButton().click();*/
		tObj.getNewUserIdTextBox().clear();
		tObj.getNewUserIdTextBox().sendKeys(dataTable.getData("General_Data", "UserId"));
		tObj.getNewPasswordTextBox().sendKeys(dataTable.getData("General_Data", "Password"));
		tObj.getNewLoginButton().click();
		WebElement folderFrame1 = null;
		if (driver.getTitle().contains("Information Console")) 
		{
			String[] foldersName = dataTable.getData("General_Data", "PreReportFolderName").split(";");
			int LengthFolder = foldersName.length;
			for (int folderLoop = 0; folderLoop < foldersName.length - 1; folderLoop++)
			{
				folderFrame1 = driver.findElement(By.xpath("//frame[@name='main']"));
				driver.switchTo().frame(folderFrame1);
				if (waitForElement(driver, By.linkText(foldersName[folderLoop]), 1000)) 
				{
					driver.findElement(By.linkText(foldersName[folderLoop])).click();
					report.updateTestLog("Report Navigation", "Navigation to this folder" + foldersName[folderLoop] + "is PASSED", Status.PASS);

				} else 
				{
					report.updateTestLog("Report Navigation", "Navigation to this folder" + foldersName[folderLoop] + "is failed", Status.FAIL);
				}
				driver.switchTo().defaultContent();
			}
			folderFrame1 = driver.findElement(By.xpath("//frame[@name='main']"));
			driver.switchTo().frame(folderFrame1);
			Thread.sleep(4000);
			driver.findElement(By.partialLinkText(foldersName[(LengthFolder-1)])).click();
		//	final String PreReportName_xpath = "//a[contains(@href,'" + foldersName[(LengthFolder-1)] + "')]";
		//	int NumberReports = driver.findElements(By.xpath(PreReportName_xpath)).size();
			//System.out.println("Number of Reports :" +NumberReports);
			
			//reporting for report present or not
		//	driver.findElements(By.xpath(PreReportName_xpath)).get(NumberReports-1).click();
			driver.switchTo().defaultContent();
			folderFrame1 = driver.findElement(By.xpath("//frame[@name='main']"));
			driver.switchTo().frame(folderFrame1);
			String reportName = dataTable.getData("General_Data", "ReportName");
			waitForElement(driver, By.id("toolbarframe"), 200);
				driver.switchTo().frame(driver.findElement(By.id("toolbarframe")));
				String no_Of_Pages = driver.findElement(By.xpath("//input[@name='editThis']/parent::td/following-sibling::td/font")).getText().trim();
				no_Of_Pages = no_Of_Pages.substring(3, no_Of_Pages.length());
				int no_OfPages = Integer.parseInt(no_Of_Pages);
				driver.switchTo().defaultContent();
			/*	for(int j=1;j<=6;j++)
				{
					folderFrame1 = driver.findElement(By.xpath("//frame[@name='main']"));
					driver.switchTo().frame(folderFrame1);
					driver.switchTo().frame(driver.findElement(By.id("reportframe")));
					if(j<=3)
						report.updateTestLog("Pre Report"+reportName, "Page No" +j+ "is captured", Status.PASS);
					driver.switchTo().defaultContent();
					folderFrame1 = driver.findElement(By.xpath("//frame[@name='main']"));
					driver.switchTo().frame(folderFrame1);
					driver.switchTo().frame(driver.findElement(By.id("toolbarframe")));
					driver.findElement(By.linkText("Next")).click();
					driver.switchTo().defaultContent();
					if(j>=4)
					{
						folderFrame1 = driver.findElement(By.xpath("//frame[@name='main']"));
						driver.switchTo().frame(folderFrame1);
						driver.switchTo().frame(driver.findElement(By.id("toolbarframe")));
						mObj.getEditPageToolbar().clear();
						mObj.getEditPageToolbar().sendKeys(Integer.toString(j));
						mObj.getEditPageToolbar().sendKeys(Keys.ENTER);
						waitForElement(driver, By.id("toolbarframe"), 200);
						report.updateTestLog("Pre Report"+reportName, "Page No" +j+ "is captured", Status.PASS);
						
					}
				}*/
				
				for (int l = 1; l <= no_OfPages; l++)
				{
					folderFrame1 = driver.findElement(By.xpath("//frame[@name='main']"));
					driver.switchTo().frame(folderFrame1);
					driver.switchTo().frame(driver.findElement(By.id("reportframe")));
					
					if(l<=3||l>no_OfPages-3)
						report.updateTestLog("Pre Report"+reportName, "Page No" +l+ "is captured", Status.PASS);
					driver.switchTo().defaultContent();
					folderFrame1 = driver.findElement(By.xpath("//frame[@name='main']"));
					driver.switchTo().frame(folderFrame1);
					driver.switchTo().frame(driver.findElement(By.id("toolbarframe")));
					driver.findElement(By.linkText("Next")).click();
					driver.switchTo().defaultContent();
					/*waitForElement(driver, By.id("reportframe"), 2000);
					driver.switchTo().frame(driver.findElement(By.id("reportframe")));*/
				}
	}
		else
			report.updateTestLog("Prereport Folders", "Pre Report folders pages is not opened", Status.FAIL);
		
		//post report code starts ....,.
		String PostURL = dataTable.getData("General_Data", "PostURL");
		driver.get(PostURL);

		tObj.getPostUserIdTextBox().clear();

		tObj.getPostUserIdTextBox().sendKeys(dataTable.getData("General_Data", "UserId"));
		tObj.getPostPasswordTextBox().sendKeys(dataTable.getData("General_Data", "Password"));
		tObj.getPostLoginButton().click();
		
		WebElement folderFrame = null;
		String[] postFolderName = dataTable.getData("General_Data", "PostReportFolderName").split(";");
		if (waitForElement(driver, By.id("acFileExplorer"), 4000)) 
		{
			//System.out.println("test1");
			for (int folders = 0; folders < (postFolderName.length-1); folders++) 
			{
				driver.switchTo().frame(driver.findElement(By.id("acFileExplorer")));
				folderFrame = driver.findElement(By.xpath("//frame[@name='main']"));
				driver.switchTo().frame(folderFrame);
				
				String dynaXpathFolder1 = "//a[contains(@href,'" + postFolderName[folders] + "')]";
				
				//if (waitForElement(driver, By.linkText(postFolderName[folders]), 1000)) 
				if (waitForElement(driver, By.xpath(dynaXpathFolder1), 1000)) 
				{
					driver.findElement(By.linkText(postFolderName[folders])).click();
					driver.switchTo().defaultContent();
				} 
				else 
					report.updateTestLog("Post Report Folder Navigation", "Post Report Folder Navigation" + folderFrame + "Has failed", Status.FAIL);
			}
			
			driver.switchTo().frame(driver.findElement(By.id("acFileExplorer")));
			folderFrame = driver.findElement(By.xpath("//frame[@name='main']"));
			driver.switchTo().frame(folderFrame);
			String postReportName = dataTable.getData("General_Data", "PostReportName");	
			if (waitForElement(driver, By.linkText(postReportName), 1000)) 
			{
				driver.findElement(By.linkText(postReportName)).click();
				driver.switchTo().defaultContent();
				driver.switchTo().frame(driver.findElement(By.id("acFileExplorer")));
				folderFrame = driver.findElement(By.xpath("//frame[@name='main']"));
				driver.switchTo().frame(folderFrame);
				driver.switchTo().frame(driver.findElement(By.id("toolbarframe")));
				String no_Of_Pages = driver.findElement(By.xpath("//input[@name='editThis']/parent::td/following-sibling::td/font")).getText().trim();
				no_Of_Pages = no_Of_Pages.substring(3, no_Of_Pages.length());
				int no_OfPages = Integer.parseInt(no_Of_Pages);
				driver.switchTo().defaultContent();
				for (int i = 1; i <= no_OfPages; i++) 
				{
					driver.switchTo().frame(driver.findElement(By.id("acFileExplorer")));
					folderFrame = driver.findElement(By.xpath("//frame[@name='main']"));
					driver.switchTo().frame(folderFrame);
					driver.switchTo().frame(driver.findElement(By.id("reportframe")));
					if( i<=3||i>no_OfPages-3)
						report.updateTestLog("Post Report "+postReportName, "Page No" +i+ " is captured successfully", Status.PASS);
					driver.switchTo().defaultContent();
					driver.switchTo().frame(driver.findElement(By.id("acFileExplorer")));
					folderFrame = driver.findElement(By.xpath("//frame[@name='main']"));
					driver.switchTo().frame(folderFrame);
					driver.switchTo().frame(driver.findElement(By.id("toolbarframe")));
					
					driver.findElement(By.linkText("Next")).click();
					driver.switchTo().defaultContent();
					waitForElement(driver, By.id("acFileExplorer"), 2000);
				}
			}
			else 
				report.updateTestLog("Post Report Navigation", "Not able to find Report Folder", Status.FAIL);
			
		}
		else 
			report.updateTestLog("Post Report Folder Navigation", "Not able to find Post Report Folder structure", Status.FAIL);
	}
	
}