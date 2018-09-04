/**
 * @author
 * @createtime
 * @class KnowledgeRightsForm
 * @extends Ext.Window
 * @description KnowledgeRights表单
 * @company 智维软件
 */
KnowledgeRightsForm = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		KnowledgeRightsForm.superclass.constructor.call(this, {
					id : 'KnowledgeRightsFormWin',
					layout : 'fit',
					items : this.formPanel,
					modal : true,
					height : 420,
					width : 620,
					maximizable : true,
					title : '知识管理权限控制',
					buttonAlign : 'center',
					buttons : [{
								text : '保存',
								iconCls : 'btn-save',
								scope : this,
								handler : this.save
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
			// id : 'KnowledgeRightsForm',
//			defaults : {
//				anchor : '96%,96%'
//			},
//			defaultType : 'textfield',
			items : [{
				xtype : 'hidden',
				name : 'docPrivilege.privilegeId'
			}, {
				xtype : 'hidden',
				name : 'folderId',
				value : this.folderId
			}, {
				xtype : 'fieldset',
				border : false,
				layout : 'column',
				items : [{
							xtype : 'label',
							text : '选择人员',
							width : 100
						}, {
							xtype : 'hidden',
							name : 'userIds',
							id : 'userIds'
						}, {
							xtype : 'textarea',
							name : 'userNames',
							id : 'userNames',
							width : 300
						}, {
							xtype : 'button',
							text : '选择',
							iconCls : 'btn-select',
							width : 80,
							handler : function() {
								// 显示选择器，并且设置用户
								UserSelector.getView(
										function(uIds, fnames) {
											var sharedUserIds = Ext
													.getCmp('userIds');
											var sharedUserNames = Ext
													.getCmp('userNames');

											if (sharedUserIds.getValue() == '') {// 若原没有值，则直接设置
												sharedUserIds
														.setValue(uIds);
												sharedUserNames
														.setValue(fnames);
												return;
											}
											// 去掉重复的人员
											var vIds = sharedUserIds
													.getValue().split(',');
											var fnms = sharedUserNames
													.getValue().split(',');

											sharedUserIds
													.setValue(uniqueArray(vIds
															.concat(uIds
																	.split(','))));
											sharedUserNames
													.setValue(uniqueArray(fnms
															.concat(fnames
																	.split(','))));

										}).show();
							}
						}, {
							xtype : 'button',
							iconCls : 'btn-clear',
							text : '重置',
							handler : function() {
								var sharedUserIds = Ext.getCmp('userIds');
								var sharedUserNames = Ext
										.getCmp('userNames');

								sharedUserIds.setValue('');
								sharedUserNames.setValue('');
							},
							width : 80
						}]
			}, {
				xtype : 'fieldset',
				border : false,
				layout : 'column',
				items : [{
							xtype : 'label',
							text : '选择部门',
							width : 100
						}, {
							name : 'depIds',
							id : 'depIds',
							xtype : 'hidden'
						}, {
							name : 'depNames',
							id : 'depNames',
							xtype : 'textarea',
							width : 300
						}, {
							xtype : 'button',
							text : '选择',
							iconCls : 'btn-select',
							width : 80,
							handler : function() {
								DepSelector.getView(function(ids, names) {
									var sharedDepIds = Ext.getCmp('depIds');
									var sharedDepNames = Ext
											.getCmp('depNames');

									if (sharedDepIds.getValue() == '') {// 若原没有值，则直接设置
										sharedDepIds.setValue(ids);
										sharedDepNames.setValue(names);
										return;
									}
									// 去掉重复的部门
									var vIds = sharedDepIds.getValue()
											.split(',');
									var fnms = sharedDepNames.getValue()
											.split(',');

									sharedDepIds.setValue(uniqueArray(vIds
											.concat(ids.split(','))));
									sharedDepNames
											.setValue(uniqueArray(fnms
													.concat(names
															.split(','))));

								}).show();
							}
						}, {
							xtype : 'button',
							text : '重置',
							iconCls : 'btn-clear',
							handler : function() {
								var sharedDepIds = Ext.getCmp('depIds');
								var sharedDepNames = Ext.getCmp('depNames');

								sharedDepIds.setValue('');
								sharedDepNames.setValue('');
							},
							width : 80
						}]
			}, {
				xtype : 'fieldset',
				border : false,
				layout : 'column',
				items : [{
							xtype : 'label',
							text : '选择角色',
							width : 100
						}, {
							xtype : 'hidden',
							id : 'roleIds',
							name : 'roleIds'
						}, {
							name : 'roleNames',
							id : 'roleNames',
							xtype : 'textarea',
							width : 300
						}, {
							xtype : 'button',
							text : '选择',
							iconCls : 'btn-select',
							width : 80,
							handler : function() {
								RoleSelector.getView(function(ids, names) {

									var sharedRoleIds = Ext
											.getCmp('roleIds');
									var sharedRoleNames = Ext
											.getCmp('roleNames');

									if (sharedRoleIds.getValue() == '') {// 若原没有值，则直接设置
										sharedRoleIds.setValue(ids);
										sharedRoleNames.setValue(names);
										return;
									}

									// 去掉重复的部门
									var vIds = sharedRoleIds.getValue()
											.split(',');
									var fnms = sharedRoleNames.getValue()
											.split(',');

									sharedRoleIds.setValue(uniqueArray(vIds
											.concat(ids.split(','))));
									sharedRoleNames
											.setValue(uniqueArray(fnms
													.concat(names
															.split(','))));

								}).show();
							}
						}, {
							xtype : 'button',
							text : '重置',
							iconCls : 'btn-clear',
							handler : function() {
								var sharedRoleIds = Ext.getCmp('roleIds');
								var sharedRoleNames = Ext
										.getCmp('roleNames');

								sharedRoleIds.setValue('');
								sharedRoleNames.setValue('');
							},
							width : 80
						}]
			},{
				xtype : 'fieldset',
				border : false,
				layout : 'column',
				items : [{
							xtype : 'label',
							text : '权限选择：',
							width : 100
						}, {
							xtype : 'checkbox',
							name : 'rightR',
							id : 'rightR'
						}, {
							xtype : 'label',
							text : '可读',
							width : 60
						}, {
							xtype : 'checkbox',
							name : 'rightU',
							id : 'rightU',
							listeners:{
							   "check":function(){
							      var rightU=Ext.getCmp('rightU');
							      var rightD=Ext.getCmp('rightD');
							       var rightR=Ext.getCmp('rightR');
							      if(rightU.getValue()){
							         rightR.setValue(true);
							         rightR.disable();
							      }else if(!rightD.getValue()){
							         rightR.enable();
							      }
							   	
							   }
							}
						}, {
							xtype : 'label',
							text : '可修改',
							width : 60
						}, {
							xtype : 'checkbox',
							name : 'rightD',
							id : 'rightD',
							listeners:{
							   "check":function(){
							      var rightD=Ext.getCmp('rightD');
							      var rightU=Ext.getCmp('rightU');
							      var rightR=Ext.getCmp('rightR');
							      if(rightD.getValue()){
							         rightR.setValue(true);
							         rightR.disable();
							      }else if(!rightU.getValue()){
							         rightR.enable();
							      }
							   	
							   }
							}
						}, {
							xtype : 'label',
							text : '可删除',
							width : 60
						}]

			}]
		});
		// 加载表单对应的数据
		if (this.folderId != null && this.folderId != 'undefined') {
			this.formPanel.loadData({
						url : __ctxPath + '/document/getDocPrivilege.do?folderId='
								+ this.folderId,
						root : 'data',
						preName : 'docPrivilege'
					});
		}

		// 加载表单对应的数据
		if (this.docId != null && this.docId != 'undefined') {
			this.formPanel.loadData({
						url : __ctxPath + '/document/getDocPrivilege.do?docId='
								+ this.docId,
						root : 'data',
						preName : 'docPrivilege'
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
	save : function() {
		var win=this;
		var userIds = Ext.getCmp('userIds').getValue();
		var depIds = Ext.getCmp('depIds').getValue();
		var roleIds = Ext.getCmp('roleIds').getValue();
		var rightR = Ext.getCmp('rightR').getValue();
		if (userIds != '' || depIds != '' || roleIds != '') {
			if (rightR == true) {
				var rightRC = Ext.getCmp('rightR');
				rightRC.enable();
				var gridPanel=Ext.getCmp('KnowledgeGrid');
				this.formPanel.getForm().submit({
					url : __ctxPath
							+ '/document/addDocPrivilege.do',
					method : 'post',
					waitMsg : '正在提交...',
					success : function(fp, action) {
						Ext.ux.Toast.msg('提示', '保存成功！');
						gridPanel.getStore().reload();
						win.close();
					},
					failure : function(fp, action) {
						Ext.ux.Toast.msg('出错', '请与管理员联系！');
					}
				});
			} else {
				Ext.ux.Toast.msg('提示', '读权限为基本权限！');
			}
		} else {
			Ext.ux.Toast.msg('提示', '请选择！');
		}
	}// end of save

});