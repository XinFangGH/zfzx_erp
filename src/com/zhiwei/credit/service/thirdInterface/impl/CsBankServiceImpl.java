package com.zhiwei.credit.service.thirdInterface.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.thirdInterface.CsBankDao;
import com.zhiwei.credit.model.creditFlow.common.CsBank;
import com.zhiwei.credit.service.thirdInterface.CsBankService;



/**
 * 
 * @author 
 *
 */
public class CsBankServiceImpl extends BaseServiceImpl<CsBank> implements CsBankService{
	@SuppressWarnings("unused")
	private CsBankDao dao;
	
	public CsBankServiceImpl(CsBankDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<CsBank> getListByBankName(String bankName) {
		
		return dao.getListByBankName(bankName);
	}

}