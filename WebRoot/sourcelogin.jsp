<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String basePath=request.getContextPath();
%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.zhiwei.core.util.AppUtil"%>

<%@page import="org.apache.commons.lang.StringUtils"%><html>
	<head>
		<link rel="shortcut icon" href="favicon.ico" />
		<title>欢迎登录<%=AppUtil.getCompanyName()%></title>
		<link rel="stylesheet" type="text/css"
			href="<%=request.getContextPath()%>/ext3/resources/css/ext-all.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=request.getContextPath()%>/ext3/resources/css/ext-patch.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=request.getContextPath()%>/css/login.css" />
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
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/js/App.LoginWin.js"></script>
		<script type="text/javascript">
		function setmiddle() {
		var broswer_height = document.body.clientHeight; //网页工作区的高
		var broswer_width = document.body.clientWidth; //网页工作区的宽
		var logintable = document.getElementById("logintable");
		var tab_width = logintable.clientWidth; //图片高
		var tab_height = logintable.clientHeight; //图片宽
		position_height = (broswer_height - tab_height) / 2; //图像顶边距
		position_width = (broswer_width - tab_width) / 2; //图像左边距
		if (position_height <= 0)//若图像超过浏览器工作区域大小的判断
			position_height = 0;
		if (position_width <= 0)
			position_width = 0;
		logintable.style.marginLeft = position_width;
		logintable.style.marginTop = position_height;
		if (window.ActiveXObject)
			logintable.style.marginTop = position_height;
		else
		{
		   logintable.style.marginTop = position_height+"px";
			//document.getElementById("loginDiv").marginTop=position_height;
			//logintable.style["margin-top"] = position_height;
		}
			

	}
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
		<style type="text/css">
tr td {
	font-size: 12px
}
</style>
	</head>
	<body
		style="background-image: url('<%=request.getContextPath()%>/images/login/lgbc.jpg'); width: 100%; height: auto; overflow: hidden; border: none; padding: 0px; margin: 0px;"
		onload="setmiddle();">
		<form id="loginForm" class="form" action="<%=request.getContextPath()%>/login.do" method="post"  onsubmit="return checkform()">
			<input type='hidden' value='<%=request.getParameter("company")%>' name='company'>
			<table id="logintable" border="0" cellpadding="0" cellspacing="0"
				style="width: 1200px; height: 650px; background-image: url('<%=request.getContextPath()%>/images/login/login_dynamic.jpg')">
				<tr>
					<td style="width: 670px; height: 650px">
					<div style="width:250px; height:30px; margin:0px 0px 0px 385px; font-size:16px; font-weight:bold; font-family:'微软雅黑';"><span><%=AppUtil.getSystemName()%></span></div>
                    <div style="width:250px; height:20px; margin:0px 0px 0px 385px; font-size:12px; font-family:'Arial'; "><span><%=AppUtil.getSystemVersion()%></span></div>
                    <div style="width:300px; height:60px;"></div>
					</td>
					<td style="width: 530px; height: 650px">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td style="width: 530px; height: 265px">
									&nbsp;
								</td>
							</tr>
							<tr>
								<td style="height: 32px">
									<table border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td style="width: 70px">
												&nbsp;用户名：
											</td>
											<td style="width: 460px">
												<input name="username" id="username" type="text" class="text-user" /> &nbsp;<span style="color:red">${FieldErrors.username[0]}</span>
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td style="height: 34px">
									<table border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td style="width: 70px">
												&nbsp;密<span style="visibility: hidden">中</span>码：
											</td>
											<td style="width: 460px">
												<input name="password" type="password" id="password" class="text-lock" /> &nbsp;<span style="color:red">${FieldErrors.password[0]}</span>
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<%if (codeEnabled.equals("1")) {%>
							<tr>
								<td style="height: 32px">

									<table border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td style="width: 55px">
												&nbsp;验证码：
											</td>
											<td style="width: 475px">
												<input id="checkCode" name="checkCode" type="text" class="text-code" />
												<img id="loginCode" title="换一张" border="0" height="20" width="80" src="<%=request.getContextPath()%>/CaptchaImg" alt="换一张"/>
												&nbsp;<span style="color:red">${FieldErrors.checkCode[0]}</span>
											</td>
										</tr>
									</table>

								</td>
							</tr>
							<%}else{ %>
							<%} %>
							<tr>
								<td style="height: 32px">
									<input name="_spring_security_remember_me" type="checkbox" />
									&nbsp;让系统记住我
									<!--<a style="margin-left: 10px" href="javascript:toSuggestBox()">意见箱</a>
								--></td>
							</tr>
							<tr>
								<td style="height: 50px">
									<div style="width: 403px; height: 8px">
									</div>
									<div style="width: 403px; height: 30px">
										&nbsp;
										<input id="btnOK" type="submit" value="登录" class="btn-login"/>
										<input id="btnReset" type="reset" value="重置"
											class="btn-login-reset" />
									</div>
									<div style="width: 403px; height: 88px">
									</div>
								</td>
							</tr>
							<tr>
								<td style="width: 530px; height: 201px">
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>