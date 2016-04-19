package kr.pe.usee.ls;

import java.io.Serializable;
import java.util.ArrayList;

public class InfoOfTable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String tableName;
	ArrayList<InfoOfColumn> list;

	public InfoOfTable() {
		list = new ArrayList<InfoOfColumn>();
	}

	public InfoOfTable(String tableName) {
		this.tableName = tableName;
		list = new ArrayList<InfoOfColumn>();
	}
	
	public void addColInfo(InfoOfColumn col) {
		list.add(col);
	}
	
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public ArrayList<InfoOfColumn> getList() {
		return list;
	}
	public void setList(ArrayList<InfoOfColumn> list) {
		this.list = list;
	}
	
	public String toString() {
		return tableName + ": colCnt("+list.size()+")";
		
	}

}
