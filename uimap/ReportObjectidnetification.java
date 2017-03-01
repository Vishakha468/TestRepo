package uimap;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import componentgroups.GenericComponents;

import supportlibraries.ReusableLibrary;
import supportlibraries.ScriptHelper;
public class ReportObjectidnetification extends ReusableLibrary{
	
	public ReportObjectidnetification(ScriptHelper scriptHelper) {
		super(scriptHelper);
		// TODO Auto-generated constructor stub
	}
	
	GenericComponents gen =new GenericComponents(scriptHelper);
	
	private static final String PagetTitle_xpath    = ".//*[contains(@id,'water')]/center"; 
	
	private static final String PageNo_xpath = ".//*[contains(@class,'C234')]/nobr";
	private static final String lnkFolders_xpath   =".//*[@id='ItemsTable']//a"; 
	
	private final String txtReportHeader_xpath ="//body[@onunload='onUnload()']/div/div";
	
	private final String versionlnk_xpath     =".//*[contains(text(),'Version')]";
	private static final String PageVal1_xpath   = ".//*[contains(@class,'C235')]/nobr";
	private static final String Next_xpath       = ".//*[@id='toolbar']//a[@title='Next Page']";
	//MRF412 Last Page-2
	private static final String TotPartnerText_xpath  = ".//*[contains(@class,'C380')]//nobr"; 
	private static final String LastPage_2Table_xpath = ".//*[contains(@class,'C373')]//nobr";
	private static final String mrktCostPayCurr_xpath ="//div[contains(@onmouseover,'Mkt Cost Payment Curr')]//nobr";
	private static final String totalPay_xpath = "//div[contains(@onmouseover,'Total Payment')]//nobr";
	private static final String DiffVal_xpath = "//div[contains(@onmouseover,'Difference')]//nobr";
	
	// xpath for all pages
	public static final String allVal_xpath = "//html//body//*//nobr";
	//** Changes made by vishakha on 20/10/2016******
	private static final String ProcessDateVal_xpath = ".//*[contains(@class,'C237')]/nobr";

	private static final String reprtLnk_xpath    =".//*[@id='OutputFileName']/a";
	//MRF412Object mObj1 = new MRF412Object(scriptHelper);
	String strPreValue = dataTable.getData("General_Data", "ReportName");
	private  final String ReprtLnksCount = "(.//*[@id='OutputFileName']/a[contains(text(),'"+strPreValue+"') and (contains(text(),'ROI') or contains(text(),'roi'))])";
	private final String  editBox_xpath  =".//*[@id='toolbar']//input";
	//MRF417
	private final String pageNoMRF417_xpath = ".//*[@class='C310']/nobr";
	private final String proccDateVal_xpath =".//*[contains(@onmouseover,'RunDate')]/nobr";
	private final String Bussval2_xpath    = ".//*[contains(@onmouseover,'IntegerControl')]/nobr";
	private final String Bussval1_xpath    =".//*[contains(@onmouseover,'DateTimeControl')]/nobr";

	private final String MRM104Bussval1_xpath ="(.//*[contains(@onmouseover,'DateTimeControl')]/nobr)[1]"; 
	
	private final String BussVal1_MRF458_xpath ="(.//*[contains(@onmouseover,'TextControl')])[1]";
	
	//MRF208
	private final String pagenoMRF208_xpath =".//*[contains(@class,'C482')]//nobr";
	private final String procDateVal208_xpath = ".//*[contains(@class,'C487')]/nobr";
	
	//MRO202
	private final String pagenoMRO202nMRF203_xpath =".//*[@class='C226']/nobr";
	private final String ProccDateVal202_xpath =".//*[contains(@class,'C229')]/nobr";
	private final String lnkFilesNFolders_xpath =".//*[@id='FilesFoldersLink']";
	
	//MRO203
	//private final String pagenoMRF203_xpath =".//*[contains(@class,'C482')]//nobr";/
	String strPostReport = dataTable.getData("General_Data","PostReportName");
	private final String PostReportLnk_xpath  ="//a[contains(@href,'"+strPostReport+".ROI;')]";
	private final String pagenoMRF200_xpath =  ".//*[@class='C234']/nobr";
	private final String preReportLinks_xpath = ".//*[@id='ItemsTable']//a";
	private final String postReportLinks_xpath = "html/body/div//div//table[2]//a";
	
	
	
	
	public List<WebElement> getBussVal2MRM104(){
		gen.waitForObjectExists(By.xpath(Bussval1_xpath));
		gen.checkElementExists("Pre Report Links", By.xpath(Bussval1_xpath));
		return driver.findElements(By.xpath(Bussval1_xpath));	
	}
	
