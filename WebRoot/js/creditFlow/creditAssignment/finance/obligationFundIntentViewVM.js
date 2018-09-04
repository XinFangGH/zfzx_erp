//obligationFundIntentViewVM

/**
 * @author
 * @class obligationFundIntentViewVM
 * @extends Ext.Panel
 * @description [SlFundIntent]管理
 * @company 智维软件
 * @createtime:
 */

var slprojectMoney = 0;
var slnotprojectMoney = 0;
obligationFundIntentViewVM = Ext.extend(Ext.Panel, {
	name:"obligationFundIntentViewVM",
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
		obligationFundIntentViewVM.superclass.constructor.call(this, {
			name : "obligationFundIntentViewVM_panel",
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
		this.comboType = new DicIndepCombo({
			editable : false,
			lazyInit : false,
			forceSelection : false,
			nodeKey : "AssignmentFundType",
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
				hidden : this.isHiddenCanBtn
			}), {
				iconCls : 'btn-details',
				text : '导出Excel',
				xtype : 'button',
				hidden : this.isHiddenExcel,
				scope : this,
				handler : function() {
					this.toExcel();
				}
			}]
		});
		var field = Ext.data.Record.create([{
			name : 'fundIntentId'
		}, {
			name : 'fundType'
		}, {
			name : 'fundTypeName'
		}, {
			name : 'incomeMoney'
		},{
			name : 'payintentPeriod'
		},
		{
			name : 'payMoney'
		}, {
			name : 'intentDate'
		}, {
			name : 'factDate'
		},{
			name : 'investPersonId'
		},{
			name : 'investpersonName'
		},{
			name : 'obligationId'
		},{
			name :'obligationInfoId'
		}, {
			name : 'isValid'
		}, {
			name : 'isCheck'
		}, {
			name : 'companyId'
		}, {
			name : 'afterMoney'
		}, {
			name : 'notMoney'
		}]);
		var url = __ctxPath + "/creditFlow/finance/listObligationSlFundIntent.do";
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
				 investPersonId : this.investPersonId,
				 obligationInfoId:this.obligationInfoId
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
			columns : [{
				header : 'fundIntentId',
				dataIndex : 'fundIntentId',
				name:'fundIntentId',
				hidden : true
			},{
				header : '投资人',
				dataIndex : 'investpersonName',
				name:'fundIntentId',
				align : 'right'
			},{
				header : '期数',
				dataIndex : 'payintentPeriod',
				editor : new Ext.form.ComboBox({
				    triggerAction: 'all',
				    readOnly : this.isHidden1,
				    store: new Ext.data.SimpleStore({
						autoLoad : true,
						url : __ctxPath+ '/creditFlow/creditAssignment/project/getPayIntentPeriodObObligationProject.do',
						fields : ['itemId', 'itemName']
					}),
				    valueField: 'itemId',
				    displayField: 'itemName'
		
				}),
				renderer : function(value, metaData, record, rowIndex,colIndex, store){
					if (record.data.isValid == 1) {
						if( record.data.fundType!='investPrincipalLending' ){
							return '<font style="font-style:italic;text-decoration: line-through;color:gray">第'+value+'期</font>';
						}else{
							if(null!=value){
								return '<font style="font-style:italic;text-decoration: line-through;color:gray">'+value+'</font>'
							}
						}
					}else{
						if (record.data.notMoney == 0) {
							if( record.data.fundType!='investPrincipalLending' ){
								return '<font style="color:gray">第'+value+'期</font>';
							}else{
								if(null!=value){
									return '<font style="color:gray">'+value+'</font>'
								}
							}
						}else{
							if( record.data.fundType!='investPrincipalLending' ){
								return '第'+value+'期';
							}else{
								if(null!=value){
									return value
								}
							}
						}
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

						if (record.data.fundTypeName == "") {

							record.data.fundTypeName = value

						} else {
							var x = store.getModifiedRecords();
							if (x != null && x != "") {
								record.data.fundTypeName = value
							}
						}
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
				header : '到账日',
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
				header : '对账日',
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
			}
					
			],
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
						if (e.record.data['afterMoney'] != 0) {
							
							e.cancel = true;
						} else {
							e.cancel = false;
						}
					} else if (e.record.data['fundIntentId'] == "") {
						e.cancel = false;
					}
					
					/*if (e.record.data['fundType'] == 'principalLending') {
						e.cancel = true;
					}*/
				},
				afteredit : function(e) {
					if (e.record.data['fundType'] == 'principalLending'
							|| e.record.data['fundType'] == 'FinancingInterest'
							|| e.record.data['fundType'] == 'FinancingRepay'
							|| e.record.data['fundType'] == 'financingconsultationMoney'
							|| e.record.data['fundType'] == 'financingserviceMoney' || e.record.data['fundType'] =='backInterest' || e.record.data['fundType'] =='investPrincipalLending') {
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
			items : []
		});
		/*// 为infoPanel添加数据
		if (this.isThisSuperviseRecord == null
				|| isThisEarlyPaymentRecord == null
				|| isThisAlterAccrualRecord != null) {
			this.fillDatas(this.projectId, this.businessType);
		}*/

	},// end of the initComponents()
