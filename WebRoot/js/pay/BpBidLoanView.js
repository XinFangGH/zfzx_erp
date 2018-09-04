/**
 * @author
 * @class BpBidLoanView
 * @extends Ext.Panel
 * @description [BpBidLoan]管理
 * @company 智维软件
 * @createtime:
 */
BpBidLoanView = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				BpBidLoanView.superclass.constructor.call(this, {
							id : 'BpBidLoanView',
							title : '[BpBidLoan]管理',
							region : 'center',
							layout : 'border',
							items : [this.searchPanel, this.gridPanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				// 初始化搜索条件Panel
				this.searchPanel=new HT.SearchPanel({
							layout : 'form',
							region : 'north',
							colNums:3,
							items:[
																					 																																					 								{
									fieldLabel:'pId',
									name : 'Q_pId_L_EQ',
									flex:1,
																		xtype:'numberfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'loanNo',
									name : 'Q_loanNo_S_EQ',
									flex:1,
																		xtype : 'textfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'orderNo',
									name : 'Q_orderNo_S_EQ',
									flex:1,
																		xtype : 'textfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'batchNo',
									name : 'Q_batchNo_S_EQ',
									flex:1,
																		xtype : 'textfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'amount',
									name : 'Q_amount_D_EQ',
									flex:1,
																		xtype:'numberfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'transferName',
									name : 'Q_transferName_S_EQ',
									flex:1,
																		xtype : 'textfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'remark',
									name : 'Q_remark_S_EQ',
									flex:1,
																		xtype : 'textfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'loanOutFlag',
									name : 'Q_loanOutFlag_S_EQ',
									flex:1,
																		xtype : 'textfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'loanInFlag',
									name : 'Q_loanInFlag_S_EQ',
									flex:1,
																		xtype : 'textfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'state',
									name : 'Q_state_SN_EQ',
									flex:1,
																		xtype:'numberfield'
																	}
															 							 							 															],
								buttons:[
									{
										text:'查询',
										scope:this,
										iconCls:'btn-search',
										handler:this.search
									},{
										text:'重置',
										scope:this,
										iconCls:'btn-reset',
										handler:this.reset
									}							
								]	
				});// end of searchPanel

				this.topbar = new Ext.Toolbar({
						items : [{
									iconCls : 'btn-add',
									text : '添加[BpBidLoan]',
									xtype : 'button',
									scope : this,
									handler : this.createRs
								}, {
									iconCls : 'btn-del',
									text : '删除[BpBidLoan]',
									xtype : 'button',
									scope:this,
									handler : this.removeSelRs
								}]
				});
	
				this.gridPanel=new HT.GridPanel({
					region:'center',
					tbar:this.topbar,
					//使用RowActions
					rowActions:true,
					id:'BpBidLoanGrid',
					url : __ctxPath + "/pay/listBpBidLoan.do",
					fields : [{
									name : 'bidLoanId',
									type : 'int'
								}
																																																	,'pId'
																																										,'loanNo'
																																										,'orderNo'
																																										,'batchNo'
																																										,'amount'
																																										,'transferName'
																																										,'remark'
																																										,'loanOutFlag'
																																										,'loanInFlag'
																																										,'state'
																																			],
					columns:[
								{
									header : 'bidLoanId',
									dataIndex : 'bidLoanId',
									hidden : true
								}
																																																								,{
																	header : 'pId',	
																	dataIndex : 'pId'
								}
																																																,{
																	header : 'loanNo',	
																	dataIndex : 'loanNo'
								}
																																																,{
																	header : 'orderNo',	
																	dataIndex : 'orderNo'
								}
																																																,{
																	header : 'batchNo',	
																	dataIndex : 'batchNo'
								}
																																																,{
																	header : 'amount',	
																	dataIndex : 'amount'
								}
																																																,{
																	header : 'transferName',	
																	dataIndex : 'transferName'
								}
																																																,{
																	header : 'remark',	
																	dataIndex : 'remark'
								}
																																																,{
																	header : 'loanOutFlag',	
																	dataIndex : 'loanOutFlag'
								}
																																																,{
																	header : 'loanInFlag',	
																	dataIndex : 'loanInFlag'
								}
																																																,{
																	header : 'state',	
																	dataIndex : 'state'
								}
																																								, new Ext.ux.grid.RowActions({
									header:'管理',
									width:100,
									actions:[{
											 iconCls:'btn-del',qtip:'删除',style:'margin:0 3px 0 3px'
										},{
											 iconCls:'btn-edit',qtip:'编辑',style:'margin:0 3px 0 3px'
										}
									],
									listeners:{
										scope:this,
										'action':this.onRowAction
									}
								})
					]//end of columns
				});
				
				this.gridPanel.addListener('rowdblclick',this.rowClick);
					
			},// end of the initComponents()
			//重置查询表单
			reset : function(){
				this.searchPanel.getForm().reset();
			},
			//按条件搜索
			search : function() {
				$search({
					searchPanel:this.searchPanel,
					gridPanel:this.gridPanel
				});
			},
			//GridPanel行点击处理事件
			rowClick:function(grid,rowindex, e) {
				grid.getSelectionModel().each(function(rec) {
					new BpBidLoanForm({bidLoanId:rec.data.bidLoanId}).show();
				});
			},
			//创建记录
			createRs : function() {
				new BpBidLoanForm().show();
			},
			//按ID删除记录
			removeRs : function(id) {
				$postDel({
					url:__ctxPath+ '/pay/multiDelBpBidLoan.do',
					ids:id,
					grid:this.gridPanel
				});
			},
			//把选中ID删除
			removeSelRs : function() {
				$delGridRs({
					url:__ctxPath + '/pay/multiDelBpBidLoan.do',
					grid:this.gridPanel,
					idName:'bidLoanId'
				});
			},
			//编辑Rs
			editRs : function(record) {
				new BpBidLoanForm({
					bidLoanId : record.data.bidLoanId
				}).show();
			},
			//行的Action
			onRowAction : function(grid, record, action, row, col) {
				switch (action) {
					case 'btn-del' :
						this.removeRs.call(this,record.data.bidLoanId);
						break;
					case 'btn-edit' :
						this.editRs.call(this,record);
						break;
					default :
						break;
				}
			}
});
