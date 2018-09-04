package com.zhiwei.credit.dao.flow;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.flow.ProUserAssign;

/**
 * 
 * @author 
 *
 */
public interface ProUserAssignDao extends BaseDao<ProUserAssign>{
	public List<ProUserAssign> getByDeployId(String deployId);
	
	/**
	 * 取得某流程某个任务的授权信息
	 * @param deployId
	 * @param activityName
	 * @return
	 */
	public ProUserAssign getByDeployIdActivityName(String deployId,String activityName);
	
	/**
	 * 取得某流程某个任务的授权信息
	 * @param deployId
	 * @param flowNodeKey
	 * @return
	 * add by lu 2012.07.09
	 */
	public ProUserAssign getByDeployIdFlowNodeKey(String deployId,String flowNodeKey);
	
	/**
	 * @description 根据节点名称查询所有对象
	 * @param activityName
	 * @return List<ProUserAssign>
	 */
	public List<ProUserAssign> findByActivityName(String activityName);
}