package com.zhiwei.credit.service.creditFlow.customer.enterprise.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.customer.enterprise.BpCustEntDownstreamCustomDao;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.BpCustEntDownstreamCustom;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.BpCustEntDownstreamCustomService;

/**
 * 
 * @author 
 *
 */
public class BpCustEntDownstreamCustomServiceImpl extends BaseServiceImpl<BpCustEntDownstreamCustom> implements BpCustEntDownstreamCustomService{
	@SuppressWarnings("unused")
	private BpCustEntDownstreamCustomDao dao;
	
	public BpCustEntDownstreamCustomServiceImpl(BpCustEntDownstreamCustomDao dao) {
		super(dao);
		this.dao=dao;
	}

}