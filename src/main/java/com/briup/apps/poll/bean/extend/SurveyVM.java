package com.briup.apps.poll.bean.extend;

import com.briup.apps.poll.bean.Course;
import com.briup.apps.poll.bean.PollUser;

public class SurveyVM {
	private Long id;
	private Double average;
	private String status;
	private String code;
	private String surveydate;
	
	private Course course;
	private ClazzVM clazzVM;
	private PollUser user;
	private QuestionnaireVM qnVM;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getSurveydate() {
		return surveydate;
	}
	public void setSurveydate(String surveydate) {
		this.surveydate = surveydate;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public ClazzVM getClazzVM() {
		return clazzVM;
	}
	public void setClazzVM(ClazzVM clazzVM) {
		this.clazzVM = clazzVM;
	}
	
	public PollUser getUser() {
		return user;
	}
	public void setUser(PollUser user) {
		this.user = user;
	}
	public QuestionnaireVM getQnVM() {
		return qnVM;
	}
	public void setQnVM(QuestionnaireVM qnVM) {
		this.qnVM = qnVM;
	}
	public Double getAverage() {
		return average;
	}
	public void setAverage(Double average) {
		this.average = average;
	}
	
	
}
