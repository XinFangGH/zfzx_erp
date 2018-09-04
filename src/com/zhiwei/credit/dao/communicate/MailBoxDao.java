package com.zhiwei.credit.dao.communicate;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.communicate.MailBox;

/**
 * 
 * @author 
 *
 */
public interface MailBoxDao extends BaseDao<MailBox>{

	public Long CountByFolderId(Long folderId);
	
	public List<MailBox> findByFolderId(Long folderId);

	public List<MailBox> findBySearch(String searchContent, PagingBean pb);
}