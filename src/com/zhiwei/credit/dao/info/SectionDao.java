package com.zhiwei.credit.dao.info;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
*/
import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.info.Section;

/**
 * 
 * @author 
 *
 */
public interface SectionDao extends BaseDao<Section>{
	public Integer getLastColumn();
}