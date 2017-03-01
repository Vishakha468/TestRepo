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
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import uimap.ReportObjectidnetification;
import uimap.TestObjects;
import static org.junit.Assert.assertTrue;

public class ReportcomparisonComponents1 extends ReusableLibrary 
{
	public ReportcomparisonComponents1(ScriptHelper scriptHelper) 
	{
		super(scriptHelper);
		// TODO Auto-generated constructor stub
	}


	ArrayList<String> newTab = null;
	ReportObjectidnetification mObj = new ReportObjectidnetification(scriptHelper);
	TestObjects tObj = new TestObjects(scriptHelper);
	String oldTab, oldtab1;
	//**********Changes made by Vishakha on 20/10/2016****

	 ArrayList<ArrayList<ArrayList<String>>> pages = new ArrayList<ArrayList<ArrayList<String>>>();
	 ArrayList<ArrayList<ArrayList<String>>> Postpages = new ArrayList<ArrayList<ArrayList<String>>>();
	 ArrayList<ArrayList<String>> page = new ArrayList<ArrayList<String>>();
	 ArrayList<ArrayList<String>> postPage = new ArrayList<ArrayList<String>>();
	 ArrayList<String> strHeaderVal = new ArrayList<String>();
	 
	static int pagenoPreReport;
	static int CountofReport;
	static int reportIterator;
	int intPostPageNo;
	static int size2;
	static int reprtsAvailCount;

	//*************************************************
	public void storePreReportValues() throws IOException, InterruptedException {

		//	newTab.clear();
		String Report = dataTable.getData("General_Data", "ReportName");
		//String winName = "Management Console - ["+Report+"]";
		//System.out.println("Window Name is : "+winName);


		oldTab = driver.getWindowHandle();


		//System.out.println("Old tab is : "+driver.getTitle());
		Thread.sleep(1000);
		newTab = new ArrayList<String>(driver.getWindowHandles());

		newTab.remove(oldTab);
		if (reportIterator > 1) {
			oldtab1 = driver.getWindowHandle();
			newTab.remove(oldtab1);
		}
		int s3 = newTab.size();

		if (s3 > 1) {
			driver.switchTo().window(newTab.get(s3 - 1));
			System.out.println("Window name in second Iteration is : " + driver.getTitle());
		} else {
			driver.switchTo().window(newTab.get(0));

		}
		Thread.sleep(1000);
		WebElement frame3 = driver.findElement(By.id("reportframe"));
		driver.switchTo().frame(frame3);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String strPageVal = "";
		//Conditions for Different Reports

		if (Report.contains("MRF412")) {
			strPageVal = mObj.getPageNoMRF412().getText();

		} else if (Report.contains("MRF417")) {
			strPageVal = mObj.getPageNoMRF417().getText();
		} else if (Report.contains("MRF208")) {
			strPageVal = mObj.getPageNoMRF208().getText();
		} else if (Report.contains("MRO202")) {
			strPageVal = mObj.getPageNoMRF203n203().getText();
		} else if (Report.contains("MRO203")) {
			strPageVal = mObj.getPageNoMRF203n203().getText();
		} else {
			report.updateTestLog("Verify Report Name", "Invalid Report name", Status.FAIL);
		}

		ArrayList<String> strPartnerName1 = new ArrayList<String>();
		int intVarIncement = 0;
		int TotalPages = Integer.parseInt(strPageVal);
		String strPageTitle = mObj.getPageTitle().getText();
		//Condition for sampling 
		if (TotalPages > 25) {

			int LastPage = TotalPages;

			//for first page
			if (strPageTitle.contains("MultiUserTest License")) {
				report.updateTestLog("Verify Page Title", "Page Title Verification is successful for page 1", Status.PASS);
				strPartnerName1 = mObj.getAllValuesFromPage1();
				page.add(strPartnerName1);
			} else {
				report.updateTestLog("Verify Page Title", "Page Title Verification failed for Page 1", Status.FAIL);
			}
			//for last page
			driver.switchTo().window(newTab.get(0));
			driver.switchTo().frame("toolbarframe");
			String lspage = Integer.toString(LastPage);
			//System.out.println("value is : " + lspage);
			mObj.getEditPageToolbar().clear();
			mObj.getEditPageToolbar().sendKeys(lspage);

			mObj.getEditPageToolbar().sendKeys(Keys.ENTER);
			report.updateTestLog("Enter Last page", "Last Page is entered in Edit box", Status.DONE);
			Thread.sleep(1500);
			driver.switchTo().window(newTab.get(0));
			driver.switchTo().frame(frame3);
			strPageTitle = mObj.getPageTitle().getText();
			if (strPageTitle.contains("MultiUserTest License")) {
				report.updateTestLog("Verify Page Title", "Page Title Verification is successful for Last Page", Status.PASS);
				strPartnerName1 = mObj.getAllValuesFromPage1();
				page.add(strPartnerName1);
			} else {
				report.updateTestLog("Verify Page Title", "Page Title Verification failed for Last Page", Status.FAIL);
			}

			// For Other Pages
			if (s3 > 1) {
				driver.switchTo().window(newTab.get(1));
			} else {
				driver.switchTo().window(newTab.get(0));

			}
			driver.switchTo().frame("toolbarframe");
			mObj.getEditPageToolbar().clear();
			mObj.getEditPageToolbar().sendKeys("2");
			mObj.getEditPageToolbar().sendKeys(Keys.ENTER);
			report.updateTestLog("Enter Second page", "Second Page is entered in Edit box", Status.DONE);
			Thread.sleep(1500);
			if (s3 > 1) {
				driver.switchTo().window(newTab.get(1));
			} else {
				driver.switchTo().window(newTab.get(0));

			}
			driver.switchTo().frame(frame3);
			for (int Pageiter1 = 1; Pageiter1 < 5; Pageiter1++) 
		//	for (int Pageiter1 = 1; Pageiter1 < (TotalPages - 1); Pageiter1++) 
			{
				strPageTitle = mObj.getPageTitle().getText();
				intVarIncement = (int) Math.pow(2, Pageiter1);
				System.out.println("intVarIncement value is : " + intVarIncement);
				if (strPageTitle.contains("MultiUserTest License")) {
					report.updateTestLog("Verify Page Title", "Page Title Verification is successful for page " + intVarIncement, Status.PASS);
					strPartnerName1 = mObj.getAllValuesFromPage1();
					page.add(strPartnerName1);
				} else {
					report.updateTestLog("Verify Page Title", "Page Title Verification failed for Page " + intVarIncement, Status.FAIL);
				}

				if (s3 > 1) {
					driver.switchTo().window(newTab.get(1));
				} else {
					driver.switchTo().window(newTab.get(0));

				}

				driver.switchTo().frame("toolbarframe");
				Thread.sleep(1000);
				if (intVarIncement > TotalPages) {
					break;
				} else {
					mObj.getEditPageToolbar().clear();
					mObj.getEditPageToolbar().sendKeys(Integer.toString(intVarIncement));
					mObj.getEditPageToolbar().sendKeys(Keys.ENTER);
					synchronized (driver) {
						driver.wait(1000);
					}

					if (s3 > 1) {
						driver.switchTo().window(newTab.get(1));
					} else {
						driver.switchTo().window(newTab.get(0));

					}
					WebElement frame3_upd = driver.findElement(By.id("reportframe"));
					driver.switchTo().frame(frame3_upd);
				}

			}
			pages.add(page);
		} else {
			pagenoPreReport = TotalPages;
			// Loop for storing values page by page
			for (int Pageiter = 0; Pageiter < TotalPages; Pageiter++) {
				String strPageSource = driver.getPageSource();
				CommonData.strPageSource = strPageSource;
				strPageTitle = mObj.getPageTitle().getText();


				CommonData.strPageTitle = strPageTitle;
				if (strPageTitle.contains("MultiUserTest License")) {

					report.updateTestLog("Verify Page Title", "Page Title Verification is successful for page" + (Pageiter + 1), Status.PASS);
					String strPartnerNameList1;
					CommonData.strPageNumber = strPageVal;
					strPartnerName1 = mObj.getAllValuesFromPage1();
					page.add(strPartnerName1);
				} else {
					report.updateTestLog("Verify Page Title", "Page Title Verification", Status.FAIL);
				}
				if (s3 > 1) {
					driver.switchTo().window(newTab.get(s3 - 1));
				} else {
					driver.switchTo().window(newTab.get(0));

				}
				driver.switchTo().frame("toolbarframe");

				mObj.getNextLink().click();

				synchronized (driver) {
					driver.wait(1000);
				}
				if (s3 > 1) {

					driver.switchTo().window(newTab.get(s3 - 1));
				} else {
					driver.switchTo().window(newTab.get(0));

				}
				WebElement frame3_upd = driver.findElement(By.id("reportframe"));
				driver.switchTo().frame(frame3_upd);

			}

			pages.add(page);
		}
		report.updateTestLog("Storing Data", "Data has been stored for Pre report", Status.DONE);
		//End code for Pre Report
		driver.switchTo().defaultContent();
		driver.close();

		driver.switchTo().window(oldTab);
		driver.switchTo().defaultContent();
		driver.switchTo().frame("TableFrame");
		tObj.getCompletedLnk().click();
		Thread.sleep(1200);
		driver.switchTo().frame("ifrMainFrame");
	}

