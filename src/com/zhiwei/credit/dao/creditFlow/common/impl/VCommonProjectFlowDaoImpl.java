/**
 * 
 */
package com.zhiwei.credit.dao.creditFlow.common.impl;

import java.util.List;

import com.zhiwei.core.Constants;
import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.common.VCommonProjectFlowDao;
import com.zhiwei.credit.model.creditFlow.common.VCommonProjectFlow;

/**
 * @author Administrator
 *
 */
public class VCommonProjectFlowDaoImpl extends BaseDaoImpl<VCommonProjectFlow> implements VCommonProjectFlowDao{

	public VCommonProjectFlowDaoImpl(){
		super(VCommonProjectFlow.class);
	}
	
	public Long getProjectIdByRunId(Long runId) {
		String hql = "from VCommonProjectFlow v where v.runId="+runId;
		return findByHql(hql).get(0).getProjectId();
	}
	
	public List<VCommonProjectFlow> getByTaskIds(String taskIds,PagingBean pb){
		String hql = "from VCommonProjectFlow vp where vp.taskId in("+taskIds+") order by vp.runId desc";
		return findByHql(hql, null, pb);
	}
	
	public List<VCommonProjectFlow> getByPiId(String piId){
		String hql = "from VCommonProjectFlow vp where vp.piId=? and vp.runStatus=?";
		return findByHql(hql, new Object[]{piId,Constants.PROJECT_STATUS_MIDDLE});
	}
	
	public List<VCommonProjectFlow> getByTaskId(String taskId){
		String hql = "from VCommonProjectFlow vp where vp.taskId=?";
		return findByHql(hql,new Object[]{taskId});
	}
	
	public VCommonProjectFlow getByRunId(Long runId){
		String hql = "from VCommonProjectFlow vp where vp.runId=?";
		List<VCommonProjectFlow> list = findByHql(hql,new Object[]{runId});
		if(list!=null&&list.size()!=0){
			return list.get(0);
		}
		return null;
	}
}
