package com.zhiwei.credit.service.creditFlow.finance.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.finance.SlLoanAccountErrorLogDao;
import com.zhiwei.credit.model.creditFlow.finance.SlLoanAccountErrorLog;
import com.zhiwei.credit.service.creditFlow.finance.SlLoanAccountErrorLogService;

/**
 * 
 * @author 
 *
 */
public class SlLoanAccountErrorLogServiceImpl extends BaseServiceImpl<SlLoanAccountErrorLog> implements SlLoanAccountErrorLogService{
	@SuppressWarnings("unused")
	private SlLoanAccountErrorLogDao dao;
	
	public SlLoanAccountErrorLogServiceImpl(SlLoanAccountErrorLogDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<SlLoanAccountErrorLog> getList(String customerName,
			String projectNum,String companyId, PagingBean pb) {
		
		return dao.getList(customerName, projectNum,companyId, pb);
	}

}