package kr.pe.usee.ls;

import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 * ListRow는 TobeTable object 하나 AsisTable object 다수개의 멤버, Mapping Row 가지고 있음. Excel Row를 언급한 구조체로 바꾸어 줌.
 * @author Administrator
 *
 */
public class ListRow {
	public static String EMPTY_STRING = "";
	
	TobeTable tobeTable;
	AsisTable[] asisTables;
	ArrayList<MappingRow> mappingRows;
	
	
	public ListRow(Row row, String ver) {
		mappingRows = new ArrayList<MappingRow>();
		asisTables = new AsisTable[0];
		tobeTable = new TobeTable();
		parse(row, ver);
	}
	
	public ListRow(Row row, String ver, String type) {
		mappingRows = new ArrayList<MappingRow>();
		asisTables = new AsisTable[0];
		tobeTable = new TobeTable();
		parse2(row, ver);
	}
	
	public String getKey() {
		return tobeTable.getSystem().trim()+tobeTable.getSchema().trim()+tobeTable.getTable().trim();
	}
	
	private void parse(Row row, String version) {
		AsisTable asisTable = new AsisTable();
		java.util.ArrayList<AsisTable> arrayList = new ArrayList<AsisTable>();

		String ts;
		short minColIx = row.getFirstCellNum();
		short maxColIx  = row.getLastCellNum();

		AsisTable a1 = new AsisTable();
		AsisTable a2 = new AsisTable();
		AsisTable a3 = new AsisTable();
		AsisTable a4 = new AsisTable();
		AsisTable a5 = new AsisTable();
		AsisTable a6 = new AsisTable();
		AsisTable a7 = new AsisTable();
		AsisTable a8 = new AsisTable();

	    for(short colIx=minColIx; colIx<maxColIx; colIx++) 
	    {
	    	ts = ListRow.EMPTY_STRING;
	    	Cell cell = row.getCell(colIx);
	        if(cell != null) 
	        {
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
	        }
	        
	        if( ! ts.equals(EMPTY_STRING) )
	        {
       	
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
		    	case 20 : a1.setSystem(     ts); break;
		    	case 21 : a1.setSchema(     ts); break;
		    	case 22 : a1.setTable(      ts); break;
		    	case 23 : a1.setComment(    ts); break;
		    	case 24 : a2.setSystem(     ts); break;
		    	case 25 : a2.setSchema(     ts); break;
		    	case 26 : a2.setTable(      ts); break;
		    	case 27 : a2.setComment(    ts); break;
		    	case 28 : a3.setSystem(     ts); break;
		    	case 29 : a3.setSchema(     ts); break;
		    	case 30 : a3.setTable(      ts); break;
		    	case 31 : a3.setComment(    ts); break;
		    	case 32 : a4.setSystem(     ts); break;
		    	case 33 : a4.setSchema(     ts); break;
		    	case 34 : a4.setTable(      ts); break;
		    	case 35 : a4.setComment(    ts); break;
		    	case 36 : a5.setSystem(     ts); break;
		    	case 37 : a5.setSchema(     ts); break;
		    	case 38 : a5.setTable(      ts); break;
		    	case 39 : a5.setComment(    ts); break;
		    	case 40 : a6.setSystem(     ts); break;
		    	case 41 : a6.setSchema(     ts); break;
		    	case 42 : a6.setTable(      ts); break;
		    	case 43 : a6.setComment(    ts); break;
		    	case 44 : a7.setSystem(     ts); break;
		    	case 45 : a7.setSchema(     ts); break;
		    	case 46 : a7.setTable(      ts); break;
		    	case 47 : a7.setComment(    ts); break;
		    	case 48 : a8.setSystem(     ts); break;
		    	case 49 : a8.setSchema(     ts); break;
		    	case 50 : a8.setTable(      ts); break;
		    	case 51 : a8.setComment(    ts); break;
		        }
		        tobeTable.setVersion(version);
	        }
		}  //for loop end

