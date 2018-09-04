package com.zhiwei.credit.service.p2p.loan.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.p2p.loan.P2pLoanRateDao;
import com.zhiwei.credit.model.p2p.loan.P2pLoanRate;
import com.zhiwei.credit.service.p2p.loan.P2pLoanRateService;

/**
 * 
 * @author 
 *
 */
public class P2pLoanRateServiceImpl extends BaseServiceImpl<P2pLoanRate> implements P2pLoanRateService{
	@SuppressWarnings("unused")
	private P2pLoanRateDao dao;
	
	public P2pLoanRateServiceImpl(P2pLoanRateDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<P2pLoanRate> p2pLoanRateList(PagingBean pb, Long productId) {
		// TODO Auto-generated method stub
		return dao.p2pLoanRateList(pb, productId);
	}

	@Override
	public Long p2pLoanRateListCount(Long productId) {
		// TODO Auto-generated method stub
		return dao.p2pLoanRateListCount(productId);
	}

}