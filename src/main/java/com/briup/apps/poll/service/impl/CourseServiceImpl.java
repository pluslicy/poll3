package com.briup.apps.poll.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.briup.apps.poll.bean.Course;
import com.briup.apps.poll.bean.CourseExample;
import com.briup.apps.poll.dao.CourseMapper;
import com.briup.apps.poll.service.ICourseService;

@Service
public class CourseServiceImpl implements ICourseService {
	@Autowired
	private CourseMapper courseMapper;
	
	
	@Override
	public List<Course> findAll() throws Exception {
		CourseExample example = new CourseExample();
		return courseMapper.selectByExampleWithBLOBs(example);
	}

	@Override
	public Course findById(long id) throws Exception {
		return courseMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Course> query(String keywords) throws Exception {
		CourseExample example = new CourseExample();
		//添加了一个条件，name属性中包含keywords关键字
		example.createCriteria().andNameLike(keywords);
		return courseMapper.selectByExampleWithBLOBs(example);
	}

	@Override
	public void saveOrUpdate(Course course) throws Exception {
		if(course.getId()!=null){
			//更新
			courseMapper.updateByPrimaryKey(course);
		} else {
			//插入
			courseMapper.insert(course);
		}
	}

	@Override
	public void deleteById(long id) throws Exception {
		courseMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void batchDelete(List<Long> ids) throws Exception {
		for(long id : ids){
			courseMapper.deleteByPrimaryKey(id);
		}
	}
}













