package com.zhiwei.credit.service.creditFlow.financingAgency.typeManger.impl;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.financingAgency.typeManger.PlBiddingTypeDao;
import com.zhiwei.credit.model.creditFlow.financingAgency.typeManger.PlBiddingType;
import com.zhiwei.credit.service.creditFlow.financingAgency.typeManger.PlBiddingTypeService;

/**
 * 
 * @author 
 *
 */
public class PlBiddingTypeServiceImpl extends BaseServiceImpl<PlBiddingType> implements PlBiddingTypeService{
	@SuppressWarnings("unused")
	private PlBiddingTypeDao dao;
	
	public PlBiddingTypeServiceImpl(PlBiddingTypeDao dao) {
		super(dao);
		this.dao=dao;
	}

}