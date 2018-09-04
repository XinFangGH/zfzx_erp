package com.zhiwei.credit.dao.archive.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import java.util.Date;
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.archive.ArchHastenDao;
import com.zhiwei.credit.model.archive.ArchHasten;

public class ArchHastenDaoImpl extends BaseDaoImpl<ArchHasten> implements ArchHastenDao{

	public ArchHastenDaoImpl() {
		super(ArchHasten.class);
	}

	@Override
	public Date getLeastRecordByUser(Long archivesId) {
		String hql="from ArchHasten vo where vo.archives.archivesId=? order by vo.createtime desc";
		List<ArchHasten> list=findByHql(hql,new Object[]{archivesId});
		if(list.size()>0){
			return list.get(0).getCreatetime();
		}else{
			return null;
		}
	}

}