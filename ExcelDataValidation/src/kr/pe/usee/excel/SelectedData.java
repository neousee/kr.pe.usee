package kr.pe.usee.excel;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SelectedData {
	String l3_nm;
	String prod_cd;
	String prod_nm;
	String kbfAttrNm;
	String kbfAttrValue;
	
	public SelectedData() {
		
	}
	
	public SelectedData(String line) {
		String[] t = line.split(":");
		l3_nm = t[0];
		prod_cd = t[1];
		prod_nm = t[2];
		kbfAttrNm = t[3];
		kbfAttrValue = t[4];
	}

	public SelectedData(ResultSet rs) throws SQLException {
		l3_nm = rs.getString("l3_nm");
		prod_cd = rs.getString("prod_cd");
		prod_nm = rs.getString("prod_nm");
		kbfAttrNm = rs.getString("kbf_attr_id");
		kbfAttrValue = rs.getString("kbf_attr_value");
	}

	public String getL3_nm() {
		return l3_nm;
	}

	public void setL3_nm(String l3_nm) {
		this.l3_nm = l3_nm;
	}

	public String getProd_cd() {
		return prod_cd;
	}

	public void setProd_cd(String prod_cd) {
		this.prod_cd = prod_cd;
	}

	public String getProd_nm() {
		return prod_nm;
	}

	public void setProd_nm(String prod_nm) {
		this.prod_nm = prod_nm;
	}

	public String getKbfAttrNm() {
		return kbfAttrNm;
	}

	public void setKbfAttrNm(String kbfAttrNm) {
		this.kbfAttrNm = kbfAttrNm;
	}

	public String getKbfAttrValue() {
		return kbfAttrValue;
	}

	public void setKbfAttrValue(String kbfAttrValue) {
		this.kbfAttrValue = kbfAttrValue;
	}
	
}
