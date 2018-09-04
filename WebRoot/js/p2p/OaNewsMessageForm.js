/**
 * @author 
 * @createtime 
 * @class OaNewsMessageForm
 * @extends Ext.Window
 * @description OaNewsMessage表单
 * @company 智维软件
 */
OaNewsMessageForm = Ext.extend(Ext.Window, {
			//构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				this.initUIComponents();
				OaNewsMessageForm.superclass.constructor.call(this, {
							id : 'OaNewsMessageFormWin',
							layout : 'fit',
							items : this.formPanel,
							modal : true,
							height : 400,
							width : 500,
							maximizable : true,
							title : '站内信详细信息',
							buttonAlign : 'center',
							buttons : [
										{
											text : '发送',
											iconCls : 'btn-save',
											scope : this,
											hidden:this.isSendHide,
											handler : this.send
										},{
											text : '只保存不发送',
											iconCls : 'btn-save',
											scope : this,
											hidden:this.isAllHide,
											handler : this.save
										}, {
											text : '取消',
											iconCls : 'btn-cancel',
											scope : this,
											hidden:this.isAllHide,
											handler : this.cancel
										}
							         ]
				});
			},//end of the constructor
			//初始化组件
			initUIComponents : function() {
				this.formPanel = new Ext.form.FormPanel({
							layout : 'form',
							bodyStyle : 'padding:5px',
							border : false,
							autoScroll:true,
							labelAlign : 'right',
							frame : true,
							defaults : {
								anchor : '96%,96%',
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
									labelWidth : 60
								},
								items : [{
										name : 'oaNewsMessage.id',
										xtype : 'hidden',
										value : this.id == null ? '' : this.id
									},{
										columnWidth : 1, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : 60,
										labelAlign :'right',
										items : [{
												xtype : 'textfield',
												fieldLabel : '标题',	
				 								name : 'oaNewsMessage.title'
				 								,maxLength: 255,
				 								anchor : '100%',
				 								readOnly:this.isAllReadOnly,
				 								allowBlank : false,
				 								blankText : "标题不能为空，请正确填写!"
				
				 							}]
								  },{
								  	columnWidth : 1, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : 60,
										labelAlign :'right',
										items :[{
												xtype : "dickeycombo",
												fieldLabel : '类型',	
				 								hiddenName : 'oaNewsMessage.type',
				 								nodeKey : 'webletter',
				 								allowBlank : false,
				 								emptyText : '请选择',
				 								maxLength: 255,
				 								anchor : '100%',
				 								scope : this,
				 								readOnly:this.isAllReadOnly,
												editable : false,
				 								listeners : {
													scope : this,
													afterrender : function(combox) {
														var st = combox.getStore();
														st.on("load", function() {
																combox.setValue(combox.getValue());
																combox.clearInvalid();
														})
													}
												}
				
				 							}]
								  },{
										columnWidth : 0.7, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : 60,
										labelAlign :'right',
										items : [{
				 								xtype : "combo",
				 								triggerClass : 'x-form-search-trigger',
				 								fieldLabel : '接收人',	
				 								name : 'oaNewsMessage.comment1',
				 								anchor : "100%",
				 								editable : false,
				 								allowBlank : false,
				 								readOnly:this.isSendHide,
												onTriggerClick : function(cc) {
													var obj = this;
													var appuerIdObj = obj.nextSibling();
													var userIds = appuerIdObj.getValue();
													if ("" == obj.getValue()) {
														userIds = "";
													}
													new MemberDialog({
														userIds : userIds,
														userName : obj.getValue(),
														single : false,
														title : "请选择用户",
														callback : function(uId, uname) {
															obj.setValue(uId);
															obj.setRawValue(uname);
															appuerIdObj.setValue(uId);
														}
													}).show();
												}
											},{
												xtype : "hidden",
												name : "oaNewsMessage.comment2",
												value : ""
											}]
								},{
										columnWidth : .3, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : 60,
										labelAlign :'right',
										items : [{
												xtype : "checkbox",
												boxLabel : "全员发送",
												disabled : this.isSendHide,
												anchor : "100%",
												name : "isAllSend",
												checked : this.record == null
														|| this.record.data.oaNewsMessage.isAllSend == 0
														? null
														: true,
												listeners : {
													scope : this,
													'check' : function(box, value) {
														if (value == true) {
															this
																.getCmpByName('oaNewsMessage.isAllSend')
																.setValue(1);
															this
																	.getCmpByName('oaNewsMessage.comment2')
																	.setValue("_ALL");
															this
																.getCmpByName('oaNewsMessage.comment1')
																.setValue("全体成员");
															this
																.getCmpByName('oaNewsMessage.comment1')
																.setReadOnly(true);
														} else {
															this
																.getCmpByName('oaNewsMessage.isAllSend')
																.setValue(0);
															this
																	.getCmpByName('oaNewsMessage.comment2')
																	.setValue("");
															this
																.getCmpByName('oaNewsMessage.comment1')
																.setValue("");
															this
																.getCmpByName('oaNewsMessage.comment1')
																.setReadOnly(false);
														}
													}
												}
										},{
											xtype : "hidden",
											name : "oaNewsMessage.isAllSend",
											value : 0
										}]
									},{
								  	columnWidth : 1, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : 60,
										labelAlign :'right',
										items :[{
												fieldLabel : '操作人',	
				 								name : 'oaNewsMessage.operator',
				 								xtype : "hidden",
				 								maxLength: 255
				 								
				 							}]
								  },{
								  	columnWidth : 1, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : 60,
										labelAlign :'right',
										items :[{
												fieldLabel : '发件人（全名）',	
												anchor : "100%",
				 								name : 'oaNewsMessage.addresser',
				 								xtype : "hidden",
				 								maxLength: 255
				 							}]
								  },{
								  	columnWidth : 1, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : 60,
										labelAlign :'right',
										items :[{
												fieldLabel : '状态（修改为已发送和未发送）',	
				 								name : 'oaNewsMessage.status',
				 								xtype : "hidden",
				 								anchor : "100%",
				 								maxLength: 255,
				 								value:0
				 							}]
								  },{
								  	columnWidth : 1, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : 60,
										labelAlign :'right',
										items :[{
												fieldLabel : '阅读时间',	
				 								name : 'oaNewsMessage.readTime',
				 								xtype : "hidden",
				 								anchor : "100%",
												format:'Y-m-d'
				 							}]
								  },{
								  	columnWidth : 1, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : 60,
										labelAlign :'right',
										items :[{
				 								xtype : 'textarea',
												width : 1000,
												height : 300,
												anchor : "100%",
												readOnly:this.isAllReadOnly,
												style : "margin-top:4px",
												fieldLabel : '内容',	
				 								name : 'oaNewsMessage.content'
				 								,maxLength: 255,
				 								allowBlank : false,
				 								blankText : "内容不能为空，请正确填写!"
				 							}]
								  }]
						}]});
				//加载表单对应的数据	
				if (this.id != null && this.id != 'undefined') {
					this.formPanel.loadData({
								url : __ctxPath + '/p2p/getOaNewsMessage.do?id='+ this.id,
								root : 'data',
								preName : 'oaNewsMessage'
							});
				}
				
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
				$postForm({
						formPanel:this.formPanel,
						scope:this,
						url:__ctxPath + '/p2p/saveOaNewsMessage.do',
						callback:function(fp,action){
							var gridPanel = Ext.getCmp('OaNewsMessageGrid');
							if (gridPanel != null) {
								gridPanel.getStore().reload();
							}
							this.close();
						}
					}
				);
			}//end of save
			,
			/**
			 * 发送给指定的收件人
			 */
			send : function() {
				$postForm({
						formPanel:this.formPanel,
						scope:this,
						url:__ctxPath + '/p2p/newsendSomeUserMessageOaNewsMessage.do',
						callback:function(fp,action){
							var gridPanel = Ext.getCmp('OaNewsMessageGrid');
							if (gridPanel != null) {
								gridPanel.getStore().reload();
							}
							this.close();
						}
					}
				);
			}//end of send


		});