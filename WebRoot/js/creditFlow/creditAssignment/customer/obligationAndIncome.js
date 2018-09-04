/** 债权库查询--债权匹配情况--债权详情 */
obligationAndIncome = Ext.extend(Ext.Panel, {
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		};
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		obligationAndIncome.superclass.constructor.call(this, {
					labelAlign : 'right',
					buttonAlign : 'center',
					frame : true,
					monitorValid : true,
					autoWidth : true,
					autoHeight : true,
					labelWidth : 110,
					autoScroll : true,
					border : false,
								items : [{
											xtype : 'fieldset',
											title : '债权详情',
											collapsible : true,
											anchor : '100%',
											items : [this.investObligationPanel]
										}, {
											xtype : 'fieldset',
											title : '款项收益表',
											collapsible : true,
											autoHeight : true,
											anchor : '100%',
											items : [new obligationFundIntentViewVM({
												isHiddenautocreateBtn:true,
												obligationInfoId:this.obligationInfoId,
												investPersonId:this.investPersonId,
												keyValue:"oneSlFundIntentCreat",
												projectId : this.projectId
											})]

										}]
				})
	},
	initUIComponents : function() {
		// 投资人债权详情======================
		this.investObligationPanel = new Ext.form.FormPanel({
					autoWidth : true,
					layout : 'column',
					autoScroll : true,
					labelAlign : 'right',
					border : false,
					monitorValid : true,
					defaults : {
						border : false,
						labelWidth : 70,
						bodyStyle : 'padding:8px'
					},
					layoutConfig : {
						columns : 3
					},
					items : [{
								columnWidth : 0.4,
								layout : "form",
								labelAlign : 'right',
								items : [{
											xtype : 'textfield',
											readOnly : true,
											fieldLabel : "债权人",
											name : "investName",
											anchor : '100%'
										}]
							}, {
								columnWidth : 0.6,
								labelAlign : 'right',
								layout : "form",
								items : [{
											//colspan : 2,
											xtype : 'textfield',
											readOnly : true,
											fieldLabel : "债权名称",
											name : "obligationName",
											anchor : '100%'
										}]
							}, {
								columnWidth : 0.35,
								layout : "form",
								labelAlign : 'right',
								items : [{
											xtype : 'textfield',
											readOnly : true,
											fieldLabel : "投资份额",
											name : "investQuotient",
											anchor : '100%'
										}]
							}, {
									columnWidth : .05, 
									layout : "form", 
									labelWidth : 20,
									items : [{
												fieldLabel : "份 ",
												labelSeparator : '',
												anchor : "90%"
											}]
							},{
								columnWidth : 0.25,
								layout : "form",
								labelAlign : 'right',
								items : [{
											xtype : 'textfield',
											readOnly : true,
											fieldLabel : "投资金额",
											name : "investMoney",
											anchor : '100%',
											renderer : function(value) {
												if (value == "") {
													return "0.00";
												} else {
													return Ext.util.Format.number(value,
															',000,000,000.00');
												}
											}
										}]
							}, {
									columnWidth : .05, 
									layout : "form", 
									labelWidth : 20,
									items : [{
												fieldLabel : "元 ",
												labelSeparator : '',
												anchor : "90%"
											}]
							},{
								//width : 400,
								columnWidth : 0.25,
								layout : "form",
								labelAlign : 'right',
								items : [{
											xtype : 'textfield',
											readOnly : true,
											fieldLabel : "债权比例",
											name : "investRate",
											anchor : '100%'
										}]
							}, {
									columnWidth : .05, 
									layout : "form", 
									labelWidth : 20,
									items : [{
												fieldLabel : "% ",
												labelSeparator : '',
												anchor : "100%"
											}]
							},{
								columnWidth : 0.35,
								layout : "form",
								labelAlign : 'right',
								items : [{
											xtype : 'textfield',
											readOnly : true,
											fieldLabel : "投资期限",
											name : "payintentPeriod",
											anchor : '100%'
										}]
							},{
									columnWidth : .05, 
									layout : "form", 
									labelWidth : 20,
									items : [{
												fieldLabel : "月 ",
												labelSeparator : '',
												anchor : "90%"
											}]
							}, {
								columnWidth : 0.3,
								layout : "form",
								labelAlign : 'right',
								items : [{
											xtype : 'textfield',
											readOnly : true,
											fieldLabel : "贷款起始日",
											name : "investStartDate",
											anchor : '100%'
										}]
							}, {
								columnWidth : 0.3,
								layout : "form",
								labelAlign : 'right',
								items : [{
											xtype : 'textfield',
											readOnly : true,
											fieldLabel : "贷款截止日",
											name : "investEndDate",
											anchor : '100%'
										}]
							}]
				});
		// 款项收益表============================
				
		// 加载投资人信息
		this.investObligationPanel.loadData({
					url : __ctxPath
							+ '/creditFlow/creditAssignment/customer/seeObligationByIdCsInvestmentperson.do?id='
							+ this.obligationInfoId,
					root : 'data'
				});
	},
	reset : function() {
		this.searchPanel.getForm().reset();
	},
	search : function() {
		var searchPanel = this.searchPanel;
		var gridPanel = this.gridPanel;
		$search({
					searchPanel : searchPanel,
					gridPanel : gridPanel
				});
	}
})