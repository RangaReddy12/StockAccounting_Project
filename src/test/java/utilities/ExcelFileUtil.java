package utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelFileUtil {
	Workbook wb;
	//constructor for reading excel path
	public ExcelFileUtil(String Excelpath)throws Throwable
	{
		FileInputStream fi = new FileInputStream(Excelpath);
		wb  = WorkbookFactory.create(fi);
	}
	//count no of rows in a sheet
	public int rowCount(String Sheetname)
	{
		return wb.getSheet(Sheetname).getLastRowNum();
	}
	//method for get cell data
	public String getCellData(String sheetName,int row,int column)
	{
		String data="";
		if(wb.getSheet(sheetName).getRow(row).getCell(column).getCellType()==Cell.CELL_TYPE_NUMERIC)
		{
			int celldata = (int) wb.getSheet(sheetName).getRow(row).getCell(column).getNumericCellValue();
			data =String.valueOf(celldata);
		}
		else
		{
			data =wb.getSheet(sheetName).getRow(row).getCell(column).getStringCellValue();
		}
		return data;
	}
	//method for set cell 
	public void setCellData(String sheetName,int row,int column,String Status,String WriteExcel)throws Throwable
	{
		//get sheet from wb
		Sheet ws =wb.getSheet(sheetName);
		//get row from sheet
		Row rowNum = ws.getRow(row);
		//create cell in row
		Cell cell =rowNum.createCell(column);
		//write status
		cell.setCellValue(Status);
		if(Status.equalsIgnoreCase("Pass"))
		{
			CellStyle style =wb.createCellStyle();
			Font font =wb.createFont();
			font.setColor(IndexedColors.GREEN.getIndex());
			font.setBold(true);
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			style.setFont(font);
			rowNum.getCell(column).setCellStyle(style);
		}
		else  if(Status.equalsIgnoreCase("Fail"))
		{
			CellStyle style =wb.createCellStyle();
			Font font =wb.createFont();
			font.setColor(IndexedColors.RED.getIndex());
			font.setBold(true);
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			style.setFont(font);
			rowNum.getCell(column).setCellStyle(style); 
		}
		else if(Status.equalsIgnoreCase("Blocked"))
		{
			CellStyle style =wb.createCellStyle();
			Font font =wb.createFont();
			font.setColor(IndexedColors.BLUE.getIndex());
			font.setBold(true);
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			style.setFont(font);
			rowNum.getCell(column).setCellStyle(style);
		}
		FileOutputStream fo = new FileOutputStream(WriteExcel);
		wb.write(fo);
	}
	public static void main(String[] args) throws Throwable {
		ExcelFileUtil xl = new ExcelFileUtil("C:\\Users\\PASKAL\\git\\StockAccounting_Project\\Sample.xlsx");
		int rc = xl.rowCount("Empdata");
		System.out.println(rc);
		for(int i=1;i<=rc;i++)
		{
			String fname =xl.getCellData("Empdata", i, 0);
			String mname =xl.getCellData("Empdata", i, 1);
			String lname =xl.getCellData("Empdata", i, 2);
			String eid =xl.getCellData("Empdata", i, 3);
			System.out.println(fname+"    "+mname+"   "+lname+"    "+eid);
			xl.setCellData("Empdata", i, 4, "Pass", "C:\\Users\\PASKAL\\git\\StockAccounting_Project\\Results.xlsx");
			//xl.setCellData("Empdata", i, 4, "Fail", "C:\\Users\\PASKAL\\git\\StockAccounting_Project\\Results.xlsx");
			//xl.setCellData("Empdata", i, 4, "Blocked", "C:\\Users\\PASKAL\\git\\StockAccounting_Project\\Results.xlsx");
		}
	}
}










