package com.quanlytrungtamdayhoc.dbo;

import java.beans.Transient;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class Teacher {
	private int teaId;
	private String teaEmail;
	private String teaName;
	private String teaPhone;
	private String teaAddress;
	private String teaGender;
	private Date teaBirthdate;
	private Date teaHiredate;
	private String teaSchool;
	private String teaAvatar;
	private Float teaSalaryrate;
	
	public int getTeaId() {
		return teaId;
	}
	public void setTeaId(int teaId) {
		this.teaId = teaId;
	}
	public String getTeaEmail() {
		return teaEmail;
	}
	public void setTeaEmail(String teaEmail) {
		this.teaEmail = teaEmail;
	}
	public String getTeaName() {
		return teaName;
	}
	public void setTeaName(String teaName) {
		this.teaName = teaName;
	}
	public String getTeaPhone() {
		return teaPhone;
	}
	public void setTeaPhone(String teaPhone) {
		this.teaPhone = teaPhone;
	}
	public String getTeaAddress() {
		return teaAddress;
	}
	public void setTeaAddress(String teaAddress) {
		this.teaAddress = teaAddress;
	}
	public String getTeaGender() {
		return teaGender;
	}
	public void setTeaGender(String teaGender) {
		this.teaGender = teaGender;
	}
	public Date getTeaBirthdate() {
		return teaBirthdate;
	}
	public void setTeaBirthdate(Date teaBirthdate) {
		this.teaBirthdate = teaBirthdate;
	}
	public Date getTeaHiredate() {
		return teaHiredate;
	}
	public void setTeaHiredate(Date teaHiredate) {
		this.teaHiredate = teaHiredate;
	}
	public String getTeaSchool() {
		return teaSchool;
	}
	public void setTeaSchool(String teaSchool) {
		this.teaSchool = teaSchool;
	}
	public String getTeaAvatar() {
		return teaAvatar;
	}
	public void setTeaAvatar(String teaAvatar) {
		this.teaAvatar = teaAvatar;
	}
	public Float getTeaSalaryrate() {
		return teaSalaryrate;
	}
	public void setTeaSalaryrate(Float teaSalaryrate) {
		this.teaSalaryrate = teaSalaryrate;
	}
	
	@Transient
	public String getAvatarPath() {
		if(teaAvatar == null || teaId < 0) {
			return "/images/default_user.png";
		}
		
		return "/images/" + teaId + "/" + teaAvatar;
	}
	
}
