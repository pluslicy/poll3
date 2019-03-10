/**
 * Project Name:poll3
 * File Name:ISurveyLogService.java
 * Package Name:com.briup.apps.poll.service
 * Date:2018年11月16日上午10:16:39
 * Copyright (c) 2018, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.briup.apps.poll.service;
/**
 * ClassName:ISurveyLogService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2018年11月16日 上午10:16:39 <br/>
 * @author   lichunyu
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */

import java.util.List;

import com.briup.apps.poll.bean.SurveyLog;

public interface ISurveyLogService {
	List<SurveyLog> query(SurveyLog log);
	
	void save(SurveyLog log) throws Exception;
}

