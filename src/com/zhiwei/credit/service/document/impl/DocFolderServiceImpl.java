package com.zhiwei.credit.service.document.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.List;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.document.DocFolderDao;
import com.zhiwei.credit.model.document.DocFolder;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.document.DocFolderService;

public class DocFolderServiceImpl extends BaseServiceImpl<DocFolder> implements DocFolderService{
	private DocFolderDao dao;
	
	public DocFolderServiceImpl(DocFolderDao dao) {
		super(dao);
		this.dao=dao;
	}
	
	public List<DocFolder> getUserFolderByParentId(Long userId,Long parentId){
		return dao.getUserFolderByParentId(userId, parentId);
	}
	
	/**
	 * 取得某path下的所有Folder
	 * @param path
	 * @return
	 */
	public List<DocFolder> getFolderLikePath(String path){
		return dao.getFolderLikePath(path);
	}

	@Override
	public List<DocFolder> getPublicFolderByParentId(Long parentId) {
		return dao.getPublicFolderByParentId( parentId);
	}

	@Override
	public List<DocFolder> findByParentId(Long parentId) {
		
		return dao.findByParentId(parentId);
	}

	@Override
	public List<DocFolder> findByUserAndName(Long userId, String foleName) {
		return dao.findByUserAndName(userId, foleName);
	}

	@Override
	public List<DocFolder> getOnlineFolderByParentId(Long parentId) {
		return dao.getOnlineFolderByParentId(parentId);
	}
}