package com.briup.apps.poll.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.briup.apps.poll.bean.Questionnaire;
import com.briup.apps.poll.bean.QuestionnaireExample;
import com.briup.apps.poll.bean.extend.QuestionnaireVM;
import com.briup.apps.poll.dao.QuestionnaireMapper;
import com.briup.apps.poll.dao.extend.QuestionnaireVMMapper;
import com.briup.apps.poll.service.IQuestionnaireService;

@Service
public class QuestionnaireServiceImpl implements IQuestionnaireService {
	@Autowired
	private QuestionnaireMapper qnMapper;
	@Autowired
	private QuestionnaireVMMapper qnVMMapper;
	
	
	@Override
	public List<Questionnaire> findAll() throws Exception {
		QuestionnaireExample example = new QuestionnaireExample();
		return qnMapper.selectByExampleWithBLOBs(example);
	}

	@Override
	public QuestionnaireVM findById(long id) throws Exception {
		return qnVMMapper.selectById(id);
	}

}
