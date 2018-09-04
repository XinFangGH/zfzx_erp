/**
 * @extends Ext.Panel
 * @description 线下推荐明细表(一级、二级)
 */
Recommend = Ext.extend(Ext.Panel, {
	panel:null,
	url:'',
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		Recommend.superclass.constructor.call(this, {
			id : 'Recommend'+this.reportKey,
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
				labelWidth:40,    
				items : [ {
					fieldLabel : '姓名',
					name : 'userName',
					xtype :'textfield',
					anchor : '100%'
				}] 
			},{ 
				columnWidth : .2,
				layout : 'form',
				border : false,
				labelAlign : 'right',
				labelWidth:40,    
				items : [ {
					fieldLabel : '推荐码',
					name : 'plainpassword',
					xtype :'textfield',
					anchor : '100%'
				}] 
			},{ 
				columnWidth : .2,
				layout : 'form',
				border : false,
				labelAlign : 'right',
				labelWidth:60,    
				items : [ {
					fieldLabel : '查询日期',
					name : 'investDateGE',
					xtype :'datefield',
					anchor : '100%',
					format : 'Y-m-d',
					value:firstDate
				}] 
			},{ 
				columnWidth : .18,
				layout : 'form',
				border : false,
				labelAlign : 'right',
				style : 'padding-left:15px;',
				labelWidth:20,
				items : [{
					fieldLabel : '至',
					name : 'investDateLE',
					xtype :'datefield',
					anchor : '100%',
					format : 'Y-m-d',
					value:firstDate
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
							var investDateGE=this.getCmpByName('investDateGE');
							var investDateLE=this.getCmpByName('investDateLE');
							if(""==investDateGE.getValue() || ""==investDateLE.getValue()){
								Ext.ux.Toast.msg('操作信息', '日期条件不能为空,请重新填写!');
								return false;
							}
							commomClick(this.panel,this.url,reportKey,'');
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
						this.panel.getForm().reset();
						var investDateGE=this.getCmpByName('investDateGE');
						var investDateLE=this.getCmpByName('investDateLE');
						investDateGE.setValue(firstDate);
						investDateLE.setValue(firstDate);
					}
				}]
			}]
		});
		this.panel=Ext.getCmp('reportSearchPanel'+reportKey);
		this.url=__ctxPath + '/system/recommendOfflineRecommend.do?reportKey='+this.reportKey;
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