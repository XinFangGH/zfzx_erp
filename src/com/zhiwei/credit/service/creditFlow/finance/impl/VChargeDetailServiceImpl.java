package com.zhiwei.credit.service.creditFlow.finance.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;
import java.util.Map;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.finance.VChargeDetailDao;
import com.zhiwei.credit.dao.creditFlow.finance.VFundDetailDao;
import com.zhiwei.credit.model.creditFlow.finance.VChargeDetail;
import com.zhiwei.credit.model.creditFlow.finance.VFundDetail;
import com.zhiwei.credit.service.creditFlow.finance.VChargeDetailService;
import com.zhiwei.credit.service.creditFlow.finance.VFundDetailService;

/**
 * 
 * @author 
 *
 */
public class VChargeDetailServiceImpl extends BaseServiceImpl<VChargeDetail> implements VChargeDetailService{
	@SuppressWarnings("unused")
	private VChargeDetailDao dao;
	
	public VChargeDetailServiceImpl(VChargeDetailDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<VChargeDetail> search(Map<String, String> map) {
		// TODO Auto-generated method stub
		return dao.search(map);
	}

	@Override
	public int searchsize(Map<String, String> map) {
		// TODO Auto-generated method stub
		return dao.searchsize(map);
	}


}