<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib uri="/WEB-INF/creditTag.tld" prefix="credit"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
	<head>
	    <title>新增个人信息</title>
	    <script type="text/javascript">
	    	var __ctxPath = "<%=basePath%>";
	    </script>
	    <script language="javascript" src="<%=basePath%>js/core/ux/HTExt.js" type="text/javascript"></script>
	    <script type="text/javascript" src="<%=basePath%>ext3/ux/Toast.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/creditFlow/customer/person/ajaxValidation.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/creditFlow/customer/person/addPerson.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/creditFlow/customer/person/selectPersonWin.js"></script>
		<!-- 关系人参照 -->
		<script type="text/javascript" src="<%=basePath%>js/creditFlow/customer/person/relationPerson/relationPersonWin.js" charset="utf-8"></script>
    </head>
 <body>
   	<div id="addPersonDiv"></div>
  </body>
	<body>
	</body>
</html>
