package com.kh.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kh.common.JDBCTemplate;
import com.kh.model.vo.MemberVo;

public class MemberDao {
	
	/**
	 *	회원 추가 메서드
	 *	@param conn, m : Connection 객체와 사용자 입력 데이터 MemberVo객체
	 * 	@return result : 처리된 행의 수(1 or 0) 
	 */
	public int insertMember(Connection conn, MemberVo m) {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String sql = "INSERT INTO MEMBER VALUES(SEQ_USERNO.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, DEFAULT)";
		
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
		
		String sql = "SELECT * FROM MEMBER";
		
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
		
		String sql = "SELECT * FROM MEMBER WHERE USERID = ?";

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
		
		String sql = "SELECT * FROM MEMBER WHERE USERNAME LIKE '%' || ? || '%'";
		
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
		
		String sql = "UPDATE MEMBER SET USERPW=?, USERNAME=?, GENDER=?, AGE=?, EMAIL=?, PHONE=?, HOBBY=? WHERE USERID=?";
		
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
		
		String sql = "DELETE FROM MEMBER WHERE USERID=? AND USERPW=?";
		
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
