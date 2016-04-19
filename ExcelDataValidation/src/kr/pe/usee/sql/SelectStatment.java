package kr.pe.usee.sql;

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
		int idxFrom=0;
		for(int i=0;i<rt.length;i++) {
			if(rt[i].trim().startsWith(ReservedWord.KWD_FROM)) {
				idxFrom = i;
				break;
			}
		}
		
		String[][] splitTk = splitArray(rt, idxFrom);
		selectTokens = splitTk[0];
		fromTokens = splitTk[1];
		System.out.println("SelectStatment--------------- : \r\n"+this.toString());
	}
	

	private void parseSingleSelect(String[] rt) {
		// TODO Auto-generated method stub
		
	}

	private String[] getSelectedTokens(String[] rt) {
		// TODO Auto-generated method stub
		return null;
	}

	private String[][] splitArray(String[] rt, int index) {
		String[] al1 = new String[index];
		String[] al2 = new String[rt.length-index];
		for(int i=0;i<index;i++) {
			al1[i] = rt[i];
		}
		
		for(int i=index;i<rt.length;i++) {
			al2[i-index] = rt[i];
		}
		
		String[][] rtArray = new String[2][];
		rtArray[0] = al1;
		rtArray[1] = al2;
		
		return rtArray;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for(String a:selectTokens) {
			sb.append(a+" ");
		}
		for(String a:fromTokens) {
			sb.append(a+" ");
		}
		return sb.toString();
	}
}
