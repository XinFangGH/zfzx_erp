/**
 * @author
 * @createtime
 * @class ProviderForm
 * @extends Ext.Window
 * @description ProviderForm表单
 * @company 智维软件
 */
ProviderForm = Ext.extend(Ext.Window, {
	// 内嵌FormPanel
	formPanel : null,
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		ProviderForm.superclass.constructor.call(this, {
					layout : 'fit',
					id : 'ProviderFormWin',
					title : '供应商详细信息',
					iconCls : 'menu-provider',
					width : 500,
					height : 395,
					minWidth : 499,
					minHeight : 394,
					items:this.formPanel,
					maximizable : true,
					border : false,
					modal : true,
					plain : true,
					buttonAlign : 'center',
					buttons : this.buttons
				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		//初始化form表单
		this.formPanel =  new Ext.FormPanel({
				url : __ctxPath + '/customer/saveProvider.do',
				layout : 'form',
				id : 'ProviderForm',
				bodyStyle : 'padding : 5px;',
				frame : false,
				defaults : {
					width : 400,
					anchor : '98%,98%'
				},
				formId : 'ProviderFormId',
				defaultType : 'textfield',
				items : [{
							name : 'provider.providerId',
							id : 'providerId',
							xtype : 'hidden',
							value : this.providerId == null
									? ''
									: this.providerId
						}, {
							fieldLabel : '供应商名称',
							name : 'provider.providerName',
							id : 'providerName',
							allowBlank : false,
							blankText : '供应商名称不能为空!'
						}, {
							fieldLabel : '联系人',
							name : 'provider.contactor',
							id : 'contactor',
							allowBlank : false,
							blankText : '联系人姓名不能为空!'
						}, {
							fieldLabel : '电话',
							name : 'provider.phone',
							id : 'phone',
							allowBlank : false,
							blankText : '电话不能为空!'
						}, {
							fieldLabel : '地址',
							name : 'provider.address',
							id : 'address',
							allowBlank : false,
							blankText : '地址不能为空!'
						}, {
							fieldLabel : '等级',
							hiddenName : 'provider.rank',
							id : 'rank',
							xtype : 'combo',
							mode : 'local',
							editable : false,
							triggerAction : 'all',
							store : [['1', '一级供应商'],
									['2', '二级供应商'],
									['3', '三级供应商'],
									['4', '四级供应商']],
							value : 1
						}, {
							xtype : 'tabpanel',
							activeTab : 0,// 激活第一个panel
							border : false,
							plain : true,
							height : 195,
							defaultType : 'panel',
							bodyStyle : 'background-color : transparent;',
							items : [{
										title : '联系方式',
										layout : 'form',
										defaultType : 'textfield',
										defaults : {
											widht : 400,
											anchor : '96%,96%'
										},
										items : [{
													fieldLabel : '传真',
													name : 'provider.fax',
													id : 'fax'
												}, {
													fieldLabel : '网址',
													name : 'provider.site',
													id : 'site'
												}, {
													fieldLabel : '邮箱',
													name : 'provider.email',
													id : 'email',
													vtype : 'email',
													vtypeText : '邮箱格式不正确!'
												}, {
													fieldLabel : '邮编',
													name : 'provider.zip',
													id : 'zip',
													xtype:'numberfield'
												}, {
													fieldLabel : '开户银行',
													name : 'provider.openBank',
													id : 'openBank'
												}, {
													fieldLabel : '银行账号',
													name : 'provider.account',
													id : 'account'
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
													fieldLabel : '备注',
													name : 'provider.notes',
													id : 'notes',
													height : 146
												}]
									}]
						}

				]
			});//end of the formPanel

	if (this.providerId != null && this.providerId != 'undefined') {
		this.formPanel.getForm().load({
			deferredRender : false,
			url : __ctxPath + '/customer/getProvider.do?providerId='
					+ this.providerId,
			waitMsg : '正在载入数据...',
			success : function(form, action) {
			},
			failure : function(form, action) {
				Ext.ux.Toast.msg('编辑', '载入失败');
			}
		});
	}
		
		this.buttons = [{
					text : '保存',
					iconCls : 'btn-save',
					handler : function() {
						var fp = Ext.getCmp('ProviderForm');
						if (fp.getForm().isValid()) {
							fp.getForm().submit({
								method : 'post',
								waitMsg : '正在提交数据...',
								success : function(fp, action) {
									Ext.ux.Toast.msg('操作信息', '成功保存信息！');
									Ext.getCmp('ProviderGrid').getStore()
											.reload();
									Ext.getCmp('ProviderFormWin').close();
								},
								failure : function(fp, action) {
									Ext.MessageBox.show({
												title : '操作信息',
												msg : '信息保存出错，请联系管理员！',
												buttons : Ext.MessageBox.OK,
												icon : 'ext-mb-error'
											});
									Ext.getCmp('ProviderFormWin').close();
								}
							});
						}
					}
				}, {
					text : '取消',
					iconCls : 'btn-cancel',
					handler : function() {
						Ext.getCmp('ProviderFormWin').close();
					}
				}];//end of the buttons
	}//end of the initUIComponents
});