	public List<WebElement> getPreReportLnks(){
		gen.waitForObjectExists(By.xpath(preReportLinks_xpath));
		gen.checkElementExists("Pre Report Links", By.xpath(preReportLinks_xpath));
		return driver.findElements(By.xpath(preReportLinks_xpath));	
	}
	
	public List<WebElement> getPostReportLnks()
	{
		gen.waitForObjectExists(By.xpath(postReportLinks_xpath));
		gen.checkElementExists("Post Report Links", By.xpath(postReportLinks_xpath));
		return driver.findElements(By.xpath(postReportLinks_xpath));	
	}
	
	public WebElement getPageNoMRF203n203(){
		gen.waitForObjectExists(By.xpath(pagenoMRO202nMRF203_xpath));
		gen.checkElementExists("Page No of MRF203 and 202", By.xpath(pagenoMRO202nMRF203_xpath));
		return driver.findElement(By.xpath(pagenoMRO202nMRF203_xpath));	
	}
	public WebElement getPageNoMRF200(){
		gen.waitForObjectExists(By.xpath(pagenoMRF200_xpath));
		gen.checkElementExists("Page No of MRF200", By.xpath(pagenoMRF200_xpath));
		return driver.findElement(By.xpath(pagenoMRF200_xpath));	
	}
	public WebElement getPageNoMRF208(){
		gen.waitForObjectExists(By.xpath(pagenoMRF208_xpath));
		gen.checkElementExists("Page No of MRF208", By.xpath(pagenoMRF208_xpath));
		return driver.findElement(By.xpath(pagenoMRF208_xpath));	
	}
	public WebElement getEditPageToolbar(){
		gen.waitForObjectExists(By.xpath(editBox_xpath));
		gen.checkElementExists("Edit Box On toolbar", By.xpath(editBox_xpath));
		return driver.findElement(By.xpath(editBox_xpath));	
	}
	public WebElement getProccDateValAll(){
		gen.waitForObjectExists(By.xpath(proccDateVal_xpath));
		gen.checkElementExists("Process Date value", By.xpath(proccDateVal_xpath));
		return driver.findElement(By.xpath(proccDateVal_xpath));	
	}
	public List<WebElement> getcountReportLnk(){
		gen.waitForObjectExists(By.xpath(ReprtLnksCount));
		gen.checkElementExists("MRF 412 Count", By.xpath(ReprtLnksCount));
		return driver.findElements(By.xpath(ReprtLnksCount));	
	}
	
	public List<WebElement> getVersionLnk(){
		gen.waitForObjectExists(By.xpath(versionlnk_xpath));
		gen.checkElementExists("Version ", By.xpath(versionlnk_xpath));
		return driver.findElements(By.xpath(versionlnk_xpath));	
	}
	
	public List<WebElement> getcountPostReportVersion(){
		gen.waitForObjectExists(By.xpath(PostReportLnk_xpath));
		gen.checkElementExists("Post Report Version", By.xpath(PostReportLnk_xpath));
		return driver.findElements(By.xpath(PostReportLnk_xpath));	
	}
	public List<WebElement> getHeaderValues(){
		gen.waitForObjectExists(By.xpath(txtReportHeader_xpath));
		gen.checkElementExists("Header Values", By.xpath(txtReportHeader_xpath));
		return driver.findElements(By.xpath(txtReportHeader_xpath));	
	}
	public WebElement getProcessDateVal(){
		gen.waitForObjectExists(By.xpath(ProcessDateVal_xpath));
		gen.checkElementExists("Process Date Value", By.xpath(ProcessDateVal_xpath));
		return driver.findElement(By.xpath(ProcessDateVal_xpath));	
	}
	
	
	public List<WebElement> getReportLnk(){
		gen.waitForObjectExists(By.xpath(reprtLnk_xpath));
		gen.checkElementExists("Report Link", By.xpath(reprtLnk_xpath));
		return driver.findElements(By.xpath(reprtLnk_xpath));	
	}
	
	public List<WebElement> getAllValuesFromPage(){
		gen.waitForObjectExists(By.xpath(allVal_xpath));
		gen.checkElementExists("Values From Page", By.xpath(allVal_xpath));
		return driver.findElements(By.xpath(allVal_xpath));	
	}
	
