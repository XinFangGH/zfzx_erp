/**
 * @author lyy
 * @createtime
 * @class JobChangeForm
 * @extends Ext.Panel
 * @description JobChange表单
 * @company 智维软件
 */
JobChangeForm = Ext.extend(Ext.Panel, {
	// 内嵌FormPanel
	formPanel : null,
	// 构造函数
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		// 必须先初始化组件
		this.initComponents();
		JobChangeForm.superclass.constructor.call(this, {
			id : 'JobChangeForm',
			layout : 'fit',
			iconCls:'menu-job-reg',
			items : this.formPanel,
			modal : true,
			height : 200,
			width : 400,
			tbar : this.toolbar,
			maximizable : true,
			title : '登记职位调动信息',
			buttonAlign : 'center'
			});
	},// end of the constructor
	// 初始化组件
	initComponents : function() {
		var _url = __ctxPath + '/system/listDepartment.do?opt=appUser';
		var depSelector = new TreeSelector('jobChange.newDepName', _url,
				'新部门名称', 'JobChangeForm.newDepId', false);
		this.formPanel = new Ext.FormPanel({
			layout : 'form',
			bodyStyle : 'padding:10px 20px 10px 10px',
			border : false,
			url : __ctxPath + '/hrm/saveJobChange.do',
			id : 'JobChangeFormPanel',
			autoScroll : true,
			defaults : {
				anchor : '98%,98%'
			},
			reader : new Ext.data.JsonReader({
						root : 'data'
					}, [{
								name : 'JobChangeForm.changeId',
								mapping : 'changeId'
							}, {
								name : 'JobChangeForm.profileId',
								mapping : 'profileId'
							}, {
								name : 'JobChangeForm.profileNo',
								mapping : 'profileNo'
							}, {
								name : 'JobChangeForm.userName',
								mapping : 'userName'
							}, {
								name : 'JobChangeForm.orgJobId',
								mapping : 'orgJobId'
							}, {
								name : 'JobChangeForm.orgJobName',
								mapping : 'orgJobName'
							}, {
								name : 'JobChangeForm.newJobId',
								mapping : 'newJobId'
							}, {
								name : 'JobChangeForm.newJobName',
								mapping : 'newJobName'
							}, {
								name : 'JobChangeForm.orgStandardNo',
								mapping : 'orgStandardNo'
							}, {
								name : 'JobChangeForm.orgStandardName',
								mapping : 'orgStandardName'
							}, {
								name : 'JobChangeForm.orgDepId',
								mapping : 'orgDepId'
							}, {
								name : 'JobChangeForm.orgDepName',
								mapping : 'orgDepName'
							}, {
								name : 'JobChangeForm.orgTotalMoney',
								mapping : 'orgTotalMoney'
							}, {
								name : 'JobChangeForm.newStandardNo',
								mapping : 'newStandardNo'
							}, {
								name : 'JobChangeForm.newStandardName',
								mapping : 'newStandardName'
							}, {
								name : 'JobChangeForm.newDepId',
								mapping : 'newDepId'
							}, {
								name : 'JobChangeForm.newDepName',
								mapping : 'newDepName'
							}, {
								name : 'JobChangeForm.newTotalMoney',
								mapping : 'newTotalMoney'
							}, {
								name : 'JobChangeForm.changeReason',
								mapping : 'changeReason'
							}, {
								name : 'JobChangeForm.regName',
								mapping : 'regName'
							}, {
								name : 'JobChangeForm.regTime',
								mapping : 'regTime'
							}, {
								name : 'JobChangeForm.checkName',
								mapping : 'checkName'
							}, {
								name : 'JobChangeForm.checkTime',
								mapping : 'checkTime'
							}, {
								name : 'JobChangeForm.checkOpinion',
								mapping : 'checkOpinion'
							}, {
								name : 'JobChangeForm.status',
								mapping : 'status'
							}, {
								name : 'JobChangeForm.memo',
								mapping : 'memo'
							}]),
			defaultType : 'textfield',
			items : [{
						name : 'jobChange.changeId',
						id : 'JobChangeForm.changeId',
						xtype : 'hidden',
						value : this.changeId == null ? '' : this.changeId
					}, {
						// fieldLabel : '档案号',
						xtype : 'hidden',
						name : 'jobChange.profileId',
						id : 'JobChangeForm.profileId'
					}, {
						// fieldLabel : '原职位号',
						xtype : 'hidden',
						name : 'jobChange.orgJobId',
						id : 'JobChangeForm.orgJobId'
					}, {
						// fieldLabel : '新职位号',
						xtype : 'hidden',
						name : 'jobChange.newJobId',
						id : 'JobChangeForm.newJobId'
					}, {
						// fieldLabel : '原部门号',
						xtype : 'hidden',
						name : 'jobChange.orgDepId',
						id : 'JobChangeForm.orgDepId'
					}, {
//						 fieldLabel : '新部门号',
						xtype : 'hidden',
						name : 'jobChange.newDepId',
						id : 'JobChangeForm.newDepId'
					}, {
					   xtype : 'hidden',
						name : 'jobChange.orgStandardId',
						id : 'JobChangeForm.orgStandardId'
					}, {
					   xtype : 'hidden',
						name : 'jobChange.newStandardId',
						id : 'JobChangeForm.newStandardId'
					}, {
						// fieldLabel : '登记人',
						xtype : 'hidden',
						name : 'jobChange.regName',
						id : 'JobChangeForm.regName'
					}, {
						// fieldLabel : '登记时间',
						xtype : 'hidden',
						name : 'jobChange.regTime',
						id : 'JobChangeForm.regTime'
					}, {
						// fieldLabel : '审核人',
						xtype : 'hidden',
						name : 'jobChange.checkName',
						id : 'JobChangeForm.checkName'
					}, {
						// fieldLabel : '审核时间',
						xtype : 'hidden',
						name : 'jobChange.checkTime',
						id : 'JobChangeForm.checkTime'
					}, {
						// fieldLabel : '审核意见',
						xtype : 'hidden',
						name : 'jobChange.checkOpinion',
						id : 'JobChangeForm.checkOpinion'
					}, {
						// fieldLabel : '状态',
						xtype : 'hidden',
						name : 'jobChange.status',
						id : 'JobChangeForm.status'
					}, {
						xtype : 'container',
						layout : 'column',
						anchor : '100%',
						items : [{
									xtype : 'label',
									style : 'padding:3px 5px 0px 17px;',
									text : '档案编号:'
								}, {
									name : 'jobChange.profileNo',
									width : '50%',
									xtype : 'textfield',
									allowBlank : false,
									readOnly:true,
									id : 'JobChangeForm.profileNo'
								}, {
									xtype : 'button',
									autoWidth : true,
									text : '选择档案',
									iconCls : 'menu-profile',
									handler : function() {
										EmpProfileSelector.getView(
												function(array) {
													Ext.getCmp('JobChangeForm.profileId').setValue(array[0]);
													Ext.getCmp('JobChangeForm.profileNo').setValue(array[1]);
													Ext.getCmp('JobChangeForm.userName').setValue(array[2]);
													Ext.getCmp('JobChangeForm.orgJobId').setValue(array[3]);
													Ext.getCmp('JobChangeForm.orgJobName').setValue(array[4]);
													Ext.getCmp('JobChangeForm.orgDepId').setValue(array[5]);
													Ext.getCmp('JobChangeForm.orgDepName').setValue(array[6]);
													Ext.getCmp('JobChangeForm.orgStandardNo').setValue(array[7]);
													Ext.getCmp('JobChangeForm.orgStandardName').setValue(array[8]);
													Ext.getCmp('JobChangeForm.orgTotalMoney').setValue(array[9]);
													Ext.getCmp('JobChangeForm.orgStandardId').setValue(array[10]);
												}).show();
									}
								}]
					}, {
						xtype : 'fieldset',
						title : '员工基本信息',
						layout : 'column',
						items : [{
									xtype : 'container',
									columnWidth : .5,
									layout : 'form',
									defaults : {
										anchor : '98%,98%'
									},
									defaultType : 'textfield',
									items : [{
												fieldLabel : '姓名',
												readOnly : true,
												allowBlank : false,
												name : 'jobChange.userName',
												id : 'JobChangeForm.userName'
											}, {
												fieldLabel : '原部门名称',
												readOnly : true,
												name : 'jobChange.orgDepName',
												id : 'JobChangeForm.orgDepName'
											}, {
												fieldLabel : '原职位名称',
												readOnly : true,
												name : 'jobChange.orgJobName',
												id : 'JobChangeForm.orgJobName'
											}]
								}, {
									xtype : 'container',
									columnWidth : .5,
									defaults : {
										anchor : '98%,98%'
									},
									layout : 'form',
									defaultType : 'textfield',
									items : [{
												fieldLabel : '原薪酬标准',
												readOnly : true,
												name : 'jobChange.orgStandardName',
												id : 'JobChangeForm.orgStandardName'
											}, {
												fieldLabel : '原薪酬编号',
												readOnly : true,
												name : 'jobChange.orgStandardNo',
												id : 'JobChangeForm.orgStandardNo'
											}, {
												fieldLabel : '原工资总额',
												readOnly : true,
												name : 'jobChange.orgTotalMoney',
												id : 'JobChangeForm.orgTotalMoney'
											}]
								}]
					}, {
						xtype : 'fieldset',
						title : '员工调动信息',
						layout : 'column',
						items : [{
							xtype : 'container',
							layout : 'form',
							columnWidth : .5,
							defaults : {
								anchor : '98%,98%'
							},
							defaultType : 'textfield',
							items : [depSelector,
									{
								fieldLabel : '新薪酬标准',
								name : 'jobChange.newStandardName',
								id : 'JobChangeForm.newStandardName',
								xtype : 'combo',
								mode : 'local',
								allowBlank : false,
								editable : false,
								valueField : 'standardName',
								displayField : 'standardName',
								triggerAction : 'all',
								store : new Ext.data.JsonStore({
											url : __ctxPath
													+ '/hrm/comboStandSalary.do',
											fields : [{
														name : 'standardId',
														type : 'int'
													}, 'standardNo',
													'standardName',
													'totalMoney',
													'setdownTime', 'status']
										}),
								listeners : {
									focus : function(){
									   Ext.getCmp('JobChangeForm.newStandardName').getStore().reload();
									},
									select : function(combo, record, index) {
										Ext
												.getCmp('JobChangeForm.newStandardId')
												.setValue(record.data.standardId);
										Ext
												.getCmp('JobChangeForm.newStandardNo')
												.setValue(record.data.standardNo);
										Ext
												.getCmp('JobChangeForm.newTotalMoney')
												.setValue(record.data.totalMoney);
									}
								}

							}, {
								fieldLabel : '新薪酬编号',
								allowBlank : false,
								readOnly : true,
								name : 'jobChange.newStandardNo',
								id : 'JobChangeForm.newStandardNo'
							}]
						}, {
							xtype : 'container',
							layout : 'form',
							defaults : {
								anchor : '98%,98%'
							},
							columnWidth : .5,
							defaultType : 'textfield',
							items : [{
								 xtype : 'container',
								 layout : 'column',
								 border : false, 
								 fieldLabel : '新职位',
								 items : [{
									 columnWidth : .99,
									 xtype : 'textfield',
									 name : 'jobChange.newJobName',
									 readOnly : true
								 }, {
									 width : 80,
									 text : '请选择',
									 xtype : 'button',
									 iconCls : 'btn-position-sel',
									 scope:this,
									 handler : function(){
									 	var formPanel = this.formPanel;
												new PositionDialog({
													single:true,
													callback : function(posIds,
															posNames) {
														formPanel
																.getCmpByName('jobChange.newJobName')
																.setValue(posNames);
														formPanel
																.getCmpByName('jobChange.newJobId')
																.setValue(posIds);
													}
												}).show();
									 }
								 }]
							 }, {
								fieldLabel : '新工资总额',
								allowBlank : false,
								readOnly : true,
								name : 'jobChange.newTotalMoney',
								id : 'JobChangeForm.newTotalMoney'
							}]
						}]
					}, {
						xtype : 'fieldset',
						title : '调动情况说明',
						layout : 'form',
						defaultType : 'textfield',
						items : [{
									fieldLabel : '更改原由',
									name : 'jobChange.changeReason',
									id : 'JobChangeForm.changeReason',
									xtype : 'textarea',
									anchor : '100%'
								}, {
									fieldLabel : '备注',
									name : 'jobChange.memo',
									id : 'JobChangeForm.memo',
									xtype : 'textarea',
									anchor : '100%'
								}]
					}

			]
		});

		this.toolbar = new Ext.Toolbar({
					id : 'JobChangeFormToolbar',
					items : [{
								text : '保存为草稿',
								xtype : 'button',
								iconCls : 'btn-save',
								handler : this.save
										.createCallback(this.formPanel)
							}, {
								text : '提交审核',
								xtype : 'button',
								iconCls : 'btn-ok',
								handler : this.submit
										.createCallback(this.formPanel)
							}, {
								text : '重置',
								xtype : 'button',
								iconCls : 'btn-reset',
								handler : this.reset
										.createCallback(this.formPanel)
							}, {
								text : '取消',
								xtype : 'button',
								iconCls : 'btn-cancel',
								handler : this.cancel.createCallback(this)
							}]
				});// end of toolbar
		// 加载表单对应的数据
		if (this.changeId != null && this.changeId != 'undefined') {
			this.formPanel.getForm().load({
				deferredRender : false,
				url : __ctxPath + '/hrm/getJobChange.do?changeId='
						+ this.changeId,
				waitMsg : '正在载入数据...',
				success : function(form, action) {
					var change=Ext.util.JSON.decode(action.response.responseText).data[0];
					Ext.getCmp('JobChangeForm.newDepId').setValue(change.newDepId);
					Ext.getCmp('jobChange.newDepName').setValue(change.newDepName);
				},
				failure : function(form, action) {
				}
			});
		}
	},// end of the initcomponents

	/**
	 * 重置
	 * 
	 * @param {}
	 *            formPanel
	 */
	reset : function(formPanel) {
		formPanel.getForm().reset();
		
		
	},
	/**
	 * 取消
	 * 
	 * @param {}
	 *            tappanel
	 */
	cancel : function(formpanel) {
		var tabs = Ext.getCmp('centerTabPanel');
		if (formpanel!= null) {
			tabs.remove('JobChangeForm');
		}
	},
	/**
	 * 保存草稿
	 */
	save : function(formPanel) {
		Ext.getCmp('JobChangeForm.status').setValue('-1');
		if (formPanel.getForm().isValid()) {
			formPanel.getForm().submit({
						method : 'POST',
						waitMsg : '正在提交数据...',
						success : function(fp, action) {
							Ext.ux.Toast.msg('操作信息', '成功保存信息！');
							var gridPanel = Ext.getCmp('JobChangeGrid');
							if (gridPanel != null) {
								gridPanel.getStore().reload();
							}
							formPanel.getForm().reset();
						},
						failure : function(fp, action) {
							Ext.MessageBox.show({
										title : '操作信息',
										msg : '信息保存出错，请联系管理员！',
										buttons : Ext.MessageBox.OK,
										icon : Ext.MessageBox.ERROR
									});
						}
					});
		}
	},// end of save
	/**
	 * 提交审核
	 */
	submit : function(formPanel) {
		Ext.getCmp('JobChangeForm.status').setValue('0');
		if (formPanel.getForm().isValid()) {
			formPanel.getForm().submit({
						method : 'POST',
						waitMsg : '正在提交数据...',
						success : function(fp, action) {
							Ext.ux.Toast.msg('操作信息', '成功保存信息！');
							var gridPanel = Ext.getCmp('JobChangeGrid');
							if (gridPanel != null) {
								gridPanel.getStore().reload();
							}
							formPanel.getForm().reset();
		                   
						},
						failure : function(fp, action) {
							Ext.MessageBox.show({
										title : '操作信息',
										msg : '信息保存出错，请联系管理员！',
										buttons : Ext.MessageBox.OK,
										icon : Ext.MessageBox.ERROR
									});
						}
					});
		}
	}// end of submit
});