package com.kh.model.vo;

import java.sql.Date;

/*
 * 	VO (Value Object)
 *  한명의 회원(db테이블의 한 행 데이터)에 대한 데이터를 기록하기 위한 객체
 * 
 */
public class MemberVo {
	// 필드
	// DB 테이블 컬럼명과 유사하게 작성
	private int userNo;
	private String userId;
	private String userPw;
	private String userName;
	private String gender;
	private int age;
	private String email;
	private String phone;
	private String address;
	private String hobby;
	private Date enrollDate; // Oracle Date타입 -> java.sql.Date 형으로 import
	
	// 생성자
	// 보통은 기본생성자, 매개변수가있는 생성자(모든 필드 데이터) 
	public MemberVo() {
		super();
	}
	
	public MemberVo(String userId, String userPw, String userName, String gender, int age, String email,
			String phone, String address, String hobby) {
		super();
		this.userId = userId;
		this.userPw = userPw;
		this.userName = userName;
		this.gender = gender;
		this.age = age;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.hobby = hobby;
	}

	public MemberVo(int userNo, String userId, String userPw, String userName, String gender, int age, String email,
			String phone, String address, String hobby, Date enrollDate) {
		this(userId, userPw, userName, gender, age, email, phone, address, hobby);
		this.userNo = userNo;
		this.enrollDate = enrollDate;
	}
	
	// 메서드
	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPw() {
		return userPw;
	}

	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	public Date getEnrollDate() {
		return enrollDate;
	}

	public void setEnrollDate(Date enrollDate) {
		this.enrollDate = enrollDate;
	}

	@Override
	public String toString() {
		return "Member [userNo=" + userNo + ", userId=" + userId + ", userPw=" + userPw + ", userName=" + userName
				+ ", gender=" + gender + ", age=" + age + ", email=" + email + ", phone=" + phone + ", address="
				+ address + ", hobby=" + hobby + ", enrollDate=" + enrollDate + "]";
	}
}
