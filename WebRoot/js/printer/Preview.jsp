<%@page pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<%
	String contextPath=request.getContextPath();
%>
<head>
    <title>打印预览</title>
    <link rel="stylesheet" type="text/css" href="<%=contextPath %>/ext3/resources/css/ext-all.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath %>/css/admin.css" />
    <script type='text/javascript' src='<%=contextPath %>/ext3/adapter/ext/ext-base.js'></script>
    <script type='text/javascript' src='<%=contextPath %>/ext3/ext-all.js'></script>
    <script type='text/javascript' src='<%=contextPath %>/js/printer/Printer.js'></script>
</head>
<body>

</body>
<script language="javascript" type="text/javascript">
    Ext.onReady(function() {
        var printSetup = new Ext.ux.PrintSetup();
    });
</script>
</html>
