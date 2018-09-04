<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
  <head>
    <title>dynamicTree area</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
 	<link rel="stylesheet" type="text/css" href="<%=basePath%>ext3/resources/css/ext-all.css"/>	
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/icons.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/credit.css" />
	<script type="text/javascript">
    	var __ctxPath = "<%=basePath%>";
    </script>
	<script type="text/javascript" src="<%=basePath%>ext3/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="<%=basePath%>ext3/ext-all.js"></script>
	<script type="text/javascript" src="<%=basePath%>ext3/ext-lang-zh_CN.js" charset="utf-8"></script>
	

    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/dictionary/treegrid/treegrid.css" rel="stylesheet" />
	<script type="text/javascript" src="<%=basePath%>js/dictionary/treegrid/TreeGridSorter.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/dictionary/treegrid/TreeGridColumnResizer.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/dictionary/treegrid/TreeGridNodeUI.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/dictionary/treegrid/TreeGridLoader.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/dictionary/treegrid/TreeGridColumns.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/dictionary/treegrid/TreeGrid.js"></script>
	
	
	
	<script type="text/javascript" src="<%=basePath%>js/dictionary/vocation.js" charset="utf-8"></script>
	
	 
     
       
	
  </head>
 
  <body style="background-color:#dfe8f6;">
 
  
  </body>
</html>
