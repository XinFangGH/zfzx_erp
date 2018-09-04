package com.zhiwei.credit.service.flow;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
*/
import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.flow.ProDefRights;

public interface ProDefRightsService extends BaseService<ProDefRights>{

	public ProDefRights findByTypeId(Long proTypeId);

	public ProDefRights findByDefId(Long defId);
	
}


