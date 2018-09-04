package com.zhiwei.credit.action.document;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.List;
import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PagingBean;


import com.zhiwei.credit.model.document.DocPrivilege;
import com.zhiwei.credit.model.system.AppRole;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.Department;
import com.zhiwei.credit.service.document.DocPrivilegeService;
import com.zhiwei.credit.service.system.AppRoleService;
import com.zhiwei.credit.service.system.AppUserService;
import com.zhiwei.credit.service.system.DepartmentService;
/**
 * 
 * @author csx
 *
 */
public class DocPrivilegeAction extends BaseAction{
	@Resource
	private DocPrivilegeService docPrivilegeService;
	private DocPrivilege docPrivilege;
	
	@Resource
	private AppRoleService appRoleService ;
	
	@Resource
	private AppUserService appUserService;
	@Resource
	private DepartmentService departmentService;
	
	private Long privilegeId;

	public Long getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(Long privilegeId) {
		this.privilegeId = privilegeId;
	}

	public DocPrivilege getDocPrivilege() {
		return docPrivilege;
	}

	public void setDocPrivilege(DocPrivilege docPrivilege) {
		this.docPrivilege = docPrivilege;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		QueryFilter filter = new QueryFilter(getRequest());
		List<DocPrivilege> list= docPrivilegeService.getAll(filter);
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:[");
		for(DocPrivilege privilege:list){
			Integer rights=privilege.getRights();
			String right=Integer.toBinaryString(rights);
		    Integer read=null;
			Integer update=null;
			Integer delete=null;
			char[] cc=right.toCharArray();
	    	  if(cc.length==1&&cc[0]=='1'){
	    		  read=1;
	    	  }
	    	  if(cc.length==2){
	    		  if(cc[0]=='1'){
	    		  update=1;
	    		  }
	    		  if(cc[1]=='1'){
	    			  read=1;
	    		  }
	    	  }
	    	 if(cc.length==3){
	    		 if(cc[0]=='1') {
	    		     delete=1;
	    		 }
	    		  if(cc[1]=='1'){
	    			  update=1;
	    		  }
	    		  if(cc[2]=='1'){
	    			  read=1;
	    		  }
	    	  }
			buff.append("{'privilegeId':"+privilege.getPrivilegeId()+",'udrId':"+privilege.getUdrId()+",'udrName':'"+privilege.getUdrName()+"','folderName':'"+privilege.getDocFolder().getFolderName()+"','flag':"+privilege.getFlag()+",'rightR':"+read+",'rightU':"+update+",'rightD':"+delete+"},");
		}
		if(list.size()>0){
			buff.deleteCharAt(buff.length()-1);
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
				docPrivilegeService.remove(new Long(id));
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
		String folderId = getRequest().getParameter("folderId");
		if(StringUtils.isNotEmpty(folderId)){
			docPrivilege = docPrivilegeService.findByFolderId(new Long(folderId));
		}else{
			String docId = getRequest().getParameter("docId");
			docPrivilege = docPrivilegeService.findByDocId(new Long(docId));
		}
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(docPrivilege));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 更改权限
	 * 
	 */
	public String change(){		
	    String strPrivilegeId=getRequest().getParameter("privilegeId");
	    String strField=getRequest().getParameter("field");
	    String strFieldValue=getRequest().getParameter("fieldValue");
	    if(StringUtils.isNotEmpty(strPrivilegeId)){
	    	docPrivilege=docPrivilegeService.get(Long.parseLong(strPrivilegeId));
	    	Integer in=docPrivilege.getRights();
	    	if(in>0){
    		String str=Integer.toBinaryString(in);
    		StringBuffer buff=new StringBuffer(str);
    		if(buff.length()==1){
    			buff.insert(0,"00");
    		}
    		if(buff.length()==2){
    			buff.insert(0,"0");
    		}
    		if(buff.length()<=0){
    			buff.insert(0,"000");
    		}
    		String rights="";
	    	if("rightR".equals(strField)){
	    		StringBuffer newBuff=new StringBuffer();
	    		if("true".equals(strFieldValue)){
	    			newBuff.append(buff.deleteCharAt(2).toString()).append("1");
	    		}else{
	    			newBuff.append(buff.deleteCharAt(2).toString()).append("0");
	    		}
	    		rights=newBuff.toString();
	    		
	    	}
	    	if("rightU".equals(strField)){
	    		StringBuffer newBuff=new StringBuffer();
	    		if("true".equals(strFieldValue)){
	    			newBuff.append(buff.charAt(0)).append("1").append(buff.charAt(2));
	    		}else{
	    			newBuff.append(buff.charAt(0)).append("0").append(buff.charAt(2));
	    		}
	    		rights=newBuff.toString();
	    	}
	    	
	    	if("rightD".equals(strField)){
	    		StringBuffer newBuff=new StringBuffer();
	    		if("true".equals(strFieldValue)){
	    			newBuff.append("1").append(buff.deleteCharAt(0).toString());
	    		}else{
	    			newBuff.append("0").append(buff.deleteCharAt(0).toString());
	    		}
	    		rights=newBuff.toString();
	    	}	    	
	    	Integer right=Integer.parseInt(rights,2);
	    	docPrivilege.setRights(right);
	    	docPrivilegeService.save(docPrivilege);
	    	setJsonString("{success:true}");
	      }
	    }else{
	    	setJsonString("{success:false}");
	    }
		return SUCCESS;
	}
	
	/**
	 * 添加保存权限
	 */
	
	public String save(){
		docPrivilegeService.save(docPrivilege);
		setJsonString("{success:true}");
		return SUCCESS;
	}
	/**
	 * 添加权限
	 * 权限right是各个权限二进制合成
	 * 其中：rights=1 相应的二进制为：001 也即是表示只有读的权限
	 * 如此类推
	 * 
	 * @return
	 */
	public String add(){
		String strFolderId=getRequest().getParameter("folderId");
		String strRoleIds=getRequest().getParameter("roleIds");
		String strUserIds=getRequest().getParameter("userIds");
		String strDepIds=getRequest().getParameter("depIds");
		String strRightR=getRequest().getParameter("rightR");
		String strRightU=getRequest().getParameter("rightU");
		String strRightD=getRequest().getParameter("rightD");
		StringBuffer buff=new StringBuffer();

		if(StringUtils.isNotEmpty(strRightD)){
			buff.append("1");
		}else{
			buff.append("0");
		}	
		if(StringUtils.isNotEmpty(strRightU)){
			buff.append("1");
		}else{
			buff.append("0");
		}
		if(StringUtils.isNotEmpty(strRightR)){
			buff.append("1");
		}else{
			buff.append("0");
		}
		Integer rights=Integer.parseInt(buff.toString(),2);
		if(StringUtils.isNotEmpty(strFolderId)){
			Long folderId=Long.parseLong(strFolderId);
			if(StringUtils.isNotEmpty(strRoleIds)){
			String []roles=strRoleIds.split(",");
			if(roles.length>0){
				for(int i=0;i<roles.length;i++){
					DocPrivilege docp=new DocPrivilege();
					docp.setFolderId(folderId);
					docp.setFlag((short)3);
					Integer roleId=Integer.parseInt(roles[i]);
					AppRole appRole=appRoleService.get(roleId.longValue());
					docp.setUdrId(roleId);
					docp.setUdrName(appRole.getName());
					docp.setRights(rights);
					docp.setFdFlag((short)0);//0=folder,1=document
					docPrivilegeService.save(docp);
				}
			}
		}
			if(StringUtils.isNotEmpty(strUserIds)){
				String []userIds=strUserIds.split(",");
				if(userIds.length>0){
					for(int i=0;i<userIds.length;i++){
						DocPrivilege docp=new DocPrivilege();
						docp.setFolderId(folderId);
						docp.setFlag((short)1);
						Integer userId=Integer.parseInt(userIds[i]);
						AppUser appUser=appUserService.get(userId.longValue());
						docp.setUdrId(userId);
						docp.setUdrName(appUser.getFullname());
						docp.setRights(rights);
						docp.setFdFlag((short)0);
						docPrivilegeService.save(docp);
					}
				}
			}
			if(StringUtils.isNotEmpty(strDepIds)){
			String []depIds=strDepIds.split(",");
			if(depIds.length>0){
				for(int i=0;i<depIds.length;i++){
					DocPrivilege docp=new DocPrivilege();
					docp.setFolderId(folderId);
					docp.setFlag((short)2);
					Integer depId=Integer.parseInt(depIds[i]);
					Department department=departmentService.get(depId.longValue());
					docp.setUdrId(depId);
					docp.setUdrName(department.getDepName());
					docp.setRights(rights);
					docp.setFdFlag((short)0);
					docPrivilegeService.save(docp);
				}
			}
		 }
		}
		setJsonString("{success:true}");
		return SUCCESS;
	}
}
