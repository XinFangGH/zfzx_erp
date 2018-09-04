//PlateFormFianceOtherDetailView.js
PlateFormFianceOtherDetailView = Ext.extend(Ext.Panel, {
			title:"",
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				PlateFormFianceOtherDetailView.superclass.constructor.call(this, {
							id : 'PlateFormFianceOtherDetailView',
							title : '非业务相关台账',
							region : 'center',
							layout : 'border',
							iconCls:"btn-tree-team39",
							items : [this.searchPanel, this.gridPanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				// 初始化搜索条件Panel
				var anchor="100%";
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
										columnWidth : .2,
										labelAlign : 'right',
										xtype : 'container',
										layout : 'form',
										labelWidth : 70,
										defaults : {
											anchor : anchor
										},
										items : [{
											fieldLabel : '交易类型',
											hiddenName : "transferType",
											xtype : 'combo',
											labelSeparator : "",
											mode : 'local',
											displayField : 'name',
											valueField : 'id',
											triggerAction : "all",
											editable : false,
											store : new Ext.data.SimpleStore({
													fields : ["name", "id"],
													data : [["充值", "1"],
															["取现", "2"],
															["取现手续费", "6"],
															["充值手续费", "23"],
															["绑卡手续费", "22"],
															["账户调账", "19"]]
												})
										}]
							         },{
										columnWidth : .2,
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
										}]
							         },{
							         	columnWidth : .2,
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
							var transferType=this.getCmpByName("transferType").getValue();//交易类型
							var startDate=this.getCmpByName("startDate").getValue();//起始日期
							if(startDate){
								startDate=startDate.format('Y-m-d');
							}
							var endDate=this.getCmpByName("endDate").getValue();//截止日期
							if(endDate){
								endDate=endDate.format('Y-m-d');
							}
							window.open(__ctxPath+ '/plateForm/outputOtherExcelPlateFormFinance.do?startDate='+startDate+'&endDate='+endDate,'_blank');
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
					sm : this.projectFundsm,
					plugins : [summary],
					// 使用RowActions
					rowActions : false,
					id : 'PlateFormFianceOtherDetailViewGrid',
					url : __ctxPath + "/plateForm/getAccountDealInfoByTransferTypePlateFormFinance.do",
					fields : [{
								name : 'id',
								type : 'int'
							}, 'accountName','accountNumber','accountId', 'incomMoney','payMoney', 
							'transferType','createDate','transferDate','recordNumber','msg', 
							'thirdPayRecordNumber','transferAccountName', 'transferAccountNumber',
							'obligationInfoName','obligationNumber','transferTypeName'],
					columns : [{
								header : 'id',
								dataIndex : 'id',
								hidden : true
							}, {
								header : '系统交易流水号',
								dataIndex : 'recordNumber',
								summaryType : 'sum',
				                summaryRenderer : totalMoney
							}, {
								header : '第三方对账流水号',
								align : 'center',
								dataIndex : 'thirdPayRecordNumber',
								renderer:function(v){
								 	return Ext.isEmpty(v)?"--":v;
                         	    }
							}, {
								header : '转入账号',
								align : 'center',
								dataIndex : 'accountNumber',
								renderer:function(v,a,r){
									if(r.data.incomMoney>0){
										
										return Ext.isEmpty(r.data.accountNumber)?"--":r.data.accountNumber;
									}else{
										return Ext.isEmpty(r.data.transferAccountNumber)?"--":r.data.transferAccountNumber;
									}
								 
                         	    }
							}, {
								header : '转出账号',
								align : 'center',
								dataIndex : 'transferAccountNumber',
								renderer:function(v,a,r){
									if(r.data.incomMoney>0){
										return  Ext.isEmpty(r.data.transferAccountNumber)?"--":r.data.transferAccountNumber;
									}else{
										return  Ext.isEmpty(r.data.accountNumber)?"--":r.data.accountNumber;
									}
								 
                         	    }
							},{
								header : '交易类型',
								align : 'center',
								dataIndex : 'transferTypeName'
							},{
								header : '收入金额',
								dataIndex : 'incomMoney',
								summaryType : 'sum',
								align : 'right',
								renderer:function(v){
								 return Ext.util.Format.number(v,',000,000,000.00')+"元"
                         	    }
							},{
								header : '支出金额',
								dataIndex : 'payMoney',
								summaryType : 'sum',
								align : 'right',
								renderer:function(v){
								 return Ext.util.Format.number(v,',000,000,000.00')+"元"
                         	    }
							} ,{
								header : '创建时间',
								dataIndex : 'createDate',
								align : 'center'
							},{
								header : '实际交易时间',
								dataIndex : 'transferDate'
							},{
								header : '交易摘要',
								dataIndex : 'msg'
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