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
    <title>人员信息列表</title>
    <script type="text/javascript">
    	var __ctxPath = "<%=basePath%>";
    	 var userInfo="<%=appUserService.getCurUserInfo()%>";
    </script>
   
    
    <link rel="stylesheet" type="text/css" href="<%=basePath%>ext3/resources/css/ext-all-css04.css"/>	
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/icons.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/admin.css"/>
	<script type="text/javascript" src="<%=basePath%>ext3/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="<%=basePath%>ext3/ext-all.js"></script>
	<script type="text/javascript" src="<%=basePath%>ext3/ext-lang-zh_CN.js" charset="utf-8"></script>
	<script language="javascript" src="<%=basePath%>js/global.js" type="text/javascript"></script>
	<script language="javascript" src="<%=basePath%>js/globalDicProperty.js" type="text/javascript"></script>
	<script language="javascript" src="<%=basePath%>js/core/ux/HTExt.js" type="text/javascript"></script>
     <script type="text/javascript" src="<%=basePath%>/ext3/ux/RowExpander.js"></script>
	<script type="text/javascript" src="<%=basePath%>/ext3/ux/Ext.ux.grid.RowActions.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/globalFunction.js" charset="utf-8"></script>
	<script type="text/javascript" src="<%=basePath%>ext3/ux/Toast.js"></script>
	<%--授权js--%>
	<script language="javascript" src="<%=basePath%>js/commonFlow/ZWAppforJsp.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=basePath%>js/creditFlow/customer/person/personWindowObjList.js" charset="utf-8"></script>
	<script type="text/javascript" src="<%=basePath%>js/creditFlow/customer/enterprise/ajaxValidation.js" charset="utf-8"></script>
	<script type="text/javascript" src="<%=basePath%>js/creditFlow/customer/enterprise/selectEnterGrid.js" charset="utf-8"></script>
	<script type="text/javascript" src="<%=basePath%>js/creditFlow/customer/dictionary/dictionaryTree.js" charset="utf-8"></script>
	<script type="text/javascript" src="<%=basePath%>js/creditFlow/customer/person/personList.js" charset="utf-8"></script>
	<script type="text/javascript" src="<%=basePath%>js/creditFlow/customer/person/selectPersonWin.js" charset="utf-8"></script>
	<script type="text/javascript" src="<%=basePath%>js/creditFlow/customer/common/common.js" charset="utf-8"></script>
	<script type="text/javascript" src="<%=basePath%>js/creditFlow/customer/person/addFamilyPerson.js" charset="utf-8"></script>
	<!--<script type="text/javascript" src="<%=basePath%>js/creditFlow/customer/person/businessRecordList.js" charset="utf-8"></script>
	--><script type="text/javascript" src="<%=basePath%>js/creditFlow/customer/person/listReditregistries.js" charset="utf-8"></script>
	<script type="text/javascript" src="<%=basePath%>js/creditFlow/customer/person/thereunderList.js" charset="utf-8"></script>
	<script type="text/javascript" src="<%=basePath%>/js/core/AppUtil.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/creditFlow/guarantee/enterpriseBusiness/EnterpriseEvaluationWin.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/creditFlow/guarantee/enterpriseBusiness/EnterpriseEvaluationGua.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/creditFlow/guarantee/enterpriseBusiness/creditRating.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/creditFlow/customer/person/bankInfoPersonList.js" charset="utf-8"></script>
	
	<!-- checkBox控件-->
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
   	<center>
	  <table style="width:100%;height:100%">
	    <tr>
	      <td id="personList" style="width:100%">
	      </td>
	    </tr>
	  </table>   	
   	</center>
  </body>
</html>
