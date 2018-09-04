/**
 * @author
 * @class FlLeaseObjectManagePlaceView
 * @extends Ext.Panel
 * @description [FlLeaseObjectManagePlace]管理
 * @company 智维软件
 * @createtime:
 */
FlLeaseObjectManagePlaceView = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				FlLeaseObjectManagePlaceView.superclass.constructor.call(this, {
							id : 'FlLeaseObjectManagePlaceView',
							title : '[FlLeaseObjectManagePlace]管理',
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
									fieldLabel:'leaseObjectId',
									name : 'Q_leaseObjectId_L_EQ',
									flex:1,
																		xtype:'numberfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'oldPlace',
									name : 'Q_oldPlace_S_EQ',
									flex:1,
																		xtype : 'textfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'destPlace',
									name : 'Q_destPlace_S_EQ',
									flex:1,
																		xtype : 'textfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'moveDate',
									name : 'Q_moveDate_D_EQ',
									flex:1,
																		xtype:'datefield',
									format:'Y-m-d'
																	}
																,
															 							 																																					 								{
									fieldLabel:'operationPersonId',
									name : 'Q_operationPersonId_S_EQ',
									flex:1,
																		xtype : 'textfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'operationDate',
									name : 'Q_operationDate_D_EQ',
									flex:1,
																		xtype:'datefield',
									format:'Y-m-d'
																	}
																,
															 							 																																					 								{
									fieldLabel:'standardSize',
									name : 'Q_standardSize_S_EQ',
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
									text : '添加[FlLeaseObjectManagePlace]',
									xtype : 'button',
									scope : this,
									handler : this.createRs
								}, {
									iconCls : 'btn-del',
									text : '删除[FlLeaseObjectManagePlace]',
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
					id:'FlLeaseObjectManagePlaceGrid',
					url : __ctxPath + "/creditFlow.leaseFinance.leaseobject/listFlLeaseObjectManagePlace.do",
					fields : [{
									name : 'id',
									type : 'int'
								}
																																																	,'leaseObjectId'
																																										,'oldPlace'
																																										,'destPlace'
																																										,'moveDate'
																																										,'operationPersonId'
																																										,'operationDate'
																																										,'standardSize'
																																			],
					columns:[
								{
									header : 'id',
									dataIndex : 'id',
									hidden : true
								}
																																																								,{
																	header : 'leaseObjectId',	
																	dataIndex : 'leaseObjectId'
								}
																																																,{
																	header : 'oldPlace',	
																	dataIndex : 'oldPlace'
								}
																																																,{
																	header : 'destPlace',	
																	dataIndex : 'destPlace'
								}
																																																,{
																	header : 'moveDate',	
																	dataIndex : 'moveDate'
								}
																																																,{
																	header : 'operationPersonId',	
																	dataIndex : 'operationPersonId'
								}
																																																,{
																	header : 'operationDate',	
																	dataIndex : 'operationDate'
								}
																																																,{
																	header : 'standardSize',	
																	dataIndex : 'standardSize'
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
					new FlLeaseObjectManagePlaceForm({id:rec.data.id}).show();
				});
			},
			//创建记录
			createRs : function() {
				new FlLeaseObjectManagePlaceForm().show();
			},
			//按ID删除记录
			removeRs : function(id) {
				$postDel({
					url:__ctxPath+ '/creditFlow.leaseFinance.leaseobject/multiDelFlLeaseObjectManagePlace.do',
					ids:id,
					grid:this.gridPanel
				});
			},
			//把选中ID删除
			removeSelRs : function() {
				$delGridRs({
					url:__ctxPath + '/creditFlow.leaseFinance.leaseobject/multiDelFlLeaseObjectManagePlace.do',
					grid:this.gridPanel,
					idName:'id'
				});
			},
			//编辑Rs
			editRs : function(record) {
				new FlLeaseObjectManagePlaceForm({
					id : record.data.id
				}).show();
			},
			//行的Action
			onRowAction : function(grid, record, action, row, col) {
				switch (action) {
					case 'btn-del' :
						this.removeRs.call(this,record.data.id);
						break;
					case 'btn-edit' :
						this.editRs.call(this,record);
						break;
					default :
						break;
				}
			}
});
