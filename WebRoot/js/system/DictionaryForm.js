/**
 * @author
 * @createtime
 * @class DictionaryForm
 * @extends Ext.Window
 * @description DictionaryForm表单
 * @company 智维软件
 */
DictionaryForm = Ext.extend(Ext.Window, {
	// 内嵌FormPanel
	formPanel : null,
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		DictionaryForm.superclass.constructor.call(this, {
			layout : 'fit',
			id : 'DictionaryFormWin',
			iconCls : 'menu-dictionary',
			items:this.formPanel,
			title : '字典详细信息',
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
			url : __ctxPath + '/system/saveDictionary.do',
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
						id : 'dicId',
						xtype : 'hidden',
						value : this.dicId == null ? '' : this.dicId
					}, {
						xtype:'hidden',
						value:this.parentId,
						name:'parentId'
					},{
						xtype:'hidden',
						id:'itemName',
						name:'dictionary.itemName',
						value:this.typeName
					},{
						xtype:'hidden',
						id:'status',
						name:'dictionary.status',
						value:'0'
					},
					{
						fieldLabel:'所属分类',
						xtype:'label',
						text:this.typeName
					},
					{
						fieldLabel : '数值',
						name : 'dictionary.itemValue',
						allowBlank : false,
						id : 'itemValue'
					},{
						fieldLabel : '序号',
						name:'dictionary.sn',
						allowBlank : false,
						id : 'sn'
					},{
						fieldLabel : 'Key',
						name : 'dictionary.dicKey',
						allowBlank : false,
						id : 'dicKey'
					}, {
						fieldLabel : '备注',
						name : 'dictionary.descp',
						id : 'descp',
						xtype : 'textarea'
					}

			]
		});

		if (this.dicId != null && this.dicId != 'undefined') {
			this.formPanel.getForm().load({
				deferredRender : false,
				url : __ctxPath + '/system/getDictionary.do?dicId='
						+ this.dicId,
				waitMsg : '正在载入数据...',
				success : function(form, action) {
					// Ext.Msg.alert('编辑', '载入成功！');
				},
				failure : function(form, action) {
					// Ext.Msg.alert('编辑', '载入失败');
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
		if (formPanel.getForm().isValid()) {
			formPanel.getForm().submit({
						method : 'POST',
						waitMsg : '正在提交数据...',
						success : function(fp, action) {
				            if(action.response.responseText.toString().trim()=="{success:true,mag:false}"){
				            	Ext.ux.Toast.msg('操作信息', '数据字典项的Key值不能重复，请重新输入！');
				            	Ext.getCmp("dicKey").setValue("");
				            }else{
								Ext.ux.Toast.msg('操作信息', '成功保存信息！');
								if(window.callback){
									window.callback.call(this);
								}
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
