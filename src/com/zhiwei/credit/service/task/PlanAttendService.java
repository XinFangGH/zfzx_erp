package com.zhiwei.credit.service.task;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.task.PlanAttend;

public interface PlanAttendService extends BaseService<PlanAttend>{
	public boolean deletePlanAttend(Long planId,Short isDep,Short isPrimary);
}


