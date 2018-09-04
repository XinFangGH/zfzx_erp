/**
 * @author
 * @createtime
 * @class CsHolidayForm
 * @extends Ext.Window
 * @description CsHoliday表单
 * @company 智维软件
 */
UpdateMessInfo = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		UpdateMessInfo.superclass.constructor.call(this, {
					id : 'UpdateMessInfo',
					layout : 'fit',
					items : this.formPanel,
					modal : true,
					height : 450,
					width : 450,
					maximizable : true,
					title : '详细信息',
					buttonAlign : 'center',
					buttons : [{
								text : '保存',
								iconCls : 'btn-save',
								scope : this,
								handler : this.save.createCallback(this.formPanel, this)
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
			url : __ctxPath + '/system/updateP2pMessInfoSystemProperties.do',
			bodyStyle : 'padding:10px',
			border : false,
			autoScroll : true,
			region : 'center',
			monitorValid : true,
			items : [{
				layout : "column",
				border : false,
				scope : this,
				items : [{
					columnWidth : 1,
					layout : 'column',
					items :[{
						columnWidth : 1,
						layout : 'form',
						items : [{
							fieldLabel : '短信接口地址',
							name : 'SMS_Address',
							anchor : '100%',
							//readOnly:true,
							xtype:'textfield'
						}]
					},{
						columnWidth : 1,
						layout : 'form',
						items : [{
							fieldLabel : '短信账号',
							name : 'SMS_UserName',
							anchor : '100%',
							//readOnly:true,
							xtype:'textfield'
						}]
					},{
						columnWidth : 1,
						layout : 'form',
						items : [{
							fieldLabel : '短信密码',
							name : 'SMS_Pass',
							id:'SMS_Pass',
							anchor : '100%',
							//readOnly:true,
							xtype:'textfield'
						}]
					},{
						columnWidth : 1,
						layout : 'form',
						items : [{
							fieldLabel : '短信模板数量',
							name : 'SMS_Count',
							anchor : '100%',
							readOnly:true,
							xtype:'textfield'
						}]
					}]
				}]
			}]
		});
		// 加载表单对应的数据
		//if (this.id != null && this.id != 'undefined') {
		this.formPanel.loadData({	
			url : __ctxPath + '/system/getP2pBaseInfoSystemProperties.do',
			root : 'data',
			success : function(response, options) {
				var respText = response.responseText;
				var syscon = Ext.util.JSON.decode(respText);
				this.getCmpByName('SMS_Pass').setValue('******');
				var fileData=syscon.data.fileData;
				SMS_Template=syscon.data.SMS_Template;
			}
		});
		//}

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
	save : function(formPanel, window) {
		if (formPanel.getForm().isValid()) {
			formPanel.getForm().submit({
				method : 'POST',
				scope:this,
				params:{
					"smsUrl":formPanel.getCmpByName('SMS_Address').getValue(),
		            "smsUserName":formPanel.getCmpByName('SMS_UserName').getValue(),
		            "smsUserPass":formPanel.getCmpByName('SMS_Pass').getValue()
				},
				success : function(form ,action) { debugger;
					var res = Ext.util.JSON.decode(action.response.responseText);
					console.log(action.response.responseText);
					if (res.e) {
						var gridPanel = Ext.getCmp('MessageAccountViewP2P');
						console.log(gridPanel);
						if(res.data.smsAccountID){
							gridPanel.getCmpByName("SMS_UserName").setValue(res.data.smsAccountID);
							console.log(res.data.smsAccountID);
						}
						if(res.data.smsUrl){
							gridPanel.getCmpByName("SMS_Address").setValue(res.data.smsUrl);
							console.log(res.data.smsUrl);
						}
						
						if(res.data.smsPassword){
							gridPanel.getCmpByName("SMS_Pass").setValue(res.data.smsPassword);
							
						}
						window.close();
						Ext.ux.Toast.msg('操作信息','信息保存成功！');
					} else {
						Ext.ux.Toast.msg('信息提示', res.result);
					}
				},
				failure : function(form ,action) {
					Ext.ux.Toast.msg('信息提示', '出错，请联系管理员！');
				}
			})// end of save
		}
	}
});
