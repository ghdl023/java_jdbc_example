package test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.Scanner;

public class TestRun {

	public static void main(String[] args) {
		
		/*
		 *	 * JDBC 사용 객체
		 *
		 *		- Connection : DB의 연결정보를 담고 있는 객체
		 *		- [Prepared]Statement : 연결된 DB에 sql문을 전달하여 실행하고 그 결과를 받아주는 객체
		 *		- ResultSet : SELECT문 실행 후 조회된 결과들이 담겨 있는 객체
		 *
		 * 	 * JDBC 과정 (순서가 중요~)
		 * 		1. JDBC Driver 등록 : 해당 DBMS(오라클)가 제공하는 클래스 등록
		 * 		2. Connection 생성 : 연결하고자 하는 DB정보를 입력해서 해당 DB와 연결하면서 생성
		 * 							접속주소(url), 계정정보(id,password)
		 * 		3. Statement 객체 생성 : Connection객체를 이용해 생성
		 * 							   sql문을 실행하고 결과를 받는 역할 	
		 * 		4. sql문을 DB에 전달하여 실행 : Statement 객체를 통해 실행
		 * 		5. 결과 받기
		 *           - SELECT문 실행 : ResultSet객체(조회된 데이터들이 담겨있음)
		 *           - DML문 실행    : int (처리된 행수)
		 *      6. 결과 처리
		 *      	 - SELECT문 실행 : ResultSet의 데이터를 하나하나 뽑아서 VO객체에 옮겨닮기 [+ArrayList에 add]
		 *      	 - DML문 실행    : 트랜잭션 처리(성공적으로 수행했으면 commit, 실패했으면 rollback)
		 *      
		 *      * Auto Commit : ON (기본값) (JDBC 버전6 이후 Auto Commit이 추가됨)
		 *        - off 설정 방법 
		 *        		* 코드로 작성: conn.setAutoCommit(false);
		 *        		* 실행시 옵션: JVM 실행 옵션 추가 
		 *        					-Doracle.jdbc.autoCommitSpecCompliant=false
		 *        				=> Run Configuration > Arguments > VM 에 작성 
		 *      
		 *      
		 *      7. 자원 반납(해제, close) => 생성 역순으로 Statement, Connection, 
		 */
		
//		insertTest();
		
		
		// 2. 각자 PC의 DB에 JDBC계정으로 접속하여 TEST 테이블의 모든 데이터를 조회(SELECT) 해보기
		//	 DQL(SELECT) => ResultSet 객체(조회된 모든 데이터) => 컬럼값들을 각각 뽑아내기
		
		// 필요한 변수 세팅
		String sql = "SELECT * FROM TEST";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("OracleDriver 등록 완료");
			
			try(Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "C##JDBC", "JDBC");
					Statement stmt = conn.createStatement()) {
				
				ResultSet rs = stmt.executeQuery(sql);
				
				while(rs.next()) {
					int t_no = rs.getInt("tno");
					String t_name = rs.getString("tname");
					Date t_date = rs.getDate("tdate");
					System.out.println(String.format("%d, %s, %s", t_no, t_name, t_date));
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} catch (ClassNotFoundException e) {
			System.out.println("OracleDriver을 찾지 못했습니다.");
			e.printStackTrace();
		}
	}
		
	public static void insertTest() {
		// 1. 각자 PC(127.0.0.1 | localhost)에 JDBC 계정을 연결한 후 TEST테이블에 INSERT 해보기
		// 	DML(INSERT) => 처리된 행 수(int) => 트랜잭션 처리
		
		// 필요한 변수 세팅
		int result = 0;
		Connection conn = null;
		Statement stmt = null; 
		
		
		// 실행할 sql문 ("완성 형태"로 만들기)
		// * Statement객체에는 항상 완성 형태의 sql문을 작성해야함
		// * sql 끝에 세미콜론이 없어야함.
//		String sql = "INSERT INTO TEST VALUES(1, '청명', SYSDATE)";
		
		Scanner sc = new Scanner(System.in);
		System.out.print("TNO : " );
		String t_no = sc.nextLine();
		
		System.out.print("TNAME: " );
		String t_name = sc.nextLine();
		
		String sql = String.format("INSERT INTO TEST VALUES(%s, '%s', SYSDATE)", t_no, t_name);
		
		
		try {
			// 1. JDBC Driver 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("OracleDriver 등록 완료");
			
			// 2. Connection 객체 생성 : DB에 연결
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "C##JDBC", "JDBC");
			
			// 3. Statement 객체 생성
			stmt = conn.createStatement();
			
			Savepoint sp = conn.setSavepoint();
			
			// 4. SQL 전달 & 5. 결과 받기
			result = stmt.executeUpdate(sql);
			
			// 6. 결과 처리(sql문이 DML문이므로 -> 트랜잭션 처리)
			if(result > 0 ) { // commit;
				conn.commit(); // auto-commit 이 설정되어 오류발생
			} else { // rollback;			
				conn.rollback(sp);
			}
			
			
		} catch (ClassNotFoundException e) {
			System.out.println("OracleDriver을 찾지 못했습니다.");
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		if(result > 0) {
			System.out.println("데이터가 추가되었습니다.");
		} else {
			System.out.println("데이터 추가를 실패했습니다.");
		}
	}

}
