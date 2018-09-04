package com.zhiwei.credit.service.creditFlow.guarantee.EnterpriseBusiness.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.guarantee.EnterpriseBusiness.GlRecoveryDao;
import com.zhiwei.credit.model.creditFlow.guarantee.EnterpriseBusiness.GlRecovery;
import com.zhiwei.credit.service.creditFlow.guarantee.EnterpriseBusiness.GlRecoveryService;

/**
 * 
 * @author 
 *
 */
public class GlRecoveryServiceImpl extends BaseServiceImpl<GlRecovery> implements GlRecoveryService{
	@SuppressWarnings("unused")
	private GlRecoveryDao dao;
	
	public GlRecoveryServiceImpl(GlRecoveryDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<GlRecovery> findGlRecoveryList(long projectId,
			String businessType) {
		
		return dao.findGlRecoveryList(projectId, businessType);
	}

}