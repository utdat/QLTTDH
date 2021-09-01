package com.quanlytrungtamdayhoc.dbo;

import org.springframework.stereotype.Component;

@Component
public class Student_Score {
	private Subject subject;
	private Student student;
	private float score;
	
	public Subject getSubject() {
		return subject;
	}
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public float getScore() {
		return score;
	}
	public void setScore(float score) {
		this.score = score;
	}
	
	
}
