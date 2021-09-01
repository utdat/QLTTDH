package com.quanlytrungtamdayhoc.dbo;

import org.springframework.stereotype.Component;

@Component
public class Salary {
	private Teacher teacher;
	private int salMonth;
	private Subject subject;
	private int salMoney;
	
	public Teacher getTeacher() {
		return teacher;
	}
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	public int getSalMonth() {
		return salMonth;
	}
	public void setSalMonth(int salMonth) {
		this.salMonth = salMonth;
	}
	public Subject getSubject() {
		return subject;
	}
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	public int getSalMoney() {
		return salMoney;
	}
	public void setSalMoney(int salMoney) {
		this.salMoney = salMoney;
	}
	
	
}
