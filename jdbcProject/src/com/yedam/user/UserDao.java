package com.yedam.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.yedam.Dao;

//입력, 수정, 삭제, 목록
public class UserDao {

	Connection conn;
	PreparedStatement psmt;
	ResultSet rs;
	String sql;

	private void close() {
		try {
			if (conn != null) {
				conn.close();
			}
			if (psmt != null) {
				psmt.close();
			}
			if (rs != null) {
				rs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 조회
	public UserVO search(String userId) {
		sql = "select * from tbl_users where user_id = ?";
		conn = Dao.getConnect();

		try {
			psmt = conn.prepareStatement(sql); // new UserDao(); 처럼 하나하나 호출안하고 한방에(prepare 객체)
			psmt.setString(1, userId);
			rs = psmt.executeQuery();
			if (rs.next()) { // if 한건 조회가 된다면
				UserVO user = new UserVO();
				user.setUserId(rs.getString("user_id"));
				user.setUserPw(rs.getString("user_pw"));
				user.setUserName(rs.getString("user_name"));
				user.setUserBirth(rs.getString("user_birth"));
				user.setUserPhone(rs.getString("user_phone"));
				user.setUserAddr(rs.getString("user_addr"));
				return user;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return null;

	}

	// 추가
	public boolean add(UserVO user) {
		sql = "insert into tbl_users (user_id, user_pw, user_name) " + "values(?, ?, ?)";
		conn = Dao.getConnect();
		try {
			psmt = conn.prepareStatement(sql); // new UserDao(); 처럼 하나하나 호출안하고 한방에(prepare 객체)
			psmt.setString(1, user.getUserId());
			psmt.setString(2, user.getUserPw());
			psmt.setString(3, user.getUserName());

			int r = psmt.executeUpdate();
			if (r > 0) {
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return false;
	}

}
