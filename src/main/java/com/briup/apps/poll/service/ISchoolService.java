/**
 * Project Name:poll
 * File Name:ISchoolService.java
 * Package Name:com.briup.apps.poll.service.impl
 * Date:2018年6月10日下午6:15:44
 * Copyright (c) 2018, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.briup.apps.poll.service;
/**
 * ClassName:ISchoolService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2018年6月10日 下午6:15:44 <br/>
 * @author   lichunyu
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */


import com.briup.apps.poll.bean.School;

public interface ISchoolService {
	School findById(long id) throws Exception;
	
	void saveOrUpdate(School school) throws Exception;
}

