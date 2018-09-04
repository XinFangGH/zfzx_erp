package com.zhiwei.credit.service.creditFlow.customer.person.impl;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.customer.person.BpMoneyBorrowDemandDao;
import com.zhiwei.credit.model.creditFlow.customer.person.BpMoneyBorrowDemand;
import com.zhiwei.credit.service.creditFlow.customer.person.BpMoneyBorrowDemandService;

/**
 * 
 * @author 
 *
 */
public class BpMoneyBorrowDemandServiceImpl extends BaseServiceImpl<BpMoneyBorrowDemand> implements BpMoneyBorrowDemandService{
	private BpMoneyBorrowDemandDao dao;
	
	public BpMoneyBorrowDemandServiceImpl(BpMoneyBorrowDemandDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<BpMoneyBorrowDemand> getMessageByProjectID(Long projectID) {
		return dao.getMessageByProjectID(projectID);
	}

	@Override
	public BpMoneyBorrowDemand getByBorrowId(Integer borrowId) {
		return dao.load(borrowId);
	}

}