/*
 * 签到，签退操作
 */
var SignInOffView=function(){
	var cm = new Ext.grid.ColumnModel({
		defaults: {
	        sortable: false,
	        menuDisabled: true,
	        width: 100
    	},
		columns : [new Ext.grid.RowNumberer(),{
					dataIndex:'sectionId',
					hidden:true
				},{
					dataIndex:'signInFlag',
					hidden:true
				},{
					dataIndex:'signOffFlag',
					hidden:true
				},{
					dataIndex:'allowSignIn',
					hidden:true
				},{
					dataIndex:'allowSignOff',
					hidden:true
				},{
					header : '班次名称',
					dataIndex : 'systemName'
				}, {
					header : "上班规定时间",
					dataIndex : 'dutyStartTime'
				}, {
					header : "签到时间",
					dataIndex : 'signInTime'
				}, {
					header:'签到',
					dataIndex:'signInTime',
					renderer:function(val,metadata, record){
						var sectionId=record.data.sectionId;
						var signInFlag=record.data.signInFlag;
						
						
						if(signInFlag!=''){
							//显示签到的状态
							if(signInFlag==1){//正常登记
								return '<img src="'+ __ctxPath+'/images/flag/personal/signNormal.gif" title="正常"/>' ;
							}else if(signInFlag==2){
								return '<img src="'+ __ctxPath+'/images/flag/personal/signLateEarly.gif" title="迟到"/>' ;
							}
						}else{
							var allowSignIn=record.data.allowSignIn;
							if(allowSignIn=='1'){
								return '<button class="btn-signIn" title="签到" onclick="SignInOffView.signIn('+sectionId+');">&nbsp;</button>';
								
							}else if(allowSignIn=='-1'){
								return "尚未到签到时间";	
							}else{
								return "<font color='red'>已过签到时间</font>";	
							}
						}
					}
				}, {
					header : "下班规定时间",
					dataIndex : 'dutyEndTime'
				}, {
					header : "签退时间",
					dataIndex : 'signOffTime'
				},{
					header:'签退',
					dataIndex:'signOffTime',
					renderer:function(val,metadata, record){
						var sectionId=record.data.sectionId;
						var signOffFlag=record.data.signOffFlag;
						if(signOffFlag!=''){
							//显示签到的状态
							if(signOffFlag==1){//正常登记
								return '<img src="'+ __ctxPath+'/images/flag/personal/signNormal.gif" title="正常"/>' ;
							}else if(signOffFlag==3){
								return '<img src="'+ __ctxPath+'/images/flag/personal/signLateEarly.gif" title="早退"/>' ;
							}
						}else{
							var allowSignOff=record.data.allowSignOff;
							if(allowSignOff=='1'){
								return '<button class="btn-signOff" title="签退" onclick="SignInOffView.signOff('+sectionId+');">&nbsp;</button>';
							}else if(allowSignOff=='-1'){
								return "尚未到签退时间";
							}else{
								return "<font color='red'>已过签退时间</font>";
							}
						}
					}
				}
		]
	});
	
	var store = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : __ctxPath + '/personal/todayDutyRegister.do'
			}),
			reader : new Ext.data.JsonReader({
						root : 'result',
						fields : [ 'sectionId','systemName', 'dutyStartTime', 'signInTime','signInFlag',
								'dutyEndTime', 'signOffTime', 'signOffFlag','allowSignIn','allowSignOff']
					})
	});
	
	store.load();			
	var gridPanel = new Ext.grid.GridPanel({
				id:'SignInOffGrid',
				margins:'0',
				border:false,
				//title:curUserInfo.fullname + "，班次：()，星期三",
				store : store,
				tbar:new Ext.Toolbar({height:28,items:[
					{
						text:'刷新',
						iconCls:'btn-refresh',
						handler:function(){
							store.reload();
						}
					},{
						text:'请假登记',
						iconCls:'menu-holiday',
						handler:function(){
							App.clickTopTab('ErrandsRegisterView');
						}
					},{
						text:'外出登记',
						iconCls:'menu-errands',
						handler:function(){
						   App.clickTopTab('ErrandsRegisterOutView');
						}
					},{
						text:'个人考勤查询',
						iconCls:'menu-person',
						handler:function(){
							App.clickTopTab('DutyRegisterPersonView');
						}
					}
				]}) ,
				height:200,
				cm : cm
			});
	
	var topPanel=new Ext.Panel({
		title:'考勤--签到、签退',
		iconCls:'menu-signInOff',
		id:'SignInOffView',
		layout:'anchor',
		items:[gridPanel,{
			xtype:'panel',
			height:250,
			border:false,
			bodyStyle:'padding-top:20px;padding-left:20px;text-align:center',
			html:'<h1>请按规定的时间进行上下班签到签退</h1><img src="'+__ctxPath+'/images/clock.jpg"/>'
		}]
	});
	
	return topPanel;
};
/*
 * 签到
 */
SignInOffView.signIn=function(sectionId){
	//若迟到，填写迟到原因TODO
	Ext.Ajax.request({
		url:__ctxPath+'/personal/signInDutyRegister.do',
			method:'POST',
			params:{sectionId:sectionId},
			success:function(response,options){
				Ext.getCmp('SignInOffGrid').getStore().reload();
				Ext.ux.Toast.msg('操作信息', '成功签到！');
			}
	});
};
/**
 * 签退
 */
SignInOffView.signOff=function(sectionId){
	Ext.Ajax.request({
		url:__ctxPath+'/personal/signOffDutyRegister.do',
			method:'POST',
			params:{sectionId:sectionId},
			success:function(response,options){
				Ext.getCmp('SignInOffGrid').getStore().reload();
				Ext.ux.Toast.msg('操作信息', '成功签退！');
			}
	});
};