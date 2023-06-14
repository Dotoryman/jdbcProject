package com.yedam.user;

import java.util.List;
import java.util.Scanner;

public class UserMain {
	public static void main(String[] args) {

		UserDao dao = new UserDao();
		Scanner scn = new Scanner(System.in);
		int menu = 0;

		while (true) {
			System.out.println("=======================================================");
			System.out.println("1 . 추가   2 . 조회  3 . 수정  4 . 삭제  5 . 목록  6 . 종료");
			System.out.println("=======================================================");
			System.out.println("선택 > ");
			menu = scn.nextInt();
			scn.nextLine();
			if (menu == 1) { // 1. 추가
				System.out.printf("생성하려는 ID 를 입력하세요");
				String id = scn.nextLine();
				System.out.printf("PW 를 입력하세요");
				String pw = scn.nextLine();
				System.out.printf("이름 을 입력하세요");
				String name = scn.nextLine();

				UserVO user = new UserVO();
				user.setUserId(id);
				user.setUserPw(pw);
				user.setUserName(name);

				if (dao.add(user)) {
					System.out.println("처리성공");
				} else {
					System.out.println("처리실패");
				}

			} else if (menu == 2) {// 2. ID기준 한개 상세조회
				System.out.printf("조회하려는 ID 를 입력하세요");
				String id = scn.nextLine();

				UserVO user = dao.search(id);
				if (user == null) {
					System.out.println("조회하려는 ID는 존재하지 않습니다.");
				} else {
					System.out.println(user.toString());
				}

			} else if (menu == 3) {// 3. 수정
				System.out.printf("정보를 수정하려는 ID 를 입력하세요");
				String id = scn.nextLine();
				System.out.printf("수정하려는 PW 를 입력하세요");
				String pw = scn.nextLine();
				System.out.printf("수정하려는 이름 를 입력하세요");
				String name = scn.nextLine();
				System.out.printf("수정하려는 생년월일 을 입력하세요");
				String birth = scn.nextLine();
				System.out.printf("수정하려는 전화번호 를 입력하세요");
				String phone = scn.nextLine();
				System.out.printf("수정하려는 주소 를 입력하세요");
				String addr = scn.nextLine();

				UserVO user = new UserVO();
				user.setUserId(id);
				user.setUserAddr(addr);
				user.setUserPhone(phone);

				if (dao.modify(user)) {
					System.out.println("수정이 정상적으로 완료되었습니다");
				} else {
					System.out.println("수정하려는 ID는 존재하지 않습니다");
				}
			} else if (menu == 4) { // 4. 삭제
				System.out.printf("정보를 삭제하려는 ID 를 입력하세요");
				String id = scn.nextLine();

				if (dao.remove(id)) {
					System.out.println("삭제가 정상적으로 완료되었습니다");
				} else {
					System.out.println("삭제하려는 ID는 존재하지 않습니다");
				}

			} else if (menu == 5) { // 목록
				List<UserVO> list = dao.list();

				if (list.size() == 0) {
					System.out.println("조회결과 없음");
				} else {
					for (UserVO user : list) {
						System.out.println(user.toString());
					}
				}

			} else if (menu == 6) {
				break;
			}
		}
		System.out.println("end of program");
	}
}
