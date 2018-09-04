package com.zhiwei.credit.service.customer.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.customer.InvestEnterpriseDao;
import com.zhiwei.credit.model.customer.InvestEnterprise;
import com.zhiwei.credit.service.customer.InvestEnterpriseService;

/**
 * 
 * @author 
 *
 */
public class InvestEnterpriseServiceImpl extends BaseServiceImpl<InvestEnterprise> implements InvestEnterpriseService{
	@SuppressWarnings("unused")
	private InvestEnterpriseDao dao;
	
	public InvestEnterpriseServiceImpl(InvestEnterpriseDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<InvestEnterprise> getList(HttpServletRequest request,
			PagingBean pb,String userIdStr) {
		return dao.getList(request, pb,userIdStr);
	}

	@Override
	public List<InvestEnterprise> getExcelList(HttpServletRequest request,
			PagingBean pb) {
		
		return dao.getExcelList(request, pb);
	}

	@Override
	public InvestEnterprise getByOrganizecode(String organizecode) {
		return dao.getByOrganizecode(organizecode);
	}

	@Override
	public List<InvestEnterprise> getList(String businessType, String userIdStr) {
		// TODO Auto-generated method stub
		return dao.getList(businessType, userIdStr);
	}

}