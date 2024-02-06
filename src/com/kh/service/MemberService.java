package com.kh.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.kh.common.JDBCTemplate;
import com.kh.model.dao.MemberDao;
import com.kh.model.vo.MemberVo;

public class MemberService {
	/*
	 * 1) JDBC Driver 등록
	 * 2) Connection 객체 생성
	 * 3) Connection 객체 처리
	 */
	
	// 회원 추가
	public int insertMember(MemberVo m) {
		// 1) Connection 객체 생성(jdbc driver 등록 포함)
		Connection conn = JDBCTemplate.getConnection(false);
		
		// 2) MemberVo 객체 DAO 전달하여 데이터 처리에 대한 결과 받기
		int result = new MemberDao().insertMember(conn, m);
		
		// 3)트랜 잭션 처리
		if(result > 0) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}
		JDBCTemplate.close(conn);
		
		return result;
	}
	
	// 회원 전체 목록 조회
	public List<MemberVo> selectAllList() {
		Connection conn = JDBCTemplate.getConnection(true);
		
		List<MemberVo> list = new MemberDao().selectAllList(conn);
	
		JDBCTemplate.close(conn);
		
		return list;
	}
	
	// 회원 아이디 검색
	public MemberVo searchByUserId(String userId) {
		Connection conn = JDBCTemplate.getConnection(true);
		MemberVo m = new MemberDao().searchByUserId(conn, userId);
		
		JDBCTemplate.close(conn);
		
		return m;
	}
	
	// 회원 이름으로 키워드 검색
	public List<MemberVo> searchByUserName(String userName) {
		Connection conn = JDBCTemplate.getConnection(true);
		List<MemberVo> list = new MemberDao().searchByUserName(conn, userName);
		
		JDBCTemplate.close(conn);
		
		return list;
	}
	
	// 회원 정보 수정
	public int updateMember(MemberVo m) {
		Connection conn = JDBCTemplate.getConnection(false);
		int result = new MemberDao().updateMember(conn, m);
		
		if(result > 0) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return result;
	}
	
	
	// 회원 탈퇴
	public int deleteMember(String userId, String userPw) {
		Connection conn = JDBCTemplate.getConnection(false);
		int result = new MemberDao().deleteMember(conn, userId, userPw);
		
		if(result > 0) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return result;
		
	}
}
