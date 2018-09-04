package com.zhiwei.credit.service.creditFlow.finance.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.finance.SlPlansToChargeDao;
import com.zhiwei.credit.model.creditFlow.finance.SlPlansToCharge;
import com.zhiwei.credit.service.creditFlow.finance.SlPlansToChargeService;

/**
 * 
 * @author 
 *
 */
public class SlPlansToChargeServiceImpl extends BaseServiceImpl<SlPlansToCharge> implements SlPlansToChargeService{
	@SuppressWarnings("unused")
	private SlPlansToChargeDao dao;
	
	public SlPlansToChargeServiceImpl(SlPlansToChargeDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<SlPlansToCharge> getall() {
		// TODO Auto-generated method stub
		return dao.getall();
	}

	@Override
	public List<SlPlansToCharge> getbytype(int type) {
		// TODO Auto-generated method stub
		return dao.getbytype(type);
	}

	@Override
	public int setIsValid(int isValid, SlPlansToCharge f) {
		// TODO Auto-generated method stub
		return dao.setIsValid(isValid, f);
	}

	@Override
	public List<SlPlansToCharge> getbyOperationType(int type, String businessType) {
		// TODO Auto-generated method stub
		return dao.getbyOperationType(type,businessType);
	}

	@Override
	public List<SlPlansToCharge> getallbycompanyId() {
		// TODO Auto-generated method stub
		return dao.getallbycompanyId();
	}

	@Override
	public List<SlPlansToCharge> getListByIdsNotNull(String businessType,String isValid) {
		return dao.getListByIdsNotNull(businessType,isValid);
	}

	@Override
	public List<SlPlansToCharge> checkIsExit(String productId,
			Long plansTochargeId, String businessType) {
		// TODO Auto-generated method stub
		return dao.checkIsExit(productId,plansTochargeId,businessType);
	}

	@Override
	public List<SlPlansToCharge> getByPProductIdAndOperationType(
			String productId, String businessType) {
		// TODO Auto-generated method stub
		return dao.getByPProductIdAndOperationType(productId,businessType);
	}

	@Override
	public List<SlPlansToCharge> getbyProductId(String productId,
			String businessType) {
		return dao.getbyProductId(productId,businessType);
	}

}