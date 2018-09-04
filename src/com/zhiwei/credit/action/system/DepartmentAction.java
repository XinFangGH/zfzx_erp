package com.zhiwei.credit.action.system;

/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.List;
import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.Department;
import com.zhiwei.credit.model.system.Organization;
import com.zhiwei.credit.service.system.AppUserService;
import com.zhiwei.credit.service.system.DemensionService;
import com.zhiwei.credit.service.system.DepartmentService;

public class DepartmentAction extends BaseAction{

	private Department department;
	
	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@Resource
	private DepartmentService departmentService;
	@Resource
	private AppUserService appUserService;
	@Resource
	private DemensionService demensionService;
	
	/**
	 * 显示所有的部门信息
	 * @param depId
	 * @return
	 */
	public String select(){
		
		String depId=getRequest().getParameter("depId");
		QueryFilter filter=new QueryFilter(getRequest());
		if(StringUtils.isNotEmpty(depId)&&!"0".equals(depId)){
			department=departmentService.get(new Long(depId));
			filter.addFilter("Q_path_S_LFK", department.getPath());
			
		}
		filter.addSorted("path", "asc");
		List<Department> list=departmentService.getAll(filter);
		
		if(list!=null&&list.size()>0){
			for(Department de:list){
				de.getChargeIds();
				de.getChargeNames();
			}
		}
		
		Type type=new TypeToken<List<Department>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(filter.getPagingBean().getTotalItems()).append(",result:");		
		Gson gson=new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		jsonString=buff.toString();		
		
		return SUCCESS;
	}
	
	public String list(){
		String opt=getRequest().getParameter("opt");
		StringBuffer buff = new StringBuffer();
		/*if(StringUtils.isNotEmpty(opt)){
			buff.append("[");
		}else{
			
			buff.append("[{id:'"+0+"',text:'"+AppUtil.getCompanyName()+"',expanded:true,children:[");
		}*/
		AppUser userss = (AppUser) getSession().getAttribute("users");
		Long strDepId =userss.getDepartment().getParentId();
		List<Department> listParent;
		listParent=departmentService.findByParentId(strDepId);//最顶层父节点
		for(Department dep:listParent){
				String incoCls=orgnazationIconClss(dep.getOrgType());
				//buff.append("[{id:'"+dep.getDepId()+"',text:'"+dep.getDepName().trim()+"',orgType:'"+dep.getOrgType()+"',expanded:true,children:[");
				buff.append("[{id:'"+dep.getDepId()+"',text:'"+dep.getDepName().trim()+"',");
				buff.append("orgType:'").append(dep.getOrgType()).append("',");
				buff.append("incoCls:'").append(incoCls).append("',");
			    buff.append(findChild(dep.getDepId()));
		}
		if (!listParent.isEmpty()) {
			buff.deleteCharAt(buff.length() - 1);
	    }
		buff.append("]");
		/*if(StringUtils.isNotEmpty(opt)){
		   buff.append("]");
		}else{
			buff.append("]}]");
		}*/
		System.out.println("buff======="+buff);
		setJsonString(buff.toString());
		return SUCCESS;
	}
	/**
	 * 给公司和门店以及部分添加iconClss
	 * @param orgType
	 * @return
	 */
	public String orgnazationIconClss(Short orgType){
		String iconClss="";
		if(orgType.equals(Short.valueOf("0"))||orgType.equals(Short.valueOf("1"))){
			iconClss="btn-company";
		}else if(orgType.equals(Short.valueOf("2"))){
			iconClss="btn-department";
		}else if(orgType.equals(Short.valueOf("3"))){
			iconClss="btn-stores";
		}
		return iconClss;
	}
	/*
	 * 寻找子根节点*/
	public String findChild(Long depId){
		StringBuffer buff1=new StringBuffer("");
		List<Department> list=departmentService.findByParentId(depId);
		if(list.size()==0){
			buff1.append("leaf:true},");
			return buff1.toString(); 
		}else {
			buff1.append("expanded:true,children:[");
			for(Department dep2:list){	
				String incoCls=orgnazationIconClss(dep2.getOrgType());
				buff1.append("{id:'"+dep2.getDepId()+"',text:'"+dep2.getDepName().trim()+"',");
				buff1.append("orgType:'").append(dep2.getOrgType()).append("',");
				buff1.append("incoCls:'").append(incoCls).append("',");
				buff1.append(findChild(dep2.getDepId()));
			}
			buff1.deleteCharAt(buff1.length() - 1);
			buff1.append("]},");
			System.out.println("buff1.toString()=="+buff1.toString());
			return buff1.toString();
		}
	}
	
	public String add() {
		Long parentId = department.getParentId();
		String depPath = "";
		int level = 0;
		if (parentId < 1) {
			parentId = new Long(0);
			depPath = "0.";
		} else {
			depPath = departmentService.get(parentId).getPath();
			level = departmentService.get(parentId).getDepLevel();
		}
		if (level < 1) {
			level = 1;
		}
		department.setDepLevel(level + 1);
		if(department.getDepId()==null){
			departmentService.save(department);
		}else{
			department=departmentService.get(department.getDepId());
			try {
				BeanUtil.populateEntity(getRequest(), department, "department");
				departmentService.save(department);
			} catch (Exception e) {
			}
		}
		if (department != null) {
			depPath += department.getDepId().toString() + ".";
			department.setPath(depPath);
			departmentService.save(department);
			setJsonString("{success:true}");
		} else {
			setJsonString("{success:false}");
		}
		return SUCCESS;
	}
	
	public String remove(){
		PagingBean pb=getInitPagingBean();
		Long depId=Long.parseLong(getRequest().getParameter("depId"));
		Department department=departmentService.get(depId);
		List<AppUser> userList=appUserService.findByDepartment(department.getPath(), pb);
    	if(userList.size()>0){
    		setJsonString("{success:false,message:'该部门还有人员，请将人员转移后再删除部门!'}");
    		return SUCCESS;
    	}
		departmentService.remove(depId);
	    List<Department> list=departmentService.findByParentId(depId);
	    for(Department dep:list){
	    	List<AppUser> users=appUserService.findByDepartment(dep.getPath(), pb);
	    	if(users.size()>0){
	    		setJsonString("{success:false,message:'该部门还有人员，请将人员转移后再删除部门!'}");
	    		return SUCCESS;
	    	}
	    	departmentService.remove(dep.getDepId());
	    }
	    setJsonString("{success:true}");
		return SUCCESS;
	}
	
	/**
	 * 新版本删除部门
	 * @return
	 */
	public String del(){
		//删除该部门下的所有部门
		String depId=getRequest().getParameter("depId");
		if(depId!=null){
			departmentService.delCascade(new Long(depId));
		}
		jsonString="{success:true}";
		return SUCCESS;
	}
	
	public String detail(){
		Long depId=Long.parseLong(getRequest().getParameter("depId"));
		setDepartment(departmentService.get(depId));
		Gson gson=new Gson();
		StringBuffer sb = new StringBuffer("{success:true,data:[");
		sb.append(gson.toJson(department));
		sb.append("]}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	
	public String get(){
		Long depId=Long.parseLong(getRequest().getParameter("depId"));
		setDepartment(departmentService.get(depId));
		Gson gson=new Gson();
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(department));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	
}
