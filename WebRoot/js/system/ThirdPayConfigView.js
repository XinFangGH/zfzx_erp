Ext.ns('ThirdPayConfigView');
/**
 * [SysConfig]列表
 */
var ThirdPayConfigView = function() {
	return new Ext.Panel({
				id : 'ThirdPayConfigView',
				iconCls:"menu-flowManager",
				title : '三方支付配置',
				tbar : this.tbar(),
				autoScroll : true,
				items : [this.setup()]
			});
};

ThirdPayConfigView.prototype.tbar = function() {
	var toolbar = new Ext.Toolbar();
	toolbar.add(new Ext.Button({
				text : '保存',
				iconCls : 'btn-save',
				handler : function() {
					var fp = Ext.getCmp('mailConfigForm');
					if (fp.getForm().isValid()) {
						fp.getForm().submit({
									method : 'post',
									waitMsg : '正在提交数据...',
									success : function(fp, action) {
										Ext.ux.Toast.msg('操作信息', '成功信息保存！');
										var tabs = Ext.getCmp('centerTabPanel');
										tabs.remove('ThirdPayConfigView');
									},
									failure : function(fp, action) {
										Ext.MessageBox.show({
													title : '操作信息',
													msg : '信息保存出错，请联系管理员！',
													buttons : Ext.MessageBox.OK,
													icon : 'ext-mb-error'
												});
									}
								});

					}
				}
			}));
	toolbar.add(new Ext.Button({
				text : '重置',
				iconCls : 'btn-reseted',
				handler : function() {
					var formPanel = Ext.getCmp('thirdPayConfigForm');
					formPanel.removeAll();
					ThirdPayConfigView.loadSysConfigData(formPanel);
				}
			}));
	return toolbar;
}

ThirdPayConfigView.prototype.setup = function() {
	var formPanel = new Ext.FormPanel({
				id : 'mailConfigForm',
				url : __ctxPath + '/system/saveSysConfig.do',
				defaultType : 'textfield',
				bodyStyle : 'padding-left:10%;',
				frame : false,
				border : false,
				layout : 'form',
				items : []
			});
	ThirdPayConfigView.loadSysConfigData(formPanel);
	return formPanel;
}

