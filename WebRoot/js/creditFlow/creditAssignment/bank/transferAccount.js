transferAccount = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		transferAccount.superclass.constructor.call(this, {
					id : 'transferAccountWin',
					layout : 'fit',
					items : this.formPanel,
					modal : true,
					height : 400,
					width : 500,
					maximizable : true,
					title : '平台转账',
					buttonAlign : 'center',
					buttons : [{
								text : '转账',
								iconCls : 'btn-save',
								scope : this,
								handler : this.save
							},{
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
					// id : 'transferAccount',
					defaults : {
						anchor : '96%,96%'
					},
					defaultType : 'textfield',
					items : [ {
						fieldLabel : '用户登录名',
						name : 'p2pName',
						allowBlank : false,
						maxLength : 50
					}, {
						fieldLabel : '转账金额',
						name : 'transferMoney',
						xtype : 'textfield',
						allowBlank : false,
						maxLength : 50,
						listeners : {
										scope : this,
										afterrender : function(obj) {
											obj.on("keyup")
										},change : function(nf) {
											var value = nf.getValue();
											var index = value.indexOf(".");
											if (index <= 0) { // 如果第一次输入 没有进行格式化
												nf.setValue(Ext.util.Format.number(value, '0,000.00'));
											}
										}
						}
					}]
				});
		// 加载表单对应的数据
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
			url : __ctxPath
					+ '/creditFlow/financingAgency/transferPlBidPlan.do',
			callback : function(fp, action) {
				var obj=Ext.util.JSON.decode(action.response.responseText);
				if (obj.success) {
					Ext.MessageBox.hide();//解除锁屏
                    Ext.ux.Toast.msg('操作信息', obj.msg);
					this.close();
				}else {
					Ext.ux.Toast.msg('操作信息', obj.msg);
					Ext.MessageBox.hide();//解除锁屏
				}
			}
		});
	}// end of save

});