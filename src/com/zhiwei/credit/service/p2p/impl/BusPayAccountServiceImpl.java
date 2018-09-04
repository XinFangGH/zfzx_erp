package com.zhiwei.credit.service.p2p.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.p2p.BusPayAccountDao;
import com.zhiwei.credit.model.p2p.BusPayAccount;
import com.zhiwei.credit.service.p2p.BusPayAccountService;

/**
 * 
 * @author 
 *
 */
public class BusPayAccountServiceImpl extends BaseServiceImpl<BusPayAccount> implements BusPayAccountService{
	@SuppressWarnings("unused")
	private BusPayAccountDao dao;
	
	public BusPayAccountServiceImpl(BusPayAccountDao dao) {
		super(dao);
		this.dao=dao;
	}

}