package kr.pe.usee.excel;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OracleDb {

	String DB_URL = "jdbc:oracle:thin:@10.61.9.1:1521:LSG";
	String DB_USER = "acnco01";
	String DB_PASSWORD = "acnco01";	

	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;
	String query = null;
	

	public void insertCgProdKbf(Kbf kbf) throws SQLException, IOException {

			
		String sql2 = "insert into USCM.CG_PROD_KBF VALUES(?,?,?,?,?,SYSDATE,?,SYSDATE,?)";
		String sql1 = "commit";
		
			PreparedStatement prepStmt = conn.prepareStatement(sql2);
			
			prepStmt.setString(1, kbf.getProdCd());
			prepStmt.setString(2, kbf.getKbfAttrCd());
			prepStmt.setString(3, null);
			prepStmt.setString(4, "Y");
			prepStmt.setString(5, "ACNMIG");
			prepStmt.setString(6, "ACNMIG");
			prepStmt.setString(7, kbf.getKbfAttrVal());
			//System.out.println("is running of "+dy+" ......");
			//prepStmt.addBatch(sql1);
			int result = prepStmt.executeUpdate();
			prepStmt.close();
			
			//System.out.println("is done of "+dy+" ......");
			
		
	}
	

	public OracleDb() throws ClassNotFoundException {

		Class.forName("oracle.jdbc.driver.OracleDriver");
		// 데이터베이스의 연결을 설정한다.
	}
	public void connect() {
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void closeConn() {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
}
