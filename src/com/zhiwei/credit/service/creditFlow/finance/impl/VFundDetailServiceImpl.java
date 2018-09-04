package com.zhiwei.credit.service.creditFlow.finance.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;
import java.util.Map;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.finance.VFundDetailDao;
import com.zhiwei.credit.model.creditFlow.finance.VFundDetail;
import com.zhiwei.credit.service.creditFlow.finance.VFundDetailService;

/**
 * 
 * @author 
 *
 */
public class VFundDetailServiceImpl extends BaseServiceImpl<VFundDetail> implements VFundDetailService{
	@SuppressWarnings("unused")
	private VFundDetailDao dao;
	
	public VFundDetailServiceImpl(VFundDetailDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<VFundDetail> search(Map<String, String> map) {
		// TODO Auto-generated method stub
		return dao.search(map);
	}

	@Override
	public int searchsize(Map<String, String> map) {
		// TODO Auto-generated method stub
		return dao.searchsize(map);
	}

	@Override
	public List<VFundDetail> wslistbyCharge(String businessType,
			Long projectId, String factDate) {
		// TODO Auto-generated method stub
		return dao.wslistbyCharge(businessType, projectId, factDate);
	}

	@Override
	public List<VFundDetail> wslistbyPrincipalRepay(String businessType,
			Long projectId, String factDate) {
		// TODO Auto-generated method stub
		return dao.wslistbyPrincipalRepay(businessType, projectId, factDate);
	}

	@Override
	public List<VFundDetail> wslistbyinterest(String businessType,
			Long projectId, String factDate) {
		// TODO Auto-generated method stub
		return dao.wslistbyinterest(businessType, projectId, factDate);
	}

	@Override
	public List getListByFundType(String startDate, String endDate,
			Long companyId, String fundType) {
		
		return dao.getListByFundType(startDate, endDate, companyId, fundType);
	}

	@Override
	public List getshCount(String startDate, String endDate, Long companyId,
			String fundType) {
		
		return dao.getshCount(startDate, endDate, companyId, fundType);
	}

	@Override
	public List getwjqList(String startDate, String endDate, Long companyId,
			String fundType) {
		
		return dao.getwjqList(startDate, endDate, companyId, fundType);
	}

	@Override
	public List getyqList(String endDate, Long companyId, String fundType) {
		
		return dao.getyqList(endDate, companyId, fundType);
	}

	@Override
	public List<VFundDetail> listByFundType(String fundType, Long projectId,
			String businessType) {
		
		return dao.listByFundType(fundType, projectId, businessType);
	}

	@Override
	public List getListByFundType(String fundType,
			String companyId, String startDate, String intentDate) {
	
		return dao.getListByFundType(fundType, companyId, startDate, intentDate);
	}


}