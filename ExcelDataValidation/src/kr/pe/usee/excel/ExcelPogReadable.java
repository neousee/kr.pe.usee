package kr.pe.usee.excel;

import java.util.Hashtable;

public interface ExcelPogReadable {

	Hashtable<String,PogRow> kbfList = new Hashtable<String,PogRow>();
	public void getPogData();}
