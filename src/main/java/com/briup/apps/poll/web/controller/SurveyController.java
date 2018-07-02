package com.briup.apps.poll.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.briup.apps.poll.bean.Answers;
import com.briup.apps.poll.bean.Survey;
import com.briup.apps.poll.bean.extend.SurveyVM;
import com.briup.apps.poll.service.IAnswersService;
import com.briup.apps.poll.service.ISurveyService;
import com.briup.apps.poll.util.MsgResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description="课调相关接口")
@RestController
@RequestMapping("/survey")
public class SurveyController {
	@Autowired
	private ISurveyService surveyService;
	@Autowired
	private IAnswersService answersService;
	
	@ApiOperation(value="去审核课调", 
			notes="返回课调的信息以及课调下所有答卷信息")
	@GetMapping("toCheckSurvey")
	public MsgResponse toCheckSurvey(long id){
		try {
			//1. 通过id查询课调信息
			SurveyVM surveyVM = surveyService.findById(id);
			//2. 如果课调状态为未审核才能审核
			if(surveyVM.getStatus().equals(Survey.STATUS_CHECK_UN)){
				//查询出该课调写所有答卷
				List<Answers> list = answersService.findAnswersBySurveyId(id);
				//计算出课调的平均分
				//所有单个平均分的总和
				double total = 0;
				for(Answers answer : list){
					//["5","5","4"]
					String[] arr = answer.getSelections().split("[|]");
					double singleTotal = 0;
					for(String a: arr){
						singleTotal += Integer.parseInt(a);
					}
					//每个学生对于老师的平均分
					double singleAverage = singleTotal/arr.length;
					total += singleAverage;
				}
				double average = total/list.size();
				
			} else {
				return MsgResponse.error("课调状态不合法");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	@ApiOperation(value="开启课调", 
			notes="只有在课调状态为未开启的时候才能开启课调")
	@GetMapping("beginSurvey")
	public MsgResponse beginSurvey(long id){
		try {
			//1. 通过id查询出课调
			Survey survey = surveyService.findSurveyById(id);
			//2. 修改课调的状态/课调编号
			if(survey.getStatus().equals(Survey.STATUS_INIT)){
				//2.1 将课调状态设置为 开启
				survey.setStatus(Survey.STATUS_BEGIN);
				//2.2 将课调的code设置为当前课调的ID，后期要通过code找survey
				survey.setCode(survey.getId().toString());
				//2.3 执行保存或更新操作
				surveyService.saveOrUpdate(survey);
				return MsgResponse.success("开启成功", null);
			} else {
				return MsgResponse.error("当前课调状态必须为未开启状态");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return MsgResponse.error(e.getMessage());
		}
		
	}
	
	
	
	
	@ApiOperation(value="批量删除课调", 
			notes="级联课调下的答卷信息")
	@PostMapping("batchDeleteSurveyById")	
	public MsgResponse batchDeleteSurveyById(long[] ids){
		try {
			surveyService.batchDelete(ids);
			return MsgResponse.success("批量删除成功", null);
		} catch (Exception e) {
			e.printStackTrace();
			return MsgResponse.error(e.getMessage());
		}
	}
	
	@ApiOperation(value="通过ID删除课调", 
			notes="级联课调下的答卷信息")
	@GetMapping("deleteSurveyById")	
	public MsgResponse deleteSurveyById(long id){
		try {
			surveyService.deleteById(id);
			return MsgResponse.success("删除成功", null);
		} catch (Exception e) {
			e.printStackTrace();
			return MsgResponse.error(e.getMessage());
		}
	}
	
	@ApiOperation(value="通过ID查询课调", 
			notes="级联查询课程中的课程，班级，用户，问卷")
	@GetMapping("findSurveyVMById")	
	public MsgResponse findSurveyVMById(long id){
		try {
			SurveyVM surveyVM = surveyService.findById(id);
			return MsgResponse.success("success", surveyVM);
		} catch (Exception e) {
			e.printStackTrace();
			return MsgResponse.error(e.getMessage());
		}
	}
	
	@ApiOperation(value="级联查询所有课调", 
			notes="级联查询课程中的课程，班级，用户，问卷")
	@GetMapping("findAllSurveyVM")	
	public MsgResponse findAllSurveyVM(){
		try {
			List<SurveyVM> list = surveyService.findAll();
			return MsgResponse.success("success", list);
		} catch (Exception e) {
			e.printStackTrace();
			return MsgResponse.error(e.getMessage());
		}
	}
	
	@ApiOperation(value="保存或更新课调", 
			notes="如果参数中包含id表示修改，否则表示添加。只需要接收clazzId,courseId,teacherId,questionnaireId")
	@PostMapping("saveOrUpdateSurvey")	
	public MsgResponse saveOrUpdateSurvey(Survey survey){
		try {
			surveyService.saveOrUpdate(survey);
			return MsgResponse.success("保存或更新成功", null);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return MsgResponse.error(e.getMessage());
		}
	}

	
	
	
	
	
	
	
	
}
