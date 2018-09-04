<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String basePath=request.getContextPath();
%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.zhiwei.core.util.AppUtil"%>

<%@page import="org.apache.commons.lang.StringUtils"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>小额贷款业务管理系统：登录页面</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<%
			response.addHeader("__timeout", "true");
			String codeEnabled = (String) AppUtil.getSysConfig().get(
					"codeConfig");
			String dyPwdEnabled = (String) AppUtil.getSysConfig().get(
					"dynamicPwd");
			if (StringUtils.isEmpty(codeEnabled)) {//若当前数据库没有配置验证码参数
				codeEnabled = "close";//代表需要输入
			}
			if (StringUtils.isEmpty(dyPwdEnabled)) {//若当前数据库没有配置动态密码参数
				dyPwdEnabled = "close";//代表需要输入
			}
		%>
		<script type="text/javascript">
			var __ctxPath="<%=request.getContextPath()%>";
			var __loginImage=__ctxPath + "<%=AppUtil.getCompanyLogo()%>";
		</script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/ext3/adapter/ext/ext-base.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/ext3/ext-all.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/ext3/ext-lang-zh_CN.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/App.LoginWin.js"></script>
		<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/login_style.css" />
		<script type="text/javascript">
	function refeshCode() {
		var loginCode = document.getElementById('loginCode');
		loginCode.src=__ctxPath+"/CaptchaImg?rand="+Math.random();
	}
	function toSuggestBox() {
		window.open(__ctxPath+'/info/suggest.do', '_blank');
	}
	
	function checkform(){	
	    if (document.getElementById("username").value == "") {
			alert("请输入您的用户名!");
			return false;
		}
		else if (document.getElementById("password").value == "") {
			alert("请输入您的密码!");
			return false;
		}
		else if (document.getElementById("checkCode").value == "") {
			alert("请输入您的验证码!");
			return false;
		}
	}
    </script>
	
	</head>
	<body>
	<div class="content">
		<form id="loginForm" class="form" action="<%=request.getContextPath()%>/login.do" method="post"  onsubmit="return checkform()">
		
			<input type='hidden' value='<%=request.getParameter("company")%>' name='company'>
			
			<div class="content_top">
				<div class="version">软件版本：V3.0</div>
				<div class="cont_box">
					<div class="logo">
						<img src="<%=request.getContextPath()%>/images/imagesteam/login/login_logo.jpg" />
					</div>
					<div class="login_content">
						<h1>小额贷款业务管理系统</h1>
						<span>SMALL LOANS BUSINESS MANAGEMENT SYSTEM(SBMS)</span>
						<ul>
							<li> 
								用户名：<input name="username" id="username" type="text" class="text-user" /> &nbsp;<span style="color:red">${FieldErrors.username[0]}</span>
							</li>
							<li> 
								密<span>密</span>码：<input name="password" type="password" id="password" class="text-lock" /> &nbsp;<span style="color:red">${FieldErrors.password[0]}</span>
							</li>
							<%if (codeEnabled.equals("1")) {%>
							<li> 
								验证码：<input id="checkCode" name="checkCode" type="text" class="text-code" />
												<img id="loginCode" title="换一张" border="0" height="20" width="80" src="<%=request.getContextPath()%>/CaptchaImg" alt="换一张"/>
												&nbsp;<span style="color:red">${FieldErrors.checkCode[0]}</span>
							</li>
							<li class="li_03"> 
								<input type="checkbox" />&nbsp;&nbsp;&nbsp;记住密码
							</li>
							<%}else{ %>
							<%} %>
						</ul>
					</div>
				</div>
			</div>
			<div class="content_btm">
				<div class="btn_box">
					<input type="submit" value="" class="btn01" />
					<input type="reset"  value="" class="btn02" />
					<a href="#">意见箱</a>
				</div>
			</div>
		</form>
	</div>
	<div class="footer">
		 技术支持：升升&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;网址：<a href="http://www.zxzbol.com/">www.zxzbol.com</a>
	</div>
	</body>
</html>