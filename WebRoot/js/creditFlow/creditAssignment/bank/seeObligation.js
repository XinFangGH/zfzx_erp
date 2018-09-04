/** 投资人债权查询--债权详情 */
seeObligation = Ext.extend(Ext.Panel, {
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		};
		Ext.applyIf(this, _cfg);
		this.initUIComponents();

		seeObligation.superclass.constructor.call(this, {
					labelAlign : 'right',
					buttonAlign : 'center',
					frame : true,
					monitorValid : true,
					labelWidth : 110,
					autoScroll : true,
					bodyStyle : 'overflowX:hidden',
					layout : 'form',
					border : false,
					items : [{
								layout : 'form',
								autoHeight : true,
								collapsible : false,
								anchor : '98%',
								items : [{
											xtype : 'fieldset',
											title : '投资人详细信息',
											labelWidth : 80,
											collapsible : true,
											anchor : '100%',
											items : [this.investGridPanel]
										}, {
											xtype : 'fieldset',
											title : '账户资产信息',
											collapsible : true,
											autoHeight : true,
											anchor : '100%',
											items : [this.accountAssetsPanel]

										}, {
											xtype : 'fieldset',
											title : '投资明细',
											labelWidth : 80,
											collapsible : true,
											anchor : '100%',
											items : [this.searchPanel,
													this.gridPanel]
										}]
							}]
				})
	},
	initUIComponents : function() {
		// 投资人信息======================
		this.investGridPanel = new Ext.form.FormPanel({
					autoHeight : true,
					autoWidth : true,
					layout : 'column',
					bodyStyle : 'padding:10px',
					border : false,
					autoScroll : true,
					labelAlign : 'right',
					defaults : {
						anchor : '96%',
						labelWidth : 60
					},
					items : [{
								border : false,
								columnWidth : 0.31,
								layout : "form",
								labelWidth : 70,
								labelAlign : 'right',
								items : [{
											xtype : 'textfield',
											border : false,
											readOnly : true,
											fieldLabel : "投资人",
											name : "investName",
											anchor : '100%'
										}]
							}, {
								border : false,
								columnWidth : 0.31,
								layout : "form",
								labelWidth : 70,
								labelAlign : 'right',
								items : [{
											xtype : 'textfield',
											border : false,
											readOnly : true,
											fieldLabel : "联系方式",
											name : "cellphone",
											anchor : '100%'
										}]
							}, {
								border : false,
								columnWidth : 0.31,
								layout : "form",
								labelWidth : 70,
								labelAlign : 'right',
								items : [{
											xtype : 'textfield',
											border : false,
											readOnly : true,
											fieldLabel : "邮箱",
											name : "selfemail",
											anchor : '100%'
										}]
							}]
				});
		// 账户资产信息========================
	this.accountAssetsPanel=new Ext.Panel({
			autoHeight : true,
			autoWidth : true,
			layout : 'column',
			bodyStyle : 'padding:10px',
			border : false,
			autoScroll : true,
			labelAlign : 'right',
			defaults : {
				anchor : '96%',
				labelWidth : 60
			},
			items : [{
					border : false,
					columnWidth : 0.55,
					layout : "form",
					labelWidth : 100,
					labelAlign : 'right',
					items : [{
								xtype : 'textfield',
								border : false,
								readOnly : true,
								fieldClass : 'field-align',
								fieldLabel : "预计账户总收益",
								name : "investName",
								anchor : '100%',
								value:this.record==null?'0.00':Ext.util.Format.number(this.record.data.expectAllInterest,
												',000,000,000.00')
						},{
								xtype : 'textfield',
								border : false,
								readOnly : true,
								fieldClass : 'field-align',
								fieldLabel : "待回收本金",
								name : "investName",
								anchor : '100%',
								value:this.record==null?'0.00':Ext.util.Format.number(this.record.data.unPrincipalRepayment,
												',000,000,000.00')
						},{
								xtype : 'textfield',
								border : false,
								readOnly : true,
								fieldClass : 'field-align',
								fieldLabel : "待回收利息",
								name : "investName",
								anchor : '100%',
								value:this.record==null?'0.00':Ext.util.Format.number(this.record.data.unInterest,
												',000,000,000.00')
						},{
								xtype : 'textfield',
								border : false,
								readOnly : true,
								fieldLabel : "累计投资收益",
								name : "investName",
								anchor : '100%',
								fieldClass : 'field-align',
								value:this.record==null?'0.00':Ext.util.Format.number(this.record.data.expectAllInterest,
												',000,000,000.00')
						}]
				},{
					border : false,
					columnWidth : 0.05,
					layout : "form",
					labelWidth : 20,
					labelAlign : 'right',
					items : [{
								fieldLabel : "元",
								labelSeparator : '',
								anchor : "90%"
						},{
								fieldLabel : "元",
								labelSeparator : '',
								anchor : "90%"
						},{
								fieldLabel : "元",
								labelSeparator : '',
								anchor : "90%"
						},{
								fieldLabel : "% ",
								labelSeparator : '',
								anchor : "90%"
						}]
				
				}, {
					border : false,
					columnWidth : 0.35,
					layout : "form",
					labelWidth : 100,
					labelAlign : 'right',
					items : [{
								xtype : 'textfield',
								border : false,
								readOnly : true,
								fieldLabel : "当前账户总资产",
								name : "cellphone",
								anchor : '100%',
								fieldClass : 'field-align',
								value:this.record==null?'0.00':Ext.util.Format.number(this.record.data.totalMoney,
												',000,000,000.00')
						},{
								xtype : 'textfield',
								border : false,
								readOnly : true,
								fieldLabel : "可投资额",
								name : "investName",
								anchor : '100%',
								fieldClass : 'field-align',
								value:this.record==null?'0.00':Ext.util.Format.number(this.record.data.availableInvestMoney,
												',000,000,000.00')
						},{
								xtype : 'textfield',
								border : false,
								readOnly : true,
								fieldLabel : "累计投资额",
								name : "investName",
								anchor : '100%',
								fieldClass : 'field-align',
								value:this.record==null?'0.00':Ext.util.Format.number(this.record.data.totalInvestMoney,
												',000,000,000.00')
						},{
								xtype : 'textfield',
								border : false,
								readOnly : true,
								fieldLabel : "累计收益率",
								name : "investName",
								anchor : '100%',
								fieldClass : 'field-align',
								value:this.record==null?'0.00':Ext.util.Format.number(this.record.data.personInterestRate,
												'0.00')
						}]
			},{
					border : false,
					columnWidth : 0.05,
					layout : "form",
					labelWidth : 20,
					labelAlign : 'right',
					items : [{
								fieldLabel : "元",
								labelSeparator : '',
								anchor : "90%"
						},{
								fieldLabel : "元",
								labelSeparator : '',
								anchor : "90%"
						},{
								fieldLabel : "元",
								labelSeparator : '',
								anchor : "90%"
						},{
								fieldLabel : "元",
								labelSeparator : '',
								anchor : "90%"
						}]
				
				}]
		
		
		});
		// 搜索信息===========================
		this.searchPanel = new Ext.FormPanel({
			border : false,
			anchor : '100%',
			layoutConfig : {
				align : 'middle'
			},
			defaults : {
				border : false,
				labelAlign : 'right',
				labelWidth : 70
			},
			bodyStyle : 'padding:10px 10px 10px 10px',
			scope : this,
			items : [{// 第一行开始------------
				labelAlign : 'right',
				layout : 'form',
				border : false,
				items : [{
					fieldLabel : '投资时间',
					layout : 'column',
					border : false,
					items : [{
								columnWidth : .25,
								layout : 'form',
								labelWidth : 70,
								labelAlign : 'right',
								border : false,
								items : [{
											name : 'investStartDate',
											fieldLabel : '起始时间',
											anchor : "100%",
											format : 'Y-m-d',
											xtype : 'datefield'
										}]
							}, {
								columnWidth : .25,
								layout : 'form',
								labelWidth : 70,
								labelAlign : 'right',
								border : false,
								items : [{
											name : 'investEndDate',
											fieldLabel : '截止时间',
											anchor : "100%",
											format : 'Y-m-d',
											xtype : 'datefield'
										}]
							}, {
								columnWidth : .5,
								layout : 'form',
								xtype : 'radiogroup',
								labelAlign : 'right',
								border : false,
								style : 'margin-left:10px',
								items : [{
									layout : 'form',
									boxLabel : '今天',
									name : '1',
									id : "Today",
									inputValue : false,
									listeners : {
										scope : this,
										check : function() {
											var flag = Ext.getCmp("Today")
													.getValue();
											if (flag == true) {
												this
														.getCmpByName("investStartDate")
														.setValue(new Date());
												this.search();

											}
										}
									}
								}, {
									layout : 'form',
									boxLabel : '近三天',
									name : '1',
									id : "checkThreeDay",
									inputValue : false,
									listeners : {
										scope : this,
										check : function() {
											var flag = Ext
													.getCmp("checkThreeDay")
													.getValue();
											if (flag == true) {
												var now = new Date();
												var time = now.getTime();
												time -= 1000 * 60 * 60 * 24 * 3;// 加上3天
												now.setTime(time);
												this
														.getCmpByName("investStartDate")
														.setValue(now);
												this.search();
											}
										}
									}
								}, {
									layout : 'form',
									boxLabel : '近七天',
									name : '1',
									id : "checkOneWeek",
									inputValue : false,
									listeners : {
										scope : this,
										check : function() {
											var flag = Ext
													.getCmp("checkOneWeek")
													.getValue();
											if (flag == true) {
												this
														.getCmpByName("investStartDate")
														.setValue();
												var now = new Date();
												var time = now.getTime();
												time -= 1000 * 60 * 60 * 24 * 7;// 加上3天
												now.setTime(time)
												this
														.getCmpByName("investStartDate")
														.setValue(now);
												this.search();
											}
										}
									}
								}, {
									layout : 'form',
									boxLabel : '一个月',
									name : '1',
									id : "checkOneMonth",
									inputValue : false,
									listeners : {
										scope : this,
										check : function() {
											var flag = Ext
													.getCmp("checkOneMonth")
													.getValue();
											if (flag == true) {
												this
														.getCmpByName("investStartDate")
														.setValue();
												var now = new Date();
												var time = now.getTime();
												time -= 1000 * 60 * 60 * 24
														* 30;// 加上3天
												now.setTime(time)
												this
														.getCmpByName("investStartDate")
														.setValue(now);
												this.search();
											}
										}
									}
								}, {
									layout : 'form',
									boxLabel : '三个月',
									name : '1',
									id : "checkThreeMonth",
									inputValue : false,
									listeners : {
										scope : this,
										check : function() {
											var flag = Ext
													.getCmp("checkThreeMonth")
													.getValue();
											if (flag == true) {
												this
														.getCmpByName("investStartDate")
														.setValue();
												var now = new Date();
												var time = now.getTime();
												time -= 1000 * 60 * 60 * 24
														* 90;
												now.setTime(time);
												this
														.getCmpByName("investStartDate")
														.setValue(now);
												this.search();
											}
										}
									}
								}, {
									layout : 'form',
									boxLabel : '全部',
									name : '1',
									id : "checkAllDay",
									inputValue : false,
									listeners : {
										scope : this,
										check : function() {
											var flag = Ext
													.getCmp("checkAllDay")
													.getValue();
											if (flag == true) {
												this
														.getCmpByName("investStartDate")
														.setValue();
												this
														.getCmpByName("investEndDate")
														.setValue();
												this.search();
											}
										}
									}
								}]
							}]
				}]
					// 第一行结束------------
			}, {	// 第二行开始------------
						layout : "column",
						items : [{
							columnWidth : .55,
							layout : 'form',
							border : false,
							items : [{
							 xtype : 'hidden',
            				name:"investObligationStatus"
							},{
								fieldLabel : '当前状态',
								xtype : 'radiogroup',
								items : [{
									layout : 'form',
									boxLabel : '全部',
									name : '0',
									id : "checkAll",
									checked : true,
									inputValue : false,
									listeners : {
										scope : this,
										check : function() {
											var flag = Ext.getCmp("checkAll")
													.getValue();
											if (flag == true) {
												this
														.getCmpByName("investObligationStatus")
														.setValue(null);
												this.search();
											}
										}
									}
								}, {
									layout : 'form',
									boxLabel : '回款中',
									name : '0',
									id : "check1",
									inputValue : false,
									listeners : {
										scope : this,
										check : function() {
											var flag = Ext.getCmp("check1")
													.getValue();
											if (flag == true) {
												this
														.getCmpByName("investObligationStatus")
														.setValue("0");
												this.search();
											}
										}
									}

								}, {
									layout : 'form',
									boxLabel : '已结清',
									name : '0',
									id : "check2",
									inputValue : false,
									listeners : {
										scope : this,
										check : function() {
											var flag = Ext.getCmp("check2")
													.getValue();
											if (flag == true) {
												this
														.getCmpByName("investObligationStatus")
														.setValue("1");
												this.search();
											}
										}
									}
								}]
							}]
						}, {
							columnWidth : .20,
							layout : 'form',
							border : false,
							items : [{
										text : '查询',
										xtype : 'button',
										scope : this,
										anchor : "70%",
										iconCls : 'btn-search',
										handler : this.search
									}]
						}, {
							columnWidth : .20,
							layout : 'form',
							border : false,
							items : [{
										text : '重置',
										xtype : 'button',
										scope : this,
										iconCls : 'btn-reset',
										anchor : "70%",
										handler : this.reset
									}]
						}]
					}]
				// 第二行结束------------
		});
		// 债权信息============================
		this.topbar = new Ext.Toolbar({
			items : [{
				iconCls : 'btn-detail',
				text : '查看还款收益表',
				xtype : 'button',
				scope : this,
				handler : function() {
					var s = this.gridPanel.getSelectionModel().getSelections();
					if (s <= 0) {
						Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
						return false;
					} else if (s.length > 1) {
						Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
						return false;
					} else {
						var window_seeOb = new Ext.Window({
							title : '查看收益计划表',
							layout : 'fit',
							width : (screen.width - 180) * 0.7,
							maximizable : true,
							height : 500,
							closable : true,
							modal : true,
							plain : true,
							border : false,
							autoScroll : true,
							items : [{
								border : false,
								autoWidth : true,
								autoHeight : true,
								autoScroll : true,
								anchor : '100%',
								items : [new obligationFundIntentViewVM({
									isHiddenautocreateBtn : true,
									obligationInfoId : s[0].data.id,
									investPersonId : this.investId,
									keyValue : "oneSlFundIntentCreat",
									projectId : s[0].data.obligationId
								})]
							}],
							listeners : {
								'beforeclose' : function(panel) {
									window_seeOb.destroy();
								}
							}
						});
						window_seeOb.show();
					}
				}
			}]
		});
		var summary = new Ext.ux.grid.GridSummary();
		function totalMoney(v, params, data) {
			return '总计(元)';
		}
		this.gridPanel = new HT.GridPanel({
			rowActions : false,
			autoHeight : true,
			tbar : this.topbar,
			notmask : false,
			plugins : [summary],
			root : 'result',
			url : __ctxPath
					+ '/creditFlow/creditAssignment/customer/seeObligationCsInvestmentperson.do?investId='
					+ this.investId,
			baseParams : {
				investObligationStatus : this
						.getCmpByName("investObligationStatus"),
				investStartDate : this.getCmpByName("investStartDate"),
				investEndDate : this.getCmpByName("investEndDate")
			},
			fields : ['id','obligationName', 'totalQuotient', 'investStartDate','investEndDate','obligationId',
					'projectNumber', 'investObligationStatus', 'investQuotient','obligationAccrual','investMoney',
					'fundIntentStatusName','allBackMoney','alreadyBackMoney','unBackMoney','investMoney'],
			columns : [{
						header : '债权产品名称',
						dataIndex : 'obligationName',
						summaryType : 'count',
						summaryRenderer : totalMoney,
						width : 100
					}, /*{
						header : '借款人',
						width : 100,
						dataIndex : ''
					},*/ {
						header : '投资金额',
						width : 130,
						dataIndex : 'investMoney',
						summaryType : 'sum',
						align : 'right',
						renderer:function (value){
							if(0<value){
								return Ext.util.Format.number(value,'000,000,000.00') + "元"
							}else{
								return "0元"
							}
						}
					}, {
						header : '年化率',
						width : 130,
						align : 'right',
						summaryType : 'sum',
						dataIndex : 'obligationAccrual',
						renderer:function (value){
							return Ext.util.Format.number(value,'000.00') + "%"
						}
					}, {
						header : '总计应回收款',
						width : 130,
						dataIndex : 'allBackMoney',
						align : 'right',
						summaryType : 'sum',
						renderer:function (value){
							return Ext.util.Format.number(value,',000,000.00') + "元"
						}
					}, {
						header : '已回收款',
						width : 130,
						dataIndex : 'alreadyBackMoney',
						align : 'right',
						summaryType : 'sum',
						renderer:function (value){
							return Ext.util.Format.number(value,',000,000.00') + "元"
						}
					}, {
						header : '待回收款',
						width : 130,
						dataIndex : 'unBackMoney',
						align : 'right',
						summaryType : 'sum',
						renderer:function (value){
							return Ext.util.Format.number(value,',000,000.00') + "元"
						}
					}, {
						header : '投资开始时间',
						width : 130,
						dataIndex : 'investObligationStatus',
						align : 'right',
						dataIndex : 'investStartDate',
						renderer:function (value){
							if(1==value){
							return  "交易结束"
							}else{
							return  "交易进行中"
							}
						}
					}, {
						header : '投资结束时间',
						width : 130,
						align : 'right',
						dataIndex : 'investEndDate'
					}, {
						header : '当前状态',
						width : 130,
						dataIndex : 'fundIntentStatusName'
					}]
		});
		this.gridPanel.addListener('afterrender', function() {
					this.loadMask1 = new Ext.LoadMask(this.gridPanel.getEl(), {
								msg : '正在加载数据中······,请稍候······',
								store : this.gridPanel.store,
								removeMask : true
							});
					this.loadMask1.show(); // 显示
				}, this);
		// 加载投资人信息
		this.investGridPanel.loadData({
			url : __ctxPath
					+ '/creditFlow/creditAssignment/customer/getPersonByIdCsInvestmentperson.do?investId='
					+ this.investId,

			root : 'data',
			preName : ['person'],
			success : function(response, options) {
				var respText = response.responseText;
				var alarm_fields = Ext.util.JSON.decode(respText);
				var investName = alarm_fields.data.investName;
				var cellphone = alarm_fields.data.cellphone;
				var selfemail = alarm_fields.data.selfemail;
				this.getCmpByName('investName').setValue(investName);
				this.getCmpByName('cellphone').setValue(cellphone);
				this.getCmpByName('selfemail').setValue(selfemail);
			}

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