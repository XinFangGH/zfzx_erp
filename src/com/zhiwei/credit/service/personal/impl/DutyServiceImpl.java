package com.zhiwei.credit.service.personal.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.Date;
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.personal.DutyDao;
import com.zhiwei.credit.model.personal.Duty;
import com.zhiwei.credit.service.personal.DutyService;

public class DutyServiceImpl extends BaseServiceImpl<Duty> implements DutyService{
	private DutyDao dao;
	
	public DutyServiceImpl(DutyDao dao) {
		super(dao);
		this.dao=dao;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.zhiwei.credit.service.personal.DutyService#isExistDutyForUser(java.lang.Long, java.util.Date, java.util.Date)
	 */
	public boolean isExistDutyForUser(Long userId, Date startTime, Date endTime) {
		List dutyList=dao.getUserDutyByTime(userId, startTime, endTime);
		if(dutyList.size()>0) return true;
		return false;
	}
	
	/**
	 * 取当前用户的班制
	 * @param userId
	 * @return
	 */
	public Duty getCurUserDuty(Long userId){
		List<Duty> dutyList=dao.getCurUserDuty(userId);
		if(dutyList.size()>0){
			return dutyList.get(0);
		}
		return null;
	}
	
}