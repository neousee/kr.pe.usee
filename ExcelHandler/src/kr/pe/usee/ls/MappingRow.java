package kr.pe.usee.ls;

import java.io.Serializable;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class MappingRow implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static String EMPTY_STRING = "";
	
	TobeColumn tobeColumn;
	AsisColumn[] asisColumns = new AsisColumn[0];
	
	public MappingRow(Row row, String ver) {
		parse(row, ver);
	}
	
	public String getkey() {
		return tobeColumn.getSystem().trim() + tobeColumn.getSchema().trim() + tobeColumn.getTable().trim();
	}
	
	private void parse(Row row, String version) {

		tobeColumn = new TobeColumn();
		AsisColumn asisColumn = new AsisColumn();
		ArrayList<AsisColumn> arrayList = new ArrayList<AsisColumn>();

		AsisColumn as1 = new AsisColumn();
		AsisColumn as2 = new AsisColumn();
		AsisColumn as3 = new AsisColumn();
		AsisColumn as4 = new AsisColumn();
		AsisColumn as5 = new AsisColumn();
		AsisColumn as6 = new AsisColumn();
		AsisColumn as7 = new AsisColumn();
		AsisColumn as8 = new AsisColumn();


		String ts;
		short minColIx = row.getFirstCellNum();
		short maxColIx  = row.getLastCellNum();

	    for(short colIx=minColIx; colIx<maxColIx; colIx++) 
	    {
	    	ts = MappingRow.EMPTY_STRING;
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
	        
	        if( ! EMPTY_STRING.equals(ts) )
	        {
	        	
		        switch(colIx) 
		        {

		    	case 1  : tobeColumn.setSystem(     ts);                           break;
		    	case 2  : tobeColumn.setSchema(     ts);                           break;
		    	case 3  : tobeColumn.setTable(      ts);                           break;
		    	case 4  : tobeColumn.setColumn(     ts);                           break;
		    	case 5  : tobeColumn.setNullable(   ts);                           break;
		    	case 6  : tobeColumn.setType(       ts);                           break;
		    	case 7  : tobeColumn.setComment(    ts);                           break;
		    	case 8  : tobeColumn.setBigo(       ts);                           break;
		    	case 9  : tobeColumn.setComCode(    ts);                           break;

		    	case 10 : as1.setSystem(     ts); break;
		    	case 11 : as1.setSchema(     ts); break;
		    	case 12 : as1.setTable(      ts); break;
		    	case 13 : as1.setColumn(     ts); break;
		    	case 14 : as1.setNullable(   ts); break;
		    	case 15 : as1.setType(       ts); break;
		    	case 16 : as1.setComment(    ts); break;
		    	case 17 : as2.setSystem(     ts); break;
		    	case 18 : as2.setSchema(     ts); break;
		    	case 19 : as2.setTable(      ts); break;
		    	case 20 : as2.setColumn(     ts); break;
		    	case 21 : as2.setNullable(   ts); break;
		    	case 22 : as2.setType(       ts); break;
		    	case 23 : as2.setComment(    ts); break;
		    	case 24 : as3.setSystem(     ts); break;
		    	case 25 : as3.setSchema(     ts); break;
		    	case 26 : as3.setTable(      ts); break;
		    	case 27 : as3.setColumn(     ts); break;
		    	case 28 : as3.setNullable(   ts); break;
		    	case 29 : as3.setType(       ts); break;
		    	case 30 : as3.setComment(    ts); break;
		    	case 31 : as4.setSystem(     ts); break;
		    	case 32 : as4.setSchema(     ts); break;
		    	case 33 : as4.setTable(      ts); break;
		    	case 34 : as4.setColumn(     ts); break;
		    	case 35 : as4.setNullable(   ts); break;
		    	case 36 : as4.setType(       ts); break;
		    	case 37 : as4.setComment(    ts); break;
		    	case 38 : as5.setSystem(     ts); break;
		    	case 39 : as5.setSchema(     ts); break;
		    	case 40 : as5.setTable(      ts); break;
		    	case 41 : as5.setColumn(     ts); break;
		    	case 42 : as5.setNullable(   ts); break;
		    	case 43 : as5.setType(       ts); break;
		    	case 44 : as5.setComment(    ts); break;
		    	case 45 : as6.setSystem(     ts); break;
		    	case 46 : as6.setSchema(     ts); break;
		    	case 47 : as6.setTable(      ts); break;
		    	case 48 : as6.setColumn(     ts); break;
		    	case 49 : as6.setNullable(   ts); break;
		    	case 50 : as6.setType(       ts); break;
		    	case 51 : as6.setComment(    ts); break;
		    	case 52 : as7.setSystem(     ts); break;
		    	case 53 : as7.setSchema(     ts); break;
		    	case 54 : as7.setTable(      ts); break;
		    	case 55 : as7.setColumn(     ts); break;
		    	case 56 : as7.setNullable(   ts); break;
		    	case 57 : as7.setType(       ts); break;
		    	case 58 : as7.setComment(    ts); break;
		    	case 59 : as8.setSystem(     ts); break;
		    	case 60 : as8.setSchema(     ts); break;
		    	case 61 : as8.setTable(      ts); break;
		    	case 62 : as8.setColumn(     ts); break;
		    	case 63 : as8.setNullable(   ts); break;
		    	case 64 : as8.setType(       ts); break;
		    	case 65 : as8.setComment(    ts); break;

		        }
	        }
		}  //for loop end
        tobeColumn.setVersion(version);

        if( ! as1.isEmpty() ) arrayList.add(as1); 
		if( ! as2.isEmpty() ) arrayList.add(as2); 
		if( ! as3.isEmpty() ) arrayList.add(as3); 
		if( ! as4.isEmpty() ) arrayList.add(as4); 
		if( ! as5.isEmpty() ) arrayList.add(as5); 
		if( ! as6.isEmpty() ) arrayList.add(as6); 
		if( ! as7.isEmpty() ) arrayList.add(as7); 
		if( ! as8.isEmpty() ) arrayList.add(as8); 

	    asisColumns = arrayList.toArray(asisColumns);
	}
	
	public TobeColumn getTobeCol() {
		return tobeColumn;
	}
	
	public AsisColumn[] getAsisCols() {
		return asisColumns;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(tobeColumn.toString());
		for(AsisColumn asisCol:asisColumns) {
			sb.append(asisCol.toString());
		}
		return sb.toString();
	}

}
