package kr.pe.usee.excel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * 
 * @author Administrator
 *
 */
public class KbfAttrValueList {

	int atLength;
	String lsl1      ;
	String lsl2      ;
	String lsl3code  ;
	String lsl3      ;
	String gl1       ;
	String gl2       ;
	String gl3       ;
	String attr      ;
	String[] atrrVal = new String[32];
	ArrayList<String> attrValList;
	
	public KbfAttrValueList(ResultSet rs) throws SQLException {
		lsl1      = rs.getString("lsl1")    ;
		lsl2      = rs.getString("lsl2")    ;
		lsl3code  = rs.getString("lsl3code");
		lsl3      = rs.getString("lsl3")    ;
		attr      = rs.getString("attr")    ;
		atrrVal[0 ]    = rs.getString("atrr1")   ;
		atrrVal[1 ]    = rs.getString("atrr2")   ;
		atrrVal[2 ]    = rs.getString("atrr3")   ;
		atrrVal[3 ]    = rs.getString("atrr4")   ;
		atrrVal[4 ]    = rs.getString("atrr5")   ;
		atrrVal[5 ]    = rs.getString("atrr6")   ;
		atrrVal[6 ]    = rs.getString("atrr7")   ;
		atrrVal[7 ]    = rs.getString("atrr8")   ;
		atrrVal[8 ]    = rs.getString("atrr9")   ;
		atrrVal[9 ]    = rs.getString("atrr10")  ;
		atrrVal[10]    = rs.getString("atrr11")  ;
		atrrVal[11]    = rs.getString("atrr12")  ;
		atrrVal[12]    = rs.getString("atrr13")  ;
		atrrVal[13]    = rs.getString("atrr14")  ;
		atrrVal[14]    = rs.getString("atrr15")  ;
		atrrVal[15]    = rs.getString("atrr16")  ;
		atrrVal[16]    = rs.getString("atrr17")  ;
		atrrVal[17]    = rs.getString("atrr18")  ;
		atrrVal[18]    = rs.getString("atrr19")  ;
		atrrVal[19]    = rs.getString("atrr20")  ;
		atrrVal[20]    = rs.getString("atrr21")  ;
		atrrVal[21]    = rs.getString("atrr22")  ;
		atrrVal[22]    = rs.getString("atrr23")  ;
		atrrVal[23]    = rs.getString("atrr24")  ;
		atrrVal[24]    = rs.getString("atrr25")  ;
		atrrVal[25]    = rs.getString("atrr26")  ;
		atrrVal[26]    = rs.getString("atrr27")  ;
		atrrVal[27]    = rs.getString("atrr28")  ;
		atrrVal[28]    = rs.getString("atrr29")  ;
		atrrVal[29]    = rs.getString("atrr30")  ;
		atrrVal[30]    = rs.getString("atrr31")  ;
		atrrVal[31]    = rs.getString("atrr32")  ;
		for(int i=0;i<atrrVal.length;i++) {
			if("".endsWith(atrrVal[i])) {
				atLength = i;
				break;
			} else {
				atLength = i;
			}
		}
		attrValList = new ArrayList<String>();
		for(int i=0;i<atLength;i++) {
			attrValList.add(atrrVal[i]);
		}
		
	}
	
	public KbfAttrValueList(ArrayList<KbfAttrList> all, String kbfAttr) {
		for(int i=0;i<all.size();i++) {
			KbfAttrList kbfAttr1 = all.get(i);
			atrrVal[i] = kbfAttr1.getKBF_ATTR_VAL_NM();
		}
		
		attr      = kbfAttr    ;
		for(int i=0;i<atrrVal.length;i++) {
			try {
			if("".endsWith(atrrVal[i])) {
				atLength = i;
				break;
			} else {
				atLength = i;
			}
			} catch (NullPointerException npe) {
				atLength = i;
				break;
			}
		}
		attrValList = new ArrayList<String>();
		for(int i=0;i<atLength;i++) {
			attrValList.add(atrrVal[i]);
		}
	}

	public String[] toStringArray() {
		String[] arr = new String[this.attrValList.size()];
		this.attrValList.toArray(arr);
		return arr;
	}

	public String getLsl1() {
		return lsl1;
	}

	public void setLsl1(String lsl1) {
		this.lsl1 = lsl1;
	}

	public String getLsl2() {
		return lsl2;
	}

	public void setLsl2(String lsl2) {
		this.lsl2 = lsl2;
	}

	public String getLsl3code() {
		return lsl3code;
	}

	public void setLsl3code(String lsl3code) {
		this.lsl3code = lsl3code;
	}

	public String getLsl3() {
		return lsl3;
	}

	public void setLsl3(String lsl3) {
		this.lsl3 = lsl3;
	}

	public String getGl1() {
		return gl1;
	}

	public void setGl1(String gl1) {
		this.gl1 = gl1;
	}

	public String getGl2() {
		return gl2;
	}

	public void setGl2(String gl2) {
		this.gl2 = gl2;
	}

	public String getGl3() {
		return gl3;
	}

	public void setGl3(String gl3) {
		this.gl3 = gl3;
	}

	public String getAttr() {
		return attr;
	}

	public void setAttr(String attr) {
		this.attr = attr;
	}

}
