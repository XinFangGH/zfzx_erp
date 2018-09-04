package com.zhiwei.credit.service.creditFlow.thirdRalation.impl;
/*
 *  北京互融时代软件有限公司 hurong协同办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2010 BEIJING JINZHIWANWEI SOFTWARES LIMITED COMPANY
*/


import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.thirdRalation.SVEnterprisePersonDao;
import com.zhiwei.credit.model.creditFlow.thirdRalation.SVEnterprisePerson;
import com.zhiwei.credit.service.creditFlow.thirdRalation.SVEnterprisePersonService;



public class SVEnterprisePersonServiceImpl extends BaseServiceImpl<SVEnterprisePerson> implements SVEnterprisePersonService{
	private SVEnterprisePersonDao dao;
	
	public SVEnterprisePersonServiceImpl(SVEnterprisePersonDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public SVEnterprisePerson findSVEnterprisePerson(long customerId, String type) {
		
		return dao.findSVEnterprisePerson(customerId, type);
	}


}