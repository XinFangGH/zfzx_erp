Ext.ns('NormalFlowMnagerProDefinitionView');
/**
 * 流程列表，可管理
 * 
 * @param isManager
 *            是否可管理
 */
var NormalFlowMnagerProDefinitionView = function(isManager) {
	this.isManager = isManager;
};

NormalFlowMnagerProDefinitionView.prototype.setTypeId = function(typeId) {
	this.typeId = typeId;
	NormalFlowMnagerProDefinitionView.typeId = typeId;
};

NormalFlowMnagerProDefinitionView.prototype.setOrgName = function(orgName) {
	this.orgName = orgName;
	NormalFlowMnagerProDefinitionView.orgName = orgName;
};

NormalFlowMnagerProDefinitionView.prototype.getView = function() {
	var formPanel = new Ext.FormPanel({
		id : 'proDefinitionSearchPanelNormal',
		height : 40,
		frame : false,
		border : false,
		region : 'north',
		layout : 'hbox',
		layoutConfig : {
			padding : '5',
			align : 'middle'
		},
		defaults : {
			margins : '4 4 4 4',
			style : 'padding-bottom:5px;'
		},
		keys : {
			key : Ext.EventObject.ENTER,
			fn : this.search,
			scope : this
		},
		items : [{
			text : '流程名称:',
			xtype:'label'
		}, {
			xtype : 'textfield',
			name : 'Q_name_S_LK',
			maxLength : 125,
			width : 160
		}, {
			text : '流程描述:',
			xtype : 'label'
		}, {
			xtype : 'textfield',
			name : 'Q_description_S_LK',
			maxLength : 125
		}, {
			xtype : 'button',
			text : '查询',
			iconCls : 'search',
			scope : this,
			handler : this.search
		}, {
			xtype : 'button',
			text : '重置',
			iconCls : 'btn-reset',
			scope : this,
			handler : this.clean
		}]
	});
	this.gridPanel = this.setup();
	return new Ext.Panel({
		title : '流程列表',
		id : 'proDefinitionTitlePanelNormal',
		layout:'border',
		region : 'center',
		autoScroll : true,
		items : [formPanel, this.gridPanel]
	});
};

/**
 * 建立视图
 */
