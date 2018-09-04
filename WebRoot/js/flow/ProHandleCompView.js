/**
 * @author:
 * @class ProHandleCompView
 * @extends Ext.Panel
 * @description [ProHandleComp]管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
ProHandleCompView = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				ProHandleCompView.superclass.constructor.call(this, {
							id : 'ProHandleCompView',
							title : '[ProHandleComp]管理',
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
									fieldLabel:'deployId',
									name : 'Q_deployId_S_EQ',
									flex:1,
																		xtype : 'textfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'activityName',
									name : 'Q_activityName_S_EQ',
									flex:1,
																		xtype : 'textfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'tranName',
									name : 'Q_tranName_S_EQ',
									flex:1,
																		xtype : 'textfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'eventName',
									name : 'Q_eventName_S_EQ',
									flex:1,
																		xtype : 'textfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'eventLevel',
									name : 'Q_eventLevel_L_EQ',
									flex:1,
																		xtype:'numberfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'exeCode',
									name : 'Q_exeCode_S_EQ',
									flex:1,
																		xtype : 'textfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'handleType',
									name : 'Q_handleType_L_EQ',
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
									text : '添加[ProHandleComp]',
									xtype : 'button',
									scope : this,
									handler : this.createRs
								}, {
									iconCls : 'btn-del',
									text : '删除[ProHandleComp]',
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
					id:'ProHandleCompGrid',
					url : __ctxPath + "/flow/listProHandleComp.do",
					fields : [{
									name : 'handleId',
									type : 'int'
								}
																																																	,'deployId'
																																										,'activityName'
																																										,'tranName'
																																										,'eventName'
																																										,'eventLevel'
																																										,'exeCode'
																																										,'handleType'
																																			],
					columns:[
								{
									header : 'handleId',
									dataIndex : 'handleId',
									hidden : true
								}
																																																								,{
																	header : 'deployId',	
																	dataIndex : 'deployId'
								}
																																																,{
																	header : 'activityName',	
																	dataIndex : 'activityName'
								}
																																																,{
																	header : 'tranName',	
																	dataIndex : 'tranName'
								}
																																																,{
																	header : 'eventName',	
																	dataIndex : 'eventName'
								}
																																																,{
																	header : 'eventLevel',	
																	dataIndex : 'eventLevel'
								}
																																																,{
																	header : 'exeCode',	
																	dataIndex : 'exeCode'
								}
																																																,{
																	header : 'handleType',	
																	dataIndex : 'handleType'
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
					new ProHandleCompForm({handleId:rec.data.handleId}).show();
				});
			},
			//创建记录
			createRs : function() {
				new ProHandleCompForm().show();
			},
			//按ID删除记录
			removeRs : function(id) {
				$postDel({
					url:__ctxPath+ '/flow/multiDelProHandleComp.do',
					ids:id,
					grid:this.gridPanel
				});
			},
			//把选中ID删除
			removeSelRs : function() {
				$delGridRs({
					url:__ctxPath + '/flow/multiDelProHandleComp.do',
					grid:this.gridPanel,
					idName:'handleId'
				});
			},
			//编辑Rs
			editRs : function(record) {
				new ProHandleCompForm({
					handleId : record.data.handleId
				}).show();
			},
			//行的Action
			onRowAction : function(grid, record, action, row, col) {
				switch (action) {
					case 'btn-del' :
						this.removeRs.call(this,record.data.handleId);
						break;
					case 'btn-edit' :
						this.editRs.call(this,record);
						break;
					default :
						break;
				}
			}
});
