package com.zhiwei.credit.service.creditFlow.personrelation.phonecheck.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.personrelation.phonecheck.BpPersonPhonecheckInfoDao;
import com.zhiwei.credit.model.creditFlow.personrelation.phonecheck.BpPersonPhonecheckInfo;
import com.zhiwei.credit.service.creditFlow.personrelation.phonecheck.BpPersonPhonecheckInfoService;

/**
 * 
 * @author 
 *
 */
public class BpPersonPhonecheckInfoServiceImpl extends BaseServiceImpl<BpPersonPhonecheckInfo> implements BpPersonPhonecheckInfoService{
	private BpPersonPhonecheckInfoDao dao;
	
	public BpPersonPhonecheckInfoServiceImpl(BpPersonPhonecheckInfoDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public BpPersonPhonecheckInfo getPhoneCheck(String projectId,int id) {
		return dao.getPhoneCheck(projectId,id);
	}

}