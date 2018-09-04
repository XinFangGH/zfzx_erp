/**
 * 文档共享表单
 * 
 */
 
DocumentSharedForm=Ext.extend(Ext.Window,{
    formPanel:null,
	constructor:function(_cfg){
	    Ext.applyIf(this,_cfg);
	    this.initUI();
	    DocumentSharedForm.superclass.constructor.call(this,{
	            title : '文档授权信息',
				width : 545,
				iconCls : 'menu-mail_folder',
				height : 380,
				modal : true,
				layout : 'fit',
				plain : true,
				scope : this,
				minWidth:544,
				buttonAlign : 'center',
				items : this.formPanel,
				buttons:this.buttons
	    
	    });
	},
	initUI:function(){
	    this.formPanel=new Ext.FormPanel({
			id : 'documentSharedForm',
			bodyStyle : 'padding:4px 10px 4px 10px',
			items : [{
						xtype : 'hidden',
						name : 'docId',
						value : this.docId
					}, {
						xtype : 'fieldset',
						border : false,
						layout : 'column',
						items : [{
									xtype : 'label',
									text : '共享人员',
									width : 100
								}, {
									xtype : 'hidden',
									name : 'sharedUserIds',
									id : 'sharedUserIds'
								}, {
									xtype : 'textarea',
									name : 'sharedUserNames',
									id : 'sharedUserNames',
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
															.getCmp('sharedUserIds');
													var sharedUserNames = Ext
															.getCmp('sharedUserNames');
	
													if (sharedUserIds.getValue() == '') {// 若原没有值，则直接设置
														sharedUserIds.setValue(','
																+ uIds + ',');
														sharedUserNames
																.setValue(fnames);
														return;
													} else {
														// 去掉重复的人员
														var vIds = sharedUserIds
																.getValue()
																.split(',');
														var fnms = sharedUserNames
																.getValue()
																.split(',');
														sharedUserIds
																.setValue(uniqueArray(vIds
																		.concat(uIds
																				.split(',')))
																		+ ',');
														sharedUserNames
																.setValue(uniqueArray(fnms
																		.concat(fnames
																				.split(','))));
	
													}
												}).show();
									}
								}, {
									xtype : 'button',
									iconCls : 'btn-clear',
									text : '重置',
									handler : function() {
										var sharedUserIds = Ext
												.getCmp('sharedUserIds');
										var sharedUserNames = Ext
												.getCmp('sharedUserNames');
	
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
									text : '共享部门',
									width : 100
								}, {
									name : 'sharedDepIds',
									id : 'sharedDepIds',
									xtype : 'hidden'
								}, {
									name : 'sharedDepNames',
									id : 'sharedDepNames',
									xtype : 'textarea',
									width : 300
								}, {
									xtype : 'button',
									text : '选择',
									iconCls : 'btn-select',
									width : 80,
									handler : function() {
										DepSelector.getView(function(ids, names) {
											var sharedDepIds = Ext
													.getCmp('sharedDepIds');
											var sharedDepNames = Ext
													.getCmp('sharedDepNames');
	
											if (sharedDepIds.getValue() == '') {// 若原没有值，则直接设置
												sharedDepIds.setValue(',' + ids
														+ ',');
												sharedDepNames.setValue(names);
												return;
											}
											// 去掉重复的部门
											var vIds = sharedDepIds.getValue()
													.split(',');
											var fnms = sharedDepNames.getValue()
													.split(',');
	
											sharedDepIds.setValue(uniqueArray(vIds
													.concat(ids.split(',')))
													+ ',');
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
										var sharedDepIds = Ext
												.getCmp('sharedDepIds');
										var sharedDepNames = Ext
												.getCmp('sharedDepNames');
	
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
									text : '共享角色',
									width : 100
								}, {
									xtype : 'hidden',
									id : 'sharedRoleIds',
									name : 'sharedRoleIds'
								}, {
									name : 'sharedRoleNames',
									id : 'sharedRoleNames',
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
													.getCmp('sharedRoleIds');
											var sharedRoleNames = Ext
													.getCmp('sharedRoleNames');
	
											if (sharedRoleIds.getValue() == '') {// 若原没有值，则直接设置
												sharedRoleIds.setValue(',' + ids
														+ ',');
												sharedRoleNames.setValue(names);
												return;
											}
	
											// 去掉重复的角色
											var vIds = sharedRoleIds.getValue()
													.split(',');
											var fnms = sharedRoleNames.getValue()
													.split(',');
	
											sharedRoleIds.setValue(uniqueArray(vIds
													.concat(ids.split(',')))
													+ ',');
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
										var sharedRoleIds = Ext
												.getCmp('sharedRoleIds');
										var sharedRoleNames = Ext
												.getCmp('sharedRoleNames');
	
										sharedRoleIds.setValue('');
										sharedRoleNames.setValue('');
									},
									width : 80
								}]
					}]
		});
	
		if (this.docId != null && this.docId != '' && this.docId != 'undefined') {
			this.formPanel.getForm().load({
						deferredRender : false,
						url : __ctxPath + '/document/getDocument.do?docId=' + this.docId,
						waitMsg : '正在载入数据...',
						success : function(form, action) {
	
						},
						failure : function(form, action) {
							Ext.MessageBox.show({
										title : '操作信息',
										msg : '载入信息失败，请联系管理员！',
										buttons : Ext.MessageBox.OK,
										icon : 'ext-mb-error'
									});
						}
					});
		}
		
		
		this.buttons=[{
					xtype : 'button',
					text : '共享',
					scope:this,
					iconCls : 'btn-ok',
					handler : function() {
						var win=this;
						this.formPanel.getForm().submit({
									url : __ctxPath
											+ '/document/shareDocument.do',
									method : 'post',
									waitMsg : '正在提交...',
									success : function(fp, action) {
										var view=Ext.getCmp('PrivateDocumentView');
										if(view!=null){
										   view.dataView.getStore().reload();
										}
										win.close();
									}
								});
					}
				},{
				    xtype : 'button',
					text : '取消共享',
					scope:this,
					iconCls : 'btn-cancel',
					handler : function() {
						var win=this;
						this.formPanel.getForm().submit({
									url : __ctxPath
											+ '/document/unshareDocument.do',
									method : 'post',
									waitMsg : '正在提交...',
									success : function(fp, action) {
										var view=Ext.getCmp('PrivateDocumentView');
										if(view!=null){
										   view.dataView.getStore().reload();
										}
										win.close();
									}
								});
					}
					
				},{
					xtype : 'button',
					iconCls : 'close',
					text : '关闭',
					scope:this,
					handler : function() {
						this.close();
					}
				}];
	
	}
 
});
