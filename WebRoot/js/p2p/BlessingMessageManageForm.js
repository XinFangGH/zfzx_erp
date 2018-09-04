//OaNewsMessageTypeForm.js
BlessingMessageManageForm = Ext.extend(Ext.Window, {
	// 内嵌FormPanel
	formPanel : null,
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
        BlessingMessageManageForm.superclass.constructor.call(this, {
			layout : 'fit',
			id : 'BlessingMessageManageForm',
			iconCls : 'menu-dictionary',
			items:this.formPanel,
			title : '节日短信',
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
			url : __ctxPath + '/p2p/saveOaHolidayMessage.do',
			layout : 'form',
			id : 'BlessingMessageManageForm',
			bodyStyle:'padding:5px',
			border : false,
			labelAlign :'right',
			formId : 'BlessingMessageManageFormId',
			defaultType : 'textfield',
			defaults:{
				anchor:'98%,98%'
			},
			items : [{
						name : 'oaHolidayMessage.id',
						xtype : 'hidden'
					},{
						fieldLabel : '节日名称',
						name : 'oaHolidayMessage.holidayName',
						allowBlank : false
					}, {
						xtype : (this.isReadOnly==true?'datefield':'datetimefield'),
						allowBlank : false,
               			editable : false,
						format : 'Y-m-d H:i:s',
						fieldLabel : '节日日期',
						name : 'oaHolidayMessage.holidayDate'
					},{
						fieldLabel : '状态',
				   		xtype : 'combo',
                		hiddenName:'oaHolidayMessage.state',
						allowBlank : false,
               			editable : false,
						mode : 'local',
               			triggerAction : 'all',
						displayField : 'name',
						valueField : 'id',
						store : new Ext.data.SimpleStore({
							fields : ["name", "id"],
							data : [['激活', '1'],['关闭', '0']]
						}),
						readOnly:this.readOnly
					},{
						fieldLabel : '类型',
						xtype : 'combo',
						allowBlank : false,
						editable : false,
						mode : 'local',
						triggerAction : 'all',
						displayField : 'name',
						valueField : 'id',
						store : new Ext.data.SimpleStore({
							fields : ["name", "id"],
							data : [['节日短信', '1'],['生日短信', '2']]
						}),
						hiddenName : 'oaHolidayMessage.type',
						readOnly:this.readOnly,
					}, {
						xtype : 'textarea',
						fieldLabel : '消息内容',
						name : 'oaHolidayMessage.message'
					},{
						xtype : "panel",
						border : false,
						layout : "column",
						html : '<font color="red">用户姓名请用{name},登陆名请用{loginName},手机号码请用{telphone}分别代替</br>注意：节日短信不支持</font>'
					}
			]
		});

		if (this.id != null && this.id != 'undefined') {
			this.formPanel.loadData({
				url : __ctxPath + "/p2p/getOaHolidayMessage.do?id="+ this.id,
				root : 'data',
				preName : 'oaHolidayMessage',
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
								Ext.ux.Toast.msg('操作信息', '成功保存信息！');
                                var window = Ext.getCmp('BlessingMessageManageForm');
                                window.close();
                                var gridPanel = Ext.getCmp('BlessingMessageManagePanel');
                                if (gridPanel != null) {
                                    gridPanel.getStore().reload();
				            }
						},
						failure : function(fp, action) {
							var Msg = "信息保存出错，请联系管理员！";
                            var obj=Ext.util.JSON.decode(action.response.responseText);
                            if(obj.message != null && obj.message != "" && obj.message != "undefined"){
                                Msg = obj.message
                            }
							Ext.MessageBox.show({
										title : '操作信息',
										msg : Msg,
										buttons : Ext.MessageBox.OK,
										icon : Ext.MessageBox.ERROR
									});
							window.close();
						}
					});
		}
	}// end of save
});