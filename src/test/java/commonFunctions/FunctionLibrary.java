package commonFunctions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import utilities.PropertyFileUtil;

public class FunctionLibrary 
{
	public static WebDriver driver;
	public static String Expected ="";
	public static String Actual ="";
	// method for launch browser
	public static WebDriver startBrowser() throws Throwable
	{
		if (PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("chrome")) 
		{
			driver = new ChromeDriver();
			driver.manage().window().maximize();	
		}
		else if (PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("firefox")) 
		{
			driver = new FirefoxDriver();
		}
		else 
		{
			System.out.println("Browser value Not Matching");
		}
		return driver;
	}
	
	// method for launch url
	public static void openUrl(WebDriver driver) throws Throwable 
	{
		driver.get(PropertyFileUtil.getValueForKey("Url"));
	}
	
	// method for wait for element
	public static void waitForElement(WebDriver driver,String LocatorType,String LocatorValue,String MyWait) 
	{
		WebDriverWait wait = new WebDriverWait(driver, Integer.parseInt(MyWait));
		if (LocatorType.equalsIgnoreCase("id")) 
		{
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(LocatorValue)));
		}
		else if (LocatorType.equalsIgnoreCase("xpath")) 
		{
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LocatorValue)));
		}
		else if (LocatorType.equalsIgnoreCase("name")) 
		{
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(LocatorValue)));
		}
	}
	
	// method for type actions
	public static void typeAction(WebDriver driver,String LocatorType,String LocatorValue,String TestData) 
	{
		if (LocatorType.equalsIgnoreCase("id")) 
		{
			driver.findElement(By.id(LocatorValue)).clear();
			driver.findElement(By.id(LocatorValue)).sendKeys(TestData);
		}
		else if (LocatorType.equalsIgnoreCase("xpath")) 
		{
			driver.findElement(By.xpath(LocatorValue)).clear();
			driver.findElement(By.xpath(LocatorValue)).sendKeys(TestData);
		}
		else if (LocatorType.equalsIgnoreCase("name")) 
		{
			driver.findElement(By.name(LocatorValue)).clear();
			driver.findElement(By.name(LocatorValue)).sendKeys(TestData);
		}
	}
	
	// method for click action
	public static void clickAction(WebDriver driver,String LocatorType,String LocatorValue) 
	{
		if (LocatorType.equalsIgnoreCase("name")) 
		{
			driver.findElement(By.name(LocatorValue)).click();
		}
		else if (LocatorType.equalsIgnoreCase("xpath")) 
		{
			driver.findElement(By.xpath(LocatorValue)).click();
		}
		else if (LocatorType.equalsIgnoreCase("id")) 
		{
			driver.findElement(By.id(LocatorValue)).sendKeys(Keys.ENTER);
		}
	}
	public static void validateTitle(WebDriver driver,String ExpectedTitle) 
	{
		String Actualtitle = driver.getTitle();
		try 
		{
			Assert.assertEquals(Actualtitle, ExpectedTitle, "Title is Not Matching");
		} catch (Throwable t) 
		{
			System.out.println(t.getMessage());
		}
	}
	
	// method for close browser
	public static void closeBrowser(WebDriver driver) 
	{
		driver.quit();
	}
	
	// method for mouse click
	public static void mouseClick(WebDriver driver) throws Throwable 
	{
		Actions ac = new Actions(driver);
		ac.moveToElement(driver.findElement(By.xpath("//a[.='Stock Items ']"))).perform();
		Thread.sleep(3000);
		ac.moveToElement(driver.findElement(By.xpath("(//a[contains(.,'Stock Categories')])[2]"))).click().perform();
		Thread.sleep(3000);
	}
	
	// method for category table
	public static void categoryTable(WebDriver driver,String ExpectedData) throws Throwable 
	{
		if(!driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-textbox"))).isDisplayed())
			// click search panel button if search textbox not displayed
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-panel"))).click();
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-textbox"))).sendKeys(ExpectedData);
		Thread.sleep(4000);
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-button"))).click();
		Thread.sleep(4000);
		// capture category name from table
		String ActualData = driver.findElement(By.xpath("//table[@id='tbl_a_stock_categorieslist']/tbody/tr[3]/td[4]/div/span/span")).getText();
		System.out.println(ExpectedData+"     "+ActualData);
		Assert.assertEquals(ExpectedData, ActualData,"Category Name Not Found in Table");
	}
	
	// method for capture supplier number
	public static void captureData(WebDriver driver,String Locatortype, String LocatorValue) 
	{
		Expected = driver.findElement(By.name(LocatorValue)).getAttribute("value");
	}
	
	// method for supplier table
	public static void supplierTable(WebDriver driver) throws Throwable 
	{
		if(!driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-textbox"))).isDisplayed())
			// click search panel button if search textbox not displayed
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-panel"))).click();
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-textbox"))).sendKeys(Expected);
		Thread.sleep(4000);
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-button"))).click();
		Thread.sleep(4000);
		Actual =driver.findElement(By.xpath("//table[@id='tbl_a_supplierslist']/tbody/tr[1]/td[6]/div/span/span")).getText();
		System.out.println(Expected+"    "+Actual);
		Assert.assertEquals(Expected, Actual,"Supplier Number Not found in table");
	}
	
	// method for capture customer number
	public static void captureData1(WebDriver driver,String Locatortype, String LocatorValue) 
	{
		Expected = driver.findElement(By.name(LocatorValue)).getAttribute("value");
	}
	
	// method for customer table
	public static void customerTable(WebDriver driver) throws Throwable 
	{
		if(!driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-textbox"))).isDisplayed())
			// click search panel button if search textbox not displayed
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-panel"))).click();
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-textbox"))).sendKeys(Expected);
		Thread.sleep(4000);
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-button"))).click();
		Thread.sleep(4000);
		Actual =driver.findElement(By.xpath("//table[@id='tbl_a_customerslist']/tbody/tr[1]/td[5]/div/span/span")).getText();
		System.out.println(Expected+"    "+Actual);
		Assert.assertEquals(Expected, Actual,"Customer Number Not found in table");
	}
	
	// method for date pattern
	public static String generateDate() 
	{
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("YYYY_MM_DD");
		return df.format(date);
	}
}
