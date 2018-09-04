package com.zhiwei.credit.dao.archive.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.archive.ArchTemplateDao;
import com.zhiwei.credit.model.archive.ArchTemplate;

public class ArchTemplateDaoImpl extends BaseDaoImpl<ArchTemplate> implements ArchTemplateDao{

	public ArchTemplateDaoImpl() {
		super(ArchTemplate.class);
	}

}