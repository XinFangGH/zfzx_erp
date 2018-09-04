var DutyForm = function(dutyId) {
	this.dutyId = dutyId;
	var fp = this.setup();
	var window = new Ext.Window({
		id : 'DutyFormWin',
		title : '排班详细信息',
		iconCls : 'menu-duty',
		width : 550,
		height : 260,
		maximizable : true,
		modal : true,
		layout : 'fit',
		buttonAlign : 'center',
		items : [this.setup()],
		keys : {
			key : Ext.EventObject.ENTER,
			fn : function(){
				DutyForm.save();
			},
			scope : this
		},
		buttons : [{
			text : '保存',
			iconCls : 'btn-save',
			handler : function(){
				DutyForm.save();
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
 * 保存操作
 */
DutyForm.save = function() {
	var fp = Ext.getCmp('DutyForm');
	if (fp.getForm().isValid()) {
		fp.getForm().submit({
			method : 'post',
			waitMsg : '正在提交数据...',
			success : function(fp, action) {
				Ext.ux.Toast.msg('操作信息', '成功保存信息！');
				var exception=action.result.exception;
				if(exception!=undefined && exception!=''){
					Ext.ux.Toast.msg(' 操作信息',exception);
				}
				Ext.getCmp('DutyGrid').getStore().reload();
				Ext.getCmp('DutyFormWin').close();
			},
			failure : function(fp, action) {
				Ext.MessageBox.show({
					title : '操作信息',
					msg : '信息保存出错，请联系管理员！',
					buttons : Ext.MessageBox.OK,
					icon : 'ext-mb-error'
				});
				Ext.getCmp('DutyFormWin').close();
			}
		});
	}
};

DutyForm.prototype.setup = function() {
	var dutyId=(this.dutyId == null || this.dutyId==undefined) ? '' : this.dutyId;
	var formPanel = new Ext.FormPanel({
				url : __ctxPath + '/personal/saveDuty.do',
				layout : 'form',
				id : 'DutyForm',
				border : false,
				bodyStyle : 'padding:5px;',
				defaults : {
					anchor : '98%,98%'
				},
				formId : 'DutyFormId',
				defaultType : 'textfield',
				items : [{
							name : 'duty.dutyId',
							id : 'dutyId',
							xtype : 'hidden',
							value : dutyId
						},{
							id : 'dutySystemId',
							name : 'dutySystemId',
							xtype : 'hidden'
						},{
							fieldLabel : '班制',
							name : 'dutySysId',
							id : 'dutySysId',
							xtype : 'combo',
							allowBlank : false,
							editable : false,
							lazyInit : false,
							triggerAction : 'all',
							listeners:{
								select : function(combo,record,index){
									Ext.getCmp('dutySystemId').setValue(record.data.systemId);
								} 
							},
							store : new Ext.data.SimpleStore({
								autoLoad : true,
								url : __ctxPath
										+ '/personal/comboDutySystem.do',
								fields : ['systemId', 'systemName']
							}),
							displayField : 'systemName',
							valueField : 'systemId'
						}, {
							fieldLabel : '开始时间',
							name : 'duty.startTime',
							id : 'startTime',
							format : 'Y-m-d',
							allowBlank : false,
							xtype : 'datefield'
						}, {
							fieldLabel : '结束时间',
							name : 'duty.endTime',
							format : 'Y-m-d',
							id : 'endTime',
							xtype : 'datefield',
							allowBlank : false
						},{
							xtype : 'container',
							layout : 'column',
							fieldLabel : '员工',
							items : [{
								name : 'duty.fullname',
								xtype : 'textarea',
								id : 'fullname',
								allowBlank : false,
								readOnly : true,
								columnWidth : .999,
								height : 45
							}, {
								layout : 'form',
								defaultType : 'button',
								border : false,
								items : [ {
									text : '选择',
									id : 'userSelect',
									iconCls : 'btn-user-sel',
									width : 70,
									//人员选择器
									handler : function() {
										//当为更新时，用户选择器为单选，添加时，可为选择多个用户 
										var isSingle=false;
										if (dutyId!=''){
											isSingle = true;
										}
										UserSelector.getView(
											function(ids, names) {
											  var fullname = Ext.getCmp('fullname');
											  var userId = Ext.getCmp('userId');
											  fullname.setValue(names);
											  userId.setValue(ids);
											},isSingle).show();
									}
								}, {
									width : 70,
									text : '重置',
									iconCls : 'reset',
									handler : function(){
										Ext.getCmp('fullname').setValue('');
										Ext.getCmp('userId').setValue('');
									}
								}]
							}, {
								name : 'duty.userId',
								id : 'userId',
								xtype : 'hidden'
							}]
						}
				]
			});

	if (this.dutyId != null && this.dutyId != 'undefined') {
		formPanel.getForm().load({
			deferredRender : false,
			url : __ctxPath + '/personal/getDuty.do?dutyId='+ this.dutyId,
			waitMsg : '正在载入数据...',
			success : function(form, action) {
				var result = action.result.data;
				var startTime = getDateFromFormat(result.startTime,'yyyy-MM-dd HH:mm:ss');
				var endTime = getDateFromFormat(result.endTime,'yyyy-MM-dd HH:mm:ss');
				
				Ext.getCmp('startTime').setValue(new Date(startTime));
				Ext.getCmp('endTime').setValue(new Date(endTime));
				Ext.getCmp('dutySystemId').setValue(result.dutySystem.systemId);
				Ext.getCmp('dutySysId').setValue(result.dutySystem.systemName);
			},
			failure : function(form, action) {
			}
		});
	}
	return formPanel;

};
