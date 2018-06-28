/**
 * Project Name:poll
 * File Name:IGradeService.java
 * Package Name:com.briup.apps.poll.service
 * Date:2018年6月10日下午6:16:41
 * Copyright (c) 2018, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.briup.apps.poll.service;
/**
 * ClassName:IGradeService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2018年6月10日 下午6:16:41 <br/>
 * @author   lichunyu
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */

import java.util.List;

import com.briup.apps.poll.bean.Grade;

public interface IGradeService {
	
	List<Grade> findAll() throws Exception;
	
	Grade findById(long id) throws Exception;
	
	void saveOrUpdate(Grade grade) throws Exception;
	
	void deleteById(long id) throws Exception;
	
	void batchDelete(long[] ids) throws Exception;
}

