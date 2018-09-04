package com.zhiwei.credit.service.flow;

/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
 */
import java.util.Date;
import java.util.List;
import com.zhiwei.core.service.BaseService;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.action.flow.FlowRunInfo;

import com.zhiwei.credit.model.flow.ProDefinition;

import com.zhiwei.credit.model.flow.ProcessRun;

public interface ProcessRunService extends BaseService<ProcessRun> {

	public ProcessRun getInitNewProcessRun(ProDefinition proDefinition);

	/**
	 * 从流程运行提交的信息中初始化ProcessRun
	 * 
	 * @param runInfo
	 * @return
	 */
	public ProcessRun getInitFromFlowRunInfo(FlowRunInfo runInfo);

	/**
	 * 按流程的executionId取得流程的运行实例
	 * 
	 * @param exeId
	 * @return
	 */
	public ProcessRun getByExeId(String exeId);

	/**
	 * 按任务TaskId取得流程的运行实例
	 * 
	 * @param taskId
	 * @return
	 */
	public ProcessRun getByTaskId(String taskId);

	/**
	 * 
	 * @param piId
	 * @return
	 */
	public ProcessRun getByPiId(String piId);

//	/**
//	 * 完成任务，同时把数据保存至form_data表，记录该任务填写的表单数据
//	 * 
//	 * @param piId
//	 * @param transitionName
//	 * @param variables
//	 */
//	public ProcessRun saveAndNextStep(FlowRunInfo runInfo);

	/**
	 * 删除某一流程的所有实例
	 * 
	 * @param defId
	 *            流程定义的Id，则表pro_defintion的defId
	 */
	public void removeByDefId(Long defId);

	/**
	 * 按标题模糊查询某个用户所参与的流程列表
	 * 
	 * @param userId
	 * @param processName
	 * @param pb
	 * @return
	 */
	public List<ProcessRun> getByUserIdSubject(Long userId, String subject,
			PagingBean pb);

	/**
	 * 根据流程定义id查询对应的数据，如果存在：true,否则：false
	 * 
	 * @param defId
	 *            流程定义id
	 * @return 存在:true
	 */
	Boolean checkRun(Long defId);
	/**
	 * 统计流程的正在运行的实例数目
	 * @param defId
	 * @return
	 */
	public Integer countRunningProcess(Long defId);
	
	/**
	 * 项目事项追回-根据用户id和业务流程key查询相应任务
	 * @param userId
	 * @param processName
	 * @param PagingBean
	 * @return
	 * add by lu 2012.03.08
	 */
	public List<ProcessRun> getProcessRunsByUserIdProcessName(Long userId,String processName,PagingBean pb);
	
	/**
	 * 我参与的项目-根据用户id和业务流程key查询相应任务
	* @param userId
	 * @param processName
	 * @param PagingBean
	 * @param createTimeStart
	 * @param createTimeEnd
	 * @param runStatus
	 * @param subject
	 * @return
	 * add by lu 2012.03.09
	 */
	public List<ProcessRun> getMyProjectsByUserIdProcessName(Long userId,String processName,PagingBean pb,String createTimeStart,String createTimeEnd,String runStatus,String subject);
	
	/**
	 * 我发起的项目-根据用户id和业务流程key查询相应任务
	 * @param userId
	 * @param processName
	 * @param PagingBean
	 * @param createTimeStart
	 * @param createTimeEnd
	 * @param runStatus
	 * @param subject
	 * @return
	 * add by lu 2012.03.09
	 */
	public List<ProcessRun> getMyCreateProjectsByUserIdProcessName(Long userId,String processName,PagingBean pb,String createTimeStart,String createTimeEnd,String runStatus,String subject);
	
	/**
	 * 查询小贷常规流程、快速流程是否存在展期流程。add by lu 2012.04.24
	 * @param projectId
	 * @param businessType
	 * @param processName
	 * @return
	 * add by lu 2012.04.24
	 */
	public List<ProcessRun> getByProcessNameProjectId(Long projectId,String businessType,String processName);
	
	public ProcessRun getByBusinessTypeProjectId(Long projectId,String businessType);
}
