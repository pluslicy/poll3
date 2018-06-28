/**
 * Project Name:poll
 * File Name:SchoolController.java
 * Package Name:com.briup.apps.poll.web.controller
 * Date:2018年6月10日下午6:19:01
 * Copyright (c) 2018, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.briup.apps.poll.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.briup.apps.poll.bean.School;
import com.briup.apps.poll.service.ISchoolService;
import com.briup.apps.poll.util.MsgResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * ClassName:SchoolController <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2018年6月10日 下午6:19:01 <br/>
 * @author   lichunyu
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
@Api(description="校园信息相关接口")
@RestController
@RequestMapping("/school")
public class SchoolController {
	@Autowired
	private ISchoolService schoolService;
	
	@ApiOperation(value="查询校园信息", notes="通过id查询校园信息,默认当前学校ID为1")
	@GetMapping("findById")
	public MsgResponse findById(@RequestParam long id) {
		try {
			School school = schoolService.findById(id);
			return MsgResponse.success("success", school);
		} catch (Exception e) {
			e.printStackTrace();
			return MsgResponse.error(e.getMessage());
		}
	}
	
	

	
	@ApiOperation(value="郑州轻工代码测试",notes="如果参数中id不为空表示修改操作，如果参数中id为空表示保存操作")
	@PostMapping("test")
	public MsgResponse test() {
		return null;
	}
	
	@ApiOperation(value="保存或者更新校园信息",notes="如果参数中id不为空表示修改操作，如果参数中id为空表示保存操作")
	@PostMapping("saveOrUpdate")
	public MsgResponse saveOrUpdate(@ModelAttribute School school) {
		try {
			schoolService.saveOrUpdate(school);
			return MsgResponse.success("校园信息更新成功", null);
		} catch (Exception e) {
			e.printStackTrace();
			return MsgResponse.error(e.getMessage());
		}
	}
}

