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
InterestFundIntentView = Ext.extend(Ext.Panel, {
	name : "InterestFundIntentView",
	infoObj : null,
	// 构造函数
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (_cfg.isHiddenMoney != "undefined") {
			this.isHiddenMoney = _cfg.isHiddenMoney;
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
				html : '<br/><B><font class="x-myZW-fieldset-title">【投资人奖励明细表】</font></B>'

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
								iconCls : 'btn-add',
								text : '生成',
								xtype : 'button',
								scope : this,
								handler : this.autocreate
							}]
		
		})
		var field = Ext.data.Record.create([{
					name : 'payintentPeriod'
				}, {
					name : 'fundType'
				}, {
					name : 'investPersonName'
				}, {
					name : 'incomeMoney'
				}, {
					name : 'intentDate'
				}, {
					name : 'sumMoney'
				}, {
					name : 'factDate'
				}]);
		var url = __ctxPath
				+ "/creditFlow/finance/listInterestBpFundIntent.do?bidPlanId="
				+ this.bidPlanId;
		var jStore = new Ext.data.JsonStore({
					url : url,
					root : 'result',
					fields : field
				});
		jStore.load({
					params : {
		// bidPlanId : this.bidPlanId
					}
				});
		this.projectFundsm = new Ext.grid.CheckboxSelectionModel({
					header : '序号'
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
		this.gridPanel = new HT.GridPanel({
			border : false,
			name : 'gridPanel',
			scope : this,
			store : jStore,
			tbar :  this.isHiddenDelBtn == true ? this.topbar : null,
			autoScroll : true,
			autoWidth : true,
			layout : 'anchor',
			clicksToEdit : 1,
			viewConfig : {
				forceFit : true
			},
			bbar : false,
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
					}, {
						header : '期数',
						dataIndex : 'payintentPeriod',
						summaryType : 'count',
						summaryRenderer : totalMoney,
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store) {
							if (null != value) {
								if (record.data.isValid == 1) {
									return '<font style="font-style:italic;text-decoration: line-through;color:gray">'
											+ '第' + value + '期' + "</font>"
								} else {
									return '第' + value + '期';
								}
							}
						}
					},{
						header : '投资方',
						align : 'center',
						dataIndex : 'investPersonName'
					}, {
						header : '费用类型',
						align : 'center',
						dataIndex : 'fundType',
						renderer : function(val){
							if(val=="couponInterest"){
								return "奖励利息";
							}else if(val=="subjoinInterest"){
								return "奖励利息";
							}else if(val=="principalCoupons"){
								return "奖励本金";
							}else if(val=="commoninterest"){
								return "奖励利息";
							}else if(val=="raiseinterest"){
								return "募集期奖励";
							}
						}			
					},{
						header : '金额',
						dataIndex : 'incomeMoney',
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
							if (value != '') {
								return Ext.util.Format.number(value,
										',000,000,000.00')
										+ "元"
							} else {
								return '';
							}
						}
					},{

						header : '计划到帐日',
						dataIndex : 'intentDate',
						format : 'Y-m-d'
					}],
			listeners : {
				scope : this
			}
		});

	},// end of the initComponents()
	
	autocreate : function() {
		var fundResource=null;
		var	investPersonName=null;
		var startInterestDate=null;
		var projectMoney=null;
		var intentDate=null;
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
			    isHaveLending:"yes",
			    'createType':'couponInterest'
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
				Ext.ux.Toast.msg('操作信息', obj.msg);
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
		}else{
			Ext.Msg.alert('友好提示','请先选择投资人信息才能生成放款收息表')
		}
		
	
		
		
	}
});
