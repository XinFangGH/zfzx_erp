package com.zhiwei.credit.service.creditFlow.customer.person.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.customer.person.BpCustPersonEducationDao;
import com.zhiwei.credit.model.creditFlow.customer.person.BpCustPersonEducation;
import com.zhiwei.credit.service.creditFlow.customer.person.BpCustPersonEducationService;

/**
 * 
 * @author 
 *
 */
public class BpCustPersonEducationServiceImpl extends BaseServiceImpl<BpCustPersonEducation> implements BpCustPersonEducationService{
	@SuppressWarnings("unused")
	private BpCustPersonEducationDao dao;
	
	public BpCustPersonEducationServiceImpl(BpCustPersonEducationDao dao) {
		super(dao);
		this.dao=dao;
	}

}