package com.zhiwei.credit.service.document.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.document.DocPrivilegeDao;
import com.zhiwei.credit.model.document.DocPrivilege;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.document.DocPrivilegeService;

public class DocPrivilegeServiceImpl extends BaseServiceImpl<DocPrivilege> implements DocPrivilegeService{
	private DocPrivilegeDao dao;
	
	public DocPrivilegeServiceImpl(DocPrivilegeDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<DocPrivilege> getAll(DocPrivilege docPrivilege,Long folderId, PagingBean pb) {
		
		return dao.getAll(docPrivilege,folderId, pb);
	}

	@Override
	public List<Integer> getRightsByFolder(AppUser user, Long folderId) {
		return dao.getRightsByFolder(user, folderId);
	}

	@Override
	public Integer getRightsByDocument(AppUser user, Long docId) {
		return dao.getRightsByDocument(user, docId);
	}

	@Override
	public DocPrivilege findByDocId(Long docId) {
		return dao.findByDocId(docId);
	}

	@Override
	public DocPrivilege findByFolderId(Long folderId) {
		return dao.findByFolderId(folderId);
	}

}