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


public class RowTextCheck extends ReusableLibrary
{
	public RowTextCheck(ScriptHelper scriptHelper) 
	{
		super(scriptHelper);
		// TODO Auto-generated constructor stub
	}
	ArrayList<WebElement> column_Values_Properties = new ArrayList<WebElement>();
	ArrayList<WebElement> column_Values_Elements = new ArrayList<WebElement>();
	ArrayList<String> column_Values = new ArrayList<String>();
	ArrayList<String> Postcolumn_Values = new ArrayList<String>();
	
	ReportObjectidnetification mObj = new ReportObjectidnetification(scriptHelper);
	TestObjects tObj = new TestObjects(scriptHelper);
	public void checkText() throws InterruptedException
	{
		launchPreReport();
	//	openPreReportRow();
		openNewPreReportRow();
	//	launchPostURL();
	//	openPostReportRow();
		
	}
	public void launchPreReport() throws InterruptedException {
		String PreURL = dataTable.getData("General_Data", "PreURL");
		driver.get(PreURL);
		//tObj.getUserIdTextBox().clear();
		tObj.getNewUserIdTextBox().clear();
		//tObj.getUserIdTextBox().sendKeys(dataTable.getData("General_Data", "UserId"));
		tObj.getNewUserIdTextBox().sendKeys(dataTable.getData("General_Data", "UserId"));
		//tObj.getPasswordTextBox().sendKeys(dataTable.getData("General_Data", "Password"));
		tObj.getNewPasswordTextBox().sendKeys(dataTable.getData("General_Data", "Password"));
	//	tObj.getLoginButton().click();
		tObj.getNewLoginButton().click();
		//driver.wait(20);
		//driver.wait();
		//tObj.getJobLink().click();
		synchronized (driver) {
			driver.wait(3);
		}
	}
	
	public void openPreReportRow() throws InterruptedException 
	{
		Thread.sleep(5000);
		
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
			
			final String PreReportName_xpath = "//a[contains(@href,'" + foldersName[(LengthFolder-1)] + "')]";
			int NumberReports = driver.findElements(By.xpath(PreReportName_xpath)).size();
			//System.out.println("Number of Reports :" +NumberReports);
			driver.findElements(By.xpath(PreReportName_xpath)).get(NumberReports-1).click();
			
			driver.switchTo().defaultContent();
			driver.switchTo().frame(driver.findElement(By.id("TableFrame")));
			driver.switchTo().frame(driver.findElement(By.id("tableFrame")));
			String reportName = dataTable.getData("General_Data", "ReportName");
			Thread.sleep(3000);
		/*	if (waitForElement(driver, By.linkText(reportName), 3000))
			{ 
				driver.findElement(By.linkText(reportName)).click(); */
				ArrayList<String> tab = new ArrayList<String>(driver.getWindowHandles());
				driver.switchTo().window(tab.get(1));
			//	System.out.println("Window Title : " +driver.getTitle());
				waitForElement(driver, By.id("toolbarframe"), 4000);
				driver.switchTo().frame(driver.findElement(By.id("toolbarframe")));
				//System.out.println("Switched to frame");
				String RowNumber = dataTable.getData("General_Data", "RowNumber");
				String PageNumber = dataTable.getData("General_Data", "PageNumber");
				mObj.getEditPageToolbar().clear();
				mObj.getEditPageToolbar().sendKeys(PageNumber);
				mObj.getEditPageToolbar().sendKeys(Keys.ENTER);
				report.updateTestLog("Enter Page Number", "Page Number "+PageNumber+ " Entered ", Status.DONE);
				String no_Of_Pages = driver.findElement(By.xpath("//input[@name='editThis']/parent::td/following-sibling::td/font")).getText().trim();
				no_Of_Pages = no_Of_Pages.substring(3, no_Of_Pages.length());
				
			//	System.out.println("value of no_Of_Pages:  " +no_Of_Pages);
				
				int no_OfPages = Integer.parseInt(no_Of_Pages);
				String dynaXpath;
				System.out.println("No of pages: " +no_OfPages);
				
				driver.switchTo().defaultContent();
				driver.switchTo().frame(driver.findElement(By.id("reportframe")));
				ArrayList<WebElement> no_of_rows = new ArrayList<WebElement>();
				no_of_rows = (ArrayList<WebElement>) driver.findElements(By.xpath("//body[@onunload='onUnload()']/div/div/div"));
				
				System.out.println("Number Of rows:" +no_of_rows.size());
				dynaXpath = "//body[@onunload='onUnload()']/div/div/div[" + RowNumber + "]/*/nobr";
				column_Values_Properties = (ArrayList<WebElement>) driver.findElements(By.xpath(dynaXpath));
				
				if (column_Values_Properties.size() > 0)
				{
					for (int j = 0; j < column_Values_Properties.size(); j++)
					{
						
						column_Values.add(j, column_Values_Properties.get(j).getText());
						report.updateTestLog("Text values", "Text Value of row " +RowNumber+"for Column "+(j+1)+ "is :" +column_Values_Properties.get(j).getText(), Status.DONE);
					}
				}
				
				
		/*	}
			else 
			{
				report.updateTestLog("Report Selection", "Not able to find the report Name", Status.FAIL);
			} */
		}
		else
		{
			report.updateTestLog("Prereport Folders", "Pre Report folders pages is not opened", Status.FAIL);
		}
		
