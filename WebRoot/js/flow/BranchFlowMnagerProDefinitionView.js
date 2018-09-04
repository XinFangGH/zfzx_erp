Ext.ns('BranchFlowMnagerProDefinitionView');
/**
 * 流程列表，可管理
 * 
 * @param isManager
 *            是否可管理
 */
var BranchFlowMnagerProDefinitionView = function(isManager) {
	this.isManager = isManager;
};

BranchFlowMnagerProDefinitionView.prototype.setTypeId = function(typeId) {
	this.typeId = typeId;
	BranchFlowMnagerProDefinitionView.typeId = typeId;
};

BranchFlowMnagerProDefinitionView.prototype.setOrgName = function(orgName) {
	this.orgName = orgName;
	BranchFlowMnagerProDefinitionView.orgName = orgName;
};

BranchFlowMnagerProDefinitionView.prototype.getView = function() {
	var formPanel = new Ext.FormPanel({
		id : 'proDefinitionSearchPanelCompany',
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
		id : 'proDefinitionTitlePanelCompany',
		layout:'border',
		region : 'center',
		autoScroll : true,
		items : [formPanel, this.gridPanel]
	});
};

/**
 * 建立视图
 */
BranchFlowMnagerProDefinitionView.prototype.setup = function() {
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
					align:'center',
					dataIndex : 'createtime'
				}, {
					header : '工作流id',
					align:'center',
					dataIndex : 'deployId',
					hidden : 'true'
				},{
					header : '版本号',
					align:'center',
					dataIndex : 'newVersion'
				},{
					header : '状态',
					align:'center',
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
											var gridPanel = Ext.getCmp('ProDefinitionGridCompany' + (isManager ? '' : '0'));
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
					align:'center',
					renderer : function(value, metadata, record, rowIndex,
							colIndex) {
						var defId = record.data.defId;
						var name = record.data.name;
						var deployId = record.data.deployId;
						var drawDefXml=record.data.drawDefXml;
						var str = '';
						if (isManager) {
							if (isGranted('_FlowDel3')) {
								str = '<button title="删除" value=" " class="btn-del" onclick="BranchFlowMnagerProDefinitionView.remove('
										+ defId + ')"></button>';
							}
							if (isGranted('_FlowEdit3') && drawDefXml!=null) {
								str += '&nbsp;<button title="编辑在线" value=" " class="btn-flow-design" onclick="BranchFlowMnagerProDefinitionView.reDesign('+ defId + ')"></button>';
							}
							if(isGranted('_FlowEdit3')){
								str += '&nbsp;<button title="编辑" value=" " class="btn-edit" onclick="BranchFlowMnagerProDefinitionView.edit('
									+ defId + ',\'' + name + '\')"></button>';
							}
						}
						if (deployId != null) {
							str += '&nbsp;<button title="查看" class="btn-preview" onclick="BranchFlowMnagerProDefinitionView.view('
									+ defId + ',\'' + name + '\')"></button>';
							if (isManager) {
								if (isGranted('_FlowSetting3')) {
									str += '&nbsp;<button title="流程设置" class="btn-settinga" onclick="BranchFlowMnagerProDefinitionView.setting('
											+ ''
											+ defId
											+ ',\''
											+ name
											+ '\')"></button>';
								}
							}
							
							/*str += '&nbsp;<button title="新建流程" class="btn-newFlow" onclick="BranchFlowMnagerProDefinitionView.newFlow('
								+ '' + defId + ',\'' + name + '\')"></button>';*/
						}

						/*if(isManager){
							str += '&nbsp;<button title="设置权限" class="btn-shared" onclick="BranchFlowMnagerProDefinitionView.rights('+ defId + ')"></button>';
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
				id : 'ProDefinitionGridCompany' + (isManager ? '' : '0'),
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
								if (isGranted('_FlowEdit3')) {
									BranchFlowMnagerProDefinitionView.reDesign(rec.data.defId);
								}
							}
						});
	});
	return grid;

};

/**
 * 初始化数据
 */
BranchFlowMnagerProDefinitionView.prototype.store = function() {
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
					url : __ctxPath + '/flow/listByBranchCompanyIdProDefinition.do?branchCompanyId=getCompanyIdBySession'
				}),
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCounts',
							id : 'id',
							fields : [{
										name : 'defId',
										type : 'int'
									}, 'proType', 'name', 'description',
									'createtime', 'deployId',{
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
BranchFlowMnagerProDefinitionView.prototype.topbar = function() {

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
			if(BranchFlowMnagerProDefinitionView.orgName==null||BranchFlowMnagerProDefinitionView.orgName==''||BranchFlowMnagerProDefinitionView.orgName=='undefined'){
				Ext.ux.Toast.msg('信息提示', '请选择分公司!');
				return;
			}
			if(BranchFlowMnagerProDefinitionView.typeId==null||BranchFlowMnagerProDefinitionView.typeId==''||BranchFlowMnagerProDefinitionView.typeId=='undefined'){
				Ext.ux.Toast.msg('信息提示', '请选择分公司!');
				return;
			}
			new CreateBusinessFlowView({
				branchCompanyId : BranchFlowMnagerProDefinitionView.typeId,
				orgName : BranchFlowMnagerProDefinitionView.orgName
			}).show();
		}
	}));
	toolbar.add('-');*/
	if (isGranted('_FlowDesign3')) {
		toolbar.add(new Ext.Button({
		iconCls : 'btn-flow-design',
		text : '在线流程设计',
		handler : function() {
			//flowType=normal:发布标准版本流程;flowType=分公司Id:为分公司指派流程;flowType=currentCompany:为当前分公司发布流程(从后台session获取对应companyId)
			window.open(__ctxPath + '/bpm/bpmDesign.do?flowType=currentCompany', '_blank');
		}
	}));
	}

	/*if (isGranted('_FlowAdd3')) {
		toolbar.add('-');
		toolbar.add(new Ext.Button({
					iconCls : 'btn-add',
					text : '发布JBPM4流程',
					handler : function() {
						new ProDefinitionForm(null, BranchFlowMnagerProDefinitionView.typeId);
					}
				}));
	}*/
	if (isGranted('_FlowDel3')) {
		if(isGranted('_FlowAdd3')){
			toolbar.add('-');
		}
		toolbar.add(new Ext.Button({
					iconCls : 'btn-del',
					text : '删除流程',
					handler : function() {

						var grid = Ext.getCmp("ProDefinitionGridCompany");
						var selectRecords = grid.getSelectionModel().getSelections();

						if (selectRecords.length == 0) {
							Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
							return;
						}
						var ids = Array();
						for (var i = 0; i < selectRecords.length; i++) {
							ids.push(selectRecords[i].data.defId);
						}

						BranchFlowMnagerProDefinitionView.remove(ids);
					}
				}));
	}
	return toolbar;
};

