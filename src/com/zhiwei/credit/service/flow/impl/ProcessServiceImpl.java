package com.zhiwei.credit.service.flow.impl;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.jbpm.api.ExecutionService;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.TaskService;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.task.ParticipationImpl;
import org.jbpm.pvm.internal.task.TaskImpl;

import com.dmkj.webservice.WebServiceUtil;
import com.messageAlert.service.SmsSendService;
import com.zhiwei.core.SendEmailSMSThread;
import com.zhiwei.core.jms.MailMessageProducer;
import com.zhiwei.core.model.DynaModel;
import com.zhiwei.core.model.MailModel;
import com.zhiwei.core.service.DynamicService;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.StringUtil;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.action.flow.FlowRunInfo;
import com.zhiwei.credit.dao.creditFlow.fileUploads.FileFormDao;
import com.zhiwei.credit.dao.flow.ProDefinitionDao;
import com.zhiwei.credit.dao.flow.ProcessFormDao;
import com.zhiwei.credit.dao.flow.TaskDao;
import com.zhiwei.credit.dao.system.AppUserDao;
import com.zhiwei.credit.entity.Mail;
import com.zhiwei.credit.model.creditFlow.fileUploads.FileForm;
import com.zhiwei.credit.model.flow.ProDefinition;
import com.zhiwei.credit.model.flow.ProcessForm;
import com.zhiwei.credit.model.flow.ProcessRun;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.creditFlow.common.CreditProjectService;
import com.zhiwei.credit.service.flow.FlowFormService;
import com.zhiwei.credit.service.flow.JbpmService;
import com.zhiwei.credit.service.flow.ProDefinitionService;
import com.zhiwei.credit.service.flow.ProcessRunService;
import com.zhiwei.credit.service.flow.ProcessService;
import com.zhiwei.credit.service.flow.RunDataService;
import com.zhiwei.credit.service.sms.util.SmsSendUtil;
import com.zhiwei.credit.service.system.AppUserService;


// 流程整体控制(含实例,应该分离):启动,下一结点,初始化,获取定义/实例
//三大功能，流程，流程实例,通知/请求

public class ProcessServiceImpl implements ProcessService{
	
	private final Log logger=LogFactory.getLog(ProcessServiceImpl.class);
	
	public ProcessServiceImpl() {}
	
	@Resource
	private ProcessRunService processRunService;
	@Resource
	ProDefinitionService proDefinitionService;
	@Resource
	private JbpmService jbpmService;
	@Resource
	private FlowFormService flowFormService;
	@Resource
	private RunDataService runDataService;
	@Resource
	private ExecutionService executionService;
	@Resource
	private ProcessFormDao processFormDao;
	@Resource
	private CreditProjectService creditProjectService;
	@Resource
	private TaskService taskService;
	@Resource
	private TaskDao taskDao;
	@Resource
	private FileFormDao fileFormDao;
	@Resource
	private ProDefinitionDao proDefinitionDao;
	@Resource
	private AppUserDao appUserDao;
	@Resource
	private SmsSendService smsSendService;

	/**
	 * 启动工作流  传入defId
	 * @param request
	 * @return
	 * @throws Exception 
	 * 
	 * 获取流程运行情况
	 * 1.前置/后置处理
	 * 2.保存流程
	 * 3.启动流程
	 * 4.通知
	 * 5.返沪i
	 * 
	 */
	
