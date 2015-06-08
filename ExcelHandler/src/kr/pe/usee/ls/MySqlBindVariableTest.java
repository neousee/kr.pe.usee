package kr.pe.usee.ls;
/*****************************************************************/
/* Copyright 2013 Code Strategies                                */
/* This code may be freely used and distributed in any project.  */
/* However, please do not remove this credit if you publish this */
/* code in paper or electronic form, such as on a web site.      */
/*****************************************************************/

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySqlBindVariableTest {
	public static void main(String[] args) {
		Connection conn = null;
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String connectionUrl = "jdbc:mysql://localhost:3306/usee";
			String connectionUser = "root";
			String connectionPassword = "apmsetup";
			conn = DriverManager.getConnection(connectionUrl, connectionUser, connectionPassword);
			String sqlStmt = "SELECT * FROM employees where last_name=? and first_name like ?";
			System.out.println("SQL Statement:\n\t" + sqlStmt);
			prepStmt = conn.prepareStatement(sqlStmt);
			System.out.println("Prepared Statement before bind variables set:\n\t" + prepStmt.toString());
			prepStmt.setString(1, "Doe");
			prepStmt.setString(2, "%n%");
			System.out.println("Prepared Statement after bind variables set:\n\t" + prepStmt.toString());
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				String id = rs.getString("id");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				System.out.println("ID: " + id + ", First Name: " + firstName + ", Last Name: " + lastName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (prepStmt != null)
					prepStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}