	    if(isAsisTable(a1)) arrayList.add(a1);
	    if(isAsisTable(a2)) arrayList.add(a2);
	    if(isAsisTable(a3)) arrayList.add(a3);
	    if(isAsisTable(a4)) arrayList.add(a4);
	    if(isAsisTable(a5)) arrayList.add(a5);
	    if(isAsisTable(a6)) arrayList.add(a6);
	    if(isAsisTable(a7)) arrayList.add(a7);
	    if(isAsisTable(a8)) arrayList.add(a8);
	    asisTables = arrayList.toArray(asisTables);
	}
	
	
	private void parse2(Row row, String version) {
		AsisTable asisTable = new AsisTable();
		java.util.ArrayList<AsisTable> arrayList = new ArrayList<AsisTable>();

		String ts;
		short minColIx = row.getFirstCellNum();
		short maxColIx  = row.getLastCellNum();

		AsisTable a1 = new AsisTable();
		AsisTable a2 = new AsisTable();
		AsisTable a3 = new AsisTable();
		AsisTable a4 = new AsisTable();
		AsisTable a5 = new AsisTable();
		AsisTable a6 = new AsisTable();
		AsisTable a7 = new AsisTable();
		AsisTable a8 = new AsisTable();

	    for(short colIx=minColIx; colIx<maxColIx; colIx++) 
	    {
	    	ts = ListRow.EMPTY_STRING;
	    	Cell cell = row.getCell(colIx);
	        if(cell != null) 
	        {
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
	        }
	        
	        if( ! ts.equals(EMPTY_STRING) )
	        {
       	
		        switch(colIx) 
		        {
		        case 0  : tobeTable.setTable(      ts);                           break;
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
		    	case 20 : a1.setSystem(     ts); break;
		    	case 21 : a1.setSchema(     ts); break;
		    	case 22 : a1.setTable(      ts); break;
		    	case 23 : a1.setComment(    ts); break;
		    	case 24 : a2.setSystem(     ts); break;
		    	case 25 : a2.setSchema(     ts); break;
		    	case 26 : a2.setTable(      ts); break;
		    	case 27 : a2.setComment(    ts); break;
		    	case 28 : a3.setSystem(     ts); break;
		    	case 29 : a3.setSchema(     ts); break;
		    	case 30 : a3.setTable(      ts); break;
		    	case 31 : a3.setComment(    ts); break;
		    	case 32 : a4.setSystem(     ts); break;
		    	case 33 : a4.setSchema(     ts); break;
		    	case 34 : a4.setTable(      ts); break;
		    	case 35 : a4.setComment(    ts); break;
		    	case 36 : a5.setSystem(     ts); break;
		    	case 37 : a5.setSchema(     ts); break;
		    	case 38 : a5.setTable(      ts); break;
		    	case 39 : a5.setComment(    ts); break;
		    	case 40 : a6.setSystem(     ts); break;
		    	case 41 : a6.setSchema(     ts); break;
		    	case 42 : a6.setTable(      ts); break;
		    	case 43 : a6.setComment(    ts); break;
		    	case 44 : a7.setSystem(     ts); break;
		    	case 45 : a7.setSchema(     ts); break;
		    	case 46 : a7.setTable(      ts); break;
		    	case 47 : a7.setComment(    ts); break;
		    	case 48 : a8.setSystem(     ts); break;
		    	case 49 : a8.setSchema(     ts); break;
		    	case 50 : a8.setTable(      ts); break;
		    	case 51 : a8.setComment(    ts); break;
		        }
		        tobeTable.setVersion(version);
	        }
		}  //for loop end

	    if(isAsisTable(a1)) arrayList.add(a1);
	    if(isAsisTable(a2)) arrayList.add(a2);
	    if(isAsisTable(a3)) arrayList.add(a3);
	    if(isAsisTable(a4)) arrayList.add(a4);
	    if(isAsisTable(a5)) arrayList.add(a5);
	    if(isAsisTable(a6)) arrayList.add(a6);
	    if(isAsisTable(a7)) arrayList.add(a7);
	    if(isAsisTable(a8)) arrayList.add(a8);
	    asisTables = arrayList.toArray(asisTables);
	}
	
	public boolean isAsisTable(AsisTable asis) {
		if( asis.getSystem() != null && !asis.getSystem().equals("") && asis.getSchema() != null && !asis.getSchema().equals("") && asis.getTable() != null && !asis.getTable().equals("") )
			return true;
		else 
			return false;
			
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
			sb.append("<ASIS = "+asisTable.getSystem() + ":" +asisTable.getSchema() + ":" +asisTable.getTable() + ">");
		}
		sb.append("]");
		return sb.toString();
	}

	public void addMappingRow(MappingRow row) {
		this.mappingRows.add(row);
	}
	
	public ArrayList<MappingRow> getMappingRows() {
		return mappingRows;
	}
	
	public void setMappingRows(ArrayList<MappingRow> arrayList) {
		this.mappingRows = arrayList;
	}
	
}
