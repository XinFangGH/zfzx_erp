package com.zhiwei.credit.service.system.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.system.DemensionDao;
import com.zhiwei.credit.model.system.Demension;
import com.zhiwei.credit.service.system.DemensionService;

public class DemensionServiceImpl extends BaseServiceImpl<Demension> implements DemensionService{
	@SuppressWarnings("unused")
	private DemensionDao dao;
	
	public DemensionServiceImpl(DemensionDao dao) {
		super(dao);
		this.dao=dao;
	}

}