/**
 * 手机登录初始化
 * by cjj
 */
//extjs4.0动态加载
Ext.Loader.setConfig({
			enabled : true,
			paths : {
				'mobile' : 'htmobile'
			}
		});

Ext.application({
    name: 'mobileLogin',
    launch: function() {
		Ext.Msg.defaultAllowedConfig.showAnimation = false;
        Ext.Msg.defaultAllowedConfig.hideAnimation = false;
    	clientWidth = document.body.clientWidth;
    	mobileView = Ext.Viewport;
    	var username = localStorage.getItem("userName")!=null?localStorage.getItem("userName"):'';
    	var isGesturePassword = localStorage.getItem(username+"isGesturePassword")!=null?localStorage.getItem(username+"isGesturePassword"):'';
        var errorPasswordcount=localStorage.getItem(username+"errorPassword");
        localStorage.setItem(username+"updateGesturePassword","0");//0初始化，1，修改密码的第一步，输入原始密码2，新密码
             mobileLogin = Ext.create('mobile.Login', {
								fullscreen : true
							});
    }

});
//检查当前用户有权访问funKey对应的功能
function isGranted(funKey){
	if(curUserInfo.rights.indexOf('__ALL')!=-1){
		return true;
	}
	var rights = curUserInfo.rights;
	var arr=rights.split(",");
	for(var i=0;i<arr.length;i++){
		if(funKey.trim()==arr[i].trim()){
			return true;
		}	
	} 
	return false;
}

/**
 * 在流程vm文件中不需要显示某个节点的的意见与说明，
 * 则把该节点的key放到以下变量中。
 */
var filterableNodeKeys="onlineJudgement,filterableNodeKeys,ExaminationArrangement,xsps,resolutionOnlineReviewMeeting";
var processNameFlowKey="PersonVehiclePledge,PersonVehicleMortgage,houseMortgageFlow,PersonSpecial,PersonLoanNormalFlow";
var myProcessNameFlowKey="PersonVehiclePledge,PersonVehicleMortgage,houseMortgageFlow,PersonSpecial,PersonLoanNormalFlow";
var quitflag = 0;
     
document.addEventListener( "deviceready",onDeviceReady, false);
    function onDeviceReady() {
           navigator.splashscreen.hide(); 
           document.addEventListener( "backbutton", eventBackButton, false); //注册返回键
    }
    function eventBackButton(){
     if(window.plugins.toast){
           window.plugins.toast.shortshow( "再按一次返回键退出程序" );
     }
 
    quitflag++;
    setTimeout(function() {
                quitflag = 0;
           }, 3000);
 
            if (quitflag >= 2) {
                navigator.app.exitApp();
           }   
    }