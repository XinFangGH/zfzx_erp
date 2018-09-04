/**企业客户关怀显示页面*/
InvestEnterpriseCareForm = Ext.extend(Ext.Window, {
			//构造函数
			constructor : function(_cfg) {
	
				Ext.applyIf(this, _cfg);
				
				//必须先初始化组件
				this.initUIComponents();
				InvestEnterpriseCareForm.superclass.constructor.call(this, {
							id : 'InvestPersonCareFormWin',
							layout : 'fit',
							items : this.formPanel,
							modal : true,
							height :this.careHidden?450:650,
							width : 750,
							maximizable : true,
//							closeAction : 'hide',
							title : '个人客户关怀管理详细信息',
							buttonAlign : 'center',
							buttons : this.careHidden?null:[
										{
											text : '保存',
											iconCls : 'btn-save',
											scope : this,
											handler : this.saveRs
										},/* {
											text : '重置',
											iconCls : 'btn-reset',
											scope : this,
											handler : this.reset
										}, */{
											text : '取消',
											iconCls : 'btn-cancel',
											scope : this,
											handler : this.cancel
										}
							         ],
							         listeners : {  
							            'close' : function() { 
											Ext.getCmp('InvestPersonGrid').getStore().reload();
							            }  
							        } 
				});
			},//end of the constructor
			//初始化组件
			initUIComponents : function() {
				 var leftlabel=100;
				var rightlabel=90
				var investPersonCare = this.InvestPersonCare;
				this.formPanel = new Ext.FormPanel({
							id : 'InvestEnterpriseCareForm',
							layout : 'form',
							bodyStyle : 'padding:10px',
							autoScroll : true,
							frame : true,
							labelAlign : 'right',
							defaults : {
									anchor : '98%',
									columnWidth : 1,
									labelWidth : 60
								},
							items : [{
								layout : "column",
								xtype : 'fieldset',
								title : '企业基本信息',
								collapsible : true,
								hidden : this.isHidden,
								items : [{
							bodyStyle : 'padding-right:10px',
							columnWidth : 1, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							items : [{
								xtype : 'fieldset',
								layout : "column",
								border : false,
								defaults : {
									anchor : '100%',
									columnWidth : 1,
									isFormField : true,
									labelWidth : leftlabel
								},
								items : [{
									columnWidth : 1, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : leftlabel,
									items : [{
												xtype : 'textfield',
												anchor : '100%',
												allowBlank : false,
												fieldLabel : "企业名称",
												name : "investEnterprise.enterprisename",
												readOnly : true,
												blankText : "企业名称不能为空，请正确填写!"
											}]
								}, {
									columnWidth : 0.5, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : leftlabel,
									items : [{
												xtype : "textfield",
												anchor : '100%',
												name : "investEnterprise.shortname",
												fieldLabel : "企业简称",
												readOnly : true,
												blankText : "企业简称不能为空，请正确填写!"

											}]
								}, {
									columnWidth : 0.5, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : rightlabel,
									items : [{
										xtype : "combo",
										triggerClass : 'x-form-search-trigger',
										name : "investEnterprise.mainHangye",
										fieldLabel : "行业类别",
										anchor : '100%',
										scope : this,
										readOnly : true,
										onTriggerClick : function(e) {
											var obj = this;
											var oobbj = obj.ownerCt.ownerCt
													.getCmpByName("investEnterprise.hangyeType");
											selectTradeCategory(obj, oobbj);
										}
									}]
								}, {
									columnWidth : 0.5, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : leftlabel,
									items : [{
										xtype : "textfield",
										name : "investEnterprise.organizecode",
										allowBlank : false,
										fieldLabel : "组织机构代码",
										regex : /^[A-Za-z0-9]{8}-[A-Za-z0-9]{1}/,
										regexText : '组织机构代码格式不正确',
										readOnly : true,
										blankText : "组织机构代码不能为空，请正确填写!",
										anchor : "100%"
									}]
								}, {
									xtype : "hidden",
									name : 'investEnterprise.hangyeType'
								}, {
									columnWidth : 0.5, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : rightlabel,
									items : [{
												xtype : "textfield",
												name : "investEnterprise.cciaa",
												fieldLabel : "营业执照号码",
												readOnly : true,
												blankText : "营业执照号码不能为空，请正确填写!",
												allowBlank : false,
												anchor : "100%"
											}]
								}, {
									columnWidth : 0.5, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : leftlabel,
									items : [{
										xtype : "textfield",
										name : "investEnterprise.telephone",
										allowBlank : false,
										fieldLabel : "联系电话",
										readOnly : true,
										blankText : "联系电话不能为空，请正确填写!",
										regex : /((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)/,
										regexText : '联系电话格式不正确',
										anchor : "100%"
									}]
								}, {
									columnWidth : 0.5, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : rightlabel,
									items : [{
												xtype : "textfield",
												allowBlank : false,
												name : "investEnterprise.postcoding",
												fieldLabel : "邮政编码",
												readOnly : true,
												blankText : "邮政编码不能为空，请正确填写!",
												regex : /^[0-9]{6}$/,
												regexText : '邮政编码格式不正确',
												anchor : "100%"
											}]
								}, {
									columnWidth : 1, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : leftlabel,
									items : [{
												xtype : "textfield",
												fieldLabel : "通讯地址",
												readOnly : true,
												allowBlank : false,
												name : "investEnterprise.area",
												anchor : '100%'
											}]
								}]
							}]

						}]
					},{
						layout : "column",
						xtype : 'fieldset',
						title : '客户关怀记录',
						collapsible : true,
						hidden : this.isCloHidden,
						items :[new CustomerCareRecords({
							peid :this.enterpriseId,
							isEnterprise : 0,
							isHiddenEdit : this.isHiddenEdit
						})]
					},{
						layout : "column",
						xtype : 'fieldset',
						title : '立即关怀',
						collapsible : true,
						hidden : this.careHidden,
						items : [{
							layout : "column",
							columnWidth : 1,
							items : [{
								xtype : "hidden",
								name : 'investPersonCare.id'
							},{
									layout : 'form',
									columnWidth : .50,
									labelWidth : 60,
									labelAlign : 'right',
									xtype : 'container',
									items : [{
										xtype : "dickeycombo",
										hiddenName : "investPersonCare.careWay",
										nodeKey : 'careWay', // xx代表分类名称
										fieldLabel : "关怀方式",
										emptyText : "请选择",
										readOnly : this.isAllReadOnly,
										allowBlank : false,
										value : investPersonCare == null ? '' : investPersonCare.careWay,
										anchor : '100%',
										listeners : {
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
								layout : 'form',
								columnWidth : .50,
								labelWidth : 60,
								labelAlign : 'right',
								items : [{
											fieldLabel : '关怀标题',
											xtype : 'textfield',
											name : 'investPersonCare.careTitle',
											allowBlank : false,
											readOnly : this.isAllReadOnly,
											anchor : '100%'
										}]
								}]
							},{
								layout : "column",
								columnWidth : 1,
								items : [{
									layout : 'form',
									columnWidth : 1,
									labelWidth : 60,
									labelAlign : 'right',
									items : [{
												fieldLabel : '关怀内容',
												xtype : 'textfield',
												allowBlank : false,
												name : 'investPersonCare.careContent',
												readOnly : this.isAllReadOnly,
												anchor : '100%'
											}]
									}]
							},{
								layout : "column",
								columnWidth : 1,
								items : [{
									layout : 'form',
									columnWidth : .50,
									labelWidth : 60,
									items : [{
												fieldLabel : '关怀时间',
												readOnly : this.isAllReadOnly,
												name : 'investPersonCare.careDate',
												allowBlank : false,
//												editor : new Ext.form.DateField({}),
												xtype : 'datefield',
												format : 'Y-m-d',
												anchor : "100%"
										}]
								},{
									layout : 'form',
									columnWidth : .50,
									labelWidth : 60,
									labelAlign : 'right',
									items : [{
											xtype : "combo",
											triggerClass : 'x-form-search-trigger',
											hiddenName : "investPersonCare.careMan",
											fieldLabel : "关怀人",
											blankText : "关怀人不能为空，请正确填写!",
											allowBlank : false,
											readOnly : this.isAllReadOnly,
											anchor : "100%",
											onTriggerClick : function(cc) {
												var obj = this;
												var appuerIdObj = obj.nextSibling();
												var userIds = appuerIdObj.getValue();
												if ("" == obj.getValue()) {
													userIds = "";
												}
												new UserDialog({
													single : false,
													userIds : userIds,
													userName : obj.getValue(),
													callback : function(uId, uname) {
														obj.setValue(uId);
														obj.setRawValue(uname);
														appuerIdObj.setValue(uId);
													}
												}).show();

								}
										},{
											xtype : 'hidden',
											value :""
										}]
									}]
							},{
								layout : "column",
								columnWidth : 1,
								items : [{
									layout : 'form',
									columnWidth : 1,
									labelWidth : 60,
									labelAlign : 'right',
									items : [{
												fieldLabel : '备注',
												xtype : 'textarea',
												name : 'investPersonCare.careMarks',
												readOnly : this.isAllReadOnly,
												anchor : '100%'
											},{
												xtype : 'hidden',
												name : 'investPersonCare.isEnterprise',
												value : 0
											}]
									}]
							}]
					}]
						});
				//加载表单对应的数据	
				if (this.enterpriseId != null && this.enterpriseId != 'undefined') {
					this.formPanel.loadData({
								url : __ctxPath + '/customer/getInvestEnterprise.do?id='+this.enterpriseId,
								root : 'data',
								preName : ['investEnterprise','investPerson'],
								scope : this,
								success : function(resp, options) {
									
									var obj = Ext.decode(resp.responseText);
								}
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
			saveRs : function(){
				var pannel = this;
				this.formPanel.getForm().submit({
					url:__ctxPath + '/customer/saveEnterpriseCareInvestPersonCare.do?',
					method : 'POST',
					scope : this,
					//root :'tesult',
					params :{
						enterpriseId : this.enterpriseId
					},
					success : function(response, options){
						Ext.ux.Toast.msg('状态', '保存成功');
						Ext.getCmp('CustomerCareRecords').getStore().reload();
						this.getCmpByName('investPersonCare.careWay').setValue(null);
						this.getCmpByName('investPersonCare.careTitle').setValue(null);
						this.getCmpByName('investPersonCare.careContent').setValue(null);
						this.getCmpByName('investPersonCare.careDate').setValue(null);
						this.getCmpByName('investPersonCare.careMarks').setValue(null);
					}
				});
			},
			save : function() {
				/*$postForm({
						formPanel:this.formPanel,
						scope:this,
						url:__ctxPath + '/customer/saveInvestPersonCare.do',
						callback:function(fp,action){
							var gridPanel = Ext.getCmp('InvestPersonCareGrid');
							if (gridPanel != null) {
								gridPanel.getStore().reload();
							}
							this.close();
						}
					});*/
			}//end of save

		});