/**
 * 删除单个记录
 */
BranchFlowMnagerProDefinitionView.remove = function(id) {
	var grid = Ext.getCmp("ProDefinitionGridCompany");
	Ext.Msg.confirm('信息确认', '注意：删除该流程定义，该流程下的所有相关数据也一并删除，\n并不能恢复，您确认要删除该记录吗？',
		function(btn) {
			if (btn == 'yes') {
				Ext.Ajax.request({
					url : __ctxPath + '/flow/multiDelProDefinition.do',//旧的删除方法，没有删除完全数据，多次发布新流程后版本号会累加。
					//url : __ctxPath + '/flow/delByDefIdsProDefinition.do',
					params : {
						ids : id
					},
					method : 'post',
					success : function() {
						/*var res = Ext.util.JSON.decode(response.responseText);
						Ext.Msg.alert('信息提示', res.msg);
						if (res.success) {
							grid.getStore().reload({
								params : {
									start : 0,
									limit : 25
								}
							});
						}*/
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
BranchFlowMnagerProDefinitionView.prototype.clean = function(){
	Ext.getCmp('proDefinitionSearchPanelCompany').getForm().reset();
};

/**
 * 搜索
 */
BranchFlowMnagerProDefinitionView.prototype.search = function(){
	var formPanel = Ext.getCmp('proDefinitionSearchPanelCompany');
	var gridPanel = Ext.getCmp('ProDefinitionGridCompany' + (this.isManager ? '' : '0'));
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
BranchFlowMnagerProDefinitionView.edit = function(id) {
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
BranchFlowMnagerProDefinitionView.view = function(defId, name) {
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
BranchFlowMnagerProDefinitionView.setting = function(defId, name) {
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
BranchFlowMnagerProDefinitionView.newFlow = function(defId, name) {
	var contentPanel = App.getContentPanel();
	var startForm = contentPanel.getItem('ProcessRunStartCompany' + defId);

	if (!startForm) {
		startForm = new ProcessRunStart({
					id : 'ProcessRunStartCompany' + defId,
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
 BranchFlowMnagerProDefinitionView.rights = function(defId){
 	new ProDefRightsForm({
 		defId : defId
 	}).show();
 };

 
 /**
  * 编辑数据
  * @param {} defId 流程主键id
  */
 BranchFlowMnagerProDefinitionView.reDesign = function(defId){
 	//flowType=isEditFlow：编辑流程的时候不执行更新companyId的代码。
	window.open(__ctxPath + '/bpm/bpmDesign.do?defId=' + defId +'&flowType=isEditFlow', 'flowDesign'+defId);
 };
 
 /**
  * 
  */
 BranchFlowMnagerProDefinitionView.edit = function(defId) {
 	new ProDefinitionForm(defId);
 };