	public ArrayList<String> getAllValuesFromPage1(){
		gen.waitForObjectExists(By.xpath(allVal_xpath));
		gen.checkElementExists("Values From Page", By.xpath(allVal_xpath));
		List<WebElement> elements=driver.findElements(By.xpath(allVal_xpath));
		WebElement strProcVal ;
		WebElement strBussVal;
		ArrayList<String> elementValues = new ArrayList<String>();
		String Elementval;
		
		for(int i=0;i<elements.size();i++)
		{
			Elementval = elements.get(i).getText();
			strProcVal = driver.findElement(By.xpath(proccDateVal_xpath));
			strBussVal = driver.findElement(By.xpath(proccDateVal_xpath));
			if(strPreValue.contains("MRF412"))
			{
				strProcVal = driver.findElement(By.xpath(proccDateVal_xpath));
				if(Elementval.contains(strProcVal.getText()))
				{
					Elementval = "Pass";
				} 
			
			}
			else if(strPreValue.contains("MRF417"))
			{
				strProcVal = driver.findElement(By.xpath(proccDateVal_xpath));
				if(Elementval.contains(strProcVal.getText()))
						{
						Elementval = "Pass";
						}
			}
			else if(strPreValue.contains("MRF208"))
			{
				strProcVal = driver.findElement(By.xpath(proccDateVal_xpath));
				if(Elementval.contains(strProcVal.getText()))
						{
						Elementval = "Pass";
						}
			}
			else if(strPreValue.contains("MRO202"))
			{
				strProcVal = driver.findElement(By.xpath(proccDateVal_xpath));
				if(Elementval.contains(strProcVal.getText()))
						{
						Elementval = "Pass";
						}
			}
			else if(strPreValue.contains("MRO203"))
			{
				strProcVal = driver.findElement(By.xpath(proccDateVal_xpath));
				if(Elementval.contains(strProcVal.getText()))
						{
						Elementval = "Pass";
						}
			}
			elementValues.add(Elementval);
		}
		return elementValues;
	}
	
	public List<WebElement> getFolderLnks(){
		gen.waitForObjectExists(By.xpath(lnkFolders_xpath));
		gen.checkElementExists("Folder Links", By.xpath(lnkFolders_xpath));
		return driver.findElements(By.xpath(lnkFolders_xpath));	
	}
	
	public ArrayList<String> getAllValuesFromPage2()
	{
		gen.waitForObjectExists(By.xpath(allVal_xpath));
		gen.checkElementExists("Values From Page", By.xpath(allVal_xpath));
		List<WebElement> elements=driver.findElements(By.xpath(allVal_xpath));
		ArrayList<String> elementValues = new ArrayList<String>();
		String PostElementval;
		WebElement strPostProcVal;
		
		for(int i=0;i<elements.size();i++){
			PostElementval = elements.get(i).getText();
			if(strPostReport.contains("MRF412"))
			{
				strPostProcVal = driver.findElement(By.xpath(proccDateVal_xpath));
				if(PostElementval.contains(strPostProcVal.getText()))
				{
					PostElementval = "Pass";
				} 
			
			}
			else if(strPostReport.contains("MRF417"))
			{
				strPostProcVal = driver.findElement(By.xpath(proccDateVal_xpath));
				if(PostElementval.contains(strPostProcVal.getText()))
						{
					PostElementval = "Pass";
						}
			}
			else if(strPostReport.contains("MRF208"))
			{
				strPostProcVal = driver.findElement(By.xpath(proccDateVal_xpath));
				if(PostElementval.contains(strPostProcVal.getText()))
						{
					PostElementval = "Pass";
						}
			}
			else if(strPostReport.contains("MRO202"))
			{
				strPostProcVal = driver.findElement(By.xpath(proccDateVal_xpath));
				if(PostElementval.contains(strPostProcVal.getText()))
						{
					PostElementval = "Pass";
						}
			}
			else if(strPostReport.contains("MRO203"))
			{
				strPostProcVal = driver.findElement(By.xpath(proccDateVal_xpath));
				if(PostElementval.contains(strPostProcVal.getText()))
						{
					PostElementval = "Pass";
						}
			}
			
			elementValues.add(PostElementval);
		}			
		return elementValues;
	}
	public WebElement getLnkFilesNFolders(){
		gen.waitForObjectExists(By.xpath(lnkFilesNFolders_xpath));
		gen.checkElementExists("Files Folder Link", By.xpath(lnkFilesNFolders_xpath));
		return driver.findElement(By.xpath(lnkFilesNFolders_xpath));	
	}
	
