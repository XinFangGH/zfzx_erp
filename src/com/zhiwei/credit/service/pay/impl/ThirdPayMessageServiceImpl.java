package com.zhiwei.credit.service.pay.impl;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.pay.ThirdPayMessageDao;
import com.zhiwei.credit.model.pay.ThirdPayMessage;
import com.zhiwei.credit.service.pay.ThirdPayMessageService;

/**
 * 
 * @author 
 *
 */
public class ThirdPayMessageServiceImpl extends BaseServiceImpl<ThirdPayMessage> implements ThirdPayMessageService{
	@SuppressWarnings("unused")
	private ThirdPayMessageDao dao;
	
	public ThirdPayMessageServiceImpl(ThirdPayMessageDao dao) {
		super(dao);
		this.dao=dao;
	}

}