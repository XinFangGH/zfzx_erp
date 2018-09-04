var ProDefinitionForm = function(defId,typeId) {
	this.defId = defId;
	this.typeId=typeId;
	this.setup();
	var window = new Ext.Window({
		id : 'ProDefinitionFormWin',
		title : '流程定义详细信息',
		iconCls:'menu-flowNew',
		width : 500,
		height : 420,
		modal : true,
		layout : 'anchor',
		plain : true,
		bodyStyle : 'padding:5px;',
		buttonAlign : 'center',
		items : [this.formPanel],
		buttons : [{
			text : '保存并发布',
			iconCls:'btn-save',
			handler : function() {
				var fp = Ext.getCmp('ProDefinitionForm');
				if (fp.getForm().isValid()) {
					fp.getForm().submit({
						method : 'post',
						params:{
							deploy:true
						},
						waitMsg : '正在提交数据...',
						success : function(fp, action) {
							Ext.ux.Toast.msg('操作信息', '成功信息保存！');
							Ext.getCmp('ProDefinitionGrid').getStore().reload();
							window.close();
						},
						failure : function(fp, action) {
							Ext.MessageBox.show({
										title : '操作信息',
										msg : '信息保存出错，请联系管理员！',
										buttons : Ext.MessageBox.OK,
										icon : 'ext-mb-error'
									});
							//window.close();
						}
					});
				}
			}
		}, {
			iconCls:'btn-cancel',
			text : '取消',
			handler : function() {
				window.close();
			}
		}]
	});
	window.show();
};

ProDefinitionForm.prototype.setup = function() {
	var typeId=this.typeId;
	this.formPanel = new Ext.FormPanel({
				url : __ctxPath + '/flow/saveProDefinition.do',
				layout : 'form',
				id : 'ProDefinitionForm',
				frame : true,
				defaults : {
					widht : 400,
					anchor : '96%,96%'
				},
				formId : 'ProDefinitionFormId',
				defaultType : 'textfield',
				items : [{
							name : 'proDefinition.defId',
							xtype : 'hidden',
							value : this.defId == null ? '' : this.defId
						}, {
						xtype : 'compositefield',
						fieldLabel : '流程类型',
						items : [{
									name : 'proDefinition.proTypeName',
									xtype : 'textfield',
									width : 250,
									readOnly : true,
									allowBlank : false
								}, {
									xtype : 'button',
									text : '选择类型',
									iconCls : 'btn-select',
									scope : this,
									handler : function() {
										var fPanel = this.formPanel;
										new GlobalTypeSelector({
													catKey : 'FLOW',
													isSingle : true,
													callback : function(typeId,typeName) {
														fPanel.getCmpByName('proDefinition.proTypeId').setValue(typeId);
														fPanel.getCmpByName('proDefinition.proTypeName').setValue(typeName);
													}
												}).show();
									}

								}]
						}, {
							fieldLabel : '流程状态',
							hiddenName : 'proDefinition.status',
							xtype : 'combo',
							allowBlank : false,
							editable : false,
							mode : 'local',
							triggerAction : 'all',
							store : [['0', '禁用'], ['1', '激活']],
							value : 1
						},{
							xtype : 'hidden',
							id : 'proTypeId',
							name : 'proDefinition.proTypeId'
						}, {
							fieldLabel : '流程的名称',
							name : 'proDefinition.name'
						}, {
							fieldLabel : '描述',
							xtype:'textarea',
							name : 'proDefinition.description'
						}, {
							fieldLabel : '流程定义的XML',
							name : 'proDefinition.defXml',
							xtype:'textarea',
							height:200
						}
				]
			});
			
			var defId=(this.defId==null || this.defId == 'undefined')?'':this.defId;
			var typeId=(this.typeId==null||this.typeId=='undefined')?'':this.typeId;
			//初次加载用于显示流程分类，若为更新的操作，保存新的xml
			this.formPanel.loadData({
				url : __ctxPath + '/flow/getProDefinition.do?defId='+ defId + '&proTypeId='+typeId,
				root : 'data',
				preName:'proDefinition',
				scope:this,
				success : function(response, option) {
					var result=Ext.util.JSON.decode(response.responseText);
					if(result){
						var proType=result.data.proType;
						if(proType!=null){
							this.formPanel.getCmpByName('proDefinition.proTypeName').setValue(proType.typeName);
							this.formPanel.getCmpByName('proDefinition.proTypeId').setValue(proType.proTypeId);
						}
						var deployId = result.data.deployId;
						if(deployId){
							this.formPanel.getCmpByName('proDefinition.name').getEl().dom.readOnly = true;
						}
					}
				}
			});

	return this.formPanel;

};
