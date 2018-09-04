package com.zhiwei.credit.service.flow;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
*/
import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.flow.ProcessModule;

public interface ProcessModuleService extends BaseService<ProcessModule>{

	public ProcessModule getByKey(String string);
	
}


