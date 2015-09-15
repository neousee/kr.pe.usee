package kr.pe.usee.excel;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PogRed {
	String prod_cd        ;
	String prod_nm        ;
	String orgnp_cd       ;
	String orgnp_nm       ;
	String guksan         ;
	String disp_totqty_val; 
	String disp_unit_nm   ;
	String disp_unit_cd   ;
	String brnd_cd        ;
	String brnd_nm        ;
	String ven_cd         ;
	String ven_nm         ;

	public PogRed(ResultSet rs) throws SQLException {
		prod_cd         = rs.getString("prod_cd")!=null?rs.getString("prod_cd") : "" ; 
		prod_nm         = rs.getString("prod_nm")!=null?rs.getString("prod_nm") : "" ;
		orgnp_cd        = rs.getString("orgnp_cd")!=null?rs.getString("orgnp_cd") : "" ;
		orgnp_nm        = rs.getString("orgnp_nm")!=null?rs.getString("orgnp_nm") : "" ;
		guksan          = rs.getString("guksan")!=null?rs.getString("guksan") : "" ;
		disp_totqty_val = rs.getString("disp_totqty_val")!=null?rs.getString("disp_totqty_val") : "" ; 
		disp_unit_nm    = rs.getString("disp_unit_nm")!=null?rs.getString("disp_unit_nm") : "" ;
		disp_unit_cd    = rs.getString("disp_unit_cd")!=null?rs.getString("disp_unit_cd") : "" ;
		brnd_cd         = rs.getString("brnd_cd")!=null?rs.getString("brnd_cd") : "" ;
		brnd_nm         = rs.getString("brnd_nm")!=null?rs.getString("brnd_nm") : "" ;
		ven_cd          = rs.getString("ven_cd")!=null?rs.getString("ven_cd") : "" ;
		ven_nm          = rs.getString("ven_nm")!=null?rs.getString("ven_nm") : "" ;	}
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
	public String getOrgnp_cd() {
		return orgnp_cd;
	}
	public void setOrgnp_cd(String orgnp_cd) {
		this.orgnp_cd = orgnp_cd;
	}
	public String getOrgnp_nm() {
		return orgnp_nm;
	}
	public void setOrgnp_nm(String orgnp_nm) {
		this.orgnp_cd = orgnp_cd;
	}
	public String getGuksan() {
		return guksan;
	}
	public void setGuksan(String guksan) {
		this.guksan = guksan;
	}
	public String getDisp_totqty_val() {
		return disp_totqty_val;
	}
	public void setDisp_totqty_val(String disp_totqty_val) {
		this.disp_totqty_val = disp_totqty_val;
	}
	public String getDisp_unit_nm() {
		return disp_unit_nm;
	}
	public void setDisp_unit_nm(String disp_unit_nm) {
		this.disp_unit_nm = disp_unit_nm;
	}
	public String getDisp_unit_cd() {
		return disp_unit_cd;
	}
	public void setDisp_unit_cd(String disp_unit_cd) {
		this.disp_unit_cd = disp_unit_cd;
	}
	public String getBrnd_cd() {
		return brnd_cd;
	}
	public void setBrnd_cd(String brnd_cd) {
		this.brnd_cd = brnd_cd;
	}
	public String getBrnd_nm() {
		return brnd_nm;
	}
	public void setBrnd_nm(String brnd_nm) {
		this.brnd_nm = brnd_nm;
	}
	public String getVen_cd() {
		return ven_cd;
	}
	public void setVen_cd(String ven_cd) {
		this.ven_cd = ven_cd;
	}
	public String getVen_nm() {
		return ven_nm;
	}
	public void setVen_nm(String ven_nm) {
		this.ven_nm = ven_nm;
	}

}
