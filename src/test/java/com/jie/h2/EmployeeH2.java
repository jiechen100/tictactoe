package com.jie.h2;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeH2 {
	public static void main(String[] args) {
		testH2();
	}

	static void testH2() {
		Connection conn = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;

		try {
			Class.forName("org.h2.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			boolean isTestDb = true;

			String jdbc = isTestDb ? "jdbc:h2:tcp://localhost/~/test"
					: "jdbc:h2:tcp://localhost/~/source";
			String sql = isTestDb ? "select * from employee order by id"
					: "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES";
			
			conn = DriverManager.getConnection(jdbc, "sa", "");

			cstmt = conn.prepareCall(sql);
			rs = cstmt.executeQuery();
			while (rs.next()) {
				if (isTestDb) {
					System.out.println(rs.getInt(1) + "," + rs.getString(2));
				} else {
					System.out.println(rs.getString(1));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (cstmt != null) {
					cstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
