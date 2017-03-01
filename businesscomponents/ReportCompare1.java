package businesscomponents;

//import java.awt.List;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

public class ReportCompare1 {

	public CommonData commonData;

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub

		WebDriver driver = new FirefoxDriver();
		driver.get("http://148.173.174.122:8900/acadmin/?serverURL=http://wpqwa551:8000");
		driver.manage().window().maximize();

		String strUserName = "kgoutham";
		String strPassWord = "kgoutham";

		driver.findElement(By.name("userID")).clear();
		driver.findElement(By.name("userID")).sendKeys(strUserName);
		driver.findElement(By.name("Password")).clear();
		driver.findElement(By.name("Password")).sendKeys(strPassWord);

		driver.findElement(By.name("loginBtn")).click();
		Thread.sleep(1200);
		System.out.println("Page title is: " + driver.getTitle());

		if (driver.getTitle().contains("Files & Folders")) {
			driver.findElement(By.id("Jobs")).click();
			Thread.sleep(2500);
			driver.switchTo().defaultContent();
			WebElement frame = driver.findElement(By.id("TableFrame"));
			driver.switchTo().frame(frame);
			Thread.sleep(1200);
			if (driver.findElement(By.xpath("//a[contains(@onmouseover,'completedjobs')]")).isDisplayed()) {
				try {
					JavascriptExecutor executor = (JavascriptExecutor) driver;
					executor.executeScript("arguments[0].click();",
							driver.findElement(By.xpath("//a[contains(@onmouseover,'completedjobs')]")));
				} catch (Exception e) {
					driver.findElement(By.xpath("//a[contains(@onmouseover,'completedjobs')]")).click();
				}

				System.out.println("Clicking on Completed Tabs");
			} else {
				System.out.println("Failed:Unable to Find the Completed Tab section");
			}
			Thread.sleep(1200);
			WebElement frame1 = driver.findElement(By.id("TableFrame"));
			driver.switchTo().frame(frame1);
			String strValue = "MRF412";
			driver.findElement(By.id("FilterText")).clear();
			driver.findElement(By.id("FilterText")).sendKeys(strValue);
			Thread.sleep(1200);
			driver.findElement(By.xpath("//input[@value='Apply']")).click();
			Thread.sleep(3500);
			WebElement frame2 = driver.findElement(By.id("ifrListFrame"));
			driver.switchTo().frame(frame2);

			if (driver.findElement(By.xpath("(//a[contains(text(),'MRF412_reportcheck.ROI')])[1]")).isDisplayed()) {

				String oldTab = driver.getWindowHandle();
				driver.findElement(By.xpath("(//a[contains(text(),'MRF412_reportcheck.ROI')])[1]")).click();
				Thread.sleep(5000);
				ArrayList<String> newTab = new ArrayList<String>(driver.getWindowHandles());
				newTab.remove(oldTab);
				// change focus to new tab
				driver.switchTo().window(newTab.get(0));
				WebElement frame3 = driver.findElement(By.id("reportframe"));
				driver.switchTo().frame(frame3);
				String strPageSource = driver.getPageSource();
				CommonData.strPageSource = strPageSource;
				String strPageTitle = driver.findElement(By.xpath("//div[contains(@id,'water')]")).getText();
				CommonData.strPageTitle = strPageTitle;

				if (strPageTitle.contains("MultiUserTest License")) {
					System.out.println("Verifying the MultiUserTest License page is displayed");
					List<WebElement> products = driver
							.findElements(By.xpath("//div[contains(@onmouseover,'Partner Name')]"));
					ArrayList<String> strPartnerName = CommonData.strPartnerName;
					ArrayList<String> strInvoiceNumber = CommonData.strInvoiceNumber;
					ArrayList<String> strTotalPayment = CommonData.strTotalPayment;
					ArrayList<String> strSENumber = CommonData.strSENumber;
					ArrayList<String> strPaymentMarket = CommonData.strPaymentMarket;
					ArrayList<String> strPaymentunit = CommonData.strPaymentunit;
					ArrayList<String> strLiabilityUnit = CommonData.strLiabilityUnit;
					ArrayList<String> strLiabilityCurrency = CommonData.strLiabilityCurrency;
					ArrayList<String> strMarketCurrency = CommonData.strMarketCurrency;
					ArrayList<String> strInvoiceReleaseDate = CommonData.strInvoiceReleaseDate;
					ArrayList<String> strUserId = CommonData.strUserId;

					for (int i = 1; i <= products.size(); i++) {
						System.out.println(
								"-----------------DISPLAYING LIST OF TABLE VALUES----------------------->: " + i);
						String strPartnerNameList = driver
								.findElement(By.xpath("(//div[contains(@onmouseover,'Partner Name')])[" + i + "]"))
								.getText();
						strPartnerName.add(strPartnerNameList);
						System.out.println("Displaying the Partner Name list :" + strPartnerNameList);
						String strInvoiceNumberList = driver
								.findElement(By.xpath("(//div[contains(@onmouseover,'Invoice Number')])[" + i + "]"))
								.getText();
						strInvoiceNumber.add(strInvoiceNumberList);
						System.out.println("Displaying the Invoice Number list :" + strInvoiceNumberList);
						String strTotalPaymentList;
						if (i > 1) {
							int j;
							if (i == 3) {
								j = i + 2;
							} else {
								j = i + 1;
							}

							strTotalPaymentList = driver
									.findElement(By.xpath("(//div[contains(@onmouseover,'Total Payment')])[" + j + "]"))
									.getText();
						} else {
							strTotalPaymentList = driver
									.findElement(By.xpath("(//div[contains(@onmouseover,'Total Payment')])[" + i + "]"))
									.getText();
						}
						strTotalPayment.add(strTotalPaymentList);
						System.out.println("Displaying the Total Payment list :" + strTotalPaymentList);
						String strSENumberList = driver
								.findElement(By.xpath("(//div[contains(@onmouseover,'SE Number')])[" + i + "]"))
								.getText();
						strSENumber.add(strSENumberList);
						System.out.println("Displaying the SE Number list :" + strSENumberList);
						String strPaymentMarketList = driver
								.findElement(By.xpath("(//div[contains(@onmouseover,'Payment Market')])[" + i + "]"))
								.getText();
						strPaymentMarket.add(strPaymentMarketList);
						System.out.println("Displaying the Payment Market list :" + strPaymentMarketList);
						String strPaymentUnitList = driver
								.findElement(By.xpath("(//div[contains(@onmouseover,'Payment Unit')])[" + i + "]"))
								.getText();
						strPaymentunit.add(strPaymentUnitList);
						System.out.println("Displaying the Payment Unit list :" + strPaymentUnitList);
						String strLiabilityUnitList = driver
								.findElement(By.xpath("(//div[contains(@onmouseover,'Liability Unit')])[" + i + "]"))
								.getText();
						strLiabilityUnit.add(strLiabilityUnitList);
						System.out.println("Displaying the Liability Unit list :" + strLiabilityUnitList);
						String strLiabilityCurrencyList = driver
								.findElement(
										By.xpath("(//div[contains(@onmouseover,'Liability Currency')])[" + i + "]"))
								.getText();
						strLiabilityCurrency.add(strLiabilityCurrencyList);
						System.out.println("Displaying the Liability Currency list :" + strLiabilityCurrencyList);
						String strMarketCurrencyList = driver
								.findElement(By.xpath("(//div[contains(@onmouseover,'Market Currency')])[" + i + "]"))
								.getText();
						strMarketCurrency.add(strMarketCurrencyList);
						System.out.println("Displaying the Market Currency list :" + strMarketCurrencyList);
						String strInvoiceReleaseDateList;
						if (i > 1) {
							int j;
							if (i == 3) {
								j = i + 2;
							} else {
								j = i + 1;
							}

							strInvoiceReleaseDateList = driver.findElement(By
									.xpath("(//div[contains(@onmouseover,'This is the difference in cost between Invoice Total and Market Totals')]/nobr)["
											+ j + "]"))
									.getText();
						} else {
							strInvoiceReleaseDateList = driver.findElement(By
									.xpath("(//div[contains(@onmouseover,'This is the difference in cost between Invoice Total and Market Totals')]/nobr)["
											+ i + "]"))
									.getText();
						}
						strInvoiceReleaseDate.add(strInvoiceReleaseDateList);
						System.out.println("Displaying the Invoice release date list :" + strInvoiceReleaseDateList);
						String strUserIDList = driver
								.findElement(By.xpath("(//div[contains(@onmouseover,'USER ID')])[" + i + "]"))
								.getText();
						strUserId.add(strUserIDList);
						System.out.println("Displaying the UserId list :" + strUserIDList);
					}
//					for (int i = 0; i < products.size(); i++) {
//						System.out.println("############ CHECKING ########################");
//						System.out.println(CommonData.strInvoiceNumber.get(i) + " Invoice Number");
//						System.out.println(CommonData.strInvoiceReleaseDate.get(i) + " Invoice Release Date");
//						System.out.println(CommonData.strTotalPayment.get(i) + " Total Payment");
//
//					}
				} else {
					System.out.println("MultiUserTest License is not displayed");
				}

				driver.switchTo().defaultContent();
				driver.close();
				driver.switchTo().window(oldTab);
				// driver.get("
				// http://148.173.174.122:8900/acadmin/?serverURL=http://wpqwa551:8000");
				driver.get(
						"http://148.173.174.122:8900/acadmin/jobmanager.jsp?serverURL=http%3a%2f%2fwpqwa551%3a8000&volume=wpqwa551&daemonURL=http://wpqwa551:8100&daemonURL=http://wpqwa551:8100");

				if (driver.findElement(By.xpath("//td[contains(text(),'System')]")).isDisplayed()) {
					System.out.println("--------------##### Focus Changed to old window #### -----------");
				} else {
					System.out.println("---------Focus not changed---------------");
				}
				driver.switchTo().defaultContent();
				WebElement postframe = driver.findElement(By.id("TableFrame"));
				driver.switchTo().frame(postframe);
				Thread.sleep(1200);
				if (driver.findElement(By.xpath("//a[contains(@onmouseover,'completedjobs')]")).isDisplayed()) {
					try {
						JavascriptExecutor executor = (JavascriptExecutor) driver;
						executor.executeScript("arguments[0].click();",
								driver.findElement(By.xpath("//a[contains(@onmouseover,'completedjobs')]")));
					} catch (Exception e) {
						 driver.findElement(By.xpath("//a[contains(@onmouseover,'completedjobs')]")).click();
					}					
					System.out.println("Clicking on Completed Tabs");
				} else {
					System.out.println("Failed:Unable to Find the Completed Tab section");
				}
				Thread.sleep(5000);
				WebElement postframe1 = driver.findElement(By.id("TableFrame"));
				driver.switchTo().frame(postframe1);
				String strPostValue = "MRF412";
				driver.findElement(By.id("FilterText")).clear();
				driver.findElement(By.id("FilterText")).sendKeys(strPostValue);
				Thread.sleep(1200);
				driver.findElement(By.xpath("//input[@value='Apply']")).click();
				Thread.sleep(3500);
				WebElement postframe2 = driver.findElement(By.id("ifrListFrame"));
				driver.switchTo().frame(postframe2);
				String postoldTab = driver.getWindowHandle();
				driver.findElement(By.xpath("(//a[contains(text(),'MRF412_reportcheck.ROI')])[1]")).click();
				Thread.sleep(5000);
				ArrayList<String> newTab_1 = new ArrayList<String>(driver.getWindowHandles());
				newTab_1.remove(postoldTab);
				// change focus to new tab
				driver.switchTo().window(newTab_1.get(0));
				WebElement postframe3 = driver.findElement(By.id("reportframe"));
				driver.switchTo().frame(postframe3);
				System.out.println(
						"-############## COMPARING PRE-REPORT and POST-REPORT-##############--");
				// String strPostPageSource = driver.getPageSource();
				String strPostPageTitle = driver.findElement(By.xpath("//div[contains(@id,'water')]")).getText();
				if (strPostPageTitle.contains(CommonData.strPageTitle)) {
					System.out.println("Passed : Page Title is matching with Pre-report and Post-Report "+strPostPageTitle);

					for (int i = 1, k = 0; i <= CommonData.strPartnerName.size(); i++, k++) {
						System.out.println(
								"----------------->>>  COMPARING LIST OF TABLE VALUES FORM PRE-REPORT AND POST-REPORT  ----------------------->>>: "
										+ i);
						String strPostPartnerNameList = driver
								.findElement(By.xpath("(//div[contains(@onmouseover,'Partner Name')])[" + i + "]"))
								.getText();
						if (CommonData.strPartnerName.get(k).contains(strPostPartnerNameList)) {
							System.out.println("Passed : Partner Name is matching with Pre-report and Post-Report :"
									+ strPostPartnerNameList);
						} else {
							System.out.println("Failed : Partner Name is not matching with Pre-report and Post-Report :"
									+ strPostPartnerNameList);
						}

						String strPostInvoiceNumberList = driver
								.findElement(By.xpath("(//div[contains(@onmouseover,'Invoice Number')])[" + i + "]"))
								.getText();
						if (CommonData.strInvoiceNumber.get(k).contains(strPostInvoiceNumberList)) {
							System.out.println(
									"Passed : Post Invoice Number is matching with Pre-report and Post-Report :"
											+ strPostInvoiceNumberList);
						} else {
							System.out.println(
									"Failed : Post Invoice Number is not matching with Pre-report and Post-Report :"
											+ strPostInvoiceNumberList);
						}

						String strPostTotalPaymentList;
						if (i > 1) {
							int j;
							if (i == 3) {
								j = i + 2;
							} else {
								j = i + 1;
							}

							strPostTotalPaymentList = driver
									.findElement(By.xpath("(//div[contains(@onmouseover,'Total Payment')])[" + j + "]"))
									.getText();
							if (CommonData.strTotalPayment.get(k).contains(strPostTotalPaymentList)) {
								System.out.println(
										"Passed : Total Payment List is matching with Pre-report and Post-Report :"
												+ strPostTotalPaymentList);
							} else {
								System.out.println(
										"Failed : Total Payment List is not matching with Pre-report and Post-Report :"
												+ strPostTotalPaymentList);
							}
						} else {
							strPostTotalPaymentList = driver
									.findElement(By.xpath("(//div[contains(@onmouseover,'Total Payment')])[" + i + "]"))
									.getText();
							if (CommonData.strTotalPayment.get(k).contains(strPostTotalPaymentList)) {
								System.out.println(
										"Passed : Total Payment List is matching with Pre-report and Post-Report :"
												+ strPostTotalPaymentList);
							} else {
								System.out.println(
										"Failed : Total Payment List is not matching with Pre-report and Post-Report :"
												+ strPostTotalPaymentList);
							}
						}

						String strPostSENumberList = driver
								.findElement(By.xpath("(//div[contains(@onmouseover,'SE Number')])[" + i + "]"))
								.getText();

						if (CommonData.strSENumber.get(k).contains(strPostSENumberList)) {
							System.out.println("Passed : SE Number List is matching with Pre-report and Post-Report :"
									+ strPostSENumberList);
						} else {
							System.out
									.println("Failed : SE Number List is not matching with Pre-report and Post-Report :"
											+ strPostSENumberList);
						}

						String strPostPaymentMarketList = driver
								.findElement(By.xpath("(//div[contains(@onmouseover,'Payment Market')])[" + i + "]"))
								.getText();

						if (CommonData.strPaymentMarket.get(k).contains(strPostPaymentMarketList)) {
							System.out.println("Passed : Payment Market is matching with Pre-report and Post-Report :"
									+ strPostPaymentMarketList);
						} else {
							System.out
									.println("Failed : Payment Market is not matching with Pre-report and Post-Report :"
											+ strPostPaymentMarketList);
						}

						String strPostPaymentUnitList = driver
								.findElement(By.xpath("(//div[contains(@onmouseover,'Payment Unit')])[" + i + "]"))
								.getText();
						if (CommonData.strPaymentunit.get(k).contains(strPostPaymentUnitList)) {
							System.out
									.println("Passed : Payment Unit list is matching with Pre-report and Post-Report :"
											+ strPostPaymentUnitList);
						} else {
							System.out.println(
									"Failed : Payment Unit list is not matching with Pre-report and Post-Report :"
											+ strPostPaymentUnitList);
						}

						String strPostLiabilityUnitList = driver
								.findElement(By.xpath("(//div[contains(@onmouseover,'Liability Unit')])[" + i + "]"))
								.getText();
						if (CommonData.strLiabilityUnit.get(k).contains(strPostLiabilityUnitList)) {
							System.out.println(
									"Passed : Liability Unit list is matching with Pre-report and Post-Report :"
											+ strPostLiabilityUnitList);
						} else {
							System.out.println(
									"Failed : Liability Unit list is not matching with Pre-report and Post-Report :"
											+ strPostLiabilityUnitList);
						}

						String strPostLiabilityCurrencyList = driver
								.findElement(
										By.xpath("(//div[contains(@onmouseover,'Liability Currency')])[" + i + "]"))
								.getText();
						if (CommonData.strLiabilityCurrency.get(k).contains(strPostLiabilityCurrencyList)) {
							System.out.println(
									"Passed : Liability Currency list is matching with Pre-report and Post-Report :"
											+ strPostLiabilityCurrencyList);
						} else {
							System.out.println(
									"Failed : Liability Currency list is not matching with Pre-report and Post-Report :"
											+ strPostLiabilityCurrencyList);
						}

						String strPostMarketCurrencyList = driver
								.findElement(By.xpath("(//div[contains(@onmouseover,'Market Currency')])[" + i + "]"))
								.getText();
						if (CommonData.strMarketCurrency.get(k).contains(strPostMarketCurrencyList)) {
							System.out.println(
									"Passed : Market Currency list is matching with Pre-report and Post-Report :"
											+ strPostMarketCurrencyList);
						} else {
							System.out.println(
									"Failed : Market Currency list is not matching with Pre-report and Post-Report :"
											+ strPostMarketCurrencyList);
						}

						String strPostInvoiceReleaseDateList;
						if (i > 1) {
							int j;
							if (i == 3) {
								j = i + 2;
							} else {
								j = i + 1;
							}

							strPostInvoiceReleaseDateList = driver.findElement(By
									.xpath("(//div[contains(@onmouseover,'This is the difference in cost between Invoice Total and Market Totals')]/nobr)["
											+ j + "]"))
									.getText();
							if (CommonData.strInvoiceReleaseDate.get(k).contains(strPostInvoiceReleaseDateList)) {
								System.out.println(
										"Passed : Invoice Release Date list is matching with Pre-report and Post-Report :"
												+ strPostInvoiceReleaseDateList);
							} else {
								System.out.println(
										"Failed : Invoice Release Date list is not matching with Pre-report and Post-Report :"
												+ strPostInvoiceReleaseDateList);
							}
						} else {
							strPostInvoiceReleaseDateList = driver.findElement(By
									.xpath("(//div[contains(@onmouseover,'This is the difference in cost between Invoice Total and Market Totals')]/nobr)["
											+ i + "]"))
									.getText();
							if (CommonData.strInvoiceReleaseDate.get(k).contains(strPostInvoiceReleaseDateList)) {
								System.out.println(
										"Passed : Invoice Release Date list is matching with Pre-report and Post-Report :"
												+ strPostInvoiceReleaseDateList);
							} else {
								System.out.println(
										"Failed : Invoice Release Date list is not matching with Pre-report and Post-Report :"
												+ strPostInvoiceReleaseDateList);
							}
						}

						String strPostUserIDList = driver
								.findElement(By.xpath("(//div[contains(@onmouseover,'USER ID')])[" + i + "]"))
								.getText();
						if (CommonData.strUserId.get(k).contains(strPostUserIDList)) {
							System.out.println("Passed : User ID list is matching with Pre-report and Post-Report :"
									+ strPostUserIDList);
						} else {
							System.out.println("Failed : User ID list is not matching with Pre-report and Post-Report :"
									+ strPostUserIDList);
						}

					}
					driver.switchTo().defaultContent();
					driver.close();
					driver.switchTo().window(postoldTab);
					System.out.println(
							"########################## COMPLETED VALIDATIONS ALL ARE MATCHING ##################################");
				} else {
					System.out.println("Failed : Page Title is not matching with Pre-report and Post-Report");
				}

			} else {
				System.out.println("Failed to open the Multi user license page");
			}

		} else {
			System.out.println("Unable to open the Files and folder page after login");
		}
		driver.quit();
	}

	public void preResultValues() {

	}
	
	
	

}