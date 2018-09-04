package com.zhiwei.credit.dao.flow;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
*/
import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.flow.ProDefRights;

/**
 * 
 * @author 
 *
 */
public interface ProDefRightsDao extends BaseDao<ProDefRights>{

	public ProDefRights findByDefId(Long defId);

	public ProDefRights findByTypeId(Long proTypeId);
	
}