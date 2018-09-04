

Ext.define('htmobile.usermanage.UserSettingManage', {
    extend: 'Ext.Panel',
    
    name: 'UserSettingManage',

    constructor: function (config) {
      
    	updateGesturePassword=function(){
    		var username = localStorage.getItem("userName")!=null?localStorage.getItem("userName"):'';
	    	localStorage.setItem(username+"updateGesturePassword","1");
	    	mobileNavi.destroy();
	        localStorage.setItem(username+"settinggesturePasswordcount",1);
			 gesturePasswordSetting = Ext.create('mobile.usermanage.GesturePasswordSetting', {fullscreen: true,errormsg:"请绘制原始解锁图案"});
	    	
    	}
    	config = config || {};
    	Ext.apply(config,{
    		style:'background-color:white;',
		    fullscreen: true,
		    title:'我的业绩',
		    scrollable:{
		    	direction: 'vertical'
		    },
		    html:"<div style='background:white;height:50px;padding: 15px;'>"+curUserInfo.fullname+"</div>"+
		    	"<div style='background:white;height:50px;font-size:16px;padding: 15px;' onclick=\"javascript:updateGesturePassword();\">修改手势密码</div>"+
		    	"<div style='background:white;height:50px;font-size:16px;padding: 15px;' onclick=\"javascript:exitappself();\">退出</div>"
    		});
    	exitappself=function(){
    		
           	Ext.Msg.show({
							message : '<div class="exitBtn">确定退出登录？</div>',
							width : 180,
							style:'background-color:red;',
							buttons : [{
										text : '确认',
										itemId : '1',
										style:''
									}, {
										text : '取消',
										itemId : '0',
										style:''
									}],
							fn : function(itemId) {
								if (itemId == '1') {
									navigator.app.exitApp();
								}
							}
						});
    	}
    	this.callParent([config]);
    }

});
