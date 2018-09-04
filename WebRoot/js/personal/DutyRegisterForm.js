var DutyRegisterForm = function(registerId) {
	this.registerId = registerId;
	var fp = this.setup();
	var window = new Ext.Window({
		id : 'DutyRegisterFormWin',
		iconCls : 'menu-signInOff',
		title : '补签到、签退',
		width : 500,
		minWidth : 200,
		height : 260,
		minHeight : 250,
		maximizable : true,
		modal : true,
		layout : 'fit',
		buttonAlign : 'center',
		items : [this.setup()],
		keys : {
			key : Ext.EventObject.ENTER,
			fn : function(){
				DutyRegisterForm.save();
			},
			scope : this
		},
		buttons : [{
			text : '保存',
			iconCls : 'btn-save',
			handler : function(){
				DutyRegisterForm.save();
			}
		}, {
			text : '取消',
			iconCls : 'btn-cancel',
			handler : function() {
				window.close();
			}
		}]
	});
	window.show();
};

/**
 * 数据保存
 */
DutyRegisterForm.save = function() {
	var fp = Ext.getCmp('DutyRegisterForm');
	if (fp.getForm().isValid()) {
		fp.getForm().submit({
			method : 'post',
			waitMsg : '正在提交数据...',
			success : function(fp, action) {
				Ext.ux.Toast.msg('操作信息', '成功保存信息！');
				Ext.getCmp('DutyRegisterGrid').getStore().reload();
				Ext.getCmp('DutyRegisterFormWin').close();
			},
			failure : function(fp, action) {
				Ext.MessageBox.show({
					title : '操作信息',
					msg : '信息保存出错，请联系管理员！',
					buttons : Ext.MessageBox.OK,
					icon : 'ext-mb-error'
				});
				Ext.getCmp('DutyRegisterFormWin').close();
			}
		});
	}
};

DutyRegisterForm.prototype.setup = function() {
	var formPanel = new Ext.FormPanel({
				url : __ctxPath + '/personal/saveDutyRegister.do',
				layout : 'form',
				id : 'DutyRegisterForm',
				border : false,
				bodyStyle : 'padding:5px;',
				defaults : {
					width : 400,
					anchor : '98%,98%'
				},
				formId : 'DutyRegisterFormId',
				defaultType : 'textfield',
				items : [{
							name : 'dutyRegister.registerId',
							id : 'registerId',
							xtype : 'hidden',
							value : this.registerId == null ? '': this.registerId
						}, {
							fieldLabel : '登记时间',
							name : 'registerDate',
							xtype:'datetimefield',
							format:'Y-m-d H:i:s',
							allowBlank:false
						},{
							fieldLabel:'班次',
							name:'sectionName',
							hiddenName:'sectionId',
							xtype:'combo',
							allowBlank:false,
							editable : false,
							lazyInit: false,
							allowBlank:false,
							triggerAction : 'all',
							store : new Ext.data.SimpleStore({
								autoLoad : true,
								url : __ctxPath + '/personal/comboDutySection.do',
								fields : ['sectionId', 'sectionName']
							}),
							displayField : 'sectionName',
							valueField : 'sectionId'
						},{
							xtype:'radiogroup',
							fieldLabel : '签到类型',
							autoHeight: true,
							columns :2,
							items : [{
									boxLabel : '上班',
									name : 'inOffFlag',
									inputValue : 1,
									id:'inOffFlag1',
									checked : true
								},{
									boxLabel : '下班',
									name : 'inOffFlag',
									inputValue : 2,
									id:'inOffFlag2'
								}]
						},{
							xtype : 'container',
							layout : 'column',
							fieldLabel : '登记人',
							border : false,
							items : [{
								columnWidth : .999,
								name : 'dutyRegister.fullname',
								id : 'fullname',
								xtype : 'textarea',
								allowBlank : false,
								height : 45
							}, {
								xtype : 'hidden',
								name : 'userIds',
								id : 'userId'
							}, {
								layout : 'form',
								defaultType : 'button',
								border : false,
								items : [{
									width : 70,
									text : '选择',
									iconCls : 'btn-user-sel',
									handler : function() {
										UserSelector.getView(function(ids, names) {
										  var fullname = Ext.getCmp('fullname');
										  var userId = Ext.getCmp('userId');
										  fullname.setValue(names);
										  userId.setValue(ids);
										},false).show();//true表示单选
									}
								}, {
									width : 70,
									iconCls : 'reset',
									text : '重置',
									handler : function(){
										Ext.getCmp('fullname').setValue('');
										Ext.getCmp('userId').setValue('');
									}
								}]
							}]
						}
				]
			});

	if (this.registerId != null && this.registerId != 'undefined') {
		formPanel.getForm().load({
			deferredRender : false,
			url : __ctxPath + '/personal/getDutyRegister.do?registerId='
					+ this.registerId,
			waitMsg : '正在载入数据...',
			success : function(form, action) {
			},
			failure : function(form, action) {
				Ext.ux.Toast.msg('编辑', '载入失败');
			}
		});
	}
	return formPanel;

};
