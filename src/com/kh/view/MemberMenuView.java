package com.kh.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.kh.controller.MemberController;
import com.kh.model.vo.MemberVo;

/*
 * 	View
 * 	: 사용자가 보게될 시각적인 요소(화면) 출력 및 입력
 */
public class MemberMenuView {
	// 필드 - 입력용객체(Scanner), Controller 객체
	// 입력용 객체 - Scanner
	private Scanner sc = new Scanner(System.in);
	
	private MemberController mc = new MemberController();

	
	public void mainMenu() {
		while(true) {
			System.out.println("============회원 관리 프로그램============");
			System.out.println("1.회원 추가");
			System.out.println("2.회원 전체 조회");
			System.out.println("3.회원 아이디 검색");
			System.out.println("4.회원 이름으로 키워드 검색");
			System.out.println("5.회원 정보 변경");
			System.out.println("6.회원 탈퇴");
			System.out.println("0.프로그램 종료");
			System.out.println("======================================");
			System.out.print(">>> 메뉴 선택 : ");
			
			int selMenu = Integer.parseInt(sc.nextLine());
			
			switch(selMenu) {
				case 1: 
					insertMember();
					break;
				case 2: 
					mc.selectAllList();
					break;
				case 3: 
					searchByUserId();
					break;
				case 4: 
					searchByUserName();
					break;
				case 5: 
					updateMember();
					break;
				case 6: 
					deleteMember();
					break;
				case 0: 
					System.out.println("프로그램을 종료합니다.");
					return;
				default: 
					System.out.println("잘못 입력 하셨습니다. 다시 입력해주세요.");
			}
		}
	}
	
	/*
	 * 	회원 추가 창(서브 화면)
	 */
	public void insertMember() {
		System.out.println("\n===== 회원 추가 =====");
		System.out.print("아이디 : ");
		String userId = sc.nextLine();
		
		System.out.print("비밀번호 : ");
		String userPw = sc.nextLine();
		
		System.out.print("이름 : ");
		String userName = sc.nextLine();
		
		System.out.print("성별(남 또는 여) : ");
		String gender = sc.nextLine();
		
		System.out.print("나이 : ");
		int age = Integer.parseInt(sc.nextLine());
		
		System.out.print("이메일 : ");
		String email = sc.nextLine();
		
		System.out.print("전화번호( - 빼고 입력) : ");
		String phone = sc.nextLine();
		
		System.out.print("주소 : ");
		String address = sc.nextLine();
		
		System.out.print("취미(,로 구분하여 여러개 작성 가능) : ");
		String hobby = sc.nextLine();
		

		// 사용자가 입력한 정보를 MemberController에게 전달
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		map.put("userPw", userPw);
		map.put("userName", userName);
		map.put("gender", gender);
		map.put("age", age);
		map.put("email", email);
		map.put("phone", phone);
		map.put("address", address);
		map.put("hobby", hobby);		
		
		mc.insertMember(map);
	}
	
	public void searchByUserId() {
		System.out.println("\n===== 회원 아이디 검색 =====");
		System.out.print("아이디 : ");
		String userId = sc.nextLine();
		
		mc.searchByUserId(userId);
	}
	
	
	public void searchByUserName() {
		System.out.println("\n===== 회원 이름으로 키워드 검색 =====");
		System.out.print("이름 : ");
		String userName = sc.nextLine();
		
		mc.searchByUserName(userName);
	}
	
	
	public void updateMember() {
		System.out.println("\n===== 회원 정보 변경 =====");
		System.out.print("변경할 아이디 : ");
		String userId = sc.nextLine();
		
		System.out.print("비밀번호 : ");
		String userPw = sc.nextLine();
		
		System.out.print("이름 : ");
		String userName = sc.nextLine();
		
		System.out.print("성별(남 또는 여) : ");
		String gender = sc.nextLine();
		
		System.out.print("나이 : ");
		int age = Integer.parseInt(sc.nextLine());
		
		System.out.print("이메일 : ");
		String email = sc.nextLine();
		
		System.out.print("전화번호( - 빼고 입력) : ");
		String phone = sc.nextLine();
		
		System.out.print("주소 : ");
		String address = sc.nextLine();
		
		System.out.print("취미(,로 구분하여 여러개 작성 가능) : ");
		String hobby = sc.nextLine();
		

		// 사용자가 입력한 정보를 MemberController에게 전달
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		map.put("userPw", userPw);
		map.put("userName", userName);
		map.put("gender", gender);
		map.put("age", age);
		map.put("email", email);
		map.put("phone", phone);
		map.put("address", address);
		map.put("hobby", hobby);		
		
		mc.updateMember(map);
	}
	
	public void deleteMember() {
		System.out.println("\n===== 회원 정보 변경 =====");
		System.out.print("삭제할 아이디 : ");
		String userId = sc.nextLine();
		
		System.out.print("비밀번호 : ");
		String userPw = sc.nextLine();
		
		mc.deleteMember(userId, userPw);
	}
	
	// ---------------------------------------- 응답 화면 --------------------------------------------------
	/**
	 *	요청 처리 후 성공했을 경우 사용자가 보게될 화면
	 *	@param message : 객체 별 성공 메시지
	 */
	public void displaySuccess(String message) {
		System.out.println("\n요청 성공 : " + message);
	}
	
	/**
	 *	요청 처리 후 실패했을 경우 사용자가 보게될 화면
	 *	@param message : 객체 별 실패 메시지
	 */
	public void displayFailed(String message) {
		System.out.println("\n요청 실패 : " + message);
	}
	
	/**
	 * 조회 요청 후 조회 결과가 없을 때 사용자가 보게될 화면
	 * @param message : 객체 별 메시지
	 */
	public void displayNoData(String message) {
		System.out.println("\n결과 없음 : " + message);
	}
	
	/**
	 * 조회 요청 후 조회 결과가 있을때 사용자가 보게될 화면
	 * @param list : 조회된 결과가 목록이 담겨진 리스트 객체
	 */
	public void displayList(List<MemberVo> list) {
		for(MemberVo m: list) {
			System.out.println(m);
		}
	}
	
	/**
	 *  회원 정보에 대한 결과를 사용자가 보게될 화면
	 *  @param m: 회원정보를 담고있는 MemberVo 객체
	 */
	public void displayMember(MemberVo m) {
		System.out.println(m);
	}
}
