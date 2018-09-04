package com.zhiwei.credit.service.creditFlow.finance.impl;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.finance.FundsToPromoteDao;
import com.zhiwei.credit.model.creditFlow.finance.FundsToPromote;
import com.zhiwei.credit.service.creditFlow.finance.FundsToPromoteService;

/**
 * 
 * @author 
 *
 */
public class FundsToPromoteServiceImpl extends BaseServiceImpl<FundsToPromote> implements FundsToPromoteService{
	private FundsToPromoteDao dao;
	
	public FundsToPromoteServiceImpl(FundsToPromoteDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<FundsToPromote> getListByProjectId(Long projectId) {
		
		return dao.getListByProjectId(projectId);
	}

}