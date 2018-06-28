package com.briup.apps.poll.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.briup.apps.poll.bean.Clazz;
import com.briup.apps.poll.bean.extend.ClazzVM;
import com.briup.apps.poll.service.IClazzService;
import com.briup.apps.poll.util.MsgResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description="班级相关接口")
@RestController
@RequestMapping("/clazz")
public class ClazzController {
	@Autowired
	private IClazzService clazzService;
	
	@ApiOperation(value="保存或修改班级信息",
			notes="如果参数中包含ID表示修改操作，否则表示保存操作")
	@PostMapping("saveOrUpdateClazz")
	public MsgResponse saveOrUpdateClazz(Clazz clazz){
		try {
			clazzService.saveOrUpdateClazz(clazz);
			return MsgResponse.success("保存或更新成功", null);
		} catch (Exception e) {
			e.printStackTrace();
			return MsgResponse.error(e.getMessage());
		}
	}
	
	
	@ApiOperation(value="查询所有班级",
			notes="班级中携带班级所属年级信息以及班主任信息")
	@GetMapping("findAllVM")
	public MsgResponse findAllVM(){
		try {
			List<ClazzVM> list = clazzService.findAllClazzVM();
			return MsgResponse.success("success", list);
		} catch (Exception e) {
			e.printStackTrace();
			return MsgResponse.error(e.getMessage());
		}
	}

	@ApiOperation(value="查询所有班级",notes="单表")
	@GetMapping("findAll")
	public MsgResponse findAll(){
		try {
			List<Clazz> list = clazzService.findAll();
			return MsgResponse.success("success", list);
		} catch (Exception e) {
			e.printStackTrace();
			return MsgResponse.error(e.getMessage());
		}
	}
}
