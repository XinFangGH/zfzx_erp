<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%--jiang --%>
<title>指标管理</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>ext/resources/css/ext-all.css" />
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/total.css" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/icons.css" />
	<script type="text/javascript" src="<%=basePath%>ext/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="<%=basePath%>ext/ext-all.js"></script>
	<script type="text/javascript" src="<%=basePath%>ext/ext-lang-zh_CN.js" charset="utf-8"></script>
	<%--<script type="text/javascript" src="<%=basePath%>credit/creditManagement/optionManagement.js" charset="utf-8"></script>
	--%>
	<script type="text/javascript" src="<%=basePath%>js/global.js" charset="utf-8"></script>
	<script type="text/javascript" src="<%=basePath%>js/creditManagement/indicatorManagement.js" charset="utf-8"></script>
	<script type="text/javascript" src="<%=basePath%>js/creditManagement/indicatorstoreTree.js" charset="utf-8"></script>
</head>

<body>
    <center>
    	
    </center>
  </body>
</html>
