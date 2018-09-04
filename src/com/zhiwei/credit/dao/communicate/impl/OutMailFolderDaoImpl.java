package com.zhiwei.credit.dao.communicate.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.communicate.OutMailFolderDao;
import com.zhiwei.credit.model.communicate.MailFolder;
import com.zhiwei.credit.model.communicate.OutMailFolder;

public class OutMailFolderDaoImpl extends BaseDaoImpl<OutMailFolder> implements OutMailFolderDao{

	public OutMailFolderDaoImpl() {
		super(OutMailFolder.class);
	}
	/**
	 * 根据用户ID,或文件夹ParentID,或用户ID为空取得文件夹
	 * 取得MailFolder第一层文件夹,包括收件箱,发件箱,草稿箱,删除箱
	 */
	@Override
	public List<OutMailFolder> getAllUserFolderByParentId(Long userId,Long parentId) {
		String hql = "from OutMailFolder mf where mf.appUser.userId=? and parentId=? or userId is null";
		return findByHql(hql, new Object[]{userId, parentId});
		
	}
	
	/**
	 * 根据用户ID,或文件夹ParentID取得文件夹
	 */
	@Override
	public List<OutMailFolder> getUserFolderByParentId(Long userId, Long parentId) {
		String hql = "from OutMailFolder mf where mf.appUser.userId=? and parentId=?";
		return findByHql(hql, new Object[]{userId, parentId});
	}
	
	/**
	 * 取得某path下的所有Folder
	 * @param path
	 * @return
	 */
	@Override
	public List<OutMailFolder> getFolderLikePath(String path) {
		String hql="from OutMailFolder mf where mf.path like ?";
		return findByHql(hql,new Object[]{path+'%'});
	}
	

}