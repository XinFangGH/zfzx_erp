package com.zhiwei.credit.service.task.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.task.PlanAttendDao;
import com.zhiwei.credit.model.task.PlanAttend;
import com.zhiwei.credit.service.task.PlanAttendService;

public class PlanAttendServiceImpl extends BaseServiceImpl<PlanAttend> implements PlanAttendService{
	private PlanAttendDao dao;
	
	public PlanAttendServiceImpl(PlanAttendDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public boolean deletePlanAttend(Long planId,Short isDep,Short isPrimary) {
		List<PlanAttend> list=dao.FindPlanAttend(planId,isDep,isPrimary);
		for(PlanAttend pa:list){
			dao.remove(pa.getAttendId());
		}
		return true;
	}

}