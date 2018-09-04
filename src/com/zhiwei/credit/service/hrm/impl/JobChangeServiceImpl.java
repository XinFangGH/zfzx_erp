package com.zhiwei.credit.service.hrm.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.hrm.JobChangeDao;
import com.zhiwei.credit.model.hrm.JobChange;
import com.zhiwei.credit.service.hrm.JobChangeService;

public class JobChangeServiceImpl extends BaseServiceImpl<JobChange> implements JobChangeService{
	private JobChangeDao dao;
	
	public JobChangeServiceImpl(JobChangeDao dao) {
		super(dao);
		this.dao=dao;
	}

}