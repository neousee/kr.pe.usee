package kr.pe.usee.ls;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelHandler {

	File excelFile;
	ArrayList<ListRow> arr;
	public ExcelHandler() {
		excelFile = new File("");
		arr = new ArrayList<ListRow>();
	}
	
	public ExcelHandler(String filePath) {
		excelFile = new File(filePath);
		arr = new ArrayList<ListRow>();		
	}
	
	public void setFile(String filePath) {
		excelFile = new File(filePath);
	}	
	
	public void read() throws IOException {
        FileInputStream fis = new FileInputStream(excelFile);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);		
        //String version = "20150617";
        
        /* List sheet Start */
        int count=0;
        readListSheet(workbook,"20150701");
        Hashtable<String, ArrayList<MappingRow>> ht = readMappingSheet(workbook,"20150701");
        for(ListRow listRow:arr) {
        	if(ht.containsKey(listRow.getKey())) {
        		listRow.setMappingRows(ht.get(listRow.getKey()));
        	}
        }
        
        
	}
	
	public void read2() throws IOException {
        FileInputStream fis = new FileInputStream(excelFile);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);		
        //String version = "20150617";
        
        /* List sheet Start */
        int count=0;
        readListSheet2(workbook,"20150701");
        Hashtable<String, ArrayList<MappingRow>> ht = readMappingSheet(workbook,"20150701");
        for(ListRow listRow:arr) {
        	if(ht.containsKey(listRow.getKey())) {
        		listRow.setMappingRows(ht.get(listRow.getKey()));
        	}
        }
        
		
	}

	private void readListSheet(XSSFWorkbook workbook, String version) {
		XSSFSheet sheet = workbook.getSheet("List");
        Iterator<Row> rowIterator = sheet.iterator();
        ListRow listRow;
        while(rowIterator.hasNext()) 
        {
            Row row = rowIterator.next();
            if(row.getRowNum()>2) 
            {
	            //System.out.print("ROW#:"+row.getRowNum());
	            listRow = new ListRow(row, version);
	            arr.add(listRow);
            }
            //System.out.println("");
        }
	}
	

	private void readListSheet2(XSSFWorkbook workbook, String version) {
		XSSFSheet sheet = workbook.getSheet("INDEX");
        Iterator<Row> rowIterator = sheet.iterator();
        ListRow listRow;
        while(rowIterator.hasNext()) 
        {
            Row row = rowIterator.next();
            if(row.getRowNum()>2) 
            {
	            //System.out.print("ROW#:"+row.getRowNum());
	            listRow = new ListRow(row, version,"");
	            arr.add(listRow);
            }
            //System.out.println("");
        }
	}
	
	
	
	private Hashtable<String, ArrayList<MappingRow>> readMappingSheet(XSSFWorkbook workbook, String version) {
		XSSFSheet sheet = workbook.getSheet("매핑정의서");
        Iterator<Row> rowIterator = sheet.iterator();
        Hashtable<String,ArrayList<MappingRow>> mapHash = new Hashtable<String, ArrayList<MappingRow>>();
        ArrayList<MappingRow> tempMapping = new ArrayList<MappingRow>();
        MappingRow mappingRow;
        while(rowIterator.hasNext()) 
        {
            Row row = rowIterator.next();
            if(row.getRowNum()>1) 
            {
	            //System.out.print("ROW#:"+row.getRowNum());
	            mappingRow = new MappingRow(row, version);
	            tempMapping.add(mappingRow);
	            if(mapHash.containsKey(mappingRow.getkey())) {
	            	mapHash.get(mappingRow.getkey()).add(mappingRow);
	            } else {
	            	ArrayList<MappingRow> arr = new ArrayList<MappingRow>();
	            	arr.add(mappingRow);
	            	mapHash.put(mappingRow.getkey(), arr);
	            }
	            
            }
            //System.out.println("");
        }
        
        return mapHash;
	}

	public ArrayList<ListRow> getListRow() {
		return arr;
	}
}
