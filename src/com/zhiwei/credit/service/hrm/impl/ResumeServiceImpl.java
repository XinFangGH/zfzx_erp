package com.zhiwei.credit.service.hrm.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.hrm.ResumeDao;
import com.zhiwei.credit.model.hrm.Resume;
import com.zhiwei.credit.service.hrm.ResumeService;

public class ResumeServiceImpl extends BaseServiceImpl<Resume> implements ResumeService{
	private ResumeDao dao;
	
	public ResumeServiceImpl(ResumeDao dao) {
		super(dao);
		this.dao=dao;
	}

}