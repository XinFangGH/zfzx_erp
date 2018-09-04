package com.zhiwei.credit.service.creditFlow.finance.impl;
/*
 *  北京互融时代软件有限公司 hurong协同办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2010 BEIJING JINZHIWANWEI SOFTWARES LIMITED COMPANY
*/
import java.util.List;
import java.util.Map;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.finance.SlBankAccountDao;
import com.zhiwei.credit.model.creditFlow.finance.SlBankAccount;
import com.zhiwei.credit.service.creditFlow.finance.SlBankAccountService;


public class SlBankAccountServiceImpl extends BaseServiceImpl<SlBankAccount> implements SlBankAccountService{
	@SuppressWarnings("unused")
	private SlBankAccountDao dao;
	
	public SlBankAccountServiceImpl(SlBankAccountDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public  List<SlBankAccount> getbyaccount(String accont) {
		// TODO Auto-generated method stub
		return dao.getbyaccount(accont);
	}

	@Override
	public List<SlBankAccount> getall(Map<String, String> map) {
		// TODO Auto-generated method stub
		return dao.getall(map);
	}

	@Override
	public int getallsize(Map<String, String> map) {
		// TODO Auto-generated method stub
		return dao.getallsize(map);
	}

	@Override
	public List<SlBankAccount> getallbycompanyId(PagingBean pb) {
		// TODO Auto-generated method stub
		return dao.getallbycompanyId(pb);
	}

	@Override
	public List<SlBankAccount> getallbycompanyId(PagingBean pb,
			Map<String, String> map) {
		// TODO Auto-generated method stub
		return dao.getallbycompanyId(pb, map);
	}

	@Override
	public List<SlBankAccount> selectbycompanyId(PagingBean pb,
			Map<String, String> map) {
		// TODO Auto-generated method stub
		return dao.selectbycompanyId(pb, map);
	}

	@Override
	public List<SlBankAccount> webgetbyaccount(String accont, Long companyId) {
		// TODO Auto-generated method stub
		return dao.webgetbyaccount(accont, companyId);
	}

}