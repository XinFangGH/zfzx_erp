Ext.ns("App");

/**
 * processNameFlowKey流程的key：根据流程key查询对应的任务等信息。(全局变量)
 * 待办事项(桌面)：App.home.js
 * 待办事项(more)：MyTaskView.js
 * 项目待办事项(左侧菜单): ActivityTaskView.js
 * 我参与的项目	：MyProcessRunView.js
 * 项目事项追回  ：MyRunningTaskView.js
 * 我发起的项目  : ProcessRunView.js
 * 已办结事项	：CompleteTaskView.js
 */

//用户信息
var UserInfo=function(user){
	this.userId=user.userId;
	this.username=user.username;
	this.fullname=user.fullname;
	this.depId=user.depId;
	this.depName=user.depName;
	this.rights=user.rights;
	this.portalConfig=user.items;
	this.topModules=user.topModules;
};
//系统配置
var SysConfig=function(sysConfigs){
	this.dynamicPwd=sysConfigs.dynamicPwd;
};

//当前登录用户
var curUserInfo=null;
//取得当前系统配置
var sysConfigInfo=null

//检查当前用户有权访问funKey对应的功能
function isGranted(funKey){
	if(curUserInfo.rights.indexOf('__ALL')!=-1){
		return true;
	}
	if(curUserInfo.rights.indexOf(funKey)!=-1){
		return true;
	}
	return false;
}

App.init = function() {
	Ext.QuickTips.init();//这句为表单验证必需的代码
	Ext.BLANK_IMAGE_URL=__ctxPath+'/ext3/resources/images/default/s.gif';
	Ext.util.Observable.observeClass(Ext.data.Connection);
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
	
	
	
};



	
//应用程序总入口
Ext.onReady(App.init);
