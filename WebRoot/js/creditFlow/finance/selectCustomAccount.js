/**
 * @author
 * @createtime
 * @class SlFundIntentForm
 * @extends Ext.Window
 * @description SlFundIntent表单
 * @company 智维软件
 */
selectCustomAccount = function(funname){
	var searchPanel = new HT.SearchPanel({
		layout : 'column',
		region : 'north',
		height : 40,
		anchor : '98%',
		keys : [{
			key : Ext.EventObject.ENTER,
			fn : this.search,
			scope : this
		}, {
			key : Ext.EventObject.ESC,
			fn : this.reset,
			scope : this
		}],
		layoutConfig : {
			align : 'middle'
		},
		items : [{
			columnWidth : 0.3,
			layout : 'form',
			border : false,
			labelWidth : 60,
			labelAlign : 'right',

			items : [{
				fieldLabel : '银行名称',
				anchor : '100%',
				name : 'bankname',
				xtype : 'textfield',
				anchor : '98%'
			}]

		}, {
			columnWidth : 0.3,
			layout : 'form',
			border : false,
			labelWidth :75,
			labelAlign : 'right',

			items : [{
				anchor : '100%',
				fieldLabel : '贷款卡卡号',
				name : 'accountnum',
				xtype : 'textfield'
			}]

		}, {
			columnWidth : 0.3,
			layout : 'form',
			border : false,
			labelWidth :75,
			labelAlign : 'right',

			items : [{
				anchor : '100%',
				fieldLabel : '开户名称',
				name : 'name',
				xtype : 'textfield'
			}]

		},{
			columnWidth : 0.24,
			layout : 'form',
			border : false,
			labelWidth : 70,
			labelAlign : 'right',
			items : [{
			anchor : '100%',
			xtype : 'combo',
			mode : 'local',
			displayField : 'name',
			valueField : 'id',
			editable : false,
			store : new Ext.data.SimpleStore({
						fields : ["name", "id"],
						data : [["企业", "0"],
								["个人", "1"]]
					}),
			triggerAction : "all",
			hiddenName : "isEnterprise",
			fieldLabel : '客户类别',
    		name : "isEnterprise"
			}]

		}

		, {
			columnWidth : .08,
			xtype : 'container',
			layout : 'form',
			defaults : {
				xtype : 'button'
			},
			style : 'padding-left:10px;',
			items : [{
				text : '查询',
				iconCls : 'btn-search',
				handler : function() {
					$search({
						searchPanel : searchPanel,
						gridPanel : gridPanel
					});
				}
			}]
		}, {
			columnWidth : .08,
			xtype : 'container',
			layout : 'form',
			defaults : {
				xtype : 'button'
			},
			style : 'padding-left:10px;',
			items : [{
				text : '重置',
				iconCls : 'reset',
				handler : function() {
					searchPanel.getForm().reset();
				}
			}]
		}

		]
	});// end of searchPanel
	var gridPanel = new HT.GridPanel({
					region : 'center',
					// 使用RowActions
					rowActions : false,
					border : false,
					id : 'selectOpAccountlGrid',
			    	//url : __ctxPath + '/credit/customer/enterprise/queryAllEnterpriseBank.do',
			    	url : __ctxPath + '/creditFlow/customer/common/listbycheckboxEnterpriseBank.do',
					fields : [
					'id'
					,'enterpriseid'
					,  'bankid'
					 ,'bankname'
					,  'accountnum'
					,'openType'
					, 'accountType'
					, 'iscredit'
					,'creditnum'
					, 'creditpsw'
					, 'remarks'
					, 'isEnterprise'
					, 'openCurrency'
					, 'name'
								],
					columns : [	
//						{
//								header : "开户类型",
//								width : 120,
//								sortable : true,
//								dataIndex : 'openType',
//								renderer:function(v){
//								if(v=="0")return '个人';
//								if(v=="1")return '公司';
//								return '';}
//							},{
//								header : "账户类型",
//								width : 100,
//								sortable : true,
//								dataIndex : 'accountType',
//								renderer:function(v){
//									if(v=="0"){
//									return "个人储蓄户";
//									}else if(v=="1"){
//									return "基本户"
//									} else if(v=="2"){return "一般户";
//									
//									}else{
//									  return '';
//									}
//                           }
//							}, 
								{
								header : "银行名称",
								width : 110,
								sortable : true,
								dataIndex : 'bankname'
							}, {
								//id : 'autobankname',
								header : "银行开户类别",
								width : 120,
								sortable : true,
								dataIndex : 'openCurrency',
								renderer:function(v){
								if(v=="0"){return '本币开户';}else if(v=="1"){return '外币开户';}else{return "";}
							}}, {
								header : "是否放款账号",
								width : 80,
								sortable : true,
								dataIndex : 'iscredit',
								renderer:function(v){
								if(v=="0"){return '是';}else if(v=="1"){return '否';}else{return "";}
							}},{
								header : "开户名称",
								width : 100,
								sortable : true,
								dataIndex : 'name'
							},{
								header : "贷款卡卡号",
								width : 100,
								sortable : true,
								dataIndex : 'accountnum'
							},{
								header : "客户类别",
								width : 100,
								sortable : true,
								dataIndex : 'isEnterprise',
								renderer:function(v){if(v=="0"){return '企业';}else if(v=="1"){return "个人";}else{return '';}}
							}],
									listeners:{
									     'rowdblclick' : function(grid,rowIndex,e) {
		
		                                                grid.getSelectionModel().each(function(rec) {
				                                       	funname(rec.data.accountnum,rec.data.bankname,rec.data.name);
			                      	                    });
		                                         selectAccountlWindow.destroy();
			}
		}
						// end of columns
				});
	var selectAccountlWindow = new Ext.Window({
		width: (screen.width-180)*0.7,
		title : '对方账户列表',
		height : 600 ,//高度自by chencc
		//autoHeight :true,//高度自动 by chencc
		collapsible : true,
		region : 'center',
		layout : 'border',
		buttonAlign : 'center',
		border : false,
		modal : true,
		resizable : false,
		items : [searchPanel, gridPanel]
	});
selectAccountlWindow.show();
}