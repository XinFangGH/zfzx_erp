
var slprojectMoney = 0;
var slnotprojectMoney = 0;
PlMmOrderAssigninterestVM = Ext.extend(Ext.Panel, {
	name : "PlMmOrderAssigninterestVM",
	infoObj : null,
	isHiddenAddBtn : true,
	isHiddenDelBtn : true,
	isHiddenautocreateBtn : true,
	// 构造函数
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (typeof(_cfg.isHiddenAddBtn) != "undefined") {
			this.isHiddenAddBtn = _cfg.isHiddenAddBtn;
		}
		if (typeof(_cfg.isHiddenDelBtn) != "undefined") {
			this.isHiddenDelBtn = _cfg.isHiddenDelBtn;
		}
		if (typeof(_cfg.isHiddenautocreateBtn) != "undefined") {
			this.isHiddenautocreateBtn = _cfg.isHiddenautocreateBtn;
		}

		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		PlMmOrderAssigninterestVM.superclass.constructor.call(this, {
			name : "PlMmOrderAssigninterestVM",
			hidden : this.isHiddenPanel,
			region : 'center',
			layout : 'anchor',
			anchor : '100%',
			items : [this.gridPanel]
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
							},new Ext.Toolbar.Separator({
										hidden : this.isHiddenAddBtn
							}), {
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
							}]
				});
		var field = [{
						name : 'assignInterestId',
						type : 'int'
					}, 
					'orderId',
					'investPersonId',
					'investPersonName',
					'mmplanId',
					'mmName', 
					'fundType', 
					'incomeMoney',
					'afterMoney',
					'factDate',
					'payMoney',
					'intentDate',
					'periods',
					'keystr',
					'isCheck',
					'isValid'
					]	
				
	    var url = __ctxPath + "/creditFlow/financingAgency/createFundListPlMmOrderInfo.do?plMmOrderInfoId="+this.projectId;
		var isThisSuperviseRecord = this.isThisSuperviseRecord;
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
						header : 'assignInterestId',
						dataIndex : 'assignInterestId',
						hidden : true
					}/*, {
						header : 'orderId',
						dataIndex : 'orderId'
					}, {
						header : 'investPersonId',
						dataIndex : 'investPersonId'
					}, {
						header : 'investPersonName',
						dataIndex : 'investPersonName'
					}, {
						header : 'mmplanId',
						dataIndex : 'mmplanId'
					}, {
						header : 'mmName',
						dataIndex : 'mmName'
					}*/,
					{
						header : 'afterMoney',
						dataIndex : 'afterMoney',
						hidden : true
					},{
						header : '期数',
						dataIndex : 'periods',
						
						renderer:function(v){
							if(v==null){return "";}
							else{
							return "第"+v+"期"
							}
						}
					}, {
						header : '资金类型',
						dataIndex : 'fundType',
						editor : {
							xtype : 'combo',
							mode : 'local',
							displayField : 'name',
							valueField : 'id',
							editable : false,
							triggerAction : 'all',
							width : 70,
							store : new Ext.data.SimpleStore({
								fields : ["name", "id"],
								data : [["利息", "loanInterest"],["本金", "principalRepayment"]]
							})
						},
						renderer:function(v){
							if(v=="loanInterest"){
							   return "利息"
							}else if(v=="riskRate"){
							  return "风险金"
							}else if(v=="liquidatedDamages"){
							  return "提前赎回违约金"
							}else if(v=="principalRepayment") {
							  return "本金";
							}else {
							  return "";
							}
						}
					}, {
						header : '收入金额',
						dataIndex : 'incomeMoney',
						summaryType : 'sum',
						align : 'right',
						editor : {
							xtype : 'numberfield'
						},
						renderer:function(v){
							return Ext.util.Format.number(v,',000,000,000.00')+"元";
						}
					}, {
						header : '支出金额',
						dataIndex : 'payMoney',
						summaryType : 'sum',
						editor : {
							xtype : 'numberfield'
						},
						align : 'right',
						renderer:function(v){
							return Ext.util.Format.number(v,',000,000,000.00')+"元";
						}
					}, {
						header : '计划派息日期',
						dataIndex : 'intentDate',
						format : 'Y-m-d',
						editor : {
							xtype : 'datefield',
							format : 'Y-m-d'
						},
						renderer : function(value, metaData, record, rowIndex,colIndex, store) {
							if(value!=null&&value!="undefined"){
								return Ext.util.Format.date(value, 'Y-m-d');
							}
							return "";
						}
					}],
			listeners : {
				scope : this
			}
		})

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
		this.gridPanel.getStore().insert(this.gridPanel.getStore().getCount(),newData);
		this.gridPanel.getView().refresh();
		this.gridPanel.getSelectionModel().selectRow((this.gridPanel.getStore().getCount() - 1));
		this.gridPanel.startEditing(0, 0);
	},

	getGridDate : function() {
		 
		var vRecords = this.gridPanel.getStore().getRange(0,this.gridPanel.getStore().getCount()); // 得到修改的数据（记录对象）
		var vCount = vRecords.length; // 得到记录长度
		var vDatas = '';
		if (vCount > 0) {
			var st = "";
			for (var i = 0; i < vCount; i++) {
					st = {
						"afterMoney" : vRecords[i].data.afterMoney,
					//	"assignInterestId" : vRecords[i].data.assignInterestId,
						"factDate" : vRecords[i].data.factDate,
						"fundType" : vRecords[i].data.fundType,
						"incomeMoney" : vRecords[i].data.incomeMoney,
						"intentDate" : vRecords[i].data.intentDate,
						"investPersonId" : vRecords[i].data.investPersonId,
						"investPersonName": vRecords[i].data.investPersonName,
						"isCheck": vRecords[i].data.isCheck,
						"isValid": vRecords[i].data.isValid,
						"keystr": vRecords[i].data.keystr,
						"mmName": vRecords[i].data.mmName,
						"mmplanId": vRecords[i].data.mmplanId,
						"orderId": vRecords[i].data.orderId,
						"payMoney": vRecords[i].data.payMoney,
						"periods": vRecords[i].data.periods
					};
					vDatas += Ext.util.JSON.encode(st) + '@';
			}
			vDatas = vDatas.substr(0, vDatas.length - 1);
		}
		
		return vDatas;
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
	onRowAction : function(grid, record, action, row, col) {
		this.fundIntentWaterReconciliationInfo.call(this, record, 1);
	},
	autocreate : function() {
		var plMmOrderInfoId= this.projectId;
		var startinInterestTime = this.plMmPlanBuyPanel.getCmpByName("plMmOrderInfo.startinInterestTime").getValue();
		var endinInterestTime = this.plMmPlanBuyPanel.getCmpByName("plMmOrderInfo.endinInterestTime").getValue();
		var gridstore = this.gridPanel.getStore();
		gridstore.setBaseParam("plMmOrderInfoId",plMmOrderInfoId);
		gridstore.setBaseParam("plMmOrderInfo.startinInterestTime",startinInterestTime); 
		gridstore.setBaseParam("plMmOrderInfo.endinInterestTime",endinInterestTime);
		gridstore.setBaseParam("flag","create");
		this.gridPanel.getStore().load();
			
	}
});
