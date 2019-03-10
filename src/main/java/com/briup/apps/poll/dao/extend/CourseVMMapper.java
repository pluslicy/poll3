/**
 * Project Name:poll3
 * File Name:CourseVMMapper.java
 * Package Name:com.briup.apps.poll.dao.extend
 * Date:2018年11月15日下午2:33:07
 * Copyright (c) 2018, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.briup.apps.poll.dao.extend;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.briup.apps.poll.bean.Course;

/**
 * ClassName:CourseVMMapper <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2018年11月15日 下午2:33:07 <br/>
 * @author   lichunyu
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public interface CourseVMMapper {
	// 分页查询
	List<Course> query(
				@Param("page") int page,
				@Param("pageSize") int pageSize,
				@Param("course") Course course);
	long count(@Param("course") Course course);
}

