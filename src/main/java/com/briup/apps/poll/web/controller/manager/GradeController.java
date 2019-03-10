/**
 * Project Name:poll
 * File Name:GradeController.java
 * Package Name:com.briup.apps.poll.web.controller
 * Date:2018年6月10日下午11:15:28
 * Copyright (c) 2018, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.briup.apps.poll.web.controller.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.briup.apps.poll.bean.Grade;
import com.briup.apps.poll.service.IGradeService;
import com.briup.apps.poll.util.MsgResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * ClassName:GradeController <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2018年6月10日 下午11:15:28 <br/>
 * @author   lichunyu
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
@Api(description="年级相关接口")
@RestController
@RequestMapping("/manager/grade")
public class GradeController {
	@Autowired
	private IGradeService gradeService;
	
	@ApiOperation(value="批量删除年级信息",notes="ids为集合")
	@GetMapping(value="batchDelete")
	public MsgResponse batchDelete(@RequestParam long[] ids) {
		try {
			gradeService.batchDelete(ids);
			return MsgResponse.success("批量删除成功", null);
		} catch (Exception e) {
			return MsgResponse.error(e.getMessage());
		}
	}
	
	
	@ApiOperation(value="根据id删除年级信息",notes="id不允许为空")
	@GetMapping(value="deleteById")
	public MsgResponse deleteById(@RequestParam long id) {
		try {
			gradeService.deleteById(id);
			return MsgResponse.success("删除成功", null);
		} catch (Exception e) {
			return MsgResponse.error(e.getMessage());
		}
	}
	
	@ApiOperation(value="保存或更新年级信息",notes="参数中id不为空表示插入，否则表示更新")
	@PostMapping(value="saveOrUpdate")
	public MsgResponse saveOrUpdate(@ModelAttribute Grade grade) {
		try {
			gradeService.saveOrUpdate(grade);
			return MsgResponse.success("保存或更新成功", null);
		} catch (Exception e) {
			return MsgResponse.error(e.getMessage());
		}
	}
	
	@ApiOperation(value="查询所有年级信息")
	@GetMapping(value="findAll")
	public MsgResponse findAll() {
		try {
			List<Grade> list = gradeService.findAll();
			return MsgResponse.success("success", list);
		} catch (Exception e) {
			return MsgResponse.error(e.getMessage());
		}
	}
}

