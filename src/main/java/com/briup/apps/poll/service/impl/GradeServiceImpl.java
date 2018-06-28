/**
 * Project Name:poll
 * File Name:GradeServiceImpl.java
 * Package Name:com.briup.apps.poll.service.impl
 * Date:2018年6月10日下午6:18:04
 * Copyright (c) 2018, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.briup.apps.poll.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.briup.apps.poll.bean.Grade;
import com.briup.apps.poll.bean.GradeExample;
import com.briup.apps.poll.dao.GradeMapper;
import com.briup.apps.poll.service.IGradeService;

/**
 * ClassName:GradeServiceImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2018年6月10日 下午6:18:04 <br/>
 * @author   lichunyu
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
@Service
public class GradeServiceImpl implements IGradeService {
	@Autowired
	private GradeMapper gradeMapper;
	
	@Override
	public List<Grade> findAll() throws Exception {
		GradeExample example = new GradeExample();
		return gradeMapper.selectByExample(example);
	}

	@Override
	public Grade findById(long id) throws Exception {
		return gradeMapper.selectByPrimaryKey(id);
	}

	@Override
	public void saveOrUpdate(Grade grade) throws Exception {
		if(grade.getId()!=null) {
			gradeMapper.updateByPrimaryKey(grade);
		} else {
			gradeMapper.insert(grade);
		}
	}

	@Override
	public void deleteById(long id) throws Exception {
		gradeMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void batchDelete(long[] ids) throws Exception {
		for(long id : ids) {
			gradeMapper.deleteByPrimaryKey(id);
		}
	}

}

