StandardReconciliationTransfer = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				StandardReconciliationTransfer.superclass.constructor.call(this, {
							id : 'StandardReconciliationTransfer',
							title : '转账对账',
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
										labelWidth : 100,
										defaults : {
											anchor : anchor
										},
										items : [{
											fieldLabel : '日期',
											name : 'abortDate',
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

				this.topbar = new Ext.Toolbar({
					items : [{
			        	iconCls : 'btn-detail',
			        	text : '查看',
						xtype : 'button',
						scope : this,
						handler : this.see
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
					url : __ctxPath + "/creditFlow/creditAssignment/bank/StandardReconciliationTransferListObSystemAccount.do",
					fields : [ {
						name : 'id',
						type : 'long'
					}, 'TransferOrderNo','TransferData','TransferPay','TransferRectipt','TransferMoney'
					,'TransferPayData','TransferPayTime','TransferPayOrderNo','TransferAccountData'],
					columns : [{
						       header : 'id',
						       dataIndex:'id',
						       hidden : true
					          },{
								header : 'P2P平台请求流水号号',
								dataIndex : 'TransferOrderNo',
								width : 200,
								height:100,
				                summaryRenderer : totalMoney
							},{
								header : 'P2P平台交易日期',
								dataIndex : 'TransferData',
								width : 250,
								height:100
							},{
								header : '转出方账户号',
								dataIndex : 'TransferPay',
								width : 200,
								height:100
							},{
								header : '转入方账户号',
								dataIndex : 'TransferRectipt',
								width : 200,
								height:100
							},{
								header : '金额',
								dataIndex : 'TransferMoney',
								summaryType : 'sum',
								width : 200,
								height:100
							},{
								header : '支付平台日期',
								dataIndex : 'TransferPayData',
								width : 200,
								height:100
							},{
								header : '支付平台时间',
								dataIndex : 'TransferPayTime',
								width : 200,
								height:100
							},{
								header : '支付平台流水号',
								dataIndex : 'TransferPayOrderNo',
								width : 200,
								height:100
							},{
								header : '账户日期',
								dataIndex : 'TransferAccountData',
								width : 200,
								height:100
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
