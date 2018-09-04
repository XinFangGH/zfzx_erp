package com.zhiwei.credit.service.p2p.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.p2p.BpFinanceApplyUserDao;
import com.zhiwei.credit.model.p2p.BpFinanceApplyUser;
import com.zhiwei.credit.service.p2p.BpFinanceApplyUserService;

/**
 * 
 * @author 
 *
 */
public class BpFinanceApplyUserServiceImpl extends BaseServiceImpl<BpFinanceApplyUser> implements BpFinanceApplyUserService{
	private BpFinanceApplyUserDao dao;
	
	public BpFinanceApplyUserServiceImpl(BpFinanceApplyUserDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<BpFinanceApplyUser> personList(Integer start, Integer limit, Integer state,
			HttpServletRequest request) {
		return dao.personList(start, limit,state, request);
		
	}

	@Override
	public BpFinanceApplyUser getDetailed(String loanId) {
		return dao.getDetailed(loanId);
	}

}