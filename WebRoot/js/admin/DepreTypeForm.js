/**
 * @author
 * @createtime
 * @class DepreTypeForm
 * @extends Ext.Window
 * @description DepreTypeForm表单
 * @company 智维软件
 */
DepreTypeForm = Ext.extend(Ext.Window, {
	// 内嵌FormPanel
	formPanel : null,
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		DepreTypeForm.superclass.constructor.call(this, {
			layout : 'fit',
			id : 'DepreTypeFormWin',
			title : '折旧类型详细信息',
			iconCls : 'menu-depre-type',
			width : 400,
			height : 240,
			minWidth : 399,
			minHeight : 239,
			items : this.formPanel,
			maximizable : true,
			border : false,
			modal : true,
			plain : true,
			buttonAlign : 'center',
			buttons : this.buttons,
			keys : {
				key : Ext.EventObject.ENTER,
				fn : this.save,
				scope : this
			}
		});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		this.formPanel = new Ext.FormPanel({
				url : __ctxPath + '/admin/saveDepreType.do',
				layout : 'form',
				id : 'DepreTypeForm',
				frame : false,
				border : false,
				bodyStyle : 'padding:5px;',
				defaults : {
					anchor : '98%,98%'
				},
				defaultType : 'textfield',
				items : [{
							name : 'depreType.depreTypeId',
							id : 'depreTypeId',
							xtype : 'hidden',
							value : this.depreTypeId == null ? '' : this.depreTypeId
						}, {
							fieldLabel : '分类名称',
							name : 'depreType.typeName',
							id : 'typeName',
							allowBlank : false
						}, {
							xtype : 'container',
							height : 28,
							fieldLabel : '折旧周期(月)',
							layout : 'column',
							border : false,
							layoutConfigs : {
								align : 'middle'
							},
							defaults : {
								margins : '0 2 0 2'
							},
							items : [{
								columnWidth : .999,
								xtype : 'numberfield',
								name : 'depreType.deprePeriod',
								id : 'deprePeriod',
								allowBlank : false
							}]
						}, {
							fieldLabel : '折旧计算方法',
							hiddenName : 'depreType.calMethod',
							id : 'calMethod',
							xtype : 'combo',
							mode : 'local',
							editable : false,
							allowBlank : false,
							triggerAction : 'all',
							store : [['1', '平均年限法'], ['2', '工作量法'],
									['3', '双倍余额递减法'], ['4', '年数总和法']]
						}, {
							fieldLabel : '类型说明',
							name : 'depreType.typeDesc',
							id : 'typeDesc',
							xtype : 'textarea'
						}

				]
			});//end of the formaPanel

	if (this.depreTypeId != null && this.depreTypeId != 'undefined') {
		this.formPanel.getForm().load({
			deferredRender : false,
			url : __ctxPath + '/admin/getDepreType.do?depreTypeId='
					+ this.depreTypeId,
			waitMsg : '正在载入数据...',
			success : function(form, action) {
			},
			failure : function(form, action) {
				Ext.ux.Toast.msg('编辑', '载入失败');
			}
		});
	}//end of load formPanel
		this.buttons = [{
			text : '保存',
			iconCls : 'btn-save',
			handler : this.save,
			scope : this
		}, {
			text : '取消',
			iconCls : 'btn-cancel',
			handler : function() {
				Ext.getCmp('DepreTypeFormWin').close();
			}
		}];//end of the buttons 
	},//end of the initUIComponents
	
	/**
	 * 搜索
	 */
	save : function() {
		var fp = Ext.getCmp('DepreTypeForm');
		if (fp.getForm().isValid()) {
			fp.getForm().submit({
				method : 'post',
				waitMsg : '正在提交数据...',
				success : function(fp, action) {
					Ext.ux.Toast.msg('操作信息', '成功保存信息！');
					Ext.getCmp('DepreTypeGrid').getStore()
							.reload();
					Ext.getCmp('DepreTypeFormWin').close();
				},
				failure : function(fp, action) {
					Ext.MessageBox.show({
								title : '操作信息',
								msg : '信息保存出错，请联系管理员！',
								buttons : Ext.MessageBox.OK,
								icon : 'ext-mb-error'
							});
					Ext.getCmp('DepreTypeFormWin').close();
				}
			});
		}
	}
});
