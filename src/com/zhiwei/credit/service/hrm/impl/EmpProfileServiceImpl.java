package com.zhiwei.credit.service.hrm.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.hrm.EmpProfileDao;
import com.zhiwei.credit.model.hrm.EmpProfile;
import com.zhiwei.credit.service.hrm.EmpProfileService;

public class EmpProfileServiceImpl extends BaseServiceImpl<EmpProfile> implements EmpProfileService{
	private EmpProfileDao dao;
	
	public EmpProfileServiceImpl(EmpProfileDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public boolean checkProfileNo(String profileNo) {
		return dao.checkProfileNo(profileNo);
	}

}