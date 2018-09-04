package com.zhiwei.credit.dao.flow.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.flow.ProUserAssignDao;
import com.zhiwei.credit.model.flow.ProUserAssign;

public class ProUserAssignDaoImpl extends BaseDaoImpl<ProUserAssign> implements ProUserAssignDao{

	public ProUserAssignDaoImpl() {
		super(ProUserAssign.class);
	}
	
	public List<ProUserAssign> getByDeployId(String deployId){
		String hql="from ProUserAssign pua where pua.deployId=?";
		return findByHql(hql, new Object[]{deployId});
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.zhiwei.credit.dao.flow.ProUserAssignDao#getByDeployIdActivityName(java.lang.String, java.lang.String)
	 */
	public ProUserAssign getByDeployIdActivityName(String deployId,String activityName){
		String hql="from ProUserAssign pua where pua.deployId=? and pua.activityName=?";
		return (ProUserAssign)findUnique(hql, new Object[]{deployId,activityName});
	}
	
	/**
	 * 取得某流程某个任务的授权信息
	 * @param deployId
	 * @param flowNodeKey
	 * @return
	 * add by lu 2012.07.09
	 */
	public ProUserAssign getByDeployIdFlowNodeKey(String deployId,String flowNodeKey){
		String hql = "from ProUserAssign pua where pua.deployId=? and pua.taskSequence like '%"+flowNodeKey+"%' order by pua.assignId desc";
		List<ProUserAssign> list = findByHql(hql, new Object[]{deployId});
		if(list!=null&&list.size()!=0){
			return list.get(0);
		}
		return null;
	}

	/**
	 * @description 根据节点名称查询所有对象
	 * @param activityName
	 * @return List<ProUserAssign>
	 */
	public List<ProUserAssign> findByActivityName(String activityName){
		String hql = "from ProUserAssign pua where pua.activityName = ? order by pua.assignId desc";
		return findByHql(hql, new Object[]{activityName});
	}
}