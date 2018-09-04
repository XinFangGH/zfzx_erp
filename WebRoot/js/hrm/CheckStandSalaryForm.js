var CheckStandSalaryForm = function(standardId) {
	this.standardId = standardId;
	var fp = this.setup(standardId);
	var window = new Ext.Window({
				id : 'CheckStandSalaryFormWin',
				title : '标准审核',
				iconCls:'menu-goods-apply',
				width : 500,
				height : 500,
				modal : true,
				layout : 'fit',
				plain : true,
				bodyStyle : 'padding:5px;',
				buttonAlign : 'center',
				items : [this.setup(standardId)],
				buttons : [{
					text : '审核通过',
					iconCls : 'btn-finish',
					handler : function() {
						var fp = Ext.getCmp('CheckStandSalaryForm');
						if (fp.getForm().isValid()) {
							fp.getForm().submit({
								method : 'post',
								params : {
									status : '1'
								},// 状态参数1,表审核通过
								waitMsg : '正在提交数据...',
								success : function(fp, action) {
									Ext.ux.Toast.msg('操作信息', '成功保存信息！');
									Ext.getCmp('StandSalaryGrid').getStore().reload();
									window.close();
								},
								failure : function(fp, action) {
									Ext.MessageBox.show({
												title : '操作信息',
												msg : '信息保存出错，请联系管理员！',
												buttons : Ext.MessageBox.OK,
												icon : Ext.MessageBox.ERROR
											});
									window.close();
								}
							});
						}
					}
				}, {
					text : '审核未通过',
					iconCls : 'btn-disagree',
					handler : function() {
						var fp = Ext.getCmp('CheckStandSalaryForm');
						if (fp.getForm().isValid()) {
							fp.getForm().submit({
								method : 'post',
								waitMsg : '正在提交数据...',
								success : function(fp, action) {
									Ext.ux.Toast.msg('操作信息', '成功保存信息！');
									Ext.getCmp('StandSalaryGrid').getStore().reload();
									window.close();
								},
								failure : function(fp, action) {
									Ext.MessageBox.show({
												title : '操作信息',
												msg : '信息保存出错，请联系管理员！',
												buttons : Ext.MessageBox.OK,
												icon : Ext.MessageBox.ERROR
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

CheckStandSalaryForm.prototype.setup = function(standardId) {

	var standSalaryItemView = new CheckStandSalaryItemView(standardId);
	var formPanel = new Ext.FormPanel({
		url : __ctxPath + '/hrm/checkStandSalary.do',
		layout : 'form',
		id : 'CheckStandSalaryForm',
		formId : 'CheckStandSalaryFormId',
		bodyStyle : 'padding:0px 5px 5px 5px;',
		defaultType : 'textfield',
		border : false,
//		reader : new Ext.data.JsonReader({
//					root : 'data'
//				}, [{
//							name : 'standSalary.standardId',
//							mapping : 'standardId'
//						}, {
//							name : 'standSalary.standardNo',
//							mapping : 'standardNo'
//						}, {
//							name : 'standSalary.standardName',
//							mapping : 'standardName'
//						}, {
//							name : 'standSalary.totalMoney',
//							mapping : 'totalMoney'
//						}, {
//							name : 'standSalary.framer',
//							mapping : 'framer'
//						}, {
//							name : 'standSalary.memo',
//							mapping : 'memo'
//						}]),
		items : [{
					name : 'standSalary.standardId',
					id : 'standSalary.standardId',
					xtype : 'hidden',
					value : this.standardId == null ? '' : this.standardId
				}, {
					xtype : 'fieldset',
					title : '薪酬信息',
					layout : 'form',
					anchor : '98%',
					autoWidth : true,
					autoHeight : true,
					labelWidth : 70,
					defaultType : 'textfield',
					items : [{
						xtype : 'container',
						style : 'padding-left:0px;',
						defaultType : 'textfield',
						layout : 'column',
						autoWidth : true,
						height : 26,
						defaults : {
							width : 150
						},
						items : [{
									xtype : 'label',
									text : '标准编号:',
									style : 'padding-left:0px;padding-top:3px;',
									width : 71
								}, {
									fieldLabel : '标准编号',
									readOnly : true,
									name : 'standSalary.standardNo'
									// id : 'standardNo'
							}	, {
									xtype : 'label',
									text : '标准名称',
									style : 'padding-left:0px;padding-top:3px;',
									width : 71
								}, {
									fieldLabel : '标准名称',
									readOnly : true,
									name : 'standSalary.standardName'
									// id : 'standardName'
							}]
					}, {
						xtype : 'container',
						style : 'padding-left:0px;',
						layout : 'column',
						defaultType : 'textfield',
						autoWidth : true,
						height : 26,
						defaults : {
							width : 150
						},
						items : [{
									xtype : 'label',
									text : '薪资总额',
									style : 'padding-left:0px;padding-top:3px;',
									width : 71
								}, {
									// fieldLabel : '薪资总额',
									name : 'standSalary.totalMoney',
									// id : 'totalMoney',
									readOnly : true
								}, {
									xtype : 'label',
									text : '标准制定人',
									style : 'padding-left:0px;padding-top:3px;',
									width : 71
								}, {
									// fieldLabel : '标准制定人',
									name : 'standSalary.framer',
									// id : 'framer',
									readOnly : true,
									value : curUserInfo.fullname
								}]
					}, {
						fieldLabel : '备注',
						name : 'standSalary.memo',
						readOnly : true,
						// id : 'memo',
						width : 370,
						xtype : 'textarea'
					}, {
						fieldLabel : '审核意见',
						name : 'standSalary.checkOpinion',
						allowBlank : false,
						blankText : '审核意见不可为空!',
						// id : 'standSalary.checkOpinion',
						width : 370,
						xtype : 'textarea'
					}]
				}, standSalaryItemView]
	});

	if (this.standardId != null && this.standardId != 'undefined') {
		formPanel.loadData({
			preName : 'standSalary',
			root : 'data',
			url : __ctxPath + '/hrm/getStandSalary.do?standardId='
					+ this.standardId,
			waitMsg : '正在载入数据...',
			success : function(form, action) {
				// Ext.Msg.alert('编辑', '载入成功！');
			},
			failure : function(form, action) {
				// Ext.Msg.alert('编辑', '载入失败');
			}
		});
	}
	return formPanel;

};
