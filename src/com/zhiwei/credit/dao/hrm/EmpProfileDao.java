package com.zhiwei.credit.dao.hrm;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.hrm.EmpProfile;

/**
 * 
 * @author 
 *
 */
public interface EmpProfileDao extends BaseDao<EmpProfile>{

	public boolean checkProfileNo(String profileNo);
	
}