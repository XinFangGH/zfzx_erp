//seniorgrant.js
seniorgrant = Ext.extend(Ext.Window, {

	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		seniorgrant.superclass.constructor.call(this, {
			id : 'seniorgrant',
			title : '高级分配',
			region : 'center',
			layout : 'border',
			iconCls :'menu-company',
			width :500,
			height:300,
			modal : true,
			items : [this.tabpanel ],
			buttonAlign : 'center',
			buttons : [{
								text : '确定',
								iconCls : 'btn-save',
								hidden : this.isLook,
								scope : this,
								handler : this.save
							},{
								text : '取消',
								iconCls : 'btn-cancel',
								hidden : this.isLook,
								scope : this,
								handler : this.cancel
							}]
		});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		this.tabpanel = new Ext.TabPanel({
			resizeTabs : true, 
			enableTabScroll : true,
			Active : 0,
			width:500,
			height:100,
			region : 'center', 
			border:false,
			deferredRender : false,
			activeTab : 0, // first tab initially active
			xtype : 'tabpanel',
			items : [new GrantByCreator({}),
			         new GrantByBelongId({}),
			         new GrantByAreaId({})
					]
		});
	},
	save:function(){
		var ActiveTab =this.items.get(0).getActiveTab();
		var title =this.items.get(0).getActiveTab().title;
		var arg=null;
		var url=null;
		if(title=="按创建人"){
			var creatorId =this.items.get(0).getActiveTab().getCmpByName("creatorId").getValue();
			var grantCreatorId =this.items.get(0).getActiveTab().getCmpByName("grantCreatorId").getValue();
			if(creatorId==null || creatorId==""|| grantCreatorId==null|| grantCreatorId==""){
				Ext.ux.Toast.msg('操作信息', '请选择创建人和分配人后在进行分配！');
				return;
			}else{
				url=__ctxPath+ "/creditFlow/customer/customerProsperctive/getSeniorGrantCreatorIdBpCustProsperctive.do";
				arg ={
					creatorId:creatorId,
					grantCreatorId:grantCreatorId
				};
			}
		}else if(title=="按共享人"){
			var belongId =this.items.get(0).getActiveTab().getCmpByName("belongId").getValue();
			var grantBelongId =this.items.get(0).getActiveTab().getCmpByName("grantBelongId").getValue();
			if(belongId==null || belongId==""|| grantBelongId==null|| grantBelongId==""){
				Ext.ux.Toast.msg('操作信息', '请选择共享人和分配人后在进行分配！');
				return;
			}else{
				url=__ctxPath+ "/creditFlow/customer/customerProsperctive/getSeniorGrantBelongIdBpCustProsperctive.do";
				arg ={
					belongId:belongId,
					grantBelongId:grantBelongId
				};
			}
		}else if(title=="按地区"){
			var areaId =this.items.get(0).getActiveTab().getCmpByName("areaId").getValue();
			var grantAreaId =this.items.get(0).getActiveTab().getCmpByName("grantAreaId").getValue();
			if(areaId==null || areaId==""|| grantAreaId==null|| grantAreaId==""){
				Ext.ux.Toast.msg('操作信息', '请选择地区和分配人后在进行分配！');
				return;
			}else{
				url=__ctxPath+ "/creditFlow/customer/customerProsperctive/getSeniorGrantAreaIdBpCustProsperctive.do";
				arg ={
					areaId:areaId,
					grantAreaId:grantAreaId
				};
			}
		}
		if(arg!=null && url !=null){
			Ext.Msg.confirm('信息确认', '确认进行客户分配吗？', function(btn) {
								if (btn == 'yes') {
									Ext.Ajax.request({
										url : url,
										method : "post",
										params :arg,
										scope : this,
										success : function(response) {
											grid.getStore().reload();
											Ext.ux.Toast.msg('操作信息', '分配成功！');
										},
										failure : function() {
											Ext.ux.Toast.msg('操作信息', '分配失败！');
										}
									})
								}
							})
		}
		
	},
	cancel:function(){
		this.close();
	}
	
	
});