package kr.pe.usee.ls;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AsisTable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2688604608084821935L;
	
	String system ;
	String schema ;
	String table ;
	String comment ;
	
	public AsisTable() {
		
	}
	
	public AsisTable(ResultSet rs) throws SQLException {
		setAsisDataFromResult(rs);
	}
	
	public void setAsisDataFromResult(ResultSet rs) throws SQLException {
		if(rs.next()) {
			system      = rs.getString(1 );
			schema      = rs.getString(2 );
			table       = rs.getString(3 );
			comment     = rs.getString(4 );
		
		}
	}

	public String getSystem() {
		return system;
	}
	public void setSystem(String system) {
		this.system = system;
	}
	public String getSchema() {
		return schema;
	}
	public void setSchema(String schema) {
		this.schema = schema;
	}
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}

}
