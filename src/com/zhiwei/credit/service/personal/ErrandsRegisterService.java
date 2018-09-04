package com.zhiwei.credit.service.personal;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.action.flow.FlowRunInfo;
import com.zhiwei.credit.model.personal.ErrandsRegister;

public interface ErrandsRegisterService extends BaseService<ErrandsRegister>{
	/**
	 * 在流程中保存流程申请记录
	 * @param flowRunInfo 流程相关运行信息
	 * @return
	 */
	public Integer saveRegister(FlowRunInfo flowRunInfo);
}


