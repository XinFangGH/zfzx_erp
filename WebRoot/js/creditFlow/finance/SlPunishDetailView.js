/**
 * @author
 * @class SlPunishDetailView
 * @extends Ext.Panel
 * @description [SlPunishDetail]管理
 * @company 智维软件
 * @createtime:
 */
SlPunishDetailView = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				SlPunishDetailView.superclass.constructor.call(this, {
							id : 'SlPunishDetailView',
							title : '[SlPunishDetail]管理',
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
									fieldLabel:'fundQlideId',
									name : 'Q_fundQlideId_L_EQ',
									flex:1,
																		xtype:'numberfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'punishInterestId',
									name : 'Q_punishInterestId_L_EQ',
									flex:1,
																		xtype:'numberfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'operTime',
									name : 'Q_operTime_D_EQ',
									flex:1,
																		xtype:'datefield',
									format:'Y-m-d'
																	}
																,
															 							 																																					 								{
									fieldLabel:'afterMoney',
									name : 'Q_afterMoney_S_EQ',
									flex:1,
																		xtype:'numberfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'factDate',
									name : 'Q_factDate_D_EQ',
									flex:1,
																		xtype:'datefield',
									format:'Y-m-d'
																	}
																,
															 							 																																					 								{
									fieldLabel:'transactionType',
									name : 'Q_transactionType_S_EQ',
									flex:1,
																		xtype : 'textfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'checkuser',
									name : 'Q_checkuser_S_EQ',
									flex:1,
																		xtype : 'textfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'iscancel',
									name : 'Q_iscancel_SN_EQ',
									flex:1,
																		xtype:'numberfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'cancelremark',
									name : 'Q_cancelremark_S_EQ',
									flex:1,
																		xtype : 'textfield'
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
									text : '添加[SlPunishDetail]',
									xtype : 'button',
									scope : this,
									handler : this.createRs
								}, {
									iconCls : 'btn-del',
									text : '删除[SlPunishDetail]',
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
					id:'SlPunishDetailGrid',
					url : __ctxPath + "/creditFlow.finance/listSlPunishDetail.do",
					fields : [{
									name : 'punishDetailId',
									type : 'int'
								}
																																																	,'fundQlideId'
																																										,'punishInterestId'
																																										,'operTime'
																																										,'afterMoney'
																																										,'factDate'
																																										,'transactionType'
																																										,'checkuser'
																																										,'iscancel'
																																										,'cancelremark'
																																			],
					columns:[
								{
									header : 'punishDetailId',
									dataIndex : 'punishDetailId',
									hidden : true
								}
																																																								,{
																	header : 'fundQlideId',	
																	dataIndex : 'fundQlideId'
								}
																																																,{
																	header : 'punishInterestId',	
																	dataIndex : 'punishInterestId'
								}
																																																,{
																	header : 'operTime',	
																	dataIndex : 'operTime'
								}
																																																,{
																	header : 'afterMoney',	
																	dataIndex : 'afterMoney'
								}
																																																,{
																	header : 'factDate',	
																	dataIndex : 'factDate'
								}
																																																,{
																	header : 'transactionType',	
																	dataIndex : 'transactionType'
								}
																																																,{
																	header : 'checkuser',	
																	dataIndex : 'checkuser'
								}
																																																,{
																	header : 'iscancel',	
																	dataIndex : 'iscancel'
								}
																																																,{
																	header : 'cancelremark',	
																	dataIndex : 'cancelremark'
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
					new SlPunishDetailForm({punishDetailId:rec.data.punishDetailId}).show();
				});
			},
			//创建记录
			createRs : function() {
				new SlPunishDetailForm().show();
			},
			//按ID删除记录
			removeRs : function(id) {
				$postDel({
					url:__ctxPath+ '/creditFlow.finance/multiDelSlPunishDetail.do',
					ids:id,
					grid:this.gridPanel
				});
			},
			//把选中ID删除
			removeSelRs : function() {
				$delGridRs({
					url:__ctxPath + '/creditFlow.finance/multiDelSlPunishDetail.do',
					grid:this.gridPanel,
					idName:'punishDetailId'
				});
			},
			//编辑Rs
			editRs : function(record) {
				new SlPunishDetailForm({
					punishDetailId : record.data.punishDetailId
				}).show();
			},
			//行的Action
			onRowAction : function(grid, record, action, row, col) {
				switch (action) {
					case 'btn-del' :
						this.removeRs.call(this,record.data.punishDetailId);
						break;
					case 'btn-edit' :
						this.editRs.call(this,record);
						break;
					default :
						break;
				}
			}
});
