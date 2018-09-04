/**
 * @author
 * @createtime
 * @class SmsinfoForm
 * @extends Ext.Window
 * @description Smsinfo表单
 * @company 智维软件
 */
SmsinfoForm = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		SmsinfoForm.superclass.constructor.call(this, {
					id : 'SmsinfoFormWin',
					layout : 'fit',
					items : this.formPanel,
					modal : true,
					height : 200,
					width : 500,
					maximizable : true,
					title : '短信详细信息',
					buttonAlign : 'center',
					buttons : [{
								text : '发送',
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
					// id : 'SmsinfoForm',
					defaults : {
						anchor : '96%,96%'
					},
					defaultType : 'textfield',
					items : [{
								name : 'smsinfo.id',
								xtype : 'hidden',
								value : this.id == null ? '' : this.id
							}, {
								fieldLabel : '发送手机号',
								name : 'smsinfo.sendPhone',
								maxLength : 1000
							}, {
								fieldLabel : '发送内容',
								name : 'smsinfo.sendContent',
								xtype : 'textarea',
								maxLength : 65535
							}]
				});
		// 加载表单对应的数据
		if (this.id != null && this.id != 'undefined') {
			this.formPanel.loadData({
						url : __ctxPath + '/system/getSmsinfo.do?id=' + this.id,
						root : 'data',
						preName : 'smsinfo'
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
					url : __ctxPath + '/system/saveSmsinfo.do',
					callback : function(fp, action) {
						var gridPanel = Ext.getCmp('SmsinfoGrid');
						if (gridPanel != null) {
							gridPanel.getStore().reload();
							 var obj=Ext.getCmp("smsCount");
	                         ZW.refreshSmsCount(obj);
						}
						this.close();
					}
				});
	}// end of save

});