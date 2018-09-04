package com.zhiwei.credit.action.creditFlow.smallLoan.project;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;
import javax.annotation.Resource;

import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.util.BeanUtil;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;


import com.zhiwei.credit.model.creditFlow.smallLoan.project.ProjectPropertyClassification;
import com.zhiwei.credit.service.creditFlow.smallLoan.project.ProjectPropertyClassificationService;
/**
 * 
 * @author 
 *
 */
public class ProjectPropertyClassificationAction extends BaseAction{
	@Resource
	private ProjectPropertyClassificationService projectPropertyClassificationService;
	private ProjectPropertyClassification projectPropertyClassification;
	
	private Long id;
	private Long projectId;
	private String businessType;
	
	
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ProjectPropertyClassification getProjectPropertyClassification() {
		return projectPropertyClassification;
	}

	public void setProjectPropertyClassification(ProjectPropertyClassification projectPropertyClassification) {
		this.projectPropertyClassification = projectPropertyClassification;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<ProjectPropertyClassification> list= projectPropertyClassificationService.getAll(filter);
		
		Type type=new TypeToken<List<ProjectPropertyClassification>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		Gson gson=new Gson();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		
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
				projectPropertyClassificationService.remove(new Long(id));
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
		ProjectPropertyClassification projectPropertyClassification=projectPropertyClassificationService.get(id);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(projectPropertyClassification));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(projectPropertyClassification.getId()==null){
			projectPropertyClassificationService.save(projectPropertyClassification);
		}else{
			ProjectPropertyClassification orgProjectPropertyClassification=projectPropertyClassificationService.get(projectPropertyClassification.getId());
			try{
				BeanUtil.copyNotNullProperties(orgProjectPropertyClassification, projectPropertyClassification);
				projectPropertyClassificationService.save(orgProjectPropertyClassification);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	public String getClassification(){
		ProjectPropertyClassification projectPropertyClassification=projectPropertyClassificationService.getByProjectId(projectId, businessType);
		
		if(null==projectPropertyClassification){
			setJsonString("{success:true}");
		}else{
			Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			
			//将数据转成JSON格式
			StringBuffer sb = new StringBuffer("{success:true,data:");
			sb.append(gson.toJson(projectPropertyClassification));
			sb.append("}");
			setJsonString(sb.toString());
		}
		
		return SUCCESS;
	}
}
