package com.zhiwei.credit.dao.task.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.task.WorkPlanDao;
import com.zhiwei.credit.model.system.AppRole;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.Department;
import com.zhiwei.credit.model.task.WorkPlan;

public class WorkPlanDaoImpl extends BaseDaoImpl<WorkPlan> implements WorkPlanDao{

	public WorkPlanDaoImpl() {
		super(WorkPlan.class);
	}

	@Override
	public List<WorkPlan> findByDepartment(WorkPlan workPlan,AppUser user, PagingBean pb) {
		StringBuffer sb=new StringBuffer();
		ArrayList list=new ArrayList();
	    if(!user.getRights().contains(AppRole.SUPER_RIGHTS)){
			sb.append("select distinct wp from WorkPlan wp,PlanAttend pa where pa.workPlan=wp and wp.status=1 and wp.isPersonal=0 and ((pa.appUser.userId=? and pa.isDep=0)");
			Department dep=user.getDepartment();
			list.add(user.getUserId());
			if(dep!=null){
				String path=dep.getPath();
			    if(StringUtils.isNotEmpty(path)){
					StringBuffer buff=new StringBuffer(path.replace(".", ","));
					buff.deleteCharAt(buff.length()-1);
					sb.append(" or (pa.department.depId in ("+buff.toString()+") and pa.isDep=1)");
				}
			}
			sb.append(")");
	    }else{
	    	sb.append("select wp from WorkPlan wp where wp.status=1 and wp.isPersonal=0");
	    }
		if(workPlan!=null){
			if(StringUtils.isNotEmpty(workPlan.getPlanName())){
				sb.append(" and wp.planName like ?");
				list.add("%"+workPlan.getPlanName()+"%");
			}
			if(StringUtils.isNotEmpty(workPlan.getPrincipal())){
				sb.append(" and wp.principal like ?");
				list.add("%"+workPlan.getPrincipal()+"%");
			}
			if(workPlan.getTypeName()!=null){
					sb.append(" and wp.typeName like ?");
					list.add("%"+workPlan.getTypeName()+"%");
			}
		}
		
		//sb.append(" group by wp.planId");
		return findByHql(sb.toString(), list.toArray(),pb);
	}

	@Override
	public List<WorkPlan> sendWorkPlanTime() {
		String dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		String hql = "select wp from WorkPlan wp where wp.isPersonal=1 and  wp.endTime <="+dateTime; 
		return findByHql(hql);
	}
}