package com.zhiwei.credit.dao.creditFlow.thirdRalation.impl;
/*
 *  北京互融时代软件有限公司 hurong协同办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2010 BEIJING JINZHIWANWEI SOFTWARES LIMITED COMPANY
*/


import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.thirdRalation.SVEnterprisePersonDao;
import com.zhiwei.credit.model.creditFlow.thirdRalation.SVEnterprisePerson;


@SuppressWarnings("unchecked")
public class SVEnterprisePersonDaoImpl extends BaseDaoImpl<SVEnterprisePerson> implements SVEnterprisePersonDao{

	public SVEnterprisePersonDaoImpl() {
		super(SVEnterprisePerson.class);
	}

	@Override
	public SVEnterprisePerson findSVEnterprisePerson(long customerId, String type) {
		String hql="from SVEnterprisePerson sVEnterprisePerson where sVEnterprisePerson.id=? and sVEnterprisePerson.type=?";
		return (SVEnterprisePerson)getSession().createQuery(hql).setParameter(0, customerId).setParameter(1, type).uniqueResult();
	}



}