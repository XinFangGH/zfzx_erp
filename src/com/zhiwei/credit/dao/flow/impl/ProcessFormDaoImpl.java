package com.zhiwei.credit.dao.flow.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.transform.Transformers;

import com.zhiwei.core.Constants;
import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.flow.ProcessFormDao;
import com.zhiwei.credit.model.creditFlow.finance.BpFundIntent;
import com.zhiwei.credit.model.flow.RunData;
import com.zhiwei.credit.model.flow.ProcessForm;

public class ProcessFormDaoImpl extends BaseDaoImpl<ProcessForm> implements ProcessFormDao{

	public ProcessFormDaoImpl() {
		super(ProcessForm.class);
	}
	/*
	 * (non-Javadoc)
	 * @see com.zhiwei.credit.dao.flow.ProcessFormDao#getByRunId(java.lang.Long)
	 */
	public List getByRunId(Long runId){
		//String hql="select pf.activityName,pf.creatorName,pf.status,pf.createtime,pf.endtime,pf.durTimes,pf.comments,pf.safeLevel from ProcessForm pf where pf.processRun.runId=? and pf.endtime is not null order by pf.formId desc";
		//String hql="from ProcessForm pf where pf.processRun.runId=? and pf.endtime is not null order by pf.formId desc";
		//pf.endtime is not null表示进行中项目,没有显示在意见与说明列表。去掉此查询条件。(驳回：-1;审批通过：1;未审批：0;跳转：2) update by lu 2012.04.24
		String hql="from ProcessForm pf where pf.processRun.runId=? and pf.taskId is not null and pf.endtime is not null order by pf.formId desc";
		return findByHql(hql, new Object[]{runId});
	}
	
