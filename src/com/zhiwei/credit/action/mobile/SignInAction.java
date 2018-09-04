package com.zhiwei.credit.action.mobile;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.AuthenticationManager;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;

import com.zhiwei.core.util.StringUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.Organization;
import com.zhiwei.credit.service.system.AppUserService;
import com.zhiwei.credit.service.system.OrganizationService;

public class SignInAction extends BaseAction{
	@Resource
	private AppUserService userService;
	@Resource
	private OrganizationService organizationService;
	@Resource(name="authenticationManager")
	private AuthenticationManager authenticationManager=null; 
	
	private String username;
	private String password;

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

	@Override
	public String execute() throws Exception {
		Organization o=this.organizationService.getGroupCompany();
		String userName=username+"@"+o.getKey();
		if(StringUtils.isNotEmpty(userName) && StringUtils.isNotEmpty(password)){
			AppUser user=userService.findByUserName(userName);
			if(user!=null){
				String enPassword=StringUtil.encryptSha256(password);
				if(enPassword.equals(user.getPassword())){
					
					UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(userName, password);
					SecurityContext securityContext = SecurityContextHolder.getContext();
					securityContext.setAuthentication(authenticationManager.authenticate(authRequest));
					SecurityContextHolder.setContext(securityContext);
					this.getRequest().getSession().setAttribute("loginType", "mobile"); 
					return SUCCESS;
				}
			}
		}
		
		return INPUT;
		
	}
}
