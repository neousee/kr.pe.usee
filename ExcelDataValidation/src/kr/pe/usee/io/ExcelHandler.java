package kr.pe.usee.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ExcelHandler extends FileHandler {
	
	XSSFWorkbook workbook;
	public ExcelHandler(String string) throws IOException {
		file = new File(string);
        FileInputStream fis = new FileInputStream(file);
        workbook = new XSSFWorkbook(fis);		

	}
	
	public ExcelHandler(String dir, String string) throws IOException {
		file = new File(dir, string);
        FileInputStream fis = new FileInputStream(file);
        workbook = new XSSFWorkbook(fis);		

	}

	public XSSFSheet getSheetData(String sheetName) {
		return workbook.getSheet(sheetName);		
	}
		
	public void setWorkbook(XSSFWorkbook workbook) {
		this.workbook = workbook;
		
	}
	
	public XSSFWorkbook getWorkbook() {
		return this.workbook;
	}

}
