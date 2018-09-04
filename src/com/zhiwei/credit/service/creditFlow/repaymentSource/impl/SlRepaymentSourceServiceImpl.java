package com.zhiwei.credit.service.creditFlow.repaymentSource.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.repaymentSource.SlRepaymentSourceDao;
import com.zhiwei.credit.model.creditFlow.repaymentSource.SlRepaymentSource;
import com.zhiwei.credit.service.creditFlow.repaymentSource.SlRepaymentSourceService;


/**
 * 
 * @author 
 *
 */
public class SlRepaymentSourceServiceImpl extends BaseServiceImpl<SlRepaymentSource> implements SlRepaymentSourceService{
	@SuppressWarnings("unused")
	private SlRepaymentSourceDao dao;
	
	public SlRepaymentSourceServiceImpl(SlRepaymentSourceDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<SlRepaymentSource> findListByProjId(long projId) {
		
		return dao.findListByProjId(projId);
	}

}