	public List<WebElement>getDiff_List(){
		gen.waitForObjectExists(By.xpath(DiffVal_xpath));
		gen.checkElementExists("Difference values", By.xpath(DiffVal_xpath));
		return driver.findElements(By.xpath(DiffVal_xpath));	
	}
	public List<WebElement>getMrktCostPayCurr_List(){
		gen.waitForObjectExists(By.xpath(mrktCostPayCurr_xpath));
		gen.checkElementExists("Market Cost Pay Currency", By.xpath(mrktCostPayCurr_xpath));
		return driver.findElements(By.xpath(mrktCostPayCurr_xpath));	
	}
	public List<WebElement>getTableElementslastPage_2_List(){
		gen.waitForObjectExists(By.xpath(LastPage_2Table_xpath));
		gen.checkElementExists("Third last page table values", By.xpath(LastPage_2Table_xpath));
		return driver.findElements(By.xpath(LastPage_2Table_xpath));	
	}
	public List<WebElement>getTotalPayThirdLastPage_List(){
		gen.waitForObjectExists(By.xpath(totalPay_xpath));
		gen.checkElementExists("Third last page Total Pay values", By.xpath(totalPay_xpath));
		return driver.findElements(By.xpath(totalPay_xpath));	
	}
	
	public WebElement getTotalPartnerText(){
		gen.waitForObjectExists(By.xpath(TotPartnerText_xpath));
		gen.checkElementExists("Total Partner Text", By.xpath(TotPartnerText_xpath));
		return driver.findElement(By.xpath(TotPartnerText_xpath));	
	}
	public WebElement getNextLink(){
		gen.waitForObjectExists(By.xpath(Next_xpath));
		gen.checkElementExists("Next Link", By.xpath(Next_xpath));
		return driver.findElement(By.xpath(Next_xpath));	
	}
	public WebElement getpageNumber1(){
		gen.waitForObjectExists(By.xpath(PageVal1_xpath));
		gen.checkElementExists("Page Number on", By.xpath(PageVal1_xpath));
		return driver.findElement(By.xpath(PageVal1_xpath));	
	}
	
	
	public WebElement getPageNoMRF412(){
		gen.waitForObjectExists(By.xpath(PageNo_xpath));
		gen.checkElementExists("PageNo.MRF412", By.xpath(PageNo_xpath));
		return driver.findElement(By.xpath(PageNo_xpath));	
	}
	public WebElement getPageNoMRF417(){
		gen.waitForObjectExists(By.xpath(pageNoMRF417_xpath));
		gen.checkElementExists("PageNo.MRF417", By.xpath(pageNoMRF417_xpath));
		return driver.findElement(By.xpath(pageNoMRF417_xpath));	
	}
	
	
	
