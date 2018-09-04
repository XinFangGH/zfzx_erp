var HolidayRecordForm = function(recordId) {
	this.recordId = recordId;
	var fp = this.setup();
	var window = new Ext.Window({
		id : 'HolidayRecordFormWin',
		title : '假期设置详细信息',
		iconCls : 'menu-holidayRecord',
		width : 390,
		minWidth : 390,
		height : 270,
		minHeight : 270,
		maximizable : true,
		modal : true,
		layout : 'fit',
		buttonAlign : 'center',
		items : [this.setup()],
		keys : {
			key : Ext.EventObject.ENTER,
			fn : function(){
				HolidayRecordForm.save();
			},
			scope : this
		},
		buttons : [{
			text : '保存',
			iconCls : 'btn-save',
			handler : function(){
				HolidayRecordForm.save();
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

HolidayRecordForm.save = function(){
	var fp = Ext.getCmp('HolidayRecordForm');
	if (fp.getForm().isValid()) {
		fp.getForm().submit({
			method : 'post',
			waitMsg : '正在提交数据...',
			success : function(fp, action) {
				Ext.ux.Toast.msg('操作信息', '成功保存信息！');
				Ext.getCmp('HolidayRecordGrid').getStore().reload();
				Ext.getCmp('HolidayRecordFormWin').close();
			},
			failure : function(fp, action) {
				Ext.MessageBox.show({
					title : '操作信息',
					msg : '信息保存出错，请联系管理员！',
					buttons : Ext.MessageBox.OK,
					icon : 'ext-mb-error'
				});
				Ext.getCmp('HolidayRecordFormWin').close();
			}
		});
	}
};

HolidayRecordForm.prototype.setup = function() {

	var formPanel = new Ext.FormPanel({
		url : __ctxPath + '/personal/saveHolidayRecord.do',
		layout : 'form',
		id : 'HolidayRecordForm',
		bodyStyle : 'padding:5px;',
		border : false,
		defaults : {
			width : 400,
			anchor : '98%,98%'
		},
		formId : 'HolidayRecordFormId',
		defaultType : 'textfield',
		items : [{
			name : 'holidayRecord.recordId',
			id : 'recordId',
			xtype : 'hidden',
			value : this.recordId == null ? '' : this.recordId
		}, {
			fieldLabel : '开始日期',
			name : 'holidayRecord.startTime',
			xtype:'datefield',
			format:'Y-m-d',
			id : 'startTime',
			allowBlank : false
		}, {
			fieldLabel : '结束日期',
			xtype : 'datefield',
			name : 'holidayRecord.endTime',
			id : 'endTime',
			format : 'Y-m-d',
			allowBlank : false
		}, {
			fieldLabel : '描述',
			xtype : 'textarea',
			name : 'holidayRecord.descp',
			id : 'descp',
			allowBlank : false
		},{
			width : '100%',
			xtype : 'container',
			layout : 'column',
			defaultType : 'textfield',
			height : 26,
			items : [{
				xtype : 'label',
				text : '所属用户:',
				width : 105
			}, {
				columnWidth : .999,
				name : 'holidayRecord.fullname',
				id : 'fullname',
				readOnly : true
			}, {
				xtype : 'hidden',
				name : 'holidayRecord.userId',
				id : 'userId'
			},{
				width : 70,
				xtype : 'button',
				text : '选择',
				id:'userSelect',
				iconCls : 'btn-user-sel',
				//人员选择器
				handler : function() {
					UserSelector.getView(
							function(ids, names) {
							  var fullname = Ext.getCmp('fullname');
							  var userId = Ext.getCmp('userId');
							  fullname.setValue(names);
							  userId.setValue(ids);
							},true).show();//true表示单选
				}
			}]
		}, {
			fieldLabel : '全公司假期',
			name : 'holidayRecord.isAll',
			id : 'isAll',
			xtype : 'checkbox',
			inputValue : '0',
			listeners : {
				check : function(ck,isChecked){
					var fullname = Ext.getCmp('fullname');
					var userId = Ext.getCmp('userId');
					var userSelect = Ext.getCmp('userSelect');
					if(isChecked){
						fullname.setValue('');
						fullname.setDisabled(true);
						userSelect.setDisabled(true);
						userId.setValue('');
					}else{
						fullname.setDisabled(false);
						userSelect.setDisabled(false);
					}
				}
			}
		}
		]
	});

	if (this.recordId != null && this.recordId != 'undefined') {
		formPanel.getForm().load({
			deferredRender : false,
			url : __ctxPath + '/personal/getHolidayRecord.do?recordId='
					+ this.recordId,
			waitMsg : '正在载入数据...',
			success : function(form, action) {
				var result = action.result.data;
				var startTime = getDateFromFormat(result.startTime,'yyyy-MM-dd HH:mm:ss');
				var endTime = getDateFromFormat(result.endTime,'yyyy-MM-dd HH:mm:ss');
				
				Ext.getCmp('startTime').setValue(new Date(startTime));
				Ext.getCmp('endTime').setValue(new Date(endTime));
				
				if(result.isAll==1){
					Ext.getCmp('isAll').setValue(true);
				}
			},
			failure : function(form, action) {
			}
		});
	}
	return formPanel;

};
