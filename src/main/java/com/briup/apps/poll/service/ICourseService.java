package com.briup.apps.poll.service;

import java.util.List;

import com.briup.apps.poll.bean.Course;
import com.briup.apps.poll.vm.PageVM;

public interface ICourseService {
	PageVM<Course> query(int page,int pageSize,Course course) ;
	
	
	/**
	 * 查询所有
	 * */
	List<Course> findAll() throws Exception;
	
	/**
	 * 通过id查询
	 * */
	Course findById(long id) throws Exception;
	
	/**
	 * 关键字查询
	 * */
	List<Course> query(String keywords) throws Exception;
	
	/**
	 * 保存或更新
	 * */
	void saveOrUpdate(Course course) throws Exception;
	
	/**
	 * 通过ID删除
	 * */
	void deleteById(long id) throws Exception;
	
	/**
	 * 批量删除
	 * */
	void batchDelete(long[] ids) throws Exception;
}
