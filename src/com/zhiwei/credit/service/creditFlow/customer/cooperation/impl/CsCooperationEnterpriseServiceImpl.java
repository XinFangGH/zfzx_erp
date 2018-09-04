package com.zhiwei.credit.service.creditFlow.customer.cooperation.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;
import java.util.Map;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.customer.cooperation.CsCooperationEnterpriseDao;
import com.zhiwei.credit.model.creditFlow.customer.cooperation.CsCooperationEnterprise;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseView;
import com.zhiwei.credit.service.creditFlow.customer.cooperation.CsCooperationEnterpriseService;

/**
 * 
 * @author 
 *
 */
public class CsCooperationEnterpriseServiceImpl extends BaseServiceImpl<CsCooperationEnterprise> implements CsCooperationEnterpriseService{
	@SuppressWarnings("unused")
	private CsCooperationEnterpriseDao dao;
	
	public CsCooperationEnterpriseServiceImpl(CsCooperationEnterpriseDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<CsCooperationEnterprise> getAllAccountList(Map map,PagingBean pb) {
		// TODO Auto-generated method stub
		return dao.getAllAccountList(map,pb);
	}

	@Override
	public List<CsCooperationEnterprise> accountList(Map map, PagingBean pb) {
		// TODO Auto-generated method stub
		return dao.accountList(map,pb);
	}

}