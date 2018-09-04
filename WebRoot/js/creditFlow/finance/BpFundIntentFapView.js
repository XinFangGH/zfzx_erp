var slprojectMoney = 0;
var slnotprojectMoney = 0;
BpFundIntentFapView= Ext.extend(Ext.Panel, {
	jStore:null,
	isHiddenseesumqlideBtn:true,
	isOwnBpFundProject:true,
//	fundResource:0,
	hiddenCheck:true,
	isFlow:false,
	// 构造函数
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (typeof(_cfg.isHiddenseesumqlideBtn) != "undefined") {
			this.isHiddenseesumqlideBtn = _cfg.isHiddenseesumqlideBtn;
		}
		if (typeof(_cfg.isOwnBpFundProject) != "undefined") {
			this.isOwnBpFundProject = _cfg.isOwnBpFundProject;
		}
		/*if (typeof(_cfg.fundResource) != "undefined") {
			this.fundResource = _cfg.fundResource;
		}*/
		if (typeof(_cfg.isFlow) != "undefined") {
			this.isFlow = _cfg.isFlow;
		}
		if(typeof(_cfg.hiddenCheck)!="undefined"){
			this.hiddenCheck = _cfg.hiddenCheck;
		}
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		BpFundIntentFapView.superclass.constructor.call(this, {
			name : "BpFundIntentFapView",
			hidden : this.isHiddenPanel,
			region : 'center',
			layout : 'anchor',
			anchor : '100%',
			items : [{
				xtype : 'panel',
				border : false,
				bodyStyle : 'margin-bottom:5px',
				html : '<br/><B><font class="x-myZW-fieldset-title">【投资人放款收息表】</font></B>',
				hidden : this.isHiddenTitle
			}, this.gridPanel/*, this.infoPanel*/]
		});
	},
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
			items : [/*{
				html:'<font class="x-myZW-fieldset-title">【投资人放款收息表】</font>'
			},*/{
				iconCls : 'btn-add',
				text : '增加',
				xtype : 'button',
				scope : this,
				hidden:this.isHiddenAdd,
				handler : this.createRs
			}, new Ext.Toolbar.Separator({
				hidden : this.isHiddenAdd
			}), {
				iconCls : 'btn-del',
				text : '删除',
				xtype : 'button',
				scope : this,
				hidden:this.isHiddenDel,
				handler : this.removeSelRs
			}, new Ext.Toolbar.Separator({
				hidden : this.isHiddenDel
			}),{
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
			}), {
				iconCls : 'btn-details',
				text : '项目总流水记录',
				xtype : 'button',
				hidden : this.isHiddenseesumqlideBtn,
				scope : this,
				handler : function() {
					this.projectWaterReconciliationInfo(2);
				}
			}, {
				iconCls : 'btn-info-add',
				text : '生成',
				xtype : 'button',
				scope : this,
				hidden:this.isHiddenAutoCreate,
				handler : this.confirmRs
			}, new Ext.Toolbar.Separator({
				hidden : this.isHiddenAutoCreate
			}), {
				iconCls : 'btn-xls',
				text : '导出Excel',
				xtype : 'button',
				scope : this,
				hidden:this.isHiddenExcel,
				handler : function(){
					window.open(__ctxPath + "/creditFlow/finance/outputExcelSlFundIntent.do?inverstPersonId="+this.inverstPersonId+"&projectId="+this.projectId,'_blank');
				}
			}, "->",{
				xtype : 'radio',
				boxLabel : '按投资人分组显示',
				name : "paixu",
				scope : this,
				hidden: this.hiddenPx,
				listeners : {
					scope : this,
					'check':function(com,flag){
						if(flag==true){
							this.gridPanel.getView().enableGrouping=true
							this.gridPanel.getView().refresh()
							this.related(false);
						}
					}
				}
			}, new Ext.Toolbar.Separator({
				hidden : this.hiddenPx
			}), {
				xtype : 'radio',
				name : "paixu",
				boxLabel : '按计划到账日期排序显示',
				scope : this,
				checked : false,
				hidden: this.hiddenPx,
				listeners : {
					scope : this,
					'check':function(com,flag){
						if(flag==true){
							this.gridPanel.getView().enableGrouping=false
							this.gridPanel.getView().refresh()
							this.related(true);
						}
					}
				}
			}, ' ', ' ', ' ', ' ']
		});
		var url=__ctxPath + "/creditFlow/finance/listBpFundIntent.do"
		if(null!=this.inverstPersonId){
			
			url=__ctxPath + "/creditFlow/finance/listOfInverstPersonBpFundIntent.do?inverstPersonId="+this.inverstPersonId
		}
		 this.reader = new Ext.data.JsonReader({
			root : "result",
			totalProperty : "totalCounts"
			}, [{
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
			},{
				name : 'investPersonId'
			},{
				name : 'investPersonName'
			},{
				name : 'remark'
			},{
				name : 'payintentPeriod'
			},{
				name : 'isValid'
			},{
				name : 'factDate'
			},{
				name : 'afterMoney'
			},{
				name : 'notMoney'
			},{
				name : 'flatMoney'
			},{
				name : 'fundResource'
			},{
				name : 'preceptId'
			},{
			name : 'bidPlanId'
			},{
			name : 'orderNo'
			}, {
				name : 'interestStarTime'
			}, {
				name : 'interestEndTime'
			}]);
		this.jStore = new Ext.data.GroupingStore({
			url : url,
			root : 'result',
			reader:this.reader,
			groupField : 'investPersonName'
		});
		this.jStore.load({
			params : {
				projectId : this.projectId,
				businessType : this.businessType,
				preceptId:this.preceptId,
				bidPlanId:this.bidPlanId,
				slEarlyRepaymentId: this.slEarlyRepaymentId,
				flag1:1
			}
		});
		var  investPersonName=function(data, cellmeta, record){
       	   if(typeof(record.get("investPersonName"))!="undefined"){
       	   		 if(null!=record.data.payintentPeriod && record.data.payintentPeriod){
       	   		 	
       	   		 }
       	   		 if(record.data.isValid == 1){
       	   		 	return '<font style="font-style:italic;text-decoration: line-through;color:gray">'+ record.get("investPersonName")+ "</font>"
       	   		 }else{
	          		 return record.get("investPersonName");
       	   		 }
       	   }else{
       	   		if(record.data.isValid == 1){
       	   		 	return '<font style="font-style:italic;text-decoration: line-through;color:gray">未知</font>'
       	   		}else{
	       	   		return '未知'
       	   		}
       	   }
       }
		this.projectFundsm = new Ext.grid.CheckboxSelectionModel({
			header : '序号'
		});
		this.gridPanel = new HT.EditorGridPanel({
			border : false,
			name : 'gridPanel',
			scope : this,
			store : this.jStore,
			autoScroll : true,
			autoWidth : true,
			layout : 'anchor',
			clicksToEdit : 1,
			view : new Ext.grid.GroupingView({
				forceFit : true,
				enableGrouping:false,
				groupTextTpl : '{text}'
			
			}),
			bbar : false,
			tbar : this.topbar,
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
				header:'资金来源',
				dataIndex:'fundResource',
				sortable : true,
				width : 100,
				scope : this,
				editor:{
					xtype:'combo',
					id : 'type',
					mode : 'local',
				    displayField : 'name',
				    valueField : 'value',
				    width : 70,
				    readOnly:this.isHidden,
				    store : new Ext.data.SimpleStore({
							fields : ["name", "value"],
							data : [["企业", "1"],["个人", "0"]]
					}),
					triggerAction : "all",
					listeners : {
						scope : this,
						'select' : function(combox,record,index){
							var grid_sharteequity=this.gridPanel;
							var r=combox.getValue();
							var personCom = new Ext.form.ComboBox({
								triggerClass : 'x-form-search-trigger',
								resizable : true,
								onTriggerClick : function() {
									selectInvestEnterPrise(selectInvestEnterpriseObj,r,false);
								},
								mode : 'remote',
								editable : false,
								lazyInit : false,
								allowBlank : false,
								typeAhead : true,
								minChars : 1,
								width : 100,
								listWidth : 150,
								store : new Ext.data.JsonStore({}),
								triggerAction : 'all',
								listeners : {
									'select' : function(combo,record,index) {
										grid_sharteequity.getView().refresh();
									}
								}
							})
							var ComboBox = new Ext.grid.GridEditor(personCom);
							grid_sharteequity.getColumnModel().setEditor(4,ComboBox);
						}
					}
				},
				renderer : function(value) {
						return "个人";
				}
			},{
				header : '投资方',
				dataIndex : 'investPersonName',
				readOnly:this.isHidden,
				sortable : true,
				width : 100,
				scope : this,
				summaryType : 'count',
				summaryRenderer : totalMoney
			},/*,{
				header : '投资人',
				hidden : true,
				dataIndex : 'investPersonId'
//				renderer : investPersonName
			},{
				header : '投资人',
				dataIndex : 'investPersonName',
				editor :new Ext.form.ComboBox({
							triggerClass : 'x-form-search-trigger',
							resizable : true,
							onTriggerClick : function() {
								selectInvestPerson(selectPersonObj);
								//selectPWName(selectPersonObj);
							},
							mode : 'remote',
							editable : false,
							lazyInit : false,
							allowBlank : false,
							typeAhead : true,
							minChars : 1,
							width : 100,
							listWidth : 150,
							readOnly:this.isHidden,
							store : new Ext.data.JsonStore({}),
							triggerAction : 'all',
							listeners : {
								scope : this,
								'select' : function(combo,record,index) {
									grid.getView().refresh();
								},
								'blur' : function(f) {
									if (f.getValue() != null && f.getValue() != '') {
									}
								}
							}
						}),
				renderer : investPersonName
			}, */{
				header : '资金类型',
				dataIndex : 'fundType',
				editor : this.comboType,
				width : 107,
				summaryType : 'count',
				summaryRenderer : totalMoney,
				renderer : function(value, metaData, record, rowIndex,colIndex, store) {
					var objcom = this.editor;
					var objStore = objcom.getStore();
					var idx = objStore.find("dicKey", value);
					if (idx != "-1") {
						if (record.data.isValid == 1) {
							return '<font style="font-style:italic;text-decoration: line-through;color:gray">'+ objStore.getAt(idx).data.itemValue+ "</font>"
						}else{
							return objStore.getAt(idx).data.itemValue;
						}
					} else {
						if (record.data.isValid == 1) {
							return '<font style="font-style:italic;text-decoration: line-through;color:gray">'+ record.get("fundTypeName")+ "</font>"
						}else{
							return record.get("fundTypeName");
						}
					}
				}
			}, {
				header : '计划到帐日',
				dataIndex : 'intentDate',
				format : 'Y-m-d',
				editor : this.datafield,
				fixed : true,
				width : 80,
				renderer : function(value, metaData, record, rowIndex,colIndex, store) {
					var v;
					try {
						if (typeof(value) == "string") {
							v = value;
						}else{
						 	v=Ext.util.Format.date(value, 'Y-m-d');
						}
					} catch (err) {
						v = value;
					}
					if (record.data.isValid == 1) {
						return '<font style="font-style:italic;text-decoration: line-through;color:gray">'+ v + "</font>";
					} else {
						return v;
					}
				}	
			}, {
				header : '投资客户计划收入金额',
				dataIndex : 'incomeMoney',
				summaryType : 'sum',
				align : 'right',
				editor : {
					xtype : 'numberfield',
					allowBlank : false,
					readOnly : this.isHidden1
				},
				renderer : function(value, metaData, record, rowIndex,colIndex, store) {
					if (record.data.isValid == 1) {
						return '<font style="font-style:italic;text-decoration: line-through;color:gray">'+ Ext.util.Format.number(value,',000,000,000.00')+ "元</font>"
					} else {
						return Ext.util.Format.number(value, ',000,000,000.00')+ "元";
					}
				}
			}, {
				header : '投资客户计划支出金额',
				dataIndex : 'payMoney',
				align : 'right',
				summaryType : 'sum',
				editor : {
					xtype : 'numberfield',
					allowBlank : false,
					readOnly : this.isHidden1
				},
				renderer : function(value, metaData, record, rowIndex,colIndex, store) {
					if (record.data.isValid == 1) {
						return '<font style="font-style:italic;text-decoration: line-through;color:gray">'+ Ext.util.Format.number(value,',000,000,000.00')+ "元</font>"
					} else {
						return Ext.util.Format.number(value, ',000,000,000.00')+ "元";
					}
				}
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
			},{
				header : '实际开始时间',
				dataIndex : 'interestStarTime'
			},{
				header : '实际截止时间',
				dataIndex : 'interestEndTime'
			}/*,{
				header : '备注',
				dataIndex: 'remark',
				editor : {
					xtype : 'textfield',
					readOnly :this.isHidden
				},
				renderer : function(value, metaData, record, rowIndex,colIndex, store){
					if(null!=value){
						if (record.data.isValid == 1) {
							return '<font style="font-style:italic;text-decoration: line-through;color:gray">'+value+ "</font>"
						} else {
							return value;
						}
					}
				}
			}*/,{
				header : '实际到帐日',
				dataIndex : 'factDate',
				format : 'Y-m-d',
				hidden:this.hiddenCheck,
				editor : this.datafield,
				fixed : true,
				width : 80,
				renderer : function(value, metaData, record, rowIndex,colIndex, store) {
					var v;
					try {
						if (typeof(value) == "string") {
							v = value;
						}else{
						 	v=Ext.util.Format.date(value, 'Y-m-d');
						}
					} catch (err) {
						v = value;
					}
					if (record.data.isValid == 1) {
						return '<font style="font-style:italic;text-decoration: line-through;color:gray">'+ v + "</font>";
					} else {
						return v;
					}
				}	
			}, {
				header : '已对账金额',
				summaryType : 'sum',
				hidden:this.hiddenCheck,
				dataIndex : 'afterMoney',
				renderer : function(value, metaData, record, rowIndex,colIndex, store) {
					if (record.data.isValid == 1) {
						return '<font style="font-style:italic;text-decoration: line-through;color:gray">'+ Ext.util.Format.number(value,',000,000,000.00')+ "元</font>"
					} else {
						return Ext.util.Format.number(value, ',000,000,000.00')+ "元";
					}
				}
			},{
				header : '未对账金额',
				summaryType : 'sum',
				hidden:this.hiddenCheck,
				dataIndex : 'notMoney',
				renderer : function(value, metaData, record, rowIndex,colIndex, store) {
					if (record.data.isValid == 1) {
						return '<font style="font-style:italic;text-decoration: line-through;color:gray">'+ Ext.util.Format.number(value,',000,000,000.00')+ "元</font>"
					} else {
						return Ext.util.Format.number(value, ',000,000,000.00')+ "元";
					}
				}
			}, {
				header : '平账金额',
				width : 150,
				hidden:true,
				dataIndex : 'flatMoney',
				renderer : function(value, metaData, record, rowIndex,colIndex, store) {
					if (record.data.isValid == 1) {
						return '<font style="font-style:italic;text-decoration: line-through;color:gray">'+ Ext.util.Format.number(value,',000,000,000.00')+ "元</font>"
					} else {
						return Ext.util.Format.number(value, ',000,000,000.00')+ "元";
					}
				}
			}],
			listeners : {
				scope : this,
				afteredit : function(e) {
					if (e.record.data['fundType'] == 'principalLending') {
						e.record.set('incomeMoney', 0);
						e.record.commit();
					} else {
						e.record.set('payMoney', 0);
						e.record.commit()
					}
				}
			}
		});
		var grid=this.gridPanel;
      	var selectPersonObj=function(obj){
			  grid.getSelectionModel().getSelected().data['investPersonId'] = obj.perId;
			  grid.getSelectionModel().getSelected().data['investPersonName'] = obj.perName;
			  grid.getStore().groupBy('investPersonId',true)
			  grid.getView().refresh();
		}
		var selectInvestEnterpriseObj=function(obj){
			Ext.getCmp('investId').setValue(obj.id);
			Ext.getCmp('investName').setValue(obj.InvestEnterpriseName);
		}
	/*	this.infoPanel = new Ext.Panel({
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
			this.fillDatas(this.projectId, this.businessType);
		}*/

	},// end of the initComponents()

	createRs : function() {
		var payintentPeriod="";
		var isStartDatePay="";
		var fundResource="";
		var investName="";
		if(this.isOwnBpFundProject){
			payintentPeriod=this.object.getCmpByName("ownBpFundProject.payintentPeriod").getValue();
			isStartDatePay=this.object.getCmpByName("ownBpFundProject.isStartDatePay").getValue();
			fundResource="Pawn"==this.object.getCmpByName("ownBpFundProject.fundResource").getValue()?'1':'0';
			investName=this.object.getCmpByName("ownBpFundProject.investName").getValue();
		}else{
			payintentPeriod=this.object.getCmpByName("platFormBpFundProject.payintentPeriod").getValue();
			isStartDatePay=this.object.getCmpByName("platFormBpFundProject.isStartDatePay").getValue()
		}
		this.datafield.setValue('');
		var newRecord = this.gridPanel.getStore().recordType;
		var newData = new newRecord({
			fundResource:fundResource,
			investPersonName:investName,
			fundIntentId : '',
			fundType : '',
			payMoney : 0,
			incomeMoney : 0,
			intentDate : new Date(),
			remark : '',
			payintentPeriod:payintentPeriod
		});
		var combox=new Ext.form.ComboBox({
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
		this.gridPanel.stopEditing();
		this.gridPanel.getStore().insert(this.gridPanel.getStore().getCount(),newData);
		this.gridPanel.getView().refresh();
		this.gridPanel.getSelectionModel().selectRow((this.gridPanel.getStore().getCount() - 1));
		this.gridPanel.startEditing(0, 0);
		this.gridPanel.getStore().sortInfo={field:'investPersonId',direction:"ASC"}
	//	this.gridPanel.getView().refresh();
	},
	
	confirmRs : function(){
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
			var bpFundProjectId = null;
		 	var operationType=null;
		 	var startInterestDate = null;
		 	var accrual = null;
		 	var accrualtype=null;
		 	var yearAccrualRate = null;
		 	var payaccrualType=null;
		 	var projectMoney=null;
		 	var isStartDatePay=null;
		 	var startDate=null;
		 	var payintentPerioDate=null;
		 	var accrualnew=null;
		 	var financeServiceOfRate=null;   
		 	var managementConsultingOfRate=null;
		 	var payintentPeriod=null;
		  	var payintentPerioddays=null;
		  	var intentDate=null;
		   	var dayOfEveryPeriod=null;
		   	var isPreposePayConsultingCheck=null;
		   	var isPreposePayAccrual=null;
		   	var isInterestByOneTime=null;
		   	var dateMode=null;
		   	var dayAccrualRate=null;
		   	var dayManagementConsultingOfRate=null;
		   	var dayFinanceServiceOfRate=null;
		   	var investId=null;
		   	var preceptId=null;
		   	var balanceRate=null;
		   	var riskRate=null; //保证金比例
			if (this.businessType == "SmallLoan") {
				  startInterestDate=this.bidPlanFinanceInfo.getCmpByName("plBidPlan.startIntentDate").getValue();
				   projectMoney=this.bidPlanFinanceInfo.getCmpByName("bidMoney1").hiddenField.value;
				    intentDate=this.bidPlanFinanceInfo.getCmpByName("plBidPlan.endIntentDate").getValue();
				if(this.isOwnBpFundProject){
					  accrualtype=this.object.getCmpByName("ownBpFundProject.accrualtype").getValue();
					  accrual = this.object.getCmpByName("ownBpFundProject.accrual").getValue();
					  payaccrualType=this.object.getCmpByName("ownBpFundProject.payaccrualType").getValue();
					 
					  isStartDatePay=1;//this.object.getCmpByName("ownBpFundProject.isStartDatePay").getValue();//用固定在形式放款
					  startDate=this.object.getCmpByName("ownBpFundProject.startDate").getValue();
					
					  accrualnew=this.object.getCmpByName("ownBpFundProject.accrualnew").getValue();
					  managementConsultingOfRate=//this.object.getCmpByName("ownBpFundProject.managementConsultingOfRate").getValue();
					  financeServiceOfRate=this.object.getCmpByName("ownBpFundProject.financeServiceOfRate").getValue();
					  payintentPeriod=this.object.getCmpByName("ownBpFundProject.payintentPeriod").getValue();
					 
					  payintentPerioDate=new Date(intentDate).getDate();//this.object.getCmpByName("ownBpFundProject.payintentPerioDate").getValue();//update by gao
					  dayOfEveryPeriod=this.object.getCmpByName("ownBpFundProject.dayOfEveryPeriod").getValue();
					  isPreposePayAccrual=this.object.getCmpByName("isPreposePayAccrualCheck").getValue();
					  isInterestByOneTime=this.object.getCmpByName('ownBpFundProject.isInterestByOneTime').getValue();
					  dateMode=this.object.getCmpByName('ownBpFundProject.dateMode').getValue();
					  dayAccrualRate=this.object.getCmpByName('ownBpFundProject.dayAccrualRate').getValue();
					  dayManagementConsultingOfRate=this.object.getCmpByName('ownBpFundProject.dayManagementConsultingOfRate').getValue();
					  dayFinanceServiceOfRate=this.object.getCmpByName('ownBpFundProject.dayFinanceServiceOfRate').getValue();
					  investId=this.object.getCmpByName('ownBpFundProject.investorIds').getValue();
					  preceptId=this.object.getCmpByName("ownBpFundProject.id").getValue();
					  riskRate=this.object.getCmpByName("ownBpFundProject.riskRate").getValue();
					  balanceRate=0;
				}else{
					  accrualtype=this.object.getCmpByName("platFormBpFundProject.accrualtype").getValue();
					  accrual = this.object.getCmpByName("platFormBpFundProject.accrual").getValue();
					  payaccrualType=this.object.getCmpByName("platFormBpFundProject.payaccrualType").getValue();
					  isStartDatePay=this.object.getCmpByName("platFormBpFundProject.isStartDatePay").getValue();
					  if(this.object.getCmpByName("platFormBpFundProject.startDate")){
					  	startDate=this.object.getCmpByName("platFormBpFundProject.startDate").getValue();
					  }
					  yearAccrualRate = this.object.getCmpByName("platFormBpFundProject.yearAccrualRate").getValue();
					  payintentPerioDate=this.object.getCmpByName("platFormBpFundProject.payintentPerioDate").getValue();
					  accrualnew=this.object.getCmpByName("platFormBpFundProject.accrualnew").getValue();
					  managementConsultingOfRate=this.object.getCmpByName("platFormBpFundProject.managementConsultingOfRate").getValue();
					  financeServiceOfRate=this.object.getCmpByName("platFormBpFundProject.financeServiceOfRate").getValue();
					  payintentPeriod=this.object.getCmpByName("platFormBpFundProject.payintentPeriod").getValue();
					  dayOfEveryPeriod=this.object.getCmpByName("platFormBpFundProject.dayOfEveryPeriod").getValue();
					  isPreposePayAccrual=this.object.getCmpByName("isPreposePayAccrualCheck").getValue();
					  isInterestByOneTime=this.object.getCmpByName('platFormBpFundProject.isInterestByOneTime').getValue();
					  dateMode=this.object.getCmpByName('platFormBpFundProject.dateMode').getValue();
					  dayAccrualRate=this.object.getCmpByName('platFormBpFundProject.dayAccrualRate').getValue();
					  dayManagementConsultingOfRate=this.object.getCmpByName('platFormBpFundProject.dayManagementConsultingOfRate').getValue();
				//	  dayFinanceServiceOfRate=this.object.getCmpByName('platFormBpFundProject.dayFinanceServiceOfRate').getValue();
					  investId=this.object.getCmpByName('platFormBpFundProject.investorIds').getValue();
					  preceptId=this.object.getCmpByName("platFormBpFundProject.id").getValue();
					  balanceRate=this.object.getCmpByName("platFormBpFundProject.balanceRate").getValue()
				}
			}
			var calcutype = this.businessType; // 业务品种
			var projId = this.projectId;
			var isPreposePayAccrualCheck = this.object.getCmpByName("isPreposePayAccrualCheck").getValue();
			var message = "";
			if (accrualtype == "请选择" || accrualtype == null || accrualtype == "") {
				message = "计息方式";
			} else if (payaccrualType == "请选择" || payaccrualType == null) {
				message = "付息方式";
			} else if (managementConsultingOfRate === ""
					|| managementConsultingOfRate == null) {
				message = "管理咨询费率";
			} 
		/*	else if (financeServiceOfRate === "" || financeServiceOfRate == null) {
				message = "财务服务费率";
			}*/

			if (calcutype == "SmallLoan") {
				if (projectMoney == "" || projectMoney == null) {
					message = "对接金额";
				}
				if (startDate == "" || startDate == null) {
					message = "放款日期";
				}
				if (startInterestDate == "" || startInterestDate == null) {
					message = "起息日期";
				}
				if (payintentPeriod == "" || payintentPeriod == null) {
					message = "放款期限";
				}
				if (accrualnew == "" || accrualnew == null) {
					message = "贷款利率";
				}
				if (accrualtype == "ontTimeAccrual") {
					if (intentDate == "" || intentDate == null) {
						message = "还款日期";
					}
				} else {
					if (isStartDatePay == "1") {
						if (payintentPerioDate == "" || payintentPerioDate == null) {
							message = "固定在几号还款";
						}
					}
					/* 
					if (startDate == "" || startDate == null) {
						intentDate = startInterestDate;
					}else 
						intentDate = startDate;*/
					if (payaccrualType == "owerPay") {
						if (dayOfEveryPeriod == "" || dayOfEveryPeriod == null) {
							message = "自定义周期";
						}
					}
				}
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
			if (calcutype == "SmallLoan" && accrualtype == "ontTimeAccrual" /*&& intentDate <= startInterestDate*/) {
				if ((startDate == "" || startDate == null)&&(startInterestDate == "" || startInterestDate == null)) {
					if (startInterestDate == "" || startInterestDate == null) {
						message = "还款日期必须在起息日期之后"
					}else if (startDate == "" || startDate == null) {
						message = "还款日期必须在放款日期之后"
					}
					//message = "放款日期";
				}
				/*message = "还款日期必须在放款日期之后";*/
				Ext.MessageBox.show({
					title : '操作信息',
					msg : message,
					buttons : Ext.MessageBox.OK,
					icon : 'ext-mb-error'
				});
				return null;
			}
			var isPreposePayAccrual = 0;
			if (isPreposePayAccrualCheck == true) {
				var isPreposePayAccrual = 1;
			}
			var params1 =this.isOwnBpFundProject?{
				//projectId : projId,
			    'ownBpFundProject.accrualtype': accrualtype,
				'ownBpFundProject.isPreposePayAccrual': isPreposePayAccrual==false?0:1,
			    'ownBpFundProject.payaccrualType': payaccrualType,
			    'ownBpFundProject.projectMoney': projectMoney,
			    'ownBpFundProject.startDate': startDate,
			    'ownBpFundProject.intentDate': intentDate,
			    'ownBpFundProject.accrual':accrual,
			    'ownBpFundProject.startInterestDate': startInterestDate,
			    'ownBpFundProject.isStartDatePay': isStartDatePay,
			    'ownBpFundProject.payintentPerioDate': payintentPerioDate,
			    'ownBpFundProject.accrualnew': accrualnew,
			    'ownBpFundProject.operationType': operationType,
			    'ownBpFundProject.financeServiceOfRate': financeServiceOfRate,
			    'ownBpFundProject.managementConsultingOfRate': managementConsultingOfRate,
			    'ownBpFundProject.balanceRate': balanceRate ,
			    'ownBpFundProject.payintentPeriod': payintentPeriod,
			    'ownBpFundProject.dayOfEveryPeriod': dayOfEveryPeriod,
			    'ownBpFundProject.isPreposePayConsultingCheck':isPreposePayConsultingCheck,
			    'ownBpFundProject.isInterestByOneTime': isInterestByOneTime,
			    'ownBpFundProject.dateMode':dateMode,
			    'ownBpFundProject.dayAccrualRate':dayAccrualRate,
			    'ownBpFundProject.dayManagementConsultingOfRate':dayManagementConsultingOfRate,
			    'ownBpFundProject.dayFinanceServiceOfRate':dayFinanceServiceOfRate,
			    'ownBpFundProject.riskRate':riskRate,
			    
			    'calcutype':calcutype,
			    'flag1':0,
			    'fundResource':fundResource,
			    'projectId':projId,
			    'preceptId':preceptId,
			    'bidPlanId':this.bidPlanId,
			    'businessType':businessType,
			    'ownBpFundProject.investorIds':investId,
			    isHaveLending:"yes"
			}:{
				// : projId,
			    'platFormBpFundProject.accrualtype': accrualtype,
				'platFormBpFundProject.isPreposePayAccrual': isPreposePayAccrual==false?0:1,
			    'platFormBpFundProject.payaccrualType': payaccrualType,
			    'platFormBpFundProject.projectMoney': projectMoney,
			    "platFormBpFundProject.yearAccrualRate":yearAccrualRate,
			    'platFormBpFundProject.startInterestDate': startInterestDate,
			    'platFormBpFundProject.startDate': startDate,
			    'platFormBpFundProject.intentDate': intentDate,
			    'platFormBpFundProject.isStartDatePay': isStartDatePay,
			    'platFormBpFundProject.payintentPerioDate': payintentPerioDate,
			    'platFormBpFundProject.accrual':accrual,
			    'platFormBpFundProject.accrualnew': accrualnew,
			    'platFormBpFundProject.operationType': operationType,
			    'platFormBpFundProject.financeServiceOfRate': financeServiceOfRate,
			    'platFormBpFundProject.managementConsultingOfRate': managementConsultingOfRate,
			    'platFormBpFundProject.balanceRate': balanceRate ,
			    'platFormBpFundProject.payintentPeriod': payintentPeriod,
			    'platFormBpFundProject.dayOfEveryPeriod': dayOfEveryPeriod,
			    'platFormBpFundProject.isPreposePayConsultingCheck':isPreposePayConsultingCheck,
			    'platFormBpFundProject.isInterestByOneTime': isInterestByOneTime,
			    'platFormBpFundProject.dateMode':dateMode,
			    'platFormBpFundProject.dayAccrualRate':dayAccrualRate,
			    'platFormBpFundProject.dayManagementConsultingOfRate':dayManagementConsultingOfRate,
			    'platFormBpFundProject.dayFinanceServiceOfRate':dayFinanceServiceOfRate,
			    'calcutype':calcutype,
			    'flag1':0,
			    'fundResource':fundResource,
			    'projectId':projId,
			    'preceptId':preceptId,
			    'bidPlanId':this.bidPlanId,
			    'orderNo':this.orderNo,
			    'businessType':businessType,
			    'platFormBpFundProject.investorIds':investId,
			    isHaveLending:"yes"
			}
			var combox=new Ext.form.ComboBox({
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
			// 
			/*gridPanel1.getStore().proxy = new Ext.data.HttpProxy( {
				url : __ctxPath + "/creditFlow/finance/listSlFundIntent.do"
			});*/
			gridPanel1.getStore().reload();
		 var custerGridPanel=	this.custerFundIntentView.gridPanel;
			var custerGridPanelstore = custerGridPanel.getStore();
			custerGridPanelstore.on('beforeload', function(gridstore, o) {
				Ext.apply(o.params, params1);
			});
			custerGridPanelstore.reload();

			var vRecords = this.gridPanel.getStore().getRange(0,this.gridPanel.getStore().getCount()); // 得到修改的数据（记录对象）
			var vCount = vRecords.length; // 得到记录长度
			var vDatas = '';
			if (vCount > 0) {
				for (var i = 0; i < vCount; i++) {
					if (vRecords[i].data.afterMoney != null&& vRecords[i].data.afterMoney != 0) {
						Ext.Msg.alert('', '有款项已对过账不能被覆盖');
					}
				}
			}
		}else{
			Ext.Msg.alert('友好提示','请先选择投资人信息才能生成放款收息表')
		}
	},
//autocreate : function() {},
	getGridDate : function() {
		var vRecords = this.gridPanel.getStore().getRange(0,
				this.gridPanel.getStore().getCount()); // 得到修改的数据（记录对象）
		var vCount = vRecords.length; // 得到记录长度
		var vDatas = '';
		if (vCount > 0) {
			// begin 将记录对象转换为字符串（json格式的字符串）
			for (var i = 0; i < vCount; i++) {

				if (vRecords[i].data.fundType != null&& vRecords[i].data.fundType != "") {
						var st = "";
						st = {
//							"fundIntentId" : vRecords[i].data.fundIntentId, // add by zcb 2014-01-08
							"fundType" : vRecords[i].data.fundType,
							"fundTypeName" : vRecords[i].data.fundTypeName,
							"incomeMoney" : vRecords[i].data.incomeMoney,
							"payMoney" : vRecords[i].data.payMoney,
							"intentDate" : vRecords[i].data.intentDate,
							"remark" : vRecords[i].data.remark,
							"investPersonId" : vRecords[i].data.investPersonId,
							"orderNo" : vRecords[i].data.orderNo,
							"investPersonName" : vRecords[i].data.investPersonName,
							"interestStarTime" : vRecords[i].data.interestStarTime,
							"interestEndTime" : vRecords[i].data.interestEndTime,
							'payintentPeriod':vRecords[i].data.payintentPeriod
							
						};
						vDatas += Ext.util.JSON.encode(st) + '@';
					
				}
			}

			vDatas = vDatas.substr(0, vDatas.length - 1);
		}
		return vDatas;
	},

	// 保存数据
	saveRs : function() {
		var data = this.getGridDate();
		Ext.Ajax.request({
			url : __ctxPath + '/creditFlow/finance/saveBpFundIntent.do',
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
		var preceptId=this.preceptId
		var bidPlanId=this.bidPlanId
		gridstore.on('beforeload', function(gridstore, o) {
			var new_params = {
				projectId : projectId,
				businessType : businessType,
				preceptId:preceptId,
				bidPlanId:bidPlanId,
				flag1:1
			};
			Ext.apply(o.params, new_params);
		});

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
		//var fill1 = this.fillDatas1;
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
					//	fill1(inforpanel, gridPanel, businessType);
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
						gridPanel.getView().refresh()
					} else {
						deleteFun(__ctxPath
								+ '/creditFlow/finance/deleteBpFundIntent.do',
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
								+ this.infoObj.slProjectMoney
								+ "元&nbsp;&nbsp;未还贷款本金："
								+ this.infoObj.slProjectMoneyBalance
								+ "元&nbsp;&nbsp;应收费用总额："
								+ this.infoObj.slProjectIncomeintentsum
								+ "元&nbsp;&nbsp;未收费用总额："
								+ this.infoObj.slProjectNotincomeintentsum
								+ "元</span><font class='x-myZW-fieldset-title'></font><br/><font class='x-myZW-fieldset-title'></font><span><font color=red>逾期提示</font>：逾期未还款项共计<font color=red>"
								+ this.infoObj.slProjectoverduecount
								+ "</font>笔 &nbsp;&nbsp;逾期未还款项总额<font color=red>"
								+ this.infoObj.slProjectoverdueintent
								+ "</font>元 &nbsp;&nbsp;逾期违约金总额<font color=red>"
								+ this.infoObj.slProjectoverduebreakMoneysum
								+ "</font>元</span><font class='x-myZW-fieldset-title'></font>"
					});
				} else if (businessType == 'Financing') {
					inforpanel.add({
						xtype : 'label',
						html : "<span style='line-height:2.2'>总计提示: <font class='x-myZW-fieldset-title'></font>借款本金总额："
								+ this.infoObj.flProjectMoney
								+ "元&nbsp;&nbsp;未还借款本金："
								+ this.infoObj.flProjectMoneyBalance
								+ "&nbsp;&nbsp;应付费用总额："
								+ this.infoObj.flProjectChargeMoney
								+ "元&nbsp;&nbsp;未付费用总额："
								+ this.infoObj.flProjectChargeMoneyBalance
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
				intent:flag
			};
			Ext.apply(o.params, new_params);
		});
		this.gridPanel.getStore().reload();
	}

});