		launchPostURL();
		
		
		WebElement folderFrame = null;
		String[] postFolderName = dataTable.getData("General_Data", "PostReportFolderName").split(";");
		if (waitForElement(driver, By.id("acFileExplorer"), 4000)) {
			//System.out.println("test1");
			for (int folders = 0; folders < (postFolderName.length-1); folders++) {
				driver.switchTo().frame(driver.findElement(By.id("acFileExplorer")));
				folderFrame = driver.findElement(By.xpath("//frame[@name='main']"));
				driver.switchTo().frame(folderFrame);
				String dynaXpathFolder1 = "//a[contains(@href,'" + postFolderName[folders] + "')]";
				if (waitForElement(driver, By.xpath(dynaXpathFolder1), 1000)) 
				{
					driver.findElement(By.xpath(dynaXpathFolder1)).click();
					driver.switchTo().defaultContent();
				} 
				else 
				{
					report.updateTestLog("Post Report Folder Navigation", "Post Report Folder Navigation" + folderFrame + "Has failed", Status.FAIL);
				}
			}
			driver.switchTo().frame(driver.findElement(By.id("acFileExplorer")));
			folderFrame = driver.findElement(By.xpath("//frame[@name='main']"));
			driver.switchTo().frame(folderFrame);
			String postReportName = dataTable.getData("General_Data", "ReportName");
			
			String	PostdynaXpath;
			if (waitForElement(driver, By.linkText(postReportName), 1000))
			{
				driver.findElement(By.linkText(postReportName)).click();
				
				driver.switchTo().defaultContent();
				driver.switchTo().frame(driver.findElement(By.id("acFileExplorer")));
				folderFrame = driver.findElement(By.xpath("//frame[@name='main']"));
				driver.switchTo().frame(folderFrame);
				driver.switchTo().frame(driver.findElement(By.id("toolbarframe")));
				String PostRowNumber = dataTable.getData("General_Data", "postRowNumber");
				String PageNumber = dataTable.getData("General_Data", "PageNumber");
				mObj.getEditPageToolbar().clear();
				String PostPageNumber =dataTable.getData("General_Data", "PostPageNumber");
				mObj.getEditPageToolbar().sendKeys(PostPageNumber);
				mObj.getEditPageToolbar().sendKeys(Keys.ENTER);
				report.updateTestLog("Enter Page Number", "Page Number "+PageNumber+ " Entered ", Status.DONE);
				
				driver.switchTo().defaultContent();
				driver.switchTo().frame(driver.findElement(By.id("acFileExplorer")));
				folderFrame = driver.findElement(By.xpath("//frame[@name='main']"));
				driver.switchTo().frame(folderFrame);
				driver.switchTo().frame(driver.findElement(By.id("reportframe")));
			//	ArrayList<WebElement> column_Values_Elements = new ArrayList<WebElement>();
				PostdynaXpath = "//body[@onunload='onUnload()']/div/div/div[" + PostRowNumber + "]/*/nobr";
				
				column_Values_Elements = (ArrayList<WebElement>) driver.findElements(By.xpath(PostdynaXpath));
				if (column_Values_Elements.size() > 0) 
				{
					for (int k = 0; k < column_Values_Elements.size(); k++) 
					{
						Postcolumn_Values.add(k, column_Values_Elements.get(k).getText());
						report.updateTestLog("Text values", "Text Value of row " +PostRowNumber+"for Column "+(k+1)+ "is :" +column_Values_Elements.get(k).getText(), Status.DONE);
					}
				}
			}
			else
			{
				report.updateTestLog("Post Report Navigation", "Not able to find Report Folder", Status.FAIL);
			}
		}
		else 
		{
			report.updateTestLog("Post Report Folder Navigation", "Not able to find Post Report Folder structure", Status.FAIL);
		}
		System.out.println("value for for loop is : " +column_Values.size());
		
