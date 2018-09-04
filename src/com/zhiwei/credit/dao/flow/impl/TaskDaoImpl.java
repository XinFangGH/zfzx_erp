package com.zhiwei.credit.dao.flow.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.jbpm.api.Execution;
import org.jbpm.pvm.internal.model.ExecutionImpl;
import org.jbpm.pvm.internal.task.TaskImpl;
import org.springframework.jdbc.core.RowMapper;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.jbpm.pv.TaskInfo;
import com.zhiwei.core.web.paging.PageBean;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.flow.TaskDao;
import com.zhiwei.credit.model.flow.JbpmTask;
import com.zhiwei.credit.model.system.AppRole;
import com.zhiwei.credit.model.system.AppUser;

@SuppressWarnings("unchecked")
public class TaskDaoImpl extends BaseDaoImpl<TaskImpl> implements TaskDao{

	public TaskDaoImpl() {
		super(TaskImpl.class);
	}
	
	/**
	 * 查找个人归属任务，不包括其角色归属的任务
	 * @param userId
	 * @param pb
	 * @return
	 */
	public List<TaskImpl> getPersonTasks(String userId,PagingBean pb){
		
		StringBuffer hqlSb=new StringBuffer();
		hqlSb.append("select task from org.jbpm.pvm.internal.task.TaskImpl task  where task.assignee=?");
		hqlSb.append(" order by task.createTime desc");
		
		return findByHql(hqlSb.toString(),new Object[]{userId}, pb);
	}
	/*
	 * @param pb
	 * @return
	 */
	public List<TaskImpl> getAllTasks(String taskName,PagingBean pb){
		List params=new ArrayList();
		String hql="from org.jbpm.pvm.internal.task.TaskImpl task where 1=1 and task.state ='open' and task.duedate!=null";
		//String hql="from org.jbpm.pvm.internal.task.TaskImpl task where 1=1 and task.state ='open' and task.assignee!=null and task.duedate!=null";
		//流程任务管理,任务名称通过组合得到,而不仅仅是jbpm4_task的任务名称,taskName查询条件不成立。页面索引的时候得到全部数据再进行过滤。update by lu 2012.04.28
		if(StringUtils.isNotEmpty(taskName)){
			hql+=" and task.processRun.subject like ?";
			params.add("%"+taskName+"%");
		}
		hql+=" order by task.createTime desc";
		return findByHql(hql,params.toArray(),pb);
	}
	
	/**
	 * 取得某个用户候选的任务列表
	 * @param userId
	 * @param pb
	 * @return
	 */
	public List<TaskImpl> getCandidateTasks(String userId,PagingBean pb){
		AppUser user=(AppUser)getHibernateTemplate().load(AppUser.class, new Long(userId));
		Iterator<AppRole> rolesIt=user.getRoles().iterator();
		StringBuffer groupIds=new StringBuffer();
		int i=0;
		while(rolesIt.hasNext()){
			if(i++>0)groupIds.append(",");
			groupIds.append("'"+rolesIt.next().getRoleId().toString()+"'");
		}
		StringBuffer hqlSb=new StringBuffer();
		hqlSb.append("select distinct task from org.jbpm.pvm.internal.task.TaskImpl task left join task.participations pt ");
		hqlSb.append(" where task.assignee is null and pt.type = 'candidate' and ( pt.userId=? ");
		
		if(user.getRoles().size()>0){
			hqlSb.append(" or pt.groupId in ("+groupIds.toString()+")");
		}
		hqlSb.append(")");
		hqlSb.append(" order by task.createTime desc");
		
		return findByHql(hqlSb.toString(), new Object[]{userId,userId},pb);
	}
	
	/**
	 * 取得用户的对应的任务列表
	 * @param userId
	 * @return
	 */
	public List<TaskImpl> getTasksByUserId(String userId,PagingBean pb){
		AppUser user=(AppUser)getHibernateTemplate().load(AppUser.class, new Long(userId));
		Iterator<AppRole> rolesIt=user.getRoles().iterator();
		StringBuffer groupIds=new StringBuffer();
		int i=0;
		while(rolesIt.hasNext()){
			if(i++>0)groupIds.append(",");
			groupIds.append("'"+rolesIt.next().getRoleId().toString()+"'");
		}
		StringBuffer hqlSb=new StringBuffer();
		hqlSb.append("select distinct task from org.jbpm.pvm.internal.task.TaskImpl task left join task.participations pt where task.assignee=? and task.state ='open'");
		hqlSb.append(" or ( task.assignee is null and pt.type = 'candidate' and  ( pt.userId = ? ");
		
		if(user.getRoles().size()>0){
			hqlSb.append(" or pt.groupId in ("+groupIds.toString()+")");
		}
		hqlSb.append("))");
		hqlSb.append(" order by task.createTime desc");

		return findByHql(hqlSb.toString(), new Object[]{userId,userId},pb);
		
	}
	
