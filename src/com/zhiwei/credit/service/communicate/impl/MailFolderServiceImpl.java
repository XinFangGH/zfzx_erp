package com.zhiwei.credit.service.communicate.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.communicate.MailFolderDao;
import com.zhiwei.credit.model.communicate.MailFolder;
import com.zhiwei.credit.model.document.DocFolder;
import com.zhiwei.credit.service.communicate.MailFolderService;

public class MailFolderServiceImpl extends BaseServiceImpl<MailFolder> implements MailFolderService{
	private MailFolderDao dao;
	
	public MailFolderServiceImpl(MailFolderDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<MailFolder> getUserFolderByParentId(Long curUserId,Long parentId) {
		
		return dao.getUserFolderByParentId(curUserId, parentId);
	}

	@Override
	public List<MailFolder> getAllUserFolderByParentId(Long userId,
			Long parentId) {
		return dao.getAllUserFolderByParentId(userId,parentId);
	}

	@Override
	public List<MailFolder> getFolderLikePath(String path) {
		return dao.getFolderLikePath(path);
	}

	

}