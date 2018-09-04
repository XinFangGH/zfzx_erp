/**
 * @author zhangcb
 * @createtime
 * @class SMSTest
 * @extends Ext.Window
 * @description SMSTest表单
 * @company 北京互融时代软件有限公司
 * @exception 测试短信是否可以正常发送
 */
SMSTest = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		SMSTest.superclass.constructor.call(this, {
			title : '测试短信连接',
			layout : 'fit',
			modal : true,
			width : 300,
			height : 130,
			resizable:false,
			items : this.formPanel,
			buttonAlign : 'center',
			buttons : [{
				text : '开始测试',
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
		var comId = this.id;
		this.formPanel = new Ext.FormPanel({
			bodyStyle : 'padding:10px',
			autoScroll : true,
			frame : true,
			items :[{
				columnWidth : 1,
				layout : 'form',
				labelWidth:56,
				items : [{
					xtype:'hidden',
					name:'type',
					value:this.type
				},{
					xtype:'numberfield',
					fieldLabel : '手机号码',
					name : 'telphone',
					allowBlank:false,
					anchor : '100%'
				}]
			}]
		});
	},// end of the initcomponents

	/**
	 * 重置
	 * 
	 * @param {}
	 * formPanel
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
		var reg=/^[1][34578][0-9]{9}$/;
		var tel=this.getCmpByName('telphone');
		if(tel.getValue() && reg.test(tel.getValue())){
			$postForm({
				formPanel : this.formPanel,
				scope : this,
				url : __ctxPath+ '/system/testSMSConnSystemProperties.do',
				callback : function(fp, action) {
					var respText = action.response.responseText;
					var syscon = Ext.util.JSON.decode(respText);
					Ext.ux.Toast.msg('操作信息',syscon.msg);
					this.close();
				}
			});
		}else{
			Ext.ux.Toast.msg('操作信息','手机号码格式不正确');
		}
	}// end of save
});