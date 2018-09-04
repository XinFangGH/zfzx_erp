package com.zhiwei.credit.service.customer.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.customer.InvestPersonCareDao;
import com.zhiwei.credit.model.customer.InvestPersonCare;
import com.zhiwei.credit.service.customer.InvestPersonCareService;

/**
 * 
 * @author 
 *
 */
public class InvestPersonCareServiceImpl extends BaseServiceImpl<InvestPersonCare> implements InvestPersonCareService{
	@SuppressWarnings("unused")
	private InvestPersonCareDao dao;
	
	
	public InvestPersonCareServiceImpl(InvestPersonCareDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<InvestPersonCare> getByperId(Long perId) {
		// TODO Auto-generated method stub
		return dao.getByperId(perId);
	}

	@Override
	public List<InvestPersonCare> getList(Long perId, Integer isEnterprise) {
		return dao.getList(perId, isEnterprise);
	}

}