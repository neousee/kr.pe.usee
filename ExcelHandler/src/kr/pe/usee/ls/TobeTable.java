package kr.pe.usee.ls;

import java.io.Serializable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TobeTable implements Serializable {
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = -8311204744573647331L;
	
	String system ;
	String schema ;
	String table ;
	String apCode ;
	String comment ;
	String relation ;
	String cdVsTr ;
	String migRange ;
	String incCnt ;
	String incUnit ;
	String bizNonCmplt ;
	String cleanYn ;
	String cleanDueDt ;
	String cleanOwner ;
	String mapDefDueDt ;
	String fillupOwner ;
	String fillupDueDt ;
	String migSqlDueDt ;
	String migWay ;
	String version ;
	ArrayList<AsisTable> asisTables = new ArrayList<AsisTable>();
	
	public TobeTable() {
		
	}
	
	public TobeTable(ResultSet rs) throws SQLException {
		setTobeDataFromResult(rs);
	}
	
	public TobeTable(ResultSet rs, int i) throws SQLException {
		system      = rs.getString(1 ) == null ? "" :rs.getString(1 );
		schema      = rs.getString(2 ) == null ? "" :rs.getString(2 );
		table       = rs.getString(3 ) == null ? "" :rs.getString(3 );
		apCode      = rs.getString(4 );
		comment     = rs.getString(5 );
		relation    = rs.getString(6 );
		cdVsTr      = rs.getString(7 );
		migRange    = rs.getString(8 );
		incCnt      = rs.getString(9 );
		incUnit     = rs.getString(10);
		bizNonCmplt = rs.getString(11);
		cleanYn     = rs.getString(12);
		cleanDueDt  = rs.getString(13);
		cleanOwner  = rs.getString(14);
		mapDefDueDt = rs.getString(15);
		fillupOwner = rs.getString(16);
		fillupDueDt = rs.getString(17);
		migSqlDueDt = rs.getString(18);
		migWay      = rs.getString(19);
		version     = rs.getString(20);
	
	}

	public void setTobeDataFromResult(ResultSet rs) throws SQLException {
		if(rs.next()) {
			system      = rs.getString(1 ) == null ? "" :rs.getString(1 );
			schema      = rs.getString(2 ) == null ? "" :rs.getString(2 );
			table       = rs.getString(3 ) == null ? "" :rs.getString(3 );
			apCode      = rs.getString(4 );
			comment     = rs.getString(5 );
			relation    = rs.getString(6 );
			cdVsTr      = rs.getString(7 );
			migRange    = rs.getString(8 );
			incCnt      = rs.getString(9 );
			incUnit     = rs.getString(10);
			bizNonCmplt = rs.getString(11);
			cleanYn     = rs.getString(12);
			cleanDueDt  = rs.getString(13);
			cleanOwner  = rs.getString(14);
			mapDefDueDt = rs.getString(15);
			fillupOwner = rs.getString(16);
			fillupDueDt = rs.getString(17);
			migSqlDueDt = rs.getString(18);
			migWay      = rs.getString(19);
			version     = rs.getString(20);
		}
	}
	
	public String getSystem() {
		if(system==null) return "";
		return system;
	}
	public void setSystem(String system) {
		this.system = system;
	}
	public String getSchema() {
		if(schema==null) return "";
		return schema;
	}
	public void setSchema(String schema) {
		this.schema = schema;
	}
	public String getTable() {
		if(table==null) return "";
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
	public String getApCode() {
		return apCode;
	}
	public void setApCode(String apCode) {
		this.apCode = apCode;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	public String getCdVsTr() {
		return cdVsTr;
	}
	public void setCdVsTr(String cdVsTr) {
		this.cdVsTr = cdVsTr;
	}
	public String getMigRange() {
		return migRange;
	}
	public void setMigRange(String migRange) {
		this.migRange = migRange;
	}
	public String getIncCnt() {
		return incCnt;
	}
	public void setIncCnt(String incCnt) {
		this.incCnt = incCnt;
	}
	public String getIncUnit() {
		return incUnit;
	}
	public void setIncUnit(String incUnit) {
		this.incUnit = incUnit;
	}
	public String getBizNonCmplt() {
		return bizNonCmplt;
	}
	public void setBizNonCmplt(String bizNonCmplt) {
		this.bizNonCmplt = bizNonCmplt;
	}
	public String getCleanYn() {
		return cleanYn;
	}
	public void setCleanYn(String cleanYn) {
		this.cleanYn = cleanYn;
	}
	public String getCleanDueDt() {
		return cleanDueDt;
	}
	public void setCleanDueDt(String cleanDueDt) {
		this.cleanDueDt = cleanDueDt;
	}
	public String getCleanOwner() {
		return cleanOwner;
	}
	public void setCleanOwner(String cleanOwner) {
		this.cleanOwner = cleanOwner;
	}
	public String getMapDefDueDt() {
		return mapDefDueDt;
	}
	public void setMapDefDueDt(String mapDefDueDt) {
		this.mapDefDueDt = mapDefDueDt;
	}
	public String getFillupOwner() {
		return fillupOwner;
	}
	public void setFillupOwner(String fillupOwner) {
		this.fillupOwner = fillupOwner;
	}
	public String getFillupDueDt() {
		return fillupDueDt;
	}
	public void setFillupDueDt(String fillupDueDt) {
		this.fillupDueDt = fillupDueDt;
	}
	public String getMigSqlDueDt() {
		return migSqlDueDt;
	}
	public void setMigSqlDueDt(String migSqlDueDt) {
		this.migSqlDueDt = migSqlDueDt;
	}
	public String getMigWay() {
		return migWay;
	}
	public void setMigWay(String migWay) {
		this.migWay = migWay;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ArrayList<AsisTable> getAsisTables() {
		return asisTables;
	}

	public void setAsisTables(ArrayList<AsisTable> asisTables) {
		this.asisTables = asisTables;
	}

	public void addAsis(AsisTable asisTable) {
		this.asisTables.add(asisTable);		
	}
	
	
	 
}