	public WebElement getPageTitle(){
		gen.waitForObjectExists(By.xpath(PagetTitle_xpath));
		gen.checkElementExists("Page Title", By.xpath(PagetTitle_xpath));
		return driver.findElement(By.xpath(PagetTitle_xpath));	
		}
	
	
	public ArrayList<String> getHeaderValuesPre()
	{
		gen.waitForObjectExists(By.xpath(txtReportHeader_xpath));
		gen.checkElementExists("Header Values From Page", By.xpath(txtReportHeader_xpath));
		List<WebElement> Headerelements=driver.findElements(By.xpath(txtReportHeader_xpath));
		ArrayList<String> HeaderelementValues = new ArrayList<String>();
		String HeaderElementval;
		WebElement strPostProcVal;
		WebElement strBussVal1;
		WebElement strBussval2;
		
		for(int i=1;i<Headerelements.size();i++){
			HeaderElementval = Headerelements.get(i).getText();
			//System.out.println("Header value at index : " +i+ " is " +HeaderElementval);
				strPostProcVal = driver.findElement(By.xpath(proccDateVal_xpath));
				strBussVal1 =  driver.findElement(By.xpath(Bussval1_xpath));
				strBussval2 = driver.findElement(By.xpath(Bussval2_xpath));
				
				if((HeaderElementval.contains(strPostProcVal.getText()))||(HeaderElementval.contains(strBussVal1.getText()))||(HeaderElementval.contains(strBussval2.getText())) )
				{
					HeaderElementval = "Pass";
					
				} 
			
			HeaderelementValues.add(HeaderElementval);
		}			
		return HeaderelementValues;
	}
	
	
	public ArrayList<String> getHeaderValuesPost()
	{
		gen.waitForObjectExists(By.xpath(txtReportHeader_xpath));
		gen.checkElementExists("Header Values From Page", By.xpath(txtReportHeader_xpath));
		List<WebElement> Headerelements=driver.findElements(By.xpath(txtReportHeader_xpath));
		ArrayList<String> HeaderelementValues = new ArrayList<String>();
		String HeaderElementval;
		WebElement strPostProcVal;
		WebElement strBussVal1;
		WebElement strBussval2;
		
		for(int i=1;i<Headerelements.size();i++){
			HeaderElementval = Headerelements.get(i).getText();
		//	System.out.println("Header value :" +HeaderElementval);
				strPostProcVal = driver.findElement(By.xpath(proccDateVal_xpath));
				strBussVal1 =  driver.findElement(By.xpath(Bussval1_xpath));
				strBussval2 = driver.findElement(By.xpath(Bussval2_xpath));
				
				if((HeaderElementval.contains(strPostProcVal.getText()))||(HeaderElementval.contains(strBussVal1.getText()))||(HeaderElementval.contains(strBussval2.getText())) )
				{
					HeaderElementval = "Pass";
				} 
			
			HeaderelementValues.add(HeaderElementval);
		}			
		return HeaderelementValues;
	}
		public ArrayList<String> getHeaderValuesrprt601()
		{
			gen.waitForObjectExists(By.xpath(txtReportHeader_xpath));
			gen.checkElementExists("Header Values From Page", By.xpath(txtReportHeader_xpath));
			List<WebElement> rprt601Headerelements=driver.findElements(By.xpath(txtReportHeader_xpath));
			ArrayList<String> rprt601HeaderelementValues = new ArrayList<String>();
			String rprt601HeaderElementval;
			WebElement rprt601strPostProcVal;
			WebElement rprt601strBussVal1;
			WebElement rprt601strBussval2;
			
			for(int i=0;i<rprt601Headerelements.size();i++)
			{
				rprt601HeaderElementval = rprt601Headerelements.get(i).getText();
				//System.out.println("Header value at index : " +i+ " is " +HeaderElementval);
				rprt601strPostProcVal = driver.findElement(By.xpath(proccDateVal_xpath));
				rprt601strBussVal1 =  driver.findElement(By.xpath(Bussval1_xpath));
				rprt601strBussval2 = driver.findElement(By.xpath(Bussval2_xpath));
					
					if((rprt601HeaderElementval.contains(rprt601strPostProcVal.getText()))||(rprt601HeaderElementval.contains(rprt601strBussVal1.getText()))||(rprt601HeaderElementval.contains(rprt601strBussval2.getText())))
					{
						rprt601HeaderElementval = "Pass";
					} 
					if(i==27)
					{
						rprt601HeaderElementval="";
					}
					rprt601HeaderelementValues.add(rprt601HeaderElementval);
			}			
			return rprt601HeaderelementValues;
		}
		
	
		public ArrayList<String> getHeaderValuesrprt202()
		{
			gen.waitForObjectExists(By.xpath(txtReportHeader_xpath));
			gen.checkElementExists("Header Values From Page", By.xpath(txtReportHeader_xpath));
			List<WebElement> rprt601Headerelements=driver.findElements(By.xpath(txtReportHeader_xpath));
			ArrayList<String> rprt202HeaderelementValues = new ArrayList<String>();
			String rprt202HeaderElementval;
			WebElement rprt202strPostProcVal;
			WebElement rprt202strBussVal1;
			//WebElement rprt202strBussval2;
			
			for(int i=1;i<rprt601Headerelements.size();i++)
			{
				rprt202HeaderElementval = rprt601Headerelements.get(i).getText();
				//System.out.println("Header value at index : " +i+ " is " +HeaderElementval);
				rprt202strPostProcVal = driver.findElement(By.xpath(proccDateVal_xpath));
				rprt202strBussVal1 =  driver.findElement(By.xpath(Bussval1_xpath));
			//	rprt601strBussval2 = driver.findElement(By.xpath(Bussval2_xpath));
					
					if((rprt202HeaderElementval.contains(rprt202strPostProcVal.getText()))||(rprt202HeaderElementval.contains(rprt202strBussVal1.getText())))
					{
						rprt202HeaderElementval = "Pass";
					} 
					rprt202HeaderelementValues.add(rprt202HeaderElementval);
			}			
			return rprt202HeaderelementValues;
		}
		
	
	
