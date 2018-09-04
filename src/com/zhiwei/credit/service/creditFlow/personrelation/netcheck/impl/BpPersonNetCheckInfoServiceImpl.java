package com.zhiwei.credit.service.creditFlow.personrelation.netcheck.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.personrelation.netcheck.BpPersonNetCheckInfoDao;
import com.zhiwei.credit.model.creditFlow.personrelation.netcheck.BpPersonNetCheckInfo;
import com.zhiwei.credit.service.creditFlow.personrelation.netcheck.BpPersonNetCheckInfoService;

/**
 * 
 * @author 
 *
 */
public class BpPersonNetCheckInfoServiceImpl extends BaseServiceImpl<BpPersonNetCheckInfo> implements BpPersonNetCheckInfoService{
	@SuppressWarnings("unused")
	private BpPersonNetCheckInfoDao dao;
	
	public BpPersonNetCheckInfoServiceImpl(BpPersonNetCheckInfoDao dao) {
		super(dao);
		this.dao=dao;
	}

}