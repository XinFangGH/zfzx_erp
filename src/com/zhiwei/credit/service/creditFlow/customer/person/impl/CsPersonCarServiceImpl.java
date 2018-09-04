package com.zhiwei.credit.service.creditFlow.customer.person.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.customer.person.CsPersonCarDao;
import com.zhiwei.credit.model.creditFlow.customer.person.CsPersonCar;
import com.zhiwei.credit.service.creditFlow.customer.person.CsPersonCarService;

/**
 * 
 * @author 
 *
 */
public class CsPersonCarServiceImpl extends BaseServiceImpl<CsPersonCar> implements CsPersonCarService{
	@SuppressWarnings("unused")
	private CsPersonCarDao dao;
	
	public CsPersonCarServiceImpl(CsPersonCarDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<CsPersonCar> getByPersonId(String personId) {
	
		return dao.getByPersonId(personId);
	}

}