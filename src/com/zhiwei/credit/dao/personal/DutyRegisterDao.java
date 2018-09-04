package com.zhiwei.credit.dao.personal;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.personal.DutyRegister;

/**
 * 
 * @author 
 *
 */
public interface DutyRegisterDao extends BaseDao<DutyRegister>{
	/**
	 * 查取当前用户当天上下班登记情况
	 * @param userId
	 * @param inOffFlag
	 * @param sectionId
	 * @return
	 */
	public DutyRegister getTodayUserRegister(Long userId,Short inOffFlag,Long sectionId);
}