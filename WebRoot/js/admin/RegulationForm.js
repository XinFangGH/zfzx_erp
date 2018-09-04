/**
 * @author
 * @createtime
 * @class RegulationForm
 * @extends Ext.Window
 * @description Regulation表单
 * @company 智维软件
 */
RegulationForm = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		RegulationForm.superclass.constructor.call(this, {
			id : 'RegulationFormWin',
			layout : 'fit',
			items : this.formPanel,
			modal : true,
			iconCls : 'menu-regulation',
			height : 577,
			width : 1000,
			maximizable : true,
			title : '规章制度详细信息',
			buttonAlign : 'center',
			buttons : [{
				text : '草稿',
				iconCls : 'btn-save',
				scope : this,
				handler : this.save.createCallback(0,this.formPanel,this)
			}, {
				text : '生效',
				iconCls : 'btn-save',
				scope : this,
				handler : this.save.createCallback(1,this.formPanel,this)
			}, {
				text : '重置',
				iconCls : 'btn-reset',
				scope : this,
				handler : this.reset
			}, {
				text : '取消',
				iconCls : 'btn-cancel',
				scope : this,
				handler : this.cancel
			}]
		});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		this.formPanel = new Ext.FormPanel({
			layout : 'form',
			bodyStyle : 'padding:10px',
			border : false,
			autoScroll : true,
			// id : 'RegulationForm',
			defaults : {
				anchor : '98%,96%'
			},
			defaultType : 'textfield',
			items : [{
						name : 'regulation.regId',
						xtype : 'hidden',
						value : this.regId == null ? '' : this.regId
					}, {
						fieldLabel : '状态',
						name : 'regulation.status',
						xtype : 'hidden'
					},{
						name : 'regulation.proTypeId',
						xtype : 'hidden'
					},{
						name : 'regulation.issueUserId',
						xtype : 'hidden',
						value : curUserInfo.userId
					},  {
						name : 'regulation.issueDepId',
						xtype : 'hidden',
						value : curUserInfo.depId
					},  {
						name : 'regulation.recDepIds',
						xtype : 'hidden',
						maxLength : 1024
					},  {
						name : 'regulation.recUserIds',
						xtype : 'hidden',
						maxLength : 1024
					},  {
						name : 'regAttachsFileIds',
						id : 'regAttachsFileIds',
						xtype : 'hidden'
					}, {
						xtype : 'compositefield',
						fieldLabel : '制度类型',
						items : [{
									name : 'regulation.proTypeName',
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
											catKey : 'REGULATION',
											isSingle : true,
											callback : function(typeId,typeName) {
												fPanel.getCmpByName('regulation.proTypeId').setValue(typeId);
												fPanel.getCmpByName('regulation.proTypeName').setValue(typeName);
											}
										}).show();
									}

								},{
									xtype : 'displayfield',
									value : '关键字',
									width : 60
								},{
									xtype : 'textfield',
									name : 'regulation.keywords',
									maxLength : 256,
									width : 250
								},{
									xtype : 'displayfield',
									value : '发布日期',
									width : 60
								},{
									fieldLabel : '',
									name : 'regulation.issueDate',
									xtype : 'datefield',
									format : 'Y-m-d',
									value : new Date()
								}]
					}, {
						xtype : 'compositefield',
						fieldLabel : '发布人',
						items : [ {
									value : curUserInfo.fullname,
									width : 250,
									xtype : 'textfield',
									readOnly: true,
									name : 'regulation.issueFullname',
									maxLength : 64
								},{
									xtype : 'button',
									iconCls : 'btn-select',
									text : '选择人员',
									scope : this,
									handler : function(){
										var fPanel = this.formPanel;
										UserSelector.getView(
											function(userIds,fullnames) {
												fPanel.getCmpByName('regulation.issueFullname').setValue(fullnames);
												fPanel.getCmpByName('regulation.issueUserId').setValue(userIds);
											},true).show();
									}
								},{
									xtype : 'displayfield',
									value : '发布部门',
									width : 60
								},{
									xtype : 'textfield',
									name : 'regulation.issueDep',
									maxLength : 64,
									width : 250,
									readOnly: true,
									value : curUserInfo.depName
								},{
									xtype : 'button',
									iconCls : 'btn-select',
									text : '选择部门',
									scope : this,
									handler : function(){
										var fPanel = this.formPanel;
										DepSelector.getView(function(ids, names) {
												fPanel.getCmpByName('regulation.issueDep').setValue(names);
												fPanel.getCmpByName('regulation.issueDepId').setValue(ids);
											}, true).show();
									}
								}]
					}, {
						xtype : 'compositefield',
						fieldLabel : '接收部门范围',
						items : [{
								name : 'regulation.recDeps',
								xtype : 'textarea',
								readOnly: true,
								width : 650,
								maxLength : 1024
							},{
								xtype : 'button',
								text : '选择部门',
								iconCls : 'btn-select',
								scope : this,
								handler : function(){
									var fPanel = this.formPanel;
									DepSelector.getView(function(ids, names) {
											fPanel.getCmpByName('regulation.recDeps').setValue(names);
											fPanel.getCmpByName('regulation.recDepIds').setValue(ids);
										}).show();
								}
							} ]
					}, {
						xtype : 'compositefield',
						fieldLabel : '接收人范围',
						items : [{
									width : 650,
									name : 'regulation.recUsers',
									readOnly: true,
									xtype : 'textarea',
									maxLength : 1024
								},{
									xtype : 'button',
									iconCls : 'btn-select',
									text : '选择人员',
									scope : this,
									handler : function(){
										fPanel = this.formPanel;
										UserSelector.getView(
											function(userIds,fullnames) {
												fPanel.getCmpByName('regulation.recUsers').setValue(fullnames);
												fPanel.getCmpByName('regulation.recUserIds').setValue(userIds);
											}).show();
									}
								}]
					},{
						fieldLabel : '标题',
						name : 'regulation.subject',
						allowBlank : false,
						maxLength : 256
					},  {
						fieldLabel : '内容',
						name : 'content',
						xtype : 'fckeditor',
						maxLength : 65535
					}, {
						xtype : 'textarea',
						hidden : true,
						name : 'regulation.content' 
					}, {
						xtype:'container',
						layout : 'column',
						border:false,
						defaults:{border:false},
						items : [{
									columnWidth : .7,
									layout : 'form',
									border:false,
									items : [{
										fieldLabel : '附件',
										xtype : 'panel',
										name : 'regAttachs',
										frame : false,
										border:true,
										bodyStyle:'padding:4px 4px 4px 4px',
										height : 70,
										autoScroll : true,
										html : ''
									}]
								}, {
									columnWidth : .3,
									items : [{
										border:false,
										xtype : 'button',
										text : '添加附件',
										scope : this,
										iconCls:'menu-attachment',
										handler : function() {
											var fPanel = this.formPanel;
											var dialog = App.createUploadDialog({
												file_cat : 'admin/regulation',
												callback : function(data) {
													var fileIds = fPanel.getCmpByName("regAttachsFileIds");
													var filePanel = fPanel.getCmpByName('regAttachs');
	
													for (var i = 0; i < data.length; i++) {
														if (fileIds.getValue() != '') {
															fileIds.setValue(fileIds.getValue()+ ',');
														}
														fileIds.setValue(fileIds.getValue()+ data[i].fileId);
														Ext.DomHelper.append(filePanel.body,
															'<span><a href="#" onclick="FileAttachDetail.show('
																	+ data[i].fileId
																	+ ')">'
																	+ data[i].fileName
																	+ '</a> <img class="img-delete" src="'
																	+ __ctxPath
																	+ '/images/system/delete.gif" onclick="removeFile(this,'
																	+ data[i].fileId
																	+ ')"/>&nbsp;|&nbsp;</span>');
													}
												}
											});
											dialog.show(this);
										}
									}, {
										xtype : 'button',
										text : '清除附件',
										scope : this,
										iconCls : 'reset',
										handler : function() {
											var fPanel = this.formPanel;
											var fileIds = fPanel.getCmpByName("regAttachsFileIds");
											var filePanel =  fPanel.getCmpByName('regAttachs');
										
											filePanel.body.update('');
											fileIds.setValue('');
										}
									}]
								}]
					}]
		});
		
		// 加载表单对应的数据
		if (this.regId != null && this.regId != 'undefined') {
			var fPanel = this.formPanel;
			this.formPanel.loadData({
				url : __ctxPath + '/admin/getRegulation.do?regId=' + this.regId,
				root : 'data',
				preName : 'regulation',
				success : function(response,options) {
					var regulation=Ext.util.JSON.decode(response.responseText).data;
					//fPanel.get
					var af = regulation.regAttachs;
					var filePanel =  fPanel.getCmpByName('regAttachs');;
					var fileIds = fPanel.getCmpByName("regAttachsFileIds");
					for (var i = 0; i < af.length; i++) {
						if (fileIds.getValue() != '') {
							fileIds.setValue(fileIds.getValue() + ',');
						}
						fileIds.setValue(fileIds.getValue() + af[i].fileId);
						Ext.DomHelper.append(filePanel.body,
							'<span><a href="#" onclick="FileAttachDetail.show('
									+ af[i].fileId
									+ ')">'
									+ af[i].fileName
									+ '</a><img class="img-delete" src="'
									+ __ctxPath
									+ '/images/system/delete.gif" onclick="removeFile(this,'
									+ af[i].fileId
									+ ')"/>&nbsp;|&nbsp;</span>');
					}
				},
				failure : function(response,options) {
					Ext.ux.Toast.msg('编辑', '载入失败');
				}
			});
		}

	},// end of the initcomponents

	/**
	 * 重置
	 * 
	 * @param {}
	 *            formPanel
	 */
	reset : function() {
		this.formPanel.getForm().reset();
	},
	/**
	 * 取消
	 * 
	 * @param {}
	 *            window
	 */
	cancel : function() {
		this.close();
	},
	/**
	 * 保存记录
	 */
	save : function(_status,formPanel,win) {
		formPanel.getCmpByName('regulation.status').setValue(_status);
		formPanel.getCmpByName('regulation.content').setValue(formPanel.getCmpByName('content').getValue());
		$postForm({
			formPanel : formPanel,
			scope : this,
			url : __ctxPath + '/admin/saveRegulation.do',
			callback : function(fp, action) {
				var gridPanel = Ext.getCmp('RegulationGrid');
				if (gridPanel != null) {
					gridPanel.getStore().reload();
				}
				win.close();
			}
		});
	}// end of save

});
function removeFile(obj, fileId) {
			var fileIds = Ext.getCmp("regAttachsFileIds");
			var value = fileIds.getValue();
			if (value.indexOf(',') < 0) {// 仅有一个附件
				fileIds.setValue('');
			} else {
				value = value.replace(',' + fileId, '').replace(fileId + ',', '');
				fileIds.setValue(value);
			}
		
			var el = Ext.get(obj.parentNode);
			el.remove();
		};