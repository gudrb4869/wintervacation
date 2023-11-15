package com.ssafy.member.model;

public class MemberDto {
	private String userId;
	private String userName;
	private String userPass;
	private String email;
	private String birthDate;
	private String gender;
	private String address;
	private String joinDate;
	private String salt;
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPass() {
		return userPass;
	}
	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getJoinDate() {
		return joinDate;
	}
	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	
	@Override
	public String toString() {
		return "MemberDto [userId=" + userId + ", userName=" + userName + ", userPass=" + userPass + ", email=" + email
				+ ", birthDate=" + birthDate + ", gender=" + gender + ", address=" + address + ", joinDate=" + joinDate
				+ ", salt=" + salt + "]";
	}
	

	
}
