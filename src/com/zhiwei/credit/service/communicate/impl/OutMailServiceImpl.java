package com.zhiwei.credit.service.communicate.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import java.util.List;
import java.util.Map;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.communicate.OutMailDao;
import com.zhiwei.credit.model.communicate.OutMail;
import com.zhiwei.credit.service.communicate.OutMailService;

public class OutMailServiceImpl extends BaseServiceImpl<OutMail> implements OutMailService{
	private OutMailDao dao;
	
	public OutMailServiceImpl(OutMailDao dao) {
		super(dao);
		this.dao=dao;
	}
	public List<OutMail> findByFolderId(Long folderId){
		return dao.findByFolderId( folderId);
	}

	public Long CountByFolderId(Long folderId){
		return dao.CountByFolderId(folderId);
	}
	public Map getUidByUserId(Long userId){
		return dao.getUidByUserId(userId);
	}
	
}