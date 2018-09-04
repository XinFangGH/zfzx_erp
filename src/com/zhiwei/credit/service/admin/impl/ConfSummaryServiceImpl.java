package com.zhiwei.credit.service.admin.impl;

/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
 */

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.admin.ConfSummaryDao;
import com.zhiwei.credit.model.admin.ConfSummary;
import com.zhiwei.credit.service.admin.ConfSummaryService;

/**
 * @description ConfSummarySerivceImpl
 * @author YHZ
 * @data 2010-10-8 PM
 * 
 */
public class ConfSummaryServiceImpl extends BaseServiceImpl<ConfSummary>
		implements ConfSummaryService {
	private ConfSummaryDao dao;

	public ConfSummaryServiceImpl(ConfSummaryDao dao) {
		super(dao);
		this.dao = dao;
	}

	/**
	 * @description 发送
	 * @param cm
	 *            ConfSummary
	 * @param fileIds
	 * @return ConfSummary
	 */
	public ConfSummary send(ConfSummary cm, String fileIds) {
		return dao.send(cm, fileIds);
	}

	/**
	 * @description 保存
	 * @param cm
	 *            ConfSummary
	 * @param fileIds
	 * @return ConfSummary
	 */
	public ConfSummary save(ConfSummary cm, String fileIds) {
		return dao.save(cm, fileIds);
	}

}