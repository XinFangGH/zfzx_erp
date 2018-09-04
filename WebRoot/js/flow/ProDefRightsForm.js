/**
 * @author
 * @createtime
 * @class ProDefRightsForm
 * @extends Ext.Window
 * @description ProDefRights表单
 * @company 智维软件
 */
ProDefRightsForm = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		ProDefRightsForm.superclass.constructor.call(this, {
					id : 'ProDefRightsFormWin',
					layout : 'fit',
					items : this.formPanel,
					modal : true,
					height : 300,
					width : 500,
					maximizable : true,
					title : '流程权限详细信息',
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
			// id : 'ProDefRightsForm',
			defaults : {
				anchor : '96%,96%'
			},
			defaultType : 'textfield',
			items : [{
						name : 'proDefRights.rightsId',
						xtype : 'hidden',
						value : this.rightsId == null ? '' : this.rightsId
					}, {
						name : 'proDefRights.proTypeId',
						xtype : 'hidden',
						value : this.proTypeId ? this.proTypeId : null
					}, {
						name : 'proDefRights.defId',
						xtype : 'hidden',
						value : this.defId ? this.defId : null
					}, {
						name : 'proDefRights.userIds',
						xtype : 'hidden'
					}, {
						name : 'proDefRights.roleIds',
						xtype : 'hidden'
					}, {
						name : 'proDefRights.depIds',
						xtype : 'hidden'
					}, {
						xtype : 'compositefield',
						fieldLabel : '用户权限',
						items : [{
									width : 255,
									name : 'proDefRights.userNames',
									xtype : 'textarea',
									maxLength : 2000
								}, {
									xtype : 'button',
									iconCls : 'btn-select',
									text : '选择人员',
									scope : this,
									handler : function() {
										fPanel = this.formPanel;
										UserSelector.getView(
												function(userIds, fullnames) {
													fPanel.getCmpByName('proDefRights.userNames')
															.setValue(fullnames);
													fPanel.getCmpByName('proDefRights.userIds')
															.setValue(userIds);
												}).show();
									}
								}]
					}, {
						xtype : 'compositefield',
						fieldLabel : '角色权限',
						items : [{
									width : 255,
									name : 'proDefRights.roleNames',
									xtype : 'textarea',
									maxLength : 2000
								}, {
									xtype : 'button',
									iconCls : 'btn-select',
									text : '选择角色',
									scope : this,
									handler : function() {
										fPanel = this.formPanel;
										RoleSelector.getView(
												function(userIds, fullnames) {
													fPanel.getCmpByName('proDefRights.roleNames')
															.setValue(fullnames);
													fPanel.getCmpByName('proDefRights.roleIds')
															.setValue(userIds);
												}).show();
									}
								}]
					}, {
						xtype : 'compositefield',
						fieldLabel : '部门权限',
						items : [{
									width : 255,
									name : 'proDefRights.depNames',
									xtype : 'textarea',
									maxLength : 2000
								}, {
									xtype : 'button',
									iconCls : 'btn-select',
									text : '选择部门',
									scope : this,
									handler : function() {
										fPanel = this.formPanel;
										DepSelector.getView(
												function(userIds, fullnames) {
													fPanel.getCmpByName('proDefRights.depNames')
															.setValue(fullnames);
													fPanel.getCmpByName('proDefRights.depIds')
															.setValue(userIds);
												}).show();
									}
								}]
					}]
		});
		// 加载表单对应的数据
		if (this.proTypeId != null && this.proTypeId != 'undefined') {
			this.formPanel.loadData({
						url : __ctxPath + '/flow/getProDefRights.do?proTypeId='
								+ this.proTypeId,
						root : 'data',
						preName : 'proDefRights'
					});
		}

		// 加载表单对应的数据
		if (this.defId != null && this.defId != 'undefined') {
			this.formPanel.loadData({
						url : __ctxPath + '/flow/getProDefRights.do?defId='
								+ this.defId,
						root : 'data',
						preName : 'proDefRights'
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
		$postForm({
					formPanel : this.formPanel,
					scope : this,
					url : __ctxPath + '/flow/saveProDefRights.do',
					callback : function(fp, action) {
						var gridPanel = Ext.getCmp('ProDefRightsGrid');
						if (gridPanel != null) {
							gridPanel.getStore().reload();
						}
						this.close();
					}
				});
	}// end of save

});