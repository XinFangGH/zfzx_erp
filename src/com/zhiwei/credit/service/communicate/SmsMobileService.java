package com.zhiwei.credit.service.communicate;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.communicate.SmsMobile;

public interface SmsMobileService extends BaseService<SmsMobile>{
	public List<SmsMobile> getNeedToSend();
	public void saveSms(String userIds,String content);
	public void sendSms();
	public void sendOneSms(SmsMobile smsMobile);
}


