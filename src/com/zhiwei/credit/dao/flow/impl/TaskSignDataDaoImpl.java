package com.zhiwei.credit.dao.flow.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
*/
import java.util.List;
import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.flow.TaskSignDataDao;
import com.zhiwei.credit.model.flow.TaskSign;
import com.zhiwei.credit.model.flow.TaskSignData;

@SuppressWarnings("unchecked")
public class TaskSignDataDaoImpl extends BaseDaoImpl<TaskSignData> implements TaskSignDataDao{

	public TaskSignDataDaoImpl() {
		super(TaskSignData.class);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.zhiwei.credit.dao.flow.TaskSignDataDao#getVoteCounts(java.lang.String, java.lang.Short)
	 */
	public Long getVoteCounts(String taskId,Short isAgree,String paramType){
		String hql = "";
		if("true".equals(paramType)){
			String params = isAgree+","+TaskSign.SIGN_VOTE_QUALIFIED;
			hql="select count(dataId) from TaskSignData tsd where tsd.taskId=? and tsd.isAgree in("+params+")";
		}else{
			hql="select count(dataId) from TaskSignData tsd where tsd.taskId=? and tsd.isAgree="+isAgree;
		}
		
		Object count=findUnique(hql, new Object[]{taskId});
		return new Long(count.toString());
	}
	
	/**
	 * 取得某任务对应的会签投票情况
	 * @param taskId
	 * @return
	 */
	public List<TaskSignData> getByTaskId(String taskId){
		String hql="from TaskSignData tsd where tsd.taskId=?";
		return findByHql(hql,new Object[]{taskId});
	}

	/**
	 * 取得某项目对应的会签投票情况
	 * @param runId
	 * @return
	 */
	public List<TaskSignData> getByRunId(Long runId) {
		String hql="from TaskSignData tsd where tsd.runId=?";
		return findByHql(hql,new Object[]{runId});
	}
	
	/**
	 * 取得某项目对应的会签投票情况
	 * @param runId
	 * @param taskId
	 * @return add  by lu 2012.05.24
	 */
	public List<TaskSignData> getByRunId(Long runId,String taskId){
		String hql="from TaskSignData tsd where tsd.runId=? and tsd.taskId=?";
		return findByHql(hql,new Object[]{runId,taskId});
	}
	
	/**
	 * 取得某项目对应的会签投票情况
	 * @param runId
	 * @param fromTaskId
	 * @return add  by lu 2012.05.25
	 */
	public Integer getTotalScore(Long runId,String fromTaskId){
		String hql="select SUM(tsd.isAgree) from TaskSignData tsd where tsd.runId=? and tsd.taskId=? and tsd.isAgree!=-1";
		Object count=findUnique(hql, new Object[]{runId,fromTaskId});
		if(count!=null&&!"0".equals(count)){
			return new Integer(count.toString());
		}else{
			return 0;
		}
	}
	
	/**
	 * 取得某项目对应的会签投票情况(针对多次会签的情况获取当前的会签投票情况)
	 * @param fromTaskId
	 * @return add  by lu 2012.05.31
	 */
	public List<TaskSignData> getByFromTaskId(String fromTaskId){
		String hql="from TaskSignData tsd where tsd.fromTaskId=?";
		return findByHql(hql,new Object[]{fromTaskId});
	}
	
	/**
	 * 取得某项目对应的会签投票情况(针对多次会签的情况获取当前的会签投票情况)
	 * @param runId
	 * @return add  by lisl 2012.06.06
	 */
	public List<TaskSignData> getDecisionByRunId(Long runId){
		String hql="from TaskSignData tsd where tsd.runId=? order by tsd.dataId desc";
		List<TaskSignData> list = findByHql(hql,new Object[]{runId});
		String fromTaskId = "";
		if(list.size() > 0) {
			fromTaskId =  findByHql(hql,new Object[]{runId}).get(0).getFromTaskId();
			String hql1 = "from TaskSignData tsd where tsd.runId=? and tsd.fromTaskId = ?";
			return findByHql(hql1,new Object[]{runId,fromTaskId});
		}
		return null;
	}
	
	/**
	 * 根据流程审保会记录查询对应会签情况  add by lu 2012.12.21
	 * @param formId
	 * @return
	 */
	public TaskSignData getByFormId(Long formId){
		String hql="from TaskSignData tsd where tsd.formId=?";
		List<TaskSignData> list = findByHql(hql, new Object[]{formId});
		if(list!=null&&list.size()!=0){
			return list.get(0);
		}
		return null;
	}
}