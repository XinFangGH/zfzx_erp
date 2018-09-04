<%@page import="com.zhiwei.core.util.RequestUtil" isErrorPage="true" pageEncoding="UTF-8"%>
<%!private final static transient org.apache.commons.logging.Log logger = org.apache.commons.logging.LogFactory.getLog("500_jsp");%>
<%
	String errorUrl = RequestUtil.getErrorUrl(request);
	//boolean isContent = (errorUrl.endsWith(".do") || errorUrl.endsWith(".jsp"));
	logger.warn("Error Occur, url: " + errorUrl+" Referrer: "+request.getHeader("REFERER"));
	logger.error(RequestUtil.getErrorInfoFromRequest(request, logger.isInfoEnabled()));
	
	response.addHeader("__500_error","true");
%>
<html>
	<head>
		<title>内部出错</title>
	</head>
	<body>
		<h2>Error</h2>
		<br/>
		<b>url:<%=errorUrl %></b>
	</body>
	
</html>