ThirdPayConfigView.loadSysConfigData = function(formPanel){
	Ext.Ajax.request({
		url : __ctxPath + "/system/loadSysConfig.do",
		success : function(response, options) {
			// alert(response.responseText);
			var object = Ext.util.JSON.decode(response.responseText)

			// start of the mail config
//			var mailcon = object.data.mailConfig;
//			var mailConfigItems = [];
//			if(mailcon){
//			for (var i = 0; i < mailcon.length; i++) {
//				mailConfigItems.push({
//							xtype : 'container',
//							style : 'padding-bottom:3px;',
//							layout : 'column',
//							items : [{
//										xtype : 'label',
//										style : 'font-weight:bold;',
//										text : mailcon[i].configName,
//										width : 100
//									}, {
//										xtype : 'textfield',
//										width : 300,
//										allowBlank : false,
//										id : mailcon[i].configKey,
//										name : mailcon[i].configKey,
//										value : mailcon[i].dataValue
//									}, {
//										xtype : 'label',
//										width : 200,
//										text : mailcon[i].configDesc
//									}]
//						})
//				}
//			}
//			var mailConfig = {
//				xtype : 'fieldset',
//				title : '系统邮件配置',
//				width : 650,
//				style : 'padding-bottom:3px;',
//				layout : 'form',
//				items : mailConfigItems
//			};
			
			
			
			// end of the sms config
			
			// start of the sms config
			var moneyMoreMore = object.data.moneyMoreMoreConfig;
			var moneyMoreMoreConfigItems = [];
			if(moneyMoreMore){
				for (var i = 0; i < moneyMoreMore.length; i++) {
					if(moneyMoreMore[i].dataType == 2){
						moneyMoreMoreConfigItems.push({
								xtype : 'container',
								style : 'padding-bottom:3px;',
								layout : 'column',
								items : [{
											xtype : 'label',
											style : 'font-weight:bold;',
											text : moneyMoreMore[i].configName,
											width : 100
										}, {
											xtype : 'combo',
											mode : 'local',
											editable : false,
											triggerAction : 'all',
											store : [['1', '打开'], ['2', '关闭']],
											width : 300,
											allowBlank : false,
											hiddenName : moneyMoreMore[i].configKey,
											value : moneyMoreMore[i].dataValue
										}, {
											xtype : 'label',
											width : 200,
											text : moneyMoreMore[i].configDesc
										}]
							})
					}else{
						moneyMoreMoreConfigItems.push({
								xtype : 'container',
								style : 'padding-bottom:3px;',
								layout : 'column',
								items : [{
											xtype : 'label',
											style : 'font-weight:bold;',
											text : moneyMoreMore[i].configName,
											width : 100
										}, {
											xtype : 'textfield',
											width : 300,
											allowBlank : false,
											id : moneyMoreMore[i].configKey,
											name : moneyMoreMore[i].configKey,
											value : moneyMoreMore[i].dataValue
										}, {
											xtype : 'label',
											width : 200,
											text : moneyMoreMore[i].configDesc
										}]
							})
					}
				}
			}
			
			
			var moneyMoreMoreConfig = {
					xtype : 'fieldset',
					title : '钱多多',
					width : 650,
					style : 'padding-bottom:3px;',
					layout : 'form',
					items : moneyMoreMoreConfigItems
				};
				
			//国付宝	
			var gopay = object.data.gopayConfig;
			var gopayConfigItems = [];
			if(gopay){
				for (var i = 0; i < gopay.length; i++) {
					if(gopay[i].dataType == 2){
						gopayConfigItems.push({
								xtype : 'container',
								style : 'padding-bottom:3px;',
								layout : 'column',
								items : [{
											xtype : 'label',
											style : 'font-weight:bold;',
											text : gopay[i].configName,
											width : 100
										}, {
											xtype : 'combo',
											mode : 'local',
											editable : false,
											triggerAction : 'all',
											store : [['1', '打开'], ['2', '关闭']],
											width : 300,
											allowBlank : false,
											hiddenName : gopay[i].configKey,
											value : gopay[i].dataValue
										}, {
											xtype : 'label',
											width : 200,
											text : gopay[i].configDesc
										}]
							})
					}else{
						gopayConfigItems.push({
								xtype : 'container',
								style : 'padding-bottom:3px;',
								layout : 'column',
								items : [{
											xtype : 'label',
											style : 'font-weight:bold;',
											text : gopay[i].configName,
											width : 100
										}, {
											xtype : 'textfield',
											width : 300,
											allowBlank : false,
											id : gopay[i].configKey,
											name : gopay[i].configKey,
											value : gopay[i].dataValue
										}, {
											xtype : 'label',
											width : 200,
											text : gopay[i].configDesc
										}]
							})
					}
				}
			}
			
			
			var gopayConfig = {
					xtype : 'fieldset',
					title : '国付宝',
					width : 650,
					style : 'padding-bottom:3px;',
					layout : 'form',
					items : gopayConfigItems
				};
				
				//富友
			var fuiou = object.data.fuiouConfig;
			var fuiouConfigItems = [];
			if(fuiou){
				for (var i = 0; i < fuiou.length; i++) {
					if(fuiou[i].dataType == 2){
						fuiouConfigItems.push({
								xtype : 'container',
								style : 'padding-bottom:3px;',
								layout : 'column',
								items : [{
											xtype : 'label',
											style : 'font-weight:bold;',
											text : fuiou[i].configName,
											width : 100
										}, {
											xtype : 'combo',
											mode : 'local',
											editable : false,
											triggerAction : 'all',
											store : [['1', '打开'], ['2', '关闭']],
											width : 300,
											allowBlank : false,
											hiddenName : fuiou[i].configKey,
											value : fuiou[i].dataValue
										}, {
											xtype : 'label',
											width : 200,
											text : fuiou[i].configDesc
										}]
							})
					}else{
						fuiouConfigItems.push({
								xtype : 'container',
								style : 'padding-bottom:3px;',
								layout : 'column',
								items : [{
											xtype : 'label',
											style : 'font-weight:bold;',
											text : fuiou[i].configName,
											width : 100
										}, {
											xtype : 'textfield',
											width : 300,
											allowBlank : false,
											id : fuiou[i].configKey,
											name : fuiou[i].configKey,
											value : fuiou[i].dataValue
										}, {
											xtype : 'label',
											width : 200,
											text : fuiou[i].configDesc
										}]
							})
					}
				}
			}
			
			
			var fuiouConfig = {
					xtype : 'fieldset',
					title : '富友',
					width : 650,
					style : 'padding-bottom:3px;',
					layout : 'form',
					items : fuiouConfigItems
				};
		//易生支付（easypay）
			var easypay = object.data.easypayConfig;
			var easypayConfigItems = [];
			if(easypay){
				for (var i = 0; i < easypay.length; i++) {
					if(easypay[i].dataType == 2){
						easypayConfigItems.push({
								xtype : 'container',
								style : 'padding-bottom:3px;',
								layout : 'column',
								items : [{
											xtype : 'label',
											style : 'font-weight:bold;',
											text : easypay[i].configName,
											width : 100
										}, {
											xtype : 'combo',
											mode : 'local',
											editable : false,
											triggerAction : 'all',
											store : [['1', '打开'], ['2', '关闭']],
											width : 300,
											allowBlank : false,
											hiddenName : easypay[i].configKey,
											value : easypay[i].dataValue
										}, {
											xtype : 'label',
											width : 200,
											text : fuiou[i].configDesc
										}]
							})
					}else{
						easypayConfigItems.push({
								xtype : 'container',
								style : 'padding-bottom:3px;',
								layout : 'column',
								items : [{
											xtype : 'label',
											style : 'font-weight:bold;',
											text : easypay[i].configName,
											width : 100
										}, {
											xtype : 'textfield',
											width : 300,
											allowBlank : false,
											id : easypay[i].configKey,
											name : easypay[i].configKey,
											value : easypay[i].dataValue
										}, {
											xtype : 'label',
											width : 200,
											text : easypay[i].configDesc
										}]
							})
					}
				}
			}
			var easypayConfig = {
					xtype : 'fieldset',
					title : '易生支付',
					width : 650,
					style : 'padding-bottom:3px;',
					layout : 'form',
					items : easypayConfigItems
				};	
			// end of the dynamicPwd config
//			formPanel.add(mailConfig);
			formPanel.add(moneyMoreMoreConfig);
			formPanel.add(gopayConfig);
			formPanel.add(fuiouConfig);
			formPanel.add(easypayConfig);
			formPanel.doLayout();
		}
	});
}