package com.yedam.board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.yedam.Dao;


public class BoardDao {
	Connection conn;
	PreparedStatement psmt;
	ResultSet rs;
	String sql;

	// 0. close 클래스
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

	// 0. login check. id & pass => 로그인하기,
	// 아이디와비밀번호는 데이터베이스에서 가져오기
	// + '작성자' 칸에 로그인한 아이디가 들어가도록
	public boolean loginCheck(String id, String pw) {
		sql = "select * from tbl_users where user_id=? and user_pw=?";
		conn = Dao.getConnect();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			psmt.setString(2, pw);
			
			rs = psmt.executeQuery();
			if(rs.next()) {
				System.out.println("로그인에 성공했습니다");
				return false; //아이디가 있다는 의미
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		}
		System.out.println("아이디 또는 비밀번호가 틀렸습니다");
		return true;
	}

	// 1. 등록
	public boolean add(BoardVO board) {
		sql = "insert into tbl_board (brd_no, brd_title, brd_writer, brd_content) "
				+ "values(board_seq.nextval,?, ?, ?)";
		conn = Dao.getConnect();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, board.getBoardTitle());
			psmt.setString(2, board.getBoardWriter());
			psmt.setString(3, board.getBoardContent());

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

	// 2. 삭제
	public boolean remove(int no) {
		sql = "delete from tbl_board" + " where brd_no = ?";
		conn = Dao.getConnect();
		try {

			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, no);

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

	// 3. 글내용수정
	public boolean modify(BoardVO board) {
		sql = "update tbl_board " + "set brd_content = nvl(?, brd_content) " + "where brd_no = ?";

		conn = Dao.getConnect();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, board.getBoardContent());
			psmt.setInt(2, board.getBoardNo());

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

	// 4. 글목록
	public List<BoardVO> list() {
		List<BoardVO> list = new ArrayList<>();

		sql = "select * from tbl_board";
		conn = Dao.getConnect();

		try {
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();

			while (rs.next()) {
				BoardVO board = new BoardVO();
				board.setBoardNo(rs.getInt("brd_no"));
				board.setBoardTitle(rs.getString("brd_title"));
				board.setBoardWriter(rs.getString("brd_writer"));
				board.setBoardContent(rs.getString("brd_content"));
				board.setBoardDate(rs.getString("create_date"));
				board.setBoardCount(rs.getInt("click_cnt"));
				list.add(board);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return list;
	}

	// 5. 상세조회(글번호검색으로 1건 상세 조회)
	public BoardVO search(int no) {
		sql = "select * from tbl_board where brd_no = ?";
		String sql1 = " update tbl_board" + " set click_cnt = click_cnt + 1" + " where brd_no = ?";
		conn = Dao.getConnect();

		try {
			psmt = conn.prepareStatement(sql1);
			psmt.setInt(1, no);
			rs = psmt.executeQuery();

			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, no);
			rs = psmt.executeQuery();

			if (rs.next()) { // if 한건 조회가 된다면
				BoardVO board = new BoardVO();
				board.setBoardNo(rs.getInt("brd_no"));
				board.setBoardTitle(rs.getString("brd_title"));
				board.setBoardWriter(rs.getString("brd_writer"));
				board.setBoardContent(rs.getString("brd_content"));
				board.setBoardDate(rs.getString("create_date"));
				board.setBoardCount(rs.getInt("click_cnt"));
				return board;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return null;

	}

}
