package com.zhiwei.credit.service.personal;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.Date;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.personal.DutyRegister;
import com.zhiwei.credit.model.system.AppUser;

public interface DutyRegisterService extends BaseService<DutyRegister>{
	/**
	 * 签到,签退
	 * @param sectionId 班次
	 * @param signInOff 1=signIn 2=signOff
	 * @param curUser 用户
	 * @param 登记时间
	 */
	public void signInOff(Long sectionId,Short signInOff,AppUser curUser,Date registerDate);
	
	/**
	 * 查取当前用户当天上下班登记情况
	 * @param userId
	 * @param inOffFlag
	 * @param sectionId
	 * @return
	 */
	public DutyRegister getTodayUserRegister(Long userId,Short inOffFlag,Long sectionId);
}


