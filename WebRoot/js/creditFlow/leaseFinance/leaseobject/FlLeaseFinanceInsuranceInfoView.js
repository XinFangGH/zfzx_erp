/**
 * @author
 * @class FlLeaseFinanceInsuranceInfoView
 * @extends Ext.Panel
 * @description [FlLeaseFinanceInsuranceInfo]管理
 * @company 智维软件
 * @createtime:
 */
FlLeaseFinanceInsuranceInfoView = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				FlLeaseFinanceInsuranceInfoView.superclass.constructor.call(this, {
							id : 'FlLeaseFinanceInsuranceInfoView',
							title : '[FlLeaseFinanceInsuranceInfo]管理',
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
									fieldLabel:'insuranceName',
									name : 'Q_insuranceName_S_EQ',
									flex:1,
																		xtype : 'textfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'insuranceCompanyName',
									name : 'Q_insuranceCompanyName_S_EQ',
									flex:1,
																		xtype : 'textfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'insuranceCode',
									name : 'Q_insuranceCode_S_EQ',
									flex:1,
																		xtype : 'textfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'insuranceMoney',
									name : 'Q_insuranceMoney_S_EQ',
									flex:1,
																		xtype:'numberfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'startDate',
									name : 'Q_startDate_D_EQ',
									flex:1,
																		xtype:'datefield',
									format:'Y-m-d'
																	}
																,
															 							 																																					 								{
									fieldLabel:'endDate',
									name : 'Q_endDate_D_EQ',
									flex:1,
																		xtype:'datefield',
									format:'Y-m-d'
																	}
																,
															 							 																																					 								{
									fieldLabel:'insurancePerson',
									name : 'Q_insurancePerson_S_EQ',
									flex:1,
																		xtype : 'textfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'insuranceComment',
									name : 'Q_insuranceComment_S_EQ',
									flex:1,
																		xtype : 'textfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'proejctId',
									name : 'Q_proejctId_L_EQ',
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
									text : '添加[FlLeaseFinanceInsuranceInfo]',
									xtype : 'button',
									scope : this,
									handler : this.createRs
								}, {
									iconCls : 'btn-del',
									text : '删除[FlLeaseFinanceInsuranceInfo]',
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
					id:'FlLeaseFinanceInsuranceInfoGrid',
					url : __ctxPath + "/creditFlow.leaseFinance.leaseobject/listFlLeaseFinanceInsuranceInfo.do",
					fields : [{
									name : 'insuranceId',
									type : 'int'
								}
																		],
					columns:[
								{
									header : 'insuranceId',
									dataIndex : 'insuranceId',
									hidden : true
								}
																																																								,{
																	header : 'insuranceName',	
																	dataIndex : 'insuranceName'
								}
																																																,{
																	header : 'insuranceCompanyName',	
																	dataIndex : 'insuranceCompanyName'
								}
																																																,{
																	header : 'insuranceCode',	
																	dataIndex : 'insuranceCode'
								}
																																																,{
																	header : 'insuranceMoney',	
																	dataIndex : 'insuranceMoney'
								}
																																																,{
																	header : 'startDate',	
																	dataIndex : 'startDate'
								}
																																																,{
																	header : 'endDate',	
																	dataIndex : 'endDate'
								}
																																																,{
																	header : 'insurancePerson',	
																	dataIndex : 'insurancePerson'
								}
																																																,{
																	header : 'insuranceComment',	
																	dataIndex : 'insuranceComment'
								}
																																																,{
																	header : 'proejctId',	
																	dataIndex : 'proejctId'
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
					new FlLeaseFinanceInsuranceInfoForm({insuranceId:rec.data.insuranceId}).show();
				});
			},
			//创建记录
			createRs : function() {
				new FlLeaseFinanceInsuranceInfoForm().show();
			},
			//按ID删除记录
			removeRs : function(id) {
				$postDel({
					url:__ctxPath+ '/creditFlow.leaseFinance.leaseobject/multiDelFlLeaseFinanceInsuranceInfo.do',
					ids:id,
					grid:this.gridPanel
				});
			},
			//把选中ID删除
			removeSelRs : function() {
				$delGridRs({
					url:__ctxPath + '/creditFlow.leaseFinance.leaseobject/multiDelFlLeaseFinanceInsuranceInfo.do',
					grid:this.gridPanel,
					idName:'insuranceId'
				});
			},
			//编辑Rs
			editRs : function(record) {
				new FlLeaseFinanceInsuranceInfoForm({
					insuranceId : record.data.insuranceId
				}).show();
			},
			//行的Action
			onRowAction : function(grid, record, action, row, col) {
				switch (action) {
					case 'btn-del' :
						this.removeRs.call(this,record.data.insuranceId);
						break;
					case 'btn-edit' :
						this.editRs.call(this,record);
						break;
					default :
						break;
				}
			}
});
