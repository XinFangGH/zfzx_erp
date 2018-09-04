<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<head>
		<title>文档管理</title>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>ext/resources/css/ext-all.css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/icons.css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/credit.css" />
		
		<link rel="stylesheet" type="text/css" href="<%=basePath%>publicmodel/uploads/css/UploadPanel.css">
		<script type="text/javascript">
    		var __ctxPath = "<%=basePath%>";
    	</script>
		<script type="text/javascript" src="<%=basePath%>ext/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="<%=basePath%>ext/ext-all.js"></script>
		<script type="text/javascript" src="<%=basePath%>ext/ext-lang-zh_CN.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath%>ext3/ux/Toast.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/global.js" charset="utf-8"></script>
		
		<script type="text/javascript" src="<%=basePath%>publicmodel/uploads/js/swfupload.js"></script>
		<script type="text/javascript" src="<%=basePath%>publicmodel/uploads/js/UploadPanel.js"></script>
		<script type="text/javascript" src="<%=basePath%>publicmodel/uploads/js/upload.js"></script>
		<!-- 选择企业贷项目参照窗口 -->
		<script type="text/javascript" src="<%=basePath%>js/accountmanage/projectAccount/cs_tn_showProjInfoList.js" charset="utf-8"></script>
		<!-- 选择车贷项目参照窗口 -->
		<script type="text/javascript" src="<%=basePath%>js/document/showCarProjInfoList.js" charset="utf-8"></script>
		
		<script type="text/javascript" src="<%=basePath%>js/document/documentTest.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath%>js/document/documentManageList.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath%>js/document/cs_tn_enterpriseElementWin.js" charset="utf-8"></script><!-- 企业贷选择项目要素窗口 -->
		<script type="text/javascript" src="<%=basePath%>js/document/cs_tn_carElementWin.js" charset="utf-8"></script><!-- 车贷选择项目要素窗口 -->
		<script type="text/javascript" src="<%=basePath%>js/document/cs_tn_enterpriseElementWin.js" charset="utf-8"></script><!-- 车贷或企业贷要素汇总窗口 -->
	</head>
	<body>
		<form name="form_post" method="post">
			<input id="documentId" name="documentId" type="hidden" />
			<input id="documentElementId" name="documentElementId" type="hidden" />
			<input id="documentElementValue" name="documentElementValue" type="hidden" />
			<input id="sortorder" name="sortorder" type="hidden" />
			<div class="x-panel-header x-unselectable fixedRow">
				文档管理
			</div>
			<table cellspacing="0" class="x-toolbar x-small-editor" width="100%" border='0'>
				<tr height="25">
					<td>
						<div id='btn_add'></div>
					</td>
					<td>
						<div id='btn_update'></div>
					</td>
					<td>
						<div id='btn_delete'></div>
					</td>
					<td>
						<div id='btn_seeEnterpriseElement'></div>
					</td>
					<td>
						<div id='btn_seeCarElement'></div>
					</td>
					<td width="57%">
						&nbsp;
					</td>
					<td width="70%">
						<span style="color:#15428B;font-family:tahoma,arial,verdana,sans-serif;font-size:11px;font-weight:bold">要素列表</span>
					</td>
				</tr>
			</table>
		</form>
		<table width="100%">
			<tr>
				<td width="60%">
					<div id="documentListDiv"></div>
				</td>
				<td width="40%">
					<div id="documentEelementListDiv"></div>
				</td>
			</tr>
		</table>
	</body>
</html>

