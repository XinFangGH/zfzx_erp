package com.thirdPayInteface.ThirdPayLog.service.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import com.thirdPayInteface.ThirdPayLog.dao.ThirdPayRecordDao;
import com.thirdPayInteface.ThirdPayLog.model.ThirdPayRecord;
import com.thirdPayInteface.ThirdPayLog.service.ThirdPayRecordService;
import com.zhiwei.core.service.impl.BaseServiceImpl;

/**
 * 
 * @author 
 *
 */
public class ThirdPayRecordServiceImpl extends BaseServiceImpl<ThirdPayRecord> implements ThirdPayRecordService{
	@SuppressWarnings("unused")
	private ThirdPayRecordDao dao;
	
	public ThirdPayRecordServiceImpl(ThirdPayRecordDao dao) {
		super(dao);
		this.dao=dao;
	}

}