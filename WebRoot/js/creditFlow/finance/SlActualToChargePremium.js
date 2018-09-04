/**
 * @author:
 * @class SlActualToChargePremium
 * @extends Ext.Panel
 * @description [SlActualToChargePremium]管理
 * @company 广州宏天软件有限公司
 * @createtime:
 */
SlActualToChargePremium = Ext.extend(Ext.Panel, {

	isUnLoadData : false,
	// 构造函数
	constructor : function(_cfg) {
//alert(111);
		if (typeof(_cfg.projId) != "undefined") {
			this.projectId = _cfg.projId;
		}
		if (typeof(_cfg.businessType) != "undefined") {
			this.businessType = _cfg.businessType;
		}
		if (typeof(_cfg.isUnLoadData) != "undefined") {
			this.isUnLoadData = _cfg.isUnLoadData;
		}
		if (typeof(_cfg.object) != "undefined") {
			this.object = _cfg.object;
		}
		if (typeof(_cfg.isHidden) != "undefined") {
			this.isHidden = _cfg.isHidden;
		}
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		SlActualToChargePremium.superclass.constructor.call(this, {
			layout : 'anchor',
			anchor : '100%',
			items : [{
				xtype : 'panel',
				border : false,
				bodyStyle : 'margin-bottom:5px',
				html : '<br/><B><font class="x-myZW-fieldset-title">【业务收费】:</font></B>'
			}, this.gridPanel]
		});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		var summary = new Ext.ux.grid.GridSummary();
		function totalMoney(v, params, data) {
			return '总计(元)';
		}
		this.topbar = new Ext.Toolbar({
			items : [{
				text : '刷新应收保费',
				iconCls : 'btn-reset',
				scope : this,
				hidden : this.isHidden,
				handler : this.createRs
			}]
		});
							
		this.gridPanel = new HT.EditorGridPanel({
			border : false,
			clicksToEdit : 1,
			hiddenCm :this.isHidden,
			isShowTbar : this.isHidden ? false : true,
			tbar : this.isHidden ? null : this.topbar,
			showPaging : false,
			autoHeight : true,
			region : 'center',
			plugins: [summary],
			// tbar : this.topbar,
			// 使用RowActions
			rowActions : true,
			id : 'SlActualToChargePremium',
			url : __ctxPath
					+ "/creditFlow/finance/listbyprojectSlActualToCharge.do?projectId="
					+ this.projectId + "&isUnLoadData=" + this.isUnLoadData
					+ "&businessType=" + this.businessType
					+ "&chargeKey='premiumMoney','earnestMoney'",
			fields : [{
						name : 'actualChargeId'
					}, {
						name : 'planChargeId'
					}, {
						name : 'typeName'
					}, {
						name : 'planCharges'
					}, {
						name : 'chargeStandard'
					}, {
						name : 'payMoney'
					}, {
						name : 'incomeMoney'
					}, {
						name : 'intentDate'
					}, {
						name : 'isOverdue'
					},  {
						name : 'remark'
					}],
			columns : [{
						header : 'actualChargeId',
						dataIndex : 'actualChargeId',
						hidden : true
					},{
						header : "收入类型",
						width : 110,
						dataIndex : 'typeName',
						summaryType: 'count',
						summaryRenderer : totalMoney,
						renderer : function(value, metaData, record, rowIndex, colIndex, store) {
							var flag1 = 0; // incomeMoney
							if (record.data.payMoney != 0) { // payMoney
								flag1 = 1;
							}
							if (record.data.notMoney == 0) {
								return '<font style="color:gray">'+ value+ "</font>";
							}
							if (record.data.isOverdue == "是" && flag1 != 1) {
								return '<font style="color:red">'+ value+ "</font>";
							}
							return value;
						}
					}, /*
						 * { header : "系统自动计算保费", width : 120, dataIndex :
						 * 'computeMoney', renderer : function(v) { if (v ==
						 * null || v == '') { return ''; } else { return v +
						 * '元'; } } },
						 */{
						header : "金额",
						width : 80,
						dataIndex : 'incomeMoney',
						align : 'right',
						summaryType: 'sum',
						editor : {
							xtype : 'numberfield',
							readOnly : this.isHidden
						},
						renderer : function(v) {
							if (v == null || v == '') {
								return '';
							} else {
								return v + '元';
							}
						}
					}, {
						header : "备注",
						width : 280,
						dataIndex : 'remark',
						align : "center",
						editor : new Ext.form.TextField({
							readOnly : this.isHidden
						})
					}, new Ext.ux.grid.RowActions({
							header : '管理',
							width : 100,
							hidden : true,
							actions : [{
								iconCls : 'btn-del',
								qtip : '删除',
								style : 'margin:0 3px 0 3px'
							}, {
								iconCls : 'btn-edit',
								qtip : '编辑',
								style : 'margin:0 3px 0 3px'
							}],
							listeners : {
								scope : this,
								'action' : this.onRowAction
							}
						})],
			listeners : {
				scope : this,
				afteredit : function(e) {
					var value = e.value;
					var actualChargeId = e.record.data['actualChargeId'];
					if (e.originalValue != e.value) {// 编辑行数据发生改变
						if (e.field == 'incomeMoney') {
							args = {
								'slActualToCharge.actualChargeId' : actualChargeId,
								'slActualToCharge.incomeMoney' : e.value,
								oprationType : 'manually',
								businessType :this.businessType,
								'projectId' : this.projId
							}
						}
						if (e.field == 'remark') {
							args = {
								'slActualToCharge.actualChargeId' : actualChargeId,
								'slActualToCharge.remark' : e.value,
								oprationType : 'manually',
								businessType :this.businessType,
								'projectId' : this.projId
							}
						}

						Ext.Ajax.request({
							url : __ctxPath + '/creditFlow/finance/computeMoneySlActualToCharge.do',
							method : 'POST',
							scope : this,
							success : function(response, request) {
								this.gridPanel.getStore().reload();
								e.record.commit();
							},
							failure : function(response) {
								Ext.ux.Toast.msg('状态', '操作失败，请重试');
							},
							params : args
						})
					}
				},
				'cellclick' : function(grid, rowIndex, columnIndex, e) {},
				rowdblclick : function(grid, rowIndex, e) {}
			}
		});// end of columns
		this.gridPanel.addListener('rowdblclick', this.rowClick);

	},// end of the initComponents()
	// GridPanel行点击处理事件
	rowClick : function(grid, rowindex, e) {
		grid.getSelectionModel().each(function(rec) {
			/*
			 * new SlMortgageForm({ mortId : rec.data.mortId }).show();
			 */
		});
	},
	// 创建记录
	createRs : function() {
		var customerEarnestmoneyScale = this.object.getCmpByName("gLGuaranteeloanProject.customerEarnestmoneyScale").getValue();
		var premiumRate = this.object.getCmpByName("gLGuaranteeloanProject.premiumRate").getValue();
		var projectMoney = this.object.getCmpByName("gLGuaranteeloanProject.projectMoney").getValue();
		if (projectMoney == null || projectMoney == '') {
			Ext.ux.Toast.msg('提示信息', '请先输入贷款金额后再刷新');
			return;
		}
		if (premiumRate == null || premiumRate == '') {
			Ext.ux.Toast.msg('提示信息', '请先输入保费费率后再刷新');
			return;
		}
		if (customerEarnestmoneyScale == null || customerEarnestmoneyScale == '') {
			Ext.ux.Toast.msg('提示信息', '请先输入保证金费率后再刷新');
			return;
		}
		Ext.Ajax.request({
			url : __ctxPath + '/creditFlow/finance/computeMoneySlActualToCharge.do',
			method : 'POST',
			scope : this,
			success : function(response, request) {
				// e.record.commit();
				this.gridPanel.getStore().reload();
			},
			failure : function(response) {
				Ext.ux.Toast.msg('状态', '操作失败，请重试');
			},
			params : {
				businessType : this.businessType,
				projectId : this.projectId,
				computePremiumMoney : premiumRate * projectMoney / 100,
				computeEarnestMoney : customerEarnestmoneyScale * projectMoney / 100,
				oprationType : 'system',
				chargeKey : "'premiumMoney','earnestMoney'",
				isCheck : (this.isCheck==0?0:1)
			}
		});
	},

	// 行的Action
	onRowAction : function(grid, record, action, row, col) {
		switch (action) {
			case 'btn-del' :
				this.removeRs.call(this, record.data.mortId);
				break;
			case 'btn-edit' :
				this.editRs.call(this, record);
				break;
			default :
				break;
		}
	}/*,
	getGridDate : function() {
	var vRecords = this.gridPanel.getStore().getRange(0,
			this.gridPanel.getStore().getCount()); // 得到修改的数据（记录对象）
	var vCount = vRecords.length; // 得到记录长度
	var vDatas = '';
	if (vCount > 0) {
		// begin 将记录对象转换为字符串（json格式的字符串）
		for ( var i = 0; i < vCount; i++) {
			if(vRecords[i].data.planChargeId !=null && vRecords[i].data.planChargeId !=""){
			 if(vRecords[i].data.actualChargeId == null || vRecords[i].data.actualChargeId == "")  {
			   st={"planChargeId":vRecords[i].data.planChargeId,"chargeStandard":vRecords[i].data.chargeStandard,"incomeMoney":vRecords[i].data.incomeMoney,"payMoney":vRecords[i].data.payMoney,"intentDate":vRecords[i].data.intentDate,"remark":vRecords[i].data.remark};
			 }else{
			    st={"actualChargeId":vRecords[i].data.actualChargeId,"planChargeId":vRecords[i].data.planChargeId,"chargeStandard":vRecords[i].data.chargeStandard,"incomeMoney":vRecords[i].data.incomeMoney,"payMoney":vRecords[i].data.payMoney,"intentDate":vRecords[i].data.intentDate,"remark":vRecords[i].data.remark};
			 }
			 vDatas += Ext.util.JSON
						.encode(st) + '@';
		}
		}
		vDatas = vDatas.substr(0, vDatas.length - 1);
	}
	//Ext.getCmp('fundIntentJsonData2').setValue(vDatas);
//						this.gridPanel.getColumnsgetEditor().getCmpByName('fundIntentJsonData').setValue(vDatas);
//						alert(this.gridPanel.getEditor().getCmpByName('fundIntentJsonData').getValue());
	return vDatas;
}*/
});
