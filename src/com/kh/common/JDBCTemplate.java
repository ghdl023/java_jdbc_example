package com.kh.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCTemplate {
//	private static final String ORACLE_DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
//	private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
//	private static final String DB_ID = "C##JDBC";
//	private static final String DB_PW = "JDBC";
	
	// 1. Connection 객체 생성 (DB 접속) 한 후 해당 Connection 객체를 반환 해주는 메서드
	// [1],[2]에 해당
	public static Connection getConnection(boolean autoCommit) {
		
		Connection conn = null;
		
		try {
			Properties prop = new Properties();
			prop.load(new FileInputStream("jdbc.properties"));
			
			// [1] jdbc driver 등록
			Class.forName(prop.getProperty("ORACLE_DRIVER_NAME"));
			
			// [2] Connection 객체 생성
			conn = DriverManager.getConnection(prop.getProperty("DB_URL"), prop.getProperty("DB_ID"), prop.getProperty("DB_PW"));
			
			if(!autoCommit) {
				conn.setAutoCommit(autoCommit);
			}
			
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return conn;
	}
	
	// 2. commit 처리해주는 메서드
	public static void commit(Connection conn) {
		try {
			if(conn != null && !conn.isClosed()) {
				conn.commit();	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 3. rollback 처리해주는 메서드
	public static void rollback(Connection conn) {
		try {
			if(conn != null && !conn.isClosed()) {
				conn.rollback();	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 4. JDBC 관련 자원 반납 -> Connection, [Prepared]Statement, ResultSet
	public static void close(Object obj) {
		try {
			if(obj != null) {
				if(obj instanceof Connection) {
					Connection conn = (Connection)obj;
					if(!conn.isClosed()) conn.close();
					
				} else if(obj instanceof Statement) {
					Statement stmt = (Statement)obj;
					if(!stmt.isClosed()) stmt.close();
					
				} else if(obj instanceof ResultSet) {
					ResultSet rs = (ResultSet)obj;
					if(!rs.isClosed()) rs.close();
					
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
