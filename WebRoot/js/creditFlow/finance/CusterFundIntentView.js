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
CusterFundIntentView = Ext.extend(Ext.Panel, {
	name : "SlFundIntentViewVM",
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
		CusterFundIntentView.superclass.constructor.call(this, {
			name : "CusterFundIntentView_panel",
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
		if (this.businessType == "LeaseFinance") {
			fundTypenodekey = "LeaseFinance_independent";
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
							}/*, {
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
									var selRs = this.gridPanel
											.getSelectionModel()
											.getSelections();
									if (selRs <= 0) {
										Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
										return;
									} else if (selRs.length > 1) {
										Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
										return;
									}
									this.fundIntentWaterReconciliationInfo
											.call(this, selRs[0], 1);
								}
							}, new Ext.Toolbar.Separator({
										hidden : this.isHiddenseeqlideBtn
									}), {
								iconCls : 'btn-details',
								text : '放款',
								xtype : 'button',
								// hidden :this.isFinbtn,
								hidden : true,
								scope : this,
								handler : this.affrim
							}, new Ext.Toolbar.Separator({
										// hidden : this.isFinbtn
										hidden : this.affrim
									}),
										 * { iconCls : 'btn-details', text :
										 * '手动放款', xtype : 'button', //hidden
										 * :this.isFinbtn, scope : this, handler :
										 * this.handAffrim },new
										 * Ext.Toolbar.Separator({ hidden :
										 * this.isFinbtn }),
										 {
								iconCls : 'btn-details',
								text : '项目总流水记录',
								xtype : 'button',
								hidden : this.isHiddenseesumqlideBtn,
								scope : this,
								handler : function() {
									this.projectWaterReconciliationInfo(2);
								}
							}*//*, new Ext.Toolbar.Separator({
										hidden : this.isHiddenExcel
									}), {
								iconCls : 'btn-details',
								text : '导出Excel',
								xtype : 'button',
								hidden : this.isHiddenExcel,
								scope : this,
								handler : function() {
									this.toExcel();
								}
							}*/]
				});
		var field = Ext.data.Record.create([{
					name : 'payintentPeriod'
				}, {
					name : 'principal'
				}, {
					name : 'interest'
				}, {
					name : 'consultationMoney'
				}, {
					name : 'serviceMoney'
				}, {
					name : 'interestPenalty'				
				},{
					name : 'intentDate'
				}, {
					name : 'sumMoney'
				},{
					name : 'factDate'
				},{
					name : 'afterMoney'
				},{
					name : 'accrualMoney'
				}]);	
	var url=__ctxPath + "/creditFlow/finance/listloanBpFundIntent.do"
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
				summaryType : 'count',
				summaryRenderer : totalMoney,
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

						header : '计划到帐日',
						dataIndex : 'intentDate',
						format : 'Y-m-d'
					}, {
						header : '本金',
						dataIndex : 'principal',
						editor : this.comboType,
						width : 107,
						summaryType : 'sum',
						align : 'right',
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store) {
									if(value!=''){
										return Ext.util.Format.number(value,',000,000,000.00')+"元"
									}else{
										return '';
									}
								}
					}, {
						header : '利息',
						dataIndex : 'interest',
						summaryType : 'sum',
						width : 110,
						align : 'right',
						editor : {
							xtype : 'numberfield',
							allowBlank : false,
							readOnly : this.isHidden1
						},
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store) {
									if(value!=''){
										return Ext.util.Format.number(value,',000,000,000.00')+"元"
									}else{
										return '';
									}
								}
					}, {
						header : '管理咨询费',
						dataIndex : 'consultationMoney',
						align : 'right',
						width : 110,
						summaryType : 'sum',
						editor : {
							xtype : 'numberfield',
							allowBlank : false,
							readOnly : this.isHidden1
						},
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store) {
									if(value!=''){
										return Ext.util.Format.number(value,',000,000,000.00')+"元"
									}else{
										return '';
									}
								}
					}, {
						header : '财务服务费',
						dataIndex : 'serviceMoney',
						summaryType : 'sum',
						align : 'right',
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store) {
									if(value!=''){
										return Ext.util.Format.number(value,',000,000,000.00')+"元"
									}else{
										return '';
									}
								}
					}, {
						header : '补偿息金额',
						dataIndex : 'interestPenalty',
						summaryType : 'sum',
						align : 'right',
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store) {
									if(value!=''){
										return Ext.util.Format.number(value,',000,000,000.00')+"元"
									}else{
										return '';
									}
								}
					},{
						header : '罚息金额',
						dataIndex : 'accrualMoney',
						summaryType : 'sum',
						align : 'right',
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store) {
									if(value!=''){
										return Ext.util.Format.number(value,',000,000,000.00')+"元"
									}else{
										return '';
									}
								}
					}, {
						header : '合计',
						dataIndex : 'sumMoney',
						summaryType : 'sum',
						align : 'right',
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store) {
									if(value!=''){
										return Ext.util.Format.number(value,',000,000,000.00')+"元"
									}else{
										return '';
									}
								}
					}, {

						header : '实际到帐日',
						dataIndex : 'factDate',
						hidden : (this.isFactHidden==false?false:true),
						format : 'Y-m-d'
					}, {
						header : '实际到账金额',
						dataIndex : 'afterMoney',
						summaryType : 'sum',
						align : 'right',
						hidden : (this.isFactHidden==false?false:true),
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store) {
									if(value!=''){
										return Ext.util.Format.number(value,',000,000,000.00')+"元"
									}else{
										return '';
									}
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
		// 为infoPanel添加数据
		if (this.isThisSuperviseRecord == null
				|| isThisEarlyPaymentRecord == null
				|| isThisAlterAccrualRecord != null) {
			if (this.businessType == 'SmallLoan') {
				this.fillDatas(this.projectId, this.businessType);
			}
		}

	},// end of the initComponents()

	createRs : function() {
		// var
		// payintentPeriod=this.object.getCmpByName("platFormBpFundProject.payintentPeriod").getValue();
		var payintentPeriod = (typeof(this.object) != 'undefined'
				? this.object
						.getCmpByName('platFormBpFundProject.payintentPeriod')
						.getValue()
				: this.payintentPeriod);
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
					payintentPeriod : payintentPeriod
				});
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
		this.gridPanel.getColumnModel().setEditor(3, combox);
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
			var st = "";
			for (var i = 0; i < vCount; i++) {
				/*if (vRecords[i].data.fundType != null
						&& vRecords[i].data.fundType != "") {
				}*/
					if ((null == vRecords[i].data.afterMoney || vRecords[i].data.afterMoney == 0) && (null == vRecords[i].data.isValid || vRecords[i].data.isValid==0)) {
						st = {
							"fundIntentId" : null,
							"fundType" : vRecords[i].data.fundType,
							"incomeMoney" : vRecords[i].data.incomeMoney,
							"payMoney" : vRecords[i].data.payMoney,
							"intentDate" : vRecords[i].data.intentDate,
							"remark" : vRecords[i].data.remark,
							"payintentPeriod" : vRecords[i].data.payintentPeriod,
							"interestStarTime" : vRecords[i].data.interestStarTime,
							"interestEndTime" : vRecords[i].data.interestEndTime,
							"bidPlanId":this.bidPlanId,
							"preceptId":this.preceptId
						};
						vDatas += Ext.util.JSON.encode(st) + '@';
					}
			}

			vDatas = vDatas.substr(0, vDatas.length - 1);
		}
		// Ext.getCmp('fundIntentJsonData2').setValue(vDatas);
		// this.gridPanel.getColumnsgetEditor().getCmpByName('fundIntentJsonData').setValue(vDatas);
		// alert(this.gridPanel.getEditor().getCmpByName('fundIntentJsonData').getValue());
		return vDatas;
	},
	// 放款按钮调用方法
	affrim : function() {
		var panel = this;
		var toolbar = this.gridPanel.getTopToolbar();
		var store = this.gridPanel.getStore();
		Ext.Ajax.request({
					url : __ctxPath
							+ '/webservice/dataSubmitFundIntentPrincipalLending.do',
					method : 'POST',
					scope : this,
					params : {
						projectId : this.projectId,
						businessType : this.businessType
					},
					success : function(response, request) {
						var object = Ext.util.JSON
								.decode(response.responseText);
						var sub = object.sub;
						var flag = object.flag;
						var tip = object.flag1;
						var result = object.result;
						if (sub == 1) {// 用于判断本金放贷信息是否成功生成： 0表示没有生成；1表示生成
							if (flag == 1) {// 用于判断发送数据是否成功准备 ： 0表示未成功；1表示生成
								if (tip == 1) {// 用于判断发送本金放贷信息是否成功 发送：
												// 0表示未成功；1表示成功
									Ext.Msg.alert('放款信息', result);
									store.reload();
									toolbar.get(10).setVisible(true)
									toolbar.get(11).setVisible(true)
									toolbar.get(14).setVisible(false)
									toolbar.get(13).setVisible(false)
									panel.doLayout();
								} else if (tip == 2) {
									Ext.Msg.confirm("提示!", result,
											function(btn) {
												if (btn == "yes") {
													toolbar.get(10)
															.setVisible(true)
													toolbar.get(11)
															.setVisible(true)
													toolbar.get(14)
															.setVisible(false)
													toolbar.get(13)
															.setVisible(false)
													panel.handAffrim
															.call(panel);
													store.reload();
												} else {
													store.reload();
													panel.doLayout();
												}
											});
								} else {
									Ext.Msg.alert('出错信息', result);

								}
							} else {
								Ext.Msg.alert('数据抓取信息', result);
							}
						} else {
							Ext.Msg.alert('生成本金贷款信息', result);

						}
					}
				});
	},
	handAffrim : function() {
		var panel = this;
		var toolbar = this.gridPanel.getTopToolbar();
		var store = this.gridPanel.getStore();
		Ext.Ajax.request({
			url : __ctxPath
					+ '/webservice/handDateSubmitFundIntentPrincipalLending.do',
			method : 'POST',
			scope : this,
			params : {
				projectId : this.projectId,
				businessType : this.businessType
			},
			success : function(response, request) {
				var object = Ext.util.JSON.decode(response.responseText);
				var sub = object.sub;
				var result = object.result;
				if (sub == 1) {// 用于判断本金放贷信息是否成功生成： 0表示没有生成；1表示生成
					// Ext.Msg.alert('放款信息', result);
				} else {
					Ext.Msg.alert('生成本金贷款信息', result);
				}
			}
		});
	},
	// 保存数据
	saveRs : function() {
		var data = this.getGridDate();
		Ext.Ajax.request({
					url : __ctxPath
							+ '/creditFlow/finance/savejson1SlFundIntent.do',
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
						'flag1' : 1
					};
					Ext.apply(o.params, new_params);
				});
		this.gridPanel.getStore().setBaseParam('flag1', 1);
		this.gridPanel.getStore().reload();
		this.gridPanel.getView().refresh();
		this.fillDatas(this.projectId, this.businessType);
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

						deleteFun(
								__ctxPath
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
						deleteFun(
								__ctxPath
										+ '/creditFlow/finance/backSlFundIntent.do',
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
						deleteFun(
								__ctxPath
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
	fillDatas : function(projId, businessType) {/*
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
															 * this.infoObj =
															 * Ext.util.JSON.decode(response.responseText);
															 * var inforpanel =
															 * this.getCmpByName("infoPanel")
															 * inforpanel.removeAll();
															 * slprojectMoney =
															 * this.infoObj.slProjectMoney;
															 * slnotprojectMoney =
															 * this.infoObj.slProjectMoneyBalance;
															 * if (businessType ==
															 * 'SmallLoan') {
															 * inforpanel.add({
															 * xtype : 'label',
															 * html : "<span
															 * style='line-height:2.2'>总计提示:
															 * <font
															 * class='x-myZW-fieldset-title'></font>贷款本金总额：" +
															 * Ext.util.Format.number(this.infoObj.slProjectMoney,
															 * ',000,000,000.00') +
															 * "元&nbsp;&nbsp;未还贷款本金：" +
															 * Ext.util.Format.number(this.infoObj.slProjectMoneyBalance,
															 * ',000,000,000.00') +
															 * "元&nbsp;&nbsp;应收费用总额：" +
															 * Ext.util.Format.number(this.infoObj.slProjectIncomeintentsum,
															 * ',000,000,000.00') +
															 * "元&nbsp;&nbsp;未收费用总额：" +
															 * Ext.util.Format.number(this.infoObj.slProjectNotincomeintentsum,
															 * ',000,000,000.00') +
															 * "元</span><font
															 * class='x-myZW-fieldset-title'></font><br/><font
															 * class='x-myZW-fieldset-title'></font><span><font
															 * color=red>逾期提示</font>：逾期未还款项共计<font
															 * color=red>" +
															 * Ext.util.Format.number(this.infoObj.slProjectoverduecount,
															 * ',000,000,000.00') + "</font>笔
															 * &nbsp;&nbsp;逾期未还款项总额<font
															 * color=red>" +
															 * Ext.util.Format.number(this.infoObj.slProjectoverdueintent,
															 * ',000,000,000.00') + "</font>元
															 * &nbsp;&nbsp;逾期违约金总额<font
															 * color=red>" +
															 * Ext.util.Format.number(this.infoObj.slProjectoverduebreakMoneysum,
															 * ',000,000,000.00') + "</font>元</span><font
															 * class='x-myZW-fieldset-title'></font>"
															 * }); } else if
															 * (businessType ==
															 * 'Financing') {
															 * inforpanel.add({
															 * xtype : 'label',
															 * html : "<span
															 * style='line-height:2.2'>总计提示:
															 * <font
															 * class='x-myZW-fieldset-title'></font>借款本金总额：" +
															 * Ext.util.Format.number(this.infoObj.flProjectMoney,',000,000,000.00') +
															 * "元&nbsp;&nbsp;未还借款本金：" +
															 * Ext.util.Format.number(this.infoObj.flProjectMoneyBalance,',000,000,000.00') +
															 * "&nbsp;&nbsp;应付费用总额：" +
															 * Ext.util.Format.number(this.infoObj.flProjectChargeMoney,',000,000,000.00') +
															 * "元&nbsp;&nbsp;未付费用总额：" +
															 * Ext.util.Format.number(this.infoObj.flProjectChargeMoneyBalance,',000,000,000.00') +
															 * "元</span><font
															 * class='x-myZW-fieldset-title'></font><br/><font
															 * class='x-myZW-fieldset-title'></font><span>"
															 * }); }
															 * inforpanel.doLayout()
															 
					},
					failure : function(response) {
						Ext.ux.Toast.msg('状态', '请求失败，请重试');
					},
					params : {
						projectId : projId
					}
				})
	*/},
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
		/*
		 * if (businessType == 'SmallLoan') { inforpanel.add({ xtype : 'label',
		 * html : "<span style='line-height:2.2'>总计提示: <font
		 * class='x-myZW-fieldset-title'></font>贷款本金总额：" + slprojectMoney +
		 * "元&nbsp;&nbsp;未还贷款本金：" + slnotprojectMoney + "元&nbsp;&nbsp;应收费用总额：" +
		 * s3 / 1000 + "元&nbsp;&nbsp;未收费用总额：" + s5 / 1000 + "元</span><font
		 * class='x-myZW-fieldset-title'></font><br/><font
		 * class='x-myZW-fieldset-title'></font><span><font color=red>逾期提示</font>：逾期未还款项共计<font
		 * color=red>" + s6 + "</font>笔 &nbsp;&nbsp;逾期未还款项总额<font color=red>" +
		 * s7 / 1000 + "</font>元 &nbsp;&nbsp;逾期违约金总额<font color=red>" + s1 /
		 * 1000 + "</font>元</span><font class='x-myZW-fieldset-title'></font>"
		 * }); } else if (businessType == 'Financing') { inforpanel.add({ xtype :
		 * 'label', html : "<span style='line-height:2.2'>总计提示: <font
		 * class='x-myZW-fieldset-title'></font>借款本金总额：" + s1 / 1000 +
		 * "元&nbsp;&nbsp;未还借款本金：" + (s1 - s2) / 1000 + "&nbsp;&nbsp;应付费用总额：" +
		 * s3 / 1000 + "元&nbsp;&nbsp;未付费用总额：" + (s3 - s4) / 1000 + "元</span><font
		 * class='x-myZW-fieldset-title'></font><br/><font
		 * class='x-myZW-fieldset-title'></font><span>" }); }
		 * inforpanel.doLayout()
		 */
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
	oneopenliushuiwin : function() {

		var s = this.gridPanel.getSelectionModel().getSelections();
		var record = s[0];
		var flag = 0; // incomeMoney
		if (record.data.payMoney != 0) { // payMoney
			flag = 1;
		}
		if (record.data.fundIntentId == null) {
			Ext.ux.Toast.msg('操作信息', '请先保存');
		} else {
			new SlFundIntentForm({
						fundIntentId : record.data.fundIntentId,
						fundType : record.data.fundType,
						notMoney : record.data.notMoney,
						flag : flag,
						businessType : record.data.businessType,
						companyId : record.data.companyId,
						otherPanel : this.gridPanel
					}).show();
		}

	},
	manyInntentopenliushuiwin : function() {

		var s = this.gridPanel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息', '请选中记录');
			return false;
		} else {
			var a = 0;
			var b = 0;
			var sumnotMoney = 0;
			for (var i = 0; i < s.length; i++) {
				if (s[i].data.payMoney > 0)
					a++;
				if (s[i].data.incomeMoney > 0)
					b++;
				sumnotMoney = sumnotMoney + s[i].data.notMoney;
			}
			if (a > 0 && b > 0) {
				Ext.ux.Toast.msg('操作信息', '请选中的记录支出保持一致');
				return false;

			}

			var ids = $getGdSelectedIds(this.gridPanel, 'fundIntentId');
			var record = s[0];
			var flag = 0; // incomeMoney
			if (record.data.payMoney != 0) { // payMoney
				flag = 1;
			}
			var count = 0;
			for (var i = 0; i < ids.length; i++) {
				if (s[i].data.fundIntentId == null)
					count++;
			}

			if (count > 0) {
				Ext.ux.Toast.msg('操作信息', '请先保存');
			} else {
				new SlFundIntentForm1({
							ids : ids,
							flag : flag,
							fundType : record.data.fundType,
							sumnotMoney : sumnotMoney,
							companyId : record.data.companyId,
							otherPanel : this.gridPanel
						}).show();
			}
		}

	},
	openliushuiwin : function() {
		var mr = this.gridPanel.getStore().getModifiedRecords();// 获得表格是否修改了，修改了提醒先保存
		if (mr.length == 0) {
			var s = this.gridPanel.getSelectionModel().getSelections();
			if (s <= 0) {
				Ext.ux.Toast.msg('操作信息', '请选中记录');
				return false;
			} else if (s.length > 1) {
				this.manyInntentopenliushuiwin();

			} else if (s.length == 1) {
				this.oneopenliushuiwin();
			}
		} else {
			Ext.ux.Toast.msg('操作信息', '数据已经修改，请先保存');
		}
	},
	autocreate : function() {
		/*var fundResource="";
		var investPersonName="";
		if(this.isOwnBpFundProject){
			fundResource=this.object.getCmpByName("ownBpFundProject.fundResource").getValue();
			investPersonName=this.object.getCmpByName("ownBpFundProject.investName").getValue();
		}else{
			fundResource=this.object.getCmpByName("platFormBpFundProject.fundResource").getValue()
			investPersonName=this.object.getCmpByName("platFormBpFundProject.investName").getValue()
		}*/
		var fundResource=null;
		var	investPersonName=null;
		//var payintentPeriod=null;
		var startInterestDate=null;
		var projectMoney=null;
		var intentDate=null;
		//var isStartDatePay=null;
		var count=0;
		if(this.isFlow){
			fundResource=this.object.getCmpByName("ownBpFundProject.fundResource").getValue();
			investPersonName=this.object.getCmpByName("ownBpFundProject.investName").getValue();
		}else{
			count=this.ownerCt.items.items[1].grid_sharteequity.store.getCount();
		}
		
		if(investPersonName || count>0){
			var businessType=this.businessType;
			var gridPanel1=this.gridPanel
			var message=""
			if (this.businessType == "SmallLoan") {
				  startInterestDate=this.bidPlanFinanceInfo.getCmpByName("plBidPlan.startIntentDate").getValue();
				  projectMoney=this.bidPlanFinanceInfo.getCmpByName("bidMoney1").hiddenField.value;
				  intentDate=this.bidPlanFinanceInfo.getCmpByName("plBidPlan.endIntentDate").getValue();
				 //  payintentPeriod=this.object.getCmpByName("platFormBpFundProject.payintentPeriod").getValue();
				 // isStartDatePay=this.object.getCmpByName("platFormBpFundProject.isStartDatePay").getValue();
				  if (startInterestDate == "" || startInterestDate == null) {
					 message = "起息日期";
				  }
				
				if (message != "") {
					Ext.MessageBox.show({
						title : '操作信息',
						msg : message + '不能为空',
						buttons : Ext.MessageBox.OK,
						icon : 'ext-mb-error'
					});
					return null;
				}
			}
			var projId = this.projectId;
			var params1 ={
			    'platFormBpFundProject.startInterestDate': startInterestDate,
			    'platFormBpFundProject.intentDate': intentDate,
			     'platFormBpFundProject.projectMoney': projectMoney,
			    'calcutype':businessType,
			    'flag1':0,
			    'fundResource':fundResource,
			    'projectId':projId,
			    'preceptId':this.preceptId,
			    'bidPlanId':this.bidPlanId,
			    'orderNo':this.orderNo,
			    'businessType':businessType,
			    isHaveLending:"yes"
			}
			var param2={
				projectId : this.projectId,
				flag1 : 1,
				bidPlanId:this.bidPlanId,
				preceptId:this.preceptId,
				slEarlyRepaymentId: this.slEarlyRepaymentId,
				businessType : this.businessType
			}
			var pbar = Ext.MessageBox.wait('款项生成中，可能需要较长时间，请耐心等待...','请等待',{
				interval:200,
			    increment:15
			});
			
			var InterestFundIntentViewObj = this.InterestFundIntentView;
			
			Ext.Ajax.request({
			url : __ctxPath + "/creditFlow/finance/listloanBpFundIntent.do",
			method : 'POST',
			scope:this,
			timeout: 600000,
			success : function(response, request) {
				obj = Ext.util.JSON.decode(response.responseText);
				Ext.ux.Toast.msg('操作信息', '生成成功!');
				pbar.getDialog().close();
				var gridstore = gridPanel1.getStore();
				gridstore.on('beforeload', function(gridstore, o) {
				Ext.apply(o.params, param2);
				
				/**
				 * 生成奖励加息表...
				 */
				if(InterestFundIntentViewObj!=null&&InterestFundIntentViewObj!="undefined"){
					InterestFundIntentViewObj.gridPanel.getStore().load();
				}
				
			});
			gridPanel1.getStore().reload();
			},
			failure : function(response,request) {
				pbar.getDialog().close();
				Ext.ux.Toast.msg('操作信息', '查询任务完成时限操作失败!');
			},
			params : params1
		});
			/*var combox=new Ext.form.ComboBox({
			    triggerAction: 'all',
			    store: new Ext.data.SimpleStore({
					autoLoad : true,
					url : __ctxPath+ '/project/getPayIntentPeriodSlSmallloanProject.do',
					fields : ['itemId', 'itemName'],
					baseParams : {payintentPeriod:payintentPeriod,isStartDatePay:isStartDatePay}
				}),
			    valueField: 'itemId',
			    displayField: 'itemName'
			})
			this.gridPanel.getColumnModel().setEditor(8,combox);
			var gridstore = gridPanel1.getStore();
			gridstore.on('beforeload', function(gridstore, o) {
				Ext.apply(o.params, params1);
			});
			gridPanel1.getStore().reload();
		
			var vRecords = this.gridPanel.getStore().getRange(0,this.gridPanel.getStore().getCount()); // 得到修改的数据（记录对象）
			var vCount = vRecords.length; // 得到记录长度
			var vDatas = '';
			if (vCount > 0) {
				for (var i = 0; i < vCount; i++) {
					if (vRecords[i].data.afterMoney != null&& vRecords[i].data.afterMoney != 0) {
						Ext.Msg.alert('', '有款项已对过账不能被覆盖');
					}
				}
			}*/
		}else{
			Ext.Msg.alert('友好提示','请先选择投资人信息才能生成放款收息表')
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
