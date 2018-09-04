App.init = function() {
	Ext.QuickTips.init();//这句为表单验证必需的代码
	Ext.BLANK_IMAGE_URL=__ctxPath+'/ext3/resources/images/default/s.gif';
	setTimeout(function() {
				Ext.get('loading').remove();
				Ext.get('loading-mask').fadeOut({remove:true});
				document.getElementById('app-header').style.display='block';
			}, 1000); 
	
	Ext.util.Observable.observeClass(Ext.data.Connection);
	Ext.data.Connection.prototype.timeout='30000';
	Ext.data.Connection.on('requestcomplete', function(conn, resp,options ){
		if (resp && resp.getResponseHeader){
		    if(resp.getResponseHeader('__timeout')) {
		    	Ext.ux.Toast.msg('操作提示：','操作已经超时，请重新登录!');
	        	window.location.href=__ctxPath+'/index.jsp?randId=' + parseInt(1000*Math.random());
	    	}else if(resp.getResponseHeader('__403_error')){
	    		Ext.ux.Toast.msg('系统访问权限提示：','你目前没有权限访问：{0}',options.url);
	    	}
		}
	});
	Ext.data.Connection.on('requestexception',function(conn,resp,options){
		if (resp && resp.getResponseHeader){
			if(resp.getResponseHeader('__500_error')){
	    		Ext.ux.Toast.msg('后台出错','您访问的URL:{0}出错了，具体原因请联系管理员。',options.url);
	    	}else if(resp.getResponseHeader('__404_error')){
	    		Ext.ux.Toast.msg('后台出错','您访问的URL:{0}对应的页面不存在，具体原因请联系管理员。',options.url);
	    	}
		}
	});
	
	//userinfo 变量来自index.jsp
	var object=Ext.util.JSON.decode(userInfo);
	//取得当前登录用户的相关信息，包括权限
	var user=object.user;
	var conf=user.items;
	curUserInfo=new UserInfo(user);
	var sysConfigs=object.sysConfigs;
	sysConfigInfo=new SysConfig(sysConfigs);
	//显示主页
	var indexPage=new IndexPage();
	//显示应用程序首页
	//App.clickTopTab('ComIndexPage');
	App.clickTopTab('AppHome');//个人桌面
	var indexCombo=new Ext.form.ComboBox({
		mode : 'local',
		//contentEl : 'top-subject-change',
		editable : false,
		value:'切换皮肤',
		width:100,
		triggerAction : 'all',
		store :[['ext-all-css04','缺省灰白'],['ext-all','浅蓝主题'],['ext-all-css05','绿色主题'],['ext-all-css03','粉红主题'],['xtheme-tp','灰色主题'],['xtheme-default2','灰蓝主题'],['xtheme-default16','绿色主题'],['xtheme-access','Access风格']],
		listeners:{
	         scope: this,
	         'select':function(combo,record, index){
	         	if(combo.value!=''){
	         		var expires = new Date();
					expires.setDate(expires.getDate() + 300);
					setCookie("theme",combo.value,expires, __ctxPath);
					Ext.util.CSS.swapStyleSheet("theme", __ctxPath + "/ext3/resources/css/" + combo.value + ".css");
	         	}
	         } 
	    }
	});
	indexCombo.render("top-subject-change");
	
	
};