/**
 * @author
 * @class SlFundQlideView
 * @extends Ext.Panel
 * @description [SlFundQlide]管理
 * @company 智维软件
 * @createtime:
 */
CashQlideView = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造11112
		CashQlideView.superclass.constructor.call(this, {
					id : 'CashQlideView_'+this.type,
					title : '现金流水录入',
					region : 'center',
					layout : 'border',
					iconCls : 'btn-team43',
					items : [this.searchPanel, this.gridPanel]
				});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		var summary = new Ext.ux.grid.GridSummary();
				function totalMoney(v, params, data) {
							return '总计(元)';
						}
		
		// 初始化搜索条件Panel
		var isShow=false;
		if(RoleType=="control"){
		  isShow=true;
		}
		this.searchPanel = new HT.SearchPanel({
			layout : 'column',
			region : 'north',
			height : 40,
			anchor : '70%',
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
				columnWidth : 0.15,
				layout : 'form',
				border : false,
				labelWidth : 60,
				labelAlign : 'right',

				items : [
							{
					name : 'Q_transactionType_D_LE',
							xtype : 'textfield',
							fieldLabel : '用途摘要',
							anchor : '98%'
						}, {
							xtype : 'combo',
							mode : 'local',
							displayField : 'name',
							valueField : 'id',
							editable : false,
							store : new Ext.data.SimpleStore({
										fields : ["name", "id"],
										data : [["支出", "0"], ["收入", "1"]]

									}),
							triggerAction : "all",
							hiddenName : "Q_fundType_SN_EQ",
							fieldLabel : '交易类型',
							name : 'Q_fundType_SN_EQ',
							anchor : '98%'

						}]

			}, {
				columnWidth : 0.18,
				layout : 'form',
				border : false,
				labelWidth : 80,
				labelAlign : 'right',
				items : [{
							name : 'Q_factDate_D_GE',
							xtype : 'datefield',
							format : 'Y-m-d',
							fieldLabel : '到账日期：从',
							labelSeparator : '',
							anchor : '99%'
						}, {
							name : 'Q_dialMoney_BD_GE',
							xtype : 'textfield',
							width : 97,
							fieldLabel : '交易金额：从',
							labelSeparator : '',
							anchor : '98%'

						}]

			}, {
				columnWidth : 0.13,
				layout : 'form',
				border : false,
				labelWidth : 10,
				labelAlign : 'right',
				items : [{
							name : 'Q_factDate_D_LE',
							labelSeparator : '',
							xtype : 'datefield',
							format : 'Y-m-d',
							fieldLabel : '到',
							anchor : '88%'
						}, {
							name : 'Q_dialMoney_BD_LE',
							labelSeparator : '',
							xtype : 'textfield',
							width : 97,
							fieldLabel : '到',
							anchor : '88%'

						}]

			}, {
				columnWidth : 0.18,
				layout : 'form',
				border : false,
				labelWidth : 85,
				labelAlign : 'right', 
				items : [
					     
					{
						labelWidth:85,   
						fieldLabel : '流水状态',
						name : 'Q_notMoney_BD_LE',
						width:100,
						flex : 1,
						xtype:'combo',
			             mode : 'local',
		               displayField : 'name',
		               valueField : 'id',
		                 store : new Ext.data.SimpleStore({
				        fields : ["name", "id"],
			            data : [["不限",""],
			                    ["已对清流水", "0"],
						     	["未对清流水", "1"]]
		              	}),
			             triggerAction : "all",
		                hiddenName:"Q_notMoney_BD_LE",
		                anchor : '96%',
		                 value : 1
					},
					{
						labelWidth:85,   
						fieldLabel : '是否项目相关',
						name : 'Q_isProject_BD_LE',
						width:100,
						flex : 1,
						xtype:'combo',
			             mode : 'local',
		               displayField : 'name',
		               valueField : 'id',
		                 store : new Ext.data.SimpleStore({
				        fields : ["name", "id"],
			            data : [["不限",""],
			                    ["是", "是"],
						     	["否", "否"]]
		              	}),
			             triggerAction : "all",
		                hiddenName:"Q_isProject_BD_LE",
		                anchor : '96%'
					}]
			}
        
			,isShow?{
					columnWidth : 0.16,
					labelWidth : 80,
					layout : 'form',
					border : false,
					items : [
					{
							xtype : "combo",
							anchor : "100%",
							fieldLabel : '所属分公司',
							hiddenName : "companyId",
							displayField : 'companyName',
							valueField : 'companyId',
							triggerAction : 'all',
							store : new Ext.data.SimpleStore({
								autoLoad : true,
								url : __ctxPath+'/system/getControlNameOrganization.do',
								fields : ['companyId', 'companyName']
							})
					}
				]}:{columnWidth : 0.001,
				border : false}, {
				columnWidth : .08,
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
							handler : this.search
						}, {
							style : 'padding-top:3px;',
							text : '重置',
							scope : this,
							iconCls : 'btn-reset',
							handler : this.reset
						}]
			}

			]
				// ,
				// buttons:[
				// {
				// text:'查询',
				// scope:this,
				// iconCls:'btn-search',
				// handler:this.search
				// },{
				// text:'重置',
				// scope:this,
				// iconCls:'btn-reset',
				// handler:this.reset
				// }
				// ]
			});// end of searchPanel

		this.topbar = new Ext.Toolbar({
					items : [{
								iconCls : 'addIcon',
								text : '批量添加',
								xtype : 'button',
								scope : this,
								hidden : isGranted('_createall_b_'+this.type)?false:true,
								handler : this.savealot
							},new Ext.Toolbar.Separator({
								hidden : isGranted('_createall_b_'+this.type)?false:true
							}),{
								iconCls : 'btn-add',
								text : '添加',
								xtype : 'button',
								scope : this,
								hidden : isGranted('_createxjls_b_'+this.type)?false:true,
								handler : this.createRs
							},new Ext.Toolbar.Separator({
								hidden : isGranted('_createxjls_b_'+this.type)?false:true
							}),{
								iconCls : 'btn-detail',
								text : '查看',
								xtype : 'button',
								scope : this,
								hidden : isGranted('_seexjls_b_'+this.type)?false:true,
								handler : this.seeRs
							} ,new Ext.Toolbar.Separator({
								hidden : (isGranted('_seexjls_b_'+this.type)?false:true)
							}),{
								iconCls : 'btn-edit',
								text : '编辑',
								xtype : 'button',
								scope : this,
								hidden : isGranted('_editxjls_b_'+this.type)?false:true,
								handler : this.editRs
							} ,new Ext.Toolbar.Separator({
								hidden : (isGranted('_editxjls_b_'+this.type)?false:true)||(isGranted('_removexjls_b_'+this.type)?false:true)
							}),{
								iconCls : 'btn-del',
								text : '删除',
								xtype : 'button',
								scope : this,
								hidden : isGranted('_removexjls_b_'+this.type)?false:true,
								handler : this.removeSelRs
							}]
				});

		this.gridPanel = new HT.GridPanel({
			region : 'center',
			tbar : this.topbar,
		    plugins: [summary],
			// 使用RowActions
			rowActions : false,
			id : 'CashQlideViewGrid_'+this.type,
			url : __ctxPath + "/creditFlow/finance/listcashSlFundQlide.do",
			fields : [{
						name : 'fundQlideId',
						type : 'int'
					}, 'dialMoney', 'afterMoney',
					'notMoney', 'factDate', 'opAccount', 'fundType',
					'currency', 'transactionType',
					'incomeMoney', 'payMoney','isProject','finalMoney','frozenfinalMoney','surplusfinalMoney','bankTransactionTypeName','orgName'

			],
			columns : [{
						header : 'fundQlideId',
						align:'center',
						dataIndex : 'fundQlideId',
						hidden : true
					},{
						header : "所属分公司",
						sortable : true,
						align:'center',
						hidden:RoleType=="control"?false:true,
						dataIndex : 'orgName'
					},
					 {
						header : '币种',
						align:'center',
						dataIndex : 'currency',
						width:50,
						summaryRenderer: totalMoney
						
					}, {
						header : '到账时间',
						align:'center',
						dataIndex : 'factDate',
						sortable : true,
						width:60
					}, {
						header : '收入金额(元)',
						dataIndex : 'incomeMoney',
						align : 'right',
						width:70,
						summaryType: 'sum',
						renderer : function(v) {
							if (v != null) {
								return Ext.util.Format.number(v,
										',000,000,000.00')
							} else {
								return v
							}

						}
					}, {
						header : '支出金额(元)',
						dataIndex : 'payMoney',
						align : 'right',
						width:70,
						summaryType: 'sum',
						renderer : function(v) {
							if (v != null) {
								return Ext.util.Format.number(v,
										',000,000,000.00')
								return v
							} else {
								return v
							}

						}
				 },{
						header : '期末总余额(元)',	
						dataIndex : 'finalMoney',
						align : 'right',
						width :85,
						renderer:function(v){
							if(v !=null){
								return Ext.util.Format.number(v,',000,000,000.00')
							return v
								}else{
								return v}
								
                      	}
				}
//				,{
//						header : '可用期末余额',	
//						dataIndex : 'surplusfinalMoney',
//						align : 'right',
//						width :85,
//						renderer:function(v){
//							if(v !=null){
//								return Ext.util.Format.number(v,',000,000,000.00')+"元"
//							return v+"元"
//								}else{
//								return v}
//								
//                      	}
//				},{
//						header : '冻结期末余额',	
//						dataIndex : 'frozenfinalMoney',
//						align : 'right',
//						width :85,
//						renderer:function(v){
//							if(v !=null){
//								return Ext.util.Format.number(v,',000,000,000.00')+"元"
//							return v+"元"
//								}else{
//								return v}
//								
//                      	}
//				}

					, {
						header : '未对账金额(元)',
						dataIndex : 'notMoney',
						align : 'right',
						width:70,
						summaryType: 'sum',
						renderer : function(v) {
							if (v != null) {
								return Ext.util.Format.number(v,
										',000,000,000.00')
							} else {
								return v
							}

						}

					}, {
						header : '已对账金额(元)',
						dataIndex : 'afterMoney',
						align : 'right',
						width:70,
						summaryType: 'sum',
						renderer : function(v) {
							if (v != null) {
								return Ext.util.Format.number(v,
										',000,000,000.00')
							} else {
								return v
							}

						}

					}, {
						header : "交易类型",
						align:'center',
						dataIndex : "bankTransactionTypeName",
						width:60

					}, {
						header : "是否项目相关",
						align:'center',
						dataIndex : "isProject",
						align : 'center',
						width:60

					}
			, {
						header : '用途摘要',
						align:'left',
						dataIndex : 'transactionType',
						width:60
					}

					]
				// end of columns
			});

		// this.gridPanel.addListener('rowdblclick',this.rowClick);

	},// end of the initComponents()
	// 重置查询表单


	upload : function() {
		new SlFundQlideView({
					gridPanel : this.gridPanel
				}).show();

	},
	reset : function() {
		this.searchPanel.getForm().reset();
	},
	// 按条件搜索
	search : function() {
		$search({
					searchPanel : this.searchPanel,
					gridPanel : this.gridPanel
				});
	},
	// GridPanel行点击处理事件
	rowClick : function(grid, rowindex, e) {
		grid.getSelectionModel().each(function(rec) {
					new SlFundQlideForm({
								fundQlideId : rec.data.fundQlideId
							}).show();
				});
	},
	// 创建记录
	createRs : function() {
		new CashQlideForm().show();
	},
	// 按ID删除记录
	removeRs : function(id) {
		$postDel({
					url : __ctxPath + '/creditFlow/finance/multiDelSlFundQlide.do',
					ids : id,
					grid : this.gridPanel,
					error : '确定要删除？'
				});
	},
	// 把选中ID删除
	removeSelRs : function() {
		$delGridRs({
					url : __ctxPath + '/creditFlow/finance/multiDelSlFundQlide.do',
					grid : this.gridPanel,
					idName : 'fundQlideId',
					error : '确定要删除？'
				});
	},
	// 查看Rs
	seeRs : function(record) {
			var s = this.gridPanel.getSelectionModel().getSelections();
				if (s <= 0) {
					Ext.ux.Toast.msg('操作信息','请选中任何一条记录');
					return false;
				}else if(s.length>1){
					Ext.ux.Toast.msg('操作信息','只能选中一条记录');
					return false;
				}else{	
					  record=s[0];
					  new CashQlideForm({
								fundQlideId : record.data.fundQlideId,
								ischeck :true,
								isAllReadOnly:true
							}).show();
					
				}
	},
	// 编辑Rs
	editRs : function(record) {
			var s = this.gridPanel.getSelectionModel().getSelections();
				if (s <= 0) {
					Ext.ux.Toast.msg('操作信息','请选中任何一条记录');
					return false;
				}else if(s.length>1){
					Ext.ux.Toast.msg('操作信息','只能选中一条记录');
					return false;
				}else{	
					record=s[0];
					if(record.data.afterMoney ==0){
					  new CashQlideForm({
								fundQlideId : record.data.fundQlideId,
								ischeck :false,
								isAllReadOnly:false
							}).show();
					}else{
						Ext.ux.Toast.msg('操作信息','此流水已经对过账！');
					  new CashQlideForm({
								fundQlideId : record.data.fundQlideId,
								ischeck :true,
								isAllReadOnly:false
							}).show();
					}
				}
	},
	savealot :function(){
	   new CashQlideAlotAdd().show();
	
	},
	// 行的Action
	onRowAction : function(grid, record, action, row, col) {
		switch (action) {
			case 'btn-del' :
				this.removeRs.call(this, record.data.fundQlideId);
				break;
			case 'btn-edit' :
				this.editRs.call(this, record);
				break;
			default :
				break;
		}
	}
});
var selectAccountkLinkMan = function(a) {
	Ext.getCmp('Q_dialAccounts_S_EQ').setValue(a);
}
var selectAccountkLinkMan1 = function(a) {
	Ext.getCmp('Q_recAccounts_S_EQ').setValue(a);
}