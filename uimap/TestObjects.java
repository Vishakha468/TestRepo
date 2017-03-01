package uimap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import componentgroups.GenericComponents;

import supportlibraries.ReusableLibrary;
import supportlibraries.ScriptHelper;

public class TestObjects extends ReusableLibrary{
	
	public TestObjects(ScriptHelper scriptHelper) {
		super(scriptHelper);
		// TODO Auto-generated constructor stub
	}
	GenericComponents gen =new GenericComponents(scriptHelper);
	
	private static final String strGoogleTxt_xpath = ".//*[@id='q']" ;
	private static final String strText_xpath      =".//*[@id='logo-sub']";
	private static final String btnSearch_xpath    = ".//*[@name='btnK']";
	private static final String txtboxUserId_xpath =".//*[@name='userID']";
	private static final String txtboxNewUserID_xpath =".//*[@name='userid']";
	
	private static final String txtPasswordtxtBox_xpath =".//*[@name='Password']";
	private static final String txtNewPasswordtxtBox_xpath =".//*[@name='password']";
	
	private static final String btnLogin_xpath     = ".//*[@name='loginBtn']";
	private static final String btnNewLogin_xpath     = ".//*[@name='login']";
	
	private static final String txtReportId       = ".//*[@id='I625']";
	private static final String lnkJobs           =".//*[@id='JobsLink']";
	private static final String lnkSubMenu        = "//a[contains(@onmouseover,'completedjobs')]";
	private static final String lnkReport         = "(.//*[@id='OutputFileName']/a)[2]";
	private static final String PostUserId_xpath  =".//*[@name='userid']";
	private static final String Password_xpath  =".//*[@name='password']";
	private static final String postLogin_xpath  =".//*[@value='Log In']";
	
	
	
	
	
	public WebElement getPostUserIdTextBox	(){
		gen.waitForObjectExists(By.xpath(PostUserId_xpath));
		gen.checkElementExists("PostUserID text box", By.xpath(PostUserId_xpath));
		return driver.findElement(By.xpath(PostUserId_xpath));	
	}
	public WebElement getPostPasswordTextBox(){
		gen.waitForObjectExists(By.xpath(Password_xpath));
		gen.checkElementExists("Post password text box", By.xpath(Password_xpath));
		return driver.findElement(By.xpath(Password_xpath));	
	}
	public WebElement getPostLoginButton(){
		gen.waitForObjectExists(By.xpath(postLogin_xpath));
		gen.checkElementExists("Login Button for Post URL", By.xpath(postLogin_xpath));
		return driver.findElement(By.xpath(postLogin_xpath));	
	}
	public WebElement getReportlnk(){
		gen.waitForObjectExists(By.xpath(lnkReport));
		gen.checkElementExists("Report Document Link", By.xpath(lnkReport));
		return driver.findElement(By.xpath(lnkReport));	
	}
	public WebElement getCompletedLnk(){
		gen.waitForObjectExists(By.xpath(lnkSubMenu));
		gen.checkElementExists("Completed Link", By.xpath(lnkSubMenu));
		return driver.findElement(By.xpath(lnkSubMenu));	
	}
	
	public WebElement getJobLink(){
		gen.waitForObjectExists(By.xpath(lnkJobs));
		gen.checkElementExists("job Link", By.xpath(lnkJobs));
		return driver.findElement(By.xpath(lnkJobs));	
	}
	
	
	public WebElement getReportID(){
		gen.waitForObjectExists(By.xpath(txtReportId));
		gen.checkElementExists("ReportID", By.xpath(txtReportId));
		return driver.findElement(By.xpath(txtReportId));	
	}
	
	public WebElement getLoginButton(){
		gen.waitForObjectClickable(By.xpath(btnLogin_xpath));
		gen.waitForObjectExists(By.xpath(btnLogin_xpath));
		gen.checkElementExists("Login Button", By.xpath(btnLogin_xpath));
		return driver.findElement(By.xpath(btnLogin_xpath));
	}
	public WebElement getNewLoginButton()
	{
		gen.waitForObjectClickable(By.xpath(btnNewLogin_xpath));
		gen.waitForObjectExists(By.xpath(btnNewLogin_xpath));
		gen.checkElementExists("Login Button", By.xpath(btnNewLogin_xpath));
		return driver.findElement(By.xpath(btnNewLogin_xpath));
	}
	public WebElement getPasswordTextBox(){
		gen.waitForObjectClickable(By.xpath(txtPasswordtxtBox_xpath));
		gen.waitForObjectExists(By.xpath(txtPasswordtxtBox_xpath));
		gen.checkElementExists("Password Text Box", By.xpath(txtPasswordtxtBox_xpath));
		return driver.findElement(By.xpath(txtPasswordtxtBox_xpath));
	}
	
	public WebElement getNewPasswordTextBox()
	{
		gen.waitForObjectClickable(By.xpath(txtNewPasswordtxtBox_xpath));
		gen.waitForObjectExists(By.xpath(txtNewPasswordtxtBox_xpath));
		gen.checkElementExists("Password Text Box", By.xpath(txtNewPasswordtxtBox_xpath));
		return driver.findElement(By.xpath(txtNewPasswordtxtBox_xpath));
	}
	public WebElement getUserIdTextBox(){
		gen.waitForObjectClickable(By.xpath(txtboxUserId_xpath));
		gen.waitForObjectExists(By.xpath(txtboxUserId_xpath));
		gen.checkElementExists("User Id Text Box", By.xpath(txtboxUserId_xpath));
		return driver.findElement(By.xpath(txtboxUserId_xpath));
	}
	public WebElement getNewUserIdTextBox()
	{
		gen.waitForObjectClickable(By.xpath(txtboxNewUserID_xpath));
		gen.waitForObjectExists(By.xpath(txtboxNewUserID_xpath));
		gen.checkElementExists("User Id Text Box", By.xpath(txtboxNewUserID_xpath));
		return driver.findElement(By.xpath(txtboxNewUserID_xpath));
	}
	
	
		
	
	
	
}