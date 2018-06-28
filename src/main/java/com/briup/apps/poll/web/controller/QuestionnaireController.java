package com.briup.apps.poll.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.briup.apps.poll.bean.Question;
import com.briup.apps.poll.bean.Questionnaire;
import com.briup.apps.poll.bean.extend.QuestionnaireVM;
import com.briup.apps.poll.service.IQuestionnaireService;
import com.briup.apps.poll.util.MsgResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description="问卷相关的接口")
@RestController
@RequestMapping("/questionnaire")
public class QuestionnaireController {
	@Autowired
	private IQuestionnaireService qnService;
	
	@ApiOperation(value="通过ID查询问卷",notes="问卷下具有问题信息")
	@GetMapping("findQuestionnaireVMById")
	public MsgResponse findQuestionnaireVMById(long id){
		try {
			QuestionnaireVM qnVM = qnService.findById(id);
			// 返回成功信息
			return MsgResponse.success("success", qnVM);
		} catch (Exception e) {
			e.printStackTrace();
			// 返回失败信息
			return MsgResponse.error(e.getMessage()) ;
		}
	}
	
	@ApiOperation(value="查询所有问卷",notes="单表")
	@GetMapping("findAllQuestionnaire")
	public MsgResponse findAllQuestionnaire(){
		try {
			List<Questionnaire> list = qnService.findAll();
			// 返回成功信息
			return MsgResponse.success("success", list);
		} catch (Exception e) {
			e.printStackTrace();
			// 返回失败信息
			return MsgResponse.error(e.getMessage()) ;
		}
	}
}
