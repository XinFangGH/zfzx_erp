<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.zhiwei.credit.service.system.AppUserService"%>
<%@page import="com.zhiwei.core.util.AppUtil"%>
<%@page import="com.zhiwei.core.util.ContextUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
AppUserService appUserService=(AppUserService)AppUtil.getBean("appUserService");
%>
<html>
  <head>
    <title>中介公司信息列表</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>ext3/resources/css/ext-all-css04.css"/>	
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/icons.css"/>
	<script type="text/javascript">
    	var __ctxPath = "<%=basePath%>";
    	var userInfo="<%=appUserService.getCurUserInfo()%>";
    </script>
	<script type="text/javascript" src="<%=basePath%>ext3/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="<%=basePath%>ext3/ext-all.js"></script>
	<script type="text/javascript" src="<%=basePath%>ext3/ext-lang-zh_CN.js" charset="utf-8"></script>
	<script type="text/javascript" src="<%=basePath%>ext3/ux/Toast.js"></script>
	<%--授权js--%>
	<script language="javascript" src="<%=basePath%>js/commonFlow/ZWAppforJsp.js" type="text/javascript"></script>
	
	<script type="text/javascript" src="<%=basePath%>js/globalDicProperty.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/creditFlow/customer/dictionary/dictionaryTree.js" charset="utf-8"></script><%--银行 --%>
	<script type="text/javascript" src="<%=basePath%>js/creditFlow/customer/bankrelationperson/bankRelationPersonList.js" charset="utf-8"></script>
	<script type="text/javascript" src="<%=basePath%>/js/core/AppUtil.js"></script>
	<script type="text/javascript">
	   Ext.onReady(function(){
		   	  var storeTheme=getCookie('theme');
		   	  if(storeTheme==null || storeTheme==''){
			   	  storeTheme='ext-all-css04';
		   	  }
		      Ext.util.CSS.swapStyleSheet("theme", __ctxPath+"/ext3/resources/css/"+storeTheme+".css");  
	    });
  </script>
  </head>
  <body>
   <div id="bankRelationPersonListDiv"><div>
  </body>
</html>
