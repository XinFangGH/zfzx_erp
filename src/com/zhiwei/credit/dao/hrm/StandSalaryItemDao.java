package com.zhiwei.credit.dao.hrm;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.hrm.StandSalaryItem;

/**
 * 
 * @author 
 *
 */
public interface StandSalaryItemDao extends BaseDao<StandSalaryItem>{

	public List<StandSalaryItem> getAllByStandardId(Long standardId);
	
}