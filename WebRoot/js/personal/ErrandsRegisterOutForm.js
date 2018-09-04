var ErrandsRegisterOutForm = function(dateId) {
	this.dateId = dateId;
	var fp = this.setup();
	var window = new Ext.Window({
		id : 'ErrandsRegisterOutFormWin',
		title : '外出单详细信息',
		iconCls:'menu-errands',
		width : 400,
		height : 250,
		modal : true,
		layout : 'fit',
		buttonAlign : 'center',
		items : [this.setup()],
		buttons : [{
			text : '保存',
			iconCls : 'btn-save',
			handler : function() {
				var fp = Ext.getCmp('ErrandsRegisterOutForm');
				if (fp.getForm().isValid()) {
					fp.getForm().submit({
						method : 'post',
						waitMsg : '正在提交数据...',
						success : function(fp, action) {
							Ext.ux.Toast.msg('操作信息', '成功保存信息！');
							Ext.getCmp('ErrandsRegisterOutGrid').getStore().reload();
							window.close();
						},
						failure : function(fp, action) {
							Ext.MessageBox.show({
								title : '操作信息',
								msg : '信息保存出错，请联系管理员！',
								buttons : Ext.MessageBox.OK,
								icon : 'ext-mb-error'
							});
							window.close();
						}
					});
				}
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

ErrandsRegisterOutForm.prototype.setup = function() {

	var formPanel = new Ext.FormPanel({
		url : __ctxPath + '/personal/saveErrandsRegister.do',
		layout : 'form',
		id : 'ErrandsRegisterOutForm',
		bodyStyle : 'padding:5px;',
		border:false,
		defaults : {
			width : 400,
			anchor : '98%,98%'
		},
		formId : 'ErrandsRegisterOutFormId',
		defaultType : 'textfield',
		items : [{
			name : 'errandsRegister.dateId',
			id : 'dateId',
			xtype : 'hidden',
			value : this.dateId == null ? '' : this.dateId
		}, {
			xtype:'hidden',
			name : 'errandsRegister.userId',
			id : 'userId'
		},  {
			xtype:'hidden',
			name : 'errandsRegister.status',
			id : 'status'
		}, {
			xtype:'hidden',
			name : 'errandsRegister.approvalOption',
			id : 'approvalOption'
		},  {
			xtype:'hidden',
			name : 'errandsRegister.approvalName',
			id : 'approvalName'
		},{
			name : 'errandsRegister.flag',
			id : 'flag',
			xtype : 'hidden',
			value:2
		}, {
			fieldLabel : '描述',
			xtype:'textarea',
			name : 'errandsRegister.descp',
			allowBlank : false,
			id : 'descp'
		}, {
			fieldLabel : '开始时间',
			name : 'errandsRegister.startTime',
			xtype:'datetimefield',
			format:'Y-m-d H:i:s',
			allowBlank : false,
			id : 'startTime'
		}, {
			fieldLabel : '结束时间',
			name : 'errandsRegister.endTime',
			xtype:'datetimefield',
			format:'Y-m-d H:i:s',
			allowBlank : false,
			id : 'endTime'
		}, {
			fieldLabel : '审批人',
			hiddenName : 'errandsRegister.approvalId',
			id : 'approvalId',
			emptyText : '请选择审批人',
			xtype : 'combo',
			mode : 'local',
			anchor : '98%',
			allowBlank : false,
			editable : false,
			valueField : 'id',
			displayField : 'name',
			triggerAction : 'all',
			store : new Ext.data.SimpleStore({
		        autoLoad : true,
				url : __ctxPath + '/system/upUserAppUser.do',
				fields : ['id', 'name']
			}),
			listeners : {
				select : function(combo, record, index) {
					Ext.getCmp('approvalName').setValue(record.data.name);
				}
			}
		}]
	});

	if (this.dateId != null && this.dateId != 'undefined') {
		formPanel.getForm().load({
			deferredRender : false,
			url : __ctxPath + '/personal/getErrandsRegister.do?dateId='
					+ this.dateId,
			waitMsg : '正在载入数据...',
			success : function(form, action) {
				var result = action.result.data;
				
				var startTime = getDateFromFormat(result.startTime,'yyyy-MM-dd H:mm:ss');
				var endTime = getDateFromFormat(result.endTime,'yyyy-MM-dd H:mm:ss');
				Ext.getCmp('startTime').setValue(new Date(startTime));
				Ext.getCmp('endTime').setValue(new Date(endTime));
			},
			failure : function(form, action) {
				Ext.ux.Toast.msg('编辑', '载入失败');
			}
		});
	}
	return formPanel;

};
