package com.zhiwei.credit.dao.personal;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.Date;
import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.personal.Duty;

/**
 * 
 * @author 
 *
 */
public interface DutyDao extends BaseDao<Duty>{
	/**
	 * 检查当前这个时间段是否横跨于现有的该用户班制时间
	 * @param userId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<Duty> getUserDutyByTime(Long userId,Date startTime,Date endTime);
	
	/**
	 * 取当前用户的班制
	 * @param userId
	 * @return
	 */
	public List<Duty> getCurUserDuty(Long userId);
}