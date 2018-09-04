<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%
String basePath = request.getContextPath()+"/";

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%--jiang --%>
<title>指标管理</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>ext3/resources/css/ext-all-css04.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>ext/resources/css/ext-all.css" />
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/total.css" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/icons.css" />
	<script type="text/javascript">
	var __ctxPath = "<%=basePath%>";
	</script>
	
	<script type="text/javascript" src="<%=basePath%>ext/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="<%=basePath%>ext3/ext-all.js"></script>
	<script type="text/javascript" src="<%=basePath%>ext3/ext-lang-zh_CN.js" charset="utf-8"></script>
	<script type="text/javascript" src="<%=basePath%>/js/core/AppUtil.js"></script>
	<script language="javascript" src="<%=basePath%>js/core/ux/HTExt.js" type="text/javascript"></script>
	<script type="text/javascript">
		   Ext.onReady(function(){
			   	  var storeTheme=getCookie('theme');
			   	  if(storeTheme==null || storeTheme==''){
				   	  storeTheme='ext-all-css04';
			   	  }
			      Ext.util.CSS.swapStyleSheet("theme", __ctxPath+"/ext3/resources/css/"+storeTheme+".css");  
		    });
  </script>
	<script type="text/javascript" src="<%=basePath%>js/creditFlow/guarantee/creditManagement/creditRatingManage.js" charset="utf-8"></script>
</head>

<body>
    <center>
    	
    </center>
  </body>
</html>
