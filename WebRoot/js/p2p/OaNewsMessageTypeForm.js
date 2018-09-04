//OaNewsMessageTypeForm.js
OaNewsMessageTypeForm = Ext.extend(Ext.Window, {
	// 内嵌FormPanel
	formPanel : null,
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		OaNewsMessageTypeForm.superclass.constructor.call(this, {
			layout : 'fit',
			id : 'OaNewsMessageTypeForm'+this.nodeKey,
			iconCls : 'menu-dictionary',
			items:this.formPanel,
			title : '站内信类型',
			width : 380,
			height : 280,
			modal : true,
			buttonAlign : 'center',
			buttons : this.buttons,
			keys : {
				key : Ext.EventObject.ENTER,
				fn : this.save.createCallback(this.formPanel, this),
				scope : this
			}
		});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		this.formPanel = new Ext.FormPanel({
			url : __ctxPath + '/system/saveTypeDictionary.do',
			layout : 'form',
			id : 'DictionaryForm',
			bodyStyle:'padding:5px',
			border : false,
			labelAlign :'right',
			formId : 'DictionaryFormId',
			defaultType : 'textfield',
			defaults:{
				anchor:'98%,98%'
			},
			items : [{
						name : 'dictionary.dicId',
						xtype : 'hidden'
					}, {
						xtype:'hidden',
						value:this.nodeKey,
						name:'dicKey'
					},{
						xtype:'hidden',
						name:'dictionary.status',
						value:'0'
					},{
						fieldLabel : '数值',
						name : 'dictionary.itemValue',
						allowBlank : false
					},{
						fieldLabel : '序号',
						readOnly:this.readOnly,
						name:'dictionary.sn'
					},{
						fieldLabel : 'Key',
						name : 'dictionary.dicKey',
						readOnly:this.readOnly,
						allowBlank : false
					}, {
						xtype : 'textarea',
						fieldLabel : '备注',
						name : 'dictionary.descp'
					}

			]
		});

		if (this.dicId != null && this.dicId != 'undefined') {
			this.formPanel.loadData({
										url : __ctxPath + "/system/getDictionary.do?dicId="+ this.dicId ,
										root : 'data',
										preName : 'dictionary',
										success : function(response, options) {
											
										}
									});
			
			
		};

		// 初始化功能按钮
		this.buttons = [{
					text : '保存',
					iconCls : 'btn-save',
					handler : this.save.createCallback(this.formPanel, this)
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
	cancel : function(window) {
		window.close();
	},
	/**
	 * 保存记录
	 */
	save : function(formPanel, window) {
		var callback=window.callback;
		var panel=this.gridPanel;
		if (formPanel.getForm().isValid()) {
			formPanel.getForm().submit({
						method : 'POST',
						waitMsg : '正在提交数据...',
						success : function(fp, action) {
				            if(action.response.responseText.toString().trim()=="{success:true,mag:false}"){
				            	Ext.ux.Toast.msg('操作信息', '保存出错！');
				            	Ext.getCmpByName("dicKey").setValue("");
				            	
				            }else{
								Ext.ux.Toast.msg('操作信息', '成功保存信息！');
								if(window.callback){
									window.callback.call(this);
								}
								panel.getStore().reload();
								window.close();
								
				            }
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
	}// end of save
});