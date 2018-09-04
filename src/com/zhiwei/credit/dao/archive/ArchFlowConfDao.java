package com.zhiwei.credit.dao.archive;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.archive.ArchFlowConf;

/**
 * 
 * @author 
 *
 */
public interface ArchFlowConfDao extends BaseDao<ArchFlowConf>{
	/**
	 * 根据类型来查找配置
	 * @param archType
	 * @return
	 */
	public ArchFlowConf getByFlowType(Short archType);
}