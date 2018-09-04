package com.zhiwei.credit.dao.task.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.ArrayList;
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.task.PlanAttendDao;
import com.zhiwei.credit.model.task.PlanAttend;

public class PlanAttendDaoImpl extends BaseDaoImpl<PlanAttend> implements PlanAttendDao{

	public PlanAttendDaoImpl() {
		super(PlanAttend.class);
	}

	@Override
	public List<PlanAttend> FindPlanAttend(Long planId,Short isDep,Short isPrimary) {
		StringBuffer hql=new StringBuffer("from PlanAttend vo where vo.workPlan.planId=?");
		ArrayList list=new ArrayList();
		list.add(planId);
		if(isDep!=null){
			hql.append(" and vo.isDep=?");
			list.add(isDep);
		}
		if(isPrimary!=null){
			hql.append(" and vo.isPrimary=?");
			list.add(isPrimary);
		}
		return findByHql(hql.toString(), list.toArray());
	}

}