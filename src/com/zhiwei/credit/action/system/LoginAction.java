package com.zhiwei.credit.action.system;

/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
 */

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import nl.captcha.Captcha;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.AuthenticationManager;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.ui.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.ui.webapp.AuthenticationProcessingFilter;

import com.crypto.encrypt.EncryptData;
import com.crypto.encrypt.Util;
import com.hurong.core.Constants;
import com.hurong.credit.config.DynamicConfig;
import com.hurong.credit.util.MyUserSession;
import com.hurong.credit.util.TemplateConfigUtil;
import com.zhiwei.core.log.LogResource;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.Base64Encode;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.core.util.StringUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.filter.MySessionFilter;
import com.zhiwei.credit.dao.creditFlow.bonusSystem.setting.WebBonusSettingDao;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.Organization;
import com.zhiwei.credit.model.system.SysConfig;
import com.zhiwei.credit.service.creditFlow.log.UserloginlogsService;
import com.zhiwei.credit.service.system.AppRoleService;
import com.zhiwei.credit.service.system.AppUserService;
import com.zhiwei.credit.service.system.OrganizationService;
import com.zhiwei.credit.service.system.SysConfigService;
import com.zhiwei.credit.util.DesUtils;
import com.zhiwei.credit.util.GetMACUtil;
public class LoginAction extends BaseAction{
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
	private AppUserService appUserService;
	@Resource
	UserloginlogsService userloginlogsService;
	@Resource(name = "authenticationManager")
	private AuthenticationManager authenticationManager = null;
	private String returnURL;
	public String getReturnURL() {
		return returnURL;
	}
	public void setReturnURL(String returnURL) {
		this.returnURL = returnURL;
	}
	
	@Resource
	private WebBonusSettingDao webBonusSettingDao;
	
