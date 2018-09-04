package com.zhiwei.credit.service.archive.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import java.util.Date;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.archive.ArchHastenDao;
import com.zhiwei.credit.model.archive.ArchHasten;
import com.zhiwei.credit.service.archive.ArchHastenService;

public class ArchHastenServiceImpl extends BaseServiceImpl<ArchHasten> implements ArchHastenService{
	private ArchHastenDao dao;
	
	public ArchHastenServiceImpl(ArchHastenDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public Date getLeastRecordByUser(Long archivesId) {
		return dao.getLeastRecordByUser(archivesId);
	}

}