/**
 * @author 
 * @createtime 
 * @class SystemP2PUpdateSmsTemplateView
 * @extends Ext.Window
 * @description BpCouponSetting表单
 * @company 智维软件
 */
SystemP2PUpdateSmsTemplateView = Ext.extend(Ext.Window, {
	 isChange:false,
			//构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				this.initUIComponents();
				SystemP2PUpdateSmsTemplateView.superclass.constructor.call(this, {
							id : 'SystemP2PUpdateSmsTemplateView',
							layout : 'fit',
							items : this.formPanel,
							modal : true,
							height : 300,
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
												name : '',
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
													xtype : 'textarea',
													fieldLabel : '模板说明',
													id : 'useExplain',
													anchor : '100%',
													allowBlank : false,
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
													id : 'content',
													anchor : '100%',
													allowBlank : false,
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
				var useExplain = this.getCmpByName("useExplain").getValue();
				var content = this.getCmpByName("content").getValue();
				if(useExplain==null||useExplain==""){
					Ext.ux.Toast.msg('操作信息', "模板说明不能为空!");
					return false;
				}
				if(content==null||content==""){
					Ext.ux.Toast.msg('操作信息', "模板内容不能为空!");
					return false;
				}
					Ext.Msg.wait('操作信息',"正在修改内容...");
						Ext.Ajax.request( {
							scope:this,
							url : __ctxPath + '/system/updateP2PSmsSystemProperties.do?key='+this.key+'&useExplain='+useExplain+'&content='+content,
							method : 'post',
				           	success : function(response, request) {
				           		Ext.Msg.hide();
								var obj = Ext.util.JSON.decode(response.responseText);
								Ext.ux.Toast.msg('操作信息', obj.msg);
								this.gridPanel.getStore().reload();
								this.close();
							}
						})
				}

		});