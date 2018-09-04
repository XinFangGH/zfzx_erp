//NewPlateFormFianceBidIncomeDetailView.js    新的平台随息收费清单
NewPlateFormFianceBidIncomeDetailView = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				NewPlateFormFianceBidIncomeDetailView.superclass.constructor.call(this, {
							id : 'NewPlateFormFianceBidIncomeDetailView',
							title : '平台随息收费台账',
							region : 'center',
							layout : 'border',
							iconCls:"btn-tree-team39",
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
					items : [ {
						iconCls : 'btn-detail',
						text : '查看随息明细',
						xtype : 'button',
						scope : this,
						handler : this.see
					},{
						text : '导出到Excel',
						iconCls : 'btn-xls',
						scope : this,
						hidden : isGranted('_export_grkh') ? false : true,
						handler : function() {
							var bidPlanName=this.getCmpByName("bidPlanName").getValue();//招标项目名称
							var bidPlanProjectNum=this.getCmpByName("bidPlanProjectNum").getValue();//招标项目编号
							var intentDatel=this.getCmpByName("intentDatel").getValue();//计划到账日开始
							if(intentDatel){
								intentDatel=intentDatel.format('Y-m-d');
							}
							var intentDateg=this.getCmpByName("intentDateg").getValue();//计划到账日结束
							if(intentDateg){
								intentDateg=intentDateg.format('Y-m-d');
							}
							var factDatel=this.getCmpByName("factDatel").getValue();//实际到账日开始
							if(factDatel){
								factDatel=factDatel.format('Y-m-d');
							}
							var factDateg=this.getCmpByName("factDateg").getValue();//实际到账日结束
							if(factDateg){
								factDateg=factDateg.format('Y-m-d');
							}
							var projectName=this.getCmpByName("projectName").getValue();//借款项目名称
							
							window.open(__ctxPath+ '/creditFlow/finance/outputExcelSlFundIntent.do?' +
										'bidPlanName='+bidPlanName+'&bidPlanProjectNum='+bidPlanProjectNum+'' +
										'&intentDatel='+intentDatel+"&intentDateg="+intentDateg+"" +
									    "&factDatel="+factDatel+"&factDateg="+factDateg+
									    "&projectName="+projectName+"&isPay='notPay'&isPlateForm=1",'_blank');
						}
					}]
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
					id : 'SlFundIntentPeriodGird',
					isautoLoad:true,
					url : __ctxPath + "/creditFlow/finance/listPeriodbyLedgerSlFundIntent.do?isPay=all&isPlateForm=1",
					fields : ['intentDate',
							'principalRepayment.afterMoney','principalRepayment.incomeMoney','principalRepayment.accrualMoney',
							'loanInterest.afterMoney','loanInterest.incomeMoney','loanInterest.accrualMoney',					
							'consultationMoney.afterMoney','consultationMoney.incomeMoney','consultationMoney.accrualMoney',							
							'serviceMoney.afterMoney','serviceMoney.incomeMoney','serviceMoney.accrualMoney',
						    'interestPenalty.afterMoney','interestPenalty.incomeMoney','interestPenalty.accrualMoney',
							 'factDate','accrualMoney', 'bidPlanName','bidPlanProjectNum'
							,'ptpborrowId','borrowName','punishDays','repaySource','planId','authorizationStatus',
							'payintentPeriod','ids'],
					columns : [{
								header : 'planId',
								dataIndex : 'planId',
								hidden : true
							}, {
								header : '借款人',
								dataIndex : 'borrowName',
								summaryType : 'count',
								summaryRenderer : totalMoney,
								width : 100
							}
							, {
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
								header : "管理咨询费",
								dataIndex : 'consultationMoney.incomeMoney',
								align :'right',
								summaryType :"sum",
								width:110,
								renderer:function(v){
									return Ext.util.Format.number(v,',000,000,000.00')+"元"
                         	     }
							
							} ,{
								header : "财务服务费",
								dataIndex : 'serviceMoney.incomeMoney',
								align :'right',
								summaryType :"sum",
								width:110,
								renderer:function(v){
									return Ext.util.Format.number(v,',000,000,000.00')+"元"
                         	     }
							
							},  {
								header : "合计",
								dataIndex : 'loanInterest.incomeMoney',
								align :'right',
								width:110,
								renderer:function(v,a,r){
									return Ext.util.Format.number((
									r.get("consultationMoney.incomeMoney")+r.get("serviceMoney.incomeMoney")),',000,000,000.00')+"元"
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
								return Ext.util.Format.number((r.get("consultationMoney.afterMoney")+r.get("serviceMoney.afterMoney")),',000,000,000.00')+"元"
                         	     }
							
							}, {
								header : '实际到账日期',
								width : 100,
								dataIndex : 'factDate',
								align : 'center'
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
							}
						]

					});


			},// end of the initComponents()


			reset : function() {
				this.searchPanel.getForm().reset();
				var obj = Ext.getCmp('Q_fundType_N_EQ'+tabflag);
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
			if(record.data.authorizationStatus!=null&&eval(record.data.authorizationStatus)==eval(1)){
				
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
									ids:ids,
									planId:planId,
									intentDate:intentDate,
									ptpborrowId:ptpborrowId
									
									
								}
							});
			}else{
			  Ext.ux.Toast.msg('操作信息', '借款人没有授权该标进行自动还款');
			  return ;
			}
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