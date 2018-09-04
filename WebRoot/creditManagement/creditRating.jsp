<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%--jiang --%>
<title>新建资信评估</title>
		<style type="text/css">
		.key {
			background: url(../images/icon_table.gif) no-repeat 0px 0px;
		}
		
		.key {
			background-color: #FFFFFF;
			padding-left: 20px;
		
		}

		</style>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>ext3/resources/css/ext-all-css04.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/icons.css"/>

	<script type="text/javascript" src="<%=basePath%>ext3/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="<%=basePath%>ext3/ext-all.js"></script>
	<script type="text/javascript" src="<%=basePath%>ext3/ext-lang-zh_CN.js" charset="utf-8"></script>
	<script type="text/javascript" src="<%=basePath%>ext3/ux/js/Ext.ux.util.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/core/AppUtil.js"></script>
		<script language="javascript" src="<%=basePath%>js/core/ux/HTExt.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=basePath%>ext3/ux/Toast.js"></script>
	 <script type="text/javascript">
    	var __ctxPath = "<%=basePath%>";
    </script>
	<script type="text/javascript">
			   Ext.onReady(function(){
				   	  var storeTheme=getCookie('theme');
				   	  if(storeTheme==null || storeTheme==''){
					   	  storeTheme='ext-all-css04';
				   	  }
				      Ext.util.CSS.swapStyleSheet("theme", __ctxPath+"/ext3/resources/css/"+storeTheme+".css");  
			    });
	  </script>

		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/total.css" />

		<script type="text/javascript" src="<%=basePath%>js/global.js"
			charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath%>js/globalDicProperty.js"
			charset="utf-8"></script>
			<script type="text/javascript"
			src="<%=basePath%>js/creditFlow/customer/person/personWindowObjList.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/creditFlow/customer/enterprise/selectEnterprise.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/creditFlow/guarantee/creditManagement/creditRating.js" charset="utf-8"></script>

	
</head>

<body>
		<form id="addCreditRating" action="<%=basePath%>creditFlow/creditmanagement/addCreditRating.do" method="post">
			
			<input id="creditRating.customerType" type="hidden" name="creditRating.customerType" />
			<input id="creditRating.customerId" type="hidden" name="creditRating.customerId" />
			<input id="creditRating.customerName" type="hidden" name="creditRating.customerName" />
			
			<input id="creditRating.creditTemplate" type="hidden" name="creditRating.creditTemplate" />
			<input id="creditRating.creditTemplateId" type="hidden" name="creditRating.creditTemplateId" />
			<input id="creditRating.financeFile" type="hidden" name="creditRating.financeFile" />
		</form>
	</body>
</html>
