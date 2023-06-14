package com.yedam;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class InsertEx {
	public static void main(String[] args) {
		
		Scanner scn = new Scanner(System.in);
		System.out.printf("ID 를 입력하세요");
		String id = scn.nextLine();
		System.out.printf("PW 를 입력하세요");
		String pw = scn.nextLine();
		System.out.printf("이름 을 입력하세요");
		String name = scn.nextLine();
		
		String url = "jdbc:oracle:thin:@localhost:1521/xe";
		String user = "proj";
		String pass = "proj";
		
		Connection conn = null;
		Statement stmt = null;
		String sql ="insert into tbl_users (user_id, user_pw, user_name)" 
				+ "values('" + id + "' , '" + pw + "' , '" + name + "')";
		
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			conn = DriverManager.getConnection(url, user, pass);
			stmt = conn.createStatement();
			int r = stmt.executeUpdate(sql); //insert, upadte, delete
			if(r > 0) {
				System.out.println("처리성공");
			} else {
				System.out.println("그... 잘 안됐습니다 암튼");
			}
			
		} catch (Exception e) {
			System.out.println("처리중 에러발생");
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("end of program");
	}
}
