package kr.pe.usee.ls;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TestReader {

	File excel; //= new File("D:\\myworkspace\\LSGS_BU_CG_단위테스트계획서_1차_통합본_v0.4_ext.csv");
	ArrayList<TestSenarioListRow> arr; // = new ArrayList<TestSenarioListRow>();
	MigListDB db; // = new MigListDB();
	public static void main(String[] args) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		// TODO Auto-generated method stub
		TestReader tr = new TestReader();
		tr.read();
	}
	
	public TestReader() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		excel = new File("D:\\myworkspace\\LSGS_CM_1차단위테스트취합_20150707_ext.xlsx");
		arr = new ArrayList<TestSenarioListRow>();
		db = new MigListDB();
		
		
	}
	
	public void read() throws IOException, SQLException {
        FileInputStream fis = new FileInputStream(excel);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);		
        String version = "20150706";
        
        /* List sheet Start */
        int count=0;
        XSSFSheet sheet = workbook.getSheet("내용");
        Iterator<Row> rowIterator = sheet.iterator();
        TestSenarioListRow tempListRow;
        while(rowIterator.hasNext()) 
        {
            Row row = rowIterator.next();
            if(row.getRowNum()>2) 
            {
	            //System.out.print("ROW#:"+row.getRowNum());
	            tempListRow = makeListRowData(row,version);
	            arr.add(tempListRow);
            }
            //System.out.println("");
        }
	        
        insertArray();

	}
	
	public TestSenarioListRow makeListRowData(Row row, String version) {
		TestSenarioListRow listRow = new TestSenarioListRow(row, version);
		//System.out.println(listRow);
		return listRow;
	}

	public void insertArray() throws SQLException {
		for(TestSenarioListRow tslr : arr) {
			insert(tslr);
		}
		db.close();
	}
	
	public void insert(TestSenarioListRow row) throws SQLException {
		db.insert(row);
	}
}
