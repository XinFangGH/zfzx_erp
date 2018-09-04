/**
 * @author
 * @class PlMmOrderChildrenOrView
 * @extends Ext.Panel
 * @description [PlMmOrderChildrenOr]管理
 * @company 智维软件
 * @createtime:
 */
PlMmOrderChildrenOrView = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				PlMmOrderChildrenOrView.superclass.constructor.call(this, {
							id : 'PlMmOrderChildrenOrView',
							title : '[PlMmOrderChildrenOr]管理',
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
									fieldLabel:'orderId',
									name : 'Q_orderId_L_EQ',
									flex:1,
																		xtype:'numberfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'childrenorId',
									name : 'Q_childrenorId_L_EQ',
									flex:1,
																		xtype:'numberfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'investPersonId',
									name : 'Q_investPersonId_L_EQ',
									flex:1,
																		xtype:'numberfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'investPersonName',
									name : 'Q_investPersonName_S_EQ',
									flex:1,
																		xtype : 'textfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'mmplanId',
									name : 'Q_mmplanId_L_EQ',
									flex:1,
																		xtype:'numberfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'mmName',
									name : 'Q_mmName_S_EQ',
									flex:1,
																		xtype : 'textfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'parentOrBidId',
									name : 'Q_parentOrBidId_L_EQ',
									flex:1,
																		xtype:'numberfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'parentOrBidName',
									name : 'Q_parentOrBidName_S_EQ',
									flex:1,
																		xtype : 'textfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'matchingMoney',
									name : 'Q_matchingMoney_S_EQ',
									flex:1,
																		xtype:'numberfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'childrenOrDayRate',
									name : 'Q_childrenOrDayRate_S_EQ',
									flex:1,
																		xtype:'numberfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'matchingStartDate',
									name : 'Q_matchingStartDate_D_EQ',
									flex:1,
																		xtype:'datefield',
									format:'Y-m-d'
																	}
																,
															 							 																																					 								{
									fieldLabel:'matchingEndDate',
									name : 'Q_matchingEndDate_D_EQ',
									flex:1,
																		xtype:'datefield',
									format:'Y-m-d'
																	}
																,
															 							 																													 								 								{
									fieldLabel:'matchingLimit',
									name : 'Q_matchingLimit_N_EQ',
									flex:1,
																		xtype:'numberfield'
																	}
																,
															 							 																													 								 								{
									fieldLabel:'matchingEndDateType',
									name : 'Q_matchingEndDateType_N_EQ',
									flex:1,
																		xtype:'numberfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'matchingGetMoney',
									name : 'Q_matchingGetMoney_S_EQ',
									flex:1,
																		xtype:'numberfield'
																	}
																,
															 							 																													 								 								{
									fieldLabel:'matchingState',
									name : 'Q_matchingState_N_EQ',
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
									text : '添加[PlMmOrderChildrenOr]',
									xtype : 'button',
									scope : this,
									handler : this.createRs
								}, {
									iconCls : 'btn-del',
									text : '删除[PlMmOrderChildrenOr]',
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
					id:'PlMmOrderChildrenOrGrid',
					url : __ctxPath + "/creditFlow.financingAgency.manageMoney/listPlMmOrderChildrenOr.do",
					fields : [{
									name : 'matchId',
									type : 'int'
								}
																																																	,'orderId'
																																										,'childrenorId'
																																										,'investPersonId'
																																										,'investPersonName'
																																										,'mmplanId'
																																										,'mmName'
																																										,'parentOrBidId'
																																										,'parentOrBidName'
																																										,'matchingMoney'
																																										,'childrenOrDayRate'
																																										,'matchingStartDate'
																																										,'matchingEndDate'
																																										,'matchingLimit'
																																										,'matchingEndDateType'
																																										,'matchingGetMoney'
																																										,'matchingState'
																																			],
					columns:[
								{
									header : 'matchId',
									dataIndex : 'matchId',
									hidden : true
								}
																																																								,{
																	header : 'orderId',	
																	dataIndex : 'orderId'
								}
																																																,{
																	header : 'childrenorId',	
																	dataIndex : 'childrenorId'
								}
																																																,{
																	header : 'investPersonId',	
																	dataIndex : 'investPersonId'
								}
																																																,{
																	header : 'investPersonName',	
																	dataIndex : 'investPersonName'
								}
																																																,{
																	header : 'mmplanId',	
																	dataIndex : 'mmplanId'
								}
																																																,{
																	header : 'mmName',	
																	dataIndex : 'mmName'
								}
																																																,{
																	header : 'parentOrBidId',	
																	dataIndex : 'parentOrBidId'
								}
																																																,{
																	header : 'parentOrBidName',	
																	dataIndex : 'parentOrBidName'
								}
																																																,{
																	header : 'matchingMoney',	
																	dataIndex : 'matchingMoney'
								}
																																																,{
																	header : 'childrenOrDayRate',	
																	dataIndex : 'childrenOrDayRate'
								}
																																																,{
																	header : 'matchingStartDate',	
																	dataIndex : 'matchingStartDate'
								}
																																																,{
																	header : 'matchingEndDate',	
																	dataIndex : 'matchingEndDate'
								}
																																																,{
																	header : 'matchingLimit',	
																	dataIndex : 'matchingLimit'
								}
																																																,{
																	header : 'matchingEndDateType',	
																	dataIndex : 'matchingEndDateType'
								}
																																																,{
																	header : 'matchingGetMoney',	
																	dataIndex : 'matchingGetMoney'
								}
																																																,{
																	header : 'matchingState',	
																	dataIndex : 'matchingState'
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
					new PlMmOrderChildrenOrForm({matchId:rec.data.matchId}).show();
				});
			},
			//创建记录
			createRs : function() {
				new PlMmOrderChildrenOrForm().show();
			},
			//按ID删除记录
			removeRs : function(id) {
				$postDel({
					url:__ctxPath+ '/creditFlow.financingAgency.manageMoney/multiDelPlMmOrderChildrenOr.do',
					ids:id,
					grid:this.gridPanel
				});
			},
			//把选中ID删除
			removeSelRs : function() {
				$delGridRs({
					url:__ctxPath + '/creditFlow.financingAgency.manageMoney/multiDelPlMmOrderChildrenOr.do',
					grid:this.gridPanel,
					idName:'matchId'
				});
			},
			//编辑Rs
			editRs : function(record) {
				new PlMmOrderChildrenOrForm({
					matchId : record.data.matchId
				}).show();
			},
			//行的Action
			onRowAction : function(grid, record, action, row, col) {
				switch (action) {
					case 'btn-del' :
						this.removeRs.call(this,record.data.matchId);
						break;
					case 'btn-edit' :
						this.editRs.call(this,record);
						break;
					default :
						break;
				}
			}
});
