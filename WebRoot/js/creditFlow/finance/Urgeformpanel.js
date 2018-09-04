/**
 * @author
 * @createtime
 * @class urgeformpanel
 * @extends panel
 * @description urgeformpanel 催收表单
 * @company 互融云软件
 */
Urgeformpanel = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		Urgeformpanel.superclass.constructor.call(this, {
					//id : 'Urgeformpanel' +this.fundIntentId+"_"+this.projectId,
					layout : 'anchor',
					items : this.formPanel,
					autoHeight : true,
					modal : true
					
				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		this.msgContent=function(type,panel){
		  Ext.Ajax.request({
		  			url : __ctxPath+'/creditFlow/finance/getUrgeMsgContentSlFundintentUrge.do',
					method : 'POST',
					success : function(response, request) {
						
						var obj = Ext.util.JSON.decode(response.responseText);
						
						panel.setValue(obj.msg)
					},
					failure : function(response) {
						Ext.ux.Toast.msg('状态', '操作失败，请重试！');
						
					},
					params : {
						urgeType:type,
						projectId:this.projectId,
						fundIntentId:this.fundIntentId

					}
		  })
		};
		
		this.formPanel =  new Ext.FormPanel({
			layout : 'column',
			bodyStyle : 'padding:1px',
			autoScroll : true,
			monitorValid : true,
			frame : true,
			plain : true,
			labelAlign : "right",
			defaults : {
				anchor : '96%',
				labelWidth : 80,
				columnWidth : 1,
				layout : 'column'
			},
			items : [{
						columnWidth : 0.1,
						labelWidth : 73,
						layout : 'form',
						items : [{
									fieldLabel : '催收方式',
									name : 'urgeType',
									xtype : 'label',
									anchor : '98%'
								}, {
									name : 'slFundintentUrge.urgeType',
									xtype : 'hidden',
									value : 1
								}, {
									name : 'slFundintentUrge.fundIntentId',
									xtype : 'hidden',
									value : this.fundIntentId
								}, {
									name : 'slFundintentUrge.projectId',
									xtype : 'hidden',
									value : this.projectId
								}]
					},{
						columnWidth : 0.2,
						labelWidth : 73,
						layout : 'form',
						items : [{
							xtype : 'radio',
							boxLabel : '电话催收',
							name : '1',
							id : "13" + this.fundIntentId,
							inputValue : false,
							checked : true,
							listeners : {
								scope : this,
								check : function(obj, checked) {
									var flag = Ext.getCmp("13"+ this.fundIntentId).getValue();
									if (flag == true) {
										this.getCmpByName('slFundintentUrge.urgeContext').removeClass('readOnlyClass');
										this.getCmpByName('slFundintentUrge.urgeContext').readOnlyClass="";
										this.getCmpByName('urgeContext').setVisible(true);
										this.getCmpByName('upload').setVisible(false);
										this.getCmpByName('slFundintentUrge.urgeType').setValue(3);
										this.getCmpByName('slFundintentUrge.urgeContext').setValue();
									}
								}
							}
						}]
					}, {
						columnWidth : 0.2,
						labelWidth : 73,
						layout : 'form',
						items : [{
							xtype : 'radio',
							boxLabel : '发送邮件【系统】',
							name : '1',
							id : "11" + this.fundIntentId,
							inputValue : true,
							listeners : {
								scope : this,
								check : function(obj, checked) {
									var flag = Ext.getCmp("11"+ this.fundIntentId).getValue();
									if (flag == true) {
										this.getCmpByName('slFundintentUrge.urgeContext').setReadOnly(false);
										this.getCmpByName('slFundintentUrge.urgeContext').removeClass('readOnlyClass');
										this.getCmpByName('slFundintentUrge.urgeType').setValue(1);
										this.getCmpByName('urgeContext').setVisible(true);
										this.getCmpByName('upload').setVisible(true);
										this.msgContent(1,this.getCmpByName('slFundintentUrge.urgeContext'));
										
									}
								}
							}
						}]
					}, {
						columnWidth : 0.2,
						labelWidth : 73,
						layout : 'form',
						items : [{
							xtype : 'radio',
							boxLabel : '发送短信【系统】',
							name : '1',
							id : "12" + this.fundIntentId,
							inputValue : false,
							listeners : {
								scope : this,
								check : function(obj, checked) {
									var flag = Ext.getCmp("12"+ this.fundIntentId).getValue();
									if (flag == true) {
										this.msgContent(2,this.getCmpByName('slFundintentUrge.urgeContext'));
										this.getCmpByName('slFundintentUrge.urgeType').setValue(2);
										this.getCmpByName('urgeContext').setVisible(true);
										this.getCmpByName('upload').setVisible(false);
										
										this.getCmpByName('slFundintentUrge.urgeContext').setValue();
										this.getCmpByName('slFundintentUrge.urgeContext').addClass('readOnlyClass','readOnlyClass');
										this.getCmpByName('slFundintentUrge.urgeContext').setReadOnly(true);
									}
								}
							}
						}]
					},  {
						columnWidth : 0.2,
						labelWidth : 73,
						layout : 'form',
						items : [{
							xtype : 'radio',
							boxLabel : '纸质邮件',
							name : '1',
							id : "14" +this.fundIntentId,
							inputValue : true,
							listeners : {
								scope : this,
								check : function(obj, checked) {
									var flag = Ext.getCmp("14"+ this.fundIntentId).getValue();
									if (flag == true) {
										this.getCmpByName('slFundintentUrge.urgeContext').setReadOnly(false);
										this.getCmpByName('slFundintentUrge.urgeContext').removeClass('readOnlyClass');
										this.getCmpByName('urgeContext').setVisible(true);
										this.getCmpByName('upload').setVisible(false);
										this.getCmpByName('slFundintentUrge.urgeType').setValue(1);
										this.getCmpByName('slFundintentUrge.urgeContext').setValue();
									}
								}
							}
						}]
					}, {
						columnWidth : 1,
						labelWidth : 73,
						layout : 'form',
						items : [{
									fieldLabel : '催收标题',
									name : 'slFundintentUrge.urgeTitle',
									xtype : 'textfield',
									anchor : '98%'
								}]
					}, {
						columnWidth : 1,
						labelWidth : 73,
						layout : 'form',
						name:'urgeContext',
						items : [{
							fieldLabel : '催收内容',
							name : 'slFundintentUrge.urgeContext',
							xtype : 'textarea',
							//readOnly:true,
							anchor : '98%'
						}]
					},{
						columnWidth : 1,
						labelWidth : 73,
						layout : 'form',
						name:'upload',
						items : [{
							xtype : 'button',
							text : '上传附件',
							iconCls : 'slupIcon',
							//width : 110,
							//scope : this,
							handler : function() {
								var mark="test11."+1;
								//uploadfile 方法多一个属性 1（可以写任意值）： 主要是为控制title是否显示 。不添加这个属性会正常显示title，添加这个属性则会让title显示
								uploadfile("附件上传",mark,null,null,null,null,100);
							}
						}]
					}

			]

		});
		


	}
});