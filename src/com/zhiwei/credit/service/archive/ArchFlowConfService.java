package com.zhiwei.credit.service.archive;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.archive.ArchFlowConf;

public interface ArchFlowConfService extends BaseService<ArchFlowConf>{
	/**
	 * 根据类型来查找配置
	 * @param archType
	 * @return
	 */
	public ArchFlowConf getByFlowType(Short archType);
	/**
	 * 
	 */
	public Long getDefId(Short archType);
}


