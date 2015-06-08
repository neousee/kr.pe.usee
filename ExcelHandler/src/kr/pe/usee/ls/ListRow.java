package kr.pe.usee.ls;

import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class ListRow {
	public static String EMPTY_STRING = "";
	
	TobeTable tobeTable;
	AsisTable[] asisTables = new AsisTable[0];
	
	public ListRow(Row row, String ver) {
		parse(row, ver);
	}
	
	private void parse(Row row, String version) {

		tobeTable = new TobeTable();
		AsisTable asisTable = new AsisTable();
		java.util.ArrayList<AsisTable> arrayList = new ArrayList<AsisTable>();

		String ts;
		short minColIx = row.getFirstCellNum();
		short maxColIx  = row.getLastCellNum();

	    for(short colIx=minColIx; colIx<maxColIx; colIx++) 
	    {
	    	ts = ListRow.EMPTY_STRING;
	    	Cell cell = row.getCell(colIx);
	        if(cell != null) 
	        {
			    switch(cell.getCellType()) {
			        case Cell.CELL_TYPE_BOOLEAN:
			            System.out.print("[BOOLEAN]"+cell.getBooleanCellValue() + "\t\t");
			            ts = Boolean.toString(cell.getBooleanCellValue());
			            break;
			        case Cell.CELL_TYPE_NUMERIC:
			            System.out.print("[NUMERIC]"+cell.getNumericCellValue() + "\t\t");
			            ts = Double.toString(cell.getNumericCellValue());
			            break;
			        case Cell.CELL_TYPE_STRING:
			            System.out.print("[STRING]"+cell.getStringCellValue() + "\t\t" +colIx+ "\t\t" );
			            ts = cell.getStringCellValue();
			            break;
			    }
	        }
	        
	        if( ! ts.equals(EMPTY_STRING) )
	        {
	        	if( colIx == 20 ) { asisTable = new AsisTable(); }
	        	if( colIx == 24 ) { asisTable = new AsisTable(); }
	        	if( colIx == 28 ) { asisTable = new AsisTable(); }
	        	if( colIx == 32 ) { asisTable = new AsisTable(); }
	        	if( colIx == 36 ) { asisTable = new AsisTable(); }
	        	if( colIx == 40 ) { asisTable= new AsisTable(); }
	        	if( colIx == 44 ) { asisTable = new AsisTable(); }
	        	if( colIx == 48 ) { asisTable = new AsisTable(); }
	        	
		        switch(colIx) 
		        {
		    	case 1  : tobeTable.setSystem(     ts);                           break;
		    	case 2  : tobeTable.setSchema(     ts);                           break;
		    	case 3  : tobeTable.setTable(      ts);                           break;
		    	case 4  : tobeTable.setApCode(     ts);                           break;
		    	case 5  : tobeTable.setComment(    ts);                           break;
		    	case 6  : tobeTable.setRelation(   ts);                           break;
		    	case 7  : tobeTable.setCdVsTr(     ts);                           break;
		    	case 8  : tobeTable.setMigRange(   ts);                           break;
		    	case 9  : tobeTable.setIncCnt(     ts);                           break;
		    	case 10 : tobeTable.setIncUnit(    ts);                           break;
		    	case 11 : tobeTable.setBizNonCmplt(ts);                           break;
		    	case 12 : tobeTable.setCleanYn(    ts);                           break;
		    	case 13 : tobeTable.setCleanDueDt( ts);                           break;
		    	case 14 : tobeTable.setCleanOwner( ts);                           break;
		    	case 15 : tobeTable.setMapDefDueDt(ts);                           break;
		    	case 16 : tobeTable.setFillupOwner(ts);                           break;
		    	case 17 : tobeTable.setFillupDueDt(ts);                           break;
		    	case 18 : tobeTable.setMigSqlDueDt(ts);                           break;
		    	case 19 : tobeTable.setMigWay(     ts);                           break;
		    	case 20 : asisTable.setSystem(     ts);                           break;
		    	case 21 : asisTable.setSchema(     ts);                           break;
		    	case 22 : asisTable.setTable(      ts);                           break;
		    	case 23 : asisTable.setComment(    ts); arrayList.add(asisTable); break;
		    	case 24 : asisTable.setSystem(     ts);                           break;
		    	case 25 : asisTable.setSchema(     ts);                           break;
		    	case 26 : asisTable.setTable(      ts);                           break;
		    	case 27 : asisTable.setComment(    ts); arrayList.add(asisTable); break;
		    	case 28 : asisTable.setSystem(     ts);                           break;
		    	case 29 : asisTable.setSchema(     ts);                           break;
		    	case 30 : asisTable.setTable(      ts);                           break;
		    	case 31 : asisTable.setComment(    ts); arrayList.add(asisTable); break;
		    	case 32 : asisTable.setSystem(     ts);                           break;
		    	case 33 : asisTable.setSchema(     ts);                           break;
		    	case 34 : asisTable.setTable(      ts);                           break;
		    	case 35 : asisTable.setComment(    ts); arrayList.add(asisTable); break;
		    	case 36 : asisTable.setSystem(     ts);                           break;
		    	case 37 : asisTable.setSchema(     ts);                           break;
		    	case 38 : asisTable.setTable(      ts);                           break;
		    	case 39 : asisTable.setComment(    ts); arrayList.add(asisTable); break;
		    	case 40 : asisTable.setSystem(     ts);                           break;
		    	case 41 : asisTable.setSchema(     ts);                           break;
		    	case 42 : asisTable.setTable(      ts);                           break;
		    	case 43 : asisTable.setComment(    ts); arrayList.add(asisTable); break;
		    	case 44 : asisTable.setSystem(     ts);                           break;
		    	case 45 : asisTable.setSchema(     ts);                           break;
		    	case 46 : asisTable.setTable(      ts);                           break;
		    	case 47 : asisTable.setComment(    ts); arrayList.add(asisTable); break;
		    	case 48 : asisTable.setSystem(     ts);                           break;
		    	case 49 : asisTable.setSchema(     ts);                           break;
		    	case 50 : asisTable.setTable(      ts);                           break;
		    	case 51 : asisTable.setComment(    ts); arrayList.add(asisTable); break;
		        }
		        tobeTable.setVersion(version);
	        }
		}  //for loop end
	    
	    asisTables = arrayList.toArray(asisTables);
	}

	public TobeTable getTobeTable() {
		return tobeTable;
	}

	public void setTobeTable(TobeTable tobeTable) {
		this.tobeTable = tobeTable;
	}

	public AsisTable[] getAsisTables() {
		return asisTables;
	}

	public void setAsisTables(AsisTable[] asisTables) {
		this.asisTables = asisTables;
	}
	
	public Integer getCountOfAsisTables() {
		return new Integer(asisTables.length);
		
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[TOBE = "+tobeTable.getSystem() + ":" +tobeTable.getSchema() + ":" +tobeTable.getTable() + " , ");
		for(AsisTable asisTable : asisTables) {
			sb.append("[ASIS = "+asisTable.getSystem() + ":" +asisTable.getSchema() + ":" +asisTable.getTable() + "]");
		}
		sb.append("]");
		return sb.toString();
	}
	
}
