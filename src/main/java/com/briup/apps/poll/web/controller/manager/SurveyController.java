package com.briup.apps.poll.web.controller.manager;

import java.text.DecimalFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.briup.apps.poll.bean.Answers;
import com.briup.apps.poll.bean.Clazz;
import com.briup.apps.poll.bean.Survey;
import com.briup.apps.poll.bean.SurveyLog;
import com.briup.apps.poll.bean.extend.SurveyVM;
import com.briup.apps.poll.service.IAnswersService;
import com.briup.apps.poll.service.IClazzService;
import com.briup.apps.poll.service.ISurveyLogService;
import com.briup.apps.poll.service.ISurveyService;
import com.briup.apps.poll.util.MsgResponse;
import com.briup.apps.poll.vm.PageVM;
import com.briup.apps.poll.vm.SurveyAndAnswersVM;
import com.briup.apps.poll.vm.SurveyPVM;
import com.briup.apps.poll.vm.SurveyStatisticsVM;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description="课调相关接口")
@RestController
@RequestMapping("/manager/survey")
public class SurveyController {
	@Autowired
	private ISurveyService surveyService;
	@Autowired
	private IAnswersService answersService;
	@Autowired
	private IClazzService clazzService;
	@Autowired
	private ISurveyLogService surveyLogService;
	
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
	
	@ApiOperation(value="按照月份进行统计", 
			notes="")
	@PostMapping("statisticsByMonth")
	public MsgResponse statisticsByMonth(@RequestParam("month") String month){
		try {
			List<SurveyStatisticsVM> list = surveyService.statisticsByMonth(month);
			return MsgResponse.success("success", list);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return MsgResponse.error(e.getMessage());
		}
		
		
	}
	
	
	
	
	@ApiOperation(value="根据班级ID查询出该班级下所有的已审核的课调", 
			notes="")
	@GetMapping("findSurveyByClazzId")
	public MsgResponse findSurveyByClazzId(long id){
		try {
			List<SurveyVM> list = surveyService.findByClazzIdAndCheckPass(id);
			return MsgResponse.success("success", list);
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

	
	@ApiOperation(value="审核课调", 
			notes="只有当前课调的状态为未审核的时候才能被审核，"
					+ "参数id表示课调编号，参数status的取值只能为0/1,"
					+ "如果是0表示审核不通过，如果是1表示审核通过")
	@GetMapping("checkSurvey")
	public MsgResponse checkSurvey(long id,int status){
		try {
			//1. 通过id找课调
			Survey survey = surveyService.findSurveyById(id);
			//2. 判断当前课调的状态是否为未审核状态
			if(survey!=null && survey.getStatus().equals(Survey.STATUS_CHECK_UN)){
				if(status == 0){
					//2.1 审核不通过
					survey.setStatus(Survey.STATUS_CHECK_NOPASS);
				} else {
					//2.0 审核通过
					survey.setStatus(Survey.STATUS_CHECK_PASS);
					// 设置课调次数
					long clazzId = survey.getClazzId();
					Clazz clazz = clazzService.findById(clazzId);
					int clazzNumber = clazz.getSurveyNumber()==null?0:clazz.getSurveyNumber();
					clazz.setSurveyNumber(++clazzNumber);
					clazzService.saveOrUpdateClazz(clazz);
				}
				surveyService.saveOrUpdate(survey);
				return MsgResponse.success("审核完成！", null);
			} else {
				return MsgResponse.error("课调状态不合法");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return MsgResponse.error(e.getMessage());
		}
	}
	
	
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
				double average =total==0 ? 0: total/list.size();
				DecimalFormat df = new DecimalFormat("#.00");
				double av = Double.parseDouble(df.format(average));
				surveyVM.setAverage(av);
				
				//将平均分保存到数据库中
				Survey survey = surveyService.findSurveyById(id);
				//如果数据库中的平均分没有设定，我们再进行设定，否则不做操作
				if(survey.getAverage()== null || survey.getAverage().isEmpty()){
					System.out.println("==============="+av+","+average);
					survey.setAverage(av+"");
					surveyService.saveOrUpdate(survey);
				}
				
				//如何将surveyVM 和list 返回,封装到一个对象中
				SurveyAndAnswersVM savm = new SurveyAndAnswersVM();
				savm.setSurveyVM(surveyVM);
				savm.setAnswers(list);
				return MsgResponse.success("success", savm);
			} else {
				return MsgResponse.error("课调状态不合法");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@ApiOperation(value="关闭课调", 
			notes="只有在课调状态为开启的时候才能关闭课调")
	@GetMapping("stopSurvey")
	public MsgResponse stopSurvey(long id){
		try {
			//1. 通过id查询出课调
			Survey survey = surveyService.findSurveyById(id);
			if(survey!=null && survey.getStatus().equals(Survey.STATUS_BEGIN)){
				survey.setStatus(Survey.STATUS_CHECK_UN);
				surveyService.saveOrUpdate(survey);
				return MsgResponse.success("关闭课调成功",null);
			} else {
				return MsgResponse.error("当前课调状态必须为未开启状态");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return MsgResponse.error(e.getMessage());
		}
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
	
	
	@ApiOperation(value="查询课调进度")
	@GetMapping("surveyProcess")	
	public MsgResponse surveyProcess(long id){
		try {
			SurveyLog log = new SurveyLog();
			log.setSurveyId(id);
			List<SurveyLog> list = surveyLogService.query(log);
			return MsgResponse.success("success", list.size());
		} catch (Exception e) {
			e.printStackTrace();
			return MsgResponse.error(e.getMessage());
		}
	}
	
	
	
}
