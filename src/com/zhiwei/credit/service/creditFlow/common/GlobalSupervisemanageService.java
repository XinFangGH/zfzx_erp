package com.zhiwei.credit.service.creditFlow.common;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.creditFlow.common.GlobalSupervisemanage;

/**
 * 
 * @author 
 *
 */
public interface GlobalSupervisemanageService extends BaseService<GlobalSupervisemanage>{
	public void init();
	public boolean supervisemanagePlanPush();
	public List<GlobalSupervisemanage> getListByProjectId(Long projectId,String businessType);
	
	/**
	 * 启动监管流程
	 * @param globalSupervisemanage 监管计划对象
	 */
	public void startSuperviseFlow(GlobalSupervisemanage globalSupervisemanage);
}


