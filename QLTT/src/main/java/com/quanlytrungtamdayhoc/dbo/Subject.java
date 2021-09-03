package com.quanlytrungtamdayhoc.dbo;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Subject {
	private int subId;
	private String subName;
	private Date subStartdate;
	private String subSchedule;
	private String subRoom;
	private int subTuition;
	@Autowired
	private Teacher teacher;
	
	public int getSubId() {
		return subId;
	}
	public void setSubId(int subId) {
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
	
	
}
