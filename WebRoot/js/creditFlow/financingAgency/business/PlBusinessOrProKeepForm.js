/**
 * @author
 * @createtime
 * @class PlOrProPackForm
 * @extends Ext.Window
 * @description PlOrProPack表单
 * @company 智维软件
 */
PlBusinessOrProKeepForm = Ext.extend(Ext.Window, {
	businessType : 'SmallLoan',
	operationType : 'SmallLoanBusiness',
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		PlBusinessOrProKeepForm.superclass.constructor.call(this, {
					id : 'PlBusinessOrProKeepFormWin',
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
			width : 190
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
			width : 155
		});
		this.SlProcreditMaterialsView = new SlEnterPriseProcreditMaterialsView(
				{
					ProId : 105,
					businessType : this.businessType,
					isHiddenEdit : true,
					isHidden:true,
					isHidden_materials : true,
					operationType : this.operationType
				});
		this.formPanel = new Ext.form.FormPanel({
			id : 'PlBusinessOrProKeepForm',
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
						name : 'plOrProKeep.keepId',
								xtype : 'hidden',
								value : this.keepId == null ? '' : this.keepId
					}, {
						fieldLabel : '机构id',
						id : 'proGtOrzId',
						name : "plOrProKeep.proGtOrzId",
						xtype : 'hidden'
					},  {
						fieldLabel : '缓存项目id',
						xtype : 'hidden',
						id : 'bOrProId',
						name : 'plOrProKeep.bOrProId'
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
										id : 'proNubmer',
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
												fieldLabel : '贷款企业',
												xtype : "textfield",
												id : 'businessName',
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
									columnWidth : 1, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 90,
									labelAlign : 'right',
									items : [{
												fieldLabel : '项目描述',
												name : 'plOrProKeep.proDes',
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
												name : 'plOrProKeep.proUseWay',
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
										name : 'plOrProKeep.proPayMoneyWay',
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
												name : 'plOrProKeep.proRiskCtr',
												id : 'proRiskCtr',
												xtype : 'textarea',
												anchor : '100%'
											}]
								}]
					},/*, {
						xtype : 'fieldset',
						collapsible : true,
						autoHeight : true,
						bodyStyle : 'padding: 5px',
						title : '  贷款材料清单',
						columnWidth : 1, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局

						items : [this.SlProcreditMaterialsView]
					}, */{
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
										name : 'plOrProKeep.proBusinessScope',
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
										name : 'plOrProKeep.proBusinessStatus',
										xtype : 'textarea',
										id : 'proBusinessStatus',
										anchor : '100%'
									}]
						}]
					}, {
						xtype : 'fieldset',
						autoHeight : true,
						collapsible : true,
						bodyStyle : 'padding: 5px',
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
								id : 'proGtOrzName',
								xtype : 'trigger',
								//allowBlank:false,
								triggerClass : 'x-form-search-trigger',
								hiddenName : 'plOrProKeep.proGtOrzId',
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
										name : 'plOrProKeep.proGtOrjIdea',
										id : 'proGtOrjIdea',
										xtype : 'textarea',
										anchor : '100%'
									}]
						}, {
							xtype : 'fieldset',
							autoHeight : true,
							bodyStyle : 'padding: 5px',
							title : ' 抵质押担保',
							columnWidth : 1, // 该列在整行中所占的百分比
							items : [new DZYMortgageView({
										ProId : this.ProId,
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
										ProId : this.ProId,
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
						}]
					}]
		});
		// 加载表单对应的数据

		if (this.keepId != null && this.keepId != 'undefined') {
			this.formPanel.getForm().load({
				deferredRender : false,
				scope:this,
				url : __ctxPath
						+ '/creditFlow/financingAgency/business/getPlOrProKeep.do?keepId='
						+ this.keepId,
				waitMsg : '正在载入数据...',
				success : function(form, action) {
					proTypeComboBox
							.setValue(action.result.data.plKeepProtype.typeId);
					proLevelIdComboBox
							.setValue(action.result.data.plKeepCreditlevel.creditLevelId);
							if(action.result.data.plKeepGtorz!=null){
					Ext.getCmp('proGtOrzName')
							.setValue(action.result.data.plKeepGtorz.name);
					Ext.getCmp('proGtOrzId')
							.setValue(action.result.data.plKeepGtorz.proGtOrzId);
					Ext.getCmp('proGtOrzRemark')
							.setValue(action.result.data.plKeepGtorz.remark);
							}
					Ext.getCmp('proName')
							.setValue(action.result.data.bpBusinessOrsignPro.proName);
						
					Ext.getCmp('proNubmer')
							.setValue(action.result.data.bpBusinessOrsignPro.proNumber);
					Ext.getCmp('businessName')
							.setValue(action.result.data.bpBusinessOrsignPro.businessName);
							
					if(action.result.data.proLoanLlimitsShow!=null&&action.result.data.proLoanLlimitsShow!= ''){
					this.fillShowIds(Ext.getCmp('dzymortgage'),action.result.data.proLoanLlimitsShow,false);
					}
					if(action.result.data.proLoanLevelShow!=null&&action.result.data.proLoanLevelShow!= ''){
					this.fillShowIds(Ext.getCmp('baozmortgage'),action.result.data.proLoanLevelShow,false);
					}
					if(action.result.data.proLoanMaterShow!=null&&action.result.data.proLoanMaterShow!= ''){
					
					this.fillShowIds(Ext.getCmp('procredMaterialsGp'),action.result.data.proLoanMaterShow,true);
					}
				},
				failure : function(form, action) {
					Ext.ux.Toast.msg('编辑', '载入失败');
				}
			});
		};

		if (this.keep) {
			Ext.getCmp('bOrProId').setValue(this.bOrProId);
			Ext.getCmp('proName').setValue(this.ProName);
			Ext.getCmp('proNubmer').setValue(this.ProNumber);
			Ext.getCmp('businessName').setValue(this.businessName);

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
		var proTypeId = Ext.getCmp('proTypeComboBox').getValue();
		var proLevelId = Ext.getCmp('proLevelIdComboBox').getValue();
		//抵质押担保展示id字符串
		var llimitsIds=this.findShowIds(Ext.getCmp('dzymortgage'));
		//信用担保展示id字符串
		var levelIds=this.findShowIds(Ext.getCmp('baozmortgage'));
		//贷款材料清单展示id字符串
		var materialsIds="";
		//var materialsIds=this.findShowIds(Ext.getCmp('procredMaterialsGp'));
		$postForm({
			formPanel : this.formPanel,
			scope : this,
			url : __ctxPath
					+ '/creditFlow/financingAgency/business/savePlBusinessOrProKeep.do',
			params : {
				proTypeId : proTypeId,
				llimitsIds:llimitsIds,
				levelIds:levelIds,
				materialsIds:materialsIds,
				proLevelId : proLevelId
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
		
		
  }
});