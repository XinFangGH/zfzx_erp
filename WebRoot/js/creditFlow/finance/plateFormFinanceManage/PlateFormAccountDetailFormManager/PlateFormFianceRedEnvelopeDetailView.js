//PlateFormFianceRedEnvelopeDetailView.js
PlateFormFianceRedEnvelopeDetailView = Ext.extend(Ext.Panel, {
			title:"",
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				PlateFormFianceRedEnvelopeDetailView.superclass.constructor.call(this, {
							id : 'PlateFormFianceRedEnvelopeDetailView',
							title : '红包发放台账',
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
											fieldLabel : '会员账号',
											name : "loginname",
											xtype : 'textfield',
											labelSeparator : ""
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
											fieldLabel : '会员姓名',
											name : "truename",
											xtype : 'textfield',
											labelSeparator : ""
										}]
							         },{
										columnWidth : .2,
										labelAlign : 'right',
										xtype : 'container',
										layout : 'form',
										labelWidth : 90,
										defaults : {
											anchor : anchor
										},
										items : [{
											fieldLabel : '到账起始日期',
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
										labelWidth : 90,
										defaults : {
											anchor : anchor
										},
										items : [{
											fieldLabel : '到账截止日期',
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
							var loginname=this.getCmpByName("loginname").getValue();//会员账号
							var truename=this.getCmpByName("truename").getValue();//会员名称
							var startDate=this.getCmpByName("startDate").getValue();//到账起始日期
							if(startDate){
								startDate=startDate.format('Y-m-d');
							}
							var endDate=this.getCmpByName("endDate").getValue();//到账截止日期
							if(endDate){
								endDate=endDate.format('Y-m-d');
							}
							window.open(__ctxPath+ '/plateForm/outputRedExcelPlateFormFinance.do?' +
										'loginname='+loginname+'&truename='+truename+'' +
										'&startDate='+startDate+"&endDate="+endDate,'_blank');
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
					id : 'PlateFormFianceRedEnvelopeDetailGrid',
					url : __ctxPath + "/plateForm/getPlateFormRedFianceDetailPlateFormFinance.do",
					fields : [{
								name : 'redTopersonId',
								type : 'int'
							}, 'redId','redName','bpCustMemberId', 'truename','loginname', 
							'registrationDate','redMoney','dredMoney','distributeTime','orderNo', 
							'requestNo'],
					columns : [{
								header : 'redTopersonId',
								dataIndex : 'redTopersonId',
								hidden : true
							}, {
								header : '红包系统流水编号',
								dataIndex : 'orderNo',
								summaryType : 'sum',
								align : 'center',
				                summaryRenderer : totalMoney,
								renderer:function(v){
								 	return Ext.isEmpty(v)?"--":v;
                         	    }
							}, /*{
								header : '第三方对账流水号',
								dataIndex : 'requestNo',
								align : 'center',
								renderer:function(v){
								 	return Ext.isEmpty(v)?"--":v;
                         	    }
							},*/ {
								header : '会员账号',
								align : 'center',
								dataIndex : 'loginname',
								renderer:function(v){
								 	return Ext.isEmpty(v)?"--":v;
                         	    }
							}, {
								header : '会员姓名',
								dataIndex : 'truename',
								align : 'center',
								renderer:function(v){
								 	return Ext.isEmpty(v)?"--":v;
                         	    }
							},{
								header : '会员注册时间',
								dataIndex : 'registrationDate',
								align : 'center',
								renderer:function(v){
								 	return Ext.isEmpty(v)?"--":v;
                         	    }
							},{
								header : '红包名称',
								dataIndex : 'redName'
							},{
								header : '计划派发金额',
								dataIndex : 'redMoney',
								summaryType : 'sum',
								align : 'right',
								renderer:function(v){
								 return Ext.util.Format.number(v,',000,000,000.00')+"元"
                         	    }
							},{
								header : '实际派发金额',
								dataIndex : 'dredMoney',
								summaryType : 'sum',
								align : 'right',
								renderer:function(v){
								 return Ext.util.Format.number(v,',000,000,000.00')+"元"
                         	    }
							} ,{
								header : '到账时间',
								dataIndex : 'distributeTime',
								align : 'center'
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
				/*var startDate=this.searchPanel.getCmpByName("startDate").getValue();
				var endDate=this.searchPanel.getCmpByName("endDate").getValue();
				if(startDate==null||startDate==""){
					this.searchPanel.getCmpByName("startDate").setValue(new Date());
				}
				if(endDate==null||endDate==""){
					this.searchPanel.getCmpByName("endDate").setValue(new Date());
				}*/
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