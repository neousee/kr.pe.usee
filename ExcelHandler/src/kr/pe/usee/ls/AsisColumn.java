package kr.pe.usee.ls;

import java.io.Serializable;

public class AsisColumn implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String system  ;
	String schema  ;
	String table   ;
	String column  ;
	String nullable;
	String type    ;
	String comment ;
	String version ;
	
	public boolean isEmpty() {
		if( system == null && schema == null &&table == null &&column == null &&nullable == null &&type == null &&comment == null )
			return true;
		else 
			return false;
	}
	
	public String getSystem() {
		if(system == null)
			return "";
		return system;
	}
	public void setSystem(String system) {
		this.system = system;
	}
	public String getSchema() {
		if(schema==null)
			return "";
		return schema;
	}
	public void setSchema(String schema) {
		this.schema = schema;
	}
	public String getTable() {
		if(table == null) return "";
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
	public String getColumn() {
		return column;
	}
	public void setColumn(String column) {
		this.column = column;
	}
	public String getNullable() {
		return nullable;
	}
	public void setNullable(String nullable) {
		this.nullable = nullable;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String ver) {
		version = ver;
	}
}
