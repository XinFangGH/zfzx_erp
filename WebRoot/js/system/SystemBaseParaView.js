/**
 * @author
 * @createtime
 * @class GuofubaoForm
 * @extends Ext.Window
 * @description Guofubao表单
 * @company 智维软件
 */
 var SMS_Template=null;
SystemBaseParaView = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		SystemBaseParaView.superclass.constructor.call(this, {
			id:'SystemBaseParaView',
			region : 'center',
			layout : 'border',
			iconCls:"menu-flowManager",
			items : [this.topPanel,this.formPanel],
			frame:true,
			title : '基础配置参数'
		});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		this.topPanel = new Ext.FormPanel({
			border : false,
			region : 'north',
			height : 40,
			layout : 'form',
            items:[{
				xtype : 'button',
				iconCls:'btn-save',  
				text : '保存',
				anchor : "6%",
				height:30,
				scope : this,
				handler : this.save
            }]
		})
		
		this.formPanel = new Ext.FormPanel({
			region : 'center',
			autoScroll : true,
			monitorValid : true,
			bodyStyle : 'padding:10px',
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
							fieldLabel : '系统版本号',
							name : 'SystemVersion',
							anchor : '50%',
							readOnly:true,
							xtype:'textfield'
						}]
					},{
						columnWidth : 1,
						layout : 'form',
						items : [{
							fieldLabel : '是否集团版',
							name : 'isGroupVersion',
							anchor : '50%',
							readOnly:true,
							xtype:'textfield'
						}]
					},{
						columnWidth : 1,
						layout : 'form',
						items : [{
							fieldLabel : '是否包含OA系统',
							name : 'isOA',
							anchor : '50%',
							readOnly:true,
							xtype:'textfield'
						}]
					},{
						columnWidth : 1,
						layout : 'form',
						items : [{
							hidden:'true',
							name:'remark1'
						},{
							fieldLabel : '登陆logo',
							xtype : 'label',
							name:"login_logoFile",
							anchor : '30%',
							html :function(){
								return '<img src="'+ __ctxPath+ '/images/nopic.jpg" width =140 height=80 /><a href="#" onClick =uploadLogoFile(\'SystemBaseParaView\',\'登陆logo\',\'system\',\'login_logoFile\',\'399*397\')>上传</a>&nbsp;&nbsp;&nbsp;<a href="#" onClick =delLogoFile()>删除</a><font style="padding-left:20px;">图片大小： 399*397</font>';
							}()
						}]
					},{
						columnWidth : 1,
						layout : 'form',
						items : [{
							hidden:'true',
							name:'remark2'
						},{
							fieldLabel : '首页logo',
							xtype : 'label',
							name:"index_logoFile",
							anchor : '30%',
							html :function(){
								return '<img src="'+ __ctxPath+ '/images/nopic.jpg" width =140 height=80 /><a href="#" onClick =uploadLogoFile(\'SystemBaseParaView\',\'首页logo\',\'system\',\'index_logoFile\',\'63*63\')>上传</a>&nbsp;&nbsp;&nbsp;<a href="#" onClick =delLogoFile()>删除</a><font style="padding-left:20px;">图片大小： 63*63</font>';
							}()
						}]
					},{
						columnWidth : 1,
						layout : 'form',
						items : [{
							hidden:'true',
							name:'remark3'
						},{
							fieldLabel : '页签icon',
							xtype : 'label',
							name:"iconFile",
							anchor : '30%',
							html :function(){
								return '<img src="'+ __ctxPath+ '/images/nopic.jpg" width =140 height=80 /><a href="#" onClick =uploadLogoFile(\'SystemBaseParaView\',\'页签icon\',\'system\',\'iconFile\',\'47*43\')>上传</a>&nbsp;&nbsp;&nbsp;<a href="#" onClick =delLogoFile()>删除</a><font style="padding-left:20px;">图片大小： 47*43</font>';
							}()
						}]
					},{
						columnWidth : .5,
						layout : 'form',
						items : [{
							fieldLabel : 'FTP的IP',
							name : 'FTP_IP',
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
							html:'<a href="#" onclick="testFTPConn()" style="color:#1678E2;padding-left:15px;">测试FTP连接</a>',
							anchor : '20%'
						}]
					},{
						columnWidth : 1,
						layout : 'form',
						items : [{
							fieldLabel : 'FTP账号',
							name : 'FTP_UserName',
							anchor : '50%',
							readOnly:true,
							xtype:'textfield'
						}]
					},{
						columnWidth : 1,
						layout : 'form',
						items : [{
							fieldLabel : 'FTP密码',
							name : 'FTP_Pass',
							anchor : '50%',
							readOnly:true,
							xtype:'textfield'
						}]
					},{
						columnWidth : 1,
						layout : 'form',
						items : [{
							fieldLabel : 'FTP端口号',
							name : 'FTP_Port',
							anchor : '50%',
							readOnly:true,
							xtype:'textfield'
						}]
					},{
						columnWidth : .5,
						layout : 'form',
						labelWidth:212,
						items : [{
							fieldLabel : '邮箱地址(备注：互融时代的邮箱)',
							name : 'Email_Address',
							anchor : '100%',
							readOnly:true,
							xtype:'textfield'
						}]
					},{
						columnWidth : .5,
						layout : 'form',
						bodyStyle:'padding-top:12px;',
						items : [{
							xtype:'label',
							labelSeparator : '',
							html:'<a href="#" onclick="testEmailConn()" style="color:#1678E2;padding-left:15px;">测试邮箱连接</a>',
							anchor : '20%'
						}]
					},{
						columnWidth : .5,
						layout : 'form',
						labelWidth:212,
						items : [{
							fieldLabel : '邮箱账号(备注：互融时代的账号)',
							name : 'Email_UserName',
							anchor : '100%',
							readOnly:true,
							xtype:'textfield'
						}]
					},{
						columnWidth : 1,
						layout : 'form',
						labelWidth:212,
						items : [{
							fieldLabel : '邮箱密码(备注：互融时代的密码)',
							name : 'Email_Pass',
							anchor : '50%',
							readOnly:true,
							xtype:'textfield'
						}]
					},{
						columnWidth : 1,
						layout : 'form',
						labelWidth:212,
						items : [{
							fieldLabel : 'SMTP服务器(备注：互融时代的服务器)',
							name : 'SMTP',
							anchor : '50%',
							readOnly:true,
							xtype:'textfield'
						}]
					},{
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
							html:'<a href="#" onclick="seeTemplate()" style="color:#1678E2;padding-left:15px;">查看模板列表</a>'
						}]
					},{
						columnWidth : 1,
						layout : 'form',
						items : [{
							xtype:'combo',
							fieldLabel : '第三方支付类型',
							hiddenName : 'thirdPayType',
							mode : 'local',
							anchor : '50%',
							readOnly:true,
							editable : false,
							displayField : 'itemName',// 显示字段值
							valueField : 'itemValue',
							triggerAction : 'all',
							store : new Ext.data.SimpleStore({
								fields : ['itemName','itemValue'],
								data : [['资金托管', '0'],['资金池','1']]
							})
						}]
					},{
						columnWidth : 1,
						layout : 'form',
						items : [{
							xtype:'combo',
							fieldLabel : '第三方环境',
							hiddenName : 'thirdPayEnvironmentType',
							readOnly:true,
							mode : 'local',
							anchor : '50%',
							editable : false,
							displayField : 'itemName',// 显示字段值
							valueField : 'itemValue',
							triggerAction : 'all',
							store : new Ext.data.SimpleStore({
								fields : ['itemName','itemValue'],
								data : [['测试环境', 'testEnvironment'],['正式环境','normalEnvironment']]
							})
						}]
					},{
						columnWidth : 1,
						layout : 'form',
						items : [{
							xtype:'combo',
							fieldLabel : '第三方名称',
							hiddenName : 'thirdPayConfig',
							mode : 'local',
							anchor : '50%',
							editable : false,
							readOnly:true,
							displayField : 'itemName',// 显示字段值
							valueField : 'itemValue',
							triggerAction : 'all',
							store : new Ext.data.SimpleStore({
								fields : ['itemName','itemValue'],
								data : [['易宝支付', 'yeepayConfig'],['富有支付','fuiouConfig'],['易生支付','easypayConfig'],['国付宝支付','gopayConfig'],
										['双乾支付','moneyMoreMoreConfig'],['汇付天下支付','huifuConfig'],['联动优势','umpayConfig'],['新浪支付','sinapayConfig']]
							})
						}]
					},{
						columnWidth : 1,
						layout : 'form',
						items : [{
							fieldLabel : '第三方后台处理类',
							name : 'thirdPayURL',
							anchor : '50%',
							readOnly:true,
							xtype:'textfield'
						}]
					},{
						columnWidth : 1,
						layout : 'form',
						labelWidth:111,
						items : [{
							fieldLabel : '编辑器图片上传路径',
							name : 'imagePath',
							anchor : '50%',
							readOnly:true,
							xtype:'textfield'
						}]
					},{
						columnWidth : 1,
						layout : 'form',
						items : [{
							fieldLabel : '对应P2P端URL',
							name : 'p2pUrl',
							anchor : '50%',
							readOnly:true,
							xtype:'textfield'
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
				this.getCmpByName('FTP_Pass').setValue('******');
				this.getCmpByName('Email_Pass').setValue('******');
				this.getCmpByName('SMS_Pass').setValue('******');
				var fileData=syscon.data.fileData;
				SMS_Template=syscon.data.SMS_Template;
				if(fileData){
					for(var i=0;i<fileData.length;i++){
						if(fileData[i].remark=='login_logoFile'){
							this.getCmpByName('login_logoFile').setText('<img src="'+ __ctxPath+"/"+fileData[i].webPath+'" ondblclick=showPic("'+fileData[i].webPath+'") width =140 height=80  /><a href="#" onClick =uploadLogoFile(\'SystemBaseParaView\',\'登陆logo\',\'system\',\'login_logoFile\',\'399*397\')>上传</a>&nbsp;&nbsp;&nbsp;<a href="#" onClick =delLogoFile(\'SystemBaseParaView\',\'登陆logo\','+fileData[i].id+',\'login_logoFile\',\'399*397\',\'system\')>删除</a><font style="padding-left:20px;">图片大小： 399*397</font>',false);
						}else if(fileData[i].remark=='index_logoFile'){
							this.getCmpByName('index_logoFile').setText('<img src="'+ __ctxPath+"/"+fileData[i].webPath+'" ondblclick=showPic("'+fileData[i].webPath+'") width =140 height=80  /><a href="#" onClick =uploadLogoFile(\'SystemBaseParaView\',\'首页logo\',\'system\',\'index_logoFile\',\'63*63\')>上传</a>&nbsp;&nbsp;&nbsp;<a href="#" onClick =delLogoFile(\'SystemBaseParaView\',\'首页logo\','+fileData[i].id+',\'index_logoFile\',\'63*63\',\'system\')>删除</a><font style="padding-left:20px;">图片大小： 63*63</font>',false);
						}else if(fileData[i].remark=='iconFile'){
							this.getCmpByName('iconFile').setText('<img src="'+ __ctxPath+"/"+fileData[i].webPath+'" ondblclick=showPic("'+fileData[i].webPath+'") width =140 height=80  /><a href="#" onClick =uploadLogoFile(\'SystemBaseParaView\',\'页签icon\',\'system\',\'iconFile\',\'47*43\')>上传</a>&nbsp;&nbsp;&nbsp;<a href="#" onClick =delLogoFile(\'SystemBaseParaView\',\'页签icon\','+fileData[i].id+',\'iconFile\',\'47*43\',\'system\')>删除</a><font style="padding-left:20px;">图片大小： 47*43</font>',false);
						}
					}
				}
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
function seeTemplate(){
	new SMSTemplate({}).show();
};

/**
 * 测试ftp连接
 */
function testFTPConn(){
	var parent=Ext.getCmp('SystemBaseParaView');
	var ftpIp=parent.getCmpByName('FTP_IP').getValue();
	var ftpPort=parent.getCmpByName('FTP_Port').getValue();
	Ext.Ajax.request({
		url : __ctxPath	+ '/system/testFtpConnSystemProperties.do',
		method : 'post',
		params : {
			ftpIp : ftpIp,
			ftpPort : ftpPort
		},
		success : function(response, option) {
			var obj = Ext.decode(response.responseText);
			if(obj.success){
				Ext.ux.Toast.msg('操作信息',obj.msg);
			}
		}
	 });
};

/**
 * 测试短信连接
 * 注意:测试模板需要每个项目根据不同的短信提供商重新备案。
 * 华兴软通-> 短信接口测试连接成功，请放心使用。【${subject}】
 * 亿信通->   短信接口测试连接成功，请放心使用。
 */
function testSMSConn(){
	new SMSTest({type:'ERP'}).show();
};

/**
 * 测试邮件是否可以正常发送
 */
function testEmailConn(){
	new EmailTest({}).show();
};