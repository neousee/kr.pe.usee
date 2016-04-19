package kr.pe.usee.ls;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MapList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String toSystem ;
	String toSchema ;
	String toTable ;
	String asSystem ;
	String asSchema ;
	String asTable ;
	String asComment;
	
	public MapList(ResultSet rs) throws SQLException {
		toSystem  = rs.getString("toSystem"  );
		toSchema  = rs.getString("toSchema"  );
		toTable   = rs.getString("toTable"   );
		asSystem  = rs.getString("asSystem"  );
		asSchema  = rs.getString("asSchema"  );
		asTable   = rs.getString("asTable"   );
		asComment = rs.getString("comment"   );

	}
	
	public String getToSystem() {
		return toSystem;
	}
	public void setToSystem(String toSystem) {
		this.toSystem = toSystem;
	}
	public String getToSchema() {
		return toSchema;
	}
	public void setToSchema(String toSchema) {
		this.toSchema = toSchema;
	}
	public String getToTable() {
		return toTable;
	}
	public void setToTable(String toTable) {
		this.toTable = toTable;
	}
	public String getAsSystem() {
		return asSystem;
	}
	public void setAsSystem(String asSystem) {
		this.asSystem = asSystem;
	}
	public String getAsSchema() {
		return asSchema;
	}
	public void setAsSchema(String asSchema) {
		this.asSchema = asSchema;
	}
	public String getAsTable() {
		return asTable;
	}
	public void setAsTable(String asTable) {
		this.asTable = asTable;
	}
	public String getAsComment() {
		return asComment;
	}
	public void setAsComment(String asComment) {
		this.asComment = asComment;
	}


}
