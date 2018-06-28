/**
 * Project Name:poll
 * File Name:SchoolServiceImpl.java
 * Package Name:com.briup.apps.poll.service.impl
 * Date:2018年6月10日下午6:16:56
 * Copyright (c) 2018, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.briup.apps.poll.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.briup.apps.poll.bean.School;
import com.briup.apps.poll.dao.SchoolMapper;
import com.briup.apps.poll.service.ISchoolService;

/**
 * ClassName:SchoolServiceImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2018年6月10日 下午6:16:56 <br/>
 * @author   lichunyu
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
@Service
public class SchoolServiceImpl implements ISchoolService {
	@Autowired
	private SchoolMapper schoolMapper;
	
	
	@Override
	public School findById(long id) throws Exception {
		return schoolMapper.selectByPrimaryKey(id);
	}

	@Override
	public void saveOrUpdate(School school) throws Exception {
		if(school.getId()!=null) {
			schoolMapper.updateByPrimaryKey(school);
		} else {
			schoolMapper.insert(school);
		}
	}

}

