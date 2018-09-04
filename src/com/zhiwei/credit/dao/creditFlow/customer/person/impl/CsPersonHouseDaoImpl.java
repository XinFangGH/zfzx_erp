package com.zhiwei.credit.dao.creditFlow.customer.person.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.customer.person.CsPersonHouseDao;
import com.zhiwei.credit.model.creditFlow.customer.person.CsPersonHouse;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class CsPersonHouseDaoImpl extends BaseDaoImpl<CsPersonHouse> implements CsPersonHouseDao{

	public CsPersonHouseDaoImpl() {
		super(CsPersonHouse.class);
	}

	@Override
	public List<CsPersonHouse> getByPersonId(String personId) {
		String hql=" from CsPersonHouse as house where house.person.id=? ";
		return this.getSession().createQuery(hql).setParameter(0, Integer.valueOf(personId)).list();
	}

}