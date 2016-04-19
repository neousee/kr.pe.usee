package kr.pe.usee.excel;

import java.util.ArrayList;

public class HeaderRow {
	ArrayList<String> hName = new ArrayList<String>();
	public HeaderRow(Row row) {
		
		short minColIx = row.getFirstCellNum();
		short maxColIx = row.getLastCellNum();
		for(short colIx=minColIx; colIx<maxColIx; colIx++) {
		   XSSFCell cell = (XSSFCell) row.getCell(colIx);
		   if(cell == null) {
		     continue;
		   }
		   hName.add(cell.getStringCellValue());
		   System.out.println(cell.getStringCellValue());
		}

	}
	
	public String getValue(short ind) {
		return hName.get(ind);
	}

}
