package com.zhiwei.credit.dao.creditFlow.finance.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.finance.SlFundintentUrgeDao;
import com.zhiwei.credit.model.creditFlow.finance.SlFundintentUrge;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class SlFundintentUrgeDaoImpl extends BaseDaoImpl<SlFundintentUrge> implements SlFundintentUrgeDao{

	public SlFundintentUrgeDaoImpl() {
		super(SlFundintentUrge.class);
	}

	@Override
	public List<SlFundintentUrge> getlistbyfundintentId(Long fundIntentId) {
		String hql = "from SlFundintentUrge s where s.fundIntentId="+fundIntentId;
		return findByHql(hql);
	}

	@Override
	public List<SlFundintentUrge> getListByProjectId(Long projectId) {
		String hql="from SlFundintentUrge as s where s.projectId=? and s.fundIntentId is null";
		return getSession().createQuery(hql).setParameter(0, projectId).list();
	}

}