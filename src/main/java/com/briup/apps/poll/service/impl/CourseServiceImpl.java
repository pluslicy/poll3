package com.briup.apps.poll.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.briup.apps.poll.bean.Course;
import com.briup.apps.poll.bean.CourseExample;
import com.briup.apps.poll.dao.CourseMapper;
import com.briup.apps.poll.dao.extend.CourseVMMapper;
import com.briup.apps.poll.service.ICourseService;
import com.briup.apps.poll.vm.PageVM;

@Service
public class CourseServiceImpl implements ICourseService {
	@Autowired
	private CourseMapper courseMapper;
	@Autowired
	private CourseVMMapper courseVMMapper;
	
	
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
			courseMapper.updateByPrimaryKeyWithBLOBs(course);
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
	public void batchDelete(long[] ids) throws Exception {
		for(long id : ids){
			courseMapper.deleteByPrimaryKey(id);
		}
	}

	@Override
	public PageVM<Course> query(int page, int pageSize, Course course) {
		if(course.getName()!=null) {
			course.setName("%"+course.getName()+"%");
		}
		
		List<Course> list = courseVMMapper.query(page, pageSize, course);
		long total = courseVMMapper.count(course);
		PageVM<Course> pageVM = new PageVM<>(page, pageSize, total, list);
		return pageVM;
	}
}













