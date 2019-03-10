package com.briup.apps.poll.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.briup.apps.poll.bean.Answers;
import com.briup.apps.poll.bean.Survey;
import com.briup.apps.poll.bean.SurveyLog;
import com.briup.apps.poll.bean.extend.SurveyVM;
import com.briup.apps.poll.service.IAnswersService;
import com.briup.apps.poll.service.ISurveyLogService;
import com.briup.apps.poll.service.ISurveyService;
import com.briup.apps.poll.util.MsgResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description="学生相关接口")
@RestController
@RequestMapping("/student")
public class StudentController {
	@Autowired
	private ISurveyService surveyService;
	@Autowired
	private IAnswersService answersService;
	@Autowired
	private ISurveyLogService surveyLogService;
	
	@ApiOperation(value="登录课调",notes="当课调状态为开启的时候才能登录课调")
	@GetMapping("loginSurvey")
	public MsgResponse loginSurvey(HttpServletRequest request, long id){
		try {
			//1. 通过id查找课调
			SurveyVM surveyVM = surveyService.findById(id);
			//2. 身份验证，判断用户ip，如果该ip下的用户已经提交过了问卷就不允许二次提交了
			String remoteAddr = "";
	        if (request != null) {
	            remoteAddr = request.getHeader("X-FORWARDED-FOR");
	            if (remoteAddr == null || "".equals(remoteAddr)) {
	                remoteAddr = request.getRemoteAddr();
	            }
	        }
	        SurveyLog log = new SurveyLog();
	        log.setIp(remoteAddr);
	        log.setSurveyId(id);
	        List<SurveyLog> list = surveyLogService.query(log);
	        if(list.size()>0) {
	        		return MsgResponse.error("您已经提交过该课调了！");
	        }
	        
			//3. 如果课调存在，并且课调的状态为"开启"才能返回课调信息，否则返回错误信息
			if(surveyVM!=null && surveyVM.getStatus().equals(Survey.STATUS_BEGIN)){
				return MsgResponse.success("success", surveyVM);
			} else {
				return MsgResponse.error("课调状态不合法");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return MsgResponse.error(e.getMessage());
		}
	}
	
	@ApiOperation(value="提交答卷")
	@PostMapping("submitAnswers")
	public MsgResponse submitAnswers(HttpServletRequest request,Answers answers){
		try {
			//1. 通过id查找课调
			SurveyVM surveyVM = surveyService.findById(answers.getSurveyId());
			if(surveyVM!=null && !surveyVM.getStatus().equals(Survey.STATUS_BEGIN)){
				return MsgResponse.error("课调状态不合法");
			}
			//2. 身份验证，判断用户ip，如果该ip下的用户已经提交过了问卷就不允许二次提交了
			String remoteAddr = "";
	        if (request != null) {
	            remoteAddr = request.getHeader("X-FORWARDED-FOR");
	            if (remoteAddr == null || "".equals(remoteAddr)) {
	                remoteAddr = request.getRemoteAddr();
	            }
	        }
			SurveyLog surveyLog = new SurveyLog();
			surveyLog.setIp(remoteAddr);
			surveyLog.setSurveyId(answers.getSurveyId());
	        List<SurveyLog> list = surveyLogService.query(surveyLog);
	        if(list.size()>0) {
	        		return MsgResponse.error("您已经提交过该课调了！");
	        }
	        
			//2. 保存答案
			answersService.saveOrUpdate(answers);
			//3. 保存课调日志
			// 封装课调日志
			
			// 设置提交时间
			Date now = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = sdf.format(now);
			surveyLog.setSurveyDate(date);
			// 设置课调ip
			surveyLogService.save(surveyLog);
			return MsgResponse.success("提交成功！您的意见是我们前进的动力！", null);
		} catch (Exception e) {
			e.printStackTrace();
			return MsgResponse.error(e.getMessage());
		}
	}
	
}

















