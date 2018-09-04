/**
 * @author
 * @createtime
 * @class CsCooperationEnterpriseForm
 * @extends Ext.Window
 * @description CsCooperationEnterprise表单
 * @company 智维软件
 */
CsCooperationEnterpriseForm = Ext.extend(Ext.Window, {
	// 构造函数
	gridPanel : null,
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		if(typeof(_cfg.gridPanel)!='undefined'){
			this.gridPanel = _cfg.gridPanel;
		}
		// 必须先初始化组件
		this.initUIComponents();
		CsCooperationEnterpriseForm.superclass.constructor.call(this, {
					id : 'CsCooperationEnterpriseFormWin',
					layout : 'fit',
					items : this.formPanel,
					modal : true,
					height : 600,
					width : 800,
					maximizable : true,
					title : '合作企业档案管理',
					buttonAlign : 'center',
					buttons : [{
								text : '保存',
								iconCls : 'btn-save',
								scope : this,
								handler : this.save
							},/*
								 * { text : '重置', iconCls : 'btn-reset', scope :
								 * this, handler : this.reset },
								 */{
								text : '取消',
								iconCls : 'btn-cancel',
								scope : this,
								handler : this.cancel
							}]
				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		this.formPanel = new Ext.FormPanel({
					layout : 'form',
					autoScroll : true,
					frame : true,
					anchor : '100%',
					labelAlign : 'right',
					defaults : {
						anchor : '96%',
						labelWidth : 80
					},
					// id : 'CsCooperationEnterpriseForm',
					items : [{
								columnWidth : 1,
								layout : 'form',
								labelWidth : 80,
								labelAlign : 'right',
								border : false,
								items : [{
									xtype : 'fieldset',
									title : '机构基本信息',
									bodyStyle : 'padding-left:10px',
									collapsible : true,
									labelAlign : 'right',
									autoHeight:true,
									items : [
										{
											layout : "column",
											border : false,
											scope : this,
											items:[
												{
													name : 'csCooperationEnterprise.id',
													xtype : 'hidden'
												},
												{
													name : 'csCooperationEnterprise.type',
													xtype : 'hidden',
													value : this.type
												},
												{
													name : 'csCooperationEnterprise.isCheckCard',
													xtype : 'hidden'
												},
												{
													name : 'csCooperationEnterprise.isOpenP2P',
													xtype : 'hidden'
												},
												{
													columnWidth : 1,
													layout : 'form',
													labelWidth : 80,
													labelAlign : 'right',
													border : false,
													items : [{
															fieldLabel : '机构名称',
															readOnly : this.readOnly,
															allowBlank : false,
															name : 'csCooperationEnterprise.name',
															anchor : "100%",
															xtype : 'textfield'
													}]
												},
												/*{
													columnWidth : .5,
													layout : 'form',
													labelWidth : 80,
													labelAlign : 'right',
													border : false,
													items : [{
															fieldLabel : '机构类型',
															readOnly : this.readOnly,
															name : 'csCooperationEnterprise.type',
															anchor : "100%",
															xtype : 'textfield'
															
															xtype : "dickeycombo",
															readOnly : this.readOnly,
															nodeKey : 'csCooperationEnterpriseType',
															hiddenName : "csCooperationEnterprise.type",
															fieldLabel : "机构类型",
															allowBlank : false,
															anchor : '100%',
															editable : false,
															listeners : {
																scope : this,
																afterrender : function(combox) {
																	var st = combox.getStore();
																	st.on("load", function() {
																				combox.setValue(combox
																						.getValue());
																				combox.clearInvalid();
																			})
																}
															}
												
										
													}]
												},*/
												{
													columnWidth : .5,
													layout : 'form',
													labelWidth : 80,
													labelAlign : 'right',
													border : false,
													items : [{
															/*fieldLabel : '机构来源',
															readOnly : this.readOnly,
															name : 'csCooperationEnterprise.typeFrom',
															anchor : "100%",
															xtype : 'textfield'*/
															
															xtype : "dickeycombo",
															readOnly : this.readOnly,
															allowBlank : false,
															nodeKey : 'csCooperationEnterpriseTypeFrom',
															hiddenName : "csCooperationEnterprise.typeFrom",
															fieldLabel : "机构来源",
															anchor : '100%',
															editable : false,
															listeners : {
																scope : this,
																afterrender : function(combox) {
																	var st = combox.getStore();
																	st.on("load", function() {
																				combox.setValue(combox
																						.getValue());
																				combox.clearInvalid();
																			})
																}
															}
													}]
												},{
													columnWidth : .5,
													layout : 'form',
													labelWidth : 80,
													labelAlign : 'right',
													border : false,
													items : [{
															xtype : 'combo',
															fieldLabel : '证件类型',
															readOnly:this.readOnly,
															editable : false,
															anchor : '100%',
															allowBlank : false,
															mode : 'local',
															displayField : 'typeValue',
											                valueField : 'typeId',
											                triggerAction : 'all',
															hiddenName : 'csCooperationEnterprise.documentType',
															store : new Ext.data.SimpleStore({
														        data : [['三证合一',1],['非三证合一',2]],
														        fields:['typeValue','typeId']
												            }),
															listeners : {
																scope : this,
																afterrender : function(combox) {
																	var combo = this.getCmpByName('csCooperationEnterprise.organizationNumber'); 
																	if(combox.getValue() == 1){
																		combo.allowBlank=false;
														 				combo.fieldLabel = '社会信用代码';
																		this.findById('licenseNumber').setVisible(false);  
																		this.findById('taxnum').setVisible(false);  
																	}else{
																		combo.allowBlank=false;
														 				combo.fieldLabel = '组织机构代码';
																		this.findById('licenseNumber').setVisible(true);  
																		this.findById('taxnum').setVisible(true);  
																	}
																},
																select:function(combox){
																	var combo = this.getCmpByName('csCooperationEnterprise.organizationNumber'); 
																	if(combox.getValue() == 1){
																		var a = combo.el.parent().parent().first();
																		combo.allowBlank=false;
														 				a.dom.innerHTML ="<font color='red'>*</font>社会信用代码:";
														 				combo.fieldLabel = '社会信用代码';
																		this.findById('licenseNumber').setVisible(false);  
																		this.findById('taxnum').setVisible(false);  
																	}else{
																		var a = combo.el.parent().parent().first();
																		combo.allowBlank=false;
														 				a.dom.innerHTML ="<font color='red'>*</font>组织机构代码";
														 				combo.fieldLabel = '组织机构代码';
																		this.findById('licenseNumber').setVisible(true);  
																		this.findById('taxnum').setVisible(true);  
																	}
																}
															}
														}]
												},{
													columnWidth : .5,
													layout : 'form',
													labelWidth : 80,
													labelAlign : 'right',
													border : false,
													items : [{
															fieldLabel : '组织机构号码',
															readOnly : this.readOnly,
															allowBlank : false,
															name : 'csCooperationEnterprise.organizationNumber',
															anchor : "100%",
															xtype : 'textfield'
													}]
												},
												{
													columnWidth : .5,
													layout : 'form',
													labelWidth : 80,
													labelAlign : 'right',
													border : false,
													id:'licenseNumber',
													items : [{
															fieldLabel : '营业执照号码',
															readOnly : this.readOnly,
															allowBlank : true,
															name : 'csCooperationEnterprise.licenseNumber',
															anchor : "100%",
															xtype : 'textfield'
													}]
												},{
													columnWidth : .5,
													layout : 'form',
													labelWidth : 80,
													labelAlign : 'right',
													border : false,
													id:'taxnum',
													items : [{
															fieldLabel : '税务登记号码',
															readOnly : this.readOnly,
															allowBlank : true,
															name : 'csCooperationEnterprise.taxnum',
															anchor : "100%",
															xtype : 'textfield'
													}]
												},
												{
													columnWidth : .5,
													layout : 'form',
													labelWidth : 80,
													labelAlign : 'right',
													border : false,
													items : [{
														     fieldLabel : '公司电话',
														     xtype : 'textfield',
														     readOnly:this.readOnly,
														     allowBlank : true,
														     anchor : "100%",
														     name : 'csCooperationEnterprise.tellPhone',
														     xtype : 'textfield'
														   }]
												},
												{
													columnWidth : .5,
													layout : 'form',
													labelWidth : 80,
													labelAlign : 'right',
													border : false,
													items : [{
															fieldLabel : '传真',
															readOnly : this.readOnly,
															name : 'csCooperationEnterprise.fax',
															anchor : "100%",
															xtype : 'textfield'
													}]
												},
												{
													columnWidth : .5,
													layout : 'form',
													labelWidth : 80,
													labelAlign : 'right',
													border : false,
													items : [{
															fieldLabel : '注册资本金',
															readOnly : this.readOnly,
															name : 'csCooperationEnterprise.registeredMoney',
															anchor : "100%",
															xtype : 'textfield'
													}]
												},
												{
													columnWidth : .5,
													layout : 'form',
													labelWidth : 80,
													labelAlign : 'right',
													border : false,
													items : [{
															fieldLabel : '公司成立日期',
															readOnly : this.readOnly,
															name : 'csCooperationEnterprise.buildDate',
															anchor : "100%",
															editable : false,
															format : 'Y-m-d',
															xtype : 'datefield'
													}]
												},
												{
													columnWidth : .5,
													layout : 'form',
													labelWidth : 80,
													labelAlign : 'right',
													border : false,
													items : [{
															fieldLabel : '合作公司开始时间',
															readOnly : this.readOnly,
															name : 'csCooperationEnterprise.cooperationDate',
															anchor : "100%",
															editable : false,
															format : 'Y-m-d',
															xtype : 'datefield'
													}]
												},
												{
													columnWidth : .5,
													layout : 'form',
													labelWidth : 80,
													labelAlign : 'right',
													border : false,
													items : [{
															fieldLabel : '公司地址',
															readOnly : this.readOnly,
															name : 'csCooperationEnterprise.companyAddress',
															anchor : "100%",
															xtype : 'textfield'
													}]
												},
												{
													columnWidth : 1,
													layout : 'form',
													labelWidth : 80,
													labelAlign : 'right',
													border : false,
													items : [{
															fieldLabel : '公司网址',
															readOnly : this.readOnly,
															name : 'csCooperationEnterprise.companyURL',
															anchor : "100%",
															xtype : 'textfield'
													}]
												},
												{
													columnWidth : 1,
													layout : 'form',
													labelWidth : 80,
													labelAlign : 'right',
													border : false,
													items : [{
															fieldLabel : '公司简介',
															readOnly : this.readOnly,
															name : 'csCooperationEnterprise.companyIntro',
															anchor : "100%",
															xtype : 'textarea'
													}]
												}
											]
										}
									]
									}]
								},
											
									{
										columnWidth : 1,
										layout : 'form',
										labelWidth : 80,
										labelAlign : 'right',
										border : false,
										items : [{
											xtype : 'fieldset',
											title : '主联系人信息',
											readOnly : this.readOnly,
											bodyStyle : 'padding-left:10px',
											collapsible : true,
											labelAlign : 'right',
											autoHeight:true,
											items : [
												{
													layout : "column",
													border : false,
													scope : this,
													items:[
														{
															columnWidth : .5,
															layout : 'form',
															labelWidth : 80,
															labelAlign : 'right',
															border : false,
															items : [{
																	fieldLabel : '联系人',
																	allowBlank : false,
																	readOnly : this.readOnly,
																	name : 'csCooperationEnterprise.pname',
																	anchor : "100%",
																	xtype : 'textfield'
															}]
														},{
															columnWidth : .5,
															layout : 'form',
															labelWidth : 80,
															labelAlign : 'right',
															border : false,
															items : [{
																		fieldLabel : '性别',
																		readOnly : this.readOnly,
																		allowBlank : false,
																		anchor : "100%",
																		xtype : 'combo',
																		mode : 'local',
																		displayField : 'name',
																		valueField : 'id',
																		editable : false,
																		triggerAction : 'all',
																		store : new Ext.data.SimpleStore({
																			fields : ["name", "id"],
																			data : [["男", "0"],["女", "1"]]
																		}),
																		hiddenName : 'csCooperationEnterprise.psex'
																		
																}]
														},{
															columnWidth : .5,
															layout : 'form',
															labelWidth : 80,
															labelAlign : 'right',
															border : false,
															items : [{
																	fieldLabel : '联系人电话',
																	readOnly : this.readOnly,
																	allowBlank : false,
																	name : 'csCooperationEnterprise.pphone',
																	anchor : "100%",
																	xtype : 'textfield',
																    regex : /^[1][34578][0-9]{9}$/,
														            regexText : '联系人电话格式不正确，应为手机号'	,
															        listeners : {
																     scope:this,
															        'blur' : function(f) {
																     var reg=/^[1][34578][0-9]{9}$/;
																     var flag=reg.test(this.getCmpByName('csCooperationEnterprise.pphone').getValue());
																     if(!flag){
																     Ext.Msg.alert('操作信息','联系人电话格式不正确，应为手机号');
																	 this.getCmpByName('csCooperationEnterprise.pphone').setValue(null);
															     }
														       }
														      }

															}]
														},{
															columnWidth : .5,
															layout : 'form',
															labelWidth : 80,
															labelAlign : 'right',
															border : false,
															items : [{
																	fieldLabel : '称谓',
																	readOnly : this.readOnly,
																	name : 'csCooperationEnterprise.pappellation',
																	anchor : "100%",
																	xtype : 'textfield'
															}]
														},{
															columnWidth : .5,
															layout : 'form',
															labelWidth : 80,
															labelAlign : 'right',
															border : false,
															items : [{
																	fieldLabel : '邮箱',
																	readOnly : this.readOnly,
																	allowBlank : false,
																	name : 'csCooperationEnterprise.pemail',
																	anchor : "100%",
																	xtype : 'textfield'
															}]
														},{
															columnWidth : .5,
															layout : 'form',
															labelWidth : 80,
															labelAlign : 'right',
															border : false,
															items : [{
																	fieldLabel : '身份证号码',
																	readOnly : this.readOnly,
																	allowBlank : false,
																	name : 'csCooperationEnterprise.pcardNumber',
																	anchor : "100%",
																	xtype : 'textfield',
																	listeners:{
																		scope:this,
																		'blur':function(f){
																			if(validateIdCard(f.getValue())==1){
																				Ext.Msg.alert('身份证号码验证','证件号码不正确,请仔细核对')
																				f.setValue("");
																				return;
																			}else if(validateIdCard(f.getValue())==2){
																				Ext.Msg.alert('身份证号码验证','证件号码地区不正确,请仔细核对')
																				f.setValue("");
																				return;
																			}else if(validateIdCard(f.getValue())==3){
																				Ext.Msg.alert('身份证号码验证','证件号码生日日期不正确,请仔细核对')
																				f.setValue("");
																				return;
																			}else{
																				var sex=this.getCmpByName('csCooperationEnterprise.psex');
																				if(f.getValue().split("").reverse()[1]%2==0){
										                            				sex.setValue(313);
										                            				sex.setRawValue("女")
										                            			}else{
										                            				sex.setValue(312);
										                            				sex.setRawValue("男")
										                            			}
																			}
																		}
																	}
															}]
														}/*,{
															columnWidth : .6,
															hidden : false,
															layout : "form",
															labelWidth : 90,
															labelAlign : 'right',
															defaults : {
																anchor : '100%'
															},
															items : [{
																xtype : 'combo',
																anchor : '100%',
																triggerClass : 'x-form-search-trigger',
																fieldLabel : "<font color='red'>*</font>保函名称",
																//name : "backletterName",
																name : "backletterType",
																editable:false,
																allowBlank:true,
																readOnly : this.isEnterpriseNameReadOnly,
																blankText : "保函名称不能为空，请正确填写!",
																scope : this,
																onTriggerClick : function() {
																		var op=this.ownerCt.ownerCt.ownerCt.ownerCt;
																		var EnterpriseNameStockUpdateNew=function(obj){
																			//op.getCmpByName('backletterType').setValue("");
																	
																		}
																		selectCsCooperationPerson(EnterpriseNameStockUpdateNew);
																		selectCsCooperationEnterprise(EnterpriseNameStockUpdateNew);
																},
																resizable : true,
																mode : 'romote',
																lazyInit : false,
																typeAhead : true,
																minChars : 1,
														
																triggerAction : 'all'
															
															},{
																xtype : 'hidden',
																name : 'plGuaranteeBackletterInfoId'
															}
															]
														}*/
						
						
						
						
						
						
						
						
						
													
													]
												}
											
											]
										}]
									},
									{
										columnWidth : 1,
										layout : 'form',
										labelWidth : 80,
										labelAlign : 'right',
										border : false,
										items : [{
											xtype : 'fieldset',
											title : '材料清单',
											bodyStyle : 'padding-left:0px',
											collapsible : true,
											labelAlign : 'right',
											autoHeight:true,
											items : [/*new SlReportView({
												projectId : this.projectId,
												businessType : 'SmallLoan',
												Template:'CsCooperationEnterprise',
												isHidden_report : false
											})*/
											new uploads({
												projectId : this.id,
												isHidden : false,
												tableName :'csCooperationEnterprise',
												typeisfile :'csCooperationEnterprise',
												businessType : 'csCooperationEnterprise',
												uploadsSize : 15
												
											})
											
											]
										}]
									}]
				});
		// 加载表单对应的数据
		if (this.id != null && this.id != 'undefined') {
			var formPanel=this.formPanel;
			var organizationNumber=formPanel.getCmpByName('csCooperationEnterprise.organizationNumber');
			var documentType=formPanel.getCmpByName('csCooperationEnterprise.documentType');
			formPanel.loadData({
				url : __ctxPath+ '/creditFlow/customer/cooperation/getCsCooperationEnterprise.do?id='+ this.id,
				root : 'data',
				preName : 'csCooperationEnterprise',
				success : function(response, options) {
					var respText = response.responseText;
					var alarm = Ext.util.JSON.decode(respText);
					//如果该用户已经开通并实名认证了p2p账户则组织机构代码不允许编辑
					if(alarm.data.isCheckCard){
						organizationNumber.setReadOnly(true);
						if(organizationNumber.getEl().dom.className.indexOf("readOnlyClass")==-1){
							organizationNumber.getEl().dom.className+=" readOnlyClass";
						}
						documentType.setReadOnly(true);
						if(documentType.getEl().dom.className.indexOf("readOnlyClass")==-1){
							documentType.getEl().dom.className+=" readOnlyClass";
						}
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
		$postForm({
			formPanel : this.formPanel,
			scope : this,
			url : __ctxPath
					+ '/creditFlow/customer/cooperation/saveCsCooperationEnterprise.do',
			callback : function(fp, action) {
				var gridPanel = this.gridPanel;
				if (gridPanel != null) {
					gridPanel.getStore().reload();
				}
				this.close();
			}
		});
	}// end of save

});