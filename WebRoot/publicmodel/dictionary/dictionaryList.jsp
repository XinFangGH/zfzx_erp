<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
	<head>
		<title>数据字典</title>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/ext3/resources/css/ext-all-css04.css" />
	
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/icons.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/credit.css" />
		<script type="text/javascript">
	    	var __ctxPath = "<%=basePath%>";
	    </script>
		<script type="text/javascript"
			src="<%=basePath%>ext3/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="<%=basePath%>ext/ext-all.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>ext3/ext-lang-zh_CN.js" charset="utf-8"></script>
	
			<!-- load the extjs libary -->
		<script type="text/javascript" src="<%=basePath%>/js/core/AppUtil.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/dynamic.jsp"></script>

		<script type="text/javascript" src="<%=basePath%>/ext3/ux/Toast.js"></script>
		
		
		<!-- core 工具JS -->
		<script type="text/javascript" src="<%=basePath%>/js/core/ux/HTExt.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>/js/core/ScriptMgr.js"></script>
		<!-- FCK控件JS -->
		<script type="text/javascript"
			src="<%=basePath%>/js/fckeditor/fckeditor.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>/ext3/ux/Fckeditor.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>/js/fckeditor/custom/plugins/flvPlayer/swfobject.js"></script>
				<script type="text/javascript" src="<%=basePath%>js/global.js"
			charset="utf-8"></script>

		<script type="text/javascript"
			src="<%=basePath%>js/dictionary/dictionaryList.js" charset="utf-8"></script>
		<script type="text/javascript">
	
	Ext.onReady(function() {
		var storeTheme = getCookie('theme');
		if (storeTheme == null || storeTheme == '') {
			storeTheme = 'ext-all-css04';
		}
		Ext.util.CSS.swapStyleSheet("theme", __ctxPath + "/ext3/resources/css/"
				+ storeTheme + ".css");

	});
</script>
			
	</head>
	<body>
		<form name="form_post" method="post">
			<input id="dictionaryId" name="dictionaryId" type="hidden" />
			<input id="dictionaryValue" name="dictionaryvalue" type="hidden" />
			<input id="dictionaryOptionId" name="dictionaryOptionId"
				type="hidden" />
			<input id="dictionaryOptionValue" name="dictionaryOptionValue"
				type="hidden" />
				<input id="dictionaryOptionRemarks" name="dictionaryOptionRemarks"
							type="hidden" />
			<input id="sortorder" name="sortorder" type="hidden" />
			<div class="x-panel-header x-unselectable fixedRow">
				数据字典
			</div>
			<table cellspacing="0" class="x-toolbar x-small-editor" width="100%"
				border='0'>
				<tr height="25">
					
					<td>
						&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
					<td>
						字典类别：
					</td>
					<td>
						<div>
							<input id="type" name="type" />
						</div>
					</td>
					<td>
						&nbsp;
					</td>
					<td>
						<div id="btn_search"></div>
					</td>
					<td>
						<div id='btn_split1'></div>
					</td>
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
						<div id='btn_split2'></div>
					</td>
					<td>
						<div id='btn_chadd'></div>
					</td>
					<td>
						<div id='btn_chedit'></div>
					</td>
					<td>
						<div id='btn_chdelete'></div>
					</td>
					<td width="100%">
						&nbsp;
					</td>
				</tr>
			</table>
		</form>
		<table width="100%" cellspacing="0" border='0'>
			<tr>
				<td width="40%" border='0'>
					<div id="dictionaryList"></div>
				</td>
				<td width="60%" border='0'>
					<div id="dictionaryOption"></div>
				</td>
			</tr>
		</table>
	</body>
</html>
