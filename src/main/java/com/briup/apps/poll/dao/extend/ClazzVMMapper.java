package com.briup.apps.poll.dao.extend;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.briup.apps.poll.bean.Clazz;
import com.briup.apps.poll.bean.extend.ClazzVM;

public interface ClazzVMMapper {
	// 分页查询
	List<ClazzVM> query(
			@Param("page") int page,
			@Param("pageSize") int pageSize,
			@Param("clazz") Clazz clazz);
	long count(
			@Param("clazz") Clazz clazz);
	
	List<ClazzVM> selectAll();
	
	ClazzVM selectById(long id);
}
