package com.zhiwei.credit.service.creditFlow.riskControl.creditInvestigation.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.riskControl.creditInvestigation.BpLoneExternalDao;
import com.zhiwei.credit.model.creditFlow.riskControl.creditInvestigation.BpLoneExternal;
import com.zhiwei.credit.service.creditFlow.riskControl.creditInvestigation.BpLoneExternalService;

/**
 * 
 * @author 
 *
 */
public class BpLoneExternalServiceImpl extends BaseServiceImpl<BpLoneExternal> implements BpLoneExternalService{
	@SuppressWarnings("unused")
	private BpLoneExternalDao dao;
	
	public BpLoneExternalServiceImpl(BpLoneExternalDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public Long bpLoneExternalCount(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return dao.bpLoneExternalCount(request);
	}

	@Override
	public List<BpLoneExternal> bpLoneExternalList(PagingBean pb,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		return dao.bpLoneExternalList(pb, request);
	}

}