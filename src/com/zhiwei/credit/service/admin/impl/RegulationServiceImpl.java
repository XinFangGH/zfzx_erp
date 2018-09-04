package com.zhiwei.credit.service.admin.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.admin.RegulationDao;
import com.zhiwei.credit.model.admin.Regulation;
import com.zhiwei.credit.service.admin.RegulationService;

public class RegulationServiceImpl extends BaseServiceImpl<Regulation> implements RegulationService{
	@SuppressWarnings("unused")
	private RegulationDao dao;
	
	public RegulationServiceImpl(RegulationDao dao) {
		super(dao);
		this.dao=dao;
	}

}