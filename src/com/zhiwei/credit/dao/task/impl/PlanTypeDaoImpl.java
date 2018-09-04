package com.zhiwei.credit.dao.task.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.task.PlanTypeDao;
import com.zhiwei.credit.model.task.PlanType;

public class PlanTypeDaoImpl extends BaseDaoImpl<PlanType> implements PlanTypeDao{

	public PlanTypeDaoImpl() {
		super(PlanType.class);
	}

}