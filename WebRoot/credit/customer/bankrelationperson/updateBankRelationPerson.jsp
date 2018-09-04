<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
  <head>
    <title>更新银行联系人信息</title>
    <script type="text/javascript">
    	var __ctxPath = "<%=basePath%>";
    </script>
    <script language="javascript" src="<%=basePath%>js/core/ux/HTExt.js" type="text/javascript"></script>
    <script type="text/javascript" src="<%=basePath%>ext3/ux/Toast.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/creditFlow/customer/bankrelationperson/updateBankRelationPerson.js" charset="utf-8"></script>
  </head>
  <body>
   <div id="updateBankRelationPersonDiv"><div>
  </body>
</html>
