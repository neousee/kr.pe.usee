package kr.pe.usee.ls;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class MigListReader {
    static int colrange=1000;
    XSSFWorkbook workbook;
    Hashtable<String, ListRow> list = new Hashtable<String, ListRow>(); 
    MappingRow[] colList = new MappingRow[0];
    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
    	MigListReader reader = new MigListReader();
    	//reader.setSource(new File(args[0]));
    	reader.setSource(new File("D:\\myworkspace\\All_20150617.xlsx"),args);
    	//SqlMaker maker = new SqlMaker();
    }
    
	/**
	 * @param file
	 * @throws IOException
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public void setSource(File file, String[] args) throws IOException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        FileInputStream fis = new FileInputStream(file);
        workbook = new XSSFWorkbook(fis);		
        String version = "20150617";
        
        /* List sheet Start */
        int count=0;
	        XSSFSheet sheet = workbook.getSheet("List");
	        Iterator<Row> rowIterator = sheet.iterator();
	        ListRow tempListRow;
	        while(rowIterator.hasNext()) 
	        {
	            Row row = rowIterator.next();
	            if(row.getRowNum()>2) 
	            {
		            //System.out.print("ROW#:"+row.getRowNum());
		            tempListRow = makeListRowData(row,version);
		            list.put(tempListRow.getTobeTable().getTable(), tempListRow);
	            }
	            //System.out.println("");
	        }
	        
	        for(Row r:sheet)
	        {
	            short minColIx=r.getFirstCellNum();
	            short maxColIx=r.getLastCellNum();
	            for(short colIx=minColIx;colIx<maxColIx;colIx++) {
	                Cell c= r.getCell(colIx);
	                if(c!=null) {
	                    if(c.getCellType()== Cell.CELL_TYPE_STRING||c.getCellType() == Cell.CELL_TYPE_NUMERIC||c.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
	                        count++; // can i use hashcode in here to get the key and value pair? key=column index value=total number of rows in that column
	                            } 
	                    }
	                    else break;
	                }
            }
	        /* List Sheet end */
	        /*
	    MysqlDb db = new MysqlDb(args[0], args[1], args[2]);
        db.getConn();
        db.upsert(list);
        */

        /* mapping sheet Start */
        count=0;
	        sheet = workbook.getSheet("매핑정의서");
	        rowIterator = sheet.iterator();
	        ArrayList<MappingRow> al2 = new ArrayList<MappingRow>();
	        while(rowIterator.hasNext()) 
	        {
	            Row row = rowIterator.next();
	            if(row.getRowNum()>1) 
	            {
		            //System.out.print("ROW#:"+row.getRowNum());
		            al2.add(setMappingRowData(row));
	            }
	            //System.out.println("");
	        }
	        colList = al2.toArray(colList);
	        for(Row r:sheet)
	        {
	            short minColIx=r.getFirstCellNum();
	            short maxColIx=r.getLastCellNum();
	            for(short colIx=minColIx;colIx<maxColIx;colIx++) {
	                Cell c= r.getCell(colIx);
	                if(c!=null) {
	                    if(c.getCellType()== Cell.CELL_TYPE_STRING||c.getCellType() == Cell.CELL_TYPE_NUMERIC||c.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
	                        count++; // can i use hashcode in here to get the key and value pair? key=column index value=total number of rows in that column
	                            } 
	                    }
	                    else break;
	                }
            }
	        /* Mapping Sheet end */
	        
	        /* connect mapping and tobe table */
	        String tTable = null;
	        
	        for(MappingRow mappingRow:colList) {
	        	try {
	        	tTable = mappingRow.getTobeCol().getTable();
	        	}catch(NullPointerException npe) {}
	        	if(tTable != null && list.containsKey(tTable)) {
	        		list.get(mappingRow.getTobeCol().getTable()).addMappingRow(mappingRow);
	        	} else {
	        		System.out.println("Not Contained : "+colList.toString());
	        	}
	        }
	    System.out.println("\nTotal Number of columns are:\t"+count);
	    //System.out.println(hm);
	    fis.close();
	}
	
	public ListRow makeListRowData(Row row,String version) {
		ListRow listRow = new ListRow(row, version);
		//System.out.println(listRow);
		return listRow;
	}

	private ListRow makeListRowData(Row row) {
		ListRow listRow = new ListRow(row, "20150603");
		//System.out.println(listRow);
		return listRow;
	}
	

	private MappingRow setMappingRowData(Row row) {
		MappingRow listRow = new MappingRow(row, "20150603");
		//System.out.println(listRow);
		return listRow;
	}

	public ListRow[] getList() {
		Collection<ListRow> col = list.values();
		ListRow[] a = new ListRow[col.size()];
		return col.toArray(a);
	}
	
	public MappingRow[] getColumnList() {
		return colList;
	}
}