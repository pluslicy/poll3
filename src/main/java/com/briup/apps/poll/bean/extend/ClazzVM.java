package com.briup.apps.poll.bean.extend;

import com.briup.apps.poll.bean.Grade;
import com.briup.apps.poll.bean.PollUser;

public class ClazzVM {
	private Long id;
	private String name;
	private String description;
	private Integer surveyNumber;
	private Integer studentNumber;
	private Grade grade;
	private PollUser charge;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Grade getGrade() {
		return grade;
	}
	public void setGrade(Grade grade) {
		this.grade = grade;
	}
	
	public PollUser getCharge() {
		return charge;
	}
	public void setCharge(PollUser charge) {
		this.charge = charge;
	}
	public Integer getSurveyNumber() {
		return surveyNumber;
	}
	public void setSurveyNumber(Integer surveyNumber) {
		this.surveyNumber = surveyNumber;
	}
	public Integer getStudentNumber() {
		return studentNumber;
	}
	public void setStudentNumber(Integer studentNumber) {
		this.studentNumber = studentNumber;
	}
}
