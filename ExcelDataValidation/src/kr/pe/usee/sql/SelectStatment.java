package kr.pe.usee.sql;

import java.util.*;

import kr.pe.usee.sql.parser.ReservedWord;

public class SelectStatment {
	//mandatory
	String[] selectTokens;
	String[] fromTokens;
	
	// optional
	String[] groupByTokens;
	String[] unionTokens;
	String[] minusTokens;
	String[] exceptTokens;
	String[] limitTokens;
	String[] forUpdateTokens;
	public SelectStatment() {
		// TODO Auto-generated constructor stub
	}

	public SelectStatment(String[] rt) {
		//parse(rt);
		System.out.println("SelectStatment---------------");
	}
	

	private void parseSingleSelect(String[] rt) {
		// TODO Auto-generated method stub
		
	}

	private String[] getSelectedTokens(String[] rt) {
		// TODO Auto-generated method stub
		return null;
	}

}
