/**
 * @author
 * @createtime
 * @class WebTemplateMessageForm
 * @extends Ext.Window
 * @description WebTemplateMessage表单
 * @company 智维软件
 */
WebTemplateMessageForm = Ext.extend(Ext.Window, {
	type : 0,
	titlePrefix : '',
	id : null,
	// 构造函数
	constructor : function(_cfg) {
		
		if (typeof(_cfg.type) != "undefined") {
			this.type = parseInt(_cfg.type);
		}
		if (typeof(_cfg.titlePrefix) != "undefined") {
			this.titlePrefix = _cfg.titlePrefix;
		}
		if (typeof(_cfg.id) != "undefined") {
			this.id = parseInt(_cfg.id);
		}
					
		Ext.applyIf(this, _cfg);
		
		// 必须先初始化组件
		this.initUIComponents();
		WebTemplateMessageForm.superclass.constructor.call(this, {
					id : 'WebTemplateMessageFormWin',
					layout : 'fit',
					items : this.formPanel,
					modal : true,
					height : 400,
					width : 500,
					maximizable : true,
					title : this.titlePrefix+(this.id == null ? '添加' : '编辑'),
					buttonAlign : 'center',
					buttons : [{
								text : '保存',
								iconCls : 'btn-save',
								scope : this,
								handler : this.save
							}, {
								text : '重置',
								iconCls : 'btn-reset',
								scope : this,
								handler : this.reset
							}, {
								text : '取消',
								iconCls : 'btn-cancel',
								scope : this,
								handler : this.cancel
							}]
				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		this.formPanel = new Ext.FormPanel({
					layout : 'form',
					bodyStyle : 'padding:10px',
					border : false,
					autoScroll : true,
					// id : 'WebTemplateMessageForm',
					defaults : {
						anchor : '96%,96%'
					},
					defaultType : 'textfield',
					items : [{
								name : 'webTemplateMessage.id',
								xtype : 'hidden',
								value : this.id == null ? '' : this.id
							}, {
								//fieldLabel : '类型（1邮件，2短信，3站内信）',
								name : 'webTemplateMessage.type',
								xtype : 'numberfield',
								value:this.type,
								hidden:true
							}, {
								fieldLabel : '站内信标题',
								name : 'webTemplateMessage.name',
								maxLength : 255
							}, {
								xtype : 'textarea',
								fieldLabel : '站内信模板内容',
								name : 'webTemplateMessage.content',
								maxLength : 255
							}]
				});
		// 加载表单对应的数据
		if (this.id != null && this.id != 'undefined') {
			this.formPanel.loadData({
				url : __ctxPath + '/web/getWebTemplateMessage.do?id=' + this.id,
				root : 'data',
				preName : 'webTemplateMessage'
			});
		}

	},// end of the initcomponents

	/**
	 * 重置
	 * 
	 * @param {}
	 *            formPanel
	 */
	reset : function() {
		this.formPanel.getForm().reset();
	},
	/**
	 * 取消
	 * 
	 * @param {}
	 *            window
	 */
	cancel : function() {
		this.close();
	},
	/**
	 * 保存记录
	 */
	save : function() {
		$postForm({
					formPanel : this.formPanel,
					scope : this,
					url : __ctxPath + '/web/saveWebTemplateMessage.do',
					callback : function(fp, action) {
						var panel = this.panel;
						var gridPanel = Ext.getCmp('WebTemplateMessageGrid');
						if (gridPanel != null) {
							gridPanel.getStore().reload();
						}
						if (panel != null) {
							panel.getStore().reload();
						}
						this.close();
					}
				});
	}// end of save

});