package com.briup.apps.poll.service;

import java.util.List;

import com.briup.apps.poll.bean.Clazz;
import com.briup.apps.poll.bean.extend.ClazzVM;
import com.briup.apps.poll.vm.PageVM;

public interface IClazzService {
	List<Clazz> findAll() throws Exception;
	
	PageVM<ClazzVM> query(int page,int pageSize,Clazz clazz) ;
	
	Clazz findById(long id) ;
	
	List<ClazzVM> findAllClazzVM() throws Exception;
	
	void saveOrUpdateClazz(Clazz clazz) throws Exception;
	
	void deleteById(long id) throws Exception;
	
	void batchDelete(long[] ids) throws Exception;
}
