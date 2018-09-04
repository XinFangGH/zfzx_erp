package com.zhiwei.credit.service.p2p.loan.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.p2p.loan.P2pLoanApplyStepDao;
import com.zhiwei.credit.model.p2p.loan.P2pLoanApplyStep;
import com.zhiwei.credit.service.p2p.loan.P2pLoanApplyStepService;

/**
 * 
 * @author 
 *
 */
public class P2pLoanApplyStepServiceImpl extends BaseServiceImpl<P2pLoanApplyStep> implements P2pLoanApplyStepService{
	@SuppressWarnings("unused")
	private P2pLoanApplyStepDao dao;
	
	public P2pLoanApplyStepServiceImpl(P2pLoanApplyStepDao dao) {
		super(dao);
		this.dao=dao;
	}

}