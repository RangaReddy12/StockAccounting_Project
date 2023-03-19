package driverFactory;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import commonFunctions.FunctionLibrary;
import utilities.ExcelFileUtil;

public class DriverScript 
{
	public static WebDriver driver;
	String inputpath = "C:\\Users\\dell\\git\\StockAccounting_Project\\FileInput\\DataEngine.xlsx";
	String outputpath = "C:\\Users\\dell\\git\\StockAccounting_Project\\FileOutput\\HybridResults.xlsx";
	ExtentReports report;
	ExtentTest logger;
	public void startTest() throws Throwable 
	{
		String ModuleStatus="";
		// create Object for excel file util class
		ExcelFileUtil xl = new ExcelFileUtil(inputpath);
		// iterate all rows in mastertestcases sheet
		for (int i = 1; i <=xl.rowCount("MasterTestCases"); i++) 
		{
			// read execution status cell
			String Execution_Status = xl.getCellData("MasterTestCases", i, 2);
			if (Execution_Status.equalsIgnoreCase("Y")) 
			{
				// read corresponding sheet from MasterTestCases
				String TCModule = xl.getCellData("MasterTestCases", i, 1);
				report = new ExtentReports("./ExtentReports/"+TCModule+"_"+FunctionLibrary.generateDate()+".html");
				logger = report.startTest(TCModule);
				logger.assignAuthor("Uma");
				// iterate corresponding sheet
				for (int j = 1; j <=xl.rowCount(TCModule); j++) 
				{
					// read all cells from TCModule sheet
					String Description = xl.getCellData(TCModule, j, 0);
					String ObjectType = xl.getCellData(TCModule, j, 1);
					String LocatorType = xl.getCellData(TCModule, j, 2);
					String LocatorValue = xl.getCellData(TCModule, j, 3);
					String TestData = xl.getCellData(TCModule, j, 4);
					try 
					{
						if (ObjectType.equalsIgnoreCase("startBrowser")) 
						{
							driver = FunctionLibrary.startBrowser();
							logger.log(LogStatus.INFO, Description);
						}
						else if (ObjectType.equalsIgnoreCase("openUrl")) 
						{
							FunctionLibrary.openUrl(driver);
							logger.log(LogStatus.INFO, Description);
						}
						else if (ObjectType.equalsIgnoreCase("waitForElement")) 
						{
							FunctionLibrary.waitForElement(driver, LocatorType, LocatorValue, TestData);
							logger.log(LogStatus.INFO, Description);
						}
						else if (ObjectType.equalsIgnoreCase("typeAction")) 
						{
							FunctionLibrary.typeAction(driver, LocatorType, LocatorValue, TestData);
							logger.log(LogStatus.INFO, Description);
						}
						else if (ObjectType.equalsIgnoreCase("clickAction")) 
						{
							FunctionLibrary.clickAction(driver, LocatorType, LocatorValue);
							logger.log(LogStatus.INFO, Description);
						}
						else if (ObjectType.equalsIgnoreCase("validateTitle")) 
						{
							FunctionLibrary.validateTitle(driver, TestData);
							logger.log(LogStatus.INFO, Description);
						}
						else if (ObjectType.equalsIgnoreCase("closeBrowser")) 
						{
							FunctionLibrary.closeBrowser(driver);
							logger.log(LogStatus.INFO, Description);
						}
						else if (ObjectType.equalsIgnoreCase("mouseClick")) 
						{
							FunctionLibrary.mouseClick(driver);
							logger.log(LogStatus.INFO, Description);
						}
						else if (ObjectType.equalsIgnoreCase("categoryTable")) 
						{
							FunctionLibrary.categoryTable(driver, TestData);
							logger.log(LogStatus.INFO, Description);
						}
						else if (ObjectType.equalsIgnoreCase("captureData")) 
						{
							FunctionLibrary.captureData(driver, LocatorType, LocatorValue);	
							logger.log(LogStatus.INFO, Description);
						}
						else if (ObjectType.equalsIgnoreCase("supplierTable")) 
						{
							FunctionLibrary.supplierTable(driver);
							logger.log(LogStatus.INFO, Description);
						}
						// write as pass into TCModule Sheet
						xl.setCellData(TCModule, j, 5, "Pass", outputpath);
						logger.log(LogStatus.PASS, Description);
						ModuleStatus="True";
					} catch (Exception e) 
					{
						System.out.println(e.getMessage());
						// write as Fail into TCModule sheet
						xl.setCellData(TCModule, j, 5, "Fail", outputpath);
						logger.log(LogStatus.FAIL, Description);
						ModuleStatus="False";
					}
					if (ModuleStatus.equalsIgnoreCase("True")) 
					{
						xl.setCellData("MasterTestCases", i, 3, "Pass", outputpath);
					}
					else 
					{
						xl.setCellData("MasterTestCases", i, 3, "Fail", outputpath);						
					}
					report.endTest(logger);
					report.flush();
				}
			}
			else 
			{
				// write as blocked which testcase flag to N
				xl.setCellData("MasterTestCases", i, 3, "Blocked", outputpath);
			}
		}
	}
}
