PlArchivesMaterialManager = Ext.extend(Ext.Panel, {
	constructor : function(conf) {
		Ext.applyIf(this, conf);
		this.initUIComponents();
		PlArchivesMaterialManager.superclass.constructor.call(this, {
					id:'PlArchivesMaterialManager',
					title : '归档材料管理',
					iconCls:"btn-tree-team36",
					layout : 'border',
					items : [this.searchPanel, this.gridPanel]
				});
	},
	initUIComponents : function() {
				
		this.searchPanel = new HT.SearchPanel({
						layout : 'column',
						style : 'padding-left:5px;padding-right:5px;padding-top:5px;',
						region : 'north',
						height : 20,
						anchor : '96%',
						layoutConfig: {
			               align:'middle',
			               padding : '5px'
			               
			            },
						items : [{   
								columnWidth : 0.4,
								layout : 'form',
								border : false,
								labelWidth : 100,
								labelAlign : 'right',
								items : [ {
								xtype : 'combo',
								anchor : '100%',
								triggerClass : 'x-form-search-trigger',
								fieldLabel : "项目名称",
								name : "projectName",
								editable:false,
								scope : this,
								onTriggerClick : function() {
									var obj=this;
									var op=this.ownerCt.ownerCt;
									op.getCmpByName('projectId').setValue("");
									op.getCmpByName('businessType').setValue("");
									new ProjectListWin({
										fromPage:'GeneralizedFlowsheet',
										businessClass:"AllProject",
										callback : function(projectId, projectName,businessType, record) {
											obj.setValue(projectName);
											obj.nextSibling().setValue(projectId);
											obj.nextSibling().nextSibling().setValue(businessType);
										
										}
									}).show();
																			
								},
								resizable : true,
								mode : 'romote',
								lazyInit : false,
								typeAhead : true,
								minChars : 1,
						
								triggerAction : 'all'
							
							},{
								xtype : 'hidden',
								name : 'projectId'
							},{
								xtype : 'hidden',
								name : 'businessType'
							},{
								xtype : 'hidden',
								name : 'show',
								value:true
							}] 
								      
							},{
							columnWidth : .08,
							xtype : 'container',
							layout : 'form',
							defaults : {
									xtype : 'button'
							},
							style : 'padding-left:10px;',
							items : [{
										text : '查询',
										scope : this,
										iconCls : 'btn-search',
										handler : this.search
									}]
						},{
							columnWidth : .08,
							xtype : 'container',
							layout : 'form',
							defaults : {
									xtype : 'button'
							},
							style : 'padding-left:10px;',
							items : [{
										text : '重置',
										scope : this,
										iconCls : 'btn-reset',
										handler : this.reset
									}]
						}]
	
					});
		this.datefield = new Ext.form.DateField({
					format : 'Y-m-d',
					readOnly : !this.isAffrimEdit,
					allowBlank : false
				})
		this.recievedatefield = new Ext.form.DateField({
					format : 'Y-m-d',
					readOnly : !(!this.isHiddenAffrim && this.affrimEditable),
					allowBlank : false
				})
		this.topbar = new Ext.Toolbar({
					items : [{
								iconCls : 'btn-add',
								text : '增加',
								xtype : 'button',
								scope : this,
								handler : this.createRs
							}, '-', {
								iconCls : 'btn-delete',
								text : '删除',
								xtype : 'button',
								scope : this,
								handler : this.updateRs
							}]
				});
		var checkColumn = new Ext.grid.CheckColumn({
					header : '是否已提交',
					dataIndex : 'isPigeonhole',
					editable :false,
					width : 50
				});
		var recievecheckColumn = new Ext.grid.CheckColumn({
					header : '是否已收到',
					dataIndex : 'isReceive',
					editable :false,
					width : 50
				});
		this.store = new Ext.data.JsonStore({
					baseParams:{
						start:0,
						limit:25
					},
					url : __ctxPath + "/creditFlow/archives/listPlArchivesMaterials.do",
					root : 'result',
					totalProperty : 'totalCounts',
					fields : ['proMaterialsId', 'projId', 'materialsId',
							  'materialsName', 'isReceive', 'isShow', 'datumNums',
					          'isPigeonhole', 'remark', 'archiveConfirmRemark', 'xxnums',
					          'pigeonholeTime', 'recieveTime', 'materialsType']
				});
		this.store.load();
		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:false});
		var cm = new Ext.grid.ColumnModel({
					columns : [sm,new Ext.grid.RowNumberer(),{
						header : 'proMaterialsId',
						dataIndex : 'proMaterialsId',
						hidden : true
					}, {
						header : '材料名称',
						width : 250,
						dataIndex : 'materialsName'
					}, {
						header : '是否必备',
						align:'center',
						dataIndex : 'materialsType',
						width : 150,
						//hidden : this.isHiddenType,
						renderer : function(value) {
							if (value == 1) {
								return '必备'
							} else if (value == 2) {
								return '可选'
							} else {
								return ''
							}
						}
					}, {
						header : '线下份数',
						width : 60,
						align:'center',
						dataIndex : 'xxnums',
						editor : {
							xtype : 'numberfield'
						}
					}, {
						header : '线上份数',
						fixed : true,
						width : 60,
						align:'center',
						dataIndex : 'datumNums',
						//hidden : this.isHidden_materialsxs,
						renderer : function(v) {
							return "<font color=red>" + v + "</font>"
						}

					}, {
						header : '下载',
						fixed : true,
						width : 44,
						align:'center',
						dataIndex : 'uploadOrDown',
						hidden : this.isHidden_materials,
						renderer : function() {
							return "<img src='" + __ctxPath+ "/images/download-start.png'/>";
						}
					}, {
						header : '预览',
						fixed : true,
						width : 60,
						dataIndex : 'viewPic',
						//hidden : this.isHidden_materialsyl,
						renderer : this.imageView
					}, checkColumn, {
						header : '提交时间',
						align:'center',
						dataIndex : 'pigeonholeTime',
						//hidden : this.isHiddenRecive,
						//editor : this.datefield,
						renderer : ZW.ux.dateRenderer(this.datefield)
					}, {
						header : '提交备注',
						align : "center",
						dataIndex : 'archiveConfirmRemark'
					}, recievecheckColumn, {
						header : '收到时间',
						align:'center',
						dataIndex : 'recieveTime',
						renderer : ZW.ux.dateRenderer(this.recievedatefield)
					}, {
						header : '备注',
						align : "center",
						dataIndex : 'remark'
					}],
					defaults : {
						sortable : false,
						menuDisabled : true,
						width : 90
					}
					
				});
		
		this.gridPanel = new Ext.grid.GridPanel({
					id:'PlArchivesMaterialGrid',
					region : 'center',
					plugins : [checkColumn, recievecheckColumn],
					store : this.store,
					shim : true,
					trackMouseOver : true,
					loadMask : true,
					cm : cm,
					//bbar : new HT.PagingBar({store : this.store}),
					scope : this,
					viewConfig:{
					 forceFit: true// 注意不要用autoFill:true,那样设置的话当GridPanel的大小变化（比如你resize了它）时不会自动调整column的宽度
					 //scrollOffset: 0 //不加这个的话，会在grid的最右边有个空白，留作滚动条的位置
					  },
					listeners : {
						'cellclick' : function(grid, rowIndex, columnIndex, e) {
							var record = grid.getStore().getAt(rowIndex); //Get the Record
							var fieldName = grid.getColumnModel().getDataIndex(columnIndex); //Get field name
		
							var loadData = function(size) {
								var oldSize = grid.getStore().getAt(rowIndex).get("datumNums");
								grid.getStore().getAt(rowIndex).set("datumNums", size);
								var e1 = {
									record : grid.getStore().getAt(rowIndex),
									field : 'datumNums',
									'value' : size,
									'originalValue' : oldSize
								}
								grid.fireEvent("afteredit", e1);
								return false;
							}
							if ("uploadOrDown" == fieldName) {
								/*if (this.ownerCt.isHidden_materials)
									return false;*/
								loadData = function(){};//不需要执行该方法
								var markId = grid.getStore().getAt(rowIndex).get("proMaterialsId");
								var talbeName = "sl_procredit_materials.";
								var mark = talbeName + markId;
								uploadfile("下载归档材料", mark, 15, null, null, loadData);
							}
							if ("viewPic" == fieldName) {
								var markId = grid.getStore().getAt(rowIndex)
										.get("proMaterialsId");
								var talbeName = "sl_procredit_materials.";
								var mark = talbeName + markId;
								picViewer(mark, this.ownerCt.isHiddenEdit);
							}
						}
					}
				});
		
		
			
	},//end of initUIComponents
		// 重置查询表单
	reset : function() {
		this.searchPanel.getForm().reset();
	},

	// 查询条件
	search : function() {
		$search({
					searchPanel : this.searchPanel,
					gridPanel : this.gridPanel
				});
	},
	refresh : function() {
		this.store.reload();
	},
	//导出到Excel
	toExcel:function(){
		var userId=this.searchPanel.getCmpByName('userId').getValue();
		var taskName=this.searchPanel.getCmpByName('taskName').getValue();
		var projectName=this.searchPanel.getCmpByName('projectName').getValue();
		
		var status=this.searchPanel.getCmpByName('status').getValue();
		var taskstatus=this.searchPanel.getCmpByName('taskstatus').getValue();
		var stateDate=this.searchPanel.getCmpByName('stateDate').getValue();
		var endDate=this.searchPanel.getCmpByName('endDate').getValue();
		
		var finishStartDate=this.searchPanel.getCmpByName('finishStartDate').getValue();
		var finishEndDate=this.searchPanel.getCmpByName('finishEndDate').getValue();
		
		window.open(__ctxPath + '/flow/allExportExcelTask.do?taskName='+taskName+"&userId="+userId+"&projectName="+projectName+"&taskstatus="+taskstatus+"&stateDate="+stateDate+"&endDate="+endDate+"&finishStartDate="+finishStartDate+"&finishEndDate="+finishEndDate+"&status="+status,'_blank');
		
	},
	imageView :  function(){
			  return "<img src='"+__ctxPath+"/images/btn/read_document.png'>";
			}

});


