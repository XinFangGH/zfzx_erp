package com.zhiwei.credit.dao.flow;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jbpm.api.Execution;
import org.jbpm.pvm.internal.model.ExecutionImpl;
import org.jbpm.pvm.internal.task.TaskImpl;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.core.jbpm.pv.TaskInfo;
import com.zhiwei.core.web.paging.PageBean;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.flow.JbpmTask;

public interface TaskDao extends BaseDao<TaskImpl>{
	
	public List<TaskImpl> getTasksByUserId(String userId,PagingBean pb);
	
	/**
	 * 取得所有任务
	 * @param taskName
	 * @param pb
	 * @return
	 */
	public List<TaskImpl> getAllTasks(String taskName,PagingBean pb);
	
	/**
	 * 通过活动名称及参数Key取得某任务列表
	 * @param activityName
	 * @param varKey
	 * @return
	 */
	public List<JbpmTask> getByActivityNameVarKeyLongVal(String activityName,String varKey,Long value);
	/**
	 * 通过任务ID来查找任务人员组的角色ID
	 * @param taskId
	 * @return
	 */
	public List<Long> getGroupByTask(Long taskId);
	/**
	 * 通过任务的ID来查找子任务的执行人员ID
	 * @param taskId
	 * @return
	 */
	public List<Long> getUserIdByTask(Long taskId);
	
	/**
	 * 取得某个用户候选的任务列表
	 * @param userId
	 * @param pb
	 * @return
	 */
	public List<TaskImpl> getCandidateTasks(String userId,PagingBean pb);
	
	/**
	 * 查找个人归属任务，不包括其角色归属的任务
	 * @param userId
	 * @param pb
	 * @return
	 */
	public List<TaskImpl> getPersonTasks(String userId,PagingBean pb);
	
	/**
	 * 按主键查找execution实体
	 * @param dbid
	 * @return
	 */
	public Execution getExecutionByDbid(Long dbid);
	
	/**
	 * 保存executionimpl
	 * @param executionImpl
	 */
	public void save(ExecutionImpl executionImpl);
	
	/**
	 * 去掉某个execution的子execution及其相关联的记录
	 * @param parentDbid
	 */
	public void removeExeByParentId(Long parentDbid);
	
	/**
	 * 会签节点：根据同样名称和同样的创建时间获取父任务信息,为了获取ExecutionId(没有提供获取SUPERTASK_的方法和该属性)。
	 * @param activityName
	 * @param createTime
	 * add by lu 2011.12.14
	 */
	public List<TaskImpl> getTaskByNameAndCreateTime(String activityName,Date createTime);
	
	/**
	 * 删除任务：根据executionId查询类似并列任务、会签任务的子任务一并删除
	 * @param executionId 等同于process_run中的piId
	 * add by lu 2012.02.21
	 */
	public List<TaskImpl> getTaskByExecutionId(String executionId);
	
	/**
	 * 取得用户的对应的某个流程定义的任务列表
	 * @param userId
	 * @param processName
	 * @param PagingBean
	 * @return
	 * add by lu 2012.03.01
	 */
	public List<TaskImpl> getTasksByUserIdProcessName(String userId,String processName,PagingBean pb,String projectName,String projectNumber,String piId);
	
	/**
	 * 项目信息库-贷前项目列表-获取会签、同步任务
	 * @param piDbid
	 * @return
	 * add by lu 2012.03.28
	 */
	public List<TaskImpl> getSynchronousTasksByRunId(Long piDbid);
	
	/**
	 * 获取当前流程实例的所有任务。需要根据创建时间排序
	 * @param piId
	 * @return
	 * add by lu 2012.05.02
	 */
	//public List<TaskImpl> getTasksByPiId(String piId);
	
	/**
	 * 根据流程对应的key、节点名称查询所有任务。
	 * @param processName
	 * @param activityName
	 * @return
	 * add by lu 2012.08.13
	 */
	public List<TaskImpl> getTasksByProcessNameActivityName(String processName,String activityName,PagingBean pb,String projectName,String projectNumber,String customerName);
	
	/**
	 * 根据流程对应的key、节点名称、节点处理人Id查询所有任务。
	 * @param processName
	 * @param activityName
	 * @param userId
	 * @return
	 * add by lu 2012.08.13
	 */
	public List<TaskImpl> getTasksByProcessNameActivityNameUserId(String processName,String activityName,String userId,PagingBean pb,String projectName,String projectNumber,String customerName);
	
	/**
	 * 根据流程的key和用户id获取相应的任务列表
	 * @param userId
	 * @param processName
	 * @return
	 * add by lu 2012.08.17
	 */
	public int getAllByUserIdProcessName(String userId,String processName);

	public List<TaskImpl> getTaskByExecutionId(String piId, String state);
	/**
	 * 带有request条件查询出来当前应该执行的认为
	 * @param userId
	 * @param projectName
	 * @param taskName
	 * @param pb
	 * @return
	 */
	public List<TaskImpl> getCurrentTaskByParameter(HttpServletRequest request, PagingBean pb);

	public void findTaskByName(Map<String,String> map,PageBean<TaskInfo> pageBean);
	

	/**
	 * 取得用户的对应的某个流程定义的任务列表
	 * @param userId
	 * @return
	 */
	public List<TaskImpl> getTasksByUserIdProcessName(String userId,String processName,PagingBean pb);
}
