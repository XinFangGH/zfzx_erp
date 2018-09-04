//SystemAccountIsException    平台下下属会员账户的对账文件查询
SystemAccountIsException = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				SystemAccountIsException.superclass.constructor.call(this, {
							id : 'SystemAccountIsException',
							title : '用户资金异常账单',
							region : 'center',
							layout : 'border',
							iconCls :'btn-tree-team39',
							items : [this.searchPanel,this.gridPanel]
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
											fieldLabel : '用户名',
											name : "loginname",
											xtype : 'textfield'
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
											fieldLabel : '真实姓名',
											name : "accountName",
											xtype : 'textfield'
										}]
							         },{
							         	columnWidth : .2,
										labelAlign : 'right',
										xtype : 'container',
										layout : 'form',
										labelWidth : 100,
										defaults : {
											anchor : anchor
										},
										items : [{
											fieldLabel : '第三方支付账号',
											name : "thirdPayFlagId",
											xtype : 'textfield'
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
			        	iconCls : 'btn-user-sel',
			        	text : '刷新单个异常用户账户资金',
						xtype : 'button',
						scope : this,
						handler : this.refreshThirdAccount
							
					}]
				});

				var summary = new Ext.ux.grid.GridSummary();
				function totalMoney(v, params, data) {
					return '总计(元)';
				}
				
				var cmGroupRow = [{},{},
				{
					header : "用户基础信息",
					colspan : 3,
					align : 'center'
				},{
					header : "平台资金记录 ",
					colspan : 3,
					width:600,
					align : 'center'
				}, {
					header : "第三方资金记录 ",
					colspan : 3,
					width:600,
					align : 'center'
				},{
					header : ' ',
					colspan : 1,
					align : 'center'
				}]
				
				var group = new Ext.ux.grid.ColumnHeaderGroup({
					rows : [cmGroupRow]
				});
				this.gridPanel = new HT.EditorGridPanel( {
					region : 'center',
					tbar : this.topbar,
					clicksToEdit :1,
					pruneModifiedRecords:true,
					HiddenCm:true,
					plugins : [group,summary],
					url : __ctxPath + "/creditFlow/creditAssignment/bank/newSystemAccountListObSystemAccount.do?isException=1",
					fields : [ {
						name : 'id',
						type : 'long'
					}, 'accountName', 'accountNumber', 'investmentPersonId','thirdPayConfigId', 'totalMoney','isException','loginname',
							'accountStatus', 'investPersionType','thirdTotalMoney','thirdFreezyMoney','thirdAviableMoney','checkDate','freezeMoney','availableInvestMoney'],
					columns : [{
								header : '用户名',
								dataIndex : 'loginname',
								width : 150
								
							}, {
								header : '真实姓名',
								width : 150,
								dataIndex : 'accountName',
								align : 'center'
							}, {
								header : '第三支付账号',
								width : 150,
								align : 'center',
								dataIndex : 'thirdPayConfigId',
								renderer:function(v,a,r){
								  return  Ext.isEmpty(r.data.thirdPayConfigId)?"---":r.data.thirdPayConfigId;	
                         	    }
							},{
								header : '账户金额',
								dataIndex : 'totalMoney',
								width : 200,
								summaryType : 'sum',
								align : 'right',
								renderer:function(v,a,r){
								  return  Ext.isEmpty(r.data.totalMoney)?"0.00元":Ext.util.Format.number(v,',000,000,000.00')+"元";	
                         	    }
							}, {
								header : '可用金额',
								dataIndex : 'availableInvestMoney',
								width :200,
								align : 'right',
								summaryType : 'sum',
								renderer:function(v,a,r){
								  return  Ext.isEmpty(r.data.availableInvestMoney)?"0.00元":Ext.util.Format.number(v,',000,000,000.00')+"元";	
                         	    }
							}, {
								header : '冻结金额',
								dataIndex : 'freezeMoney',
								align : 'right',
								width : 200,
								summaryType : 'sum',
								renderer:function(v,a,r){
								  return  Ext.isEmpty(r.data.freezeMoney)?"0.00元":Ext.util.Format.number(v,',000,000,000.00')+"元";	
                         	    }
							},{
								header : '账户金额',
								dataIndex : 'thirdTotalMoney',
								width : 200,
								summaryType : 'sum',
								align : 'right',
								renderer:function(v,a,r){
								  return  Ext.isEmpty(r.data.thirdTotalMoney)?"0.00元":Ext.util.Format.number(v,',000,000,000.00')+"元";	
                         	    }
							}, {
								header : '可用金额',
								dataIndex : 'thirdAviableMoney',
								width :200,
								align : 'right',
								summaryType : 'sum',
								renderer:function(v,a,r){
								  return  Ext.isEmpty(r.data.thirdAviableMoney)?"0.00元":Ext.util.Format.number(v,',000,000,000.00')+"元";	
                         	    }
							}, {
								header : '冻结金额',
								dataIndex : 'thirdFreezyMoney',
								align : 'right',
								width : 200,
								summaryType : 'sum',
								renderer:function(v,a,r){
								  return  Ext.isEmpty(r.data.thirdFreezyMoney)?"0.00元":Ext.util.Format.number(v,',000,000,000.00')+"元";	
                         	    }
							}, {
								header : '最后对账日',
								width : 150,
								dataIndex : 'checkDate',
								align : 'center',
								renderer:function(v,a,r){
								  return  Ext.isEmpty(r.data.checkDate)?"---":r.data.checkDate;	
                         	    }
							}
						]
					});

				// this.gridPanel.addListener('rowdblclick',this.rowClick);

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
			refreshThirdAccount : function() {
				var s = this.gridPanel.getSelectionModel().getSelections();
				if (s <= 0) {
					Ext.ux.Toast.msg('操作信息', '请选中一条记录');
					return false;
				} else if (s.length > 1) {
					Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
					return false;
				}else{
					var thirdConfigId=s[0].data.thirdPayConfigId;
					var panel=this.gridPanel;
					var accountId=s[0].data.id;
					Ext.MessageBox.wait("系统正在刷新用户资金记录，请稍后......");
					if(thirdConfigId!=null&&thirdConfigId!=""){
						var accountId=s[0].data.id;
						Ext.Ajax.request({
							url: __ctxPath + '/creditFlow/creditAssignment/bank/refreshThirdPayAccountCheckFileObSystemAccount.do',
							params : {
								accountId : accountId
							},
							scope : this,
							method : 'post',
							success : function(response){
								Ext.ux.Toast.msg('操作信息','刷新成功!');
								Ext.MessageBox.hide();
								panel.getStore().reload();
							},
							failure : function(){
								
							}
						});
					}else{
						Ext.ux.Toast.msg('操作信息','系统账户没有第三方支付账户，不需要刷新第三方账户对账文件');
					}
				}
				
			},
			refreshAllThirdAccount : function() {
				Ext.MessageBox.confirm('刷新系统所有用户的资金记录','刷新所有用户的资金记录需要较长的时间',function(btn){
					if(btn=="yes"){
						Ext.MessageBox.wait("系统正在刷新所有用户资金记录,可能需要较长时间,请耐心等待.......");
						Ext.Ajax.request({
								url: __ctxPath + '/creditFlow/creditAssignment/bank/refreshAllobSystemAccountObSystemAccount.do?day=',
								scope : this,
								method : 'post',
								success : function(response){
									Ext.ux.Toast.msg('操作信息','刷新成功!');
									Ext.MessageBox.hide();
									panel.getStore().reload();
								},
								failure : function(){
									
								}
							});
					}
				})
			},
		
			refresh:function(){
				var accountType=this.accountType;
				var topbar=this.topbar
				Ext.Ajax.request({
					url: __ctxPath + '/plateForm/getAccountBalanceMoneyPlateFormFinance.do',
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
					url: __ctxPath + '/plateForm/plateRechargePrePlateFormFinance.do?accountType=plateFormAccount',
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
					url: __ctxPath + '/plateForm/plateRechargePrePlateFormFinance.do?accountType=plateFormAccount',
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