	public List getByRunId(Long runId,Long safeLevel){
		//String hql="select pf.activityName,pf.creatorName,pf.status,pf.createtime,pf.endtime,pf.durTimes,(case when pf.safeLevel >"+safeLevel+" then '您无权查看意见' else pf.comments end) as comments,pf.safeLevel from ProcessForm pf where pf.processRun.runId=? and pf.endtime is not null order by pf.formId desc";
		//pf.endtime is not null表示进行中项目,没有显示在意见与说明列表。去掉此查询条件。(驳回：-1;审批通过：1;未审批：0;跳转：2) update by lu 2012.04.24
		//String hql="select pf.activityName,pf.creatorName,pf.status,pf.createtime,pf.endtime,pf.durTimes,(case when pf.safeLevel >"+safeLevel+" then '您无权查看意见' else pf.comments end) as comments,pf.safeLevel from ProcessForm pf where pf.processRun.runId=? and pf.taskId is not null and pf.endtime is not null order by pf.formId desc";
		String hql="from ProcessForm pf where pf.processRun.runId=? and pf.taskId is not null and pf.endtime is not null order by pf.formId desc";
		return findByHql(hql, new Object[]{runId});
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.zhiwei.credit.dao.flow.ProcessFormDao#getByRunIdActivityName(java.lang.Long, java.lang.String)
	 */
	public ProcessForm getByRunIdActivityName(Long runId,String activityName){
		//取得最新的sn号
		Integer maxSn=getActvityExeTimes(runId, activityName).intValue();
		String hql="from ProcessForm pf where pf.processRun.runId=? and pf.activityName=? order by pf.formId desc";
		List<ProcessForm> formList=findByHql(hql, new Object[]{runId,activityName});
		if(formList!=null&&formList.size()!=0){
			return formList.get(0);
		}
		return null;
		//return (ProcessForm)findUnique(hql, new Object[]{runId,activityName});
	}
	
//	/**
//	 * 构造最新的流程实例对应的所有字段及数据
//	 * @param runId
//	 * @return
//	 */
//	public Map getVariables(Long runId){
//		Map variables=new HashMap();
//		String hql="from ProcessForm pf where pf.processRun.runId=? order by pf.createtime desc";
//		List<ProcessForm> forms=findByHql(hql,new Object[]{runId});
//		
//		for(ProcessForm form : forms){
//			Iterator<RunData> formDataIt = form.getFormDatas().iterator();
//			while(formDataIt.hasNext()){
//				RunData formData=formDataIt.next();
//				if(!variables.containsKey(formData.getFieldName())){//放置最新的值
//					variables.put(formData.getFieldName(), formData.getVal());
//				}
//			}
//		}
//		return variables;
//	}
	
	/*
	 * (non-Javadoc)
	 * @see com.zhiwei.credit.dao.flow.ProcessFormDao#getActvityExeTimes(java.lang.Long, java.lang.String)
	 */
	public Long getActvityExeTimes(Long runId,String taskSequenceNodeKey){
		String hql="from ProcessForm pf where pf.processRun.runId="+runId+" and pf.taskSequenceNodeKey like '%"+taskSequenceNodeKey+"%'";
		return Long.valueOf(findByHql(hql).size());
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.zhiwei.credit.dao.flow.ProcessFormDao#getByTaskId(java.lang.String)
	 */
	public ProcessForm getByTaskId(String taskId){
		String hql="from ProcessForm pf where pf.taskId=? order by pf.createtime desc";
		List<ProcessForm> pfs=findByHql(hql, new Object[]{taskId});
		if(pfs.size()>0){
			return pfs.get(0);
		}
		return null;
		//return (ProcessForm)findUnique(hql, new Object[]{taskId});
	}
	/*
	 * (non-Javadoc)
	 * @see com.zhiwei.credit.dao.flow.ProcessFormDao#getByRunIdTaskName(java.lang.Long, java.lang.String)
	 */
	public ProcessForm getByRunIdTaskName(Long runId,String taskName){
		String hql="from ProcessForm pf where pf.processRun.runId=? and pf.activityName=? order by pf.formId desc";
		List<ProcessForm> pfs=findByHql(hql, new Object[]{runId,taskName});
		if(pfs.size()>0){
			return pfs.get(0);
		}
		return null;
	}
	
	public List<ProcessForm> getCompleteTaskByUserId(Long userId,PagingBean pb){
		//String hql = "from ProcessForm p where p.creatorId=? and p.taskId is not null and p.status=? order by p.createtime desc";
		//已完成项目：status=1 or status=2(驳回：-1;审批通过：1;未审批：0;跳转：2) update by lu 2012.04.24
		String hql = "from ProcessForm p where p.creatorId=? and p.taskId is not null and p.status in(1,2,3) order by p.createtime desc";
		return find(hql, new Object[]{userId}, pb);
	}
	
	public List<ProcessForm> getCompleteTaskByUserIdProcessName(Long userId,String processName,PagingBean pb){
		List<Object> params=new ArrayList<Object>();
		StringBuffer hql=new StringBuffer();
		
		hql.append("from ProcessForm p where 1=1");
		
	    
	    
		if(StringUtils.isNotEmpty(processName)&&!"ALL".equals(processName)){
			String[] proArrs = processName.split(",");
			hql.append(" and (p.processRun.processName like ?");
			params.add("%" + proArrs[0] + "%");
			if(proArrs.length>1){
				for(int k=1;k<proArrs.length;k++){
					hql.append(" or p.processRun.processName like ?");
					params.add("%" + proArrs[k] + "%");
					if(k==proArrs.length-1){
						hql.append(")");
					}
				}
			}else{
				hql.append(")");
			}
		}
		
		hql.append(" and p.creatorId=? and p.taskId is not null and p.status in(1,2,3)");
		params.add(userId);
		//params.add(Constants.PROJECT_STATUS_MIDDLE);
		
		hql.append(" order by p.createtime desc");
         
		return findByHql(hql.toString(),params.toArray(),pb);
	}
	
	/**
	 * 查找某个流程某个任务某个状态的执行人
	 * @param runId
	 * @param activityName
	 * @param status
	 * @return
	 */
	public List<String> getByRunIdActivityName(Long runId,String taskSequenceNodeKey,Short status) {
		String hql="from ProcessForm pf where pf.processRun.runId="+runId+" and pf.taskSequenceNodeKey like '%"+taskSequenceNodeKey+"' and pf.status="+status;
		List<ProcessForm> fpList = findByHql(hql);
		List<String> taskIdList = new ArrayList<String>();
		for(int i=0;i<fpList.size();i++) {
			taskIdList.add(fpList.get(i).getTaskId());
		}
		return taskIdList;
	}
	
	/**
	 * 查询小贷常规、快速、展期流程(如果有)的意见与说明。add by lu 2012.04.24
	 * @param runIds
	 * @return
	 */
	public List getByRunIds(String runIds){
		String hql = "from ProcessForm pf where pf.processRun.runId in("+runIds+")"+" and pf.taskId is not null and pf.endtime is not null order by pf.createtime desc";
		return findByHql(hql.toString());
	}
	
	/**
	 * 根据权限号取得某个流程实例的所有表单(包括小贷展期流程)add by lu 2012.04.24
	 * @param runIds
	 * @param safeLevel
	 * @return
	 */
	public List getByRunId(String runIds,Long safeLevel){
		//String hql="select pf.activityName,pf.creatorName,pf.status,pf.createtime,pf.endtime,pf.durTimes,(case when pf.safeLevel >"+safeLevel+" then '您无权查看意见' else pf.comments end) as comments,pf.safeLevel from ProcessForm pf where pf.processRun.runId in("+runIds+")"+" and pf.taskId is not null and pf.endtime is not null order by pf.formId desc";
		String hql="from ProcessForm pf where pf.processRun.runId in("+runIds+")"+" and pf.taskId is not null and pf.endtime is not null order by pf.formId desc";
		return findByHql(hql.toString());
	}
	
	/**
	 * 获取某个流程的某个会签节点的数量add by lu 2012.05.24
	 * @param runId
	 * @param activityName
	 * @return
	 */
	public List<ProcessForm> getListByRunIdActivityName(Long runId,String activityName,String taskId){
		String hql = "from ProcessForm pf where pf.processRun.runId=? and pf.activityName like ? and pf.fromTaskId=? order by pf.formId desc";
		return findByHql(hql, new Object[]{runId,activityName+'%',taskId});
	}
	
	/**
	 * 根据fromTaskId获取某个流程的某个会签节点的相关信息add by lu 2012.05.25
	 * @param fromTaskId
	 * @return
	 */
	public List<ProcessForm> getListByFromTaskId(String fromTaskId){
		String hql = "from ProcessForm pf where pf.fromTaskId=? order by pf.formId desc";
		return findByHql(hql, new Object[]{fromTaskId});
	}
	@Override
	public List<ProcessForm> getByRunIdTaskIdIsNotNull(Long runId) {
		//String hql="select pf.activityName,pf.creatorName,pf.status,pf.createtime,pf.endtime,pf.durTimes,pf.comments,pf.safeLevel from ProcessForm pf where pf.processRun.runId=? and pf.endtime is not null order by pf.formId desc";
		//String hql="from ProcessForm pf where pf.processRun.runId=? and pf.endtime is not null order by pf.formId desc";
		String hql="from ProcessForm pf where pf.processRun.runId=? and pf.taskId is not null order by pf.formId desc";
		return findByHql(hql, new Object[]{runId});
	}
	@Override
	public ProcessForm getByRunIdFormTskIdIsNull(Long runId) {
		String hql="from ProcessForm pf where pf.processRun.runId=? and pf.fromTaskId is null and  pf.taskId is not null";
		ProcessForm pf=null;
		List<ProcessForm> list=findByHql(hql, new Object[]{runId});
		if(null!=list && list.size()>0){
			pf=new ProcessForm();
			pf=list.get(0);
		}
		return pf;
	}
	@Override
	public ProcessForm getProcessFormByRunIdAndActivityName(Long runId,
			Long creatorId) {
		String hql="from ProcessForm pf where pf.processRun.runId=? and pf.creatorId=?" ;
		ProcessForm pf=null;
		List<ProcessForm> list=findByHql(hql, new Object[]{runId,creatorId});
		if(null!=list && list.size()>0){
			pf=new ProcessForm();
			pf=list.get(0);
		}
		return pf;
	}
	
	/**
	 * 通过投票信息获取该节点的意见与说明(ProcessForm对象)add by lu 2012.06.06
	 * @param runId
	 * @param creatorId(该属性与processForm中的creatorId完全同步)
	 * @param fromTaskId
	 * @return
	 */
	@Override
    public ProcessForm getByRunIdFromTaskIdCreatorId(Long runId,String fromTaskId,Long creatorId){
		String hql = "from ProcessForm pf where pf.processRun.runId=? and pf.fromTaskId=? and pf.creatorId=?";
		List<ProcessForm> list = findByHql(hql, new Object[]{runId,fromTaskId,creatorId});
		if(list!=null&&list.size()!=0){
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 通过投票信息获取是否存在未投票人员add by lu 2012.06.06
	 * @param runId
	 * @param fromTaskId
	 * @return
	 */
	public int getCountUnVoteUsers(Long runId,String fromTaskId) {
		String hql = "from ProcessForm pf where pf.processRun.runId=? and pf.fromTaskId=? and pf.status=0";
		return findByHql(hql, new Object[]{runId,fromTaskId}).size();
	}
	
	/**
	 * 获取尚未执行完成的并列实例节点表单add by lisl 2012-06-09
	 * @param fromTaskId
	 * @return
	 */
	public List<ProcessForm> getByFromTaskId(String fromTaskId) {
		String hql = "from ProcessForm pf where pf.fromTaskId=? and pf.status=0";
		List<ProcessForm> list = findByHql(hql, new Object[]{fromTaskId});
		if(list.size() > 0) {
			return list;
		}
		return null;
	}
	
	/**
	 * 根据runId和对应流程节点的key获取processForm对象 add by lu 2012-07-09
	 * @param runId
	 * @param flowNodeKey
	 * @return
	 */
	public ProcessForm getByRunIdFlowNodeKey(Long runId,String flowNodeKey){
		String hql = "from ProcessForm pf where pf.processRun.runId=? and pf.taskSequenceNodeKey like '%"+flowNodeKey+"%' order by pf.formId desc";
		List<ProcessForm> list = findByHql(hql, new Object[]{runId});
		if(list!=null&&list.size()!=0){
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 根据runId和获取意见与说明记录的ProcessForm对象(要过滤类似审保会的情况：根据对应的节点的key过滤。) add by lu 2012-07-11
	 * @param runId
	 * @param flowNodeKeys 所有流程的要过滤的节点的key
	 * @return
	 */
	public List<ProcessForm> getCommentsByRunId(Long runId,String flowNodeKeys){
		//String hql = "from ProcessForm pf where pf.processRun.runId=? and pf.taskSequenceNodeKey not like '%MeettingInPublic%' and pf.taskSequenceNodeKey not like '%ExaminationArrangement%' and pf.taskId is not null and pf.endtime is not null order by formId desc";
		List<Object> params = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer();
		
		hql.append("from ProcessForm pf where pf.processRun.runId=?");
		params.add(runId);
		
		if(StringUtils.isNotEmpty(flowNodeKeys)){
			String[] proArrs = flowNodeKeys.split(",");
			hql.append(" and pf.taskSequenceNodeKey not like ? ");
			params.add("%" + proArrs[0] + "%");
			if(proArrs.length>1){
				for(int k=1;k<proArrs.length;k++){
					hql.append(" and pf.taskSequenceNodeKey not like ? ");
					params.add("%" + proArrs[k] + "%");
				}
			}
		}
		
		hql.append(" and pf.taskId is not null and pf.endtime is not null order by pf.taskSequenceNodeKey asc, pf.formId desc");
		
		return findByHql(hql.toString(),params.toArray());
	}
	
	/**
	 * 根据runId和获审保会所有记录  add by lu 2012-12-21
	 * @param runId
	 * @param flowNodeKey
	 * @return
	 */
	public List<ProcessForm> getSbhRecordsByRunIdFlowNodeKey(Long runId,String flowNodeKey){
		String hql = "from ProcessForm pf where pf.processRun.runId=? and pf.taskSequenceNodeKey like '%"+flowNodeKey+"%'";
		return findByHql(hql,new Object[]{runId});
	}
	
	@Override
	public List<ProcessForm> allProcessTask(HttpServletRequest request,
			PagingBean pb) {
		// TODO Auto-generated method stub
		String hql="SELECT " +
					  "p.formId as formId, " +
					  "run.`subject` as projectName, " +
					  "p.activityName as activityName, " +
					  "p.createtime as createtime, " +
					  "p.endtime as endtime, " +
					  "p.taskLimitTime as taskLimitTime, " +
					  "p.durtimes as durtimes, " +
					  "p.`status`as `status`, " +
					  "p.runId as processRunId, " +
					  "run.businessType as businessType, " +
					  "TIMESTAMPDIFF(SECOND ,IFNULL(p.endtime,now()),p.`taskLimitTime`) as minTime, "+
					  "IFNULL(p.creatorName,u.fullname)  as creatorName " +
					"FROM " +
						"`process_form` AS p " +
					"LEFT JOIN process_run AS run ON p.runId = run.runId " +
					"left join jbpm4_task as t on p.taskId=t.DBID_ " +
					"left join app_user as u on (t.ASSIGNEE_=u.userId) " +
					"WHERE " +
						"p.taskSequenceNodeKey != '0_start_start'";
		//查询条件
		String status=request.getParameter("status");//任务状态
		if(StringUtils.isNotEmpty(status)){
			hql+=" and p.status="+Short.valueOf(status);
		}
		
		String taskstatus=request.getParameter("taskstatus");//处理人
		if(StringUtils.isNotEmpty(taskstatus)){
			if(taskstatus.equals("1")){//正常任务
				hql+=" and TIMESTAMPDIFF(SECOND ,IFNULL(p.endtime,now()),p.`taskLimitTime`)>=0";
			}else if(taskstatus.equals("2")){//逾期任务
				hql+=" and TIMESTAMPDIFF(SECOND ,IFNULL(p.endtime,now()),p.`taskLimitTime`)<0";
			}
			
		}
		
		String userId=request.getParameter("userId");//处理人
		if(StringUtils.isNotEmpty(userId)){
			hql+=" and (p.creatorId="+Long.valueOf(userId)+" or t.ASSIGNEE_='"+userId+"')";
		}
		
		String projectName=request.getParameter("projectName");//项目名称
		if(StringUtils.isNotEmpty(projectName)){ 
			hql+=" and run.subject like '%"+projectName+"%'";
		}
		String taskName=request.getParameter("taskName");//任务名称
		if(StringUtils.isNotEmpty(taskName)){
			hql+=" and p.activityName like '%"+taskName+"%'";
		}
		String stateDate=request.getParameter("startDate");//任务分配查询开始时间
		if(StringUtils.isNotEmpty(stateDate)){
			hql+=" and p.createtime >='"+stateDate+" 00:00:00'";
		}
		
		String endDate=request.getParameter("endDate");//任务分配查询结束时间
		if(StringUtils.isNotEmpty(endDate)){
			hql+=" and p.createtime <='"+endDate+" 23:59:59'";
		}
		String finishStartDate=request.getParameter("finishStartDate");//任务完成查询开始时间
		if(StringUtils.isNotEmpty(finishStartDate)){
			hql+=" and p.endtime >='"+finishStartDate+" 00:00:00'";
		}
		
		String finishEndDate=request.getParameter("finishEndDate");//任务完成查询结束时间
		if(StringUtils.isNotEmpty(finishEndDate)){
			hql+=" and p.endtime <='"+finishEndDate+" 23:59:59'";
		}
		
		hql=hql+" order by p.createtime desc";
		System.out.println(hql);
		List listsize=this.getSession().createSQLQuery(hql).
						 addScalar("formId", Hibernate.LONG).
						 addScalar("projectName", Hibernate.STRING).
						 addScalar("activityName", Hibernate.STRING).
						 addScalar("createtime", Hibernate.TIMESTAMP).
						 addScalar("endtime", Hibernate.TIMESTAMP).
						 addScalar("taskLimitTime", Hibernate.TIMESTAMP).
						 addScalar("durtimes", Hibernate.LONG).
						 addScalar("status", Hibernate.SHORT).
						 addScalar("processRunId", Hibernate.LONG).
						 addScalar("businessType", Hibernate.STRING).
						 addScalar("minTime", Hibernate.LONG). 
						 addScalar("creatorName", Hibernate.STRING).
						 setResultTransformer(Transformers.aliasToBean(ProcessForm.class)).list();
		if(pb!=null){
			pb.setTotalItems(listsize.size());
			List list=this.getSession().createSQLQuery(hql).
			 addScalar("formId", Hibernate.LONG).
			 addScalar("projectName", Hibernate.STRING).
			addScalar("activityName", Hibernate.STRING).
			 addScalar("createtime", Hibernate.TIMESTAMP).
			 addScalar("endtime", Hibernate.TIMESTAMP).
			 addScalar("taskLimitTime", Hibernate.TIMESTAMP).
			 addScalar("durtimes", Hibernate.LONG).
			 addScalar("status", Hibernate.SHORT).
			 addScalar("processRunId", Hibernate.LONG).
			 addScalar("businessType", Hibernate.STRING).
			 addScalar("minTime", Hibernate.LONG).
			 addScalar("creatorName", Hibernate.STRING).
			 setResultTransformer(Transformers.aliasToBean(ProcessForm.class)).
			 setFirstResult(pb.getFirstResult()).
			 setMaxResults(pb.getPageSize()).
			 list();
			return  list;
		}else{
			List list=this.getSession().createSQLQuery(hql).
			 addScalar("formId", Hibernate.LONG).
			 addScalar("projectName", Hibernate.STRING).
			addScalar("activityName", Hibernate.STRING).
			 addScalar("createtime", Hibernate.TIMESTAMP).
			 addScalar("endtime", Hibernate.TIMESTAMP).
			 addScalar("taskLimitTime", Hibernate.TIMESTAMP).
			 addScalar("durtimes", Hibernate.LONG).
			 addScalar("status", Hibernate.SHORT).
			 addScalar("processRunId", Hibernate.LONG).
			 addScalar("businessType", Hibernate.STRING).
			 addScalar("minTime", Hibernate.LONG).
			 addScalar("creatorName", Hibernate.STRING).
			 setResultTransformer(Transformers.aliasToBean(ProcessForm.class)).
			 list();
			return list;
		}
	}
	@Override
	public List<ProcessForm> allCompleteProcessTask(HttpServletRequest request,
			PagingBean pb) {

		// TODO Auto-generated method stub
		String hql="SELECT " +
					  "p.formId as formId, " +
					  "run.`subject` as projectName, " +
					  "p.activityName as activityName, " +
					  "p.createtime as createtime, " +
					  "p.endtime as endtime, " +
					  "p.taskLimitTime as taskLimitTime, " +
					  "p.durtimes as durtimes, " +
					  "p.`status`as `status`, " +
					  "p.runId as processRunId, " +
					  "run.businessType as businessType, " +
					  "TIMESTAMPDIFF(SECOND ,IFNULL(p.endtime,now()),p.`taskLimitTime`) as minTime, "+
					  "IFNULL(p.creatorName,u.fullname)  as creatorName " +
					"FROM " +
						"`process_form` AS p " +
					"LEFT JOIN process_run AS run ON p.runId = run.runId " +
					"left join jbpm4_task as t on p.taskId=t.DBID_ " +
					"left join app_user as u on (t.ASSIGNEE_=u.userId) " +
					"WHERE " +
						"p.taskSequenceNodeKey != '0_start_start' and p.status!=0";
		//查询条件
		
		String taskstatus=request.getParameter("taskstatus");//异常状态
		if(StringUtils.isNotEmpty(taskstatus)){
			if(taskstatus.equals("1")){//正常任务
				hql+=" and TIMESTAMPDIFF(SECOND ,IFNULL(p.endtime,now()),p.`taskLimitTime`)>=0";
			}else if(taskstatus.equals("2")){//逾期任务
				hql+=" and TIMESTAMPDIFF(SECOND ,IFNULL(p.endtime,now()),p.`taskLimitTime`)<0";
			}
			
		}
		String userId=request.getParameter("userId");//处理人
		if(StringUtils.isNotEmpty(userId)){
			hql+=" and (p.creatorId="+Long.valueOf(userId)+" or t.ASSIGNEE_='"+userId+"')";
		}
		String projectName=request.getParameter("projectName");//项目名称
		if(StringUtils.isNotEmpty(projectName)){ 
			hql+=" and run.subject like '%"+projectName+"%'";
		}
		String taskName=request.getParameter("taskName");//任务名称
		if(StringUtils.isNotEmpty(taskName)){
			hql+=" and p.activityName like '%"+taskName+"%'";
		}
		String stateDate=request.getParameter("startDate");//任务分配查询开始时间
		if(StringUtils.isNotEmpty(stateDate)){
			hql+=" and p.createtime >='"+stateDate+" 00:00:00'";
		}
		
		String endDate=request.getParameter("endDate");//任务分配查询结束时间
		if(StringUtils.isNotEmpty(endDate)){
			hql+=" and p.createtime <='"+endDate+" 23:59:59'";
		}
		String finishStartDate=request.getParameter("finishStartDate");//任务完成查询开始时间
		if(StringUtils.isNotEmpty(finishStartDate)){
			hql+=" and p.endtime >='"+finishStartDate+" 00:00:00'";
		}
		
		String finishEndDate=request.getParameter("finishEndDate");//任务完成查询结束时间
		if(StringUtils.isNotEmpty(finishEndDate)){
			hql+=" and p.endtime <='"+finishEndDate+" 23:59:59'";
		}
		hql=hql+" order by p.createtime desc";
		System.out.println(hql);
		List listsize=this.getSession().createSQLQuery(hql).
						 addScalar("formId", Hibernate.LONG).
						 addScalar("projectName", Hibernate.STRING).
						 addScalar("activityName", Hibernate.STRING).
						 addScalar("createtime", Hibernate.TIMESTAMP).
						 addScalar("endtime", Hibernate.TIMESTAMP).
						 addScalar("taskLimitTime", Hibernate.TIMESTAMP).
						 addScalar("durtimes", Hibernate.LONG).
						 addScalar("status", Hibernate.SHORT).
						 addScalar("processRunId", Hibernate.LONG).
						 addScalar("businessType", Hibernate.STRING).
						 addScalar("minTime", Hibernate.LONG). 
						 addScalar("creatorName", Hibernate.STRING).
						 setResultTransformer(Transformers.aliasToBean(ProcessForm.class)).list();
		if(pb!=null){
			pb.setTotalItems(listsize.size());
			List list=this.getSession().createSQLQuery(hql).
			 addScalar("formId", Hibernate.LONG).
			 addScalar("projectName", Hibernate.STRING).
			addScalar("activityName", Hibernate.STRING).
			 addScalar("createtime", Hibernate.TIMESTAMP).
			 addScalar("endtime", Hibernate.TIMESTAMP).
			 addScalar("taskLimitTime", Hibernate.TIMESTAMP).
			 addScalar("durtimes", Hibernate.LONG).
			 addScalar("status", Hibernate.SHORT).
			 addScalar("processRunId", Hibernate.LONG).
			 addScalar("businessType", Hibernate.STRING).
			 addScalar("minTime", Hibernate.LONG).
			 addScalar("creatorName", Hibernate.STRING).
			 setResultTransformer(Transformers.aliasToBean(ProcessForm.class)).
			 setFirstResult(pb.getFirstResult()).
			 setMaxResults(pb.getPageSize()).
			 list();
			return  list;
		}else{
			List list=this.getSession().createSQLQuery(hql).
			 addScalar("formId", Hibernate.LONG).
			 addScalar("projectName", Hibernate.STRING).
			addScalar("activityName", Hibernate.STRING).
			 addScalar("createtime", Hibernate.TIMESTAMP).
			 addScalar("endtime", Hibernate.TIMESTAMP).
			 addScalar("taskLimitTime", Hibernate.TIMESTAMP).
			 addScalar("durtimes", Hibernate.LONG).
			 addScalar("status", Hibernate.SHORT).
			 addScalar("processRunId", Hibernate.LONG).
			 addScalar("businessType", Hibernate.STRING).
			 addScalar("minTime", Hibernate.LONG).
			 addScalar("creatorName", Hibernate.STRING).
			 setResultTransformer(Transformers.aliasToBean(ProcessForm.class)).
			 list();
			return list;
		}
	
	}
	@Override
	public List<Long> getByRunIdAllCreator(Long runId) {
		// TODO Auto-generated method stub
		String hql="select distinct pf.creatorId from ProcessForm as pf where pf.processRun.runId="+runId+" ";
		return this.getHibernateTemplate().find(hql);
	}
	
	
}