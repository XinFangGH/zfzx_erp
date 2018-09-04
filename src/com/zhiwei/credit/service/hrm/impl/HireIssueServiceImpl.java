package com.zhiwei.credit.service.hrm.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.hrm.HireIssueDao;
import com.zhiwei.credit.model.hrm.HireIssue;
import com.zhiwei.credit.service.hrm.HireIssueService;

public class HireIssueServiceImpl extends BaseServiceImpl<HireIssue> implements HireIssueService{
	private HireIssueDao dao;
	
	public HireIssueServiceImpl(HireIssueDao dao) {
		super(dao);
		this.dao=dao;
	}

}