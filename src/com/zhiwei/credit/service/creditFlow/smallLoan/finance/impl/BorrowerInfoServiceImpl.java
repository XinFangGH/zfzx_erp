package com.zhiwei.credit.service.creditFlow.smallLoan.finance.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.smallLoan.finance.BorrowerInfoDao;
import com.zhiwei.credit.model.creditFlow.smallLoan.finance.BorrowerInfo;
import com.zhiwei.credit.service.creditFlow.smallLoan.finance.BorrowerInfoService;

/**
 * 
 * @author 
 *
 */
public class BorrowerInfoServiceImpl extends BaseServiceImpl<BorrowerInfo> implements BorrowerInfoService{
	@SuppressWarnings("unused")
	private BorrowerInfoDao dao;
	
	public BorrowerInfoServiceImpl(BorrowerInfoDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<BorrowerInfo> getBorrowerList(Long projectId) {
		
		return dao.getBorrowerList(projectId);
	}

}