	public Map doStartFlow(HttpServletRequest request) throws Exception{
		FlowRunInfo startInfo=getFlowRunInfo(request);
		ProcessRun processRun=null;
		String useTemplate=request.getParameter("useTemplate");
		
		//若在提交参数中指定启动工作流前需要预处理
		int result=invokeHandler(startInfo, "PRE");
		
		if(result==-1 || result>=1){//正常
			DynaModel entity=null;
			if(!"true".equals(useTemplate)){
				//保存业务数据
				entity=flowFormService.doSaveData(startInfo);
				if(entity!=null){
					//把业务数据也放至流程中
					startInfo.getVariables().putAll(entity.getDatas());
				}
			}
			//启动流程
			processRun=jbpmService.doStartProcess(startInfo);
			processRun.setSubject(String.valueOf(startInfo.getBusMap().get("subjectName")));
			if("true".equals(useTemplate)){
			    startInfo.getVariables().putAll(BeanUtil.getMapFromRequest(request));
			}
			//保存后，把流程中相关的变量及数据全部提交至run_data表中，以方便后续的展示
			runDataService.saveFlowVars(processRun.getRunId(), startInfo.getVariables());
			//11111111111
			if (processRun != null && processRun.getPiId() != null) {
				PagingBean pb =new PagingBean(0,5);
				List<TaskImpl> list = taskDao.getTasksByUserIdProcessName(ContextUtil.getCurrentUser().getUserId().toString(), null,pb, null, null, processRun.getPiId());
				if(list!=null&&list.size()!=0){
					String taskId = list.get(0).getId();
					String activityName = list.get(0).getActivityName();
					startInfo.getBusMap().put("taskId",taskId);
					startInfo.getBusMap().put("activityName",activityName);
				}
			}
			//11111111111
			
			//加上，以方便第三方业务读取流程相关的数据
			startInfo.setProcessRun(processRun);
			
			//发送邮件或短信通知相关人员
			//启动新线程执行发送邮件或短信通知相关人员 add by lu 2012.06.15
			if("slDirectorOpinionFlow".equals(processRun.getProcessName())){
				Map busMap = new HashMap();
				busMap.put("isSlDirectorOpinionFlow", "true");
				startInfo.setBusMap(busMap);
			}
			Thread thread = new SendEmailSMSThread(processRun,startInfo);
			thread.start();
			//notice(processRun,startInfo);
			
			if(entity!=null){
				//更新runId，通过runId，可以取到该审批业务的所有审批信息 
				try{
					entity.set("runId", processRun.getRunId());
					DynamicService service=BeanUtil.getDynamicServiceBean((String)entity.get(FlowRunInfo.ENTITY_NAME));
					service.save(entity.getDatas());
				}catch(Exception ex){
					ex.printStackTrace();
					logger.debug("error:" + ex.getMessage());
				}
			}
		}
		//若在提交参数中指定启动工作流后需要后处理
		invokeHandler(startInfo,"AFT");
		//update by lu 2011.11.14:把启动项目后的项目名称、项目编号放到busMap中返回到页面进行提示。
		return startInfo.getBusMap();
		//return processRun;
	}
	/**
	 * 完成任务，并且进入下一步
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	public ProcessRun doNextFlow(HttpServletRequest request) throws Exception{
		
		//用不用模板??
		String useTemplate=request.getParameter("useTemplate");
		FlowRunInfo nextInfo=getFlowRunInfo(request);
		
		//以下方法针对追回的任务的taskId进行处理。add by lu 2012.07.12 start
		//String isJump = nextInfo.getRequest().getParameter("isJump");
		//if(isJump!=null&&"1".equals(isJump)){
		//jbpm获取任务
		Task newTask = taskService.getTask(nextInfo.getTaskId());
		if(newTask==null){
			//追回id???
			String newTaskId = creditProjectService.getNewTaskId(nextInfo.getTaskId());
			if(newTaskId!=null&&!"".equals(newTaskId)){
				ProcessForm pf = processFormDao.getByTaskId(nextInfo.getTaskId());
				if(pf!=null){
					pf.setStatus(ProcessForm.STATUS_PASS);
				}
				nextInfo.setTaskId(newTaskId);
			}
		}////end
		ProcessRun processRun=null;
		
		try{
			int result=invokeHandler(nextInfo, "PRE");
			if(result==-1 || result>=1){//正常
				if(!"true".equals(useTemplate)){
					//保存业务数据
					DynaModel entity=flowFormService.doSaveData(nextInfo);
					if(entity!=null){
						//把业务数据也放至流程中
						nextInfo.getVariables().putAll(entity.getDatas());
					}
				}
				//保存流程数据--------------------------子流程
				processRun= jbpmService.doNextStep(nextInfo);
				
				//begin @@
				String[] assignIds=request.getParameterValues("flowAssignId");
				if(null!=assignIds && null!=assignIds[0] && !"".equals(assignIds[0])){
					String flowAssignId=nextInfo.getTransitionName()+"|"+assignIds[0];
					BeanUtil.getMapFromRequest(request).put("flowAssignId",flowAssignId);
				}
				//end   @@
				if("true".equals(useTemplate)){
				    nextInfo.getVariables().putAll(BeanUtil.getMapFromRequest(request));
				}
    			//保存后，把流程中相关的变量及数据全部提交至run_data表中，以方便后续的展示
    			runDataService.saveFlowVars(processRun.getRunId(), nextInfo.getVariables());
				
				nextInfo.setProcessRun(processRun);
				
				//启动新线程执行发送邮件或短信通知相关人员 add by lu 2012.06.15
				Thread thread = new SendEmailSMSThread(processRun,nextInfo);
				thread.start();
				//发送短信
				sendShortMessage( processRun,nextInfo,newTask);
			}
			//执行之后的动作
			invokeHandler(nextInfo,"AFT");
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return processRun;
	}
	/**
	 * 消息提醒
	 * 流程节点提交处的短信息提醒功能
	 * 提醒说明：通知已经办理过此项目的人员
	 * 消息格式：项目名称+项目编号+已经通过了节点名称
	 * add by luyue 2015-08-17
	 */
	public void sendShortMessage(ProcessRun processRun,FlowRunInfo flowRunInfo,Task task){
		String sendMsg = flowRunInfo.getRequest().getParameter("sendMsg");//是否勾了发送消息
		String sendMail = flowRunInfo.getRequest().getParameter("sendMail");//是否勾了发送消息
		String signalName = flowRunInfo.getRequest().getParameter("signalName");//所提交结点的名称
		String taskId = flowRunInfo.getRequest().getParameter("taskId");
		ProcessDefinition processDefinition=jbpmService.getProcessDefinitionByPdId(processRun.getPdId());
		AppUser appUser = ContextUtil.getCurrentUser();
		Long senderId = appUser.getUserId();  //发送人ID
		 //最后一个节点发送的内容
		ProcessForm pf = processFormDao.getByTaskId(taskId);
		String content = "";
		SmsSendUtil sms = new SmsSendUtil();
		String projectName=processRun.getSubject();
		if(signalName.contains("完成")||signalName.contains("结束")||signalName.contains("终止")||signalName.contains("制定资金方案")||signalName.contains("债权匹配")){
			String flowName=proDefinitionDao.getDefByKey(processRun.getProcessName()).getName(); // 流程名称
			List<Long> allCreatorList = processFormDao.getByRunIdAllCreator(processRun.getRunId());
			String s = ""; //接收人ID串
			//拼串
			for(Long str :allCreatorList){
				if(str!=null&&!"null".equals(str)&&!"".equals(str)){
					AppUser user=appUserDao.get(str);
					System.out.println("发送的手机号码="+user.getMobile());
					if(null!=user.getMobile() && !"".equals(user.getMobile())){
						Map<String, String> map = new HashMap<String, String>();
						map.put("telephone", user.getMobile());
						map.put("${projectName}", projectName);
						map.put("${projNumber}", processRun.getProjectNumber());
						map.put("${flowName}",flowName);
			        	if(signalName.contains("完成") || signalName.contains("债权匹配")){
			        		map.put("key","sms_flowpass");
							//sms.sms_projectFinish(user.getMobile(), projectName,processRun.getProjectNumber(), flowName, "1");//发送短信
						}else if(signalName.contains("结束")){
							map.put("key","sms_flowfinish");
							//sms.sms_projectFinish(user.getMobile(), projectName,processRun.getProjectNumber(), flowName, "2");//发送短信
						}else if(signalName.contains("终止")){
							map.put("key","sms_flowstop");
							//sms.sms_projectFinish(user.getMobile(), projectName,processRun.getProjectNumber(), flowName, "3");//发送短信
						} else if(signalName.contains("制定资金方案")){
							map.put("key","sms_flowpass");
							//sms.sms_projectFinish(user.getMobile(), projectName,processRun.getProjectNumber(), flowName, "1");//终审走完后发送短信
						}
			        	smsSendService.smsSending(map);
					}
				}
			}
		}else if("true".equals(sendMsg)){
		     //下一个节点为同步节点，拿到每一个节点的任务名和执行人，分别发送信息
		 if(null!=signalName && !"".equals(signalName) && signalName.indexOf(",")>=0){
	        	String strs []=signalName.split(",");
	        	for(int i=0;i<strs.length;i++){
	        		Set<AppUser>  appuser1=jbpmService.getNodeHandlerUsers(processDefinition, strs[i],processRun.getUserId());
	        		if(null!=appuser1 && appuser1.size()>0){
	        			Iterator it1=appuser1.iterator();
	        			while (it1.hasNext()) {  
	        				AppUser user = (AppUser) it1.next(); 
	        				
	        				if(null!=user.getMobile() && !"".equals(user.getMobile())){
		        				Map<String, String> map = new HashMap<String, String>();
								map.put("telephone", user.getMobile());
								map.put("${projectName}", projectName);
								map.put("${signalName}", signalName);
								map.put("key","sms_nextnoderemind");
								smsSendService.smsSending(map);
		    					//sms.sms_nextNodeRemind(user.getMobile(), projectName, signalName);
		    				 }
	        			}  
	        		}
	        	}
	        }else{
	        	Set<AppUser>  appuser=jbpmService.getNodeHandlerUsers(processDefinition, signalName,processRun.getUserId());
	    		if(null!=appuser && appuser.size()>0){
	    			Iterator it=appuser.iterator();
	    			while (it.hasNext()) {  
	    				AppUser user = (AppUser) it.next(); 
	    				  if(null!=user.getMobile() && !"".equals(user.getMobile())){
	    					  Map<String, String> map = new HashMap<String, String>();
							  map.put("telephone", user.getMobile());
							  map.put("${projectName}", projectName);
							  map.put("${signalName}", signalName);
							  map.put("key","sms_nextnoderemind");
							  smsSendService.smsSending(map);
	    					 // sms.sms_nextNodeRemind(user.getMobile(), projectName, signalName);
	    				  }
	    				}  
	    		}
	    		
	        }
		}
		if(sendMail.equals("true")){//发送邮件
			if(flowRunInfo.isSendMail()){
				//发送邮件
				if(appUser.getEmail()!=null){
					MailMessageProducer mailMessageProducerThread = (MailMessageProducer) AppUtil.getBean("mailMessageProducer");
					if(logger.isDebugEnabled()){
						logger.info("Notice " + appUser.getFullname() + " by mail:" + appUser.getEmail());
					}
					 Date curDate=new Date();
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
					String curDateStr=sdf.format(curDate);
					String dueDateStr = sdf.format(task.getDuedate());
					String tempPath="mail/flowMail.vm";
					Map model=new HashMap();
					model.put("curDateStr", curDateStr);
					model.put("dueDateStr", dueDateStr);
					model.put("appUser", appUser);
					model.put("signalName", signalName);
					model.put("projectName", processRun.getSubject());
					model.put("projectNumber", processRun.getProjectNumber());
					String subject="来自"+AppUtil.getCompanyName()+"办公系统的待办任务提醒";
					model.put("content", subject);
					MailModel mailModel=new MailModel();
					mailModel.setMailTemplate(tempPath);
					mailModel.setTo(appUser.getEmail());
					mailModel.setSubject(subject);
					mailModel.setMailData(model);
					//把邮件加至发送列队中去
					mailMessageProducerThread.send(mailModel);
				}
			}
		}
	}
	
