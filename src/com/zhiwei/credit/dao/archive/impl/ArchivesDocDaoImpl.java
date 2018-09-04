package com.zhiwei.credit.dao.archive.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.archive.ArchivesDocDao;
import com.zhiwei.credit.model.archive.ArchivesDoc;

public class ArchivesDocDaoImpl extends BaseDaoImpl<ArchivesDoc> implements ArchivesDocDao{

	public ArchivesDocDaoImpl() {
		super(ArchivesDoc.class);
	}

	@Override
	public List<ArchivesDoc> findByAid(Long archivesId) {
		String hql="from ArchivesDoc vo where vo.archives.archivesId=?";
		Object [] objs={archivesId};
		return findByHql(hql, objs);
	}

}