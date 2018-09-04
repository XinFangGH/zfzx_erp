package com.zhiwei.credit.service.archive.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.archive.ArchivesDocDao;
import com.zhiwei.credit.model.archive.ArchivesDoc;
import com.zhiwei.credit.service.archive.ArchivesDocService;
public class ArchivesDocServiceImpl extends BaseServiceImpl<ArchivesDoc> implements ArchivesDocService{
	private ArchivesDocDao dao;
	
	public ArchivesDocServiceImpl(ArchivesDocDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<ArchivesDoc> findByAid(Long archivesId) {
		return dao.findByAid(archivesId);
	}

}