package com.zhiwei.credit.dao.flow.impl;

/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
 */

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.flow.TaskSignDao;
import com.zhiwei.credit.model.flow.TaskSign;

/**
 * @description 任务会签
 * @class TaskSignDaoImpl
 * @author YHZ
 * @company www.credit-software.com
 * @data 2011-1-5PM
 * 
 */
@SuppressWarnings("unchecked")
public class TaskSignDaoImpl extends BaseDaoImpl<TaskSign> implements
		TaskSignDao {

	public TaskSignDaoImpl() {
		super(TaskSign.class);
	}

	/**
	 * @description 根据assignId查询TaskSign对象数据
	 */
	@Override
	public TaskSign findByAssignId(Long assignId) {
		String hql = "from TaskSign ts where ts.proUserAssign.assignId=? ";
		return (TaskSign) findUnique(hql, new Object[] { assignId });
	}

}