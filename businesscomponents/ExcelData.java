package businesscomponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelData {
	static String path;
	static String relPath;
	public static void main(String[] args) throws IOException,InterruptedException {
		// TODO Auto-generated method stub
		/*createSheet();
		report("Test case Name1","Description","Fail");
		report("Test case Name2","Description","Done");
		report("Test case Name2","Description","Done");
		statusSheet();*/
	    
	}
		
	static void createSheet() throws IOException
	{
		relPath = new File("").getAbsolutePath();
		String filePath = "D:\\";
		String filePath_1 = relPath + "\\Run_results";
	    String Workbook = "Detailed_Summary.xlsx";
	    //  String Sheetname1 = "Sheet2";
	    path = "Run_" + new SimpleDateFormat("yyyy_MM_dd_HHmmss").format(Calendar.getInstance().getTime()).toString();
	    //File file_run = new File("D:\\Runresults");
	    File file_run = new File(filePath_1);
	    if (!file_run.exists())
	    {
	    	file_run.mkdir();
	    }
	    File file1 = new File(filePath_1 +"\\" + path);
	    file1.mkdir();
	    File  file = new File(filePath_1+ "\\" +  path +"\\"+Workbook);
	    XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Detailed_Summary");
    	Row newRow = sheet.createRow(0);
		Cell cell = newRow.createCell(0);
		Cell cell1 = newRow.createCell(1);
		Cell cell2 = newRow.createCell(2);
		cell.setCellValue("XML Name");
		cell1.setCellValue("Result Description");
		cell2.setCellValue("Status");
        FileOutputStream outputStream = new FileOutputStream(file); 
        workbook.write(outputStream);
        outputStream.close();
               
		
	}
	
	static void report(String XMLName,String Description,String status) throws IOException
		{
			//System.out.println(path);
			//File  file = new File("D:\\Runresults\\" + path + "\\Detailed_Summary.xlsx");
			File file_1 = new File(relPath+ "\\Run_results\\" + path + "\\Detailed_Summary.xlsx");
			FileInputStream inputStream = new FileInputStream(file_1);
			Workbook myworkbookobj = new XSSFWorkbook(inputStream);
			Sheet mysheetobj = myworkbookobj.getSheet("Detailed_Summary");
			int rowCount = mysheetobj.getLastRowNum()-mysheetobj.getFirstRowNum();
			Row newRow = mysheetobj.createRow(rowCount+1);
			Cell cell = newRow.createCell(0);
			Cell cell1 = newRow.createCell(1);
			Cell cell2 = newRow.createCell(2);
			cell.setCellValue(XMLName);
			cell1.setCellValue(Description);
			cell2.setCellValue(status);
			//System.out.println("Entered report" + rowCount);
			FileOutputStream outputStream1 = new FileOutputStream(file_1);
		    myworkbookobj.write(outputStream1);
			//myworkbookobj = new XSSFWorkbook(new FileInputStream(file));
		    outputStream1.close();
		    inputStream.close();
		  }
	static void statusSheet() throws IOException
		{
			//String filePath = "D:\\";
		    String Workbook = "Overall_Status.xlsx";
		    Cell cell_TC,cell_desc,cell_status;
		    ArrayList<String> tc = new ArrayList<String>();
		    String x_testcase,y2_status;
		  //  String Sheetname1 = "Sheet2";
		    String filePath_1 = relPath + "\\Run_results";
		    File file1 = new File(filePath_1 + "\\" + path);
		    //file1.mkdir();
		    File  file = new File(filePath_1 +"\\" + path +"\\"+Workbook);
			XSSFWorkbook workbook = new XSSFWorkbook();
	        XSSFSheet sheet = workbook.createSheet("Overall_Status");
	    	Row newRow = sheet.createRow(0);
			Cell cell = newRow.createCell(0);
			Cell cell1 = newRow.createCell(1);
			Cell cell2 = newRow.createCell(2);
			cell.setCellValue("XML Name");
			cell1.setCellValue("Status Description");
			cell2.setCellValue("Status");
	        //FileOutputStream outputStream = new FileOutputStream(file); 
	        //workbook.write(outputStream);
	        //outputStream.close();
	        int rowCount_status = sheet.getLastRowNum()-sheet.getFirstRowNum();
	        //File  file2 = new File("D:\\Runresults\\" + path + "\\Detailed_Summary.xlsx");
	        File  file2 = new File(filePath_1 + "\\" + path + "\\Detailed_Summary.xlsx");
			FileInputStream inputStream = new FileInputStream(file2);
			Workbook myworkbookobj = new XSSFWorkbook(inputStream);
			Sheet mysheetobj1 = myworkbookobj.getSheet("Detailed_Summary");
			int rowCount1 = mysheetobj1.getLastRowNum()-mysheetobj1.getFirstRowNum();
		
			for (int i = 1; i < rowCount1+1; i++) {
				Row Status_row = sheet.createRow(rowCount_status+1);
				Row row = mysheetobj1.getRow(i);
				Row row1 = mysheetobj1.getRow(i+1);
				x_testcase = row.getCell(0).getStringCellValue();
				y2_status = row.getCell(2).getStringCellValue();
				//ar.add(y2);
				if(y2_status.equalsIgnoreCase("Files Not Found"))
				{
					//System.out.println("Files Not found" +x_testcase + "Files Not Found");
					cell_TC = Status_row.createCell(0);
					cell_desc = Status_row.createCell(1);
					cell_status = Status_row.createCell(2);
					cell_TC.setCellValue(x_testcase);
					cell_desc.setCellValue("Files not Found in Destination Folder");
					cell_status.setCellValue(y2_status);
					rowCount_status++;
				}
				else if(y2_status.equalsIgnoreCase("Similar"))
				{
					cell_TC = Status_row.createCell(0);
					cell_desc = Status_row.createCell(1);
					cell_status = Status_row.createCell(2);
					cell_TC.setCellValue(x_testcase);
					cell_desc.setCellValue("Two XMLs are Similar");
					cell_status.setCellValue(y2_status);
					rowCount_status++;
				}
				else if(y2_status.equalsIgnoreCase("PASS"))
				{
					System.out.println("Strings are Pass" +x_testcase);
					//write to excel
					cell_TC = Status_row.createCell(0);
					cell_desc = Status_row.createCell(1);
					cell_status = Status_row.createCell(2);
					cell_TC.setCellValue(x_testcase);
					cell_desc.setCellValue("XML's are Identical");
					cell_status.setCellValue(y2_status);
					rowCount_status++;
				}
				else if ((y2_status.equalsIgnoreCase("Fail"))&& (!tc.contains(x_testcase) ))

				{	
					tc.add(x_testcase);
					//write to excel
					System.out.println("Strings are failing" +x_testcase + "Fail");
					cell_TC = Status_row.createCell(0);
					cell_desc = Status_row.createCell(1);
					cell_status = Status_row.createCell(2);
					cell_TC.setCellValue(x_testcase);
					cell_desc.setCellValue("XML's are Different");
					cell_status.setCellValue(y2_status);
					rowCount_status++;
				}
				else
				{
					if(i!= rowCount1) 
					{
						if (!tc.contains(x_testcase)&& (!x_testcase.equals(row1.getCell(0).getStringCellValue())))
						{
							    //done.add(x_testcase);
								//done.add(y2_status);
								//&&(!done.contains(x_testcase))
								//write to excel
								System.out.println("Strings are done" +x_testcase);
								cell_TC = Status_row.createCell(0);
								cell_desc = Status_row.createCell(1);
								cell_status = Status_row.createCell(2);
								cell_TC.setCellValue(x_testcase);
								cell_desc.setCellValue("XML's are Similar");
								cell_status.setCellValue(y2_status);
								rowCount_status++;
							
						}
					}
					else
					{
						if (!tc.contains(x_testcase))
						{
							System.out.println("Strings are done" +x_testcase);
							cell_TC = Status_row.createCell(0);
							cell_desc = Status_row.createCell(1);
							cell_status = Status_row.createCell(2);
							cell_TC.setCellValue(x_testcase);
							cell_desc.setCellValue("XML's are Similar");
							cell_status.setCellValue(y2_status);
							rowCount_status++;
						}
					}
					}
				
			}
			
			FileOutputStream outputStream_status = new FileOutputStream(file); 
	        workbook.write(outputStream_status);
	        outputStream_status.close();
			}
		}

