/**
 * @author
 * @createtime
 * @class AppRoleForm
 * @extends Ext.Window
 * @description AppRoleForm表单
 * @company 智维软件
 */
AppRoleFormOnly = Ext.extend(Ext.Window, {
	// 内嵌FormPanel
	formPanel : null,
	// 构造函数
	constructor : function(_cfg) {
		if(typeof(_cfg.roleType)!="undefined"){
			this.roleType=_cfg.roleType
		}
		if(typeof(_cfg.roleGridId)!="undefined"){
			this.roleGridId=_cfg.roleGridId
		}
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		AppRoleFormOnly.superclass.constructor.call(this, {
					layout : 'fit',
					items : this.formPanel,
					modal : true,
					id : 'AppRoleFormOnlyWin',
					title : '角色详细信息',
					iconCls : 'menu-role',
					width : 370,
					height : 220,
					buttonAlign : 'center',
					buttons : this.buttons
				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		this.formPanel = new Ext.FormPanel({
					url : __ctxPath + '/system/saveForOneAppRole.do?isCopy='+this.isCopy,
					layout : 'form',
					id : 'AppRoleFormOnly',
					border:false,
					bodyStyle : 'padding:5px;',
					defaults : {
						anchor : '96%,96%'
					},
					formId : 'AppRoleFormId',
					defaultType : 'textfield',
					items : [{
								name : 'appRole.roleId',
								xtype : 'hidden',
								value : this.roleId == null ? '' : this.roleId
							}, {
								fieldLabel : '角色名称',
								name : 'appRole.roleName'
							}, {
								fieldLabel : '角色描述',
								xtype : 'textarea',
								name : 'appRole.roleDesc'
							}, {
								fieldLabel : '状态',
								hiddenName : 'appRole.status',
								xtype : 'combo',
								mode : 'local',
								editable : true,
								triggerAction : 'all',
								store : [['0', '禁用'], ['1', '可用']],
								value : 1
							},{
							   xtype:'hidden',
							   name:'appRole.roleType',
							   value:this.roleType
							},{
							   xtype:'hidden',
							   name:'appRole.orgId',
							   value:this.orgId
							},{
							   xtype:'hidden',
							   name:'appRole.org_type',
							   value:this.org_type
							}]
				});

		if (this.roleId != null && this.roleId != 'undefined') {
			this.formPanel.loadData({
				url:__ctxPath + '/system/getAppRole.do?roleId=' + this.roleId,
				preName:'appRole',
				root:'data'
			});
		}

		this.buttons = [{
			text : '保存',
			iconCls : 'btn-save',
			scope:this,
			handler : this.save
		}, {
			text : '取消',
			iconCls : 'btn-cancel',
			scope:this,
			handler : function() {
					this.close();
			}
		}]
	},
	save:function(){
		var roleGridId=this.roleGridId;
		var fp = Ext.getCmp('AppRoleFormOnly');
		if (this.isCopy == 1) {
			var roleName = this.formPanel.getCmpByName('appRole.roleName').getValue();
			Ext.Ajax.request({
				url : __ctxPath + '/system/checkAppRole.do',
				params : {
					roleName : roleName
				},
				method : 'post',
				success : function(response) {
					var result = Ext.util.JSON.decode(response.responseText);
					if (result.success) {
						if (fp.getForm().isValid()) {
							fp.getForm().submit({
								method : 'post',
								waitMsg : '正在提交数据...',
								success : function(fp, action) {
									Ext.ux.Toast.msg('操作信息', '成功信息保存！');
									
									Ext.getCmp('AppRoleGrid').getStore().reload();
								
									Ext.getCmp('AppRoleFormOnlyWin').close();
								},
								failure : function(fp, action) {
									Ext.MessageBox.show({
												title : '操作信息',
												msg : '信息保存出错，请联系管理员！',
												buttons : Ext.MessageBox.OK,
												icon : 'ext-mb-error'
											});
									Ext.getCmp('AppRoleFormOnlyWin').close();
								}
							});
						}
					} else {
						Ext.ux.Toast.msg('提示信息', '该角色名字已经存在，请更改！');
					}
				},
				failure : function() {
				}
			});
		} else {
			if (fp.getForm().isValid()) {
				fp.getForm().submit({
							method : 'post',
							waitMsg : '正在提交数据...',
							success : function(fp, action) {
								Ext.ux.Toast.msg('操作信息', '成功保存信息！');
								
								Ext.getCmp('AppRoleGrid').getStore().reload();
								
								Ext.getCmp('AppRoleFormOnlyWin').close();
							},
							failure : function(fp, action) {
								Ext.MessageBox.show({
											title : '操作信息',
											msg : '信息保存出错，请联系管理员！',
											buttons : Ext.MessageBox.OK,
											icon : 'ext-mb-error'
										});
								Ext.getCmp('AppRoleFormOnlyWin').close();
							}
						});
			}
		}

	}//end of save
});