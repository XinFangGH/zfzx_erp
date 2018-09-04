package com.zhiwei.credit.action.system;

/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
 */

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.log.LogResource;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.StringUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.core.creditUtils.ExcelHelper;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.model.system.AppFunction;
import com.zhiwei.credit.model.system.AppRole;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.Organization;
import com.zhiwei.credit.service.system.AppFunctionService;
import com.zhiwei.credit.service.system.AppRoleService;
import com.zhiwei.credit.service.system.AppUserService;
import com.zhiwei.credit.service.system.OrganizationService;
import com.zhiwei.credit.util.ExcelUtils;

import flex.messaging.io.ArrayList;

/**
 * 
 * @author csx
 * 
 */
public class AppRoleAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Object DefaultElement = new DefaultElement("");

	@Resource
	private AppFunctionService appFunctionService;

	private static String IS_COPY = "1";

	@Resource
	private AppRoleService appRoleService;
	@Resource
	private OrganizationService organizationService;
	@Resource
	private AppUserService appUserService;
	private AppRole appRole;

	private Long roleId;
	private String companyType;
	private String roleType;
	private String controlComId;
	private Long orgId;
	private String biaos;
	private String userIds;
	private String roleName;
	private File excelsql;
	private Element element;
	private String PStr = "";
	private String CStr = "";
	private String PCStr = "";
	private String CStrV = "";
	private String isTrue = "";
	private String roleNames = "";
	private String roleIds = "";
	private String accessIds = "";//权限id 串  用来 和导入的权限进行对比
	private String[] rightsArr;
	private String[] listArr;
	private String listStr;
	private static String[] rNameArr;
	private static String[] rRightsArr;
	private static Long oid;
	private boolean hasE=false;
	private String PStrV = "";
	private String PStrR = "";
	List<String[]> list;

	public String getUserIds() {
		return userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

	public String getBiaos() {
		return biaos;
	}

	public void setBiaos(String biaos) {
		this.biaos = biaos;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getControlComId() {
		return controlComId;
	}

	public void setControlComId(String controlComId) {
		this.controlComId = controlComId;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public AppRole getAppRole() {
		return appRole;
	}

	public void setAppRole(AppRole appRole) {
		this.appRole = appRole;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleName() {
		return roleName;
	}

	public File getExcelsql() {
		return excelsql;
	}

	public void setExcelsql(File excelsql) {
		this.excelsql = excelsql;
	}

	public String getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	/**
	 * 显示列表
	 */
	public String list() {

		List<AppRole> list = null;
		String roleName = this.getRequest().getParameter("Q_roleName_S_LK");
		if (roleName == null) {
			roleName = "";
		}
		String roleDesc = this.getRequest().getParameter("Q_roleDesc_S_LK");
		if (roleDesc == null) {
			roleDesc = "";
		}
		if (null != roleType && roleType.endsWith("control")) {
			list = appRoleService.getListByRoleType(roleName, roleDesc, start,
					limit);
			long totalCount = appRoleService.getCount(roleName, roleDesc);
			Type type = new TypeToken<List<AppRole>>() {
			}.getType();
			StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
					.append(totalCount).append(",result:");
			Gson gson = new GsonBuilder()
					.excludeFieldsWithoutExposeAnnotation().create();
			buff.append(gson.toJson(list, type));
			buff.append("}");
			jsonString = buff.toString();
		} else {
			if (null == orgId) {
				orgId = new Long(1);
			}
			if (null != orgId) {

				list = appRoleService.getListByOrgId(orgId, roleName, roleDesc,
						start, limit);
				long totalCount = appRoleService.getCountByOrgId(orgId,
						roleName, roleDesc);
				Type type = new TypeToken<List<AppRole>>() {
				}.getType();
				StringBuffer buff = new StringBuffer(
						"{success:true,'totalCounts':").append(totalCount)
						.append(",result:");
				Gson gson = new GsonBuilder()
						.excludeFieldsWithoutExposeAnnotation().create();
				buff.append(gson.toJson(list, type));
				buff.append("}");
				jsonString = buff.toString();
			}
		}

		return SUCCESS;
	}

	public String listAll() {
		PagingBean pb = new PagingBean(start, limit);
		List<AppRole> list = appRoleService.getAll(pb);
		Type type = new TypeToken<List<AppRole>>() {
		}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(pb.getTotalItems()).append(",result:");
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}

	/**
	 * 列出角色树
	 * 
	 */
	public String tree() {
		Boolean flag = Boolean.valueOf(AppUtil.getSystemIsGroupVersion());
		if (flag) {
			String type = this.getRequest().getParameter("type");
			String companyId_ = this.getRequest().getParameter("companyId");
			if ((null != type && type.equals("branch"))
					|| type.equals("undefined")) {
				String roleType = ContextUtil.getRoleTypeSession();
				if (null != roleType && "business".equals(roleType)) {
					Long companyId = ContextUtil.getLoginCompanyId();
					if (null != companyId_ && !"".equals(companyId_)) {
						companyId = Long.valueOf(companyId_);
					}
					if (null != companyId) {
						List<AppRole> list = appRoleService
								.getRoleListByOrgId(companyId);
						StringBuffer buff = new StringBuffer("[");
						for (AppRole role : list) {
							buff.append("{id:'" + role.getRoleId() + "',text:'"
									+ role.getRoleName() + "',leaf:true},");
						}
						if (!list.isEmpty()) {
							buff.deleteCharAt(buff.length() - 1);
						}
						buff.append("]");
						setJsonString(buff.toString());
					}
				} else if (null != roleType && "control".equals(roleType)) {
					String strs = ContextUtil.getBranchIdsStr();
					if (null != strs && !"".equals(strs)) {
						StringBuffer buff = new StringBuffer("[");
						String[] companyIds = strs.split(",");
						for (String companyId : companyIds) {
							Organization org = organizationService.get(Long
									.valueOf(companyId));
							buff.append("{id:'" + org.getOrgId() + "',text:'"
									+ org.getOrgName()
									+ "',leaf:false,expanded:true,children:[");
							if (null != org && org.getOrgId() != null) {
								List<AppRole> list = appRoleService
										.getRoleListByOrgId(org.getOrgId());
								for (AppRole role : list) {
									buff.append("{id:'" + role.getRoleId()
											+ "',text:'" + role.getRoleName()
											+ "',leaf:true},");
								}
								if (!list.isEmpty()) {
									buff.deleteCharAt(buff.length() - 1);
								}
							}
							buff.append("]},");
						}
						if (null != companyIds) {
							buff.deleteCharAt(buff.length() - 1);
						}
						buff.append("]");
						setJsonString(buff.toString());
					}
				}
			} else if (null != type && type.equals("all")) {
				StringBuffer buff = new StringBuffer(
						"[{text:'业务角色',expanded:true,children:[");
				List<Organization> olist = organizationService
						.getAllByOrgType();
				for (Organization org : olist) {
					buff.append("{id:'" + org.getOrgId() + "',text:'"
							+ org.getOrgName()
							+ "',leaf:false,expanded:true,children:[");
					if (null != org && org.getOrgId() != null) {
						List<AppRole> list = appRoleService
								.getRoleListByOrgId(org.getOrgId());
						for (AppRole role : list) {
							buff.append("{id:'" + role.getRoleId() + "',text:'"
									+ role.getRoleName() + "',leaf:true},");
						}
						if (!list.isEmpty()) {
							buff.deleteCharAt(buff.length() - 1);
						}
					}
					buff.append("]},");
				}
				if (!olist.isEmpty()) {
					buff.deleteCharAt(buff.length() - 1);
				}
				buff.append("]},{text:'管控角色',expanded:true,children:[");
				List<AppRole> rlist = appRoleService
						.getListByRoleType("control");
				for (AppRole role : rlist) {
					buff.append("{id:'" + role.getRoleId() + "',text:'"
							+ role.getRoleName() + "',leaf:true},");
				}
				if (!rlist.isEmpty()) {
					buff.deleteCharAt(buff.length() - 1);
				}
				buff.append("]}]");
				setJsonString(buff.toString());
			}
		} else {
			List<AppRole> listRole;
			StringBuffer buff = new StringBuffer("[");
			listRole = appRoleService.getAll();// 最顶层父节点
			for (AppRole role : listRole) {
				buff.append("{id:'" + role.getRoleId() + "',text:'"
						+ role.getRoleName() + "',leaf:true},");
			}
			if (!listRole.isEmpty()) {
				buff.deleteCharAt(buff.length() - 1);
			}
			buff.append("]");
			setJsonString(buff.toString());
		}

		return SUCCESS;
	}

	public String treeCompany() {
		String companyId_ = this.getRequest().getParameter("companyId");
		Long companyId = null;
		if (null != companyId_ && !"".equals(companyId_)) {
			companyId = Long.valueOf(companyId_);
		}
		if (null != companyId) {
			List<AppRole> list = appRoleService.getRoleListByOrgId(companyId);
			StringBuffer buff = new StringBuffer("[");
			for (AppRole role : list) {
				buff.append("{id:'" + role.getRoleId() + "',text:'"
						+ role.getRoleName() + "',leaf:true},");
			}
			if (!list.isEmpty()) {
				buff.deleteCharAt(buff.length() - 1);
			}
			buff.append("]");
			setJsonString(buff.toString());
		}
		return SUCCESS;
	}

	public String changeRoletype() {
		String roleType = this.getRequest().getParameter("roleType");
		String ActiveCompanyId = "";
		Organization og = null;
		String companyKey = "";
		if (null != this.getSession().getAttribute("company")&&!"null".equals(this.getSession().getAttribute("company"))&&!"".equals(this.getSession().getAttribute("company"))) {
			companyKey = (String) this.getSession().getAttribute("company");
			og = this.organizationService.getBranchCompanyByKey(companyKey);
		}
		if (null == og) {
			og = this.organizationService.getGroupCompany();
		}
		if (roleType.equals("business")) {
			this.getRequest().getSession().setAttribute("ActiveCompanyId",
					String.valueOf(og.getOrgId()));

		} else if (roleType.equals("control")) {

			ActiveCompanyId = (String) this.getSession().getAttribute(
					"Control CompanyId");
			this.getRequest().getSession().setAttribute("ActiveCompanyId",
					ActiveCompanyId);
		}
		this.getRequest().getSession().setAttribute("RoleType", roleType); // 角色类别
		String userInfo = appUserService.getCurUserInfo();
		JsonUtil.responseJsonString("{success:true,userInfo:" + userInfo + "}");
		return SUCCESS;
	}
	//修改系统类别session
	public String changeSystype() {
		String sysType = this.getRequest().getParameter("sysType");
		
		this.getRequest().getSession().setAttribute("sysType", sysType); // 系统类别
	
		return SUCCESS;
	}

	public String setAuth() {

		String roleType = this.getRequest().getParameter("roleType");
		String companyKey = "";
		AppUser appuser = ContextUtil.getCurrentUser();
		Organization og = null;
		if (null != this.getSession().getAttribute("company")&&!"null".equals(this.getSession().getAttribute("company"))&&!"".equals(this.getSession().getAttribute("company"))) {
			companyKey = (String) this.getSession().getAttribute("company");
			og = this.organizationService.getBranchCompanyByKey(companyKey);
		}
		if (null == og) {
			og = this.organizationService.getGroupCompany();
		}
		Long CompanyId = og.getOrgId();
		String ActiveCompanyId = "";
		String ControlCompanyId = "";
		if (null != roleType && !"".equals(roleType)) {
			if (roleType.equals("control")) { // 如果选择管控角色
				ControlCompanyId = appRoleService.getControlCompanyId(appuser);
				ActiveCompanyId = ControlCompanyId;
			} else if (roleType.equals("business")) { // 如果选择业务角色
				ControlCompanyId = appRoleService.getControlCompanyId(appuser);
				ActiveCompanyId = String.valueOf(CompanyId);

			}
		}

		//this.getRequest().getSession().setAttribute("RoleType", roleType); // 角色类别
		this.getRequest().getSession().setAttribute("CompanyId", CompanyId);
		this.getRequest().getSession().setAttribute("ActiveCompanyId",
				ActiveCompanyId);
		this.getRequest().getSession().setAttribute("Control CompanyId",
				ControlCompanyId);
		String userInfo = appUserService.getCurUserInfo();
		JsonUtil.responseJsonString("{success:true,userInfo:" + userInfo + "}");
		return SUCCESS;
	}

	/**
	 * 批量删除
	 * 
	 * @return
	 */
	@LogResource(description = "删除角色")
	public String multiDel() {
		int count=0;
		String[] ids = getRequest().getParameterValues("ids");
		if (ids != null) {
			for (String id : ids) {
				AppRole appRole = appRoleService.get(new Long(id));
				if(appRole.getAppUsers().isEmpty()){
					count++;
					appRole.getAppUsers().remove(appRole);
					appRole.getFunctions().remove(appRole);
					appRoleService.remove(appRole);
				}
			}
		}
		StringBuffer sb=new StringBuffer("{success:true,message:");
		if(count==ids.length){
			sb.append("true");
		}else{
			if(ids.length>1){
				sb.append("'部分角色已分配指定的用户,不允许删除!'");
			}else{
				sb.append("'该角色已分配指定的用户,不允许删除!'");
			}
		}
		sb.append("}");
		jsonString = sb.toString();
		return SUCCESS;
	}

	/**
	 * 角色授权
	 * 
	 * @return
	 */
	public String grant() {
		AppRole appRole = appRoleService.get(roleId);
		String rights = getRequest().getParameter("rights");

		if (rights == null) {
			rights = "";
		}

		if (!rights.equals(appRole.getRights())) {
			appRole.setRights(rights);
			appRole.getFunctions().clear();

			String[] funs = rights.split("[,]");

			for (int i = 0; i < funs.length; i++) {
				if (funs[i].startsWith("_")) {
					AppFunction af = appFunctionService.getByKey(funs[i]);

					if (af != null) {
						appRole.getFunctions().add(af);
						appRole.getFunctions().clear();
					}
				}
			}

			// 检查Right是否发生了变化
			appRoleService.save(appRole);
			// 重新加载权限 优化为只是更新该角色对应的权限
			AppUtil.reloadSecurityDataSource();
		}

		return SUCCESS;
	}

	/**
	 * 通过导入为角色授权
	 * 
	 * @return
	 */
	public String[] grantFromExcel(Long id, String[] roleNameArr,String[] rightsArr) {
		String str="<span>角色权限导入成功信息：</span>";
		String[] s=new String[2];
		String[] strUpOrAdd=new String[2];
		if(id!=null&&rightsArr!=null&&roleNameArr!=null&&rightsArr.length!=0&&roleNameArr.length!=0){
			if(rightsArr.length==roleNameArr.length){
				String sY="";
				String sN="";
				String sYN="";
				String sNN="";
				AppRole role;
				for(int i=0 ;i<roleNameArr.length;i++){
					role=appRoleService.getCountByOrgId(id, roleNameArr[i]);
						if(role!=null){
							strUpOrAdd=upOrAdd("update",id, rightsArr[i], role, roleNameArr[i]);
							if(strUpOrAdd[0].equals("Y")){
								sY=sY+roleNameArr[i]+",";
							}else{
								sYN=sYN+roleNameArr[i]+",";
							}
						}else{
							strUpOrAdd=upOrAdd("add",id, rightsArr[i], role, roleNameArr[i]);
							if(strUpOrAdd[0].equals("Y")){
								sN=sN+roleNameArr[i]+",";
							}else{
								sNN=sNN+roleNameArr[i]+",";
							}
						}
						
				}
				
				
				if(!sY.equals("")){
					s[0]="Y";
					str=str+"<li>"+sY+"权限更新成功！</li>";
					s[1]=str;
				}
				if(!sN.equals("")){
					s[0]="Y";
					str=str+"<li>"+sN+"权限添加成功！</li>";
					s[1]=str;
				}
				if(!sYN.equals("")){
					s[0]="Y";
					str=str+"<li>"+sYN+"权限更新失败！</li>";
					s[1]=str;
				}
				if(!sNN.equals("")){
					s[0]="Y";
					str=str+"<li>"+sNN+"权限添加失败！</li>";
					s[1]=str;
				}
				if(sY.equals("")&&sN.equals("")&&sYN.equals("")&&sNN.equals("")){
					s[0]="N";
					str="<span>角色权限导入失败</span>";
					s[1]=str;
				}
			}
		}
		 rNameArr=null;
		 rRightsArr=null;
		 oid=null;
		return s;
	}
	
	private String[] upOrAdd(String flag,Long id,String rights,AppRole role,String roleName){
		String[] str=new String[2];
		try {
			if(flag.equals("add")){ //新增加
				AppRole newAppRole=new AppRole();
				newAppRole.setIsDefaultIn((short) 0);
				
				newAppRole.setRoleDesc(roleName);
				newAppRole.setStatus((short)1);
				newAppRole.setRoleName(roleName);
				newAppRole.setOrg_type((short)1);
				if(id==0){
					newAppRole.setOrgId(null);
					newAppRole.setRoleType("control");
				}else{
					newAppRole.setOrgId(id);
				newAppRole.setRoleType("business");
				}
				newAppRole.setRights(rights);
				appRoleService.save(newAppRole);
				
			}else{//更新
				role.setRights(rights);
				role.getFunctions().clear();

				String[] funs = rights.split("[,]");

				for (int i = 0; i < funs.length; i++) {
					if (funs[i].startsWith("_")) {
						AppFunction af = appFunctionService.getByKey(funs[i]);

						if (af != null) {
							role.getFunctions().add(af);
							role.getFunctions().clear();
						}
					}
				}

				// 检查Right是否发生了变化
				appRoleService.save(role);
			}
			str[0]="Y";
			
		} catch (Exception e) {
			str[0]="N";
		}
	
		return str;
	}

	/**
	 * 加载授权的XML
	 * 
	 * @return
	 */
	public String grantXml() {
		//String id = "";
		//String text = "";
		Document grantMenuDoc = AppUtil.getGrantMenuDocument();
		Boolean isGroup = Boolean.valueOf(AppUtil.getSystemIsGroupVersion());
		if (null != companyType && companyType.equals("1")) {
			// 分公司
			String str = "/Modules/Menus/Items/Item/Functions/Function";
			List funNodeList1 = grantMenuDoc.getRootElement().selectNodes(str);
			if (null != funNodeList1 && funNodeList1.size() > 0) {
				for (int j = 0; j < 4; j++) {
					List funNodeList = grantMenuDoc.getRootElement().selectNodes(str);
					for (int i = 0; i < funNodeList.size(); i++) {
						Element funNode = (Element) funNodeList.get(i);
						String name = funNode.attributeValue("isShow");
						if (null != name && name.equals("false")) {
							funNode.getParent().remove(funNode);
						}
						
						if(!isGroup){
							String isGroupVersion = funNode.attributeValue("isGroup");
							if(isGroupVersion!=null&&!"".equals(isGroupVersion)&&"true".equals(isGroupVersion)){
								funNode.getParent().remove(funNode);
							}
						}
					}
					str = str.substring(0, str.lastIndexOf("/"));
				}
			} else {
				String str1 = "/Modules/Menus/Items/Item/Function";
				for (int j = 0; j < 3; j++) {
					List funNodeList = grantMenuDoc.getRootElement().selectNodes(str1);
					for (int i = 0; i < funNodeList.size(); i++) {
						Element funNode = (Element) funNodeList.get(i);
						//id = funNode.attributeValue("id");
						//text = funNode.attributeValue("text");
						String name = funNode.attributeValue("isShow");
						if (null != name && name.equals("false")) {
							funNode.getParent().remove(funNode);
						}
						
						if(!isGroup){
							String isGroupVersion = funNode.attributeValue("isGroup");
							if(isGroupVersion!=null&&!"".equals(isGroupVersion)&&"true".equals(isGroupVersion)){
								funNode.getParent().remove(funNode);
							}
						}
					}
					str1 = str1.substring(0, str1.lastIndexOf("/"));
				}
			}
		}
		if (null != companyType && companyType.equals("0")) {
			// 分公司
			String str = "/Modules/Menus/Items/Item/Functions/Function";
			List funNodeList1 = grantMenuDoc.getRootElement().selectNodes(str);
			if (null != funNodeList1 && funNodeList1.size() > 0) {
				for (int j = 0; j < 4; j++) {
					List funNodeList = grantMenuDoc.getRootElement().selectNodes(str);
					for (int i = 0; i < funNodeList.size(); i++) {
						Element funNode = (Element) funNodeList.get(i);
						String name = funNode.attributeValue("isHeadShow");
						if (null != name && name.equals("false")) {
							funNode.getParent().remove(funNode);
						}
						
						if(!isGroup){
							String isGroupVersion = funNode.attributeValue("isGroup");
							if(isGroupVersion!=null&&!"".equals(isGroupVersion)&&"true".equals(isGroupVersion)){
								funNode.getParent().remove(funNode);
							}
						}
					}
					str = str.substring(0, str.lastIndexOf("/"));
				}
			} else {
				String str1 = "/Modules/Menus/Items/Item/Function";
				for (int j = 0; j < 3; j++) {
					List funNodeList = grantMenuDoc.getRootElement().selectNodes(str1);
					for (int i = 0; i < funNodeList.size(); i++) {
						Element funNode = (Element) funNodeList.get(i);
						String name = funNode.attributeValue("isHeadShow");
						if (null != name && name.equals("false")) {
							funNode.getParent().remove(funNode);
						}
						
						if(!isGroup){
							String isGroupVersion = funNode.attributeValue("isGroup");
							if(isGroupVersion!=null&&!"".equals(isGroupVersion)&&"true".equals(isGroupVersion)){
								funNode.getParent().remove(funNode);
							}
						}
					}
					str1 = str1.substring(0, str1.lastIndexOf("/"));
				}
			}
		}
		if (null != biaos && biaos.equals("control")) {
			// 掌控
			String str = "/Modules/Menus/Items/Item/Functions/Function";
			List funNodeList1 = grantMenuDoc.getRootElement().selectNodes(str);
			if (null != funNodeList1 && funNodeList1.size() > 0) {
				for (int j = 0; j < 4; j++) {
					List funNodeList = grantMenuDoc.getRootElement().selectNodes(str);
					for (int i = 0; i < funNodeList.size(); i++) {
						Element funNode = (Element) funNodeList.get(i);
						String name = funNode.attributeValue("isControl");
						if (null != name && name.equals("false")) {
							funNode.getParent().remove(funNode);
						}
						
						if(!isGroup){
							String isGroupVersion = funNode.attributeValue("isGroup");
							if(isGroupVersion!=null&&!"".equals(isGroupVersion)&&"true".equals(isGroupVersion)){
								funNode.getParent().remove(funNode);
							}
						}
					}
					str = str.substring(0, str.lastIndexOf("/"));
				}
			} else {
				String str1 = "/Modules/Menus/Items/Item/Function";
				for (int j = 0; j < 3; j++) {
					List funNodeList = grantMenuDoc.getRootElement().selectNodes(str1);
					for (int i = 0; i < funNodeList.size(); i++) {
						Element funNode = (Element) funNodeList.get(i);
						String name = funNode.attributeValue("isControl");
						if (null != name && name.equals("false")) {
							funNode.getParent().remove(funNode);
						}
						
						if(!isGroup){
							String isGroupVersion = funNode.attributeValue("isGroup");
							if(isGroupVersion!=null&&!"".equals(isGroupVersion)&&"true".equals(isGroupVersion)){
								funNode.getParent().remove(funNode);
							}
						}
					}
					str1 = str1.substring(0, str1.lastIndexOf("/"));
				}
			}
		}
		setJsonString(grantMenuDoc.asXML());

		return SUCCESS;
	}

	/**
	 * 显示详细信息
	 * 
	 * @return
	 */
	public String get() {
		AppRole appRole = appRoleService.get(roleId);
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		// 将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(appRole));
		sb.append("}");
		setJsonString(sb.toString());

		return SUCCESS;
	}

	
	/**
	 * 添加及保存操作 当是isCopy='1'时，则是为角色的复制
	 */
	@LogResource(description = "添加角色")
	public String save() {
		String isCopy = getRequest().getParameter("isCopy");
		if (StringUtils.isNotEmpty(isCopy) && IS_COPY.equals(isCopy)) {
			AppRole role = new AppRole();
			role.setIsDefaultIn((short) 0);
			role.setRoleDesc(appRole.getRoleDesc());
			role.setStatus(appRole.getStatus());
			role.setRoleName(appRole.getRoleName());
			role.setOrg_type(appRole.getOrg_type());
			role.setOrgId(appRole.getOrgId());
			role.setRoleType(appRole.getRoleType());
			appRole = appRoleService.get(appRole.getRoleId());
			Set<AppFunction> set = new HashSet<AppFunction>(appRole
					.getFunctions());
			role.setFunctions(set);
			role.setRights(appRole.getRights());
			appRoleService.save(role);
		} else {
			if (appRole.getRoleId() == null) {
				appRole.setIsDefaultIn((short) 0);
				appRoleService.save(appRole);
			} else {
				AppRole orgRole = appRoleService.get(new Long(appRole
						.getRoleId()));
				try {
					BeanUtil.copyNotNullProperties(orgRole, appRole);
					appRoleService.save(orgRole);
				} catch (Exception ex) {
				}
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
	}

	public String check() {
		String roleName = getRequest().getParameter("roleName");
		AppRole appRole = appRoleService.getByRoleName(roleName);
		if (appRole == null) {
			setJsonString("{success:true}");
		} else {
			setJsonString("{success:false}");
		}
		return SUCCESS;
	}

	/**
	 * 得到所有的分公司
	 */
	public String getBranchCompany() {
		List<Organization> list = organizationService.getBranchByPage(start,
				limit);
		/*
		 * List<Organization> list1=organizationService.getBranchCompany(); long
		 * totalCount=0; if(null!=list1 && list1.size()>0){
		 * totalCount=list1.size(); }
		 */
		long totalCount = organizationService.getBranchCount();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(totalCount).append(",result:[");
		String arr[] = new String[] {};
		if (null != roleId) {
			AppRole role = appRoleService.get(roleId);
			if (null != role) {
				if (null != role.getControlCompanyId()
						&& !"".equals(role.getControlCompanyId())) {
					arr = role.getControlCompanyId().split(",");
				}
			}
		}
		for (Organization or : list) {
			buff.append("{\"orgId\":");
			buff.append(or.getOrgId());
			buff.append(",\"orgName\":'");
			buff.append(or.getOrgName());
			buff.append("',\"isControl\":'");
			for (int i = 0; i < arr.length; i++) {
				if (or.getOrgId() == Long.parseLong(arr[i])) {
					buff.append(true);
				}
			}
			buff.append("'},");
		}

		if (null != list && list.size() > 0) {
			buff.deleteCharAt(buff.length() - 1);
		}
		buff.append("]}");
		jsonString = buff.toString();
		return SUCCESS;
	}

	/**
	 * 保存管控公司Id
	 */
	public String saveControlComId() {
		String msg = "";
		try {
			AppRole role = appRoleService.get(roleId);
			if (null != role) {
				role.setControlCompanyId(controlComId);
				appRoleService.save(role);
			}
			msg = "{success:true}";
		} catch (Exception e) {
			msg = "{success:false}";
			e.printStackTrace();
		}
		jsonString = msg;
		return SUCCESS;
	}

	public String companyTree() {
		Organization organization = organizationService.getHeadquarters();
		StringBuffer buff = new StringBuffer("[{id:'" + organization.getOrgId()
				+ "',text:'" + organization.getOrgName()
				+ "',org_type:'0',leaf:false,expanded:true,children:[");
		List<Organization> list = organizationService.getBranchCompany(null);
		for (Organization or : list) {
			buff.append("{id:'" + or.getOrgId()).append(
					"',text:'" + or.getOrgName()).append("',");
			buff.append("org_type:'1',leaf:true},");

		}

		if (!list.isEmpty()) {
			buff.deleteCharAt(buff.length() - 1);
		}
		buff.append("]}]");
		setJsonString(buff.toString());
		return SUCCESS;
	}

	public String listForOne() {
		String roleName = this.getRequest().getParameter("Q_roleName_S_LK");
		if (null == roleName) {
			roleName = "";
		}
		String roleDesc = this.getRequest().getParameter("Q_roleDesc_S_LK");
		if (null == roleDesc) {
			roleDesc = "";
		}
		List<AppRole> list = appRoleService.getListForOne(roleName, roleDesc,
				start, limit);
		Long count = appRoleService.getCountForOne(roleName, roleDesc);
		Type type = new TypeToken<List<AppRole>>() {
		}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(count).append(",result:");
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}

	public String saveForOne() {
		String isCopy = this.getRequest().getParameter("isCopy");
		if (StringUtils.isNotEmpty(isCopy) && IS_COPY.equals(isCopy)) {
			AppRole role = new AppRole();
			role.setIsDefaultIn((short) 0);
			role.setRoleDesc(appRole.getRoleDesc());
			role.setStatus(appRole.getStatus());
			role.setRoleName(appRole.getRoleName());
			role.setOrg_type(appRole.getOrg_type());
			role.setOrgId(appRole.getOrgId());
			role.setRoleType(appRole.getRoleType());
			role.setOrg_type(appRole.getOrg_type());
			appRole = appRoleService.get(appRole.getRoleId());
			Set<AppFunction> set = new HashSet<AppFunction>(appRole
					.getFunctions());
			role.setFunctions(set);
			role.setRights(appRole.getRights());
			appRoleService.save(role);
		} else {
			if (appRole.getRoleId() == null) {
				appRole.setIsDefaultIn((short) 0);
				Long orgId = ContextUtil.getLoginCompanyId();
				Organization org=organizationService.get(orgId);
				appRole.setOrgId(orgId);
				appRole.setOrg_type(org.getOrgType());
				appRoleService.save(appRole);
			} else {
				AppRole orgRole = appRoleService.get(new Long(appRole
						.getRoleId()));
				try {
					BeanUtil.copyNotNullProperties(orgRole, appRole);
					appRoleService.save(orgRole);
				} catch (Exception ex) {
				}
			}
		}
		return SUCCESS;
	}

	public String getAppUsers() {
		String msg = "";
		try {
			if (null != roleId) {
				appRole = appRoleService.get(roleId);
				Set<AppUser> users = appRole.getAppUsers();
				Iterator<AppUser> it = users.iterator();
				String ids = "";
				String names = "";
				while (it.hasNext()) {
					AppUser user = it.next();
					ids = ids + user.getId() + ",";
					names = names + user.getFullname() + ",";
				}
				if (!"".equals(ids)) {
					ids = ids.substring(0, ids.length() - 1);
				}
				if (!"".equals(names)) {
					names = names.substring(0, names.length() - 1);
				}
				msg = "{success:true,userIds:'" + ids + "',userNames:'" + names
						+ "'}";
			}

		} catch (Exception e) {
			msg = "{success:false}";
			e.printStackTrace();
		}
		jsonString = msg;
		return SUCCESS;
	}

	public String saveAppUsers() {
		if (null != roleId) {
			appRole = appRoleService.get(roleId);
		}
		String[] userIdArr = userIds.split(",");
		String username="";
		if (null != userIdArr && !"".equals(userIds)) {
			appRole.setAppUsers(new HashSet<AppUser>());
			for (String userId : userIdArr) {
				boolean flag=true;
				AppUser user = appUserService.get(new Long(userId));
				Set<AppRole> roles = user.getRoles();
				Iterator<AppRole> it = roles.iterator();
				while (it.hasNext()) {
					AppRole role = it.next();
					if (null != role && null != role.getRoleType()
							&& "control".equals(role.getRoleType())) {
						flag=false;
						username=username+","+user.getFullname();
						break;
						/*jsonString = "{succcess:true,unique:false,username:'"
								+ user.getFullname() + "'}";
						return SUCCESS;*/
					}
				}
				if(flag==true){
					appRole.getAppUsers().add(user);
				}
			}
			appRoleService.save(appRole);
		} else {
			appRole.setAppUsers(null);
			appRoleService.save(appRole);
		}
		if(!username.equals("")){
			jsonString = "{succcess:true,unique:false,username:'"+ username + "'}";
		}else{
			jsonString = "{success:true}";
		}
		return SUCCESS;
	}

	public String setSystem() {
		String msg = "{success:true}";
		String type = this.getRequest().getParameter("systemType");
		if (null != type && !"".equals(type)) {
			this.getSession().setAttribute("OAorTeam", type);
		}
		jsonString = msg;
		return SUCCESS;
	}

	/**
	 * 导出权限
	 */
	public String exitExcel() {
		AppRole appRole;
		list = new ArrayList();
		String rights = "";
		roleNames = URLDecoder.decode(roleNames); //解密 字符串防止 出现乱码
		String headStr = "id,权限," + roleNames;
		String[] tableHeader = headStr.split(",");
		String[] idArr = roleIds.split(",");

		for (int i = 0; i < idArr.length; i++) {
			appRole = appRoleService.get(Long.valueOf(idArr[i]));
			if (appRole != null) {
				if (appRole.getRights() == null) {
					rights = rights + " " + "@";
				} else {
					rights = rights + appRole.getRights() + "@";
				}
			}
		}
		rights = rights.substring(0, rights.lastIndexOf("@"));

		String msg = "{success:true}";
		Document grantMenuDoc = AppUtil.getGrantMenuDocument();
		Element rootElt = grantMenuDoc.getRootElement();
		for (Iterator iter = rootElt.elementIterator(); iter.hasNext();) {
			getSubEle((Element) iter.next(), rights, orgId);
		}
		download(list, tableHeader);
		list = null;
		jsonString = msg;
		return SUCCESS;
	}

	/**
	 * 判断是否有更深的节点
	 */
	public boolean isHasSubEle(Element e) {
		return !e.isTextOnly();
	}

	/**
	 * 判断是否有父节点
	 * e 节点
	 * 
	 */
	private boolean isParentEle(Element e) {
		boolean isTrue;
		if (e.getParent() != null
				&& e.getParent().attributeValue("text") != null) {
			isTrue = true;
		} else{
			isTrue = false;
		}
		return isTrue;
	}
	/**
	 * 判断是否有该权限
	 * e 节点
	 * orgId 根据不同的组织机构id 判断该机构应有的权限
	 */
	private void hasEle(Element e,Long orgId){
		
		if(orgId==1){ //组织机构为1表示 出现所有 的授权项
			hasE = false;
			
		}else if(orgId==0) {//组织机构为 0表示  管控授权树 不出现的配置  这里  不存在为0 的机构
			if(e.attributeValue("isControl")!=null&&e.attributeValue("isControl").equals("false")){
				hasE = true;
			}
		}else{//组织机构不为 1 表示  分公司授权树 不出现的配置
			if(e.attributeValue("isShow")!=null&&e.attributeValue("isShow").equals("false")){
				hasE = true;
			}
		}
		
	}

	/**
	 * 节点遍历
	 * 
	 * @param e
	 */
	public void getSubEle(Element e, String rights, Long orgId) {
		boolean sub = isHasSubEle(e);
		if (sub) {
			List<Element> el = e.elements();
			for (int i = 0; i < el.size(); i++) {
				getParentStr(el.get(i), rights, orgId);
				getSubEle(el.get(i), rights, orgId);
				
			}

		} else {

		}
	}

	/**
	 * 逐级获取父节点
	 * 
	 * @param e
	 */
	private String getParentStr(Element e, String rights, Long orgId) {
		if (CStr.equals("")) {
			CStr = e.attributeValue("text"); // 获取叶子节点 属性值
			CStrV = e.attributeValue("id");// 获取叶子节点 属性id
		}
		boolean sub = isParentEle(e);// 判断是否有符合的父节点
		hasEle(e, orgId);
		if (sub) {
			PStr = e.getParent().attributeValue("text") + "_" + PStr;
			PStrV=e.getParent().attributeValue("id");
		//	System.out.println("-->>>"+PStrV);
			getParentStr(e.getParent(), rights, orgId);
		} else {
			//最顶级 such as "Mod_team"的权限
			if(PStrV.indexOf("Mod_")!=-1 && !PStrR.equals(PStrV)){
			//	System.out.println("-->"+PStrV.indexOf("Mod_"));
				if(!rights.equals("")){
					rightsArr = rights.split("@");
					for (int i = 0; i < rightsArr.length; i++) {
						if (rightsArr[i].indexOf(PStrV) == -1) {
							isTrue = isTrue + "无" + ",";
						} else {
							isTrue = isTrue + "有" + ",";
						}
					}
					isTrue = isTrue.substring(0, isTrue.lastIndexOf(","));
					listStr = e.attributeValue("id") + "," + getStr(e.attributeValue("text") + "@") + "," + isTrue;
					listArr = listStr.split(",");
					if(!hasE){
					list.add(listArr);
					}
					listStr = "";
					isTrue="";
					PStrR=PStrV;
					PStrV="";
					
					hasE=false;
						}else{
							if(!hasE){
							accessIds=accessIds+PStrV+",";
							}
							PStrR=PStrV;
							PStrV="";
							hasE=false;
						}
			}
				if(!rights.equals("")){
					rightsArr = rights.split("@");
					for (int i = 0; i < rightsArr.length; i++) {
						if (rightsArr[i].indexOf(CStrV) == -1) {
							isTrue = isTrue + "无" + ",";
						} else {
							isTrue = isTrue + "有" + ",";
						}
					}
					isTrue = isTrue.substring(0, isTrue.lastIndexOf(","));
					PCStr = PStr + CStr;
					listStr = CStrV + "," + getStr(PCStr + "@") + "," + isTrue;
					listArr = listStr.split(",");
					if(!hasE){
					list.add(listArr);
					}
					rightsArr = null;
					listStr = "";
					PStr = "";
					CStr = "";
					PCStr = "";
					isTrue = "";
					hasE=false;
						}else{
							if(!hasE){
							accessIds=accessIds+CStrV+",";
							}
							CStr = "";
							CStrV="";
							hasE=false;
						}
			}
		return PStr;
	}

	/**
	 * 解析字符串
	 * 
	 */
	private String getStr(String s) {
		String str = "";
		String[] ss = s.split("_");
		for (int i = 0; i < ss.length; i++) {
			str = str + ss[i] + StringUtil.splitStr(i, "_");
		}
		str = str.substring(0, str.lastIndexOf("@"));
		return str;
	}

	/**
	 *下载文件
	 * 
	 */
	public String download(List<String[]> list, String[] tableHeader) {
		try {
			ExcelHelper.exportE(list, tableHeader, "权限分配");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 检测Excel数据
	 */
	public String getExcData() {

		String flag = this.getRequest().getParameter("uploadtype");
		String fileName=this.getRequest().getParameter("filename");
		if (flag.equals("accessupload")) {

			String b = "";
			String[] str;

			if (null == excelsql) {
				b = "请选择导入文件";
				setJsonString(b);
				return "false";
			}
			if(fileName!=null&&!fileName.equals("")){
			int startIndex=fileName.lastIndexOf("\\")+1;
			//int endIdex=fileName.lastIndexOf(".");
			fileName=fileName.substring(startIndex);
			}
			String sourceProductImagePath = upLoadImgFile(excelsql);
			//校验数据的准确性
			str=checkAccess(sourceProductImagePath,fileName);

			if (str[0].equals("Y")) {
				//success:0 表示检测信息,flag:0 表示导入成功
				StringBuffer sb = new StringBuffer("{success:0,flag:0,result:'"+str[1]+"'}");
				//grantFromExcel(orgId, rNameArr,rRightsArr);
				setJsonString(sb.toString());
				return "upLoadSuccess"; //成功页面
			} else {
				StringBuffer sb = new StringBuffer("{success:0,flag:1,result:'"+str[1]+"'}");
				setJsonString(sb.toString());
				return "upLoadSuccess";
				//return "upLoadFalse";失败页面
			}

		}
		return null;

	}
	
	/**
	 * 检测Excel数据
	 */
	public String importExcData() {

			String[] str;

			//导入权限
			str=grantFromExcel(oid, rNameArr,rRightsArr);

			if (str[0].equals("Y")) {
				//success:1 表示导入信息,flag:0 表示导入成功
				StringBuffer sb = new StringBuffer("{success:1,flag:0,result:'"+str[1]+"'}");
				setJsonString(sb.toString());
				return "upLoadSuccess"; //成功页面
			} else {
				StringBuffer sb = new StringBuffer("{success:1,flag:1,result:'"+str[1]+"'}");
				setJsonString(sb.toString());
				return "upLoadSuccess";
				//return "upLoadFalse";失败页面
			}

	}
	/**
	 * 检查权限excel安全性
	 * @param filePath
	 * @return
	 */
	private String[] checkAccess(String filePath,String fileName){
		String[] str=new String[2];
		String[] accessIdArr;
		String[] ImportAccessIdArr;
		String[] strArr;
		Document grantMenuDoc = AppUtil.getGrantMenuDocument();
		Element rootElt = grantMenuDoc.getRootElement();
		for (Iterator iter = rootElt.elementIterator(); iter.hasNext();) {
			getSubEle((Element) iter.next(), "", orgId);
		}//获取机构对应的 权限 节点  
		accessIds=accessIds.substring(0,accessIds.lastIndexOf(","));
		accessIdArr=accessIds.split(",");
		if(!filePath.equals("")){
			strArr= ExcelUtils.checkAccessExcelData(filePath);
			if(!strArr[4].equals("")){
				str[0]="N";
				str[1]="<span>检测失败信息：</span><li>文件名称："+fileName+"</li><li>"+strArr[4]+"</li>";//判断 是否有错误
			}else if(accessIdArr==null || accessIdArr.length==0){
				str[0]="N";
				str[1]="<span>检测失败信息：</span><li>文件名称："+fileName+"</li><li>系统权限树有误请检查！</li>";
			}else if(accessIdArr.length!=Integer.valueOf(strArr[0])){
				str[0]="N";
				str[1]="<span>检测失败信息：</span><li>文件名称："+fileName+"</li><li>导入的权限节点数和 系统权限节点数不一致</li><li>系统中有："+String.valueOf(accessIdArr.length)+"个</li><li>导入的有："+strArr[0]+"个。</li>";
			}else if(strArr[2].equals("")){
				str[0]="N";
				str[1]="<span>检测失败信息：</span><li>文件名称："+fileName+"</li><li>导入的权限节点id 不能为空！</li>";
			}else{
				ImportAccessIdArr=strArr[2].split(",");
				int num=0;//记录导入的权限id 和系统id 是否一一对应
				String strContent="";
				String[] strCheckArr=StringUtil.checkIdIsRepeat(strArr[2],",");
				if(strCheckArr[0].equals("N")){
					str[0]="N";
					str[1]="<span>检测失败信息：</span><li>文件名称："+fileName+"</li><li>"+strCheckArr[1]+"</li>";
				}else{
				for(int i=0;i<ImportAccessIdArr.length;i++){//遍历导入的权限id
					for(int j=0;j<accessIdArr.length;j++){
						if(ImportAccessIdArr[i].equals(accessIdArr[j])){
							num++;
						}
					}
					if(num!=1){
						str[0]="N";
						strContent=strContent+ImportAccessIdArr[i]+",";
						
					}
					num=0;
				}
				}
				if(!strContent.equals("")){
					str[1]="<span>检测失败信息：</span><li>文件名称："+fileName+"</li><li>导入的权限节点和系统权限节点不一致，请检查导入的节点id："+strContent+"是否正确。</li>";
				}
				
				if(str[1]==null||str[1].equals("")){//当str[1] 为null 或空的时候检查角色是否已存在
					String[] roleNameArr=strArr[1].split(",");
					String sY="";
					String sN="";
					
					AppRole role;
					for(int j=0;j<roleNameArr.length;j++){
						role=appRoleService.getCountByOrgId(orgId, roleNameArr[j]);
						if(role!=null){
							sY=sY+roleNameArr[j]+",";
						}else{
							sN=sN+roleNameArr[j]+",";
						}
					}
					if(sY.equals("")){
						str[0]="Y";
						str[1]="<span>检测成功信息：</span><li>文件名称："+fileName+"</li><li>共检测到" +strArr[0]+"个权限节点。</li><li>共检测到"+roleNameArr.length+"个角色,分别有："+strArr[1]+"。</li><li>系统中未存在角色:"+sN+"</li><li>其中"+sN+"将被增加。</li>";
					}else if(sN.equals("")){
						str[0]="Y";
						str[1]="<span>检测成功信息：</span><li>文件名称："+fileName+"</li><li>共检测到" +strArr[0]+"个权限节点。</li><li>共检测到"+roleNameArr.length+"个角色,分别有："+strArr[1]+"。</li><li>系统中已存在角色："+sY+"</li><li>其中"+sY+"将被更新。</li>";
					}else{
						str[0]="Y";
						str[1]="<span>检测成功信息：</span><li>文件名称："+fileName+"</li><li>共检测到" +strArr[0]+"个权限节点。</li><li>共检测到"+roleNameArr.length+"个角色,分别有："+strArr[1]+"。</li><li>系统中已存在角色："+sY+"</li><li>系统中未存在角色:"+sN+"</li><li>其中"+sN+"将被增加</li><li>其中"+sY+"将被更新。</li>";
					}
					rNameArr=roleNameArr;
					rRightsArr=strArr[3].split("@");
					oid=orgId;
				}
			}
			
		}
		return str;
	}
	

	public String upLoadImgFile(File imgefile) {
	
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
		String dateString = simpleDateFormat.format(new Date());
		String uuid = UUID.randomUUID().toString();
		String subuuid = uuid.substring(0, 8) + uuid.substring(9, 13)
				+ uuid.substring(14, 18) + uuid.substring(19, 23)
				+ uuid.substring(24);
		String sourceProductImagePath = "attachFiles/excelupload/" + dateString
				+ "/" + subuuid + ".xls";
		File sourceProductImageFile = new File(ServletActionContext
				.getServletContext().getRealPath(sourceProductImagePath));
		File sourceProductImageParentFile = sourceProductImageFile
				.getParentFile();

		if (!sourceProductImageParentFile.exists()) {
			sourceProductImageParentFile.mkdirs();
		}
		try {

			FileUtils.copyFile(imgefile, sourceProductImageFile);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("记录上传出错" + e.getMessage());
		}
		sourceProductImagePath = ServletActionContext.getServletContext()
				.getRealPath(sourceProductImagePath);
		return sourceProductImagePath;
	}
}
