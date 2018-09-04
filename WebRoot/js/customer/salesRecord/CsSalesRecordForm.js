/**
 * @author 
 * @createtime 
 * @class CsSalesRecordForm
 * @extends Ext.Window
 * @description CsSalesRecord表单
 * @company 智维软件
 */
CsSalesRecordForm = Ext.extend(Ext.Window, {
			//构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				this.initUIComponents();
				CsSalesRecordForm.superclass.constructor.call(this, {
							id : 'CsSalesRecordFormWin',
							layout : 'fit',
							items : this.formPanel,
							modal : true,
							height : 300,
							width : 800,
							maximizable : true,
							title : this.titleValue,
							frame:true,
							buttonAlign : 'center',
							buttons : [
										{
											text : '保存',
											iconCls : 'btn-save',
											scope : this,
											hidden:this.isHidden,
											handler : this.save
										}, {
											text : '重置',
											iconCls : 'btn-reset',
											scope : this,
											hidden:this.isHidden,
											handler : this.reset
										}, {
											text : '取消',
											iconCls : 'btn-cancel',
											scope : this,
											hidden:this.isHidden,
											handler : this.cancel
										}
							         ]
				});
			},//end of the constructor
			//初始化组件
			initUIComponents : function() {
				var leftlabel = 130;
				//this.csSalesRecordCommon =new csSalesRecordCommon({})
				this.formPanel = new Ext.form.FormPanel({
						layout : 'form',
						bodyStyle : 'padding:10px',
						autoScroll : true,
						frame : true,
						labelAlign : 'right',
						defaults : {
								anchor : '100%',
								columnWidth : 1
								//labelWidth : 60
							},
						items : [{
									layout : "column",
									border : false,
									scope : this,
									defaults : {
										anchor : '100%',
										columnWidth : 1,
										isFormField : true,
										labelAlign : 'right',
										labelWidth : leftlabel
									},
									items : [{
															xtype : "hidden",
															name : "csSalesRecord.saleId"
														},{
															xtype : "hidden",
															name : "csSalesRecord.userId"
														},{
															xtype : "hidden",
															name : "csSalesRecord.companyId"
														},{
															xtype : "hidden",
															name : "csSalesRecord.personType",
															value:this.personType
														},{
															columnWidth : .34, // 该列在整行中所占的百分比
															layout : "form", // 从上往下的布局
															labelWidth : leftlabel,
															items : [{
																		fieldLabel : "人员名称",
																		xtype : "combo",
																		triggerClass : 'x-form-search-trigger',
																		readOnly : this.isAllReadOnly,
																		name : "csSalesRecord.userName",
																		blankText : "人员名称不能为空，请正确填写!",
																		allowBlank : false,
																		scope : this,
																		anchor : "100%",// 控制文本框的长度
																		onTriggerClick : function() {
																				var op=this.ownerCt.ownerCt.ownerCt.ownerCt;
																				var AppUserList=function(obj){
																					op.getCmpByName('csSalesRecord.userId').setValue("");
																					op.getCmpByName('csSalesRecord.userName').setValue("");
																					op.getCmpByName('csSalesRecord.userNumber').setValue("");
																					op.getCmpByName('csSalesRecord.userGroupName').setValue("");
																					
																					if(obj.userId!=null&&obj.userId!=0){
																						op.getCmpByName('csSalesRecord.userId').setValue(obj.userId);
																					}if(obj.fullname!=null&&obj.fullname!=0){
																						op.getCmpByName('csSalesRecord.userName').setValue(obj.fullname);
																					}if(obj.userNumber!=null&&obj.userNumber!=0){
																						op.getCmpByName('csSalesRecord.userNumber').setValue(obj.userNumber);
																					}if(obj.depNames!=null&&obj.depNames!=0){
																						op.getCmpByName('csSalesRecord.userGroupName').setValue(obj.depNames);
																					}
																				}
																				selectAppUser(AppUserList);
																			
																		},
																		resizable : true,
																		mode : 'romote',
																		editable : false,
																		lazyInit : false,
																		typeAhead : true,
																		minChars : 1,
																		
																		triggerAction : 'all'
								
																	}]
														}, {
															columnWidth : .33, // 该列在整行中所占的百分比
															layout : "form", // 从上往下的布局
															labelWidth : leftlabel,
															items : [{
																		fieldLabel : "员工编号",
																		xtype : "textfield",
																		name : "csSalesRecord.userNumber",
																		readOnly : true,
																		blankText : "项目编号不能为空，请正确填写!",
																		anchor : "100%"
								
																	}]
														},{
															columnWidth : .33, // 该列在整行中所占的百分比
															layout : "form", // 从上往下的布局
															labelWidth : leftlabel,
															items : [{
																xtype : "textfield",
																fieldLabel : "部门名称",
																fieldClass : 'field-align',
																name : "csSalesRecord.userGroupName",
																readOnly : true,
																blankText : "贷款金额不能为空，请正确填写!",
																anchor : "100%",// 控制文本框的长度
																listeners : {}
															}]
												},{
															columnWidth : .5, // 该列在整行中所占的百分比
															layout : "form", // 从上往下的布局
															labelWidth : leftlabel,
															items : [{
																xtype : "textfield",
																fieldLabel : "有效呼出次数",
																fieldClass : 'field-align',
																name : "csSalesRecord.saleCommCount",
																readOnly : this.isAllReadOnly,
																anchor : "100%",// 控制文本框的长度
																listeners : {}
															}]
												},{
															columnWidth : .5, // 该列在整行中所占的百分比
															layout : "form", // 从上往下的布局
															labelWidth : leftlabel,
															items : [{
																xtype : "textfield",
																fieldLabel : "呼出时长",
																fieldClass : 'field-align',
																name : "csSalesRecord.saleCommTime",
																readOnly : this.isAllReadOnly,
																anchor : "100%",// 控制文本框的长度
																listeners : {}
															}]
												},{
															columnWidth : .5, // 该列在整行中所占的百分比
															layout : "form", // 从上往下的布局
															labelWidth : leftlabel,
															items : [{
																xtype : "textfield",
																fieldLabel : "当天上门拜访未成交数",
																fieldClass : 'field-align',
																name : "csSalesRecord.faceToseeCount",
																readOnly : this.isAllReadOnly,
																anchor : "100%",// 控制文本框的长度
																listeners : {}
															}]
												},{
															columnWidth : .5, // 该列在整行中所占的百分比
															layout : "form", // 从上往下的布局
															labelWidth : leftlabel,
															items : [{
																xtype : "textfield",
																fieldLabel : "当天上门拜访成交数",
																fieldClass : 'field-align',
																name : "csSalesRecord.faceToDealCount",
																readOnly : this.isAllReadOnly,
																anchor : "100%",// 控制文本框的长度
																listeners : {}
															}]
												},{
															columnWidth : 1, // 该列在整行中所占的百分比
															layout : "form", // 从上往下的布局
															labelWidth : leftlabel,
															items : [{
																xtype : "textarea",
																fieldLabel : "备注",
																fieldClass : 'field-align',
																name : "csSalesRecord.saleRemark",
																readOnly : this.isAllReadOnly,
																anchor : "100%",// 控制文本框的长度
																listeners : {}
															}]
												}]
							}]
					});
				//加载表单对应的数据	
				if (this.saleId != null && this.saleId != 'undefined') {
					this.formPanel.loadData({
								url : __ctxPath + '/customer/salesRecord/getCsSalesRecord.do?saleId='+ this.saleId,
								root : 'data',
								preName : 'csSalesRecord'
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
						url:__ctxPath + '/customer/salesRecord/saveCsSalesRecord.do',
						callback:function(fp,action){
							var gridPanel = Ext.getCmp('CsSalesRecordGrid');
							if (gridPanel != null) {
								gridPanel.getStore().reload();
							}
							this.close();
						}
					}
				);
			}//end of save

		});