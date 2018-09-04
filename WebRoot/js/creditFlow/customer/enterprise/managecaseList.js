var managecaseListWin = function(eid,orgVal){
Ext.Ajax.request({   
   url: __ctxPath + '/creditFlow/customer/enterprise/isEntEmptyEnterprise.do',   
   method:'post',   
   params:{organizecode:orgVal},   
   success: function(response, option) {   
    var obj = Ext.decode(response.responseText);
    
	var jStore_companyBusiness = new Ext.data.JsonStore(getCompanyBusinessStoreCfg(eid));
	
	var cModel_companyBusiness = new Ext.grid.ColumnModel(companyBusinessModelCfg);
	
	var gPanel_companyBusiness = new Ext.grid.GridPanel({
		title : '公司业务情况',
		store : jStore_companyBusiness,
//	 	autoHeight:true,
		colModel : cModel_companyBusiness,
		selModel : new Ext.grid.RowSelectionModel(),
		stripeRows : true,
		plugins : expanderCompanyBusiness,
		viewConfig : {forceFit : true},
		listeners : {
			'rowcontextmenu' : function(g,r,e){
				e.stopEvent() ;
				g.getSelectionModel().selectRow(r) ;
				var id = g.getStore().getAt(r).get('id');
				var menu = new Ext.menu.Menu({
					items : [{
								iconCls : 'addIcon',
								text : '新增',
								handler : function() {
									if(this.clicked!=true){
										addCompanyBusiness(eid,g) ;
									}
									this.clicked = true ;
								}
							},'-',{
								iconCls : 'updateIcon',
								text : '修改',
								handler : function() {
									if(this.clicked!=true){
										updateCompanyBusiness(g,r) ;
									}
									this.clicked = true ;
								}
							},'-',{
								iconCls : 'deleteIcon',
								text : '删除',
								handler : function() {
									if(this.clicked!=true){
										globalGridDeleteRecord(g,r,e,__ctxPath + '/creditFlow/customer/enterprise/deleteRsEnterpriseManagecase.do')
									}
									this.clicked = true ;
								}
							}]
				})
				menu.showAt(e.getXY());
			},'headercontextmenu' : function(g,c,e){
				e.stopEvent() ;
				var menu = new Ext.menu.Menu({
					items : [{
								iconCls : 'addIcon',
								text : '新增',
								handler : function() {
									if(this.clicked!=true){
										addCompanyBusiness(eid,g) ;
									}
									this.clicked = true ;
								}
							}]
				})
				menu.showAt(e.getXY());
			}
		}
	});
	
	jStore_companyBusiness.addListener('load',function(){
		gPanel_companyBusiness.getSelectionModel().selectFirstRow() ;
	}) ;
			//---------------------------公司未结算业务--应付开始-----------------------------------
	var jStore_unSettlementCompanyBusinessTackle = new Ext.data.JsonStore(getUnSettlementCompanyBusinessTackleStoreCfg(eid,'应付'));
	
	var cModel_unSettlementCompanyBusinessTackle = new Ext.grid.ColumnModel(unSettlementCompanyBusinessTackleModelCfg);
	
	var gPanel_unSettlementCompanyBusinessTackle = new Ext.grid.GridPanel({
		title : '未结算业务-应付',
		store : jStore_unSettlementCompanyBusinessTackle,
//	 	autoHeight:true,
		colModel : cModel_unSettlementCompanyBusinessTackle,
		viewConfig : {forceFit : true},
		selModel : new Ext.grid.RowSelectionModel(),
		stripeRows : true,
		plugins : expanderUnSettlementCompanyBusinessTackle,
		listeners : {
			'rowcontextmenu' : function(g,r,e){
				e.stopEvent() ;
				g.getSelectionModel().selectRow(r) ;
				var id = g.getStore().getAt(r).get('id');
				var menu = new Ext.menu.Menu({
					items : [{
								iconCls : 'addIcon',
								text : '新增',
								handler : function() {
									if(this.clicked!=true){
										addUnSettlementCompanyBusinessTackle(eid,g) ; 
									}
									this.clicked = true ;
								}
							},'-',{
								iconCls : 'updateIcon',
								text : '修改',
								handler : function() {
									if(this.clicked!=true){
										updateUnSettlementCompanyBusinessTackle(g,r) ;
									}
									this.clicked = true ;
								}
							},'-',{
								iconCls : 'deleteIcon',
								text : '删除',
								handler : function() {
									if(this.clicked!=true){
										globalGridDeleteRecord(g,r,e,__ctxPath + '/credit/customer/enterprise/ajaxDeleteUnSettlementCompanyBusiness.do') ;
									}
									this.clicked = true ;
								}
							}]
				})
				menu.showAt(e.getXY());
			},'headercontextmenu' : function(g,c,e){
				e.stopEvent() ;
				var menu = new Ext.menu.Menu({
					items : [{
								iconCls : 'addIcon',
								text : '新增',
								handler : function() {
									if(this.clicked!=true){
										addUnSettlementCompanyBusinessTackle(eid,g) ;
									}
									this.clicked = true ;
								}
							}]
				})
				menu.showAt(e.getXY());
			}
		}
	});
		
	jStore_unSettlementCompanyBusinessTackle.addListener('load',function(){
		gPanel_unSettlementCompanyBusinessTackle.getSelectionModel().selectFirstRow() ;
	}) ;
//---------------------------公司未结算业务--应付结束-----------------------------------
/********************公司未结算业务--应收开始**********/
	var jStore_unSettlementCompanyBusinessReceivable = new Ext.data.JsonStore(getUnSettlementCompanyBusinessReceivableStoreCfg(eid,'应收'));
	
	var cModel_unSettlementCompanyBusinessReceivable = new Ext.grid.ColumnModel(unSettlementCompanyBusinessReceivableModelCfg);
	
	var gPanel_unSettlementCompanyBusinessReceivable = new Ext.grid.GridPanel({
		title : '未结算业务-应收',
		store : jStore_unSettlementCompanyBusinessReceivable,
//	 	autoHeight:true,
		colModel : cModel_unSettlementCompanyBusinessReceivable,
		selModel : new Ext.grid.RowSelectionModel(),
		stripeRows : true,
		viewConfig : {forceFit : true},
		plugins : expanderUnSettlementCompanyBusinessReceivable,
		listeners : {
			'rowcontextmenu' : function(g,r,e){
				e.stopEvent() ;
				g.getSelectionModel().selectRow(r) ;
				var id = g.getStore().getAt(r).get('id');
				var menu = new Ext.menu.Menu({
					items : [{
								iconCls : 'addIcon',
								text : '新增',
								handler : function() {
									if(this.clicked!=true){
										addUnSettlementCompanyBusinessReceivable(eid,g) ;
									}
									this.clicked = true ;
								}
							},'-',{
								iconCls : 'updateIcon',
								text : '修改',
								handler : function() {
									if(this.clicked!=true){
										updateUnSettlementCompanyBusinessReceivable(g,r) ;
									}
									this.clicked = true ;
								}
							},'-',{
								iconCls : 'deleteIcon',
								text : '删除',
								handler : function() {
									if(this.clicked!=true){
										globalGridDeleteRecord(g,r,e,__ctxPath + '/credit/customer/enterprise/ajaxDeleteUnSettlementCompanyBusiness.do') ;
									}
									this.clicked = true ;
								}
							}]
				})
				menu.showAt(e.getXY());
			},'headercontextmenu' : function(g,c,e){
				e.stopEvent() ;
				var menu = new Ext.menu.Menu({
					items : [{
								iconCls : 'addIcon',
								text : '新增',
								handler : function() {
									if(this.clicked!=true){
										addUnSettlementCompanyBusinessReceivable(eid,g) ;
									}
									this.clicked = true ;
								}
							}]
				})
				menu.showAt(e.getXY());
			}
		}
	});
	
	jStore_unSettlementCompanyBusinessReceivable.addListener('load',function(){
		gPanel_unSettlementCompanyBusinessReceivable.getSelectionModel().selectFirstRow() ;
	}) ;
/**************************************公司未结算业务--应收结束**********************/
	/********************市场客户  -   上游 客户  开始**upriver*****downriver***/
	var upriverCustomer = "上游客户" ;
	var jStore_marketCustomerUpriver = new Ext.data.JsonStore(getMarketCustomerStoreCfg(eid,upriverCustomer));
	
	var cModel_marketCustomerUpriver = new Ext.grid.ColumnModel(marketCustomerModelCfg.slice(0));
	
	var gPanel_marketCustomerUpriver = new Ext.grid.GridPanel({
		title : '上游客户',
		store : jStore_marketCustomerUpriver,
//	 	autoHeight:true,
		colModel : cModel_marketCustomerUpriver,
		stripeRows : true,
		viewConfig : {forceFit : true},
		plugins : expanderMarketCustomerUpriver,
		listeners : {
			'rowcontextmenu' : function(g,r,e){
				e.stopEvent() ;
				g.getSelectionModel().selectRow(r) ;
				var id = g.getStore().getAt(r).get('id');
				var menu = new Ext.menu.Menu({
					items : [{
								iconCls : 'addIcon',
								text : '新增',
								handler : function() {
									if(this.clicked!=true){
										addMarketCustomer(eid,g,upriverCustomer) ;
									}
									this.clicked = true ;
								}
							},'-',{
								iconCls : 'updateIcon',
								text : '修改',
								handler : function() {
									if(this.clicked!=true){
										updateMarketCustomer(g,r,upriverCustomer) ;
									}
									this.clicked = true ;
								}
							},'-',{
								iconCls : 'deleteIcon',
								text : '删除',
								handler : function() {
									if(this.clicked!=true){
										globalGridDeleteRecord(g,r,e,__ctxPath + '/credit/customer/enterprise/ajaxDeleteMarketCustomer.do') ;
									}
									this.clicked = true ;
								}
							}]
				})
				menu.showAt(e.getXY());
			},'headercontextmenu' : function(g,c,e){
				e.stopEvent() ;
				var menu = new Ext.menu.Menu({
					items : [{
								iconCls : 'addIcon',
								text : '新增',
								handler : function() {
									if(this.clicked!=true){
										addMarketCustomer(eid,g,upriverCustomer) ;
									}
									this.clicked = true ;
								}
							}]
				})
				menu.showAt(e.getXY());
			}
		}
	});
	
	jStore_marketCustomerUpriver.addListener('load',function(){
		gPanel_marketCustomerUpriver.getSelectionModel().selectFirstRow() ;
	}) ;
/**************************************市场客户 --上游客户  结束**********************/
	/********************市场客户  -  下游客户开始**********/
	var downriverCustomer = "下游客户" ;
	var jStore_marketCustomerDownriver = new Ext.data.JsonStore(getMarketCustomerStoreCfg(eid,downriverCustomer));
	
	var cModel_marketCustomerDownriver = new Ext.grid.ColumnModel(marketCustomerModelCfg.slice(0));
	
	var gPanel_marketCustomerDownriver = new Ext.grid.GridPanel({
		title : '下游客户',
		store : jStore_marketCustomerDownriver,
//	 	autoHeight:true,
		colModel : cModel_marketCustomerDownriver,
		stripeRows : true,
		viewConfig : {forceFit : true},
		plugins : expanderMarketCustomerDownriver,
		listeners : {
			'rowcontextmenu' : function(g,r,e){
				e.stopEvent() ;
				g.getSelectionModel().selectRow(r) ;
				var id = g.getStore().getAt(r).get('id');
				var menu = new Ext.menu.Menu({
					items : [{
								iconCls : 'addIcon',
								text : '新增',
								handler : function() {
									if(this.clicked!=true){
										addMarketCustomer(eid,g,downriverCustomer) ;
									}
									this.clicked = true ;
								}
							},'-',{
								iconCls : 'updateIcon',
								text : '修改',
								handler : function() {
									if(this.clicked!=true){
										updateMarketCustomer(g,r,downriverCustomer) ;
									}
									this.clicked = true ;
								}
							},'-',{
								iconCls : 'deleteIcon',
								text : '删除',
								handler : function() {
									if(this.clicked!=true){
										globalGridDeleteRecord(g,r,e,__ctxPath + '/credit/customer/enterprise/ajaxDeleteMarketCustomer.do') ;
									}
									this.clicked = true ;
								}
							}]
				})
				menu.showAt(e.getXY());
			},'headercontextmenu' : function(g,c,e){
				e.stopEvent() ;
				var menu = new Ext.menu.Menu({
					items : [{
								iconCls : 'addIcon',
								text : '新增',
								handler : function() {
									if(this.clicked!=true){
										addMarketCustomer(eid,g,downriverCustomer) ;
									}
									this.clicked = true ;
								}
							}]
				})
				menu.showAt(e.getXY());
			}
		}
	});
	
	jStore_marketCustomerDownriver.addListener('load',function(){
		gPanel_marketCustomerDownriver.getSelectionModel().selectFirstRow() ;
	}) ;
/**************************************市场客户---下游客户 结束**********************/
/********************当前履行的主要合同--应付类合同开始**********/
	var jStore_tackleContract = new Ext.data.JsonStore(getTackleContractStoreCfg(eid));
	
	var cModel_tackleContract = new Ext.grid.ColumnModel(tackleContractModelCfg);
	
	var gPanel_tackleContract = new Ext.grid.GridPanel({
		title : '应付类合同',
		store : jStore_tackleContract,
//	 	autoHeight:true,
		colModel : cModel_tackleContract,
		selModel : new Ext.grid.RowSelectionModel(),
		stripeRows : true,
		viewConfig : {forceFit : true},
		plugins : expanderTackleContract,
		listeners : {
			'rowcontextmenu' : function(g,r,e){
				e.stopEvent() ;
				g.getSelectionModel().selectRow(r) ;
				var id = g.getStore().getAt(r).get('id');
				var menu = new Ext.menu.Menu({
					items : [{
								iconCls : 'addIcon',
								text : '新增',
								handler : function() {
									if(this.clicked!=true){
										addTackleContract(eid,g) ;
									}
									this.clicked = true ;
								}
							},'-',{
								iconCls : 'updateIcon',
								text : '修改',
								handler : function() {
									if(this.clicked!=true){
										updateTackleContract(g,r) ;
									}
									this.clicked = true ;
								}
							},'-',{
								iconCls : 'deleteIcon',
								text : '删除',
								handler : function() {
									if(this.clicked!=true){
										globalGridDeleteRecord(g,r,e,__ctxPath + '/credit/customer/enterprise/ajaxDeleteCompanyContract.do') ;
									}
									this.clicked = true ;
								}
							}]
				})
				menu.showAt(e.getXY());
			},'headercontextmenu' : function(g,c,e){
				e.stopEvent() ;
				var menu = new Ext.menu.Menu({
					items : [{
								iconCls : 'addIcon',
								text : '新增',
								handler : function() {
									if(this.clicked!=true){
										addTackleContract(eid,g) ;
									}
									this.clicked = true ;
								}
							}]
				})
				menu.showAt(e.getXY());
			}
		}
	});
	
	jStore_tackleContract.addListener('load',function(){
		gPanel_tackleContract.getSelectionModel().selectFirstRow() ;
	}) ;
/**************************************当前履行的主要合同--应付类合同开始**********************/
/********************当前履行的主要合同--应收类合同开始**********/
	var jStore_receivableContract = new Ext.data.JsonStore(getReceivableContractStoreCfg(eid));
	
	var cModel_receivableContract = new Ext.grid.ColumnModel(receivableContractModelCfg);
	
	var gPanel_receivableContract = new Ext.grid.GridPanel({
		title : '应收类合同',
		store : jStore_receivableContract,
//	 	autoHeight:true,
		colModel : cModel_receivableContract,
		selModel : new Ext.grid.RowSelectionModel(),
		stripeRows : true,
		viewConfig : {forceFit : true},
		plugins : expanderReceivableContract,
		listeners : {
			'rowcontextmenu' : function(g,r,e){
				e.stopEvent() ;
				g.getSelectionModel().selectRow(r) ;
				var id = g.getStore().getAt(r).get('id');
				var menu = new Ext.menu.Menu({
					items : [{
								iconCls : 'addIcon',
								text : '新增',
								handler : function() {
									if(this.clicked!=true){
										addReceivableContract(eid,g) ;
									}
									this.clicked = true ;
								}
							},'-',{
								iconCls : 'updateIcon',
								text : '修改',
								handler : function() {
									if(this.clicked!=true){
										updateReceivableContract(g,r) ;
									}
									this.clicked = true ;
								}
							},'-',{
								iconCls : 'deleteIcon',
								text : '删除',
								handler : function() {
									if(this.clicked!=true){
										globalGridDeleteRecord(g,r,e,__ctxPath + '/credit/customer/enterprise/ajaxDeleteCompanyContract.do') ;
									}
									this.clicked = true ;
								}
							}]
				})
				menu.showAt(e.getXY());
			},'headercontextmenu' : function(g,c,e){
				e.stopEvent() ;
				var menu = new Ext.menu.Menu({
					items : [{
								iconCls : 'addIcon',
								text : '新增',
								handler : function() {
									if(this.clicked!=true){
										addReceivableContract(eid,g) ;
									}
									this.clicked = true ;
								}
							}]
				})
				menu.showAt(e.getXY());
			}
		}
	});
	
	jStore_receivableContract.addListener('load',function(){
		gPanel_receivableContract.getSelectionModel().selectFirstRow() ;
	}) ;
/**************************************当前履行的主要合同--应收类合同开始**********************/
/********************当前履行的主要合同--非收益类合同开始**********/
	var jStore_unIncomeContract = new Ext.data.JsonStore(getUnIncomeContractStoreCfg(eid));
	
	var cModel_unIncomeContract = new Ext.grid.ColumnModel(unIncomeContractModelCfg);
	
	var gPanel_unIncomeContract = new Ext.grid.GridPanel({
		title : '非收益类合同',
		store : jStore_unIncomeContract,
//	 	autoHeight:true,
		colModel : cModel_unIncomeContract,
		selModel : new Ext.grid.RowSelectionModel(),
		stripeRows : true,
		viewConfig : {forceFit : true},
		plugins : expanderUnIncomeContract,
		listeners : {
			'rowcontextmenu' : function(g,r,e){
				e.stopEvent() ;
				g.getSelectionModel().selectRow(r) ;
				var id = g.getStore().getAt(r).get('id');
				var menu = new Ext.menu.Menu({
					items : [{
								iconCls : 'addIcon',
								text : '新增',
								handler : function() {
									if(this.clicked!=true){
										addUnIncomeContract(eid,g) ;
									}
									this.clicked = true ;
								}
							},'-',{
								iconCls : 'updateIcon',
								text : '修改',
								handler : function() {
									if(this.clicked!=true){
										updateUnIncomeContract(g,r) ;
									}
									this.clicked = true ;
								}
							},'-',{
								iconCls : 'deleteIcon',
								text : '删除',
								handler : function() {
									if(this.clicked!=true){
										globalGridDeleteRecord(g,r,e,__ctxPath + '/credit/customer/enterprise/ajaxDeleteCompanyContract.do') ;
									}
									this.clicked = true ;
								}
							}]
				})
				menu.showAt(e.getXY());
			},'headercontextmenu' : function(g,c,e){
				e.stopEvent() ;
				var menu = new Ext.menu.Menu({
					items : [{
								iconCls : 'addIcon',
								text : '新增',
								handler : function() {
									if(this.clicked!=true){
										addUnIncomeContract(eid,g) ;
									}
									this.clicked = true ;
								}
							}]
				})
				menu.showAt(e.getXY());
			}
		}
	});
	
	jStore_unIncomeContract.addListener('load',function(){
		gPanel_unIncomeContract.getSelectionModel().selectFirstRow() ;
	}) ;
	
/**************************************当前履行的主要合同--非收益类合同结束**********************/
	
	
	var LineTabPanel = new Ext.TabPanel({
	    id: 'LineTabPanel',
	    border : false ,
	    activeTab: 0,
//	    autoScroll: true,
//	    width : (screen.width-180)*0.6 + 7,
	    deferredRender: false,
	    items: [
	   	 gPanel_companyBusiness,gPanel_unSettlementCompanyBusinessTackle,gPanel_unSettlementCompanyBusinessReceivable,
	   	 gPanel_marketCustomerUpriver,gPanel_marketCustomerDownriver,gPanel_tackleContract,gPanel_receivableContract,gPanel_unIncomeContract
       ]
	}); 
	new Ext.Window({
		id :'win_listEnterpriseManagecase',
		title : '<font color=red><'+(obj.data == null?'':obj.data.shortname)+'></font>业务经营情况',
		width :(screen.width-180)*0.6 + 130,
		height : 400,
		buttonAlign : 'center',
		layout:'fit',
		modal : true,
		constrainHeader : true ,
		collapsible : true, 
		border : false,
		maximizable : true,
		autoScroll : true ,
		items :[LineTabPanel]
	}).show();
	jStore_unSettlementCompanyBusinessTackle.load() ;
	jStore_unSettlementCompanyBusinessReceivable.load() ;
	jStore_marketCustomerUpriver.load() ;
	jStore_marketCustomerDownriver.load();
	jStore_tackleContract.load() ;
	jStore_receivableContract.load() ;
	jStore_unIncomeContract.load() ;
	jStore_companyBusiness.load();
	}
	})
}