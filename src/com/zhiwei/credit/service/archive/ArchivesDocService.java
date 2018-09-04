package com.zhiwei.credit.service.archive;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.archive.ArchivesDoc;

public interface ArchivesDocService extends BaseService<ArchivesDoc>{
	/**
	 * 根据公文ID来查找公文撰稿
	 */
	public List<ArchivesDoc> findByAid(Long archivesId);
}


