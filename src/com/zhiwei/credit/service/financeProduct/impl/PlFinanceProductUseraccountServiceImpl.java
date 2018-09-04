package com.zhiwei.credit.service.financeProduct.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.financeProduct.PlFinanceProductUseraccountDao;
import com.zhiwei.credit.model.financeProduct.PlFinanceProductUseraccount;
import com.zhiwei.credit.service.financeProduct.PlFinanceProductUseraccountService;

/**
 * 
 * @author 
 *
 */
public class PlFinanceProductUseraccountServiceImpl extends BaseServiceImpl<PlFinanceProductUseraccount> implements PlFinanceProductUseraccountService{
	@SuppressWarnings("unused")
	private PlFinanceProductUseraccountDao dao;
	
	public PlFinanceProductUseraccountServiceImpl(PlFinanceProductUseraccountDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<PlFinanceProductUseraccount> getUserAccountList(
			Map<String,String> request, PagingBean pb) {
		// TODO Auto-generated method stub
		return dao.getUserAccountList(request,pb);
	}

}