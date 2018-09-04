package com.zhiwei.credit.service.archive.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.archive.ArchDispatchDao;
import com.zhiwei.credit.model.archive.ArchDispatch;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.archive.ArchDispatchService;

public class ArchDispatchServiceImpl extends BaseServiceImpl<ArchDispatch> implements ArchDispatchService{
	private ArchDispatchDao dao;
	
	public ArchDispatchServiceImpl(ArchDispatchDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<ArchDispatch> findByUser(AppUser user, PagingBean pb) {
		return dao.findByUser(user, pb);
	}

	@Override
	public int countArchDispatch(Long archivesId) {
		return dao.findRecordByArc(archivesId).size();
	}

}