/**
 * @author lyy
 * @createtime
 * @class SalaryPayoffForm
 * @extends Ext.Window
 * @description SalaryPayoff表单
 * @company 智维软件
 */
SalaryPayoffForm = Ext.extend(Ext.Panel, {
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
		SalaryPayoffForm.superclass.constructor.call(this, {
					id : 'SalaryPayoffForm',
					layout : 'fit',
					iconCls:'menu-add-salay',
					items : this.formPanel,
					modal : true,
					tbar : this.toolbars,
					height : 200,
					width : 400,
					maximizable : true,
					title : '工资发放详细信息'
				});
	},// end of the constructor
	// 初始化组件
	initComponents : function() {
		this.formPanel = new Ext.FormPanel({
			layout : 'form',
			autoScroll : true,
			bodyStyle : 'padding:10px 10px 10px 10px',
			border : false,
			url : __ctxPath + '/hrm/saveSalaryPayoff.do',
			id : 'SalaryPayoffFormPanel',
			defaults : {
				anchor : '98%,98%'
			},
			defaultType : 'textfield',
			reader : new Ext.data.JsonReader({
						root : 'data'
					}, [{
								name : 'SalaryPayoffForm.recordId',
								mapping : 'recordId'
							}, {
								name : 'SalaryPayoffForm.userId',
								mapping : 'userId'
							}, {
								name : 'SalaryPayoffForm.regTime',
								mapping : 'regTime'
							}, {
								name : 'SalaryPayoffForm.register',
								mapping : 'register'
							}, {
								name : 'SalaryPayoffForm.checkName',
								mapping : 'checkName'
							}, {
								name : 'SalaryPayoffForm.checkTime',
								mapping : 'checkTime'
							}, {
								name : 'SalaryPayoffForm.checkStatus',
								mapping : 'checkStatus'
							}, {
								name : 'SalaryPayoffForm.acutalAmount',
								mapping : 'acutalAmount'
							}, {
								name : 'SalaryPayoffForm.startTime',
								mapping : 'startTime'
							}, {
								name : 'SalaryPayoffForm.endTime',
								mapping : 'endTime'
							}, {
								name : 'SalaryPayoffForm.profileNo',
								mapping : 'profileNo'
							}, {
								name : 'SalaryPayoffForm.fullname',
								mapping : 'fullname'
							}, {
								name : 'SalaryPayoffForm.idNo',
								mapping : 'idNo'
							}, {
								name : 'SalaryPayoffForm.standAmount',
								mapping : 'standAmount'
							}, {
								name : 'SalaryPayoffForm.encourageAmount',
								mapping : 'encourageAmount'
							}, {
								name : 'SalaryPayoffForm.achieveAmount',
								mapping : 'achieveAmount'
							}, {
								name : 'SalaryPayoffForm.deductAmount',
								mapping : 'deductAmount'
							}, {
								name : 'SalaryPayoffForm.encourageDesc',
								mapping : 'encourageDesc'
							}, {
								name : 'SalaryPayoffForm.deductDesc',
								mapping : 'deductDesc'
							}, {
								name : 'SalaryPayoffForm.memo',
								mapping : 'memo'
							}, {
								name : 'SalaryPayoffForm.standardId',
								mapping : 'standardId'
							}]),
			items : [{
						name : 'salaryPayoff.recordId',
						id : 'SalaryPayoffForm.recordId',
						xtype : 'hidden',
						value : this.recordId == null ? '' : this.recordId
					}, {
//						fieldLabel : '所属员工',
						xtype : 'hidden',
						name : 'salaryPayoff.userId',
						id : 'SalaryPayoffForm.userId'
					}, {
//						fieldLabel : '登记时间',
						xtype : 'hidden',
						name : 'salaryPayoff.regTime',
						id : 'SalaryPayoffForm.regTime'
					}, {
//						fieldLabel : '登记人',
						xtype : 'hidden',
						name : 'salaryPayoff.register',
						id : 'SalaryPayoffForm.register'
					}, {
//						fieldLabel : '审批人',
						xtype : 'hidden',
						name : 'salaryPayoff.checkName',
						id : 'SalaryPayoffForm.checkName'
					}, {
//						fieldLabel : '审批时间',
						xtype : 'hidden',
						name : 'salaryPayoff.checkTime',
						id : 'SalaryPayoffForm.checkTime'
					}, {
//						fieldLabel : '审批状态',
						xtype : 'hidden',
						name : 'salaryPayoff.checkStatus',
						id : 'SalaryPayoffForm.checkStatus'
					}, {
//						fieldLabel : '实发金额',
						xtype : 'hidden',
						name : 'salaryPayoff.acutalAmount',
						id : 'SalaryPayoffForm.acutalAmount'
					}, {
						xtype : 'hidden',
						name : 'salaryPayoff.standardId',
						id : 'SalaryPayoffForm.standardId'
					},{
						xtype : 'fieldset',
						title : '发放时间段',
						layout : 'column',
						items : [{
									layout : 'form',
									border : false,
									columnWidth : .5,
									items : [{
												fieldLabel : '开始时间',
												xtype : 'datefield',
												format : 'Y-m-d',
												allowBlank : false,
												name : 'salaryPayoff.startTime',
												id : 'SalaryPayoffForm.startTime'
											}]
								}, {
									layout : 'form',
									border : false,
									columnWidth : .5,
									items : [{
												xtype : 'datefield',
												format : 'Y-m-d',
												fieldLabel : '结束时间',
												allowBlank : false,
												name : 'salaryPayoff.endTime',
												id : 'SalaryPayoffForm.endTime'
											}]
								}]
					}, {
						xtype : 'fieldset',
						title : '基本情况',
						// defaultType : 'textfield',
						layout : 'form',
						defaults : {
							anchor : '96%,96%'
						},
						items : [{
							xtype : 'container',
							layout : 'column',
							style : 'padding-bottom:3px;',
							anchor : '100%',
							items : [{
										columnWidth : .5,
										xtype : 'container',
										defaults : {
											anchor : '96%,96%'
										},
										style : 'padding-left:0px;',
										layout : 'form',
										items : [{
													fieldLabel : '档案编号',
													name : 'salaryPayoff.profileNo',
													// width : '50%',
													xtype : 'textfield',
													readOnly : true,
													allowBlank : false,
													id : 'SalaryPayoffForm.profileNo'
												}]
									}, {
										columnWidth : .5,
										xtype : 'button',
										autoWidth : true,
										text : '选择档案',
										iconCls : 'menu-profile',
										handler : function() {
											EmpProfileSelector.getView(
													function(array) {
														Ext.getCmp('SalaryPayoffForm.profileNo')
																.setValue(array[1]);
														Ext.getCmp('SalaryPayoffForm.fullname')
																.setValue(array[2]);
														Ext.getCmp('SalaryPayoffForm.standAmount')
																.setValue(array[9]);
														Ext.getCmp('SalaryPayoffForm.standardId').setValue(array[10]);
														Ext.getCmp('SalaryPayoffForm.idNo')
																.setValue(array[11]);
														Ext.getCmp('SalaryPayoffForm.userId')
																.setValue(array[12]);

													}).show();
										}
									}]
						}, {
							layout : 'column',
							xtype : 'container',
							items : [{
										xtype : 'container',
										layout : 'form',
										columnWidth : .5,
										defaults : {
											anchor : '96%,96%'
										},
										style : 'padding-left:0px;',
										defaultType : 'textfield',
										items : [{
													fieldLabel : '员工姓名',
													name : 'salaryPayoff.fullname',
													readOnly : true,
													allowBlank:false,
													id : 'SalaryPayoffForm.fullname'
												}]
									}, {
										xtype : 'container',
										layout : 'form',
										columnWidth : .5,
										defaults : {
											anchor : '96%,96%'
										},
										defaultType : 'textfield',
										items : [{
													fieldLabel : '身份证号',
													name : 'salaryPayoff.idNo',
													readOnly : true,
													id : 'SalaryPayoffForm.idNo'
												}]
									}]
						}]
					}, {
						xtype : 'fieldset',
						layout : 'column',
						title : '工资发放基本情况',
						items : [{
									layout : 'form',
									columnWidth : .5,
									xtype : 'container',
									items : [{
												fieldLabel : '薪标金额',
												xtype : 'numberfield',
												readOnly : true,
												allowBlank : false,
												name : 'salaryPayoff.standAmount',
												id : 'SalaryPayoffForm.standAmount',
												blankText : '薪标金额不能为空!'
											},{
												fieldLabel : '扣除金额',
												xtype : 'numberfield',
												allowBlank : false,
												name : 'salaryPayoff.deductAmount',
												id : 'SalaryPayoffForm.deductAmount',
												blankText : '扣除金额不能为空!'
											}]
								}, {
									layout : 'form',
									columnWidth : .5,
									xtype : 'container',
									items : [{
												fieldLabel : '奖励金额',
												xtype : 'numberfield',
												allowBlank : false,
												name : 'salaryPayoff.encourageAmount',
												id : 'SalaryPayoffForm.encourageAmount',
												blankText : '奖励金额不能为空!'
											}, {
												fieldLabel : '绩效工资',
												allowBlank : false,
												xtype : 'numberfield',
												name : 'salaryPayoff.achieveAmount',
												id : 'SalaryPayoffForm.achieveAmount',
												blankText : '绩效工资不能为空!'
											}]
								}

						]

					}, {
						xtype : 'fieldset',
						title : '发放说明',
						layout : 'form',
						defaultType : 'textarea',
						items : [{
									fieldLabel : '奖励描述',
									name : 'salaryPayoff.encourageDesc',
									anchor : '100%',
									id : 'SalaryPayoffForm.encourageDesc'
								}, {
									fieldLabel : '扣除描述',
									anchor : '100%',
									name : 'salaryPayoff.deductDesc',
									id : 'SalaryPayoffForm.deductDesc'
								}, {
									fieldLabel : '备注',
									anchor : '100%',
									name : 'salaryPayoff.memo',
									id : 'SalaryPayoffForm.memo'
								}]
					}]
		});
		// 加载表单对应的数据
		if (this.recordId != null && this.recordId != 'undefined') {
			this.formPanel.getForm().load({
				deferredRender : false,
				url : __ctxPath + '/hrm/getSalaryPayoff.do?recordId='
						+ this.recordId,
				waitMsg : '正在载入数据...',
				success : function(form, action) {
					var pay=Ext.util.JSON.decode(action.response.responseText).data[0];
					Ext.getCmp('SalaryPayoffForm.startTime').setValue(new Date(getDateFromFormat(pay.startTime,'yyyy-MM-dd HH:mm:ss')));
					Ext.getCmp('SalaryPayoffForm.endTime').setValue(new Date(getDateFromFormat(pay.endTime,'yyyy-MM-dd HH:mm:ss')));
				},
				failure : function(form, action) {
				}
			});
		}
		// 初始化功能按钮
		this.toolbars = [{
					text : '保存',
					iconCls : 'btn-save',
					handler : this.save.createCallback(this.formPanel)
				}, {
					text : '重置',
					iconCls : 'btn-reset',
					handler : this.reset.createCallback(this.formPanel)
				}, {
					text : '取消',
					iconCls : 'btn-cancel',
					handler : this.cancel.createCallback(this)
				}];
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
	 *            window
	 */
	cancel : function(panel) {
		var tabs = Ext.getCmp('centerTabPanel');
		if (panel != null) {
			tabs.remove('SalaryPayoffForm');
		}
	},
	/**
	 * 保存记录
	 */
	save : function(formPanel) {
		if (formPanel.getForm().isValid()) {
			formPanel.getForm().submit({
						method : 'POST',
						waitMsg : '正在提交数据...',
						success : function(fp, action) {
							Ext.ux.Toast.msg('操作信息', '成功保存信息！');
							var gridPanel = Ext.getCmp('SalaryPayoffGrid');
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
	}// end of save

});