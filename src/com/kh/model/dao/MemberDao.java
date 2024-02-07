package com.kh.model.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.kh.common.JDBCTemplate;
import com.kh.model.vo.MemberVo;

public class MemberDao {
	/*
	 *  SQL문
	 *   기존 : DAO클래스의 메서드 내에 SQL문을 명시적으로 작성함(정적코딩방식)
	 *    ->문제점 : SQL문을 수정해야할대 자바 소스코드를 수정해얗마 => 수정한 내용을 반영할때 프로그램을 종료 후 재시작해야함(재배포....)
	 *    ->해결: SQL문을 별도로 관리하는 외부파일(.xml)로 만들어서 실시간으로 기록된 SQL문을 읽어서 실행(동적실행방식)
	 */
	private Properties prop = new Properties();
	
	{
		try {
			prop.loadFromXML(new FileInputStream("resource/memberDao.xml"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *	회원 추가 메서드
	 *	@param conn, m : Connection 객체와 사용자 입력 데이터 MemberVo객체
	 * 	@return result : 처리된 행의 수(1 or 0) 
	 */
	public int insertMember(Connection conn, MemberVo m) {
		PreparedStatement pstmt = null;
		int result = 0;
		
//		String sql = "INSERT INTO MEMBER VALUES(SEQ_USERNO.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, DEFAULT)";
		String sql = prop.getProperty("insertMember");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, m.getUserId());
			pstmt.setString(2, m.getUserPw());
			pstmt.setString(3, m.getUserName());
			pstmt.setString(4, m.getGender());
			pstmt.setInt(5, m.getAge());
			pstmt.setString(6, m.getEmail());
			pstmt.setString(7, m.getPhone());
			pstmt.setString(8, m.getAddress());
			pstmt.setString(9, m.getHobby());
			
			result = pstmt.executeUpdate();
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}
	
	public List<MemberVo> selectAllList(Connection conn) {
		PreparedStatement pstmt = null;
		List<MemberVo> list = new ArrayList<>();
		
//		String sql = "SELECT * FROM MEMBER";
		String sql = prop.getProperty("selectAllList"); // Properties에서 가져오기
				
		try {
			pstmt = conn.prepareStatement(sql);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				list.add(new MemberVo(
							rs.getInt("userNo"),
							rs.getString("userId"),
							rs.getString("userPw"),
							rs.getString("userName"),
							rs.getString("gender"),
							rs.getInt("age"),
							rs.getString("email"),
							rs.getString("phone"),
							rs.getString("address"),
							rs.getString("hobby"),
							rs.getDate("enrollDate")));
			}
			
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public MemberVo searchByUserId(Connection conn, String userId) {
		PreparedStatement pstmt = null;
		MemberVo m = null;
		
//		String sql = "SELECT * FROM MEMBER WHERE USERID = ?";
		String sql = prop.getProperty("searchByUserId");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				m = new MemberVo(
						rs.getInt("userNo"),
						rs.getString("userId"),
						rs.getString("userPw"),
						rs.getString("userName"),
						rs.getString("gender"),
						rs.getInt("age"),
						rs.getString("email"),
						rs.getString("phone"),
						rs.getString("address"),
						rs.getString("hobby"),
						rs.getDate("enrollDate"));
			}
			
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return m;
			
	}
	
	public List<MemberVo> searchByUserName(Connection conn, String userName) {
		PreparedStatement pstmt = null;
		List<MemberVo> list = new ArrayList<>();
		
//		String sql = "SELECT * FROM MEMBER WHERE USERNAME LIKE '%' || ? || '%'";
		String sql = prop.getProperty("searchByUserName");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userName);
			
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				list.add(new MemberVo(
						rs.getInt("userNo"),
						rs.getString("userId"),
						rs.getString("userPw"),
						rs.getString("userName"),
						rs.getString("gender"),
						rs.getInt("age"),
						rs.getString("email"),
						rs.getString("phone"),
						rs.getString("address"),
						rs.getString("hobby"),
						rs.getDate("enrollDate")));
			}
			
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public int updateMember(Connection conn, MemberVo m) {
		PreparedStatement pstmt = null;
		int result = 0;
		
//		String sql = "UPDATE MEMBER SET USERPW=?, USERNAME=?, GENDER=?, AGE=?, EMAIL=?, PHONE=?, HOBBY=? WHERE USERID=?";
		String sql = prop.getProperty("updateMember");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, m.getUserPw());
			pstmt.setString(2, m.getUserName());
			pstmt.setString(3, m.getGender());
			pstmt.setInt(4, m.getAge());
			pstmt.setString(5, m.getEmail());
			pstmt.setString(6, m.getPhone());
			pstmt.setString(7, m.getHobby());
			pstmt.setString(8, m.getUserId());
			
			result = pstmt.executeUpdate();
			
			JDBCTemplate.close(pstmt);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public int deleteMember(Connection conn, String userId, String userPw) {
		int result = 0;
		
//		String sql = "DELETE FROM MEMBER WHERE USERID=? AND USERPW=?";
		String sql = prop.getProperty("deleteMember");
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setString(2, userPw);
			
			result = pstmt.executeUpdate();
			
			JDBCTemplate.close(pstmt);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return result;
	}
}
