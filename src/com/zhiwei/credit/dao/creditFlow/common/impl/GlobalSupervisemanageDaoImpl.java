package com.zhiwei.credit.dao.creditFlow.common.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.common.GlobalSupervisemanageDao;
import com.zhiwei.credit.model.creditFlow.common.GlobalSupervisemanage;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class GlobalSupervisemanageDaoImpl extends BaseDaoImpl<GlobalSupervisemanage> implements GlobalSupervisemanageDao{

	public GlobalSupervisemanageDaoImpl() {
		super(GlobalSupervisemanage.class);
	}
	
	public List<GlobalSupervisemanage> getListByProjectId(Long projectId,String businessType) {
		String hql = "from GlobalSupervisemanage s where s.projectId ="+projectId+" and businessType ='"+businessType+"'";
		return findByHql(hql);
	}

	@Override
	public List<GlobalSupervisemanage> listByDesignSuperviseManageTime(
			String designSuperviseManageTime) {
		String hql="from GlobalSupervisemanage as g where g.designSuperviseManageTime<='"+designSuperviseManageTime+"' and g.isProduceTask=0";
		return this.findByHql(hql);
	}
}