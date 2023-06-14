package com.yedam.user;

import java.util.Scanner;

public class UserMain {
	public static void main(String[] args) {
		
		UserDao dao = new UserDao();
		Scanner scn = new Scanner(System.in);
		int menu = 0;
		
		while(true) {
			System.out.println("============================================");
			System.out.println("1 . 추가   2 . 조회  3 . 수정  4 . 삭제  5 . 종료");
			System.out.println("============================================");
			System.out.println("선택 > ");
			menu = scn.nextInt();scn.nextLine();
			if(menu == 1) {
				System.out.printf("ID 를 입력하세요");
				String id = scn.nextLine();
				System.out.printf("PW 를 입력하세요");
				String pw = scn.nextLine();
				System.out.printf("이름 을 입력하세요");
				String name = scn.nextLine();
				
				UserVO user = new UserVO();
				user.setUserId(id);
				user.setUserPw(pw);
				user.setUserName(name);
				
				if(dao.add(user)) {
					System.out.println("처리성공");
				} else {
					System.out.println("처리실패");
				}
				
			} else if(menu == 2) {
				
			} else if(menu == 3) {
				
			} else if(menu == 4) {
				
			} else if(menu == 5) {
				break;
			}
		}
		System.out.println("end of program");
	}
}
