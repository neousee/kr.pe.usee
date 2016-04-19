package kr.pe.usee.excel;

import java.sql.SQLException;
import java.util.ArrayList;

import kr.pe.usee.db.mysql.MysqlDb;

public class BodyRow {
	int size;
	String l3;
	String prodCd;
	String prodNm;
	ArrayList<String> kbfList = new ArrayList<String>();
	public BodyRow(Row row, HeaderRow hRow) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		
		short minColIx = row.getFirstCellNum();
		short maxColIx = row.getLastCellNum();
		size = maxColIx - minColIx;
		for(short colIx=minColIx; colIx<maxColIx; colIx++) {
			String ts = null;
		    XSSFCell cell = (XSSFCell) row.getCell(colIx);
		    if(cell == null) {
		    	continue;
		    } else {
			    switch(cell.getCellType()) {
			        case Cell.CELL_TYPE_BOOLEAN:
			            //System.out.print("[BOOLEAN]"+cell.getBooleanCellValue() + "\t\t");
			            ts = Boolean.toString(cell.getBooleanCellValue());
			            break;
			        case Cell.CELL_TYPE_NUMERIC:
			            //System.out.print("[NUMERIC]"+cell.getNumericCellValue() + "\t\t");
			            ts = Double.toString(cell.getNumericCellValue());
			            break;
			        case Cell.CELL_TYPE_STRING:
			            //System.out.print("[STRING]"+cell.getStringCellValue() + "\t\t" +colIx+ "\t\t" );
			            ts = cell.getStringCellValue();
			            break;
			    }
			    kbfList.add(ts);
	        }

		   //System.out.println(ts);
		}
		String t = null;
		for(int i=0;i<kbfList.size();i++) {
			t = kbfList.get(i);
			switch(i) {
			case 0 : l3 = t; break;
			case 1 : prodCd = t; break;
			case 2 : prodNm = t; break;
			default :
				if( !"".equals(kbfList.get(i)) && kbfList.get(i) != null ) {
					System.out.println(l3+":"+prodCd+":"+prodNm+":"+hRow.getValue((short) i)+":"+t);
					//MysqlDb.insertKbfData(l3,prodCd,prodNm,hRow.getValue((short) i),t);
				}
			}
		}

	}
	}
