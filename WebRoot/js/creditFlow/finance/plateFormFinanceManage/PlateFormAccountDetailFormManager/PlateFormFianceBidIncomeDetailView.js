//PlateFormFianceBidIncomeDetailView.js
PlateFormFianceBidIncomeDetailView = Ext.extend(Ext.Panel, {
			title:"",
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				PlateFormFianceBidIncomeDetailView.superclass.constructor.call(this, {
							id : 'PlateFormFianceBidIncomeDetailView',
							title : "随息费用台账",
							region : 'center',
							layout : 'border',
							iconCls :'btn-team29',
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
											anchor : anchor
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
										labelWidth : 70,
										defaults : {
											anchor : anchor
										},
										items : [{
											fieldLabel : '起始日期',
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
										labelWidth : 70,
										defaults : {
											anchor : anchor
										},
										items : [{
											fieldLabel : '截止日期',
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
					items : []
				});

				var summary = new Ext.ux.grid.GridSummary();
				function totalMoney(v, params, data) {
					return '总计(元)';
				}
				this.gridPanel = new HT.GridPanel({
					bodyStyle : "width : 100%",
					region : 'center',
					tbar : this.topbar,
					sm : this.projectFundsm,
					plugins : [summary],
					viewConfig: {  
		            	forceFit:false  
		            },
		            forceFit: true,
					// 使用RowActions
					rowActions : false,
					id : 'PlateFormFianceBidIncomeDetailGrid',
					url : __ctxPath + "/plateForm/getBidPlateFormReciveMoneyPlateFormFinance.do",
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
								dataIndex : 'borrowerName',
								width : '8%',
								summaryType : 'sum',
				                summaryRenderer : totalMoney
							}, {
								header : '招标项目名称',
								dataIndex : 'bidPlanName',
								width : '19%',
								summaryType : 'sum',
				                summaryRenderer : totalMoney
							}, {
								header : '招标项目编号',
								dataIndex : 'bidPlanNumber',
								width : "19%",
								summaryType : 'sum',
				                summaryRenderer : totalMoney
							},  {
								header : '资金类型',
								dataIndex : 'fundTypeName',
								width : "10%",
								summaryType : 'sum',
				                summaryRenderer : totalMoney
							},{
								header : '计划收入金额',
								dataIndex : 'factIncomeMoney',
								width : "8%",
								summaryType : 'sum',
								align : 'right',
								renderer:function(v){
									return Ext.util.Format.number(v,',000,000,000.00')+"元"
                         	    }
							},{
								header : '计划收入时间',
								width : "8%",
								dataIndex : 'planReciveDate',
								align : 'center'
							},{
								header : '实际到账金额',
								dataIndex : 'factIncomeMoney',
								width : "8%",
								summaryType : 'sum',
								align : 'right',
								renderer:function(v){
								 	return Ext.util.Format.number(v,',000,000,000.00')+"元"
                         	    }
							} ,{
								header : '实际到账时间',
								width : "8%",
								dataIndex : 'factReciveDate'
							},{
								header : '未到账金额',
								dataIndex : 'notMoney',
								width : "8%",
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