package com.zhiwei.credit.service.creditFlow.assuretenet.impl;
/*
 *  北京互融时代软件有限公司 hurong协同办公管理系统   -- http://www.hurongtime.com/
 *  Copyright (C) 2008-2010 BEIJING JINZHIWANWEI SOFTWARES LIMITED COMPANY
*/
import java.util.List;
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.assuretenet.SlProcreditAssuretenetDao;
import com.zhiwei.credit.model.creditFlow.assuretenet.SlProcreditAssuretenet;
import com.zhiwei.credit.service.creditFlow.assuretenet.SlProcreditAssuretenetService;

public class SlProcreditAssuretenetServiceImpl extends BaseServiceImpl<SlProcreditAssuretenet> implements SlProcreditAssuretenetService{
	private SlProcreditAssuretenetDao dao;
	
	public SlProcreditAssuretenetServiceImpl(SlProcreditAssuretenetDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<SlProcreditAssuretenet> getByProjId(String projId,String businessType) {
		return dao.getByProjId(projId, businessType);
	}

}