/**
 * @author
 * @createtime
 * @class PlOrProPackForm
 * @extends Ext.Window
 * @description PlOrProPack表单
 * @company 智维软件
 */
PlPersionDirProKeepForm = Ext.extend(Ext.Window, {
	businessType : 'SmallLoan',
	operationType : 'SmallLoanBusiness',
	proType : null,
	proIdupload : null,
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		if(_cfg.proType != 'underfined'){
			this.proType=_cfg.proType
		}
		if(_cfg.proIdupload != 'underfined'){
			this.proIdupload=_cfg.proIdupload
		}
		// 必须先初始化组件
		this.initUIComponents();
		PlPersionDirProKeepForm.superclass.constructor.call(this, {
					id : 'PlPersionDirProKeepFormWin',
					layout : 'fit',
					items : this.formPanel,
					modal : true,
					autoScroll : true,
					height : 600,
					width : 900,
					maximizable : true,
					title : '详细信息',
					buttonAlign : 'center',
					buttons : [{
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
							}]
				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		// 项目类型
		var proTypeComboBox = new Ext.form.ComboBox({
			id : 'proTypeComboBox',
			fieldLabel : '项目类型',
			store : new Ext.data.ArrayStore({
				autoLoad : true,
				url : __ctxPath
						+ "/creditFlow/financingAgency/typeManger/loadItemPlKeepProtype.do",
				fields : ['itemValue', 'itemName']
			}),
			valueField : 'itemValue',
			displayField : 'itemName',
			mode : "remote",
			triggerAction : 'all',
			emptyText : '选择项目类型',
			allowBlank:false,
			selectOnFocus : true,
			width : 190,
			listeners : {
				afterrender : function(combox) {
					var st = combox.getStore();
					st.on("load", function() {
						combox.setValue(combox.getValue());
						combox.clearInvalid();
					})
		       }
			}
		});
		// 信用等级
		var proLevelIdComboBox = new Ext.form.ComboBox({
			id : 'proLevelIdComboBox',
			fieldLabel : '信用等级',
			store : new Ext.data.ArrayStore({
				autoLoad : true,
				url : __ctxPath
						+ "/creditFlow/financingAgency/typeManger/loadItemPlKeepCreditlevel.do",
				fields : ['itemValue', 'itemName']
			}),
			valueField : 'itemValue',
			displayField : 'itemName',
			mode : "remote",
			triggerAction : 'all',
			allowBlank:false,
			emptyText : '选择信用等级',
			selectOnFocus : true,
			width : 155,
			listeners : {
				afterrender : function(combox) {
					var st = combox.getStore();
					st.on("load", function() {
						combox.setValue(combox.getValue());
						combox.clearInvalid();
					})
		       }
			}
		});
		this.SlProcreditMaterialsView = new SlEnterPriseProcreditMaterialsView(
				{
					projectId : this.record.data.proId,
					businessType : this.businessType,
					isHiddenEdit : true,
					isHidden:true,
					isHidden_materials : true,
					isp2pEdit:true,
					operationType : "SmallLoanBusiness"
				});
		this.P2pShowProcreditMaterialsView = new P2pShowProcreditMaterialsView(
				{
					projectId : this.record.data.proId,
					businessType : this.businessType,
					isHiddenEdit : false,
					isHidden:false,
					isHidden_materials : false,
					operationType : "SmallLoanBusiness"
					
				});			
		this.formPanel = new Ext.form.FormPanel({
			id : 'PlPersionDirProKeepForm',
			autoHeight : true,
			autoWidth : true,
			layout : 'column',
			bodyStyle : 'padding:10px',
			border : false,
			autoScroll : true,
			frame : true,
			labelAlign : 'right',
			defaults : {
				anchor : '96%',
				labelWidth : 60
			},
			items : [{
						name : 'plPersionDirProKeep.keepId',
								xtype : 'hidden',
								value : this.keepId == null ? '' : this.keepId
					}, {
						fieldLabel : '机构id',
						id : 'proGtOrzId',
						name : "plPersionDirProKeep.proGtOrzId",
						xtype : 'hidden'
					},  {
						fieldLabel : '直投缓存项目id',
						xtype : 'hidden',
						id : 'pdirProId',
						name : 'plPersionDirProKeep.pDirProId'
					},  {
						fieldLabel : '债权项目id',
						xtype : 'hidden',
						id : 'porProId',
						name : 'plPersionDirProKeep.pOrProId'
					},  {
						fieldLabel : '项目类别',
						xtype : 'hidden',
						id : 'proType',
						name : 'plPersionDirProKeep.proType'
					}, {
						xtype : 'fieldset',
						autoHeight : true,
						collapsible : true,
						bodyStyle : 'padding: 5px',
						title : '项目基本信息',
						columnWidth : 1, // 该列在整行中所占的百分比
						layout : "column", // 从上往下的布局

						items : [{
									columnWidth : .7, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 90,
									labelAlign : 'right',
									items : [{
												xtype : "textfield",
												fieldLabel : '贷款项目名称',
												id : 'proName',
												allowBlank : false,
												readOnly : true,
												anchor : '100%'
											}]
								}, {
									columnWidth : .3, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 90,
									labelAlign : 'right',
									items : [{
										xtype : "textfield",
										fieldLabel : '贷款项目编号',
										id : 'proNumber',
										allowBlank : false,
										readOnly : true,
										anchor : '100%'
									}]
								}, {
									columnWidth : .3, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 90,
									labelAlign : 'right',
									items : [{
												fieldLabel : '借款人姓名',
												xtype : "textfield",
												id : 'persionName',
												allowBlank : false,
												readOnly : true,
												anchor : '100%'
											}]
								},  {
									columnWidth : .35, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 90,
									labelAlign : 'right',
									items : [proTypeComboBox]
								}, {
									columnWidth : .3, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 90,
									labelAlign : 'right',
									items : [proLevelIdComboBox]
								}, {
									columnWidth : .3, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 90,
									labelAlign : 'right',
									items : [{
												fieldLabel : '用户名',
												xtype : "textfield",
												id : 'userName',
												//allowBlank : false,
												readOnly : true,
												anchor : '100%'
											}]
								},  {
									columnWidth : .35, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 90,
									labelAlign : 'right',
									items : [{
												fieldLabel : '性别',
												xtype : "textfield",
												id : 'sex',
												//allowBlank : false,
												readOnly : true,
												anchor : '100%'
											}]
								}, {
									columnWidth : .3, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 90,
									labelAlign : 'right',
									items : [{
												fieldLabel : '年龄',
												xtype : "textfield",
												id : 'age',
												//allowBlank : false,
												readOnly : true,
												anchor : '100%'
											}]
								}, {
									columnWidth : .3, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 90,
									labelAlign : 'right',
									items : [{
												fieldLabel : '学历',
												xtype : "textfield",
												id : 'education',
												//allowBlank : false,
												readOnly : true,
												anchor : '100%'
											}]
								},  {
									columnWidth : .35, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 90,
									labelAlign : 'right',
									items : [{
												fieldLabel : '婚姻',
												xtype : "textfield",
												id : 'marriage',
												//allowBlank : false,
												readOnly : true,
												anchor : '100%'
											}]
								}, {
									columnWidth : .3, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 90,
									labelAlign : 'right',
									items : [{
												fieldLabel : '月收入',
												xtype : "textfield",
												id : 'monthIncome',
												//allowBlank : false,
												readOnly : true,
												anchor : '100%'
											}]
								}, {
									columnWidth : .3, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 90,
									labelAlign : 'right',
									items : [{
												fieldLabel : '现居住地',
												xtype : "textfield",
												id : 'address',
												//allowBlank : false,
												readOnly : true,
												anchor : '100%'
											}]
								},  {
									columnWidth : .35, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 90,
									labelAlign : 'right',
									items : [{
												fieldLabel : '工作时间',
												xtype : "textfield",
												id : 'workTime',
												//allowBlank : false,
												readOnly : true,
												anchor : '100%'
											}]
								}, {
									columnWidth : .3, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 90,
									labelAlign : 'right',
									items : [{
												fieldLabel : '公司地址',
												xtype : "textfield",
												id : 'workCity',
												//allowBlank : false,
												readOnly : true,
												anchor : '100%'
											}]
								}, {
									columnWidth : .3, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 90,
									labelAlign : 'right',
									items : [{
												fieldLabel : '公司行业',
												xtype : "textfield",
												id : 'companyIndustry',
												//allowBlank : false,
												readOnly : true,
												anchor : '100%'
											}]
								},  {
									columnWidth : .35, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 90,
									labelAlign : 'right',
									items : [{
												fieldLabel : '公司规模',
												xtype : "textfield",
												id : 'companyScale',
												//allowBlank : false,
												readOnly : true,
												anchor : '100%'
											}]
								}, {
									columnWidth : .3, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 90,
									labelAlign : 'right',
									items : [{
												fieldLabel : '职位',
												xtype : "textfield",
												id : 'position',
												//allowBlank : false,
												readOnly : true,
												anchor : '100%'
											}]
								},{
									columnWidth : 1, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 90,
									labelAlign : 'right',
									items : [{
										fieldLabel : '借款人主要财务',
										name : 'plPersionDirProKeep.mainFinance',
										xtype : 'textarea',
										anchor : '100%'
									}]
								},{
									columnWidth : 1, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 90,
									labelAlign : 'right',
									items : [{
										fieldLabel : '借款人主要债务',
										name : 'plPersionDirProKeep.mainDebt',
										xtype : 'textarea',
										anchor : '100%'
									}]
								}, {
									columnWidth : 1, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 90,
									labelAlign : 'right',
									items : [{
												fieldLabel : '项目描述',
												name : 'plPersionDirProKeep.proDes',
												id : 'proDes',
												xtype : 'textarea',
												anchor : '100%'
											}]
								}, {
									columnWidth : 1, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 90,
									labelAlign : 'right',
									items : [{
												fieldLabel : '资金用途',
												name : 'plPersionDirProKeep.proUseWay',
												xtype : 'textarea',
												id : 'proUseWay',
												anchor : '100%'
											}]
								}, {
									columnWidth : 1, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 90,
									labelAlign : 'right',
									items : [{
										fieldLabel : '还款来源',
										name : 'plPersionDirProKeep.proPayMoneyWay',
										xtype : 'textarea',
										id : 'proPayMoneyWay',
										anchor : '100%'
									}]
								}, {

									columnWidth : 1, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 90,
									labelAlign : 'right',
									items : [{
												fieldLabel : '风险控制',
												name : 'plPersionDirProKeep.proRiskCtr',
												id : 'proRiskCtr',
												xtype : 'textarea',
												anchor : '100%'
											}]
								}]
					},{
						xtype : 'fieldset',
						collapsible : true,
						autoHeight : true,
						anchor : "100%",
						bodyStyle : 'padding: 5px',
						title : '  标的Logo',
						columnWidth : 1, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						items : [ {
							xtype : 'button',
							text:'Logo上传',
							fieldLabel : 'Logo上传',
							value:'上传',
							anchor : "100%",
							width:1,
							scope : this,
							handler : this.upload
						}]
					},{
						xtype : 'fieldset',
						collapsible : true,
						autoHeight : true,
						bodyStyle : 'padding: 5px',
						title : '  贷款材料清单',
						columnWidth : 1, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局

						items : [this.SlProcreditMaterialsView]
					}, {
						xtype : 'fieldset',
						collapsible : true,
						autoHeight : true,
						bodyStyle : 'padding: 5px',
						title : '网站贷款材料展示清单',
						columnWidth : 1, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局

						items : [this.P2pShowProcreditMaterialsView]
					}, /*{
						xtype : 'fieldset',
						autoHeight : true,
						collapsible : true,
						bodyStyle : 'padding: 5px',
						title : ' 企业信息',
						columnWidth : 1, // 该列在整行中所占的百分比
						layout : "column", // 从上往下的布局
						anchor : '100%',

						items : [{
							columnWidth : 1, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 90,
							labelAlign : 'right',
							items : [{
										fieldLabel : '营业范围',
										name : 'plPersionDirProKeep.proBusinessScope',
										id : 'proBusinessScope',
										xtype : 'textarea',
										anchor : '100%'
									}]
						}, {
							columnWidth : 1, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 90,
							labelAlign : 'right',
							items : [{
										fieldLabel : '经营情况',
										name : 'plPersionDirProKeep.proBusinessStatus',
										xtype : 'textarea',
										id : 'proBusinessStatus',
										anchor : '100%'
									}]
						}]
					},*/ {
						xtype : 'fieldset',
						autoHeight : true,
						collapsible : true,
						bodyStyle : 'padding: 5px',
						name:"guarantorsMethod",
						title : ' 担保措施',
						columnWidth : 1, // 该列在整行中所占的百分比
						layout : "column", // 从上往下的布局
						anchor : '100%',
						items : [{
							columnWidth : 1, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 90,
							labelAlign : 'right',
							items : [{
								fieldLabel : '担保机构',
								//name : 'plPersionDirProKeep.guarantorsName',
								xtype : 'trigger',
								editable : false,
								triggerClass : 'x-form-search-trigger',
								id:'guarantorsName',
								name : 'plPersionDirProKeep.guarantorsName',
								onTriggerClick : function() {
											var op = this.ownerCt.ownerCt;
											var EnterpriseNameStockUpdateNew = function(obj) {
													if (null != obj.mateid && "" != obj.mateid)
														op.getCmpByName('plPersionDirProKeep.guarantorsName').setValue(obj.mateid);
													if (null != obj.id && "" != obj.id){
														op.getCmpByName('plPersionDirProKeep.guarantorsId').setValue(obj.id);
													}
													if (null != obj.companyIntro && "" != obj.companyIntro){
														op.getCmpByName('plPersionDirProKeep.guarantorsDes').setValue(obj.companyIntro);
													}
													
											}
											selectCsCooperationEnterprise(EnterpriseNameStockUpdateNew,'guarantee');
											
											
										
								},
								anchor : '100%'
							},{
								xtype:'hidden',
								id:'guarantorsId',
								name:'plPersionDirProKeep.guarantorsId'
							
							}]
						}, {
							columnWidth : 1, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 90,
							labelAlign : 'right',
							items : [{
										xtype : 'combo',
										fieldLabel : '保障方式',
										allowBlank : true,
										mode : 'local',
										displayField : 'typeValue',
										editable : false,
						                valueField : 'typeId',
						                triggerAction : 'all',
						                anchor : '100%',
						                id:'guaranteeWay',
										hiddenName : 'plPersionDirProKeep.guaranteeWay',
										store : new Ext.data.SimpleStore({
								        data : [['本金保障',1]],//,['本息保障',2],['全部保障',3]
								        fields:['typeValue','typeId']
							            })
									}]
						}, {
							columnWidth : 1, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 90,
							labelAlign : 'right',
							items : [{
										fieldLabel : '担保机构简介',
										name : 'plPersionDirProKeep.guarantorsDes',
										xtype : 'textarea',
										readOnly : true,
										id:'guarantorsDes',
										anchor : '100%'
									}]
						}, {
							columnWidth : 1, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 90,
							labelAlign : 'right',
							items : [{
										fieldLabel : '担保机构意见',
										name : 'plPersionDirProKeep.guarantorsAdvise',
										id:'guarantorsAdvise',
										xtype : 'textarea',
										anchor : '100%'
									}]
						}/*{
							columnWidth : 1, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 90,
							labelAlign : 'right',
							items : [{
								fieldLabel : '担保机构',
								id : 'proGtOrzName',
								xtype : 'trigger',
								//allowBlank:false,
								triggerClass : 'x-form-search-trigger',
								hiddenName : 'plPersionDirProKeep.proGtOrzId',
								onTriggerClick : function() {
									OrOrzSelector.getView(
											function(id, name, remark) {
												Ext.getCmp('proGtOrzRemark')
														.setValue(remark);
												Ext.getCmp('proGtOrzId')
														.setValue(id);
												Ext.getCmp('proGtOrzName')
														.setValue(name);
											}, true).show();
								},
								anchor : '100%'
							}]
						}, {
							columnWidth : 1, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 90,
							labelAlign : 'right',
							items : [{
										fieldLabel : '担保机构简介',
										id : 'proGtOrzRemark',
										xtype : 'textarea',
										readOnly : true,
										anchor : '100%'
									}]
						}, {
							columnWidth : 1, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 90,
							labelAlign : 'right',
							items : [{
										fieldLabel : '担保机构意见',
										name : 'plPersionDirProKeep.proGtOrjIdea',
										id : 'proGtOrjIdea',
										xtype : 'textarea',
										anchor : '100%'
									}]
						}*//*, {
							xtype : 'fieldset',
							autoHeight : true,
							bodyStyle : 'padding: 5px',
							title : ' 抵质押担保',
							columnWidth : 1, // 该列在整行中所占的百分比
							items : [new DZYMortgageView({
										projectId : 109,
										titleText : '抵质押担保',
										businessType : this.businessType,
										isHiddenAddContractBtn : true,
										isHiddenDelContractBtn : true,
										isHiddenEdiContractBtn : true,
										isHiddenRelieve : true,
										isblHidden : true,
										isRecieveHidden : true,
										isgdHidden : true,
										isHiddenAddBtn : true,
										isHiddenDelBtn : true,
										isHiddenEdiBtn : true,
										isPackShow:false
									})]
						}, {
							xtype : 'fieldset',
							autoHeight : true,
							bodyStyle : 'padding: 5px',
							title : ' 保证担保',
							columnWidth : 1, // 该列在整行中所占的百分比
							items : [new BaozMortgageView({
										projectId : this.record.data.proId,
										titleText : '保证担保',
										businessType : this.businessType,
										isHiddenAddContractBtn : true,
										isHiddenDelContractBtn : true,
										isHiddenEdiContractBtn : true,
										isHiddenRelieve : true,
										isRecieveHidden : true,
										isblHidden : true,
										isgdHidden : true,
										isHiddenAddBtn : true,
										isHiddenDelBtn : true,
										isHiddenEdiBtn : true,
										isPackShow:false
									})]
						}*/]
					}]
		});
		
		// 加载表单对应的数据
		
		// 已维护时加载信息
		if (this.keepId != null && this.keepId != 'undefined') {
			this.formPanel.getForm().load({
				deferredRender : false,
				scope:this,
				url : __ctxPath
						+ '/creditFlow/financingAgency/persion/getPlPersionDirProKeep.do?keepId='
						+ this.keepId,
				waitMsg : '正在载入数据...',
				success : function(form, action) {
					proTypeComboBox
								.setValue(action.result.data.plKeepProtype.typeId);
					proLevelIdComboBox
								.setValue(action.result.data.plKeepCreditlevel.creditLevelId);
					this.fillData(action);
				},
				failure : function(form, action) {
					Ext.ux.Toast.msg('编辑', '载入失败');
				}
			});
		};

		//未维护时加载信息
		if (this.keep) {
			//个人直投标信息加载
			if(this.record.data.pdirProId!=null&&this.record.data.pdirProId!='undefined'){
				Ext.getCmp('pdirProId').setValue(this.record.data.pdirProId);
				this.formPanel.getForm().load({
					deferredRender : false,
					scope:this,
					url : __ctxPath
							+ '/creditFlow/financingAgency/persion/getBpPersionDirPro.do?pdirProId='
							+ this.record.data.pdirProId,
					waitMsg : '正在载入数据...',
					success : function(form, action) {
						Ext.getCmp('proType').setValue(this.proType);
					},
					failure : function(form, action) {
						Ext.ux.Toast.msg('编辑', '载入失败');
					}
				});
				//个人债权标信息加载
			}else if(this.record.data.porProId!=null&&this.record.data.porProId!='undefined'){
				Ext.getCmp('porProId').setValue(this.record.data.porProId);
				this.formPanel.getForm().load({
					deferredRender : false,
					scope:this,
					url : __ctxPath
							+ '/creditFlow/financingAgency/persion/getBpPersionOrPro.do?porProId='
							+ this.record.data.porProId,
					waitMsg : '正在载入数据...',
					success : function(form, action) {
						Ext.getCmp('proType').setValue(this.proType);
					},
					failure : function(form, action) {
						Ext.ux.Toast.msg('编辑', '载入失败');
					}
				});
			}
			
			/*if(this.record.data.porProId!=null&&this.record.data.porProId!='undefined'){
				Ext.getCmp('porProId').setValue(this.record.data.porProId);
			}
			Ext.getCmp('proType').setValue(this.proType);
			Ext.getCmp('proName').setValue(this.record.data.proName);
			Ext.getCmp('proNubmer').setValue(this.record.data.proNumber);
			Ext.getCmp('persionName').setValue(this.record.data.persionName);
			Ext.getCmp('sex').setValue(this.record.data.sex);
			Ext.getCmp('age').setValue(this.record.data.age);
			Ext.getCmp('education').setValue(this.record.data.education);
			Ext.getCmp('marriage').setValue(this.record.data.marriage);
			Ext.getCmp('userName').setValue(this.record.data.userName);
			Ext.getCmp('monthIncome').setValue(this.record.data.monthIncome);
			Ext.getCmp('address').setValue(this.record.data.address);
			Ext.getCmp('companyIndustry').setValue(this.record.data.companyIndustry);
			Ext.getCmp('companyScale').setValue(this.record.data.companyScale);
			Ext.getCmp('position').setValue(this.record.data.position);
			Ext.getCmp('workTime').setValue(this.record.data.workTime);
			Ext.getCmp('workCity').setValue(this.record.data.workCity);*/

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
		var gname=this.getCmpByName('plPersionDirProKeep.guarantorsName').getValue();
		var gway=this.getCmpByName('plPersionDirProKeep.guaranteeWay').getValue();
		if(""!=gname){
		  if(""==gway){
		  		Ext.MessageBox.show({
							title : '操作信息',
							msg : '请选择保障方式！',
							buttons : Ext.MessageBox.OK,
							icon : 'ext-mb-error'
						});
			return;
		  }
		
		}
		var proTypeId = Ext.getCmp('proTypeComboBox').getValue();
		var proLevelId = Ext.getCmp('proLevelIdComboBox').getValue();
		//抵质押担保展示id字符串
		//var llimitsIds=this.findShowIds(Ext.getCmp('dzymortgage'));
		 var llimitsIds="";
		//信用担保展示id字符串
		//var levelIds=this.findShowIds(Ext.getCmp('baozmortgage'));
		 var levelIds="";
		//贷款材料清单展示id字符串
		 materialsIds="";
		//var materialsIds=this.findShowIds(Ext.getCmp('procredMaterialsGp'));
		 var proType=this.proType;
		 var bpPersonId=this.proIdupload;
		$postForm({
			formPanel : this.formPanel,
			scope : this,
			url : __ctxPath
					+ '/creditFlow/financingAgency/persion/savePlPersionDirProKeep.do',
			params : {
				proTypeId : proTypeId,
				llimitsIds:llimitsIds,
				levelIds:levelIds,
				materialsIds:materialsIds,
				proLevelId : proLevelId,
				proType : proType,
				bpPersonId : bpPersonId
			},
			callback : function(fp, action) {
				var gridPanel = this.gp;
				if (gridPanel != null) {
					gridPanel.getStore().reload();
				}
				this.close();
			}
		});
	},// end of save
	//获取gridePanel 中被选中的展示列的id 字符串
  findShowIds:function(gp){
        var store = gp.store;
		var rowCount = store.getCount(); //记录数
		var ids="";
		for(i=0;i<rowCount;i++){
			if(store.getAt(i).data['isPackShow']){
			  ids+=store.getAt(i).data['id']+",";
			}
			if(store.getAt(i).data['isPackShowMaterials']){
			  ids+=store.getAt(i).data['proMaterialsId']+",";
			}
		}
		
		return ids;
  },
  
  	//获取gridePanel 并填选中所选项
  //isMaterials 判断是否是贷款材料清单 
  fillShowIds:function(gp,ids,isMaterials){
        var store = gp.store;
		var rowCount = store.getCount(); //记录数
		var arr=new Array();
		arr=ids.split(",");
		for(i=0;i<arr.length;i++){
			index=store.findBy(function(red,id){
				if(!isMaterials){
				return red.get('id')==arr[i];
				}else{
					return red.get('proMaterialsId')==arr[i];
				}
			})
			if(!isMaterials)
			store.getAt(index).set("isPackShow",true);
			if(isMaterials)//贷款材料清单
			store.getAt(index).set("isPackShowMaterials",true);
		}
		
		
  },
  
  upload : function() {
      new SlSubjectLogoUpload({
    	  tablename:this.tablename,
    	  proIdupload:this.proIdupload
		}).show();
  },
  
  fillData:function(action){
  		if(action.result.data.mainDebt){
	  		this.getCmpByName('plPersionDirProKeep.mainDebt').setValue(action.result.data.mainDebt);
	  	}
	  	if(action.result.data.mainFinance){
	  		this.getCmpByName('plPersionDirProKeep.mainFinance').setValue(action.result.data.mainFinance);
	  	}
  	                if(action.result.data.plKeepGtorz!=null){
						Ext.getCmp('proGtOrzName')
								.setValue(action.result.data.plKeepGtorz.name);
						Ext.getCmp('proGtOrzId')
								.setValue(action.result.data.plKeepGtorz.proGtOrzId);
						Ext.getCmp('proGtOrzRemark')
								.setValue(action.result.data.plKeepGtorz.remark);
                    }
					var dataObj;
					if(action.result.data.bpPersionDirPro!=null){
						dataObj=action.result.data.bpPersionDirPro;
					}
					if(action.result.data.bpPersionOrPro!=null){
						dataObj=action.result.data.bpPersionOrPro;
					}
							
					Ext.getCmp('proName')
							.setValue(dataObj.proName);
					Ext.getCmp('proNumber')
							.setValue(dataObj.proNumber);
					Ext.getCmp('persionName')
							.setValue(dataObj.persionName);
					Ext.getCmp('sex').setValue(dataObj.sex);
					Ext.getCmp('age').setValue(dataObj.age);
					Ext.getCmp('education').setValue(dataObj.education);
					Ext.getCmp('marriage').setValue(dataObj.marriage);
					Ext.getCmp('userName').setValue(dataObj.userName);
					Ext.getCmp('monthIncome').setValue(dataObj.monthIncome);
					Ext.getCmp('address').setValue(dataObj.address);
					Ext.getCmp('companyIndustry').setValue(dataObj.companyIndustry);
					Ext.getCmp('companyScale').setValue(dataObj.companyScale);
					Ext.getCmp('position').setValue(dataObj.position);
					Ext.getCmp('workTime').setValue(dataObj.workTime);
					Ext.getCmp('workCity').setValue(dataObj.workCity);
					Ext.getCmp('guarantorsId').setValue(action.result.data.guarantorsId);
					Ext.getCmp('guarantorsName').setValue(action.result.data.guarantorsName);
					Ext.getCmp('guarantorsDes').setValue(action.result.data.guarantorsDes);
					Ext.getCmp('guarantorsAdvise').setValue(action.result.data.guarantorsAdvise);	
					
					if(action.result.data.proLoanLlimitsShow!=null&&action.result.data.proLoanLlimitsShow!= ''){
						this.fillShowIds(Ext.getCmp('dzymortgage'),action.result.data.proLoanLlimitsShow,false);
					}
					if(action.result.data.proLoanLevelShow!=null&&action.result.data.proLoanLevelShow!= ''){
						this.fillShowIds(Ext.getCmp('baozmortgage'),action.result.data.proLoanLevelShow,false);
					}
					if(action.result.data.proLoanMaterShow!=null&&action.result.data.proLoanMaterShow!= ''){
						this.fillShowIds(Ext.getCmp('procredMaterialsGp'),action.result.data.proLoanMaterShow,true);
					}
  }
});