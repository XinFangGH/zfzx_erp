package com.zhiwei.credit.dao.flow;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
*/
import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.flow.ProcessModule;

/**
 * 
 * @author 
 *
 */
public interface ProcessModuleDao extends BaseDao<ProcessModule>{

	public ProcessModule getByKey(String string);
	
}