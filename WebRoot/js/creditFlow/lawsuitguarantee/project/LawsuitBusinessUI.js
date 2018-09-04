
// 项目信息
lawsuitBusinessProjectInfoPanel = Ext.extend(Ext.Panel, {
	layout : "form",
	autoHeight : true,
	labelAlign : 'right',
	isAllReadOnly : false,
	isDiligenceReadOnly : false,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (_cfg.isAllReadOnly) {
			this.isAllReadOnly = _cfg.isAllReadOnly;
			this.isDiligenceReadOnly = true;
		}
		if (_cfg.isDiligenceReadOnly) {
			this.isDiligenceReadOnly = _cfg.isDiligenceReadOnly;
		}
		Ext.applyIf(this, _cfg);
		var leftlabel=85;
		var centerlabel=87;
		var rightlabel=91;
		lawsuitBusinessProjectInfoPanel.superclass.constructor.call(this, {
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
					xtype : 'hidden',
					name : 'sgLawsuitguaranteeProject.projectId'
				},
				{
					xtype : 'hidden',
					name : 'businessType'
				}
				,{
					columnWidth : .6, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : leftlabel,
					items : [{
						fieldLabel : "项目名称",
						xtype : "textfield",
						readOnly : this.isAllReadOnly,
						readOnly : this.isDiligenceReadOnly,
						name : "sgLawsuitguaranteeProject.projectName",
						blankText : "项目名称不能为空，请正确填写!",
						allowBlank : false,
						anchor : "100%"// 控制文本框的长度

					}]
				}, {
					columnWidth : .4, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : leftlabel,
					items : [{
						fieldLabel : "项目编号",
						xtype : "textfield",
						name : "sgLawsuitguaranteeProject.projectNumber",
						allowBlank : false,
						readOnly : this.isAllReadOnly,
						readOnly : this.isDiligenceReadOnly,
						blankText : "项目编号不能为空，请正确填写!",
						anchor : "100%"

					}]
				}, {
					columnWidth : .3, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth :leftlabel,
					defaults : {
							anchor : '100%'
						},
					items : [{
						xtype : "textfield",
						anchor : "100%",
						readOnly : this.isAllReadOnly,
						readOnly : this.isDiligenceReadOnly,
						name : "businessTypeKey",
						fieldLabel : "业务类别"
					}]
				}, {
					columnWidth : .3, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : rightlabel,
					items : [{
						fieldLabel : "业务品种",
						xtype : "textfield",
						name:"operationTypeKey",
						readOnly:true,
						anchor : "100%"
					}]
				}, {
					columnWidth : .4, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : leftlabel,
					items : [{
						fieldLabel : "流程类别",
						xtype : "textfield",
						name:"flowTypeKey",
						anchor : '100%',
						readOnly : true
					}]
			},{
					columnWidth : .3, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : leftlabel,
					items : [{
						fieldLabel : "我方主体类型",
						xtype : "textfield",
						name:"mineTypeKey",
						readOnly : this.isAllReadOnly,
						readOnly : this.isDiligenceReadOnly,
						anchor : "100%"
					}]
			
			},{
					columnWidth : .3, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : rightlabel,
					items : [{
						xtype : "textfield",
						name : "sgLawsuitguaranteeProject.mineName",
						fieldLabel : "我方主体",
						readOnly : this.isAllReadOnly,
						readOnly : this.isDiligenceReadOnly,
						anchor : "100%"
					}]
			
			}, 
				//gLGuaranteeloan_creditType 	
				{
					columnWidth : .4, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : leftlabel,
					items : [{xtype:"hidden",name:"sgLawsuitguaranteeProject.appUserId"},{
						fieldLabel : "业务经理",
						xtype : "combo",
						allowBlank : false,
						editable : false,
						triggerClass : 'x-form-search-trigger',
						itemVale : creditkindDicId, // xx代表分类名称
						hiddenName : "appUsers",
						readOnly : this.isAllReadOnly,
					    anchor : "100%",
					    onTriggerClick : function(cc) {
						     var obj = this;
						     var appuerIdObj=obj.previousSibling();
							 new UserDialog({
							 	userIds:appuerIdObj.getValue(),
							 	userName:obj.getValue(),
								single : false,
								title:"业务经理",
								callback : function(uId, uname) {
									obj.setRawValue(uname);
									appuerIdObj.setValue(uId);
								}
							}).show();

						}

					}]
				} ,{
					columnWidth : 1, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : leftlabel,
					items : [{
						fieldLabel : "受理法院",
						xtype : "textfield",
						readOnly : this.isAllReadOnly,
						name : "sgLawsuitguaranteeProject.acceptCourt",
						blankText : "受理法院不能为空，请正确填写!",
						allowBlank : false,
						anchor : "100%"// 控制文本框的长度

					}]
				}]
			}]
		});
	}
});
// 财务表单上
ProjectInfoLawsuitFinancePanel = Ext.extend(Ext.Panel, {
	layout : "form",
	autoHeight : true,
	labelAlign : 'right',
	isAllReadOnly : false,
	isDiligenceReadOnly : false,
	isHiddenPanel : false,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (_cfg.isAllReadOnly) {
			this.isAllReadOnly = _cfg.isAllReadOnly;
			this.isDiligenceReadOnly = true;
		}
		if (_cfg.isDiligenceReadOnly) {
			this.isDiligenceReadOnly = _cfg.isDiligenceReadOnly;
		}
		if (_cfg.isHiddenPanel) {
			this.isHiddenPanel = _cfg.isHiddenPanel;
		}
		Ext.applyIf(this, _cfg);
		this.initComponents();
		var leftlabel=85;
		var centerlabel=100;
		var rightlabel=100;
		ProjectInfoLawsuitFinancePanel.superclass.constructor.call(this, {
			hidden : this.isHiddenPanel,
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
				items : [
				{
					name : 'sgLawsuitguaranteeProject.projectStatus',
					xtype : 'hidden'
				},{
					name : 'sgLawsuitguaranteeProject.bmStatus',
					xtype : 'hidden'
				},
				 {
					columnWidth : .22, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : leftlabel,
					items : [{
						xtype : "textfield",
						labelWidth : rightlabel,
						fieldLabel : "担保金额",
						fieldClass:'field-align',
						name : "projectMoney1",
						readOnly : this.isAllReadOnly,
						allowNegative: false, // 允许负数 
					    style: {imeMode:'disabled'},
						blankText : "担保金额不能为空，请正确填写!",
						allowBlank : false,
						anchor : "100%",// 控制文本框的长度
					    listeners : {
					    	scope:this,
							afterrender : function(obj) {
								    obj.on("keyup")
							},
							change  :function(nf) {
								
								var value= nf.getValue();
								{
										nf.setValue(Ext.util.Format.number(value,'0,000.00'))
										this.getCmpByName("sgLawsuitguaranteeProject.projectMoney").setValue(value);
								}
							}
					    }
					},{
					    xtype : "hidden",
						name : "sgLawsuitguaranteeProject.projectMoney"
					}]
				},{
					columnWidth : .03, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 22,
					items : [{
						fieldLabel : "<span style='margin-left:1px'>元</span> ",
						labelSeparator:'', 
						anchor : "100%"
					}]
				},{
						columnWidth : .25, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : centerlabel,
						items : [{
							fieldLabel : "币种",
						    xtype : "dickeycombo",
						    name:'sgLawsuitguaranteeProject.currencyType',
							hiddenName:'sgLawsuitguaranteeProject.currencyType',
							nodeKey : 'gLGuaranteeloan_currencyType',
							readOnly : this.isAllReadOnly,
							emptyText : "请选择",
							anchor : "100%",
						    listeners : {
						    afterrender:function (combox){
						     	  var st=combox.getStore();
						     	  st.on("load",function()
						     	  {
						     	 	   combox.clearInvalid();
						     	 	   if(combox.getValue()>0)
						     	 	   {
						     	 	          combox.setValue(combox.getValue());
						     	 	      
					                   }
						     	   })
						    }
						}
					}]
				},{
					columnWidth : .22, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : leftlabel,
					items : [{
						xtype : "numberfield",
						name : "sgLawsuitguaranteeProject.premiumRate",
						allowBlank : false,
						fieldLabel : "保费费率",
						decimalPrecision:10,
						fieldClass:'field-align',
						readOnly : this.isAllReadOnly,
						blankText : "保费费率不能为空，请正确填写!",
						anchor : "100%"
					}]
				},{
					columnWidth : .03, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 22,
					items : [{
						fieldLabel : "<span style='margin-left:1px'>%</span> ",
						labelSeparator:'', 
						anchor : "100%"
					}]
				},{
					columnWidth : .25, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : centerlabel,
					items : [{
						xtype : "datefield",
						format : 'Y-m-d',
						name : "sgLawsuitguaranteeProject.startDate",
						allowBlank : false,
						fieldLabel : "担保起始日期",
						decimalPrecision:10,
						fieldClass:'field-align',
						style: {imeMode:'disabled'},
						readOnly : this.isAllReadOnly,
						blankText : "担保起始日期不能为空，请正确填写!",
						anchor : "100%",
						listeners : {
					    	scope:this,
							change  :function(nf,date) {
							 
							}
							
							}
					}]
				}
				]
				}]
		});
	},
	initComponents : function() {},
	cc : function() {

	}
});