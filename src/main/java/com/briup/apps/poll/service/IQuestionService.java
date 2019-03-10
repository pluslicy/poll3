package com.briup.apps.poll.service;

import java.util.List;

import com.briup.apps.poll.bean.Question;
import com.briup.apps.poll.bean.extend.QuestionVM;
import com.briup.apps.poll.vm.PageVM;

public interface IQuestionService {
	List<Question> findAll() throws Exception;
	
	PageVM<QuestionVM> query(int page ,int pageSize , Question question) ;
	
	List<QuestionVM> findAllQuestionVM() throws Exception;
	
	void saveOrUpdateQuestionVM(QuestionVM questionVM) throws Exception;
	
	void deleteById(long id) throws Exception;
	
	void batchDelete(long[] ids) throws Exception;
}
