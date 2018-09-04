package com.zhiwei.credit.service.info.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.info.SuggestBoxDao;
import com.zhiwei.credit.model.info.SuggestBox;
import com.zhiwei.credit.service.info.SuggestBoxService;

public class SuggestBoxServiceImpl extends BaseServiceImpl<SuggestBox> implements SuggestBoxService{
	private SuggestBoxDao dao;
	
	public SuggestBoxServiceImpl(SuggestBoxDao dao) {
		super(dao);
		this.dao=dao;
	}

}