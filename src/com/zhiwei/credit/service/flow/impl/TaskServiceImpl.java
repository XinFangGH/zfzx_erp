package com.zhiwei.credit.service.flow.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.jbpm.api.Execution;
import org.jbpm.pvm.internal.model.ExecutionImpl;
import org.jbpm.pvm.internal.task.TaskImpl;

import com.zhiwei.core.jbpm.pv.TaskInfo;
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.web.paging.PageBean;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.flow.ProUserAssignDao;
import com.zhiwei.credit.dao.flow.TaskDao;
import com.zhiwei.credit.model.flow.JbpmTask;
import com.zhiwei.credit.model.flow.ProDefinition;
import com.zhiwei.credit.model.flow.ProUserAssign;
import com.zhiwei.credit.model.flow.ProcessRun;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.flow.ProcessRunService;
import com.zhiwei.credit.service.flow.TaskService;
import com.zhiwei.credit.service.system.AppUserService;

public class TaskServiceImpl extends BaseServiceImpl<TaskImpl> implements TaskService{

	@Resource
	private ProcessRunService processRunService;
	@Resource
	private ProUserAssignDao proUserAssignDao;
	private TaskDao dao;
	public TaskServiceImpl(TaskDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Resource
	private AppUserService appUserService;
	
	public List<TaskImpl> getTasksByUserId(String userId,PagingBean pb){
		return dao.getTasksByUserId(userId,pb);
	}
	/**
	 * 取得所有任务
	 * @param taskName
	 * @param pb
	 * @return
	 */
	public List<TaskInfo> getAllTaskInfos(String taskName,PagingBean pb){
		List<TaskImpl> list=dao.getAllTasks(taskName, pb);
		List<TaskInfo> taskInfoList=constructTaskInfos(list);;
		return taskInfoList;
	}
	
	
	protected List<TaskInfo> constructTaskInfos(List<TaskImpl> taskImpls){
		List<TaskInfo> taskInfoList=new ArrayList<TaskInfo>();
		TaskInfo taskInfo=null;
		try{
		for(TaskImpl taskImpl:taskImpls){
			 taskInfo=new TaskInfo(taskImpl);
			if(taskImpl.getAssignee()!=null&&!taskImpl.getAssignee().trim().equalsIgnoreCase("null")){
				AppUser user=appUserService.get(new Long(taskImpl.getAssignee()));
				if(user!=null){
					taskInfo.setAssignee(user.getFullname());
				}
			}
			if(taskImpl.getSuperTask()!=null){
				taskImpl=taskImpl.getSuperTask();
			}
			ProcessRun processRun=processRunService.getByPiId(taskInfo.getPiId());
			if(processRun!=null){
				//taskInfo.setTaskName(processRun.getSubject() + "--" + taskImpl.getActivityName());
				taskInfo.setTaskName(processRun.getSubject());
				taskInfo.setActivityName(taskImpl.getActivityName());
				//查询该节点是否为会签节点
			    String deployId=processRun.getProDefinition().getDeployId();
				if(null!=deployId && !"".equals(deployId)){
					ProUserAssign passign=proUserAssignDao.getByDeployIdActivityName(deployId, taskImpl.getActivityName());
				   if(null!=passign){
					   taskInfo.setIsSigned(passign.getIsSigned());
				   }
				}
			}
			//显示任务，需要加上流程的名称
			taskInfoList.add(taskInfo);
			taskInfo=null;
		}
		}catch(Exception ex){
			logger.error(ex);
		}
		return taskInfoList;
	}
	/**
	 * 显示自定义的任务信息
	 */
	public List<TaskInfo> getTaskInfosByUserId(String userId,PagingBean pb){
		List<TaskImpl> list=getTasksByUserId(userId, pb);
//		List<TaskInfo> taskInfoList=new ArrayList<TaskInfo>();
//		for(TaskImpl taskImpl:list){
//			TaskInfo taskInfo=new TaskInfo(taskImpl);
//			if(taskImpl.getAssignee()!=null){
//				AppUser user=appUserService.get(new Long(taskImpl.getAssignee()));
//				taskInfo.setAssignee(user.getFullname());
//				
//			}
//			ProcessRun processRun=processRunService.getByPiId(taskImpl.getExecutionId());
//			if(processRun!=null){
//				taskInfo.setTaskName(processRun.getProDefinition().getName() + "--" + taskImpl.getActivityName());
//				taskInfo.setActivityName(taskImpl.getActivityName());
//			}
//			//显示任务，需要加上流程的名称
//			taskInfoList.add(taskInfo);
//		}
		
		return constructTaskInfos(list);
		
		
	}

	@Override
	public Set<Long> getHastenByActivityNameVarKeyLongVal(String activityName,
			String varKey, Long value) {
		List<JbpmTask> jtasks=dao.getByActivityNameVarKeyLongVal(activityName, varKey, value);
		Set<Long> userIds=new HashSet<Long>();
		for(JbpmTask jtask:jtasks){
			if(jtask.getAssignee()==null){
				List<Long> userlist=dao.getUserIdByTask(jtask.getTaskId());
				userIds.addAll(userlist);
				List<Long> groupList=dao.getGroupByTask(jtask.getTaskId());
				for(Long l:groupList){
					List<AppUser> uList=appUserService.findByRoleId(l);
					List<Long> idList=new ArrayList<Long>();
					for(AppUser appUser:uList){
						idList.add(appUser.getUserId());
					}
					userIds.addAll(idList);
				}
			}else{
				userIds.add(new Long(jtask.getAssignee()));
			}
		}
		return userIds;
	}
	@Override
	public List<TaskImpl> getCandidateTasks(String userId, PagingBean pb) {
		return dao.getCandidateTasks(userId, pb);
	}
	
	@Override
	public List<TaskImpl> getPersonTasks(String userId, PagingBean pb) {
		return dao.getPersonTasks(userId, pb);
	}
	
	/**
	 * 按主键查找execution实体
	 * @param dbid
	 * @return
	 */
	public Execution getExecutionByDbid(Long dbid){
		return dao.getExecutionByDbid(dbid);
	}
	
	/**
	 * 保存executionimpl
	 * @param executionImpl
	 */
	public void save(ExecutionImpl executionImpl){
		dao.save(executionImpl);
	}
	
	/**
	 * 去掉某个execution的子execution及其相关联的记录
	 * @param parentDbid
	 */
	public void removeExeByParentId(Long parentDbid){
		dao.removeExeByParentId(parentDbid);
	}
	
	/**
	 * 会签节点：根据同样名称和同样的创建时间获取父任务信息,为了获取ExecutionId(没有提供获取SUPERTASK_的方法和该属性)。
	 * @param activityName
	 * @param createTime
	 * add by lu 2011.12.14
	 */
	public List<TaskImpl> getTaskByNameAndCreateTime(String activityName,Date createTime){
		return dao.getTaskByNameAndCreateTime(activityName,createTime);
	}
	
	/**
	 * 删除任务：根据executionId查询类似并列任务、会签任务的子任务一并删除
	 * @param executionId 等同于process_run中的piId
	 * add by lu 2012.02.21
	 */
	public List<TaskImpl> getTaskByExecutionId(String executionId){
		return dao.getTaskByExecutionId(executionId);
	}
	
	/**
	 * 取得用户的对应的某个流程定义的任务列表
	 * @param userId
	 * @param processName
	 * @param PagingBean
	 * @return
	 * add by lu 2012.03.01
	 */
	public List<TaskInfo> getTasksByUserIdProcessNameTransfer(String userId,String processName,PagingBean pb,String projectName,String projectNumber){
		List<TaskImpl> list=getTasksByUserIdProcessName(userId,processName,pb,projectName,projectNumber);
		return constructTaskInfos(list);
	}
	
	/**
	 * 取得用户的对应的某个流程定义的任务列表
	 * @param userId
	 * @param processName
	 * @param PagingBean
	 * @return
	 * add by lu 2012.03.01
	 */
	public List<TaskImpl> getTasksByUserIdProcessName(String userId,String processName,PagingBean pb,String projectName,String projectNumber){
		return dao.getTasksByUserIdProcessName(userId, processName, pb,projectName,projectNumber,null);
	}
	
	/**
	 * 项目信息库-贷前项目列表-获取会签、同步任务
	 * @param piDbid
	 * @return
	 * add by lu 2012.03.28
	 */
	public List<TaskInfo> getSynchronousTasksByRunId(Long piDbid){
		List<TaskImpl> list = getSynchronousTasksByRunIdTransfer(piDbid);
		return constructTaskInfos(list);
	}
	
	/**
	 * 项目信息库-贷前项目列表-获取会签、同步任务
	 * @param piDbid
	 * @return
	 * add by lu 2012.03.28
	 */
	public List<TaskImpl> getSynchronousTasksByRunIdTransfer(Long piDbid){
		return dao.getSynchronousTasksByRunId(piDbid);
	}
	
	/**
	 * 获取当前流程实例的所有任务。需要根据创建时间排序
	 * @param piId
	 * @return
	 * add by lu 2012.05.02
	 */
	/*public List<TaskImpl> getTasksByPiId(String piId){
		return dao.getTasksByPiId(piId);
	}*/
	
	/**
	 * 根据流程对应的key、节点名称查询所有任务。
	 * @param processName
	 * @param activityName
	 * @return
	 * add by lu 2012.08.13
	 */
	public List<TaskInfo> getTasksByProcessNameActivityName(String processName,String activityName,PagingBean pb,String projectName,String projectNumber,String customerName){
		List<TaskImpl> list = dao.getTasksByProcessNameActivityName(processName, activityName, pb,projectName,projectNumber,customerName);
		return constructTaskInfos(list);
		//return dao.getTasksByProcessNameActivityName(processName, activityName,pb);
	}
	
	
	/**
	 * 根据流程对应的key、节点名称、节点处理人Id查询所有任务。
	 * @param processName
	 * @param activityName
	 * @param userId
	 * @return
	 * add by lu 2012.08.13
	 */
	public List<TaskInfo> getTasksByProcessNameActivityNameUserId(String processName,String activityName,String userId,PagingBean pb,String projectName,String projectNumber,String customerName){
		List<TaskImpl> list = dao.getTasksByProcessNameActivityNameUserId(processName, activityName, userId, pb,projectName,projectNumber,customerName);
		return constructTaskInfos(list);
		//return dao.getTasksByProcessNameActivityNameUserId(processName, activityName, userId,pb);
	}
	
	/**
	 * 根据流程的key和用户id获取相应的任务列表
	 * @param userId
	 * @param processName
	 * @return
	 * add by lu 2012.08.17
	 */
	public int  getAllByUserIdProcessName(String userId,String processName){
		return dao.getAllByUserIdProcessName(userId, processName);
	}
	
	/**
	 * 展期流程、利率变更、提前还款等子流程，对应的第一个任务是否为当前启动流程的用户，
	 * 如果是则返回taskId和activityName，直接打开对应任务的页签进行任务处理。
	 * @param piId
	 * @param userId
	 * @return
	 * add by lu 2013.06.05
	 */
	public String currentTaskIsStartFlowUser(String piId,String userId,String projectName){
		String str = "";
		PagingBean pb =new PagingBean(0,5);
		List<TaskImpl> list = dao.getTasksByUserIdProcessName(userId, null, pb, null, null, piId);
		if(list!=null&&list.size()!=0){
			String taskId = list.get(0).getId();
			String activityName = list.get(0).getActivityName();
			str="taskId:'"+taskId+"',activityName:'"+activityName+"'"+",projectName:'"+projectName+"'";
		}else{
			str="taskId:'1',activityName:'',projectName:''";
		}
		return str;
	}
	@Override
	public List<TaskImpl> getTaskByExecutionId(String piId, String state) {
		return dao.getTaskByExecutionId(piId,state);
	}
	@Override
	public List<TaskInfo> getCurrentTaskByParameter(HttpServletRequest request, PagingBean pb) {
		// TODO Auto-generated method stub
		List<TaskImpl> list=dao.getCurrentTaskByParameter(request, pb);
		List<TaskInfo> taskInfoList=constructTaskInfos(list);;
		return taskInfoList;
	}
	@Override
	public void findTaskByName(Map<String,String> map,PageBean<TaskInfo> pageBean) {
		dao.findTaskByName(map,pageBean);
	}
	
	/**
	 * 显示自定义的任务信息
	 */
	public List getMyMobileTaskByUserId(String userId,String processName,PagingBean pb){
		List<TaskImpl> list=dao.getTasksByUserIdProcessName(userId, processName, pb);
		return pb!=null?constructTaskInfos(list):list;
	}
}
