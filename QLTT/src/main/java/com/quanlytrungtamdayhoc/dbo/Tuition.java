package com.quanlytrungtamdayhoc.dbo;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class Tuition {
	private Date tuiDate;
	private Student student;
	private Subject subject;
	private int tuiMoney;
	private String tuiDescription;
	
	public Date getTuiDate() {
		return tuiDate;
	}
	public void setTuiDate(Date tuiDate) {
		this.tuiDate = tuiDate;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public Subject getSubject() {
		return subject;
	}
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	public int getTuiMoney() {
		return tuiMoney;
	}
	public void setTuiMoney(int tuiMoney) {
		this.tuiMoney = tuiMoney;
	}
	public String getTuiDescription() {
		return tuiDescription;
	}
	public void setTuiDescription(String tuiDescription) {
		this.tuiDescription = tuiDescription;
	}
	
	
}
