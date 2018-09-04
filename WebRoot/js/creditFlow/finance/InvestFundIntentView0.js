
/**
 * @author
 * @class P2pFriendlinkView
 * @extends Ext.Panel
 * @description [P2pFriendlink]管理
 * @company 智维软件
 * @createtime:
 */
InvestFundIntentView0 = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		InvestFundIntentView0.superclass.constructor.call(this, {
					id : 'InvestFundIntentView0',
					title : '款项到期查询',
					region : 'center',
					layout : 'border',
					items : [this.searchPanel,this.gridPanel]
				});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		var summary = new Ext.ux.grid.GridSummary();
		function totalMoney(v, params, data) {
			return '总计(元)';
		}
		var objStore = new DicIndepCombo({
			editable : false,
			lazyInit : false,
			forceSelection : false,
			nodeKey : "financeType",
			// itemVale : 1149,
			// itemName : '贷款资金类型',
			isDisplayItemName:false,
			readOnly : this.isHidden1
		})
		objStore.store.reload();
		// 初始化搜索条件Panel
		this.searchPanel = new HT.SearchPanel({
					layout : 'column',
					border:false,
					region : 'north',
					//colNums : 2,
					defaults:{
						border:false,
						labelAlign :'right'
					},
					items : [{
						layout:'form',
						columnWidth:.2,
						items:[{
								fieldLabel : '即将到期',
								allowBlank : false,
								xtype : 'combo',
								hiddenName:'num',
								width:80,
								name:'num',
								editable : false,
								mode : 'local',
								triggerAction : 'all',
								valueField:'id',
								displayField:'name',
								store : new Ext.data.SimpleStore({
									fields : ["id","name"],
									data : [['1', '1天内'],['3', '3天内'],['5', '5天内'],['7', '一周内'], ['10', '10天内'], ['15', '15天内'], ['30', '一个月内']]
								}),
								value : 1
							}]
					},{
						layout:'form',
						columnWidth:.1,
						items:[{
								text : '查询',
								xtype:'button',
								scope : this,
								iconCls : 'btn-search',
								handler : this.search
							}]
					},{
						layout:'form',
						columnWidth:.1,
						items:[ {
								text : '重置',
								scope : this,
								xtype:'button',
								iconCls : 'btn-reset',
								handler : this.reset
							}]
					}]
				});// end of searchPanel


		this.gridPanel = new HT.GridPanel({
			region : 'center',
			// 使用RowActions
			// rowActions : true,
			id : 'investPersionFundIntent',
			plugins : [summary],
			url : __ctxPath + "/creditFlow/finance/list5BpFundIntent.do",
			fields : [{
						name : 'fundIntentId'
					}, {
						name : 'fundType'
					}, {
						name : 'fundTypeName'
					}, {
						name : 'incomeMoney'
					}, {
						name : 'payMoney'
					}, {
						name : 'intentDate'
					}, {
						name : 'investPersonId'
					}, {
						name : 'investPersonName'
					}, {
						name : 'remark'
					}, {
						name : 'payintentPeriod'
					}, {
						name : 'isValid'
					}, {
						name : 'factDate'
					}, {
						name : 'afterMoney'
					}, {
						name : 'notMoney'
					}, {
						name : 'flatMoney'
					}, {
						name : 'fundResource'
					}, {
						name : 'preceptId'
					}, {
						name : 'bidPlanId'
					}, {
						name : 'projectName'
					},{
						name:'orderNo'
					}],
			columns : [{
						header : 'fundIntentId',
						dataIndex : 'fundIntentId',
						hidden : true
					}, {
						header : '资金来源',
						dataIndex : 'fundResource',
						sortable : true,
						width : 100,
						scope : this,
						renderer : function(value) {
							
								if (value == '1') {
									return '线下客户'
								} else if (value == '0') {
									return '线上客户'
								}else{
									return value;
								}
						}
					},{
						header : '投资项目名称',
						dataIndex : 'projectName',
						readOnly : this.isHidden,
						sortable : true,
						width : 100,
						scope : this,
						summaryType : 'count',
						summaryRenderer : totalMoney
					}, {
						header : '投资方',
						dataIndex : 'investPersonName',
						readOnly : this.isHidden,
						sortable : true,
						width : 100,
						scope : this
					},/*
						 * ,{ header : '投资人', hidden : true, dataIndex :
						 * 'investPersonId' // renderer : investPersonName },{
						 * header : '投资人', dataIndex : 'investPersonName',
						 * editor :new Ext.form.ComboBox({ triggerClass :
						 * 'x-form-search-trigger', resizable : true,
						 * onTriggerClick : function() {
						 * selectInvestPerson(selectPersonObj);
						 * //selectPWName(selectPersonObj); }, mode : 'remote',
						 * editable : false, lazyInit : false, allowBlank :
						 * false, typeAhead : true, minChars : 1, width : 100,
						 * listWidth : 150, readOnly:this.isHidden, store : new
						 * Ext.data.JsonStore({}), triggerAction : 'all',
						 * listeners : { scope : this, 'select' :
						 * function(combo,record,index) {
						 * grid.getView().refresh(); }, 'blur' : function(f) {
						 * if (f.getValue() != null && f.getValue() != '') { } } }
						 * }), renderer : investPersonName },
						 */{
						header : '资金类型',
						dataIndex : 'fundType',
						editor : this.comboType,
						width : 107,
						renderer:function(value){
							//this.comboType
							// 
							//var objStore = objcom.getStore();
							var idx = objStore.getStore().find("dicKey", value);
							if(-1==idx){
								return value;
							}else{
								return objStore.getStore().getAt(idx).data.itemValue;
							}
						}
					}, {
						header : '计划到账日',
						dataIndex : 'intentDate',
						format : 'Y-m-d',
						fixed : true,
						width : 80,
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store) {
							var v;
							try {
								if (typeof(value) == "string") {
									v = value;
								} else {
									v = Ext.util.Format.date(value, 'Y-m-d');
								}
							} catch (err) {
								v = value;
							}
							if (record.data.isValid == 1) {
								return '<font style="font-style:italic;text-decoration: line-through;color:gray">'
										+ v + "</font>";
							} else {
								return v;
							}
						}
					}, {
						header : '计划收入金额',
						dataIndex : 'incomeMoney',
						summaryType : 'sum',
						align : 'right',
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store) {
							if (record.data.isValid == 1) {
								return '<font style="font-style:italic;text-decoration: line-through;color:gray">'
										+ Ext.util.Format.number(value,
												',000,000,000.00') + "元</font>"
							} else {
								return Ext.util.Format.number(value,
										',000,000,000.00')
										+ "元";
							}
						}
					}, {
						header : '实际到账日',
						dataIndex : 'factDate',
						format : 'Y-m-d',
						hidden : false,
						editor : this.datafield,
						fixed : true,
						width : 80,
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store) {
							var v;
							try {
								if (typeof(value) == "string") {
									v = value;
								} else {
									v = Ext.util.Format.date(value, 'Y-m-d');
								}
							} catch (err) {
								v = value;
							}
							if (record.data.isValid == 1) {
								return '<font style="font-style:italic;text-decoration: line-through;color:gray">'
										+ v + "</font>";
							} else {
								return v;
							}
						}
					}, {
						header : '已对账金额(元)',
						summaryType : 'sum',
						hidden : false,
						dataIndex : 'afterMoney',
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store) {
							if (record.data.isValid == 1) {
								return '<font style="font-style:italic;text-decoration: line-through;color:gray">'
										+ Ext.util.Format.number(value,
												',000,000,000.00') + "元</font>"
							} else {
								return Ext.util.Format.number(value,
										',000,000,000.00')
										+ "元";
							}
						}
					}, {
						header : '计划支出金额',
						dataIndex : 'payMoney',
						align : 'right',
						summaryType : 'sum',
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store) {
							if (record.data.isValid == 1) {
								return '<font style="font-style:italic;text-decoration: line-through;color:gray">'
										+ Ext.util.Format.number(value,
												',000,000,000.00') + "元</font>"
							} else {
								return Ext.util.Format.number(value,
										',000,000,000.00')
										+ "元";
							}
						}
					}, {
						header : '期数',
						dataIndex : 'payintentPeriod',
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store) {
							if (null != value) {
								if (record.data.isValid == 1) {
									return '<font style="font-style:italic;text-decoration: line-through;color:gray">'
											+ '第' + value + '期' + "</font>"
								} else {
									return '第' + value + '期';
								}
							}
						}
					}, {
						header : '备注',
						dataIndex : 'remark',
						editor : {
							xtype : 'textfield',
							readOnly : this.isHidden
						},
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store) {
							if (null != value) {
								if (record.data.isValid == 1) {
									return '<font style="font-style:italic;text-decoration: line-through;color:gray">'
											+ value + "</font>"
								} else {
									return value;
								}
							}
						}
					}, {
						header : '未对账金额(元)',
						summaryType : 'sum',
						hidden : true,
						dataIndex : 'notMoney',
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store) {
							if (record.data.isValid == 1) {
								return '<font style="font-style:italic;text-decoration: line-through;color:gray">'
										+ Ext.util.Format.number(value,
												',000,000,000.00') + "元</font>"
							} else {
								return Ext.util.Format.number(value,
										',000,000,000.00')
										+ "元";
							}
						}
					}, {
						header : '平账金额',
						width : 150,
						hidden : true,
						dataIndex : 'flatMoney',
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store) {
							if (record.data.isValid == 1) {
								return '<font style="font-style:italic;text-decoration: line-through;color:gray">'
										+ Ext.util.Format.number(value,
												',000,000,000.00') + "元</font>"
							} else {
								return Ext.util.Format.number(value,
										',000,000,000.00')
										+ "元";
							}
						}
					}]
				// end of columns
		});

		this.gridPanel.addListener('rowdblclick', this.rowClick);

	},// end of the initComponents()
	// 重置查询表单
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
					new P2pFriendlinkForm({
								id : rec.data.id
							}).show();
				});
	},
	// 还款
	repayment : function() {
				var s = this.gridPanel.getSelectionModel().getSelections();
				if (s <= 0) {
					Ext.ux.Toast.msg('操作信息','请选中一条记录');
					return false;
				}else if(s.length>1){
					Ext.ux.Toast.msg('操作信息','只能选中一条记录');
					return false;
				}else{	
					var	record=s[0];
					var fundIntentId=record.data.fundIntentId;
					new Repayment({record:record}).show();
		
				}
	
			
	},
	// 按ID删除记录
	removeRs : function(id) {
		$postDel({
					url : __ctxPath + '/p2p/multiDelP2pFriendlink.do',
					ids : id,
					grid : this.gridPanel
				});
	},
	// 把选中ID删除
	removeSelRs : function() {
		$delGridRs({
					url : __ctxPath + '/p2p/multiDelP2pFriendlink.do',
					grid : this.gridPanel,
					idName : 'id'
				});
	},
	// 编辑Rs
	editRs : function(record) {
		new P2pFriendlinkForm({
					id : record.data.id
				}).show();
	},
	// 行的Action
	onRowAction : function(grid, record, action, row, col) {
		switch (action) {
			case 'btn-del' :
				this.removeRs.call(this, record.data.id);
				break;
			case 'btn-edit' :
				this.editRs.call(this, record);
				break;
			default :
				break;
		}
	}
});
