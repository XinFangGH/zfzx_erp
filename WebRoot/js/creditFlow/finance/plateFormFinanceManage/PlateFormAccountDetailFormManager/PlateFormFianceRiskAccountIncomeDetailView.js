//PlateFormFianceRiskAccountIncomeDetailView
PlateFormFianceRiskAccountIncomeDetailView = Ext.extend(Ext.Panel, {
			title:"",
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				PlateFormFianceRiskAccountIncomeDetailView.superclass.constructor.call(this, {
							id : 'PlateFormFianceRiskAccountIncomeDetailView',
							title : "平台其他收费台账",
							region : 'center',
							layout : 'border',
							iconCls:"btn-tree-team39",
							items : [this.searchPanel, this.gridPanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				// 初始化搜索条件Panel
				this.searchPanel = new HT.SearchPanel({
							layout : 'column',
							style : 'padding-left:5px;padding-right:5px;padding-top:5px;',
							region : 'north',
							height : 20,
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
							items : [{
										columnWidth : .3,
										labelAlign : 'right',
										xtype : 'container',
										layout : 'form',
										labelWidth : 90,
										defaults : {
											anchor : '96%'
										},
										items : [{
											fieldLabel : '招标项目名称',
											name : "bidPlanName",
											xtype : 'textfield',
											labelSeparator : ""
										},{
											fieldLabel : '招标项目编号',
											name : "bidPlanNumber",
											xtype : 'textfield',
											labelSeparator : ""
										}]
							         },{
										columnWidth : .25,
										labelAlign : 'right',
										xtype : 'container',
										layout : 'form',
										labelWidth : 120,
										defaults : {
											anchor : '96%'
										},
										items : [{
											fieldLabel : '实际到账日期开始',
											name : "startDate",
											xtype : 'datefield',
											labelSeparator : "",
											format : 'Y-m-d'
										},{
											fieldLabel : '借款人',
											name : "borrowerName",
											xtype : 'textfield',
											labelSeparator : ""
										}]
							         },{
							         	columnWidth : .25,
										labelAlign : 'right',
										xtype : 'container',
										layout : 'form',
										labelWidth : 120,
										defaults : {
											anchor :'96%'
										},
										items : [{
											fieldLabel : '实际到账日期截止',
											name : "endDate",
											xtype : 'datefield',
											labelSeparator : "",
											format : 'Y-m-d'
										}]
							         },{
										columnWidth : .1,
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
										},{
											text : '重置',
											scope : this,
											iconCls : 'reset',
											handler : this.reset
										}]
									}]
						});

				this.topbar = new Ext.Toolbar({
					items : [{
						text : '导出到Excel',
						iconCls : 'btn-xls',
						scope : this,
						hidden : isGranted('_export_grkh') ? false : true,
						handler : function() {
							var bidPlanName=this.getCmpByName("bidPlanName").getValue();//招标项目名称
							var bidPlanNumber=this.getCmpByName("bidPlanNumber").getValue();//招标项目编号
							var startDate=this.getCmpByName("startDate").getValue();//起始日期
							if(startDate){
								startDate=startDate.format('Y-m-d');
							}
							var endDate=this.getCmpByName("endDate").getValue();//截止日期
							if(endDate){
								endDate=endDate.format('Y-m-d');
							}
							var borrowerName=this.getCmpByName("borrowerName").getValue();//借款人
							
							window.open(__ctxPath+ '/plateForm/outputExcelPlateFormFinance.do?' +
										'bidPlanName='+bidPlanName+'&bidPlanNumber='+bidPlanNumber+'' +
										'&startDate='+startDate+"&endDate="+endDate+"" +
									    "&borrowerName="+borrowerName,'_blank');
						}
					}]
				});

				var summary = new Ext.ux.grid.GridSummary();
				function totalMoney(v, params, data) {
					return '总计(元)';
				}
				this.gridPanel = new HT.GridPanel({
					bodyStyle : "width : 100%",
					region : 'center',
					tbar : this.topbar,
					//sm : this.projectFundsm,
					plugins : [summary],
					//viewConfig: {  
		            	//forceFit:false  
		            //},
		           // forceFit: true,
					// 使用RowActions
					rowActions : false,
					id : 'PlateFormFianceRiskAccountIncomeDetailGrid',
					url : __ctxPath + "/plateForm/getOneTimePlateFormReciveMoneyPlateFormFinance.do",
					fields : [{
								name : 'fundId',
								type : 'int'
							}, 'fundType','fundTypeName','borrowerName', 'borrowerId','p2pborrowerId', 'p2pborrowerName','bidPlanId','bidPlanName',
							 'bidPlanNumber','planIncomeMoney', 'planReciveDate','factIncomeMoney', 'factReciveDate','notMoney'],
					columns : [{
								header : 'fundId',
								dataIndex : 'fundId',
								hidden : true
							}, {
								header : '借款人',
								width : 100,
								summaryRenderer : totalMoney,
								dataIndex : 'borrowerName'
							}, {
								header : '招标项目名称',
								width : 180,
								dataIndex : 'bidPlanName'
							}, {
								header : '招标项目编号',
								width : 120,
								dataIndex : 'bidPlanNumber'
							},  {
								header : '资金类型',
								width : 100,
								dataIndex : 'fundTypeName'
							},{
								header : '计划收入金额',
								dataIndex : 'planIncomeMoney',
								
								summaryType : 'sum',
								align : 'right',
								width : 100,
								renderer:function(v){
								 return Ext.util.Format.number(v,',000,000,000.00')+"元"
                         	    }
							},{
								header : '计划收入时间',
								width : 100,
								dataIndex : 'planReciveDate',
								align : 'center'
							},{
								header : '实际到账金额',
								dataIndex : 'factIncomeMoney',								
								width : 100,
								summaryType : 'sum',
								align : 'right',
								renderer:function(v){
								 return Ext.util.Format.number(v,',000,000,000.00')+"元"
                         	    }
							} ,{
								header : '实际到账时间',
								width : 100,
								dataIndex : 'factReciveDate'
							},{
								header : '未到账金额',
								dataIndex : 'notMoney',
								width : 100,
								summaryType : 'sum',
								align : 'right',
								renderer:function(v){
								 return Ext.util.Format.number(v,',000,000,000.00')+"元"
                         	    }
							}
						]
					});

				 //this.gridPanel.addListener('rowdblclick',this.rowClick);

			},
			
			reset : function() {
				this.searchPanel.getForm().reset();
				var obj = Ext.getCmp('Q_fundType_N_EQ');
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
			
			rowClick:function(){
				
			},
			createAccount : function() {
				var accountType=this.accountType;
				Ext.Ajax.request({
					url: __ctxPath + '/creditFlow/creditAssignment/bank/getPrepareMentObSystemAccount.do?accountType=plateFormAccount',
					params : {
						accountType : accountType
					},
					scope : this,
					method : 'post',
					success : function(response){
						var result = Ext.util.JSON.decode(response.responseText)
						var code=result.code;
						if(eval(code)==eval(1)){//表示可以开通平台普通账户或者风险保证金账户
							var createnewForm=new PlateFormCreateAccountForm({
								accountType:accountType,
								thirdPayName:result.thirdPayName,
								thirdPayType:result.thirdPayTypeName,
								plateFormnumber:result.platFormNumber
								
							});
							createnewForm.show();
						}else{
							Ext.ux.Toast.msg('操作信息',result.msg);
						}
						
					},
					failure : function(){
						Ext.ux.Toast.msg('操作信息','操作出错,请联系管理员');
					}
				});
			},
		//
			refresh:function(){
				var accountType=this.accountType;
				var topbar=this.topbar
				Ext.Ajax.request({
					url: __ctxPath + '/creditFlow/creditAssignment/bank/getAccountBalanceMoneyObSystemAccount.do',
					params : {
						accountType : accountType
					},
					scope : this,
					method : 'post',
					success : function(response){
						var result = Ext.util.JSON.decode(response.responseText)
						var code=result.msg;
						topbar.items.get(topbar.items.length-2).setText(code,false);
					},
					failure : function(){
						Ext.ux.Toast.msg('操作信息','操作出错,请联系管理员');
					}
				});
			},
			recharge :function(){
				var accountType=this.accountType;
				var refreshPanel=this.gridPanel;
				Ext.Ajax.request({
					url: __ctxPath + '/creditFlow/creditAssignment/bank/plateRechargePreObSystemAccount.do?accountType=plateFormAccount',
					params : {
						accountType : accountType
					},
					scope : this,
					method : 'post',
					success : function(response){
						var result = Ext.util.JSON.decode(response.responseText)
						var code=result.code;
						if(eval(code)==eval(1)){//表示可以开通平台普通账户或者风险保证金账户
							var createnewForm=new PlateFormAccountRechargeForm({
								refreshPanel:refreshPanel,
								accountType:accountType,
								thirdPayName:result.thirdPayName,
								thirdPayType:result.thirdPayTypeName,
								plateFormnumber:result.platFormNumber,
								accountName:result.accountName,
								accountId:result.accountId,
								accountNumber:result.accountNumber,
								balanceMoney:result.balanceMoney,
								openNewPage:result.openNewPage  //表示充值页面是否需要用window.open 打开页面 0表示不需要，1需要
							});
							createnewForm.show();
						}else{
							Ext.ux.Toast.msg('操作信息',result.msg);
						}
						
					},
					failure : function(){
						Ext.ux.Toast.msg('操作信息','操作出错,请联系管理员');
					}
				});
			},
			withdraw:function(){
				var accountType=this.accountType;
				var refreshPanel=this.gridPanel;
				Ext.Ajax.request({
					url: __ctxPath + '/creditFlow/creditAssignment/bank/plateRechargePreObSystemAccount.do?accountType=plateFormAccount',
					params : {
						accountType : accountType
					},
					scope : this,
					method : 'post',
					success : function(response){
						var result = Ext.util.JSON.decode(response.responseText)
						var code=result.code;
						if(eval(code)==eval(1)){//表示可以开通平台普通账户或者风险保证金账户
							var createnewForm=new PlateFormAccountWithdraw({
								refreshPanel:refreshPanel,
								accountType:accountType,
								thirdPayName:result.thirdPayName,
								thirdPayType:result.thirdPayTypeName,
								plateFormnumber:result.platFormNumber,
								accountName:result.accountName,
								accountId:result.accountId,
								accountNumber:result.accountNumber,
								balanceMoney:result.balanceMoney,
								openNewPage:result.openNewPage  //表示充值页面是否需要用window.open 打开页面 0表示不需要，1需要
							});
							createnewForm.show();
						}else{
							Ext.ux.Toast.msg('操作信息',result.msg);
						}
						
					},
					failure : function(){
						Ext.ux.Toast.msg('操作信息','操作出错,请联系管理员');
					}
				});
			},
			//账户转账功能
			transfer:function(){
				Ext.ux.Toast.msg('操作信息',"功能开发中，敬请期待");
			},
			//账户调账功能
			changeCorrect:function(){
				Ext.ux.Toast.msg('操作信息',"功能开发中，敬请期待");
			}
		});