//增加信息
	createRs : function() {
		//var payintentPeriod=this.object.getCmpByName("slSmallloanProject.payintentPeriod").getValue();
		var payintentPeriod = (typeof(this.object)!='undefined'?this.object.getCmpByName('obObligationProject.payintentPeriod').getValue():this.payintentPeriod);
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
//封装数据成string数组
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
											  st={"fundIntentId":vRecords[i].data.fundIntentId,"fundType":vRecords[i].data.fundType,
											  	  "incomeMoney":vRecords[i].data.incomeMoney,"payMoney":vRecords[i].data.payMoney,
											  	  "intentDate":vRecords[i].data.intentDate,"factDate":vRecords[i].data.factDate,
											  	  "obligationId":vRecords[i].data.obligationId,
											  	  "payintentPeriod":vRecords[i].data.payintentPeriod,"investPersonId":vRecords[i].data.investPersonId,
											  	  "obligationInfoId":vRecords[i].data.obligationInfoId,"investpersonName":vRecords[i].data.investpersonName};
											vDatas += Ext.util.JSON.encode(st) + '@';
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
		this.fillDatas(this.projectId, this.businessType);
	},
	
	//删除数据
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



	/*// 为infoPanel添加数据
	fillDatas : function(projId, businessType) {
		var requestUrl = "";
		if (businessType == "SmallLoan") {
			requestUrl = __ctxPath
					+ '/creditFlow/finance/slFundIntentCountInfoSlFundIntent.do?businessType='
					+ this.businessType
		} else if (businessType == "Financing") {
			requestUrl = __ctxPath
					+ '/creditFlow/finance/flFundIntentCountInfoSlFundIntent.do?businessType='
					+ this.businessType
		}
		Ext.Ajax.request({
			url : requestUrl,
			method : 'POST',
			scope : this,
			success : function(response, request) {
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
			},
			failure : function(response) {
				Ext.ux.Toast.msg('状态', '请求失败，请重试');
			},
			params : {
				projectId : projId
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
		
	},*/
	
	//生成款项计划
	autocreate : function() { 
		var investPersonId=this.investPersonId;  //投资人id
		var keyValue=this.keyValue;
		var projId=null;
		var gridPanel1=this.gridPanel
		var accrualtype=null;
		var payaccrualType=null;
		var projectMoney=null;
		var isStartDatePay=null;
		var startDate=null;
		var payintentPerioDate=null;
		var payintentPeriod=null;
		var intentDate=null;
		var dayOfEveryPeriod=null;
		var accrualnew =null;
		if(keyValue=="oneSlFundIntentCreat"){//一个投资人生成款项
			projId=this.object.getCmpByName("obObligationInvestInfo.obligationId").getValue();
			accrualtype=this.object.getCmpByName("obObligationProject.accrualtype").getValue();
			payaccrualType=this.object.getCmpByName("obObligationProject.payaccrualType").getValue();
			projectMoney=this.object.getCmpByName("obObligationInvestInfo.investMoney").getValue();
			isStartDatePay=this.object.getCmpByName("obObligationProject.isStartDatePay").getValue();
			startDate=this.object.getCmpByName("obObligationInvestInfo.investStartDate").getValue();
			payintentPerioDate=this.object.getCmpByName("obObligationProject.payintentPerioDate").getValue();
			accrualnew=this.object.getCmpByName("obObligationInvestInfo.obligationAccrual").getValue();
			// managementConsultingOfRate=this.object.getCmpByName("slSmallloanProject.managementConsultingOfRate").getValue();
			//financeServiceOfRate=this.object.getCmpByName("slSmallloanProject.financeServiceOfRate").getValue();
			payintentPeriod=this.object.getCmpByName("obObligationProject.payintentPeriod").getValue();
			intentDate=this.object.getCmpByName("obObligationInvestInfo.investEndDate").getValue();
			dayOfEveryPeriod=this.object.getCmpByName("obObligationProject.dayOfEveryPeriod").getValue();
			isPreposePayAccrual=this.object.getCmpByName("obObligationProject.isPreposePayAccrual").getValue();
			//isInterestByOneTime=this.object.getCmpByName('slSmallloanProject.isInterestByOneTime').getValue();
			//dateMode=this.object.getCmpByName('slSmallloanProject.dateMode').getValue();
			// dayAccrualRate=this.object.getCmpByName('slSmallloanProject.dayAccrualRate').getValue();
			// dayManagementConsultingOfRate=this.object.getCmpByName('slSmallloanProject.dayManagementConsultingOfRate').getValue();
			//dayFinanceServiceOfRate=this.object.getCmpByName('slSmallloanProject.dayFinanceServiceOfRate').getValue();
			//
		}else if(keyValue=="manySlFundIntentCreat"){//一个项目给所有投资人生成款项
			
			projId=this.object.getCmpByName("obObligationProject.id").getValue();
			
			accrualtype=this.object.getCmpByName("obObligationProject.accrualtype").getValue();
			payaccrualType=this.object.getCmpByName("obObligationProject.payaccrualType").getValue();
			projectMoney=this.object.getCmpByName("obObligationProject.projectMoney").getValue();//项目的总金额
			isStartDatePay=this.object.getCmpByName("obObligationProject.isStartDatePay").getValue();
			//startDate=this.object.getCmpByName("obObligationProject.startDate").getValue();//项目的开始时间
			payintentPerioDate=this.object.getCmpByName("obObligationProject.payintentPerioDate").getValue();
			accrualnew=this.object.getCmpByName("obObligationProject.accrual").getValue();//项目的利率
			// managementConsultingOfRate=this.object.getCmpByName("slSmallloanProject.managementConsultingOfRate").getValue();
			//financeServiceOfRate=this.object.getCmpByName("slSmallloanProject.financeServiceOfRate").getValue();
			payintentPeriod=this.object.getCmpByName("obObligationProject.payintentPeriod").getValue();
			intentDate=this.object.getCmpByName("obObligationProject.intentDate").getValue();//项目的自动关闭时间
			dayOfEveryPeriod=this.object.getCmpByName("obObligationProject.dayOfEveryPeriod").getValue();
			isPreposePayAccrual=this.object.getCmpByName("obObligationProject.isPreposePayAccrual").getValue();
			//isInterestByOneTime=this.object.getCmpByName('slSmallloanProject.isInterestByOneTime').getValue();
			//dateMode=this.object.getCmpByName('slSmallloanProject.dateMode').getValue();
			// dayAccrualRate=this.object.getCmpByName('slSmallloanProject.dayAccrualRate').getValue();
			// dayManagementConsultingOfRate=this.object.getCmpByName('slSmallloanProject.dayManagementConsultingOfRate').getValue();
			//dayFinanceServiceOfRate=this.object.getCmpByName('slSmallloanProject.dayFinanceServiceOfRate').getValue();
			//
			
		}
		
		if(projId!=null&&projId!=""){
			if(keyValue=="oneSlFundIntentCreat"){
//				alert("projectMoney=="+projectMoney+"  startDate=="+startDate+"   intentDate=="+intentDate)
				if(projectMoney==null||projectMoney==""||startDate==null||startDate==""||intentDate==null||intentDate==""){
					Ext.MessageBox.show({
			            title : '操作信息',
			            msg : '投资人投资信息不能为空，请检查是否已经正确填写！',
			            buttons : Ext.MessageBox.OK,
			            icon : 'ext-mb-error'
				    });
				    return null;
				}
			}
			var message="";
			if(accrualtype=="请选择" || accrualtype==null || accrualtype==""){
				 message="计息方式";
			}else if(payaccrualType=="请选择" || payaccrualType==null){
				 message="付息方式";
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
							investPersonId:investPersonId,
							projectId : projId,
						    'obObligationProject.accrualtype': accrualtype,
						    'obObligationProject.payaccrualType': payaccrualType,
						    'obObligationProject.projectMoney': projectMoney,
						    'obObligationProject.startDate': startDate,
						    'obObligationProject.intentDate': intentDate,
						    'obObligationProject.isStartDatePay': isStartDatePay,
						    'obObligationProject.payintentPerioDate': payintentPerioDate,
						    'obObligationProject.payintentPeriod': payintentPeriod,
						    'obObligationProject.dayOfEveryPeriod': dayOfEveryPeriod,
						    /*'obObligationProject.calcutype':calcutype,*/
						    'obObligationProject.accrual':accrualnew,
						    'obObligationProject.isPreposePayAccrual':isPreposePayAccrual,
						    'flag1':0,
						    keyValue:keyValue,
						    isHaveLending:"yes",
						    investPersonId:investPersonId
						}
					   var combox=new Ext.form.ComboBox({
						    triggerAction: 'all',
						    store: new Ext.data.SimpleStore({
								autoLoad : true,
								url : __ctxPath+ '/creditFlow/creditAssignment/project/getPayIntentPeriodObObligationProject.do',
								fields : ['itemId', 'itemName'],
								baseParams : {payintentPeriod:payintentPeriod,payaccrualType:payaccrualType,startDate:startDate,intentDate:intentDate}
							}),
						    valueField: 'itemId',
						    displayField: 'itemName'
				
						})
						gridPanel1.getColumnModel().setEditor(4,combox);
					
						var gridstore = gridPanel1.getStore();
						gridstore.on('beforeload', function(gridstore, o) {
							
							Ext.apply(o.params, params1);
						});
						
						
						var projectId = this.projectId;
						gridPanel1.getStore().reload();
                        var vRecords = this.gridPanel.getStore().getRange(0,this.gridPanel.getStore().getCount()); // 得到修改的数据（记录对象）
						var vCount = vRecords.length; // 得到记录长度
						var vDatas = '';
							

		}else{
			Ext.MessageBox.show({
			            title : '操作信息',
			            msg : '债权产品信息不能为空，请检查是否已经正确填写！',
			            buttons : Ext.MessageBox.OK,
			            icon : 'ext-mb-error'
		    });
		    return null;
		
		}
					
	},
	//导出Excel方法
	toExcel:function(){
			var projectId = this.projectId;
			var businessType=this.businessType;
			var  investPersonId =this.investPersonId;
			var obligationInfoId=this.obligationInfoId
			window.open( __ctxPath + "/creditFlow/finance/assignmentDownloadSlFundIntent.do?projectId="+projectId+"&businessType="+businessType+"&investPersonId="+investPersonId+"&obligationInfoId="+obligationInfoId,'_blank');
						
	}

});
