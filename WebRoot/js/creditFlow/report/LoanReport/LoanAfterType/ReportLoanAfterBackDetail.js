/**
 * @extends Ext.Panel
 * @description 贷款本息实收明细表
 */
ReportLoanAfterBackDetail = Ext.extend(Ext.Panel, {
	panel:null,
	url:'',
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		ReportLoanAfterBackDetail.superclass.constructor.call(this, {
			id : 'ReportLoanAfterBackDetail'+this.reportKey, 
			title : this.title,
			layout : 'border',
			iconCls:"btn-tree-team39",
			items : [searchPanel,this.toolbar,{
				region : 'center',
				id : 'reportTemp' + this.reportKey,
				border : false,
				xtype : 'panel',
				items:this.toolbar,
				html : '<div id="'+this.reportKey+'"></div>'
			}],
			listeners : {
				'afterrender' : function(v) {
					commomClick(this.panel,this.url,this.reportKey,'');
				}
			}
		});
	},
	// 初始化组件
	initUIComponents : function() {
		var firstDate = new Date();
		firstDate.setDate(1);
		var endDate = new Date(firstDate);
		endDate.setMonth(firstDate.getMonth()+1);
		endDate.setDate(0);

		var reportKey=this.reportKey;
		
		var pdfButton = new Ext.Button({
			text : 'pdf',
			iconCls : 'btn-pdf'
		});
		var htmlButton = new Ext.Button({
			text : 'html',
			iconCls : 'btn-ie'
		});
		var xlsButton = new Ext.Button({
			text : 'excel',
			iconCls : 'btn-xls'
		});
		
		this.toolbar = new Ext.Toolbar({
			autoWidth : true,
			layout : 'hbox',
			defaults : {
				anchor : '10%,10%'
			},
			items : [pdfButton,xlsButton, htmlButton]
		});

		searchPanel = new HT.SearchPanel({
			id : 'reportSearchPanel' + reportKey,
			layout : 'column',
			region : 'north',
			height : 20,
			anchor : '100%',
			keys : [{
				key : Ext.EventObject.ENTER,
				fn : this.search,
				scope : this
			}, {
				key : Ext.EventObject.ESC,
				fn : this.reset,
				scope : this
			}],
			layoutConfig: {
               align:'middle'
            },
			items : [{ 
				columnWidth : reportKey=="ContractRegister"?.14:.2,
				layout : 'form',
				border : false,
				labelAlign : 'right',
				labelWidth:60,    
				items : [ {
					fieldLabel : reportKey=="ContractRegister"?'放款日期':'起始时间',
					name : 'factDate_GE',
					xtype :'datefield',
					anchor : '100%',
					format : 'Y-m-d',
					value:firstDate,
					listeners:{
						scope:this,
						'afterrender':function(v){
							v.fireEvent('change',v);
						},
						'blur':function(v){
							var intentDateGE=this.getCmpByName('intentDate_GE');
							var intentDateLE=this.getCmpByName('intentDate_LE');
							intentDateGE.setValue();
							intentDateGE.setDisabled(true);
							intentDateLE.setValue();
							intentDateLE.setDisabled(true);
						}
					}
				}] 
			},{ 
				columnWidth : reportKey=="ContractRegister"?.12:.18,
				layout : 'form',
				border : false,
				labelAlign : 'right',
				style : 'padding-left:15px;',
				labelWidth:20,
				items : [{
					fieldLabel : '至',
					name : 'factDate_LE',
					xtype :'datefield',
					anchor : '100%',
					format : 'Y-m-d',
					value:endDate,
					listeners:{
						scope:this,
						'afterrender':function(v){
							v.fireEvent('change',v);
						},
						'blur':function(v){
							var intentDateGE=this.getCmpByName('intentDate_GE');
							var intentDateLE=this.getCmpByName('intentDate_LE');
							intentDateGE.setValue();
							intentDateGE.setDisabled(true);
							intentDateLE.setValue();
							intentDateLE.setDisabled(true);
						}
					}
				}] 
			},{
				columnWidth :.14,
				layout : 'form',
				border : false,
				labelAlign : 'right',
				hidden:reportKey=="ContractRegister"?false:true,
				labelWidth:60,    
				items : [ {
					fieldLabel :'到期日期',
					name : 'intentDate_GE',
					xtype :'datefield',
					anchor : '100%',
					format : 'Y-m-d',
					listeners:{
						scope:this,
						'blur':function(v){
							var factDateGE=this.getCmpByName('factDate_GE');
							var factDateLE=this.getCmpByName('factDate_LE');
							factDateGE.setValue();
							factDateGE.setDisabled(true);
							factDateLE.setValue();
							factDateLE.setDisabled(true);
						}
					}
				}] 
			},{
				columnWidth : .12,
				layout : 'form',
				border : false,
				labelAlign : 'right',
				hidden:reportKey=="ContractRegister"?false:true,
				style : 'padding-left:15px;',
				labelWidth:20,
				items : [{
					fieldLabel : '至',
					name : 'intentDate_LE',
					xtype :'datefield',
					anchor : '100%',
					format : 'Y-m-d',
					listeners:{
						scope:this,
						'blur':function(v){
							var factDateGE=this.getCmpByName('factDate_GE');
							var factDateLE=this.getCmpByName('factDate_LE');
							factDateGE.setValue();
							factDateGE.setDisabled(true);
							factDateLE.setValue();
							factDateLE.setDisabled(true);
						}
					}
				}] 
			},{ 
				columnWidth : reportKey=="ContractRegister"?.16:.2,
				layout : 'form',
				border : false,
				labelAlign : 'right',
				labelWidth:reportKey=="ContractRegister"?70:60,    
				items : [ {
					fieldLabel : reportKey=="ContractRegister"?'借款人名称':'客户名称',
					name : 'customerName',
					xtype :'textfield',
					anchor : '100%'
				}] 
			},{
				columnWidth : reportKey=="ContractRegister"?.16:.2,
				layout : 'form',
				border : false,
				labelWidth:60,    
				labelAlign : 'right',
				items : [ {
					fieldLabel : reportKey=="ContractRegister"?'合同编号':'项目编号',
					name : 'projectNumber',
					xtype :'textfield',
					anchor : '100%'
				}] 
			},{
				columnWidth : .07,
				xtype : 'container',
				layout : 'form',
				defaults : {
					xtype : 'button'
				},
				style : 'padding-left:10px;',
				items : [{
					text : '查询',
					scope : this,
					iconCls : 'btn-search',
					handler:function(){
						commomClick(this.panel,this.url,reportKey,'');
					}
				}]
			},{
				columnWidth : .07,
				xtype : 'container',
				layout : 'form',
				defaults : {
					xtype : 'button'
				},
				style : 'padding-left:10px;',
				items : [{
					text : '重置',
					scope : this,
					iconCls : 'reset',
					handler:function(){
						this.panel.getForm().reset();
						var intentDateGE=this.getCmpByName('intentDate_GE');
						var intentDateLE=this.getCmpByName('intentDate_LE');
						var factDateGE=this.getCmpByName('factDate_GE');
						var factDateLE=this.getCmpByName('factDate_LE');
						intentDateGE.setDisabled(false);
						intentDateLE.setDisabled(false);
						factDateGE.setDisabled(false);
						factDateLE.setDisabled(false);
						this.panel.getForm().reset();
					}
				}]
			}]
		});
		
		this.panel=Ext.getCmp('reportSearchPanel'+reportKey);
		this.url=__ctxPath + '/system/getLoanAfterBackLoanReport.do';
		
		pdfButton.on('click', function(xlsButton) {
			commomClick(this.panel,this.url,reportKey,'pdf');
		}, this);
			
		htmlButton.on('click', function(htmlButton) {
		 	commomClick(this.panel,this.url,reportKey,'html');
		 }, this);
			
		 xlsButton.on('click', function(xlsButton) {
		 	commomClick(this.panel,this.url,reportKey,'xls');
		}, this);
	}
});