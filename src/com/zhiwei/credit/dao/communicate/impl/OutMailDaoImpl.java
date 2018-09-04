package com.zhiwei.credit.dao.communicate.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.communicate.OutMailDao;
import com.zhiwei.credit.model.communicate.MailBox;
import com.zhiwei.credit.model.communicate.OutMail;

public class OutMailDaoImpl extends BaseDaoImpl<OutMail> implements OutMailDao{

	public OutMailDaoImpl() {
		super(OutMail.class);
	}
	public List<OutMail> findByFolderId(Long folderId){
		String hql = "from OutMail where folderId = ?";
		return findByHql(hql, new Object[]{folderId});
	}
	
	@Override
	public Long CountByFolderId(Long folderId) {
		String hql = "select count(*) from OutMail where folderId ="+folderId;
		return (Long)getHibernateTemplate().find(hql).get(0);
	}
	@Override
	public Map getUidByUserId(Long userId){
		String hql = "select om.uid from OutMail om where om.userId ="+userId;
		List<String> uidList= getHibernateTemplate().find(hql);
		Map uidMap = new HashMap();
		for(String uid:uidList){
			uidMap.put(uid, "Y");
		}
		return uidMap;
	}
	
}