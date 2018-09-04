package com.zhiwei.credit.service.creditFlow.customer.cooperation.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;
import java.util.Map;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.customer.cooperation.CsCooperationPersonDao;
import com.zhiwei.credit.model.creditFlow.customer.cooperation.CsCooperationPerson;
import com.zhiwei.credit.service.creditFlow.customer.cooperation.CsCooperationPersonService;

/**
 * 
 * @author 
 *
 */
public class CsCooperationPersonServiceImpl extends BaseServiceImpl<CsCooperationPerson> implements CsCooperationPersonService{
	@SuppressWarnings("unused")
	private CsCooperationPersonDao dao;
	
	public CsCooperationPersonServiceImpl(CsCooperationPersonDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public CsCooperationPerson queryByCardnumber(String cardNumber) {
		// TODO Auto-generated method stub
		return dao.queryByCardnumber(cardNumber);
	}

	@Override
	public List<CsCooperationPerson> getAllAccountList(Map map, PagingBean pb) {
		// TODO Auto-generated method stub
		return dao.getAllAccountList(map,pb);
	}

	@Override
	public List<CsCooperationPerson> accountList(Map map, PagingBean pb) {
		// TODO Auto-generated method stub
		return dao.accountList(map,pb);
	}
	
	

}