	/**
	 * 使用邮件或短信通知相关的人员处理
	 * @param processRun
	 * @param flowInfo
	 * notice方法由原来的private修改为public
	 * 发送邮件和发送短信由新线程SendEmailSMSThread执行，该线程调用notice方法。
	 * 线程启动时，因为没有通过spring容器，所以也就找不到对应的bean，
	 * 所以通过AppUserService appUserService=(AppUserService) AppUtil.getBean("appUserService");的方式重新获取bean。
	 * update by lu 2012.06.15
	 */
	public void notice(ProcessRun processRun,FlowRunInfo flowInfo){
		if(processRun.getPiId()==null) return;
		
		JbpmService jbpmServiceThread = (JbpmService) AppUtil.getBean("jbpmService");
		AppUserService appUserServiceThread = (AppUserService) AppUtil.getBean("appUserService");
		
		List<Task> taskList=jbpmServiceThread.getTasksByPiId(processRun.getPiId());
		
		if("zmNormalFlow".equals(processRun.getProcessName()) && "true".equals(flowInfo.getBusMap().get("isSendEmailBDRole"))){
			//给业务总监角色下的人员发送邮件或者短信
			String roleId = flowInfo.getBusMap().get("businessDirectorRoleId").toString();
			List<AppUser> userList = appUserServiceThread.findByRoleId(new Long(roleId));
			if(userList!=null&&userList.size()!=0){
				for(int i=0;i<userList.size();i++){
					AppUser user = userList.get(i);
					if(user!=null){
						sendMailNoticeZM(processRun,taskList.get(0), user,flowInfo);
					}
				}
			}
		}else{
			if("SmallLoan".equals(processRun.getBusinessType())&& "true".equals(flowInfo.getBusMap().get("isSlDirectorOpinionFlow"))){//主管意见流程
				List<ProcessRun> processRunList = processRunService.getByProcessNameProjectId(processRun.getProjectId(), processRun.getBusinessType(), "slDirectorOpinionFlow");
				if(processRunList!=null&&processRunList.size()!=0){
					ProcessRun run = processRunList.get(0);
					List<Task> directorOpinionList=jbpmServiceThread.getTasksByPiId(run.getPiId());
					flowInfo.setSendMail(true);
					this.sendEmailGetAssignByTask(directorOpinionList, run, flowInfo);//给主管意见的处理人员发送邮件、短信
				}
			}
			
			this.sendEmailGetAssignByTask(taskList,processRun,flowInfo);//给主流程的下个节点处理人员发送邮件、短信
		}
	}
	
