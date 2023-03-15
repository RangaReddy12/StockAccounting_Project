package utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.remote.server.commandhandler.Status;

public abstract class ExcelFileUtil {
	
	Workbook wb;
	
	//constructor for reading excel path
	
	public ExcelFileUtil(String Excelpath) throws Throwable 
	{
		FileInputStream fi = new FileInputStream(Excelpath);
		wb = WorkbookFactory.create(fi);
	}
	  //count no of rows in a sheet
	   public  int rowCount(String Sheetname)
	   {
		   return
		   wb.getSheet(Sheetname).getLastRowNum();
	   }
	   //method for get cell data
	   public String getcelldata(String sheetname,int row, int column)
	   {
		   String data="";
		   if(wb.getSheet(sheetname).getRow(row).getCell(column).getCellType()==Cell.CELL_TYPE_NUMERIC)
		   {
			   int celldata = (int)
			   wb.getSheet(sheetname).getRow(row).getCell(column).getNumericCellValue();
			   data = String.valueOf(celldata);
		   }
			   
		   else
		   {
			   data = wb.getSheet(sheetname).getRow(row).getCell(column).getStringCellValue();
		   }
			 return data;
	      }
	        //method for set cell
	   public void setcellData(String sheetname,int row,int column,String status,String writeExcel)throws Throwable
	   {
		   //get sheet from wb
		   Sheet ws = wb.getSheet(sheetname);
		   
		   //get row from sheet
		   Row rowNum = ws.getRow(row);
		   
		   //create cell in row
		   Cell cell = rowNum.createCell(column);
		   
		   //write status
		   cell.setCellValue(status);
		   if(status.equalsIgnoreCase("pass"))
		   {
			   CellStyle style = wb.createCellStyle();
			   Font font = wb.createFont();
			   font.setColor(IndexedColors.GREEN.getIndex());
			   font.setBold(true);
			   font.setBoldweight(font.BOLDWEIGHT_BOLD);
			   style.setFont(font);
			   rowNum.getCell(column).setCellStyle(style);
		   }
		   else if(status.equalsIgnoreCase("fail"))
		   {
			   CellStyle style = wb.createCellStyle();
			   Font font = wb.createFont();
			   font.setColor(IndexedColors.RED.getIndex());
			   font.setBold(true);
			   font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			   rowNum.getCell(column).setCellStyle(style);
		   }
		   else if(status.equalsIgnoreCase("Blocked"))
		   {
			 CellStyle style = wb.createCellStyle();
			 Font font = wb.createFont();
			 font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			 style.setFont(font);
			 rowNum.getCell(column).setCellStyle(style);
		   }
		   FileOutputStream fo = new
		   FileOutputStream(writeExcel);  
		   
		   wb.write(fo);
		   
	   }
	   public static void main(String[] args)throws Throwable
	   {
		  ExcelFileUtil xl = new ExcelFileUtil("D:/Sample.xlsx");
		   int rc = xl.rowCount("Empdata");
		   System.out.println(rc);
		   
		   for(int i=1;i<=rc;i++)
		   {
			   String fname = xl.getcelldata("Empdata",i,0);
			   
			   String mname = xl.getcelldata("Empdata",i,1);
			   
			   String lname = xl.getcelldata("Empdata",i,2);
			   
			   String eid = xl.getcelldata("Empdata",i,3);
			   
			   System.out.println(fname+" "+mname+" "+lname+" "+eid);
			   
			   //xl.setcelldata("Empdata",i,4,"pass","D:/Results.xlsx");
			   
			   xl.setcellData("Empdata", i, 4 , "Blocked","D:/Results.xlsx");
			   
		   }
			   
		   }
		   
		   
		   
			   
			   
			   
			   
	   }
	
	
	
	
	
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
