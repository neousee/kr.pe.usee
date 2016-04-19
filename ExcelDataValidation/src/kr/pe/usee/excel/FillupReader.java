package kr.pe.usee.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import kr.pe.usee.db.mysql.MysqlDb;

public class FillupReader {
	
	File excelDir;
	File[] fillupFiles;

	MysqlDb db;

	public static void main(String[] args) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, EncryptedDocumentException, InvalidFormatException {
		FillupReader fr = new FillupReader();
		

		for(File file:fr.getFillupFiles()) {
			fr.getKbfValue(file);
		}
		
	}
	
	public FillupReader() throws IOException {
		excelDir = new File("D:\\KBF");
		fillupFiles = excelDir.listFiles();
		db = new MysqlDb();
	}

	private void getKbfValue(File file) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, EncryptedDocumentException, InvalidFormatException {
		System.out.print(file.getName()+":");
		String l1Code = file.getName().substring(0, 3);
        FileInputStream fis = new FileInputStream(file);
        //XSSFWorkbook workbook = new XSSFWorkbook(fis);
        org.apache.poi.ss.usermodel.Workbook workbook = WorkbookFactory.create(file);
        //String version = "20150617";
        int sheetCount = workbook.getNumberOfSheets();
        System.out.println(sheetCount);
        Sheet sheet;
        
        /* List sheet Start */
        String sheetName;
        String l2Code;
        Kbf kbf = null;
        ArrayList<Kbf> arr = new ArrayList<Kbf>();
        OracleDb odb = new OracleDb();
        //odb.connect();
        for(int i=0;i<sheetCount;i++) {
        	sheet = workbook.getSheetAt(i);
        	sheetName = sheet.getSheetName();
        	l2Code = db.getL2Code(l1Code, sheetName);
        	db.getConn();
        	
        	Row row = null;
            Iterator<Row> rowIterator = sheet.iterator();
            while(rowIterator.hasNext()) 
            {
                row = rowIterator.next();
                if(row.getRowNum()>0) 
                {
    	            //System.out.println(row.getFirstCellNum()+":"+row.getLastCellNum());
    	            kbf = new Kbf(db, l1Code, l2Code, row);
    	            arr.add(kbf);
    	            db.insertKbf(kbf);
                }
                
                try {
                if("".equals(kbf.getProdCd()))
                	System.out.println(kbf);

                	//odb.insertCgProdKbf(kbf);
                } catch (NullPointerException npe) {
                	System.out.println(kbf);
                }
                
            }
            db.closeConn();
        }
       // odb.closeConn();
		
	}
	

	public File getExcelDir() {
		return excelDir;
	}

	public void setExcelDir(File excelDir) {
		this.excelDir = excelDir;
	}

	public File[] getFillupFiles() {
		return fillupFiles;
	}

	public void setFillupFiles(File[] fillupFiles) {
		this.fillupFiles = fillupFiles;
	}
}
