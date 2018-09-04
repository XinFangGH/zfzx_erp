package com.zhiwei.credit.action.system;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import nl.captcha.Captcha;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.security.AuthenticationManager;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.ui.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.ui.webapp.AuthenticationProcessingFilter;

import com.zhiwei.core.util.StringUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.system.AppUserService;

public class SignInAction extends BaseAction{	
	// must be same to app-security.xml
	private String key = "RememberAppUser";
	
	@Resource
	private AppUserService appUserService;
	
	@Resource(name = "authenticationManager")
	AuthenticationManager authenticationManager;
	
	private String username;
	
	private String password;
	
	private String checkCode;
	
	/**
	 * 登录
	 * @return
	 */
	public String signIn(){
		
		AppUser user=appUserService.findByUserName(username);
		
		if (user!=null) {
			//判断密码及用户名是否一致
			String newPassword = StringUtil.encryptSha256(password);
			//密码是否一致
			if(!user.getPassword().equals(newPassword)){
				getRequest().getSession().setAttribute("username",username);
				return INPUT;
			}
			//检查验证码
			// 取得验证码
			Captcha captcha = (Captcha) getSession().getAttribute(Captcha.NAME);
			// 验证码验证
			if (!captcha.isCorrect(checkCode)) {
				getRequest().getSession().setAttribute("username",username);
				return "codeError";
			}
			UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
			SecurityContext securityContext = SecurityContextHolder.getContext();
			securityContext.setAuthentication(authenticationManager.authenticate(authRequest));
			SecurityContextHolder.setContext(securityContext);
			getSession().setAttribute(AuthenticationProcessingFilter.SPRING_SECURITY_LAST_USERNAME_KEY,username);
			String rememberMe = getRequest().getParameter("_spring_security_remember_me");
			
			if (rememberMe != null && rememberMe.equals("on")) {
				// 加入cookie
				long tokenValiditySeconds = 1209600; // 14 days
				long tokenExpiryTime = System.currentTimeMillis() + (tokenValiditySeconds * 1000);
				String signatureValue = DigestUtils.md5Hex(username + ":" + tokenExpiryTime + ":" + user.getPassword() + ":" + key);
				String tokenValue = username + ":" + tokenExpiryTime + ":" + signatureValue;
				String tokenValueBase64 = new String(Base64.encodeBase64(tokenValue.getBytes()));
				getResponse().addCookie(makeValidCookie(tokenExpiryTime, tokenValueBase64));
			}
			
		}else{//用户不存在
			getRequest().getSession().setAttribute("username",username);
			return INPUT;
		}
		return SUCCESS;
	}
	
	// add Cookie
	protected Cookie makeValidCookie(long expiryTime, String tokenValueBase64) {
		HttpServletRequest request = getRequest();
		Cookie cookie = new Cookie(
				TokenBasedRememberMeServices.SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY,
				tokenValueBase64);
		cookie.setMaxAge(60 * 60 * 24 * 365 * 5); // 5 years
		cookie.setPath(org.springframework.util.StringUtils.hasLength(request
				.getContextPath()) ? request.getContextPath() : "/");
		return cookie;
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
	
	
	
}