NormalFlowMnagerProDefinitionView.prototype.setup = function() {
	var isManager = this.isManager;
	var row = 0;
	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel({
		columns : [sm, new Ext.grid.RowNumberer(), {
					header : 'defId',
					dataIndex : 'defId',
					hidden : true
				}, {
					header : '分类名称',
					dataIndex : 'proType',
					renderer : function(value) {
						if (value != null)
							return value.typeName;
						else
							return '<font color=\'red\'>未定义</font>';
					}
				}, {
					header : '流程的名称',
					dataIndex : 'name'
				}, {
					header : '描述',
					dataIndex : 'description'
				}, {
					header : '创建时间',
					dataIndex : 'createtime'
				}, {
					header : '工作流id',
					dataIndex : 'deployId',
					hidden : 'true'
				},{
					header : '版本号',
					dataIndex : 'newVersion'
				},{
					header : '状态',
					dataIndex : 'status',
					renderer : function(value){
						if(value !=null && value ==1){
							return '<font color="green">激活</font>';
						}else{
							return '<font color="red">禁用</font>';
						}
					},
					editor : isManager ? new Ext.form.ComboBox({
						allowBlank : false,
						editable : false,
						mode : 'local',
						triggerAction : 'all',
						store : [['0', '禁用'], ['1', '激活']],
						listeners : {
										'change' : function(field, newValue,oldValue) {
											var gridPanel = Ext.getCmp('ProDefinitionGridNormal' + (isManager ? '' : '0'));
											//var record = gridPanel.getStore().getAt(row);
											var defId = gridPanel.getSelectionModel().getSelected().data['defId'];
											Ext.Ajax.request({
												url : __ctxPath
														+ '/flow/updateProDefinition.do',
												params : {
													'proDefinition.defId' : defId,
													'proDefinition.status' : newValue
												},
												method : 'POST',
												success : function(response, options) {
													Ext.ux.Toast.msg('操作信息', '修改成功！');
												},
												failure : function(response, options) {
													Ext.ux.Toast.msg('操作信息','操作出错，请联系管理员！');
												}
											});
										}
									}
					}):null
				},{
					header : '管理',
					dataIndex : 'defId',
					renderer : function(value, metadata, record, rowIndex,
							colIndex) {
						var defId = record.data.defId;
						var name = record.data.name;
						var deployId = record.data.deployId;
						var drawDefXml=record.data.drawDefXml;
						var str = '';
						if (isManager) {
							if (isGranted('_FlowDel2')) {
								str = '<button title="删除" value=" " class="btn-del" onclick="NormalFlowMnagerProDefinitionView.remove('
										+ defId + ')"></button>';
							}
							if (isGranted('_FlowEdit2') && drawDefXml!=null) {
								str += '&nbsp;<button title="编辑在线" value=" " class="btn-flow-design" onclick=" NormalFlowMnagerProDefinitionView.reDesign('+ defId + ')"></button>';
							}
							if(isGranted('_FlowEdit2')){
								str += '&nbsp;<button title="编辑" value=" " class="btn-edit" onclick="NormalFlowMnagerProDefinitionView.edit('
									+ defId + ',\'' + name + '\')"></button>';
							}
						}
						if (deployId != null) {
							str += '&nbsp;<button title="查看" class="btn-preview" onclick="NormalFlowMnagerProDefinitionView.view('
									+ defId + ',\'' + name + '\')"></button>';
							if (isManager) {
								if (isGranted('_FlowSetting2')) {
									str += '&nbsp;<button title="流程设置" class="btn-setting" onclick="NormalFlowMnagerProDefinitionView.setting('
											+ ''
											+ defId
											+ ',\''
											+ name
											+ '\')"></button>';
								}
							}
							
							/*str += '&nbsp;<button title="新建流程" class="btn-newFlow" onclick="NormalFlowMnagerProDefinitionView.newFlow('
								+ '' + defId + ',\'' + name + '\')"></button>';*/
						}

						/*if(isManager){
							str += '&nbsp;<button title="设置权限" class="btn-shared" onclick="NormalFlowMnagerProDefinitionView.rights('+ defId + ')"></button>';
						}*/
								
						return str;
					}
				}],
		defaults : {
			sortable : true,
			menuDisabled : false
		}
	});

	var tbar = isManager ? this.topbar() : null;
	var store = this.store();
	store.load({
				params : {
					start : 0,
					limit : 25
				}
			});
	var grid = new Ext.grid.EditorGridPanel({
				clicksToEdit:1,
				//layout:'fit',
				region : 'center',
				id : 'ProDefinitionGridNormal' + (isManager ? '' : '0'),
				tbar : tbar,
				store : store,
				trackMouseOver : true,
				disableSelection : false,
				loadMask : true,
				//height : 500,
				cm : cm,
				sm : sm,
				viewConfig : {
					forceFit : true,
					enableRowBody : false,
					showPreview : false
				},
				bbar : new HT.PagingBar({store : store}),
				listeners : {
							scope : this,
							afteredit : function(e) {e.record.commit();},
							//'cellclick' : function(grid,rowIndex,columnIndex,e){},
							rowdblclick : function(grid, rowIndex, e) {
								//this.seeMortgageInfo();
							}
						} 
			});

	grid.addListener('rowdblclick', function(grid, rowindex, e) {
				row = rowindex;
				grid.getSelectionModel().each(function(rec) {
							if (isManager) {
								if (isGranted('_FlowEdit2')) {
									NormalFlowMnagerProDefinitionView.reDesign(rec.data.defId);
								}
							}
						});
	});
	return grid;

};

/**
 * 初始化数据
 */
NormalFlowMnagerProDefinitionView.prototype.store = function() {
	var _params = null;
	if(this.isManager){
		_params = {
			typeId : this.typeId == null ? 0 : this.typeId
		};
	}else{
		_params = {
			typeId : this.typeId == null ? 0 : this.typeId,
			'Q_deployId_S_NEQ' : 'null', //只取已发布的
			'Q_status_SN_EQ' : 1 //只取激活
		};
	}
	var store = new Ext.data.Store({
				baseParams : _params,
				proxy : new Ext.data.HttpProxy({
					//0:标准流程;-1:非标准版流程;否则是查询分公司对应流程。
					url : __ctxPath + '/flow/listByBranchCompanyIdProDefinition.do?branchCompanyId=0'
				}),
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCounts',
							id : 'id',
							fields : [{
										name : 'defId',
										type : 'int'
									}, 'proType', 'name', 'description',
									'createtime', 'deployId','processName',{
										name : 'newVersion',
										type : 'int'
									},'status','drawDefXml']
						}),
				remoteSort : true
			});
	store.setDefaultSort('defId', 'desc');
	return store;
};

