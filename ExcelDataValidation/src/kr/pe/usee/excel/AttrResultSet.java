package kr.pe.usee.excel;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AttrResultSet {
	
	String l1;
	String l2;
	String l3;
	String l4List;
	String attrList;
	String a1;
	String a2;
	String a3;
	String a4;

	public AttrResultSet(ResultSet rs) throws SQLException {
		l1       = rs.getString("l1");
		l2       = rs.getString("l2");
		l3       = rs.getString("l3");
		l4List   = rs.getString("l4");
		attrList = rs.getString("attrList");
		a1       = rs.getString("at1");
		a2       = rs.getString("at2");
		a3       = rs.getString("at3");
		a4       = rs.getString("at4");
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

	public String getL4List() {
		return l4List;
	}

	public void setL4List(String l4List) {
		this.l4List = l4List;
	}

	public String getAttrList() {
		return attrList;
	}

	public void setAttrList(String attrList) {
		this.attrList = attrList;
	}

	public String getA1() {
		return a1;
	}

	public void setA1(String a1) {
		this.a1 = a1;
	}

	public String getA2() {
		return a2;
	}

	public void setA2(String a2) {
		this.a2 = a2;
	}

	public String getA3() {
		return a3;
	}

	public void setA3(String a3) {
		this.a3 = a3;
	}

	public String getA4() {
		return a4;
	}

	public void setA4(String a4) {
		this.a4 = a4;
	}
}
