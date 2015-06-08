package kr.pe.usee.ls;

import java.io.UnsupportedEncodingException;
//STEP 1. Import required packages
import java.sql.*;

public class MysqlDb {

	
	// JDBC driver name and database URL
 	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
 	static final String DB_URL = "jdbc:mysql://localhost/usee";

 	//  Database credentials
	static final String USER = "root";
	static final String PASS = "apmsetup";
 
	Connection conn = null;

	public Connection getConn() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {

		Class.forName("com.mysql.jdbc.Driver").newInstance();
		String connectionUrl = "jdbc:mysql://localhost:3306/usee?useUnicode=yes&characterEncoding=UTF-8";
		String connectionUser = "root";
		String connectionPassword = "apmsetup";
		conn = DriverManager.getConnection(connectionUrl, connectionUser, connectionPassword);
	    return conn;
	}

	public void closeConn() throws SQLException {
		 
		conn.close();
		try
		{
			if(conn!=null)
				conn.close();
		}catch(SQLException se){
		       se.printStackTrace();
		}//end finally try
		System.out.println("Goodbye!");

	}
	
	public void upsert(ListRow [] list) throws SQLException, UnsupportedEncodingException {
		//conn.setAutoCommit(false);
		TobeTable tobe;
		TobeTable tobeDb;
		AsisTable asisDb;
		
	//	try 
		{
			for(ListRow row : list) {
				tobe = row.getTobeTable();
				tobeDb = selectTobeFromDb(conn, tobe);
				if(tobeDb== null) {
					insertTobe(conn, row);
				} else {
					updateTobe(conn, row);
				}
				
				for(AsisTable asisTable : row.getAsisTables()) {
					asisDb = selectAsisFromDb(conn, asisTable);
					if(asisDb == null) {
						insertAsis(conn,asisTable);
					} else {
						updateAsis(conn,asisTable);
					}

					upsertMap(conn,tobe,asisTable);	
				}
				
			}		  
		}/* catch (SQLException se) {
			System.out.println(se.getErrorCode() + ":" +se.getMessage());
			conn.rollback();
		}*/
		//conn.commit();
		//conn.setAutoCommit(true);
	}
	 
	private void upsertMap(Connection conn, TobeTable tobeTable, AsisTable asisTable) throws SQLException {
		String sqlStmt = "SELECT 'YES' FROM toas WHERE `toSystem` = ? and `toSchema` = ? and `toTable`=? and `asSystem` = ? and `asSchema` = ? and `asTable`=?";
		PreparedStatement prepStmt = conn.prepareStatement(sqlStmt);
		prepStmt.setString(1 ,tobeTable.getSystem     () );
		prepStmt.setString(2 ,tobeTable.getSchema     () );
		prepStmt.setString(3 ,tobeTable.getTable      () );
		prepStmt.setString(4 ,asisTable.getSystem     () );
		prepStmt.setString(5 ,asisTable.getSchema     () );
		prepStmt.setString(6 ,asisTable.getTable      () );
		ResultSet rs = prepStmt.executeQuery();
		
		if(rs.next()) {
		} else {
			String sql = "INSERT INTO `toas` VALUES (?,?,?,?,?,?)";
		    PreparedStatement stmt = conn.prepareStatement(sql);
		    stmt.setString(1 ,tobeTable.getSystem     () );
		    stmt.setString(2 ,tobeTable.getSchema     () );
		    stmt.setString(3 ,tobeTable.getTable      () );
		    stmt.setString(4 ,asisTable.getSystem     () );
		    stmt.setString(5 ,asisTable.getSchema     () );
		    stmt.setString(6 ,asisTable.getTable      () );
		    int result = stmt.executeUpdate();
		}
	}

	private int updateAsis(Connection conn, AsisTable asisTable) throws SQLException {
		//String sqlStmt = "SELECT `system`, `schema`, `table`, `apCode`, `comment`, `relation`, `cdVsTr`, `migRange`, `incCnt`, `incUnit`, `bizNonCmplt`, `cleanYn`, `cleanDueDt`, `cleanOwner`, `mapDefDueDt`, `fillupOwner`, `fillupDueDt`, `migSqlDueDt`, `migWay`, `ver` FROM `tobetable` WHERE  1 = 1 and `system` = ? and `schema` = ? and `table` = ? and ver = ?";
		String sqlStmt = "UPDATE `asistable` SET `system`=?, `schema`=?, `table`=?, `comment`=? WHERE `system` = ? and `schema` = ? and `table`=?";
		System.out.println("SQL Statement:\n\t" + sqlStmt);
		PreparedStatement prepStmt = conn.prepareStatement(sqlStmt);
		System.out.println("Prepared Statement before bind variables set:\n\t" + prepStmt.toString());
		prepStmt.setString(1 ,asisTable.getSystem     () );
		prepStmt.setString(2 ,asisTable.getSchema     () );
		prepStmt.setString(3 ,asisTable.getTable      () );
		prepStmt.setString(4 ,asisTable.getComment    () );
		prepStmt.setString(5 ,asisTable.getSystem());
		prepStmt.setString(6 ,asisTable.getSchema());
		prepStmt.setString(7 ,asisTable.getTable());
	    //stmt.setString(24,row.getTobeTable().getVersion());
		System.out.println("Prepared Statement after bind variables set:\n\t" + prepStmt.toString());
		int result = prepStmt.executeUpdate();
		return result;
	}

