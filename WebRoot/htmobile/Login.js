
/**
 * 手机登录
 * by cjj
 */

Ext.define('mobile.Login', {
    extend: 'Ext.form.Panel',
    name: 'login',
    constructor: function (config) {
    	config = config || {};
    	var user = localStorage.getItem("userName")!=null?localStorage.getItem("userName"):'';
    	Ext.apply(config,{
    		items : [{
		        html:'<div class="g-body login">'+
				    '<section class="login-logo posr">'+
				    '<span>×</span>'+
				        '<a href="" title=""><img  src=htmobile/resources/images/login-logo.png></a>'+
				    '</section>'+
				    '<div class="m-login-form">'+
				        '<div class="w-form-item">'+
				            '<div class="w-bar-label">帐号：</div>'+
				            '<!--默认隐藏 为了展示现在为显示状态-->'+
				            '<a href="#" title=""  id="delete_loginName"  onclick="javascript:passbtn();" class="w-bar-input-clear" style="display: block;visibility:hidden">x</a>'+
				            '<div class="w-bar-control">'+
				                '<input id="username" name="username"  oninput="javascript:showInput();" placeholder="请输入帐号" class="w-bar-input" type="text">'+
				            '</div>'+
				       ' </div>'+
				        '<div class="w-form-item">'+
				           ' <div class="w-bar-label">密码：</div>'+
				           ' <!--默认隐藏 为了展示现在为显示状态-->'+
				           ' <a href="#" title="" class="login_key" id="showPwd_css_1" onclick="javascript:showPwd1();" style="display: block;"></a>'+
				              ' <a href="#" title="" class="login_key2" id="showPwd_css_2" onclick="javascript:showPwd2();" style="display: block;visibility:hidden"></a>'+
				            '<div class="w-bar-control">'+
				                '<input id="password" name="password" oninput="javascript:changeEye();" placeholder="请输入密码(6-16位)" class="w-bar-input" type="password">'+
				            '</div>'+
				       ' </div>'+
				   ' </div>'+
				    '<div class="m-login-submit">'+
				       ' <a onclick="javascript:loginbtn();" class="w-button w-button-main" id="btnLogin">登 录</a>'+
				    '</div>'+
				    '<div class="m-login-link txt-r">'+
				        '<a onclick="forgetPassword();" class="txt-orange">忘记密码？</a>请联系管理员'+
				    '</div>'+
				'</div>'

		        }]
    	});
    	
    	this.callParent([config]);
    	
    	passbtn=function(){    		
    		document.getElementById("username").value="";
    	},
    	showInput=function(){
    		var username = document.getElementById("username").value;
    		if(username==""||username==null){
    			document.getElementById("delete_loginName").style.visibility="hidden";
    		}else{
    		document.getElementById("delete_loginName").style.visibility="visible";
    		}
    	},
    	showPwd1 = function(){
    			document.getElementById("password").type="text";
    			document.getElementById("showPwd_css_2").style.visibility="visible";
    			document.getElementById("showPwd_css_1").style.visibility="hidden";	
    	},
    	showPwd2 = function(){
    			document.getElementById("password").type="password";
    			document.getElementById("showPwd_css_2").style.visibility="hidden";
    			document.getElementById("showPwd_css_1").style.visibility="visible";
    	},
    	changeEye = function (){
    	var password = document.getElementById("password").value;
    	if(password==""||password==null){
    			document.getElementById("showPwd_css_2").style.visibility="hidden";
    			document.getElementById("showPwd_css_1").style.visibility="visible";
    		}
    	},
    	loginbtn=function(){
/*			if (loginForm.getCmpByName('remenberUser').getValue() == 1) {
				localStorage.setItem("userName",loginForm.getCmpByName('username').getValue());
//				Ext.util.Cookies.set("password", loginForm.getCmpByName('password').getValue());
			}*/
            var username=document.getElementById("username").value;
            var password=document.getElementById("password").value;
            if(Ext.isEmpty(username)){
			   Ext.Msg.alert('', "用户名不能为空");
			   return ;
			}
			if(Ext.isEmpty(password)){
			   Ext.Msg.alert('', "密码不能为空");
			   return ;
			}
            password=b64_sha256(password)+"=";
			
			Ext.Ajax.request({
			    url: __ctxPath+'/ajaxValidation.do',
		        params: {
		            username: username,
		            password: password,
		            isMobile:1
		        },
		        method: 'POST',
		        success: function(form,action,response) {
		        	var obj = Ext.util.JSON.decode(form.responseText);
		        	if(obj.success){
		        		curUserInfo=obj.user;
		        		mobileView.removeAt(0);
		        		mobileLogin.destroy();
		        		localStorage.setItem("userName",username);
		        	    localStorage.setItem(username+"updateGesturePassword","0");
		        	    
		        	    mobileNavi = Ext.create('mobile.View', {
											username : obj.user.fullname,
											userId : obj.user.userId
										});
		        		mobileView.add(mobileNavi);
		        		
		        	}else{
						Ext.Msg.alert('', obj.msg);
		        	}
		        },
		        failure: function(form,action,response){
					var obj = Ext.util.JSON.decode(response);
					Ext.Msg.alert('', obj.msg);
		        }
			});
	},
    
    setConfig=function(){
    	var setDomain = Ext.create('mobile.SetDomain',{domVal:__ctxPath});
		mobileView.removeAt(0);
		this.destroy();
    	mobileView.add(setDomain);
    },
    forgetPassword=function(){
    	mobileView.removeAt(0);
		mobileLogin.destroy();
    	var forgetPw = Ext.create('htmobile.public.resetPassword');
    	mobileView.add(forgetPw);
    	}
    }
});

