package com.zhiwei.credit.service.task.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.task.PlanTypeDao;
import com.zhiwei.credit.model.task.PlanType;
import com.zhiwei.credit.service.task.PlanTypeService;

public class PlanTypeServiceImpl extends BaseServiceImpl<PlanType> implements PlanTypeService{
	private PlanTypeDao dao;
	
	public PlanTypeServiceImpl(PlanTypeDao dao) {
		super(dao);
		this.dao=dao;
	}

}