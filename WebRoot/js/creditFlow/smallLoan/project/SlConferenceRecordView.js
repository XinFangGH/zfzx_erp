/**
 * @author
 * @class SlConferenceRecordView
 * @extends Ext.Panel
 * @description [SlConferenceRecord]管理
 * @company 智维软件
 * @createtime:
 */
SlConferenceRecordView = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				SlConferenceRecordView.superclass.constructor.call(this, {
							id : 'SlConferenceRecordView',
							title : '[SlConferenceRecord]管理',
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
									fieldLabel:'projectId',
									name : 'Q_projectId_L_EQ',
									flex:1,
																		xtype:'numberfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'conforenceTime',
									name : 'Q_conforenceTime_D_EQ',
									flex:1,
																		xtype:'datefield',
									format:'Y-m-d'
																	}
																,
															 							 																																					 								{
									fieldLabel:'conforencePlace',
									name : 'Q_conforencePlace_S_EQ',
									flex:1,
																		xtype : 'textfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'recordPerson',
									name : 'Q_recordPerson_S_EQ',
									flex:1,
																		xtype : 'textfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'jionPerson',
									name : 'Q_jionPerson_S_EQ',
									flex:1,
																		xtype : 'textfield'
																	}
																,
															 							 																													 								 								{
									fieldLabel:'decisionType',
									name : 'Q_decisionType_N_EQ',
									flex:1,
																		xtype:'numberfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'conferenceResult',
									name : 'Q_conferenceResult_S_EQ',
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
									text : '添加[SlConferenceRecord]',
									xtype : 'button',
									scope : this,
									handler : this.createRs
								}, {
									iconCls : 'btn-del',
									text : '删除[SlConferenceRecord]',
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
					id:'SlConferenceRecordGrid',
					url : __ctxPath + "/project/listSlConferenceRecord.do",
					fields : [{
									name : 'conforenceId',
									type : 'int'
								}
																																																	,'projectId'
																																										,'conforenceTime'
																																										,'conforencePlace'
																																										,'recordPerson'
																																										,'jionPerson'
																																										,'decisionType'
																																										,'conferenceResult'
																																			],
					columns:[
								{
									header : 'conforenceId',
									dataIndex : 'conforenceId',
									hidden : true
								}
																																																								,{
																	header : 'projectId',	
																	dataIndex : 'projectId'
								}
																																																,{
																	header : 'conforenceTime',	
																	dataIndex : 'conforenceTime'
								}
																																																,{
																	header : 'conforencePlace',	
																	dataIndex : 'conforencePlace'
								}
																																																,{
																	header : 'recordPerson',	
																	dataIndex : 'recordPerson'
								}
																																																,{
																	header : 'jionPerson',	
																	dataIndex : 'jionPerson'
								}
																																																,{
																	header : 'decisionType',	
																	dataIndex : 'decisionType'
								}
																																																,{
																	header : 'conferenceResult',	
																	dataIndex : 'conferenceResult'
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
					new SlConferenceRecordForm({conforenceId:rec.data.conforenceId}).show();
				});
			},
			//创建记录
			createRs : function() {
				new SlConferenceRecordForm().show();
			},
			//按ID删除记录
			removeRs : function(id) {
				$postDel({
					url:__ctxPath+ '/project/multiDelSlConferenceRecord.do',
					ids:id,
					grid:this.gridPanel
				});
			},
			//把选中ID删除
			removeSelRs : function() {
				$delGridRs({
					url:__ctxPath + '/project/multiDelSlConferenceRecord.do',
					grid:this.gridPanel,
					idName:'conforenceId'
				});
			},
			//编辑Rs
			editRs : function(record) {
				new SlConferenceRecordForm({
					conforenceId : record.data.conforenceId
				}).show();
			},
			//行的Action
			onRowAction : function(grid, record, action, row, col) {
				switch (action) {
					case 'btn-del' :
						this.removeRs.call(this,record.data.conforenceId);
						break;
					case 'btn-edit' :
						this.editRs.call(this,record);
						break;
					default :
						break;
				}
			}
});
