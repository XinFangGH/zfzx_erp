<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String basePath=request.getContextPath();
	Map<?,?> configMap=AppUtil.getConfigMap();
%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.zhiwei.core.util.AppUtil"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="java.util.Map"%>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<% if(null!=configMap && null!=configMap.get("iconFile")){
		%>
			<link rel="shortcut icon" href="<%=configMap.get("iconFile")%>" />
		<%	
		   }else{
		%>
			<link rel="shortcut icon" href="favicon.ico" />
		<%	
		   } 
		%>
		<title><%=AppUtil.getSystemName()%>：登陆页面</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<%
			response.addHeader("__timeout", "true");
			String codeEnabled = (String) AppUtil.getSysConfig().get("codeConfig");
			String dyPwdEnabled = (String) AppUtil.getSysConfig().get("dynamicPwd");
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
		<script type="text/javascript" src="<%=request.getContextPath()%>/ext3/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/ext3/ext-all.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/ext3/ext-lang-zh_CN.js"></script>
	 	<script type="text/javascript" src="<%=request.getContextPath()%>/js/App.LoginWin.js"></script>
	 	<script type="text/javascript" src="<%=request.getContextPath()%>/js/App.SHA-256.js"></script>
	 	<script type="text/javascript" src="<%=request.getContextPath()%>/js/encode64.js"></script>
	 	
		<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css" />
		<script type="text/javascript">
			function refeshCode() {
				var loginCode = document.getElementById('loginCode');
				loginCode.src=__ctxPath+"/CaptchaImg?rand="+Math.random();
			}
			function toSuggestBox() {
				window.open(__ctxPath+'/info/suggest.do', '_blank');
			}
			function getRootPath(){
		    	//获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
		    	var curWwwPath=window.document.location.href;
		    	//获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
		    	var pathName=window.document.location.pathname;
		    	var pos=curWwwPath.indexOf(pathName);
		    	//获取主机地址，如： http://localhost:8083
		    	var localhostPaht=curWwwPath.substring(0,pos);
		    	//获取带"/"的项目名，如：/uimcardprj
		   	 	var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
		    	return(localhostPaht+projectName);
			}
			function BindEnter(obj) {    
		 		if(obj.keyCode == 13){             
		  			checkform();
		 		}
			}
			function checkform(){	
			    var username_=document.getElementById("username").value;
			    var password_=document.getElementById("password").value;
			    password_ = b64_sha256(password_)+"=";//密码加密
			    //var key = "_hry2009100188";//定义key值一起加密
			   // password_ = encode64(password_+key);//密码加密
			    
			    var checkCode_=document.getElementById("checkCode").value;
			    var company_=document.getElementById("company").value;
			    document.getElementById("userNameError").innerHTML="";
		        document.getElementById("passwrodError").innerHTML="";
		        document.getElementById("loginCodeError").innerHTML="";
			    if (document.getElementById("username").value == "") {
					document.getElementById("userNameError").innerHTML="请输入您的用户名!";
					refeshCode();
					return false;
				}else if (document.getElementById("password").value == "") {
					document.getElementById("passwrodError").innerHTML="请输入您的密码!";
					refeshCode();
					return false;
				}else if (document.getElementById("checkCode").value == "") {
					document.getElementById("loginCodeError").innerHTML="请输入验证码!";
					refeshCode();
					return false;
				}
				Ext.Ajax.request({
					url:'<%=request.getContextPath()%>'+"/ajaxValidation.do",
					method:"post",
					success:function(response,opts){
					     var data = Ext.decode(response.responseText);
					     var isFalg=data.success;
					     var msg=data.msg;
					     var errorType=data.type;
					     if(!isFalg){
					         document.getElementById("userNameError").innerHTML="";
					         document.getElementById("passwrodError").innerHTML="";
					         document.getElementById("loginCodeError").innerHTML="";
					         document.getElementById("loginCodeError").innerHTML=msg;
					         refeshCode();
					     }else{
					        window.location.href=getRootPath()+'/index.jsp';
					     }
					 },
            		 failure : function(form, action) {
           			 },
					 params:{
						 username:username_,
						 password:password_,
						 company:company_,
						 checkCode:checkCode_
					 } 
         		});
			}
    	</script>
	</head>
	<body onkeydown="BindEnter(event)">
		<div class="loginform cf">
			<div class="logindiv">
		        <div class="loginleft">
		            <h2><%=AppUtil.getSystemName()%></h2>
		            <div class="login_c_d">
		                <form id="loginForm"  method="post"  onsubmit="return checkform()"  accept-charset="utf-8">
		                    <input type='hidden' value='<%=request.getParameter("company")%>' name='company' id="company"/>
		                    <p>
			                    <input id="username" class="input_text input-icon" type="text" name="username" placeholder="用户名" oncopy="return false" onpaste="return false" oncut="return false" oncontextmenu="return false"/>
			                    <em class="one"></em>
			                    <span id="userNameError" style="color:red">${FieldErrors.username[0]}</span>
		                    </p>
		                    <p>
		                    	<input id="password" class="input_text input-icon" type="password" name="password"  id="password" placeholder="密码" oncopy="return false" onpaste="return false" oncut="return false" oncontextmenu="return false"/>
		                    	<em class="two"></em>
		                    	<span id="passwrodError" style="color:red">${FieldErrors.password[0]}</span>
		                    </p>
		                    <p>
		                        <input id="checkCode" class="input_text input-icon login_yzm "type="text" id="checkCode" name="checkCode" placeholder="验证码" />
		                        <em class="three"></em>
		                        <span> <img id="loginCode" title="换一张" src="<%=request.getContextPath()%>/CaptchaImg" alt="换一张" width="100"/></span>
		                        <span style="color:red" id="loginCodeError">${FieldErrors.checkCode[0]}</span>
		                    </p>							
		                    <p class="login_btn">
		                        <input type="button" value="登 录" class="btn_one"  onclick="checkform()" placeholder="验证码"/>
		                        <input type="reset" value="重置" class="btn_two" placeholder="验证码"/>
		                    </p>
		                    <!--
		                    <div class="reg_c_e">
		                       <label>
		                           <input type="checkbox" checked id="readAgreement">记住密码					
		                       </label>
		                    </div>-->
		                </form>
		            </div>
		        </div>
		        <% if(null!=configMap && null!=configMap.get("login_logoFile")){
					%>
						<div style="width:399px;height:400px;float:left;background:url(<%=configMap.get("login_logoFile")%>) no-repeat;"></div>
					<%	
				}else{
					%>
						<div class="loginright"></div>
					<%	
				} %>
		    </div>
		    <div class="text">软件版本：V3.0  &nbsp;&nbsp;&nbsp;</div>
		</div>
	</body>
</html>