package com.zhiwei.credit.service.flow;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.flow.ProcessForm;
/**
 * 流程表单服务类
 * @author csx
 *
 */
public interface ProcessFormService extends BaseService<ProcessForm>{
	/**
	 * 取得某个流程实例的所有表单不包含无taskId
	 */
	
    public  List<ProcessForm> getByRunIdTaskIdIsNotNull(Long runId);	
    
	/**
	 * 取得某个流程实例对象 无FormTskId有taskId;
	 */
	
    public  ProcessForm getByRunIdFormTskIdIsNull(Long runId);	
	
	/**
	 * 取得某个流程实例的所有表单
	 * @param runId
	 * @return
	 */
	public List getByRunId(Long runId);
	
	/**
	 * 根据权限号取得某个流程实例的所有表单
	 * @param runId
	 * @return
	 */
	public List getByRunId(Long runId,Long safeLevel);
	
	/**
	 * 查找某个流程某个任务的表单数据
	 * @param runId
	 * @param activityName
	 * @return
	 */
	public ProcessForm getByRunIdActivityName(Long runId,String activityName);
	
	/**
	 * 取得某一流程某一任务已经执行的次数，如某一任务被不断驳回，就会被执行多次。
	 * @return
	 */
	public Long getActvityExeTimes(Long runId,String activityName);
	
//	/**
//	 * 构造最新的流程实例对应的所有字段及数据
//	 * @param runId
//	 * @return
//	 */
//	public Map getVariables(Long runId);
	
	/**
	 * 初始一个未持久化的历史 
	 * @return
	 */
	public ProcessForm getInitProcessForm();
	
	/**
	 * 取得某个任务其对应的流程表单信息（流程历史表单）
	 * @param taskId
	 * @return
	 */
	public ProcessForm getByTaskId(String taskId);
	
	/**
	 * 按runId及TaskName取到某个ProcessForm，目前使用的目的是为了取到某个流程开始的历史表单信息
	 * @param runId
	 * @param taskName
	 * @return
	 */
	public ProcessForm getByRunIdTaskName(Long runId,String taskName);
	
	/**
	 * 按用户id查询所有已完成的任务信息 add by lu 2011.12.12
	 * @param userId
	 * @param PagingBean pb
	 * @return
	 */
	public List<ProcessForm> getCompleteTaskByUserId(Long userId,PagingBean pb);
	
	/**
	 * 按用户id,业务流程类型查询所有已完成的任务信息 add by lu 2012.03.02
	 * @param userId
	 * @param processName
	 * @param PagingBean pb
	 * @return
	 */
	public List<ProcessForm> getCompleteTaskByUserIdProcessName(Long userId,String processName,PagingBean pb);
	
	/**
	 * 查找某个流程某个任务某个状态的执行人
	 * @param runId
	 * @param activityName
	 * @param status
	 * @return
	 */
	public List<String> getByRunIdActivityName(Long runId,String activityName,Short status);
	
	/**
	 * 查询小贷常规、快速、展期流程(如果有)的意见与说明。add by lu 2012.04.24
	 * @param runIds
	 * @return
	 */
	public List getByRunIds(String runIds);
	
	/**
	 * 根据权限号取得某个流程实例的所有表单(包括小贷展期流程)add by lu 2012.04.24
	 * @param runIds
	 * @param safeLevel
	 * @return
	 */
	public List getByRunId(String runIds,Long safeLevel);
	
	/**
	 * 获取某个流程的某个会签节点的数量add by lu 2012.05.24
	 * @param runId
	 * @param activityName
	 * @return
	 */
	public List<ProcessForm> getListByRunIdActivityName(Long runId,String taskSequenceNodeKey,String taskId);
	
	/**
	 * 根据fromTaskId获取某个流程的某个会签节点的相关信息。所有状态的记录。add by lu 2012.05.25
	 * @param fromTaskId
	 * @return
	 */
	public List<ProcessForm> getListByFromTaskId(String fromTaskId);
	
	
	public  ProcessForm  getProcessFormByRunIdAndActivityName(Long runId,Long creatorId);
	
	/**
	 * 通过投票信息获取该节点的意见与说明(ProcessForm对象)add by lu 2012.06.06 (此方法可以不需要，这是针对不少的旧数据的处理的方法。add by lu 2013.06.18)
	 * @param runId
	 * @param creatorId(该属性与processForm中的creatorId完全同步)
	 * @param fromTaskId
	 * @return
	 */
    public ProcessForm getByRunIdFromTaskIdCreatorId(Long runId,String fromTaskId,Long creatorId);
	
	
    /**
	 * 通过投票信息获取是否存在未投票人员add by lu 2012.06.06
	 * @param runId
	 * @param fromTaskId
	 * @return
	 */
	public int getCountUnVoteUsers(Long runId,String fromTaskId);
	
	/**
	 * 获取尚未执行完成的并列实例节点表单,未进行任何处理的记录。add by lisl 2012-06-09
	 * @param fromTaskId
	 * @return
	 */
	public List<ProcessForm> getByFromTaskId(String fromTaskId);
	
	/**
	 * 根据runId和对应流程节点的key获取processForm对象 add by lu 2012-07-09
	 * @param runId
	 * @param flowNodeKey
	 * @return
	 */
	public ProcessForm getByRunIdFlowNodeKey(Long runId,String flowNodeKey);
	
	/**
	 * 根据runId和获取意见与说明记录的ProcessForm对象(要过滤类似审保会的情况：根据对应的节点的key过滤。) add by lu 2012-07-11
	 * @param runId
	 * @param flowNodeKeys(not like所包含的key) 所有流程的要过滤的节点的key
	 * @return
	 */
	public List<ProcessForm> getCommentsByRunId(Long runId ,String flowNodeKeys);
	
	/**
	 * 根据runId和获审保会所有记录  add by lu 2012-12-21
	 * @param runId
	 * @param flowNodeKey
	 * @return
	 */
	public List<ProcessForm> getSbhRecordsByRunIdFlowNodeKey(Long runId,String flowNodeKey);
	
	/**
	 * 获取由上一个节点指派下一个会签节点的处理人员ID,如果打回的节点为会签节点的情况下使用。
	 * @param runId
	 * @param activityName
	 * @return
	 * add by lu 2013.06.18
	 */
	public String getCountersignUserIds(Long runId,String activityName);

	public List<ProcessForm> listByRunId(Long projectId);
	/**
	 * 获取全部的流程任务
	 * @param request
	 * @param pb
	 * @return
	 */
	public List<ProcessForm> allProcessTask(HttpServletRequest request,
			PagingBean pb);
	/**
	 * 获取全部的已经完成的流程任务
	 * @param request
	 * @param pb
	 * @return
	 */
	public List<ProcessForm> allCompleteProcessTask(HttpServletRequest request,PagingBean pb);
}