	//Launch Post URL
	public void launchPostURL() throws InterruptedException {
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

	// code started for Post Report
	public void getPostReportValues() throws InterruptedException {
		By by = By.id("acFileExplorer");
		waitForElement(driver, by, 3000);
		driver.switchTo().frame(driver.findElement(By.id("acFileExplorer")));
		WebElement folderFrame = driver.findElement(By.xpath("//frame[@name='main']"));
		driver.switchTo().frame(folderFrame);
		driver.findElement(By.xpath("//a[contains(@href,'Sox2015')]")).click();
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.id("acFileExplorer")));
		WebElement documentsFrame = driver.findElement(By.xpath("//frame[@name='main']"));
		driver.switchTo().frame(documentsFrame);
		driver.findElement(By.xpath("//a[contains(@href,'/Sox2015/MRF200_SOX15.ROI;1')]")).click();
		driver.switchTo().frame(driver.findElement(By.id("toolbarframe")));
		String noOfPages = driver.findElement(By.xpath("//input[@name='editThis']/parent::td/following-sibling::td/font")).getText().trim();
		noOfPages = noOfPages.substring(3, noOfPages.length());
		int intPostPageNo1;
		intPostPageNo1 = Integer.parseInt(noOfPages);
		intPostPageNo = intPostPageNo1;
		ArrayList<String> currentReportValues = new ArrayList<String>();
		driver.switchTo().defaultContent();

		for (int i = 0; i < intPostPageNo; i++) {
			driver.switchTo().frame(driver.findElement(By.id("acFileExplorer")));
			driver.switchTo().frame(driver.findElement(By.name("main")));
			driver.switchTo().frame(driver.findElement(By.id("reportframe")));
			currentReportValues = mObj.getAllValuesFromPage2();
			driver.switchTo().defaultContent();
			driver.switchTo().frame(driver.findElement(By.id("acFileExplorer")));
			driver.switchTo().frame(driver.findElement(By.name("main")));
			driver.switchTo().frame(driver.findElement(By.id("toolbarframe")));
			driver.findElement(By.name("button4")).click();
			driver.switchTo().defaultContent();
			page.add(currentReportValues);

		}

