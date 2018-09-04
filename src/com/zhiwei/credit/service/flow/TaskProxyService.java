package com.zhiwei.credit.service.flow;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import com.zhiwei.core.service.BaseService;
import com.zhiwei.core.web.paging.PageBean;
import com.zhiwei.credit.model.flow.TaskProxy;

/**
 * 
 * @author 
 *
 */
public interface TaskProxyService extends BaseService<TaskProxy>{

	/**
	 * 代理列表查询方法
	 * @param pageBean
	 */
	void findList(PageBean<TaskProxy> pageBean);
	
}


