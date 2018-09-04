/**
 * @author:
 * @class FormTemplateView
 * @extends Ext.Panel
 * @description [FormTemplate]管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
FormTemplateView = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				FormTemplateView.superclass.constructor.call(this, {
							id : 'FormTemplateView',
							title : '[FormTemplate]管理',
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
									fieldLabel:'映射ID',
									name : 'Q_mappingId_L_EQ',
									flex:1,
																		xtype:'numberfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'节点名称',
									name : 'Q_nodeName_S_EQ',
									flex:1,
																		xtype : 'textfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'模板内容',
									name : 'Q_tempContent_S_EQ',
									flex:1,
																		xtype : 'textfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'',
									name : 'Q_extDef_S_EQ',
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
									text : '添加[FormTemplate]',
									xtype : 'button',
									scope : this,
									handler : this.createRs
								}, {
									iconCls : 'btn-del',
									text : '删除[FormTemplate]',
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
					id:'FormTemplateGrid',
					url : __ctxPath + "/flow/listFormTemplate.do",
					fields : [{
									name : 'templateId',
									type : 'int'
								}
																																																	,'mappingId'
																																										,'nodeName'
																																										,'tempContent'
																																										,'extDef'
																																			],
					columns:[
								{
									header : 'templateId',
									dataIndex : 'templateId',
									hidden : true
								}
																																																								,{
																	header : '映射ID',	
																	dataIndex : 'mappingId'
								}
																																																,{
																	header : '节点名称',	
																	dataIndex : 'nodeName'
								}
																																																,{
																	header : '模板内容',	
																	dataIndex : 'tempContent'
								}
																																																,{
																	header : '',	
																	dataIndex : 'extDef'
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
					new FormTemplateForm({templateId:rec.data.templateId}).show();
				});
			},
			//创建记录
			createRs : function() {
				new FormTemplateForm().show();
			},
			//按ID删除记录
			removeRs : function(id) {
				$postDel({
					url:__ctxPath+ '/flow/multiDelFormTemplate.do',
					ids:id,
					grid:this.gridPanel
				});
			},
			//把选中ID删除
			removeSelRs : function() {
				$delGridRs({
					url:__ctxPath + '/flow/multiDelFormTemplate.do',
					grid:this.gridPanel,
					idName:'templateId'
				});
			},
			//编辑Rs
			editRs : function(record) {
				new FormTemplateForm({
					templateId : record.data.templateId
				}).show();
			},
			//行的Action
			onRowAction : function(grid, record, action, row, col) {
				switch (action) {
					case 'btn-del' :
						this.removeRs.call(this,record.data.templateId);
						break;
					case 'btn-edit' :
						this.editRs.call(this,record);
						break;
					default :
						break;
				}
			}
});
