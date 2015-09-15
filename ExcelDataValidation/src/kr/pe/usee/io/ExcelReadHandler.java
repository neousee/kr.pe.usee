package kr.pe.usee.io;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

import kr.pe.usee.excel.ExcelKbfReadable;
import kr.pe.usee.excel.ExcelPogReadable;
import kr.pe.usee.excel.KbfRow;
import kr.pe.usee.excel.PogRow;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;

/**
 * MD들이 정해준 소분류별 KBF 속성 정의
 * 대분류	중분류	소분류	소분류별 세분류 리스트 소분류별 KBF 속성 리스트 속성1	속성2	속성3	속성4
 * @author Administrator
 *
 */
public class ExcelReadHandler extends ExcelHandler implements ExcelKbfReadable,ExcelPogReadable{
	
	XSSFSheet sheet;
	int titleRow;

	Hashtable<String,KbfRow> kbfList = new Hashtable<String,KbfRow>();
	Hashtable<String,PogRow> pogList = new Hashtable<String,PogRow>();
	public ExcelReadHandler(String string) throws IOException {
		super(string);
	}

	/**
	 * 
	 */
	@Override
	public void getKbfData() {
		sheet = workbook.getSheet("List");
		titleRow = sheet.getTopRow();
		Iterator<Row> rows = sheet.iterator();
		Row row;
		KbfRow kbf;
		while(rows.hasNext()) {
			row = rows.next();
			kbf = getKbfRow(row);
			kbfList.put(kbf.getL1()+":"+kbf.getL2()+":"+kbf.getL3(), kbf);
		}		
	}
	
	private KbfRow getKbfRow(Row row) {
		KbfRow kbf;
		if(row!=null) {
			kbf = new KbfRow(row);			
		}
		else kbf = null;
		return kbf;
	}

	@Override
	public void getPogData() {
		// TODO Auto-generated method stub
		sheet = workbook.getSheet("List");
		titleRow = sheet.getTopRow();
		Iterator<Row> rows = sheet.iterator();
		Row row;
		PogRow pog;
		while(rows.hasNext()) {
			row = rows.next();
			pog = getPogRow(row);
			pogList.put(pog.getL1()+":"+pog.getL2()+":"+pog.getL3(),pog);
		}		
		
	}

	private PogRow getPogRow(Row row) {
		PogRow pog;
		if(row!=null) {
			pog = new PogRow(row);			
		}
		else pog = null;
		return pog;
	}

	public XSSFSheet getSheet() {
		return sheet;
	}

	public void setSheet(XSSFSheet sheet) {
		this.sheet = sheet;
	}

	public int getTitleRow() {
		return titleRow;
	}

	public void setTitleRow(int titleRow) {
		this.titleRow = titleRow;
	}

	public Hashtable<String, KbfRow> getKbfList() {
		return kbfList;
	}

	public void setKbfList(Hashtable<String, KbfRow> kbfList) {
		this.kbfList = kbfList;
	}

	public Hashtable<String, PogRow> getPogList() {
		return pogList;
	}

	public void setPogList(Hashtable<String, PogRow> pogList) {
		this.pogList = pogList;
	}
		
	
	
}
