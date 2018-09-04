package com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.financingAgency.manageMoney.PlManageMoneyPlanDao;
import com.zhiwei.credit.dao.creditFlow.financingAgency.manageMoney.PlManageMoneyTypeDao;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlManageMoneyPlan;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlManageMoneyType;
import com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.PlManageMoneyPlanService;
import com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.PlManageMoneyTypeService;

/**
 * 
 * @author 
 *
 */
public class PlManageMoneyTypeServiceImpl extends BaseServiceImpl<PlManageMoneyType> implements PlManageMoneyTypeService{
	@SuppressWarnings("unused")
	private PlManageMoneyTypeDao dao;
	
	public PlManageMoneyTypeServiceImpl(PlManageMoneyTypeDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<PlManageMoneyType> getListbykeystr(String keystr) {
		// TODO Auto-generated method stub
		return dao.getListbykeystr(keystr);
	}

	@Override
	public PlManageMoneyPlan yesOrNo(Long manageMoneyTypeId) {
		return dao.yesOrNo(manageMoneyTypeId);
	}

}