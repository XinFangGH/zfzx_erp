package com.zhiwei.credit.dao.creditFlow.guarantee.EnterpriseBusiness.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.guarantee.EnterpriseBusiness.GlRecoveryDao;
import com.zhiwei.credit.model.creditFlow.guarantee.EnterpriseBusiness.GlRecovery;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class GlRecoveryDaoImpl extends BaseDaoImpl<GlRecovery> implements GlRecoveryDao{

	public GlRecoveryDaoImpl() {
		super(GlRecovery.class);
	}

	@Override
	public List<GlRecovery> findGlRecoveryList(long projectId,
			String businessType) {
		String hql="from GlRecovery as gl where gl.projectId=? and gl.businessType=?";
		return getSession().createQuery(hql).setParameter(0, projectId).setParameter(1, businessType).list();
	}

}