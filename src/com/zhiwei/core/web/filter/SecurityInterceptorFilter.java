package com.zhiwei.core.web.filter;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.AccessDeniedException;
import org.springframework.security.Authentication;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import com.zhiwei.core.security.SecurityDataSource;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.StringUtil;
/**
 * 权限拦载器
 * @author csx
 */
public class SecurityInterceptorFilter extends OncePerRequestFilter {
	
	/**
	 * 角色权限映射列表源，用于权限的匹配
	 */
	private HashMap<String, Set<String>> roleUrlsMap=null;
	
	private SecurityDataSource securityDataSource;

	public void setSecurityDataSource(SecurityDataSource securityDataSource) {
		this.securityDataSource = securityDataSource;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
		String url=request.getRequestURI();
		//若有contextPath,则切出来
		if(org.springframework.util.StringUtils.hasLength(request.getContextPath())){
			String contextPath=request.getContextPath();
			int index=url.indexOf(contextPath);
			if(index!=-1){
				url=url.substring(index+contextPath.length());
			}
		}
		Authentication auth= SecurityContextHolder.getContext().getAuthentication();//取得认证器
		
		boolean isSuperUser=false;
		for(int i=0;i<auth.getAuthorities().length;i++){
			//logger.info("角色名称:"+auth.getAuthorities()[i].getAuthority());
			if("超级管理员".equals(auth.getAuthorities()[i].getAuthority())){
				isSuperUser=true;
				break;
			}else if("ROLE_PUBLIC".equals(auth.getAuthorities()[i].getAuthority())){//TODO，加上这个是为了项目权限分配的简化处理，不需要细化每个url
				isSuperUser=true;
			}
		}
		
 		String flexUserId = request.getParameter("flexUserId"); // 用户名称
		if(StringUtils.isNotEmpty(flexUserId) && url.equalsIgnoreCase("/file-upload")){
			if(StringUtil.isNumeric(flexUserId)&& AppUtil.getOnlineUserIds().contains(new Long(flexUserId))){
				chain.doFilter(request, response);
				return;		
			}
		}
			
		if(!isSuperUser){//非超级管理员
			if(!isUrlGrantedRight(url,auth)){//如果未授权
				if(logger.isDebugEnabled()){
					logger.info("ungranted url:" + url);
				}
				if(url.startsWith("/mobile/")){
					response.sendRedirect(request.getContextPath() + "/mobile/login.jsp");
					return;
				}
				throw new AccessDeniedException("Access is denied! Url:" + url + " User:" + SecurityContextHolder.getContext().getAuthentication().getName());
			}
		}
		
		if(logger.isDebugEnabled()){
			logger.debug("pass the url:" + url);
		}
		//进行下一个Filter
		chain.doFilter(request, response);
	}
	
	/**
	 * 检查该URL是否授权访问
	 * @param url
	 * @return
	 */
	private boolean isUrlGrantedRight(String url,Authentication auth){
		//遍历该用户下所有角色对应的URL，看是否有匹配的
		for(GrantedAuthority ga:auth.getAuthorities()){
			Set<String> urlSet=roleUrlsMap.get(ga.getAuthority());
			//TODO AntPathMatcher here
			if(urlSet!=null && urlSet.contains(url)){
				return true;
			}
		}
		return false;
	}
	
	public void loadDataSource(){
		roleUrlsMap=securityDataSource.getDataSource();
	}
	
	@Override
	public void afterPropertiesSet() throws ServletException {
		loadDataSource();
		if(roleUrlsMap==null){
			throw new RuntimeException("没有进行设置系统的权限匹配数据源");
		}
	}
}
