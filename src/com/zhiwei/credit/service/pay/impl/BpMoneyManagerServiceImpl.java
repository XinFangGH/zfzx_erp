package com.zhiwei.credit.service.pay.impl;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.pay.BpMoneyManagerDao;
import com.zhiwei.credit.model.pay.BpMoneyManager;
import com.zhiwei.credit.service.pay.BpMoneyManagerService;

/**
 * 
 * @author 
 *
 */
public class BpMoneyManagerServiceImpl extends BaseServiceImpl<BpMoneyManager> implements BpMoneyManagerService{
	@SuppressWarnings("unused")
	private BpMoneyManagerDao dao;
	
	public BpMoneyManagerServiceImpl(BpMoneyManagerDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public BpMoneyManager getByOrderAndType(String OrderNo, String LoanNo,
			String Type) {
		return dao.getByOrderAndType(OrderNo,LoanNo,Type);
	}

}