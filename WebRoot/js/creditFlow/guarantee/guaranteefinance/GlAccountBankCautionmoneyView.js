/**
 * @author
 * @class GlAccountBankCautionmoneyView
 * @extends Ext.Panel
 * @description [GlAccountBankCautionmoney]管理
 * @company 智维软件
 * @createtime:
 */
GlAccountBankCautionmoneyView = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				GlAccountBankCautionmoneyView.superclass.constructor.call(this, {
							id : 'GlAccountBankCautionmoneyView',
							title : '[GlAccountBankCautionmoney]管理',
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
									name : 'Q_parentId_S_EQ',
									flex:1,
																		xtype : 'textfield'
																	}
																,
															 							 																													 								 								{
									fieldLabel:'bankBranchId',
									name : 'Q_bankBranchId_N_EQ',
									flex:1,
																		xtype:'numberfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'accountId',
									name : 'Q_accountId_S_EQ',
									flex:1,
																		xtype : 'textfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'text',
									name : 'Q_text_S_EQ',
									flex:1,
																		xtype : 'textfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'createDate',
									name : 'Q_createDate_D_EQ',
									flex:1,
																		xtype:'datefield',
									format:'Y-m-d'
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
									fieldLabel:'leaf',
									name : 'Q_leaf_N_EQ',
									flex:1,
																		xtype:'numberfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'bankBranchName',
									name : 'Q_bankBranchName_S_EQ',
									flex:1,
																		xtype : 'textfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'serviceTypeAccount',
									name : 'Q_serviceTypeAccount_S_EQ',
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
									text : '添加[GlAccountBankCautionmoney]',
									xtype : 'button',
									scope : this,
									handler : this.createRs
								}, {
									iconCls : 'btn-del',
									text : '删除[GlAccountBankCautionmoney]',
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
					id:'GlAccountBankCautionmoneyGrid',
					url : __ctxPath + "/creditFlow.guarantee.guaranteefinance/listGlAccountBankCautionmoney.do",
					fields : [{
									name : 'id',
									type : 'int'
								}
																																																	,'parentId'
																																										,'bankBranchId'
																																										,'accountId'
																																										,'text'
																																										,'createDate'
																																										,'remark'
																																										,'leaf'
																																										,'bankBranchName'
																																										,'serviceTypeAccount'
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
																	header : 'bankBranchId',	
																	dataIndex : 'bankBranchId'
								}
																																																,{
																	header : 'accountId',	
																	dataIndex : 'accountId'
								}
																																																,{
																	header : 'text',	
																	dataIndex : 'text'
								}
																																																,{
																	header : 'createDate',	
																	dataIndex : 'createDate'
								}
																																																,{
																	header : 'remark',	
																	dataIndex : 'remark'
								}
																																																,{
																	header : 'leaf',	
																	dataIndex : 'leaf'
								}
																																																,{
																	header : 'bankBranchName',	
																	dataIndex : 'bankBranchName'
								}
																																																,{
																	header : 'serviceTypeAccount',	
																	dataIndex : 'serviceTypeAccount'
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
					new GlAccountBankCautionmoneyForm({id:rec.data.id}).show();
				});
			},
			//创建记录
			createRs : function() {
				new GlAccountBankCautionmoneyForm().show();
			},
			//按ID删除记录
			removeRs : function(id) {
				$postDel({
					url:__ctxPath+ '/creditFlow.guarantee.guaranteefinance/multiDelGlAccountBankCautionmoney.do',
					ids:id,
					grid:this.gridPanel
				});
			},
			//把选中ID删除
			removeSelRs : function() {
				$delGridRs({
					url:__ctxPath + '/creditFlow.guarantee.guaranteefinance/multiDelGlAccountBankCautionmoney.do',
					grid:this.gridPanel,
					idName:'id'
				});
			},
			//编辑Rs
			editRs : function(record) {
				new GlAccountBankCautionmoneyForm({
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
