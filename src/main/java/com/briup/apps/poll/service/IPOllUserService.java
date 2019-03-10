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

import org.springframework.security.core.userdetails.UserDetails;

import com.briup.apps.poll.bean.PollUser;
import com.briup.apps.poll.bean.User;
import com.briup.apps.poll.vm.PageVM;


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
public interface IPOllUserService {
	//更新用户信息	
	UserDetails updateUser(User user) throws Exception;
	//更新用户密码	
	void updateUserPassword(User user) throws Exception;
	
	List<PollUser> findAllEnabledUser();
	
	
	List<PollUser> findAll();
	
	void saveOrUpdate(PollUser user) throws Exception;
	
	void register(User user) throws Exception;
	
	PageVM<User> query(int page ,int pageSize, String keywords);
	
	void deleteById(long id) throws Exception;
	
	void batchDelete(long[] ids) throws Exception;
	
}