	private int insertAsis(Connection conn, AsisTable asisTable) throws SQLException {
		String sql = "INSERT INTO `asistable` (`system`, `schema`, `table`, `comment`) VALUES (?,?,?,?)";
	    PreparedStatement stmt = conn.prepareStatement(sql);
	    stmt.setString(1 ,asisTable.getSystem     () );
	    stmt.setString(2 ,asisTable.getSchema     () );
	    stmt.setString(3 ,asisTable.getTable      () );
	    stmt.setString(4 ,asisTable.getComment    () );
	    int result = stmt.executeUpdate();
	    
		return result;
	}

	private int updateTobe(Connection conn, ListRow row) throws SQLException {
		//String sqlStmt = "SELECT `system`, `schema`, `table`, `apCode`, `comment`, `relation`, `cdVsTr`, `migRange`, `incCnt`, `incUnit`, `bizNonCmplt`, `cleanYn`, `cleanDueDt`, `cleanOwner`, `mapDefDueDt`, `fillupOwner`, `fillupDueDt`, `migSqlDueDt`, `migWay`, `ver` FROM `tobetable` WHERE  1 = 1 and `system` = ? and `schema` = ? and `table` = ? and ver = ?";
		String sqlStmt = "UPDATE `tobetable` SET `system`=?, `schema`=?, `table`=?, `apCode`=?, `comment`=?, `relation`=?, `cdVsTr`=?, `migRange`=?, `incCnt`=?, `incUnit`=?, `bizNonCmplt`=?, `cleanYn`=?, `cleanDueDt`=?, `cleanOwner`=?, `mapDefDueDt`=?, `fillupOwner`=?, `fillupDueDt`=?, `migSqlDueDt`=?, `migWay`=? WHERE `system` = ? and `schema` = ? and `table` = ?";;
		System.out.println("SQL Statement:\n\t" + sqlStmt);
		PreparedStatement prepStmt = conn.prepareStatement(sqlStmt);
		System.out.println("Prepared Statement before bind variables set:\n\t" + prepStmt.toString());
		prepStmt.setString(1 ,row.getTobeTable().getSystem     () );
		prepStmt.setString(2 ,row.getTobeTable().getSchema     () );
		prepStmt.setString(3 ,row.getTobeTable().getTable      () );
		prepStmt.setString(4 ,row.getTobeTable().getApCode     () );
		prepStmt.setString(5 ,row.getTobeTable().getComment    () );
		prepStmt.setString(6 ,row.getTobeTable().getRelation   () );
		prepStmt.setString(7 ,row.getTobeTable().getCdVsTr     () );
		prepStmt.setString(8 ,row.getTobeTable().getMigRange   () );
		prepStmt.setString(9 ,row.getTobeTable().getIncCnt     () );
		prepStmt.setString(10,row.getTobeTable().getIncUnit    () );
		prepStmt.setString(11,row.getTobeTable().getBizNonCmplt() );
		prepStmt.setString(12,row.getTobeTable().getCleanYn    () );
		prepStmt.setString(13,row.getTobeTable().getCleanDueDt () );
		prepStmt.setString(14,row.getTobeTable().getCleanOwner () );
		prepStmt.setString(15,row.getTobeTable().getMapDefDueDt() );
		prepStmt.setString(16,row.getTobeTable().getFillupOwner() );
		prepStmt.setString(17,row.getTobeTable().getFillupDueDt() );
		prepStmt.setString(18,row.getTobeTable().getMigSqlDueDt() );
		prepStmt.setString(19,row.getTobeTable().getMigWay     () );
	    //stmt.setString(20,row.getTobeTable().getVersion    () );
		prepStmt.setString(20,row.getTobeTable().getSystem());
		prepStmt.setString(21,row.getTobeTable().getSchema());
		prepStmt.setString(22,row.getTobeTable().getTable());
	    //stmt.setString(24,row.getTobeTable().getVersion());
		System.out.println("Prepared Statement after bind variables set:\n\t" + prepStmt.toString());
		int result = prepStmt.executeUpdate();
		return result;
	}

