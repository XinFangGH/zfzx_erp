RechargeReconciliation = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				RechargeReconciliation.superclass.constructor.call(this, {
							id : 'RechargeReconciliation',
							title : '充值对账',
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
							style : 'padding-left:5px;  -right:5px;padding-top:5px;',
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
											fieldLabel : '系统账号',
											name : "accountNumber",
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
										}],
										items : [{
											fieldLabel : '日期',
											name : 'searchDate',
											xtype : 'datefield',
											format : "Y-m-d"
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

				var summary = new Ext.ux.grid.GridSummary();
				function totalMoney(v, params, data) {
					return '总计(元)';
				}
				
				var cmGroupRow = [{
					header : "平台资金记录 ",
					colspan : 3,
					width:1000,
					align : 'center'
				}, {
					header : "第三方资金记录 ",
					colspan : 1,
					width:1000,
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
                        url : __ctxPath + "/creditFlow/creditAssignment/bank/rechargeReconciliationListObSystemAccount.do?type="+"1",
					//"/creditFlow/creditAssignment/bank/downLoadFileObSystemAccount.do",
					//
					fields : [
							'p2pRequestNo','createDate','accountNo','plateFormNo','money','accountNoDate','accountNoTime','accountOrderNo','userName',
							'accountNoDate','accountNoTime','accountOrderNo','rechargeTime','rechargeMoney','accountBalance','p2pRequestNoLocal','p2pTransferDate'
							],

					columns : [{

						       header :'id',
						       dataIndex:'id',
						       hidden : true
					          },{
								header : '用户名',
								dataIndex : 'investPersonName',
								width : 200,

								header : '账户',
								dataIndex : 'userName',
								width : 100,
				                summaryRenderer : totalMoney
							},{
								header : '充值时间',
								dataIndex :'rechargeTime', 
								width : 100
							},{

								header : '充值金额',
								dataIndex : 'incomMoney',
								width : 200,
								summaryType : 'sum',
								align : 'right',
								renderer:function(v,a,r){
								  return  Ext.isEmpty(r.data.incomMoney)?"0.00元":Ext.util.Format.number(v,',000,000,000.00')+"元";
								}
                     	    },{
								header : '账户余额',
								dataIndex : 'currentMoney',
								width : 200,
								summaryType : 'sum',
								align : 'right',
								renderer:function(v,a,r){
								  return  Ext.isEmpty(r.data.currentMoney)?"0.00元":Ext.util.Format.number(v,',000,000,000.00')+"元";	
	                     	    }
						 },{

								header : '充值金额',
								dataIndex : 'rechargeMoney',
								summaryType : 'sum',
								width : 100
							},{
								header : '账户余额',
								summaryType : 'sum',
								dataIndex : 'accountBalance',
								width : 100
							},{
								header : 'p2p平台请求流水号',
								dataIndex : 'p2pRequestNoLocal',
								width : 100
							},{
								header : 'p2p平台交易日期',
								dataIndex : 'p2pTransferDate',
								width : 100
							},{
							header : '商户号',
								dataIndex : 'plateFormNo',
								width : 100
							},{
							header : '第三方充值金额',
								dataIndex : 'money',
								summaryType : 'sum',
								width : 100
							},{
							header : '资金账户托管平台日期',
								dataIndex : 'accountNoDate',
								width : 100
							},{
							header : '资金账户托管平台时间',
								dataIndex : 'accountNoTime',
								width : 100
							},{
							header : '资金账户托管平台流水号',
								dataIndex : 'accountOrderNo',
								width : 100
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
			
			transfer:function(){
				Ext.ux.Toast.msg('操作信息',"功能开发中，敬请期待");
			},
			//账户调账功能
			changeCorrect:function(){
				Ext.ux.Toast.msg('操作信息',"功能开发中，敬请期待");
			}
		});
