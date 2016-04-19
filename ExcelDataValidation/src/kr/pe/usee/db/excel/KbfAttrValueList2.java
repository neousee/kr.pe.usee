package kr.pe.usee.db.excel;

import java.sql.ResultSet;
import java.sql.SQLException;

public class KbfAttrValueList2 {
	String KBF_ATTR_ID    ;
	String KBF_ATTR_VAL_ID;
	String KBF_ATTR_VAL_NM;
	String ETC_ATTR_VAL_YN;
	String GRP_SORT_SEQ   ;
	String SORT_SEQ       ;
	String GRP_USE_YN     ;

	public KbfAttrValueList2(ResultSet rs) throws SQLException {
		KBF_ATTR_ID     = rs.getString("KBF_ATTR_ID")     != null ? rs.getString("KBF_ATTR_ID")     : "" ;
		KBF_ATTR_VAL_ID = rs.getString("KBF_ATTR_VAL_ID") != null ? rs.getString("KBF_ATTR_VAL_ID") : "" ;
		KBF_ATTR_VAL_NM = rs.getString("KBF_ATTR_VAL_NM") != null ? rs.getString("KBF_ATTR_VAL_NM") : "" ;
		ETC_ATTR_VAL_YN = rs.getString("ETC_ATTR_VAL_YN") != null ? rs.getString("ETC_ATTR_VAL_YN") : "" ;
		GRP_SORT_SEQ    = rs.getString("GRP_SORT_SEQ")    != null ? rs.getString("GRP_SORT_SEQ")    : "" ;
		SORT_SEQ        = rs.getString("SORT_SEQ")        != null ? rs.getString("SORT_SEQ")        : "" ;
		GRP_USE_YN      = rs.getString("GRP_USE_YN")      != null ? rs.getString("GRP_USE_YN")      : "" ;
	}

	public KbfAttrValueList2() {
		// TODO Auto-generated constructor stub
	}

	public String getKBF_ATTR_ID() {
		return KBF_ATTR_ID;
	}

	public void setKBF_ATTR_ID(String kBF_ATTR_ID) {
		KBF_ATTR_ID = kBF_ATTR_ID;
	}

	public String getKBF_ATTR_VAL_ID() {
		return KBF_ATTR_VAL_ID;
	}

	public void setKBF_ATTR_VAL_ID(String kBF_ATTR_VAL_ID) {
		KBF_ATTR_VAL_ID = kBF_ATTR_VAL_ID;
	}

	public String getKBF_ATTR_VAL_NM() {
		return KBF_ATTR_VAL_NM;
	}

	public void setKBF_ATTR_VAL_NM(String kBF_ATTR_VAL_NM) {
		KBF_ATTR_VAL_NM = kBF_ATTR_VAL_NM;
	}

	public String getETC_ATTR_VAL_YN() {
		return ETC_ATTR_VAL_YN;
	}

	public void setETC_ATTR_VAL_YN(String eTC_ATTR_VAL_YN) {
		ETC_ATTR_VAL_YN = eTC_ATTR_VAL_YN;
	}

	public String getGRP_SORT_SEQ() {
		return GRP_SORT_SEQ;
	}

	public void setGRP_SORT_SEQ(String gRP_SORT_SEQ) {
		GRP_SORT_SEQ = gRP_SORT_SEQ;
	}

	public String getSORT_SEQ() {
		return SORT_SEQ;
	}

	public void setSORT_SEQ(String sORT_SEQ) {
		SORT_SEQ = sORT_SEQ;
	}

	public String getGRP_USE_YN() {
		return GRP_USE_YN;
	}

	public void setGRP_USE_YN(String gRP_USE_YN) {
		GRP_USE_YN = gRP_USE_YN;
	}

}
