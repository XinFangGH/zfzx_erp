<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>

<html>
  <head>
    <title>授信银行管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>ext3/resources/css/ext-all.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/icons.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/total.css"/>
		<script type="text/javascript">
    	var __ctxPath = "<%=basePath%>";
    </script>
	<script type="text/javascript" src="<%=basePath%>ext3/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="<%=basePath%>ext3/ext-all.js"></script>
	<script type="text/javascript" src="<%=basePath%>ext3/ext-lang-zh_CN.js" charset="utf-8"></script>
		
	<script type="text/javascript" src="<%=basePath%>js/global.js" charset="utf-8"></script>
	<script type="text/javascript"  src="<%=basePath%>js/globalDicProperty.js" type="text/javascript"></script>
	
	<link rel="stylesheet" type="text/css" href="<%=basePath%>js/dictionary/treegrid/treegrid.css" rel="stylesheet" />
	<script type="text/javascript" src="<%=basePath%>js/dictionary/treegrid/TreeGridSorter.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/dictionary/treegrid/TreeGridColumnResizer.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/dictionary/treegrid/TreeGridNodeUI.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/dictionary/treegrid/TreeGridLoader.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/dictionary/treegrid/TreeGridColumns.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/dictionary/treegrid/TreeGrid.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/dictionary/TreeNodeChecked.js" charset="utf-8"></script>
    
    <script language="javascript" src="<%=basePath%>js/core/ux/HTExt.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=basePath%>ext3/ux/Toast.js"></script>
   	<script type="text/javascript" src="<%=basePath%>/js/core/AppUtil.js"></script>
	<script type="text/javascript">
		   Ext.onReady(function(){
			   	  var storeTheme=getCookie('theme');
			   	  if(storeTheme==null || storeTheme==''){
				   	  storeTheme='ext-all-css04';
			   	  }
			      Ext.util.CSS.swapStyleSheet("theme", __ctxPath+"/ext3/resources/css/"+storeTheme+".css");  
		    });
  </script>
    

    <script type="text/javascript" src="<%=basePath%>js/creditFlow/customer/dictionary/dictionaryTree.js" charset="utf-8"></script>
 <script type="text/javascript" src="<%=basePath%>js/creditFlow/guarantee/guaranteefinance/accountBankManage/manageCautionAccount.js" charset="utf-8"></script>
    <script type="text/javascript" src="<%=basePath%>js/creditFlow/guarantee/guaranteefinance/accountBankManage/allParentBanksTreeWin.js" charset="utf-8"></script>
	<script type="text/javascript" src="<%=basePath%>js/creditFlow/guarantee/guaranteefinance/accountBankManage/accountBankManage.js" charset="utf-8"></script>
		 	
  </head>
  <body>
  </body>
</html>