/**
 * 建立操作的Toolbar
 */
NormalFlowMnagerProDefinitionView.prototype.topbar = function() {

	var toolbar = new Ext.Toolbar({
				height : 30,
				//bodyStyle : 'text-align:left',
				items : []
			});
	
	/*toolbar.add(new Ext.Button({
		iconCls : 'btn-flow-design',
		text : '创建业务流程',
		hidden : this.isHiddenCreateBusFlowButton||this.isHiddenCreateBusFlowButton=="undefined"?true:false,
		handler : function() {
			if(NormalFlowMnagerProDefinitionView.orgName==null||NormalFlowMnagerProDefinitionView.orgName==''||NormalFlowMnagerProDefinitionView.orgName=='undefined'){
				Ext.ux.Toast.msg('信息提示', '请选择分公司!');
				return;
			}
			if(NormalFlowMnagerProDefinitionView.typeId==null||NormalFlowMnagerProDefinitionView.typeId==''||NormalFlowMnagerProDefinitionView.typeId=='undefined'){
				Ext.ux.Toast.msg('信息提示', '请选择分公司!');
				return;
			}
			new CreateBusinessFlowView({
				branchCompanyId : NormalFlowMnagerProDefinitionView.typeId,
				orgName : NormalFlowMnagerProDefinitionView.orgName
			}).show();
		}
	}));*/
	if (isGranted('_FlowDesign2')) {
		toolbar.add(new Ext.Button({
			iconCls : 'btn-flow-design',
			text : '在线流程设计',
			handler : function() {
				//flowType=normal:发布标准版本流程;flowType=分公司Id:为分公司指派流程;flowType=currentCompany:为当前分公司发布流程(从后台session获取对应companyId)
				window.open(__ctxPath + '/bpm/bpmDesign.do?flowType=normal', '_blank');
			}
		}));
	}

	if (isGranted('_FlowUpdateBranchCompany')) {
		if(isGranted('_FlowUpdateBranchCompany')){
			toolbar.add('-');
		}
		toolbar.add(new Ext.Button({
					iconCls : 'btn-edit',
					text : '更新分公司流程',
					handler : function() {
						var grid = Ext.getCmp("ProDefinitionGridNormal");
						var selectRecords = grid.getSelectionModel().getSelections();

						if (selectRecords.length == 0) {
							Ext.ux.Toast.msg("提示信息", "请选择要更新的流程！");
							return;
						}
						if (selectRecords.length > 1) {
							Ext.ux.Toast.msg("提示信息", "只能选择一个流程！");
							return;
						}
						/*var ids = Array();
						for (var i = 0; i < selectRecords.length; i++) {
							ids.push(selectRecords[i].data.defId);
						}*/
						var processName = selectRecords[0].data.processName;
						var flowName = selectRecords[0].data.name;
						var defId = selectRecords[0].data.defId;
						new UpdateBranchCompanyFlowView({
							processName : processName,
							flowName : flowName,
							defId : defId
						}).show();
					}
				}));
	}
	
	/*if (isGranted('_FlowAdd2')) {
		toolbar.add('-');
		toolbar.add(new Ext.Button({
					iconCls : 'btn-add',
					text : '发布JBPM4流程',
					handler : function() {
						new ProDefinitionForm(null, NormalFlowMnagerProDefinitionView.typeId);
					}
				}));
	}*/
	if (isGranted('_FlowDel2')) {
		if(isGranted('_FlowDesign2')){
			toolbar.add('-');
		}
		toolbar.add(new Ext.Button({
					iconCls : 'btn-del',
					text : '删除流程',
					handler : function() {

						var grid = Ext.getCmp("ProDefinitionGridNormal");
						var selectRecords = grid.getSelectionModel().getSelections();

						if (selectRecords.length == 0) {
							Ext.ux.Toast.msg("提示信息", "请选择要删除的记录！");
							return;
						}
						var ids = Array();
						for (var i = 0; i < selectRecords.length; i++) {
							ids.push(selectRecords[i].data.defId);
						}

						NormalFlowMnagerProDefinitionView.remove(ids);
					}
				}));
	}
	return toolbar;
};

