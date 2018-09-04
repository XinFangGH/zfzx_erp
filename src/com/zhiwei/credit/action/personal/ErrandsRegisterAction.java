package com.zhiwei.credit.action.personal;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.jbpm.api.task.Task;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zhiwei.core.Constants;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.jbpm.pv.ParamField;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.JsonUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.action.flow.FlowRunInfo;
import com.zhiwei.credit.action.flow.ProcessActivityAssistant;
import com.zhiwei.credit.model.flow.ProDefinition;
import com.zhiwei.credit.model.flow.ProcessForm;
import com.zhiwei.credit.model.flow.ProcessModule;
import com.zhiwei.credit.model.flow.ProcessRun;
import com.zhiwei.credit.model.personal.ErrandsRegister;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.flow.JbpmService;
import com.zhiwei.credit.service.flow.ProcessModuleService;
import com.zhiwei.credit.service.flow.ProcessRunService;
import com.zhiwei.credit.service.personal.ErrandsRegisterService;
import com.zhiwei.credit.service.system.AppUserService;

import flexjson.JSONSerializer;
/**
 * 
 * @author 
 *
 */
public class ErrandsRegisterAction extends BaseAction{
	
	@Resource
	private ProcessRunService processRunService;
	
	@Resource
	private JbpmService jbpmService;
	
	@Resource
	private ErrandsRegisterService errandsRegisterService;
	
	@Resource
	private ProcessModuleService processModuleService;
	
	@Resource
	private  AppUserService appUserService;
	private ErrandsRegister errandsRegister;
	
	private Long dateId;

	public Long getDateId() {
		return dateId;
	}

	public void setDateId(Long dateId) {
		this.dateId = dateId;
	}

	public ErrandsRegister getErrandsRegister() {
		return errandsRegister;
	}

	public void setErrandsRegister(ErrandsRegister errandsRegister) {
		this.errandsRegister = errandsRegister;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		QueryFilter filter=new QueryFilter(getRequest());
		//filter.addFilter("Q_appUser.userId_L_EQ", ContextUtil.getCurrentUserId().toString());
		List<ErrandsRegister> list= errandsRegisterService.getAll(filter);
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:[");		
		
		JSONSerializer serializer=JsonUtil.getJSONSerializer("startTime","endTime");
		for(ErrandsRegister er : list){
			buff.append(serializer.prettyPrint(true).serialize(er));
			if(er.getRunId()!=null){
				
				ProcessRun processRun=processRunService.get(er.getRunId());
				if(processRun.getPiId()!=null){
					buff.deleteCharAt(buff.length()-1);//减掉"}",把task任务加进来
					List<Task> curTasks=jbpmService.getTasksByPiId(processRun.getPiId());
					buff.append(",tasks:[");
					for(Task task:curTasks){
						buff.append("{taskId:").append(task.getId()).append(",taskName:").append(gson.toJson(task.getName()));
						if(task.getAssignee()!=null){
							AppUser user=appUserService.get(new Long(task.getAssignee()));
							if(user!=null){
								buff.append(",userId:").append(task.getAssignee()).append(",fullname:").append(gson.toJson(user.getFullname()));
							}
						}
						buff.append("},");
					}
					if(curTasks.size()>0){
						buff.deleteCharAt(buff.length()-1);
					}
					buff.append("]");
					buff.append("}");//把减掉的 } 加回来
				}
			}
			buff.append(",");
		}
		
		if(list.size()>0){
			buff.deleteCharAt(buff.length()-1);//减掉最后一个","
		}
		buff.append("]}");
		
		jsonString=buff.toString();
		
		return SUCCESS;
	}
	/**
	 * 批量删除
	 * @return
	 */
	public String multiDel(){
		
		String[]ids=getRequest().getParameterValues("ids");
		if(ids!=null){
			for(String id:ids){
				errandsRegisterService.remove(new Long(id));
			}
		}
		
		jsonString="{success:true}";
		
		return SUCCESS;
	}
	
	/**
	 * 显示详细信息
	 * @return
	 */
	public String get(){
		ErrandsRegister errandsRegister=errandsRegisterService.get(dateId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(errandsRegister));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		//是否为新记录
//		boolean isNew=errandsRegister.getDateId()==null? true:false;
		
		if(errandsRegister.getDateId()!=null){
			
			ErrandsRegister org=errandsRegisterService.get(errandsRegister.getDateId());
			
			try{
				BeanUtil.copyNotNullProperties(org, errandsRegister);
				errandsRegisterService.save(org);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
			
		}else{
			errandsRegister.setAppUser(ContextUtil.getCurrentUser());
			errandsRegister.setStatus((short)0);//未通过
			errandsRegisterService.save(errandsRegister);
			
			Map<String,ParamField> fieldMap=constructStartFlowMap(errandsRegister);
			//取得请假流程的定义
			ProcessModule processModule = processModuleService.getByKey("ERRANDS_REGISTER");
			ProDefinition proDefinition = null;//jbpmService.getProDefinitionByKey(Constants.FLOW_LEAVE_KEY);
			if(processModule !=null){
				proDefinition = processModule.getProDefinition();
			}
			if(proDefinition!=null){
				ProcessRun processRun=processRunService.getInitNewProcessRun(proDefinition);
				//流程的启动表单信息
				ProcessForm processForm=new ProcessForm();
				processForm.setActivityName("开始");
				processForm.setProcessRun(processRun);
				
				//流程启动的信息
				FlowRunInfo runInfo=new FlowRunInfo();
				runInfo.setParamFields(fieldMap);
				runInfo.setStartFlow(true);
				runInfo.setDefId(proDefinition.getDefId().toString());
				runInfo.getVariables().put("dateId",errandsRegister.getDateId());
				//设置审批人
				runInfo.setdAssignId(errandsRegister.getApprovalId().toString());
				//启动流程
				//processRunService.saveProcessRun(processRun, processForm,runInfo);
				processRun = jbpmService.doStartProcess(runInfo);
				
				//把流程RUNID绑定在请假外出表
				errandsRegister.setRunId(processRun.getRunId());
				errandsRegisterService.save(errandsRegister);
				
			}else{
				logger.error("请假流程没有定义！");
			}
		}

		setJsonString("{success:true}");
		return SUCCESS;
	}
	
	
	protected Map<String, ParamField> constructStartFlowMap(ErrandsRegister register){
		
		String activityName="开始";
		String processName="请假外出";

		Map<String,ParamField> map=ProcessActivityAssistant.constructFieldMap(processName, activityName);

		ParamField pfDateId=map.get("dateId");
		
		if(pfDateId!=null){
			pfDateId.setValue(register.getDateId().toString());
		}
		
		SimpleDateFormat sdf=new SimpleDateFormat(Constants.DATE_FORMAT_FULL);
		ParamField pfOption=map.get("reqDesc");
		if(pfOption!=null){
			pfOption.setValue(register.getDescp());
		}
		
		ParamField pfStartTime=map.get("startTime");
		if(pfStartTime!=null){
			pfStartTime.setValue(sdf.format(register.getStartTime()));
		}
		
		ParamField pfEndTime=map.get("endTime");
		if(pfEndTime!=null){
			pfEndTime.setValue(sdf.format(register.getEndTime()));
		}
		
		ParamField pfApprovalName=map.get("approvalName");
		if(pfApprovalName!=null){
			pfApprovalName.setValue(register.getApprovalName());
		}

		return map;
	}
	
}
