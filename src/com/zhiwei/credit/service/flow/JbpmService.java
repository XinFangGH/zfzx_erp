package com.zhiwei.credit.service.flow;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessInstance;

import org.jbpm.api.model.Transition;
import org.jbpm.api.task.Task;


import com.zhiwei.core.jbpm.jpdl.Node;
import com.zhiwei.credit.action.flow.FlowRunInfo;
import com.zhiwei.credit.model.flow.ProDefinition;
import com.zhiwei.credit.model.flow.ProUserAssign;
import com.zhiwei.credit.model.flow.ProcessRun;
import com.zhiwei.credit.model.system.AppUser;
/**
 * Jbpm操作接口
 * @author csx
 *
 */
public interface JbpmService {
	
	/**
	 * 通过ExecutionId取得processInstance
	 * @param executionId
	 * @return
	 */
	public ProcessInstance getProcessInstanceByExeId(String executionId);

	/**
	 * 取得任务节点
	 * @param defId
	 * @return
	 */
	public List<Node>getTaskNodesByDefId(Long defId);
	/**
	 * 取得任务节点
	 * @param defId
	 * @return
	 */
	public List<Node>getTaskNodesByDefId(Long defId,boolean start,boolean end);
	
	/**
	 * 取得任务定义
	 * @param taskId
	 * @return
	 */
	public Task getTaskById(String taskId);
	
	/**
	 * 任务指定执行
	 * @param taskId
	 * @param userId
	 */
	public void assignTask(String taskId,String userId);
	
	/**
	 * 加载需要填写表单的流程节点
	 * @param defId
	 * @return
	 */
	public List<Node>getFormNodesByDeployId(Long deployId);
	
	
	
	/**
	 * 按流程key取得流程定义
	 * @return
	 */
	public ProDefinition getProDefinitionByKey(String processKey);
	
	/**
	 * 通过任务取得流程节义
	 * @param taskId
	 * @return
	 */
	public ProcessDefinition getProcessDefinitionByTaskId(String taskId);
	
	/**
	 * 按流程的Key取得流程定义
	 * @param processKey
	 * @return
	 */
	public ProcessDefinition getProcessDefinitionByKey(String processKey);
	/**
	 * 按流程定义（ProDefinition）取得流程定义
	 * @param defId
	 * @return
	 */
	public ProcessDefinition getProcessDefinitionByDefId(Long defId);
	
	/**
	 * 按流程定义id取得流程定义
	 * @param pdId
	 * @return
	 */
	public ProcessDefinition getProcessDefinitionByPdId(String pdId);
	
	/**
	 * 
	 * @param defId
	 * @return
	 */
	public String getDefinitionXmlByDefId(Long defId);
	/**
	 * 按发布id取得流程定义
	 * @return
	 */
	public String getDefinitionXmlByDpId(String deployId);
	/**
	 * 按流程实例ID取得流程定义
	 * @param piId
	 * @return
	 */
	public String getDefinitionXmlByPiId(String piId);
	
	/**
	 * 按流程执行的id取得流程定义
	 * @param exeId
	 * @return
	 */
	public String getDefinitionXmlByExeId(String exeId);
	
	/**
	 * 取得流程实例
	 * @param piId
	 * @return
	 */
	public ProcessInstance getProcessInstance(String piId);
	
	/**
	 * 按任务id取得流程实例
	 * @param taskId
	 * @return
	 */
	public ProcessInstance getProcessInstanceByTaskId(String taskId);
	
	
	/**
	 * 删除流程定义，同时也删除该流程的相关数据，包括启动的实例，表单等相关的数据
	 * @param defId
	 */
	public void doUnDeployProDefinition(Long defId);
	
	/**
	 * 发布或更新流程定义
	 * @param proDefinition
	 * @return
	 */
	public ProDefinition saveOrUpdateDeploy(ProDefinition proDefinition);
	
	
	/**
	 *
	 * 启动工作流，并且返回ProcessRun实例
	 * @param startInfo
	 */
	public ProcessRun doStartProcess(FlowRunInfo startInfo);
	/**
	 * 执行流程下一步的操作,并且返回ProcessRun实例
	 * @param nextInfo
	 * @return
	 */
	public ProcessRun doNextStep(FlowRunInfo nextInfo);
	
	/**
	 * 显示某个流程当前任务对应的所有出口
	 * @param piId
	 * @return
	 */
	 public List<Transition> getTransitionsForSignalProcess(String piId);
	 
