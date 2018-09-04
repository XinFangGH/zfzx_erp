package com.zhiwei.credit.service.admin;

/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
 */

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.admin.ConfSummary;

/**
 * @description ConfSummaryService
 * @author YHZ
 * @date 2010-10-8 PM
 * 
 */
public interface ConfSummaryService extends BaseService<ConfSummary> {

	/**
	 * @description 发送
	 * @param cm
	 *            ConfSummary
	 * @param fileIds
	 *            附件ids
	 * @return ConfSummary
	 */
	ConfSummary send(ConfSummary cm, String fileIds);

	/**
	 * @description 保存
	 * @param cm
	 *            ConfSummary
	 * @param fileIds
	 * @return ConfSummary
	 */
	ConfSummary save(ConfSummary cm, String fileIds);
}
