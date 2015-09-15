package kr.pe.usee.excel;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.poi.ss.usermodel.Row;

public class PogRow {
	
	String prodCode;
	String prodName;
	String l1;
	String l2;
	String l3;
	String l4;

	public PogRow(Row row) {
		prodCode    = row.getCell(0).getStringCellValue();
		prodName    = row.getCell(1).getStringCellValue();
		l1          = row.getCell(2).getStringCellValue();
		l2          = row.getCell(3).getStringCellValue();
		l3          = row.getCell(4).getStringCellValue();
		l4          = row.getCell(5).getStringCellValue();
		
	}

	public PogRow(ResultSet rs) throws SQLException {
		prodCode = rs.getString("prodCd");
		prodName = rs.getString("nm");
		l1       = rs.getString("tl1");
		l2       = rs.getString("tl2");
		l3       = rs.getString("tl3");
		l4       = rs.getString("tl4");
	}

	public String getProdCode() {
		return prodCode;
	}

	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public String getL1() {
		return l1;
	}

	public void setL1(String l1) {
		this.l1 = l1;
	}

	public String getL2() {
		return l2;
	}

	public void setL2(String l2) {
		this.l2 = l2;
	}

	public String getL3() {
		return l3;
	}

	public void setL3(String l3) {
		this.l3 = l3;
	}

	public String getL4() {
		return l4;
	}

	public void setL4(String l4) {
		this.l4 = l4;
	}

}
