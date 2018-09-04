package com.zhiwei.credit.dao.personal.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.credit.dao.personal.DutyRegisterDao;
import com.zhiwei.credit.model.personal.DutyRegister;

public class DutyRegisterDaoImpl extends BaseDaoImpl<DutyRegister> implements DutyRegisterDao{

	public DutyRegisterDaoImpl() {
		super(DutyRegister.class);
	}
	/**
	 * 查取当前用户当天上下班登记情况
	 * @param userId
	 * @param inOffFlag
	 * @param sectionId
	 * @return
	 */
	public DutyRegister getTodayUserRegister(Long userId,Short inOffFlag,Long sectionId){
		String hql="from DutyRegister dr where dr.appUser.userId=? and dr.registerDate>=? and dr.registerDate<=? and dr.inOffFlag=? and dr.dutySection.sectionId=?";
		Calendar cal=Calendar.getInstance();
		Date startTime=DateUtil.setStartDay(cal).getTime();
		Date endTime=DateUtil.setEndDay(cal).getTime();
		List<DutyRegister> list=findByHql(hql, new Object[]{userId,startTime,endTime,inOffFlag,sectionId});
		
		if(list.size()>0){
			return list.get(0);
		}
		
		return null;
	}

}