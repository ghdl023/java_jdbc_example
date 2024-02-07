package test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class TestRun {

	public static void main(String[] args) {
		// Properties
		
		/*
		 * 특징)
		 * 	- Map계열의 컬렉션(key-value 세트로 데이터를 저장)
		 *  - K,V(String, String) 타입으로만 저장
		 *  - 값을 저장 : setProperty(key, value)
		 *  - 값을 조회 : getProperty(key)
		 *  - 저장할 파일종류 : 
		 *  	.properties / .xml
		 */
		
		
//		setPropertyTest();
//		getPropertyTest();
		
		// SQL문 관련 파일 출력
		Properties prop = new Properties();
		
		// SQL문 저장
		String sql = "INSERT INTO MEMBER VALUES(SEQ_USERNO.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, DEFAULT)";
		prop.setProperty("insertMember", sql);
		prop.setProperty("selectAllList", "SELECT * FROM MEMBER");
		prop.setProperty("searchByUserId", "SELECT * FROM MEMBER WHERE USERID = ?");
		prop.setProperty("searchByUserName", "SELECT * FROM MEMBER WHERE USERNAME LIKE '%' || ? || '%'");
		prop.setProperty("updateMember", "UPDATE MEMBER SET USERPW=?, USERNAME=?, GENDER=?, AGE=?, EMAIL=?, PHONE=?, HOBBY=? WHERE USERID=?");
		prop.setProperty("deleteMember", "DELETE FROM MEMBER WHERE USERID=? AND USERPW=?");
		
		try {
			prop.store(new FileOutputStream("resource/memberDao.properties"), "Member Query");
			prop.storeToXML(new FileOutputStream("resource/memberDao.xml"), "Member Query");
			
			System.out.println("SQL문 저장 완료");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void getPropertyTest() {
		
		Properties prop = new Properties();
		Properties propXml = new Properties();
		
		try {
			prop.load(new FileInputStream("resource/hong.properties"));
			System.out.println(prop.get("name"));
			System.out.println(prop.get("gender"));
			System.out.println(prop.get("age"));
			System.out.println(prop.get("address"));
			System.out.println(prop.get("ADDRESS")); // 대소문자 구분
			
			propXml.loadFromXML(new FileInputStream("resource/hong.xml"));
			System.out.println(propXml.get("name"));
			System.out.println(propXml.get("gender"));
			System.out.println(propXml.get("age"));
			System.out.println(propXml.get("address"));
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void setPropertyTest() {
		Properties prop = new Properties(); 
		
		// 값 설정
		prop.setProperty("name", "hong");
		prop.setProperty("gender", "male");
		prop.setProperty("age", "24");
		prop.setProperty("address", "seoul");
		
		// 파일에 출력
		try {
			prop.store(new FileOutputStream("resource/hong.properties"), "hong's profile");
			prop.storeToXML(new FileOutputStream("resource/hong.xml"), "hong's profile");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
