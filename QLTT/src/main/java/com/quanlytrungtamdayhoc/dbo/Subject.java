package com.quanlytrungtamdayhoc.dbo;

import java.util.Date;

public class Subject {

	private Integer subId;
	private String subName;
	private Date subStartdate;
	private String subSchedule;
	private String subRoom;
	private Integer subTuition;
	private Teacher teacher;

	public Integer getSubId() {
		return subId;
	}

	public void setSubId(Integer subId) {
		this.subId = subId;
	}

	public String getSubName() {
		return subName;
	}

	public void setSubName(String subName) {
		this.subName = subName;
	}

	public Date getSubStartdate() {
		return subStartdate;
	}

	public void setSubStartdate(Date subStartdate) {
		this.subStartdate = subStartdate;
	}

	public String getSubSchedule() {
		return subSchedule;
	}

	public void setSubSchedule(String subSchedule) {
		this.subSchedule = subSchedule;
	}

	public String getSubRoom() {
		return subRoom;
	}

	public void setSubRoom(String subRoom) {
		this.subRoom = subRoom;
	}

	public Integer getSubTuition() {
		return subTuition;
	}

	public void setSubTuition(Integer subTuition) {
		this.subTuition = subTuition;
	}

	

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	@Override
	public String toString() {
		return "Subject [subId=" + subId + ", subName=" + subName + ", subStartdate=" + subStartdate + ", subSchedule="
				+ subSchedule + ", subRoom=" + subRoom + ", subTuition=" + subTuition + ", teaId=" + teacher + "]";
	}

}