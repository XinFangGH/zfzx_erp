//SystemAccountTotalCheckFile    平台下下属会员账户的对账文件查询
StandardReconciliation = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				StandardReconciliation.superclass.constructor.call(this, {
							id : 'StandardReconciliation',
							title : '标的对账',
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
//										columnWidth : .2,
//										labelAlign : 'right',
//										xtype : 'container',
//										layout : 'form',
//										labelWidth : 70,
//										defaults : {
//											anchor : anchor
//										},
//										items : [{
//											fieldLabel : '标的名称',
//											name : "bidProName",
//											xtype : 'textfield'
//										}]
//							         },{
							         	columnWidth : .2,
										labelAlign : 'right',
										xtype : 'container',
										layout : 'form',
										labelWidth : 100,
										defaults : {
											anchor : anchor
										},
										items : [{
											fieldLabel : '日期',
											name : 'searchDate',
											xtype : 'datefield',
											format : "Y-m-d"
										}]
							         },
							         {
										columnWidth : .1,
										xtype : 'container',
										layout : 'form',
										defaults : {
											xtype : 'button'
										},
										
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
			        	iconCls : 'btn-detail',
			        	text : '查看还款明细',
						xtype : 'button',
						scope : this,
						handler : this.see
							
					}/*,new Ext.Toolbar.Separator({
						//hidden : isGranted('_liushui_f_'+this.businessType)?false:true
					}),{
						iconCls : 'btn-detail',
						text : '查看系统交易明细',
						xtype : 'button',
						scope : this
			
					}*/]
				});

				var summary = new Ext.ux.grid.GridSummary();
				function totalMoney(v, params, data) {
					return '总计(元)';
				}
				
			
				this.gridPanel = new HT.EditorGridPanel( {
					region : 'center',
					tbar : this.topbar,
					clicksToEdit :1,
					pruneModifiedRecords:true,
					HiddenCm:true,
					plugins : [summary],
					url : __ctxPath + "/creditFlow/creditAssignment/bank/bidReconciliationListObSystemAccount.do",
					fields : [ {
						name : 'id',
						type : 'long'
					}, 'bidId','bidAccount','state','balance','createDate','borrowPersonList','bondsmanList','payerList','bidName',
							'accountBalance','withdrawMoney','transferMoney','checkAccountDate','saveAccountDate','transferState'],
					columns : [{
						       header : 'id',
						       dataIndex:'id',
						       hidden : true
					          },{
								header : '标的名称',
								dataIndex : 'bidName',
								width : 200,
				                summaryRenderer : totalMoney
							},{
								header : '标的号',
								dataIndex : 'bidId',
								width : 200
							},{
								header : '标的账户号',
								dataIndex : 'bidAccount',
								width : 200,
								align : 'right'
								
                     	    },{
								header : '状态',
								dataIndex : 'state',
								width : 200,
								align : 'right'
						 },{
								header : '余额',
								dataIndex : 'balance',
								width : 200,
								summaryType : 'sum',
								renderer:function(v,a,r){
								  return  Ext.isEmpty(r.data.balance)?"0.00元":Ext.util.Format.number(v,',000,000,000.00')+"元";	
	                     	    }
							}
							,{
								header : '创建日期',
								dataIndex : 'createDate',
								width : 200
							}
							,{
								header : '借款人列表',
								dataIndex : 'borrowPersonList',
								width : 200
							}
						 ,{
								header : '担保人列表',
								dataIndex : 'bondsmanList',
								width : 200
                     	    }
                     	    ,{
								header : '缴费方列表',
								dataIndex : 'payerList',
								width : 200
							}
						]
					});
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
			see : function(){
				var s = this.gridPanel.getSelectionModel().getSelections();
				if (s <= 0) {
					Ext.ux.Toast.msg('操作信息', '请选中一条记录');
					return false;
				} else if (s.length > 1) {
					Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
					return false;
				}else{
					var record = s[0];
					new StandardReconciliationView({
						planId:record.data.bidId,
						borrowPersonList:record.data.borrowPersonList
					}).show();
				  }
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
