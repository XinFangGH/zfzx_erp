//鉴于每个功能展示效果不同,可能是表格、图表等其他形式,所以此处每个功能都需要定制
//1.并且对象名称必须和用户表中的desks值相同
//2.所有的功能都定义在DeskFeature.js里

PersonalDesktop = Ext.extend(Ext.Panel, {
	portalPanel : null,
	toolbar : null,
	constructor : function(_cfg) {
		var jsCtArr = [
			__ctxPath + '/ext3/ux/Portal.js',
			__ctxPath + '/ext3/ux/PortalColumn.js',
			__ctxPath + '/ext3/ux/Portlet.js',
			__ctxPath + '/js/system/ResetPasswordForm.js',
			__ctxPath + '/js/system/setPasswordForm.js',
			__ctxPath +'/js/system/DeskFeature.js'
  		];
		Ext.applyIf(this, _cfg);
		$ImportSimpleJs(jsCtArr, this.initUIComponents, this);
		PersonalDesktop.superclass.constructor.call(this, {
			title : '工作桌面',
			closable : false,
			id : 'PersonalDesktop',
			border : false,
			iconCls : 'menu-desktop'
//			items : [this.portalPanel]
		});
	},
	initUIComponents : function() {
		var confs = new Array();
		var flag=true;//如果用户配置了属于自己的个人桌面则flag=false
		var temp="";
		if(curUserInfo.userDesk){//如果用户已经配置了属于自己的个人桌面
			temp=curUserInfo.userDesk;
			flag=false
		}else if(curUserInfo.deskRights){//用户拥有的角色配置了个人桌面
			temp=curUserInfo.deskRights;
		}
		//创建公共功能_开始
		if(flag){
			var common=['TaskPanelViewNew','SystemAccountIsException','LateView','PlBidPlanView'];
			for(var j=0;j<4;j++){
				var p = {
					panelId : common[j],
					row : j,
					column : j%2==0?0:1
				};
				confs.push(p);
			}
		}
		//创建公共功能_结束
		
		temp=temp.replace(/ /g,'');//替换全部空格
		var initDesks=initDeskMenus.replace("{","").replace("}","").split(',');//当前系统哪些业务具有属于自己的个人桌面
		//循环获得功能节点信息
		for(var i=0;i<initDesks.length;i++){
			var parentId=initDesks[i].split('=')[0].replace(" ","");//节点id
			if(temp.indexOf(',')==-1){
				temp=temp.replace(parentId,'');
			}else{
				temp=temp.replace(parentId+',','');
			}
		}
		var feature=temp.split(',');
		for(var j=0;j<feature.length;j++){
			var p = {
				panelId : feature[j],
				row : j,
				column : j%2==0?0:1
			};
			confs.push(p);
		}
		
		var column0 = [];
		var column1 = [];
		var dh = document.body.clientHeight;
		for (var v = 0; v < confs.length; v++) {
			try{
				if (confs[v].column==0 && confs[v].panelId){
					column0.push(eval('new ' + confs[v].panelId + '({height:'+((dh*0.6))+'})'));
					/*if(confs[v].panelId.includes('Desk')){//图表
					}else{
						column0.push(eval('new ' + confs[v].panelId + '({height:'+((dh*0.4))+'})'));
					}*/
				} 
				if(confs[v].column==1 && confs[v].panelId){
					column1.push(eval('new ' + confs[v].panelId + '({height:'+((dh*0.6))+'})'));
					/*if(confs[v].panelId.includes('Desk')){//图表
					}else{
						column1.push(eval('new ' + confs[v].panelId + '({height:'+(dh*0.4)+'})'));
					}*/
				}
			}catch(err){
//				Ext.ux.Toast.msg('操作信息',confs[v].panelId+'没有定义!');
			}
		}
		this.portalPanel = {
			id : 'Portal',
			layout:'column',//是column不是portlet 否则时不时的报错 getupdate出错。。。。add by gao
			border : false,
			region : 'center',
			margins : '5 5 5 0',
			items : [{
				columnWidth : .5,
				border:false,
				style : 'padding:5px 0 0 10px',
				items : column0
			}, {
				columnWidth : .5,
				border:false,
				style : 'padding:5px 0 0 10px',
				items : column1
			}]
		};
		
		this.add(this.portalPanel)
		this.doLayout();
	}
});