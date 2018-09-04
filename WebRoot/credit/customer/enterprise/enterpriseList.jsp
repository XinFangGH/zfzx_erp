<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page import="com.zhiwei.credit.service.system.AppUserService"%>
<%@page import="com.zhiwei.core.util.AppUtil"%>
<%@page import="com.zhiwei.core.util.ContextUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
AppUserService appUserService=(AppUserService)AppUtil.getBean("appUserService");
//String userInfo = request.getAttribute("enterpriseAppUser").toString();
%>
<html>
  <head>
    <title>企业维护列表</title>
    <script type="text/javascript">
    	var __ctxPath = "<%=basePath%>";
    	var userInfo="<%=appUserService.getCurUserInfo()%>";
    </script>
    
    <link rel="stylesheet" type="text/css" href="<%=basePath%>ext3/resources/css/ext-all-css04.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/icons.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>ext3/ux/css/Ext.ux.form.LovCombo.css" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/admin.css"/>
	<script type="text/javascript" src="<%=basePath%>ext3/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="<%=basePath%>ext3/ext-all.js"></script>
	<script type="text/javascript" src="<%=basePath%>ext3/ext-lang-zh_CN.js" charset="utf-8"></script>
	<script type="text/javascript" src="<%=basePath%>ext3/ux/js/Ext.ux.util.js"></script>
	<script type="text/javascript" src="<%=basePath%>ext3/ux/js/Ext.ux.form.LovCombo.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/creditFlow/common/EnterpriseShareequity.js" charset="utf-8"></script>
	<script type="text/javascript" src="<%=basePath%>js/commonFlow/ExtUD.Ext.js" charset="utf-8"></script>
	<script type="text/javascript" src="<%=basePath%>js/globalDicProperty.js" charset="utf-8"></script>
	<script type="text/javascript" src="<%=basePath%>js/global.js" charset="utf-8"></script>
	<script type="text/javascript" src="<%=basePath%>js/globalFunction.js" charset="utf-8"></script>
	<script language="javascript" src="<%=basePath%>js/core/ux/HTExt.js" type="text/javascript"></script>
	<%--授权js--%>
	<script language="javascript" src="<%=basePath%>js/commonFlow/ZWAppforJsp.js" type="text/javascript"></script>
		
	<script type="text/javascript" src="<%=basePath%>ext3/ux/Toast.js"></script>
	<script type="text/javascript" src="<%=basePath%>/ext3/ux/RowExpander.js"></script>
	<!-- <script type="text/javascript" src="js/permission/functions.js" charset="utf-8"></script> 以往权限控制-->
	<script type="text/javascript" src="<%=basePath%>js/creditFlow/customer/enterprise/public.js" charset="utf-8"></script>
	<script type="text/javascript" src="<%=basePath%>js/creditFlow/customer/dictionary/dictionaryTree.js" charset="utf-8"></script>

	<script type="text/javascript" src="<%=basePath%>js/creditFlow/customer/person/selectPersonWin.js" charset="utf-8"></script>
	<script type="text/javascript" src="<%=basePath%>js/creditFlow/customer/enterprise/selectEnterGrid.js" charset="utf-8"></script>
	<script type="text/javascript" src="<%=basePath%>js/creditFlow/customer/enterprise/enterpriseList.js" charset="utf-8"></script>
	<script type="text/javascript" src="<%=basePath%>js/creditFlow/customer/enterprise/addEnterprise.js" charset="utf-8"></script>
	<script type="text/javascript" src="<%=basePath%>js/creditFlow/customer/enterprise/addFastEnterprise.js" charset="utf-8"></script>
	<script type="text/javascript" src="<%=basePath%>js/creditFlow/customer/enterprise/updateEnterprise.js" charset="utf-8"></script>
	<script type="text/javascript" src="<%=basePath%>js/creditFlow/customer/enterprise/seeEnterprise.js" charset="utf-8"></script>
	<script type="text/javascript" src="<%=basePath%>js/creditFlow/customer/enterprise/selectEnterprise.js" charset="utf-8"></script>
	<script type="text/javascript" src="<%=basePath%>js/creditFlow/customer/enterprise/ajaxValidation.js" charset="utf-8"></script>
	<script type="text/javascript" src="<%=basePath%>js/creditFlow/customer/person/ajaxValidation.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/creditFlow/customer/enterprise/bankInfoList.js" charset="utf-8"></script>
	<script type="text/javascript" src="<%=basePath%>js/creditFlow/customer/dictionary/dictionaryTree.js" charset="utf-8"></script>
	<script type="text/javascript" src="<%=basePath%>js/creditFlow/customer/enterprise/managecaseList.js" charset="utf-8"></script>
	<!--<script type="text/javascript" src="<%=basePath%>js/creditFlow/customer/enterprise/shareequityList.js" charset="utf-8"></script>
	--><script type="text/javascript" src="<%=basePath%>js/creditFlow/customer/enterprise/leadteamList.js" charset="utf-8"></script>
	<script type="text/javascript" src="<%=basePath%>js/creditFlow/customer/enterprise/debtList.js" charset="utf-8"></script>
	<script type="text/javascript" src="<%=basePath%>js/creditFlow/customer/enterprise/creditorList.js" charset="utf-8"></script>
	<script type="text/javascript" src="<%=basePath%>js/creditFlow/customer/enterprise/outassureList.js" charset="utf-8"></script>
	<script type="text/javascript" src="<%=basePath%>js/creditFlow/customer/enterprise/outinvestList.js" charset="utf-8"></script>
	<script type="text/javascript" src="<%=basePath%>js/creditFlow/customer/enterprise/relatedataList.js" charset="utf-8"></script>
	<script type="text/javascript" src="<%=basePath%>js/creditFlow/customer/enterprise/prizeList.js" charset="utf-8"></script>
	<script type="text/javascript" src="<%=basePath%>js/creditFlow/customer/enterprise/companyList.js" charset="utf-8"></script>
	<script type="text/javascript" src="<%=basePath%>js/creditFlow/customer/enterprise/employeeStructure.js" charset="utf-8"></script>
	<script type="text/javascript" src="<%=basePath%>js/creditFlow/customer/enterprise/financeInfo.js" charset="utf-8"></script>
	<!--<script type="text/javascript" src="<%=basePath%>js/creditFlow/customer/enterprise/businessRecordRelationEnterprise.js" charset="utf-8"></script>
	--><script type="text/javascript" src="<%=basePath%>/js/system/TradeCategorySelecter.js" charset="utf-8"></script>
	<script type="text/javascript" src="<%=basePath%>js/creditFlow/customer/person/seePersonCanzhao.js" charset="utf-8"></script>
	<script type="text/javascript" src="<%=basePath%>js/creditFlow/customer/person/personWindowObjList.js" charset="utf-8"></script>
	<script type="text/javascript" src="<%=basePath%>js/creditFlow/customer/enterprise/relationPersonList.js" charset="utf-8"></script>
	<script type="text/javascript" src="<%=basePath%>js/creditFlow/customer/common/common.js" charset="utf-8"></script>

	<script type="text/javascript" src="<%=basePath%>/js/core/AppUtil.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/creditFlow/guarantee/enterpriseBusiness/EnterpriseEvaluationWin.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/creditFlow/guarantee/enterpriseBusiness/EnterpriseEvaluationGua.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/creditFlow/guarantee/enterpriseBusiness/creditRating.js"></script>
	
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
</html>
