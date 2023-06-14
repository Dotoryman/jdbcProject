package com.yedam.board;

import java.util.List;
import java.util.Scanner;


public class BoardMain { // 로그인기능 넣기 - user리스트 사용, boardDao에 넣기
	public static void main(String[] args) {

		BoardDao dao = new BoardDao();
		Scanner scn = new Scanner(System.in);
		int menu = 0;
		//로그인기능, 아이디&비번으로
		boolean run = true;
		while(run) {
			System.out.println("관리자 계정을 입력하세요");
			String id = scn.nextLine();
			System.out.println("비밀번호를 입력하세요");
			String pw = scn.nextLine();
			run = dao.loginCheck(id, pw);
		}
		while (true) {
			System.out.println("........................게시판 기능........................");
			System.out.println("=======================================================");
			System.out.println("1 . 글등록 \n2 . 삭제 \n3 . 글내용수정 \n4 . 글목록보기 \n5 . 상세보기 \n6 . 종료하기");
			System.out.println("=======================================================");
			System.out.println("선택 > ");
			menu = scn.nextInt();
			scn.nextLine();
			if (menu == 1) { // 1. 글등록
				System.out.printf("생성하려는 글의 제목을 입력하세요");
				String title = scn.nextLine();
				System.out.printf("작성자의 이름을 입력하세요");
				String writer = scn.nextLine();
				System.out.printf("글 내용을 입력하세요");
				String content = scn.nextLine();

				BoardVO board = new BoardVO();
				board.setBoardTitle(title);
				board.setBoardWriter(writer);
				board.setBoardContent(content);

				if (dao.add(board)) {
					System.out.println("처리성공");
				} else {
					System.out.println("처리실패");
				}

			} else if (menu == 2) {// 2. 삭제
				System.out.printf("정보를 삭제하려는 글번호 를 입력하세요");
				int no = Integer.parseInt(scn.nextLine());

				if (dao.remove(no)) {
					System.out.println("삭제가 정상적으로 완료되었습니다");
				} else {
					System.out.println("삭제하려는 글번호는 존재하지 않습니다");
				}

			} else if (menu == 3) {// 3. 수정
				System.out.printf("정보를 수정하려는 글번호를 입력하세요");
				int no = Integer.parseInt(scn.nextLine());
				System.out.printf("수정하려는 글 내용을 입력하세요");
				String content = scn.nextLine();

				BoardVO board = new BoardVO();
				board.setBoardNo(no);
				board.setBoardContent(content);

				if (dao.modify(board)) {
					System.out.println("수정이 정상적으로 완료되었습니다");
				} else {
					System.out.println("수정하려는 글번호는 존재하지 않습니다");
				}
			} else if (menu == 4) { // 4. 글목록 보기

				List<BoardVO> list = dao.list();

				if (list.size() == 0) {
					System.out.println("조회결과 없음");
				} else {
					for (BoardVO board : list) {
						System.out.println(board.toString());
					}
				}

			} else if (menu == 5) { // 5.상세조회

				System.out.printf("조회하려는 글번호를 입력하세요");
				int no = Integer.parseInt(scn.nextLine());

				BoardVO board = dao.search(no);
				if (board == null) {
					System.out.println("조회하려는 글번호는 존재하지 않습니다.");
				} else {
					System.out.println(board.toString());
				}

			} else if (menu == 6) {
				break;
			}
		}
		System.out.println("end of program");
	}
}
