package com.yedam.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

	// 1. 추가
	public boolean add(UserVO user) {
		sql = "insert into tbl_users (user_id, user_pw, user_name) " + "values(?, ?, ?)";
		conn = Dao.getConnect();
		try {
			psmt = conn.prepareStatement(sql); // new UserDao(); 처럼 하나하나 호출안하고 한방에(prepare 객체)
			psmt.setString(1, user.getUserId());
			psmt.setString(2, user.getUserPw());
			psmt.setString(3, user.getUserName());

			int r = psmt.executeUpdate();
			if (r > 0) { // 수정사항 반영
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return false;
	}

	// 2. 조회(id검색으로 1건 상세 조회)
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

	// 3. 수정
	public boolean modify(UserVO user) {
		sql = "update tbl_users " + "set user_pw = nvl(?, user_pw)," + "user_name = nvl(?, user_name),"
				+ "user_birth = nvl(?, user_birth)," + "user_phone = nvl(?, user_phone),"
				+ "user_addr = nvl(?, user_addr) " + "where user_id = ?";

		conn = Dao.getConnect();
		try {
			psmt = conn.prepareStatement(sql); // new UserDao(); 처럼 하나하나 호출안하고 한방에(prepare 객체)
			psmt.setString(1, user.getUserPw());
			psmt.setString(2, user.getUserName());
			psmt.setString(3, user.getUserBirth());
			psmt.setString(4, user.getUserPhone());
			psmt.setString(5, user.getUserAddr());
			psmt.setString(6, user.getUserId());

			int r = psmt.executeUpdate(); // 쿼리의 실행!
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

	// 4. 삭제
	public boolean remove(String id) {
		sql = "delete from tbl_users" + " where user_id = ?";
		conn = Dao.getConnect();
		try {
//			conn.setAutoCommit(false); //예를들어 a에서 b로 송금할 때, a와 b 모두 수정이 성공하고나서 커밋해야함 
//			conn.commit();

			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);

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

	// 5. 목록
	public List<UserVO> list() {
		List<UserVO> list = new ArrayList<>();

		sql = "select * from tbl_users";
		conn = Dao.getConnect();

		try {
			psmt = conn.prepareStatement(sql); // new UserDao(); 처럼 하나하나 호출안하고 한방에(prepare 객체)
			rs = psmt.executeQuery();

			while (rs.next()) {
				UserVO user = new UserVO();
				user.setUserId(rs.getString("user_id"));
				user.setUserId(rs.getString("user_id"));
				user.setUserPw(rs.getString("user_pw"));
				user.setUserName(rs.getString("user_name"));
				user.setUserBirth(rs.getString("user_birth"));
				user.setUserPhone(rs.getString("user_phone"));
				user.setUserAddr(rs.getString("user_addr"));
				list.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return list;
	}

}
