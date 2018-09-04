package com.zhiwei.credit.dao.flow.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.flow.ProHandleCompDao;
import com.zhiwei.credit.model.flow.ProHandleComp;

@SuppressWarnings("unchecked")
public class ProHandleCompDaoImpl extends BaseDaoImpl<ProHandleComp> implements ProHandleCompDao{

	public ProHandleCompDaoImpl() {
		super(ProHandleComp.class);
	}
	/*
	 * (non-Javadoc)
	 * @see com.zhiwei.credit.dao.flow.ProHandleCompDao#getByDeployIdActivityName(java.lang.String, java.lang.String)
	 */
	public List<ProHandleComp> getByDeployIdActivityName(String deployId,String activityName){
		String hql="from ProHandleComp phc where phc.deployId=? and phc.activityName=?";
		return(List<ProHandleComp>)findByHql(hql, new Object[]{deployId,activityName});
	}
	/*
	 * (non-Javadoc)
	 * @see com.zhiwei.credit.dao.flow.ProHandleCompDao#getByDeployIdActivityNameHandleType(java.lang.String, java.lang.String, java.lang.Short)
	 */
	public List<ProHandleComp> getByDeployIdActivityNameHandleType(String deployId,String activityName,Short handleType){
		String hql="from ProHandleComp phc where phc.deployId=? and phc.activityName=? and phc.handleType=?";
		return(List<ProHandleComp>)findByHql(hql, new Object[]{deployId,activityName,handleType});
	}
	
	public ProHandleComp getProHandleComp(String deployId,String activityName,String eventName){
		String hql="from ProHandleComp phc where phc.deployId=? and phc.activityName=? and eventName=? ";
		return (ProHandleComp)findUnique(hql, new Object[]{deployId,activityName,eventName});
	}
	/*
	 * (non-Javadoc)
	 * @see com.zhiwei.credit.dao.flow.ProHandleCompDao#getByDeployId(java.lang.String)
	 */
	public List<ProHandleComp> getByDeployId(String deployId){
		String hql="from ProHandleComp phc where phc.deployId=?";
		return (List<ProHandleComp>)findByHql(hql,new Object[]{deployId});
	}

}