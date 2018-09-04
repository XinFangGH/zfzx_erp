/**
 * @author
 * @class SlFundIntentView
 * @extends Ext.Panel
 * @description [SlFundIntent]管理
 * @company 智维软件
 * @createtime:
 */


CusLoanedFundIntentView = Ext.extend(Ext.Panel, {

	// 构造函数
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
	
		// this.businessType="SmallLoan"
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		CusLoanedFundIntentView.superclass.constructor.call(this, {
			name : "CusLoanedFundIntentView",
			hidden : this.isHiddenPanel,
			region : 'center',
			layout : 'anchor',
			anchor : '100%',
			items : [{
				xtype : 'panel',
				border : false,
				bodyStyle : 'margin-bottom:5px',
				html : this.isChangeTitle == true
						? '<br/><font class="x-myZW-fieldset-title">（</font>颜色预警：<font color=red>逾期款项</font>&nbsp;&nbsp<font style="line-height:20px">未结清项</font>&nbsp;&nbsp<font color=gray>已结清项</font><font class="x-myZW-fieldset-title" >）：</font>'
						: '<br/><B><font class="x-myZW-fieldset-title">【借款人放款收息表】</font></B><font class="x-myZW-fieldset-title">（</font>颜色预警：<font color=red>逾期款项</font>&nbsp;&nbsp<font style="line-height:20px">未结清项</font>&nbsp;&nbsp<font color=gray>已结清项</font><font class="x-myZW-fieldset-title" >）：</font>',
				hidden : this.isHiddenTitle
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
								iconCls : 'btn-details',
								text : '导出Excel',
								xtype : 'button',
								hidden : this.isHiddenExcel,
								scope : this,
								handler : function() {
									this.toExcel();
								}
							}, "->", {
								xtype : 'checkbox',
								boxLabel : '费用相关',
								inputValue : true,
								name : "charge",
								checked : true,
								scope : this,
								handler : function() {
									var charge = this.topbar
											.getCmpByName("charge");
									var intent = this.topbar
											.getCmpByName("intent");
									if (charge.getValue() == true
											&& intent.getValue() == true) {
										this.related("all");
									}
									if (charge.getValue() == false
											&& intent.getValue() == true) {
										this.related("intent");
									}
									if (charge.getValue() == true
											&& intent.getValue() == false) {
										this.related("charge");
									}
									if (charge.getValue() == false
											&& intent.getValue() == false) {
										this.related("none");
									}
								}
							}, '-', {
								xtype : 'checkbox',
								name : "intent",
								boxLabel : '本金相关',
								inputValue : true,
								scope : this,
								checked : true,
								handler : function() {
									var charge = this.topbar
											.getCmpByName("charge");
									var intent = this.topbar
											.getCmpByName("intent");
									if (charge.getValue() == true
											&& intent.getValue() == true) {
										this.related("all");
									}
									if (charge.getValue() == false
											&& intent.getValue() == true) {
										this.related("intent");
									}
									if (charge.getValue() == true
											&& intent.getValue() == false) {
										this.related("charge");
									}
									if (charge.getValue() == false
											&& intent.getValue() == false) {
										this.related("none");
									}
								}
							}, ' ', ' ', ' ', ' ']
				});
		var field = Ext.data.Record.create([{
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
					name : 'factDate'
				}, {
					name : 'afterMoney'
				}, {
					name : 'notMoney'
				}, {
					name : 'accrualMoney'
				}, {
					name : 'isValid'
				}, {
					name : 'flatMoney'
				}, {
					name : 'overdueRate'
				}, {
					name : 'isOverdue'
				}, {
					name : 'companyId'
				}, {
					name : 'interestStarTime'
				}, {
					name : 'interestEndTime'
				}, {
					name : 'payintentPeriod'
				}, {
					name : 'slSuperviseRecordId'
				}]);	
	var url=__ctxPath + "/creditFlow/finance/listloanCommonBpFundIntent.do"
	
		var jStore = new Ext.data.JsonStore({
					url : url,
					root : 'result',
					fields : field
				});
		jStore.load({
					params : {
						projectId : this.projectId,
						flag1 : 1,
						bidPlanId:this.bidPlanId,
						preceptId:this.preceptId,
						slEarlyRepaymentId: this.slEarlyRepaymentId,
						businessType : this.businessType
					}
				});
		this.projectFundsm = new Ext.grid.CheckboxSelectionModel({
					header : '序号'
				});
		this.gridPanel = new HT.GridPanel({
			border : false,
			name : 'gridPanel',
			scope : this,
			store : jStore,
			autoScroll : true,
			autoWidth : true,
			layout : 'anchor',
			clicksToEdit : 1,
			viewConfig : {
				forceFit : true
			},
			bbar : false,
			tbar : this.isChangeTitle == true ? null : this.topbar,
			rowActions : false,
			showPaging : false,
			stripeRows : true,
			plain : true,
			loadMask : true,
			autoHeight : true,
			sm : this.projectFundsm,
			plugins : [summary],
			columns : [{
						header : 'fundIntentId',
						dataIndex : 'fundIntentId',
						hidden : true
					},{
				header : '期数',
				dataIndex : 'payintentPeriod',
				renderer : function(value, metaData, record, rowIndex,colIndex, store){
					if(null!=value){
						if (record.data.isValid == 1) {
							return '<font style="font-style:italic;text-decoration: line-through;color:gray">'+ '第'+value+'期'+ "</font>"
						} else {
							return '第'+value+'期';
						}
					}
				 }
				}, {
						header : '资金类型',
						dataIndex : 'fundTypeName',
						width : 107,
						summaryType : 'count',
						summaryRenderer : totalMoney,
						renderer : function(value, metaData, record, rowIndex,colIndex, store) {
						
							if (record.data.isValid == 1) {
								return '<font style="font-style:italic;text-decoration: line-through;color:gray">'+ record.get("fundTypeName")+ "</font>"
							}else{
								return record.get("fundTypeName");
							}
							
						}
					}, {
						header : '计划收入金额',
						dataIndex : 'payMoney',
						summaryType : 'sum',
						width : 110,
						align : 'right',
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store) {
							
								if (record.data.isValid == 1) {
									return '<font style="font-style:italic;text-decoration: line-through;color:gray">'
											+ Ext.util.Format.number(value,
													',000,000,000.00')
											+ "元</font>"
								}else{
						

									return Ext.util.Format.number(value,
											',000,000,000.00')
											+ "元";
								}
						}

						
						
					}, {
						header : '计划支出金额',
						dataIndex : 'incomeMoney',
						align : 'right',
						width : 110,
						summaryType : 'sum',
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store) {
						
									if (record.data.isValid == 1) {
										return '<font style="font-style:italic;text-decoration: line-through;color:gray">'
												+ Ext.util.Format.number(value,
														',000,000,000.00')
												+ "元</font>"
									}else{
								
								return Ext.util.Format.number(value,
										',000,000,000.00')
										+ "元";
									}
							}


					}, {
						header : '计划到账日',
						dataIndex : 'intentDate',
						format : 'Y-m-d',
						editor : this.datafield,
						width : 100,
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store) {
							var flag1 = 0; // incomeMoney
							if (record.data.payMoney != 0) { // payMoney
								flag1 = 1;
							}
							var v;
							try {
								if (typeof(value) == "string") {
									v = value;
									// return v;
								} else {
									v = Ext.util.Format.date(value, 'Y-m-d');
								}
							} catch (err) {
								v = value;
								return v;
							}
							
							if (record.data.isValid == 1) {
								return '<font style="font-style:italic;text-decoration: line-through;color:gray">'
										+ v + "</font>";
							} else {
								if (record.data.notMoney == 0) {
									return '<font style="color:gray">' + v
											+ "</font>";
								}
								if (record.data.isOverdue == "是" && flag1 != 1) {

									return '<font style="color:red">' + v
											+ "</font>";
								}

								if (record.data.afterMoney == 0) {
									return v;

								}
								return v;

							}

						}
					}/*, {
						header : '实际到账日',
						dataIndex : 'factDate',
						format : 'Y-m-d',
						hidden : this.isHiddenMoney,
						// editor :this.datafield1,
						width : 80,
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store) {
							var flag1 = 0; // incomeMoney
							if (record.data.payMoney != 0) { // payMoney
								flag1 = 1;
							}
							var v;
							try {
								if (typeof(value) == "string") {
									v = value;
									// return v;
								} else {
									v = Ext.util.Format.date(value, 'Y-m-d');
								}
							} catch (err) {
								v = value;
								return v;
							}
							if (v != null) {
								if (isThisSuperviseRecord != null
										|| isThisEarlyPaymentRecord != null
										|| isThisAlterAccrualRecord != null) {
									if ((flag1 == 1)
											|| (flag1 == 0 && record.data.incomeMoney == record.data.afterMoney)) {
									} else {
										if (record.data.isValid == 1) {
											return '<font style="font-style:italic;text-decoration: line-through;color:gray">'
													+ v + "</font>";
										}
										// else {
										// return '<font
										// style="font-style:italic;text-decoration:
										// line-through">'
										// + v + "</font>";
										// }
									}

								}
								if (record.data.isValid == 1) {

									return '<font style="font-style:italic;text-decoration: line-through;color:gray">'
											+ v + "</font>";

								} else {
									if (record.data.notMoney == 0) {
										return '<font style="color:gray">' + v
												+ "</font>";
									}
									if (record.data.isOverdue == "是"
											&& flag1 != 1) {

										return '<font style="color:red">' + v
												+ "</font>";
									}

									if (record.data.afterMoney == 0) {
										return v;

									}

									return v;
								}

							} else {
								return "";
							}

						}
					}*/, {

						header : '计息开始日期',
						dataIndex : 'interestStarTime',
						format : 'Y-m-d',
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store) {
							var flag1 = 0; // incomeMoney
							if (record.data.payMoney != 0) { // payMoney
								flag1 = 1;
							}
							var v;
							try {
								if (typeof(value) == "string") {
									v = value;
									// return v;
								} else {
									v = Ext.util.Format.date(value, 'Y-m-d');
								}
							} catch (err) {
								v = value;
								return v;
							}
						
							if (record.data.isValid == 1) {
								return '<font style="font-style:italic;text-decoration: line-through;color:gray">'
										+ v + "</font>";
							} else {
								if (record.data.notMoney == 0) {
									return '<font style="color:gray">' + v
											+ "</font>";
								}
								if (record.data.isOverdue == "是" && flag1 != 1) {

									return '<font style="color:red">' + v
											+ "</font>";
								}

								if (record.data.afterMoney == 0) {
									return v;

								}
								return v;

							}

						}
					}, {
						header : '计息结束日期',
						dataIndex : 'interestEndTime',
						format : 'Y-m-d',
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store) {
							var flag1 = 0; // incomeMoney
							if (record.data.payMoney != 0) { // payMoney
								flag1 = 1;
							}
							var v;
							try {
								if (typeof(value) == "string") {
									v = value;
									// return v;
								} else {
									v = Ext.util.Format.date(value, 'Y-m-d');
								}
							} catch (err) {
								v = value;
								return v;
							}
							
							if (record.data.isValid == 1) {
								return '<font style="font-style:italic;text-decoration: line-through;color:gray">'
										+ v + "</font>";
							} else {
								if (record.data.notMoney == 0) {
									return '<font style="color:gray">' + v
											+ "</font>";
								}
								if (record.data.isOverdue == "是" && flag1 != 1) {

									return '<font style="color:red">' + v
											+ "</font>";
								}

								if (record.data.afterMoney == 0) {
									return v;

								}
								return v;

							}

						}
					}/*, {
						header : '已对账金额',
						dataIndex : 'afterMoney',
						align : 'right',
						summaryType : 'sum',
						hidden : this.isHiddenMoney,
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store) {
							var flag1 = 0; // incomeMoney
							if (record.data.payMoney != 0) { // payMoney
								flag1 = 1;
							}
							if (value != null) {

								if (isThisSuperviseRecord != null
										|| isThisEarlyPaymentRecord != null
										|| isThisAlterAccrualRecord != null) {
									if ((flag1 == 1)
											|| (flag1 == 0 && record.data.incomeMoney == record.data.afterMoney)) {
									} else {
										if (record.data.isValid == 1) {
											return '<font style="font-style:italic;text-decoration: line-through;color:gray">'
													+ Ext.util.Format.number(
															value,
															',000,000,000.00')
													+ "元</font>"
										}
										// else {
										// return '<font
										// style="font-style:italic;text-decoration:
										// line-through">'
										// + Ext.util.Format.number(value,
										// ',000,000,000.00')
										// + "元</font>"
										// }
									}

								}
								if (record.data.isValid == 1) {
									return '<font style="font-style:italic;text-decoration: line-through;color:gray">'
											+ Ext.util.Format.number(value,
													',000,000,000.00')
											+ "元</font>"
								} else {
									if (record.data.notMoney == 0) {
										return '<font style="color:gray">'
												+ Ext.util.Format.number(value,
														',000,000,000.00')
												+ "元" + "</font>";
									}
									if (record.data.isOverdue == "是"
											&& flag1 != 1) {

										return '<font style="color:red">'
												+ Ext.util.Format.number(value,
														',000,000,000.00')
												+ "元" + "</font>";
									}

									if (record.data.afterMoney == 0) {
										return Ext.util.Format.number(value,
												',000,000,000.00')
												+ "元"

									}

									return Ext.util.Format.number(value,
											',000,000,000.00')
											+ "元"
								}
							} else
								return "";

						}
					}, {
						header : '未对账金额',
						dataIndex : 'notMoney',
						align : 'right',
						summaryType : 'sum',
						hidden : this.isHiddenMoney,
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store) {
							var flag1 = 0; // incomeMoney
							if (record.data.payMoney != 0) { // payMoney
								flag1 = 1;
							}
							if (value != null) {

								if (isThisSuperviseRecord != null
										|| isThisEarlyPaymentRecord != null
										|| isThisAlterAccrualRecord != null) {
									if ((flag1 == 1)
											|| (flag1 == 0 && record.data.incomeMoney == record.data.afterMoney)) {
									} else {
										if (record.data.isValid == 1) {
											return '<font style="font-style:italic;text-decoration: line-through;color:gray">'
													+ Ext.util.Format.number(
															value,
															',000,000,000.00')
													+ "元</font>"
										}
										// else {
										// return '<font
										// style="font-style:italic;text-decoration:
										// line-through">'
										// + Ext.util.Format.number(value,
										// ',000,000,000.00')
										// + "元</font>"
										// }
									}

								}
								if (record.data.isValid == 1) {
									return '<font style="font-style:italic;text-decoration: line-through;color:gray">'
											+ Ext.util.Format.number(value,
													',000,000,000.00')
											+ "元</font>"
								} else {
									if (record.data.notMoney == 0) {
										return '<font style="color:gray">'
												+ Ext.util.Format.number(value,
														',000,000,000.00')
												+ "元" + "</font>";
									}
									if (record.data.isOverdue == "是"
											&& flag1 != 1) {

										return '<font style="color:red">'
												+ Ext.util.Format.number(value,
														',000,000,000.00')
												+ "元" + "</font>";
									}

									if (record.data.afterMoney == 0) {
										return Ext.util.Format.number(value,
												',000,000,000.00')
												+ "元"

									}

									return Ext.util.Format.number(value,
											',000,000,000.00')
											+ "元"
								}
							} else
								return "";
						}
					}, {
						header : '已平账金额',
						dataIndex : 'flatMoney',
						align : 'right',
						summaryType : 'sum',
						hidden : this.isHiddenMoney,
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store) {
							var flag1 = 0; // incomeMoney
							if (record.data.payMoney != 0) { // payMoney
								flag1 = 1;
							}
							if (value != null) {
								if (isThisSuperviseRecord != null
										|| isThisEarlyPaymentRecord != null
										|| isThisAlterAccrualRecord != null) {
									if ((flag1 == 1)
											|| (flag1 == 0 && record.data.incomeMoney == record.data.afterMoney)) {
									} else {
										if (record.data.isValid == 1) {
											return '<font style="font-style:italic;text-decoration: line-through;color:gray">'
													+ Ext.util.Format.number(
															value,
															',000,000,000.00')
													+ "元</font>"
										}
										// else {
										// return '<font
										// style="font-style:italic;text-decoration:
										// line-through">'
										// + Ext.util.Format.number(value,
										// ',000,000,000.00')
										// + "元</font>"
										// }
									}

								}
								if (record.data.isValid == 1) {
									return '<font style="font-style:italic;text-decoration: line-through;color:gray">'
											+ Ext.util.Format.number(value,
													',000,000,000.00')
											+ "元</font>"
								} else {
									if (record.data.notMoney == 0) {
										return '<font style="color:gray">'
												+ Ext.util.Format.number(value,
														',000,000,000.00')
												+ "元" + "</font>";
									}
									if (record.data.isOverdue == "是"
											&& flag1 != 1) {

										return '<font style="color:red">'
												+ Ext.util.Format.number(value,
														',000,000,000.00')
												+ "元" + "</font>";
									}

									if (record.data.afterMoney == 0) {
										return Ext.util.Format.number(value,
												',000,000,000.00')
												+ "元"

									}

									return Ext.util.Format.number(value,
											',000,000,000.00')
											+ "元"
								}
							} else
								return "";

						}

					}*/
					// , {
					// header : '逾期状态',
					// dataIndex : 'isOverdue',
					// renderer : function(value,metaData, record,rowIndex,
					// colIndex,store){
					// if(value !=null && record.data.fundType !=1748){
					// if(record.data.isValid==1){
					// return '<font style="font-style:italic;text-decoration:
					// line-through;color:gray">'+value+"</font>"
					// }else{
					// if(record.data.isOverdue=="是"){
					//													      	
					// return '<font style="color:red">'+value+"</font>";
					// }
					// if(record.data.notMoney==0){
					// return '<font style="color:gray">'+value+"</font>";
					// }
					// if(record.data.afterMoney==0){
					// return value
					//													      	
					// }
					//												           
					// return value
					// }
					// }else return "";
					//												  
					// }
					//								
					//								
					//								
					//								
					// }

					// , {
					// header : '是否有效',
					// dataIndex : 'isValid'
					// }
					/*, {
						header : '逾期费率',
						dataIndex : 'overdueRate',
						align : 'center',
						hidden : this.isHiddenOverdue,
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store) {
							var flag1 = 0; // incomeMoney
							if (record.data.payMoney != 0) { // payMoney
								flag1 = 1;
							}
							if (value != null && flag1 != 1) {
								if (isThisSuperviseRecord != null
										|| isThisEarlyPaymentRecord != null
										|| isThisAlterAccrualRecord != null) {
									if ((flag1 == 1)
											|| (flag1 == 0 && record.data.incomeMoney == record.data.afterMoney)) {
									} else {
										if (record.data.isValid == 1) {
											return '<font style="font-style:italic;text-decoration: line-through;color:gray">'
													+ Ext.util.Format.number(
															value,
															',000,000,000.000')
													+ "元</font>"
										}
										// else {
										// return '<font
										// style="font-style:italic;text-decoration:
										// line-through">'
										// + Ext.util.Format.number(value,
										// ',000,000,000.00')
										// + "元</font>"
										// }
									}

								}
								if (record.data.isValid == 1) {
									return '<font style="font-style:italic;text-decoration: line-through;color:gray">'
											+ Ext.util.Format.number(value,
													',000,000,000.000')
											+ "%/日</font>"
								} else {
									if (record.data.notMoney == 0) {
										return '<font style="color:gray">'
												+ Ext.util.Format.number(value,
														',000,000,000.000')
												+ "%/日" + "</font>";
									}
									if (record.data.isOverdue == "是") {

										return '<font style="color:red">'
												+ Ext.util.Format.number(value,
														',000,000,000.000')
												+ "%/日" + "</font>";
									}

									if (record.data.afterMoney == 0) {
										return Ext.util.Format.number(value,
												',000,000,000.000')
												+ "%/日"

									}

									return Ext.util.Format.number(value,
											',000,000,000.000')
											+ "%/日"
								}
							} else
								return "";

						}
					}, {
						header : '逾期违约金总额',
						dataIndex : 'accrualMoney',
						align : 'right',
						summaryType : 'sum',
						hidden : this.isHiddenOverdue,
						width : 100,
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store) {
							var flag1 = 0; // incomeMoney
							if (record.data.payMoney != 0) { // payMoney
								flag1 = 1;
							}
							if (value != null && flag1 != 1) {
								if (isThisSuperviseRecord != null
										|| isThisEarlyPaymentRecord != null
										|| isThisAlterAccrualRecord != null) {
									if ((flag1 == 1)
											|| (flag1 == 0 && record.data.incomeMoney == record.data.afterMoney)) {
									} else {
										if (record.data.isValid == 1) {
											return '<font style="font-style:italic;text-decoration: line-through;color:gray">'
													+ Ext.util.Format.number(
															value,
															',000,000,000.00')
													+ "元</font>"
										}
										// else {
										// return '<font
										// style="font-style:italic;text-decoration:
										// line-through">'
										// + Ext.util.Format.number(value,
										// ',000,000,000.00')
										// + "元</font>"
										// }
									}

								}
								if (record.data.isValid == 1) {
									return '<font style="font-style:italic;text-decoration: line-through;color:gray">'
											+ Ext.util.Format.number(value,
													',000,000,000.00')
											+ "元</font>"
								} else {
									if (record.data.notMoney == 0) {
										return '<font style="color:gray">'
												+ Ext.util.Format.number(value,
														',000,000,000.00')
												+ "元" + "</font>";
									}
									if (record.data.isOverdue == "是") {

										return '<font style="color:red">'
												+ Ext.util.Format.number(value,
														',000,000,000.00')
												+ "元" + "</font>";
									}

									if (record.data.afterMoney == 0) {
										return Ext.util.Format.number(value,
												',000,000,000.00')
												+ "元"

									}

									return Ext.util.Format.number(value,
											',000,000,000.00')
											+ "元"
								}
							} else
								return "";

						}
					}*/],
			listeners : {
				scope : this,
				beforeedit : function(e) {
					if (e.record.data['isValid'] != null) {
						if (e.record.data['isValid'] == 1
								|| e.record.data['afterMoney'] != 0) {
							e.cancel = true;
						}
					}
					if (e.record.data['fundIntentId'] != "") {
						if (this.enableEdit == true) {

							e.cancel = false;
						} else if (this.enableEdit == false) {
							e.cancel = true;
						}
					} else if (e.record.data['fundIntentId'] == "") {
						e.cancel = false;
					}

					/*
					 * if (e.record.data['fundType'] == 'principalLending') {
					 * e.cancel = true; }
					 */
				},
				afteredit : function(e) {
					if (e.record.data['fundType'] == 'principalLending'
							|| e.record.data['fundType'] == 'FinancingInterest'
							|| e.record.data['fundType'] == 'FinancingRepay'
							|| e.record.data['fundType'] == 'financingconsultationMoney'
							|| e.record.data['fundType'] == 'financingserviceMoney'
							|| e.record.data['fundType'] == 'backInterest') {
						e.record.set('incomeMoney', 0);
						e.record.commit();
					} else {
						e.record.set('payMoney', 0);
						e.record.commit()
					}
				}
			}
		});
	

	},

	autocreate : function() {
	var earlyProjectMoney=this.object.getCmpByName('earlyProjectMoney1').hiddenField.value
			var earlyDate=this.object.getCmpByName('slEarlyRepaymentRecord.earlyDate').getValue()
			var penaltyDays=this.object.getCmpByName('slEarlyRepaymentRecord.penaltyDays').getValue()
			var payintentPeriod=null
			if(this.isOwnBpFundProject==true){
				dayOfEveryPeriod=this.objectFinance.getCmpByName('ownBpFundProject.dayOfEveryPeriod').getValue()
				isStartDatePay=this.objectFinance.getCmpByName('ownBpFundProject.isStartDatePay').getValue()
			}else{
				dayOfEveryPeriod=this.objectFinance.getCmpByName('platFormBpFundProject.dayOfEveryPeriod').getValue()
				isStartDatePay=this.objectFinance.getCmpByName('platFormBpFundProject.isStartDatePay').getValue()
			}
			var params1={
				'slEarlyRepaymentRecord.earlyDate':earlyDate,
				'slEarlyRepaymentRecord.penaltyDays':penaltyDays,
				'slEarlyRepaymentRecord.earlyProjectMoney':earlyProjectMoney,
				preceptId:this.preceptId,
				bidPlanId:this.bidPlanId,
				flag1 : 0
			}
		var combox = new Ext.form.ComboBox({
			triggerAction : 'all',
			store : new Ext.data.SimpleStore({
						autoLoad : true,
						url : __ctxPath
								+ '/project/getPayIntentPeriodSlSmallloanProject.do',
						fields : ['itemId', 'itemName'],
						baseParams : {
							payintentPeriod : payintentPeriod
						}
					}),
			valueField : 'itemId',
			displayField : 'itemName'

		})
		var gridPanel1=this.gridPanel
		gridPanel1.getColumnModel().setEditor(3, combox);

		var gridstore = gridPanel1.getStore();
		gridstore.on('beforeload', function(gridstore, o) {

					Ext.apply(o.params, params1);
				});

		gridPanel1.getStore().reload();
		var vRecords = this.gridPanel.getStore().getRange(0,
				this.gridPanel.getStore().getCount()); // 得到修改的数据（记录对象）
		var vCount = vRecords.length; // 得到记录长度
		var vDatas = '';
		if (vCount > 0) {
			for (var i = 0; i < vCount; i++) {
				if (vRecords[i].data.afterMoney != null
						&& vRecords[i].data.afterMoney != 0) {
				}
			}
		}

	},
	toExcel : function() {
		var projectId = this.projectId;
		var businessType = this.businessType;
		window
				.open(
						__ctxPath
								+ "/creditFlow/finance/downloadSlFundIntent.do?projectId="
								+ projectId + "&businessType=" + businessType,
						'_blank');

	}

});