/**
 * 删除单个记录
 */
NormalFlowMnagerProDefinitionView.remove = function(id) {
	var grid = Ext.getCmp("ProDefinitionGridNormal");
	Ext.Msg.confirm('信息确认', '注意：删除该流程定义，该流程下的所有相关数据也一并删除，\n并不能恢复，您确认要删除该记录吗？',
			function(btn) {
				if (btn == 'yes') {
					Ext.Ajax.request({
								url : __ctxPath
										+ '/flow/multiDelProDefinition.do',
								params : {
									ids : id
								},
								method : 'post',
								success : function() {
									Ext.ux.Toast.msg("信息提示", "成功删除所选记录！");
									grid.getStore().reload({
												params : {
													start : 0,
													limit : 25
												}
											});
								}
							});
				}
			});
};

/**
 * 清空
 */
NormalFlowMnagerProDefinitionView.prototype.clean = function(){
	Ext.getCmp('proDefinitionSearchPanelNormal').getForm().reset();
};

/**
 * 搜索
 */
NormalFlowMnagerProDefinitionView.prototype.search = function(){
	var formPanel = Ext.getCmp('proDefinitionSearchPanelNormal');
	var gridPanel = Ext.getCmp('ProDefinitionGridNormal' + (this.isManager ? '' : '0'));
	if (formPanel.getForm().isValid()) {
		$search({
			searchPanel : formPanel,
			gridPanel : gridPanel
		});
	}
};

/**
 * 
 */
NormalFlowMnagerProDefinitionView.edit = function(id) {
	new ProDefinitionForm(id);
};
/**
 * 流程信息查看
 * 
 * @param {}
 *            id
 * @param {}
 *            name
 * @param {}
 *            deployId
 */
NormalFlowMnagerProDefinitionView.view = function(defId, name) {
	var contentPanel = App.getContentPanel();
	var detail = contentPanel.getItem('ProDefinitionDetail' + defId);

	if (detail == null) {
		detail = new ProDefinitionDetail(defId, name);
		contentPanel.add(detail);
	}
	contentPanel.activate(detail);
};
/**
 * 流程人员设置
 * 
 * @param {}
 *            id
 */
NormalFlowMnagerProDefinitionView.setting = function(defId, name) {
	var contentPanel = App.getContentPanel();
	var settingView = contentPanel.getItem('ProDefinitionSetting' + defId);
	if (!settingView) {
		settingView = new ProDefinitionSetting({defId:defId,name:name});
		contentPanel.add(settingView);
	}
	contentPanel.activate(settingView);
};

/**
 * 新建流程
 * 
 * @param {}
 *            defId
 * @param {}
 *            name
 */
NormalFlowMnagerProDefinitionView.newFlow = function(defId, name) {
	var contentPanel = App.getContentPanel();
	var startForm = contentPanel.getItem('ProcessRunStartNormal' + defId);

	if (!startForm) {
		startForm = new ProcessRunStart({
					id : 'ProcessRunStartNormal' + defId,
					defId : defId,
					flowName : name
				});
		contentPanel.add(startForm);
	}
	contentPanel.activate(startForm);
};
/**
 * Pro设置流程权限
 */
 NormalFlowMnagerProDefinitionView.rights = function(defId){
 	new ProDefRightsForm({
 		defId : defId
 	}).show();
 };

 
 /**
  * 编辑数据
  * @param {} defId 流程主键id
  */
 NormalFlowMnagerProDefinitionView.reDesign = function(defId){
 	//flowType=isEditFlow：编辑流程的时候不执行更新companyId的代码。
	window.open(__ctxPath + '/bpm/bpmDesign.do?defId=' + defId + '&flowType=isEditFlow', 'flowDesign'+defId);
	
 };
 
 /**
  * 
  */
 NormalFlowMnagerProDefinitionView.edit = function(defId) {
 	new ProDefinitionForm(defId);
 };
