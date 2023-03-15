package commonFunctions;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import utilities.PropertyFileUtil;

public class FunctionLibrary 
{
	public static WebDriver driver;
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
}
