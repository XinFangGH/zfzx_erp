package com.zhiwei.credit.service.customer.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.customer.ProjectDao;
import com.zhiwei.credit.model.customer.Project;
import com.zhiwei.credit.service.customer.ProjectService;

public class ProjectServiceImpl extends BaseServiceImpl<Project> implements ProjectService{
	private ProjectDao dao;
	
	public ProjectServiceImpl(ProjectDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public boolean checkProjectNo(String projectNo) {
		return dao.checkProjectNo(projectNo);
	}

}