		public ArrayList<String> getHeaderValuesrprtMRA112_all()
		{
			gen.waitForObjectExists(By.xpath(txtReportHeader_xpath));
			gen.checkElementExists("Header Values From Page", By.xpath(txtReportHeader_xpath));
			List<WebElement> rprt112AHeaderelements=driver.findElements(By.xpath(txtReportHeader_xpath));
			ArrayList<String> rprt112aHeaderelementValues = new ArrayList<String>();
			String rprt112AHeaderElementval;
			WebElement rprt112AstrPostProcVal;
			WebElement rprt112AstrBussVal1;
			WebElement rprt112AstrBussval2;
			
			for(int i=0;i<rprt112AHeaderelements.size();i++)
			{
				rprt112AHeaderElementval = rprt112AHeaderelements.get(i).getText();
				//System.out.println("Header value at index : " +i+ " is " +HeaderElementval);
				rprt112AstrPostProcVal = driver.findElement(By.xpath(proccDateVal_xpath));
				rprt112AstrBussVal1 =  driver.findElement(By.xpath(Bussval1_xpath));
				rprt112AstrBussval2 = driver.findElement(By.xpath(Bussval2_xpath));
					
					if((rprt112AHeaderElementval.contains(rprt112AstrPostProcVal.getText()))||(rprt112AHeaderElementval.contains(rprt112AstrBussVal1.getText()))||(rprt112AHeaderElementval.contains(rprt112AstrBussval2.getText())))
					{
						rprt112AHeaderElementval = "Pass";
					} 
					if(i==14)
					{
						rprt112AHeaderElementval="";
					}
					rprt112aHeaderelementValues.add(rprt112AHeaderElementval);
			}			
			return rprt112aHeaderelementValues;
		}
	
		public ArrayList<String> getrprt103AHeaderelementValues()
		{
			gen.waitForObjectExists(By.xpath(txtReportHeader_xpath));
			gen.checkElementExists("Header Values From Page", By.xpath(txtReportHeader_xpath));
			List<WebElement> rprt103AHeaderelements=driver.findElements(By.xpath(txtReportHeader_xpath));
			ArrayList<String> rprt103AHeaderelementValues = new ArrayList<String>();
			String rprtMRA103HeaderElementval;
			WebElement rprt103AstrPostProcVal;
			WebElement rprt103AstrBussVal1;
			WebElement rprt103AstrBussval2;
			
			for(int i=0;i<rprt103AHeaderelements.size();i++)
			{
				rprtMRA103HeaderElementval = rprt103AHeaderelements.get(i).getText();
				//System.out.println("Header value at index : " +i+ " is " +HeaderElementval);
				rprt103AstrPostProcVal = driver.findElement(By.xpath(proccDateVal_xpath));
				rprt103AstrBussVal1 =  driver.findElement(By.xpath(Bussval1_xpath));
				rprt103AstrBussval2 = driver.findElement(By.xpath(Bussval2_xpath));
					
					if((rprtMRA103HeaderElementval.contains(rprt103AstrPostProcVal.getText()))||(rprtMRA103HeaderElementval.contains(rprt103AstrBussVal1.getText()))||(rprtMRA103HeaderElementval.contains(rprt103AstrBussval2.getText())))
					{
						rprtMRA103HeaderElementval = "Pass";
					} 
					if(i==25)
					{
						rprtMRA103HeaderElementval="";
					}
					rprt103AHeaderelementValues.add(rprtMRA103HeaderElementval);
			}			
			return rprt103AHeaderelementValues;
		}
	
