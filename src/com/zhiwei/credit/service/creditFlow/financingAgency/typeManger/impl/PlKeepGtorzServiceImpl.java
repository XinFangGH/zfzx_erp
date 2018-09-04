package com.zhiwei.credit.service.creditFlow.financingAgency.typeManger.impl;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.financingAgency.typeManger.PlKeepGtorzDao;
import com.zhiwei.credit.model.creditFlow.financingAgency.typeManger.PlKeepGtorz;
import com.zhiwei.credit.service.creditFlow.financingAgency.typeManger.PlKeepGtorzService;

/**
 * 
 * @author 
 *
 */
public class PlKeepGtorzServiceImpl extends BaseServiceImpl<PlKeepGtorz> implements PlKeepGtorzService{
	@SuppressWarnings("unused")
	private PlKeepGtorzDao dao;
	
	public PlKeepGtorzServiceImpl(PlKeepGtorzDao dao) {
		super(dao);
		this.dao=dao;
	}

}