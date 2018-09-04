/**
 * @author
 * @class SlSupervisemanageView
 * @extends Ext.Panel
 * @description [SlSupervisemanage]管理
 * @company 智维软件
 * @createtime:
 */
SlSupervisemanageView = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				SlSupervisemanageView.superclass.constructor.call(this, {
							id : 'SlSupervisemanageView',
							title : '[SlSupervisemanage]管理',
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
									fieldLabel:'designSuperviseManagers',
									name : 'Q_designSuperviseManagers_S_EQ',
									flex:1,
																		xtype : 'textfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'designSuperviseManageTime',
									name : 'Q_designSuperviseManageTime_D_EQ',
									flex:1,
																		xtype:'datefield',
									format:'Y-m-d'
																	}
																,
															 							 																																					 								{
									fieldLabel:'designSuperviseManageRemark',
									name : 'Q_designSuperviseManageRemark_S_EQ',
									flex:1,
																		xtype : 'textfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'Designee',
									name : 'Q_Designee_L_EQ',
									flex:1,
																		xtype:'numberfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'SuperviseManager',
									name : 'Q_SuperviseManager_L_EQ',
									flex:1,
																		xtype:'numberfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'SuperviseManageTime',
									name : 'Q_SuperviseManageTime_D_EQ',
									flex:1,
																		xtype:'datefield',
									format:'Y-m-d'
																	}
																,
															 							 																													 								 								{
									fieldLabel:'SuperviseManageMode',
									name : 'Q_SuperviseManageMode_N_EQ',
									flex:1,
																		xtype:'numberfield'
																	}
																,
															 							 																													 								 								{
									fieldLabel:'SuperviseManageOpinion',
									name : 'Q_SuperviseManageOpinion_N_EQ',
									flex:1,
																		xtype:'numberfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'SuperviseManageRemark',
									name : 'Q_SuperviseManageRemark_S_EQ',
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
									text : '添加[SlSupervisemanage]',
									xtype : 'button',
									scope : this,
									handler : this.createRs
								}, {
									iconCls : 'btn-del',
									text : '删除[SlSupervisemanage]',
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
					id:'SlSupervisemanageGrid',
					url : __ctxPath + "/creditFlow.smallLoan.supervise/listSlSupervisemanage.do",
					fields : [{
									name : 'superviseManageId',
									type : 'int'
								}
																																																	,'designSuperviseManagers'
																																										,'designSuperviseManageTime'
																																										,'designSuperviseManageRemark'
																																										,'Designee'
																																										,'SuperviseManager'
																																										,'SuperviseManageTime'
																																										,'SuperviseManageMode'
																																										,'SuperviseManageOpinion'
																																										,'SuperviseManageRemark'
																																			],
					columns:[
								{
									header : 'superviseManageId',
									dataIndex : 'superviseManageId',
									hidden : true
								}
																																																								,{
																	header : 'designSuperviseManagers',	
																	dataIndex : 'designSuperviseManagers'
								}
																																																,{
																	header : 'designSuperviseManageTime',	
																	dataIndex : 'designSuperviseManageTime'
								}
																																																,{
																	header : 'designSuperviseManageRemark',	
																	dataIndex : 'designSuperviseManageRemark'
								}
																																																,{
																	header : 'Designee',	
																	dataIndex : 'Designee'
								}
																																																,{
																	header : 'SuperviseManager',	
																	dataIndex : 'SuperviseManager'
								}
																																																,{
																	header : 'SuperviseManageTime',	
																	dataIndex : 'SuperviseManageTime'
								}
																																																,{
																	header : 'SuperviseManageMode',	
																	dataIndex : 'SuperviseManageMode'
								}
																																																,{
																	header : 'SuperviseManageOpinion',	
																	dataIndex : 'SuperviseManageOpinion'
								}
																																																,{
																	header : 'SuperviseManageRemark',	
																	dataIndex : 'SuperviseManageRemark'
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
					new SlSupervisemanageForm({superviseManageId:rec.data.superviseManageId}).show();
				});
			},
			//创建记录
			createRs : function() {
				new SlSupervisemanageForm().show();
			},
			//按ID删除记录
			removeRs : function(id) {
				$postDel({
					url:__ctxPath+ '/creditFlow.smallLoan.supervise/multiDelSlSupervisemanage.do',
					ids:id,
					grid:this.gridPanel
				});
			},
			//把选中ID删除
			removeSelRs : function() {
				$delGridRs({
					url:__ctxPath + '/creditFlow.smallLoan.supervise/multiDelSlSupervisemanage.do',
					grid:this.gridPanel,
					idName:'superviseManageId'
				});
			},
			//编辑Rs
			editRs : function(record) {
				new SlSupervisemanageForm({
					superviseManageId : record.data.superviseManageId
				}).show();
			},
			//行的Action
			onRowAction : function(grid, record, action, row, col) {
				switch (action) {
					case 'btn-del' :
						this.removeRs.call(this,record.data.superviseManageId);
						break;
					case 'btn-edit' :
						this.editRs.call(this,record);
						break;
					default :
						break;
				}
			}
});
