package com.briup.apps.poll.service;

import java.util.List;

import com.briup.apps.poll.bean.Survey;
import com.briup.apps.poll.bean.extend.SurveyVM;

public interface ISurveyService {
	
	void saveOrUpdate(Survey survey) throws Exception;
	
	List<SurveyVM> findAll() throws Exception;
	
	//级联查询课调信息
	SurveyVM findById(long id) throws Exception;
	
	//查询课调单表
	Survey findSurveyById(long id) throws Exception;
	
	void deleteById(long id) throws Exception;
	
	void batchDelete(long[] ids) throws Exception;
}
