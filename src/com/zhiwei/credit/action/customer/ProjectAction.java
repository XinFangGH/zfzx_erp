package com.zhiwei.credit.action.customer;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.customer.Project;
import com.zhiwei.credit.model.system.FileAttach;
import com.zhiwei.credit.service.customer.ProjectService;
import com.zhiwei.credit.service.system.FileAttachService;

import flexjson.JSONSerializer;
/**
 * 
 * @author csx
 *
 */
public class ProjectAction extends BaseAction{
	@Resource
	private ProjectService projectService;
	@Resource
	private FileAttachService fileAttachService;
	private Project project;
	
	private Long projectId;
	
	private String projectNo;
	
	private String projectAttachIDs;

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String getProjectNo() {
		return projectNo;
	}

	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}

	public String getProjectAttachIDs() {
		return projectAttachIDs;
	}

	public void setProjectAttachIDs(String projectAttachIDs) {
		this.projectAttachIDs = projectAttachIDs;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<Project> list= projectService.getAll(filter);
		
		//Type type=new TypeToken<List<Project>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		//Gson gson=new Gson();
		JSONSerializer json = new JSONSerializer();
		buff.append(json.exclude(new String[]{"class","appUser.department","contracts"}).serialize(list));
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
				projectService.remove(new Long(id));
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
		Project project=projectService.get(projectId);
		
		Gson gson=new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		//JSONSerializer json = new JSONSerializer();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(project));
		sb.append(",userId:'"+project.getAppUser().getUserId()+"'");
		sb.append(",salesman:'"+project.getAppUser().getFullname()+"'");
		sb.append(",customerName:'"+project.getCustomer().getCustomerName()+"'");
		sb.append(",customerId:'"+project.getCustomerId()+"'");
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		boolean pass = false;
		StringBuffer buff = new StringBuffer("{");
		if(projectId !=null){
			if(projectService.checkProjectNo(project.getProjectNo())){
				pass = true;
			}else buff.append("msg:'项目号已存在,请重新填写!',");
		}else{
			pass = true;
		}
			
		if(pass){
			//保存项目附件
			String[] fileIDs = getProjectAttachIDs().split(",");
			Set<FileAttach> projectAttachs = new HashSet<FileAttach>();
			for (String id : fileIDs) {
      			if (!id.equals("")) {
      				projectAttachs.add(fileAttachService.get(new Long(id)));
      			}
      		}
			project.setProjectFiles(projectAttachs);
			
			projectService.save(project);
			buff.append("success:true,projectId:"+project.getProjectId()+"}");
		}else{
			buff.append("failure:true}");
		}
		setJsonString(buff.toString());
		return SUCCESS;
	}
	
	/**
	 * 系统按时间生成项目编号给用户
	 * @return
	 */
	public String number(){
		SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHHmmss-SSS");
		String projectNo = date.format(new Date());
		setJsonString("{success:true,projectNo:'"+projectNo+"'}");
		return SUCCESS;
	}
	
	/**
	 * 验证项目编号是否可用
	 * @return
	 */
	public String check(){
		boolean pass = false;
		pass = projectService.checkProjectNo(projectNo);
		if(pass){
			setJsonString("{success:true,pass:true}");
		}else{
			setJsonString("{success:true,pass:false}");
		}
		return SUCCESS;
	}
	
	/**
	 * 删除项目附件
	 * @return
	 */
	public String removeFile(){
		setProject(projectService.get(projectId));
		Set<FileAttach> projectFiles = project.getProjectFiles();
		FileAttach removeFile = fileAttachService.get(new Long(projectAttachIDs));
		projectFiles.remove(removeFile);
		project.setProjectFiles(projectFiles);
		projectService.save(project);
		setJsonString("{success:true}");
		return SUCCESS;
	}
}
