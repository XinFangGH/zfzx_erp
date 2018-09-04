package com.zhiwei.credit.service.creditFlow.finance.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.finance.SlChargeDetailDao;
import com.zhiwei.credit.model.creditFlow.finance.SlChargeDetail;
import com.zhiwei.credit.service.creditFlow.finance.SlChargeDetailService;

/**
 * 
 * @author 
 *
 */
public class SlChargeDetailServiceImpl extends BaseServiceImpl<SlChargeDetail> implements SlChargeDetailService{
	@SuppressWarnings("unused")
	private SlChargeDetailDao dao;
	
	public SlChargeDetailServiceImpl(SlChargeDetailDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<SlChargeDetail> getlistbyActualChargeId(Long actualChargeId) {
		// TODO Auto-generated method stub
		return dao.getlistbyActualChargeId(actualChargeId);
	}

	@Override
	public List<SlChargeDetail> getallbycompanyId() {
		// TODO Auto-generated method stub
		return dao.getallbycompanyId();
	}



}