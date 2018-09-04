/**
 * @author
 * @createtime
 * @class GuofubaoForm
 * @extends Ext.Window
 * @description Guofubao表单
 * @company 智维软件
 */
 var SMS_Template=null;
P2PSystemBaseParaView = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		P2PSystemBaseParaView.superclass.constructor.call(this, {
			id:'P2PSystemBaseParaView',
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
				text : '保存',
				iconCls:'btn-save',  
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
							fieldLabel : '站点名称',
							name : 'siteName',
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
							fieldLabel : '系统logo',
							xtype : 'label',
							name:"p2p_logoFile",
							anchor : '30%',
							html :function(){
								return '<img src="'+ __ctxPath+ '/images/nopic.jpg" width =140 height=80 /><a href="#" onClick =uploadLogoFile(\'P2PSystemBaseParaView\',\'系统logo\',\'system_p2p\',\'p2p_logoFile\',\'179*53\')>上传</a>&nbsp;&nbsp;&nbsp;<a href="#" onClick =delLogoFile()>删除</a><font style="padding-left:20px;">图片大小： 179*53</font>';
							}()
						}]
					},{
						columnWidth : 1,
						layout : 'form',
						items : [{
							hidden:'true',
							name:'remark2'
						},{
							fieldLabel : '页签icon',
							xtype : 'label',
							name:"p2pIconFile",
							anchor : '30%',
							html :function(){
								return '<img src="'+ __ctxPath+ '/images/nopic.jpg" width =140 height=80 /><a href="#" onClick =uploadLogoFile(\'P2PSystemBaseParaView\',\'页签icon\',\'system_p2p\',\'p2pIconFile\',\'140*138\')>上传</a>&nbsp;&nbsp;&nbsp;<a href="#" onClick =delLogoFile()>删除</a><font style="padding-left:20px;">图片大小： 140*138</font>';
							}()
						}]
					}/*,{
						columnWidth : 1,
						layout : 'form',
						items : [{
							hidden:'true',
							name:'remark3'
						},{
							fieldLabel : '微博二维码',
							xtype : 'label',
							name:"weibo",
							anchor : '30%',
							html :function(){
								return '<img src="'+ __ctxPath+ '/images/nopic.jpg" width =140 height=80 /><a href="#" onClick =uploadLogoFile(\'P2PSystemBaseParaView\',\'微博二维码\',\'system_p2p\',\'weibo\',\'399*397\')>上传</a>&nbsp;&nbsp;&nbsp;<a href="#" onClick =delLogoFile()>删除</a><font style="padding-left:20px;">图片大小： 399*397</font>';
							}()
						}]
					}*/,{
						columnWidth : 1,
						layout : 'form',
						items : [{
							hidden:'true',
							name:'remark3'
						},{
							fieldLabel : '新浪微博二维码',
							xtype : 'label',
							name:"sina_weibo",
							anchor : '30%',
							html :function(){
								return '<img src="'+ __ctxPath+ '/images/nopic.jpg" width =140 height=80 /><a href="#" onClick =uploadLogoFile(\'P2PSystemBaseParaView\',\'新浪微博二维码\',\'system_p2p\',\'sina_weibo\',\'399*397\')>上传</a>&nbsp;&nbsp;&nbsp;<a href="#" onClick =delLogoFile()>删除</a><font style="padding-left:20px;">图片大小： 399*397</font>';
							}()
						}]
					},{
						columnWidth : 1,
						layout : 'form',
						items : [{
							hidden:'true',
							name:'remark3'
						},{
							fieldLabel : '腾讯微博二维码',
							xtype : 'label',
							name:"tencent_weibo",
							anchor : '30%',
							html :function(){
								return '<img src="'+ __ctxPath+ '/images/nopic.jpg" width =140 height=80 /><a href="#" onClick =uploadLogoFile(\'P2PSystemBaseParaView\',\'腾讯微博二维码\',\'system_p2p\',\'tencent_weibo\',\'399*397\')>上传</a>&nbsp;&nbsp;&nbsp;<a href="#" onClick =delLogoFile()>删除</a><font style="padding-left:20px;">图片大小： 399*397</font>';
							}()
						}]
					},{
						columnWidth : 1,
						layout : 'form',
						items : [{
							hidden:'true',
							name:'remark4'
						},{
							fieldLabel : '微信二维码',
							xtype : 'label',
							name:"weixin",
							anchor : '30%',
							html :function(){
								return '<img src="'+ __ctxPath+ '/images/nopic.jpg" width =140 height=80 /><a href="#" onClick =uploadLogoFile(\'P2PSystemBaseParaView\',\'微信二维码\',\'system_p2p\',\'weixin\',\'140*138\')>上传</a>&nbsp;&nbsp;&nbsp;<a href="#" onClick =delLogoFile()>删除</a><font style="padding-left:20px;">图片大小： 140*138</font>';
							}()
						}]
					},{
						columnWidth : .5,
						layout : 'form',
						items : [{
							fieldLabel : '统计代码ID',
							name : 'countCode',
							anchor : '100%',
							xtype:'textfield'
						}]
					},{
						columnWidth : .5,
						layout : 'form',
						bodyStyle:'padding-top:4px;height:22px;',
						items : [{
							xtype:'label',
							labelSeparator : '',
							html:'<font style="color:#1678E2;padding-left:15px;">例: 1259115367</font><br/>',
							anchor : '20%'
						}]
					},{
						columnWidth : .5,
						layout : 'form',
						items : [{
							fieldLabel : '站点版权',
							name : 'copyRight',
							anchor : '100%',
							xtype:'textfield'
						}]
					},{
						columnWidth : .5,
						layout : 'form',
						bodyStyle:'padding-top:4px;height:22px;',
						items : [{
							xtype:'label',
							labelSeparator : '',
							html:'<font style="color:#1678E2;padding-left:15px;">例: ©2009-2016 All Rights Reserved</font><br/>',
							anchor : '20%'
						}]
					},{
						columnWidth : .5,
						layout : 'form',
						items : [{
							fieldLabel : '工作时间',
							name : 'workTime',
							anchor : '100%',
							xtype:'textfield'
						}]
					},{
						columnWidth : .5,
						layout : 'form',
						bodyStyle:'padding-top:4px;height:22px;',
						items : [{
							xtype:'label',
							labelSeparator : '',
							html:'<font style="color:#1678E2;padding-left:15px;">例: 周一至周五 9:00 - 18:30</font><br/>',
							anchor : '20%'
						}]
					},{
						columnWidth : .5,
						layout : 'form',
						items : [{
							fieldLabel : '网站备案信息',
							name : 'beianInfo',
							anchor : '100%',
							xtype:'textfield'
						}]
					},{
						columnWidth : .5,
						layout : 'form',
						bodyStyle:'padding-top:4px;height:22px;',
						items : [{
							xtype:'label',
							labelSeparator : '',
							html:'<font style="color:#1678E2;padding-left:15px;">例: 京ICP备15016554号</font><br/>',
							anchor : '20%'
						}]
					},{
						columnWidth : 1,
						layout : 'form',
						items : [{
							fieldLabel : '网站认证信息',
							name : 'attest',
							anchor : '50%',
							height:70,
							xtype:'textarea'
						}]
					}/*,{
						columnWidth : .5,
						layout : 'form',
						bodyStyle:'padding-top:4px;height:72px;',
						items : [{
							xtype:'label',
							labelSeparator : '',
							html:'<font style="color:#1678E2;padding-left:15px;">例: [href=" ",src=" "]</font><br/>' +
								 '<font style="color:#1678E2;padding-left:33px;">[href=" ",src=" "]</font><br/>' +
								 '<font style="color:#1678E2;padding-left:69px;"> .</font><br/>' +
								 '<font style="color:#1678E2;padding-left:69px;"> .</font><br/>' +
								 '<font style="color:#1678E2;padding-left:69px;"> .</font>',
							anchor : '20%'
						}]
					}*/,{
						columnWidth : .5,
						layout : 'form',
						items : [{
							fieldLabel : '百度地图APPKEY',
							name : 'baiduMap',
							anchor : '100%',
							xtype:'textfield'
						}]
					},{
						columnWidth : .5,
						layout : 'form',
						bodyStyle:'padding-top:4px;height:22px;',
						items : [{
							xtype:'label',
							labelSeparator : '',
							html:'<font style="color:#1678E2;padding-left:15px;">例: eVQrLyTiwYKmIjhmFX5heuE6</font><br/>',
							anchor : '20%'
						}]
					},{
						columnWidth : .5,
						layout : 'form',
						items : [{
							fieldLabel : '百度地图标记',
							name : 'baiduMapMarker',
							anchor : '100%',
							xtype:'textfield'
						}]
					},{
						columnWidth : .5,
						layout : 'form',
						bodyStyle:'padding-top:4px;height:22px;',
						items : [{
							xtype:'label',
							labelSeparator : '',
							html:'<font style="color:#1678E2;padding-left:15px;">例: {title:"互融时代"}</font><br/>',
							anchor : '20%'
						}]
					},{
						columnWidth : 1,
						layout : 'form',
						items : [{
							fieldLabel : '公司地址',
							name : 'companyAddress',
							anchor : '50%',
							xtype:'textfield'
						}]
					},{
						columnWidth : 1,
						layout : 'form',
						items : [{
							fieldLabel : '客服邮箱',
							name : 'customerEmail',
							anchor : '50%',
							xtype:'textfield'
						}]
					},{
						columnWidth : 1,
						layout : 'form',
						items : [{
							fieldLabel : '客服电话',
							name : 'consumerHotline',
							anchor : '50%',
							xtype:'textfield'
						}]
					},{
						columnWidth : 1,
						layout : 'form',
						items : [{
							fieldLabel : '客服QQ',
							name : 'consumerQQ',
							anchor : '50%',
							xtype:'textfield'
						}]
					}/*,{
						columnWidth : 1,
						layout : 'form',
						items : [{
							fieldLabel : '虚拟会员人数',
							name : 'member',
							anchor : '50%',
							xtype:'textfield'
						}]
					}*/,{
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
					}]
				}]
			}]
		});
		// 加载表单对应的数据
		this.formPanel.loadData({
			url : __ctxPath + '/system/getP2PBaseInfoSystemProperties.do',
			root : 'data',
			success : function(response, options) {
				var respText = response.responseText;
				var syscon = Ext.util.JSON.decode(respText);
				this.getCmpByName('Email_Pass').setValue('******');
				this.getCmpByName('SMS_Pass').setValue('******');
				var fileData=syscon.data.fileData;
				SMS_Template=syscon.data.SMS_Template;
				if(fileData){
					for(var i=0;i<fileData.length;i++){
						if(fileData[i].remark=='p2p_logoFile'){
							this.getCmpByName('p2p_logoFile').setText('<img src="'+ __ctxPath+"/"+fileData[i].webPath+'" ondblclick=showPic("'+fileData[i].webPath+'") width =140 height=80  /><a href="#" onClick =uploadLogoFile(\'P2PSystemBaseParaView\',\'系统logo\',\'system_p2p\',\'p2p_logoFile\',\'179*53\')>上传</a>&nbsp;&nbsp;&nbsp;<a href="#" onClick =delLogoFile(\'P2PSystemBaseParaView\',\'系统logo\','+fileData[i].id+',\'p2p_logoFile\',\'179*53\',\'system_p2p\')>删除</a><font style="padding-left:20px;">图片大小： 179*53</font>',false);
						}else if(fileData[i].remark=='p2pIconFile'){
							this.getCmpByName('p2pIconFile').setText('<img src="'+ __ctxPath+"/"+fileData[i].webPath+'" ondblclick=showPic("'+fileData[i].webPath+'") width =140 height=80  /><a href="#" onClick =uploadLogoFile(\'P2PSystemBaseParaView\',\'页签icon\',\'system_p2p\',\'p2pIconFile\',\'63*63\')>上传</a>&nbsp;&nbsp;&nbsp;<a href="#" onClick =delLogoFile(\'P2PSystemBaseParaView\',\'页签icon\','+fileData[i].id+',\'p2pIconFile\',\'63*63\',\'system_p2p\')>删除</a><font style="padding-left:20px;">图片大小： 63*63</font>',false);
						}else if(fileData[i].remark=='weibo'){
							this.getCmpByName('weibo').setText('<img src="'+ __ctxPath+"/"+fileData[i].webPath+'" ondblclick=showPic("'+fileData[i].webPath+'") width =140 height=80  /><a href="#" onClick =uploadLogoFile(\'P2PSystemBaseParaView\',\'微博二维码\',\'system_p2p\',\'weibo\',\'140*138\')>上传</a>&nbsp;&nbsp;&nbsp;<a href="#" onClick =delLogoFile(\'P2PSystemBaseParaView\',\'微博二维码\','+fileData[i].id+',\'weibo\',\'140*138\',\'system_p2p\')>删除</a><font style="padding-left:20px;">图片大小： 140*138</font>',false);
						}else if(fileData[i].remark=='weixin'){
							this.getCmpByName('weixin').setText('<img src="'+ __ctxPath+"/"+fileData[i].webPath+'" ondblclick=showPic("'+fileData[i].webPath+'") width =140 height=80  /><a href="#" onClick =uploadLogoFile(\'P2PSystemBaseParaView\',\'微信二维码\',\'system_p2p\',\'weixin\',\'140*138\')>上传</a>&nbsp;&nbsp;&nbsp;<a href="#" onClick =delLogoFile(\'P2PSystemBaseParaView\',\'微信二维码\','+fileData[i].id+',\'weixin\',\'140*138\',\'system_p2p\')>删除</a><font style="padding-left:20px;">图片大小： 140*138</font>',false);
						}else if(fileData[i].remark=='tencent_weibo'){
							this.getCmpByName('tencent_weibo').setText('<img src="'+ __ctxPath+"/"+fileData[i].webPath+'" ondblclick=showPic("'+fileData[i].webPath+'") width =140 height=80  /><a href="#" onClick =uploadLogoFile(\'P2PSystemBaseParaView\',\'腾讯微博二维码\',\'system_p2p\',\'tencent_weibo\',\'140*138\')>上传</a>&nbsp;&nbsp;&nbsp;<a href="#" onClick =delLogoFile(\'P2PSystemBaseParaView\',\'腾讯微博二维码\','+fileData[i].id+',\'tencent_weibo\',\'140*138\',\'system_p2p\')>删除</a><font style="padding-left:20px;">图片大小： 140*138</font>',false);
						}else if(fileData[i].remark=='sina_weibo'){
							this.getCmpByName('sina_weibo').setText('<img src="'+ __ctxPath+"/"+fileData[i].webPath+'" ondblclick=showPic("'+fileData[i].webPath+'") width =140 height=80  /><a href="#" onClick =uploadLogoFile(\'P2PSystemBaseParaView\',\'新浪微博二维码\',\'system_p2p\',\'sina_weibo\',\'140*138\')>上传</a>&nbsp;&nbsp;&nbsp;<a href="#" onClick =delLogoFile(\'P2PSystemBaseParaView\',\'新浪微博二维码\','+fileData[i].id+',\'sina_weibo\',\'140*138\',\'system_p2p\')>删除</a><font style="padding-left:20px;">图片大小： 140*138</font>',false);
						}
					}
				}
			}
		});
	},
	save : function() {
//		var thirdPayEnvType=this.getCmpByName('thirdPayEnvironmentType').getValue();
		var countCode=this.getCmpByName('countCode').getValue();//统计代码
		var copyRight=this.getCmpByName('copyRight').getValue();//站点版权
		var workTime=this.getCmpByName('workTime').getValue();//工作时间
		var beianInfo=this.getCmpByName('beianInfo').getValue();//备案信息
		var attest=this.getCmpByName('attest').getValue();//认证信息
		var baiduMap=this.getCmpByName('baiduMap').getValue();//百度地图
		var baiduMapMarker=this.getCmpByName('baiduMapMarker').getValue();//百度地图标记
		var companyAddress=this.getCmpByName('companyAddress').getValue();//公司地址
		var customerEmail=this.getCmpByName('customerEmail').getValue();//客服邮箱
		var consumerHotline=this.getCmpByName('consumerHotline').getValue();//客服电话
		var consumerQQ=this.getCmpByName('consumerQQ').getValue();//客服QQ
		Ext.Ajax.request({
			url : __ctxPath	+ '/system/saveP2PSystemProperties.do',
			method : 'post',
			params : {
//				thirdPayEnvType : thirdPayEnvType,
				countCode : countCode,
				copyRight : copyRight,
				workTime : workTime,
				beianInfo : beianInfo,
				attest : attest,
				baiduMap : baiduMap,
				baiduMapMarker : baiduMapMarker,
				companyAddress : companyAddress,
				customerEmail : customerEmail,
				consumerHotline : consumerHotline,
				consumerQQ : consumerQQ
			},
			success : function(response, option) {
				var obj = Ext.decode(response.responseText);
				if(obj.success){
					Ext.ux.Toast.msg('操作信息',obj.msg);
				}
			}
		 });
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
	new SMSTest({type:'P2P'}).show();
};

/**
 * 测试邮件是否可以正常发送
 */
function testEmailConn(){
	new EmailTest().show();
};