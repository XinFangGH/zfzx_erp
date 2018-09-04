package com.zhiwei.credit.service.flow.impl;

/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
 */
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.flow.TaskSignDao;
import com.zhiwei.credit.model.flow.TaskSign;
import com.zhiwei.credit.service.flow.TaskSignService;

/**
 * @description 任务会签
 * @class TaskSignServiceImpl
 * @author YHZ
 * @company www.credit-software.com
 * @data 2011-1-5PM
 * 
 */
public class TaskSignServiceImpl extends BaseServiceImpl<TaskSign> implements TaskSignService {
	private TaskSignDao dao;

	public TaskSignServiceImpl(TaskSignDao dao) {
		super(dao);
		this.dao = dao;
	}
	
	/**
	 * @description 根据assignId查询TaskSign对象数据
	 */
	@Override
	public TaskSign findByAssignId(Long assignId) {
		return dao.findByAssignId(assignId);
	}

}