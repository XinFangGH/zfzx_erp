package com.zhiwei.credit.dao.archive.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import java.util.Iterator;
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.archive.ArchDispatchDao;
import com.zhiwei.credit.model.archive.ArchDispatch;
import com.zhiwei.credit.model.system.AppRole;
import com.zhiwei.credit.model.system.AppUser;

public class ArchDispatchDaoImpl extends BaseDaoImpl<ArchDispatch> implements ArchDispatchDao{

	public ArchDispatchDaoImpl() {
		super(ArchDispatch.class);
	}

	@Override
	public List<ArchDispatch> findByUser(AppUser user,PagingBean pb) {
		Iterator<AppRole> it=user.getRoles().iterator();
		StringBuffer sb=new StringBuffer();
		while(it.hasNext()){
			if(sb.length()>0){
				sb.append(",");
			}
			sb.append(it.next().getRoleId().toString());
		}
		StringBuffer hql=new StringBuffer("from ArchDispatch vo where vo.archUserType=2 and vo.isRead=0 and (vo.userId=?");
		if(sb.length()>0){
			hql.append(" or vo.disRoleId in ("+sb+")");
		}
		hql.append(") order by vo.dispatchId desc");
		Object[] objs={user.getUserId()};
		return findByHql(hql.toString(), objs, pb);
	}

	@Override
	public List<ArchDispatch> findRecordByArc(Long archivesId) {
		String hql="from ArchDispatch vo where (vo.archUserType=0 or vo.archUserType=1) and vo.archives.archivesId=?";
		Object[] objs={archivesId};
		return findByHql(hql,objs);
	}

}