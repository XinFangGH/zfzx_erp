CommRecorForm = Ext.extend(Ext.Window, {
	isLook : false,
	// 构造函数
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		};
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		CommRecorForm.superclass.constructor.call(this, {
					layout : 'fit',
					autoScroll:true,
					items : this.formPanel,
					modal : true,
					height : 400,
					width : 650,
					maximizable : true,
					title : this.titleChange,
					buttonAlign : 'center',
					buttons : [{
								text : '保存',
								iconCls : 'btn-save',
								hidden : this.isLook,
								scope : this,
								handler : this.save
							}, {
								text : '重置',
								iconCls : 'btn-reset',
								hidden : this.isLook,
								scope : this,
								handler : this.reset
							}, {
								text : '取消',
								iconCls : 'btn-cancel',
								hidden : this.isLook,
								scope : this,
								handler : this.cancel
							}]
				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		var leftlabel = 75;
		this.formPanel = new Ext.form.FormPanel({
			layout : 'column',
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
						xtype : 'hidden',
	                    name : 'bpCustProspectiveFollowup.followId'
					},{
							columnWidth : .5, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : leftlabel,
							items : [{
								xtype : "combo",
								triggerClass : 'x-form-search-trigger',
								hiddenName : "followPersonId1",
								editable : false,
								fieldLabel : "跟进人",
								blankText : "跟进人不能为空，请正确填写!",
								allowBlank : false,
								anchor : "100%",
								readOnly : this.isRead,
								onTriggerClick : function(cc) {
									var obj = this;
									var appuerIdObj = obj.nextSibling();
									var userIds = appuerIdObj.getValue();
									if ("" == obj.getValue()) {
										userIds = "";
									}
									new UserDialog({
										userIds : userIds,
										userName : obj.getValue(),
										single : false,
										title : "选择跟进人",
										callback : function(uId, uname) {
											obj.setValue(uId);
											obj.setRawValue(uname);
											appuerIdObj.setValue(uId);
										}
									}).show();
								}
							},{
	                       	 	xtype : 'hidden',
	                        	name : 'bpCustProspectiveFollowup.followPersonId'
							}]
						}, {
							columnWidth : .5, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : leftlabel,
							items : [{
										xtype : "dickeycombo",
										nodeKey : 'comm_type',
										hiddenName : 'bpCustProspectiveFollowup.followType',
										fieldLabel : "跟进方式",
										anchor : '100%',
										allowBlank : false,
										editable : true,
										blankText : "跟进方式不能为空，请正确填写!",
										readOnly : this.isRead,
										listeners : {
											afterrender : function(combox) {
												var st = combox.getStore();
												st.on("load", function() {
													if (combox.getValue() == 0
															|| combox
																	.getValue() == 1
															|| combox
																	.getValue() == ""
															|| combox
																	.getValue() == null) {
														combox.setValue("");
													} else {
														combox.setValue(combox
																.getValue());
													}
													combox.clearInvalid();
												})
											},
											select : function(combo, record,
													index) {}
										}
									}]
						},{
							columnWidth : .5, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : leftlabel,
							items : [{
										xtype : 'datefield',
										format : 'Y-m-d',
										fieldLabel : '跟进时间',
										name : 'bpCustProspectiveFollowup.followDate',
										allowBlank : false,
										anchor : '100%',
										readOnly : this.isRead
										
										
									}]
				},{
							columnWidth : .25, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : leftlabel,
							items : [{
								xtype : "dickeycombo",
								nodeKey : 'bpCustProspectiveFollowup_successRate',
								hiddenName : 'bpCustProspectiveFollowup.successRate',
								fieldLabel : "跟进成功率",
								anchor : '100%',
								allowBlank : false,
								editable : false,
								blankText : "跟进成功率不能为空，请正确填写!",
								readOnly : this.isRead,
								listeners : {
									afterrender : function(combox) {
										var st = combox.getStore();
										st.on("load", function() {
											if (combox.getValue() == 0|| combox.getValue() == 1
													|| combox.getValue() == ""|| combox.getValue() == null) {
												combox.setValue("");
											} else {
												combox.setValue(combox.getValue());
											}
											combox.clearInvalid();
										})
									},
									select : function(combo, record,index) {}
								}
							}]
				},{
					columnWidth : .25, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : leftlabel,
					items : [{
						xtype : "dickeycombo",
						nodeKey : 'bpCustProspectiveFollowup_customerSystematics',
						hiddenName : 'bpCustProspectiveFollowup.customerSystematics',
						fieldLabel : "意向客户分级",
						anchor : '100%',
						editable : false,
						readOnly : this.isRead,
						listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
									if (combox.getValue() == 0|| combox.getValue() == 1
											|| combox.getValue() == ""|| combox.getValue() == null) {
										combox.setValue("");
									} else {
										combox.setValue(combox.getValue());
									}
									combox.clearInvalid();
								})
							},
							select : function(combo, record,index) {}
						}
					}]
				},{
					columnWidth : 1, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : leftlabel,
					items : [{
								xtype : 'textfield',
								fieldLabel : '跟进标题',
								anchor : '100%',
								readOnly : this.isRead,
								name : 'bpCustProspectiveFollowup.followTitle'
									}]
				},{
					columnWidth : 1, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : leftlabel,
					items : [{
								xtype : 'textarea',
								fieldLabel : '跟进内容',
								anchor : '100%',
								readOnly : this.isRead,
								name : 'bpCustProspectiveFollowup.followInfo'
									}]
				},{
							columnWidth : 1, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : leftlabel,
							items : [{
								xtype : "combo",
								triggerClass : 'x-form-search-trigger',
								hiddenName : "commentorrId1",
								editable : false,
								fieldLabel : "点评人",
								readOnly : this.isReadOnly,
								anchor : "100%",
								onTriggerClick : function(cc) {
									var obj = this;
									var appuerIdObj = obj.nextSibling();
									var userIds = appuerIdObj.getValue();
									if ("" == obj.getValue()) {
										userIds = "";
									}
									new UserDialog({
										userIds : userIds,
										userName : obj.getValue(),
										single : false,
										title : "选择点评人",
										callback : function(uId, uname) {
											obj.setValue(uId);
											obj.setRawValue(uname);
											appuerIdObj.setValue(uId);
										}
									}).show();
								}
							},{
	                       	 	xtype : 'hidden',
	                        	name : 'bpCustProspectiveFollowup.commentorrId'
							}]
						},{
					columnWidth : 1, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : leftlabel,
					items : [{
								xtype : 'textarea',
								fieldLabel : '点评内容',
								anchor : '100%',
								readOnly : this.isReadOnly,
								name : 'bpCustProspectiveFollowup.commentRemark'
									}]
				}]
		});
		
		// 加载表单对应的数据
		if (this.followId != null && this.followId != 'undefined') {
			var panel=this
			this.formPanel.loadData({
						url:__ctxPath + "/creditFlow/customer/customerProsperctiveFollowup/getBpCustProspectiveFollowup.do?followId="+this.followId,
						root : 'data',
						preName : ['bpCustProspectiveFollowup',"object"],
						success : function(resp, options) {
							var respText = resp.responseText;
							var alarm_fields = Ext.util.JSON.decode(respText);
							var appUsers= alarm_fields.data.bpCustProspectiveFollowup.name;
							var appUserId= alarm_fields.data.bpCustProspectiveFollowup.followPersonId;
							if(""!=appUserId &&  null!=appUserId){
							   this.getCmpByName('followPersonId1').setValue(appUserId);
							   this.getCmpByName('followPersonId1').setRawValue(appUsers);
							   this.getCmpByName('followPersonId1').nextSibling().setValue(appUserId);
							}
							
							var commentorrName= alarm_fields.data.bpCustProspectiveFollowup.commentorName;
							var commentorrId= alarm_fields.data.bpCustProspectiveFollowup.commentorrId;
							if(""!=commentorrId &&  null!=commentorrId){
							   this.getCmpByName('commentorrId1').setValue(commentorrId);
							   this.getCmpByName('commentorrId1').setRawValue(commentorrName);
							   this.getCmpByName('commentorrId1').nextSibling().setValue(commentorrId);
							}
						}
					});
		}

	},// end of the initcomponents

	/**
	 * 重置
	 * 
	 * @param {}
	 *            formPanel
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
		var formPanel1 =this.formPanel;
		formPanel1.getForm().submit({
			 clientValidation: false,
					url :__ctxPath+ '/creditFlow/customer/customerProsperctiveFollowup/updateBpCustProspectiveFollowup.do',
					method : 'post',
					waitMsg : '数据正在提交，请稍后...',
					scope: this,
					success : function(fp, action) {
						Ext.ux.Toast.msg('操作信息', '保存信息成功!');
						flashTargat.reload();
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