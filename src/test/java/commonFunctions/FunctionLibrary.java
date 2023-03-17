package commonFunctions;

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

public class FunctionLibrary {

	public static WebDriver driver;

	// method for launch bBrowser

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

			System.out.println("Browser value Not Mathing");

		}

		return driver;

	}

	// method for launch url

	public static void openUrl(WebDriver driver) throws Throwable

	{

		driver.get(PropertyFileUtil.getValueForKey("Url"));

	}

	// method for wait for element

	public static void waitForElement(WebDriver driver, String LocatorType, String LocatorValue, String MyWait)

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

	public static void typeAction(WebDriver driver, String LocatorType, String Locatorvalue, String TestData)

	{

		if (LocatorType.equalsIgnoreCase("id"))

		{

			driver.findElement(By.id(Locatorvalue)).clear();

			driver.findElement(By.id(Locatorvalue)).sendKeys(TestData);

		}

		else if (LocatorType.equalsIgnoreCase("xpath"))

		{

			driver.findElement(By.xpath(Locatorvalue)).clear();

			driver.findElement(By.xpath(Locatorvalue)).sendKeys(TestData);

		}

		else if (LocatorType.equalsIgnoreCase("name"))

		{

			driver.findElement(By.name(Locatorvalue)).clear();

			driver.findElement(By.name(Locatorvalue)).sendKeys(TestData);

		}

	}

	// method for click action

	public static void clickAction(WebDriver driver, String LocatorType, String LocatorValue)

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

	public static void validateTitle(WebDriver driver, String ExpectedTitle)

	{

		String Actualtitle = driver.getTitle();
		try {
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
	//method for mouse click
	public static void mouseClick(WebDriver driver) throws Throwable
	{
		Actions ac = new Actions(driver);
		ac.moveToElement(driver.findElement(By.xpath("//a[.='Stock Items ']"))).perform();
		Thread.sleep(3000);
		ac.moveToElement(driver.findElement(By.xpath("(//a[contains(,'Stock Categories')])[2]"))).perform();
		Thread.sleep(3000);	
	}
	//method for Category table
	public static void categoryTable(WebDriver driver,String ExpectedData) throws Throwable
	{
		if(!driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search_textbox"))).isDisplayed())
			//click search panel button if search textbox not visable
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search_panel"))).click();
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search_textbox"))).sendKeys(ExpectedData);
		Thread.sleep(3000);
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search_button"))).click();
		Thread.sleep(3000);
		//capture category name from table
		String ActualData = driver.findElement(By.xpath("//table[@id='tbl_a_stock_categorieslist']/tbody/tr[1]/td[4]/div/span/span")).getText();
		System.out.println(ExpectedData+"   "+ActualData);
		Assert.assertEquals(ExpectedData, ActualData,"Category name  Not found in table");
	}
}






