	@LogResource(description="登录系统")
	public String ajaxValidation(){
		try{
			//用户ip add by chencc
			String ipAddr = StringUtil.html2Text(this.getRequest().getRemoteAddr());
			String macAddr = userLoginAddr;
			String loginAddr = userLoginAddr;
			String userName=this.getRequest().getParameter("username");
			String password=this.getRequest().getParameter("password");
			String isMobile=this.getRequest().getParameter("isMobile");
			String isGesturePassword=this.getRequest().getParameter("isGesturePassword");
//			password = Base64Encode.decode(password);
//			String str[] = password.split("_");//去掉加密的key值
//			password = str[0];//获取密码
			String checkCode=this.getRequest().getParameter("checkCode");
			String company=this.getRequest().getParameter("company");
			StringBuffer msg = new StringBuffer("{\"msg\":'");
			Boolean flag=Boolean.valueOf(AppUtil.getSystemIsGroupVersion());
			Organization organization=null;
			java.util.Calendar.getInstance().getTime().getYear();
			
			//判断同一个ip 15分钟内登录失败次数
			Integer loginErrorNum = userloginlogsService.getLoginErrorNum(ipAddr,userName);
			if(loginErrorNum!=null&&loginErrorNum>=100){
				 msg.append("用户名或密码多次输入错误,请稍后在试或联系管理员!'");
				 msg.append(",\"type\":'loginCodeError'");
				 msg.append(",\"success\":false}");
				 setJsonString(msg.toString());
				 //userloginlogsService.saveSysLog(ipAddr, macAddr, loginAddr, userName, false,companyId);
				 return SUCCESS;
			}else{
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
				if (user != null) {
					SysConfig codeConfig = sysConfigService.findByKey("codeConfig");
					Captcha captcha = (Captcha) getSession().getAttribute(Captcha.NAME);
					if (codeConfig != null && codeConfig.getDataValue().equals(SysConfig.CODE_OPEN)) {
						if(isMobile==null||isMobile==""){
							if (captcha!=null && !captcha.getAnswer().toLowerCase().equals(checkCode.toLowerCase())) {
								this.getSession().setAttribute(Captcha.NAME,"");
								msg.append("验证码不正确!'");
								msg.append(",\"type\":'loginCodeError'");
								msg.append(",\"success\":false}");
								setJsonString(msg.toString());
								userloginlogsService.saveSysLog(ipAddr, macAddr, loginAddr, userName, false,companyId);
								return SUCCESS;
							}
						}
								String newPassword = StringUtil.encryptSha256(password);
								if (!user.getPassword().equals(newPassword)) {
								    msg.append("用户名或密码错误!'");
								    msg.append(",\"type\":'passwrodError'");
									msg.append(",\"success\":false}");
									setJsonString(msg.toString());
									Organization o=this.organizationService.getGroupCompany();
									userName=userName+"@"+o.getKey();
									userloginlogsService.saveSysLog(ipAddr, macAddr, loginAddr, userName, false,companyId);
									return SUCCESS;
								}else{
									if(user.getStatus().toString().equals("0")){
										msg.append("该账户被禁用，请联系管理员！'");
										msg.append(",\"type\":'userNameError'");
										msg.append(",\"success\":false}");
										setJsonString(msg.toString());
										userloginlogsService.saveSysLog(ipAddr, macAddr, loginAddr, userName, false,companyId);
										return SUCCESS;
									}else{
												//根据登录用户查找该用户对应门店
												Organization shopOrg=this.getShopByUser(user.getDepartment().getDepId());
												if(null!=shopOrg){
													this.getRequest().getSession().setAttribute("shopId",shopOrg.getOrgId().toString());
													this.getRequest().getSession().setAttribute("shopName",shopOrg.getOrgName());
												}
												
												if(null!=company && !"".equals(company) &&  !"null".equals(company)){
													this.getSession().setAttribute("company", company);
												}else{
													this.getRequest().getSession().setAttribute("CompanyId", "1");
												}
													UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(userName, password);
													SecurityContext securityContext = SecurityContextHolder.getContext();
													securityContext.setAuthentication(authenticationManager.authenticate(authRequest));
													SecurityContextHolder.setContext(securityContext);
													getSession().setAttribute(AuthenticationProcessingFilter.SPRING_SECURITY_LAST_USERNAME_KEY, userName);
													String companyIds=appRoleService.getControlCompanyId(user);
													boolean isOnlyHaveControlRole=appRoleService.isOnlyHaveControlRole(user);
													if(flag && "".equals(companyIds)){
														if(null==organization){
															organization=this.organizationService.getGroupCompany();
														}
															this.getRequest().getSession().setAttribute("RoleType", "business"); //角色类别
															this.getRequest().getSession().setAttribute("CompanyId", String.valueOf(organization.getOrgId()));
															this.getRequest().getSession().setAttribute("ActiveCompanyId", String.valueOf(organization.getOrgId()));
															this.getRequest().getSession().setAttribute("Control CompanyId",String.valueOf(organization.getOrgId()));
															this.getRequest().getSession().setAttribute("users",user);//session保存user信息 退出系统时获取
													}
													if(flag && isOnlyHaveControlRole && !"".equals(companyIds)){ //集团版本 具有管控角色 并且只有管控角色
														if(null==organization){
														    organization=this.organizationService.getGroupCompany();
														}
															this.getRequest().getSession().setAttribute("RoleType", "control"); //角色类别
															this.getRequest().getSession().setAttribute("CompanyId", String.valueOf(organization.getOrgId()));
															this.getRequest().getSession().setAttribute("ActiveCompanyId",companyIds);    
															this.getRequest().getSession().setAttribute("Control CompanyId",companyIds);
															this.getRequest().getSession().setAttribute("users",user);//session保存user信息 退出系统时获取
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
														this.getRequest().getSession().setAttribute("users",user);//session保存user信息 退出系统时获取
														//this.getRequest().getSession().setAttribute("RoleType", "business"); 
													}
													
													//集团版数据分离会用到 begin
													StringBuffer userIds=new StringBuffer();
													List<AppUser> userList = appUserService.findRelativeUsersByUserId(user.getUserId());
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
													
													//(初始化桌面条件参数)----开始
													Map<String,String> deskMenus=AppUtil.getDeskMenus();
													if(null!=deskMenus){
														this.getRequest().getSession().setAttribute("initDeskMenus",deskMenus);
													}
													//----结束
													
													HttpSession session =(HttpSession)MySessionFilter.session.get();
													// 实现单一登录 踢人
					
									                /*   if ( null != SessionListener.sessionMap.get(user.getUserId())) {   
					
									                            //第一次登录的用户session销毁
					
									                            //将第一次登录用户的信息从map中移除
									                	        logger.info("因在其它地方重新登录被系统强制退出"); 
									                	        ContextUtil.loginOutInfo="因在其它地方重新登录被系统强制退出";
									                	        this.getRequest().getSession().setAttribute("usLoginFo","");
									                            this.forceLogoutUser(user.getUserId());
									                            
									                            //本次登录用户添加到map中                                                                    
									                            SessionListener.sessionMap.put(user.getUserId(), session);                                                                               
					
									                   } else{      
					
									                            //以用户id为key键存入map中，以判断下一次登录的人
									                	       ContextUtil.loginOutInfo="";
									                	       this.getRequest().getSession().setAttribute("usLoginFo","usLoginFo");
									                            SessionListener.sessionMap.put(user.getUserId(), session);
					
									                   }	*/							
													userloginlogsService.saveSysLog(ipAddr, macAddr, loginAddr, userName, true,companyId);//加入用户登录记录 add by chencc
													if(isMobile!=null&&isMobile.endsWith("1")){
														jsonString=userService.getCurUserInfoOfmobile();
														System.out.println(jsonString);
														setJsonString(jsonString);
														return SUCCESS;
													}
													setJsonString("{success:true}");
										}
									}
						
					}
				}else{
					msg.append("用户名或密码错误.'");
					msg.append(",\"type\":'userNameError'");
					msg.append(",\"success\":false}");
					userloginlogsService.saveSysLog(ipAddr, macAddr, loginAddr, username, false,companyId);
					setJsonString(msg.toString());
					return SUCCESS;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return SUCCESS;
//注释掉这段代码 liny 2012-12-26
	/*if (user != null) {
			String newPassword = StringUtil.encryptSha256(password);
			if (!user.getPassword().equals(newPassword)) {
			    msg.append("密码不正确!'");
			    msg.append(",type:'passwrodError'");
				msg.append(",success:false}");
				setJsonString(msg.toString());
				System.out.println(msg.toString());
			}if(user.getStatus().toString().equals("0")){
				msg.append("该账户被禁用，请联系管理员！'");
				msg.append(",type:'userNameError'");
				msg.append(",success:false}");
				setJsonString(msg.toString());
			}
			else{
				SysConfig codeConfig = sysConfigService.findByKey("codeConfig");
				Captcha captcha = (Captcha) getSession().getAttribute(Captcha.NAME);
				if (codeConfig != null && codeConfig.getDataValue().equals(SysConfig.CODE_OPEN)) 
				{
					if (captcha!=null && !captcha.isCorrect(checkCode)) {
						 msg.append("验证码不正确!'");
						 msg.append(",type:'loginCodeError'");
						 msg.append(",success:false}");
						 setJsonString(msg.toString());
						 System.out.println(msg.toString());
					}
					else{
						
						if(null!=company && !"".equals(company) &&  !"null".equals(company)){
							this.getSession().setAttribute("company", company);
						}
						else{
							this.getRequest().getSession().setAttribute("CompanyId", "1");
						}
						UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(userName, password);
						SecurityContext securityContext = SecurityContextHolder.getContext();
						securityContext.setAuthentication(authenticationManager.authenticate(authRequest));
						SecurityContextHolder.setContext(securityContext);
						getSession().setAttribute(AuthenticationProcessingFilter.SPRING_SECURITY_LAST_USERNAME_KEY, userName);
						String companyIds=appRoleService.getControlCompanyId(user);
						boolean isOnlyHaveControlRole=appRoleService.isOnlyHaveControlRole(user);
						if(flag && "".equals(companyIds)){ //表示是只具有业务角色
							if(null==organization)
								organization=this.organizationService.getGroupCompany();
							this.getRequest().getSession().setAttribute("RoleType", "business"); //角色类别
							this.getRequest().getSession().setAttribute("CompanyId", String.valueOf(organization.getOrgId()));
							this.getRequest().getSession().setAttribute("ActiveCompanyId", String.valueOf(organization.getOrgId()));
							this.getRequest().getSession().setAttribute("Control CompanyId",String.valueOf(organization.getOrgId()));
						}
						if(flag && isOnlyHaveControlRole && !"".equals(companyIds)){ //集团版本 具有管控角色 并且只有管控角色
							if(null==organization)
							organization=this.organizationService.getGroupCompany();
							
							this.getRequest().getSession().setAttribute("RoleType", "control"); //角色类别
							this.getRequest().getSession().setAttribute("CompanyId", String.valueOf(organization.getOrgId()));
							this.getRequest().getSession().setAttribute("ActiveCompanyId",companyIds);    
							this.getRequest().getSession().setAttribute("Control CompanyId",companyIds);
						}
						else {
							//this.getRequest().getSession().setAttribute("RoleType", "business"); 
						}
						sysLogService.saveSysLog(ipAddr, macAddr, loginAddr, username, true,companyId);//加入用户登录记录 add by chencc

						setJsonString("{success:true}");
					}
				}
			}
		}else{
			msg.append("用户不存在.'");
			msg.append(",type:'userNameError'");
			msg.append(",success:false}");
			setJsonString(msg.toString());
		}
		return SUCCESS;*/
		
	}
	
	/**
	 * 根据用户 查询相应门店
	 * @return
	 */
	public Organization getShopByUser(Long depId){
		Organization org=organizationService.get(depId);
		if(null==org){
			return null;
		}else if(org.getOrgType()!=3){
			return getShopByUser(org.getOrgSupId());
		}else{
			return org;
		}
	}
	
	/**
	 * jsp登录
	 * 
	 * @return
	 */
	public String login() {
		
		String company = this.getRequest().getParameter("company");
		if(null!=company && !"".equals(company) &&  !"null".equals(company)){
			this.getSession().setAttribute("company", company);
		}
		//用户ip add by chencc
		String ipAddr = this.getRequest().getRemoteAddr();
		String macAddr = userLoginAddr;
		String loginAddr = userLoginAddr;
		//
	
		AppUser user=null;
		Organization organization=null;
		Boolean flag=Boolean.valueOf(AppUtil.getSystemIsGroupVersion());
		if(flag){
			if(null!=company && !"".equals(company) &&  !"null".equals(company)){
				organization=this.organizationService.getBranchCompanyByKey(company);
				user = userService.findByUserNameAndConmpany(username,organization.getPath());
				companyId=organization.getOrgId();
			}
			else{
				Organization o=this.organizationService.getGroupCompany();
				List<Organization> oss=this.organizationService.getDepartmentByCompany(o.getOrgId());
			    user = userService.findByUserNameAndOrganization(username, oss);
			    companyId=o.getOrgId();
			}
		}
		else{
			this.getRequest().getSession().setAttribute("CompanyId", "1");//如果不是集团版本 默认登录公司ID为1 shendexuan
			user=userService.findByUserName(username);
		}
		SysConfig codeConfig = sysConfigService.findByKey("codeConfig");
		Captcha captcha = (Captcha) getSession().getAttribute(Captcha.NAME);
		if (codeConfig != null && codeConfig.getDataValue().equals(SysConfig.CODE_OPEN)) 
		{
			if (captcha!=null && !captcha.isCorrect(checkCode)) {
				 //addFieldError("checkCode","验证码不正确!");
				userloginlogsService.saveSysLog(ipAddr, macAddr, loginAddr, username, false,companyId);
				 this.getRequest().setAttribute("company", company);
				 return INPUT;
			}
		}
	    if (user != null) {
			String newPassword = StringUtil.encryptSha256(password);
			if (!user.getPassword().equals(newPassword)) {
			    //addFieldError("password","密码不正确!");
				userloginlogsService.saveSysLog(ipAddr, macAddr, loginAddr, username, false,companyId);
			    this.getRequest().setAttribute("company", company);
			    return INPUT;
			}
			else{
					UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
					SecurityContext securityContext = SecurityContextHolder.getContext();
					securityContext.setAuthentication(authenticationManager.authenticate(authRequest));
					SecurityContextHolder.setContext(securityContext);
					getSession().setAttribute(AuthenticationProcessingFilter.SPRING_SECURITY_LAST_USERNAME_KEY, username);
					String rememberMe = getRequest().getParameter("_spring_security_remember_me");
					if (rememberMe != null && rememberMe.equals("on")) {
						long tokenValiditySeconds = 1209600;
						long tokenExpiryTime = System.currentTimeMillis() + (tokenValiditySeconds * 1000);
						String signatureValue = DigestUtils.md5Hex(username + ":" + tokenExpiryTime + ":" + user.getPassword() + ":" + key);
						String tokenValue = username + ":" + tokenExpiryTime + ":" + signatureValue;
						String tokenValueBase64 = new String(Base64.encodeBase64(tokenValue.getBytes()));
						getResponse().addCookie(makeValidCookie(tokenExpiryTime, tokenValueBase64));
					}
					String companyIds=appRoleService.getControlCompanyId(user);
					if(flag && "".equals(companyIds)){ //表示是只具有业务角色
						if(null==organization)
							organization=this.organizationService.getGroupCompany();
						this.getRequest().getSession().setAttribute("RoleType", "business"); //角色类别
						this.getRequest().getSession().setAttribute("CompanyId", String.valueOf(organization.getOrgId()));
						this.getRequest().getSession().setAttribute("ActiveCompanyId", String.valueOf(organization.getOrgId()));
						this.getRequest().getSession().setAttribute("Control CompanyId",String.valueOf(organization.getOrgId()));
					}
					userloginlogsService.saveSysLog(ipAddr, macAddr, loginAddr, username, true,companyId);//加入用户登录记录 add by chencc
								}
		} else {
			// addFieldError("username","用户不存在!");
			userloginlogsService.saveSysLog(ipAddr, macAddr, loginAddr, username, false,companyId);
			 this.getRequest().setAttribute("company", company);
		     return INPUT;
		}
		return SUCCESS;
	}

	/**
	 * Ext登录
	 * 
	 * @return
	 */
	public String authorization(){
		String keyP=this.getRequest().getParameter("key");
		String password=this.getRequest().getParameter("password");
	     String macSt=GetMACUtil.getMacStr(); //本台机器的mac地址
	     DesUtils des=new DesUtils(password);
	     String savestr=keyP+","+password;
	    try{
	         String desContent=des.decrypt(keyP);
	         if(null!=desContent && !"".equals(desContent)){
	        	 
	        	 if(desContent.split(",").length>1){
	        		 String [] strtemp=desContent.split(",");
	        		 String desCode=strtemp[0]; //机器码
	        		 String date=strtemp[1];  //授权日期
	        		 Integer days=com.zhiwei.core.util.DateUtil.getDaysBetweenDate(DateUtil.getNowDateTime("yyyy-MM-dd"), date);
                     if(!desCode.equals(macSt))
                     {
                    	 setJsonString("{success:false,msg:'密匙或者授权码不正确!'}");
                    	 return SUCCESS;
                     }
                     else{
                    	  if(days<0){
                    		  setJsonString("{success:false,msg:'授权已过期!'}");
                    		  return SUCCESS;
                    	  }
                    	  else{
                    		    String path = this.getRequest().getSession().getServletContext().getRealPath("");
                    		    System.out.println(path);
  	     		    	        String newPath=path+"\\attachFiles\\authorizationFile\\authorization.txt";
  	     		    	        path+="\\attachFiles\\authorizationFile\\temp.txt";
    	     		    		File   f= new File(path); 
    	     		    		if(f.exists()){
    	     		    			f.delete();
    	     		    		}
    	     		    		FileOutputStream   fos   =   null; 
    	     		    		f.createNewFile(); 
    	     					fos=new FileOutputStream(f); 
    	     					fos.write(savestr.getBytes("GBK"));
    	     					fos.close(); 
    	     					System.gc();
    	     					String str=Util.getValue("keypath");
    	     					EncryptData ed=new EncryptData(this.getRequest().getSession().getServletContext().getRealPath("")+"\\"+str);
    	     					try
    	     					{
    	     					     ed.createEncryptData(path,newPath);
    	     					     if(f.exists())
    	     					    	 f.delete();
    	     					}
    	     					catch (Exception e) {
    	     						e.printStackTrace();
    	     					}
    	     					finally{
    	     						System.gc();
    	     					}
                    	  }
                     }
	        	 }
	        	 else{
	        		 
	        		  if(!desContent.equals(macSt)){
	     		    	 setJsonString("{success:false}");
	     		    	 return SUCCESS;
	     		     }else{
	     		    	    String path = this.getRequest().getSession().getServletContext().getRealPath("");
	     		    	    String newPath=path+"\\attachFiles\\authorizationFile\\authorization.txt";
	     		    	    path+="\\attachFiles\\authorizationFile\\temp.txt";
	     		    		File   f= new File(path); 
	     		    		if(f.exists()){
	     		    			f.delete();
	     		    		}
	     		    		FileOutputStream   fos   =   null; 
	     		    		f.createNewFile(); 
	     					fos=new FileOutputStream(f); 
	     					fos.write(savestr.getBytes("GBK"));
	     					fos.close(); 
	     					System.gc();
	     					String str=Util.getValue("keypath");
	     					EncryptData ed=new EncryptData(str);
	     					try
	     					{
	     					     ed.createEncryptData(path,newPath);
	     					}
	     					catch (Exception e) {
	     						setJsonString("{success:false,msg:'密匙或者授权码不正确!'}");
	     						return SUCCESS;
	     					}
	     		     }	 
	 }
	     }
	    }catch (Exception e) {
			setJsonString("{success:false,msg:'密匙或者授权码不正确!'}");
			return SUCCESS;
		}
		return SUCCESS;
}
	// add Cookie
	protected Cookie makeValidCookie(long expiryTime, String tokenValueBase64) {
		HttpServletRequest request = getRequest();
		Cookie cookie = new Cookie(TokenBasedRememberMeServices.SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY, tokenValueBase64);
		cookie.setMaxAge(60 * 60 * 24 * 365 * 5); // 5 years
		cookie.setPath(org.springframework.util.StringUtils.hasLength(request.getContextPath()) ? request.getContextPath() : "/");
		return cookie;
	}
	public String logout (){
		
		HttpSession session =(HttpSession)MySessionFilter.session.get();
	    String company=(String)session.getAttribute("company");
	    AppUser u=(AppUser)session.getAttribute("users");
	    String loginType=(String) session.getAttribute("loginType");

	    Long uid=null;
		if(null!=u){
		   uid=u.getUserId();
		}
	    if(null==session.getAttribute("company")){
	    	if(loginType!=null&&loginType.equals("mobile")){
	    		returnURL="mobile/login.jsp";
		    	session.invalidate();
	    	}else{
	    	returnURL="index.jsp";
	    	if(uid!=null){
	    		this.forceLogoutUser(uid);
	    	}
	    	session.invalidate();
	    	}
	    	return SUCCESS;
	    }
	    else{
	    	if(loginType!=null&&loginType.equals("mobile")){
	    		returnURL="mobile/login.jsp";
		    	session.invalidate();
	    	}else{
	    	returnURL="branch/"+company+"/";
	    	if(uid!=null){
	    		this.forceLogoutUser(uid);
	    	}
	    	session.invalidate();
	    	}
	    	return SUCCESS;
		}
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

	public String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

	private boolean dyPwdCheck(StringBuffer msg, boolean login) {
		// 取得动态密码配置
		SysConfig dyPwdConfig = sysConfigService.findByKey("dynamicPwd");
		// 判断是否需要动态密码
		if (dyPwdConfig != null && dyPwdConfig.getDataValue().equals(SysConfig.DYPWD_OPEN)) {
			// 动态密码配置为打开状态
			if (user.getUserId().longValue() == AppUser.SUPER_USER.longValue()) {
				// 假如是超级管理员,则不需要动态密码
				login = true;
			} else {
				if (StringUtils.isEmpty(user.getDynamicPwd())) {
					msg.append("此用户未有令牌,请联系管理员.'");
				} else if (user.getDyPwdStatus().shortValue() == AppUser.DYNPWD_STATUS_UNBIND.shortValue()) {
					msg.append("此用户令牌未绑定,请联系管理员.'");
				} else {
					String curDynamicPwd = getRequest().getParameter("curDynamicPwd");
					HashMap<String, String> input = new HashMap<String, String>();
					input.put("app", "demoauthapp");
					input.put("user", user.getDynamicPwd());
					input.put("pw", curDynamicPwd);

					String result = userService.initDynamicPwd(input, "verify");
					if (result.equals("ok")) {
						if (user.getStatus() == 1) {
							login = true;
						} else
							msg.append("此用户已被禁用.'");
					} else {
						msg.append("令牌不正确,请重新输入.'");
					}
				}
			}

		} else {
			// 此处不需要动态密码
			// 判断用户是否被禁用,超级管理员不可被禁用
			if (user.getStatus() == 1 || user.getUserId().longValue() == AppUser.SUPER_USER.longValue()) {
				login = true;
			} else
				msg.append("此用户已被禁用.'");
		}

		return login;
	}

	/**
	 * 验证码 <no use>
	 * 
	 * @return
	 */
	public String validateCaptcha() {
		// 定义验证信息
		StringBuffer msg = new StringBuffer("{msg:'");
		// 取得验证码配置
		SysConfig codeConfig = sysConfigService.findByKey("codeConfig");

		// 取得验证码
		Captcha captcha = (Captcha) getSession().getAttribute(Captcha.NAME);
		// 判断是否需要验证码验证
		if (codeConfig != null && codeConfig.getDataValue().equals(SysConfig.CODE_OPEN)) {
			if (captcha == null) {
				msg.append("请刷新验证码再登录.'");
			} else {
				// 验证码验证
				if (captcha.isCorrect(checkCode)) {
					// login = dyPwdCheck(msg, login);
					setJsonString("{success:true}");
				} else {
					msg.append("验证码不正确.'");
					msg.append(",failure:true}");
					setJsonString(msg.toString());
				}
			}
		} else {
			// 此处不需要验证码验证
			// login = dyPwdCheck(msg, login);
			setJsonString("{success:true}");
		}
		return SUCCESS;
	}
}
