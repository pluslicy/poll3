package com.briup.apps.poll.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.briup.apps.poll.bean.Course;
import com.briup.apps.poll.service.ICourseService;
import com.briup.apps.poll.util.MsgResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description="课程相关的接口")
@RestController
@RequestMapping("/course")
public class CourseController {
	
	@Autowired
	private ICourseService courseService;
	
	@ApiOperation(value="通过id删除课程信息")
	@GetMapping(value="deleteById")
	public MsgResponse deleteById(@RequestParam long id) {
		try {
			courseService.deleteById(id);
			return MsgResponse.success("删除成功", null);
		} catch (Exception e) {
			return MsgResponse.error(e.getMessage());
		}
	}
	
	@ApiOperation(value="批量删除课程信息")
	@PostMapping(value="batchDelete")
	public MsgResponse batchDelete(@RequestParam long[] ids) {
		try {
			courseService.batchDelete(ids);
			return MsgResponse.success("批量删除成功", null);
		} catch (Exception e) {
			return MsgResponse.error(e.getMessage());
		}
	}
	
	@ApiOperation(value="保存或更新课程信息，如果参数中id不为空表示更新操作，否则表示保存操作")
	@PostMapping(value="saveOrUpdate")
	public MsgResponse saveOrUpdate(@ModelAttribute Course course) {
		try {
			courseService.saveOrUpdate(course);
			return MsgResponse.success("保存或更新成功", null);
		} catch (Exception e) {
			return MsgResponse.error(e.getMessage());
		}
	}
	
	@ApiOperation(value="查询所有课程信息")
	@GetMapping("findAllCourse")
	public MsgResponse findAllCourse(){
		try {
			List<Course> list = courseService.findAll();
			// 返回成功信息
			return MsgResponse.success("success", list);
		} catch (Exception e) {
			e.printStackTrace();
			// 返回失败信息
			return MsgResponse.error(e.getMessage()) ;
		}
	}
	
	
	
	
}
