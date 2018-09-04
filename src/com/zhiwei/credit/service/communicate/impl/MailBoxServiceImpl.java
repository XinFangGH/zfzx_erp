package com.zhiwei.credit.service.communicate.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.communicate.MailBoxDao;
import com.zhiwei.credit.model.communicate.MailBox;
import com.zhiwei.credit.service.communicate.MailBoxService;

public class MailBoxServiceImpl extends BaseServiceImpl<MailBox> implements MailBoxService{
	private MailBoxDao dao;
	
	public MailBoxServiceImpl(MailBoxDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public Long CountByFolderId(Long folderId) {
		return dao.CountByFolderId(folderId);
	}

	public List<MailBox> findByFolderId(Long folderId){
		return dao.findByFolderId(folderId);
	}

	@Override
	public List<MailBox> findBySearch(String searchContent, PagingBean pb) {
		return dao.findBySearch(searchContent,pb);
	}
}