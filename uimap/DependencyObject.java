package uimap;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import componentgroups.GenericComponents;

import supportlibraries.ReusableLibrary;
import supportlibraries.ScriptHelper;

public class DependencyObject extends ReusableLibrary{
	
	public DependencyObject(ScriptHelper scriptHelper) {
		super(scriptHelper);
		// TODO Auto-generated constructor stub
	}
	
	GenericComponents gen =new GenericComponents(scriptHelper);
	
	private static final String  reprtArrow_xpath = ".//*[contains(@id,'ItemPopZone')]" ;
	private static final String  reprtlnks_xpath = "(.//*[@id='ItemsTable']//a)";
	private static final String propertiesLnk_xpath =".//*[@value='FileProperties']";
	private static final String btnAdd_xpath    =".//input[contains(@value,'Add')]";
	private static final String lnkRootElement_xpath =".//*[@id='BannerPathTable']//a";
	private static final String btnOk_xpath =".//*[@name='bOK']";
	private static final String btnOkMainScreen_xpath =".//*[@value='OK']";
	
	
	
	public WebElement getBtnOKMainScreen(){
		gen.waitForObjectExists(By.xpath(btnOkMainScreen_xpath));
		gen.checkElementExists("OK Button on Main Screen", By.xpath(btnOkMainScreen_xpath));
		return driver.findElement(By.xpath(btnOkMainScreen_xpath));	
	}
	
	public WebElement getBtnOK(){
		gen.waitForObjectExists(By.xpath(btnOk_xpath));
		gen.checkElementExists("OK Button", By.xpath(btnOk_xpath));
		return driver.findElement(By.xpath(btnOk_xpath));	
	}
	public List<WebElement> getRootElemnt(){
		gen.waitForObjectExists(By.xpath(lnkRootElement_xpath));
		gen.checkElementExists("Root Element Link", By.xpath(lnkRootElement_xpath));
		return driver.findElements(By.xpath(lnkRootElement_xpath));	
	}
	public WebElement getAddBtn(){
		gen.waitForObjectExists(By.xpath(btnAdd_xpath));
		gen.checkElementExists("Add Button", By.xpath(btnAdd_xpath));
		return driver.findElement(By.xpath(btnAdd_xpath));	
	}
	public WebElement getPropertiesLnk(){
		gen.waitForObjectExists(By.xpath(propertiesLnk_xpath));
		gen.checkElementExists("Properties Link", By.xpath(propertiesLnk_xpath));
		return driver.findElement(By.xpath(propertiesLnk_xpath));	
	}
	public List<WebElement> getPreReportArrows(){
		gen.waitForObjectExists(By.xpath(reprtArrow_xpath));
		gen.checkElementExists("Report image Links", By.xpath(reprtArrow_xpath));
		return driver.findElements(By.xpath(reprtArrow_xpath));	
	}
	
	public List<WebElement> getPreReportLinks(){
		gen.waitForObjectExists(By.xpath(reprtlnks_xpath));
		gen.checkElementExists("Report Links", By.xpath(reprtlnks_xpath));
		return driver.findElements(By.xpath(reprtlnks_xpath));	
	}
	
	public WebElement getPositionsArrow()
	{
		String reprtName = dataTable.getData("General_Data", "ReportName");
		int index = 0;
		int i = getPreReportLinks().size();
	//	int j=0;
		for(int j=0;j<i;j++)
		{
			String t= getPreReportLinks().get(j).getText();
			if(reprtName.equals(t))
			{
				index=j;
				//return driver.findElements(By.xpath(reprtArrow_xpath)).get(j);
				break;
			}
			
		}
		
		return driver.findElements(By.xpath(reprtArrow_xpath)).get(index);
		
	}
}