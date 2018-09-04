package com.zhiwei.credit.action.flow;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.jbpm.api.TaskService;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.task.TaskImpl;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.jbpm.pv.TaskInfo;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.core.util.JsonUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PageBean;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.core.creditUtils.ExcelHelper;
import com.zhiwei.credit.model.creditFlow.common.VCommonProjectFlow;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.VSmallloanProject;
import com.zhiwei.credit.model.flow.ProDefinition;
import com.zhiwei.credit.model.flow.ProUserAssign;
import com.zhiwei.credit.model.flow.ProcessForm;
import com.zhiwei.credit.model.flow.ProcessRun;
import com.zhiwei.credit.model.flow.TaskProxy;
import com.zhiwei.credit.model.info.ShortMessage;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.creditFlow.common.CreditProjectService;
import com.zhiwei.credit.service.creditFlow.common.VCommonProjectFlowService;
import com.zhiwei.credit.service.creditFlow.smallLoan.project.VSmallloanProjectService;
import com.zhiwei.credit.service.flow.JbpmService;
import com.zhiwei.credit.service.flow.ProDefinitionService;
import com.zhiwei.credit.service.flow.ProUserAssignService;
import com.zhiwei.credit.service.flow.ProcessFormService;
import com.zhiwei.credit.service.flow.ProcessRunService;
import com.zhiwei.credit.service.flow.TaskProxyService;
import com.zhiwei.credit.service.info.ShortMessageService;
import com.zhiwei.credit.service.system.AppUserService;

import flexjson.JSONSerializer;

/**
 * 流程中的任务的显示及操作
 * @author csx
 *
 */
public class TaskAction extends BaseAction{
	@Resource(name="flowTaskService")
	private com.zhiwei.credit.service.flow.TaskService flowTaskService;
	@Resource
	private TaskService taskService;
	@Resource
	private ShortMessageService shortMessageService;
	@Resource
	private JbpmService jbpmService;
	@Resource
	private VCommonProjectFlowService vCommonProjectFlowService;
	//private ProcessRunDataService processRunDataService;//已修改为vCommonProjectFlowService
	@Resource
	private ProcessFormService processFormService;
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private CreditProjectService creditProjectService;
	@Resource
	private ProUserAssignService proUserAssignService;
	@Resource
	private ProDefinitionService proDefinitionService;
	@Resource
	private AppUserService appUserService;
	@Resource
	private VSmallloanProjectService vSmallloanProjectService;
	@Resource
	private TaskProxyService taskProxyService;
	
	private String processName;
	private String comments;
	
	private String projectName;//项目待办事项-查询条件(项目名称) 
	private String projectNumber;//项目待办事项-查询条件(项目编号)
	
	private String isSuspendedProject;//0：恢复项目；1：挂起项目。
	private String assignId;//中铭常规流程：风险主管调配任务选择下一个节点处理人的用户id
	
	private String flowNodeKey;//对应流程的节点的key
	private String searchType;//查询类型：all-根据流程key和节点的key查询所有任务；byUserId-根据流程key、节点的key和用户id查询所有任务
	private String customerName;
	private Date startDate;
	private Date endDate;
	
	public List<Long> proxyUser=new ArrayList<Long>();//被代理用户id集合
	/**
	 * 按分页取得目前所有的正在进行的任务
	 * @return
	 */
	public String all(){
		String taskName=getRequest().getParameter("taskName");
		PagingBean pb = new PagingBean(start, limit);
		List<TaskInfo> tasks=flowTaskService.getAllTaskInfos(taskName, pb);
		setJsonString(gsonFormat(tasks, pb.getTotalItems()));
		return SUCCESS;
	}
	
	/**
	 * 系统菜单调整后直投标齐标起息放款流程任务查询方法
	 * @return
	 */
	public String findByName(){
		String flowType=getRequest().getParameter("flowType");
		String taskSequence=getRequest().getParameter("taskSequence");
		String type=getRequest().getParameter("type");
		String resource=getRequest().getParameter("resource");
		String busType=getRequest().getParameter("busType");
		Map<String,String> map=new HashMap<String,String>();
		if(null!=flowType && !"".equals(flowType)){
			map.put("flowType",flowType);
		}
		if(null!=taskSequence && !"".equals(taskSequence)){
			map.put("taskSequence",taskSequence);
		}
		if(null!=type && !"".equals(type)){
			map.put("type",type);
		}
		if(null!=resource && !"".equals(resource)){
			map.put("resource",resource);
		}
		if(null!=busType && !"".equals(busType)){
			map.put("busType",busType);
		}
		PageBean<TaskInfo> pageBean=new PageBean<TaskInfo>(start, limit,getRequest());
		flowTaskService.findTaskByName(map,pageBean);
		setJsonString(gsonFormat(pageBean.getResult(),pageBean.getTotalCounts()));
		return SUCCESS;
	}
	
