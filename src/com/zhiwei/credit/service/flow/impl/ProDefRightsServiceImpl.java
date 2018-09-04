package com.zhiwei.credit.service.flow.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.flow.ProDefRightsDao;
import com.zhiwei.credit.model.flow.ProDefRights;
import com.zhiwei.credit.service.flow.ProDefRightsService;

public class ProDefRightsServiceImpl extends BaseServiceImpl<ProDefRights> implements ProDefRightsService{
	@SuppressWarnings("unused")
	private ProDefRightsDao dao;
	
	public ProDefRightsServiceImpl(ProDefRightsDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public ProDefRights findByDefId(Long defId) {
		return dao.findByDefId(defId);
	}

	@Override
	public ProDefRights findByTypeId(Long proTypeId) {
		return dao.findByTypeId(proTypeId);
	}

}