		Postpages.add(page);


	}

	public void comparedata() {
		int sizepostPage = Postpages.size();

		int flag = 0;
		int flag1 = 0;


		if (intPostPageNo == pagenoPreReport) {

			for (int c = 0; c < sizepostPage; c++) {

				for (int t = 0; t < Postpages.get(c).size(); t++) {
					int q = pages.get(c).size();


					for (int n = 0; n < Postpages.get(c).get(t).size(); n++) {
						//System.out.println("in Third loop");
						if ((Postpages.get(c).get(t).get(n)).equals(pages.get(c).get(t).get(n))) {
							flag++;
							//report.updateTestLog("Data Comparison", "Values are same for " +Postpages.get(c).get(t).get(n) , Status.PASS);
						} else {
							flag = 0;
							report.updateTestLog("Data Comparison", "Values Mismatches : Pre Report value is:" + pages.get(c).get(t).get(n) + "Post report value is :" + Postpages.get(c).get(t).get(n), Status.FAIL);
						}

					}
					/*}
                            else
							{
								flag1 = 1;

							}  */

					if (flag > 0) {
						report.updateTestLog("Data Comparison", "Values matches for page no " + (t + 1), Status.PASS);
					}
					if (flag1 == 1) {
						report.updateTestLog("Data Comparison", "Values Count Does not match for page no " + (t + 1), Status.FAIL);
					}
				}


			}

		} else {
			report.updateTestLog("Data Row Validation", "Data Rows are not same", Status.FAIL);
		}


	}


	


	public void completeReportComparison() throws InterruptedException, IOException {
		//int reprtsAvailCount = 3;
		CountofReport = CountofReport + 1;
		reportIterator = 0;
		//launchPreReport();
	//	openReport();
		storePreReportValues();
		launchPostURL();
		getPostReportValues();
		comparedata();
		System.out.println("Iterartion 1 is done");

		for (reportIterator = 1; reportIterator < reprtsAvailCount; reportIterator++) {
		//	launchPreReport();
		//	openReport();
			storePreReportValues();
			launchPostURL();
			getPostReportValues();
			comparedata();

			report.updateTestLog("Report Comparison", "Report Comparison Done for Report :" + (reportIterator + 1), Status.PASS);
			//System.out.println("Window Name after one iteration:" +driver.getTitle());
			Thread.sleep(500);

		}
	}

	public void completeReportComparison1() throws Exception 
	{
Thread.sleep(1000);
int flagPrepg,flagPostPg;
flagPrepg = 0;
flagPostPg = 0;

		String PreURL = dataTable.getData("General_Data", "PreURL");
		driver.get(PreURL);
		tObj.getUserIdTextBox().clear();
		tObj.getUserIdTextBox().sendKeys(dataTable.getData("General_Data", "UserId"));
		tObj.getPasswordTextBox().sendKeys(dataTable.getData("General_Data", "Password"));
		tObj.getLoginButton().click();


		HashMap<Integer, HashMap<Integer, ArrayList<String>>> pre_reportData = new HashMap<Integer, HashMap<Integer, ArrayList<String>>>();
		HashMap<Integer, HashMap<Integer, ArrayList<String>>> post_Pages_Data = new HashMap<Integer, HashMap<Integer, ArrayList<String>>>();

		try {
			Thread.sleep(1200);
			//            System.out.println("Page title is: " + driver.getTitle());
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

					}
					else 
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
			//	Thread.sleep(3000);
				//if (waitForElement(driver, By.linkText(reportName), 3000))
			//	{
				//	driver.findElement(By.linkText(reportName)).click();
					//driver.findElements(By.xpath(".//*[@id='ItemsTable']//a")).get(0).click();
					
				//	int s = driver.findElements(By.xpath(".//*[@id='ItemsTable']//a")).size();
					//driver.findElements(By.xpath(".//*[@id='ItemsTable']//a")).get(s-1).click();
					
					ArrayList<String> tab = new ArrayList<String>(driver.getWindowHandles());
					driver.switchTo().window(tab.get(1));
				//	System.out.println("Window Title : " +driver.getTitle());
					waitForElement(driver, By.id("toolbarframe"), 4000);
					driver.switchTo().frame(driver.findElement(By.id("toolbarframe")));
					//System.out.println("Switched to frame");
					String no_Of_Pages = driver.findElement(By.xpath("//input[@name='editThis']/parent::td/following-sibling::td/font")).getText().trim();
					no_Of_Pages = no_Of_Pages.substring(3, no_Of_Pages.length());
					
				//	System.out.println("value of no_Of_Pages:  " +no_Of_Pages);
					
					int no_OfPages = Integer.parseInt(no_Of_Pages);
					
					//System.out.println("No of pages: " +no_OfPages);
					
					driver.switchTo().defaultContent();
					driver.switchTo().frame(driver.findElement(By.id("reportframe")));
					ArrayList<WebElement> no_of_rows = new ArrayList<WebElement>();
					no_of_rows = (ArrayList<WebElement>) driver.findElements(By.xpath("//body[@onunload='onUnload()']/div/div/div"));
					HashMap<Integer, ArrayList<String>> table_data = null;
					//ArrayList<String> column_Values=new ArrayList<>();            
					ArrayList<WebElement> column_Values_Properties = new ArrayList<WebElement>();
					String dynaXpath;
			//	for (int l = 1; l <= 2; l++)
					for (int l = 1; l <= no_OfPages; l++)
					{
						if(reportName.contains("MRF601"))
						{
							strHeaderVal = mObj.getHeaderValuesrprt601();
						}
						else if(reportName.contains("MRF202"))
							strHeaderVal =mObj.getHeaderValuesrprt202();
						else if(reportName.equalsIgnoreCase("MRA112") ||reportName.equalsIgnoreCase("MRA114") )
						strHeaderVal= mObj.getHeaderValuesrprtMRA112_all();
						else if(reportName.equalsIgnoreCase("MRA103"))
							strHeaderVal=mObj.getrprt103AHeaderelementValues();
						else if(reportName.equalsIgnoreCase("MRF604"))
						strHeaderVal=mObj.getHeaderValuesrprt604();
						else if(reportName.equalsIgnoreCase("MRM104"))
							strHeaderVal = mObj.getHeaderValuesMRM104();
						else if(reportName.equalsIgnoreCase("MRF458"))
							strHeaderVal = mObj.getHeaderValuesMRF458();
						else if(reportName.equalsIgnoreCase("MRF807"))
							strHeaderVal = mObj.getHeaderValuesrprt807();
						else
						{
						strHeaderVal = mObj.getHeaderValuesPre();
						}
						no_of_rows = (ArrayList<WebElement>) driver.findElements(By.xpath("//body[@onunload='onUnload()']/div/div/div"));
						table_data = new HashMap<Integer, ArrayList<String>>();
						for (int i = 2; i <= no_of_rows.size(); i++)
						{
							
							dynaXpath = "//body[@onunload='onUnload()']/div/div/div[" + i + "]/*/nobr";
							column_Values_Properties = (ArrayList<WebElement>) driver.findElements(By.xpath(dynaXpath));
							ArrayList<String> column_Values = new ArrayList<String>();
							if (column_Values_Properties.size() > 0)
							{
								for (int j = 0; j < column_Values_Properties.size(); j++)
								{
									column_Values.add(j, column_Values_Properties.get(j).getText());
								}
							}

							table_data.put(i, column_Values);

						}
						
						pre_reportData.put(l, table_data);
						flagPrepg =1;
					//	System.out.println("Pre data is : " +pre_reportData);
						//table_data.clear();
						if(flagPrepg == 0)
						{
							report.updateTestLog("Pre Report"+reportName, "Data is not been stored from Page No" +l , Status.FAIL);
						}
						
						if(l<=3||l>no_OfPages-3)
						{
							report.updateTestLog("Pre Report"+reportName, "Page No" +l, Status.PASS);
							//System.out.println("Pre data for page "+l+ " is : " +pre_reportData.get(l));
						}
						page.add(strHeaderVal);
						driver.switchTo().defaultContent();
						driver.switchTo().frame(driver.findElement(By.id("toolbarframe")));
						driver.findElement(By.linkText("Next")).click();
						driver.switchTo().defaultContent();
						waitForElement(driver, By.id("reportframe"), 2000);
						driver.switchTo().frame(driver.findElement(By.id("reportframe")));
						
						flagPrepg =0;
					}
					pages.add(page);
					driver.switchTo().window(tab.get(0));
		//	} 
			//	else 
			//	{
					//report.updateTestLog("Report Selection", "Not able to find the report Name", Status.FAIL);
			//	} 

			} 
			else
			{
				report.updateTestLog("Prereport Folders", "Pre Report folders pages is not opened", Status.FAIL);
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		String PostURL = dataTable.getData("General_Data", "PostURL");
		driver.get(PostURL);

		tObj.getPostUserIdTextBox().clear();

		tObj.getPostUserIdTextBox().sendKeys(dataTable.getData("General_Data", "UserId"));
		tObj.getPostPasswordTextBox().sendKeys(dataTable.getData("General_Data", "Password"));
		tObj.getPostLoginButton().click();
		try {
			WebElement folderFrame = null;
			String[] postFolderName = dataTable.getData("General_Data", "PostReportFolderName").split(";");
			if (waitForElement(driver, By.id("acFileExplorer"), 4000)) {
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
					{
						report.updateTestLog("Post Report Folder Navigation", "Post Report Folder Navigation" + folderFrame + "Has failed", Status.FAIL);
					}
				}
				
				driver.switchTo().frame(driver.findElement(By.id("acFileExplorer")));
				folderFrame = driver.findElement(By.xpath("//frame[@name='main']"));
				driver.switchTo().frame(folderFrame);
				String postReportName = dataTable.getData("General_Data", "PostReportName");
				
				System.out.println("Post report name : " +postReportName);
				
				if (waitForElement(driver, By.linkText(postReportName), 1000)) 
				{
					driver.findElement(By.linkText(postReportName)).click();
				//	System.out.println("Pass1");
					driver.switchTo().defaultContent();
				//	System.out.println("Pass2");
					driver.switchTo().frame(driver.findElement(By.id("acFileExplorer")));
				//	System.out.println("Pass3");
					folderFrame = driver.findElement(By.xpath("//frame[@name='main']"));
					driver.switchTo().frame(folderFrame);
				//	System.out.println("Pass4");
					driver.switchTo().frame(driver.findElement(By.id("toolbarframe")));
				//	System.out.println("Pass5");
					String no_Of_Pages = driver.findElement(By.xpath("//input[@name='editThis']/parent::td/following-sibling::td/font")).getText().trim();
					no_Of_Pages = no_Of_Pages.substring(3, no_Of_Pages.length());
					int no_OfPages = Integer.parseInt(no_Of_Pages);
					driver.switchTo().defaultContent();
					HashMap<Integer, ArrayList<String>> post_Page_TableData = null;
				//	for (int i = 1; i <= 2; i++)
				for (int i = 1; i <= no_OfPages; i++) 
					{
						driver.switchTo().frame(driver.findElement(By.id("acFileExplorer")));
						folderFrame = driver.findElement(By.xpath("//frame[@name='main']"));
						driver.switchTo().frame(folderFrame);
						driver.switchTo().frame(driver.findElement(By.id("reportframe")));
						/* Changes on 11/04/2016
						 * 
						 * 
						 */
						if(postReportName.contains("MRF601"))
						{
							strHeaderVal = mObj.getHeaderValuesrprt601();
						}
						else if(postReportName.contains("MRF202"))
							strHeaderVal =mObj.getHeaderValuesrprt202();
						else if(postReportName.equalsIgnoreCase("MRA112") ||postReportName.equalsIgnoreCase("MRA114") )
							strHeaderVal= mObj.getHeaderValuesrprtMRA112_all();
						else if(postReportName.equalsIgnoreCase("MRA103"))
							strHeaderVal=mObj.getrprt103AHeaderelementValues();
						else if(postReportName.equalsIgnoreCase("MRF604"))
							strHeaderVal=mObj.getHeaderValuesrprt604();
						else if(postReportName.equalsIgnoreCase("MRM104"))
							strHeaderVal = mObj.getHeaderValuesMRM104();
						else if(postReportName.equalsIgnoreCase("MRF458"))
							strHeaderVal = mObj.getHeaderValuesMRF458();
						else if(postReportName.equalsIgnoreCase("MRF807"))
							strHeaderVal = mObj.getHeaderValuesrprt807();
							else
						strHeaderVal = mObj.getHeaderValuesPost();
						
						List<WebElement> no_Of_Rows = driver.findElements(By.xpath("//body[@onunload='onUnload()']/div/div/div"));
						String dynaXpath;
						ArrayList<WebElement> column_Values_Elements = new ArrayList<WebElement>();
						//ArrayList<String> column_Values=new ArrayList<>();
						post_Page_TableData = new HashMap<Integer, ArrayList<String>>();
						for (int j = 2; j <= no_Of_Rows.size(); j++) 
						{
							dynaXpath = "//body[@onunload='onUnload()']/div/div/div[" + j + "]/*/nobr";
							ArrayList<String> column_Values = new ArrayList<String>();
							column_Values_Elements = (ArrayList<WebElement>) driver.findElements(By.xpath(dynaXpath));
							if (column_Values_Elements.size() > 0) {
								for (int k = 0; k < column_Values_Elements.size(); k++) {
									column_Values.add(k, column_Values_Elements.get(k).getText());
								}
							}

							post_Page_TableData.put(j, column_Values);
							//column_Values.clear();
							
						}
						post_Pages_Data.put(i, post_Page_TableData);
						//System.out.println("Past Pages data is : " +post_Pages_Data);
						// post_Page_TableData.clear();
						flagPostPg = 1;
						if(flagPostPg==0)
							report.updateTestLog("Post Report "+postReportName, "Data is not been stored from Page No" +i, Status.FAIL);
						if( i<=3||i>no_OfPages-3){
							report.updateTestLog("Post Report "+postReportName, "Page No" +i, Status.PASS);
							//System.out.println("Post Page data for page number: "+i+ "  is : " +post_Pages_Data.get(i));
						}
						postPage.add(strHeaderVal);
						
						driver.switchTo().defaultContent();
						driver.switchTo().frame(driver.findElement(By.id("acFileExplorer")));
						folderFrame = driver.findElement(By.xpath("//frame[@name='main']"));
						driver.switchTo().frame(folderFrame);
						driver.switchTo().frame(driver.findElement(By.id("toolbarframe")));
						
						driver.findElement(By.linkText("Next")).click();
						driver.switchTo().defaultContent();
						waitForElement(driver, By.id("acFileExplorer"), 2000);
						
						flagPostPg =0;
						
					}
					Postpages.add(postPage);
					
					report.updateTestLog("Storing Data", "Data has been stored for Pre report", Status.PASS);
					
					compareHeaders();
					
					comparePreandPost(pre_reportData, post_Pages_Data);
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
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	public void comparePreandPost(HashMap<Integer, HashMap<Integer, ArrayList<String>>> preData, HashMap<Integer, HashMap<Integer, ArrayList<String>>> postData) {
		if (preData.size() == postData.size())
		{
			HashMap<Integer, HashMap<Integer, ArrayList<String>>> postData1;
			HashMap<Integer, ArrayList<String>> prePageData;
			HashMap<Integer, ArrayList<String>> postPageData;
			ArrayList<String> prePageDataRowData = new ArrayList<String>();
			ArrayList<String> postPageDataRowData = new ArrayList<String>();
			postData1 = postData;
			
			//System.out.println("pre data size is :" +preData.size());
		/*	System.out.println("size of pre data row is :" +prePageDataRowData.size());
			System.out.println("size of post data row is :" +postData1.size());*/
			
			for (int i = 1; i <= preData.size(); i++) 
			{
				
				prePageData = preData.get(i);
				for (int j = 2; j <= prePageData.size() + 1; j++) 
				{
						prePageDataRowData = prePageData.get(j);
						
					if (prePageDataRowData.size() > 0) 
					{
						boolean arrayFlag = false;
						
						for (int k = 1; k <= postData1.size(); k++)
						{
							boolean flag = false;
							postPageData = postData1.get(k);
							int l = 2;
							int counter = 0;
							while (l <= postPageData.size() + 1) 
							{
								
								postPageDataRowData = postPageData.get(l);
								
								if (postPageDataRowData.size() > 0) 
								{
									if (prePageDataRowData.get(0).equals(postPageDataRowData.get(0))) 
									{
										if (prePageDataRowData.size() == postPageDataRowData.size()) 
										{
											for (int m = 0; m < prePageDataRowData.size(); m++) 
											{
												if (prePageDataRowData.get(m).contentEquals(postPageDataRowData.get(m))) 
												{
													counter = counter + 1;
													
												}
											}
											
										/*	if((i==269&&j==2))
											{
												System.out.println("Counter Value is :" +counter);
												System.out.println("page values are:" +prePageData);
												System.out.println("Row values are : " +prePageDataRowData);
											} */
											
											if (counter == prePageDataRowData.size()) 
											{
												// System.out.println("Page Number"+i+"Row No " +j +"Available");
												postData1.get(k).get(l).clear();
												report.updateTestLog("Data Comparision", "Pre-PageNo " + i + " and Row No " + j +"is present in Post Report page  " +k+"in row " +l , Status.DONE);
												flag = true;
												arrayFlag = true;
												break;
											}
										/*	if((k==269&&l==2))
											{
												//System.out.println("Counter Value is :" +counter);
												System.out.println("Post page values are:" +prePageData);
												System.out.println("Post Row values are : " +postPageDataRowData);
											} */
										}
									}
								}
								l++;
								counter = 0;
							}
	
							
							if (flag) 
							{
								break;                           
							}
						}
						if (!arrayFlag) 
						{
							//System.out.println("data is : " +postData1.get(k).get(l).getText());
							report.updateTestLog("Data Comparision", "Pre-PageNo" + i + " and Row No" + j +" is not found in Post report", Status.FAIL);
						}
					}
				}
			}
		}
		else 
		{
			report.updateTestLog("Data Validation", "Pre and Post Data Pages does not matched", Status.FAIL);
		}
		
	}

	public boolean waitForElement(WebDriver driver, By by, int time) 
	{
		boolean flag;
		try {
			driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
			assertTrue((isElementPresent(driver, by)));
			WebDriverWait wait = new WebDriverWait(driver, time);
			WebElement myElement = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
			if (myElement != null)
				flag = true;
			else
				flag = false;

		} 
		catch (Exception e) {
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

	public static boolean waitForElementForSecond(WebDriver driver, By by, int time) {
		boolean flag;
		try {
			WebDriverWait wait = new WebDriverWait(driver, time);
			WebElement myElement = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
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

	public static boolean isElementsPresent(WebDriver driver, By by) {
		try {
			driver.findElements(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
	
	public void compareHeaders() {
		int sizepostPage = Postpages.size();

		int flag = 0;
		int flag1 = 0;
	// 	intPostPageNo = 1;
	 //	pagenoPreReport =1;
		
		if (intPostPageNo == pagenoPreReport) 
		{

			for (int c = 0; c < sizepostPage; c++)
			{
				for (int t = 0; t < Postpages.get(c).size(); t++) 
				{
						for (int n = 0; n < Postpages.get(c).get(t).size(); n++) 
						{
						//System.out.println("in Third loop");
						if ((Postpages.get(c).get(t).get(n)).equalsIgnoreCase(pages.get(c).get(t).get(n))) 
						{
							flag++;
							//report.updateTestLog("Data Comparison", "Values are same for " +Postpages.get(c).get(t).get(n) , Status.PASS);
						} 
						else 
						{
							flag = 0;
							report.updateTestLog("Header Values Comparison", "Value for header Mismatches : Pre Report value is:" + pages.get(c).get(t).get(n) + "Post report value is :" + Postpages.get(c).get(t).get(n), Status.FAIL);
						}

					}
					/*}
                            else
							{
								flag1 = 1;

							}  */

					if (flag > 0) 
					{
						report.updateTestLog("Header Values Comparison", "Header Values matches for page no " + (t + 1), Status.DONE);
					}
				/*	if (flag1 == 1)
					{
						report.updateTestLog("Header Values Comparison", "Header Values Count Does not match for page no " + (t + 1), Status.FAIL);
					} */
				}
			}

		} 
		else 
		{
			report.updateTestLog("Data Row Validation for Header", "Header Data Rows are not same", Status.FAIL);
		}


	}

	
	public void completeReportComparisonNew() throws Exception 
	{
Thread.sleep(1000);
int flagPrepg,flagPostPg;
flagPrepg = 0;
flagPostPg = 0;
String TC_ID = dataTable.getData("General_Data", "TC_ID");
		String PreURL = dataTable.getData("General_Data", "PreURL");
		driver.get(PreURL);
		tObj.getNewUserIdTextBox().clear();
		tObj.getNewUserIdTextBox().sendKeys(dataTable.getData("General_Data", "UserId"));
		tObj.getNewPasswordTextBox().sendKeys(dataTable.getData("General_Data", "Password"));
		tObj.getNewLoginButton().click();


		HashMap<Integer, HashMap<Integer, ArrayList<String>>> pre_reportData = new HashMap<Integer, HashMap<Integer, ArrayList<String>>>();
		HashMap<Integer, HashMap<Integer, ArrayList<String>>> post_Pages_Data = new HashMap<Integer, HashMap<Integer, ArrayList<String>>>();

		try 
		{
			Thread.sleep(1000);
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
				waitForElement(driver, By.id("toolbarframe"), 100);
					driver.switchTo().frame(driver.findElement(By.id("toolbarframe")));
					String no_Of_Pages = driver.findElement(By.xpath("//input[@name='editThis']/parent::td/following-sibling::td/font")).getText().trim();
					no_Of_Pages = no_Of_Pages.substring(3, no_Of_Pages.length());
					int no_OfPages = Integer.parseInt(no_Of_Pages);
					driver.switchTo().defaultContent();
					ArrayList<WebElement> no_of_rows = new ArrayList<WebElement>();
					no_of_rows = (ArrayList<WebElement>) driver.findElements(By.xpath("//body[@onunload='onUnload()']/div/div/div"));
					HashMap<Integer, ArrayList<String>> table_data = null;
					ArrayList<WebElement> column_Values_Properties = new ArrayList<WebElement>();
					String dynaXpath;
			//	for (int l = 1; l <= 2; l++)
					for (int l = 1; l <= no_OfPages; l++)
					{
					
					folderFrame1 = driver.findElement(By.xpath("//frame[@name='main']"));
					driver.switchTo().frame(folderFrame1);
					driver.switchTo().frame(driver.findElement(By.id("reportframe")));
					
						if(reportName.contains("MRF601"))
						{
							strHeaderVal = mObj.getHeaderValuesrprt601();
						}
						else if(reportName.contains("MRF202"))
							strHeaderVal =mObj.getHeaderValuesrprt202();
						else if(reportName.equalsIgnoreCase("MRA112") ||reportName.equalsIgnoreCase("MRA114") )
						strHeaderVal= mObj.getHeaderValuesrprtMRA112_all();
						else if(reportName.equalsIgnoreCase("MRA103"))
							strHeaderVal=mObj.getrprt103AHeaderelementValues();
						else if(reportName.equalsIgnoreCase("MRF604"))
						strHeaderVal=mObj.getHeaderValuesrprt604();
						else if(reportName.equalsIgnoreCase("MRM104"))
							strHeaderVal = mObj.getHeaderValuesMRM104();
						else if(reportName.equalsIgnoreCase("MRF458"))
							strHeaderVal = mObj.getHeaderValuesMRF458();
						else if(reportName.equalsIgnoreCase("MRF807"))
							strHeaderVal = mObj.getHeaderValuesrprt807();
						else if(TC_ID.contains("Report_MRO219"))
							strHeaderVal = mObj.getHeaderValuesPreMRO219();
						else if(TC_ID.contains("Report_MRA110_UKNOdata_Daily"))
							strHeaderVal = mObj.getHeaderValuesMRA110_UK_NoData();
						else if(reportName.equalsIgnoreCase("MRF303"))
							strHeaderVal = null;
						else
						{
						strHeaderVal = mObj.getHeaderValuesPre();
						}
						no_of_rows = (ArrayList<WebElement>) driver.findElements(By.xpath("//body[@onunload='onUnload()']/div/div/div"));
						table_data = new HashMap<Integer, ArrayList<String>>();
						for (int i = 2; i <= no_of_rows.size(); i++)
						{
							//dynaXpath = "(//body[@onunload='onUnload()']/div/div/div)[" + i + "]//nobr"; //MRF303_Report
						dynaXpath = "//body[@onunload='onUnload()']/div/div/div[" + i + "]/*/nobr";
							column_Values_Properties = (ArrayList<WebElement>) driver.findElements(By.xpath(dynaXpath));
							ArrayList<String> column_Values = new ArrayList<String>();
							if (column_Values_Properties.size() > 0)
							{
								for (int j = 0; j < column_Values_Properties.size(); j++)
								{
									column_Values.add(j, column_Values_Properties.get(j).getText());
								}
							}

							table_data.put(i, column_Values);

						}
						pre_reportData.put(l, table_data);
						flagPrepg =1;
						if(flagPrepg == 0)
						{
							report.updateTestLog("Pre Report"+reportName, "Data is not been stored from Page No" +l , Status.FAIL);
						}
						
						if(l<=3||l>no_OfPages-3)
						{
							report.updateTestLog("Pre Report"+reportName, "Page No" +l, Status.PASS);
						}
						page.add(strHeaderVal);
						driver.switchTo().defaultContent();
						folderFrame1 = driver.findElement(By.xpath("//frame[@name='main']"));
						driver.switchTo().frame(folderFrame1);
						driver.switchTo().frame(driver.findElement(By.id("toolbarframe")));
						driver.findElement(By.linkText("Next")).click();
						driver.switchTo().defaultContent();
						flagPrepg =0;
					}
					pages.add(page);
						} 
			else
			{
				report.updateTestLog("Prereport Folders", "Pre Report folders pages is not opened", Status.FAIL);
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		String PostURL = dataTable.getData("General_Data", "PostURL");
		driver.get(PostURL);

		tObj.getPostUserIdTextBox().clear();

		tObj.getPostUserIdTextBox().sendKeys(dataTable.getData("General_Data", "UserId"));
		tObj.getPostPasswordTextBox().sendKeys(dataTable.getData("General_Data", "Password"));
		tObj.getPostLoginButton().click();
		try {
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
					{
						report.updateTestLog("Post Report Folder Navigation", "Post Report Folder Navigation" + folderFrame + "Has failed", Status.FAIL);
					}
				}
				
				driver.switchTo().frame(driver.findElement(By.id("acFileExplorer")));
				folderFrame = driver.findElement(By.xpath("//frame[@name='main']"));
				driver.switchTo().frame(folderFrame);
				String postReportName = dataTable.getData("General_Data", "PostReportName");
				
				//System.out.println("Post report name : " +postReportName);
				
				if (waitForElement(driver, By.linkText(postReportName), 1000)) 
				{
					driver.findElement(By.linkText(postReportName)).click();
				//	System.out.println("Pass1");
					driver.switchTo().defaultContent();
				//	System.out.println("Pass2");
					driver.switchTo().frame(driver.findElement(By.id("acFileExplorer")));
				//	System.out.println("Pass3");
					folderFrame = driver.findElement(By.xpath("//frame[@name='main']"));
					driver.switchTo().frame(folderFrame);
				//	System.out.println("Pass4");
					driver.switchTo().frame(driver.findElement(By.id("toolbarframe")));
				//	System.out.println("Pass5");
					String no_Of_Pages = driver.findElement(By.xpath("//input[@name='editThis']/parent::td/following-sibling::td/font")).getText().trim();
					no_Of_Pages = no_Of_Pages.substring(3, no_Of_Pages.length());
					int no_OfPages = Integer.parseInt(no_Of_Pages);
					driver.switchTo().defaultContent();
					HashMap<Integer, ArrayList<String>> post_Page_TableData = null;
				//	for (int i = 1; i <= 2; i++)
				
					for (int i = 1; i <= no_OfPages; i++) 
					{
						driver.switchTo().frame(driver.findElement(By.id("acFileExplorer")));
						folderFrame = driver.findElement(By.xpath("//frame[@name='main']"));
						driver.switchTo().frame(folderFrame);
						driver.switchTo().frame(driver.findElement(By.id("reportframe")));
						/* Changes on 11/04/2016
						 * 
						 * 
						 */
						if(postReportName.contains("MRF601"))
						{
							strHeaderVal = mObj.getHeaderValuesrprt601();
						}
						else if(postReportName.contains("MRF202"))
							strHeaderVal =mObj.getHeaderValuesrprt202();
						else if(postReportName.equalsIgnoreCase("MRA112") ||postReportName.equalsIgnoreCase("MRA114") )
							strHeaderVal= mObj.getHeaderValuesrprtMRA112_all();
						else if(postReportName.equalsIgnoreCase("MRA103"))
							strHeaderVal=mObj.getrprt103AHeaderelementValues();
						else if(postReportName.equalsIgnoreCase("MRF604"))
							strHeaderVal=mObj.getHeaderValuesrprt604();
						else if(postReportName.equalsIgnoreCase("MRM104"))
							strHeaderVal = mObj.getHeaderValuesMRM104();
						else if(postReportName.equalsIgnoreCase("MRF458"))
							strHeaderVal = mObj.getHeaderValuesMRF458();
						else if(postReportName.equalsIgnoreCase("MRF807"))
							strHeaderVal = mObj.getHeaderValuesrprt807();
						else if(TC_ID.contains("Report_MRO219"))
							strHeaderVal = mObj.getHeaderValuesPreMRO219();
						else if(TC_ID.contains("Report_MRA110_UKNOdata_Daily"))
							strHeaderVal = mObj.getHeaderValuesMRA110_UK_NoData();
						else if(postReportName.equalsIgnoreCase("MRF303"))
							strHeaderVal = null;
							else
						strHeaderVal = mObj.getHeaderValuesPost();
						
						List<WebElement> no_Of_Rows = driver.findElements(By.xpath("//body[@onunload='onUnload()']/div/div/div"));
						String dynaXpath;
						ArrayList<WebElement> column_Values_Elements = new ArrayList<WebElement>();
						//ArrayList<String> column_Values=new ArrayList<>();
						post_Page_TableData = new HashMap<Integer, ArrayList<String>>();
						for (int j = 2; j <= no_Of_Rows.size(); j++) 
						{
							dynaXpath = "//body[@onunload='onUnload()']/div/div/div[" + j + "]/*/nobr";
							ArrayList<String> column_Values = new ArrayList<String>();
							column_Values_Elements = (ArrayList<WebElement>) driver.findElements(By.xpath(dynaXpath));
							if (column_Values_Elements.size() > 0) {
								for (int k = 0; k < column_Values_Elements.size(); k++) {
									column_Values.add(k, column_Values_Elements.get(k).getText());
								}
							}

							post_Page_TableData.put(j, column_Values);
							//column_Values.clear();
							
						}
						post_Pages_Data.put(i, post_Page_TableData);
						//System.out.println("Past Pages data is : " +post_Pages_Data);
						// post_Page_TableData.clear();
						flagPostPg = 1;
						if(flagPostPg==0)
							report.updateTestLog("Post Report "+postReportName, "Data is not been stored from Page No" +i, Status.FAIL);
						if( i<=3||i>no_OfPages-3){
							report.updateTestLog("Post Report "+postReportName, "Page No" +i, Status.PASS);
							//System.out.println("Post Page data for page number: "+i+ "  is : " +post_Pages_Data.get(i));
						}
						postPage.add(strHeaderVal);
						
						driver.switchTo().defaultContent();
						driver.switchTo().frame(driver.findElement(By.id("acFileExplorer")));
						folderFrame = driver.findElement(By.xpath("//frame[@name='main']"));
						driver.switchTo().frame(folderFrame);
						driver.switchTo().frame(driver.findElement(By.id("toolbarframe")));
						
						driver.findElement(By.linkText("Next")).click();
						driver.switchTo().defaultContent();
						waitForElement(driver, By.id("acFileExplorer"), 2000);
						
						flagPostPg =0;
						
					}
					Postpages.add(postPage);
					
					report.updateTestLog("Storing Data", "Data has been stored for Pre report", Status.PASS);
					
					compareHeaders();
					
					comparePreandPost(pre_reportData, post_Pages_Data);
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
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	public void completeReportComparisonMRF303() throws Exception 
	
	{
		//completeReportComparisonNew();
		//Login to Pre
		String PreURL = dataTable.getData("General_Data", "PreURL");
		driver.get(PreURL);
		tObj.getNewUserIdTextBox().clear();
		tObj.getNewUserIdTextBox().sendKeys(dataTable.getData("General_Data", "UserId"));
		tObj.getNewPasswordTextBox().sendKeys(dataTable.getData("General_Data", "Password"));
		tObj.getNewLoginButton().click();
		
		HashMap<Integer, HashMap<Integer, ArrayList<String>>> pre_reportData = new HashMap<Integer, HashMap<Integer, ArrayList<String>>>();
		HashMap<Integer, HashMap<Integer, ArrayList<String>>> post_Pages_Data = new HashMap<Integer, HashMap<Integer, ArrayList<String>>>();
		try 
		{
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
				Thread.sleep(2000);
				driver.findElement(By.partialLinkText(foldersName[(LengthFolder-1)])).click();
				driver.switchTo().defaultContent();
				folderFrame1 = driver.findElement(By.xpath("//frame[@name='main']"));
				driver.switchTo().frame(folderFrame1);
				String reportName = dataTable.getData("General_Data", "ReportName");
				waitForElement(driver, By.id("toolbarframe"), 50);
					driver.switchTo().frame(driver.findElement(By.id("toolbarframe")));
					String no_Of_Pages = driver.findElement(By.xpath("//input[@name='editThis']/parent::td/following-sibling::td/font")).getText().trim();
					no_Of_Pages = no_Of_Pages.substring(3, no_Of_Pages.length());
					int no_OfPages = Integer.parseInt(no_Of_Pages);
					driver.switchTo().defaultContent();
					ArrayList<WebElement> no_of_rows = new ArrayList<WebElement>();
					no_of_rows = (ArrayList<WebElement>) driver.findElements(By.xpath("//body[@onunload='onUnload()']/div/div/div"));
					HashMap<Integer, ArrayList<String>> table_data_1 = null;
					HashMap<Integer, ArrayList<String>> table_data_2 = null;
					ArrayList<WebElement> column_Values_Properties_1 = new ArrayList<WebElement>();
					ArrayList<WebElement> column_Values_Properties_2 = new ArrayList<WebElement>();
					String dynaXpath;
					for (int l = 1; l <= no_OfPages; l++)
					{
						folderFrame1 = driver.findElement(By.xpath("//frame[@name='main']"));
						driver.switchTo().frame(folderFrame1);
						driver.switchTo().frame(driver.findElement(By.id("reportframe")));
						no_of_rows = (ArrayList<WebElement>) driver.findElements(By.xpath("//body[@onunload='onUnload()']/div/div/div"));
						
						table_data_1 = new HashMap<Integer, ArrayList<String>>();
						table_data_2 = new HashMap<Integer, ArrayList<String>>();
						
						if(l<no_OfPages)
						{
							for (int i = 2; i <= no_of_rows.size(); i++)
							{
								if(i==(no_of_rows.size()-3))
								{
									ArrayList<String> column_Values_dummy_1 = new ArrayList<String>();
									column_Values_dummy_1.add("ByPass");
									table_data_1.put(i, column_Values_dummy_1);
								}
								else
								{
								dynaXpath = "(//body[@onunload='onUnload()']/div/div/div)[" + i + "]//nobr";
								column_Values_Properties_1 = (ArrayList<WebElement>) driver.findElements(By.xpath(dynaXpath));
								ArrayList<String> column_Values_1 = new ArrayList<String>();
								if (column_Values_Properties_1.size() > 0)
								{
									for (int j = 0; j < column_Values_Properties_1.size(); j++)
									{
										column_Values_1.add(j, column_Values_Properties_1.get(j).getText());
									}
								}
								table_data_1.put(i, column_Values_1);
								
								}
							}
							pre_reportData.put(l, table_data_1);
							
						}
						else if(l==no_OfPages)
						{
							for (int i = 2; i <= no_of_rows.size()-1; i++)
							{
								dynaXpath = "//body[@onunload='onUnload()']/div/div/div[" + i + "]//nobr";
								column_Values_Properties_2 = (ArrayList<WebElement>) driver.findElements(By.xpath(dynaXpath));
								ArrayList<String> column_Values_2 = new ArrayList<String>();
								if (column_Values_Properties_2.size() > 0)
								{
									for (int j = 0; j < column_Values_Properties_2.size(); j++)
									{
										column_Values_2.add(j, column_Values_Properties_2.get(j).getText());
									}
								}
								table_data_2.put(i, column_Values_2);
							}
							pre_reportData.put(l, table_data_2);
						}
						if(l<=3||l>no_OfPages-3)
						{
							report.updateTestLog("Pre Report"+reportName, "Page No" +l, Status.PASS);
						}
						
						driver.switchTo().defaultContent();
						folderFrame1 = driver.findElement(By.xpath("//frame[@name='main']"));
						driver.switchTo().frame(folderFrame1);
						driver.switchTo().frame(driver.findElement(By.id("toolbarframe")));
						driver.findElement(By.linkText("Next")).click();
						driver.switchTo().defaultContent();
						
					}	
					
					System.out.println("Pre data is :" +pre_reportData);
					
					
			}
			
			else
			{
			report.updateTestLog("Prereport Folders", "Pre Report folders pages is not opened", Status.FAIL);
			}
					
					
		}
		catch(Exception e)
		{
			System.out.println("Exception is :" +e);
		}
		
		
		String PostURL = dataTable.getData("General_Data", "PostURL");
		driver.get(PostURL);

		tObj.getPostUserIdTextBox().clear();

		tObj.getPostUserIdTextBox().sendKeys(dataTable.getData("General_Data", "UserId"));
		tObj.getPostPasswordTextBox().sendKeys(dataTable.getData("General_Data", "Password"));
		tObj.getPostLoginButton().click();
		
		try
		{
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
					{
						report.updateTestLog("Post Report Folder Navigation", "Post Report Folder Navigation" + folderFrame + "Has failed", Status.FAIL);
					}
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
					HashMap<Integer, ArrayList<String>> post_Page_TableData_1 = null;
					HashMap<Integer, ArrayList<String>> post_Page_TableData_2 = null;
					
					
					for (int i = 1; i <= no_OfPages; i++) 
					{
					
						driver.switchTo().frame(driver.findElement(By.id("acFileExplorer")));
						folderFrame = driver.findElement(By.xpath("//frame[@name='main']"));
						driver.switchTo().frame(folderFrame);
						driver.switchTo().frame(driver.findElement(By.id("reportframe")));
						 
						List<WebElement> no_Of_Rows = driver.findElements(By.xpath("//body[@onunload='onUnload()']/div/div/div"));
						String dynaXpath_1;
						ArrayList<WebElement> column_Values_Elements_1 = new ArrayList<WebElement>();
						ArrayList<WebElement> column_Values_Elements_2 = new ArrayList<WebElement>();
						//ArrayList<String> column_Values=new ArrayList<>();
						post_Page_TableData_1 = new HashMap<Integer, ArrayList<String>>();
						post_Page_TableData_2 = new HashMap<Integer, ArrayList<String>>();
						if(i<no_OfPages)
						{
							
							for (int j = 2; j <= no_Of_Rows.size(); j++) 
							{
								if(j==no_Of_Rows.size()-3)
								{
									ArrayList<String> column_Values_dummy = new ArrayList<String>();
									column_Values_dummy.add("ByPass");
									post_Page_TableData_1.put(j,column_Values_dummy);
								}
								
								else
								{
								dynaXpath_1 = "(//body[@onunload='onUnload()']/div/div/div)[" + j + "]/*/nobr";
								ArrayList<String> column_Values_post_1 = new ArrayList<String>();
								column_Values_Elements_1 = (ArrayList<WebElement>) driver.findElements(By.xpath(dynaXpath_1));
								if (column_Values_Elements_1.size() > 0) 
								{
									for (int k = 0; k < column_Values_Elements_1.size(); k++) 
									{
										column_Values_post_1.add(k, column_Values_Elements_1.get(k).getText());
									}
								}

								post_Page_TableData_1.put(j, column_Values_post_1);
								//column_Values.clear();
								}
							}
							post_Pages_Data.put(i, post_Page_TableData_1);
						}
						
						
						if(i==no_OfPages)
						{
						for (int j = 2; j <= no_Of_Rows.size()-1; j++) 
						{
							dynaXpath_1 = "//body[@onunload='onUnload()']/div/div/div[" + j + "]/*/nobr";
							ArrayList<String> column_Values_post_2 = new ArrayList<String>();
							column_Values_Elements_2 = (ArrayList<WebElement>) driver.findElements(By.xpath(dynaXpath_1));
							if (column_Values_Elements_2.size() > 0) 
							{
								for (int k = 0; k < column_Values_Elements_2.size(); k++) 
								{
									column_Values_post_2.add(k, column_Values_Elements_2.get(k).getText());
								}
							}

							post_Page_TableData_2.put(j, column_Values_post_2);
							//column_Values.clear();
							
						}
						post_Pages_Data.put(i, post_Page_TableData_2);
						}
						
						if( i<=3||i>no_OfPages-3)
						{
							report.updateTestLog("Post Report "+postReportName, "Page No" +i, Status.PASS);
							//System.out.println("Post Page data for page number: "+i+ "  is : " +post_Pages_Data.get(i));
						}
						driver.switchTo().defaultContent();
						driver.switchTo().frame(driver.findElement(By.id("acFileExplorer")));
						folderFrame = driver.findElement(By.xpath("//frame[@name='main']"));
						driver.switchTo().frame(folderFrame);
						driver.switchTo().frame(driver.findElement(By.id("toolbarframe")));
						
						driver.findElement(By.linkText("Next")).click();
						driver.switchTo().defaultContent();
						waitForElement(driver, By.id("acFileExplorer"), 2000);
						
					}
					
					
					report.updateTestLog("Storing Data", "Data has been stored for Pre and post reports", Status.PASS);
					System.out.println("Post Data is : " +post_Pages_Data);
					//compareHeaders();
					
					comparePreandPost(pre_reportData, post_Pages_Data);
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
		}
		catch(Exception e)
		{
			System.out.println("in post block");
			System.out.println("Exception is :" +e);
		}
	}
	
	
}

