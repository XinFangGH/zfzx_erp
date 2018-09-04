BidTransferReconciliation = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				BidTransferReconciliation.superclass.constructor.call(this, {
							id : '',
							title : '标的转账',
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
										labelWidth : 100,
										defaults : {
											anchor : anchor
										},
										items : [{
											fieldLabel : '开户日期',
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
					header : "第三方账户流水 ",
					colspan : 3,
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
                        url : __ctxPath + "/creditFlow/creditAssignment/bank/bidTransferReconciliationListObSystemAccount.do?type="+"1",
					//"/creditFlow/creditAssignment/bank/downLoadFileObSystemAccount.do",
					//
					fields : [

							'p2pRequest','p2pDate','bidId','payAccount','incomeNumber','bidAccountNumber','balance','direction','type',
							'payDate','payTime','payRequest','AccountDate'
							],

					columns : [{

						       header :'id',
						       dataIndex:'id',
						       hidden : true
					          },{
								header : 'P2P平台请求流水号',
								dataIndex : 'p2pRequest',
								width : 100,
				                summaryRenderer : totalMoney
							},{
								header : 'P2P平台交易日期',
								dataIndex :'p2pDate', 
								width : 100
							},{

								header : '标的号',
								dataIndex : 'bidId',
								width : 200,
								align : 'right'
								
                     	    },{
								header : '付款方账户号',
								dataIndex : 'payAccount',
								width : 200,
								align : 'right'
								
						 },{

								header : '收款方账户号',
								dataIndex : 'incomeNumber',
								width : 100
							},{
								header : '标的账户号',
								dataIndex : 'bidAccountNumber',
								width : 100
							},{
								header : '金额',
								dataIndex : 'balance',
								summaryType : 'sum',
								width : 100
							},{
								header : '转账方向',
								dataIndex : 'direction',
								width : 100
							},{
							header : '业务类型',
								dataIndex : 'type',
								width : 100
							},{
							header : '支付平台日期',
								dataIndex : 'payDate',
								width : 100
							},{
							header : '支付平台时间',
								dataIndex : 'payTime',
								width : 100
							},{
							header : '支付平台流水号',
								dataIndex : 'payRequest',
								width : 100
							},{
							header : '账户日期',
								dataIndex : 'AccountDate',
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
