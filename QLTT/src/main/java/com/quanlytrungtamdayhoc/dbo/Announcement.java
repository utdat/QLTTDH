package com.quanlytrungtamdayhoc.dbo;

public class Announcement {

	private Integer annId;
	private String annTitle;
	private Integer annType;
	private String annLink;
	private Integer subId;

	public Integer getAnnId() {
		return annId;
	}

	public void setAnnId(Integer annId) {
		this.annId = annId;
	}

	public String getAnnTitle() {
		return annTitle;
	}

	public void setAnnTitle(String annTitle) {
		this.annTitle = annTitle;
	}

	public Integer getAnnType() {
		return annType;
	}

	public void setAnnType(Integer annType) {
		this.annType = annType;
	}

	public String getAnnLink() {
		return annLink;
	}

	public void setAnnLink(String annLink) {
		this.annLink = annLink;
	}

	public Integer getSubId() {
		return subId;
	}

	public void setSubId(Integer subId) {
		this.subId = subId;
	}

}