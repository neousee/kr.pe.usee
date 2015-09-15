package kr.pe.usee.excel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GrpResultSet {

	String tl1;
	String tl2;
	ArrayList<ProdInfo> prodList;
	ArrayList<String> l3List;
	
	public GrpResultSet(ResultSet rs) throws SQLException {
			tl1 = rs.getString("tl1");
			tl2 = rs.getString("tl2");
			prodList = parseProdList(rs.getString("prodCdList"));
			l3List = parseL3List(rs.getString("l3List").split(":"));
	}

	private ArrayList<ProdInfo> parseProdList(String prodString) {
		StringBuffer prodListStr = new StringBuffer();
		String[] list = prodString.split(":");
		ArrayList<ProdInfo> prodList = new ArrayList<ProdInfo>();
		for(String p:list) {
			prodList.add( new ProdInfo(p) );
		}
		return prodList;
	}
	
	private ArrayList<String> parseL3List(String[] listStr) {
		ArrayList<String> al = new ArrayList<String>();
		for(String t:listStr) {
			al.add(t);
		}
		return al;
	}
	
	public String getInStr() {
		StringBuffer prodListStr = new StringBuffer();
		for(ProdInfo pi :prodList) {
			prodListStr.append("'"+pi.getCd()+"',");
		}
		return prodListStr.substring(0, prodListStr.length()-1);
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

	public ArrayList<ProdInfo> getProdList() {
		return prodList;
	}

	public void setProdList(ArrayList<ProdInfo> prodList) {
		this.prodList = prodList;
	}

	public ArrayList<String> getL3List() {
		return l3List;
	}

	public void setL3List(ArrayList<String> l3List) {
		this.l3List = l3List;
	}
	

}