	/**
	 * 取得某个任务的处理用户
	 * @return
	 */
	public String users(){
		String taskId=getRequest().getParameter("taskId");
		String activityName=getRequest().getParameter("activityName");
		Set<AppUser> users=jbpmService.getNodeHandlerUsers(taskId, activityName);
		StringBuffer uIds=new StringBuffer();
		StringBuffer uNames=new StringBuffer();
		Iterator<AppUser>it=users.iterator();
		int i=0;
		while(it.hasNext()){
			AppUser user=it.next();
			if(i>0){
				uIds.append(",");
				uNames.append(",");
			}
			uIds.append(user.getUserId());
			uNames.append(user.getFullname());
			i++;
		}
		
		jsonString="{success:true,userIds:'" + uIds.toString() + "',userNames:'" + uNames.toString()+ "'}";
		
		return SUCCESS;
	}
	
	/**
	 * 设置任务过期时间
	 * @return
	 */
	public String due(){
		String taskIds=getRequest().getParameter("taskIds");
		String dueDateStr=getRequest().getParameter("dueDate");
		if(logger.isDebugEnabled()){
			logger.debug("taskIds:" + taskIds + " dueDate:" + dueDateStr);
		}
		if(StringUtils.isNotEmpty(taskIds)){
			String[]taskIdArr=taskIds.split("[,]");
			try{
				Date dueDate=DateUtils.parseDate(dueDateStr, new String[]{"yyyy-MM-dd HH:mm:ss"});
				for(String taskId:taskIdArr){
					Task task=taskService.getTask(taskId);
					task.setDuedate(dueDate);
					taskService.saveTask(task);
				}
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		
		return SUCCESS;
	}
	//任务指派
	public String handler(){
		String taskIds=getRequest().getParameter("taskIds");
		String userId=getRequest().getParameter("userId");
		
		ProcessForm existForm = null;
		
		if(logger.isDebugEnabled()){
			logger.debug("taskIds:" + taskIds + " userId:" + userId);
		}
		if(StringUtils.isNotEmpty(taskIds)){
			String[]taskIdArr=taskIds.split("[,]");
			for(String taskId:taskIdArr){
				String newTaskId = creditProjectService.getNewTaskId(taskId);
				if(newTaskId!=null&&!"".equals(newTaskId)){
					existForm = processFormService.getByTaskId(taskId);
					taskId = newTaskId;
				}else{
					existForm = processFormService.getByTaskId(taskId);
				}
				taskService.assignTask(taskId, userId);
				//add by lisl 修改意见与记录中的备注
				String nowDateTime = DateUtil.getDateAndTime();
				String curComment = existForm.getComments()!= null?existForm.getComments() : "";
				existForm.setComments(curComment + comments + "；");
				existForm.setProjectAssign(curComment + comments + "；"+nowDateTime);
				existForm.setStatus(ProcessForm.STATUS_TASKASSIGN);//任务换人
				processFormService.save(existForm);
				//end 
			}
		}
		
		return SUCCESS;
		
	}
	
	public String list(){
		PagingBean pb=new PagingBean(start, limit);
		//List<TaskInfo> tasks=flowTaskService.getTaskInfosByUserId(ContextUtil.getCurrentUserId().toString(),pb);
		List<TaskInfo> tasks=flowTaskService.getTasksByUserIdProcessNameTransfer(ContextUtil.getCurrentUserId().toString(), processName, pb,null,null);
		setJsonString(gsonFormat(tasks, pb.getTotalItems()));
		return SUCCESS;
	}
	
	public String change(){
		HttpServletRequest request=getRequest();
		String taskId=request.getParameter("taskId");
		String userId=request.getParameter("userId");
		String curUserId=ContextUtil.getCurrentUserId().toString();
		Task task=taskService.getTask(taskId);
		if(task!=null && curUserId.equals(task.getAssignee())){
			taskService.assignTask(taskId, userId);
			String msg=request.getParameter("msg");
			if(StringUtils.isNotEmpty(msg)){
				//添加短信息提示
				shortMessageService.save(AppUser.SYSTEM_USER,userId,msg,ShortMessage.MSG_TYPE_TASK);
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
	}
	
	/**
	 * 释放任务
	 * @return
	 */
	public String unlock(){
		String taskId=getRequest().getParameter("taskId");
		Task task=taskService.getTask(taskId);
		
		String curUserId=ContextUtil.getCurrentUserId().toString();
		
		if(task!=null && curUserId.equals(task.getAssignee())){//为本人的任务，并且尚未完成才能解锁
			taskService.assignTask(task.getId(), null);
			setJsonString("{success:true,unlocked:true}");
		}else{
			setJsonString("{success:true,unlocked:false}");
		}
		
		return SUCCESS;
	}
	
	/**
	 * 锁定任务
	 * @return
	 */
	public String lock(){
		
		String taskId=getRequest().getParameter("taskId");
		Task task=taskService.getTask(taskId);
		
		if(task!=null && task.getAssignee()==null){//该任务尚未被分配，或该任务已经被处理完毕
			taskService.assignTask(task.getId(), ContextUtil.getCurrentUserId().toString());
			setJsonString("{success:true,hasAssigned:false}");
		}else{
			setJsonString("{success:true,hasAssigned:true}");
		}
		
		return SUCCESS;
	}
	
    public String display(){
		PagingBean pb=new PagingBean(0, 7);//获取前七条数据
		List<TaskInfo> tasks=flowTaskService.getTasksByUserIdProcessNameTransfer(ContextUtil.getCurrentUserId().toString(), processName, pb,null,null);
		getRequest().setAttribute("taskList", tasks);
		return "display";
	}
    
    /**
     * 当前用户所有进行中任务数
     */
    public void getCount(){
    	int size=0;
    	if(processName!=null&&!"".equals(processName)){
    	    //循环遍历task_proxy表查找userId的最终代理人
    	    findProxyUserId(ContextUtil.getCurrentUserId());
    	}
    	try{
    		for(Long l:proxyUser){
    			size+=flowTaskService.getAllByUserIdProcessName(l.toString(),processName);
    	    }
    	}catch (Exception e) {
			e.printStackTrace();
		}
    	StringBuffer buff = new StringBuffer("{success:true,'totalCounts':");
    	buff.append(size);
		buff.append("}");
		com.zhiwei.credit.core.creditUtils.JsonUtil.responseJsonString(buff.toString()) ;
    }
    
    /**
     * 根据当前登录人查询所有最终代理人的信息
     * @param userId 当前登录用户id
     * @return
     */
    public void findProxyUserId(Long userId){
    	QueryFilter filter=new QueryFilter();
    	filter.addFilter("Q_agentId_L_EQ",Long.valueOf(userId));
    	List<TaskProxy> proxy_list=taskProxyService.getAll(filter);
    	if(null!=proxy_list && proxy_list.size()>0){
    		for(TaskProxy t:proxy_list){
    			findProxyUserId(t.getPrincipalId());
    		}
    	}else{
    		if(!proxyUser.contains(Long.valueOf(userId))){
    			proxyUser.add(Long.valueOf(userId));
    		}
    	}
    	if(!proxyUser.contains(ContextUtil.getCurrentUserId())){
			proxyUser.add(ContextUtil.getCurrentUserId());
		}
    }
    
    /**
     * 当前用户所有进行中任务。
     * @return
     */
    public String userActivityAll(){
    	List<TaskInfo> tasks_list=new ArrayList<TaskInfo>();
    	PagingBean pb=new PagingBean(start, limit);//分页条件查询
    	//循环遍历task_proxy表查找userId的最终代理人
    	findProxyUserId(ContextUtil.getCurrentUserId());
    	for(Long l:proxyUser){
    		List<TaskInfo> tasks=flowTaskService.getTasksByUserIdProcessNameTransfer(l.toString(), processName, pb,projectName,projectNumber);
    		tasks_list.addAll(tasks);
	    }
    	StringBuffer buff = new StringBuffer("{\"success\":true,\"totalCounts\":");
    	if(pb.getTotalItems()>0){
    		buff.append(pb.getTotalItems());
    	}else{
    		buff.append(0);
    	}
    	buff.append(",\"result\":");
		JSONSerializer json = JsonUtil.getJSONSerializerDateByFormate("yyyy-MM-dd HH:mm:ss");
		//buff.append(json.serialize(allList));
		if(tasks_list!=null&&tasks_list.size()>0){
			buff.append(json.serialize(tasks_list));
		}else{
			buff.append("\"\"");
		}
		buff.append("}");
		jsonString=buff.toString();
		
    	return SUCCESS;
    }
    
    /**
     * 当前用户所有已完成任务。
     * @return
     */
    public String userCompleteAll(){
    	
    	//String previousCreator = "";
    	//ProcessForm pf = null;
    	//VProcessRunData vpRun = null;
    	//String fromTaskId = "";
    	
    	//List<Map> allList=new ArrayList<Map>();
    	PagingBean pb=new PagingBean(start, limit);//分页条件查询
    	List<ProcessForm> pfList=processFormService.getCompleteTaskByUserIdProcessName(ContextUtil.getCurrentUserId(),processName,pb);
    	//PagingBean pbCounts=new PagingBean(0, 10000);//总数
    	//List<ProcessForm> pfList=processFormService.getCompleteTaskByUserId(ContextUtil.getCurrentUserId(), pb);
    	//List<ProcessForm> countList=processFormService.getCompleteTaskByUserId(ContextUtil.getCurrentUserId(), pbCounts);
    	if(pfList!=null){
    		for(int i=0;i<pfList.size();i++){
    			String previousCreator = "";
    			//Map<String, Object> map = new HashMap<String, Object>();
    			//ProcessForm pf = new ProcessForm();
    			//VCommonProjectFlow vpRun = new VCommonProjectFlow();
    			
    			ProcessForm processForm = pfList.get(i);
    			//Long runId = processForm.getRunId();
    			String fromTaskId = processForm.getFromTaskId();
    			if(null==fromTaskId||"null".equals(fromTaskId)){
    				previousCreator = "null";
    			}else{
    				ProcessForm pf = processFormService.getByTaskId(fromTaskId);
    				if(pf!=null&&pf.getCreatorName()!=null){
    					previousCreator = pf.getCreatorName();
    				}
    			}
    			processForm.setPreviousCreator(previousCreator);
    			processForm.setProjectName(processForm.getProcessRun().getSubject());
    			processForm.setBusinessType(processForm.getProcessRun().getBusinessType());
    			/*vpRun = vCommonProjectFlowService.get(runId);
    			if(vpRun!=null){
    				processForm.setPreviousCreator(previousCreator);
    				map.put("processForm", processForm);
        			map.put("vCommonProjectFlow", vpRun);
    			}
    			allList.add(map);*/
    		}
    	}
    	StringBuffer buff = new StringBuffer("{success:true,'totalCounts':");
    	buff.append(pb.getTotalItems());
    	//buff.append(countList==null?0:countList.size());
    	buff.append(",result:");
		JSONSerializer json = JsonUtil.getJSONSerializerDateByFormate("yyyy-MM-dd HH:mm:ss");
		//buff.append(json.serialize(allList));
		buff.append(json.serialize(pfList));
		buff.append("}");
		jsonString=buff.toString();
    	return SUCCESS;
    }
    
    public String getAllByUserIdProcessName(){
    	if(processName!=null&&!"".equals(processName)){
    		String userId = ContextUtil.getCurrentUserId().toString();
    		//List<TaskImpl> taskList = flowTaskService.getAllByUserIdProcessName(userId, processName);
    		//jsonString = "{success:true,taskCounts:'" + taskList==null?"0":taskList.size()+ "'}";
    		//setJsonString("{success:true,taskCounts:" + taskList==null?0:taskList.size() + "}");
    	}
    	return SUCCESS;
    }
    
    /**
     * 检测当前任务是否被锁定，如果是自己的或者未锁定，则返回TRUE,
     * 已经被他人锁定，则返回FALSE
     * @return
     */
    public String check(){
		
		String taskId=getRequest().getParameter("taskId");
		TaskImpl task=(TaskImpl)taskService.getTask(taskId);
		
		StringBuffer sb=new StringBuffer("{success:true,isSubFlow:");
		//为子流程任务
		boolean isSubFlow=false;
		
		if(task!=null &&  task.getExecution()!=null && task.getExecution().getSuperProcessExecution()!=null){
			isSubFlow=true;
		}
		sb.append(isSubFlow);
		String cruUserId=ContextUtil.getCurrentUserId().toString();

		if(task!=null &&task.getAssignee()!=null&&task.getAssignee().equals(cruUserId)){//该任务尚未被分配，或该任务已经被处理完毕
			//Skip here
		}else if(task!=null && task.getAssignee()==null){//任务尚未授予人员
			taskService.assignTask(task.getId(),cruUserId );
			sb.append(",assigned:true");
		}else{
			sb.append(",assigned:false");
		}
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
    
    //挂起任务、项目
    public String suspendByRunId(){
    	String taskId=getRequest().getParameter("taskId");
		String runId=getRequest().getParameter("runId");
		String piId = "";
		String businessType = "";
			
		if(taskId!=null&&runId!=null){
			ProcessRun processRun = processRunService.get(new Long(runId));
			//更新项目等表信息
			if(processRun!=null){
				businessType = processRun.getBusinessType();
				piId = processRun.getPiId();
				Long projectId = processRun.getProjectId();
				creditProjectService.suspendProject(businessType, projectId, processRun,isSuspendedProject);
			}
			
			List<TaskImpl> task = flowTaskService.getTaskByExecutionId(piId);
			//大多时候都只存在一个任务,此时只更新一条记录即可,而非每次都进行多次循环判断执行。
			if(task!=null&&task.size()==1){//单个任务或者会签任务
				Long dbId = task.get(0).getDbid();
				List<Task> taskList = taskService.getSubTasks(dbId.toString());
				if(taskList!=null&&taskList.size()!=0){//会签任务
					for(int i=0;i<taskList.size();i++){
						TaskImpl subTask = (TaskImpl)taskList.get(i);
						if(subTask!=null){
							if(isSuspendedProject!=null&&"1".equals(isSuspendedProject)){
								subTask.setState(Task.STATE_SUSPENDED);
							}else if(isSuspendedProject!=null&&"0".equals(isSuspendedProject)){
								subTask.setState(Task.STATE_OPEN);
		    				}
							flowTaskService.merge(subTask);
						}
					}
				}else{//单个任务
					TaskImpl t = task.get(0);
					if(isSuspendedProject!=null&&"1".equals(isSuspendedProject)){
						t.setState(Task.STATE_SUSPENDED);
					}else if(isSuspendedProject!=null&&"0".equals(isSuspendedProject)){
						t.setState(Task.STATE_OPEN);
					}
					flowTaskService.merge(t);
				}
			}else if(task!=null&&task.size()>1){//存在并列任务或者并列任务、会签任务一起存在
				for(int k=0;k<task.size();k++){
					TaskImpl taskImpl = task.get(k);
					if(isSuspendedProject!=null&&"1".equals(isSuspendedProject)){
						taskImpl.setState(Task.STATE_SUSPENDED);
					}else if(isSuspendedProject!=null&&"0".equals(isSuspendedProject)){
						taskImpl.setState(Task.STATE_OPEN);
					}
					flowTaskService.merge(taskImpl);
					List<Task> taskList = taskService.getSubTasks(taskImpl.getId());
					if(taskList!=null&&taskList.size()!=0){
						for(int t=0;t<taskList.size();t++){
							TaskImpl ta = (TaskImpl)taskList.get(t);
							if(isSuspendedProject!=null&&"1".equals(isSuspendedProject)){
		    					ta.setState(Task.STATE_SUSPENDED);
		    				}else if(isSuspendedProject!=null&&"0".equals(isSuspendedProject)){
		    					ta.setState(Task.STATE_OPEN);
		    				}
							flowTaskService.merge(ta);
						}
					}
				}
			}
		}
		return SUCCESS;
    }
    
    public String getByProcessNameNodeKey(){
    	List<Map> allList=new ArrayList<Map>();
    	List<TaskInfo> tasksList=new ArrayList<TaskInfo>();
    	PagingBean pb=new PagingBean(start, limit);//分页条件查询
    	
    	if(!"".equals(flowNodeKey)&&flowNodeKey!=null&&processName!=null&&!"".equals(processName)){
    		ProDefinition proDefinition = proDefinitionService.getProdefinitionByProcessName(processName);
    		String activityName = "";
    		if(proDefinition!=null){
    			String deployId = proDefinition.getDeployId();
    			if(deployId!=null&&!"".equals(deployId)){
    				ProUserAssign proUserAssign = proUserAssignService.getByDeployIdFlowNodeKey(deployId, flowNodeKey);
    				if(proUserAssign!=null){
    					activityName = proUserAssign.getActivityName();
    				}
    			}
    		}
    		if(searchType!=null&&!"".equals(searchType)&&"byUserId".equals(searchType)){
    			String userId = ContextUtil.getCurrentUserId().toString();
    			tasksList = flowTaskService.getTasksByProcessNameActivityNameUserId(processName, activityName, userId, pb,projectName,projectNumber,customerName);
    		}else{
    			tasksList = flowTaskService.getTasksByProcessNameActivityName(processName, activityName, pb,projectName,projectNumber,customerName);
    		}
    		
    		if(tasksList!=null){
        		for(int i=0;i<tasksList.size();i++){
        			Map<String, Object> map = new HashMap<String, Object>();
        			VCommonProjectFlow vp = null;
        			TaskInfo info = tasksList.get(i);
        			if(info.getPiId()!=null){
    					List<VCommonProjectFlow> piList = vCommonProjectFlowService.getByPiId(info.getPiId());
    					if(piList!=null&&piList.size()!=0){
    						vp = piList.get(0);
    					}
    				}else{
    					ProcessForm pf = processFormService.getByTaskId(info.getTaskId().toString());
        				if(null!=pf){
        					vp = vCommonProjectFlowService.get(pf.getRunId());
        				}
    				}
        			map.put("task", info);
        			map.put("vCommonProjectFlow", vp);
        			allList.add(map);
        		}
        	}
        	StringBuffer buff = new StringBuffer("{success:true,'totalCounts':");
        	buff.append(pb.getTotalItems());
        	buff.append(",result:");
    		JSONSerializer json = JsonUtil.getJSONSerializerDateByFormate("yyyy-MM-dd HH:mm:ss");
    		buff.append(json.serialize(allList));
    		buff.append("}");
    		jsonString=buff.toString();
    	}
    	return SUCCESS;
    }
    /**
     * 贷款结案用到
     * @return
     */
    public String getByProcessNameNodeKeyOfCommon(){
    	List<Map> allList=new ArrayList<Map>();
    	List<TaskInfo> tasksList=new ArrayList<TaskInfo>();
    	PagingBean pb=new PagingBean(start, limit);//分页条件查询
    	
    	if(!"".equals(flowNodeKey)&&flowNodeKey!=null&&processName!=null&&!"".equals(processName)){
    		ProDefinition proDefinition = proDefinitionService.getProdefinitionByProcessName(processName);
    		String activityName = "";
    		if(proDefinition!=null){
    			String deployId = proDefinition.getDeployId();
    			if(deployId!=null&&!"".equals(deployId)){
    				ProUserAssign proUserAssign = proUserAssignService.getByDeployIdFlowNodeKey(deployId, flowNodeKey);
    				if(proUserAssign!=null){
    					activityName = proUserAssign.getActivityName();
    				}
    			}
    		}
    		if(searchType!=null&&!"".equals(searchType)&&"byUserId".equals(searchType)){
    			String userId = ContextUtil.getCurrentUserId().toString();
    			tasksList = flowTaskService.getTasksByProcessNameActivityNameUserId(processName, activityName, userId, pb,projectName,projectNumber,customerName);
    		}else{
    			tasksList = flowTaskService.getTasksByProcessNameActivityName(processName, activityName, pb,projectName,projectNumber,customerName);
    		}
    		long totalCount=pb.getTotalItems();
    		if(tasksList!=null){
        		for(int i=0;i<tasksList.size();i++){
        			Map<String, Object> map = new HashMap<String, Object>();
        			VSmallloanProject vp = null;
        			TaskInfo info = tasksList.get(i);
        			if(info.getPiId()!=null){	
        				List<VSmallloanProject>	sList = vSmallloanProjectService.getByPiIdAndDate(info.getPiId(), startDate, endDate);
    					if(sList!=null&&sList.size()!=0){
    						vp = sList.get(0);
    					}else{
    						totalCount=totalCount-1;
    					}
    				}else{
    					ProcessForm pf = processFormService.getByTaskId(info.getTaskId().toString());
        				if(null!=pf){
        					vp=vSmallloanProjectService.getByRunIdAndDate(pf.getRunId(), startDate, endDate);	
        				}
        				if(vp==null){
        					totalCount=totalCount-1;
        				}
    				}
        			if(vp!=null){
        				String appUserId=vp.getBusinessManager();
        				String user[]=appUserId.split(",");
        				String appUserName="";
        				for(int j=0;j<user.length;j++){
        					AppUser appuser=appUserService.get(Long.parseLong(user[j]));
        					if(null!=appuser){
        						appUserName=appUserName+appuser.getFullname()+",";
        					}
        				}
        				if(!"".equals(appUserName)){
        					appUserName=appUserName.substring(0,appUserName.length()-1);
        				}
        				vp.setBusinessManagerValue(appUserName);
        			}
        			
        			map.put("task", info);
        			map.put("vSmallloanProject", vp);
        			if(vp!=null){
        				allList.add(map);
        			}
        		}
        	}
        	StringBuffer buff = new StringBuffer("{success:true,'totalCounts':");
        	buff.append(totalCount);
        	buff.append(",result:");
    		JSONSerializer json = JsonUtil.getJSONSerializerDateByFormate("yyyy-MM-dd HH:mm:ss");
    		buff.append(json.serialize(allList));
    		buff.append("}");
    		jsonString=buff.toString();
    	}
    	return SUCCESS;
    }
    
	/**
	 * 按分页取得目前所有的正在进行的任务
	 * @return
	 */
	public String moreFeildList(){
		PagingBean pb = null;
		pb = new PagingBean(start, limit);
		List<TaskInfo> tasks=flowTaskService.getCurrentTaskByParameter(getRequest(), pb);
		setJsonString(gsonFormat(tasks, pb.getTotalItems()));
		return SUCCESS;
	}
	
	/**
	 * 将当前查询任务导出为excel
	 */
	public void processExportExcel(){
		try {
			PagingBean pb = null;
			pb = new PagingBean(0, 1000000000);
			List<TaskInfo> tasks=flowTaskService.getCurrentTaskByParameter(getRequest(), pb);
			String [] tableHeader = {"序号","项目名称","任务名称","处理人","任务分配时间","到期时间"};
			ExcelHelper.exportProcesstask(tasks,tableHeader,"流程任务工作台监控");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取全部的流程任务
	 * @return
	 */
	public String getAllProcess(){
		PagingBean pb=null;
		pb = new PagingBean(start, limit);
		List<ProcessForm> list= processFormService.allProcessTask(this.getRequest(),pb);
		setJsonString(gsonFormat(list, pb.getTotalItems()));
		return SUCCESS;
	}
	/**
	 * 将全部的流程任务导出到Excel中
	 */
	public void allExportExcel(){
		try {
			PagingBean pb = null;
			pb = new PagingBean(0, 1000000000);
			List<ProcessForm> list= processFormService.allProcessTask(this.getRequest(),pb);
			String [] tableHeader = {"序号","任务状态","项目名称","任务名称","处理人","任务分配时间","到期时间","实际完成时间","耗时","异常状态"};
			ExcelHelper.exportAllProcesstask(list,tableHeader,"流程任务综合监控");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getAllCompleteProcess(){
		PagingBean pb=null;
		pb = new PagingBean(start, limit);
		List<ProcessForm> list= processFormService.allCompleteProcessTask(this.getRequest(),pb);
		setJsonString(gsonFormat(list, pb.getTotalItems()));
		return SUCCESS;
	}
    
	/**
	 * 将已经完成的流程任务导出到Excel中
	 */
	public void completeExportExcel(){
		try {
			PagingBean pb = null;
			List<ProcessForm> list= processFormService.allProcessTask(this.getRequest(),pb);
			String [] tableHeader = {"序号","项目名称","任务名称","处理人","任务分配时间","到期时间","实际完成时间","耗时","异常状态"};
			ExcelHelper.exportCompleteProcesstask(list,tableHeader,"流程任务合规性监控");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectNumber() {
		return projectNumber;
	}

	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
	}

	public String getIsSuspendedProject() {
		return isSuspendedProject;
	}

	public void setIsSuspendedProject(String isSuspendedProject) {
		this.isSuspendedProject = isSuspendedProject;
	}

	public String getAssignId() {
		return assignId;
	}

	public void setAssignId(String assignId) {
		this.assignId = assignId;
	}

	public String getFlowNodeKey() {
		return flowNodeKey;
	}

	public void setFlowNodeKey(String flowNodeKey) {
		this.flowNodeKey = flowNodeKey;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public List<Long> getProxyUser() {
		return proxyUser;
	}

	public void setProxyUser(List<Long> proxyUser) {
		this.proxyUser = proxyUser;
	}
}
