package businesscomponents;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import supportlibraries.ReusableLibrary;
import supportlibraries.ScriptHelper;
import uimap.ReportObjectidnetification;
import uimap.TestObjects;

import com.cognizant.framework.Status;

public class MRF802issue extends ReusableLibrary {
	public MRF802issue(ScriptHelper scriptHelper) {
		super(scriptHelper);
		// TODO Auto-generated constructor stub
	}
	TestObjects tObj = new TestObjects(scriptHelper);
	ReportObjectidnetification mObj = new ReportObjectidnetification(scriptHelper);
	 ArrayList<String> strHeaderVal = new ArrayList<String>();
public void completeReportComparisonMRF802() throws Exception {

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
			if (driver.getTitle().contains("Files & Folders")) {
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
				driver.findElements(By.xpath(PreReportName_xpath)).get(NumberReports-1).click();
				
				
				driver.switchTo().defaultContent();
				driver.switchTo().frame(driver.findElement(By.id("TableFrame")));
				driver.switchTo().frame(driver.findElement(By.id("tableFrame")));
				String reportName = dataTable.getData("General_Data", "ReportName");
				Thread.sleep(3000);
			//	if (waitForElement(driver, By.linkText(reportName), 3000))
			//	{
					//driver.findElements(By.linkText(reportName)).get(0).click();
					driver.findElements(By.xpath(".//*[@id='ItemsTable']//a")).get(0).click();
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
					for (int l = 1; l <= 21; l++)
				//	for (int l = 1; l <= no_OfPages; l++)
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
							if(i==7 && l==19)
							{
								System.out.println("table data value: " +table_data);
								System.out.println("col values : " +column_Values);
							}
						}
						pre_reportData.put(l, table_data);
						//table_data.clear();
						if(l<=3||l>no_OfPages-3)
						{
							report.updateTestLog("Pre Report"+reportName, "Page No" +l, Status.PASS);
						}
			//			page.add(strHeaderVal);
						driver.switchTo().defaultContent();
						driver.switchTo().frame(driver.findElement(By.id("toolbarframe")));
						driver.findElement(By.linkText("Next")).click();
						driver.switchTo().defaultContent();
						waitForElement(driver, By.id("reportframe"), 2000);
						driver.switchTo().frame(driver.findElement(By.id("reportframe")));
						
					}
			//		pages.add(page);
					driver.switchTo().window(tab.get(0));
		/*		} 
				else 
				{
					report.updateTestLog("Report Selection", "Not able to find the report Name", Status.FAIL);
				} */

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
					HashMap<Integer, ArrayList<String>> post_Page_TableData = null;
					for (int i = 1; i <= 21; i++)
			//	for (int i = 1; i <= no_OfPages; i++) 
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
							if(i==19 && j==5)
							{
								System.out.println("Table dat afor Post : " +post_Page_TableData);
								System.out.println("values for row 5 : " +column_Values);
							}
						}
						post_Pages_Data.put(i, post_Page_TableData);
						
						// post_Page_TableData.clear();
						if(i<=3||i>no_OfPages-3){
							report.updateTestLog("Post Report "+postReportName, "Page No" +i, Status.PASS);
						}
					//	postPage.add(strHeaderVal);
						
						driver.switchTo().defaultContent();
						driver.switchTo().frame(driver.findElement(By.id("acFileExplorer")));
						folderFrame = driver.findElement(By.xpath("//frame[@name='main']"));
						driver.switchTo().frame(folderFrame);
						driver.switchTo().frame(driver.findElement(By.id("toolbarframe")));
						
						driver.findElement(By.linkText("Next")).click();
						driver.switchTo().defaultContent();
						waitForElement(driver, By.id("acFileExplorer"), 2000);

					}
				//	Postpages.add(postPage);
					
					report.updateTestLog("Storing Data", "Data has been stored for Pre report", Status.PASS);
					
				//	compareHeaders();
					
					comparePreandPostMRF802(pre_reportData, post_Pages_Data);
				} else {
					report.updateTestLog("Post Report Navigation", "Not able to find Report Folder", Status.FAIL);
				}
			} else {
				report.updateTestLog("Post Report Folder Navigation", "Not able to find Post Report Folder structure", Status.FAIL);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void comparePreandPostMRF802(HashMap<Integer, HashMap<Integer, ArrayList<String>>> preData, HashMap<Integer, HashMap<Integer, ArrayList<String>>> postData) {
		if (preData.size() == postData.size()) {
			HashMap<Integer, HashMap<Integer, ArrayList<String>>> postData1;
			HashMap<Integer, ArrayList<String>> prePageData;
			HashMap<Integer, ArrayList<String>> postPageData;
			ArrayList<String> prePageDataRowData = new ArrayList<String>();
			ArrayList<String> postPageDataRowData = new ArrayList<String>();
			postData1 = postData;
			
		//	System.out.println("pre data size is :" +preData.size());
			
			for (int i = 1; i <= preData.size(); i++) 
			{
			//	System.out.println("value of i :" +i);
				prePageData = preData.get(i);
				for (int j = 2; j <= prePageData.size() + 1; j++) 
				{
					//System.out.println("value of j :" +j);
					
					prePageDataRowData = prePageData.get(j);
					if (prePageDataRowData.size() > 0) 
					{
						if(i==19 && j==7)
						{
						System.out.println("Pre Report value is : " +prePageDataRowData);
						//System.out.println("Post Report value is : " +postPageDataRowData);
						}
						
						boolean arrayFlag = false;
						for (int k = 1; k <= postData1.size(); k++)
						{
												
						//	System.out.println("value of k is:" +k);
							boolean flag = false;
							postPageData = postData1.get(k);
							int l = 2;
							int counter = 0;
							while (l <= postPageData.size() + 1) 
							{
								if(k==19 && l==5)
								{
									System.out.println("post value is :" +postPageData);
								}
								postPageDataRowData = postPageData.get(l);
								if (postPageDataRowData.size() > 0) {
									
									
									if (prePageDataRowData.get(0).equals(postPageDataRowData.get(0))) {
										
										if (prePageDataRowData.size() == postPageDataRowData.size()) 
										{
											for (int m = 0; m < prePageDataRowData.size(); m++) 
											{
												if (prePageDataRowData.get(m).equals(postPageDataRowData.get(m))) 
												{
													counter = counter + 1;
												}
											}
											if (counter == prePageDataRowData.size()) {
												// System.out.println("Page Number"+i+"Row No " +j +"Available");
												postData1.get(k).get(l).clear();
												report.updateTestLog("Data Comparision", "Pre-PageNo " + i + " and Row No " + j +"is present in Post Report page  " +k+"in row " +l , Status.DONE);
												flag = true;
												arrayFlag = true;
												break;
											}
										}
									}
								}
								l++;
								counter = 0;
							}
							if (flag) {
								break;                           
							}
						}
						if (!arrayFlag) {
							//System.out.println("data is : " +postData1.get(k).get(l).getText());
							report.updateTestLog("Data Comparision", "Pre-PageNo" + i + " and Row No" + j +" is not found in Post report", Status.FAIL);
						}
					}
				}

			}

		}
		else {
			report.updateTestLog("Data Validation", "Pre and Post Data Pages does not matched", Status.FAIL);
		}
	}
	
	public boolean waitForElement(WebDriver driver, By by, int time) {
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
	
}
