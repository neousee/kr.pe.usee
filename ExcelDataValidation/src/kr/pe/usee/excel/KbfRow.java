package kr.pe.usee.excel;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

public class KbfRow {
	String l1;
	String l2;
	String l3;
	String l4List;
	String kbfAttrList;
	String attr1;
	String attr2;
	String attr3;
	String attr4;
	Hashtable<String, String> attr;
	public KbfRow() {
		
	}
	public KbfRow(Row row) {
		l1          = row.getCell(0).getStringCellValue();
		l2          = row.getCell(1).getStringCellValue();
		l3          = row.getCell(2).getStringCellValue();
		l4List      = row.getCell(3).getStringCellValue();
		kbfAttrList = row.getCell(4).getStringCellValue();
		attr1       = row.getCell(5).getStringCellValue();
		attr2       = row.getCell(6).getStringCellValue();
		attr3       = row.getCell(7).getStringCellValue();
		attr4       = row.getCell(8).getStringCellValue();
		attr        = new Hashtable<String, String>();
		if(attr1 != null && !"".equals(attr1) ) attr.put(attr1, attr1);
		if(attr2 != null && !"".equals(attr2) ) attr.put(attr2, attr2);
		if(attr3 != null && !"".equals(attr3) ) attr.put(attr3, attr3);
		if(attr4 != null && !"".equals(attr4) ) attr.put(attr4, attr4);

		
	}
	
	public int getAttrCount() {
		return attr.size();
	}
	
	public boolean hasAttr(String attrName) {
		return attr.containsKey(attrName);
	}
	
	public Set<String> getAttrList() {
		return attr.keySet();
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
	public String getKbfAttrList() {
		return kbfAttrList;
	}
	public void setKbfAttrList(String kbfAttrList) {
		this.kbfAttrList = kbfAttrList;
	}
	public String getAttr1() {
		return attr1;
	}
	public void setAttr1(String attr1) {
		this.attr1 = attr1;
	}
	public String getAttr2() {
		return attr2;
	}
	public void setAttr2(String attr2) {
		this.attr2 = attr2;
	}
	public String getAttr3() {
		return attr3;
	}
	public void setAttr3(String attr3) {
		this.attr3 = attr3;
	}
	public String getAttr4() {
		return attr4;
	}
	public void setAttr4(String attr4) {
		this.attr4 = attr4;
	}

	
}
