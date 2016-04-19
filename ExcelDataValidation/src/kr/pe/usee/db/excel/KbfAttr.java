package kr.pe.usee.db.excel;

import java.sql.ResultSet;
import java.sql.SQLException;

public class KbfAttr {
	String L1_CD      ;
	String L2_CD      ;
	String L3_CD      ;
	String CAT_NM     ;
	String KBF_ATTR_ID;
	String KBF_ATTR_NM;
	String KBF_SHRT_NM;
	String KBF_TYP_CD ;
	String GRP_USE_YN ;
	String USE_YN     ;
	
	public KbfAttr(ResultSet rs) throws SQLException {
		L1_CD       = rs.getString("L1_CD")       != null ? rs.getString("L1_CD")       : "" ;
		L2_CD       = rs.getString("L2_CD")       != null ? rs.getString("L2_CD")       : "" ;
		L3_CD       = rs.getString("L3_CD")       != null ? rs.getString("L3_CD")       : "" ;
		CAT_NM      = rs.getString("CAT_NM")      != null ? rs.getString("CAT_NM")      : "" ;
		KBF_ATTR_ID = rs.getString("KBF_ATTR_ID") != null ? rs.getString("KBF_ATTR_ID") : "" ;
		KBF_ATTR_NM = rs.getString("KBF_ATTR_NM") != null ? rs.getString("KBF_ATTR_NM") : "" ;
		KBF_SHRT_NM = rs.getString("KBF_SHRT_NM") != null ? rs.getString("KBF_SHRT_NM") : "" ;
		KBF_TYP_CD  = rs.getString("KBF_TYP_CD")  != null ? rs.getString("KBF_TYP_CD")  : "" ;
		GRP_USE_YN  = rs.getString("GRP_USE_YN")  != null ? rs.getString("GRP_USE_YN")  : "" ;
		USE_YN      = rs.getString("USE_YN")      != null ? rs.getString("USE_YN")      : "" ;
	}

	public String getL1_CD() {
		return L1_CD;
	}

	public void setL1_CD(String l1_CD) {
		L1_CD = l1_CD;
	}

	public String getL2_CD() {
		return L2_CD;
	}

	public void setL2_CD(String l2_CD) {
		L2_CD = l2_CD;
	}

	public String getL3_CD() {
		return L3_CD;
	}

	public void setL3_CD(String l3_CD) {
		L3_CD = l3_CD;
	}

	public String getCAT_NM() {
		return CAT_NM;
	}

	public void setCAT_NM(String cAT_NM) {
		CAT_NM = cAT_NM;
	}

	public String getKBF_ATTR_ID() {
		return KBF_ATTR_ID;
	}

	public void setKBF_ATTR_ID(String kBF_ATTR_ID) {
		KBF_ATTR_ID = kBF_ATTR_ID;
	}

	public String getKBF_ATTR_NM() {
		return KBF_ATTR_NM;
	}

	public void setKBF_ATTR_NM(String kBF_ATTR_NM) {
		KBF_ATTR_NM = kBF_ATTR_NM;
	}

	public String getKBF_SHRT_NM() {
		return KBF_SHRT_NM;
	}

	public void setKBF_SHRT_NM(String kBF_SHRT_NM) {
		KBF_SHRT_NM = kBF_SHRT_NM;
	}

	public String getKBF_TYP_CD() {
		return KBF_TYP_CD;
	}

	public void setKBF_TYP_CD(String kBF_TYP_CD) {
		KBF_TYP_CD = kBF_TYP_CD;
	}

	public String getGRP_USE_YN() {
		return GRP_USE_YN;
	}

	public void setGRP_USE_YN(String gRP_USE_YN) {
		GRP_USE_YN = gRP_USE_YN;
	}

	public String getUSE_YN() {
		return USE_YN;
	}

	public void setUSE_YN(String uSE_YN) {
		USE_YN = uSE_YN;
	}
	
	
}
