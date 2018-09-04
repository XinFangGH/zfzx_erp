package com.zhiwei.credit.dao.system.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.system.TypeKeyDao;
import com.zhiwei.credit.model.system.TypeKey;

public class TypeKeyDaoImpl extends BaseDaoImpl<TypeKey> implements TypeKeyDao{

	public TypeKeyDaoImpl() {
		super(TypeKey.class);
	}

}