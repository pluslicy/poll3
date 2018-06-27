package com.briup.apps.poll.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.briup.apps.poll.bean.Question;
import com.briup.apps.poll.bean.extend.QuestionVM;
import com.briup.apps.poll.service.IQuestionService;
import com.briup.apps.poll.util.MsgResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description="题库相关的接口")
@RestController
@RequestMapping("/question")
public class QuestionController {
	@Autowired
	private IQuestionService questionService;
	
	@ApiOperation(value="保存或修改问题",
			notes="当id不为空表示修改，否则表示更新，保存和更新的时候需要提交选项数据")
	@PostMapping("saveOrUpdateQuestion")
	public MsgResponse saveOrUpdateQuestion(QuestionVM questionVM){
		try {
			questionService.saveOrUpdateQuestionVM(questionVM);
			return MsgResponse.success("保存成功", null);
		} catch (Exception e) {
			e.printStackTrace();
			return MsgResponse.error(e.getMessage());
		}
		
	}
	
	
	@ApiOperation(value="查询所有问题",notes="单表")
	@GetMapping("findAllQuestion")
	public MsgResponse findAllQuestion(){
		try {
			List<Question> list = questionService.findAll();
			// 返回成功信息
			return MsgResponse.success("success", list);
		} catch (Exception e) {
			e.printStackTrace();
			// 返回失败信息
			return MsgResponse.error(e.getMessage()) ;
		}
	}
	
	@ApiOperation(value="查询所有问题",notes="问题中包含该问题所有的属性信息")
	@GetMapping("findAllQuestionVM")
	public MsgResponse findAllQuestionVM(){
		try {
			List<QuestionVM> list = questionService.findAllQuestionVM();
			// 返回成功信息
			return MsgResponse.success("success", list);
		} catch (Exception e) {
			e.printStackTrace();
			// 返回失败信息
			return MsgResponse.error(e.getMessage()) ;
		}
	}
}
