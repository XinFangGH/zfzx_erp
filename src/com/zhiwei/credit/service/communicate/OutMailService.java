package com.zhiwei.credit.service.communicate;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import java.util.List;
import java.util.Map;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.communicate.MailBox;
import com.zhiwei.credit.model.communicate.OutMail;

public interface OutMailService extends BaseService<OutMail>{
	public List<OutMail> findByFolderId(Long folderId);
	public Long CountByFolderId(Long folderId);
	public Map getUidByUserId(Long userId);
	
}


