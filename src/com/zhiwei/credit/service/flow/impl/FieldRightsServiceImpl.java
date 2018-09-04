package com.zhiwei.credit.service.flow.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.flow.FieldRightsDao;
import com.zhiwei.credit.model.flow.FieldRights;
import com.zhiwei.credit.service.flow.FieldRightsService;

public class FieldRightsServiceImpl extends BaseServiceImpl<FieldRights> implements FieldRightsService{
	@SuppressWarnings("unused")
	private FieldRightsDao dao;
	
	public FieldRightsServiceImpl(FieldRightsDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<FieldRights> getByMappingFieldTaskName(Long mappingId,
			Long fieldId, String taskName) {
		return dao.getByMappingFieldTaskName(mappingId, fieldId, taskName);
	}

	@Override
	public List<FieldRights> getByMappingIdAndTaskName(Long mappingId,
			String taskName) {
		return dao.getByMappingIdAndTaskName(mappingId, taskName);
	}

	@Override
	public List<FieldRights> getByMappingId(Long mappingId) {
		return dao.getByMappingId(mappingId);
	}

}