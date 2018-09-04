/**
 * @author
 * @class CsSalesRecordView
 * @extends Ext.Panel
 * @description 电销管理记录管理
 * @company 智维软件
 * @createtime:
 */
CsSalesRecordView = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				CsSalesRecordView.superclass.constructor.call(this, {
							id : 'CsSalesRecordView'+this.personType,
							title : 0==this.personType?'放款端电销记录':'融资端电销记录',
							region : 'center',
							layout : 'border',
							items : [this.searchPanel, this.gridPanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				var isShow = false;
				var rightValue =  isGranted('_see_CsSalesRecord_All_'+this.personType);
				if (RoleType == "control") {
					isShow = true;
				}
				// 初始化搜索条件Panel
				
				this.searchPanel = new Ext.FormPanel({
					layout : 'column',
					region : 'north',
					border : false,
					height : 70,
					anchor : '96%',
					layoutConfig: {
		               align:'middle'
		            },
		             bodyStyle : 'padding:10px 10px 10px 10px',
		            items : [isShow ? {
								columnWidth : 0.2,
								layout : 'form',
								border : false,
								labelWidth : 90,
								labelAlign : 'right',
								items : [{
									xtype : "combo",
									anchor : "100%",
									fieldLabel : '所属分公司',
									hiddenName : "companyId",
									displayField : 'companyName',
									valueField : 'companyId',
									triggerAction : 'all',
									store : new Ext.data.SimpleStore({
										autoLoad : true,
										url : __ctxPath+ '/system/getControlNameOrganization.do',
										fields : ['companyId', 'companyName']
									})
								}]
							}: {
						}, {
				     		columnWidth :isShow ? 0.2 :.25,
							layout : 'form',
							labelWidth : 80,
							labelAlign : 'right',
							border : false,
							items : [{
										fieldLabel:'人员名称',
										name : 'userName',
										xtype : 'textfield'
								}]
				     	},{
				     		columnWidth :isShow ? 0.2 :.25,
							layout : 'form',
							labelWidth : 80,
							labelAlign : 'right',
							border : false,
							items :[{
								fieldLabel:'员工编号',
								name : 'userNumber',
								xtype : 'textfield'
							}]
				     	},{
			     			columnWidth :.18,
							layout : 'form',
							labelWidth : 80,
							labelAlign : 'right',
							border : false,
							items : [{
								fieldLabel:'日期',
								name : 'createDate',
								xtype : 'datefield'
							}]
			     		},{
			     			columnWidth :.07,
							layout : 'form',
							border : false,
							labelWidth :80,
							items :[{
								text : '查询',
								xtype : 'button',
								scope : this,
								style :'margin-left:30px',
								anchor : "90%",
								iconCls : 'btn-search',
								handler : this.search
							}]
			     		},{
			     			columnWidth :.07,
							layout : 'form',
							border : false,
							items :[{
								text : '重置',
								style :'margin-left:30px',
								xtype : 'button',
								scope : this,
								width : 40,
								iconCls : 'btn-reset',
								handler : this.reset
							}]
			     		}]
				});
				/*this.searchPanel=new HT.SearchPanel({
							layout : 'form',
							region : 'north',
							colNums:3,
							items:[{
									fieldLabel:'人员名称',
									name : 'Q_userName_S_EQ',
								    xtype : 'textfield'
								},{
									fieldLabel:'员工编号',
									name : 'Q_userNumber_S_EQ',
									flex:1,
									xtype : 'textfield'
								},{
									fieldLabel:'userGroupName',
									name : 'Q_userGroupName_S_EQ',
									flex:1,
									xtype : 'textfield'
								}
																,
															 							 																													 								 								{
									fieldLabel:'saleCommCount',
									name : 'Q_saleCommCount_N_EQ',
									flex:1,
																		xtype:'numberfield'
																	}
																,
															 							 																																					 								{
									fieldLabel:'saleCommTime',
									name : 'Q_saleCommTime_S_EQ',
									flex:1,
																		xtype : 'textfield'
																	}
																,
															 							 																													 								 								{
									fieldLabel:'faceToseeCount',
									name : 'Q_faceToseeCount_N_EQ',
									flex:1,
																		xtype:'numberfield'
																	}
																,
															 							 																													 								 								{
									fieldLabel:'faceToDealCount',
									name : 'Q_faceToDealCount_N_EQ',
									flex:1,
									xtype:'numberfield'
									},{
									fieldLabel:'日期',
									name : 'Q_createDate_D_EQ',
									flex:1,
									xtype : 'datefield'
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
*/
				this.topbar = new Ext.Toolbar({
						items : [{
									iconCls : 'btn-add',
									text : '添加',
									xtype : 'button',
									scope : this,
									hidden :isGranted('_add_CsSalesRecord'+"_"+this.personType) ? false : true,
									handler : this.createRs
								},{
									iconCls : 'btn-edit',
									text : '编辑',
									xtype : 'button',
									scope : this,
									hidden :isGranted('_edit_CsSalesRecord'+"_"+this.personType) ? false : true,
									handler : this.editRs
								},{
									iconCls : 'btn-detail',
									text : '查看',
									xtype : 'button',
									scope : this,
									hidden :isGranted('_see_CsSalesRecord'+"_"+this.personType) ? false : true,
									handler : this.seeRs
								}, {
									iconCls : 'btn-del',
									text : '删除',
									xtype : 'button',
									scope:this,
									hidden :isGranted('_delete_CsSalesRecord'+"_"+this.personType) ? false : true,
									handler : this.removeSelRs
								}]
				});
	
				this.gridPanel=new HT.GridPanel({
					region:'center',
					tbar:this.topbar,
					//使用RowActions
					//rowActions:true,
					id:'CsSalesRecordGrid',
					url : __ctxPath + "/customer/salesRecord/listCsSalesRecord.do?personType="+this.personType+"&isAll="+rightValue,
					fields : [{
									name : 'saleId',
									type : 'int'
								}
								,'userId','userName','userNumber','userGroupId','userGroupName','companyName',
								'saleCommCount','saleCommTime','faceToseeCount','faceToDealCount','saleRemark','createDate','companyId'],
					columns:[
								{
									header : 'saleId',
									dataIndex : 'saleId',
									hidden : true
								},{
									header : 'userId',	
									dataIndex : 'userId',
									hidden : true
								},{
									header : "所属分公司",
									sortable : true,
									width : 120,
									hidden:RoleType=="control"?false:true,
									dataIndex : 'companyName'
								},{
									header : '人员名称',	
									dataIndex : 'userName'
								},{
									header : '员工编号',	
									dataIndex : 'userNumber'
								},{
									header : '部门名称',	
									dataIndex : 'userGroupName'
								},{
									header : '有效呼出次数',	
									dataIndex : 'saleCommCount'
								},{
									header : '有效呼出时长',	
									dataIndex : 'saleCommTime'
								},{
									header : '当日拜访未成交数',	
									dataIndex : 'faceToseeCount'
								},{
									header : '当日拜访成交数',	
									dataIndex : 'faceToDealCount'
								},{
									header : '日期',	
									dataIndex : 'createDate'
								}
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
					new CsSalesRecordForm({saleId:rec.data.saleId}).show();
				});
			},
			//创建记录
			createRs : function() {
				new CsSalesRecordForm({
					personType:this.personType
				}).show();
			},
			//按ID删除记录
			removeRs : function(id) {
				$postDel({
					url:__ctxPath+ '/customer/salesRecord/multiDelCsSalesRecord.do',
					ids:id,
					grid:this.gridPanel
				});
			},
			//把选中ID删除
			removeSelRs : function() {
				$delGridRs({
					url:__ctxPath + '/customer/salesRecord/multiDelCsSalesRecord.do',
					grid:this.gridPanel,
					idName:'saleId'
				});
			},
			//编辑Rs
			editRs : function() {
					var selRs = this.gridPanel.getSelectionModel().getSelections();
					if (selRs.length == 0) {
						Ext.ux.Toast.msg('操作信息', '请选择一条记录！');
						return;
					}
					if (selRs.length > 1) {
						Ext.ux.Toast.msg('操作信息', '只能选择一条记录！');
						return;
					}else{
						var record = this.gridPanel.getSelectionModel().getSelected();
							new CsSalesRecordForm({
								saleId : record.data.saleId
							}).show();
					}
			},
			//查看Rs
			seeRs : function() {
				var selRs = this.gridPanel.getSelectionModel().getSelections();
					if (selRs.length == 0) {
						Ext.ux.Toast.msg('操作信息', '请选择一条记录！');
						return;
					}
					if (selRs.length > 1) {
						Ext.ux.Toast.msg('操作信息', '只能选择一条记录！');
						return;
					}else{
						var record = this.gridPanel.getSelectionModel().getSelected();
							new CsSalesRecordForm({
								saleId : record.data.saleId,
								isAllReadOnly:true,
					isHidden:true
							}).show();
					}
			}
});
