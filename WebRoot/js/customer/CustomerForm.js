/**
 * @author
 * @createtime
 * @class CustomerForm
 * @extends Ext.Window
 * @description CustomerForm表单
 * @company 智维软件
 */
CustomerForm = Ext.extend(Ext.Window, {
	// 内嵌FormPanel
	formPanel : null,
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		CustomerForm.superclass.constructor.call(this, {
					layout : 'fit',
					id : 'CustomerFormWin',
					title : '客户详细信息',
					iconCls : 'menu-customerView',
					width : 600,
					height : 500,
					minWidth : 599,
					minHeight : 499,
					items:this.formPanel,
					maximizable : true,
					border : false,
					modal : true,
					plain : true,
					//bodyStyle : 'padding:5px;',
					buttonAlign : 'center',
					buttons : this.buttons,
					listeners : {
						'afterrender' : function(w) {
							Ext.getCmp('getNoButton').hide();
						}
					}
				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		this.formPanel = new Ext.FormPanel({
		url : __ctxPath + '/customer/saveCustomer.do',
		layout : 'form',
		id : 'CustomerForm',
		bodyStyle : 'padding:5px;',
		frame : false,
		defaults : {
			widht : 400,
			anchor : '96%,96%'
		},
		formId : 'CustomerFormId',
		defaultType : 'textfield',
		items : [{
					name : 'customer.customerId',
					id : 'customerId',
					xtype : 'hidden',
					value : this.customerId == null ? '' : this.customerId
				}, {
						xtype : 'compositefield',
						fieldLabel : '客户编号*',
						items : [ {
								xtype : 'textfield',
								width : 207,
								name : 'customer.customerNo',
								id : 'customerNo',
								allowBlank:false,
								blankText:'客户编号不能为空!',
								listeners:{
									change : function(cusotmerNo,newvalue,oldvalue){
										if(newvalue !=null && newvalue !=''&& newvalue !='undefined'){
											Ext.Ajax.request({
													url : __ctxPath
															+ '/customer/checkCustomer.do',
													params : {
														customerNo : newvalue
													},
													method : 'post',
													success : function(response) {
														var result = Ext.util.JSON.decode(response.responseText);
														if (result.pass) {
															Ext.getCmp('CheckMessage').body.update('<img src="' + __ctxPath
																		+ '/images/flag/customer/passcheck.png" />可用');
														} else {
															Ext.getCmp('CheckMessage').body.update('<img src="' + __ctxPath
																		+ '/images/flag/customer/invalid.png" />不可用');
														}
													},
													failure : function() {
														//.......
													}
												});
										}
									}
								}
							},{
								id : 'getNoButton',
								xtype : 'button',
								text : '系统生成',
								iconCls:'btn-system-setting',
								handler : function() {
									Ext.Ajax.request({
										url : __ctxPath
												+ '/customer/numberCustomer.do',
										success : function(response) {
											var result = Ext.util.JSON.decode(response.responseText)
											Ext.getCmp('customerNo').setValue(result.customerNo);
											Ext.getCmp('CheckMessage').body.update('');
										}
									})
								}
							},{
								id : 'CheckMessage',
								xtype:'panel',
								border :false,
								style:'padding-top:3px'
								//html:'something'
							} ]
				}, {
					fieldLabel : '客户名称<a style="color:red;">*</a>',
					name : 'customer.customerName',
					id : 'customerName',
					allowBlank:false,
					blankText:'客户名称不能为空!'
				}, {
						xtype : 'compositefield',
						fieldLabel : '客户经理*',
						items : [ {
								xtype : 'textfield',
								width : 270,
								readOnly : true,
								allowBlank:false,
								blankText:'客户经理不能为空!',
								name : 'customer.customerManager',
								id : 'customerManager'
							}, {
								xtype : 'button',
								text : '选择经理',
								iconCls:'btn-mail_recipient',
								handler : function() {
									UserSelector.getView(
											function(userIds, fullnames) {
												var customerManager = Ext
														.getCmp('customerManager');
												customerManager
														.setValue(fullnames);
											}, true).show();
								}
							}]
				}, {
					fieldLabel : '客户电话<a style="color:red;">*</a>',
					name : 'customer.phone',
					xtype : 'textfield',
					id : 'phone',
					allowBlank:false,
					blankText:'客户电话不能为空!'
				}, {
					fieldLabel : '所属行业<a style="color:red;">*</a>',//有缺省的选择，也可以输入',
					name : 'customer.industryType',
					id : 'industryType',
					allowBlank:false,
					blankText:'客户所属行业不能为空!',
					xtype : 'combo',
					mode : 'local',
					editable : true,
					triggerAction : 'all',
					store : this.initIndustryStore()
				}, {
					fieldLabel : '客户来源<a style="color:red;">*</a>', //可编辑，可添加',
					hiddenName : 'customer.customerSource',
					id : 'customerSource',
					xtype : 'combo',
					allowBlank:false,
					blankText:'客户来源不能为空!',
					mode : 'local',
					editable : true,
					triggerAction : 'all',
					store : [['1', '电话访问'], ['2', '网络'], ['3', '客户或朋友介绍']]
				}, {
					fieldLabel : '客户类型<a style="color:red;">*</a>',
					hiddenName : 'customer.customerType',
					id : 'customerType',
					xtype : 'combo',
					mode : 'local',
					editable : false,
					triggerAction : 'all',
					store : [['1', '正式客户'], ['2', '重要客户'], ['3', '潜在客户'],
							['4', '无效客户']],
					value : '1'
				}, {
					fieldLabel : '访问权限<a style="color:red;">*</a>',//1=客户经理及上级经理有权查看2=公开 3=共享人员有权查看',
					hiddenName : 'customer.rights',
					id : 'rights',
					xtype : 'combo',
					mode : 'local',
					editable : false,
					triggerAction : 'all',
					store : [['1', '客户经理及上级经理有权查'], ['2', '公开'],
							['3', '共享人员有权查看']],
					value : '1'
				}, {
					xtype : 'compositefield',
						fieldLabel : '所属省',
						items : [ {
								//fieldLabel : '所属省',
								name : 'customer.state',
								id : 'state',
								maxHeight : 200,
								width : 127,
								xtype : 'combo',
								mode : 'local',
								editable : false,
								triggerAction : 'all',
								store : new Ext.data.SimpleStore({
											autoLoad : true,
											url : __ctxPath
													+ '/system/listRegion.do',
											fields : ['regionId', 'regionName']
										}),
								displayField : 'regionName',
								valueField : 'regionId',
								listeners : {
									select : function(combo, record, index) {
										Ext.Ajax.request({
											url : __ctxPath
													+ '/system/listRegion.do',
											params : {
												regionId : combo.value
											},
											method : 'post',
											success : function(response) {
												var result = Ext.util.JSON
														.decode(response.responseText)
												Ext.getCmp('city').getStore()
														.loadData(result);
												Ext.getCmp('city').setValue('');
											}
										})
									}
								}
							}, {
								xtype : 'displayfield',
								value : '所属市:',
								width : 80
							}, {
								//fieldLabel : '所属市',
								name : 'customer.city',
								id : 'city',
								maxHeight : 200,
								width : 128,
								xtype : 'combo',
								mode : 'local',
								editable : false,
								triggerAction : 'all',
								store : [],
								displayField : 'regionName',
								valueField : 'regionId'
							}]
				}, {
					xtype : 'tabpanel',
					activeTab : 0,//激活第一个panel
					plain:true,
					height : 195,
					defaultType : 'panel',
					bodyStyle : 'padding:5px;',
					items : [{
								title : '联系方式',
								layout : 'form',
								defaultType : 'textfield',
								defaults : {
									widht : 400,
									anchor : '96%,96%'
								},
								items : [{
											fieldLabel : '单位负责人',//联系人
											name : 'customer.principal',
											id : 'principal'
										}, {
											fieldLabel : '传真',
											name : 'customer.fax',
											xtype : 'numberfield',
											id : 'fax'
										}, {
											fieldLabel : '网址',
											name : 'customer.site',
											id : 'site'
										}, {
											fieldLabel : 'E-mail',
											name : 'customer.email',
											id : 'email',
											vtype : 'email',
											vtypeText : '邮箱格式不正确!'
										}, {
											fieldLabel : '邮编',
											name : 'customer.zip',
											xtype : 'numberfield',
											id : 'zip'
										}, {
											fieldLabel : '地址',
											name : 'customer.address',
											id : 'address'
										}]
							}, {
								title : '公司信息',
								layout : 'form',
								defaults : {
									widht : 400,
									anchor : '96%,96%'
								},
								defaultType : 'textfield',
								items : [{
									fieldLabel : '公司规模',
									hiddenName : 'customer.companyScale',
									id : 'companyScale',
									xtype : 'combo',
									mode : 'local',
									editable : false,
									triggerAction : 'all',
									store : [['1', '1-20人'], ['2', '20-50人'],
											['3', '50-100人'],
											['4', '100-200人'],
											['5', '200-500人'],
											['6', '500-1000人'],
											['7', '1000人以上']]
								}, {
									xtype : 'container',
									layout : 'column',
									height : 26,
									items : [{
												xtype : 'label',
												text : '注册资金:',
												width : 104
											}, {
												xtype : 'textfield',
												width : 270,
												xtype : 'numberfield',
												name : 'customer.registerFun',
												id : 'registerFun'
											}, {
												name : 'customer.currencyUnit',
												width : 70,
												id : 'currencyUnit',
												xtype : 'combo',
												mode : 'local',
												editable : false,
												triggerAction : 'all',
												store : ['人民币', '美元'],
												value : '人民币'
											}]
								}, {
									xtype : 'container',
									layout : 'column',
									height : 26,
									items : [{
												xtype : 'label',
												text : '年营业额:',
												width : 104
											}, {
												xtype : 'textfield',
												width : 270,
												xtype : 'numberfield',
												name : 'customer.turnOver',
												id : 'turnOver'
											}, {
												xtype : 'label',
												text : '(单位同上)'
											}]
								}, {
									fieldLabel : '开户银行',
									name : 'customer.openBank',
									id : 'openBank'
								}, {
									fieldLabel : '银行账号',//数字
									xtype : 'numberfield',
									name : 'customer.accountsNo',
									id : 'accountsNo'
								}, {
									fieldLabel : '税号',
									name : 'customer.taxNo',
									id : 'taxNo'
								}]
							}, {
								title : '其它信息',
								layout : 'form',
								defaultType : 'textarea',
								defaults : {
									widht : 400,
									anchor : '96%,96%'
								},
								items : [{
											fieldLabel : '公司描述',
											name : 'customer.otherDesc',
											id : 'otherDesc'
										}, {
											fieldLabel : '备注',
											name : 'customer.notes',
											id : 'notes'
										}]

							}]
				}

		]
	});

	if (this.customerId != null && this.customerId != 'undefined') {
//		Ext.getCmp('getNoButton').hide();
		this.formPanel.getForm().load({
			deferredRender : false,
			url : __ctxPath + '/customer/getCustomer.do?customerId='
					+ this.customerId,
			waitMsg : '正在载入数据...',
			success : function(form, action) {
				//已成的客户号不可改
				Ext.getCmp('customerNo').getEl().dom.readOnly = true;
			},
			failure : function(form, action) {
				Ext.ux.Toast.msg('编辑', '载入失败');
			}
		});
	}
		
		this.buttons = [{
					text : '保存',
					iconCls:'btn-save',
					handler : function() {
						var fp = Ext.getCmp('CustomerForm');
						if (fp.getForm().isValid()) {
							fp.getForm().submit({
								method : 'post',
								waitMsg : '正在提交数据...',
								success : function(fp, action) {
									Ext.getCmp('CustomerGrid').getStore()
											.reload();
									var customerId = Ext.getCmp('customerId').value;
									if(customerId ==null || customerId =='' || customerId == 'undefined'){
										customerId = action.result.customerId;
										Ext.Msg.confirm('操作信息','客户信息保存成功！是否为客户增加联系人?',function(btn){
											if(btn=='yes'){
												CustomerView.addLinkman(customerId);
											}
										});
									}
									Ext.getCmp('CustomerFormWin').close();
								},
								failure : function(fp, action) {
									Ext.MessageBox.show({
												title : '操作信息',
												msg : action.result.msg,
												buttons : Ext.MessageBox.OK,
												icon : 'ext-mb-error'
											});
									if (action.result.rewrite) {
										Ext.getCmp('customerNo').setValue('');
									}
								}
							});
						}
					}
				}, {
					text : '取消',
					iconCls:'btn-cancel',
					handler : function() {
						Ext.getCmp('CustomerFormWin').close();
					}
				}];//end of the buttons
				
	}//end of the initUIComponents
});

CustomerForm.prototype.initIndustryStore = function() {
	var storeData = ['照明工业', '电子元器件', '传媒、广电', '安全、防护', '包装', '纸业', '办公、文教',
			'数码、电脑', '电工电气', '纺织、皮革', '服装', '服饰', '机械及行业设备', '五金、工具', '化工',
			'精细化学品', '橡塑', '环保', '仪器仪表', '家居用品', '家用电器', '建筑、建材', '交通运输',
			'礼品、工艺品、饰品', '能源', '农业', '汽摩及配件', '食品、饮料', '通信产品', '玩具', '冶金矿产',
			'医药、保养', '印刷', '运动、休闲', '商务服务', '项目合作', '二手设备转让', '加工', '代理',
			'库存积压'];
	return storeData;
}
