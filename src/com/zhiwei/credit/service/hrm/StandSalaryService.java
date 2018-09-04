package com.zhiwei.credit.service.hrm;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.hrm.StandSalary;

public interface StandSalaryService extends BaseService<StandSalary>{

	public boolean checkStandNo(String standardNo);
	/**
	 * 查找审核通过的工资标准列表
	 * @return  通过的工资标准列表
	 */
	public List<StandSalary> findByPassCheck();
	
}


