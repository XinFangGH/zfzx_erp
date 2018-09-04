package com.zhiwei.credit.service.flow;

/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
 */
import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.flow.TaskSign;

/**
 * @description 任务会签
 * @class TaskSignService
 * @author YHZ
 * @company www.credit-software.com
 * @data 2011-1-5PM
 * 
 */
public interface TaskSignService extends BaseService<TaskSign> {

	/**
	 * @description 根据assignId查询TaskSign对象
	 * @param assignId
	 *            assignId
	 * @return TaskSign
	 */
	public TaskSign findByAssignId(Long assignId);
}