	private int insertTobe(Connection conn, ListRow row) throws SQLException {

		String sql = "INSERT INTO `tobetable` (`system`, `schema`, `table`, `apCode`, `comment`, `relation`, `cdVsTr`, `migRange`, `incCnt`, `incUnit`, `bizNonCmplt`, `cleanYn`, `cleanDueDt`, `cleanOwner`, `mapDefDueDt`, `fillupOwner`, `fillupDueDt`, `migSqlDueDt`, `migWay`, `ver`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	    PreparedStatement stmt = conn.prepareStatement(sql);
	    stmt.setString(1 ,row.getTobeTable().getSystem     () );
	    stmt.setString(2 ,row.getTobeTable().getSchema     () );
	    stmt.setString(3 ,row.getTobeTable().getTable      () );
	    stmt.setString(4 ,row.getTobeTable().getApCode     () );
	    stmt.setString(5 ,row.getTobeTable().getComment    () );
	    stmt.setString(6 ,row.getTobeTable().getRelation   () );
	    stmt.setString(7 ,row.getTobeTable().getCdVsTr     () );
	    stmt.setString(8 ,row.getTobeTable().getMigRange   () );
	    stmt.setString(9 ,row.getTobeTable().getIncCnt     () );
	    stmt.setString(10,row.getTobeTable().getIncUnit    () );
	    stmt.setString(11,row.getTobeTable().getBizNonCmplt() );
	    stmt.setString(12,row.getTobeTable().getCleanYn    () );
	    stmt.setString(13,row.getTobeTable().getCleanDueDt () );
	    stmt.setString(14,row.getTobeTable().getCleanOwner () );
	    stmt.setString(15,row.getTobeTable().getMapDefDueDt() );
	    stmt.setString(16,row.getTobeTable().getFillupOwner() );
	    stmt.setString(17,row.getTobeTable().getFillupDueDt() );
	    stmt.setString(18,row.getTobeTable().getMigSqlDueDt() );
	    stmt.setString(19,row.getTobeTable().getMigWay     () );
	    stmt.setString(20,row.getTobeTable().getVersion    () );
	    int result = stmt.executeUpdate();
	    
		return result;
	}

	private AsisTable selectAsisFromDb(Connection conn, AsisTable asis) throws SQLException {
	    AsisTable asisData = null;

		//String sqlStmt = "SELECT * FROM employees where last_name=? and first_name like ?";
		String sqlStmt = "SELECT `system`, `schema`, `table`, `comment` FROM `asistable` WHERE  1 = 1 and `system` = ? and `schema` = ? and `table` = ?";
		System.out.println("SQL Statement:\n\t" + sqlStmt);
		PreparedStatement prepStmt = conn.prepareStatement(sqlStmt);
		System.out.println("Prepared Statement before bind variables set:\n\t" + prepStmt.toString());
		prepStmt.setString(1, asis.getSystem());
		prepStmt.setString(2, asis.getSchema());
		prepStmt.setString(3, asis.getTable());
		System.out.println("Prepared Statement after bind variables set:\n\t" + prepStmt.toString());
		ResultSet rs = prepStmt.executeQuery();
	    if(rs.next()) {
	    	asisData = new AsisTable(rs);
	    } else {
	    	
	    }
	    rs.close();
	    prepStmt.close();
	    return asisData;
	}

	public TobeTable selectTobeFromDb(Connection conn, TobeTable tobe) throws SQLException, UnsupportedEncodingException {
	    TobeTable tobeData = null;

		//String sqlStmt = "SELECT * FROM employees where last_name=? and first_name like ?";
		String sqlStmt = "SELECT `system`, `schema`, `table`, `apCode`, `comment`, `relation`, `cdVsTr`, `migRange`, `incCnt`, `incUnit`, `bizNonCmplt`, `cleanYn`, `cleanDueDt`, `cleanOwner`, `mapDefDueDt`, `fillupOwner`, `fillupDueDt`, `migSqlDueDt`, `migWay`, `ver` FROM `tobetable` WHERE  1 = 1 and `system` = ? and `schema` = ? and `table` = ? and ver = ?";
		System.out.println("SQL Statement:\n\t" + sqlStmt);
		PreparedStatement prepStmt = conn.prepareStatement(sqlStmt);
		System.out.println("Prepared Statement before bind variables set:\n\t" + prepStmt.toString());
		prepStmt.setString(1, tobe.getSystem());
		prepStmt.setString(2, tobe.getSchema());
		prepStmt.setString(3, tobe.getTable());
		prepStmt.setString(4, tobe.getVersion());
		System.out.println("Prepared Statement after bind variables set:\n\t" + prepStmt.toString());
		ResultSet rs = prepStmt.executeQuery();
	    if(rs.next()) {
	    	tobeData = new TobeTable(rs);
	    } else {
	    	
	    }
	    rs.close();
	    prepStmt.close();
	    return tobeData;
	}
}//end FirstExample
