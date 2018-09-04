/**
 * @author
 * @class SlFundIntentView
 * @extends Ext.Panel
 * @description [SlFundIntent]管理
 * @company 智维软件
 * @createtime:
 */

var slprojectMoney = 0;
var slnotprojectMoney = 0;
SuperviseLeaseFundIntentView = Ext.extend(Ext.Panel, {
	name:"SuperviseLeaseFundIntentView",
	infoObj : null,
	isHiddenAddBtn : true,
	isHiddenDelBtn : true,
	isHiddenCanBtn : true,
	isHiddenResBtn : true,
	isHiddenseeqlideBtn : false,
	isHiddenseesumqlideBtn : false,
	slSuperviseRecordId : null,
	isUnLoadData : false,
	isThisSuperviseRecord : null,
	isThisEarlyPaymentRecord : null,
	isThisEarlyPaymentRecordId : null,
	isThisAlterAccrualRecord : null,
	isThisAlterAccrualRecordId : null,
	isHiddenTitle : false,
	isHiddenPanel : false,
	isHiddenExcel : false,
	// 构造函数
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (typeof(_cfg.projectId) != "undefined") {
			this.projectId = _cfg.projectId;
		}
		if (typeof(_cfg.isHiddenExcel) != "undefined") {
			this.isHiddenExcel = _cfg.isHiddenExcel;
		}
		if (typeof(_cfg.businessType) != "undefined") {
			this.businessType = _cfg.businessType;
		}
		if (typeof(_cfg.isHiddenAddBtn) != "undefined") {
			this.isHiddenAddBtn = _cfg.isHiddenAddBtn;
		}
		if (typeof(_cfg.isHiddenDelBtn) != "undefined") {
			this.isHiddenDelBtn = _cfg.isHiddenDelBtn;
		}
		if (typeof(_cfg.isHiddenCanBtn) != "undefined") {
			this.isHiddenCanBtn = _cfg.isHiddenCanBtn;
		}
		if (typeof(_cfg.isHiddenResBtn) != "undefined") {
			this.isHiddenResBtn = _cfg.isHiddenResBtn;
		}
		if (typeof(_cfg.isHiddenResBtn1) != "undefined") {
			this.isHiddenResBtn1 = _cfg.isHiddenResBtn1;
		}
		if (typeof(_cfg.isHiddenseeqlideBtn) != "undefined") {
			this.isHiddenseeqlideBtn = _cfg.isHiddenseeqlideBtn;
		}
		if (typeof(_cfg.isHiddenseesumqlideBtn) != "undefined") {
			this.isHiddenseesumqlideBtn = _cfg.isHiddenseesumqlideBtn;
		}

		if (typeof(_cfg.enableEdit) != "undefined") {
			this.enableEdit = _cfg.enableEdit;
		}
		if (typeof(_cfg.slSuperviseRecordId) != "undefined") {
			this.slSuperviseRecordId = _cfg.slSuperviseRecordId;
		}
		if (typeof(_cfg.isUnLoadData) != "undefined") {
			this.isUnLoadData = _cfg.isUnLoadData;
		}
		if (typeof(_cfg.isThisSuperviseRecord) != "undefined") {
			this.isThisSuperviseRecord = _cfg.isThisSuperviseRecord;
		}
		if (typeof(_cfg.isHiddenTitle) != "undefined") {
			this.isHiddenTitle = _cfg.isHiddenTitle;
		}
		if (typeof(_cfg.isThisEarlyPaymentRecord) != "undefined") {
			this.isThisEarlyPaymentRecord = _cfg.isThisEarlyPaymentRecord;
		}
		if (typeof(_cfg.isThisEarlyPaymentRecordId) != "undefined") {
			this.isThisEarlyPaymentRecordId = _cfg.isThisEarlyPaymentRecordId;
		}
		if (typeof(_cfg.isThisAlterAccrualRecord) != "undefined") {
			this.isThisAlterAccrualRecord = _cfg.isThisAlterAccrualRecord;
		}
		if (typeof(_cfg.isThisAlterAccrualRecordId) != "undefined") {
			this.isThisAlterAccrualRecordId = _cfg.isThisAlterAccrualRecordId;
		}
		if (_cfg.isHiddenPanel) {
			this.isHiddenPanel = _cfg.isHiddenPanel;
		}
		if (_cfg.isHiddenMoney != "undefined") {
			this.isHiddenMoney = _cfg.isHiddenMoney;
		}
		// this.businessType="SmallLoan"
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		SuperviseLeaseFundIntentView.superclass.constructor.call(this, {
			name : "SuperviseLeaseFundIntentView_panel",
			hidden : this.isHiddenPanel,
			region : 'center',
			layout : 'anchor',
			anchor : '100%',
			items : [{
				xtype : 'panel',
				border : false,
				bodyStyle : 'margin-bottom:5px',
				html :this.isChangeTitle==true?'<br/><font class="x-myZW-fieldset-title">（</font>颜色预警：<font color=red>逾期款项</font>&nbsp;&nbsp<font style="line-height:20px">未结清项</font>&nbsp;&nbsp<font color=gray>已结清项</font><font class="x-myZW-fieldset-title" >）：</font>':'<br/><B><font class="x-myZW-fieldset-title">【放款收息表】</font></B><font class="x-myZW-fieldset-title">（</font>颜色预警：<font color=red>逾期款项</font>&nbsp;&nbsp<font style="line-height:20px">未结清项</font>&nbsp;&nbsp<font color=gray>已结清项</font><font class="x-myZW-fieldset-title" >）：</font>',
				hidden : this.isHiddenTitle
			}, this.gridPanel, this.infoPanel]
		});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		var summary = new Ext.ux.grid.GridSummary();
		function totalMoney(v, params, data) {
			return '总计(元)';
		}
		this.datafield = new Ext.form.DateField({
			format : 'Y-m-d',
			allowBlank : false,
			readOnly : this.isHidden1
		});
		this.datafield1 = new Ext.form.DateField({
			format : 'Y-m-d',
			allowBlank : false,
			readOnly : this.isHidden1
		});
		var fundTypenodekey = "";
		if (this.businessType == "Financing") {
			fundTypenodekey = "finaning_fund";
		}
		if (this.businessType == "SmallLoan") {
			fundTypenodekey = "financeType";
		}
		if(this.businessType=="LeaseFinance"){
			fundTypenodekey="leaseFundType"
		}
		this.comboType = new DicIndepCombo({
			editable : false,
			lazyInit : false,
			forceSelection : false,
			nodeKey : fundTypenodekey,
			// itemVale : 1149,
			// itemName : '贷款资金类型',
			readOnly : this.isHidden1
		})
		this.comboType.store.reload();
		this.topbar = new Ext.Toolbar({
			items : [{
				iconCls : 'btn-add',
				text : '生成',
				xtype : 'button',
				scope : this,
				hidden : this.isHiddenautocreateBtn,
				handler : this.autocreate
			}, new Ext.Toolbar.Separator({
				hidden : this.isHiddenautocreateBtn
			}),{
				iconCls : 'btn-add',
				text : '增加',
				xtype : 'button',
				scope : this,
				hidden : this.isHiddenAddBtn,
				handler : this.createRs
			}, new Ext.Toolbar.Separator({
				hidden : this.isHiddenAddBtn
			}), {
				iconCls : 'btn-del',
				text : '删除',
				xtype : 'button',
				scope : this,
				hidden : this.isHiddenDelBtn,
				handler : this.removeSelRs
			}, new Ext.Toolbar.Separator({
				hidden : this.isHiddenDelBtn
			}), {
				iconCls : 'btn-close',
				text : '取消',
				xtype : 'button',
				scope : this,
				hidden : this.isHiddenCanBtn,
				handler : this.cancel
			}, new Ext.Toolbar.Separator({
				hidden : this.isHiddenCanBtn
			}), {
				iconCls : 'btn-reset',
				text : '还原',
				xtype : 'button',
				scope : this,
				hidden : this.isHiddenResBtn,
				handler : this.back
			}, new Ext.Toolbar.Separator({
				hidden : this.isHiddenResBtn
			}), {
				iconCls : 'btn-reset',
				text : '手动对账',
				xtype : 'button',
				scope : this,
				hidden : this.isHiddenResBtn1,
				handler : this.openliushuiwin
			}, new Ext.Toolbar.Separator({
				hidden : this.isHiddenResBtn1
			}), {
				iconCls : 'btn-detail',
				text : '查看单项流水记录',
				xtype : 'button',
				scope : this,
				hidden : this.isHiddenseeqlideBtn,
				handler : function() {
					var selRs = this.gridPanel.getSelectionModel()
							.getSelections();
					if (selRs <= 0) {
						Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
						return;
					} else if (selRs.length > 1) {
						Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
						return;
					}
					this.fundIntentWaterReconciliationInfo.call(this, selRs[0],
							1);
				}
			}, new Ext.Toolbar.Separator({
				hidden : this.isHiddenseeqlideBtn
			}),{
				iconCls : 'btn-details',
				text : '放款',
				xtype : 'button',
				//hidden :this.isFinbtn,
				hidden :true,
				scope : this,
				handler : this.affrim
			},new Ext.Toolbar.Separator({
				hidden : this.isFinbtn
			}),/*{
				iconCls : 'btn-details',
				text : '手动放款',
				xtype : 'button',
				//hidden :this.isFinbtn,
				scope : this,
				handler : this.handAffrim
			},new Ext.Toolbar.Separator({
				hidden : this.isFinbtn
			}),*/ {
				iconCls : 'btn-details',
				text : '项目总流水记录',
				xtype : 'button',
				hidden : this.isHiddenseesumqlideBtn,
				scope : this,
				handler : function() {
					this.projectWaterReconciliationInfo(2);
				}
			}, "->", {
				xtype : 'checkbox',
				boxLabel : '费用相关',
				inputValue : true,
				name : "charge",
				checked : true,
				scope : this,
				handler : function() {
					var charge = this.topbar.getCmpByName("charge");
					var intent = this.topbar.getCmpByName("intent");
					if (charge.getValue() == true && intent.getValue() == true) {
						this.related("all");
					}
					if (charge.getValue() == false && intent.getValue() == true) {
						this.related("intent");
					}
					if (charge.getValue() == true && intent.getValue() == false) {
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
					var charge = this.topbar.getCmpByName("charge");
					var intent = this.topbar.getCmpByName("intent");
					if (charge.getValue() == true && intent.getValue() == true) {
						this.related("all");
					}
					if (charge.getValue() == false && intent.getValue() == true) {
						this.related("intent");
					}
					if (charge.getValue() == true && intent.getValue() == false) {
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
		},{
			name : 'interestStarTime'
		},{
			name : 'interestEndTime'
		},{
			name : 'payintentPeriod'
		},{
			name :'slSuperviseRecordId'
		},{
			name : 'remark'
		}]);
		var url = __ctxPath + "/creditFlow/finance/listSlFundIntent.do";
		if (this.isThisSuperviseRecord != null) {
			url = __ctxPath
					+ "/creditFlow/finance/listbySuperviseRecordSlFundIntent.do?slSuperviseRecordId="
					+ this.slSuperviseRecordId + "&isThisSuperviseRecord="
					+ this.isThisSuperviseRecord + "&isUnLoadData="
					+ this.isUnLoadData;

		}
		if (this.isThisEarlyPaymentRecordId != null) {
			url = __ctxPath
					+ "/creditFlow/finance/listbyEarlyRepaymentRecordSlFundIntent.do?isThisEarlyPaymentRecordId="
					+ this.isThisEarlyPaymentRecordId
					+ "&isThisEarlyPaymentRecord="
					+ this.isThisEarlyPaymentRecord + "&isUnLoadData="
					+ this.isUnLoadData;

		}
		if (this.isThisAlterAccrualRecord != null) {
			url = __ctxPath
					+ "/creditFlow/finance/listbyAlterAccrualRecordSlFundIntent.do?isThisAlterAccrualRecordId="
					+ this.isThisAlterAccrualRecordId
					+ "&isThisAlterAccrualRecord="
					+ this.isThisAlterAccrualRecord + "&isUnLoadData="
					+ this.isUnLoadData;
		}
		var isThisSuperviseRecord = this.isThisSuperviseRecord;
		var isThisEarlyPaymentRecord = this.isThisEarlyPaymentRecord;
		var isThisAlterAccrualRecord = this.isThisAlterAccrualRecord;
		var jStore = new Ext.data.JsonStore({
			url : url,
			root : 'result',
			fields : field
		});
		jStore.load({
			params : {
				projectId : this.projectId,
	     	     flag1:1,
				businessType : this.businessType
			}
		});
		this.projectFundsm = new Ext.grid.CheckboxSelectionModel({
			header : '序号'
		});
		this.gridPanel = new HT.EditorGridPanel({
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
			tbar : this.isChangeTitle==true?null:this.topbar,
			rowActions : false,
			showPaging : false,
			stripeRows : true,
			plain : true,
			loadMask : true,
			autoHeight : true,
			sm : this.projectFundsm,
			plugins : [summary],
			// viewConfig : {
			// forceFit : true,
			// getRowClass : function(record,rowIndex,rowParams,store){
			// if (record.data.notMoney != 0) {
			//				            	
			// }z
			// }
			// },
			columns : [{
				header : 'fundIntentId',
				dataIndex : 'fundIntentId',
				hidden : true
			},{
				header : '期数',
				dataIndex : 'payintentPeriod',
				editor : new Ext.form.ComboBox({
				    triggerAction: 'all',
				    readOnly : this.isHidden1,
				    store: new Ext.data.SimpleStore({
						autoLoad : true,
						url : __ctxPath+ '/project/getPayIntentPeriodSlSmallloanProject.do',
						fields : ['itemId', 'itemName'],
						baseParams : {payintentPeriod:typeof(this.object)!='undefined'?this.object.getCmpByName('slSuperviseRecord.payintentPeriod').getValue():0,projectId:this.slSuperviseRecordId,businessType:'ExhibitionBusiness'}
					}),
				    valueField: 'itemId',
				    displayField: 'itemName'
		
				}),
				renderer : function(value, metaData, record, rowIndex,colIndex, store){
					if(null!=value){
						return '展期第'+value+'期'
					}else{
						
						return ''
						
					}
				}	
			},  {
				header : '资金类型',
				dataIndex : 'fundType',
				editor : this.comboType,
				width : 107,
				summaryType : 'count',
				summaryRenderer : totalMoney,
				renderer : function(value, metaData, record, rowIndex,
						colIndex, store) {

					var objcom = this.editor;
					var objStore = objcom.getStore();

					var idx = objStore.find("dicKey", value);
					var flag1 = 0; // incomeMoney
					if (record.data.payMoney != 0) { // payMoney
						flag1 = 1;
					}
					if (idx != "-1") {
						if (isThisSuperviseRecord != null
								|| isThisEarlyPaymentRecord != null
								|| isThisAlterAccrualRecord != null) {
							if ((flag1 == 1)
									|| (flag1 == 0 && record.data.incomeMoney == record.data.afterMoney)) {
							} else {
								if (record.data.isValid == 1) {
									return '<font style="font-style:italic;text-decoration: line-through;color:gray">'
											+ objStore.getAt(idx).data.itemValue
											+ "</font>"
								} 
//								else {
//									return '<font style="font-style:italic;text-decoration: line-through">'
//											+ objStore.getAt(idx).data.itemValue
//											+ "</font>"
//								}
							}

						}
						if (record.data.isValid == 1) {
							return '<font style="font-style:italic;text-decoration: line-through;color:gray">'
									+ objStore.getAt(idx).data.itemValue
									+ "</font>"
						} else {
							if (record.data.notMoney == 0) {
								return '<font style="color:gray">'
										+ objStore.getAt(idx).data.itemValue
										+ "</font>";
							}
							if (record.data.isOverdue == "是" && flag1 != 1) {

								return '<font style="color:red">'
										+ objStore.getAt(idx).data.itemValue
										+ "</font>";
							}

							if (record.data.afterMoney == 0) {
								return objStore.getAt(idx).data.itemValue;
							}

							return objStore.getAt(idx).data.itemValue;

						}

					} else {

						/*if (record.data.fundTypeName == "") {

							record.data.fundTypeName = value

						} else {
							var x = store.getModifiedRecords();
							if (x != null && x != "") {
								record.data.fundTypeName = value
							}
						}*/
						if (isThisSuperviseRecord != null
								|| isThisEarlyPaymentRecord != null
								|| isThisAlterAccrualRecord != null) {
							if ((flag1 == 1)
									|| (flag1 == 0 && record.data.incomeMoney == record.data.afterMoney)) {

							} else {
								if (record.data.isValid == 1) {
									return '<font style="font-style:italic;text-decoration: line-through;color:gray">'
											+ record.get("fundTypeName")
											+ "</font>"
								} else {
									return  record.get("fundTypeName")/*'<font style="font-style:italic;text-decoration: line-through">'
											+ record.get("fundTypeName")
											+ "</font>"*/
								}

							}

						}
						if (record.data.isValid == 1) {
							return '<font style="font-style:italic;text-decoration: line-through;color:gray">'
									+ record.get("fundTypeName") + "</font>"
						} else {
							if (record.data.notMoney == 0) {
								return '<font style="color:gray">'
										+ record.get("fundTypeName")
										+ "</font>";
							}
							if (record.data.isOverdue == "是" && flag1 != 1) {

								return '<font style="color:red">'
										+ record.get("fundTypeName")
										+ "</font>";
							}

							if (record.data.afterMoney == 0) {
								return record.get("fundTypeName");

							}

							return record.get("fundTypeName");
						}

					}

				}
			}, {
				header : '计划收入金额',
				dataIndex : 'incomeMoney',
				summaryType : 'sum',
				align : 'right',
				editor : {
					xtype : 'numberfield',
					allowBlank : false,
					readOnly : this.isHidden1
				},
				renderer : function(value, metaData, record, rowIndex,
						colIndex, store) {
					var flag1 = 0; // incomeMoney
					if (record.data.payMoney != 0) { // payMoney
						flag1 = 1;
					}
					if (isThisSuperviseRecord != null
							|| isThisEarlyPaymentRecord != null
							|| isThisAlterAccrualRecord != null) {
						if ((flag1 == 1)
								|| (flag1 == 0 && record.data.incomeMoney == record.data.afterMoney)) {
						} else {
							if (record.data.isValid == 1) {
								return '<font style="font-style:italic;text-decoration: line-through;color:gray">'
										+ Ext.util.Format.number(value,
												',000,000,000.00') + "元</font>"
							} 
//							else {
//								return '<font style="font-style:italic;text-decoration: line-through">'
//										+ Ext.util.Format.number(value,
//												',000,000,000.00') + "元</font>"
//							}
						}

					}

					if (record.data.isValid == 1) {
						return '<font style="font-style:italic;text-decoration: line-through;color:gray">'
								+ Ext.util.Format.number(value,
										',000,000,000.00') + "元</font>"
					} else {
						if (record.data.notMoney == 0) {
							return '<font style="color:gray">'
									+ Ext.util.Format.number(value,
											',000,000,000.00') + "元"
									+ "</font>";
						}
						if (record.data.isOverdue == "是" && flag1 != 1) {

							return '<font style="color:red">'
									+ Ext.util.Format.number(value,
											',000,000,000.00') + "元"
									+ "</font>";
						}

						if (record.data.afterMoney == 0) {
							return Ext.util.Format.number(value,
									',000,000,000.00')
									+ "元"

						}

						return Ext.util.Format.number(value, ',000,000,000.00')
								+ "元";
					}

				}
			}, {
				header : '计划支出金额',
				dataIndex : 'payMoney',
				align : 'right',
				summaryType : 'sum',
				editor : {
					xtype : 'numberfield',
					allowBlank : false,
					readOnly : this.isHidden1
				},
				renderer : function(value, metaData, record, rowIndex,
						colIndex, store) {
							//return value;
							
					var flag1 = 0; // incomeMoney
					if (record.data.payMoney != 0) { // payMoney
						flag1 = 1;
					}
					if (isThisSuperviseRecord != null
							|| isThisEarlyPaymentRecord != null
							|| isThisAlterAccrualRecord != null) {
						if ((flag1 == 1)
								|| (flag1 == 0 && record.data.incomeMoney == record.data.afterMoney)) {
						} else {
							if (record.data.isValid == 1) {
								return '<font style="font-style:italic;text-decoration: line-through;color:gray">'
										+ Ext.util.Format.number(value,
												',000,000,000.00') + "元</font>"
							}
//							else {
//								return '<font style="font-style:italic;text-decoration: line-through">'
//										+ Ext.util.Format.number(value,
//												',000,000,000.00') + "元</font>"
//							}
						}

					}
					if (record.data.isValid == 1) {
						return '<font style="font-style:italic;text-decoration: line-through;color:gray">'
								+ Ext.util.Format.number(value,
										',000,000,000.00') + "元</font>"
					} else {
						if (record.data.notMoney == 0) {
							return '<font style="color:gray">'
									+ Ext.util.Format.number(value,
											',000,000,000.00') + "元"
									+ "</font>";
						}
						if (record.data.isOverdue == "是" && flag1 != 1) {

							return '<font style="color:red">'
									+ Ext.util.Format.number(value,
											',000,000,000.00') + "元"
									+ "</font>";
						}

						if (record.data.afterMoney == 0) {
							return Ext.util.Format.number(value,
									',000,000,000.00')
									+ "元";

						}

						return Ext.util.Format.number(value, ',000,000,000.00')
								+ "元";
					}

				}
			},{
				header : '计划到账日',
				dataIndex : 'intentDate',
				format : 'Y-m-d',
				editor : this.datafield,
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
							//return v;
						}
						else{
						v= Ext.util.Format.date(value, 'Y-m-d');
						}
					} catch (err) {
						v = value;
						return v;
					}
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
//							else {
//								return '<font style="font-style:italic;text-decoration: line-through">'
//										+ v + "</font>";
//							}
						}

					}
					if (record.data.isValid == 1) {
						return '<font style="font-style:italic;text-decoration: line-through;color:gray">'
								+ v + "</font>";
					} else {
						if (record.data.notMoney == 0) {
							return '<font style="color:gray">' + v + "</font>";
						}
						if (record.data.isOverdue == "是" && flag1 != 1) {

							return '<font style="color:red">' + v + "</font>";
						}

						if (record.data.afterMoney == 0) {
							return v;

						}
						return v;

					}

				}
			}, {
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
					//		return v;
						}
						else{
						v= Ext.util.Format.date(value, 'Y-m-d');
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
//								else {
//									return '<font style="font-style:italic;text-decoration: line-through">'
//											+ v + "</font>";
//								}
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
							if (record.data.isOverdue == "是" && flag1 != 1) {

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
			}, {
				
				header : '计息开始日期',
				dataIndex : 'interestStarTime',
				format : 'Y-m-d',
				 editor :this.datafield,
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
							//return v;
						}
						else{
						v= Ext.util.Format.date(value, 'Y-m-d');
						}
					} catch (err) {
						v = value;
						return v;
					}
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
//							else {
//								return '<font style="font-style:italic;text-decoration: line-through">'
//										+ v + "</font>";
//							}
						}

					}
					if (record.data.isValid == 1) {
						return '<font style="font-style:italic;text-decoration: line-through;color:gray">'
								+ v + "</font>";
					} else {
						if (record.data.notMoney == 0) {
							return '<font style="color:gray">' + v + "</font>";
						}
						if (record.data.isOverdue == "是" && flag1 != 1) {

							return '<font style="color:red">' + v + "</font>";
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
				 editor :this.datafield,
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
							//return v;
						}
						else{
						v= Ext.util.Format.date(value, 'Y-m-d');
						}
					} catch (err) {
						v = value;
						return v;
					}
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
//							else {
//								return '<font style="font-style:italic;text-decoration: line-through">'
//										+ v + "</font>";
//							}
						}

					}
					if (record.data.isValid == 1) {
						return '<font style="font-style:italic;text-decoration: line-through;color:gray">'
								+ v + "</font>";
					} else {
						if (record.data.notMoney == 0) {
							return '<font style="color:gray">' + v + "</font>";
						}
						if (record.data.isOverdue == "是" && flag1 != 1) {

							return '<font style="color:red">' + v + "</font>";
						}

						if (record.data.afterMoney == 0) {
							return v;

						}
						return v;

					}

				}
			}, {
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
											+ Ext.util.Format.number(value,
													',000,000,000.00')
											+ "元</font>"
								} 
