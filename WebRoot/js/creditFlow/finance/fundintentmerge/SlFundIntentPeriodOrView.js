//SlFundIntentPeriodOrView.js

SlFundIntentPeriodOrView = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				if(typeof(_cfg.businessType)!="undefined")
				{
				      this.businessType=_cfg.businessType;
				}
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				SlFundIntentPeriodOrView.superclass.constructor.call(this, {
							id : 'SlFundIntentPeriodOrView',
							title : '债权标还款管理',
							region : 'center',
							layout : 'border',
							iconCls :'btn-team29',
							items : [this.searchPanel, this.gridPanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				// 初始化搜索条件Panel
				var tabflag=this.tabflag;
               var labelsize=100;
				 var labelsize1=115;
				 	var isShow=false;
				if(RoleType=="control"){
				  isShow=true;
				}
				this.searchPanel = new HT.SearchPanel({
							layout : 'column',
							style : 'padding-left:5px;padding-right:5px;padding-top:5px;',
							region : 'north',
							height : 40,
							anchor : '96%',
							keys : [{
								key : Ext.EventObject.ENTER,
								fn : this.search,
								scope : this
							}, {
								key : Ext.EventObject.ESC,
								fn : this.reset,
								scope : this
							}],
							layoutConfig: {
				               align:'middle',
				               padding : '5px'
				               
				            },
				 //            bodyStyle : 'padding:10px 10px 10px 10px',
							items : [ {   columnWidth : 0.2,
								layout : 'form',
								border : false,
								labelWidth : labelsize,
								labelAlign : 'right',
								items : [{
										fieldLabel : '招标项目名称',
										name : 'bidPlanName',
										flex : 1,
										editable : false,
										width:105,
										xtype :'textfield',
										anchor : '100%'},{
										fieldLabel : '招标项目编号',
										name : 'bidPlanProjectNum',
										flex : 1,
										editable : false,
										width:105,
										xtype :'textfield',
										anchor : '100%'
										
								}]}
							, {   columnWidth : 0.2,
								layout : 'form',
								border : false,
								labelWidth : labelsize,
								labelAlign : 'right',
								items : [{
										fieldLabel : '计划到账日：从',
										name : 'intentDatel',
										flex : 1,
										width:105,
										xtype :'datefield',
										format:"Y-m-d",
										anchor : '100%'
										
								},{
										fieldLabel : '实际到账日：从',
										name : 'factDatel',
										flex : 1,
										width:105,
										xtype :'datefield',
										format:"Y-m-d",
										anchor : '100%'
										
								}]}, {   columnWidth : 0.17,
								layout : 'form',
								border : false,
								labelWidth : labelsize-55,
								labelAlign : 'right',
								items : [{
										fieldLabel : '到',
										name : 'intentDateg',
										flex : 1,
										width:105,
										xtype :'datefield',
										format:"Y-m-d",
										anchor : '100%'
										
								},{
										fieldLabel : '到',
										name : 'factDateg',
										flex : 1,
										width:105,
										xtype :'datefield',
										format:"Y-m-d",
										anchor : '100%'
										
								}]}, {   columnWidth : 0.2,
								layout : 'form',
								border : false,
								hidden :true,
								labelWidth : labelsize,
								labelAlign : 'right',
								items : [{
										fieldLabel : '未还款，已还款',
										name : 'isPay',
										id: 'isPay',
										value:"notPay",
										flex : 1,
										editable : false,
										width:105,
										xtype :'textfield',
										anchor : '100%'
										
								}]}
							,{   columnWidth : 0.2,
								layout : 'form',
								border : false,
								labelWidth : labelsize,
								labelAlign : 'right',
								items : [{
									
										fieldLabel : '借款项目名称',
										name : 'projectName',
										flex : 1,
										editable : false,
										width:105,
										xtype :'textfield',
										anchor : '100%'
										
								
										
								}]}
							, {
								columnWidth : .07,
								xtype : 'container',
								layout : 'form',
								defaults : {
									xtype : 'button'
								},
								style : 'padding-left:10px;',
								items : [{
									text : '查询',
									scope : this,
									iconCls : 'btn-search',
									handler : this.search
								}]}
								, {
								columnWidth : .07,
								xtype : 'container',
								layout : 'form',
								defaults : {
									xtype : 'button'
								},
								style : 'padding-left:10px;',
								items : [{
									text : '重置',
									scope : this,
									iconCls : 'btn-reset',
									handler : this.reset
								}]}	

							]

						});// end of searchPanel

				this.topbar = new Ext.Toolbar({
					items : [{
						iconCls : 'btn-setting',
						text :'还款',
						xtype : 'button',
						hidden:false,
						scope : this,
						//hidden : isGranted('_previewpz_f_principalIncome_'+this.businessType)?false:true,
						handler : this.borrowerToInvestor
					}, '-',/*{
						iconCls : 'btn-setting',
						text :'保证金垫付',
						xtype : 'button',
						scope : this,
						//hidden : isGranted('_previewpz_f_principalIncome_'+this.businessType)?false:true,
						handler : this.guranteeMoney
					}, '-',*/ {
						iconCls : 'btn-detail',
						text : '查看还款明细',
						xtype : 'button',
						scope : this,
						handler : this.see
					}, "->", {
								xtype : 'checkbox',
								boxLabel : '未还款',
								inputValue : true,
								name : "notPay",
								checked : true,
								scope : this,
								handler : function() {
									var notPay = this.topbar
											.getCmpByName("notPay");
									var payed = this.topbar
											.getCmpByName("payed");
									if (notPay.getValue() == true
											&& payed.getValue() == true) {
										Ext.getCmp("isPay").setValue("all");
									}
									if (notPay.getValue() == false
											&& payed.getValue() == true) {
										Ext.getCmp("isPay").setValue("payed");
									}
									if (notPay.getValue() == true
											&& payed.getValue() == false) {
										Ext.getCmp("isPay").setValue("notPay");
									}
									if (notPay.getValue() == false
											&& payed.getValue() == false) {
									    Ext.getCmp("isPay").setValue("none");
									}
									 this.search();
								}
							}, '-', {
								xtype : 'checkbox',
								name : "payed",
								boxLabel : '已还款',
								inputValue : true,
								scope : this,
								checked : false,
								handler : function() {
									var notPay = this.topbar
											.getCmpByName("notPay");
									var payed = this.topbar
											.getCmpByName("payed");
									if (notPay.getValue() == true
											&& payed.getValue() == true) {
										Ext.getCmp("isPay").setValue("all");
									}
									if (notPay.getValue() == false
											&& payed.getValue() == true) {
										Ext.getCmp("isPay").setValue("payed");
									}
									if (notPay.getValue() == true
											&& payed.getValue() == false) {
										Ext.getCmp("isPay").setValue("notPay");
									}
									if (notPay.getValue() == false
											&& payed.getValue() == false) {
									    Ext.getCmp("isPay").setValue("none");
									}
									 this.search();
								}
							}
					]
				});

				var summary = new Ext.ux.grid.GridSummary();
				function totalMoney(v, params, data) {
					return '总计';
				}
				this.gridPanel = new HT.GridPanel({
					bodyStyle : "width : 100%",
					region : 'center',
					tbar : this.topbar,
					plugins : [summary],
					viewConfig: {  
		            	forceFit:false  
		            },
					// 使用RowActions
					rowActions : false,
					id : 'SlFundIntentPeriodOrGird',
					isautoLoad:true,
					url : __ctxPath + "/creditFlow/finance/listPeriodbyLedgerSlFundIntent.do?bidPlanType=OR",
					fields : ['intentDate',
							'principalRepayment.afterMoney','principalRepayment.incomeMoney','principalRepayment.accrualMoney',
							'loanInterest.afterMoney','loanInterest.incomeMoney','loanInterest.accrualMoney',					
							'consultationMoney.afterMoney','consultationMoney.incomeMoney','consultationMoney.accrualMoney',							
							'serviceMoney.afterMoney','serviceMoney.incomeMoney','serviceMoney.accrualMoney',
						    'interestPenalty.afterMoney','interestPenalty.incomeMoney','interestPenalty.accrualMoney',
							 'factDate','accrualMoney', 'bidPlanName','bidPlanProjectNum'
							,'ptpborrowId','borrowName','punishDays','repaySource','planId','authorizationStatus','receiverName','receiverP2PId','receiverP2PName',
							'payintentPeriod','ids'],
					columns : [{
								header : 'planId',
								dataIndex : 'planId',
								hidden : true
							}, {
								header : '借款人',
								dataIndex : 'borrowName',
								width : 100
							}, {
								header : '债权拥有人',
								dataIndex : 'receiverName',
								summaryType : 'count',
								summaryRenderer : totalMoney,
								width : 100
							}, {
								header : '招标项目名称',
								dataIndex : 'bidPlanName',
								width : 120
							}, {
								header : '招标项目编号',
								dataIndex : 'bidPlanProjectNum',
							
								width : 120
							}, {
								header : '期数',
								align :'center',
								dataIndex : 'payintentPeriod',
								align : 'center',
								width : 100,
								renderer:function(v){
								  return "第"+v+"期";
								}
							
							}, {
								header : "本金",
								dataIndex : 'principalRepayment.incomeMoney',
								align :'right',
								summaryType : 'sum',
								width:110,
								renderer:function(v){
									return Ext.util.Format.number(v,',000,000,000.00')+"元"
                         	     }
							
							} ,{
								header : "利息",
								dataIndex : 'loanInterest.incomeMoney',
								align :'right',
								summaryType : 'sum',
								width:110,
								renderer:function(v){
									return Ext.util.Format.number(v,',000,000,000.00')+"元"
                         	     }
							
							} ,{
								header : "管理咨询费",
								dataIndex : 'consultationMoney.incomeMoney',
								summaryType : 'sum',
								align :'right',
								width:110,
								renderer:function(v){
									return Ext.util.Format.number(v,',000,000,000.00')+"元"
                         	     }
							
							} ,{
								header : "财务服务费",
								dataIndex : 'serviceMoney.incomeMoney',
								summaryType : 'sum',
								align :'right',
								width:110,
								renderer:function(v){
									return Ext.util.Format.number(v,',000,000,000.00')+"元"
                         	     }
							
							},{
								header : "补偿息",
								dataIndex : 'interestPenalty.incomeMoney',
								summaryType : 'sum',
								align :'right',
								width:110,
								renderer:function(v){
									return Ext.util.Format.number(v,',000,000,000.00')+"元"
                         	     }
							
							}, {
								header : "逾期天数",
								dataIndex : 'punishDays',
								summaryType : 'sum',
								align :'center',
								width:80,
								renderer:function(v){
									return Ext.util.Format.number(v,',000,000,000')+"天";
                         	     }
							
							}, {
								header : "罚息",
								dataIndex : 'loanInterest.incomeMoney',
								align :'right',
								width:80,
								renderer:function(v,a,r){
								return Ext.util.Format.number((r.get("principalRepayment.accrualMoney")+
									r.get("loanInterest.accrualMoney")+r.get("consultationMoney.accrualMoney")+r.get("serviceMoney.accrualMoney")),',000,000,000.00')+"元"
                         	     }

							
							}, {
								header : "合计",
								dataIndex : 'loanInterest.incomeMoney',
								align :'right',
								width:110,
								renderer:function(v,a,r){
									return Ext.util.Format.number((r.get("principalRepayment.incomeMoney")+
									r.get("loanInterest.incomeMoney")+r.get("consultationMoney.incomeMoney")+r.get("serviceMoney.incomeMoney")+r.get("interestPenalty.incomeMoney")+r.get("principalRepayment.accrualMoney")+
									r.get("loanInterest.accrualMoney")+r.get("consultationMoney.accrualMoney")+r.get("serviceMoney.accrualMoney")),',000,000,000.00')+"元"
                         	     }
							
							}, {
								header : '计划还款日',
								dataIndex : 'intentDate',
								width:80,
								align : 'center'
							} ,{
								header : "实际到账金额",
								dataIndex : 'loanInterest.incomeMoney',
								align :'right',
								width:110,
								renderer:function(v,a,r){
								return Ext.util.Format.number((r.get("principalRepayment.afterMoney")+
									r.get("loanInterest.afterMoney")+r.get("consultationMoney.afterMoney")+r.get("serviceMoney.afterMoney")+r.get("interestPenalty.afterMoney")),',000,000,000.00')+"元"
                         	     }
							
							}, {
								header : '实际到账日期',
								width : 100,
								dataIndex : 'factDate',
								align : 'center'
						//		sortable:true
							}, {
								header : '还款方式',
								width : 100,
								dataIndex : 'repaySource',
								align : 'center',
								renderer:function(v){
								if(parseInt(v)==1){
								 return "正常还款"
								}else if(parseInt(v)==2){
								  return "平台代偿"
								}
								return "";
								  
								}
						//		sortable:true
							}/*, {
								header : '还款授权',
								width : 100,
								dataIndex : 'ids',
								align : 'center'
								renderer:function(v){
								return "";
								}
						//		sortable:true
							}*/
						]

						// end of columns
					});


			},// end of the initComponents()
			// 重置查询表单
//			rowClick : function(grid, rowindex, e) {
//				grid.getSelectionModel().each(function(rec) {
//							new editAfterMoneyForm({
//								fundIntentId : rec.data.fundIntentId,
//								afterMoney : rec.data.afterMoney,
//								notMoney : rec.data.notMoney,
//								flatMoney : rec.data.flatMoney
//									}).show();
//						});
//				
//			},
			reset : function() {
				this.searchPanel.getForm().reset();
				var obj = Ext.getCmp('Q_fundType_N_EQ'+tabflag);
			//	obj.setEditable(false);
				var arrStore= new Ext.data.SimpleStore({});
				obj.clearValue();
                obj.store = arrStore;
			    arrStore.load({"callback":test});
			    function test(r){
			    	if (obj.view) { // 刷新视图,避免视图值与实际值不相符
			    		obj.view.setStore(arrStore);
			        }
								       
								    }
			},
			// 按条件搜索
			search : function() {
				$search({
							searchPanel : this.searchPanel,
							gridPanel : this.gridPanel
						});
			},
	related : function(flag) {
			var gridstore = this.gridPanel.getStore();
			var o = gridstore.params;
			var projectId = this.projectId;
			var businessType = this.businessType
			gridstore.on('beforeload', function(gridstore, o) {
						var new_params = {
							relateIntentOrCharge : flag
						};
						Ext.apply(o.params, new_params);
					});
			this.gridPanel.getStore().reload();
		},
			
			//平台垫付
	   planformToInvestor:function(record){
				var s = this.gridPanel.getSelectionModel().getSelections();
				if (s <= 0) {
					Ext.ux.Toast.msg('操作信息','请选中一条记录');
					return false;
				}else if(s.length>1){
					Ext.ux.Toast.msg('操作信息','只能选中一条记录');
					return false;
				}else{	
					var	record=s[0];
					var fundIntentId=record.data.fundIntentId;
			var url =__ctxPath
					+ "/creditFlow/finance/planformToInvestorSlFundIntent.do?fundIntentId="+fundIntentId ;
					
					window.open(
							url,
							'放款审核',
							'top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no',
							'_blank');
		
				}
	
			}

		
	,
	guranteeMoney:function(){
	 //弹出还款计划
		var s = this.gridPanel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息', '请选中一条记录');
			return false;
		} else if (s.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
			return false;
		} else {
			var record = s[0];
			if((record.get('principalRepayment.afterMoney')+record.get('loanInterest.afterMoney')+record.get('serviceMoney.afterMoney')+record.get('consultationMoney.afterMoney')+record.get('interestPenalty.afterMoney'))==(record.get('principalRepayment.incomeMoney')+record.get('loanInterest.incomeMoney')+record.get('serviceMoney.incomeMoney')+record.get('consultationMoney.incomeMoney')+record.get('interestPenalty.incomeMoney'))){
				  Ext.ux.Toast.msg('操作信息', '借款人当期应还金额已经全部还清，不需要进行保证金垫付');
				  return ;
				}
			var planId = record.data.planId;
			var intentDate=record.data.intentDate;
			var ids=record.data.ids;
			var ptpborrowId=record.data.ptpborrowId;
			Ext.Ajax.request({
							url: __ctxPath + '/pay/reservePay.do',
							mothed:'POST',
							waitMsg : '数据正在提交，请稍后...',
							success:function(response){
								var res = Ext.util.JSON.decode(response.responseText);
								Ext.ux.Toast.msg('操作信息', res.msg);
							},
							failure:function(response){
							},
							params:{
								ids:ids,
								planId:planId,
								intentDate:intentDate,
								ptpborrowId:ptpborrowId
							}
						});
			
			
		}
	
	},
	//平台帮助借款人直接还款
	borrowerToInvestor:function(){
	//弹出还款计划
		var s = this.gridPanel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息', '请选中一条记录');
			return false;
		} else if (s.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
			return false;
		} else {
			var record = s[0];
			if((record.get('principalRepayment.afterMoney')+record.get('loanInterest.afterMoney')+record.get('serviceMoney.afterMoney')+record.get('consultationMoney.afterMoney')+record.get('interestPenalty.afterMoney'))==(record.get('principalRepayment.incomeMoney')+record.get('loanInterest.incomeMoney')+record.get('serviceMoney.incomeMoney')+record.get('consultationMoney.incomeMoney')+record.get('interestPenalty.incomeMoney'))){
				  Ext.ux.Toast.msg('操作信息', '借款人当期应还金额已经全部还清，不需要进行还款');
				  return ;
				}
			/*if(record.data.authorizationStatus!=null&&eval(record.data.authorizationStatus)==eval(1)){*/
				
				var planId = record.data.planId;
				var intentDate=record.data.intentDate;
				var ids=record.data.ids;
				var ptpborrowId=record.data.ptpborrowId;
				Ext.Ajax.request({
								url: __ctxPath + '/pay/platformHelpLoanerRepaymentPay.do',
								mothed:'POST',
								waitMsg : '数据正在提交，请稍后...',
								success:function(response){
									var res = Ext.util.JSON.decode(response.responseText);
									Ext.ux.Toast.msg('操作信息', res.msg);
								},
								failure:function(response){
								},
								params:{
									planId:planId,
									intentDate:intentDate
									
									
								}
							});
			/*}else{
			  Ext.ux.Toast.msg('操作信息', '借款人没有授权该标进行自动还款');
			  return ;
			}*/
		}
	
	},
	see : function() {
			var s = this.gridPanel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息', '请选中一条记录');
			return false;
		} else if (s.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
			return false;
		} else {
			var record = s[0];
			new BpFundIntentPeriodView({
			  payintentPeriod: record.data.payintentPeriod,
			  intentDate:record.data.intentDate,
			  planId:record.data.planId
			}).show();
		}
			
	}
});