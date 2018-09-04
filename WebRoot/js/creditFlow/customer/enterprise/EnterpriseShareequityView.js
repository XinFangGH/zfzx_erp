//EnterpriseShareequityView.js

EnterpriseShareequityView= Ext.extend(Ext.Window, {

	// 构造函数
	constructor : function(_cfg) {
		if(typeof(_cfg.customerType)!="undefined"){
			this.customerType=_cfg.customerType
		}
		if(typeof(_cfg.customerId)!="undefined"){
			this.customerId=_cfg.customerId;
		}
		if(typeof(_cfg.personType)!="undefined"){
			this.personType=_cfg.personType;
		}
		if(typeof(_cfg.shareequityType)!="undefined"){
			this.shareequityType=_cfg.shareequityType;
		}
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		EnterpriseShareequityView.superclass.constructor.call(this, {
			id : 'EnterpriseShareequityView',
			title : '股东信息',
			region : 'center',
			layout : 'border',
			iconCls :'menu-company',
			width :800,
			height:350,
			modal : true,
			layout : 'fit',
			items : [this.EnterpriseShareequity]
		});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		this.EnterpriseShareequity=new EnterpriseShareequity({enId:this.customerId,isHidden:this.isReadOnly,isTitle:false,saveHidden:this.isReadOnly})
	}
	
	
});