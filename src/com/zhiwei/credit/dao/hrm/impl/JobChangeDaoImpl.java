package com.zhiwei.credit.dao.hrm.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.hrm.JobChangeDao;
import com.zhiwei.credit.model.hrm.JobChange;

public class JobChangeDaoImpl extends BaseDaoImpl<JobChange> implements JobChangeDao{

	public JobChangeDaoImpl() {
		super(JobChange.class);
	}

}