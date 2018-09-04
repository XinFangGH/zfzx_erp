package com.zhiwei.credit.service.archive.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.archive.ArchivesDepDao;
import com.zhiwei.credit.model.archive.ArchivesDep;
import com.zhiwei.credit.service.archive.ArchivesDepService;

public class ArchivesDepServiceImpl extends BaseServiceImpl<ArchivesDep> implements ArchivesDepService{
	private ArchivesDepDao dao;
	
	public ArchivesDepServiceImpl(ArchivesDepDao dao) {
		super(dao);
		this.dao=dao;
	}

}