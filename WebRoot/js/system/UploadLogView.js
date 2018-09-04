/**
 * @author
 * @class UploadLogView
 * @extends Ext.Panel
 * @description [UploadLog]管理
 * @company 智维软件
 * @createtime:
 */
UploadLogView = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				UploadLogView.superclass.constructor.call(this, {
							id : 'UploadLogView',
							title : '合同上传日志',
							region : 'center',
							layout : 'border',
							iconCls:"menu-flowManager",
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
									fieldLabel:'标的id',
									name : 'Q_bidId_S_EQ',
									flex:1,
																		xtype : 'textfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'标的名称',
									name : 'Q_bidName_S_EQ',
									flex:1,
																		xtype : 'textfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'合同上传时间',
									name : 'Q_createDate_D_EQ',
									flex:1,
																		xtype:'datefield',
									format:'Y-m-d'
																	}
																,
															 							 																																					 								{
									fieldLabel:'上传信息',
									name : 'Q_msg_S_EQ',
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
//
//				this.topbar = new Ext.Toolbar({
//						items : [{
//									iconCls : 'btn-add',
//									text : '添加[UploadLog]',
//									xtype : 'button',
//									scope : this,
//									handler : this.createRs
//								}, {
//									iconCls : 'btn-del',
//									text : '删除[UploadLog]',
//									xtype : 'button',
//									scope:this,
//									handler : this.removeSelRs
//								}]
//				});
	
				this.gridPanel=new HT.GridPanel({
					region:'center',
					tbar:this.topbar,
					//使用RowActions
					rowActions:true,
					id:'UploadLogGrid',
					url : __ctxPath + "/system/listUploadLog.do",
					fields : [{
									name : 'logId',
									type : 'int'
								},'bidId', 'bidName', 'createDate', 'msg'],
					columns:[
								{
									header : 'logId',
									dataIndex : 'logId',
									hidden : true
								}
																																																							,{
																 	
																	header : '标的ID',	
																	dataIndex : 'bidId'
								}
																																																,{
																 	
																	header : '标的名称',	
																	dataIndex : 'bidName'
								}
																																																,{
																	header : '创建时间',	
																	align:'center',
																	dataIndex : 'createDate'
								}
																																																,{
																	header : '上传信息',	
																	dataIndex : 'msg'
								}, new Ext.ux.grid.RowActions({
									hidden:true,
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
					new UploadLogForm({logId:rec.data.logId}).show();
				});
			},
			//创建记录
			createRs : function() {
				new UploadLogForm().show();
			},
			//按ID删除记录
			removeRs : function(id) {
				$postDel({
					url:__ctxPath+ '/system/multiDelUploadLog.do',
					ids:id,
					grid:this.gridPanel
				});
			},
			//把选中ID删除
			removeSelRs : function() {
				$delGridRs({
					url:__ctxPath + '/system/multiDelUploadLog.do',
					grid:this.gridPanel,
					idName:'logId'
				});
			},
			//编辑Rs
			editRs : function(record) {
				new UploadLogForm({
					logId : record.data.logId
				}).show();
			},
			//行的Action
			onRowAction : function(grid, record, action, row, col) {
				switch (action) {
					case 'btn-del' :
						this.removeRs.call(this,record.data.logId);
						break;
					case 'btn-edit' :
						this.editRs.call(this,record);
						break;
					default :
						break;
				}
			}
});
