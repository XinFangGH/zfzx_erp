package com.zhiwei.credit.dao.archive;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import java.util.Date;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.archive.ArchHasten;

/**
 * 
 * @author 
 *
 */
public interface ArchHastenDao extends BaseDao<ArchHasten>{
	/**
	 * 获取最后一次发送催办信息的时间
	 * @param archivesId
	 * @param userId
	 * @return
	 */
	public Date getLeastRecordByUser(Long archivesId);
}