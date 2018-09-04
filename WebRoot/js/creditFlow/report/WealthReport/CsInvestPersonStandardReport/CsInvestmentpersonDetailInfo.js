/**
 * 投资客户明细表
 * @class CsInvestmentpersonDetailInfo
 * @extends Ext.Panel
 */
CsInvestmentpersonDetailInfo = Ext.extend(Ext.Panel, {
	panel:null,
	url:'',
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		CsInvestmentpersonDetailInfo.superclass.constructor.call(this, {
			id : 'CsInvestmentpersonDetailInfo'+this.reportKey,
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
			id : 'searchPanel' + reportKey,
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
				columnWidth : .2,
				layout : 'form',
				border : false,
				labelAlign : 'right',
				items : [ {
					labelWidth:70,    
					fieldLabel : '登记日期',
					name : 'searchDate_S',
					xtype :'datefield',
					anchor : '100%',
					format : 'Y-m-d',
					value:firstDate
				}] 
			},{ 
				columnWidth : .15,
				layout : 'form',
				border : false,
				labelAlign : 'right',
//				style:'padding-left:20px;',
				labelWidth:30,    
				items : [ {
					fieldLabel : '至',
					name : 'searchDate_E',
					xtype :'datefield',
					anchor : '100%',
					format : 'Y-m-d',
					value:endDate
				}] 
			},{ 
				columnWidth : .15,
				layout : 'form',
				border : false,
				labelAlign : 'right',
				labelWidth:60,
				items : [{
					fieldLabel : '客户经理',
					name : 'managerName',
					xtype :'textfield',
					anchor : '90%'
				}] 
			},{ 
				columnWidth : .15,
				layout : 'form',
				border : false,
				labelAlign : 'right',
				style : 'padding-left:15px;',
				labelWidth:60,
				items : [{
					fieldLabel : '所属门店',
					name : 'shopName',
					xtype :'textfield',
					anchor : '90%'
				}] 
			},{
				columnWidth : .1,
				xtype : 'container',
				layout : 'form',
				defaults : {
					xtype : 'button'
				},
				style : 'padding-left:60px;',
				items : [{
					text : '查询',
					scope : this,
					iconCls : 'btn-search',
					handler:function(){
						commomClick(this.panel,this.url,reportKey,'');
					}
				}]
			},{
				columnWidth : .1,
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
					}
				}]
			}]
		});
		
		this.panel=Ext.getCmp('searchPanel'+reportKey);
		this.url=__ctxPath + '/system/personDetailInfoWealthReport.do';
		
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