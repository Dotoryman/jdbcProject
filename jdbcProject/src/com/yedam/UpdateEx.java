package com.yedam;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class UpdateEx {
	public static void main(String[] args) {
		
		Scanner scn = new Scanner(System.in);
		System.out.printf("ID 를 입력하세요");
		String id = scn.nextLine();
		System.out.printf("PW 를 입력하세요");
		String pw = scn.nextLine();
		System.out.printf("주소 를 입력하세요");
		String addr = scn.nextLine();
		
		// jdbc 드라이버 체크
		String url = "jdbc:oracle:thin:@localhost:1521/xe";
		String user = "proj";
		String pass = "proj";

		Connection conn = null;
		PreparedStatement psmt = null;
		String sql = "update tbl_users"
				+ " set user_pw = ? ,"
				+ "     user_addr = ?"
				+ " where user_id = ?" ;
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			conn = DriverManager.getConnection(url, user, pass);
			
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, pw);
			psmt.setString(2, addr);
			psmt.setString(3, id);
			
			int r = psmt.executeUpdate();
			if(r > 0) {
				System.out.println("처리성공");
			} else{
				System.out.println("처리실패");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				psmt.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("End of prog");
	}
}
