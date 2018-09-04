package com.zhiwei.credit.dao.document;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.List;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.document.DocFolder;
import com.zhiwei.credit.model.system.AppUser;

/**
 * 
 * @author 
 *
 */
public interface DocFolderDao extends BaseDao<DocFolder>{
	
	/**
	 * 根据父节点来获取用户文件夹列表
	 * @param userId
	 * @param parentId
	 * @return
	 */
	public List<DocFolder> getUserFolderByParentId(Long userId,Long parentId);
	/**
	 * 根据父节点来获取所有子文件夹
	 * @param parentId
	 * @return
	 */
	public List<DocFolder> getPublicFolderByParentId(Long parentId);
	/**
	 * 取得某path下的所有Folder
	 * @param path
	 * @return
	 */
	public List<DocFolder> getFolderLikePath(String path);
	
	/**
	 * 查找目录下的子目录 
	 */
	public List<DocFolder> findByParentId(Long parentId);
	/**
	 * 根据用户和名字查找
	 */
	public List<DocFolder> findByUserAndName(Long userId,String foleName);
	/**
	 * 根据父节点查找在线文件夹
	 */
	public List<DocFolder> getOnlineFolderByParentId(Long parentId);
	
}