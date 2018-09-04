package com.zhiwei.credit.service.creditFlow.financingAgency.persion.impl;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.financingAgency.persion.PlPersionDirProKeepDao;
import com.zhiwei.credit.model.creditFlow.financingAgency.business.PlBusinessDirProKeep;
import com.zhiwei.credit.model.creditFlow.financingAgency.persion.PlPersionDirProKeep;
import com.zhiwei.credit.service.creditFlow.financingAgency.persion.PlPersionDirProKeepService;

/**
 * 
 * @author 
 *
 */
public class PlPersionDirProKeepServiceImpl extends BaseServiceImpl<PlPersionDirProKeep> implements PlPersionDirProKeepService{
	@SuppressWarnings("unused")
	private PlPersionDirProKeepDao dao;
	
	public PlPersionDirProKeepServiceImpl(PlPersionDirProKeepDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public PlPersionDirProKeep getByType(String type, Long id) {
		
		return dao.getByType(type, id);
	}

}