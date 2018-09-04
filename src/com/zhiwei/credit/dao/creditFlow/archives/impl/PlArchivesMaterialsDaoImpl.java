package com.zhiwei.credit.dao.creditFlow.archives.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.archives.PlArchivesMaterialsDao;
import com.zhiwei.credit.model.creditFlow.archives.PlArchivesMaterials;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class PlArchivesMaterialsDaoImpl extends BaseDaoImpl<PlArchivesMaterials> implements PlArchivesMaterialsDao{

	public PlArchivesMaterialsDaoImpl() {
		super(PlArchivesMaterials.class);
	}

	@Override
	public List<PlArchivesMaterials> listbyProjectId(Long projectId,
			String businessType) {
		String hql="from PlArchivesMaterials p where p.projId="+projectId+" and p.businessType='"+businessType+"'";
		 return findByHql(hql);
	}

	@Override
	public List<PlArchivesMaterials> checkIsExit(String projectId, String businessType,
			Long materialsId) {
		String hql="from PlArchivesMaterials p where p.projId=? and p.businessType=? and p.materialsId=?";
		return this.getSession().createQuery(hql).setParameter(0, Long.valueOf(projectId)).setParameter(1, businessType).setParameter(2, materialsId).list();
	}

}