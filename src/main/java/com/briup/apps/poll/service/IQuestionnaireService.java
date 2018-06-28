package com.briup.apps.poll.service;

import java.util.List;

import com.briup.apps.poll.bean.Questionnaire;
import com.briup.apps.poll.bean.extend.QuestionnaireVM;

public interface IQuestionnaireService {
	List<Questionnaire> findAll() throws Exception;
	
	QuestionnaireVM findById(long id) throws Exception;
}
