package com.zhiwei.credit.dao.communicate.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.communicate.MailFolderDao;
import com.zhiwei.credit.model.communicate.MailFolder;
import com.zhiwei.credit.model.document.DocFolder;

public class MailFolderDaoImpl extends BaseDaoImpl<MailFolder> implements MailFolderDao{

	public MailFolderDaoImpl() {
		super(MailFolder.class);
	}
	/**
	 * 根据用户ID,或文件夹ParentID取得文件夹
	 */
	@Override
	public List<MailFolder> getUserFolderByParentId(Long userId, Long parentId) {
		String hql = "from MailFolder mf where mf.appUser.userId=? and parentId=?";
		return findByHql(hql, new Object[]{userId, parentId});
	}
	
	/**
	 * 根据用户ID,或文件夹ParentID,或用户ID为空取得文件夹
	 * 取得MailFolder第一层文件夹,包括收件箱,发件箱,草稿箱,删除箱
	 */
	@Override
	public List<MailFolder> getAllUserFolderByParentId(Long userId,Long parentId) {
		String hql = "from MailFolder mf where mf.appUser.userId=? and parentId=? or userId is null";
		return findByHql(hql, new Object[]{userId, parentId});
	}

	/**
	 * 取得某path下的所有Folder
	 * @param path
	 * @return
	 */
	@Override
	public List<MailFolder> getFolderLikePath(String path) {
		String hql="from MailFolder mf where mf.path like ?";
		return findByHql(hql,new Object[]{path+'%'});
	}

}