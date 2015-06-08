package kr.pe.usee.ls;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class MigListReader {
    static int colrange=1000;
    XSSFWorkbook workbook;
    ListRow[] list = new ListRow[0]; 
    
    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
    	MigListReader reader = new MigListReader();
    	//reader.setSource(new File(args[0]));
    	reader.setSource(new File("D:\\myworkspace\\All_20150603.xlsx"),args);
    }
    
	/**
	 * @param file
	 * @throws IOException
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	private void setSource(File file, String[] args) throws IOException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        FileInputStream fis = new FileInputStream(file);
        workbook = new XSSFWorkbook(fis);		

        HashMap hm=new HashMap();
        int count=0;
	        XSSFSheet sheet = workbook.getSheet("List");
	        Iterator<Row> rowIterator = sheet.iterator();
	        ArrayList<ListRow> al = new ArrayList<ListRow>();
	        while(rowIterator.hasNext()) 
	        {
	            Row row = rowIterator.next();
	            if(row.getRowNum()>2) 
	            {
		            System.out.print("ROW#:"+row.getRowNum());
		            al.add(setRowData(row));
	            }
	            //System.out.println("");
	        }
	        list = al.toArray(list);
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
	        
	        
	        

        System.out.println("\nTotal Number of columns are:\t"+count);
        System.out.println(hm);
        fis.close();
        
        MysqlDb db = new MysqlDb(args[0], args[1], args[2]);
        db.getConn();
        db.upsert(list);
	}

	private ListRow setRowData(Row row) {
		ListRow listRow = new ListRow(row, "20150603");
		System.out.println(listRow);
		return listRow;
	}
	
}