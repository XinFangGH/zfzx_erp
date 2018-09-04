package com.zhiwei.credit.service.creditFlow.finance.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.finance.SlAccruedDao;
import com.zhiwei.credit.model.creditFlow.finance.SlAccrued;
import com.zhiwei.credit.service.creditFlow.finance.SlAccruedService;

/**
 * 
 * @author 
 *
 */
public class SlAccruedServiceImpl extends BaseServiceImpl<SlAccrued> implements SlAccruedService{
	@SuppressWarnings("unused")
	private SlAccruedDao dao;
	
	public SlAccruedServiceImpl(SlAccruedDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<SlAccrued> wslist(Long projectId, String businessType) {
		// TODO Auto-generated method stub
		return dao.wslist(projectId, businessType);
	}

}