package com.zhiwei.credit.service.info.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.info.SectionDao;
import com.zhiwei.credit.model.info.Section;
import com.zhiwei.credit.service.info.SectionService;

public class SectionServiceImpl extends BaseServiceImpl<Section> implements SectionService{
	@SuppressWarnings("unused")
	private SectionDao dao;
	
	public SectionServiceImpl(SectionDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public Integer getLastColumn() {
		return dao.getLastColumn();
	}

}