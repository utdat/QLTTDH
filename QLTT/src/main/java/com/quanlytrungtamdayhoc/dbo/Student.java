package com.quanlytrungtamdayhoc.dbo;

import java.util.Date;

public class Student {

	private Integer stuId;
	private String stuEmail;
	private String stuName;
	private String stuPhone;
	private String stuAddress;
	private String stuGender;
	private Date stuBirthdate;
	private Date stuJoindate;
	private String stuSchool;
	private String stuAvatar;

	public Integer getStuId() {
		return stuId;
	}

	public void setStuId(Integer stuId) {
		this.stuId = stuId;
	}

	public String getStuEmail() {
		return stuEmail;
	}

	public void setStuEmail(String stuEmail) {
		this.stuEmail = stuEmail;
	}

	public String getStuName() {
		return stuName;
	}

	public void setStuName(String stuName) {
		this.stuName = stuName;
	}

	public String getStuPhone() {
		return stuPhone;
	}

	public void setStuPhone(String stuPhone) {
		this.stuPhone = stuPhone;
	}

	public String getStuAddress() {
		return stuAddress;
	}

	public void setStuAddress(String stuAddress) {
		this.stuAddress = stuAddress;
	}

	public String getStuGender() {
		return stuGender;
	}

	public void setStuGender(String stuGender) {
		this.stuGender = stuGender;
	}

	public Date getStuBirthdate() {
		return stuBirthdate;
	}

	public void setStuBirthdate(Date stuBirthdate) {
		this.stuBirthdate = stuBirthdate;
	}

	public Date getStuJoindate() {
		return stuJoindate;
	}

	public void setStuJoindate(Date stuJoindate) {
		this.stuJoindate = stuJoindate;
	}

	public String getStuSchool() {
		return stuSchool;
	}

	public void setStuSchool(String stuSchool) {
		this.stuSchool = stuSchool;
	}

	public String getStuAvatar() {
		return stuAvatar;
	}

	public void setStuAvatar(String stuAvatar) {
		this.stuAvatar = stuAvatar;
	}

	@Override
	public String toString() {
		return "Student [stuId=" + stuId + ", stuEmail=" + stuEmail + ", stuName=" + stuName + ", stuPhone=" + stuPhone
				+ ", stuAddress=" + stuAddress + ", stuGender=" + stuGender + ", stuBirthdate=" + stuBirthdate
				+ ", stuJoindate=" + stuJoindate + ", stuSchool=" + stuSchool + ", stuAvatar=" + stuAvatar + "]";
	}

	
}