//EditSystemAccountSettingItem.js
EditSystemAccountSettingItem = Ext.extend(Ext.Window, {
			// 构造函数
			constructor : function(_cfg) {
			
				Ext.applyIf(this, _cfg);
				// 必须先初始化组件
				this.initUIComponents();
				EditSystemAccountSettingItem.superclass.constructor.call(this, {
							layout : 'fit',
							border : false,
							items : this.formPanel,
							modal : true,
							height : 300,
							width : 640,
							title : '编辑系统账户配置条目',
							buttonAlign : 'center',
							buttons : [{
										text : '保存',
										iconCls : 'btn-save',
										scope : this,
										hidden:this.isAllReadOnly,
										disabled:this.isAllReadOnly,
										handler : this.save
									}, {
										text : '取消',
										iconCls : 'btn-cancel',
										scope : this,
										hidden:this.isAllReadOnly,
										disabled:this.isAllReadOnly,
										handler : this.cancel
									}]
						});
			},// end of the constructor
			// 初始化组件
			initUIComponents : function() {
				this.formPanel = new Ext.FormPanel({
							layout:'column',
							bodyStyle : 'padding:10px',
							autoScroll : true,
							monitorValid : true,
							frame : true,
						    plain : true,
						    labelAlign : "right",
							defaults : {
								anchor : '96%',
								labelWidth : 110,
								columnWidth : 1,
							    layout : 'column'
							},
							items : [{  
									columnWidth : 1,
								     layout : 'form',
									 items :[{
											xtype : 'textfield',
											fieldLabel : '名称',
											allowBlank : false,
											name : 'obSystemaccountSetting.typeName',
											anchor : '98%'
										},{
												xtype:'hidden',
												name:'obSystemaccountSetting.typeKey'
										},{
												xtype:'hidden',
												name:'obSystemaccountSetting.id'
										}]
								},{  
									 columnWidth : 1,
								     layout : 'form',
									 items :[{
											xtype : 'textarea',
											fieldLabel : '名称说明',
											allowBlank : false,
											name : 'obSystemaccountSetting.mark',
											anchor : '98%',
											readOnly:true
										  
									}]
								},{  
									 columnWidth : 1,
								     layout : 'form',
									 items :[{
											xtype : 'textarea',
											fieldLabel : '使用说明',
											allowBlank : false,
											name : 'obSystemaccountSetting.usedRemark',
											anchor : '98%',
											readOnly:true
										  
									}]
								},{
										 html:"<div style='padding:10px 0px 0px 100px;color:red;'>注：只能修改名称,系统将会立即使用修改后的名称!</div> "
								 }]
						});
						
						// 加载表单对应的数据
						if (this.itemId  != null && this.itemId  != 'undefined') {
							var   panel =this;
							this.formPanel.loadData({
										url : __ctxPath + "/creditFlow/creditAssignment/accountSetting/getObSystemaccountSetting.do?id="+ this.itemId ,
										root : 'data',
										preName : 'obSystemaccountSetting',
										success : function(response, options) {
											
										}
									});
						}
			},

			/**
			 * 重置
			 * 
			 * @param {}
			 *  formPanel
			 */
			reset : function() {
				this.formPanel.getForm().reset();
			},
			/**
			 * 取消
			 * 
			 * @param {}
			 *  window
			 */
			cancel : function() {
				this.close();
			},
			/**
			 * 保存记录
			 */
			save : function() {
				var refreshPanel=this.refreshPanel;
				this.formPanel.getForm().submit({
								clientValidation: false, 
								scope : this,
								method : 'post',
								waitMsg : '数据正在提交，请稍后...',
								scope: this,
								url : __ctxPath + '/creditFlow/creditAssignment/accountSetting/saveObSystemaccountSetting.do',
								success : function(fp, action) {
									Ext.ux.Toast.msg('操作信息','保存成功');
									refreshPanel.getStore().reload();
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
			}// end of save
		});