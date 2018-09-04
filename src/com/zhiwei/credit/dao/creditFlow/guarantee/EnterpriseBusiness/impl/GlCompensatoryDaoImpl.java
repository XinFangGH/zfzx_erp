package com.zhiwei.credit.dao.creditFlow.guarantee.EnterpriseBusiness.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.guarantee.EnterpriseBusiness.GlCompensatoryDao;
import com.zhiwei.credit.model.creditFlow.guarantee.EnterpriseBusiness.GlCompensatory;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class GlCompensatoryDaoImpl extends BaseDaoImpl<GlCompensatory> implements GlCompensatoryDao{

	public GlCompensatoryDaoImpl() {
		super(GlCompensatory.class);
	}

	@Override
	public List<GlCompensatory> findGlCompensatoryList(long projectId, String businessType) {
		String hql="from GlCompensatory as gl where gl.projectId=? and gl.businessType=?";
		return getSession().createQuery(hql).setParameter(0, projectId).setParameter(1, businessType).list();
		
	}

}