	private void sendEmailGetAssignByTask(List<Task> taskList,ProcessRun processRun,FlowRunInfo flowInfo){
		try{
			AppUserService appUserServiceTask = (AppUserService) AppUtil.getBean("appUserService");
			for(Task task:taskList){
				TaskImpl taskImpl=(TaskImpl)task;
				if(taskImpl.getAssignee()==null){
					Iterator<ParticipationImpl> partIt= taskImpl.getAllParticipants().iterator();
					while(partIt.hasNext()){
						ParticipationImpl part=partIt.next();
						if(part.getGroupId()!=null && StringUtil.isNumeric(part.getGroupId())){
							//发送邮件
							List<AppUser> appUserList=appUserServiceTask.findByRoleId(new Long(part.getGroupId()));
							for(AppUser appUser:appUserList){
								sendMailNotice(processRun,taskImpl,appUser,flowInfo);
							}
						}else if(part.getUserId()!=null && StringUtil.isNumeric(part.getUserId())){
							AppUser appUser=appUserServiceTask.get(new Long(part.getUserId()));
							sendMailNotice(processRun,taskImpl,appUser,flowInfo);
						}
					}
				}else if(StringUtil.isNumeric(taskImpl.getAssignee())){				
					AppUser appUser=appUserServiceTask.get(new Long(taskImpl.getAssignee()));
					sendMailNotice(processRun,taskImpl,appUser,flowInfo);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("从任务获取发送邮件人员信息出错："+e.getMessage());
		}
	}
	
	/**
	 * 发送邮件及短信通知
	 * 
	 * @param task
	 * @param appUser
	 */
	private void sendMailNoticeZM(ProcessRun processRun, Task task,AppUser appUser, FlowRunInfo flowRunInfo) {

		try {
			
			//ShortMessageService shortMessageServiceThreadZM = (ShortMessageService) AppUtil.getBean("shortMessageService");
			MailMessageProducer mailMessageProducerThreadZM = (MailMessageProducer) AppUtil.getBean("mailMessageProducer");
			
			String creatorName = "";//项目尽调上报材料 节点处理人
			String creatorNameZG = "";//业务主管审核上报材料 节点处理人
			String endTimeZG = "";//业务主管审核上报材料 节点完成时间
			
			String dueDateStr = "";
		    Date curDate=new Date();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String curDateStr=sdf.format(curDate);
			if(task.getDuedate()!=null){
				dueDateStr = sdf.format(task.getDuedate());
			}
			//String shortContent= curDateStr + "待办事项(" + processRun.getSubject() + ")提交至审批环节("+ task.getName() + ")，请及时审批。";
			//shortMessageServiceThreadZM.save(AppUser.SYSTEM_USER, appUser.getUserId().toString(), shortContent, ShortMessage.MSG_TYPE_TASK);

			//ProcessForm processForm = processFormDao.getByRunIdTaskName(processRun.getRunId(), "项目尽调上报材料");
			ProcessForm processForm = processFormDao.getByRunIdFlowNodeKey(processRun.getRunId(), "zmnProjectMaterial");
			if(processForm!=null){
				creatorName = processForm.getCreatorName();
			}
			
			//ProcessForm pf = processFormDao.getByRunIdTaskName(processRun.getRunId(), "业务主管审核上报材料");
			ProcessForm pf = processFormDao.getByRunIdFlowNodeKey(processRun.getRunId(), "zmnCheckMaterial");
			if(pf!=null){
				creatorNameZG = pf.getCreatorName();
				SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				if(pf.getEndtime()!=null){
					endTimeZG = simpleDate.format(pf.getEndtime());
				}
			}
			
			//if (flowRunInfo.isSendMail()) {
			// 发送邮件
			if (appUser.getEmail() != null) {
				if (logger.isDebugEnabled()) {
					logger.info("Notice " + appUser.getFullname() + " by mail:" + appUser.getEmail());
				}
				String tempPath = "mail/zmBusinessDirectorMail.vm";
				Map model = new HashMap();
				MailModel mailModel=new MailModel();
				
				model.put("appUser", appUser);
				model.put("task", task);
				model.put("projectName", processRun.getSubject());
				model.put("projectNumber", processRun.getProjectNumber());
				model.put("creatorName", creatorName);
				model.put("creatorNameZG", creatorNameZG);
				model.put("endTime", endTimeZG);
				
				mailModel.setMailTemplate(tempPath);
				mailModel.setTo(appUser.getEmail());
				mailModel.setMailData(model);
				//把邮件加至发送列队中去
				mailMessageProducerThreadZM.send(mailModel);
			}
			//}
			if (flowRunInfo.isSendMsg()) {

				// 发送手机短信
				if (appUser.getMobile() != null && !appUser.getMobile().equals("")) {
					if (logger.isDebugEnabled()) {
						logger.info("Notice " + appUser.getFullname() + " by mobile:" + appUser.getMobile());
					}
					if (appUser.getMobile() != null && !appUser.getMobile().equals("")) {
						//添加对应客户自己的短信信息代码
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	/**
	 * 发送邮件及短信通知
	 * @param task
	 * @param appUser
	 */
	private void sendMailNotice(ProcessRun processRun,Task task,AppUser appUser,FlowRunInfo flowRunInfo){
		
	  try{
			MailMessageProducer mailMessageProducerThread = (MailMessageProducer) AppUtil.getBean("mailMessageProducer");
			
		  		String dueDateStr = "";
			    Date curDate=new Date();
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
				String curDateStr=sdf.format(curDate);
				if(task.getDuedate()!=null){
					dueDateStr = sdf.format(task.getDuedate());
				}
				String shortContent= curDateStr + "待办事项(" + processRun.getSubject() + ")提交至审批环节("+ task.getName() + ")，请及时审批。";
				//注释以下一行代码：update by lu 2012.05.23 启动项目、提交任务的时候不往站内消息表发送消息。
				//shortMessageService.save(AppUser.SYSTEM_USER, appUser.getUserId().toString(), shortContent, ShortMessage.MSG_TYPE_TASK);
				
				/*if(flowRunInfo.isSendMail()){
					//发送邮件
					if(appUser.getEmail()!=null){
						if(logger.isDebugEnabled()){
							logger.info("Notice " + appUser.getFullname() + " by mail:" + appUser.getEmail());
						}
						String tempPath="mail/flowMail.vm";
						Map model=new HashMap();
						model.put("curDateStr", curDateStr);
						model.put("dueDateStr", dueDateStr);
						model.put("appUser", appUser);
						model.put("task", task);
						model.put("projectName", processRun.getSubject());
						model.put("projectNumber", processRun.getProjectNumber());
						String subject="来自"+AppUtil.getCompanyName()+"办公系统的待办任务(" + processRun.getSubject() + "--" + task.getName() + ")提醒";
						model.put("content", subject);
						
						MailModel mailModel=new MailModel();
						mailModel.setMailTemplate(tempPath);
						mailModel.setTo(appUser.getEmail());
						mailModel.setSubject(subject);
						mailModel.setMailData(model);
						//把邮件加至发送列队中去
						mailMessageProducerThread.send(mailModel);
					}
				}*/
				//流程节点用新的方法发送短信
				/*if(flowRunInfo.isSendMsg()){
					//发送手机短信
					if(appUser.getMobile()!=null && !appUser.getMobile().equals("")){
						if(logger.isDebugEnabled()){
							logger.info("Notice " + appUser.getFullname() + " by mobile:" + appUser.getMobile());
						}
						if(appUser.getMobile()!=null && !appUser.getMobile().equals("")){
							String businessTypeStr="小额贷款项目";
							if("Guarantee".equals(processRun.getBusinessType())){
								businessTypeStr="企业贷款项目";
							}
							String cName="";//客户名称
							cName=processRun.getCustomerName();
							String content=appUser.getFullname()+"您好,您有一个新任务："+cName+businessTypeStr+task.getName()+"。请于"+dueDateStr+"前登录系统完成处理工作。";
							String ECCode=com.dmkj.webservice.PropertiesUtil.getProperty("companyCode");
							String AdminNum=com.dmkj.webservice.PropertiesUtil.getProperty("userName");
							String AdminPass=com.dmkj.webservice.PropertiesUtil.getProperty("passWord");
							String SmsType="0";
						    WebServiceUtil aa = new WebServiceUtil();
					        aa.sendSms(ECCode,AdminNum,AdminPass,appUser.getMobile(),content,SmsType);
						}
					}
				}*/
		  }catch (Exception e) {
			e.printStackTrace();
		  }
		
	}
	
	/**
	 * 初始化一个新的流程
	 * @return
	 */
	public ProcessRun getInitNewProcessRun(HttpServletRequest request){
		String defId=request.getParameter("defId");
		ProDefinition proDefinition=proDefinitionService.get(new Long(defId));
		
		return processRunService.getInitNewProcessRun(proDefinition);
	}
	
	/**
	 * 取得流程运行的相关信息
	 */
	protected FlowRunInfo getFlowRunInfo(HttpServletRequest request) {
		FlowRunInfo info=new FlowRunInfo(request);
		//Map<String, ParamField> fieldMap=getConstructFieldMap(request);
		//info.setParamFields(fieldMap);
		return info;
	}

	/**
	 * 取得流程定义
	 * @return
	 */
	protected ProDefinition getProDefinition(HttpServletRequest request){
		ProDefinition proDefinition=null;
		String defId=request.getParameter("defId");
		if(defId!=null){
			 proDefinition=proDefinitionService.get(new Long(defId));
		}else {
			String taskId=request.getParameter("taskId");
			ProcessRun processRun=processRunService.getByTaskId(taskId.toString());
			proDefinition=processRun.getProDefinition();
		}
		return proDefinition;
	}
	
	/**
	 * 流程执行前后触发
	 * @param flowRunInfo
	 * @param preAfterMethodFlag 前后标识，PRE代表前置，AFT代表后置
	 * @return 0 代表失败 1代表成功，-1代表不需要执行
	 */
	@Override
	public int invokeHandler(FlowRunInfo flowRunInfo,String preAfterMethodFlag) throws Exception{
		
		String handler=null;
		//前置方法
		if("PRE".equals(preAfterMethodFlag)){
			handler=flowRunInfo.getPreHandler();
		}else{//后置方法
			handler=flowRunInfo.getAfterHandler();
		}
		//没有指定方法
		if(handler==null) return -1;
			
		Integer result=0;
		
		String [] beanMethods=handler.split("[.]");
		if(beanMethods!=null){
			String beanId=beanMethods[0];
			String method=beanMethods[1];
			//触发该Bean下的业务方法
			Object serviceBean=AppUtil.getBean(beanId);
			if(serviceBean!=null){
				Method invokeMethod=serviceBean.getClass().getDeclaredMethod(method, new Class[]{FlowRunInfo.class});
				result= (Integer)invokeMethod.invoke(serviceBean, flowRunInfo);
			}
		}
		//为after 添加流程变量
		if("AFT".equals(preAfterMethodFlag) && flowRunInfo.getVariables().size()>0){
			String piId=flowRunInfo.getPiId();
			//add by lu 2011.11.14:把业务数据(projectId)保存到run_data表进行数据,在项目完成或终止后通过此projectId可以把流程与业务数据相关联。
			//在项目启动的时候只执行一次。而把此值保存到jbpm4_variable，则该值的生命周期只在流程实例运行期间。
			runDataService.saveFlowVars(flowRunInfo.getProcessRun().getRunId(), flowRunInfo.getVariables());//增加此行代码
			if(piId!=null){
				executionService.setVariables(piId, flowRunInfo.getFlowVars());
			}
		}
		
		return result;
	}
	
	@Override
	public void testGo(String key) {
		
		
	}
	
	/**
	 * 系统公用发送短信方法
	 */
	public void sendSystemEmail(){
		try{

			Mail cn = new Mail();
			List<String> tos = new ArrayList<String>();  //收件人地址
			//String promoteids = this.getRequest().getParameter("promoteids");
			
			//AppUser user=ContextUtil.getCurrentUser();
			/*String investPersonIds = this.getRequest().getParameter("investPersonIds");
			String[] invIds = investPersonIds.split(",");
			String investPersonNames = this.getRequest().getParameter("investPersonNames");
			FundsToPromote f=new FundsToPromote();
			f.setPromoteMonthod("邮件推介");
			f.setPromoteDate(new Date());
			f.setPromotePersonId(user.getUserId());
			f.setPromotePersonName(user.getFullname());
			//f.setInvestPersonId(Long.valueOf(invIds[i]));
			f.setInvestPersonName(investPersonNames);
			f.setTitle(fundsToPromote.getTitle());
			f.setPromoteContent(fundsToPromote.getPromoteContent());
			f.setProjectId(fundsToPromote.getProjectId());
			f.setFileId(fundsToPromote.getFileId());
			for(int i=0; i<invIds.length;i++){
				
				//InvestPerson invest = investPersonService.get(Long.valueOf(invIds[i]));
				tos.add(invest.getPostCode());
				
			}*/
			tos.add("455703140@qq.com");
			//String[] ids = promoteids.split(",");
			//List<FileForm> files = new ArrayList<FileForm>();
			Map<String, String> affixs = new HashMap<String, String>();
			FileForm file = fileFormDao.get(new Long(417));
			affixs.put(file.getFilename(), ServletActionContext.getRequest().getRealPath("/")+file.getFilepath());
			/*fundsToPromoteService.save(f);
			for(String fileid : ids){
				FileForm file = fileFormService.getFileForm(Integer.valueOf(fileid));
				affixs.put(file.getFilename(), ServletActionContext.getRequest().getRealPath("/")+file.getFilepath());
				FileForm file1 = new FileForm();
				file1.setArchiveConfirmRemark(file.getArchiveConfirmRemark());
				file1.setBusinessType(file.getBusinessType());
				file1.setContentType(file.getContentType());
				file1.setCreatetime(new Date());
				file1.setCreatorid(Integer.valueOf(user.getUserId().toString()));
				file1.setExtendname(file.getExtendname());
				file1.setFileAttachID(file.getFileAttachID());
				file1.setFilename(file.getFilename());
				file1.setFilepath(file.getFilepath());
				file1.setFilesize(file.getFilesize());
				file1.setIsArchiveConfirm(file.getIsArchiveConfirm());
				file1.setMark("FundsToPromote."+f.getFundsToPromoteId());
				file1.setProjId(file.getProjId());
				//file1.setRemark("FundsToPromote");
				file1.setSetname("已发邮件");
				file1.setWebPath(file.getWebPath());
				fileFormService.save(file1);
			}*/
			
			// 设置发件人地址、收件人地址和邮件标题
			cn.setAddress("455703140@qq.com", tos, "测试标题");
			cn.setContent("根据       字第　　 号借款合同，你单位(个人)于2013年07月03向我公司所借 贷款（大写）人民币壹万壹仟壹佰壹拾壹元整，现尚余0元将于2013年06月13日到期，请抓紧筹措资金按期归还借款，如到期未还，我公司即依照借款合同有关规定予以处理。");
			// 设置要发送附件的位置和标题
			
			cn.setAffixs(affixs);
			cn.send("smtp.qiye.163.com", "ms.credit@credit-heng.com", "zhongming123");
			/*String projectId = this.getRequest().getParameter("projectId");
			SlSmallloanProject slSmallloanProject = slSmallloanProjectService.get(Long.valueOf(projectId));
			slSmallloanProject.setLastPresentDate(new Date());
			slSmallloanProjectService.merge(slSmallloanProject);
			return SUCCESS;*/
		
			/*MailMessageProducer mailMessageProducerThread = (MailMessageProducer) AppUtil.getBean("mailMessageProducer");
			String tempPath="mail/commonMail.vm";
			Map model=new HashMap();
			model.put("content", "根据       字第　　 号借款合同，你单位(个人)于2013年07月03向我公司所借 贷款（大写）人民币壹万壹仟壹佰壹拾壹元整，现尚余0元将于2013年06月13日到期，请抓紧筹措资金按期归还借款，如到期未还，我公司即依照借款合同有关规定予以处理。");
			model.put("dueDateStr", dueDateStr);
			model.put("appUser", appUser);
			model.put("task", task);
			model.put("projectName", processRun.getSubject());
			model.put("projectNumber", processRun.getProjectNumber());
			String subject="标题标题标题标题标题标题标题标题";
			
			MailModel mailModel=new MailModel();
			mailModel.setMailTemplate(tempPath);
			mailModel.setTo("455703140@qq.com");
			mailModel.setSubject(subject);
			mailModel.setContent("根据       字第　　 号借款合同，你单位(个人)于2013年07月03向我公司所借 贷款（大写）人民币壹万壹仟壹佰壹拾壹元整，现尚余0元将于2013年06月13日到期，请抓紧筹措资金按期归还借款，如到期未还，我公司即依照借款合同有关规定予以处理。");
			mailModel.setMailData(model);
			//把邮件加至发送列队中去
			mailMessageProducerThread.send(mailModel);*/
		}catch(Exception e){
			e.printStackTrace();
			logger.error("发送邮件出错："+e.getMessage());
		}
	}
}
