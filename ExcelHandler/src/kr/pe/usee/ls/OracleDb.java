package kr.pe.usee.ls;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;

public class OracleDb implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";

	String connectionUrl = "jdbc:oracle:thin:@10.61.2.41:1521:LSG";
	String connectionUser = "acnmig01";
	String connectionPassword = "acnmig01";
	
	Connection conn;
	String teamCd;
	String option;
	
	public OracleDb() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		conn = getConn();
	}
	
	public Connection getConn() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {

		Class.forName(JDBC_DRIVER).newInstance();
		return DriverManager.getConnection(this.connectionUrl, this.connectionUser, this.connectionPassword);
	    
	}
	
	public Hashtable<String, InfoOfTable> getInfoOfColumn() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		return getInfoOfColumn(Integer.parseInt(this.option), this.teamCd);
	}
	

	public Hashtable<String, InfoOfTable> getInfoOfColumn(int option, String cd) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		String sqlStmt = ""
				+ " SELECT a.OWNER,a.TABLE_NAME,a.COLUMN_NAME,a.DATA_TYPE,a.DATA_TYPE_MOD,a.DATA_TYPE_OWNER,a.DATA_LENGTH,a.DATA_PRECISION,a.DATA_SCALE,a.NULLABLE,a.COLUMN_ID,a.DEFAULT_LENGTH,a.DATA_DEFAULT,a.NUM_DISTINCT,a.LOW_VALUE,a.HIGH_VALUE,a.DENSITY,a.NUM_NULLS,a.NUM_BUCKETS,a.LAST_ANALYZED,a.SAMPLE_SIZE,a.CHARACTER_SET_NAME,a.CHAR_COL_DECL_LENGTH,a.GLOBAL_STATS,a.USER_STATS,a.AVG_COL_LEN,a.CHAR_LENGTH,a.CHAR_USED,a.V80_FMT_IMAGE,a.DATA_UPGRADED,a.HISTOGRAM,b.COMMENTS  "
				+ " FROM all_tab_columns a, all_col_comments b"                                                                                                                                                                                                                                                                                                                                                                                                                                                         
				+ " WHERE 1 = 1"
				+ " AND a.owner = b.owner"
				+ " AND a.table_name = b.table_name"
				+ " AND a.column_name = b.column_name"
				+ " AND a.owner IN ('USCM','USCU')";
		switch(option) 
		{
		case 1 : sqlStmt += " AND a.table_name = ?"; break;
		case 2 : sqlStmt += " AND a.table_name LIKE ?"; break;
		default : break;
		}
		Connection conn = getConn();
		PreparedStatement prepStmt = conn.prepareStatement(sqlStmt);
		switch(option) {
		case 1 : prepStmt.setString(1 ,cd ); break;
		case 2 : prepStmt.setString(1 ,cd +"%"); break;
		default : break;
		}
		
		ResultSet rs = prepStmt.executeQuery();
		Hashtable<String, InfoOfTable> info = new Hashtable<String, InfoOfTable>();
		ArrayList<String> tableList = new ArrayList<String>();
		InfoOfTable tableInfo = null;
		InfoOfColumn colInfo;
		while(rs.next()) {
			colInfo = new InfoOfColumn(rs);
			// first column info of table
			if(!tableList.contains(colInfo.getTABLE_NAME())) {
				if(tableInfo!=null) info.put(tableInfo.getTableName(), tableInfo);
				tableList.add(colInfo.getTABLE_NAME());
				tableInfo = new InfoOfTable(colInfo.getTABLE_NAME());
				tableInfo.addColInfo(colInfo);
			} else {
				tableInfo.addColInfo(colInfo);
			}
			
			
			//info.put(colInfo.getTABLE_NAME(), colInfo);
			
		}
		info.put(tableInfo.getTableName(), tableInfo);
		
		try{rs.close();} catch(SQLException se) { }
		try{prepStmt.close();} catch(SQLException se) { }
		try{conn.close();} catch(SQLException se) { }
		
		return info;
	}
	
	
	public String getTeamCd() {
		return teamCd;
	}

	public void setTeamCd(String teamCd) {
		this.teamCd = teamCd;
	}

	
	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public static void main(String args[]) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		OracleDb db = new OracleDb();
		db.setOption("2");
		db.setTeamCd("PU");
		Hashtable<String, InfoOfTable> in = db.getInfoOfColumn();
		
		System.out.print(in);
	}
	

}
