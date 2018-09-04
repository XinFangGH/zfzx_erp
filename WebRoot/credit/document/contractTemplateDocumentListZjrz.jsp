<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String temtype = request.getParameter("templettype");
	String bustype = request.getParameter("businessType");
	String linkUrl = "";
	if("SmallLoan".equals(bustype)||bustype=="SmallLoan"){
		linkUrl = "js/document/contractTemplateDocumentList.js";
	}else if("Financing".equals(bustype)||bustype=="Financing"){
		linkUrl = "js/document/contractTemplateDocumentListZjrz.js";
	}else if("Guarantee".equals(bustype)||bustype=="Guarantee"){
		linkUrl = "js/document/contractTemplateDocumentListZjrz.js";
	}else if("Pawn".equals(bustype)||bustype=="Pawn"){
		linkUrl = "js/document/contractTemplateDocumentListZjrz.js";
	}else if("Investment".equals(bustype)||bustype=="Investment"){
		linkUrl = "js/document/contractTemplateDocumentListZjrz.js";
	}
%>
<html>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<head>
		<title>文档管理</title>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>ext3/resources/css/ext-all-css04.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/icons.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>publicmodel/uploads/css/UploadPanel.css" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>js/dictionary/treegrid/treegrid.css" rel="stylesheet" />
	<script type="text/javascript">
    	var __ctxPath = "<%=basePath%>";
    </script>
	<script type="text/javascript" src="<%=basePath%>ext3/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="<%=basePath%>ext3/ext-all-debug.js"></script>
	<script type="text/javascript" src="<%=basePath%>ext3/ext-lang-zh_CN.js" charset="utf-8"></script>
	<script type="text/javascript" src="<%=basePath%>ext3/ux/Toast.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/globalDicProperty.js" charset="utf-8"></script>
	<script type="text/javascript" src="<%=basePath%>js/global.js" charset="utf-8"></script>
	<%@ include file="/credit/document/uploads.jsp"%>
	<script type="text/javascript">
		var temtypev = <%=temtype%> ;
		var bustypev = '<%=bustype%>' ;
	</script>
	
	<script type="text/javascript" src="<%=basePath%>js/dictionary/treegrid/TreeGridSorter.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/dictionary/treegrid/TreeGridColumnResizer.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/dictionary/treegrid/TreeGridNodeUI.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/dictionary/treegrid/TreeGridLoader.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/dictionary/treegrid/TreeGridColumns.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/dictionary/treegrid/TreeGrid.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/dictionary/TreeNodeChecked.js" charset="utf-8"></script>
	<script type="text/javascript" src="<%=basePath%><%=linkUrl%>" charset="utf-8"></script>
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
	 
	</head>
	<body>
	</body>
	
</html>