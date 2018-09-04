package com.zhiwei.credit.dao.communicate.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;

import com.zhiwei.core.Constants;
import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.communicate.MailBoxDao;
import com.zhiwei.credit.model.communicate.MailBox;

public class MailBoxDaoImpl extends BaseDaoImpl<MailBox> implements MailBoxDao{

	public MailBoxDaoImpl() {
		super(MailBox.class);
	}

	@Override
	public Long CountByFolderId(Long folderId) {
		String hql = "select count(*) from MailBox where folderId ="+folderId;
		
		Query query = getSession().createQuery(hql);
		return (Long)getHibernateTemplate().find(hql).get(0);
	}
	
	public List<MailBox> findByFolderId(Long folderId){
		String hql = "from MailBox where folderId = ?";
		return findByHql(hql, new Object[]{folderId});
	}

	@Override
	public List<MailBox> findBySearch(String searchContent, PagingBean pb) {
		ArrayList params = new ArrayList();
		
		StringBuffer hql = new StringBuffer("from MailBox mb where mb.delFlag = ? and mb.appUser.userId =?");
		params.add(Constants.FLAG_UNDELETED);
		params.add(ContextUtil.getCurrentUserId());
		
		if(StringUtils.isNotEmpty(searchContent)){
			hql.append(" and (mb.mail.subject like ? or mb.mail.content like ?)");
			params.add("%"+searchContent+"%");
			params.add("%"+searchContent+"%");
		}
		
		hql.append(" order by mb.sendTime desc");
		return findByHql(hql.toString(),params.toArray(), pb);
	}

}