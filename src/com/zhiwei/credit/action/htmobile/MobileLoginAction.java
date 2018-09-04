package com.zhiwei.credit.action.htmobile;

/*
 *  广州宏天软件有限公司 OA办公管理系统   --  http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
 */

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import nl.captcha.Captcha;

import org.springframework.security.AuthenticationManager;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.ui.webapp.AuthenticationProcessingFilter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zhiwei.core.log.LogResource;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.StringUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.filter.MySessionFilter;
import com.zhiwei.credit.model.system.AppRole;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.Organization;
import com.zhiwei.credit.model.system.SysConfig;
import com.zhiwei.credit.model.system.UserOrg;
import com.zhiwei.credit.service.creditFlow.log.UserloginlogsService;
import com.zhiwei.credit.service.system.AppRoleService;
import com.zhiwei.credit.service.system.AppUserService;
import com.zhiwei.credit.service.system.OrganizationService;
import com.zhiwei.credit.service.system.SysConfigService;
import com.zhiwei.credit.service.system.UserOrgService;



public class MobileLoginAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private AppUser user;
	private String username;
	private String password;
	private String checkCode;// 验证码
	private String userLoginAddr;//登录IP add by chencc
	private Long companyId;

	// must be same to app-security.xml
	private String key = "RememberAppUser";
     
	@Resource
	private OrganizationService organizationService;
	
	// private String rememberMe;//自动登录
	@Resource
	private AppUserService userService;
	@Resource
	private SysConfigService sysConfigService;
	@Resource
	private AppRoleService appRoleService;
	@Resource
	UserloginlogsService userloginlogsService;
	@Resource(name = "authenticationManager")
	private AuthenticationManager authenticationManager = null;
	@Resource
	private UserOrgService userOrgService;
	private String returnURL;
	public String getReturnURL() {
		return returnURL;
	}
	public void setReturnURL(String returnURL) {
		this.returnURL = returnURL;
	}
	public String loginMobile() {
		try{
			String ipAddr = this.getRequest().getRemoteAddr();
			String macAddr = userLoginAddr;
			String loginAddr = userLoginAddr;
			String userName=this.getRequest().getParameter("username");
			String password=this.getRequest().getParameter("password");
			String newPassword ="";
			if(password != null){
				 newPassword = StringUtil.encryptSha256(password);
			}
			String isGesturePassword=this.getRequest().getParameter("isGesturePassword");
			
			String company=this.getRequest().getParameter("company");
			StringBuffer msg = new StringBuffer("{\"msg\":");
			Boolean flag=Boolean.valueOf(AppUtil.getSystemIsGroupVersion());
			Organization organization=null;
			AppUser user=null;
			if(flag){
				if(null!=company && !"".equals(company) &&  !"null".equals(company)){
					organization=this.organizationService.getBranchCompanyByKey(company);
					userName=userName+"@"+organization.getKey();
					user = userService.findByUserNameAndConmpany(userName,organization.getPath());
					companyId=organization.getOrgId();
				}
				else{//不采用/branch/分公司key/的登陆形式登陆
					List<AppUser> userList = userService.findByUserNameAndAt(userName);
					if(userList!=null&&userList.size()>0){//有名字为 userName@***的appUser
						Iterator iterator = userList.iterator();
						while(iterator.hasNext()){
							user = (AppUser)iterator.next();
							if(user.getPassword().equals(newPassword)||(null!=isGesturePassword&&isGesturePassword.equals("true"))){
								userName = user.getUsername();
								break;
							}
						}
					}
					//当前 分公司
					if(null!=user){
						if(null==user.getOrgId() || "".equals(user.getOrgId())){
							String nameStr= user.getUsername();
							organization=this.organizationService.getBranchCompanyByKey(nameStr.substring(nameStr.indexOf("@")+1));
						}else{
							//查中间表@中金亿信
							List<UserOrg> uoList=userOrgService.getDepOrgsByUserId(user.getUserId());
							if(null!=uoList && uoList.size()>0){
								UserOrg uo=uoList.get(0);
								Organization org=uo.getOrganization();
								if(1!=org.getOrgSupId()){
									organization=this.organizationService.get(org.getOrgSupId());
								}else{
									organization=org;
								}
							}
						}
						if(organization==null&&user.getOrgId()==null){
							organization=this.organizationService.getGroupCompany();//总公司的特殊处理
						}
						if(organization==null){
							companyId=user.getOrgId();
						}else{
							companyId=organization.getOrgId();
						}
					}
				}
			}else{
				Organization o=this.organizationService.getGroupCompany();
				userName=userName+"@"+o.getKey();
				user=userService.findByUserName(userName);
				companyId=o.getOrgId();
			}
			//手势密码
			if(null!=isGesturePassword&&isGesturePassword.equals("true")){
				 newPassword=user.getPassword();
			}
			if (user != null) {
		//	String newPassword = StringUtil.encryptSha256(password);
				if (!user.getPassword().equals(newPassword)) {
				    msg.append("\"密码不正确!\"");
				    msg.append(",\"type\":\"passwrodError\"");
					msg.append(",\"success\":false}");
					setJsonString(msg.toString());
					Organization o=this.organizationService.getGroupCompany();
					userName=userName+"@"+o.getKey();
					userloginlogsService.saveSysLog(ipAddr, macAddr, loginAddr, userName, false,companyId);
				}else{
					if(user.getStatus().toString().equals("0")){
						msg.append("该账户被禁用，请联系管理员！\"");
						msg.append(",\"type\":\"userNameError\"");
						msg.append(",\"success\":false}");
						setJsonString(msg.toString());
						userloginlogsService.saveSysLog(ipAddr, macAddr, loginAddr, userName, false,companyId);
					}else{
 						SysConfig codeConfig = sysConfigService.findByKey("codeConfig");
						Captcha captcha = (Captcha) getSession().getAttribute(Captcha.NAME);
						if (codeConfig != null && codeConfig.getDataValue().equals(SysConfig.CODE_OPEN)) {
							
								String nameStr= user.getUsername();
								Organization oc = organizationService.getBranchCompanyByKey(nameStr.substring(nameStr.indexOf("@")+1));
								
								if(oc!=null){
									company = oc.getKey();
									this.getRequest().getSession().setAttribute("company",company);
								}else{
//									this.getRequest().getSession().setAttribute("company", "1");
								}
								
								UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(userName, newPassword);
								SecurityContext securityContext = SecurityContextHolder.getContext();
								securityContext.setAuthentication(authenticationManager.authenticate(authRequest));
								SecurityContextHolder.setContext(securityContext);
								getSession().setAttribute(AuthenticationProcessingFilter.SPRING_SECURITY_LAST_USERNAME_KEY, userName);
								String companyIds=appRoleService.getControlCompanyId(user);
								
								//add by zcb 2014.6.30 begin
								Set<AppRole> set= user.getRoles();
							    Iterator iterator=set.iterator();
							    while(iterator.hasNext()){
							    	AppRole role2=(AppRole)iterator.next();
							    	if("business".equals(role2.getRoleType())){
							    		this.getRequest().getSession().setAttribute("LoginUserBusinessRights",role2.getRights());
							    	}else if("control".equals(role2.getRoleType())){
							    		this.getRequest().getSession().setAttribute("LoginUserControlRights",role2.getRights());
							    	}
							    }
							   //add by zcb 2014.6.30  end
							    
							//	boolean isOnlyHaveControlRole=appRoleService.isOnlyHaveControlRole(user);
								if(flag && "".equals(companyIds)){//集团版 不具有管控角色
									if(null==organization){
										organization=this.organizationService.getGroupCompany();
									}
									this.getRequest().getSession().setAttribute("RoleType", "business"); //角色类别
									this.getRequest().getSession().setAttribute("CompanyId", String.valueOf(organization.getOrgId()));
									this.getRequest().getSession().setAttribute("ActiveCompanyId", String.valueOf(organization.getOrgId()));
									this.getRequest().getSession().setAttribute("Control CompanyId",String.valueOf(organization.getOrgId()));
									this.getRequest().getSession().setAttribute("users",user);//session保存user信息 退出系统时获取
									this.getRequest().getSession().setAttribute("CompanyName", organization.getAcronym());//分公司简称  add by zcb 2014.4.17
									this.getRequest().getSession().setAttribute("CompanyNumber", organization.getKey());//分公司编号
								}
								if(flag && /*isOnlyHaveControlRole &&*/ !"".equals(companyIds)){ //集团版本 具有管控角色 并且只有管控角色
									if(null==organization){
									    organization=this.organizationService.getGroupCompany();
									}
									this.getRequest().getSession().setAttribute("RoleType", "control"); //角色类别
									this.getRequest().getSession().setAttribute("CompanyId", String.valueOf(organization.getOrgId()));
									this.getRequest().getSession().setAttribute("ActiveCompanyId",companyIds);    
									this.getRequest().getSession().setAttribute("Control CompanyId",companyIds);
									this.getRequest().getSession().setAttribute("users",user);//session保存user信息 退出系统时获取
									this.getRequest().getSession().setAttribute("CompanyName", organization.getAcronym());//分公司简称  add by zcb 2014.4.17
									this.getRequest().getSession().setAttribute("CompanyNumber", organization.getKey());//分公司编号
								}else {
									if(null==organization){
										if(company==null||"".equals(company)||"null".equals(company)){
											organization=this.organizationService.getGroupCompany();
										}else{
											organization=this.organizationService.getBranchCompanyByKey(company);
										}
										company = organization.getKey();
									}
									this.getSession().setAttribute("company", company);
									if(flag){
										this.getRequest().getSession().setAttribute("CompanyId", String.valueOf(organization.getOrgId()));
									}else{
										this.getRequest().getSession().setAttribute("CompanyId", 1);
										this.getRequest().getSession().setAttribute("ActiveCompanyId","1"); 
									}
									this.getRequest().getSession().setAttribute("CompanyNumber", organization.getKey());//分公司编号
									this.getRequest().getSession().setAttribute("users",user);//session保存user信息 退出系统时获取
									if(flag && !"".equals(companyIds)){
										
										this.getRequest().getSession().setAttribute("Control CompanyId",companyIds);//add by zcb 2014.4.18
									}
								}
								//集团版数据分离会用到 begin
								StringBuffer userIds=new StringBuffer();
								List<AppUser> userList = userService.findRelativeUsersByUserId(user.getUserId());
								if(null!=userList && userList.size() > 0) {
									for(AppUser appUser : userList) {
										userIds.append(appUser.getUserId());
										userIds.append(",");
									}
									userIds.append(user.getUserId());//当前登录用户以及其所有下属用户
								}else {
									userIds.append(user.getUserId());
								}
								this.getRequest().getSession().setAttribute("userIds", userIds); 
								//end
								
								HttpSession session =(HttpSession)MySessionFilter.session.get();
									userloginlogsService.saveSysLog(ipAddr, macAddr, loginAddr, userName, true,companyId);//加入用户登录记录 add by chencc
									jsonString=userService.getCurUserInfoOfmobile();
								
						}
					}
				}
			}else{
				msg.append("\"用户不存在。\"");
				msg.append(",\"type\":\"userNameError\"");
				msg.append(",\"success\":false}");
				userloginlogsService.saveSysLog(ipAddr, macAddr, loginAddr, username, false,companyId);
				//this.getRequest().setAttribute("company", company);
				setJsonString(msg.toString());
		    }
			msg.append("登陆成功\"");
			msg.append(",\"type\":\"userNameError\"");
			msg.append(",\"success\":true}");
			System.out.println(msg.toString());
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		
		return SUCCESS;

		
	}
	public String  isSessinonValid(){
		//if(null!=isMobile&&isMobile.endsWith("1"))
		AppUser appUser=ContextUtil.getCurrentUser();
		StringBuffer sb = new StringBuffer();
		if(null==appUser){
			
			sb.append("{\"success\":false");
			
		}else{
			Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			
			sb.append("{\"success\":true,\"data\":");
			sb.append(gson.toJson(appUser));
		}
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
		
	}
	public String loginMobile1() {
		Long companyId=Long.valueOf("1");
		String ipAddr = this.getRequest().getRemoteAddr();
		String userName=this.getRequest().getParameter("username");
		String password=this.getRequest().getParameter("password");
		String newPassword ="";
		if(password != null){
			 newPassword = StringUtil.encryptSha256(password);
		}
		String isGesturePassword=this.getRequest().getParameter("isGesturePassword");
		String company=this.getRequest().getParameter("company");
		StringBuffer msg = new StringBuffer("{msg:'");
		Boolean flag=Boolean.valueOf(AppUtil.getSystemIsGroupVersion());
		Organization organization=null;
		AppUser user=null;
		
		if(flag){
			if(null!=company && !"".equals(company) &&  !"null".equals(company)){
				organization=this.organizationService.getBranchCompanyByKey(company);
				userName=userName+"@"+organization.getKey();
				user = userService.findByUserNameAndConmpany(userName,organization.getPath());
				companyId=organization.getOrgId();
			}
			else{
				Organization o=this.organizationService.getGroupCompany();
				userName=userName+"@"+o.getKey();
				List<Organization> oss=this.organizationService.getOrganizationsGroup(o);
			    user = userService.findByUserNameAndOrganization(userName,oss);
			    companyId=o.getOrgId();
			}
		}else{
			Organization o=this.organizationService.getGroupCompany();
			userName=userName+"@"+o.getKey();
			user=userService.findByUserName(userName);
			companyId=o.getOrgId();
		}
		 user = userService.findByUserName(userName);
		if (user == null) {
			jsonString = "{\"success\":false,\"msg\":\"该用户账号不存在!\"}";
			return SUCCESS;
		}
		if (user.getStatus() != 1) {
			jsonString = "{\"success\":false,\"msg\":\"账号已经被禁用!\"}";
			return SUCCESS;
		}
		//手势密码
		if(null!=isGesturePassword&&isGesturePassword.equals("true")){
			 newPassword=user.getPassword();
		}
		// 判断密码及用户名是否一致
	//	String newPassword = DataEncryptUtil.encryptSha256(password);
		
		// 密码是否一致
		if (!user.getPassword().equals(newPassword)) {
			jsonString = "{\"success\":false,\"msg\":\"密码不正确\"}";
			return SUCCESS;
		}
		
		try {
			UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
					userName, newPassword);
			SecurityContext securityContext = SecurityContextHolder
					.getContext();
			securityContext.setAuthentication(authenticationManager
					.authenticate(authRequest));
			SecurityContextHolder.setContext(securityContext);
			getSession()
					.setAttribute(
							AuthenticationProcessingFilter.SPRING_SECURITY_LAST_USERNAME_KEY,
							userName);

		//	jsonString = "{\"success\":true,\"username\":\""+user.getFullname()+"\",\"userId\":\""+user.getUserId()+"\"}";
			jsonString=userService.getCurUserInfoOfmobile();
		} catch (Exception ex) {
			ex.printStackTrace();
			jsonString = "{\"success\":false,\"msg\":\"" + ex.getCause() + "\"}";
			return SUCCESS;
		}
      System.out.println(jsonString);
		return SUCCESS;
	}
	public AppUser getUser() {
		return user;
	}

	public void setUser(AppUser user) {
		this.user = user;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