	 /**
	  * 按任务节点取得所有出口
	  * @param taskId
	  * @return
	  */
	 public List<Transition> getTransitionsByTaskId(String taskId);
	 
	 /**
	  * 按任务节点取得所有出口
	  * @param taskId
	  * @return
	  */
	 public List<Transition> getTransitionsByTaskId(String taskId,Boolean flag);
	 
//	 /**
//	  * 取得任务对应的所有跳转名称
//	  */
//	 public Set<String> getTransitionsByTaskId(Long taskId);
	 
	 
	 /**
	  * 从当前的任务节点，可以跳至流程的任何任务节点，可以创建任何跳转的连接
	  * @param taskId
	  * @return
	  */
	 public List<Transition> getFreeTransitionsByTaskId(String taskId);
	 
	/**
	 * 取到节点类型
	 * 
	 * @param xml
	 * @param nodeName
	 * @return
	 */
	public String getNodeType(String xml, String nodeName);
	
	/**
	 * 取得开始节点名称
	 * @param proDefinition
	 * @return
	 */
	public String getStartNodeName(ProDefinition proDefinition);
	
	/**
	 * 通过流程定义实例ID取得流程对应的XML
	 * @param piId
	 * @return
	 */
	public String getProcessDefintionXMLByPiId(String piId);
	
	/**
	  * 取得某流程实例对应的任务列表
	  * @param piId
	  * @return
	  */
	 public List<Task> getTasksByPiId(String piId);
	 
	/**
	 * 完成任务，
	 * @param taskId 任务ID
	 * @param transitionName　下一步的转换名称
	 * @param 目标节点名称      加上该参数，目的是为了自由跳转的流程，因为对于两个不存在的连接的节点，需要动态创建连接才能进行跳转。
	 * @param variables　流程的附加数据
	 */
	 public void completeTask(String taskId,String transitionName,String destName,Map variables);
	 
	/**
	 * 执行普通任务节点下一步
	 * @param executionId
	 * @param transitionName
	 * @param variables
	 */
	public void signalProcess(String executionId, String transitionName, Map<String, Object> variables);
	
	/**
	 * 创建新的任务
	 * @param parentTaskId 父任务 ID
	 * @param assignIds 任务执行人IDs
	 */
	public void newSubTask(String parentTaskId,Long[] userIds);
	
	/**
	 * 结束流程实例
	 * @param piId
	 */
	public void endProcessInstance(String piId);
	
	/**
     * 为流程定义加上任务的指派人员接口
     * @param deployId
     */
    public void addAssignHandler(ProUserAssign proUserAssign);
    
    /**
	 * 允许任务回退
	 * @param taskId
	 * @return
	 */
	public boolean isAllownBack(String taskId);
	
	/**
	 * 取到流程的启动用户
	 * @param taskId
	 * @return
	 */
	public Long getFlowStartUserId(String taskId);
	
	/**
	 * 是否为会签任务
	 * @param taskId
	 * @return
	 */
	public boolean isSignTask(String taskId);
	/**
	 * 取得流程定义中的某个节点的处理人
	 * @param pd
	 * @param activityName
	 * @param startUserId
	 * @return
	 */
	 public Set<AppUser> getNodeHandlerUsers(ProcessDefinition pd,String activityName,Long startUserId);
	
	 /**
     * 取得流程某个节点的处理人员列员 TODO
     * @param taskId  当前任务的实例id
     * @param activityName 下一任务活动节点的名称
     * @return
     */
    public Set<AppUser> getNodeHandlerUsers(String taskId,String activityName);
    
    /**
     * 取得流程定义中的节点处理人
     * @param defId
     * @param activityName
     * @return
     */
    public Set<AppUser> getNodeHandlerUsers(Long defId,String activityName);
    
    /**
     * 取得某个任务其对应流程变量值
     * @param taskId 任务ID
     * @param varName 变量名称
     * @return
     */
    public Object getVarByTaskIdVarName(String taskId,String varName);
    
    /**
     * 取得流程定义的节点的分支跳转
     * @param definition 流程定义
     * @param nodeName 节点名称
     * @return
     */
    public List<Transition> getNodeOuterTrans(ProcessDefinition definition,String nodeName);
    
    /**
     * 取得某个任务的所有子任务的处理人员
     * @param parentTaskId
     * @return
     */
    public List<String> getAssigneeByTaskId(String parentTaskId);
    
    /**
     * 取得父任务
     * @param subTaskId 子任务ID
     * @return
     */
    public Task getParentTask(String subTaskId);
    
