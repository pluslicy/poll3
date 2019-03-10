package com.briup.apps.poll.dao.extend;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.briup.apps.poll.bean.extend.SurveyVM;
import com.briup.apps.poll.vm.SurveyPVM;

public interface SurveyVMMapper {
	List<SurveyVM> selectAll();
	
	List<SurveyVM> query(
			@Param("page") int page,
			@Param("pageSize") int pageSize,
			@Param("survey") SurveyPVM survey);
	
	long count(@Param("survey") SurveyPVM survey);
	
	List<SurveyVM> queryByMonth(String month);
	
	SurveyVM selectById(long id) ;
	
	List<SurveyVM> selectByClazzIdAndCheckPass(long id);
}
