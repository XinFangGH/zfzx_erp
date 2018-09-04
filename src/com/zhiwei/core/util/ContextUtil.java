/**
 * 此方法作用为取得当前用户
 */
/*
 *  北京互融时代软件有限公司 OA办公自动管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 Hurong Software Company
 */
package com.zhiwei.core.util;

/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 Hurong Software Company
 */
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;

import com.hurong.credit.util.Common;
import com.zhiwei.core.web.filter.MySessionFilter;
import com.zhiwei.credit.dao.system.AppRoleDao;
import com.zhiwei.credit.model.system.AppRole;
import com.zhiwei.credit.model.system.AppUser;

public class ContextUtil {
	private static final Log logger = LogFactory.getLog(ContextUtil.class);
	public static String loginOutInfo = "";
	/**
	 * 用户userId add by lisl 2012-09-29 用于启动流程时，设置流程启动者
	 */
	private static Long userId = -1l;

	public static Long getUserId() {
		return ContextUtil.userId;
	}

	public static void setUserId(Long userId) {
		ContextUtil.userId = userId;
	}

	/**
	 * 从上下文取得当前用户
	 * 
	 * @return
	 */
	public static AppUser getCurrentUser() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		if (securityContext != null) {
			Authentication auth = securityContext.getAuthentication();
			if (auth != null) {
				Object principal = auth.getPrincipal();
				if (principal instanceof AppUser) {
					return (AppUser) principal;
				}
			} else {
				logger
						.warn("WARN: securityContext cannot be lookuped using SecurityContextHolder.");
			}
		}
		return null;
	}

	public static Long getCurrentUserId() {
		AppUser curUser = getCurrentUser();
		if (curUser != null)
			return curUser.getUserId();
		return null;
	}

	public static String getRoleTypeSession() {

		String RoleType = "";
		HttpSession session = (HttpSession) MySessionFilter.session.get();
		if (null != session && null != session.getAttribute("RoleType")) {
			RoleType = (String) session.getAttribute("RoleType");
			return RoleType;
		}
		return RoleType;
	}

	// 获取系统类型Session值
	@SuppressWarnings("unchecked")
	public static String getSysTypeSession() {
		String sysType = "";
		HttpSession session = (HttpSession) MySessionFilter.session.get();
		if (null != session && null != session.getAttribute("sysType")) {
			sysType = (String) session.getAttribute("sysType");
		} else {
			//根据用户所拥有的权限决定左侧菜单初始化的类型
			AppUser user=(AppUser)session.getAttribute("users");
			Set<AppRole> set=user.getRoles();
			if(null!=set && user.getUserId()!=1){
				Map<String,String> map=MenuUtil.getOneMenus();
				boolean flag=false;
				Iterator mod=map.keySet().iterator();
				while(mod.hasNext()){//先循环判断menu-all.xml
					String temp=mod.next().toString();
					Iterator ite=set.iterator();//必须放在循环里面
					while(ite.hasNext()){//再循环判断用户拥有的角色
						AppRole role=(AppRole)ite.next();
						if(null!=role.getRights() && role.getRights().equals("__ALL")){//判断当前用户是不是拥有超级管理员的权限，如果有则结束循环，默认加载小
							sysType=map.get(temp);
							flag=true;
							break;
						}else{
							if(null!=role.getRights() && role.getRights().contains(temp)){
								sysType=map.get(temp);
								flag=true;
								break;
							}
						}
						
					}
					if(flag){
						break;
					}
				}
			}else if(AppUtil.getConfigMap().get("initXML").equals("menu-all_1.xml")){
				sysType="customer";
			}else{
				sysType="customer";
			}
		}
		return sysType;
	}

	public static String getUsLoginSession() {
		String usLoginSession = "";
		AppUser curUser = getCurrentUser();
		if (curUser != null) {

			HttpSession session = (HttpSession) MySessionFilter.session.get();
			// String a=session.getAttribute("usLoginFo").toString();
			if (null != session && null != session.getAttribute("usLoginFo")) {
				usLoginSession = (String) session.getAttribute("usLoginFo");
				return usLoginSession;
			}
		}
		return usLoginSession;
	}

	public static String getBranchIdsStr() {

		HttpSession session = (HttpSession) MySessionFilter.session.get();

		String ActiveCompanyId = "";
		if (null != session.getAttribute("ActiveCompanyId")) {
			ActiveCompanyId = (String) session.getAttribute("ActiveCompanyId");
		}
		AppRoleDao appRoleDao = (AppRoleDao) AppUtil.getBean("appRoleDao");
		String newControlIds = appRoleDao.getControlCompanyId(ContextUtil
				.getCurrentUser());
		if (!ActiveCompanyId.equals(newControlIds)
				&& null != session.getAttribute("RoleType")
				&& session.getAttribute("RoleType").equals("control")) {
			ActiveCompanyId = newControlIds;
			session.setAttribute("ActiveCompanyId", ActiveCompanyId);
			session.setAttribute("Control CompanyId", ActiveCompanyId);
		}
		return ActiveCompanyId;
	}

	public static Long getLoginCompanyId() { // 获得当前登录的所在企业id

		Long companyId = null;
		HttpSession session = (HttpSession) MySessionFilter.session.get();
		if (null != session.getAttribute("CompanyId")) {
			companyId = Long.valueOf(session.getAttribute("CompanyId")
					.toString());
		}
		return companyId;

	}

	public static String getSystemOAorTeam() {
		String str = "team";
		String flag = AppUtil.getSystemIsOAVersion();
		if (!"".equals(flag)) {
			HttpSession session = (HttpSession) MySessionFilter.session.get();
			if (null != session.getAttribute("OAorTeam")) {
				str = (String) session.getAttribute("OAorTeam");
			}
		}
		return str;
	}

	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 取值：三位随机数+通过System.nanoTime()返回最准确的可用系统计时器的当前值，以毫微秒为单位（15位）。
	 * 第三方及系统交易流水号共18位
	 * 
	 * 修改：System.nanoTime()方法返回非15位，由于三方限制只能是20位的数字，所以修改生成策略
	 * 
	 * @return 第三方交易需要的流水号（系统账户需要的流水号） add by linyan 2015-04-21
	 */
	public static String createRuestNumber() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String format = sdf.format(new Date());
