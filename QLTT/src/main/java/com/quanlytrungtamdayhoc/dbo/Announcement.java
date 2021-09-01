package com.quanlytrungtamdayhoc.dbo;

import org.springframework.stereotype.Component;

@Component
public class Announcement {
	private int annId;
	private String annTitle;
	private int annType;
	private String annLink;
	private Subject subject;
	
	public int getAnnId() {
		return annId;
	}
	public void setAnnId(int annId) {
		this.annId = annId;
	}
	public String getAnnTitle() {
		return annTitle;
	}
	public void setAnnTitle(String annTitle) {
		this.annTitle = annTitle;
	}
	public int getAnnType() {
		return annType;
	}
	public void setAnnType(int annType) {
		this.annType = annType;
	}
	public String getAnnLink() {
		return annLink;
	}
	public void setAnnLink(String annLink) {
		this.annLink = annLink;
	}
	public Subject getSubject() {
		return subject;
	}
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	
	
}