		public ArrayList<String> getHeaderValuesrprt604()
		{
			gen.waitForObjectExists(By.xpath(txtReportHeader_xpath));
			gen.checkElementExists("Header Values From Page", By.xpath(txtReportHeader_xpath));
			List<WebElement> rprt604Headerelements=driver.findElements(By.xpath(txtReportHeader_xpath));
			ArrayList<String> rprt604HeaderelementValues = new ArrayList<String>();
			String rprt604HeaderElementval;
			WebElement rprt604strPostProcVal;
			WebElement rprt604strBussVal1;
			WebElement rprt604strBussval2;
			
			for(int i=0;i<rprt604Headerelements.size();i++)
			{
				rprt604HeaderElementval = rprt604Headerelements.get(i).getText();
				//System.out.println("Header value at index : " +i+ " is " +HeaderElementval);
				rprt604strPostProcVal = driver.findElement(By.xpath(proccDateVal_xpath));
				rprt604strBussVal1 =  driver.findElement(By.xpath(Bussval1_xpath));
				rprt604strBussval2 = driver.findElement(By.xpath(Bussval2_xpath));
					
					if((rprt604HeaderElementval.contains(rprt604strPostProcVal.getText()))||(rprt604HeaderElementval.contains(rprt604strBussVal1.getText()))||(rprt604HeaderElementval.contains(rprt604strBussval2.getText())))
					{
						rprt604HeaderElementval = "Pass";
					} 
					if(i==14)
					{
						rprt604HeaderElementval="";
					}
					rprt604HeaderelementValues.add(rprt604HeaderElementval);
			}			
			return rprt604HeaderelementValues;
		}
		public ArrayList<String> getHeaderValuesMRM104()
		{
			gen.waitForObjectExists(By.xpath(txtReportHeader_xpath));
			gen.checkElementExists("Header Values From Page", By.xpath(txtReportHeader_xpath));
			List<WebElement> Headerelements=driver.findElements(By.xpath(txtReportHeader_xpath));
			ArrayList<String> HeaderelementValues = new ArrayList<String>();
			String HeaderElementval;
			WebElement strPostProcVal;
			WebElement strBussVal1;
			WebElement strBussval2;
			
			for(int i=1;i<Headerelements.size();i++){
				HeaderElementval = Headerelements.get(i).getText();
				//System.out.println("Header value at index : " +i+ " is " +HeaderElementval);
					strPostProcVal = driver.findElement(By.xpath(proccDateVal_xpath));
					strBussVal1 =  driver.findElement(By.xpath(MRM104Bussval1_xpath));
					int s = driver.findElements(By.xpath(Bussval2_xpath)).size();
					strBussval2 = driver.findElements(By.xpath(Bussval2_xpath)).get(s-1);
					//String t  = strBussval2.getText();
				//	System.out.println("Text is : " +t);
					if((HeaderElementval.contains(strPostProcVal.getText()))||(HeaderElementval.contains(strBussVal1.getText()))||(HeaderElementval.contains(strBussval2.getText())) )
					{
						HeaderElementval = "Pass";
					} 
				
				HeaderelementValues.add(HeaderElementval);
			}			
			return HeaderelementValues;
		}
		
		public ArrayList<String> getHeaderValuesMRF458()
		{
			gen.waitForObjectExists(By.xpath(txtReportHeader_xpath));
			gen.checkElementExists("Header Values From Page", By.xpath(txtReportHeader_xpath));
			List<WebElement> Headerelements=driver.findElements(By.xpath(txtReportHeader_xpath));
			ArrayList<String> HeaderelementValues = new ArrayList<String>();
			String HeaderElementval;
			WebElement strPostProcVal;
			WebElement strBussVal1;
			WebElement strBussval2;
			
			for(int i=1;i<Headerelements.size();i++){
				HeaderElementval = Headerelements.get(i).getText();
				//System.out.println("Header value at index : " +i+ " is " +HeaderElementval);
					strPostProcVal = driver.findElement(By.xpath(proccDateVal_xpath));
					strBussVal1 =  driver.findElement(By.xpath(BussVal1_MRF458_xpath));
				//	String m = strBussVal1.getText();
				//	System.out.println(m);
					int s = driver.findElements(By.xpath(Bussval2_xpath)).size();
					strBussval2 = driver.findElements(By.xpath(Bussval2_xpath)).get(s-1);
					//String t  = strBussval2.getText();
				
					
					if((HeaderElementval.contains(strPostProcVal.getText()))||(HeaderElementval.contains(strBussVal1.getText())&&(i==7))||(HeaderElementval.contains(strBussval2.getText())) )
					{
						HeaderElementval = "Pass";
					} 
				
				HeaderelementValues.add(HeaderElementval);
			}			
			return HeaderelementValues;
		}
	
