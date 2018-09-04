package com.zhiwei.credit.service.task.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.Date;
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.task.CalendarPlanDao;
import com.zhiwei.credit.model.task.CalendarPlan;
import com.zhiwei.credit.service.task.CalendarPlanService;

public class CalendarPlanServiceImpl extends BaseServiceImpl<CalendarPlan> implements CalendarPlanService{
	private CalendarPlanDao dao;
	
	public CalendarPlanServiceImpl(CalendarPlanDao dao) {
		super(dao);
		this.dao=dao;
	}
	
	/**
	 * 今日常务
	 * @param userId
	 * @param pb
	 * @return
	 */
	public List<CalendarPlan> getTodayPlans(Long userId,PagingBean pb){
		return dao.getTodayPlans(userId, pb);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.zhiwei.credit.service.task.CalendarPlanService#getByPeriod(java.lang.Long, java.util.Date, java.util.Date)
	 */
	public List<CalendarPlan> getByPeriod(Long userId,Date startTime,Date endTime){
		return dao.getByPeriod(userId, startTime, endTime);
	}

	@Override
	public List showCalendarPlanByUserId(Long userId, PagingBean pb) {
		// TODO Auto-generated method stub
		return dao.showCalendarPlanByUserId(userId, pb);
	}

}