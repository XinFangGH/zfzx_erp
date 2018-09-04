/**
 * @extends Ext.Panel
 * @description 正式个人(企业)客户明细表
 */
ReportFormalDetail = Ext.extend(Ext.Panel, {
	panel:null,
	url:'',
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		ReportFormalDetail.superclass.constructor.call(this, {
			id : 'ReportFormalDetail'+this.reportKey,
			title : this.title,
			layout : 'anchor',
			iconCls:"btn-tree-team39",
			items : [searchPanel,{
				xtype: "panel",
				id:'tbar'+this.reportKey,
				items:this.toolbar,
				html : '<div id="'+this.reportKey+'"></div>'
			}],
			listeners : {
				scope:this,
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
		
		var reportKey=this.reportKey;

		searchPanel = new HT.SearchPanel({
			id : 'reportSearchPanel' + reportKey,
			layout : 'column',
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
				labelWidth:90,
				items : [{
					fieldLabel : '所属门店',
					anchor : '100%',
					xtype : 'combo',
					triggerClass : 'x-form-search-trigger',
					editable : false,
					readOnly : this.isReadOnly,
					hiddenName : "departMentName",
					onTriggerClick : function() {
						var op = this.ownerCt.ownerCt.ownerCt.ownerCt;
						var EnterpriseNameStockUpdateNew = function(obj) {
							if (null != obj.orgName && "" != obj.orgName){
								op.getCmpByName('departMentName').setValue(obj.orgName);
							}
						}
						selectShop(EnterpriseNameStockUpdateNew);
					}
				}] 
			},{
				columnWidth : .15,
				layout : 'form',
				border : false,
				labelAlign : 'right',
				labelWidth:60,
				items : [{
					name : 'customerManagerName',
					xtype : 'combo',
					fieldLabel : '客户经理',
					submitValue : true,
					triggerClass : 'x-form-search-trigger',
					readOnly : this.isReadOnly,
					editable : false,
					scope : this,
					anchor : '100%',
					onTriggerClick : function() {
						var obj = this;
						if ("" == obj.getValue()) {
							userIds = "";
						}
						new UserDialog({
							userName : obj.getValue(),
							single : true,
							title : "选择客户经理",
							callback : function(uId, uname) {
								obj.setValue(uname);
							}
						}).show();
					}
				}] 
			},{ 
				columnWidth : .15,
				layout : 'form',
				border : false,
				labelAlign : 'right',
				labelWidth:60,    
				items : [ {
					fieldLabel :'客户名称',
					name : 'customerName',
					xtype :'textfield',
					anchor : '95%'
				}] 
			},{ 
				columnWidth : .15,
				layout : 'form',
				border : false,
				labelAlign : 'right',
				labelWidth:60,    
				items : [ {
					fieldLabel :'登记日期',
					name : 'createDate_GE',
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
				style : 'padding-left:15px;',
				labelWidth:15,
				items : [{
					fieldLabel : '至',
					name : 'createDate_LE',
					xtype :'datefield',
					anchor : '90%',
					format : 'Y-m-d',
					value:endDate
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
					}
				}]
			}]
		});
		
		this.panel=Ext.getCmp('reportSearchPanel'+reportKey);
		this.url=__ctxPath + '/system/financeFormal2LoanReport.do';
		
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