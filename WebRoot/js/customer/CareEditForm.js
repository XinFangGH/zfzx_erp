/**
 * @author 
 * @createtime 
 * @class InvestPersonCareForm
 * @extends Ext.Window
 * @description InvestPersonCare表单
 * @company 智维软件
 */
CareEditForm = Ext.extend(Ext.Window, {
			//构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				if (typeof (_cfg.investPersonId) != "undefined") {
					this.investPersonId = _cfg.investPersonId;
				}
				if (typeof (_cfg.enterpriseId) != "undefined") {
					this.enterpriseId = _cfg.enterpriseId;
				}
				if (typeof (_cfg.careHidden) != "undefined") {
					this.careHidden = _cfg.careHidden;
				}else{
					this.careHidden = false;
				}
				if(typeof(_cfg.isCloHidden)!="undefined"){
					this.isCloHidden = _cfg.isCloHidden
					alert(this.isCloHidden)
				}else{
					this.isCloHidden = false;
				}
				
				//必须先初始化组件
				this.initUIComponents();
				CareEditForm.superclass.constructor.call(this, {
							id : 'CareEditFormWin',
							layout : 'fit',
							items : this.formPanel,
							modal : true,
							height : 300,
							width : 750,
							maximizable : true,
							title : '编辑关怀记录',
							buttonAlign : 'center',
							buttons : [
										{
											text : '保存',
											iconCls : 'btn-save',
											scope : this,
											handler : this.saveRs
										},{
											text : '取消',
											iconCls : 'btn-cancel',
											scope : this,
											handler : this.cancel
										}
							         ],
							         listeners : {  
							            'close' : function() { 
											Ext.getCmp('CustomerCareRecords').getStore().reload();
							            }  
							        }
				});
			},//end of the constructor
			//初始化组件
			initUIComponents : function() {
//				var investPerson = this.investPerson;
				var investPersonCare = this.InvestPersonCare;
				this.formPanel = new Ext.FormPanel({
							id : 'CareEditForm',
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
							title : '编辑关怀记录',
							collapsible : true,
							hidden : this.careHidden,
							items : [{
								layout : "column",
								columnWidth : 1,
								items : [{
									xtype : "hidden",
									name : 'investPersonCare.id',
									value : investPersonCare == null ? '' : investPersonCare.id
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
											readOnly : this.isLookCare,
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
												readOnly : this.isLookCare,
												value : investPersonCare == null ? '' : investPersonCare.careTitle,
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
													readOnly : this.isLookCare,
													value : investPersonCare == null ? '' : investPersonCare.careContent,
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
													readOnly : this.isLookCare,
													name : 'investPersonCare.careDate',
													allowBlank : false,
													value : investPersonCare == null ? '' : investPersonCare.careDate,
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
												editable : false,
												fieldLabel : "关怀人",
												blankText : "关怀人不能为空，请正确填写!",
												allowBlank : false,
												readOnly : this.isAllReadOnly,
												//value : investPersonCare == null ? '' : investPersonCare.careMan,
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
	//											name : 'careMan'
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
													readOnly : this.isLookCare,
													value : investPersonCare == null ? '' : investPersonCare.careMarks,
													anchor : '100%'
												},{
													xtype : 'hidden',
													name : 'investPersonCare.isEnterprise',
													value : this.isEnterprise
												}]
										}]
								}]
						}]
						});
				//加载表单对应的数据	
				if (this.InvestPersonCare.id != null && this.InvestPersonCare.id != 'undefined') {
					this.formPanel.loadData({
								url : __ctxPath + '/customer/getInvestPersonCare.do?id='+ this.InvestPersonCare.id,
								root : 'data',
								preName : ['investPersonCare'],
								scope : this,
								success : function(resp, options) {
									var obj = Ext.decode(resp.responseText);
								  	//this.formPanel.getCmpByName('investPersonCare.careMan').setValue(obj.data.appUserId);
								   	this.formPanel.getCmpByName('investPersonCare.careMan').setRawValue(obj.data.careManName);
								   	this.formPanel.getCmpByName('investPersonCare.careMan').nextSibling().setValue(obj.data.investPersonCare.careMan);
								}
							});
				}
				
			},//end of the initcomponents
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
				var url=(this.isEnterprise==1)?(__ctxPath + '/customer/saveInvestPersonCare.do?investPersonId='+this.peId) : (__ctxPath + '/customer/saveEnterpriseCareInvestPersonCare.do?enterpriseId='+this.peId);
				this.formPanel.getForm().submit({
					url:url,
					method : 'POST',
					scope : this,
					success : function(response, options){
						Ext.ux.Toast.msg('状态', '保存成功');
						this.close();
					}
				});
			}

		});