	/**
	 * 通过活动名称及参数Key取得某任务列表
	 * @param activityName
	 * @param varKey
	 * @return
	 */
	public List<JbpmTask> getByActivityNameVarKeyLongVal(String activityName,String varKey,Long value){
		String sql="select task.DBID_ taskId, task.ASSIGNEE_ assignee from jbpm4_task task join jbpm4_variable var on task.EXECUTION_=var.EXECUTION_ where  task.ACTIVITY_NAME_=? and var.KEY_=? and var.LONG_VALUE_=?";//task.ASSIGNEE_ is not null and
		Collection<JbpmTask> jbpmtask =(Collection) this.jdbcTemplate.query(sql,new Object[]{activityName,varKey,value},
				new RowMapper() {
					@Override
					public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
						JbpmTask task=new JbpmTask();
						Long taskId=rs.getLong("taskId");
						String assignee=rs.getString("assignee");
						task.setAssignee(assignee);
						task.setTaskId(taskId);
						return task;
					}
				}
		);
		return new ArrayList(jbpmtask);
	}
	@Override
	public List<Long> getGroupByTask(Long taskId) {
		String sql="select pa.GROUPID_ groupId from jbpm4_participation pa  where pa.TYPE_ = 'candidate'and pa.TASK_=?";
		Collection<String> groupIds =(Collection) this.jdbcTemplate.query(sql,new Object[]{taskId},
				new RowMapper() {
					@Override
					public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
						String groupId=rs.getString("groupId");
						return groupId;
					}
				}
		);
		return new ArrayList(groupIds);
	}
	@Override
	public List<Long> getUserIdByTask(Long taskId) {
		String hql="from org.jbpm.pvm.internal.task.TaskImpl task where task.superTask.id=?";
		Object[] objs={taskId};
		List<TaskImpl> taskList=findByHql(hql, objs);
		List<Long> list=new ArrayList<Long>();
		for(TaskImpl task:taskList){
			list.add(new Long(task.getAssignee()));
		}
		return list;
	}
	
	/**
	 * 去掉某个execution的子execution及其相关联的记录
	 * @param parentDbid
	 */
	public void removeExeByParentId(Long parentDbid){
		
		//String delVarHql="delete from Variable var where var.execution.dbid=? ";
		//String delExeHql="delete from ExecutionImpl exi where exi.parent.dbid=? ";////////////////
		String delExeHql="delete from ExecutionImpl exi where exi.parent.dbid=? and exi.state=?";
		
		//update(delVarHql,parentDbid);
		//update(delExeHql,parentDbid);////////////////////////////
		update(delExeHql,parentDbid,Execution.STATE_ACTIVE_CONCURRENT);
	}
	/**
	 * 按主键查找execution实体
	 * @param dbid
	 * @return
	 */
	public Execution getExecutionByDbid(Long dbid){
		String hql="from ExecutionImpl exi where exi.dbid=?";
		return(Execution)findUnique(hql,new Object[]{dbid});
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.zhiwei.credit.dao.flow.TaskDao#save(org.jbpm.pvm.internal.model.ExecutionImpl)
	 */
	public void save(ExecutionImpl executionImpl){
		 getHibernateTemplate().save(executionImpl);
	}
	
	/**
	 * 会签节点：根据同样名称和同样的创建时间获取父任务信息,为了获取ExecutionId(没有提供获取SUPERTASK_的方法和该属性)。
	 * @param activityName
	 * @param createTime
	 * add by lu 2011.12.14
	 */
	public List<TaskImpl> getTaskByNameAndCreateTime(String activityName,Date createTime){
		String hql = "from org.jbpm.pvm.internal.task.TaskImpl task where task.activityName=? and task.createTime=? and task.assignee is null";
		return findByHql(hql, new Object[]{activityName,createTime});
	}
	
	/**
	 * 删除任务：根据executionId查询类似并列任务、会签任务的子任务一并删除
	 * @param executionId 等同于process_run中的piId
	 * add by lu 2012.02.21
	 */
	public List<TaskImpl> getTaskByExecutionId(String executionId){
		String hql = "from org.jbpm.pvm.internal.task.TaskImpl task where task.executionId like ?";
		return findByHql(hql, new Object[]{executionId+'%'});
	}
	
	/**
	 * 取得用户的对应的某个流程定义的任务列表
	 * @param userId
	 * @param processName
	 * @param PagingBean
	 * @return
	 * add by lu 2012.03.01
	 */
	public List<TaskImpl> getTasksByUserIdProcessName(String userId,String processName,PagingBean pb,String projectName,String projectNumber,String piId){
		List<Object> params=new ArrayList<Object>();
		AppUser user=(AppUser)getHibernateTemplate().load(AppUser.class, new Long(userId));
		Iterator<AppRole> rolesIt=user.getRoles().iterator();
		StringBuffer groupIds=new StringBuffer();
		int i=0;
		while(rolesIt.hasNext()){
			if(i++>0)groupIds.append(",");
			groupIds.append("'"+rolesIt.next().getRoleId().toString()+"'");
		}
		StringBuffer hqlSb=new StringBuffer();
		hqlSb.append("select distinct task from org.jbpm.pvm.internal.task.TaskImpl task left join task.participations pt where task.assignee=? and task.state ='open'");
		params.add(userId);
		if(StringUtils.isNotEmpty(processName)&&!"ALL".equals(processName)){
			String[] proArrs = processName.split(",");
			hqlSb.append(" and (task.processRun.processName like ? ");
			params.add("%" + proArrs[0] + "%");
			if(proArrs.length>1){
				int l=proArrs.length;
				for(int k=1;k<l;k++){
					hqlSb.append(" or task.processRun.processName like ? ");
					params.add("%" + proArrs[k] + "%");
					if(k==proArrs.length-1){
						hqlSb.append(")");
					}
				}
			}else{
				hqlSb.append(")");
			}
		}
		
		if(piId!=null&&!"".equals(piId)&&!"null".equals(piId)){
			hqlSb.append(" and (task.processRun.piId like ?) ");
			params.add("%" + piId + "%");
		}
		
		if(projectName!=null&&!"".equals(projectName)&&!"null".equals(projectName)){
			hqlSb.append(" and (task.processRun.subject like ?) ");
			params.add("%" + projectName + "%");
		}
		
		if(projectNumber!=null&&!"".equals(projectNumber)&&!"null".equals(projectNumber)){
			hqlSb.append(" and (task.processRun.projectNumber like ?) ");
			params.add("%" + projectNumber + "%");
		}
		
		hqlSb.append(" or ( task.assignee is null and pt.type = 'candidate' and  ( pt.userId = ? ");
		
		
		params.add(userId);
		
		if(user.getRoles().size()>0){
			hqlSb.append(" or pt.groupId in ("+groupIds.toString()+")");
		}
		
		//hqlSb.append(" and task.state ='open'");
		
		hqlSb.append("))");
		
		hqlSb.append(" order by task.createTime desc");

		return findByHql(hqlSb.toString(),params.toArray(),pb);
		
	}

	/**
	 * 项目信息库-贷前项目列表-获取会签、同步任务
	 * @param piDbid
	 * @return
	 * add by lu 2012.03.28
	 */
	public List<TaskImpl> getSynchronousTasksByRunId(Long piDbid){
		String hql = "select distinct task from org.jbpm.pvm.internal.task.TaskImpl task where task.processRun.piDbid=? order by task.createTime desc";
		return findByHql(hql, new Object[]{piDbid});
	}
	
	/**
	 * 获取当前流程实例的所有任务。需要根据创建时间排序
	 * @param piId
	 * @return
	 * add by lu 2012.05.02
	 */
	/*public List<TaskImpl> getTasksByPiId(String piId){
		String hql = "select distinct task from org.jbpm.pvm.internal.task.TaskImpl task where task.executionId like '%"+piId+"%' order by task.createTime desc";
		return findByHql(hql);
	}*/
	
	/**
	 * 根据流程对应的key、节点名称查询所有任务。
	 * @param processName
	 * @param activityName
	 * @return
	 * add by lu 2012.08.13
	 */
	public List<TaskImpl> getTasksByProcessNameActivityName(String processName,String activityName,PagingBean pb,String projectName,String projectNumber,String customerName){

		List<Object> params=new ArrayList<Object>();
		StringBuffer hql=new StringBuffer();
		hql.append("select distinct task from org.jbpm.pvm.internal.task.TaskImpl task where task.state ='open'");
		if(StringUtils.isNotEmpty(processName)){
			hql.append(" and (task.processRun.processName like ? ");
			params.add("%" + processName + "%");
		}
		if(projectName!=null&&!"".equals(projectName)&&!"null".equals(projectName)){
			hql.append(" and (task.processRun.subject like ?) ");
			params.add("%" + projectName + "%");
		}
		
		if(projectNumber!=null&&!"".equals(projectNumber)&&!"null".equals(projectNumber)){
			hql.append(" and (task.processRun.projectNumber like ?) ");
			params.add("%" + projectNumber + "%");
		}
		
		if(customerName!=null && !"".equals(customerName) && !"null".equals(customerName)){
			hql.append(" and (task.processRun.customerName like ?) ");
			params.add("%" + customerName + "%");
		}
		hql.append(")");
		
		hql.append(" and task.activityName like ?");
		params.add("%" + activityName + "%");
		
		hql.append(" order by task.createTime desc");

		return findByHql(hql.toString(),params.toArray(),pb);
	}
	
	/**
	 * 根据流程对应的key、节点名称、节点处理人Id查询所有任务。
	 * @param processName
	 * @param activityName
	 * @param userId
	 * @return
	 * add by lu 2012.08.13
	 */
	public List<TaskImpl> getTasksByProcessNameActivityNameUserId(String processName,String activityName,String userId,PagingBean pb,String projectName,String projectNumber,String customerName){

		List<Object> params=new ArrayList<Object>();
		AppUser user=(AppUser)getHibernateTemplate().load(AppUser.class, new Long(userId));
		Iterator<AppRole> rolesIt=user.getRoles().iterator();
		StringBuffer groupIds=new StringBuffer();
		int i=0;
		while(rolesIt.hasNext()){
			if(i++>0)groupIds.append(",");
			groupIds.append("'"+rolesIt.next().getRoleId().toString()+"'");
		}
		StringBuffer hqlSb=new StringBuffer();
		hqlSb.append("select distinct task from org.jbpm.pvm.internal.task.TaskImpl task left join task.participations pt where task.assignee=? and task.state ='open'");
		params.add(userId);
		if(StringUtils.isNotEmpty(processName)){
			hqlSb.append(" and (task.processRun.processName like ? ");
			params.add("%" + processName + "%");
		}
		if(projectName!=null&&!"".equals(projectName)&&!"null".equals(projectName)){
			hqlSb.append(" and (task.processRun.subject like ?) ");
			params.add("%" + projectName + "%");
		}
		
		if(projectNumber!=null&&!"".equals(projectNumber)&&!"null".equals(projectNumber)){
			hqlSb.append(" and (task.processRun.projectNumber like ?) ");
			params.add("%" + projectNumber + "%");
		}
		
		if(customerName!=null && !"".equals(customerName) && !"null".equals(customerName)){
			hqlSb.append(" and (task.processRun.customerName like ?) ");
			params.add("%" + customerName + "%");
		}
		hqlSb.append(")");
		
		hqlSb.append(" and task.activityName like ?");
		params.add("%" + activityName + "%");
		
		hqlSb.append(" or ( task.assignee is null and pt.type = 'candidate' and  ( pt.userId = ? ");
		
		params.add(userId);
		
		if(user.getRoles().size()>0){
			hqlSb.append(" or pt.groupId in ("+groupIds.toString()+")");
		}
		
		hqlSb.append("))");
		
		hqlSb.append(" order by task.createTime desc");

		return findByHql(hqlSb.toString(),params.toArray(),pb);
	}
	
	/**
	 * 根据流程的key和用户id获取相应的任务总数
	 * @param userId
	 * @param processName
	 * @return
	 * add by lu 2012.08.17
	 */
	public int getAllByUserIdProcessName(String userId,String processName){
		List<Object> params=new ArrayList<Object>();
		AppUser user=(AppUser)getHibernateTemplate().load(AppUser.class, new Long(userId));
		Iterator<AppRole> rolesIt=user.getRoles().iterator();
		StringBuffer groupIds=new StringBuffer();
		int i=0;
		while(rolesIt.hasNext()){
			if(i++>0)groupIds.append(",");
			groupIds.append("'"+rolesIt.next().getRoleId().toString()+"'");
		}
		StringBuffer hqlSb=new StringBuffer();
		hqlSb.append("select count(DISTINCT  task.dbid) from org.jbpm.pvm.internal.task.TaskImpl task left join task.participations pt where task.assignee=? and task.state ='open'");
		params.add(userId);
		if(StringUtils.isNotEmpty(processName)&&!"ALL".equals(processName)){
			String[] proArrs = processName.split(",");
			hqlSb.append(" and (task.processRun.processName like ? ");
			params.add("%" + proArrs[0] + "%");
			if(proArrs.length>1){
				for(int k=1;k<proArrs.length;k++){
					hqlSb.append(" or task.processRun.processName like ? ");
					params.add("%" + proArrs[k] + "%");
					if(k==proArrs.length-1){
						hqlSb.append(")");
					}
				}
			}else{
				hqlSb.append(")");
			}
		}
		
		hqlSb.append(" or ( task.assignee is null and pt.type = 'candidate' and  ( pt.userId = ? ");
		
		params.add(userId);
		
		if(user.getRoles().size()>0){
			hqlSb.append(" or pt.groupId in ("+groupIds.toString()+")");
		}
		
		hqlSb.append("))");
		
		hqlSb.append(" order by task.createTime desc");
		//设定数据库开始---
		//ChangeDataSourceUtil.setDataSorce();
		//设定数据库结束---
		Query q = this.getSession().createQuery(hqlSb.toString());
		Object[] objs=params.toArray();
		if(objs!=null){
			for(int j=0;j<objs.length;j++){
				q.setParameter(j,objs[j]);
			}
		}
		Long size=(Long)(q.iterate().next());

		return size.intValue();
	}

	/**
	 * 删除任务：根据executionId查询类似并列任务提交只挂起的任务
	 * @param executionId 等同于process_run中的piId
	 * add by zcb 2014.03.29
	 */
	public List<TaskImpl> getTaskByExecutionId(String executionId,String state){
		String hql = "from org.jbpm.pvm.internal.task.TaskImpl task where task.executionId like ? and task.state=?";
		return findByHql(hql, new Object[]{executionId+'%',state});
	}

	@Override
	public List<TaskImpl> getCurrentTaskByParameter(HttpServletRequest request, PagingBean pb) {
		List params=new ArrayList();
		String hql="from org.jbpm.pvm.internal.task.TaskImpl task where task.state ='open' and task.duedate!=null";
		//String hql="from org.jbpm.pvm.internal.task.TaskImpl task where 1=1 and task.state ='open' and task.assignee!=null and task.duedate!=null";
		//流程任务管理,任务名称通过组合得到,而不仅仅是jbpm4_task的任务名称,taskName查询条件不成立。页面索引的时候得到全部数据再进行过滤。update by lu 2012.04.28
		
		String userId=request.getParameter("userId");//处理人
		if(StringUtils.isNotEmpty(userId)){
			hql+=" and task.assignee=?";
			params.add(userId);
		}
		
		String projectName=request.getParameter("projectName");//项目名称
		if(StringUtils.isNotEmpty(projectName)){
			hql+=" and task.processRun.subject like ?";
			params.add("%"+projectName+"%");
		}
		String taskName=request.getParameter("taskName");//任务名称
		if(StringUtils.isNotEmpty(taskName)){
			hql+=" and task.activityName like ?";
			params.add("%"+taskName+"%");
		}
		String stateDate=request.getParameter("startDate");//任务分配查询开始时间
		if(StringUtils.isNotEmpty(stateDate)){
			hql+=" and task.createTime >='"+stateDate+" 00:00:00'";
		}
		
		String endDate=request.getParameter("endDate");//任务分配查询结束时间
		if(StringUtils.isNotEmpty(endDate)){
			hql+=" and task.createTime <='"+endDate+" 59:59:59'";
		}
		hql+=" order by task.createTime desc";
		System.out.println("==============="+hql);
		return findByHql(hql,params.toArray(),pb);
	}

	@Override
	public void findTaskByName(Map<String,String> map,PageBean<TaskInfo> pageBean) {
		String type=map.get("type");
		String flowType=map.get("flowType");
		String resource=map.get("resource");
		String busType=map.get("busType");
		String taskSequence=map.get("taskSequence");
		
		StringBuffer hql=new StringBuffer(" select DISTINCT " +
				" task.DBID_ as taskId," +
				" pr.subject as taskName," +
				" task.ACTIVITY_NAME_ as activityName," +
				" au.fullname as assignee," +
				" task.CREATE_ as createTime," +
				" task.DUEDATE_ as dueDate" +
				" from  jbpm4_task as task " +
				" LEFT JOIN process_run as pr on task.EXECUTION_=pr.piDbid" +
				" LEFT JOIN pro_user_assign as pu ON pu.activityName = task.NAME_" +
				" LEFT JOIN sl_smallloan_project as p on p.projectId=pr.projectId" +
				" LEFT JOIN app_user as au on au.userId=task.ASSIGNEE_");
		if(null!=resource && "credit".equals(resource) && "dir".equals(busType) /*&& "person_customer".equals(type)*/){
			hql.append(" left JOIN bp_persion_dir_pro as bDir on bDir.proId=p.projectId and bDir.loanId is not null");
		}else if(null!=resource && "underline".equals(resource) && "dir".equals(busType) /*&& "person_ourmain".equals(type)*/){
			hql.append(" left JOIN bp_persion_dir_pro as bDir on bDir.proId=p.projectId and bDir.loanId is  null");
		}
		hql.append(" where task.EXECUTION_ID_ like '"+flowType+"%'" +
				" AND task.STATE_ = 'open'"+
				" AND task.DUEDATE_ is NOT NULL" +
				" AND pu.taskSequence = '"+taskSequence+"'" +
				" AND p.oppositeType = '"+type+"'");
		if("dir".equals(busType) && "person_customer".equals(type)){
			hql.append(" AND bDir.proId is not NULL ");
		}
		StringBuffer totalCounts = new StringBuffer("select count(*) from (");
		totalCounts.append(hql);
		totalCounts.append(") as a");
		hql.append(" ORDER BY task.CREATE_ DESC");
		List list = this.getSession().createSQLQuery(hql.toString())
			.addScalar("taskId",Hibernate.LONG)
			.addScalar("taskName", Hibernate.STRING)
			.addScalar("activityName", Hibernate.STRING)
			.addScalar("assignee", Hibernate.STRING)
			.addScalar("createTime", Hibernate.DATE)
			.addScalar("dueDate", Hibernate.DATE)
			.setResultTransformer(Transformers.aliasToBean(TaskInfo.class)).setFirstResult(pageBean.getStart()).setMaxResults(pageBean.getLimit()).list();
		pageBean.setResult(list);
		
		BigInteger total = (BigInteger) this.getSession().createSQLQuery(totalCounts.toString()).uniqueResult();
		pageBean.setTotalCounts(total.intValue());
	}
	
	/**
	 * 取得用户的对应的某个流程定义的任务列表
	 * @param userId
	 * @return
	 */
	public List<TaskImpl> getTasksByUserIdProcessName(String userId,String subject,PagingBean pb){
		List<Object> params=new ArrayList<Object>();
		AppUser user=(AppUser)getHibernateTemplate().load(AppUser.class, new Long(userId));
		Iterator<AppRole> rolesIt=user.getRoles().iterator();
		StringBuffer groupIds=new StringBuffer();
		int i=0;
		while(rolesIt.hasNext()){
			if(i++>0)groupIds.append(",");
			groupIds.append("'"+rolesIt.next().getRoleId().toString()+"'");
		}
		StringBuffer hqlSb=new StringBuffer();
		hqlSb.append("select distinct task from org.jbpm.pvm.internal.task.TaskImpl task left join task.participations pt where task.assignee=? and task.state='open'");

		params.add(userId);
		if(StringUtils.isNotEmpty(subject)){
			hqlSb.append(" and (task.processRun.subject like ? or task.name like ?)");
			params.add("%" + subject + "%");
			params.add("%" + subject + "%");
		}
		hqlSb.append(" or ( task.assignee is null and pt.type = 'candidate' and  ( pt.userId = ? ");
		params.add(userId);
		if(user.getRoles().size()>0){
			hqlSb.append(" or pt.groupId in ("+groupIds.toString()+")");
		}
		hqlSb.append("))");
		
		hqlSb.append(" order by task.createTime desc");
		if(pb==null){
			return findByHql(hqlSb.toString(),params.toArray());
		}else{
			return findByHql(hqlSb.toString(),params.toArray(),pb);
		}
		
	}
}
