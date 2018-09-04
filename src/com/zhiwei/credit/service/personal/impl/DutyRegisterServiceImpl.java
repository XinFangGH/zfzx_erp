package com.zhiwei.credit.service.personal.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.credit.dao.personal.DutyRegisterDao;
import com.zhiwei.credit.dao.personal.DutySectionDao;
import com.zhiwei.credit.model.personal.DutyRegister;
import com.zhiwei.credit.model.personal.DutySection;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.personal.DutyRegisterService;

/**
 * 上下班登记Service
 * @author csx
 *
 */
public class DutyRegisterServiceImpl extends BaseServiceImpl<DutyRegister> implements DutyRegisterService{
	private DutyRegisterDao dao;
	
	@Resource
	private DutySectionDao dutySectionDao;
	
	public DutyRegisterServiceImpl(DutyRegisterDao dao) {
		super(dao);
		this.dao=dao;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.zhiwei.credit.service.personal.DutyRegisterService#signInOff(java.lang.Long, java.lang.Short, com.zhiwei.credit.model.system.AppUser, java.util.Date)
	 */
	public void signInOff(Long sectionId,Short signInOff,AppUser curUser,Date registerDate) {
		DutySection dutySection=dutySectionDao.get(sectionId);

		//检查用户是否已经过了签到时间，若过了，则不能再签到，此时会当旷工处理 TODO

		//检查用户当前是否已经签到，若已经签到，不能进行再次签到 
		DutyRegister dutyRegister=dao.getTodayUserRegister(curUser.getUserId(), signInOff, sectionId);
		if(dutyRegister!=null){//已经签到
			return;
		}
		
		DutyRegister register=new DutyRegister();
		register.setAppUser(curUser);
		register.setFullname(curUser.getFullname());
		//当前登记的时间
		Calendar regiserCal=Calendar.getInstance();
		regiserCal.setTime(registerDate);
		register.setRegisterDate(registerDate);
		register.setDayOfWeek(regiserCal.get(Calendar.DAY_OF_WEEK));
		register.setInOffFlag(signInOff);
		//设置班次
		register.setDutySection(dutySection);
		//比较当前的时间与规定上班时间，比较前，把规定的时间设置为当天
		
		Calendar startCalendar=Calendar.getInstance();
		if(DutyRegister.SIGN_IN.equals(signInOff)){//签到
			startCalendar.setTime(dutySection.getDutyStartTime());
		}else{//签退
			startCalendar.setTime(dutySection.getDutyEndTime());
		}
		
		DateUtil.copyYearMonthDay(startCalendar, regiserCal);
		
		//登记时间若>规定上班时间，则算迟到，并且计算迟到分钟
		if(DutyRegister.SIGN_IN.equals(signInOff)){//签到
			if(regiserCal.compareTo(startCalendar)>0){//迟到 
				register.setRegFlag(DutyRegister.REG_FLAG_LATE);
				//计算迟到的分钟
				Long minis=(regiserCal.getTimeInMillis()-startCalendar.getTimeInMillis())/(1000*60);
				register.setRegMins(minis.intValue());
			}else{
				register.setRegFlag(DutyRegister.REG_FLAG_NORMAL);
				register.setRegMins(0);
			}
		}else{//签退
			if(regiserCal.compareTo(startCalendar)<0){//早退 
				register.setRegFlag(DutyRegister.REG_FLAG_EARLY_OFF);
				//计算早退分钟
				Long minis=(startCalendar.getTimeInMillis()-regiserCal.getTimeInMillis())/(1000*60);
				register.setRegMins(minis.intValue());
			}else{
				register.setRegFlag(DutyRegister.REG_FLAG_NORMAL);
				register.setRegMins(0);
			}
		}

		save(register);
	}
	
	/**
	 * 查取当前用户当天上下班登记情况
	 * @param userId
	 * @param inOffFlag
	 * @param sectionId
	 * @return
	 */
	public DutyRegister getTodayUserRegister(Long userId,Short inOffFlag,Long sectionId){
		return dao.getTodayUserRegister(userId, inOffFlag, sectionId);
	}

}