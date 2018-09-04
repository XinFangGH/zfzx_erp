package com.zhiwei.credit.dao.task;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.task.WorkPlan;

/**
 * 
 * @author 
 *
 */
public interface WorkPlanDao extends BaseDao<WorkPlan>{
	
	/**
	 * 查找部门分配的计划
	 */
	public List<WorkPlan> findByDepartment(WorkPlan workPlan,AppUser user,PagingBean pb);
	
	/**
	 *计划到期提醒
	 */
	public List<WorkPlan> sendWorkPlanTime();
}