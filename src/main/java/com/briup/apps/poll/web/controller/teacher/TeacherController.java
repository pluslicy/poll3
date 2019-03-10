/**
 * Project Name:poll3
 * File Name:TeacherController.java
 * Package Name:com.briup.apps.poll.web.controller.teacher
 * Date:2018年11月26日下午2:39:58
 * Copyright (c) 2018, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.briup.apps.poll.web.controller.teacher;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.briup.apps.poll.bean.Answers;
import com.briup.apps.poll.bean.Course;
import com.briup.apps.poll.bean.PollUser;
import com.briup.apps.poll.bean.Survey;
import com.briup.apps.poll.bean.User;
import com.briup.apps.poll.bean.extend.ClazzVM;
import com.briup.apps.poll.bean.extend.QuestionnaireVM;
import com.briup.apps.poll.bean.extend.SurveyVM;
import com.briup.apps.poll.service.IAnswersService;
import com.briup.apps.poll.service.IPOllUserService;
import com.briup.apps.poll.service.ISurveyService;
import com.briup.apps.poll.util.MsgResponse;
import com.briup.apps.poll.vm.PageVM;
import com.briup.apps.poll.vm.SurveyAndAnswersVM;
import com.briup.apps.poll.vm.SurveyPVM;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * ClassName:TeacherController <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2018年11月26日 下午2:39:58 <br/>
 * @author   lichunyu
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
@Api(description="教师相关接口")
@RestController
@RequestMapping("/teacher")
public class TeacherController {
	@Autowired
	private ISurveyService surveyService;
	@Autowired
	private IAnswersService answersService;
	@Autowired
	private IPOllUserService userService;
	
	@ApiOperation(value="更新用户密码", notes="")
	@PostMapping("updateUserPassword")
	public MsgResponse updateUserPassword(User user) {
		try {
			if(user.getId()!=null&& user.getPassword()!=null) {
				userService.updateUserPassword(user);
				return MsgResponse.success("更新成功，请重新登录", null);
			} else {
				return MsgResponse.error("参数异常");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return MsgResponse.error(e.getMessage());
		}
	}
	
	@ApiOperation(value="更新用户信息", notes="")
	@PostMapping("updateUser")
	public MsgResponse updateUser(User user) {
		try {
			UserDetails result= userService.updateUser(user);
			return MsgResponse.success("更新成功", result);
		} catch (Exception e) {
			e.printStackTrace();
			return MsgResponse.error(e.getMessage());
		}
	}
	
	@ApiOperation(value="多条件查询课调信息", 
			notes="")
	@PostMapping("querySurvey")
	public MsgResponse querySurvey(int page,int pageSize,SurveyPVM survey){
		try {
			PageVM<SurveyVM> pageVm = surveyService.query(page, pageSize, survey);
			return MsgResponse.success("success", pageVm);
		} catch (Exception e) {
			e.printStackTrace();
			return MsgResponse.error(e.getMessage());
		}
	}
	
	@ApiOperation(value="预览课调", 
			notes="只有当课调状态为审核通过的时候才能预览课调")
	@GetMapping("previewSurvey")
	public MsgResponse previewSurvey(long id){
		try {
			//1. 课调的信息（课程，班级，讲师，问卷，平均分） SurveyVM
			SurveyVM surveyVM = surveyService.findById(id);
			if(surveyVM!=null && 
					surveyVM.getStatus().equals(Survey.STATUS_CHECK_PASS)){
				//2. 课调的结果 主观题列表 Answers
				List<Answers> answers = answersService.findAnswersBySurveyId(id);
				//3. 将课调信息和课调答卷信息封装到一个对象中
				SurveyAndAnswersVM savm = new SurveyAndAnswersVM();
				savm.setSurveyVM(surveyVM);
				savm.setAnswers(answers);
				return MsgResponse.success("success", savm);
			} else {
				return MsgResponse.error("课调状态不合法");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return MsgResponse.error(e.getMessage());
		}
	}
	
	@ApiOperation("通过课调ID下载课调结果")
	@GetMapping(value="downLoadSurveyResultById")
	public void downLoadSurveyResultById(
			HttpServletResponse response,long id){
		try {
			//1. 查询出课调信息和答卷信息
			SurveyVM surveyVM = surveyService.findById(id);
			ClazzVM clazz = surveyVM.getClazzVM();
			Course course = surveyVM.getCourse();
			PollUser user = surveyVM.getUser();
			QuestionnaireVM qnVM = surveyVM.getQnVM();
			
			List<Answers> answers = answersService.findAnswersBySurveyId(id);
			//2. 创建Excel
			//2.1 创建excel
			XSSFWorkbook workbook = new XSSFWorkbook();
			//设置总体样式
			XSSFCellStyle titleCellStyle = workbook.createCellStyle();
			titleCellStyle.setAlignment(HorizontalAlignment.CENTER);
			
			//2.2 在excel中创建sheet
			XSSFSheet sheet = workbook.createSheet();
			//2.3在sheet创建行 
			//2.3.1（标题行）
			XSSFRow row = sheet.createRow(0);
			//2.4在row中创建一列
			String title = clazz.getName()+qnVM.getName();
			XSSFCell cell = row.createCell(0);
			cell.setCellValue(title);
			cell.setCellStyle(titleCellStyle);
			//设置第一行的跨列
			CellRangeAddress cra = new CellRangeAddress(0, 0, 0, 7);
			sheet.addMergedRegion(cra);
			//2.3.1（课调信息行）
			XSSFRow socendRow = sheet.createRow(1);
			XSSFCell cell10 = socendRow.createCell(0);
			cell10.setCellValue("讲师名称");
			XSSFCell cell11 = socendRow.createCell(1);
			cell11.setCellValue(user.getNickname());
			XSSFCell cell12 = socendRow.createCell(2);
			cell12.setCellValue("班级名称");
			XSSFCell cell13 = socendRow.createCell(3);
			cell13.setCellValue(clazz.getName());
			XSSFCell cell14 = socendRow.createCell(4);
			cell14.setCellValue("课程名称");
			XSSFCell cell15 = socendRow.createCell(5);
			cell15.setCellValue(course.getName());
			XSSFCell cell16 = socendRow.createCell(6);
			cell16.setCellValue("平均分");
			XSSFCell cell17 = socendRow.createCell(7);
			cell17.setCellValue(surveyVM.getAverage());
		
			for(int i=0;i<answers.size();i++){
				Answers answer = answers.get(i);
				XSSFRow dyRow = sheet.createRow(i+2);
				XSSFCell cell_index = dyRow.createCell(0);
				cell_index.setCellValue(i+1);
				XSSFCell cell_content = dyRow.createCell(1);
				cell_content.setCellValue(answer.getContent());
				//设置跨列
				CellRangeAddress dyCra = new CellRangeAddress(i+2, i+2, 1, 7);
				sheet.addMergedRegion(dyCra);
			}
			
			//2. 将excel写入到输出流
			response.setHeader("content-Type", "application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode("test.xlsx", "utf-8"));
			OutputStream os = response.getOutputStream();
			workbook.write(os);
			//3. 关闭资源
			os.flush();
			workbook.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

