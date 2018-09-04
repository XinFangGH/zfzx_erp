<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String flag=request.getParameter("flag");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'uploadAccessData.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="<%=basePath%>/js/dynamic.jsp"></script>
	
  </head>
  
  <body>
  <div style="text-algin:center">
  <form id="fileid" action="<%=basePath%>creditFlow/finance/getExcDataExeclToMysql.do" method="post" enctype="multipart/form-data">
    <input type="file"  name="excelsql" id="excelsql"></br>
  
        <input  type="button" value="上传" onclick='sub()'></br>
        <input  type="hidden" value=<%=flag%> name="uploadtype"></br>
  </form>
 </div>
  </body>
  
  <script>
 function sub(){
     var fi= document.getElementById("excelsql").value;
     if(fi==null || fi==""){
 //    Ext.Msg.alert("","请先选择要上传的文件");
     }else{
        document.getElementById("fileid").submit(); 
     }
  }
  
  </script>
</html>
