package com.zhiwei.credit.dao.creditFlow.guarantee.EnterpriseBusiness.impl;

/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.guarantee.EnterpriseBusiness.GlSuperviseRecordDao;
import com.zhiwei.credit.model.creditFlow.guarantee.EnterpriseBusiness.GLSuperviseRecord;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class GlSuperviseRecordDaoImpl extends BaseDaoImpl<GLSuperviseRecord> implements GlSuperviseRecordDao{

	public GlSuperviseRecordDaoImpl() {
		super(GLSuperviseRecord.class);
	}

	@Override
	public List<GLSuperviseRecord> getListByProjectId(Long projectId) {
	
		 String hql = "from GLSuperviseRecord s where s.projectId="+projectId;
		
		 return findByHql(hql);
	}

}