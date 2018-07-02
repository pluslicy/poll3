package com.briup.apps.poll.service;

import java.util.List;

import com.briup.apps.poll.bean.Answers;

public interface IAnswersService {
	
	void saveOrUpdate(Answers answers) throws Exception;
	
	Answers findById(long id) throws Exception;
	
	//通过surveyId查询answers
	List<Answers> findAnswersBySurveyId(long id) throws Exception;
}