//		return Common.getRandomNum(3) + System.nanoTime();
		return Common.getRandomNum(3) + format;
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	public static Map createResponseMap(HttpServletRequest request) {
		/**
		 * 准备将回调通知参数整合成map
		 */
		Map<String, String> map = new HashMap<String, String>();
		Enumeration paramEnu = request.getParameterNames();
		String parameter = "";
		while (paramEnu.hasMoreElements()) {
			String paramName = (String) paramEnu.nextElement();
			String paramValue = (String) request.getParameter(paramName);
			map.put(paramName, paramValue);
			parameter = parameter + (paramName + "=" + paramValue + "&");
		}
		return map;
	};
	
    public static String trimHtml2Txt(String html){      
        html = html.replaceAll("\\<head>[\\s\\S]*?</head>(?i)", "");//去掉head  
        html = html.replaceAll("\\<!--[\\s\\S]*?-->", "");//去掉注释  
        html = html.replaceAll("\\<![\\s\\S]*?>", "");  
        html = html.replaceAll("\\<style[^>]*>[\\s\\S]*?</style>(?i)", "");//去掉样式  
        html = html.replaceAll("\\<script[^>]*>[\\s\\S]*?</script>(?i)", "");//去掉js  
        html = html.replaceAll("\\<w:[^>]+>[\\s\\S]*?</w:[^>]+>(?i)", "");//去掉word标签  
        html = html.replaceAll("\\<xml>[\\s\\S]*?</xml>(?i)", "");  
        html = html.replaceAll("\\<html[^>]*>|<body[^>]*>|</html>|</body>(?i)", "");  
        html = html.replaceAll("\\\r\n|\n|\r", " ");//去掉换行  
        html = html.replaceAll("\\<br[^>]*>(?i)", "\n");  
        List<String> tags = new ArrayList<String>();  
        List<String> s_tags = new ArrayList<String>();  
        List<String> halfTag = Arrays.asList(new String[]{"img","table","thead","th","tr","td"});//  
        /*if(filterTags != null && filterTags.length > 0){  
            for (String tag : filterTags) {  
                tags.add("<"+tag+(halfTag.contains(tag)?"":">"));//开始标签  
                if(!"img".equals(tag)) tags.add("</"+tag+">");//结束标签  
                s_tags.add("#REPLACETAG"+tag+(halfTag.contains(tag)?"":"REPLACETAG#"));//尽量替换为复杂一点的标记,以免与显示文本混合,如：文本中包含#td、#table等  
                if(!"img".equals(tag)) s_tags.add("#REPLACETAG/"+tag+"REPLACETAG#");  
            }  
        }*/  
       // html = ExStringUtils.replaceEach(html, tags.toArray(new String[tags.size()]), s_tags.toArray(new String[s_tags.size()]));                 
        html = html.replaceAll("\\</p>(?i)", "");  
        html = html.replaceAll("\\<[^>]+>", "");  
        html = html.replaceAll("\\&lt;", "");  
        html = html.replaceAll("\\&gt;", "");  
       // html = ExStringUtils.replaceEach(html,s_tags.toArray(new String[s_tags.size()]),tags.toArray(new String[tags.size()]));  
        html = html.replaceAll("\\ ", " ");  
        return html.trim();  
    } 
	
}