//								else {
//									return '<font style="font-style:italic;text-decoration: line-through">'
//											+ Ext.util.Format.number(value,
//													',000,000,000.00')
//											+ "元</font>"
//								}
							}

						}
						if (record.data.isValid == 1) {
							return '<font style="font-style:italic;text-decoration: line-through;color:gray">'
									+ Ext.util.Format.number(value,
											',000,000,000.00') + "元</font>"
						} else {
							if (record.data.notMoney == 0) {
								return '<font style="color:gray">'
										+ Ext.util.Format.number(value,
												',000,000,000.00') + "元"
										+ "</font>";
							}
							if (record.data.isOverdue == "是" && flag1 != 1) {

								return '<font style="color:red">'
										+ Ext.util.Format.number(value,
												',000,000,000.00') + "元"
										+ "</font>";
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
											+ Ext.util.Format.number(value,
													',000,000,000.00')
											+ "元</font>"
								}
//								else {
//									return '<font style="font-style:italic;text-decoration: line-through">'
//											+ Ext.util.Format.number(value,
//													',000,000,000.00')
//											+ "元</font>"
//								}
							}

						}
						if (record.data.isValid == 1) {
							return '<font style="font-style:italic;text-decoration: line-through;color:gray">'
									+ Ext.util.Format.number(value,
											',000,000,000.00') + "元</font>"
						} else {
							if (record.data.notMoney == 0) {
								return '<font style="color:gray">'
										+ Ext.util.Format.number(value,
												',000,000,000.00') + "元"
										+ "</font>";
							}
							if (record.data.isOverdue == "是" && flag1 != 1) {

								return '<font style="color:red">'
										+ Ext.util.Format.number(value,
												',000,000,000.00') + "元"
										+ "</font>";
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
											+ Ext.util.Format.number(value,
													',000,000,000.00')
											+ "元</font>"
								} 
//								else {
//									return '<font style="font-style:italic;text-decoration: line-through">'
//											+ Ext.util.Format.number(value,
//													',000,000,000.00')
//											+ "元</font>"
//								}
							}

						}
						if (record.data.isValid == 1) {
							return '<font style="font-style:italic;text-decoration: line-through;color:gray">'
									+ Ext.util.Format.number(value,
											',000,000,000.00') + "元</font>"
						} else {
							if (record.data.notMoney == 0) {
								return '<font style="color:gray">'
										+ Ext.util.Format.number(value,
												',000,000,000.00') + "元"
										+ "</font>";
							}
							if (record.data.isOverdue == "是" && flag1 != 1) {

								return '<font style="color:red">'
										+ Ext.util.Format.number(value,
												',000,000,000.00') + "元"
										+ "</font>";
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

			}
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
			, {
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
											+ Ext.util.Format.number(value,
													',000,000,000.000')
											+ "元</font>"
								} 
//								else {
//									return '<font style="font-style:italic;text-decoration: line-through">'
//											+ Ext.util.Format.number(value,
//													',000,000,000.00')
//											+ "元</font>"
//								}
							}

						}
						if (record.data.isValid == 1) {
							return '<font style="font-style:italic;text-decoration: line-through;color:gray">'
									+ Ext.util.Format.number(value,
											',000,000,000.000') + "%/日</font>"
						} else {
							if (record.data.notMoney == 0) {
								return '<font style="color:gray">'
										+ Ext.util.Format.number(value,
												',000,000,000.000') + "%/日"
										+ "</font>";
							}
							if (record.data.isOverdue == "是") {

								return '<font style="color:red">'
										+ Ext.util.Format.number(value,
												',000,000,000.000') + "%/日"
										+ "</font>";
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
											+ Ext.util.Format.number(value,
													',000,000,000.00')
											+ "元</font>"
								} 
//								else {
//									return '<font style="font-style:italic;text-decoration: line-through">'
//											+ Ext.util.Format.number(value,
//													',000,000,000.00')
//											+ "元</font>"
//								}
							}

						}
						if (record.data.isValid == 1) {
							return '<font style="font-style:italic;text-decoration: line-through;color:gray">'
									+ Ext.util.Format.number(value,
											',000,000,000.00') + "元</font>"
						} else {
							if (record.data.notMoney == 0) {
								return '<font style="color:gray">'
										+ Ext.util.Format.number(value,
												',000,000,000.00') + "元"
										+ "</font>";
							}
							if (record.data.isOverdue == "是") {

								return '<font style="color:red">'
										+ Ext.util.Format.number(value,
												',000,000,000.00') + "元"
										+ "</font>";
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
			},{
				header : '备注',
				dataIndex : 'remark',
				editor : {
					xtype : 'textfield'
				}
			}],
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
					
					/*if (e.record.data['fundType'] == 'principalLending') {
						e.cancel = true;
					}*/
				},
				afteredit : function(e) {
					if (e.record.data['fundType'] == 'rentalCostsPaid'
						|| e.record.data['fundType'] =='marginRefund') {
						e.record.set('incomeMoney', 0);
						e.record.commit();
					} else {
						e.record.set('payMoney', 0);
						e.record.commit()
					}
				}
			}
		});
		this.infoPanel = new Ext.Panel({
			border : false,
			layout : {
				type : 'hbox',
				pack : 'left'
			},
			defaults : {
				margins : '10 10 0 0'
			},
			name : "infoPanel",
			hidden : this.isHiddenInfoPanel==false?false:true,
			items : []
		});
		this.fillDatas(this.projectId, this.businessType);
		// 为infoPanel添加数据
		/*if (this.isThisSuperviseRecord == null
				|| isThisEarlyPaymentRecord == null
				|| isThisAlterAccrualRecord != null) {
			this.fillDatas(this.projectId, this.businessType);
		}*/

	},// end of the initComponents()

	createRs : function() {
		//var payintentPeriod=this.object.getCmpByName("slSmallloanProject.payintentPeriod").getValue();
		var payintentPeriod = (typeof(this.object)!='undefined'?this.object.getCmpByName('slSuperviseRecord.payintentPeriod').getValue():this.payintentPeriod);
		this.datafield.setValue('');
		var newRecord = this.gridPanel.getStore().recordType;
		var newData = new newRecord({
			fundIntentId : '',
			fundType : '',
			payMoney : 0,
			incomeMoney : 0,
			flatMoney : 0,
			afterMoney : 0,
			intentDate : new Date(),
			factDate : '',
			payintentPeriod:payintentPeriod
		});
		var combox=new Ext.form.ComboBox({
		    triggerAction: 'all',
		    store: new Ext.data.SimpleStore({
				autoLoad : true,
				url : __ctxPath+ '/project/getPayIntentPeriodSlSmallloanProject.do',
				fields : ['itemId', 'itemName'],
				baseParams : {payintentPeriod:payintentPeriod}
			}),
		    valueField: 'itemId',
		    displayField: 'itemName'

		})
		this.gridPanel.getColumnModel().setEditor(3,combox);		
		this.gridPanel.stopEditing();
		this.gridPanel.getStore().insert(this.gridPanel.getStore().getCount(),
				newData);
		this.gridPanel.getView().refresh();
		this.gridPanel.getSelectionModel().selectRow((this.gridPanel.getStore()
				.getCount() - 1));
		this.gridPanel.startEditing(0, 0);
	},

	getGridDate : function() {
						var vRecords = this.gridPanel.getStore().getRange(0,
								this.gridPanel.getStore().getCount()); // 得到修改的数据（记录对象）
						var vCount = vRecords.length; // 得到记录长度
						var vDatas = '';
						if (vCount > 0) {
						var st="";
						for ( var i = 0; i < vCount; i++) {
							if(vRecords[i].data.fundType !=null && vRecords[i].data.fundType !=""){
								 if(null==vRecords[i].data.afterMoney || vRecords[i].data.afterMoney==0)  {
											  st={"fundType":vRecords[i].data.fundType,"incomeMoney":vRecords[i].data.incomeMoney,"payMoney":vRecords[i].data.payMoney,"intentDate":vRecords[i].data.intentDate,"remark":vRecords[i].data.remark,"payintentPeriod":vRecords[i].data.payintentPeriod,"interestStarTime":vRecords[i].data.interestStarTime,"interestEndTime":vRecords[i].data.interestEndTime};
											vDatas += Ext.util.JSON
													.encode(st) + '@';
									 }/*else{
									    if(vRecords[i].data.afterMoney==0){
									     st={"fundIntentId":vRecords[i].data.fundIntentId,"fundType":vRecords[i].data.fundType,"incomeMoney":vRecords[i].data.incomeMoney,"payMoney":vRecords[i].data.payMoney,"intentDate":vRecords[i].data.intentDate,"remark":vRecords[i].data.remark,"payintentPeriod":vRecords[i].data.payintentPeriod,"interestStarTime":vRecords[i].data.interestStarTime,"interestEndTime":vRecords[i].data.interestEndTime};
											vDatas += Ext.util.JSON
												.encode(st) + '@';
									 	}
									 }*/
						 
							   }
							}
							
							vDatas = vDatas.substr(0, vDatas.length - 1);
						}
						//Ext.getCmp('fundIntentJsonData2').setValue(vDatas);
//						this.gridPanel.getColumnsgetEditor().getCmpByName('fundIntentJsonData').setValue(vDatas);
//						alert(this.gridPanel.getEditor().getCmpByName('fundIntentJsonData').getValue());
						return vDatas;
					},
						getGridData : function() {
						var vRecords = this.gridPanel.getStore().getRange(0,
								this.gridPanel.getStore().getCount()); // 得到修改的数据（记录对象）
						var vCount = vRecords.length; // 得到记录长度
						var vDatas = '';
						if (vCount > 0) {
						var st="";
						for ( var i = 0; i < vCount; i++) {
							if(vRecords[i].data.fundType !=null && vRecords[i].data.fundType !=""){
								 if(null==vRecords[i].data.afterMoney || vRecords[i].data.afterMoney==0)  {
											  st={"fundType":vRecords[i].data.fundType,"incomeMoney":vRecords[i].data.incomeMoney,"payMoney":vRecords[i].data.payMoney,"intentDate":vRecords[i].data.intentDate,"payintentPeriod":vRecords[i].data.payintentPeriod};
											vDatas += Ext.util.JSON
													.encode(st) + '@';
									 }/*else{
									    if(vRecords[i].data.afterMoney==0){
									     st={"fundIntentId":vRecords[i].data.fundIntentId,"fundType":vRecords[i].data.fundType,"incomeMoney":vRecords[i].data.incomeMoney,"payMoney":vRecords[i].data.payMoney,"intentDate":vRecords[i].data.intentDate,"remark":vRecords[i].data.remark,"payintentPeriod":vRecords[i].data.payintentPeriod,"interestStarTime":vRecords[i].data.interestStarTime,"interestEndTime":vRecords[i].data.interestEndTime};
											vDatas += Ext.util.JSON
												.encode(st) + '@';
									 	}
									 }*/
						 
							   }
							}
							
							vDatas = vDatas.substr(0, vDatas.length - 1);
						}
						//Ext.getCmp('fundIntentJsonData2').setValue(vDatas);
//						this.gridPanel.getColumnsgetEditor().getCmpByName('fundIntentJsonData').setValue(vDatas);
//						alert(this.gridPanel.getEditor().getCmpByName('fundIntentJsonData').getValue());
						return vDatas;
					},
					//放款按钮调用方法
						affrim:function(){
							var panel =this;
							var toolbar=this.gridPanel.getTopToolbar();
							var store =this.gridPanel.getStore();
						Ext.Ajax.request( {
									url : __ctxPath + '/webservice/dataSubmitFundIntentPrincipalLending.do',
									method : 'POST',
									scope : this,
									params : {
										projectId : this.projectId,
										businessType :this.businessType
									},
									success : function(response, request) {
											var object=Ext.util.JSON.decode(response.responseText);
											var sub=object.sub;
											var flag = object.flag;
											var tip = object.flag1;
											var result=object.result;
											if(sub==1){//用于判断本金放贷信息是否成功生成： 0表示没有生成；1表示生成
												if(flag==1){//用于判断发送数据是否成功准备 ： 0表示未成功；1表示生成
													if(tip ==1){//用于判断发送本金放贷信息是否成功 发送： 0表示未成功；1表示成功
														Ext.Msg.alert('放款信息', result);
														store.reload();
														toolbar.get(10).setVisible(true)
														toolbar.get(11).setVisible(true)
														toolbar.get(14).setVisible(false)
														toolbar.get(13).setVisible(false)
														panel.doLayout();
													}else if(tip ==2){
																Ext.Msg.confirm("提示!", result, function(btn) {
																	if (btn == "yes") {
																		toolbar.get(10).setVisible(true)
																		toolbar.get(11).setVisible(true)
																		toolbar.get(14).setVisible(false)
																		toolbar.get(13).setVisible(false)
																		panel.handAffrim.call(panel);
																		store.reload();
																	}else{
																		store.reload();
																		panel.doLayout();
																	}
																});
													}else {
														Ext.Msg.alert('出错信息', result);

														
													}
												}else{
													Ext.Msg.alert('数据抓取信息', result);
												}
											}else{
												Ext.Msg.alert('生成本金贷款信息', result);
												
											}
									}
								});
					},
					handAffrim:function(){
							var panel =this;
							var toolbar=this.gridPanel.getTopToolbar();
							var store =this.gridPanel.getStore();
						Ext.Ajax.request( {
									url : __ctxPath + '/webservice/handDateSubmitFundIntentPrincipalLending.do',
									method : 'POST',
									scope : this,
									params : {
										projectId : this.projectId,
										businessType :this.businessType
									},
									success : function(response, request) {
											var object=Ext.util.JSON.decode(response.responseText);
											var sub=object.sub;
											var result=object.result;
											if(sub==1){//用于判断本金放贷信息是否成功生成： 0表示没有生成；1表示生成
													//Ext.Msg.alert('放款信息', result);
											}else{
												Ext.Msg.alert('生成本金贷款信息', result);
											}
									}
								});
					},
	// 保存数据
	saveRs : function() {
		var data = this.getGridDate();
		Ext.Ajax.request({
			url : __ctxPath + '/creditFlow/finance/savejson1SlFundIntent.do',
			method : 'POST',
			scope : this,
			params : {
				slFundIentJson : data,
				projectId : this.projectId,
				businessType : this.businessType
			},
			success : function(response, request) {
				this.gridPanel.getStore().reload();
				this.gridPanel.getView().refresh();
				this.fillDatas(this.projectId, this.businessType);
			}
		});

	},
	save : function() {
		var gridstore = this.gridPanel.getStore();
			var o = gridstore.params;
			var projectId = this.projectId;
			var businessType = this.businessType
			gridstore.on('beforeload', function(gridstore, o) {
				var new_params = {
				projectId : projectId,
				businessType : businessType,
					'flag1':1
				};
				Ext.apply(o.params, new_params);
			});
			this.gridPanel.getStore().setBaseParam('flag1',1) ; 
		this.gridPanel.getStore().reload();
		this.gridPanel.getView().refresh();
		//this.fillDatas(this.projectId, this.businessType);
	},
	notcheck : function() {
		// this.gridPanel.getStore().reload();
		var vRecords = this.gridPanel.getStore().getRange(0,
				this.gridPanel.getStore().getCount());
		var vCount = vRecords.length;
		var intentnotMoney = 0;
		if (vCount > 0) {
			for (var i = 0; i < vCount; i++) {
				intentnotMoney += vRecords[i].data.notMoney;
			}
		}
		return intentnotMoney;
	},

	cancel : function() {

		var gridPanel = this.gridPanel;
		var projId = this.projectId;
		var businessType = this.businessType;
		// var fill=this.fillDatas(projId);
		var inforpanel = this.getCmpByName("infoPanel")
		var fill1 = this.fillDatas1;
		var selRs = gridPanel.getSelectionModel().getSelections();
		if (selRs <= 0) {
			Ext.ux.Toast.msg('操作信息', '请选择要取消的记录');
			return false;
		};
		var deleteFun = function(url, prame, sucessFun, i, j) {
			Ext.Ajax.request({
				url : url,
				method : 'POST',
				success : function(response) {
					if (response.responseText.trim() == '{success:true}') {
						if (i == (j - 1)) {
							Ext.ux.Toast.msg('操作信息', '取消成功!');

						}
						var s = gridPanel.getSelectionModel().getSelections();
						for (var i = 0; i < s.length; i++) {
							var row = s[i];
							if (row.data.fundIntentId == null
									|| row.data.fundIntentId == '') {
							} else {
								row.data.isValid = 1;
							}
						}
						gridPanel.getView().refresh();
						fill1(inforpanel, gridPanel, businessType);

					} else if (response.responseText.trim() == '{success:false}') {
						Ext.ux.Toast.msg('状态', '取消失败!');
					}
				},
				params : prame
			});
		};

		Ext.Msg.confirm("提示!", '确定要取消吗？', function(btn) {
			if (btn == "yes") {
				var s = gridPanel.getSelectionModel().getSelections();
				for (var i = 0; i < s.length; i++) {
					var row = s[i];
					if (row.data.fundIntentId == null
							|| row.data.fundIntentId == '') {
						// gridPanel.getStore().remove(row);
					} else {

						deleteFun(__ctxPath
								+ '/creditFlow/finance/cancelSlFundIntent.do',
								{
									fundIntentId : row.data.fundIntentId,
									projectId : projId,
									businessType : this.businessType
								}, function() {
									gridPanel.getStore().reload();
								}, i, s.length);

					}

				}

			}
		});

	},
	back : function() {
		var gridPanel = this.gridPanel;
		var projId = this.projectId;
		var businessType = this.businessType;
		var inforpanel = this.getCmpByName("infoPanel")
		var fill2 = this.fillDatas2;
		var selRs = gridPanel.getSelectionModel().getSelections();
		if (selRs <= 0) {
			Ext.ux.Toast.msg('操作信息', '请选择要还原的记录');
			return false;
		};
		var deleteFun = function(url, prame, sucessFun, i, j) {
			Ext.Ajax.request({
				url : url,
				method : 'POST',
				success : function(response) {
					if (response.responseText.trim() == '{success:true}') {
						if (i == (j - 1)) {
							Ext.ux.Toast.msg('操作信息', '还原成功!');
						}
						var s = gridPanel.getSelectionModel().getSelections();
						for (var i = 0; i < s.length; i++) {
							var row = s[i];
							if (row.data.fundIntentId == null
									|| row.data.fundIntentId == '') {
							} else {
								row.data.isValid = 0;
							}
						}
						gridPanel.getView().refresh();
						fill2(inforpanel, gridPanel, businessType);
					} else if (response.responseText.trim() == '{success:false}') {
						Ext.ux.Toast.msg('状态', '还原失败!');
					}
				},
				params : prame
			});
		};

		Ext.Msg.confirm("提示!", '确定要还原吗？', function(btn) {
			if (btn == "yes") {
				var s = gridPanel.getSelectionModel().getSelections();
				for (var i = 0; i < s.length; i++) {
					var row = s[i];
					if (row.data.fundIntentId == null
							|| row.data.fundIntentId == '') {
						// gridPanel.getStore().remove(row);
					} else {
						deleteFun(__ctxPath
								+ '/creditFlow/finance/backSlFundIntent.do', {
							fundIntentId : row.data.fundIntentId,
							projectId : projId,
							businessType : this.businessType
						}, function() {
							gridPanel.getStore().reload();
						}, i, s.length);
					}
				}
			}
		});

	},
	removeSelRs : function() {
		var gridPanel = this.gridPanel;
		var projId = this.projectId;
		var businessType = this.businessType;
		var inforpanel = this.getCmpByName("infoPanel")
		var fill1 = this.fillDatas1;
		var selRs = gridPanel.getSelectionModel().getSelections();
		if (selRs <= 0) {
			Ext.ux.Toast.msg('操作信息', '请选择要删除的记录');
			return false;
		};
		var deleteFun = function(url, prame, sucessFun, i, j) {
			Ext.Ajax.request({
				url : url,
				method : 'POST',
				success : function(response) {
					if (response.responseText.trim() == '{success:true}') {
						if (i == (j - 1)) {
							Ext.ux.Toast.msg('操作信息', '删除成功!');
						}
						fill1(inforpanel, gridPanel, businessType);
						sucessFun();

					} else if (response.responseText.trim() == '{success:false}') {
						Ext.ux.Toast.msg('状态', '删除失败!');
					}
				},
				params : prame
			});
		};

		Ext.Msg.confirm("提示!", '确定要删除吗？', function(btn) {
			if (btn == "yes") {
				var s = gridPanel.getSelectionModel().getSelections();
				for (var i = 0; i < s.length; i++) {
					var row = s[i];
					if (row.data.fundIntentId == null
							|| row.data.fundIntentId == '') {
						gridPanel.getStore().remove(row);
					} else {
						deleteFun(__ctxPath
								+ '/creditFlow/finance/deleteSlFundIntent.do',
								{
									fundIntentId : row.data.fundIntentId,
									projectId : projId,
									businessType : this.businessType
								}, function() {
									gridPanel.getStore().remove(row);
									gridPanel.getStore().reload();
								}, i, s.length);
					}
				}
			}
		});
	},

	// 查看款项流水对账
	fundIntentWaterReconciliationInfo : function(record, flag) {
		var hidden = false;
		if (record.data.fundType == "principalLending") {
			hidden = true;
		}
		new detailView({
			fundIntentId : record.get("fundIntentId"),
			flag : flag,// 1.款项流水2.项目流水,
			fundType : record.data.fundType,
			hidden1 : hidden,
			hidden2 : true
		}).show();
	},

	// 查看项目流水对账
	projectWaterReconciliationInfo : function(flag) {
		new detailView({
			projectId : this.projectId,
			flag : flag,// 1.款项流水2.项目流水
			hidden1 : false,
			hidden2 : true,
			businessType : this.businessType
		}).show();
	},

	// 为infoPanel添加数据
	fillDatas : function(projId, businessType) {
		Ext.Ajax.request({
			url :  __ctxPath+ '/creditFlow/finance/leaseFundIntentInfoSlFundIntent.do?businessType='+ this.businessType+'&projectId='+projId,
			method : 'POST',
			scope : this,
			success : function(response, request) {
				var obj = Ext.util.JSON.decode(response.responseText);
				var inforpanel = this.getCmpByName("infoPanel")
				inforpanel.removeAll();
				var wszj=obj.wszj
				var yszj=obj.yszj
				var sjcze=obj.sjcze
				var wfbj=obj.wfbj
				inforpanel.add({
						xtype : 'label',
						html : "<span style='line-height:2.2'>总计提示: <font class='x-myZW-fieldset-title'></font>实际出资额："
								+ Ext.util.Format.number(sjcze,
												',000,000,000.00')
								+ "元&nbsp;&nbsp;未收租金："
								+ Ext.util.Format.number(wszj,
												',000,000,000.00')
								+ "元&nbsp;&nbsp;已收租金："
								+ Ext.util.Format.number(yszj,
												',000,000,000.00')
								+ "元&nbsp;&nbsp;未付本金："
								+ Ext.util.Format.number(wfbj,
												',000,000,000.00')
								+ "元</span><font class='x-myZW-fieldset-title'></font><br/><font class='x-myZW-fieldset-title'></font><span><font color=red>逾期提示</font>：逾期款项共计<font color=red>"
								+ obj.yqcount
								+ "</font>笔 &nbsp;&nbsp;逾期未还款项总额<font color=red>"
								+ Ext.util.Format.number(obj.money,
												',000,000,000.00')
								+ "</font>元 </span><font class='x-myZW-fieldset-title'></font>"
					});
					inforpanel.doLayout()
				/*
				this.infoObj = Ext.util.JSON.decode(response.responseText);
				var inforpanel = this.getCmpByName("infoPanel")
				inforpanel.removeAll();
				slprojectMoney = this.infoObj.slProjectMoney;
				slnotprojectMoney = this.infoObj.slProjectMoneyBalance;
				if (businessType == 'SmallLoan') {
					inforpanel.add({
						xtype : 'label',
						html : "<span style='line-height:2.2'>总计提示: <font class='x-myZW-fieldset-title'></font>贷款本金总额："
								+ Ext.util.Format.number(this.infoObj.slProjectMoney,
												',000,000,000.00')
								+ "元&nbsp;&nbsp;未还贷款本金："
								+ Ext.util.Format.number(this.infoObj.slProjectMoneyBalance,
												',000,000,000.00')
								+ "元&nbsp;&nbsp;应收费用总额："
								+ Ext.util.Format.number(this.infoObj.slProjectIncomeintentsum,
												',000,000,000.00')
								+ "元&nbsp;&nbsp;未收费用总额："
								+ Ext.util.Format.number(this.infoObj.slProjectNotincomeintentsum,
												',000,000,000.00')
								+ "元</span><font class='x-myZW-fieldset-title'></font><br/><font class='x-myZW-fieldset-title'></font><span><font color=red>逾期提示</font>：逾期未还款项共计<font color=red>"
								+ Ext.util.Format.number(this.infoObj.slProjectoverduecount,
												',000,000,000.00')
								+ "</font>笔 &nbsp;&nbsp;逾期未还款项总额<font color=red>"
								+ Ext.util.Format.number(this.infoObj.slProjectoverdueintent,
												',000,000,000.00')
								+ "</font>元 &nbsp;&nbsp;逾期违约金总额<font color=red>"
								+ Ext.util.Format.number(this.infoObj.slProjectoverduebreakMoneysum,
												',000,000,000.00')
								+ "</font>元</span><font class='x-myZW-fieldset-title'></font>"
					});
				} else if (businessType == 'Financing') {
					inforpanel.add({
						xtype : 'label',
						html : "<span style='line-height:2.2'>总计提示: <font class='x-myZW-fieldset-title'></font>借款本金总额："
								+  Ext.util.Format.number(this.infoObj.flProjectMoney,',000,000,000.00')
								+ "元&nbsp;&nbsp;未还借款本金："
								+ Ext.util.Format.number(this.infoObj.flProjectMoneyBalance,',000,000,000.00')
								+ "&nbsp;&nbsp;应付费用总额："
								+ Ext.util.Format.number(this.infoObj.flProjectChargeMoney,',000,000,000.00')
								+ "元&nbsp;&nbsp;未付费用总额："
								+ Ext.util.Format.number(this.infoObj.flProjectChargeMoneyBalance,',000,000,000.00')
								+ "元</span><font class='x-myZW-fieldset-title'></font><br/><font class='x-myZW-fieldset-title'></font><span>"
					});
				}
				inforpanel.doLayout()
			*/},
			failure : function(response) {
				Ext.ux.Toast.msg('状态', '请求失败，请重试');
			}
		})
	},
	fillDatas1 : function(inforpanel, gridPanel, businessType) {
		var s = gridPanel.getSelectionModel().getSelections();
		var vRecords = gridPanel.getStore().getRange(0,
				gridPanel.getStore().getCount());
		var vCount = vRecords.length;
		var vCount1 = s.length;
		var s1 = 0;
		var s2 = 0;
		var s3 = 0;
		var s4 = 0;
		var s5 = 0;
		var s6 = 0;
		var s7 = 0;
		var s8 = 0;
		if (vCount > 0 && vCount1 > 0) {
			for (var i = 0; i < vCount; i++) {
				var k = 0;
				for (var j = 0; j < vCount1; j++) {
					if (s[j].data.fundIntentId == vRecords[i].data.fundIntentId) {
						k++;

						if (businessType == "SmallLoan"
								&& vRecords[i].data.afterMoney != 0) {
							if (vRecords[i].data.fundType == "loanInterest"
									|| vRecords[i].data.fundType == "consultationMoney"
									|| vRecords[i].data.fundType == "serviceMoney") {
								var s3 = s3 + vRecords[i].data.afterMoney
										* 1000;
							}

						}

					}

				}
				if (k == 0 && vRecords[i].data.fundIntentId != null
						&& vRecords[i].data.fundIntentId != ""
						&& vRecords[i].data.isValid == 1) {

					if (businessType == "SmallLoan"
							&& vRecords[i].data.afterMoney != 0) {
						if (vRecords[i].data.fundType == "loanInterest"
								|| vRecords[i].data.fundType == "consultationMoney"
								|| vRecords[i].data.fundType == "serviceMoney") {
							var s3 = s3 + vRecords[i].data.afterMoney * 1000;
						}

					}
				}
				if (k == 0 && vRecords[i].data.fundIntentId != null
						&& vRecords[i].data.fundIntentId != ""
						&& vRecords[i].data.isValid == 0) {
					if (businessType == "SmallLoan") {
						if (vRecords[i].data.fundType == "loanInterest"
								|| vRecords[i].data.fundType == "consultationMoney"
								|| vRecords[i].data.fundType == "serviceMoney") {
							var s3 = s3 + vRecords[i].data.incomeMoney * 1000;
							var s5 = s5 + vRecords[i].data.notMoney * 1000;
						}
						if (vRecords[i].data.fundType == "principalRepayment") {
							var s2 = s2 + vRecords[i].data.incomeMoney * 1000;
							var s4 = s4 + vRecords[i].data.notMoney * 1000;
						}
						if (vRecords[i].data.fundType != "principalLending") {
							if (vRecords[i].data.notMoney != 0
									&& vRecords[i].data.isOverdue == "是") {
								var s6 = s6 + 1;
								var s7 = s7 + vRecords[i].data.incomeMoney
										* 1000;
								s1 = s1 + vRecords[i].data.accrualMoney * 1000;
							}
						}
					} else if (businessType == "Financing") {
						if (vRecords[i].data.fundType == "Financingborrow") {
							s1 = s1 + vRecords[i].data.incomeMoney * 1000;
						}
						if (vRecords[i].data.fundType == "FinancingRepay") {
							s2 = s2 + vRecords[i].data.payMoney * 1000;
						}
						if (vRecords[i].data.fundType == "FinancingInterest"
								|| vRecords[i].data.fundType == "financingconsultationMoney"
								|| vRecords[i].data.fundType == "financingserviceMoney") {
							s3 = s3 + vRecords[i].data.payMoney * 1000;
							s4 = s4 + vRecords[i].data.afterMoney * 1000;
						}
					}
				}

			}
		}
		inforpanel.removeAll();
		/*if (businessType == 'SmallLoan') {
			inforpanel.add({
				xtype : 'label',
				html : "<span style='line-height:2.2'>总计提示: <font class='x-myZW-fieldset-title'></font>贷款本金总额："
						+ slprojectMoney
						+ "元&nbsp;&nbsp;未还贷款本金："
						+ slnotprojectMoney
						+ "元&nbsp;&nbsp;应收费用总额："
						+ s3
						/ 1000
						+ "元&nbsp;&nbsp;未收费用总额："
						+ s5
						/ 1000
						+ "元</span><font class='x-myZW-fieldset-title'></font><br/><font class='x-myZW-fieldset-title'></font><span><font color=red>逾期提示</font>：逾期未还款项共计<font color=red>"
						+ s6
						+ "</font>笔 &nbsp;&nbsp;逾期未还款项总额<font color=red>"
						+ s7
						/ 1000
						+ "</font>元 &nbsp;&nbsp;逾期违约金总额<font color=red>"
						+ s1
						/ 1000
						+ "</font>元</span><font class='x-myZW-fieldset-title'></font>"
			});
		} else if (businessType == 'Financing') {
			inforpanel.add({
				xtype : 'label',
				html : "<span style='line-height:2.2'>总计提示: <font class='x-myZW-fieldset-title'></font>借款本金总额："
						+ s1
						/ 1000
						+ "元&nbsp;&nbsp;未还借款本金："
						+ (s1 - s2)
						/ 1000
						+ "&nbsp;&nbsp;应付费用总额："
						+ s3
						/ 1000
						+ "元&nbsp;&nbsp;未付费用总额："
						+ (s3 - s4)
						/ 1000
						+ "元</span><font class='x-myZW-fieldset-title'></font><br/><font class='x-myZW-fieldset-title'></font><span>"
			});
		}
		inforpanel.doLayout()*/
	},
	fillDatas2 : function(inforpanel, gridPanel, businessType) {
		var s = gridPanel.getSelectionModel().getSelections();
		var vRecords = gridPanel.getStore().getRange(0,
				gridPanel.getStore().getCount());
		var vCount = vRecords.length;
		var vCount1 = s.length;
		var s1 = 0;
		var s2 = 0;
		var s3 = 0;
		var s4 = 0;
		var s5 = 0;
		var s6 = 0;
		var s7 = 0;
		var s8 = 0;
		if (vCount > 0) {
			for (var i = 0; i < vCount; i++) {

				if (vRecords[i].data.fundIntentId != null
						&& vRecords[i].data.fundIntentId != ""
						&& vRecords[i].data.isValid == 1) {
					if (businessType == "SmallLoan") {
						if (vRecords[i].data.fundType == "loanInterest"
								|| vRecords[i].data.fundType == "consultationMoney"
								|| vRecords[i].data.fundType == "serviceMoney") {
							var s3 = s3 + vRecords[i].data.afterMoney * 1000;
						}
					}
				}
				if (vRecords[i].data.fundIntentId != null
						&& vRecords[i].data.fundIntentId != ""
						&& vRecords[i].data.isValid == 0) {
					if (businessType == "SmallLoan") {
						if (vRecords[i].data.fundType == "loanInterest"
								|| vRecords[i].data.fundType == "consultationMoney"
								|| vRecords[i].data.fundType == "serviceMoney") {
							var s3 = s3 + vRecords[i].data.incomeMoney * 1000;
							var s5 = s5 + vRecords[i].data.notMoney * 1000;
						}
						if (vRecords[i].data.fundType == "principalRepayment") {
							var s2 = s2 + vRecords[i].data.incomeMoney * 1000;
							var s4 = s4 + vRecords[i].data.notMoney * 1000;
						}
						if (vRecords[i].data.fundType != "principalLending") {

							if (vRecords[i].data.notMoney != 0
									&& vRecords[i].data.isOverdue == "是") {
								var s6 = s6 + 1;
								var s7 = s7 + vRecords[i].data.incomeMoney
										* 1000;
								s1 = s1 + vRecords[i].data.accrualMoney * 1000;
							}
						}
					} else if (businessType == "Financing") {
						if (vRecords[i].data.fundType == "Financingborrow") {
							s1 = s1 + vRecords[i].data.incomeMoney * 1000;
						}
						if (vRecords[i].data.fundType == "FinancingRepay") {
							s2 = s2 + vRecords[i].data.payMoney * 1000;
						}
						if (vRecords[i].data.fundType == "FinancingInterest"
								|| vRecords[i].data.fundType == "financingconsultationMoney"
								|| vRecords[i].data.fundType == "financingserviceMoney") {
							s3 = s3 + vRecords[i].data.payMoney * 1000;
							s4 = s4 + vRecords[i].data.afterMoney * 1000;
						}
					}

				}

			}
		}

		// if (vCount1 > 0 ) {
		// for ( var j = 0; j < vCount1; j++) {
		// if( s[j].data.fundIntentId != null && s[j].data.fundIntentId != "" )
		// {
		// if(s[j].data.fundType==1744){
		// var s3=s3+s[j].data.incomeMoney;
		// var s5=s5+s[j].data.notMoney;
		// }
		// if(s[j].data.fundType==1747){
		// var s2=s2+s[j].data.incomeMoney;
		// var s4=s4+s[j].data.notMoney;
		// }
		// if(s[j].data.fundType != 1748){
		// if(s[j].data.notMoney !=0 &&s[j].data.isOverdue =="是") {
		// var s6=s6+1;
		// var s7=s7+s[j].data.incomeMoney;
		// }
		// }
		// }
		//					           	
		// }
		// }
		inforpanel.removeAll();
		if (businessType == 'SmallLoan') {
			inforpanel.add({
				xtype : 'label',
				html : "<span style='line-height:2.2'>总计提示: <font class='x-myZW-fieldset-title'></font>贷款本金总额："
						+ slprojectMoney
						+ "元&nbsp;&nbsp;未还贷款本金："
						+ slnotprojectMoney
						+ "元&nbsp;&nbsp;应收费用总额："
						+ s3
						/ 1000
						+ "元&nbsp;&nbsp;未收费用总额："
						+ s5
						/ 1000
						+ "元</span><font class='x-myZW-fieldset-title'></font><br/><font class='x-myZW-fieldset-title'></font><span><font color=red>逾期提示</font>：逾期未还款项共计<font color=red>"
						+ s6
						+ "</font>笔 &nbsp;&nbsp;逾期未还款项总额<font color=red>"
						+ s7
						/ 1000
						+ "</font>元 &nbsp;&nbsp;逾期违约金总额<font color=red>"
						+ s1
						/ 1000
						+ "</font>元</span><font class='x-myZW-fieldset-title'></font>"
			});
		} else if (businessType == 'Financing') {
			inforpanel.add({
				xtype : 'label',
				html : "<span style='line-height:2.2'>总计提示: <font class='x-myZW-fieldset-title'></font>借款本金总额："
						+ s1
						/ 1000
						+ "元&nbsp;&nbsp;未还借款本金："
						+ (s1 - s2)
						/ 1000
						+ "&nbsp;&nbsp;应付费用总额："
						+ s3
						/ 1000
						+ "元&nbsp;&nbsp;未付费用总额："
						+ (s3 - s4)
						/ 1000
						+ "元</span><font class='x-myZW-fieldset-title'></font><br/><font class='x-myZW-fieldset-title'></font><span>"
			});
		}
		inforpanel.doLayout()
	},
	onRowAction : function(grid, record, action, row, col) {
		this.fundIntentWaterReconciliationInfo.call(this, record, 1);
	},
	related : function(flag) {
		var gridstore = this.gridPanel.getStore();
		var o = gridstore.params;
		var projectId = this.projectId;
		var businessType = this.businessType
		gridstore.on('beforeload', function(gridstore, o) {
			var new_params = {
				projectId : projectId,
				businessType : businessType,
				relateIntentOrCharge : flag
			};
			Ext.apply(o.params, new_params);
		});
		this.gridPanel.getStore().reload();
	},
	oneopenliushuiwin : function(){
		
		var s = this.gridPanel.getSelectionModel().getSelections();
		var	record=s[0];
	     var flag=0;            //incomeMoney
	     if(record.data.payMoney !=0){   //payMoney
	     	flag=1;
	     }
	     if(record.data.fundIntentId ==null){
	     		Ext.ux.Toast.msg('操作信息','请先保存');
	     }else{
			new SlFundIntentForm({
				fundIntentId : record.data.fundIntentId,
				fundType : record.data.fundType,
				notMoney : record.data.notMoney,
				flag:flag,
				businessType :record.data.businessType,
				companyId:record.data.companyId,
				otherPanel : this.gridPanel
			}).show();
	     }
		
	},
	manyInntentopenliushuiwin : function(){
		
		var s = this.gridPanel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息','请选中记录');
			return false;
		}else{
	    var a=0;
	    var b=0;
	    var sumnotMoney=0;
	    for(var i=0;i<s.length;i++){
	    	if(s[i].data.payMoney >0)
	    	a++;
	    	if(s[i].data.incomeMoney >0)
		    b++;
	    	sumnotMoney=sumnotMoney+s[i].data.notMoney;
	    }
	    if(a>0 && b>0){
	    	Ext.ux.Toast.msg('操作信息','请选中的记录支出保持一致');
			return false;
	    	
	    }
	    
			var ids = $getGdSelectedIds(this.gridPanel,'fundIntentId');
			var	record=s[0];
			var flag=0;            //incomeMoney
		     if(record.data.payMoney !=0){   //payMoney
		     	flag=1;
		     } 
		     var count=0;
		     for(var i=0;i<ids.length;i++){
		       if(s[i].data.fundIntentId ==null)count++;
		     }
		     
		     if(count>0){
		     		Ext.ux.Toast.msg('操作信息','请先保存');
		     }else{
				new SlFundIntentForm1({
					ids : ids,
					flag:flag,
					fundType : record.data.fundType,
					sumnotMoney :sumnotMoney,
					companyId:record.data.companyId,
					otherPanel:this.gridPanel
				}).show();
		     }
		}	
		
	},
	openliushuiwin : function() {
		var s = this.gridPanel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息','请选中记录');
			return false;
		}else if(s.length>1){
			this.manyInntentopenliushuiwin();
			
		}else if(s.length==1){
		   this.oneopenliushuiwin();
		}
	},
			autocreate : function() { 
				var businessType=this.businessType;
					var gridPanel1=this.gridPanel
					 var operationType=null;
					 var accrualtype=null;
					 var payaccrualType=null;
					 var projectMoney=null;
					 var isStartDatePay=null;
					 var startDate=null;
					 var payintentPerioDate=null;
					 var payintentPeriod=null;
					  var intentDate=null;
					   var dayOfEveryPeriod=null;
					  var leaseDepositRate=null;
					  var leaseDepositMoney=null;
					  var rentalFeeRate=null;
					  var rentalFeeMoney=null;
					  var leaseRetentionFeeRate=null;
					  var leaseRetentionFeeMoney=null;
					  var rentalRate=null;
					  var rentalMoney=null;
					  var feePayaccrualType=null;
					  var eachRentalReceivable=null;
					
					  accrualtype=this.object.getCmpByName("slSuperviseRecord.accrualtype").getValue();
					   payaccrualType=this.object.getCmpByName("slSuperviseRecord.payaccrualType").getValue();
					    projectMoney=this.object.getCmpByName("slSuperviseRecord.continuationMoney").getValue();
					   isStartDatePay=this.object.getCmpByName("slSuperviseRecord.isStartDatePay").getValue();
					  
					   startDate=this.object.getCmpByName("slSuperviseRecord.startDate").getValue();
					   payintentPerioDate=this.object.getCmpByName("slSuperviseRecord.payintentPerioDate").getValue();
					   payintentPeriod=this.object.getCmpByName("slSuperviseRecord.payintentPeriod").getValue();
					    intentDate=this.object.getCmpByName("slSuperviseRecord.endDate").getValue();
					     dayOfEveryPeriod=this.object.getCmpByName("slSuperviseRecord.dayOfEveryPeriod").getValue();
					 
					    rentalFeeRate=this.object.getCmpByName("slSuperviseRecord.managementConsultingOfRate").getValue();
					    
					     rentalRate=this.object.getCmpByName('slSuperviseRecord.continuationRate').getValue();
				
					     feePayaccrualType=this.object.getCmpByName('slSuperviseRecord.feePayaccrualType').getValue();
					    
					 	eachRentalReceivable=this.object.getCmpByName("slSuperviseRecord.eachRentalReceivable").getValue();
					
				
					   var calcutype=this.businessType;  //业务品种
					   var projId=this.projectId;
					   var message="";
					   if(accrualtype=="请选择" || accrualtype==null || accrualtype==""){
					     message="还款方式";
					   }else if(payaccrualType=="请选择" || payaccrualType==null){
					   	 message="还款周期";
					   }
							   
		
				 
							    if(projectMoney=="" || projectMoney==null){
							    	message="租赁成本";
							   }
							   
							   if(startDate=="" || startDate==null){
							   	 message="租赁开始日";
							   }
							    if(payintentPeriod=="" || payintentPeriod==null ){
							   	 message="租赁期限";
							   }
							  
							   if(rentalFeeRate=="" || rentalFeeRate==null){
							    	message="租赁手续费率";
							   }
							  
							   if(rentalRate=="" || rentalRate==null){
							    	message="租赁费率";
							   }
							  
								    if(isStartDatePay=="1"){
								    
								    	if(payintentPerioDate=="" || payintentPerioDate==null){
									   	 message="固定在几号还款";
									   }
									  
								    }
							     if(payaccrualType=="owerPay"){
							       if(dayOfEveryPeriod=="" || dayOfEveryPeriod==null){
							       	 message="自定义周期";
							       	
							       }
							     
							     	
							     }
							 
						
					    
			
			 if(message !=""){   
							  Ext.MessageBox.show({
		                    title : '操作信息',
		                    msg : message+'不能为空',
		                    buttons : Ext.MessageBox.OK,
		                    icon : 'ext-mb-error'
		                });
		                 return null;
			    	}
	
					   
						
						var params1 ={
							projectId : projId,
						    'slSuperviseRecord.accrualtype': accrualtype,
						    'slSuperviseRecord.payaccrualType': payaccrualType,
						    'slSuperviseRecord.continuationMoney': projectMoney,
						    'slSuperviseRecord.startDate': startDate,
						    'slSuperviseRecord.endDate': intentDate,
						    'slSuperviseRecord.isStartDatePay': isStartDatePay,
						    'slSuperviseRecord.payintentPerioDate': payintentPerioDate,
						     'slSuperviseRecord.payintentPeriod': payintentPeriod,
						         'slSuperviseRecord.dayOfEveryPeriod': dayOfEveryPeriod,
						      'slSuperviseRecord.managementConsultingOfRate': rentalFeeRate,
						         'slSuperviseRecord.continuationRate':rentalRate,
						         'slSuperviseRecord.feePayaccrualType':feePayaccrualType,
						         'slSuperviseRecord.eachRentalReceivable':eachRentalReceivable,
						     'calcutype':"LeaseFinance",
						     'flag1':0,
						     'projectId':projId,
						     'businessType':businessType,
						     isHaveLending:"yes"
						    
						}
			
					   var combox=new Ext.form.ComboBox({
						    triggerAction: 'all',
						    store: new Ext.data.SimpleStore({
								autoLoad : true,
								url : __ctxPath+ '/project/getPayIntentPeriodSlSmallloanProject.do',
								fields : ['itemId', 'itemName'],
								baseParams : {payintentPeriod:payintentPeriod}
							}),
						    valueField: 'itemId',
						    displayField: 'itemName'
				
						})
						gridPanel1.getColumnModel().setEditor(3,combox);
					
						var gridstore = gridPanel1.getStore();
						gridstore.on('beforeload', function(gridstore, o) {
							
							Ext.apply(o.params, params1);
						});
						
						
						var projectId = this.projectId;
						gridPanel1.getStore().reload();
						var objPanel=this.object;
						gridstore.on('load', function(gridstore, o) {
							var vRecords = gridstore.getRange(0,gridstore.getCount());
							var vCount = vRecords.length; // 得到记录长度
						var vDatas = '';
						if (vCount > 0) {
							var allMoney=0;
							var interestMoney=0;
						for ( var i = 0; i < vCount; i++) {
							 if(vRecords[i].data.fundType=='leaseFeeCharging'){
							 	allMoney=allMoney+vRecords[i].data.incomeMoney;
							 	interestMoney=interestMoney+vRecords[i].data.incomeMoney;
							 }
							 if(vRecords[i].data.fundType=='leasePrincipalRepayment'){
							 	allMoney=allMoney+vRecords[i].data.incomeMoney;
							 }
							if(vRecords[i].data.afterMoney !=null && vRecords[i].data.afterMoney !=0){
								
								//Ext.Msg.alert('', '有款项已对过账不能被覆盖');
						    }
						  }
						 objPanel.getCmpByName('flLeaseFinanceProject.allMoney').setValue(allMoney)
						 objPanel.getCmpByName('flLeaseFinanceProject.rentalMoney').setValue(interestMoney)
						 objPanel.getCmpByName('allMoney1').setValue(Ext.util.Format.number(allMoney, '0,000.00'))
						 objPanel.getCmpByName('rentalMoney1').setValue(Ext.util.Format.number(interestMoney, '0,000.00'))
						}
						});
                     
					},
					toExcel:function(){
						var projectId = this.projectId;
						var businessType=this.businessType;
						window.open( __ctxPath + "/creditFlow/finance/downloadSlFundIntent.do?projectId="+projectId+"&businessType="+businessType,'_blank');
						
					}

});
