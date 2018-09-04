/**
 * @author:
 * @class SlCompanyMainView
 * @extends Ext.Panel
 * @description [SlCompanyMain]管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
PersonAll = Ext.extend(Ext.Window, {

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
		PersonAll.superclass.constructor.call(this, {
			id : 'PersonAll',
			title : '业务往来',
			region : 'center',
			layout : 'border',
			width:850,
			height:350,
			//autoScroll:true,
			modal : true,
			iconCls :'menu-company',
			items : [this.tabpanel ]
		});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		this.tabpanel = new Ext.TabPanel({
		resizeTabs : true, // turn on tab resizing
		enableTabScroll : true,
		Active : 0,
		width : 800,
		height:300,
		defaults : {
			autoScroll : true
		},
		region : 'center', 
		//layout:'fit'
		deferredRender : false,
		activeTab : 0, // first tab initially active
		xtype : 'tabpanel',
		items : [new PersonBusiness({customerType:this.customerType,customerId:this.customerId}),
		         new PersonMortgage({personType:this.personType,assureofname:this.customerId}),
		        // new Shareequity({shareequityType:this.shareequityType,shareequityId:this.customerId}),
		         new LegalPerson({legalPersonId:this.customerId}),
		         new CreditLoanHistoryPanel({personId:this.customerId,isReadOnly:this.isReadOnly})
				]
	});
	}
	
	
});
