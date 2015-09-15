package kr.pe.usee.ls;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;

public class MigListDB implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	// JDBC driver name and database URL
 	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
  
	Connection conn = null;

	String connectionUrl = "jdbc:mysql://localhost:3306/usee?useUnicode=yes&characterEncoding=UTF-8";
	String connectionUser = "root";
	String connectionPassword = "apmsetup";

	String version;
	public MigListDB() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		conn = getConn();
	}
	

	private Connection getConn() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {

		Class.forName(JDBC_DRIVER).newInstance();
		conn = DriverManager.getConnection(this.connectionUrl, this.connectionUser, this.connectionPassword);
	    return conn;
	}

	/**
	 * SELECT a.*, b.* FROM toas a, asistable b WHERE b.system = a.assystem and b.schema = a.asschema and b.table = a.astable
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<MapList> getMapListAll() throws SQLException {
		String sqlStmt = "SELECT a.*, b.* FROM toas a, asistable b WHERE b.system = a.assystem and b.schema = a.asschema and b.table = a.astable";
		PreparedStatement prepStmt = conn.prepareStatement(sqlStmt);
		ResultSet rs = prepStmt.executeQuery();
		ArrayList<MapList> listArray = new ArrayList<MapList>();
		
		while(rs.next()) {
			listArray.add(new MapList(rs));
		}
		rs.close();
		prepStmt.close();
		return listArray;
	}

	/**
	 * SELECT * FROM tobetable WHERE ver = ?
	 */
	public ArrayList<TobeTable> getTobeListFromDb() throws SQLException {
		String sqlStmt = "SELECT * FROM tobetable WHERE ver = ?";
		PreparedStatement prepStmt = conn.prepareStatement(sqlStmt);
		prepStmt.setString(1,getVersion());
		ResultSet rs = prepStmt.executeQuery();
		ArrayList<TobeTable> tables = new ArrayList<TobeTable>();
		while(rs.next()) {
			tables.add(new TobeTable(rs,1));
		}
		rs.close();
		prepStmt.close();
		return tables;
	}

	public ArrayList<TobeTable> getMapTable() throws SQLException {
		ArrayList<TobeTable> tobeTables = getTobeListFromDb();
		ArrayList<MapList> mapList = getMapListAll();
		
		for(TobeTable tobe:tobeTables) {
			for(MapList map:mapList) {
				if( tobe.getSystem().concat(tobe.getSchema()).concat(tobe.getTable()).equals( map.getToSystem().concat(map.getToSchema()).concat(map.getToTable()) )) {
					tobe.addAsis(new AsisTable(map.getAsSystem(),map.getAsSchema(),map.getAsTable(),map.getAsComment()));
				}
			}
		}
		
		return tobeTables;
	}
	
	/**
	 * Tobe Table list를 ArrayList로 반환
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<TobeTable> getTobeList(ResultSet rs) throws SQLException {
		ArrayList<TobeTable> tables = new ArrayList<TobeTable>();
		while(rs.next()) {
			tables.add(new TobeTable(rs));
		}
		rs.close();
		return tables;
	}
	
	/**
	 * @param row
	 * @throws SQLException
	 */
	public void insert(TestSenarioListRow row) throws SQLException {
		String sql = "INSERT INTO `lsgs_ut` VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	    PreparedStatement stmt = conn.prepareStatement(sql);
	    stmt.setString(1  ,row.getSid                 () );
	    stmt.setString(2  ,row.getSname               () );
	    stmt.setString(3  ,row.getItem                () );
	    stmt.setString(4  ,row.getSenario             () );
	    stmt.setString(5  ,row.getExp_result          () );
	    stmt.setString(6  ,row.getDesigner            () );
	    stmt.setString(7  ,row.getFst_tester          () );
	    stmt.setString(8  ,row.getFst_test_dt         () );
	    stmt.setString(9  ,row.getFst_test_result     () );
	    stmt.setString(10 ,row.getFst_test_err_grade  () );
	    stmt.setString(11 ,row.getFst_test_err        () );
	    stmt.setString(12 ,row.getFst_test_comment    () );
	    stmt.setString(13 ,row.getFst_test_fix_dt     () );
	    stmt.setString(14 ,row.getFst_test_fixer      () );
	    stmt.setString(15 ,row.getFst_test_fix_item   () );
	    stmt.setString(16 ,row.getFst_test_fix_comment() );
	    stmt.setString(17 ,row.getScd_tester          () );
	    stmt.setString(18 ,row.getScd_test_dt         () );
	    stmt.setString(19 ,row.getScd_test_result     () );
	    stmt.setString(20 ,row.getScd_test_err_grade  () );
	    stmt.setString(21 ,row.getScd_test_err        () );
	    stmt.setString(22 ,row.getScd_test_comment    () );
	    stmt.setString(23 ,row.getScd_test_fix_dt     () );
	    stmt.setString(24 ,row.getScd_test_fixer      () );
	    stmt.setString(25 ,row.getScd_test_fix_item   () );
	    stmt.setString(26 ,row.getScd_test_fix_comment() );
	    stmt.setString(27 ,row.getVersion             () );
	    int result = stmt.executeUpdate();
	    stmt.close();

	}

	public void close() throws SQLException {
		conn.close();
	}

	public void setVersion(String ver) {
		this.version = ver;		
	}
	
	public String getVersion() {
		return this.version;
	}
}
