/**
 * @author 
 * @createtime 
 * @class SystemUpdateSmsTemplateView
 * @extends Ext.Window
 * @description BpCouponSetting表单
 * @company 智维软件
 */
SystemUpdateSmsTemplateView = Ext.extend(Ext.Window, {
	 isChange:false,
			//构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				this.initUIComponents();
				SystemUpdateSmsTemplateView.superclass.constructor.call(this, {
							id : 'SystemUpdateSmsTemplateView',
							layout : 'fit',
							items : this.formPanel,
							modal : true,
							height : 350,
							width : 700,
							maximizable : true,
							title : '编辑短信模板',
							buttonAlign : 'center',
							buttons : [
										{
											text : '保存',
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
						            	columnWidth: 1,
						                layout: 'form',
						                labelWidth : leftlabel,
						                labelAlign :'right',
						                items :[{
												xtype : 'textfield',
												fieldLabel : '模板KEY',
												name : 'smsTemplate.key',
												allowBlank : false,
												anchor : '100%',
												blankText : '模板KEY',
												readOnly : true,
												value:this.key
											}]
					            	},{
										columnWidth : 1, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : rightlabel,
										labelAlign :'right',
										items : [{
													xtype : 'combo',
													fieldLabel : '是否禁用',
													triggerAction : "all",
													width : 70,
													mode : 'local',
													anchor : '100%',
													editable : false,
													valueField : 'id',
													allowBlank : false,
													displayField : 'name',
													hiddenName : "smsTemplate.prohibitUse",
													value:this.prohibitUse,
													store : new Ext.data.SimpleStore({
														fields : ["name", "id"],
														data : [["否", "no"],["是", "yes"]]
													}),
													listeners : {
														afterrender : function(combox) {
															var st = combox.getStore();
															combox.setValue(combox.getValue());
														}
								
													}
										}]						
										},{
											columnWidth : 1, // 该列在整行中所占的百分比
											layout : "form", // 从上往下的布局
											labelWidth : rightlabel,
											labelAlign :'right',
											items : [{
														xtype : 'combo',
														fieldLabel : '是否测试',
														triggerAction : "all",
														width : 70,
														mode : 'local',
														anchor : '100%',
														editable : false,
														allowBlank : false,
														valueField : 'id',
														displayField : 'name',
														hiddenName : "smsTemplate.isTest",
														value:this.isTest,
														store : new Ext.data.SimpleStore({
															fields : ["name", "id"],
															data : [["否", "no"],["是", "yes"]]
														}),
														listeners : {
															afterrender : function(combox) {
																var st = combox.getStore();
																combox.setValue(combox.getValue());
															}
									
														}
											}]
										},{
										columnWidth : 1, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : rightlabel,
										labelAlign :'right',
										items : [{
													xtype : 'textarea',
													fieldLabel : '模板说明',
													name : 'smsTemplate.useExplain',
													allowBlank : false,
													anchor : '100%',
													blankText : '模板说明',
													value:this.useExplain
												}]
							
										},{
										columnWidth : 1, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : rightlabel,
										labelAlign :'right',
										items : [{
													xtype : 'textarea',
													fieldLabel : '模板内容',
													name : 'smsTemplate.content',
													allowBlank : false,
													anchor : '100%',
													blankText : '模板内容',
													value:this.content
												}]
							
										},{
										columnWidth : 1,
										layout : "form", // 从上往下的布局
										labelWidth : rightlabel,
										labelAlign :'right',
										style :'padding-left:105px;',
										items:[{
											html : "<br><font color=red >注：模板内容修改必须修改为备案后的模板，如需修改参数必须修改为系统现有的参数!</font>"
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
			save : function() {
				if (this.formPanel.getForm().isValid()) {
		    		this.formPanel.getForm().submit({
					    clientValidation: false, 
						url : __ctxPath + '/messageAlert/updateTemplateXmlSmsTemplate.do',
						method : 'post',
						waitMsg : '正在修改内容，请稍后...',
						scope: this,
						success : function(fp, action) {
							var obj = Ext.util.JSON.decode(action.response.responseText)
					        this.gridPanel.getStore().reload();
					        Ext.ux.Toast.msg('操作信息', obj.msg);
					        this.close();
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

		});