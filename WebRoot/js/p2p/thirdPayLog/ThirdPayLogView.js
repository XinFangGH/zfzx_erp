/**
 * @author
 * @class BpCustLoginlogView
 * @extends Ext.Panel
 * @description [BpCustLoginlog]管理
 * @company 智维软件
 * @createtime:
 */
ThirdPayLogView = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				ThirdPayLogView.superclass.constructor.call(this, {
							id : 'ThirdPayLogView',
							title : '三方请求日志管理',
							region : 'center',
							iconCls:"menu-finance",
							layout : 'border',
							items : [this.searchPanel, this.gridPanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				// 初始化搜索条件Panel
				this.searchPanel=new HT.SearchPanel({
							layout : 'column',
							region : 'north',
							border : false,
							height : 50,
							anchor : '80%',
							layoutConfig: {
				               align:'middle'
				            },
				            bodyStyle : 'padding:10px 10px 10px 10px',
							items:[{
					     		columnWidth :.3,
								layout : 'form',
								labelWidth : 80,
								labelAlign : 'right',
								border : false,
								items : [{
											fieldLabel:'请求时间 从',
											name : 'Q_requestTime_D_LE',
											flex:1,
											align:'center',
											xtype:'datefield',
											format:'Y-m-d',
											anchor : "90%"
											}]
					     	},{
					     		columnWidth :.28,
								layout : 'form',
								labelWidth : 50,
								labelAlign : 'right',
								border : false,
								items : [{
											fieldLabel:'至',
											name : 'Q_requestTime_D_GE',
											flex:1,
											align:'center',
											xtype:'datefield',
											format:'Y-m-d',
											anchor : "90%"
											}]
					     	},{
					     		columnWidth :.28,
								layout : 'form',
								labelWidth : 80,
								labelAlign : 'right',
								border : false,
								items : [{
											fieldLabel:'订单号',
											name : 'Q_recordNumber_S_LK',
											flex:1,
											align:'center',
											xtype:'datefield',
											format:'Y-m-d',
											anchor : "90%"
											}]
					     	},{
					     		columnWidth :.1,
								layout : 'form',
								labelWidth : 50,
								labelAlign : 'right',
								border : false,
								items : [{
										text : '查询',
										xtype : 'button',
										scope : this,
										style :'margin-left:30px',
										anchor : "90%",
										iconCls : 'btn-search',
										handler : this.search
									}]
					     	},{
					     		columnWidth :.1,
								layout : 'form',
								labelWidth : 50,
								labelAlign : 'right',
								border : false,
								items : [{
										text : '重置',
										style :'margin-left:30px',
										xtype : 'button',
										scope : this,
										//width : 40,
										anchor : "90%",
										iconCls : 'btn-reset',
										handler : this.reset
									}]
					     	}]	
				});// end of searchPanel

				this.topbar = new Ext.Toolbar({ 
						items : [{
									iconCls : 'btn-add',
									text : '撤销日志',
									xtype : 'button',
									scope : this,
									handler : this.removeLog
								}]
				});
	
				this.gridPanel=new HT.GridPanel({
					region:'center',
					id:'BpCustLoginlogGrid',
					tbar:this.topbar,
					url : __ctxPath + "/thirdPay/listThirdPayLog.do",
					fields : [{
									name : 'logId',
									type : 'int'
								}
								,'thirdPayConfig'
								,'interfaceType'
								,'interfaceName'
								,'requestTime'
								,'status'
								,'recordNumber'
								,'codeMsg'
							 ], 
					columns:[
								{
									header : 'logId',
									dataIndex : 'logId',
									hidden : true
								},{
									header : '三方类型',	
									dataIndex : 'thirdPayConfig'
								},{
									header : 'interfaceType',
									hidden : true,
									dataIndex : 'interfaceType'
								},{
									header : '接口描述',	
									dataIndex : 'interfaceName'
								},{
									header : '请求时间',	
									dataIndex : 'requestTime'
								},{
									header : '状态',	
									dataIndex : 'status'
								},{
									header : '流水号',	
									dataIndex : 'recordNumber'
								},{
									header : '返回结果',	
									dataIndex : 'codeMsg'
								}
					]//end of columns
				});
				
				this.gridPanel.addListener('rowdblclick',this.rowClick);
					
			},// end of the initComponents()
			//重置查询表单
			reset : function(){
				this.searchPanel.getForm().reset();
			},
			//按条件搜索
			search : function() {
				$search({
					searchPanel:this.searchPanel,
					gridPanel:this.gridPanel
				});
			},
			removeLog : function(){
		var gridPanel=this.gridPanel;
		var selections = gridPanel.getSelectionModel().getSelections();
		var len = selections.length;
		if (len > 1) {
			Ext.ux.Toast.msg('状态', '只能选择一条记录');
			return;
		} else if (0 == len) {
			Ext.ux.Toast.msg('状态', '请选择一条记录');
			return;
		}
		Ext.Ajax.request({
			url :__ctxPath + '/thirdPay/updateThirdPayLog.do',
			params : {
				logId : selections[0].data.logId
			},
			scope:this,
			method : 'post',
			success : function(response) {
				var result = Ext.util.JSON.decode(response.responseText);
				if (result.success) {
					Ext.ux.Toast.msg('提示信息', '撤销成功');
					gridPanel.getStore().reload();
				}
			}
		})
	}
});
