package com.zhiwei.credit.dao.archive.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.archive.ArchivesDao;
import com.zhiwei.credit.model.archive.Archives;
import com.zhiwei.credit.model.system.AppRole;

public class ArchivesDaoImpl extends BaseDaoImpl<Archives> implements ArchivesDao{

	public ArchivesDaoImpl() {
		super(Archives.class);
	}

	@Override
	public List<Archives> findByUserOrRole(Long userId, Set<AppRole> roles,
			PagingBean pb) {
		Iterator<AppRole> it=roles.iterator();
		StringBuffer sb=new StringBuffer();
		while(it.hasNext()){
			if(sb.length()>0){
				sb.append(",");
			}
			sb.append(it.next().getRoleId().toString());
		}
		StringBuffer hql=new StringBuffer("select distinct vo1.archivesId from Archives vo1,ArchDispatch vo2 where vo2.archives=vo1 and vo2.archUserType=2 and (vo2.userId=?");
		if(sb.length()>0){
			hql.append(" or vo2.disRoleId in ("+sb+")");
		}
		hql.append(")");
		Object[] objs={userId};
		List ids2=find(hql.toString(), objs, pb);
		return findByIds(ids2);
	}

	private List<Archives> findByIds(List ids){
		String hql="from Document doc where doc.docId in (";
		StringBuffer ids2=new StringBuffer();
		
		for(int i=0;i<ids.size();i++){
			ids2.append(ids.get(i).toString()).append(",");
		}
		if(ids.size()>0){
			ids2.deleteCharAt(ids2.length()-1);
			hql+=ids2.toString() + ")";
			return findByHql(hql);
		}else{
			return new ArrayList();
		}
	}

}