		for (int n = 0; n < column_Values.size(); n++) 
		{
			//System.out.println("inside for");
			if((column_Values.get(n)).contentEquals(Postcolumn_Values.get(n)))
				report.updateTestLog("Compare Values", "Values Matches for Column :" +n+ "Pre Value is : " +column_Values.get(n)+"  and post value is : " +Postcolumn_Values.get(n), Status.PASS);
			else
				report.updateTestLog("Compare Values", "Values Mismatch for Column:"+n+" Pre Value is : " +column_Values.get(n)+"  and post value is : " +Postcolumn_Values.get(n) , Status.FAIL);
		}
		
	}
			
			
	public void launchPostURL() throws InterruptedException 
	{
		String PostURL = dataTable.getData("General_Data", "PostURL");
		driver.get(PostURL);

		tObj.getPostUserIdTextBox().clear();

		tObj.getPostUserIdTextBox().sendKeys(dataTable.getData("General_Data", "UserId"));
		tObj.getPostPasswordTextBox().sendKeys(dataTable.getData("General_Data", "Password"));
		tObj.getPostLoginButton().click();
		//driver.wait(20);
		//driver.wait();
		//tObj.getJobLink().click();
		synchronized (driver) {
			driver.wait(3);
		}


	}

/*	public void openPostReportRow()
	{
		WebElement folderFrame = null;
		String[] postFolderName = dataTable.getData("General_Data", "PostReportFolderName").split(";");
		if (waitForElement(driver, By.id("acFileExplorer"), 4000)) {
			//System.out.println("test1");
			for (int folders = 0; folders < (postFolderName.length-1); folders++) {
				driver.switchTo().frame(driver.findElement(By.id("acFileExplorer")));
				folderFrame = driver.findElement(By.xpath("//frame[@name='main']"));
				driver.switchTo().frame(folderFrame);
				String dynaXpathFolder1 = "//a[contains(@href,'" + postFolderName[folders] + "')]";
				if (waitForElement(driver, By.xpath(dynaXpathFolder1), 1000)) 
				{
					driver.findElement(By.xpath(dynaXpathFolder1)).click();
					driver.switchTo().defaultContent();
				} 
				else 
				{
					report.updateTestLog("Post Report Folder Navigation", "Post Report Folder Navigation" + folderFrame + "Has failed", Status.FAIL);
				}
			}
			driver.switchTo().frame(driver.findElement(By.id("acFileExplorer")));
			folderFrame = driver.findElement(By.xpath("//frame[@name='main']"));
			driver.switchTo().frame(folderFrame);
			String postReportName = dataTable.getData("General_Data", "ReportName");
			String	PostdynaXpath;
			if (waitForElement(driver, By.linkText(postReportName), 1000))
			{
				driver.findElement(By.linkText(postReportName)).click();
				driver.switchTo().defaultContent();
				driver.switchTo().frame(driver.findElement(By.id("acFileExplorer")));
				folderFrame = driver.findElement(By.xpath("//frame[@name='main']"));
				driver.switchTo().frame(folderFrame);
				driver.switchTo().frame(driver.findElement(By.id("toolbarframe")));
				String PostRowNumber = dataTable.getData("General_Data", "postRowNumber");
				String PageNumber = dataTable.getData("General_Data", "PageNumber");
				mObj.getEditPageToolbar().clear();
				mObj.getEditPageToolbar().sendKeys(PageNumber);
				mObj.getEditPageToolbar().sendKeys(Keys.ENTER);
				report.updateTestLog("Enter Page Number", "Page Number "+PageNumber+ " Entered ", Status.DONE);
				
				driver.switchTo().defaultContent();
				driver.switchTo().frame(driver.findElement(By.id("acFileExplorer")));
				folderFrame = driver.findElement(By.xpath("//frame[@name='main']"));
				driver.switchTo().frame(folderFrame);
				driver.switchTo().frame(driver.findElement(By.id("reportframe")));
				
				PostdynaXpath = "//body[@onunload='onUnload()']/div/div/div[" + PostRowNumber + "]/*///nobr";
		/*		ArrayList<String> column_Values = new ArrayList<String>();
				column_Values_Elements = (ArrayList<WebElement>) driver.findElements(By.xpath(PostdynaXpath));
				if (column_Values_Elements.size() > 0) 
				{
					for (int k = 0; k < column_Values_Elements.size(); k++) 
					{
						column_Values.add(k, column_Values_Elements.get(k).getText());
						report.updateTestLog("Text values", "Text Value of row " +PostRowNumber+"for Column "+(k+1)+ "is :" +column_Values_Elements.get(k).getText(), Status.DONE);
					}
				}
			}
			else
			{
				report.updateTestLog("Post Report Navigation", "Not able to find Report Folder", Status.FAIL);
			}
		}
		else 
		{
			report.updateTestLog("Post Report Folder Navigation", "Not able to find Post Report Folder structure", Status.FAIL);
		}
	} */
			
			public boolean waitForElement(WebDriver driver, By by, int time) 
			{
				boolean flag;
				try {
					driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
					assertTrue((isElementPresent(driver, by)));
					WebDriverWait wait = new WebDriverWait(driver, time);
					WebElement myElement = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
					driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
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
			public static boolean isElementPresent(WebDriver driver, By by) {
				try {
					driver.findElement(by);
					return true;
				} catch (NoSuchElementException e) {
					return false;
				}
			}
			
			
			public void openNewPreReportRow() throws InterruptedException 
			{
				Thread.sleep(5000);
				
				WebElement folderFrame1 = null;
				if (driver.getTitle().contains("Information Console")) 
			{
				String[] foldersName = dataTable.getData("General_Data", "PreReportFolderName").split(";");
				int LengthFolder = foldersName.length;
				for (int folderLoop = 0; folderLoop < foldersName.length - 1; folderLoop++)
				{
				folderFrame1 = driver.findElement(By.xpath("//frame[@name='main']"));
					driver.switchTo().frame(folderFrame1);
					
					if (waitForElement(driver, By.linkText(foldersName[folderLoop]), 100)) 
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
				driver.switchTo().defaultContent();
				folderFrame1 = driver.findElement(By.xpath("//frame[@name='main']"));
				driver.switchTo().frame(folderFrame1);
				String reportName = dataTable.getData("General_Data", "ReportName");
				waitForElement(driver, By.id("toolbarframe"), 400);
					driver.switchTo().frame(driver.findElement(By.id("toolbarframe")));
					String RowNumber = dataTable.getData("General_Data", "RowNumber");
						String PageNumber = dataTable.getData("General_Data", "PageNumber");
						mObj.getEditPageToolbar().clear();
						mObj.getEditPageToolbar().sendKeys(PageNumber);
						mObj.getEditPageToolbar().sendKeys(Keys.ENTER);
						report.updateTestLog("Enter Page Number", "Page Number "+PageNumber+ " Entered ", Status.DONE);
								String dynaXpath;
								driver.switchTo().defaultContent();
								folderFrame1 = driver.findElement(By.xpath("//frame[@name='main']"));
								driver.switchTo().frame(folderFrame1);
								driver.switchTo().frame(driver.findElement(By.id("reportframe")));
					//	driver.switchTo().frame(driver.findElement(By.id("reportframe")));
								
						ArrayList<WebElement> no_of_rows = new ArrayList<WebElement>();
						no_of_rows = (ArrayList<WebElement>) driver.findElements(By.xpath("//body[@onunload='onUnload()']/div/div/div"));
						
						System.out.println("Number Of rows:" +no_of_rows.size());
						dynaXpath = "//body[@onunload='onUnload()']/div/div/div[" + RowNumber + "]/*/nobr";
						column_Values_Properties = (ArrayList<WebElement>) driver.findElements(By.xpath(dynaXpath));
						
						if (column_Values_Properties.size() > 0)
						{
							for (int j = 0; j < column_Values_Properties.size(); j++)
							{
								
								column_Values.add(j, column_Values_Properties.get(j).getText());
								report.updateTestLog("Text values", "Text Value of row " +RowNumber+"for Column "+(j+1)+ "is :" +column_Values_Properties.get(j).getText(), Status.DONE);
							}
						}
						
				}
				else
				{
					report.updateTestLog("Prereport Folders", "Pre Report folders pages is not opened", Status.FAIL);
				}
				
				launchPostURL();
				
				
				WebElement folderFrame = null;
				String[] postFolderName = dataTable.getData("General_Data", "PostReportFolderName").split(";");
				if (waitForElement(driver, By.id("acFileExplorer"), 4000)) {
					//System.out.println("test1");
					for (int folders = 0; folders < (postFolderName.length-1); folders++) {
						driver.switchTo().frame(driver.findElement(By.id("acFileExplorer")));
						folderFrame = driver.findElement(By.xpath("//frame[@name='main']"));
						driver.switchTo().frame(folderFrame);
						String dynaXpathFolder1 = "//a[contains(@href,'" + postFolderName[folders] + "')]";
						if (waitForElement(driver, By.xpath(dynaXpathFolder1), 1000)) 
						{
							driver.findElement(By.xpath(dynaXpathFolder1)).click();
							driver.switchTo().defaultContent();
						} 
						else 
						{
							report.updateTestLog("Post Report Folder Navigation", "Post Report Folder Navigation" + folderFrame + "Has failed", Status.FAIL);
						}
					}
					driver.switchTo().frame(driver.findElement(By.id("acFileExplorer")));
					folderFrame = driver.findElement(By.xpath("//frame[@name='main']"));
					driver.switchTo().frame(folderFrame);
					String postReportName = dataTable.getData("General_Data", "ReportName");
					
					String	PostdynaXpath;
					if (waitForElement(driver, By.linkText(postReportName), 1000))
					{
						driver.findElement(By.linkText(postReportName)).click();
						
						driver.switchTo().defaultContent();
						driver.switchTo().frame(driver.findElement(By.id("acFileExplorer")));
						folderFrame = driver.findElement(By.xpath("//frame[@name='main']"));
						driver.switchTo().frame(folderFrame);
						driver.switchTo().frame(driver.findElement(By.id("toolbarframe")));
						String PostRowNumber = dataTable.getData("General_Data", "postRowNumber");
						String PageNumber = dataTable.getData("General_Data", "PageNumber");
						mObj.getEditPageToolbar().clear();
						String PostPageNumber =dataTable.getData("General_Data", "PostPageNumber");
						mObj.getEditPageToolbar().sendKeys(PostPageNumber);
						mObj.getEditPageToolbar().sendKeys(Keys.ENTER);
						report.updateTestLog("Enter Page Number", "Page Number "+PageNumber+ " Entered ", Status.DONE);
						
						driver.switchTo().defaultContent();
						driver.switchTo().frame(driver.findElement(By.id("acFileExplorer")));
						folderFrame = driver.findElement(By.xpath("//frame[@name='main']"));
						driver.switchTo().frame(folderFrame);
						driver.switchTo().frame(driver.findElement(By.id("reportframe")));
					//	ArrayList<WebElement> column_Values_Elements = new ArrayList<WebElement>();
						PostdynaXpath = "//body[@onunload='onUnload()']/div/div/div[" + PostRowNumber + "]/*/nobr";
						
						column_Values_Elements = (ArrayList<WebElement>) driver.findElements(By.xpath(PostdynaXpath));
						if (column_Values_Elements.size() > 0) 
						{
							for (int k = 0; k < column_Values_Elements.size(); k++) 
							{
								Postcolumn_Values.add(k, column_Values_Elements.get(k).getText());
								report.updateTestLog("Text values", "Text Value of row " +PostRowNumber+"for Column "+(k+1)+ "is :" +column_Values_Elements.get(k).getText(), Status.DONE);
							}
						}
					}
					else
					{
						report.updateTestLog("Post Report Navigation", "Not able to find Report Folder", Status.FAIL);
					}
				}
				else 
				{
					report.updateTestLog("Post Report Folder Navigation", "Not able to find Post Report Folder structure", Status.FAIL);
				}
				System.out.println("value for for loop is : " +column_Values.size());
				System.out.println("value for post loop is : " +Postcolumn_Values.size());
				for (int n = 0; n < column_Values.size(); n++) 
				{
					//System.out.println("inside for");
					System.out.println("Pre Value is :" +column_Values.get(n));
					System.out.println("Post value is : " +Postcolumn_Values.get(n));
					
					if((column_Values.get(n)).contentEquals(Postcolumn_Values.get(n)))
						report.updateTestLog("Compare Values", "Values Matches for Column :" +n+ "Pre Value is : " +column_Values.get(n)+"  and post value is : " +Postcolumn_Values.get(n), Status.PASS);
					else
						report.updateTestLog("Compare Values", "Values Mismatch for Column:"+n+" Pre Value is : " +column_Values.get(n)+"  and post value is : " +Postcolumn_Values.get(n) , Status.FAIL);
				}
				
			}
}