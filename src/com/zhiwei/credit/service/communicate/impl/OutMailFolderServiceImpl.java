package com.zhiwei.credit.service.communicate.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.communicate.OutMailFolderDao;
import com.zhiwei.credit.model.communicate.MailFolder;
import com.zhiwei.credit.model.communicate.OutMailFolder;
import com.zhiwei.credit.service.communicate.OutMailFolderService;

public class OutMailFolderServiceImpl extends BaseServiceImpl<OutMailFolder> implements OutMailFolderService{
	private OutMailFolderDao dao;
	
	public OutMailFolderServiceImpl(OutMailFolderDao dao) {
		super(dao);
		this.dao=dao;
	}
	@Override
	public List<OutMailFolder> getAllUserFolderByParentId(Long userId,
			Long parentId) {
		return dao.getAllUserFolderByParentId(userId,parentId);
	}
	@Override
	public List<OutMailFolder> getUserFolderByParentId(Long userId,Long parentId){
		return dao.getUserFolderByParentId(userId,parentId);
	}
	@Override
	public List<OutMailFolder> getFolderLikePath(String path){
		return dao.getFolderLikePath(path);
	}

}