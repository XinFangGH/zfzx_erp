package com.zhiwei.credit.dao.communicate;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.communicate.MailFolder;
import com.zhiwei.credit.model.document.DocFolder;

/**
 * 
 * @author 
 *
 */
public interface MailFolderDao extends BaseDao<MailFolder>{

	public List<MailFolder> getUserFolderByParentId(Long userId, Long parentId);

	public List<MailFolder> getAllUserFolderByParentId(Long userId,Long parentId);

	public List<MailFolder> getFolderLikePath(String path);
	
}