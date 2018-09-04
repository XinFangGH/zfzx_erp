package com.zhiwei.credit.service.system.impl;

import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.system.JobDao;
import com.zhiwei.credit.model.hrm.Job;
import com.zhiwei.credit.service.system.JobService;

/**
 * @description 岗位管理
 * @class JObServiceImpl
 * @author YHZ
 * @company www.credit-software.com
 * @data 2010-12-23AM
 * 
 */
public class JobServiceImpl extends BaseServiceImpl<Job> implements JobService {
	private JobDao dao;

	public JobServiceImpl(JobDao dao) {
		super(dao);
		this.dao = dao;
	}

	@Override
	public List<Job> findByDep(Long jobId) {
		return dao.findByDep(jobId);
	}

	/**
	 * 根据parentId查询
	 */
	@Override
	public List<Job> findByCondition(Long parentId) {
		return dao.findByCondition(parentId);
	}

	/**
	 * 编辑
	 */
	@Override
	public void edit(Job job) {
		dao.edit(job);
	}

}