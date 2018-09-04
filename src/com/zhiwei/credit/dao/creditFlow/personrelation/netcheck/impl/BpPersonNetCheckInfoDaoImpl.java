package com.zhiwei.credit.dao.creditFlow.personrelation.netcheck.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.personrelation.netcheck.BpPersonNetCheckInfoDao;
import com.zhiwei.credit.model.creditFlow.personrelation.netcheck.BpPersonNetCheckInfo;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class BpPersonNetCheckInfoDaoImpl extends BaseDaoImpl<BpPersonNetCheckInfo> implements BpPersonNetCheckInfoDao{

	public BpPersonNetCheckInfoDaoImpl() {
		super(BpPersonNetCheckInfo.class);
	}
	@Override
	public List<BpPersonNetCheckInfo> listByProjectId(Long projectId) {
		String hql="from BpPersonNetCheckInfo as n where n.projectId=?";
		return this.findByHql(hql, new Object[]{projectId});
	}
}