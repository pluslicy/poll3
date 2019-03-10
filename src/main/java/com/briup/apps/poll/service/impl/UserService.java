/**
 * Project Name:cms
 * File Name:UserServiceImpl.java
 * Package Name:com.briup.apps.cms.service.impl
 * Date:2018年9月17日下午9:35:52
 * Copyright (c) 2018, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.briup.apps.poll.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.briup.apps.poll.bean.Role;
import com.briup.apps.poll.bean.User;
import com.briup.apps.poll.dao.RolesMapper;
import com.briup.apps.poll.dao.extend.UserMapper;
import com.briup.apps.poll.util.Util;

/**
 * ClassName:UserServiceImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2018年9月17日 下午9:35:52 <br/>
 * 
 * @author lichunyu
 * @version
 * @since JDK 1.6
 * @see
 */
@Service
public class UserService implements UserDetailsService {
	@Autowired
	UserMapper userMapper;
	@Autowired
	RolesMapper rolesMapper;

	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
		User user = userMapper.loadUserByUsername(s);
		if (user == null) {
			// 避免返回null，这里返回一个不含有任何值的User对象，在后期的密码比对过程中一样会验证失败
			return new User();
		}
		// 查询用户的角色信息，并返回存入user中
		List<Role> roles = rolesMapper.getRolesByUid(user.getId());
		user.setRoles(roles);
		return user;
	}
	
	
	
	
	/**
	 * @param user
	 * @return 0表示成功 1表示用户名重复 2表示失败
	 */
	public int reg(User user) {
		User loadUserByUsername = userMapper.loadUserByUsername(user.getUsername());
		if (loadUserByUsername != null) {
			return 1;
		}
		// 插入用户,插入之前先对密码进行加密
		user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
		user.setEnabled(true);// 用户可用
		long result = userMapper.reg(user);
		// 配置用户的角色，默认都是普通用户
		String[] roles = new String[] { "2" };
		int i = rolesMapper.addRoles(roles, user.getId());
		boolean b = i == roles.length && result == 1;
		if (b) {
			return 0;
		} else {
			return 2;
		}
	}

	public int updateUserEmail(String email) {
		return userMapper.updateUserEmail(email, Util.getCurrentUser().getId());
	}

	public List<User> getUserByNickname(String nickname) {
		List<User> list = userMapper.getUserByNickname(nickname);
		return list;
	}

	public List<Role> getAllRole() {
		return userMapper.getAllRole();
	}

	public int updateUserEnabled(Boolean enabled, Long uid) {
		return userMapper.updateUserEnabled(enabled, uid);
	}

	public int deleteUserById(Long uid) {
		return userMapper.deleteUserById(uid);
	}

	public int updateUserRoles(Long[] rids, Long id) {
		return userMapper.setUserRoles(rids, id);
	}

	public User getUserById(Long id) {
		return userMapper.getUserById(id);
	}
}
