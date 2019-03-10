/**
 * Project Name:poll3
 * File Name:SurveyLogServiceImpl.java
 * Package Name:com.briup.apps.poll.service.impl
 * Date:2018年11月16日上午10:16:56
 * Copyright (c) 2018, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.briup.apps.poll.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.briup.apps.poll.bean.SurveyLog;
import com.briup.apps.poll.bean.SurveyLogExample;
import com.briup.apps.poll.bean.SurveyLogExample.Criteria;
import com.briup.apps.poll.dao.SurveyLogMapper;
import com.briup.apps.poll.service.ISurveyLogService;

/**
 * ClassName:SurveyLogServiceImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2018年11月16日 上午10:16:56 <br/>
 * @author   lichunyu
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
@Service
public class SurveyLogServiceImpl implements ISurveyLogService {
	@Autowired
	private SurveyLogMapper surveyLog;
	
	@Override
	public List<SurveyLog> query(SurveyLog log) {
		SurveyLogExample example = new SurveyLogExample();
		Criteria criteria = example.createCriteria();
		if(log.getIp()!=null) {
			criteria.andIpEqualTo(log.getIp());
		}
		if(log.getSurveyId() != null) {
			criteria.andSurveyIdEqualTo(log.getSurveyId());
		}
		return surveyLog.selectByExample(example);
	}

	@Override
	public void save(SurveyLog log) throws Exception {
		surveyLog.insert(log);
	}
	
	
}







