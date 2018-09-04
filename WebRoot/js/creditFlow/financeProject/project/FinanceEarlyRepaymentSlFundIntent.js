// var formulatestore = new HT.JsonStore({
// url : __ctxPath + "/finance/listSlFundIntent.do"
// });

FinanceEarlyRepaymentSlFundIntent = Ext.extend(Ext.Panel, {
	isThisEarlyPaymentRecord : null,
	isThisEarlyPaymentRecordId : null,
	isUnLoadData : false,
	// 构造函数
	constructor : function(_cfg) {
		if (typeof(_cfg.projId) != "undefined") {
			this.projectId = _cfg.projId;
		}
		if (typeof(_cfg.businessType) != "undefined") {
			this.businessType = _cfg.businessType;
		}
		if (typeof(_cfg.isThisEarlyPaymentRecord) != "undefined") {
			this.isThisEarlyPaymentRecord = _cfg.isThisEarlyPaymentRecord;
		}
		if (typeof(_cfg.isUnLoadData) != "undefined") {
			this.isUnLoadData = _cfg.isUnLoadData;
		}
		if (typeof(_cfg.isThisEarlyPaymentRecordId) != "undefined") {
			this.isThisEarlyPaymentRecordId = _cfg.isThisEarlyPaymentRecordId;
		}
		if (typeof(_cfg.isHidddenautocreate) != "undefined") {
			this.isHidddenautocreate = _cfg.isHidddenautocreate;
		}
		if (typeof(_cfg.object) != "undefined") {
			this.object = _cfg.object;
		}
		// this.businessType="Financing";
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		FinanceEarlyRepaymentSlFundIntent.superclass.constructor.call(this, {
					layout : 'anchor',
					anchor : '100%',
					items : [{
						xtype : 'panel',
						border : false,
						bodyStyle : 'margin-bottom:5px',
						html : '<br/><B><font class="x-myZW-fieldset-title">【'
								+ this.titleText + '】:</font></B>'
					}, this.gridPanel]
				});

	},// end of the constructor
	// 初始化组件

	initUIComponents : function() {
		var summary = new Ext.ux.grid.GridSummary();
		function totalMoney(v, params, data) {
			return '总计(元)';
		}
		this.ttbar = new Ext.Toolbar({
					items : [{
								text : '增加',
								iconCls : 'btn-add',
								scope : this,
								handler : this.createRs
							}, '-', {
								iconCls : 'btn-del',
								scope : this,
								text : '删除',
								handler : this.removeSelRs
							}, '-', {
								iconCls : 'btn-info-add',
								scope : this,
								text : '生成',
								handler : this.autocreate
							}]
				});
		this.datafield = new Ext.form.DateField({
					format : 'Y-m-d',
					allowBlank : false,
					readOnly : this.isHidden
				})
		var fundTypenodekey = "";
		if (this.businessType == "Financing") {
			fundTypenodekey = "finaning_fund";
		}
		if (this.businessType == "SmallLoan") {
			fundTypenodekey = "financeType";
		}
		this.comboType = new DicIndepCombo({
					editable : false,
					readOnly : this.isHidden,
					lazyInit : false,
					forceSelection : false,
					nodeKey : fundTypenodekey
				})
		this.comboType.store.reload();
		var params1 = {
			projectId : this.projectId,
			flag1 : 1,
			businessType : this.businessType
		};
		url = __ctxPath
				+ "/creditFlow/finance/listbyEarlyRepaymentRecordSlFundIntent.do?isThisEarlyPaymentRecordId="
				+ this.isThisEarlyPaymentRecordId
				+ "&isThisEarlyPaymentRecord=" + this.isThisEarlyPaymentRecord
				+ "&isUnLoadData=" + this.isUnLoadData;
		this.gridPanel = new HT.EditorGridPanel({
			border : false,
			clicksToEdit : 1,
			isShowTbar : this.isHidden ? false : true,
			showPaging : false,
			autoHeight : true,
			hiddenCm : this.isHidden,
			plugins : [summary],
			// 使用RowActions
			// rowActions : true,
			id : 'EarlyRepaymentSlFundIntent',
			url : url,
			baseParams : params1,
			fields : [{
						name : 'fundIntentId'
					}, {
						name : 'fundType'
					}, {
						name : 'fundTypeName'
					}, {
						name : 'intentDate'
					}, {
						name : 'payMoney'
					}, {
						name : 'incomeMoney'
					}, {
						name : 'remark'
					}, {
						name : 'payintentPeriod'
					}, {
						name : 'slSuperviseRecordId'
					},{
						name : 'afterMoney'
					},{
						name : 'interestStarTime'
					},{
						name : 'interestEndTime'
					}],
			tbar : this.isHidden ? null : this.ttbar,
			columns : [ {
				header : '期数',
				dataIndex : 'payintentPeriod',
				editor : new Ext.form.ComboBox({
					triggerAction : 'all',
					readOnly : this.isHidden,
					store : new Ext.data.SimpleStore({
						autoLoad : true,
						url : __ctxPath
								+ '/smallLoan/finance/getPrepayintentPeriodSlEarlyRepaymentRecord.do',
						fields : ['itemId', 'itemName'],
						baseParams : {
							projectId : this.projectId
						}
					}),
					valueField : 'itemId',
					displayField : 'itemName',
					listeners : {
						scope : this,
						'select' : function(combox, record, index) {
							var value = combox.getValue();
							var value1 = value.substring(value.lastIndexOf('.')
											+ 1, value.length);
							combox.setValue(value1)
							var value2 = value.substring(0, value
											.lastIndexOf('.'))
							if (value2 != 0) {
								this.gridPanel.getSelectionModel()
										.getSelected().data.slSuperviseRecordId = value2
							} else {
								this.gridPanel.getSelectionModel()
										.getSelected().data.slSuperviseRecordId = null
							}
							// this.gridPanel.getView().refresh()
						}
					}
				}),
				renderer : function(value, metaData, record, rowIndex,
						colIndex, store) {
					if ((null == record.data.slSuperviseRecordId || record.data.slSuperviseRecordId == 0)
							&& record.data.fundType != 'principalLending') {
						return "第" + value + "期";
					} else if (null != record.data.slSuperviseRecordId
							&& record.data.slSuperviseRecordId != 0) {
						return "展期第" + value + "期"
					} else {
						return value
					}
				}
			},{
				header : '资金类型',
				dataIndex : 'fundType',
				width : 170,
				summaryType : 'count',
				summaryRenderer : totalMoney,
				editor : this.comboType,
				renderer : function(value, metaData, record, rowIndex,
						colIndex, store) {
					var objcom = this.editor;
					var objStore = objcom.getStore();

					var idx = objStore.find("dicKey", value);

					if (idx != "-1") {

						return objStore.getAt(idx).data.itemValue;
					} else {

						if (record.data.fundTypeName == "") {

							record.data.fundTypeName = value

						} else {
							var x = store.getModifiedRecords();
							if (x != null && x != "") {
								record.data.fundTypeName = value
							}
						}

						return record.get("fundTypeName")
					}

				}

			}, {
				header : '计划到账日',
				// xtype : 'datecolumn',
				format : 'Y-m-d',
				dataIndex : 'intentDate',
				sortable : true,
				width : 120,
				renderer : ZW.ux.dateRenderer(this.datafield),
				editor : this.datafield
			}, {
				header : '计划收入金额',
				dataIndex : 'incomeMoney',
				align : 'right',
				width : 127,
				summaryType : 'sum',
				editor : {
					xtype : 'numberfield',
					readOnly : this.isHidden,
					listeners : {
						blur : function(v) {
							if (v.getValue() == "") {
								v.setValue("0.00")
							}

						}

					}
				},
				renderer : function(value, metaData, record, rowIndex,
						colIndex, store) {

					return Ext.util.Format.number(value, ',000,000,000.00')
							+ "元"

				}
			}, {
				header : '计划支出金额',
				dataIndex : 'payMoney',
				align : 'right',
				width : 127,
				summaryType : 'sum',
				editor : {
					xtype : 'numberfield',
					readOnly : this.isHidden,
					listeners : {
						blur : function(v) {
							if (v.getValue() == "") {
								v.setValue("0.00")
							}

						}

					}
				},
				renderer : function(value, metaData, record, rowIndex,
						colIndex, store) {
					return Ext.util.Format.number(value, ',000,000,000.00')
							+ "元"

				}

			}, {
				
				header : '计息开始日期',
				dataIndex : 'interestStarTime',
				format : 'Y-m-d',
				width : 120,
				
				renderer : ZW.ux.dateRenderer(this.datafield),
				 editor :this.datafield
				/*renderer : function(value, metaData, record, rowIndex,
						colIndex, store) {
							if(value!=null){
						alert(value);
						v= Ext.util.Format.date(value, 'Y-m-d');
							}else{
								v="";
							}
						return v;
						}*/
			}, {
				header : '计息结束日期',
				dataIndex : 'interestEndTime',
				format : 'Y-m-d',
				 editor :this.datafield,
				width : 120,
				
				renderer : ZW.ux.dateRenderer(this.datafield),
				 editor :this.datafield
				/*renderer : function(value, metaData, record, rowIndex,
						colIndex, store) {
							if(value!=null){
						v= Ext.util.Format.date(value, 'Y-m-d');
							}else{
								v="";
							}
						return v;
						}*/
			}, {
				header : '备注',
				dataIndex : 'remark',
				align : 'center',
				width : 568,
				editor : new Ext.form.TextField({
							allowBlank : false,
							readOnly : this.isHidden
						})
			}/*
				 * , {
				 * 
				 * header :'', dataIndex : '', scope : this, editor : new
				 * Ext.form.TextField( { id : 'fundIntentJsonData2', name :
				 * 'fundIntentJsonData', allowBlank : true }) }
				 */],
			listeners : {
				afteredit : function(e) {
					if(e.record.data['fundType'] =='principalLending' || 
						e.record.data['fundType'] =='backInterest' || 
						e.record.data['fundType']=='FinancingInterest' ||
						e.record.data['fundType']=='FinancingRepay' ||
						e.record.data['fundType']=='financingconsultationMoney' ||
						e.record.data['fundType']=='financingserviceMoney'){
						e.record.set('incomeMoney',0);
						e.record.commit();
						
					}else{
						e.record.set('payMoney',0);
						e.record.commit()
					}
				}
			}
				// end of columns
		});

	},
	autocreate : function() {
		var gridPanel1 = this.gridPanel
		var projId = this.projectId;
		var calcutype = this.businessType;

		var gridPanel1 = this.gridPanel
		var operationType = null;
		var accrualtype = null;
		var payaccrualType = null;
		var earlyProjectMoney = null;
		var earlyDate = null;
		var surplusEndDate = null;
		var isStartDatePay = null;
		var payintentPerioDate = null;
		var accrual = null;
		var financeServiceOfRate = null;
		var managementConsultingOfRate = null;
		var payintentPeriod = null;
		var payintentPerioddays = null;
		var prepayintentPeriod = null;
		var slEarlyRepaymentId = null;
		//if (this.businessType == "SmallLoan") {
			/*accrualtype = this.object
					.getCmpByName("slEarlyRepaymentRecord.accrualtype")
					.getValue();*/
			// payaccrualType=this.object.getCmpByName("slEarlyRepaymentRecord.payaccrualType").getValue();
			earlyProjectMoney = this.object
					.getCmpByName("slEarlyRepaymentRecord.earlyProjectMoney")
					.getValue();
			// surplusEndDate=this.object.getCmpByName("slEarlyRepaymentRecord.surplusEndDate").getValue();
			// isStartDatePay=this.object.getCmpByName("slEarlyRepaymentRecord.isStartDatePay").getValue();
			// payintentPerioDate=this.object.getCmpByName("slEarlyRepaymentRecord.payintentPerioDate").getValue();
			 accrual=this.object.getCmpByName("slEarlyRepaymentRecord.accrual").getValue();
			// managementConsultingOfRate=this.object.getCmpByName("slEarlyRepaymentRecord.managementConsultingOfRate").getValue();
			// financeServiceOfRate=this.object.getCmpByName("slEarlyRepaymentRecord.financeServiceOfRate").getValue();
			// payintentPeriod=this.object.getCmpByName("slEarlyRepaymentRecord.payintentPeriod").getValue();
			/*prepayintentPeriod = this.object
					.getCmpByName("slEarlyRepaymentRecord.prepayintentPeriod")
					.getValue();*/
			earlyDate = this.object
					.getCmpByName("slEarlyRepaymentRecord.earlyDate")
					.getValue();
			slEarlyRepaymentId = this.object
					.getCmpByName("slEarlyRepaymentRecord.slEarlyRepaymentId")
					.getValue();
		//}
		// var
		// isPreposePayAccrualCheck=this.object.getCmpByName("isPreposePayAccrualCheck").getValue();
		// var calcutype=this.businessType; //业务品种
		// var projId=this.projectId;
		// var
		// isPreposePayAccrualCheck=this.object.getCmpByName("isPreposePayAccrualCheck").getValue();
		// var message="";
		// if(accrualtype=="请选择" || accrualtype==null || accrualtype==""){
		// message="计息方式";
		// }else if(payaccrualType=="请选择" || payaccrualType==null){
		// message="付息方式";
		// } else if(managementConsultingOfRate ==="" ||
		// managementConsultingOfRate==null){
		// message="管理咨询费率";
		// } else if(financeServiceOfRate ==="" || financeServiceOfRate==null){
		// message="财务服务费率";
		// }
		//	
		var message = "";
		if (earlyProjectMoney == "" || earlyProjectMoney == null) {
			message = "提前还款本金金额";
		}
		if (this.preaccrualtype == 'sameprincipal'
				|| this.preaccrualtype == 'sameprincipalandInterest') {
			if (prepayintentPeriod == "" || prepayintentPeriod == null) {
				message = "提前还款期数";
			}
		} else if (this.preaccrualtype == 'singleInterest'
				|| this.preaccrualtype == 'ontTimeAccrual') {
			if (earlyDate == "" || earlyDate == null) {
				message = "提前还款日期";
			}
		}
		// if(payintentPeriod=="" || payintentPeriod==null ){
		// message="放款期限";
		// }
		// if(accrual=="" || accrual==null){
		// message="贷款利率";
		// }
		// if(payaccrualType=="ontTimeAccrual"){
		//							   
		// if(intentDate=="" || intentDate==null){
		// message="还款日期";
		// }
		//							   	
		// }else{
		// if(isStartDatePay=="1"){
		//								    
		// if(payintentPerioDate=="" || payintentPerioDate==null){
		// message="固定在几号还款";
		// }
		//									  
		// }
		//							   
		//							   
		// }

		if (message != "") {
			Ext.MessageBox.show({
						title : '操作信息',
						msg : message + '不能为空',
						buttons : Ext.MessageBox.OK,
						icon : 'ext-mb-error'
					});
			return null;
		}
		// if(calcutype=="SmallLoan" &&payaccrualType=="ontTimeAccrual" &&
		// intentDate <= startDate){
		//	        	 
		// message="还款日期必须在放款日期之后";
		// Ext.MessageBox.show({
		// title : '操作信息',
		// msg : message,
		// buttons : Ext.MessageBox.OK,
		// icon : 'ext-mb-error'
		// });
		// return null;
		//	        	 	
		//	        	 	
		// }

		//			
		// var isPreposePayAccrual=0;
		// if(isPreposePayAccrualCheck == true){
		// var isPreposePayAccrual=1;
		// }

		gridPanel1.getStore().setBaseParam('projectId', projId);
		gridPanel1.getStore().setBaseParam('slEarlyRepaymentRecord.accrualtype', accrualtype);
		gridPanel1.getStore().setBaseParam('slEarlyRepaymentRecord.earlyProjectMoney', earlyProjectMoney);
		gridPanel1.getStore().setBaseParam('slEarlyRepaymentRecord.accrual', accrual);
		gridPanel1.getStore().setBaseParam('slEarlyRepaymentRecord.slEarlyRepaymentId',slEarlyRepaymentId);
		gridPanel1.getStore().setBaseParam('slEarlyRepaymentRecord.earlyDate',earlyDate);
		gridPanel1.getStore().setBaseParam('calcutype', calcutype);
		this.gridPanel.getStore().setBaseParam('flag1', 0);
		// gridPanel1.getStore().setBaseParam('slEarlyRepaymentRecord.isPreposePayAccrual',
		// isPreposePayAccrual);
		// gridPanel1.getStore().setBaseParam('slEarlyRepaymentRecord.payaccrualType',
		// payaccrualType);
	
		// gridPanel1.getStore().setBaseParam('slEarlyRepaymentRecord.surplusEndDate',
		// surplusEndDate==null || surplusEndDate==""?new Date:surplusEndDate);
		// gridPanel1.getStore().setBaseParam('slEarlyRepaymentRecord.isStartDatePay',
		// isStartDatePay);
		// gridPanel1.getStore().setBaseParam('slEarlyRepaymentRecord.payintentPerioDate',
		// payintentPerioDate);
		
		// gridPanel1.getStore().setBaseParam('slEarlyRepaymentRecord.financeServiceOfRate',
		// financeServiceOfRate);
		// gridPanel1.getStore().setBaseParam('slEarlyRepaymentRecord.managementConsultingOfRate',
		// managementConsultingOfRate);
		// gridPanel1.getStore().setBaseParam('slEarlyRepaymentRecord.payintentPeriod',
		// payintentPeriod);

		
		/*gridPanel1.getStore().setBaseParam('prepayintentPeriod1',
				this.object.getCmpByName("prepayintentPeriod1").getValue());
		gridPanel1.getStore()
				.setBaseParam('slEarlyRepaymentRecord.prepayintentPeriod',
						prepayintentPeriod);*/
		
		
		
		var combox = new Ext.form.ComboBox({
			triggerAction : 'all',
			store : new Ext.data.SimpleStore({
				autoLoad : true,
				url : __ctxPath
						+ '/smallLoan/finance/getPrepayintentPeriodSlEarlyRepaymentRecord.do',
				fields : ['itemId', 'itemName'],
				baseParams : {
					projectId : this.projectId,
					businessType : this.businessType
				}
			}),
			valueField : 'itemId',
			displayField : 'itemName',
			listeners : {
				scope : this,
				'select' : function(combox, record, index) {
					var value = combox.getValue();
					var value1 = value.substring(value.lastIndexOf('.') + 1,
							value.length);
					combox.setValue(value1)
					var value2 = value.substring(0, value.lastIndexOf('.'))
					if(value2!=0){
						gridPanel1.getSelectionModel().getSelected().data.slSuperviseRecordId = value2
					}else{
						gridPanel1.getSelectionModel().getSelected().data.slSuperviseRecordId = null

					}
					// this.gridPanel.getView().refresh()
				}
			}
		})
		gridPanel1.getColumnModel().setEditor(2, combox);
		gridPanel1.getStore().reload();
		var vRecords = this.gridPanel.getStore().getRange(0,
				this.gridPanel.getStore().getCount()); // 得到修改的数据（记录对象）
		var vCount = vRecords.length; // 得到记录长度
		var vDatas = '';
		if (vCount > 0) {
			for (var i = 0; i < vCount; i++) {
				if (vRecords[i].data.afterMoney != null
						&& vRecords[i].data.afterMoney != 0) {

					Ext.Msg.alert('', '有款项已对过账不能被覆盖');
				}
			}
		}

	},

	createRs : function() {
		var payintentPeriod1 = this.object.getCmpByName("prepayintentPeriod1")
				.getValue()
		var payintentPeriod = payintentPeriod1.substring(payintentPeriod1
						.lastIndexOf('.')
						+ 1, payintentPeriod1.length)
		var slSuperviseRecordId = payintentPeriod1.substring(0,
				payintentPeriod1.lastIndexOf('.'))
		this.datafield.setValue('');
		var newRecord = this.gridPanel.getStore().recordType;
		
		var payintentPeriodNew = this.object.getCmpByName("slEarlyRepaymentRecord.prepayintentPeriod").getValue();
		var newData = new newRecord({
					fundIntentId : 0,
					fundType : '',
					payMoney : 0,
					incomeMoney : 0,
					intentDate : new Date(),
					remark : '',
					payintentPeriod : payintentPeriodNew,
					slSuperviseRecordId : null
				});
		var combox = new Ext.form.ComboBox({
			triggerAction : 'all',
			store : new Ext.data.SimpleStore({
				autoLoad : true,
				url : __ctxPath
						+ '/smallLoan/finance/getPrepayintentPeriodSlEarlyRepaymentRecord.do',
				fields : ['itemId', 'itemName'],
				baseParams : {
					projectId : this.projectId,
					businessType : this.businessType
				}
			}),
			valueField : 'itemId',
			displayField : 'itemName',
			listeners : {
				scope : this,
				'select' : function(combox, record, index) {
					var value = combox.getValue();
					var value1 = value.substring(value.lastIndexOf('.') + 1,
							value.length);
					combox.setValue(value1)
					var value2 = value.substring(0, value.lastIndexOf('.'))
					if (value2 != 0) {
						this.gridPanel.getSelectionModel().getSelected().data.slSuperviseRecordId = value2
					} else {
						this.gridPanel.getSelectionModel().getSelected().data.slSuperviseRecordId = null
					}
					// this.gridPanel.getView().refresh()
				}
			}
		})
		this.gridPanel.getColumnModel().setEditor(2, combox);
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
				if (vRecords[i].data.fundType != null
						&& vRecords[i].data.fundType != "") {
					// if(vRecords[i].data.fundIntentId == null ||
					// vRecords[i].data.fundIntentId == "") {
					if(null==vRecords[i].data.afterMoney || vRecords[i].data.afterMoney==0){
						st = {
							//"fundIntentId" : vRecords[i].data.fundIntentId,
							"fundType" : vRecords[i].data.fundType,
							"incomeMoney" : vRecords[i].data.incomeMoney,
							"payMoney" : vRecords[i].data.payMoney,
							"intentDate" : vRecords[i].data.intentDate,
							"remark" : vRecords[i].data.remark==''?null:vRecords[i].data.remark,
							'payintentPeriod' : vRecords[i].data.payintentPeriod
							//'slSuperviseRecordId' : vRecords[i].data.slSuperviseRecordId==''?null:vRecords[i].data.slSuperviseRecordId
							,"interestStarTime":vRecords[i].data.interestStarTime,"interestEndTime":vRecords[i].data.interestEndTime
						};
						vDatas += Ext.util.JSON.encode(st) + '@';
					}
					// }else{
					// st={"fundIntentId":vRecords[i].data.fundIntentId,"fundType":vRecords[i].data.fundType,"incomeMoney":vRecords[i].data.incomeMoney,"payMoney":vRecords[i].data.payMoney,"intentDate":vRecords[i].data.intentDate,"remark":vRecords[i].data.remark};
					// vDatas += Ext.util.JSON
					// .encode(st) + '@';
					//									 
					// }
				}
			}
			vDatas = vDatas.substr(0, vDatas.length - 1);
			
		}
		// Ext.getCmp('fundIntentJsonData2').setValue(vDatas);
		// this.gridPanel.getColumnsgetEditor().getCmpByName('fundIntentJsonData').setValue(vDatas);
		// alert(this.gridPanel.getEditor().getCmpByName('fundIntentJsonData').getValue());
		return vDatas;
	},
	saveRs : function() {
		var data = this.getGridDate();
		Ext.Ajax.request({
					url : __ctxPath
							+ '/creditFlow/finance/savejsonSlFundIntent.do',
					method : 'POST',
					scope : this,
					params : {
						slFundIentJson : data,
						projectId : this.projectId,
						businessType : this.businessType
					},
					success : function(response, request) {
						this.gridPanel.getStore().setBaseParam('flag1', 1);
						this.gridPanel.getStore().reload();

					}
				});

	},
	save : function() {
		this.gridPanel.getStore().setBaseParam('flag1', 1);
		this.gridPanel.getStore().reload();

	},
	removeSelRs : function() {
		var fundIntentGridPanel = this.gridPanel;
		var projId = this.projectId
		var deleteFun = function(url, prame, sucessFun, i, j) {
			Ext.Ajax.request({
				url : url,
				method : 'POST',
				success : function(response) {
					if (response.responseText.trim() == '{success:true}') {
						if (i == (j - 1)) {
							Ext.ux.Toast.msg('操作信息', '删除成功!');
						}
						sucessFun();
					} else if (response.responseText.trim() == '{success:false}') {
						Ext.ux.Toast.msg('操作信息', '删除失败!');
					}
				},
				params : prame
			});
		};
		var a = fundIntentGridPanel.getSelectionModel().getSelections();
		if (a <= 0) {
			Ext.ux.Toast.msg('操作信息', '请选择要删除的记录');
			return false;
		};
		Ext.Msg.confirm("提示!", '确定要删除吗？', function(btn) {

			if (btn == "yes") {
				// grid_sharteequity.stopEditing();
				var s = fundIntentGridPanel.getSelectionModel().getSelections();
				for (var i = 0; i < s.length; i++) {
					var row = s[i];
					if (row.data.fundIntentId == null
							|| row.data.fundIntentId == '') {
						fundIntentGridPanel.getStore().remove(row);
					} else {
						deleteFun(
								__ctxPath
										+ '/creditFlow/finance/deleteSlFundIntent.do',
								{
									fundIntentId : row.data.fundIntentId,
									projectId : projId,
									businessType : this.businessType
								}, function() {
									fundIntentGridPanel.getStore().remove(row);
									fundIntentGridPanel.getStore().reload();
								}, i, s.length);

					}

				}

			}

		});

	}

});