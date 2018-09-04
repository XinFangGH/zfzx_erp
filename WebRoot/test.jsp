<%@page import="com.zhiwei.credit.service.system.AppUserService"%>
<%@page import="com.zhiwei.credit.service.system.AppRoleService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@page import="com.zhiwei.core.util.AppUtil"%>
<%@page import="com.zhiwei.core.util.ContextUtil"%>

<%
	String basePath=request.getContextPath();
	//登录成功后，需要把该用户显示至在线用户
	AppUtil.addOnlineUser(request.getSession().getId(), ContextUtil.getCurrentUser());
	AppUserService appUserService=(AppUserService)AppUtil.getBean("appUserService");
	AppRoleService appRoleService=(AppRoleService)AppUtil.getBean("appRoleService");
    String vids=appRoleService.getControlCompanyId(ContextUtil.getCurrentUser());
	String vroleType=ContextUtil.getRoleTypeSession();
    //String systemType=AppUtil.getSystemIsOAVersion(); 
	//String OAorTeam=ContextUtil.getSystemOAorTeam(); 
	String loginOutInfo=ContextUtil.loginOutInfo;
	String CurrentUserName=ContextUtil.getUsLoginSession();
   %>
   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >
<html>
<head>
		<link rel="shortcut icon" href="favicon.ico" />
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="msthemecompatible" content="no">
		<title><%=AppUtil.getSystemName()%></title>
        <!--不能延迟加载-->
        <link rel="stylesheet" type="text/css" href="<%=basePath%>/ext3/resources/css/ext-all-nothemeZip.css"/>
				
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/ext3/ux/css/ux-all.css"/>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/layout.css"/>
		<!-- load the extjs libary -->
		<script type="text/javascript" src="<%=basePath%>/js/dynamic.jsp"></script>
				<!-- Ext 核心JS -->
	<!-- 生成环境放开  -->	<script type="text/javascript" src="<%=basePath%>/ext3/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="<%=basePath%>/ext3/ext-all.js"></script>
		
		<!--<script type="text/javascript" src="<%=basePath%>/ext3/adapter/ext/ext-base-debug.js"></script>
		<script type="text/javascript" src="<%=basePath%>/ext3/ext-all-debug.js"></script>  -->
		
		<script type="text/javascript" src="<%=basePath%>/ext3/ext-basex.js"></script>
		<script type="text/javascript" src="ext3/ext-basex.js"></script>
		<script type="text/javascript" src="<%=basePath%>/ext3/ext-lang-zh_CN.js"></script>
        <script type="text/javascript" src="<%=basePath%>/ext3/ux/RowExpander.js"></script>
        <script type="text/javascript" src="<%=basePath%>/ext3/ux/TreeFilterX.js"></script>
		<!--使用iframe加载的依赖JS  -->
		<script type="text/javascript" src="<%=basePath%>/ext3/miframe.js"></script>
		
		<!-- ExtJS总计组件 css -->
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/ext3/ux/css/Ext.ux.grid.GridSummary.css"/>
		<!-- ExtJS总计组件 js-->
		<script type="text/javascript" src="<%=basePath%>/ext3/ux/Ext.ux.grid.GridSummary.js"></script>
		<!-- ExtJS gride 进度条-->
		<script type="text/javascript" src="<%=basePath%>/ext3/ux/Ext.ux.grid.ProgressColumn.js"></script>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/ext3/ux/css/Ext.ux.grid.ProgressColumn.css"/>
		
				
		<!-- 需要的 JS-->
		<script type="text/javascript" src="<%=basePath%>/js/App.import.js"></script>
		<script type="text/javascript" src="<%=basePath%>/ext3/ux/XmlTreeLoader.js"></script>
		<script type="text/javascript" src="<%=basePath%>/ext3/ux/TabCloseMenu.js"></script>		
		<script type="text/javascript" src="<%=basePath%>/ext3/ux/DateTimeField.js"></script>
			
		
		<!-- 首页样式加载 -->
    	<link href="<%=basePath%>/css/desktop.css" rel="stylesheet" type="text/css" />	
	   
	    <!-- 担保系统 自定义组件，比如CS,add by chencc-->
	    <!-- 登录信息,工具JS -->
		<script type="text/javascript" src="<%=basePath%>/js/team/App.Init.Team.js"></script>	
		<script type="text/javascript" src="<%=basePath%>/js/App.js"></script>
		
	     <!-- 个人首页JS -->
	    <script type="text/javascript" src="<%=basePath%>/js/team/IndexPage.Team.js"></script>
	    
	     <script type="text/javascript" src="<%=basePath%>/js/jsloader.js"></script>
		
		 <script>
		window.onload = function() {  
    setTimeout(function(){ importJs();
  
    }, 1000);  
}
</script> 

	</head>
	<body>
	<a href="system/addAspect.do?name=admin">test</a>
	</body>
</html>