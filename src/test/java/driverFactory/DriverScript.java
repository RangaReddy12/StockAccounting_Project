
package driverFactory;

import org.openqa.selenium.WebDriver;

import commonFunctions.FunctionLibrary;
import utilities.ExcelFileUtil;

public class DriverScript {

	public static WebDriver driver;

	String inputpath = "C:\\Users\\PASKAL\\git\\StockAccounting_Project\\FileInput\\DataEnginefull.xlsx";

	String outputpath = "C:\\Users\\PASKAL\\git\\StockAccounting_Project\\FileOutput\\HybridResultsfull.xlsx";

	public void startTest() throws Throwable

	{

		String ModuleStatus = "";

		// create Object for excel file util class

		ExcelFileUtil xl = new ExcelFileUtil(inputpath);

		// iterate all rows in mastertestcases sheet

		for (int i = 1; i <= xl.rowCount("MasterTestCases"); i++)

		{

			// read execution status cell

			String Execution_Status = xl.getCellData("MasterTestCases", i, 2);

			if (Execution_Status.equalsIgnoreCase("Y"))

			{

				// read corresponding sheet from MasterTestCases

				String TCModule = xl.getCellData("MasterTestCases", i, 1);

				// iterate corresponding sheet

				for (int j = 1; j <= xl.rowCount(TCModule); j++)

				{

					// read all cells from TCModule sheet

					String Description = xl.getCellData(TCModule, j, 0);

					String ObjectType = xl.getCellData(TCModule, j, 1);

					String LocatorType = xl.getCellData(TCModule, j, 2);

					String LocatorValue = xl.getCellData(TCModule, j, 3);

					String TestData = xl.getCellData(TCModule, j, 4);

					try {

						if (ObjectType.equalsIgnoreCase("startBrowser"))

						{

							driver = FunctionLibrary.startBrowser();

						}

						else if (ObjectType.equalsIgnoreCase("openUrl"))

						{

							FunctionLibrary.openUrl(driver);

						}

						else if (ObjectType.equalsIgnoreCase("waitForElement"))

						{

							FunctionLibrary.waitForElement(driver, LocatorType, LocatorValue, TestData);

						}

						else if (ObjectType.equalsIgnoreCase("typeAction"))

						{

							FunctionLibrary.typeAction(driver, LocatorType, LocatorValue, TestData);

						}

						else if (ObjectType.equalsIgnoreCase("clickAction"))

						{

							FunctionLibrary.clickAction(driver, LocatorType, LocatorValue);

						}

						else if (ObjectType.equalsIgnoreCase("validateTitle"))

						{

							FunctionLibrary.validateTitle(driver, TestData);

						}

						else if (ObjectType.equalsIgnoreCase("closeBrowser"))

						{

							FunctionLibrary.closeBrowser(driver);

						}

						// write as pass into TCModule sheet

						xl.setCellData(TCModule, j, 5, "Pass", outputpath);

						ModuleStatus = "True";

					} catch (Exception e)

					{

						System.out.println(e.getMessage());

						// write as Fail into TCModule sheet

						xl.setCellData(TCModule, j, 5, "Fail", outputpath);

						ModuleStatus = "False";

					}

					if (ModuleStatus.equalsIgnoreCase("True"))

					{

						xl.setCellData("MasterTestCases", i, 3, "Pass", outputpath);

					}

					else

					{

						xl.setCellData("MasterTestCases", i, 3, "Fail", outputpath);

					}

				}

			}

			else

			{

				// write as bllocked which testcase flag to N

				xl.setCellData("MasterTestCases", i, 3, "Blocked", outputpath);

			}

		}

	}

}
