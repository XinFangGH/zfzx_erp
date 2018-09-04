package com.zhiwei.credit.service.creditFlow.riskControl.creditInvestigation.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.riskControl.creditInvestigation.BpBadCreditDao;
import com.zhiwei.credit.model.creditFlow.riskControl.creditInvestigation.BpBadCredit;
import com.zhiwei.credit.service.creditFlow.riskControl.creditInvestigation.BpBadCreditService;

/**
 * 
 * @author 
 *
 */
public class BpBadCreditServiceImpl extends BaseServiceImpl<BpBadCredit> implements BpBadCreditService{
	@SuppressWarnings("unused")
	private BpBadCreditDao dao;
	
	public BpBadCreditServiceImpl(BpBadCreditDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public Long bpBadCreditCount(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return dao.bpBadCreditCount(request);
	}

	@Override
	public List<BpBadCredit> bpBadCreditList(PagingBean pb,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		return dao.bpBadCreditList(pb, request);
	
	}

	@Override
	public BpBadCredit getByMoneyIdType(Long moneyId, String moneyType) {
		// TODO Auto-generated method stub
		return dao.getByMoneyIdType(moneyId, moneyType);
	}

}