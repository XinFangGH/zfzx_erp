/**
 * @extends Ext.Panel
 * @description 线下一级/二级业绩明细表
 */
ReportPerformanceDetail = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		ReportPerformanceDetail.superclass.constructor.call(this, {
			id : 'ReportPerformanceDetail'+this.reportKey,
			title : this.title,
			region : 'center',
			layout : 'anchor',
			iconCls : 'menu-report',
			items : [searchPanel,this.toolbar,{
				id : 'reportTemp' + this.reportKey,
				autoHeight : false,
				autoWidth : false,
				autoScroll : true,
				border : false,
				xtype : 'panel',
				height : 478
			}],
			listeners : {
				scope:this,
				'afterrender' : function(v) {
					var reportKey=this.reportKey;
					Ext.getCmp('reportSearchPanel'+reportKey).getForm().submit({
						waitMsg : '正在提交查询',
						url : __ctxPath + '/system/financeFormalLoanReport.do?reportKey='+reportKey,
						method : 'post',
						success : function(form, action) {
							var object = Ext.util.JSON.decode(action.response.responseText)
							var temp = Ext.getCmp('reportTemp' + reportKey);
							temp.body.update('<iframe src="'+ __ctxPath+ '/report/reportTemplate.jsp?'
							+ encodeURI(encodeURI(object.data))+ '" height="98%" width="98%" scrolling="auto"></iframe>');
						}
					});
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
				columnWidth :0.2,
				labelWidth : 30,
				items : [{
					xtype : 'textfield',
					fieldLabel : '姓名',
					name : 'fullname',
					anchor : '90%'
				}]
			},{
				columnWidth : 0.18,
				labelWidth : 40,
				items : [{
					xtype : 'textfield',
					fieldLabel : '推荐码',
					name : 'plainpassword',
					anchor : '100%'
				}]
			},{
				columnWidth : 0.18,
				layout : 'form',
				border : false,
				labelWidth : 50,
				labelAlign : 'right',
				items : [{
					name : 'registrationDate_GE',
					labelSeparator : '',
					xtype : 'datefield',
					format : 'Y-m-d',
					fieldLabel : '查询日期',
					anchor : '100%',
					value:firstDate
				}]
			},{
				columnWidth : 0.13,
				layout : 'form',
				border : false,
				labelWidth : 10,
				labelAlign : 'right',
				items : [{
					name : 'registrationDate_LE',
					labelSeparator : '',
					xtype : 'datefield',
					format : 'Y-m-d',
					fieldLabel : '到',
					anchor : '100%',
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
						if (Ext.getCmp('reportSearchPanel'+this.reportKey).getForm().isValid()) {
							Ext.getCmp('reportSearchPanel'+this.reportKey).getForm().submit({
								waitMsg : '正在提交查询',
								url : __ctxPath + '/system/financeFormalLoanReport.do?reportKey='+reportKey,
								method : 'post',
								success : function(form, action) {
									var object = Ext.util.JSON.decode(action.response.responseText)
									var temp = Ext.getCmp('reportTemp' + reportKey);
									temp.body.update('<iframe src="'+ __ctxPath+ '/report/reportTemplate.jsp?'
									+ encodeURI(encodeURI(object.data))+ '" height="98%" width="98%" scrolling="auto"></iframe>');
								}
							});
						}
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
						Ext.getCmp('reportSearchPanel'+this.reportKey).getForm().reset();
					}
				}]
			}]
		});
		
		var panel=Ext.getCmp('reportSearchPanel'+reportKey);
		
		pdfButton.on('click', function(xlsButton) {
			if (panel.getForm().isValid()) {
				panel.getForm().submit({
					waitMsg : '正在提交查询',
					url : __ctxPath + '/system/financeFormalLoanReport.do?reportKey='+reportKey,
					method : 'post',
					success : function(form, action) {
						var object = Ext.util.JSON.decode(action.response.responseText)
						document.location.href = __ctxPath+ '/report/reportTemplate.jsp?'
								+ encodeURI(encodeURI(object.data))+ '&reportType=pdf';
					}
				});
			}
		}, this);
			
		htmlButton.on('click', function(htmlButton) {
			if (panel.getForm().isValid()) {
				panel.getForm().submit({
					waitMsg : '正在提交查询',
					url : __ctxPath + '/system/financeFormalLoanReport.do?reportKey='+reportKey,
					method : 'post',
					success : function(form, action) {
						var object = Ext.util.JSON.decode(action.response.responseText)
						window.open(__ctxPath+ '/report/reportTemplate.jsp?'
						+ encodeURI(encodeURI(object.data))+ '&reportType=html','blank');
					}
				});
			}
		 }, this);
			
		 xlsButton.on('click', function(xlsButton) {
			if (panel.getForm().isValid()) {
				panel.getForm().submit({
					waitMsg : '正在提交查询',
					url : __ctxPath + '/system/financeFormalLoanReport.do?reportKey='+reportKey,
					method : 'post',
					success : function(form, action) {
						var object = Ext.util.JSON.decode(action.response.responseText)
						document.location.href = __ctxPath+ '/report/reportTemplate.jsp?'
								+ encodeURI(encodeURI(object.data))+ '&reportType=xls';
					}
				});
			}
		}, this);
	}
});