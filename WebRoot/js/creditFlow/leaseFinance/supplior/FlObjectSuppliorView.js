/**
 * @author
 * @class FlObjectSuppliorView
 * @extends Ext.Panel
 * @description [FlObjectSupplior]管理
 * @company 智维软件
 * @createtime:
 */
FlObjectSuppliorView = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				FlObjectSuppliorView.superclass.constructor.call(this, {
							id : 'FlObjectSuppliorView',
							title : '[FlObjectSupplior]管理',
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
									fieldLabel:'legalPersonName',
									name : 'Q_legalPersonName_S_EQ',
									flex:1,
																		xtype : 'textfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'companyPhoneNum',
									name : 'Q_companyPhoneNum_S_EQ',
									flex:1,
																		xtype : 'textfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'Name',
									name : 'Q_Name_S_EQ',
									flex:1,
																		xtype : 'textfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'connectorName',
									name : 'Q_connectorName_S_EQ',
									flex:1,
																		xtype : 'textfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'connectorPhoneNum',
									name : 'Q_connectorPhoneNum_S_EQ',
									flex:1,
																		xtype : 'textfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'connectorPosition',
									name : 'Q_connectorPosition_S_EQ',
									flex:1,
																		xtype : 'textfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'companyFax',
									name : 'Q_companyFax_S_EQ',
									flex:1,
																		xtype : 'textfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'companyAddress',
									name : 'Q_companyAddress_S_EQ',
									flex:1,
																		xtype : 'textfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'companyComment',
									name : 'Q_companyComment_S_EQ',
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
									text : '添加[FlObjectSupplior]',
									xtype : 'button',
									scope : this,
									handler : this.createRs
								}, {
									iconCls : 'btn-del',
									text : '删除[FlObjectSupplior]',
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
					id:'FlObjectSuppliorGrid',
					url : __ctxPath + "/creditFlow.leaseFinance.supplior/listFlObjectSupplior.do",
					fields : [{
									name : 'id',
									type : 'int'
								}
																																																	,'legalPersonName'
																																										,'companyPhoneNum'
																																										,'Name'
																																										,'connectorName'
																																										,'connectorPhoneNum'
																																										,'connectorPosition'
																																										,'companyFax'
																																										,'companyAddress'
																																										,'companyComment'
																																			],
					columns:[
								{
									header : 'id',
									dataIndex : 'id',
									hidden : true
								}
																																																								,{
																	header : 'legalPersonName',	
																	dataIndex : 'legalPersonName'
								}
																																																,{
																	header : 'companyPhoneNum',	
																	dataIndex : 'companyPhoneNum'
								}
																																																,{
																	header : 'Name',	
																	dataIndex : 'Name'
								}
																																																,{
																	header : 'connectorName',	
																	dataIndex : 'connectorName'
								}
																																																,{
																	header : 'connectorPhoneNum',	
																	dataIndex : 'connectorPhoneNum'
								}
																																																,{
																	header : 'connectorPosition',	
																	dataIndex : 'connectorPosition'
								}
																																																,{
																	header : 'companyFax',	
																	dataIndex : 'companyFax'
								}
																																																,{
																	header : 'companyAddress',	
																	dataIndex : 'companyAddress'
								}
																																																,{
																	header : 'companyComment',	
																	dataIndex : 'companyComment'
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
					new FlObjectSuppliorForm({id:rec.data.id}).show();
				});
			},
			//创建记录
			createRs : function() {
				new FlObjectSuppliorForm().show();
			},
			//按ID删除记录
			removeRs : function(id) {
				$postDel({
					url:__ctxPath+ '/creditFlow.leaseFinance.supplior/multiDelFlObjectSupplior.do',
					ids:id,
					grid:this.gridPanel
				});
			},
			//把选中ID删除
			removeSelRs : function() {
				$delGridRs({
					url:__ctxPath + '/creditFlow.leaseFinance.supplior/multiDelFlObjectSupplior.do',
					grid:this.gridPanel,
					idName:'id'
				});
			},
			//编辑Rs
			editRs : function(record) {
				new FlObjectSuppliorForm({
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
