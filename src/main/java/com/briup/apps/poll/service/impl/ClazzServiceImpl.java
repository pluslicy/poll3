package com.briup.apps.poll.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.briup.apps.poll.bean.Clazz;
import com.briup.apps.poll.bean.ClazzExample;
import com.briup.apps.poll.bean.extend.ClazzVM;
import com.briup.apps.poll.dao.ClazzMapper;
import com.briup.apps.poll.dao.extend.ClazzVMMapper;
import com.briup.apps.poll.service.IClazzService;
import com.briup.apps.poll.vm.PageVM;

@Service
public class ClazzServiceImpl implements IClazzService {
	@Autowired
	private ClazzMapper clazzMapper;
	@Autowired
	private ClazzVMMapper clazzVMMapper;
	
	@Override
	public List<Clazz> findAll() throws Exception {
		ClazzExample example = new ClazzExample();
		return clazzMapper.selectByExampleWithBLOBs(example);
	}

	@Override
	public List<ClazzVM> findAllClazzVM() throws Exception {
		return clazzVMMapper.selectAll();
	}

	@Override
	public void saveOrUpdateClazz(Clazz clazz) throws Exception {
		if(clazz.getId()!=null){
			clazzMapper.updateByPrimaryKeyWithBLOBs(clazz);
		} else {
			clazzMapper.insert(clazz);
		}
	}

	@Override
	public void deleteById(long id) throws Exception {
		clazzMapper.deleteByPrimaryKey(id);
		
	}

	@Override
	public void batchDelete(long[] ids) throws Exception {
		for(long id : ids) {
			clazzMapper.deleteByPrimaryKey(id);
		}
	}

	@Override
	public PageVM<ClazzVM> query(int page, int pageSize, Clazz clazz) {
		PageVM<ClazzVM> pageVM = new PageVM<>();
		// 设置模糊查询条件
		if(clazz.getName()!=null) {
			clazz.setName("%"+clazz.getName()+"%");
		}
		
		List<ClazzVM> list = clazzVMMapper.query(page, pageSize, clazz);
		long total = clazzVMMapper.count(clazz);
		pageVM.setList(list);
		pageVM.setPage(page);
		pageVM.setPageSize(pageSize);
		pageVM.setTotal(total);
		return pageVM;
	}

	@Override
	public Clazz findById(long id) {
		return clazzMapper.selectByPrimaryKey(id);
	}

}












