<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String flag=request.getParameter("flag");
String orgId=request.getParameter("orgId");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'testupload.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<style>
	body{ font-size:12px; line-height:25px;}
	body li{ color:#F00; list-style:decimal;}
	</style>
	<script type="text/javascript" src="<%=basePath%>/js/dynamic.jsp"></script>
	<script type="text/javascript">
			var __ctxPath="<%=request.getContextPath()%>";
			
		</script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/ext3/adapter/ext/ext-base.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/ext3/ext-all.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/ext3/ext-lang-zh_CN.js"></script>

  </head>
  
  <body>
  <div id="from" style="text-algin:center">
  <form id="fileid" action="<%=basePath%>system/getExcDataAppRole.do" method="post" enctype="multipart/form-data">
    <input type="file"  name="excelsql" id="excelsql"></br>
  
        <input  type="button" value="检测" onclick='sub()'></br>
        <input  type="hidden" value=<%=flag%> name="uploadtype"></br>
        <input  type="hidden" value=<%=orgId%> name="orgId"></br>
        <input type="hidden" name="filename" id="filename">
  </form>
 </div>
 <div id="content"></div>
  </body>
  
  <script>
 function sub(){
     var fi= document.getElementById("excelsql").value;
     if(fi==null || fi==""){
    Ext.Msg.alert("","请先选择要上传的文件");
     }else{
     document.getElementById("filename").value=document.getElementById("excelsql").value;
        document.getElementById("fileid").submit(); 
          document.getElementById("from").style.display="none";
         document.getElementById("content").innerHTML="正在检测……";
     }
  }
  
  </script>
</html>
