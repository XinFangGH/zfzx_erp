<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP '1.jsp' starting page</title>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/ext3/adapter/ext/ext-base.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/ext3/ext-all.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/ext3/ext-lang-zh_CN.js"></script>
		<script type="text/javascript" src="js/core/AppUtil.js"></script>
	<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/login_style.css" />
<script language="javascript">
 Ext.onReady(function(){
	 var jsArr = [__ctxPath + '/js/commonFlow/ExtUD.Ext.js',//客户信息 项目基本信息
				    __ctxPath + '/js/creditFlow/smallLoan/project/loadDataCommon.js',// 加载数据JS
					__ctxPath + '/js/selector/UserDialog.js',
					__ctxPath + '/js/creditFlow/common/EnterpriseShareequity.js',//股东信息
					__ctxPath + '/js/creditFlow/smallLoan/materials/SlProcreditMaterialsView.js',// 贷款材料
					__ctxPath + '/js/creditFlow/assuretenet/SlProcreditAssuretenetedForm.js',// 贷款准入原则
					__ctxPath + '/js/creditFlow/report/SlReportView.js',// 调查报告
					__ctxPath + '/js/creditFlow/finance/FundIntent_formulate_editGrid.js',
					__ctxPath + '/js/creditFlow/finance/calculateFundIntent.js',
					__ctxPath + '/js/creditFlow/finance/calulateFinancePanel.js',
					__ctxPath + '/js/creditFlow/finance/caluateIntentGrid.js',
					__ctxPath + '/js/creditFlow/finance/calulateloadDataCommon.js',
					__ctxPath + '/js/creditFlow/finance/SlActualToCharge.js',
					__ctxPath + '/js/creditFlow/mortgage/AddDzyMortgageWin.js',
					__ctxPath + '/js/creditFlow/mortgage/business/BusinessForm.js',
					__ctxPath + '/js/creditFlow/smallLoan/finance/BorrowerInfo.js',
					__ctxPath + '/js/creditFlow/customer/dictionary/dictionaryNotLastNodeTree.js',
					__ctxPath + '/js/commonFlow/NewProjectForm.js',
					__ctxPath+'/js/lcy_ext/baseinfo/baseinfo.js' //第一还款来源
			];
			$ImportSimpleJs(jsArr, this.constructPanel, this);
			
	 new ExtUD.Ext.McroLoanProjectInfoPanel() 
 })
</script>

<button id="xt">点击</button>
<button id="alert">注册的新事件</button>

  </head>
  
  <body>
    This is my JSP page. <br>
  </body>
</html>
