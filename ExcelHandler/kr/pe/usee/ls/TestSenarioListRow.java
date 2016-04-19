package kr.pe.usee.ls;

import java.text.SimpleDateFormat;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class TestSenarioListRow {

	String sid                 ;
	String sname               ;
	String item                ;
	String senario             ;
	String exp_result          ;
	String designer            ;
	String fst_tester          ;
	String fst_test_dt         ;
	String fst_test_result     ;
	String fst_test_err_grade  ;
	String fst_test_err        ;
	String fst_test_comment    ;
	String fst_test_fix_dt     ;
	String fst_test_fixer      ;
	String fst_test_fix_item   ;
	String fst_test_fix_comment; 
	String scd_tester          ;
	String scd_test_dt         ;
	String scd_test_result     ;
	String scd_test_err_grade  ;
	String scd_test_err        ;
	String scd_test_comment    ;
	String scd_test_fix_dt     ;
	String scd_test_fixer      ;
	String scd_test_fix_item   ;
	String scd_test_fix_comment; 
	String version             ;
	
	public TestSenarioListRow(Row row, String ver) {
		parse(row, ver);
	}

	public void parse(Row row, String version) {

		String ts;
		short minColIx = row.getFirstCellNum();
		short maxColIx  = row.getLastCellNum();

	    for(short colIx=minColIx; colIx<maxColIx; colIx++) 
	    {
	    	ts = ListRow.EMPTY_STRING;
	    	Cell cell = row.getCell(colIx);
	    	String data;
	        if(cell != null) 
	        {
			    switch(cell.getCellType()) {
			        case Cell.CELL_TYPE_BOOLEAN:
			            //System.out.print("[BOOLEAN]"+cell.getBooleanCellValue() + "\t\t");
			            ts = Boolean.toString(cell.getBooleanCellValue());
			            break;
			        case Cell.CELL_TYPE_NUMERIC:
			        	 if (HSSFDateUtil.isCellDateFormatted(cell)){
			                 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
			                 ts = formatter.format(cell.getDateCellValue());
			            } else{
			            	ts = Double.toString(cell.getNumericCellValue());
			            }
			            //System.out.print("[NUMERIC]"+cell.getNumericCellValue() + "\t\t");
			            
			            break;
			        case Cell.CELL_TYPE_STRING:
			            //System.out.print("[STRING]"+cell.getStringCellValue() + "\t\t" +colIx+ "\t\t" );
			            ts = cell.getStringCellValue();
			            break;
			    }
	        }
	        
	        switch(colIx+1) {
	        case 1  : sid                  = ts; 
	        case 2  : sname                = ts; 
	        case 3  : item                 = ts; 
	        case 4  : senario              = ts; 
	        case 5  : exp_result           = ts; 
	        case 6  : designer             = ts; 
	        case 7  : fst_tester           = ts; 
	        case 8  : fst_test_dt          = ts; 
	        case 9  : fst_test_result      = ts; 
	        case 10 : fst_test_err_grade   = ts; 
	        case 11 : fst_test_err         = ts; 
	        case 12 : fst_test_comment     = ts; 
	        case 13 : fst_test_fix_dt      = ts; 
	        case 14 : fst_test_fixer       = ts; 
	        case 15 : fst_test_fix_item    = ts; 
	        case 16 : fst_test_fix_comment = ts; 
	        case 17 : scd_tester           = ts; 
	        case 18 : scd_test_dt          = ts; 
	        case 19 : scd_test_result      = ts; 
	        case 20 : scd_test_err_grade   = ts; 
	        case 21 : scd_test_err         = ts; 
	        case 22 : scd_test_comment     = ts; 
	        case 23 : scd_test_fix_dt      = ts; 
	        case 24 : scd_test_fixer       = ts; 
	        case 25 : scd_test_fix_item    = ts; 
	        case 26 : scd_test_fix_comment = ts; 
	        default : version = ts;
	        }
	        
	    }

	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getSenario() {
		return senario;
	}

	public void setSenario(String senario) {
		this.senario = senario;
	}

	public String getExp_result() {
		return exp_result;
	}

	public void setExp_result(String exp_result) {
		this.exp_result = exp_result;
	}

	public String getDesigner() {
		return designer;
	}

	public void setDesigner(String designer) {
		this.designer = designer;
	}

	public String getFst_tester() {
		return fst_tester;
	}

	public void setFst_tester(String fst_tester) {
		this.fst_tester = fst_tester;
	}

	public String getFst_test_dt() {
		return fst_test_dt;
	}

	public void setFst_test_dt(String fst_test_dt) {
		this.fst_test_dt = fst_test_dt;
	}

	public String getFst_test_result() {
		return fst_test_result;
	}

	public void setFst_test_result(String fst_test_result) {
		this.fst_test_result = fst_test_result;
	}

	public String getFst_test_err_grade() {
		return fst_test_err_grade;
	}

	public void setFst_test_err_grade(String fst_test_err_grade) {
		this.fst_test_err_grade = fst_test_err_grade;
	}

	public String getFst_test_err() {
		return fst_test_err;
	}

	public void setFst_test_err(String fst_test_err) {
		this.fst_test_err = fst_test_err;
	}

	public String getFst_test_comment() {
		return fst_test_comment;
	}

	public void setFst_test_comment(String fst_test_comment) {
		this.fst_test_comment = fst_test_comment;
	}

	public String getFst_test_fix_dt() {
		return fst_test_fix_dt;
	}

	public void setFst_test_fix_dt(String fst_test_fix_dt) {
		this.fst_test_fix_dt = fst_test_fix_dt;
	}

	public String getFst_test_fixer() {
		return fst_test_fixer;
	}

	public void setFst_test_fixer(String fst_test_fixer) {
		this.fst_test_fixer = fst_test_fixer;
	}

	public String getFst_test_fix_item() {
		return fst_test_fix_item;
	}

	public void setFst_test_fix_item(String fst_test_fix_item) {
		this.fst_test_fix_item = fst_test_fix_item;
	}

	public String getFst_test_fix_comment() {
		return fst_test_fix_comment;
	}

	public void setFst_test_fix_comment(String fst_test_fix_comment) {
		this.fst_test_fix_comment = fst_test_fix_comment;
	}

	public String getScd_tester() {
		return scd_tester;
	}

	public void setScd_tester(String scd_tester) {
		this.scd_tester = scd_tester;
	}

	public String getScd_test_dt() {
		return scd_test_dt;
	}

	public void setScd_test_dt(String scd_test_dt) {
		this.scd_test_dt = scd_test_dt;
	}

	public String getScd_test_result() {
		return scd_test_result;
	}

	public void setScd_test_result(String scd_test_result) {
		this.scd_test_result = scd_test_result;
	}

	public String getScd_test_err_grade() {
		return scd_test_err_grade;
	}

	public void setScd_test_err_grade(String scd_test_err_grade) {
		this.scd_test_err_grade = scd_test_err_grade;
	}

	public String getScd_test_err() {
		return scd_test_err;
	}

	public void setScd_test_err(String scd_test_err) {
		this.scd_test_err = scd_test_err;
	}

	public String getScd_test_comment() {
		return scd_test_comment;
	}

	public void setScd_test_comment(String scd_test_comment) {
		this.scd_test_comment = scd_test_comment;
	}

	public String getScd_test_fix_dt() {
		return scd_test_fix_dt;
	}

	public void setScd_test_fix_dt(String scd_test_fix_dt) {
		this.scd_test_fix_dt = scd_test_fix_dt;
	}

	public String getScd_test_fixer() {
		return scd_test_fixer;
	}

	public void setScd_test_fixer(String scd_test_fixer) {
		this.scd_test_fixer = scd_test_fixer;
	}

	public String getScd_test_fix_item() {
		return scd_test_fix_item;
	}

	public void setScd_test_fix_item(String scd_test_fix_item) {
		this.scd_test_fix_item = scd_test_fix_item;
	}

	public String getScd_test_fix_comment() {
		return scd_test_fix_comment;
	}

	public void setScd_test_fix_comment(String scd_test_fix_comment) {
		this.scd_test_fix_comment = scd_test_fix_comment;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	
}
