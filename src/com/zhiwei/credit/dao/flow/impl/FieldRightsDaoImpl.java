package com.zhiwei.credit.dao.flow.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.flow.FieldRightsDao;
import com.zhiwei.credit.model.flow.FieldRights;

@SuppressWarnings("unchecked")
public class FieldRightsDaoImpl extends BaseDaoImpl<FieldRights> implements FieldRightsDao{

	public FieldRightsDaoImpl() {
		super(FieldRights.class);
	}

	@Override
	public List<FieldRights> getByMappingFieldTaskName(Long mappingId,
			Long fieldId, String taskName) {
		String hql="from FieldRights vo where vo.formField.fieldId=? and vo.mappingId=? and vo.taskName=?";
		return findByHql(hql, new Object[]{fieldId,mappingId,taskName});
	}

	@Override
	public List<FieldRights> getByMappingIdAndTaskName(Long mappingId,
			String taskName) {
		String hql="from FieldRights vo where vo.mappingId=? and vo.taskName=?";
		return findByHql(hql, new Object[]{mappingId,taskName});
	}

	@Override
	public List<FieldRights> getByMappingId(Long mappingId) {
		String hql="from FieldRights vo where vo.mappingId=?";
		return findByHql(hql, new Object[]{mappingId});
	}

}