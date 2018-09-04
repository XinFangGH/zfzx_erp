package com.zhiwei.credit.service.creditFlow.financingAgency.business.impl;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.financingAgency.business.PlBusinessDirProKeepDao;
import com.zhiwei.credit.model.creditFlow.financingAgency.business.PlBusinessDirProKeep;
import com.zhiwei.credit.service.creditFlow.financingAgency.business.PlBusinessDirProKeepService;

/**
 * 
 * @author 
 *
 */
public class PlBusinessDirProKeepServiceImpl extends BaseServiceImpl<PlBusinessDirProKeep> implements PlBusinessDirProKeepService{
	@SuppressWarnings("unused")
	private PlBusinessDirProKeepDao dao;
	
	public PlBusinessDirProKeepServiceImpl(PlBusinessDirProKeepDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public PlBusinessDirProKeep getByType(String type, Long id) {
		
		return dao.getByType(type, id);
	}

}