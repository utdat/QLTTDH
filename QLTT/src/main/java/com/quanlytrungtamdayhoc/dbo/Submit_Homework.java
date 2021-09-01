package com.quanlytrungtamdayhoc.dbo;

import java.util.Date;

public class Submit_homework {
	private int annId;
	private Student student;
	private Date submitDate;
	private String submitLink;
	
	public int getAnnId() {
		return annId;
	}
	public void setAnnId(int annId) {
		this.annId = annId;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public Date getSubmitDate() {
		return submitDate;
	}
	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}
	public String getSubmitLink() {
		return submitLink;
	}
	public void setSubmitLink(String submitLink) {
		this.submitLink = submitLink;
	}
	
	
}