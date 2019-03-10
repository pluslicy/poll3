package com.briup.apps.poll.dao.extend;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.briup.apps.poll.bean.Question;
import com.briup.apps.poll.bean.extend.QuestionVM;

public interface QuestionVMMapper {
	
	List<QuestionVM> selectAll();
	
	List<QuestionVM> query(
			@Param("page") int page,
			@Param("pageSize") int pageSize,
			@Param("question") Question question);
	
	long count(@Param("question") Question question);
	
	List<QuestionVM> selectByQuestionnaireId(long id);
}
