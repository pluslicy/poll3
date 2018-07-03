/**
 * Project Name:poll
 * File Name:UserServiceImpl.java
 * Package Name:com.briup.apps.poll.service.impl
 * Date:2018年6月12日上午9:09:41
 * Copyright (c) 2018, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.briup.apps.poll.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.briup.apps.poll.bean.User;
import com.briup.apps.poll.bean.UserExample;
import com.briup.apps.poll.dao.UserMapper;
import com.briup.apps.poll.service.IUserService;

/**
 * ClassName:UserServiceImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2018年6月12日 上午9:09:41 <br/>
 * @author   lichunyu
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
@Service
public class UserServiceImpl implements IUserService {
	@Autowired
	private UserMapper userMapper;
	
	
	@Override
	public List<User> findAll() throws Exception {
		UserExample example = new UserExample();
		return userMapper.selectByExample(example);
	}

	@Override
	public User findById(long id) throws Exception {
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public void saveOrUpdate(User user) throws Exception {
		if(user.getId()!=null) {
			userMapper.updateByPrimaryKey(user);
		} else {
			userMapper.insert(user);
		}
	}

	@Override
	public void deleteById(long id) throws Exception {
		userMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void batchDelete(long[] ids) throws Exception {
		for(long id : ids) {
			userMapper.deleteByPrimaryKey(id);
		}
	}

	@Override
	public List<User> query(String keywords) throws Exception {
		UserExample example = new UserExample();
		example.createCriteria().andNameLike("%"+keywords+"%");
		return userMapper.selectByExample(example);
	}

}

