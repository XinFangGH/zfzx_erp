package com.zhiwei.credit.service.creditFlow.customer.person.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.customer.person.CsPersonHouseDao;
import com.zhiwei.credit.model.creditFlow.customer.person.CsPersonHouse;
import com.zhiwei.credit.service.creditFlow.customer.person.CsPersonHouseService;

/**
 * 
 * @author 
 *
 */
public class CsPersonHouseServiceImpl extends BaseServiceImpl<CsPersonHouse> implements CsPersonHouseService{
	@SuppressWarnings("unused")
	private CsPersonHouseDao dao;
	
	public CsPersonHouseServiceImpl(CsPersonHouseDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<CsPersonHouse> getByPersonId(String personId) {
		
		return dao.getByPersonId(personId);
	}

}