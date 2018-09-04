package com.zhiwei.credit.action.creditFlow.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.creditFlow.common.ProjectCommentsHistory;
import com.zhiwei.credit.model.system.GlobalType;
import com.zhiwei.credit.service.creditFlow.common.ProjectCommentsHistoryService;

public class ProjectCommentsHistoryAction extends BaseAction {

	@Resource
	private ProjectCommentsHistoryService projectCommentsHistoryService;
	
	
	private Long projectId;
	private String businessType;
	private String componentKey;
	private ProjectCommentsHistory projectCommentsHistory;
	
	public String getList(){
		List<ProjectCommentsHistory> list = projectCommentsHistoryService.getList(projectId, businessType, componentKey);
		if(null!=list){
			StringBuffer buff = new StringBuffer("{success:true,result:");	
			Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			buff.append(gson.toJson(list));
			buff.append("}");
			
			jsonString=buff.toString();
		}
		return SUCCESS;
	}
	public String save(){
		if(projectCommentsHistory!=null){
			if(projectCommentsHistory.getUserId()==null){
				projectCommentsHistory.setUserId(ContextUtil.getCurrentUserId());
			}
			projectCommentsHistory.setCommitDate(new Date());
			projectCommentsHistoryService.save(projectCommentsHistory);
		}
		return SUCCESS;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	public String getComponentKey() {
		return componentKey;
	}
	public void setComponentKey(String componentKey) {
		this.componentKey = componentKey;
	}
	public ProjectCommentsHistory getProjectCommentsHistory() {
		return projectCommentsHistory;
	}
	public void setProjectCommentsHistory(
			ProjectCommentsHistory projectCommentsHistory) {
		this.projectCommentsHistory = projectCommentsHistory;
	}
	
}
