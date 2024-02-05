package com.kh.model.dao;

import java.lang.reflect.Member;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.kh.model.vo.MemberVo;

/*
 *	DAO (Data Access Object)
 *	: DB에 직접 접근해서 사용자의 요청에 맞는 sql문을 실행 후 결과 반환(JDBC 작업) 
 *    결과를 Controller쪽으로 전달
 */

public class MemberDao {
	/*
	 * 	JDBC용 객체
	 * 	- Connection : DB연결을 하기 위한 객체. DB연결정보를 가지고 있음
	 *  - [Prepared]Statement : 연결된 DB에 SQL문을 전달하여 실행하고, 결과를 돌려받는 객체
	 *  - ResultSet : SELECT문(SQL문)을 실행하고 조회된 결과물들이 담겨있는 객체
	 * 
	 *  * JDB과정 **순서**
	 *  	[1] JDBC Driver 등록: 사용할 DBNS(오라클)가 제공하는 클래스 등록
	 *  	[2] Connection 객체 생성: 연결하려는 DB정보를 입력하여 해당 DB와 연결하면서 객체 생성
	 *  	[3] Statement 객체 생성: Connection 객체를 통해 생성
	 *  	[4] sql문 전달,실행: Statement 객체를 이용하여 실행
	 *  	[5] 결과 받기 
	 *  		- DQL(SELECT) : ResultSet(조회된 결과물) 객체로 결과 받기
	 *  		- DML(INSERT,UPDATE,DELETE) : int(처리된 행 수)로 결과 받기
	 *  	[6] 결과 처리
	 *  		- DQL결과 : ResultSet에서 하나하나 뽑아서 Vo객체에 차곡차곡 담기
	 *  		- DML결과	 : 트랜잭션처리(COMMIT, ROLLBACK), Connection객체를 통해 호출
	 *  	[7] 사용한 자원 반납(해제)
	 */
	
	private final String ORACLE_DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
	private final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private final String DB_ID = "C##JDBC";
	private final String DB_PW = "JDBC";
	
	/*
	 *  사용자가 입력한 정보들을 DB에 추가하는 메서드
	 *  @param m : 사용자가 입력한 회원정보를 담고 있는 MemberVo 객체
	 * 	@return : insert문 실행후 처리된 행 수
	 */
	public int insertMember(MemberVo m) {
		// [1]
		try {
			Class.forName(ORACLE_DRIVER_NAME); 
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		int result = 0;
		
		try(Connection connection = DriverManager.getConnection(DB_URL, DB_ID, DB_PW); // [2],[7]
				Statement stmt = connection.createStatement()) {	// [3]
			
			connection.setAutoCommit(false);
			
			String sql = String.format("INSERT INTO MEMBER VALUES(SEQ_USERNO.NEXTVAL, '%s', '%s', '%s', '%s', %d, '%s', '%s', '%s', '%s', DEFAULT)", 
						m.getUserId(), m.getUserPw(), m.getUserName(), m.getGender(), m.getAge(), m.getEmail(), m.getPhone(), m.getAddress(), m.getHobby());
			
			result = stmt.executeUpdate(sql); // [4],[5]
			
			if(result > 0) { // [6]
				connection.commit();
			} else {
				connection.rollback();
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	/*
	 * 회원 목록 전체 조회
	 * @return List<MemberVo> : 회원 목록을 담은 ArrayList 객체를 반환
	 */
	public List<MemberVo> selectAllList() {
		
		try {
			Class.forName(ORACLE_DRIVER_NAME);
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		List<MemberVo> list = new ArrayList<>();
		
		try(Connection connection = DriverManager.getConnection(DB_URL, DB_ID, DB_PW);
				Statement stmt = connection.createStatement()){
			String sql = "SELECT * FROM MEMBER";
			
			ResultSet rs = stmt.executeQuery(sql);
			
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
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	/*
	 * 	회원 아이디로 검색
	 * 	@param userId : 검색할 키워드
	 *  @return MemberVo : 아이디와 일치하는 회원 정보를 담은 MemberVo 반환
	 */
	public MemberVo searchByUserId(String userId) {
		
		try {
			Class.forName(ORACLE_DRIVER_NAME);
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		MemberVo m = null;
		
		try(Connection connection = DriverManager.getConnection(DB_URL, DB_ID, DB_PW);
				Statement stmt = connection.createStatement()) {
			
			String sql = String.format("SELECT * FROM MEMBER WHERE USERID = '%s'" , userId);
			
			ResultSet rs = stmt.executeQuery(sql);
			
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
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return m;
	}
	
	
	/*
	 *  회원 이름으로 검색
	 * 	@param userName: 검색 키워드
	 * 	@return List<MemberVo> : 해당 키워드로 검색된 ArrayList<MemberVo> 반환
	 */
	public List<MemberVo> searchByUserName(String userName) {
		try {
			Class.forName(ORACLE_DRIVER_NAME);
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		List<MemberVo> list = new ArrayList<>();
		
		try (Connection connection = DriverManager.getConnection(DB_URL, DB_ID, DB_PW);
				Statement stmt = connection.createStatement()) {
			
			String sql = "SELECT * FROM MEMBER WHERE USERNAME LIKE '%" + userName + "%'";
			
			ResultSet rs = stmt.executeQuery(sql);
			
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
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	/*
	 * 	회원 정보 변경
	 *  @param m : 사용자가 입력한 회원 정보를 담고있는 MemberVo 객체
	 *  @return result : int, 처리된 행의 수
	 */
	public int updateMember(MemberVo m) {
		
		try {
			Class.forName(ORACLE_DRIVER_NAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		int result = 0;
		
		try(Connection connection = DriverManager.getConnection(DB_URL, DB_ID, DB_PW);
				Statement stmt = connection.createStatement()) {

			connection.setAutoCommit(false);
			
			String sql = String.format("UPDATE MEMBER SET USERPW='%s', USERNAME='%s', GENDER='%s', AGE=%d, EMAIL='%s', PHONE='%s', HOBBY='%s' WHERE USERID ='%s'",
					m.getUserPw(), m.getUserName(), m.getGender(), m.getAge(), m.getEmail(), m.getPhone(), m.getHobby(), m.getUserId());
			
			result = stmt.executeUpdate(sql);
			
			if(result > 0) {
				connection.commit();
			} else {
				connection.rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	/*
	 * 	회원 탈퇴 
	 *  @param userId(아이디), userPw(비밀번호) 
	 *  @return result : int, 처리된 행의 수
	 */
	public int deleteMember(String userId, String userPw) {
		try {
			Class.forName(ORACLE_DRIVER_NAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		int result = 0;
		
		try(Connection connection = DriverManager.getConnection(DB_URL, DB_ID, DB_PW);
				Statement stmt = connection.createStatement()) {
			
			connection.setAutoCommit(false);
			String sql = String.format("DELETE FROM MEMBER WHERE USERID='%s' AND USERPW='%s'", userId, userPw);
			
			result = stmt.executeUpdate(sql);
			
			if(result > 0) {
				connection.commit();
			} else {
				connection.rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
