package com.zhiwei.credit.dao.admin;

/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
 */
import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.admin.ConfSummary;

/**
 * @description ConfSummaryDao
 * @author YHZ
 * @data 2010-10-8 PM
 * 
 */
public interface ConfSummaryDao extends BaseDao<ConfSummary> {

	/**
	 * @description 发送
	 */
	ConfSummary send(ConfSummary cm, String fileIds);

	/**
	 * @description 保存
	 */
	ConfSummary save(ConfSummary cm, String fileIds);

}