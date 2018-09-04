/**
 * 代偿台账表单
 * @class compensatoryFiance
 * @extends Ext.Panel
 */
compensatoryFiances = Ext.extend(Ext.Panel, {
	layout:"form",
	autoHeight : true,
	isRead : false,
	isDiligenceReadOnly : false,
	labelAlign : 'right',
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.applyIf(this, _cfg);
		this.initComponents();
		var leftlabel = 105;
			compensatoryFiances.superclass.constructor.call(this, {
				layout : "column",
				border : false,
				scope : this,
				defaults : {
					anchor : '100%',
					columnWidth : 1,
					isFormField : true,
					labelAlign : 'right',
					labelWidth : leftlabel
				},
				items : [{
							xtype : "hidden",
							name : "plBidCompensatory.perId"
						},{
							columnWidth : .6, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth :leftlabel,
							labelAlign : 'right',
							items : [{
										xtype : 'textfield',
										fieldLabel : '代偿项目名称',
										allowBlank : false,
										name : 'plBidCompensatory.bidPlanname',
										anchor : '100%',
										readOnly : true,
										listeners : {
											'afterrender':function(com){
											    com.clearInvalid();
											}
										}
												
									}]
						},{
							columnWidth : .4, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth :leftlabel,
							labelAlign : 'right',
							items : [{
										xtype : 'textfield',
										fieldLabel : '代偿项目编号',
										allowBlank : false,
										name : 'plBidCompensatory.bidPlanNumber',
										anchor : '100%',
										readOnly : true,
										listeners : {
											'afterrender':function(com){
											    com.clearInvalid();
											}
										}
												
									}]
						},{
							columnWidth : .25, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth :leftlabel,
							labelAlign : 'right',
							items : [{
								xtype : 'numberfield',
								fieldLabel : '未偿付罚息总额',
								name : 'plBidCompensatory.unBackPunishMoney',
								allowBlank : false,
								anchor : '100%',
								readOnly : true
							}]
						},{
							columnWidth : .05, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 20,
							items : [{
										fieldLabel : "元",
										labelSeparator : '',
										anchor : "100%"
									}]
						}, {
							columnWidth : .25, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth :leftlabel,
							labelAlign : 'right',
							items : [{
								fieldLabel : "未偿付代偿金额",
								xtype : "numberfield",
								name : 'plBidCompensatory.unBackCompensatoryMoney',
								readOnly : true,
								editable :false,
								anchor : "100%"
							}]
						},{
							columnWidth : .05, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 20,
							items : [{
										fieldLabel : "元",
										labelSeparator : '',
										anchor : "100%"
									}]
					 }, {
							columnWidth : .35, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth :leftlabel,
							labelAlign : 'right',
							items : [{
								fieldLabel : "已平账金额",
								xtype : "numberfield",
								name : 'plBidCompensatory.plateMoney',
								readOnly : true,
								editable :false,
								anchor : "100%"
							}]
						},{
							columnWidth : .05, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 20,
							items : [{
										fieldLabel : "元",
										labelSeparator : '',
										anchor : "100%"
									}]
					 },{
						columnWidth : .6,
						layout : 'form',
						labelWidth :leftlabel,
						labelAlign : 'right',
						items : [{
									xtype : 'numberfield',
									fieldLabel : '累计未偿付总额',
									readOnly : true,
									name : 'plBidCompensatory.totalMoney',
									scope:this,
									anchor : "100%"
								}]
					},{
						columnWidth : .05, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 20,
						items : [{
									fieldLabel : "元",
									labelSeparator : '',
									anchor : "100%"
								}]
				   }]
		});
	},
	initComponents : function() {
	},
	cc : function() {
	}
});

/**
 * 回款登记表
 * @class compensatoryFiance
 * @extends Ext.Panel
 */
compensatoryRecordForm = Ext.extend(Ext.Panel, {
	layout:"form",
	autoHeight : true,
	isRead : false,
	isDiligenceReadOnly : false,
	labelAlign : 'right',
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.applyIf(this, _cfg);
		this.initComponents();
		var leftlabel = 105;
			compensatoryRecordForm.superclass.constructor.call(this, {
				layout : "column",
				border : false,
				scope : this,
				defaults : {
					anchor : '100%',
					columnWidth : 1,
					isFormField : true,
					labelAlign : 'right',
					labelWidth : leftlabel
				},
				items : [{
							xtype : "hidden",
							name : "plBidCompensatoryFlow.compensatoryId",
							value:this.compensatoryId
						},{
							columnWidth : .25, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth :leftlabel,
							labelAlign : 'right',
							items : [{
								xtype : 'numberfield',
								fieldLabel : '罚息金额',
								name : 'plBidCompensatoryFlow.backPunishMoney',
								allowBlank : false,
								anchor : '100%',
								readOnly : this.isRead
							}]
						},{
							columnWidth : .05, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 20,
							items : [{
										fieldLabel : "元",
										labelSeparator : '',
										anchor : "100%"
									}]
						}, {
							columnWidth : .25, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth :leftlabel,
							labelAlign : 'right',
							items : [{
								fieldLabel : "代偿回款金额",
								xtype : "numberfield",
								name : 'plBidCompensatoryFlow.backCompensatoryMoney',
								readOnly : this.isRead,
								anchor : "100%"
							}]
						},{
							columnWidth : .05, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 20,
							items : [{
										fieldLabel : "元",
										labelSeparator : '',
										anchor : "100%"
									}]
					 },{
						columnWidth : .35,
						layout : 'form',
						labelWidth :leftlabel,
						labelAlign : 'right',
						items : [{
									xtype : 'textfield',
									fieldLabel : '平账金额',
									readOnly : this.isRead,
									name : 'plBidCompensatoryFlow.flateMoney',
									scope:this,
									anchor : "100%"
								}, {
									name : 'plBidCompensatoryFlow.backType',
									value:1,
									xtype : 'hidden'
								}]
					},{
						columnWidth : .05, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 20,
						items : [{
									fieldLabel : "元",
									labelSeparator : '',
									anchor : "100%"
								}]
				   },{
							columnWidth : .25, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth :leftlabel,
							labelAlign : 'right',
							items : [{
								xtype : 'datefield',
								fieldLabel : '到账日期',
								name : 'plBidCompensatoryFlow.backDate',
								allowBlank : false,
								format : 'Y-m-d',
								anchor : '100%'
							}]
						}]
		});
	},
	initComponents : function() {
	},
	cc : function() {
	}
});


