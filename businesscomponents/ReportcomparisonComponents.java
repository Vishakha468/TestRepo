package businesscomponents;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

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
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import uimap.ReportObjectidnetification;
import uimap.TestObjects;

public class ReportcomparisonComponents extends ReusableLibrary
{
	public ReportcomparisonComponents(ScriptHelper scriptHelper) {
		super(scriptHelper);
		// TODO Auto-generated constructor stub
	} 


	ArrayList<String> newTab = null;
	ReportObjectidnetification mObj = new ReportObjectidnetification(scriptHelper);
	TestObjects tObj = new TestObjects(scriptHelper);
	String oldTab,oldtab1;
	//**********Changes made by Vishakha on 20/10/2016****

	static ArrayList<ArrayList<ArrayList<String>>> pages= new ArrayList<ArrayList<ArrayList<String>>>();
	static ArrayList<ArrayList<ArrayList<String>>> Postpages= new ArrayList<ArrayList<ArrayList<String>>>();
	static ArrayList<ArrayList<String>> page= new ArrayList<ArrayList<String>>(); 
	static ArrayList<ArrayList<String>> postPage = new ArrayList<ArrayList<String>>();
	public static ArrayList<String> strSamplingPageNumber = new ArrayList<String>();

	static int pagenoPreReport;
	static int CountofReport;
	static int samplingAppl = 0;
	static int reportIterator;
	int intPostPageNo;
	static int size2;
	static int  reprtsAvailCount;
	//*************************************************
	public void storePreReportValues() throws IOException, InterruptedException
	{


		String Report = dataTable.getData("General_Data", "ReportName");
		oldTab = driver.getWindowHandle();
		Thread.sleep(1000);
		newTab = new ArrayList<String>(driver.getWindowHandles());
		newTab.remove(oldTab);
		if(reportIterator>1)
		{
			oldtab1 = driver.getWindowHandle();
			newTab.remove(oldtab1);
		}
		int s3 = newTab.size();
		if(s3>1)
		{
			driver.switchTo().window(newTab.get(s3-1));
			System.out.println("Window name in second Iteration is : " +driver.getTitle());
		}
		else
		{
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

		if(Report.contains("MRF412"))
		{
			strPageVal     = mObj.getPageNoMRF412().getText();
		}
		else if(Report.contains("MRF417"))
		{
			strPageVal =	mObj.getPageNoMRF417().getText();
		}
		else if(Report.contains("MRF208"))
		{
			strPageVal =	mObj.getPageNoMRF208().getText();
		} 
		else if(Report.contains("MRO202"))
		{
			strPageVal =	mObj.getPageNoMRF203n203().getText();
		}
		else if(Report.contains("MRO203"))
			
		{
			strPageVal =	mObj.getPageNoMRF203n203().getText();
		} 
		String noOfPages=driver.findElement(By.xpath("//input[@name='editThis']/parent::td/following-sibling::td/font")).getText().trim();
		noOfPages=noOfPages.substring(3, noOfPages.length());
		int intPostPageNo1;
		intPostPageNo1 = Integer.parseInt(noOfPages);
		strPageVal=noOfPages;
	

		ArrayList<String> strPartnerName1=new ArrayList<String>();
		int intVarIncement = 0;
		int TotalPages = Integer.parseInt(strPageVal);
		String strPageTitle = mObj.getPageTitle().getText();
		//Condition for sampling 
		if (TotalPages > 50)
		{
			samplingAppl = 1;
			pagenoPreReport = TotalPages;
			int LastPage = TotalPages;
			//for first page
			if (strPageTitle.contains("MultiUserTest License"))
			{
				report.updateTestLog("Verify Page Title", "Page Title Verification is successful for page 1", Status.PASS);
				strPartnerName1=mObj.getAllValuesFromPage1();
				strSamplingPageNumber.add("1");

				page.add(strPartnerName1);

			}

			else
			{
				report.updateTestLog("Verify Page Title", "Page Title Verification failed for Page 1", Status.FAIL);
			}
			//for last page
			driver.switchTo().window(newTab.get(0));
			driver.switchTo().frame("toolbarframe");
			String lspage = Integer.toString(LastPage);
			mObj.getEditPageToolbar().clear();
			mObj.getEditPageToolbar().sendKeys(lspage);

			mObj.getEditPageToolbar().sendKeys(Keys.ENTER);
			report.updateTestLog("Enter Last page", "Last Page is entered in Edit box", Status.DONE);
			Thread.sleep(1500);
			driver.switchTo().window(newTab.get(0));
			driver.switchTo().frame(frame3);
			strPageTitle = mObj.getPageTitle().getText();
			if (strPageTitle.contains("MultiUserTest License"))
			{
				report.updateTestLog("Verify Page Title", "Page Title Verification is successful for Last Page", Status.PASS);
				strPartnerName1=mObj.getAllValuesFromPage1();
				strSamplingPageNumber.add(strPageVal);
				page.add(strPartnerName1);
			}

			else
			{
				report.updateTestLog("Verify Page Title", "Page Title Verification failed for Last Page", Status.FAIL);
			}

			// For Other Pages
			if(s3>1)
			{
				driver.switchTo().window(newTab.get(s3-1));
			}
			else
			{
				driver.switchTo().window(newTab.get(0));

			}
			driver.switchTo().frame("toolbarframe");
			mObj.getEditPageToolbar().clear();
			mObj.getEditPageToolbar().sendKeys("2");
			mObj.getEditPageToolbar().sendKeys(Keys.ENTER);
			report.updateTestLog("Enter Second page", "Second Page is entered in Edit box", Status.DONE);
			Thread.sleep(1500);
			if(s3>1)
			{
				driver.switchTo().window(newTab.get(s3-1));
			}
			else
			{
				driver.switchTo().window(newTab.get(0));

			}
			driver.switchTo().frame(frame3);
			for(int Pageiter1=1;Pageiter1< (TotalPages-1);Pageiter1++)
			{

				strPageTitle = mObj.getPageTitle().getText();
				intVarIncement = (int) Math.pow(2,Pageiter1);
				if(strPageTitle.contains("MultiUserTest License"))
				{
					report.updateTestLog("Verify Page Title", "Page Title Verification is successful for page " +intVarIncement , Status.PASS);
					strPartnerName1=mObj.getAllValuesFromPage1();
					strSamplingPageNumber.add(Integer.toString(intVarIncement));
					page.add(strPartnerName1);
				}
				else
				{
					report.updateTestLog("Verify Page Title", "Page Title Verification failed for Page "+intVarIncement , Status.FAIL);
				}

				if(s3>1)
				{
					driver.switchTo().window(newTab.get(s3-1));
				}
				else
				{
					driver.switchTo().window(newTab.get(0));

				}
				driver.switchTo().frame("toolbarframe");
				Thread.sleep(1000);
				if(intVarIncement>TotalPages)
				{
					break;
				}
				else
				{
					mObj.getEditPageToolbar().clear();
					mObj.getEditPageToolbar().sendKeys(Integer.toString(intVarIncement));
					mObj.getEditPageToolbar().sendKeys(Keys.ENTER);
					synchronized (driver) {
						driver.wait(1000);
					}
					if(s3>1)
					{
						driver.switchTo().window(newTab.get(s3-1));
					}
					else
					{
						driver.switchTo().window(newTab.get(0));
					}
					WebElement frame3_upd = driver.findElement(By.id("reportframe"));
					driver.switchTo().frame(frame3_upd);
				}

			}
			pages.add(page);
		}

		else
		{
			pagenoPreReport = TotalPages;
			// Loop for storing values page by page
			for (int Pageiter=0;Pageiter< TotalPages;Pageiter++)
			{
				String strPageSource = driver.getPageSource();
				CommonData.strPageSource = strPageSource;
				strPageTitle = mObj.getPageTitle().getText();
				CommonData.strPageTitle = strPageTitle;
				if (strPageTitle.contains("MultiUserTest License"))
				{
					report.updateTestLog("Verify Page Title", "Page Title Verification is successful for page"+(Pageiter+1), Status.PASS);
					String strPartnerNameList1;
					CommonData.strPageNumber = strPageVal;	
					strPartnerName1=mObj.getAllValuesFromPage1();
					page.add(strPartnerName1);
				}

				else
				{
					report.updateTestLog("Verify Page Title", "Page Title Verification", Status.FAIL);
				}
				if(s3>1)
				{
					driver.switchTo().window(newTab.get(s3-1));
				}
				else
				{
					driver.switchTo().window(newTab.get(0));
				}
				driver.switchTo().frame("toolbarframe");

				mObj.getNextLink().click();

				synchronized (driver) {
					driver.wait(1000);
				}
				if(s3>1)
				{
					driver.switchTo().window(newTab.get(s3-1));
				}
				else
				{
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
	}

	//Launch Post URL
	public void launchPostURL() throws InterruptedException
	{
		String PostURL = dataTable.getData("General_Data", "PostURL");
		driver.get(PostURL);
		tObj.getPostUserIdTextBox().clear();
		tObj.getPostUserIdTextBox().sendKeys(dataTable.getData("General_Data", "UserId"));
		tObj.getPostPasswordTextBox().sendKeys(dataTable.getData("General_Data", "Password"));
		tObj.getPostLoginButton().click();
		synchronized (driver) 
		{
			driver.wait(3);
		}
	}

	// code started for Post Report
	public void getPostReportValues() throws InterruptedException
	{
		driver.switchTo().frame(driver.findElement(By.id("acFileExplorer")));
		WebElement folderFrame=driver.findElement(By.xpath("//frame[@name='main']"));
		driver.switchTo().frame(folderFrame);
		String strPostFolderName = dataTable.getData("General_Data","PostReportFolderName");
		driver.findElement(By.xpath("//a[contains(@href,'"+strPostFolderName+"')]")).click();
		driver.switchTo().defaultContent();
		String strPostSubFolderName = dataTable.getData("General_Data","PostReportSubFolderName");
		driver.switchTo().frame(driver.findElement(By.id("acFileExplorer")));
		driver.switchTo().frame(folderFrame);
		driver.findElement(By.xpath("//a[contains(@href,'"+strPostSubFolderName+"')]")).click();
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.id("acFileExplorer")));
		WebElement documentsFrame=driver.findElement(By.xpath("//frame[@name='main']"));
		driver.switchTo().frame(documentsFrame);
		int ReportVersion =	mObj.getcountPostReportVersion().size();
		mObj.getcountPostReportVersion().get(ReportVersion-1).click();
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.id("acFileExplorer")));
		driver.switchTo().frame(documentsFrame);
		driver.switchTo().frame(driver.findElement(By.id("toolbarframe")));
		String noOfPages=driver.findElement(By.xpath("//input[@name='editThis']/parent::td/following-sibling::td/font")).getText().trim();
		noOfPages=noOfPages.substring(3, noOfPages.length());
		int intPostPageNo1;
		intPostPageNo1 = Integer.parseInt(noOfPages);
		intPostPageNo=intPostPageNo1;
		ArrayList<String> currentReportValues=new ArrayList<String>();
		ArrayList<String> PostReportValues=new ArrayList<String>();
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.id("acFileExplorer")));
		driver.switchTo().frame(driver.findElement(By.name("main")));
		driver.switchTo().frame(driver.findElement(By.id("reportframe")));
		if(intPostPageNo>50)
		{
			int LastPostPage = intPostPageNo;
			//for first page
			PostReportValues=mObj.getAllValuesFromPage2();
			postPage.add(PostReportValues);
			//for last page
			driver.switchTo().defaultContent();
			driver.switchTo().frame(driver.findElement(By.id("acFileExplorer")));
			driver.switchTo().frame(driver.findElement(By.name("main")));
			driver.switchTo().frame(driver.findElement(By.id("toolbarframe")));
			driver.findElement(By.name("editThis")).clear();
			driver.findElement(By.name("editThis")).sendKeys(Integer.toString(LastPostPage));
			driver.findElement(By.name("editThis")).sendKeys(Keys.ENTER);
			driver.switchTo().defaultContent();
			driver.switchTo().frame(driver.findElement(By.id("acFileExplorer")));
			driver.switchTo().frame(driver.findElement(By.name("main")));
			driver.switchTo().frame(driver.findElement(By.id("reportframe")));
			PostReportValues=mObj.getAllValuesFromPage2();
			postPage.add(PostReportValues);
			driver.switchTo().defaultContent();
			driver.switchTo().frame(driver.findElement(By.id("acFileExplorer")));
			driver.switchTo().frame(driver.findElement(By.name("main")));
			driver.switchTo().frame(driver.findElement(By.id("toolbarframe")));
			mObj.getEditPageToolbar().clear();
			mObj.getEditPageToolbar().sendKeys("2");
			mObj.getEditPageToolbar().sendKeys(Keys.ENTER);
			driver.switchTo().defaultContent();
			driver.switchTo().frame(driver.findElement(By.id("acFileExplorer")));
			driver.switchTo().frame(driver.findElement(By.name("main")));
			driver.switchTo().frame(driver.findElement(By.id("reportframe")));
			for(int PostPageiter1=1;PostPageiter1< (LastPostPage-1);PostPageiter1++)
			{
				int intPostVarIncement = (int) Math.pow(2,PostPageiter1);
				PostReportValues=mObj.getAllValuesFromPage2();
				postPage.add(PostReportValues);
				driver.switchTo().defaultContent();
				driver.switchTo().frame(driver.findElement(By.id("acFileExplorer")));
				driver.switchTo().frame(driver.findElement(By.name("main")));
				driver.switchTo().frame(driver.findElement(By.id("toolbarframe")));
				Thread.sleep(1000);
				if(intPostVarIncement>LastPostPage)
				{
					break;
				}
				else
				{
					mObj.getEditPageToolbar().clear();
					mObj.getEditPageToolbar().sendKeys(Integer.toString(intPostVarIncement));
					mObj.getEditPageToolbar().sendKeys(Keys.ENTER);
					synchronized (driver) {
						driver.wait(1000);
					}
					driver.switchTo().defaultContent();
					driver.switchTo().frame(driver.findElement(By.id("acFileExplorer")));
					driver.switchTo().frame(driver.findElement(By.name("main")));
					driver.switchTo().frame(driver.findElement(By.id("reportframe")));
				}
			}
			Postpages.add(postPage);	
		}
		else {
			for(int i=0;i<intPostPageNo;i++){
				
				driver.switchTo().defaultContent();
				driver.switchTo().frame(driver.findElement(By.id("acFileExplorer")));
				driver.switchTo().frame(driver.findElement(By.name("main")));
				driver.switchTo().frame(driver.findElement(By.id("reportframe")));
				currentReportValues=mObj.getAllValuesFromPage2();
				driver.switchTo().defaultContent();
				driver.switchTo().frame(driver.findElement(By.id("acFileExplorer")));
				driver.switchTo().frame(driver.findElement(By.name("main")));
				driver.switchTo().frame(driver.findElement(By.id("toolbarframe")));
				driver.findElement(By.name("button4")).click();
				driver.switchTo().defaultContent();
				postPage.add(currentReportValues);
			}

			Postpages.add(postPage);
		}
	}
	public void comparedata()
	{
		int sizepostPage = Postpages.size();
		int flag = 0;
		int flag1 = 0;
			if(intPostPageNo==pagenoPreReport)
		{
			int t=0;
			for( t=0;t<Postpages.get(0).size();t++)
			{
				int n =0;

				for( n=0;n<Postpages.get(0).get(t).size();n++)
				{									
					if((Postpages.get(0).get(t).get(n)).equals(pages.get(0).get(t).get(n)))
					{
						flag = 1;
					}
					else
					{
						flag = 0;
						report.updateTestLog("Data Comparison", "Values Mismatches : PreReport value is:  " +pages.get(0).get(t).get(n)+"  PostReport value is :  "+Postpages.get(0).get(t).get(n)+ " on page "+(t+1), Status.FAIL);
					}
				}

				if(flag>0)
				{
					if(samplingAppl==1)
					{
						report.updateTestLog("Data Comparison", "Values Comparison done for page no "+strSamplingPageNumber.get(t),Status.DONE);
					}
					else
						report.updateTestLog("Data Comparison", "Values matches for page no "+(t+1),Status.PASS);
				}
				if(flag1==1)
				{
					if(samplingAppl==1)
					{
						report.updateTestLog("Data Comparison", "Values Count Does not match for page no " +strSamplingPageNumber.get(t),Status.FAIL);
					}
					else
						report.updateTestLog("Data Comparison", "Values Count Does not match for page no "+(t+1),Status.FAIL);
				} 
			}
		}
		else
		{
			report.updateTestLog("Data Row Validation", "Data Rows are not same", Status.FAIL);
		}
			
	}


	public void launchPreReport() throws InterruptedException
	{
		String PreURL = dataTable.getData("General_Data", "PreURL");
		driver.get(PreURL);
		tObj.getUserIdTextBox().clear();
		tObj.getUserIdTextBox().sendKeys(dataTable.getData("General_Data", "UserId"));
		tObj.getPasswordTextBox().sendKeys(dataTable.getData("General_Data", "Password"));
		tObj.getLoginButton().click();
		mObj.getLnkFilesNFolders().click();
		synchronized (driver) {
			driver.wait(3);
		}
	}
	
	public void openReport() throws InterruptedException
	{
		driver.switchTo().frame("TableFrame");
		tObj.getCompletedLnk().click();
		Thread.sleep(2000);		
		driver.switchTo().frame("ifrMainFrame");
		String strPreValue = dataTable.getData("General_Data", "ReportName");
		driver.findElement(By.id("FilterText")).clear();
		driver.findElement(By.id("FilterText")).sendKeys(strPreValue);
		Thread.sleep(1200);
		driver.findElement(By.xpath("//input[@value='Apply']")).click();
		driver.switchTo().frame("ifrListFrame");
			if(reportIterator >0)
		{
			driver.findElement(By.xpath("(.//*[@id='OutputFileName']/a[contains(text(),'"+strPreValue+"') and (contains(text(),'ROI') or contains(text(),'roi'))])["+(reportIterator+1)+"]")).click();
		}
		else
		{
			driver.findElement(By.xpath("(.//*[@id='OutputFileName']/a[contains(text(),'"+strPreValue+"') and (contains(text(),'ROI') or contains(text(),'roi'))])[1]")).click();	
		}
		Thread.sleep(1200);
	}

	public void openFolder() throws InterruptedException
	{
		driver.switchTo().frame("TFrame");
		driver.switchTo().frame("ifrListFrame");
		String preFolder = dataTable.getData("General_Data", "PreFolderName");
		String preSubFolder = dataTable.getData("General_Data", "PreSubFolderName");
		String ReportName = dataTable.getData("General_Data", "ReportName");

		int CountFolders = mObj.getFolderLnks().size();
		for (int t=0;t<CountFolders;t++)
		{
			if((mObj.getFolderLnks().get(t).getText()).contains(preFolder))
			{
				mObj.getFolderLnks().get(t).click();
				report.updateTestLog("Open Folder", "navigated to folder Successfully", Status.DONE);
				break;
			}	
		}
		Thread.sleep(3000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame("TFrame");
		driver.switchTo().frame("ifrListFrame");
		for (int t=0;t<CountFolders;t++)
		{
			if((mObj.getFolderLnks().get(t).getText()).contains(preSubFolder))
			{
				mObj.getFolderLnks().get(t).click();
				report.updateTestLog("Open Folder", "navigated to Sub Folder Successfully", Status.DONE);
				break;
			}	
		}
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame("TFrame");
		driver.switchTo().frame("ifrListFrame");
		for (int t=0;t<CountFolders;t++)
		{
			if((mObj.getFolderLnks().get(t).getText()).contains(ReportName))
			{
				mObj.getFolderLnks().get(t).click();
				report.updateTestLog("Open Folder", "navigated to Report Successfully", Status.DONE);
				break;
			}	
		}
	}


	public void completeReportComparison() throws InterruptedException, IOException
	{
		//	int reprtsAvailCount = 2;
		//CountofReport = CountofReport + 1;
		reportIterator = 0;
		String strReportName = dataTable.getData("General_Data","ReportName");
		
		launchPreReport();
		openFolder();
		storePreReportValues();
		launchPostURL();
		getPostReportValues();
		comparedata();
		report.updateTestLog("Report Comparison", "Report Comparison Done for Report :" +strReportName, Status.PASS);
		//	for(reportIterator =1;reportIterator<reprtsAvailCount;reportIterator++)
		//{
		//	launchPreReport();
		//	openReport();
		//	storePreReportValues();
		//launchPostURL();
		//getPostReportValues();
		//	comparedata(); 

		//	report.updateTestLog("Report Comparison", "Report Comparison Done for Report :" +(reportIterator+1), Status.PASS);
		//System.out.println("Window Name after one iteration:" +driver.getTitle());
		//	Thread.sleep(500);

		//	}
	}
}



