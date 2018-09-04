package com.zhiwei.credit.service.creditFlow.archives.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.archives.PlArchivesMaterialsDao;
import com.zhiwei.credit.model.creditFlow.archives.PlArchivesMaterials;
import com.zhiwei.credit.service.creditFlow.archives.PlArchivesMaterialsService;

/**
 * 
 * @author 
 *
 */
public class PlArchivesMaterialsServiceImpl extends BaseServiceImpl<PlArchivesMaterials> implements PlArchivesMaterialsService{
	@SuppressWarnings("unused")
	private PlArchivesMaterialsDao dao;
	
	public PlArchivesMaterialsServiceImpl(PlArchivesMaterialsDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<PlArchivesMaterials> listbyProjectId(Long projectId,
			String businessType) {
		// TODO Auto-generated method stub
		return dao.listbyProjectId(projectId, businessType);
	}

	@Override
	public List<PlArchivesMaterials> listByBidPlanId(Long bidPlanId) {
		String hql = "from PlArchivesMaterials  pm where pm.bidPlanId = ?";
		return dao.findByHql(hql, new Object[]{bidPlanId});
	}

}