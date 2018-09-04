package com.zhiwei.credit.dao.hrm;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.hrm.StandSalary;

/**
 * 
 * @author 
 *
 */
public interface StandSalaryDao extends BaseDao<StandSalary>{

	public boolean checkStandNo(String standardNo);
	/**
	 * 查找审核通过的工资标准列表
	 * @return  通过的工资标准列表
	 */
	public List<StandSalary> findByPassCheck();
	
}