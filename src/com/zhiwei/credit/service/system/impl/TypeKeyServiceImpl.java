package com.zhiwei.credit.service.system.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.system.TypeKeyDao;
import com.zhiwei.credit.model.system.TypeKey;
import com.zhiwei.credit.service.system.TypeKeyService;

public class TypeKeyServiceImpl extends BaseServiceImpl<TypeKey> implements TypeKeyService{
	private TypeKeyDao dao;
	
	public TypeKeyServiceImpl(TypeKeyDao dao) {
		super(dao);
		this.dao=dao;
	}

}