    /**
	  * 取得开始节点的跳出路线列表
	  * @param deployId
	  * @return
	  */
	 public List<Transition> getStartOutTransByDeployId(String deployId);
	 
	/**
	 * 返回某个任务的所有变量
	 * 
	 * @param taskId
	 * @return
	 */
	public Map<String, Object> getVarsByTaskId(String taskId);
	
	/**
	 * 取得某个任务的所有入口
	 * @param taskId
	 * @return
	 */
	public List<Transition> getInTransForTask(String taskId);
	
	/**
	 * 跳回前一执行任务
	 * @param piId 流程实例id
	 * @param assignee 执行人
	 * @param curTaskName  add by lu 2012.05.16
	 * @param preTaskName 前一任务名
	 */
	public void jumpToPreTask(String piId,String assignee,String curTaskName,String preTaskName);
	
	/**
	 * 把修改过的xml更新至回流程定义中
	 * @param deployId
	 * @param defXml
	 */
	public void wirteDefXml(String deployId,String defXml);
	
	
	/**
	 * 取得前一任务
	 * @param taskId
	 * @return
	 */
	public String getPreTask(String taskId);
	
	 /**
	  * 通过子流程的任务实例id，取得子流程在父流程的跳转分支
	  * @param subFlowTaskId  子流程的任务id
	  * @return
	  */
	 public List<Transition> getTransitionsBySubFlowTaskId(String subFlowTaskId);
	 
	 /**
	  * 取某个流程定义的对应的某个节点的跳转分支
	  * @param defId
	  * @param activityName
	  * @return
	  */
	 public List<Transition> getTransByDefIdActivityName(Long defId,String activityName);
	 
	 /**
	 * 保存流程启动时表单历史
	 * @param processRun  流程实例
	 * 小额贷款子流程：主管意见只有一个节点，该节点为会签节点，需要进行特殊处理。
	 * 在启动流程处尚未获取子任务以及在process_form中不产生会签数量的记录。
	 * 尽量避免与原方法使用一起(尽管差不多)，所以单独增加此方法。
	 * 以后每个流程第一个节点为会签节点都可以调用此方法。
	 * add by lu 2012.05.29
	 */
	public void saveInitProcessFormDirectorOpinion(ProcessRun processRun);
	
	/**
	 * 更新流程发起人
	 * @param startUserId
	 * @param executionId
	 * @param startUserIdKey
	 * add by lu 2012.08.23
	 */
	public void updateStartUserId(Long startUserId,Long executionId,String startUserIdKey);
	
	/**
	 * 为分公司分配流程执行的操作
	 * @param proDefinition
	 * @param subCompanyKey
	 * @param oldDeployId
	 * @param isUpdateBranchCurrentFlow(true：更新标准版本的流程到分公司；false：为分公司分配标准版本的流程 update by lu 2013.07.04)
	 * (isUpdateBranchCurrentFlow为false的时候该值传递的为null，分配流程用不到；
	 * 为true的时候该值传递的标准版本的流程的deployId,读取标准版本vm文件的路径进行复制。update by lu 2013.07.04)
	 * @param currentBranchCompanyDeployId
	 * add by lu 2012.09.12
	 */
	public String saveNewFlow(ProDefinition proDefinition,String subCompanyKey,String oldDeployId,boolean isUpdateBranchCurrentFlow,String currentBranchCompanyDeployId);
	
	/**
	 * 编辑当前或者所有版本的流程xml文件
	 * @param proDefinition
	 * @param isEditCurrentVersion：true(编辑当前版本的xml文件);false(编辑所有版本的xml文件)
	 * add by lu 2012.09.25
	 */
	public void wirteDefXmlToJbpmLob(ProDefinition proDefinition,boolean isEditCurrentVersion);
	
	/**
	 * 获取当前流程实例的所有任务
	 * @param piId
	 * add by lu 2012.12.27
	 */
	public List<Task> getTaskWithSubTask(String piId);
	
	/**
	 * 解决类似deployment 440001 doesn't contain object smallLoanCommonFlow的错误的方法。
	 * @param pdId
	 * @return
	 * add by lu 2013.06.24
	 */
	public List<String> getDeployIdByPdId(String pdId);
	
	/***
	 * 挂起任务处理
	 * ***/
	public void completeTask(String taskId,String destName,Map map);
	/**
	 * 挂起任务processFOrm 处理
	 *
	 * */
	public void completeForm(String taskId,String destName);
	
}
