package com.kh.controller;

import java.util.List;
import java.util.Map;

import com.kh.model.dao.MemberDao;
import com.kh.model.vo.MemberVo;
import com.kh.view.MemberMenuView;

/*
 * 	Controller 
 * 	: View를 통해서 사용자가 요청한 기능을 처리하는 역할
 *    [1] 해당 메소드로 전달된 데이터 가공 처리 후 DAO로 전달하여 호출
 *    [2] DAO로부터 반환받은 결과에 따라서 성공,실패 판단후 "응답화면 결정(vIEW 메소드 호출)"
 * 
 */
public class MemberController {
	private MemberDao memberDAO = new MemberDao();
	
	/*
	 * 회원 추가 요청을 처리해주는 메소드
	 * 
	 * 전달받은 데이터들
	 * @param userId ~ hobby
	 */
	public void insertMember(Map<String, Object> request) {
		
		// [1] view로부터 전달받은 값을 바로 dao쪽으로 전달 x
		// 어딘가에 담아서 전달
		// SpringBoot Framework에서는 매개변수로 MemberVo타입을 받을수있는 Request타입객체를 사용해서 이 과정을 생략할 수있음
		MemberVo m = new MemberVo(
				request.get("userId").toString(),
				request.get("userPw").toString(),
				request.get("userName").toString(),
				request.get("gender").toString(),
				(int)request.get("age"),
				request.get("email").toString(),
				request.get("phone").toString(),
				request.get("address").toString(),
				request.get("hobby").toString());
		
		int result = memberDAO.insertMember(m);
		
		// [2]
		if(result > 0) { // 회원 추가 성공
			new MemberMenuView().displaySuccess("회원 추가 성공했습니다.");
		} else { // 실패
			new MemberMenuView().displayFailed("회원 추가 실패했습니다.");
		}
	}
	
	/**
	 * 	회원 전체 조회에 대한 기능을 처리해주는 메서드
	 */
	public void selectAllList() {
		// list 객체에 회원 전체 목록 담기
		List<MemberVo> list = memberDAO.selectAllList();
		
		// view쪽에 결과를 전달하여 출력
		if(list.size() > 0) {
			new MemberMenuView().displayList(list);
		} else {
			new MemberMenuView().displayNoData("조회된 결과가 없습니다.");
		}
	}
	
	public void searchByUserId(String userId) {
		MemberVo m =  memberDAO.searchByUserId(userId);
		
		if(m == null) {
			new MemberMenuView().displayNoData(userId + "와 일치하는 회원이 없습니다.");
		} else {
			new MemberMenuView().displayMember(m);
		}
	}
	
	public void searchByUserName(String userName) {
		List<MemberVo> list = memberDAO.searchByUserName(userName);
		if(list.isEmpty()) {
			new MemberMenuView().displayNoData("조회된 결과가 없습니다.");
		} else {
			new MemberMenuView().displayList(list);
		}
	}
	
	public void updateMember(Map<String, Object> request) {
		MemberVo m = new MemberVo(
				request.get("userId").toString(),
				request.get("userPw").toString(),
				request.get("userName").toString(),
				request.get("gender").toString(),
				(int)request.get("age"),
				request.get("email").toString(),
				request.get("phone").toString(),
				request.get("address").toString(),
				request.get("hobby").toString());
		
		int result = memberDAO.updateMember(m);
		
		if(result > 0) { // 회원 정보 변경 성공
			new MemberMenuView().displaySuccess("회원 정보 변경 성공했습니다.");
		} else { // 실패
			new MemberMenuView().displayFailed("회원 정보 변경 실패했습니다.");
		}
	}
	
	public void deleteMember(String userId, String userPw) {
		int result = memberDAO.deleteMember(userId, userPw);
		
		if(result > 0) { // 회원 탈퇴 성공
			new MemberMenuView().displaySuccess("회원 탈퇴 성공했습니다.");
		} else { // 실패
			new MemberMenuView().displayFailed("회원 탈퇴 실패했습니다.");
		}
	}
}
