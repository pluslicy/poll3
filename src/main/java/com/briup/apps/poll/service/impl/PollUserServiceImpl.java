/**
 * Project Name:poll3
 * File Name:UserServiceImpl.java
 * Package Name:com.briup.apps.poll.service.impl
 * Date:2018年11月14日下午4:35:38
 * Copyright (c) 2018, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.briup.apps.poll.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.briup.apps.poll.bean.PollUser;
import com.briup.apps.poll.bean.PollUserExample;
import com.briup.apps.poll.bean.Role;
import com.briup.apps.poll.bean.User;
import com.briup.apps.poll.dao.PollUserMapper;
import com.briup.apps.poll.dao.RolesMapper;
import com.briup.apps.poll.dao.extend.UserMapper;
import com.briup.apps.poll.service.IPOllUserService;
import com.briup.apps.poll.vm.PageVM;

/**
 * ClassName:UserServiceImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2018年11月14日 下午4:35:38 <br/>
 * 
 * @author lichunyu
 * @version
 * @since JDK 1.6
 * @see
 */
@Service
public class PollUserServiceImpl implements IPOllUserService {
	@Autowired
	private PollUserMapper pollUserMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private RolesMapper rolesMapper;
	
	

	@Override
	public List<PollUser> findAll() {
		PollUserExample example = new PollUserExample();
		return pollUserMapper.selectByExample(example);
	}

	@Override
	public void saveOrUpdate(PollUser user) throws Exception {
		if (user.getId() != null) {
			pollUserMapper.updateByPrimaryKey(user);
		} else {
			pollUserMapper.insert(user);
		}
	}
	
	@Override
	public UserDetails updateUser(User user) throws Exception {
		//更新用户
		userMapper.updateUser(user);
		//查询用户信息
		User ruser = userMapper.getUserById(user.getId());
		// 查询用户的角色信息，并返回存入user中
		List<Role> roles = rolesMapper.getRolesByUid(user.getId());
		if(roles!=null) {
			ruser.setRoles(roles);
		}
		return ruser;
	}
	
	@Override
	public void updateUserPassword(User user) throws Exception {
		user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
		userMapper.updateUserPassword(user);
	}

	

	//注册用户
	@Override
	public void register(User user) throws Exception {
		User loadUserByUsername = userMapper.loadUserByUsername(user.getUsername());
		if (loadUserByUsername != null) {
			throw new Exception("该用户已经存在");
		}
		user.setRegTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		// 插入用户,插入之前先对密码进行加密
		user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
		user.setEnabled(true);// 用户可用
		long result = userMapper.reg(user);
		// 配置用户的角色，默认都是普通用户
		String[] roles = new String[] { "2" };
		int i = rolesMapper.addRoles(roles, user.getId());
		boolean b = i == roles.length && result == 1;
		if (!b) {
			throw new Exception("授权失败");
		}

	}

	@Override
	public PageVM<User> query(int page, int pageSize, String keywords) {
		if(keywords!=null) {
			keywords = "%"+keywords+"%";
		}
		List<User> list = userMapper.query(page, pageSize, keywords);
		long total = userMapper.count(keywords);
		PageVM<User> pageVM = new PageVM<>(page, pageSize, total, list);
		return pageVM;
	}

	@Override
	public void deleteById(long id) throws Exception {
//		pollUserMapper.deleteByPrimaryKey(id);
		PollUser user = pollUserMapper.selectByPrimaryKey(id);
		user.setEnabled(false);
		pollUserMapper.updateByPrimaryKey(user);
	}

	@Override
	public void batchDelete(long[] ids) throws Exception {
		if(ids!= null) {
			for(long id : ids) {
				PollUser user = pollUserMapper.selectByPrimaryKey(id);
				user.setEnabled(false);
				pollUserMapper.updateByPrimaryKey(user);
			}
		} else {
			throw new Exception("参数异常!");
		}
	}

	@Override
	public List<PollUser> findAllEnabledUser() {
		PollUserExample example = new PollUserExample();
		example.createCriteria().andEnabledEqualTo(true);
		return pollUserMapper.selectByExample(example);
	}
	

}









