package kr.pe.usee.excel;

import java.sql.SQLException;

import kr.pe.usee.db.mysql.MysqlDb;

public class Kbf {
	String l1Cd        ;
	String l2Cd        ;
	String l3Cd        ;
	String prodCd      ;
	String prodNm      ;
	String kbfAttrNm   ;
	String kbfAttrCd   ;
	String kbfAttrVal  ;
	String kbfAttrValNm;
	String danCd       ;
	String chamgo      ;
	String noExist     ;
	boolean isDan      ;
	
	int firstIdx;
	int lastIdx;
	MysqlDb db;

	public Kbf(MysqlDb db, String l1, String l2, Row row) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		firstIdx = row.getFirstCellNum();
		lastIdx = row.getLastCellNum();
		l1Cd = l1;
		l2Cd = l2;
		l3Cd = db.getL3Cd(l1Cd, l2Cd, getCellValue(row.getCell(0)));
		String catNm = getCellValue(row.getCell(0));
		/*
		try {
		prodCd = (row.getCell(1).getStringCellValue());
		} catch(java.lang.IllegalStateException p) {
			prodCd = "";
		} catch(java.lang.NullPointerException np) {
			prodCd = "";
		}
		*/
		prodNm = getCellValue(row.getCell(2));
		if(l1Cd.equals("001") || l1Cd.equals("003") || l1Cd.equals("014")) {
			try {
			prodCd = row.getCell(1).getStringCellValue();
			} catch(NullPointerException npe) {
				
			}
		} else {
			prodCd = db.getProdCd(catNm, prodNm);
			if("".equals(prodCd)) {
				prodCd = getCellValue(row.getCell(1));
			}
		}
		kbfAttrNm = getCellValue(row.getCell(3));
		kbfAttrCd = db.getKbfAttrCode(l3Cd,kbfAttrNm);
		if("G0816".equals(kbfAttrCd)) {
			isDan = true;
		} else {
			isDan = false;
		}
		kbfAttrValNm = getCellValue(row.getCell(4));
		if("G0001".equals(kbfAttrCd))
			kbfAttrVal = kbfAttrValNm;
		else if("G0002".equals(kbfAttrCd) || "G0003".equals(kbfAttrCd))
			kbfAttrVal = db.getkbfAttrValCdWonsanzi(kbfAttrCd,kbfAttrValNm);
		else if("G0816".equals(kbfAttrCd)) {
			try {
			String[] ts = kbfAttrValNm.split(" ");
			kbfAttrVal = ts[0]!=null?ts[0]:"";
			danCd = (ts[1]!=null && !ts[1].equals(""))?db.getKbfDanwiCode(ts[1]):"";
			} catch (java.lang.ArrayIndexOutOfBoundsException e) {
				danCd = "";
			} catch (java.lang.NullPointerException ne) {
				kbfAttrVal =  danCd = "";
			}
		} else if("G0384".equals(kbfAttrCd)) {
			if("1.0".equals(kbfAttrValNm))
				kbfAttrVal = "1";
			else if("2.0".equals(kbfAttrValNm))
				kbfAttrVal = "1";
			else
				kbfAttrVal = db.getkbfAttrValCd(kbfAttrCd,kbfAttrValNm);
		} else if("G0666".equals(kbfAttrCd)) {
			if("친환경인증없음".equals(kbfAttrValNm))
				kbfAttrValNm = "친환경 인증없음";				
			kbfAttrVal = db.getkbfAttrValCd(kbfAttrCd,kbfAttrValNm);
		} else
			kbfAttrVal = db.getkbfAttrValCd(kbfAttrCd,kbfAttrValNm);
		
		if(lastIdx>5) {
			chamgo = getCellValue(row.getCell(5));
			noExist = getCellValue(row.getCell(6));
		}
		
	}
	
	private String getCellValue(Cell cell) {
		String ts = null;
		try {
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
		} catch (java.lang.NullPointerException npe) {
			ts = "";
		}
		return ts;
	}
	
	@Override
	public String toString() {
		return "["+l1Cd +":"+l2Cd +":"+l3Cd +":"+prodNm +":"+prodCd +":"+kbfAttrNm +":"+kbfAttrCd +":"+kbfAttrVal +":"+kbfAttrValNm +":"+chamgo +":"+noExist +"]";
	}	

	public String getL1Cd() {
		return l1Cd;
	}

	public void setL1Cd(String l1Cd) {
		this.l1Cd = l1Cd;
	}

	public String getL2Cd() {
		return l2Cd;
	}

	public void setL2Cd(String l2Cd) {
		this.l2Cd = l2Cd;
	}

	public String getL3Cd() {
		return l3Cd;
	}

	public void setL3Cd(String l3Cd) {
		this.l3Cd = l3Cd;
	}

	public String getProdCd() {
		return prodCd;
	}

	public void setProdCd(String prodCd) {
		this.prodCd = prodCd;
	}

	public String getProdNm() {
		return prodNm;
	}

	public void setProdNm(String prodNm) {
		this.prodNm = prodNm;
	}

	public String getKbfAttrNm() {
		return kbfAttrNm;
	}

	public void setKbfAttrNm(String kbfAttrNm) {
		this.kbfAttrNm = kbfAttrNm;
	}

	public String getKbfAttrCd() {
		return kbfAttrCd;
	}

	public void setKbfAttrCd(String kbfAttrCd) {
		this.kbfAttrCd = kbfAttrCd;
	}

	public String getKbfAttrVal() {
		return kbfAttrVal;
	}

	public void setKbfAttrVal(String kbfAttrVal) {
		this.kbfAttrVal = kbfAttrVal;
	}

	public String getKbfAttrValNm() {
		return kbfAttrValNm;
	}

	public void setKbfAttrValNm(String kbfAttrValNm) {
		this.kbfAttrValNm = kbfAttrValNm;
	}

	public boolean isDan() {
		return isDan;
	}

	public void setDan(boolean isDan) {
		this.isDan = isDan;
	}

	public String getChamgo() {
		return chamgo;
	}

	public void setChamgo(String chamgo) {
		this.chamgo = chamgo;
	}

	public String getNoExist() {
		return noExist;
	}

	public void setNoExist(String noExist) {
		this.noExist = noExist;
	}

	public String getDanCd() {
		return danCd;
	}
	
	

}
