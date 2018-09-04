package com.zhiwei.credit.service.creditFlow.guarantee.EnterpriseBusiness.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.guarantee.EnterpriseBusiness.GlSuperviseRecordDao;
import com.zhiwei.credit.model.creditFlow.guarantee.EnterpriseBusiness.GLSuperviseRecord;
import com.zhiwei.credit.service.creditFlow.guarantee.EnterpriseBusiness.GlSuperviseRecordService;

/**
 * 
 * @author 
 *
 */
public class GlSuperviseRecordServiceImpl extends BaseServiceImpl<GLSuperviseRecord> implements GlSuperviseRecordService{
	@SuppressWarnings("unused")
	private GlSuperviseRecordDao dao;
	
	public GlSuperviseRecordServiceImpl(GlSuperviseRecordDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<GLSuperviseRecord> getListByProjectId(Long projectId) {
		// TODO Auto-generated method stub
		return this.dao.getListByProjectId(projectId);
	}

}