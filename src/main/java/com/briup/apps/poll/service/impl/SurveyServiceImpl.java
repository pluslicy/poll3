package com.briup.apps.poll.service.impl;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.briup.apps.poll.bean.Survey;
import com.briup.apps.poll.bean.extend.SurveyVM;
import com.briup.apps.poll.dao.SurveyMapper;
import com.briup.apps.poll.dao.extend.SurveyVMMapper;
import com.briup.apps.poll.service.ISurveyService;
import com.briup.apps.poll.vm.PageVM;
import com.briup.apps.poll.vm.SurveyPVM;
import com.briup.apps.poll.vm.SurveyStatisticsVM;

@Service
public class SurveyServiceImpl implements ISurveyService {
	@Autowired
	private SurveyMapper surveyMapper;
	@Autowired
	private SurveyVMMapper surveyVMMapper;
	
	@Override
	public void saveOrUpdate(Survey survey) throws Exception {
		if(survey.getId()!=null){
			surveyMapper.updateByPrimaryKey(survey);
		} else {
			// status code surveyDate
			survey.setStatus(Survey.STATUS_INIT);
			survey.setCode("");
			//获取当前时间
			Date now = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String surveydate = sdf.format(now);
			survey.setSurveydate(surveydate);
			
			surveyMapper.insert(survey);
		}
	}

	@Override
	public List<SurveyVM> findAll() throws Exception {
		return surveyVMMapper.selectAll();
	}

	@Override
	public SurveyVM findById(long id) throws Exception {
		return surveyVMMapper.selectById(id);
	}

	@Override
	public void deleteById(long id) throws Exception {
		surveyMapper.deleteByPrimaryKey(id);
		
	}

	@Override
	public void batchDelete(long[] ids) throws Exception {
		for(long id:ids){
			surveyMapper.deleteByPrimaryKey(id);
		}
	}

	@Override
	public Survey findSurveyById(long id) throws Exception {
		return surveyMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<SurveyVM> findByClazzIdAndCheckPass(long id) throws Exception {
		return surveyVMMapper.selectByClazzIdAndCheckPass(id);
	}

	@Override
	public PageVM<SurveyVM> query(int page, int pageSize, SurveyPVM survey) {
		if(survey.getYear()!=null) {
			survey.setSurveydate(survey.getYear()+"%");
		}
		if(survey.getMonth()!=null) {
			survey.setSurveydate(survey.getMonth()+"%");
		}
		
		List<SurveyVM> list = surveyVMMapper.query(page, pageSize, survey);
		long total = surveyVMMapper.count(survey);
		PageVM<SurveyVM> pagevm = new PageVM<>();
		pagevm.setList(list);
		pagevm.setTotal(total);
		pagevm.setPage(page);
		pagevm.setPageSize(pageSize);
		return pagevm;
	}

	@Override
	public List<SurveyStatisticsVM> statisticsByMonth(String month) throws Exception {
		List<SurveyVM> list = surveyVMMapper.queryByMonth(month);
		List<SurveyStatisticsVM> result = new ArrayList<>();
		//计算集合			
		Map<Long, List<SurveyVM>> map = new HashMap<>();
		
		if(list.size()>0) {
			// 分组
			for(int i=0; i<list.size(); i++) {
				SurveyVM svm = list.get(i);
				if(map.containsKey(svm.getUser().getId())){
					//如果以前在，那么追加
					map.get(svm.getUser().getId()).add(svm);
				} else {
					//如果不在,初始化保存起来,初始化计算集合
					List<SurveyVM> svmList = new ArrayList<>();
					svmList.add(svm);
					//如果不在，初始化result				
					SurveyStatisticsVM ssvm = new SurveyStatisticsVM();
					ssvm.setUserId(svm.getUser().getId());
					ssvm.setUsername(svm.getUser().getNickname());
					result.add(ssvm);
					map.put(svm.getUser().getId(), svmList);
				}
			}
			// 计算平均分最低分最高分
			Set<Long> keys = map.keySet();
			Iterator<Long> keysIter = keys.iterator();
			while(keysIter.hasNext()) {
				//计算每个人的平均分计算法
				long k = keysIter.next();
				List<SurveyVM> ls= map.get(k);
				double total = 0;
				double min = 5;
				double max = 0;
				System.out.println("size:"+ls.size());
				for(SurveyVM svm : ls) {
					System.out.println("item:"+svm.getAverage());
					total += svm.getAverage();
					if(min > svm.getAverage()) {
						min = svm.getAverage();
					}
					if(max < svm.getAverage()) {
						max = svm.getAverage();
					}
				}
				double average = total / ls.size();
				
				for(SurveyStatisticsVM ssvm : result) {
					if(ssvm.getUserId() == k) {
						DecimalFormat df = new DecimalFormat("#.00");
						ssvm.setAverage(df.format(average));
						ssvm.setMax(max+"");
						ssvm.setMin(min+"");
					}
				}
			}
			
			
			
		}
		return result;
	}
	

}