CompensatoryFlow = Ext.extend(Ext.Panel, {
	layout : 'fit',
	anchor : '100%',
	name : "gudong_info",
	projectId : null,
	isHidden : false,
	isHiddenAddBtn : true,
	isHiddenDelBtn : true,
	constructor : function(_cfg) {
		if (typeof(_cfg.projectId) != "undefined") {
			this.projectId = _cfg.projectId;
		}
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		CompensatoryFlow.superclass.constructor.call(this, {
					items : [this.grid_CompensatoryFlow]
				})
	},
	initUIComponents : function() {
		var summary = new Ext.ux.grid.GridSummary();
		function totalMoney(v, params, data) {
			return '总计(元)';
		}
		if (this.isHiddenDelBtn == true && this.isHiddenAddBtn == true) {
			this.isHidden = true;
		}
		this.datefield = new Ext.form.DateField({
					format : 'Y-m-d',
					readOnly : this.isHidden,
					allowBlank : false
				})
	

		this.grid_CompensatoryFlow = new HT.GridPanel({
			border : false,
			autoHeight : this.autoHeight,
			clicksToEdit : 1,
			stripeRows : true,
			plugins : [summary],
			enableDragDrop : false,
			viewConfig : {
				forceFit : true
			},
			showPaging:this.showPaging,
			store : new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
					url : __ctxPath
							+ '/compensatory/listPlBidCompensatoryFlow.do?compensatoryId='
							+ this.compensatoryId,
					method : "POST"
				}),
				reader : new Ext.data.JsonReader({
							fields : Ext.data.Record.create([{
										name : 'compensatoryId'
									}, {
										name : 'backPunishMoney'
									}, {
										name : 'backCompensatoryMoney'
									}, {
										name : 'flateMoney'
									}, {
										name : 'backType'
									}, {
										name : 'requestNo'
									}, {
										name : 'backStatus'
									}, {
										name : 'backDate'
									}, {
										name : 'id'
									}

							]),
							root : 'result'
						})
			}),
			columns : [ {
						header : '回款类型',
						dataIndex : 'backType',
						sortable : true,
						width : 170,
						renderer : function(v) {
							if(v==1){
								return  "线下回款"
							}else if(v==0){
								return "线上回款"
							}else{
								return ""
							}
							
						}
					},{
						header : '回款流水号',
						dataIndex : 'requestNo',
						sortable : true,
						width : 300,
						align : "center",
						renderer : function(data, metadata, record, rowIndex,
								columnIndex, store) {
							metadata.attr = ' ext:qtip="' + data + '"';
							return data;
						}
					} ,{
						header : '偿付罚息金额',
						dataIndex : 'backPunishMoney',
						sortable : true,
						align : 'right',
						summaryType : 'sum',
						width : 127,
						renderer : function(value, metaData, record, rowIndex,colIndex, store) {
							return Ext.util.Format.number(value, '0,000.00') + "元"
						}

					},{
						header : '偿付代偿金额',
						dataIndex : 'backCompensatoryMoney',
						sortable : true,
						align : 'right',
						summaryType : 'sum',
						width : 127,
						renderer : function(value, metaData, record, rowIndex,colIndex, store) {
							return Ext.util.Format.number(value, '0,000.00') + "元"
						}

					},{
						header : '平账金额',
						dataIndex : 'flateMoney',
						sortable : true,
						align : 'right',
						summaryType : 'sum',
						width : 127,
						renderer : function(value, metaData, record, rowIndex,colIndex, store) {
							return Ext.util.Format.number(value, '0,000.00') + "元"
						}

					}, {
						header : '回款日期',
						format : 'Y-m-d',
						dataIndex : 'backDate',
						sortable : true,
						align : 'center',
						width : 170
					}, {
						header : '回款状态',
						dataIndex : 'backStatus',
						sortable : true,
						width : 170,
						renderer : function(v) {
							if(v==2){
								return  "支付成功"
							}else if(v==1){
								return "支付审核中"
							}else if(v==0){
								return "等待支付"
							}else if(v==-1){
								return "支付失败"
							}
							
						}
					}],
			listeners : {
				scope : this
			}
		});

		this.grid_CompensatoryFlow.getStore().load();

	},
	save : function() {
	},
	createRepaymentSource : function() {

	},
	deleteRepaymentSource : function() {
	}
});


