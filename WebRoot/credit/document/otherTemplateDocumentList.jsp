<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<head>
		<title>文档管理</title>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>ext/resources/css/ext-all.css"/>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/icons.css"/>
		<script type="text/javascript">
    		var __ctxPath = "<%=basePath%>";
    	</script>
		<script type="text/javascript" src="<%=basePath%>ext/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="<%=basePath%>ext/ext-all-debug.js"></script>
		<script type="text/javascript" src="<%=basePath%>ext/ext-lang-zh_CN.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath%>js/globalDicProperty.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath%>js/global.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath%>ext3/ux/Toast.js"></script>
		<%@ include file="/credit/document/uploads.jsp"%>
		<script type="text/javascript" src="<%=basePath%>js/document/otherTemplateDocumentList.js" charset="utf-8"></script>
	</head>
	<body>
	</body>
</html>

