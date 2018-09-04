/**
 * @author 
 * @createtime 
 * @class SystemSendSmsTemplateView
 * @extends Ext.Window
 * @description BpCouponSetting表单
 * @company 智维软件
 */
SystemSendSmsTemplateView = Ext.extend(Ext.Window, {
	 isChange:false,
			//构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				this.initUIComponents();
				SystemSendSmsTemplateView.superclass.constructor.call(this, {
							id : 'SystemSendSmsTemplateView',
							layout : 'fit',
							items : this.formPanel,
							modal : true,
							height : 200,
							width : 550,
							maximizable : true,
							title : '测试短信发送',
							buttonAlign : 'center',
							buttons : [
										{
											text : '点击发送',
											iconCls : 'btn-save',
											scope : this,
											handler : this.send
										}, {
											text : '关闭',
											iconCls : 'close',
											scope : this,
											handler : this.cancel
										}
							         ]
				});
			},//end of the constructor
			//初始化组件
			initUIComponents : function() {
				var leftlabel = 100;
				var rightlabel = 100;
				this.formPanel = new Ext.form.FormPanel({
					layout : 'form',
					bodyStyle : 'padding:10px',
					autoScroll : true,
					frame : true,
					labelAlign : 'right',
					defaults : {
							anchor : '96%',
							columnWidth : 1,
							labelWidth : 60
						},
					items : [{
						layout : "column",
						border : false,
						scope : this,
						defaults : {
							anchor : '100%',
							columnWidth : 1,
							isFormField : true,
							labelWidth : leftlabel
						},
						items : [{
										columnWidth : 1, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : rightlabel,
										labelAlign :'right',
										items : [{
													xtype : 'textarea',
													fieldLabel : '短信模板内容',
													name : '',
													anchor : '100%',
													blankText : '短信模板内容',
													readOnly : true,
													value:this.content
												}]
							
										},{
						            	columnWidth: 1,
						                layout: 'form',
						                labelWidth : leftlabel,
						                labelAlign :'right',
						                items :[{
												xtype : 'textfield',
												fieldLabel : '发送手机号码',
												name : 'telphone',
												id:'sendTelphone',
												allowBlank : false,
												anchor : '100%',
												blankText : '发送手机号码',
												readOnly : this.isReadOnly,
												regex : /^[1][34578][0-9]{9}$/,
										regexText : '手机号码格式不正确'	,									
										listeners : {
											scope:this,
											'blur' : function(f) {
												var reg=/^[1][34578][0-9]{9}$/;
												var flag=reg.test(this.getCmpByName('telphone').getValue());
												if(!flag){
													this.getCmpByName('telphone').setValue(null);
												}
											
											}
										}
											}]
					            	}]
					}]
				});
				
			},//end of the initcomponents

			/**
			 * 重置
			 * @param {} formPanel
			 */
			reset : function() {
				this.formPanel.getForm().reset();
			},
			/**
			 * 取消
			 * @param {} window
			 */
			cancel : function() {
				this.close();
			},
			/**
			 * 保存记录
			 */
			send : function() {
				var telphone = this.getCmpByName("telphone").getValue();
				if(telphone==null||telphone==""){
					Ext.ux.Toast.msg('操作信息', "手机号码不能为空!");
				}else{
					Ext.Msg.wait('操作信息',"短信正在发送中！");
					Ext.Ajax.request( {
						scope:this,
						url : __ctxPath + '/messageAlert/getSendInfoSmsTemplate.do?telphone='+telphone+'&content='+this.content,
						method : 'post',
			           	success : function(response, request) {
			           		Ext.Msg.hide();
							var obj = Ext.util.JSON.decode(response.responseText);
							Ext.ux.Toast.msg('操作信息', obj.msg);
							this.close();
						}
					})
				}
				
			}//end of save

		});