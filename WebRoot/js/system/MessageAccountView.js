/**
 * @author
 * @createtime
 * @class GuofubaoForm
 * @extends Ext.Window
 * @description Guofubao表单
 * @company 智维软件
 */

MessageAccountView = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		MessageAccountView.superclass.constructor.call(this, {
			id:'MessageAccountView',
			region : 'center',
			layout : 'border',
			iconCls:"menu-flowManager",
			items : [this.topPanel,this.formPanel],
			frame:true,
			title : '短信模板配置'
		});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		this.topPanel = new Ext.FormPanel({
			border : false,
			region : 'north',
			height : 10,
			layout : 'form'
		})
		
		this.formPanel = new Ext.FormPanel({
			region : 'center',
			autoScroll : true,
			monitorValid : true,
			frame:true,
			bodyStyle : 'padding:10px',
			items : [{
				layout : "column",
				border : false,
				scope : this,
				items : [{
					columnWidth : 1,
					layout : 'column',
					items :[{
						columnWidth : .5,
						layout : 'form',
						items : [{
							fieldLabel : '短信接口地址',
							name : 'SMS_Address',
							anchor : '100%',
							readOnly:true,
							xtype:'textfield'
						}]
					},{
						columnWidth : .5,
						layout : 'form',
						bodyStyle:'padding-top:5px;',
						items : [{
							xtype:'label',
							labelSeparator : '',
							html:'<a href="#" onclick="testSMSConn()" style="color:#1678E2;padding-left:15px;">测试短信连接</a>',
							anchor : '20%'
						}]
					},{
						columnWidth : 1,
						layout : 'form',
						items : [{
							fieldLabel : '短信账号',
							name : 'SMS_UserName',
							anchor : '50%',
							readOnly:true,
							xtype:'textfield'
						}]
					},{
						columnWidth : 1,
						layout : 'form',
						items : [{
							fieldLabel : '短信密码',
							name : 'SMS_Pass',
							anchor : '50%',
							readOnly:true,
							xtype:'textfield'
						}]
					},{
						columnWidth : .5,
						layout : 'form',
						items : [{
							fieldLabel : '短信模板数量',
							name : 'SMS_Count',
							anchor : '100%',
							readOnly:true,
							xtype:'textfield'
						}]
					},{
						columnWidth : .5, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						bodyStyle:'padding-top:5px;',
						items : [{
							xtype:'label',
							labelSeparator : '',
							anchor : '20%',
							html:'<a href="#" onclick="changeSet()" style="color:#1678E2;padding-left:15px;">修改配置</a>'
						}]
					}]
				}]
			}]
		});
		// 加载表单对应的数据
		this.formPanel.loadData({	
			url : __ctxPath + '/system/getBaseInfoSystemProperties.do',
			root : 'data',
			success : function(response, options) {
				var respText = response.responseText;
				var syscon = Ext.util.JSON.decode(respText);
				this.getCmpByName('SMS_Pass').setValue('******');
				var fileData=syscon.data.fileData;
				SMS_Template=syscon.data.SMS_Template;
			}
		});
	},
	save : function() {
		Ext.ux.Toast.msg('操作信息','没有任何修改无需保存!');
		/*var thirdPayEnvType=this.getCmpByName('thirdPayEnvironmentType').getValue();
		Ext.Ajax.request({
			url : __ctxPath	+ '/system/saveSystemProperties.do',
			method : 'post',
			params : {
				thirdPayEnvType : thirdPayEnvType
			},
			success : function(response, option) {
				var obj = Ext.decode(response.responseText);
				if(obj.success){
					Ext.ux.Toast.msg('操作信息',obj.msg);
				}
			}
		 });*/
	}
});
/**
 * 查看短信模板列表信息
 */
function changeSet(){
	new ChangeMessage({type:'ERP'}).show();
	
};



/**
 * 测试短信连接
 * 注意:测试模板需要每个项目根据不同的短信提供商重新备案。
 * 华兴软通-> 短信接口测试连接成功，请放心使用。【${subject}】
 * 亿信通->   短信接口测试连接成功，请放心使用。
 */
function testSMSConn(){
//	new SMSTest({type:'ERP'}).show();
	/*if(confirm('开始测试')){
   		Ext.Ajax.request({
			url : __ctxPath+ '/system/testSMSConn1SystemProperties.do',
			method : 'POST',
			scope:this,
			success : function(response, request) {
				obj = Ext.util.JSON.decode(response.responseText);
			},
			failure : function(response,request) {
			}
		});
    }*/
	Ext.Msg.confirm("提示!", '短信连接测试', function(btn) {
				if (btn == "yes") {
					var pbar = Ext.MessageBox.wait('连接中','请等待',{
						interval:200,
						increment:15
					});
					Ext.Ajax.request({
						url : __ctxPath+ '/system/testSMSConn1SystemProperties.do',
						method : 'POST',
						
						params:{
							'type':'ERP'
						},
					success : function(result, action) {debugger;
						var respText = result.responseText;
						var syscon = Ext.util.JSON.decode(respText);
						Ext.ux.Toast.msg('操作信息',syscon.msg);
						pbar.getDialog().close();
						},
					failure : function(result, action) {debugger;
						var respText = action.response.responseText;
						var syscon = Ext.util.JSON.decode(respText);
						Ext.ux.Toast.msg('操作信息',syscon.msg);
						pbar.getDialog().close();
					}
				});
			}
		});

	
	
	
	
	
	
	
};