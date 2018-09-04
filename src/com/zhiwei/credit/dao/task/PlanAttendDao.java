package com.zhiwei.credit.dao.task;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.task.PlanAttend;

/**
 * 
 * @author 
 *
 */
public interface PlanAttendDao extends BaseDao<PlanAttend>{
	/**
	 * 根据ID来查找参与人
	 * @param planId
	 * @return
	 */
	public List<PlanAttend> FindPlanAttend(Long planId,Short isDep,Short isPrimary);
}