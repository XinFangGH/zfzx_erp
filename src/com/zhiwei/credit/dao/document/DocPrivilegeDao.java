package com.zhiwei.credit.dao.document;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.document.DocPrivilege;
import com.zhiwei.credit.model.system.AppUser;

/**
 * 
 * @author 
 *
 */
public interface DocPrivilegeDao extends BaseDao<DocPrivilege>{
	
	/**
	 * 获取全部权限
	 * @param docPrivilege
	 * @param folderId
	 * @param pb
	 * @return
	 */
	public List<DocPrivilege> getAll(DocPrivilege docPrivilege,Long folderId,PagingBean pb);
	/**
	 * 获取某个人的全部公共文档权限
	 * @param docPrivilege
	 * @param urdId
	 * @return
	 */
	public List<DocPrivilege> getByPublic(DocPrivilege docPrivilege,Long urdId);
	/**
	 * 获取单个文件夹的权限数组
	 * @param user
	 * @param folderId
	 * @return
	 */
	public List<Integer> getRightsByFolder(AppUser user, Long folderId);
	/**
	 * 根据个人来获取文档的权限
	 * @param user
	 * @param docId
	 * @return
	 */
	public Integer getRightsByDocument(AppUser user,Long docId);
	/**
	 * 获取权限个数
	 */
	public Integer countPrivilege();
	
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