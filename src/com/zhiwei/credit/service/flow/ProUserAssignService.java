package com.zhiwei.credit.service.flow;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.flow.ProUserAssign;
import com.zhiwei.credit.model.flow.TaskSign;

public interface ProUserAssignService extends BaseService<ProUserAssign>{
	public List<ProUserAssign> getByDeployId(String deployId);
	
	/**
	 * 取得某流程某个任务的授权信息
	 * @param deployId
	 * @param activityName
	 * @return
	 */
	public ProUserAssign getByDeployIdActivityName(String deployId,String activityName);
	/**
	 * 把旧版本的流程的人员配置复制至新版本上去
	 * @param oldDeplyId
	 * @param newDeployId
	 * @param subCompanyKey
	 */
	public void copyNewConfig(String oldDeplyId,String newDeployId,String subCompanyKey);
	
	/**
	 * 取得某流程某个任务的授权信息
	 * @param deployId
	 * @param flowNodeKey
	 * @return
	 * add by lu 2012.07.09
	 */
	public ProUserAssign getByDeployIdFlowNodeKey(String deployId,String flowNodeKey);
	
	/**
	 * @description 判断并列节点的某个节点是否为主干节点
	 * @param activityName
	 * @return boolean
	 */
	public boolean isMainStemNode(String activityName,String beJuxtaposedFlowNodeKeys);
	
	/**
	 * @description 获取存在并列节点的流程的key
	 * @param activityName
	 * @return String
	 */
	public String findProcessNameByActivityName(String activityName,String keyElement);
	
	/**
	 * 判断某个节点是否为会签节点。直接返回boolean类型。
	 * @param deployId
	 * @param activityName
	 * @return
	 * add by lu 2013.06.17
	 */
	public boolean isCountersignNode(String deployId,String activityName);
	
}