		public ArrayList<String> getHeaderValuesrprt807()
		{
			gen.waitForObjectExists(By.xpath(txtReportHeader_xpath));
			gen.checkElementExists("Header Values From Page", By.xpath(txtReportHeader_xpath));
			List<WebElement> rprt807Headerelements=driver.findElements(By.xpath(txtReportHeader_xpath));
			ArrayList<String> rprt807HeaderelementValues = new ArrayList<String>();
			String rprt807HeaderElementval;
			WebElement rprt807strPostProcVal;
			WebElement rprt807strBussVal1;
			WebElement rprt807strBussval2;
			
			for(int i=0;i<rprt807Headerelements.size();i++)
			{
				rprt807HeaderElementval = rprt807Headerelements.get(i).getText();
				//System.out.println("Header value at index : " +i+ " is " +HeaderElementval);
				rprt807strPostProcVal = driver.findElement(By.xpath(proccDateVal_xpath));
				rprt807strBussVal1 =  driver.findElement(By.xpath(BussVal1_MRF458_xpath));
				rprt807strBussval2 = driver.findElement(By.xpath(Bussval2_xpath));
					
					if((rprt807HeaderElementval.contains(rprt807strPostProcVal.getText()))||(rprt807HeaderElementval.contains(rprt807strBussVal1.getText()))||(rprt807HeaderElementval.contains(rprt807strBussval2.getText())))
					{
						rprt807HeaderElementval = "Pass";
					} 
					if(i==22)
					{
						rprt807HeaderElementval="";
					}
					rprt807HeaderelementValues.add(rprt807HeaderElementval);
			}			
			return rprt807HeaderelementValues;
		}
		public ArrayList<String> getHeaderValuesPreMRO219()
		{
			gen.waitForObjectExists(By.xpath(txtReportHeader_xpath));
			gen.checkElementExists("Header Values From Page", By.xpath(txtReportHeader_xpath));
			List<WebElement> Headerelements=driver.findElements(By.xpath(txtReportHeader_xpath));
			ArrayList<String> HeaderelementValues = new ArrayList<String>();
			String HeaderElementval;
			WebElement strPostProcVal;
			WebElement strBussVal1;
			List<WebElement> strBussval2 = null;
			
			for(int i=1;i<Headerelements.size();i++){
				HeaderElementval = Headerelements.get(i).getText();
				//System.out.println("Header value at index : " +i+ " is " +HeaderElementval);
					strPostProcVal = driver.findElement(By.xpath(proccDateVal_xpath));
					strBussVal1 =  driver.findElement(By.xpath(Bussval1_xpath));
					
					strBussval2 = driver.findElements(By.xpath(Bussval2_xpath));
					int size = strBussval2.size();
					
					if((HeaderElementval.contains(strPostProcVal.getText()))||(HeaderElementval.contains(strBussVal1.getText()))||(HeaderElementval.contains(strBussval2.get(size-1).getText())))
					{
						HeaderElementval = "Pass";
						
					} 
				
				HeaderelementValues.add(HeaderElementval);
			}			
			return HeaderelementValues;
		}
		public ArrayList<String> getHeaderValuesMRA110_UK_NoData()
		{
			gen.waitForObjectExists(By.xpath(txtReportHeader_xpath));
			gen.checkElementExists("Header Values From Page", By.xpath(txtReportHeader_xpath));
			List<WebElement> Headerelements=driver.findElements(By.xpath(txtReportHeader_xpath));
			ArrayList<String> HeaderelementValues = new ArrayList<String>();
			String HeaderElementval;
			WebElement strPostProcVal;
			WebElement strBussVal1;
			WebElement strBussval2;
			
			for(int i=1;i<Headerelements.size();i++){
				HeaderElementval = Headerelements.get(i).getText();
				//System.out.println("Header value at index : " +i+ " is " +HeaderElementval);
					strPostProcVal = driver.findElement(By.xpath(proccDateVal_xpath));
					
					strBussval2 =  driver.findElement(By.xpath(Bussval2_xpath));
					
					int s = driver.findElements(By.xpath(Bussval1_xpath)).size();
					strBussVal1 = driver.findElements(By.xpath(Bussval1_xpath)).get(s-1);
					//String t  = strBussval2.getText();
				//	System.out.println("Text is : " +t);
					if((HeaderElementval.contains(strPostProcVal.getText()))||(HeaderElementval.contains(strBussVal1.getText()))||(HeaderElementval.contains(strBussval2.getText())) )
					{
						HeaderElementval = "Pass";
					} 
				
				HeaderelementValues.add(HeaderElementval);
			}			
			return HeaderelementValues;
		}
		
}