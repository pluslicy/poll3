/**
 * Project Name:poll
 * File Name:IUserService.java
 * Package Name:com.briup.apps.poll.service
 * Date:2018年6月12日上午9:09:32
 * Copyright (c) 2018, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.briup.apps.poll.service;

import java.util.List;

import com.briup.apps.poll.bean.User;


/**
 * ClassName:IUserService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2018年6月12日 上午9:09:32 <br/>
 * @author   lichunyu
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public interface IUserService {
	List<User> findAll() throws Exception;
	
	User findById(long id) throws Exception;
	
	void saveOrUpdate(User user) throws Exception;
	
	void deleteById(long id) throws Exception;
	
	void batchDelete(long[] ids) throws Exception;
}

