package kr.pe.usee.db.excel;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Prod2 {

	String L1_CD             ;
	String l1_nm             ;
	String L2_CD             ;
	String l2_nm             ;
	String L3_CD             ;
	String L3_NM             ;
	String SORT_SEQ          ;
	String L4_CD             ;
	String L4_NM             ;
	String PROD_CD           ;
	String PROD_NM           ;
	String PROD_SHRT_NM      ;
	String PROD_OPER_STAT_CD ;
	String STAT              ;
	String KBF_ATTR_ID       ;
	String KBF_ATTR_NM       ;
	String KBF_SHRT_NM       ;
	String KBF_TYP_CD        ;
	String GRP_USE_YN        ;
	String USE_YN            ;
	
	public Prod2(ResultSet rs) throws SQLException {
		L1_CD             = rs.getString("L1_CD")             != null ? rs.getString("L1_CD")             : "" ;
		l1_nm             = rs.getString("l1_nm")             != null ? rs.getString("l1_nm")             : "" ;
		L2_CD             = rs.getString("L2_CD")             != null ? rs.getString("L2_CD")             : "" ;
		l2_nm             = rs.getString("l2_nm")             != null ? rs.getString("l2_nm")             : "" ;
		L3_CD             = rs.getString("L3_CD")             != null ? rs.getString("L3_CD")             : "" ;
		L3_NM             = rs.getString("L3_NM")             != null ? rs.getString("L3_NM")             : "" ;
		SORT_SEQ          = rs.getString("SORT_SEQ")          != null ? rs.getString("SORT_SEQ")          : "" ;
		L4_CD             = rs.getString("L4_CD")             != null ? rs.getString("L4_CD")             : "" ;
		L4_NM             = rs.getString("L4_NM")             != null ? rs.getString("L4_NM")             : "" ;
		PROD_CD           = rs.getString("PROD_CD")           != null ? rs.getString("PROD_CD")           : "" ;
		PROD_NM           = rs.getString("PROD_NM")           != null ? rs.getString("PROD_NM")           : "" ;
		PROD_SHRT_NM      = rs.getString("PROD_SHRT_NM")      != null ? rs.getString("PROD_SHRT_NM")      : "" ;
		PROD_OPER_STAT_CD = rs.getString("PROD_OPER_STAT_CD") != null ? rs.getString("PROD_OPER_STAT_CD") : "" ;
		STAT              = rs.getString("STAT")              != null ? rs.getString("STAT")              : "" ;
		KBF_ATTR_ID       = rs.getString("KBF_ATTR_ID")       != null ? rs.getString("KBF_ATTR_ID")       : "" ;
		KBF_ATTR_NM       = rs.getString("KBF_ATTR_NM")       != null ? rs.getString("KBF_ATTR_NM")       : "" ;
		KBF_SHRT_NM       = rs.getString("KBF_SHRT_NM")       != null ? rs.getString("KBF_SHRT_NM")       : "" ;
		KBF_TYP_CD        = rs.getString("KBF_TYP_CD")        != null ? rs.getString("KBF_TYP_CD")        : "" ;
		GRP_USE_YN        = rs.getString("GRP_USE_YN")        != null ? rs.getString("GRP_USE_YN")        : "" ;
		USE_YN            = rs.getString("USE_YN")            != null ? rs.getString("USE_YN")            : "" ;

	}

	public String getL1_CD() {
		return L1_CD;
	}

	public void setL1_CD(String l1_CD) {
		L1_CD = l1_CD;
	}

	public String getL1_nm() {
		return l1_nm;
	}

	public void setL1_nm(String l1_nm) {
		this.l1_nm = l1_nm;
	}

	public String getL2_CD() {
		return L2_CD;
	}

	public void setL2_CD(String l2_CD) {
		L2_CD = l2_CD;
	}

	public String getL2_nm() {
		return l2_nm;
	}

	public void setL2_nm(String l2_nm) {
		this.l2_nm = l2_nm;
	}

	public String getL3_CD() {
		return L3_CD;
	}

	public void setL3_CD(String l3_CD) {
		L3_CD = l3_CD;
	}

	public String getL3_NM() {
		return L3_NM;
	}

	public void setL3_NM(String l3_NM) {
		L3_NM = l3_NM;
	}

	public String getSORT_SEQ() {
		return SORT_SEQ;
	}

	public void setSORT_SEQ(String sORT_SEQ) {
		SORT_SEQ = sORT_SEQ;
	}

	public String getL4_CD() {
		return L4_CD;
	}

	public void setL4_CD(String l4_CD) {
		L4_CD = l4_CD;
	}

	public String getL4_NM() {
		return L4_NM;
	}

	public void setL4_NM(String l4_NM) {
		L4_NM = l4_NM;
	}

	public String getPROD_CD() {
		return PROD_CD;
	}

	public void setPROD_CD(String pROD_CD) {
		PROD_CD = pROD_CD;
	}

	public String getPROD_NM() {
		return PROD_NM;
	}

	public void setPROD_NM(String pROD_NM) {
		PROD_NM = pROD_NM;
	}

	public String getPROD_SHRT_NM() {
		return PROD_SHRT_NM;
	}

	public void setPROD_SHRT_NM(String pROD_SHRT_NM) {
		PROD_SHRT_NM = pROD_SHRT_NM;
	}

	public String getPROD_OPER_STAT_CD() {
		return PROD_OPER_STAT_CD;
	}

	public void setPROD_OPER_STAT_CD(String pROD_OPER_STAT_CD) {
		PROD_OPER_STAT_CD = pROD_OPER_STAT_CD;
	}

	public String getSTAT() {
		return STAT;
	}

	public void setSTAT(String sTAT) {
		STAT = sTAT;
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
