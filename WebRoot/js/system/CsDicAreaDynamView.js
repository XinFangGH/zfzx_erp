/**
 * @author
 * @class CsDicAreaDynamView
 * @extends Ext.Panel
 * @description [CsDicAreaDynam]管理
 * @company 智维软件
 * @createtime:
 */
CsDicAreaDynamView = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				CsDicAreaDynamView.superclass.constructor.call(this, {
							id : 'CsDicAreaDynamView',
							title : '[CsDicAreaDynam]管理',
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
									fieldLabel:'parentId',
									name : 'Q_parentId_N_EQ',
									flex:1,
																		xtype:'numberfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'title',
									name : 'Q_title_S_EQ',
									flex:1,
																		xtype : 'textfield'
																	}
																,
															 							 																													 								 								{
									fieldLabel:'number',
									name : 'Q_number_N_EQ',
									flex:1,
																		xtype:'numberfield'
																	}
																,
															 							 																													 								 								{
									fieldLabel:'leaf',
									name : 'Q_leaf_N_EQ',
									flex:1,
																		xtype:'numberfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'imgUrl',
									name : 'Q_imgUrl_S_EQ',
									flex:1,
																		xtype : 'textfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'lable',
									name : 'Q_lable_S_EQ',
									flex:1,
																		xtype : 'textfield'
																	}
																,
															 							 																													 								 								{
									fieldLabel:'isOld',
									name : 'Q_isOld_N_EQ',
									flex:1,
																		xtype:'numberfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'remarks',
									name : 'Q_remarks_S_EQ',
									flex:1,
																		xtype : 'textfield'
																	}
																,
															 							 																													 								 								{
									fieldLabel:'orderid',
									name : 'Q_orderid_N_EQ',
									flex:1,
																		xtype:'numberfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'remarks1',
									name : 'Q_remarks1_S_EQ',
									flex:1,
																		xtype : 'textfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'remarks2',
									name : 'Q_remarks2_S_EQ',
									flex:1,
																		xtype : 'textfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'remarks3',
									name : 'Q_remarks3_S_EQ',
									flex:1,
																		xtype : 'textfield'
																	}
																,
															 							 																													 								 								{
									fieldLabel:'remarks4',
									name : 'Q_remarks4_N_EQ',
									flex:1,
																		xtype:'numberfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'remarks5',
									name : 'Q_remarks5_S_EQ',
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
									text : '添加[CsDicAreaDynam]',
									xtype : 'button',
									scope : this,
									handler : this.createRs
								}, {
									iconCls : 'btn-del',
									text : '删除[CsDicAreaDynam]',
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
					id:'CsDicAreaDynamGrid',
					url : __ctxPath + "/system/listCsDicAreaDynam.do",
					fields : [{
									name : 'id',
									type : 'int'
								}
																																																	,'parentId'
																																										,'title'
																																										,'number'
																																										,'leaf'
																																										,'imgUrl'
																																										,'lable'
																																										,'isOld'
																																										,'remarks'
																																										,'orderid'
																																										,'remarks1'
																																										,'remarks2'
																																										,'remarks3'
																																										,'remarks4'
																																										,'remarks5'
																																			],
					columns:[
								{
									header : 'id',
									dataIndex : 'id',
									hidden : true
								}
																																																								,{
																	header : 'parentId',	
																	dataIndex : 'parentId'
								}
																																																,{
																	header : 'title',	
																	dataIndex : 'title'
								}
																																																,{
																	header : 'number',	
																	dataIndex : 'number'
								}
																																																,{
																	header : 'leaf',	
																	dataIndex : 'leaf'
								}
																																																,{
																	header : 'imgUrl',	
																	dataIndex : 'imgUrl'
								}
																																																,{
																	header : 'lable',	
																	dataIndex : 'lable'
								}
																																																,{
																	header : 'isOld',	
																	dataIndex : 'isOld'
								}
																																																,{
																	header : 'remarks',	
																	dataIndex : 'remarks'
								}
																																																,{
																	header : 'orderid',	
																	dataIndex : 'orderid'
								}
																																																,{
																	header : 'remarks1',	
																	dataIndex : 'remarks1'
								}
																																																,{
																	header : 'remarks2',	
																	dataIndex : 'remarks2'
								}
																																																,{
																	header : 'remarks3',	
																	dataIndex : 'remarks3'
								}
																																																,{
																	header : 'remarks4',	
																	dataIndex : 'remarks4'
								}
																																																,{
																	header : 'remarks5',	
																	dataIndex : 'remarks5'
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
					new CsDicAreaDynamForm({id:rec.data.id}).show();
				});
			},
			//创建记录
			createRs : function() {
				new CsDicAreaDynamForm().show();
			},
			//按ID删除记录
			removeRs : function(id) {
				$postDel({
					url:__ctxPath+ '/system/multiDelCsDicAreaDynam.do',
					ids:id,
					grid:this.gridPanel
				});
			},
			//把选中ID删除
			removeSelRs : function() {
				$delGridRs({
					url:__ctxPath + '/system/multiDelCsDicAreaDynam.do',
					grid:this.gridPanel,
					idName:'id'
				});
			},
			//编辑Rs
			editRs : function(record) {
				new CsDicAreaDynamForm({
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
