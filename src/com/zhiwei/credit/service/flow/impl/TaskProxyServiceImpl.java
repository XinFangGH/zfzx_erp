package com.zhiwei.credit.service.flow.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.web.paging.PageBean;
import com.zhiwei.credit.dao.flow.TaskProxyDao;
import com.zhiwei.credit.model.flow.TaskProxy;
import com.zhiwei.credit.service.flow.TaskProxyService;

/**
 * 
 * @author 
 *
 */
public class TaskProxyServiceImpl extends BaseServiceImpl<TaskProxy> implements TaskProxyService{
	@SuppressWarnings("unused")
	private TaskProxyDao dao;
	
	public TaskProxyServiceImpl(TaskProxyDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public void findList(PageBean<TaskProxy> pageBean) {
		dao.findList(pageBean);
	}

}