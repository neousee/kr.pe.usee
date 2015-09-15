package kr.pe.usee.ls;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InfoOfColumn implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String OWNER               ;
	String TABLE_NAME          ;
	String COLUMN_NAME         ;
	String DATA_TYPE           ;
	String DATA_TYPE_MOD       ;
	String DATA_TYPE_OWNER     ;
	String DATA_LENGTH         ;
	String DATA_PRECISION      ;
	String DATA_SCALE          ;
	String NULLABLE            ;
	String COLUMN_ID           ;
	String DEFAULT_LENGTH      ;
	String DATA_DEFAULT        ;
	String NUM_DISTINCT        ;
	String LOW_VALUE           ;
	String HIGH_VALUE          ;
	String DENSITY             ;
	String NUM_NULLS           ;
	String NUM_BUCKETS         ;
	String LAST_ANALYZED       ;
	String SAMPLE_SIZE         ;
	String CHARACTER_SET_NAME  ;
	String CHAR_COL_DECL_LENGTH;
	String GLOBAL_STATS        ;
	String USER_STATS          ;
	String AVG_COL_LEN         ;
	String CHAR_LENGTH         ;
	String CHAR_USED           ;
	String V80_FMT_IMAGE       ;
	String DATA_UPGRADED       ;
	String HISTOGRAM           ;
	String COMMENTS            ;
	
	public InfoOfColumn() {
		
	}
	
	public InfoOfColumn(ResultSet rs) throws SQLException {
		OWNER                = rs.getString("OWNER"               );
		TABLE_NAME           = rs.getString("TABLE_NAME"          );
		COLUMN_NAME          = rs.getString("COLUMN_NAME"         );
		DATA_TYPE            = rs.getString("DATA_TYPE"           );
		DATA_TYPE_MOD        = rs.getString("DATA_TYPE_MOD"       );
		DATA_TYPE_OWNER      = rs.getString("DATA_TYPE_OWNER"     );
		DATA_LENGTH          = rs.getString("DATA_LENGTH"         );
		DATA_PRECISION       = rs.getString("DATA_PRECISION"      );
		DATA_SCALE           = rs.getString("DATA_SCALE"          );
		NULLABLE             = rs.getString("NULLABLE"            );
		COLUMN_ID            = rs.getString("COLUMN_ID"           );
		DEFAULT_LENGTH       = rs.getString("DEFAULT_LENGTH"      );
		DATA_DEFAULT         = rs.getString("DATA_DEFAULT"        );
		NUM_DISTINCT         = rs.getString("NUM_DISTINCT"        );
		LOW_VALUE            = rs.getString("LOW_VALUE"           );
		HIGH_VALUE           = rs.getString("HIGH_VALUE"          );
		DENSITY              = rs.getString("DENSITY"             );
		NUM_NULLS            = rs.getString("NUM_NULLS"           );
		NUM_BUCKETS          = rs.getString("NUM_BUCKETS"         );
		LAST_ANALYZED        = rs.getString("LAST_ANALYZED"       );
		SAMPLE_SIZE          = rs.getString("SAMPLE_SIZE"         );
		CHARACTER_SET_NAME   = rs.getString("CHARACTER_SET_NAME"  );
		CHAR_COL_DECL_LENGTH = rs.getString("CHAR_COL_DECL_LENGTH");
		GLOBAL_STATS         = rs.getString("GLOBAL_STATS"        );
		USER_STATS           = rs.getString("USER_STATS"          );
		AVG_COL_LEN          = rs.getString("AVG_COL_LEN"         );
		CHAR_LENGTH          = rs.getString("CHAR_LENGTH"         );
		CHAR_USED            = rs.getString("CHAR_USED"           );
		V80_FMT_IMAGE        = rs.getString("V80_FMT_IMAGE"       );
		DATA_UPGRADED        = rs.getString("DATA_UPGRADED"       );
		HISTOGRAM            = rs.getString("HISTOGRAM"           );
		COMMENTS             = rs.getString("COMMENTS"            );
	}
	public String getOWNER() {
		return OWNER;
	}
	public void setOWNER(String oWNER) {
		OWNER = oWNER;
	}
	public String getTABLE_NAME() {
		return TABLE_NAME;
	}
	public void setTABLE_NAME(String tABLE_NAME) {
		TABLE_NAME = tABLE_NAME;
	}
	public String getCOLUMN_NAME() {
		return COLUMN_NAME;
	}
	public void setCOLUMN_NAME(String cOLUMN_NAME) {
		COLUMN_NAME = cOLUMN_NAME;
	}
	public String getDATA_TYPE() {
		return DATA_TYPE;
	}
	public void setDATA_TYPE(String dATA_TYPE) {
		DATA_TYPE = dATA_TYPE;
	}
	public String getDATA_TYPE_MOD() {
		return DATA_TYPE_MOD;
	}
	public void setDATA_TYPE_MOD(String dATA_TYPE_MOD) {
		DATA_TYPE_MOD = dATA_TYPE_MOD;
	}
	public String getDATA_TYPE_OWNER() {
		return DATA_TYPE_OWNER;
	}
	public void setDATA_TYPE_OWNER(String dATA_TYPE_OWNER) {
		DATA_TYPE_OWNER = dATA_TYPE_OWNER;
	}
	public String getDATA_LENGTH() {
		return DATA_LENGTH;
	}
	public void setDATA_LENGTH(String dATA_LENGTH) {
		DATA_LENGTH = dATA_LENGTH;
	}
	public String getDATA_PRECISION() {
		return DATA_PRECISION;
	}
	public void setDATA_PRECISION(String dATA_PRECISION) {
		DATA_PRECISION = dATA_PRECISION;
	}
	public String getDATA_SCALE() {
		return DATA_SCALE;
	}
	public void setDATA_SCALE(String dATA_SCALE) {
		DATA_SCALE = dATA_SCALE;
	}
	public String getNULLABLE() {
		return NULLABLE;
	}
	public void setNULLABLE(String nULLABLE) {
		NULLABLE = nULLABLE;
	}
	public String getCOLUMN_ID() {
		return COLUMN_ID;
	}
	public void setCOLUMN_ID(String cOLUMN_ID) {
		COLUMN_ID = cOLUMN_ID;
	}
	public String getDEFAULT_LENGTH() {
		return DEFAULT_LENGTH;
	}
	public void setDEFAULT_LENGTH(String dEFAULT_LENGTH) {
		DEFAULT_LENGTH = dEFAULT_LENGTH;
	}
	public String getDATA_DEFAULT() {
		return DATA_DEFAULT;
	}
	public void setDATA_DEFAULT(String dATA_DEFAULT) {
		DATA_DEFAULT = dATA_DEFAULT;
	}
	public String getNUM_DISTINCT() {
		return NUM_DISTINCT;
	}
	public void setNUM_DISTINCT(String nUM_DISTINCT) {
		NUM_DISTINCT = nUM_DISTINCT;
	}
	public String getLOW_VALUE() {
		return LOW_VALUE;
	}
	public void setLOW_VALUE(String lOW_VALUE) {
		LOW_VALUE = lOW_VALUE;
	}
	public String getHIGH_VALUE() {
		return HIGH_VALUE;
	}
	public void setHIGH_VALUE(String hIGH_VALUE) {
		HIGH_VALUE = hIGH_VALUE;
	}
	public String getDENSITY() {
		return DENSITY;
	}
	public void setDENSITY(String dENSITY) {
		DENSITY = dENSITY;
	}
	public String getNUM_NULLS() {
		return NUM_NULLS;
	}
	public void setNUM_NULLS(String nUM_NULLS) {
		NUM_NULLS = nUM_NULLS;
	}
	public String getNUM_BUCKETS() {
		return NUM_BUCKETS;
	}
	public void setNUM_BUCKETS(String nUM_BUCKETS) {
		NUM_BUCKETS = nUM_BUCKETS;
	}
	public String getLAST_ANALYZED() {
		return LAST_ANALYZED;
	}
	public void setLAST_ANALYZED(String lAST_ANALYZED) {
		LAST_ANALYZED = lAST_ANALYZED;
	}
	public String getSAMPLE_SIZE() {
		return SAMPLE_SIZE;
	}
	public void setSAMPLE_SIZE(String sAMPLE_SIZE) {
		SAMPLE_SIZE = sAMPLE_SIZE;
	}
	public String getCHARACTER_SET_NAME() {
		return CHARACTER_SET_NAME;
	}
	public void setCHARACTER_SET_NAME(String cHARACTER_SET_NAME) {
		CHARACTER_SET_NAME = cHARACTER_SET_NAME;
	}
	public String getCHAR_COL_DECL_LENGTH() {
		return CHAR_COL_DECL_LENGTH;
	}
	public void setCHAR_COL_DECL_LENGTH(String cHAR_COL_DECL_LENGTH) {
		CHAR_COL_DECL_LENGTH = cHAR_COL_DECL_LENGTH;
	}
	public String getGLOBAL_STATS() {
		return GLOBAL_STATS;
	}
	public void setGLOBAL_STATS(String gLOBAL_STATS) {
		GLOBAL_STATS = gLOBAL_STATS;
	}
	public String getUSER_STATS() {
		return USER_STATS;
	}
	public void setUSER_STATS(String uSER_STATS) {
		USER_STATS = uSER_STATS;
	}
	public String getAVG_COL_LEN() {
		return AVG_COL_LEN;
	}
	public void setAVG_COL_LEN(String aVG_COL_LEN) {
		AVG_COL_LEN = aVG_COL_LEN;
	}
	public String getCHAR_LENGTH() {
		return CHAR_LENGTH;
	}
	public void setCHAR_LENGTH(String cHAR_LENGTH) {
		CHAR_LENGTH = cHAR_LENGTH;
	}
	public String getCHAR_USED() {
		return CHAR_USED;
	}
	public void setCHAR_USED(String cHAR_USED) {
		CHAR_USED = cHAR_USED;
	}
	public String getV80_FMT_IMAGE() {
		return V80_FMT_IMAGE;
	}
	public void setV80_FMT_IMAGE(String v80_FMT_IMAGE) {
		V80_FMT_IMAGE = v80_FMT_IMAGE;
	}
	public String getDATA_UPGRADED() {
		return DATA_UPGRADED;
	}
	public void setDATA_UPGRADED(String dATA_UPGRADED) {
		DATA_UPGRADED = dATA_UPGRADED;
	}
	public String getHISTOGRAM() {
		return HISTOGRAM;
	}
	public void setHISTOGRAM(String hISTOGRAM) {
		HISTOGRAM = hISTOGRAM;
	}
	public String getCOMMENTS() {
		return COMMENTS;
	}
	public void setCOMMENTS(String cOMMENTS) {
		COMMENTS = cOMMENTS;
	}
	
	

}
