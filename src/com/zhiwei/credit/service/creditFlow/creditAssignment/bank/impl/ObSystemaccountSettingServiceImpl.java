package com.zhiwei.credit.service.creditFlow.creditAssignment.bank.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.creditAssignment.bank.ObSystemaccountSettingDao;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObSystemaccountSetting;
import com.zhiwei.credit.service.creditFlow.creditAssignment.bank.ObSystemaccountSettingService;

/**
 * 
 * @author 
 *
 */
public class ObSystemaccountSettingServiceImpl extends BaseServiceImpl<ObSystemaccountSetting> implements ObSystemaccountSettingService{
	@SuppressWarnings("unused")
	private ObSystemaccountSettingDao dao;
	
	public ObSystemaccountSettingServiceImpl(ObSystemaccountSettingDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<ObSystemaccountSetting> findObSystemaccountSetting() {
		// TODO Auto-generated method stub
		return dao.findObSystemaccountSetting();
	}

	@Override
	public List<ObSystemaccountSetting> findThirdObSystemaccountSetting() {
		return dao.findThirdObSystemaccountSetting();
	}

}