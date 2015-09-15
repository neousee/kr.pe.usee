package kr.pe.usee.db.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProdInfo2 {
	String prodCd ;
	String nm ;
	String tl1 ;
	String tl2 ;
	String tl3 ;
	String tl4 ;
	
	public ProdInfo2(ResultSet rs) throws SQLException {
		prodCd = rs.getString("prodCd");
		nm     = rs.getString("nm")    ;
		tl1    = rs.getString("tl1")   ;
		tl2    = rs.getString("tl2")   ;
		tl3    = rs.getString("tl3")   ;
		tl4    = rs.getString("tl4")   ;
	}

	public String getProdCd() {
		return prodCd;
	}

	public void setProdCd(String prodCd) {
		this.prodCd = prodCd;
	}

	public String getNm() {
		return nm;
	}

	public void setNm(String nm) {
		this.nm = nm;
	}

	public String getTl1() {
		return tl1;
	}

	public void setTl1(String tl1) {
		this.tl1 = tl1;
	}

	public String getTl2() {
		return tl2;
	}

	public void setTl2(String tl2) {
		this.tl2 = tl2;
	}

	public String getTl3() {
		return tl3;
	}

	public void setTl3(String tl3) {
		this.tl3 = tl3;
	}

	public String getTl4() {
		return tl4;
	}

	public void setTl4(String tl4) {
		this.tl4 = tl4;
	}
	
}
