package com.briup.apps.poll.service;

import java.util.List;

import com.briup.apps.poll.bean.Survey;
import com.briup.apps.poll.bean.extend.SurveyVM;
import com.briup.apps.poll.vm.PageVM;
import com.briup.apps.poll.vm.SurveyPVM;
import com.briup.apps.poll.vm.SurveyStatisticsVM;

public interface ISurveyService {
	List<SurveyVM> findByClazzIdAndCheckPass(long id) throws Exception;
	
	void saveOrUpdate(Survey survey) throws Exception;
	
	List<SurveyVM> findAll() throws Exception;
	
	//级联查询课调信息
	SurveyVM findById(long id) throws Exception;
	
	//查询课调单表
	Survey findSurveyById(long id) throws Exception;
	
	//多条件符合查询
	PageVM<SurveyVM> query(int page,int pageSize,SurveyPVM survey);
	
	//按照月份进行统计
	List<SurveyStatisticsVM> statisticsByMonth(String month) throws Exception;
	
	void deleteById(long id) throws Exception;
	
	void batchDelete(long[] ids) throws Exception;
}
