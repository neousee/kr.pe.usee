package kr.pe.usee.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import kr.pe.usee.db.mysql.MysqlDb;
import kr.pe.usee.io.DirReader;

public class KbfAttrReader {

	DirReader fr;
	public static void main(String[] args) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		KbfAttrReader kbfReader = new KbfAttrReader();
		kbfReader.read();
	}

	/**
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 * 
	 */
	public void read() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		File[] files = DirReader.getFileList("D:\\new");	
		System.out.println("Files : "+files.length);
		for(int i=0;i<files.length;i++) {	
			System.out.println("Files : "+files[i]);
			readExcel(files[i]);
		}
	}

	public void readExcel(File file) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		// TODO Auto-generated method stub
        FileInputStream fis = new FileInputStream(file);
        XSSFWorkbook workbook;
        workbook = new XSSFWorkbook(fis);
        for(XSSFSheet sheet:workbook) {
        	if(!"°ÑÇ¥Áö".equals(sheet.getSheetName()) && !"Guide".equals(sheet.getSheetName()) ) {
        		System.out.println("SHEET : "+sheet.getSheetName());
        		System.out.println("================================");
        		parseSheet(sheet);
        		System.out.println("================================");
        	}
        }
	}

	private void parseSheet(XSSFSheet sheet) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		//readFirstline(sheet);
		ArrayList<BodyRow> bodyRowList = readOther(readFirstline(sheet),sheet);
		insert(bodyRowList);
	}

	private ArrayList<BodyRow> readOther(HeaderRow readFirstline,XSSFSheet sheet) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		Iterator<Row> it = sheet.rowIterator();
		HeaderRow hRow = null;
		ArrayList<BodyRow> bodyRowList = new ArrayList<BodyRow>();
		int i=0;
		while(it.hasNext()) {
			//System.out.println("+++++++"+i);
			if(i++==0) {
				hRow = new HeaderRow(it.next());
			} else {
				bodyRowList.add(new BodyRow(it.next(),hRow));
			}
		}
		return bodyRowList;
	}
	
	private void insert(ArrayList<BodyRow> bodyRowList) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		MysqlDb db = new MysqlDb();
		db.getConn();
		for(BodyRow bodyRow : bodyRowList) {
			//bodyRow.getKbfList();
		}
	}

	private HeaderRow readFirstline(XSSFSheet sheet) {
		return new HeaderRow(sheet.getRow(0));
		
	}

}
