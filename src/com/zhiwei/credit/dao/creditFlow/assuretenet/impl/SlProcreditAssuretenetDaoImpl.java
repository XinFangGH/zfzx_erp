package com.zhiwei.credit.dao.creditFlow.assuretenet.impl;
/*
 *  北京互融时代软件有限公司 hurong协同办公管理系统   -- http://www.hurongtime.com/
 *  Copyright (C) 2008-2010 BEIJING JINZHIWANWEI SOFTWARES LIMITED COMPANY
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.assuretenet.SlProcreditAssuretenetDao;
import com.zhiwei.credit.model.creditFlow.assuretenet.SlProcreditAssuretenet;

@SuppressWarnings("unchecked")
public class SlProcreditAssuretenetDaoImpl extends BaseDaoImpl<SlProcreditAssuretenet> implements SlProcreditAssuretenetDao{

	public SlProcreditAssuretenetDaoImpl() {
		super(SlProcreditAssuretenet.class);
	}
	
	//根据项目ID查询准入原则
	@Override
	public List<SlProcreditAssuretenet> getByProjId(String projId,String businessType) {
		String hql = "from SlProcreditAssuretenet sa where sa.projid=? and sa.businessTypeKey=?";
		Object[] objs={projId,businessType};
		return findByHql(hql, objs);
	}

	@Override
	public List<SlProcreditAssuretenet> checkIsExit(String projectId,String businessTypeKey, Long assuretenetId) {
		String hql = "from SlProcreditAssuretenet sa where sa.projid=? and sa.businessTypeKey=? and sa.settingId=?";
		return this.getSession().createQuery(hql).setParameter(0, projectId).setParameter(1, businessTypeKey).setParameter(2, assuretenetId).list();
	}
}