<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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

	<style>
	body{ font-size:12px; line-height:25px;}
	body li{ color:#F00; list-style:disc;}
	</style>
	
	<script type="text/javascript"
			src="<%=request.getContextPath()%>/ext3/adapter/ext/ext-base.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/ext3/ext-all.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/ext3/ext-lang-zh_CN.js"></script>
	<script type="text/javascript" src="<%=basePath%>/js/dynamic.jsp"></script>
	
  </head>
  
  <body>
  <form id="fileid" action="<%=basePath%>system/importExcDataAppRole.do" method="post" >
     <div >
     <div id="content"></div>
     <div id="btn">
 <input  type="button" value="导入权限" onclick='sub()'></br>
 </div>
     <% 
     String a=(String)request.getAttribute("jsonString");
     %>
     <script type="text/javascript" >
   
    var t="<%= a %>";
     var object=eval('('+t+')');
      var flag=object.flag;
      var succ=object.success;
	  var result=object.result;
	if(flag==0){
		document.getElementById("content").innerHTML=result;
		if(succ==0){//表示检测成功 导入按钮出现
			document.getElementById("btn").style.display="block";
		}else{
			document.getElementById("btn").style.display="none";
		}
	}else{
	//Ext.Msg.alert('状态', '检测失败： '+result);
		document.getElementById("content").innerHTML=result;
		document.getElementById("btn").style.display="none";
			 }
     </script>
     </br></br></div>
     </form>
     <script>
 function sub(){
 	if(confirm("确定导入？")){
        document.getElementById("fileid").submit(); 
        document.getElementById("content").innerHTML="正在导入……";
 	}
  }
  
  </script>
  </body>
</html>
