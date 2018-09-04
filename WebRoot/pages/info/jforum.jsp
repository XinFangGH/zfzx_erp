<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.zhiwei.credit.model.system.AppUser"%>
<%@page import="com.zhiwei.core.util.AppUtil"%>
<%@page import="com.zhiwei.credit.service.system.AppUserService" %>
<% 
	String basePath = request.getContextPath();
   	AppUser user =(AppUser) request.getAttribute("appUser");
   	AppUserService appUserService =(AppUserService) AppUtil.getBean("appUserService");
   	if(user != null){
   		AppUser appUser=appUserService.get(new Long(user.getId()));
   		request.setAttribute("appUser", appUser);
   	}
        
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>论坛连接页面</title>
</head>
<body > 
    <table border="0" style="padding:20px 5px 5px 100px;font:22px 宋体;color: black;line-height:24px;">
     <tr>
	 	<td>
	 	    <marquee scrollAmount="1" direction="up" height="50">
	 	    	<center><font style="font-size:25px;filter:shadow(color=#FF8C00);width:200px;color:white;line-height: 150%;font-family:华文彩云">
	 	    		<font color="black">欢迎<font color="green">${appUser.username}</font>
	 	    		进入<font color="red">jforum</font>论坛连接页面,点击下面的
	 	    		<font color="red">论坛连接</font>即可进入论坛首页!</font>
	 	    	</font></center>
	 	    </marquee>
	    </td>
	 </tr>
     <tr>
        <td><img src="<%=basePath%>/images/logo.jpg"/><a  href="/jforum">论坛连接</a></td>
	 </tr>
	</table>
</body>
</html>