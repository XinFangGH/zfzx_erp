package com.zhiwei.credit.service.creditFlow.financingAgency.typeManger.impl;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.financingAgency.typeManger.PlKeepCreditlevelDao;
import com.zhiwei.credit.model.creditFlow.financingAgency.typeManger.PlKeepCreditlevel;
import com.zhiwei.credit.service.creditFlow.financingAgency.typeManger.PlKeepCreditlevelService;

/**
 * 
 * @author 
 *
 */
public class PlKeepCreditlevelServiceImpl extends BaseServiceImpl<PlKeepCreditlevel> implements PlKeepCreditlevelService{
	@SuppressWarnings("unused")
	private PlKeepCreditlevelDao dao;
	
	public PlKeepCreditlevelServiceImpl(PlKeepCreditlevelDao dao) {
		super(dao);
		this.dao=dao;
	}

}