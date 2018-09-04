/**
 * @author
 * @createtime
 * @class SuggestBoxForm
 * @extends Ext.Window
 * @description SuggestBox表单
 * @company 智维软件
 */
SuggestBoxForm = Ext.extend(Ext.Window, {
	// 内嵌FormPanel
	formPanel : null,
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		SuggestBoxForm.superclass.constructor.call(this, {
					id : 'SuggestBoxFormWin',
					layout : 'fit',
					items : this.formPanel,
					modal : true,
					height : 550,
					iconCls : 'menu-suggestbox',
					width : 735,
					maximizable : true,
					title : '意见详细信息',
					buttonAlign : 'center',
					buttons : this.buttons
				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		//if(){}
		this.formPanel = new Ext.FormPanel({
			layout : 'form',
			bodyStyle : 'padding:10px 10px 10px 10px',
			border : false,
			url : __ctxPath + '/info/saveSuggestBox.do',
			id : 'SuggestBoxForm',
			defaults : {
				anchor : '95%,95%'
			},
			autoScroll : true,
			defaultType : 'textfield',
			items : [{
						name : 'suggestBox.boxId',
						id : 'boxId',
						xtype : 'hidden',
						value : this.boxId == null ? '' : this.boxId
					},{
						name : 'suggestBox.status',
						id : 'status',
						xtype : 'hidden',
						value : 0
					}, {
						xtype : 'radiogroup',
						// id:'SuggestBoxSign',
						fieldLabel : '签名方式',
						autoHeight : true,
						columns : 2,
						width : 520,
						items : [{
							boxLabel : '使用签名',
							name : 'SuggestBoxSign',
							inputValue : 1,
							id : 'isSign1',
							checked : true,
							handler : function(value) {
								if (value.getValue() != true) {
									Ext.getCmp('PersonalInfo').hide();
									Ext.getCmp('senderFullname').setValue('');
									Ext.getCmp('senderId').setValue('');
								} else {
									Ext.getCmp('PersonalInfo').show();
									if (curUserInfo != null) {
										Ext.getCmp('senderFullname')
												.setValue(curUserInfo.fullname);
										Ext.getCmp('senderId')
												.setValue(curUserInfo.userId);
									}
								}
							}
						}, {
							boxLabel : '匿名',
							name : 'SuggestBoxSign',
							inputValue : 0,
							id : 'isSign0'
						}]
					}, {
						xtype : 'fieldset',
						title : '个人信息',
						id : 'PersonalInfo',
						defaults : {
							anchor : '95%,95%'
						},
						defaultType : 'textfield',
						layout : 'form',
						items : [{
							xtype : 'container',
							layout : 'column',
							height : 27,
							defaultType : 'textfield',
							items : [{
										xtype : 'label',
										text : '发送人',
										width : 103
									}, {
										name : 'suggestBox.senderFullname',
										id : 'senderFullname',
										value : curUserInfo !=null ? curUserInfo.fullname: null
									}, {
										xtype : 'label',
										text : '联系电话',
										width : 103
									}, {
										name : 'suggestBox.phone',
										id : 'phone'
									}]
						}, {
							fieldLabel : 'Email',
							name : 'suggestBox.email',
							id : 'email'
						}]
					}, {
						fieldLabel : '意见标题',
						allowBlank : false,
						name : 'suggestBox.subject',
						id : 'subject'
					}, {
						fieldLabel : '意见内容',
						name : 'suggestBox.content',
						id : 'content',
						allowBlank : false,
						xtype : 'fckeditor'
					}, {
						fieldLabel : '发送人ID',
						name : 'suggestBox.senderId',
						id : 'senderId',
						xtype : 'hidden',
						value : curUserInfo !=null ? curUserInfo.userId : null
					}, {
						xtype : 'radiogroup',
						fieldLabel : '是否公开',
						autoHeight : true,
						columns : 2,
						width : 520,
						items : [{
							boxLabel : '公开',
							name : 'suggestBox.isOpen',
							inputValue : 0,
							id : 'isOpen1',
							checked : true,
							handler : function(value) {
								if (value.getValue() == true) {
									Ext.getCmp('SuggestBoxFormQueryPwd').hide();
								} else {
									Ext.getCmp('SuggestBoxFormQueryPwd').show();
								}
							}
						}, {
							boxLabel : '不公开',
							name : 'suggestBox.isOpen',
							inputValue : 1,
							id : 'isOpen0'
						}]
					}, {
						xtype : 'fieldset',
						title : '查询密码（选填）',
						hidden : true,
						defaults : {
							anchor : '95%,95%'
						},
						id : 'SuggestBoxFormQueryPwd',
						defaultType : 'textfield',
						layout : 'column',
						items : [{
									xtype : 'label',
									text : '查询密码',
									width  : 103
								}, {
									name : 'suggestBox.queryPwd',
									id : 'queryPwd',
									inputType : 'password'
								}, {
									xtype : 'label',
									text : '密码确认',
									width : 103
								}, {
									name : 'SureQueryPwd',
									id : 'SureQueryPwd',
									inputType : 'password',
									listeners : {
										change : function(field,newValue,oldValue){
											var queryPwd = Ext.getCmp('queryPwd');
											if(queryPwd.getValue() != field.getValue()){
												Ext.Msg.alert('注意','两次输入的密码不一致,请重新填写.');
												queryPwd.setValue('');
												field.setValue('');
											}
										}
									}
								}]
					}

			]
		});
		// 加载表单对应的数据
		if (this.boxId != null && this.boxId != 'undefined') {
			this.formPanel.loadData({
						url : __ctxPath + '/info/getSuggestBox.do?boxId='
								+ this.boxId,
						preName : 'suggestBox',
						root : 'data'
					});
		}
		// 初始化功能按钮
		this.buttons = [{
					text : '提交',
					iconCls : 'btn-ok',
					scope : this,
					handler : this.draft
				},{
					text : '保存',
					iconCls : 'btn-save',
					scope : this,
					handler : this.save.createCallback(this.formPanel, this)
				}, {
					text : '重置',
					iconCls : 'btn-reset',
					scope : this,
					handler : this.reset.createCallback(this.formPanel)
				}, {
					text : '取消',
					iconCls : 'btn-cancel',
					scope : this,
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
	cancel : function(window) {
		window.close();
	},
	/**
	 * 保存记录
	 */
	save : function(formPanel, window) {
		var content = formPanel.getCmpByName('suggestBox.content').getValue();
		if(content == '' || content == null || content == 'undefinded'){
			Ext.ux.Toast.msg('操作信息', '意见内容不能为空！');
			return ;
		}
		if (formPanel.getForm().isValid()) {
			formPanel.getForm().submit({
						method : 'POST',
						waitMsg : '正在提交数据...',
						success : function(fp, action) {
							Ext.ux.Toast.msg('操作信息', '成功保存信息！');
							var gridPanel = Ext.getCmp('SuggestBoxGrid');
							if (gridPanel != null) {
								gridPanel.getStore().reload();
							}
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
	},// end of save
	draft : function(){
		Ext.getCmp('status').setValue('1');
		this.save(this.formPanel,this);
	}//end of draft
});