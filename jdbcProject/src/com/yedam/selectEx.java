package com.yedam;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class selectEx {
	public static void main(String[] args) {
		// jdbc 드라이버 체크
		String url = "jdbc:oracle:thin:@192.168.0.62:1521/xe";
		String user = "proj";
		String pass = "proj";
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			Connection conn = DriverManager.getConnection(url, user, pass);

			// db 쿼리실행 < - > 결과
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from tbl_users");
			while (rs.next()) {
				System.out.println(rs.getString("user_id") + 
								"," + rs.getString("user_name") + 
								"," + rs.getDate("user_birth") + 
								"," + rs.getString("user_addr"));
			}
			System.out.println("end of data");
			conn.close();
			rs.close();
			stmt.close();
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}finally {
			
		}
	}
}
