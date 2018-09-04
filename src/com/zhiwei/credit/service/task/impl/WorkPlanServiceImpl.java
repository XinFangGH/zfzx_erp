package com.zhiwei.credit.service.task.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.List;

import javax.annotation.Resource;


import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.task.WorkPlanDao;
import com.zhiwei.credit.model.info.ShortMessage;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.task.WorkPlan;
import com.zhiwei.credit.service.info.ShortMessageService;
import com.zhiwei.credit.service.task.WorkPlanService;

public class WorkPlanServiceImpl extends BaseServiceImpl<WorkPlan> implements WorkPlanService{
	private WorkPlanDao dao;
	@Resource
	private ShortMessageService shortMessageService;
	
	public WorkPlanServiceImpl(WorkPlanDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<WorkPlan> findByDepartment(WorkPlan workPlan,AppUser user, PagingBean pb) {
		return dao.findByDepartment(workPlan,user, pb);
	}

	@Override
	public void sendWorkPlanTime() {
		List<WorkPlan> list = dao.sendWorkPlanTime();
		if(list.size()>0){
			StringBuffer buff = new StringBuffer("工作计划时间");
			for(WorkPlan plan : list){
				if(plan.getIsPersonal()==1){
					buff.append(plan.getPlanName()+"个人计划时间已经到"+plan.getEndTime()+"结束时间了.");
				}else{
					buff.append(plan.getPlanName()+"部门计划时间已经到"+plan.getEndTime()+"结束时间了.");
				}
			}
			buff.append("请尽快完成您所定的计划!");
			AppUser user = ContextUtil.getCurrentUser();
			if(user!=null){
				shortMessageService.save(AppUser.SYSTEM_USER,user.getUserId().toString(), buff.toString(),ShortMessage.MSG_TYPE_SYS);
				logger.info("messages had sent to the manager!"+user.getUsername());
			}else{
				logger.info("can not find the user in the system.");
			}
			logger.info(buff.toString());	
		}else{
			logger.info("没有任何计划.");
		}
	}

}