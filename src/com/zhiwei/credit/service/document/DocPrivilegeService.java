package com.zhiwei.credit.service.document;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.document.DocPrivilege;
import com.zhiwei.credit.model.system.AppUser;

public interface DocPrivilegeService extends BaseService<DocPrivilege>{
	public List<DocPrivilege> getAll(DocPrivilege docPrivilege,Long folderId,PagingBean pb);
	public List<Integer> getRightsByFolder(AppUser user, Long folderId);
	public Integer getRightsByDocument(AppUser user, Long docId);
	/**
	 * 获取文件夹权限的信息
	 * 2011-9-14
	 * @param folderId
	 * @return
	 */
	public DocPrivilege findByFolderId(Long folderId);
	
	/**
	 * 获取文档权限信息
	 */
	public DocPrivilege findByDocId(Long docId);
	
}


