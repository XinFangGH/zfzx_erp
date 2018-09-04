package com.zhiwei.credit.service.archive.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import javax.annotation.Resource;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.archive.ArchTemplateDao;
import com.zhiwei.credit.model.archive.ArchTemplate;
import com.zhiwei.credit.service.archive.ArchTemplateService;
import com.zhiwei.credit.service.system.FileAttachService;

public class ArchTemplateServiceImpl extends BaseServiceImpl<ArchTemplate> implements ArchTemplateService{
	private ArchTemplateDao dao;
	
	@Resource
	FileAttachService fileAttachService;
	
	public ArchTemplateServiceImpl(ArchTemplateDao dao) {
		super(dao);
		this.dao=dao;
	}
	
	/**
	 * 删除该模板时，若存在附件，也需要同时删除附件
	 */
	public void remove(Long id) {
		ArchTemplate template=dao.get(id);
		remove(template);
		fileAttachService.removeByPath(template.getTempPath());
		
	}

}