package com.zhiwei.credit.service.communicate;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.communicate.MailFolder;

public interface MailFolderService extends BaseService<MailFolder>{

	public List<MailFolder> getUserFolderByParentId(Long curUserId, Long parentId);

	public List<MailFolder> getAllUserFolderByParentId(Long userId, Long parentId);
	
	public List<MailFolder> getFolderLikePath(String path);
}


