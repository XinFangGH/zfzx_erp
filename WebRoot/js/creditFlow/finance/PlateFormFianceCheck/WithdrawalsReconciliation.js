WithdrawalsReconciliation = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				WithdrawalsReconciliation.superclass.constructor.call(this, {
							id : 'WithdrawalsReconciliation',
							title : '提现对账',
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
							         },
							         	{
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

				var summary = new Ext.ux.grid.GridSummary();
				function totalMoney(v, params, data) {
					return '总计(元)';
				}
				
				var cmGroupRow = [/*{
					header : " ",
					colspan : 1,
					align : 'center'
				},{
					header : " ",
					colspan : 1,
					align : 'center'
				},{
					header : " ",
					colspan : 1,
					align : 'center'
				},{
					header : " ",
					colspan : 1,
					align : 'center'
				},{
					header : " ",
					colspan : 1,
					align : 'center'
				},{
					header : "平台资金记录 ",
					colspan : 2,
					width:600,
					align : 'center'
				}, {
					header : "第三方资金记录 ",
					colspan : 1,
					width:600,
					align : 'center'
				},{
					header : ' ',
					colspan : 1,
					align : 'center'
				}*/]
				
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
					url : __ctxPath + "/creditFlow/creditAssignment/bank/withdrawalsReconciliationListObSystemAccount.do",
					fields : [ {
						name : 'id',
						type : 'long'
					}, 'userName','telphone','withdrawTime','transferOrderNo','orderDate','plateOrderNo','plateFormNo','orderDate',
							'accountBalance','withdrawMoney','transferMoney','checkAccountDate','saveAccountDate','transferState'],
					columns : [{
						       header : 'id',
						       dataIndex:'id',
						       hidden : true
					          },{
								header : '账户',
								dataIndex : 'userName',
								width : 200,
				                summaryRenderer : totalMoney
							},{
								header : '提现时间',
								dataIndex : 'withdrawTime',
								width : 200
							},{
								header : '提现金额',
								dataIndex : 'withdrawMoney',
								width : 200,
								summaryType : 'sum',
								align : 'right',
								renderer:function(v,a,r){
								  return  Ext.isEmpty(r.data.withdrawMoney)?"0.00元":Ext.util.Format.number(v,',000,000,000.00')+"元";
								}
                     	    },{
								header : '账户余额',
								dataIndex : 'accountBalance',
								width : 200,
								summaryType : 'sum',
								align : 'right',
								renderer:function(v,a,r){
								  return  Ext.isEmpty(r.data.accountBalance)?"0.00元":Ext.util.Format.number(v,',000,000,000.00')+"元";	
	                     	    }
						 },{
								header : '商户号',
								dataIndex : 'plateFormNo',
								width : 200
							}
							,{
								header : '商户订单号',
								dataIndex : 'plateOrderNo',
								width : 200
							}
							,{
								header : '订单日期',
								dataIndex : 'orderDate',
								width : 200
							}
						 ,{
								header : '提现金额',
								dataIndex : 'transferMoney',
								width : 200,
								summaryType : 'sum',
								align : 'right',
								renderer:function(v,a,r){
								  return  Ext.isEmpty(r.data.withdrawMoney)?"0.00元":Ext.util.Format.number(v,',000,000,000.00')+"元";
								}
                     	    }
                     	    ,{
								header : '对账日期',
								dataIndex : 'checkAccountDate',
								width : 200
							}
							,{
								header : '记账日期',
								dataIndex : 'saveAccountDate',
								width : 200
							}
                     	    ,{
								header : '交易状态',
								dataIndex : 'transferState',
								width : 200
							}
							,{
								header : '交易流水',
								dataIndex : 'transferOrderNo',
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
			
			transfer:function(){
				Ext.ux.Toast.msg('操作信息',"功能开发中，敬请期待");
			},
			//账户调账功能
			changeCorrect:function(){
				Ext.ux.Toast.msg('操作信息',"功能开发中，敬请期待");
			}
		});
