package com.zhiwei.credit.service.creditFlow.guarantee.EnterpriseBusiness.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.guarantee.EnterpriseBusiness.GlCompensatoryDao;
import com.zhiwei.credit.model.creditFlow.guarantee.EnterpriseBusiness.GlCompensatory;
import com.zhiwei.credit.service.creditFlow.guarantee.EnterpriseBusiness.GlCompensatoryService;

/**
 * 
 * @author 
 *
 */
public class GlCompensatoryServiceImpl extends BaseServiceImpl<GlCompensatory> implements GlCompensatoryService{
	@SuppressWarnings("unused")
	private GlCompensatoryDao dao;
	
	public GlCompensatoryServiceImpl(GlCompensatoryDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<GlCompensatory> findGlCompensatoryList(long projectId,
			String businessType) {
		
		return dao.findGlCompensatoryList(projectId, businessType);
	}

}