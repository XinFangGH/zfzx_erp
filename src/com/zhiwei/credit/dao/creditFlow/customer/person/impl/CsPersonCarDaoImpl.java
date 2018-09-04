package com.zhiwei.credit.dao.creditFlow.customer.person.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.customer.person.CsPersonCarDao;
import com.zhiwei.credit.model.creditFlow.customer.person.CsPersonCar;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class CsPersonCarDaoImpl extends BaseDaoImpl<CsPersonCar> implements CsPersonCarDao{

	public CsPersonCarDaoImpl() {
		super(CsPersonCar.class);
	}

	@Override
	public List<CsPersonCar> getByPersonId(String personId) {
		String hql=" from  CsPersonCar as car where car.person.id=? ";
		return this.getSession().createQuery(hql).setParameter(0, Integer.valueOf(personId)).list();
	}

}