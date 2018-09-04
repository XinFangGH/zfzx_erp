
// 项目信息
enterpriseBusinessProjectInfoPanel = Ext.extend(Ext.Panel, {
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
		enterpriseBusinessProjectInfoPanel.superclass.constructor.call(this, {
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
					name : 'gLGuaranteeloanProject.projectId'
				},
				{
					xtype : 'hidden',
					name : 'businessType'
				}
				,{
					columnWidth : 1, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : leftlabel,
					items : [{
						fieldLabel : "项目名称",
						xtype : "textfield",
						readOnly : this.isAllReadOnly,
						readOnly : this.isDiligenceReadOnly,
						name : "gLGuaranteeloanProject.projectName",
						blankText : "项目名称不能为空，请正确填写!",
						allowBlank : false,
						anchor : "100%"// 控制文本框的长度

					}]
				}, {
					columnWidth : .295, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : leftlabel,
					items : [{
						fieldLabel : "项目编号",
						xtype : "textfield",
						name : "gLGuaranteeloanProject.projectNumber",
						allowBlank : false,
						readOnly : this.isAllReadOnly,
						readOnly : this.isDiligenceReadOnly,
						blankText : "项目编号不能为空，请正确填写!",
						anchor : "100%"

					}]
				}, {
					columnWidth : .3, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth :centerlabel,
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
					columnWidth : .405, // 该列在整行中所占的百分比
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
					columnWidth : .295, // 该列在整行中所占的百分比
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
					labelWidth : centerlabel,
					items : [{
						fieldLabel : "我方主体类型",
						xtype : "textfield",
						name:"mineTypeKey",
						readOnly : this.isAllReadOnly,
						readOnly : this.isDiligenceReadOnly,
						anchor : "100%"
					}]
			
			},{
					columnWidth : .405, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : rightlabel,
					items : [{
						xtype : "textfield",
						name : "gLGuaranteeloanProject.mineName",
						fieldLabel : "我方主体",
						readOnly : this.isAllReadOnly,
						readOnly : this.isDiligenceReadOnly,
						anchor : "100%"
					}]
			
			},{
					columnWidth : .295, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : leftlabel,
					items : [{
					    fieldLabel : "项目来源",
						xtype : "dickeycombo",
						allowBlank : false,
						nodeKey : "gLGuaranteeloan_projectSource",
						hiddenName:'gLGuaranteeloanProject.projectSource',
						readOnly : this.isAllReadOnly,
						emptyText : "请选择",
						value:null,
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
					columnWidth : .3, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : centerlabel,
					items : [{
						fieldLabel : "贷款用途",
						xtype : "dickeycombo",
						allowBlank : false,
						name:'gLGuaranteeloanProject.purposeType',
						nodeKey : 'gLGuaranteeloan_purposeType',
						hiddenName:'gLGuaranteeloanProject.purposeType',
						readOnly : this.isAllReadOnly,
						emptyText : "请选择",
						value:null,
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
					columnWidth : .405, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : rightlabel,
					items : [{
						fieldLabel : "信贷种类",
						xtype : "dickeycombo",
						allowBlank : false,
						name:'gLGuaranteeloanProject.creditType',
						nodeKey : 'gLGuaranteeloan_creditType',
						hiddenName:'gLGuaranteeloanProject.creditType',
						readOnly : this.isAllReadOnly,
						value:null,
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
				}, 
				//gLGuaranteeloan_creditType 	
				{
					columnWidth : .295, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : leftlabel,
					items : [{xtype:"hidden",name:"gLGuaranteeloanProject.appUserIdOfA"},{
						fieldLabel : "项目经理",
						xtype : "combo",
						allowBlank : false,
						editable : false,
						triggerClass : 'x-form-search-trigger',
						itemVale : creditkindDicId, // xx代表分类名称
						hiddenName : "appUsersOfA",
						readOnly : this.isAllReadOnly,
					    anchor : "100%",
					    onTriggerClick : function(cc) {
						     var obj = this;
						     var appuerIdObj=obj.previousSibling();
							 new UserDialog({
							 	userIds:appuerIdObj.getValue(),
							 	userName:obj.getValue(),
								single : false,
								title:"项目经理",
								callback : function(uId, uname) {
									obj.setRawValue(uname);
									appuerIdObj.setValue(uId);
								}
							}).show();

						}

					}]
				} /**{
					columnWidth : .3, // 该列在整行中所占的百分比
					labelWidth : centerlabel,
					layout : "form", // 从上往下的布局
					items : [{xtype:"hidden",name:"gLGuaranteeloanProject.appUserIdOfB"},{
						xtype : "combo",
						fieldLabel : "业务B角",
						editable : false,
						triggerClass : 'x-form-search-trigger',
						name : "appUsersOfB",
						readOnly : this.isAllReadOnly,
					//	allowBlank : false,
						anchor : "100%",// 控制文本框的长度
					  	onTriggerClick : function(cc) {
						     var obj = this;
						     var appuerIdObj=obj.previousSibling();
							 new UserDialog({
							 	userIds:appuerIdObj.getValue(),
							 	userName:obj.getValue(),
								single : false,
								title:"选择业务B角",
								callback : function(uId, uname) {
									obj.setRawValue(uname);
									appuerIdObj.setValue(uId);
								}
							}).show();

						}
					}]
				}**/]
			}]
		});
	}
});
// 财务表单上
ProjectInfoGuaranteeFinancePanel = Ext.extend(Ext.Panel, {
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
		var leftlabel=100;
		var centerlabel=100;
		var rightlabel=100;
		ProjectInfoGuaranteeFinancePanel.superclass.constructor.call(this, {
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
				},				items : [
				{
					name : 'gLGuaranteeloanProject.projectStatus',
					xtype : 'hidden'
				},{
					name : 'gLGuaranteeloanProject.bmStatus',
					xtype : 'hidden'
				},
				 {
					columnWidth : .3, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : leftlabel,
					items : [{
						xtype : "textfield",
						labelWidth : rightlabel,
						fieldLabel : "贷款金额",
						fieldClass:'field-align',
						name : "projectMoney1",
						readOnly : this.isAllReadOnly,
						allowNegative: false, // 允许负数 
					    style: {imeMode:'disabled'},
						blankText : "贷款金额不能为空，请正确填写!",
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
										this.getCmpByName("gLGuaranteeloanProject.projectMoney").setValue(value);
								}
							}
					    }
					},{
					    xtype : "hidden",
						name : "gLGuaranteeloanProject.projectMoney"
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
						columnWidth : .3, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : centerlabel,
						items : [{
							fieldLabel : "币种",
						    xtype : "dickeycombo",
						    name:'gLGuaranteeloanProject.currencyType',
							hiddenName:'gLGuaranteeloanProject.currencyType',
							nodeKey : 'gLGuaranteeloan_currencyType',
							readOnly : this.isAllReadOnly,
							emptyText : "请选择",
							anchor : "100%",
							value:null,
						    listeners : {
						    afterrender:function (combox){
						     	  var st=combox.getStore();
						     	  st.on("load",function()
						     	  {
						     	 	   combox.clearInvalid();
						     	 	   if(combox.getValue()>0)
						     	 	   {
						     	 	          combox.setValue(st.getAt(0).data.itemValue);
						     	 	      
					                   }
						     	   })
						    }
						}
					}]
				},{
					columnWidth : .33, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : rightlabel,
					items : [{
						xtype : "numberfield",
						name : "gLGuaranteeloanProject.loanRate",
						//allowBlank : false,
						fieldLabel : "贷款利率",
						decimalPrecision:10,
						fieldClass:'field-align',
						style: {imeMode:'disabled'},
						readOnly : this.isAllReadOnly,
						blankText : "贷款利率不能为空，请正确填写!",
						anchor : "100%",
						value :0,
						listeners : {
							scope : this,
							'change' : function(nf){
								if(nf.getValue()==''){
									nf.setValue(0)
								}
							}
						}
					}]
				},{
					columnWidth : .04, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 20,
					items : [{
						fieldLabel : "%",
						labelSeparator:'', 
						anchor : "100%"
					}]
				},{
					columnWidth : .3, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : centerlabel,
					items : [{
						xtype : "datefield",
						format : 'Y-m-d',
						name : "gLGuaranteeloanProject.acceptDate",
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
							    var date1=Ext.util.Format.date(nf.ownerCt.nextSibling().nextSibling().get(0).getValue(), 'Y-m-d');
							    if(date1==""){
								         return false;
								}
							    var date2=Ext.util.Format.date(nf.getValue(), 'Y-m-d');
							    var month1=parseInt(date1.split("-")[0],10)*12+parseInt(date1.split("-")[1],10);//注意分隔符是-
								var month2=parseInt(date2.split("-")[0],10)*12+parseInt(date2.split("-")[1],10);
							    var dueTime=month1-month2;
							    if(dueTime>0) //结束月大于起始月
							    {
							    
							    }else if(dueTime==0){  //结束月等于起始月
							             var days=(DateDiff(date1,date2));
								         if(days>0){
								            dueTime=dueTime+1;
								         }
							    }
							    else{
							         nf.ownerCt.nextSibling().nextSibling().nextSibling().get(0).setValue(null);
							         return false;
							    }
							    nf.ownerCt.nextSibling().nextSibling().nextSibling().get(0).setValue(dueTime);
							}
					    }
					}]
				},{
					columnWidth : .03, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 20,
					items : [{
						fieldLabel : " ",
						labelSeparator:'', 
						anchor : "100%"
					}]
				},{columnWidth : .3, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : rightlabel,
					items : [{
						xtype : "datefield",
						format : 'Y-m-d',
						name : "gLGuaranteeloanProject.intentDate",
						allowBlank : false,
						fieldLabel : "担保截至日期",
						decimalPrecision:10,
						fieldClass:'field-align',
						style: {imeMode:'disabled'},
						readOnly : this.isAllReadOnly,
						blankText : "担保截至日期不能为空，请正确填写!",
						anchor : "100%",
						listeners : {
					    	scope:this,
							change  :function(nf,date) {
								    var date1=Ext.util.Format.date(nf.getValue(), 'Y-m-d');
								    var date2=Ext.util.Format.date(nf.ownerCt.previousSibling().previousSibling().get(0).getValue(), 'Y-m-d');
								    if(date2==""){
								         return false;
								    }
								    var month1=parseInt(date1.split("-")[0],10)*12+parseInt(date1.split("-")[1],10);//注意分隔符是-
								    var month2=parseInt(date2.split("-")[0],10)*12+parseInt(date2.split("-")[1],10);
								    var temp;
								    var dueTime=month1-month2;
								    if(dueTime>0){
								    }
								    else if(dueTime==0){
								         var days=(DateDiff(date1,date2));
								         if(days>0){
								            dueTime=dueTime+1;
								         }
								    }
								    else{
								        nf.ownerCt.nextSibling().get(0).setValue(null);
								        return false;
								    }
								    nf.ownerCt.nextSibling().get(0).setValue(dueTime);
								   
							}
					    }
					}]
				},{
					columnWidth : .33, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : rightlabel,
					items : [{
						xtype : "numberfield",
						name : "gLGuaranteeloanProject.dueTime",
						style: {imeMode:'disabled'},
						fieldClass:'field-align',
						allowBlank : false,
						fieldLabel : "期   限",
						fieldClass:'field-align',
						readOnly : this.isAllReadOnly,
						blankText : "贷款期限不能为空，请正确填写!",
						anchor : "100%"
					}]
				},{
					columnWidth : .04, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 20,
					items : [{
						fieldLabel : "月",
						labelSeparator:'', 
						anchor : "100%"
					}]
				},{
					columnWidth : .3, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : leftlabel,
					items : [{
						xtype : "numberfield",
						name : "gLGuaranteeloanProject.premiumRate",
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
					columnWidth : .3, // 该列在整行中所占的百分比
					labelWidth : centerlabel,
					layout : "form", // 从上往下的布局
					items : [{
					    xtype : "dickeycombo",
						name : "gLGuaranteeloanProject.premiumcollectType",
						hiddenName:'gLGuaranteeloanProject.premiumcollectType',
					    nodeKey : 'gLGuaranteeloan_premiumcollectType',
						allowBlank : false,
						value:null,
						fieldLabel : "保费收取方式",
						readOnly : this.isAllReadOnly,
						blankText : "保费收取方式不能为空，请正确填写!",
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
						    }}
					}]
				   },{
					columnWidth : .33, // 该列在整行中所占的百分比
					labelWidth : rightlabel,
					layout : "form", // 从上往下的布局
					items : [{
						xtype : "numberfield",
						name : "gLGuaranteeloanProject.deditRate",
						fieldLabel : "违约金比例",
						allowBlank : false,
						decimalPrecision:10,
						fieldClass:'field-align',
						style: {imeMode:'disabled'},
						readOnly : this.isAllReadOnly,
						anchor : "100%"
					}]
				   },{
					columnWidth : .04, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 45,
					items : [{
						fieldLabel : "%/月",
						labelSeparator:'', 
						anchor : "100%"
					}]
				},{
					columnWidth : .3, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : leftlabel,
					items : [{
						xtype : "numberfield",
						name : "gLGuaranteeloanProject.customerEarnestmoneyScale",
						fieldLabel : "保证金费率",
						allowBlank : false,
						decimalPrecision:10,
						fieldClass:'field-align',
						style: {imeMode:'disabled'},
						readOnly : this.isAllReadOnly,
						anchor : "100%"
					}]
				},{
					columnWidth : .03, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 20,
					items : [{
						fieldLabel : "% ",
						labelSeparator:'', 
						anchor : "100%"
					}]
				},{
					columnWidth : .3, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : centerlabel,
					items : [{
						    fieldLabel : "保证金收取方式",
					       	xtype : "dickeycombo",
							hiddenName:'gLGuaranteeloanProject.earnestmoneyType',
							nodeKey : 'gLGuaranteeloan_earnestmoneyType',
							lazyInit : false,
							allowBlank : false,
							readOnly : this.isAllReadOnly,
							emptyText : "请选择",
							anchor : "100%",
							value:null,
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
					columnWidth : .33, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : centerlabel,
					items : [{
						    fieldLabel : "还贷方式",
					       	xtype : "dickeycombo",
							hiddenName:'gLGuaranteeloanProject.repayType',
							nodeKey : 'gLGuaranteeloan_repayType',
							lazyInit : false,
							allowBlank : false,
							readOnly : this.isAllReadOnly,
							emptyText : "请选择",
							anchor : "100%",
							value:null,
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
				}
				]
				}]
		});
	},
				/*items : [
				{
					name : 'gLGuaranteeloanProject.projectStatus',
					xtype : 'hidden'
				},{
					name : 'gLGuaranteeloanProject.bmStatus',
					xtype : 'hidden'
				},
				 {
					columnWidth : .3, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : leftlabel,
					items : [{
						xtype : "textfield",
						labelWidth : rightlabel,
						fieldLabel : "贷款金额",
						fieldClass:'field-align',
						name : "projectMoney1",
						readOnly : this.isAllReadOnly,
						allowNegative: false, // 允许负数 
					    style: {imeMode:'disabled'},
						blankText : "贷款金额不能为空，请正确填写!",
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
										this.getCmpByName("gLGuaranteeloanProject.projectMoney").setValue(value);
								}
							}
					    }
					},{
					    xtype : "hidden",
						name : "gLGuaranteeloanProject.projectMoney"
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
						columnWidth : .3, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : centerlabel,
						items : [{
							fieldLabel : "币种",
						    xtype : "dickeycombo",
						    name:'gLGuaranteeloanProject.currencyType',
							hiddenName:'gLGuaranteeloanProject.currencyType',
							nodeKey : 'gLGuaranteeloan_currencyType',
							readOnly : this.isAllReadOnly,
							emptyText : "请选择",
							anchor : "100%",
							value : null,
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
					columnWidth : .33, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : rightlabel,
					items : [{
						xtype : "numberfield",
						name : "gLGuaranteeloanProject.loanRate",
						//allowBlank : false,
						fieldLabel : "贷款利率",
						decimalPrecision:10,
						fieldClass:'field-align',
						style: {imeMode:'disabled'},
						readOnly : this.isAllReadOnly,
						blankText : "贷款利率不能为空，请正确填写!",
						anchor : "100%"
					}]
				},{
					columnWidth : .04, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 20,
					items : [{
						fieldLabel : "%",
						labelSeparator:'', 
						anchor : "100%"
					}]
				},{
					columnWidth : .3, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : centerlabel,
					items : [{
						xtype : "datefield",
						format : 'Y-m-d',
						name : "gLGuaranteeloanProject.acceptDate",
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
							    var date1=Ext.util.Format.date(nf.ownerCt.nextSibling().nextSibling().get(0).getValue(), 'Y-m-d');
							    if(date1==""){
								         return false;
								}
							    var date2=Ext.util.Format.date(nf.getValue(), 'Y-m-d');
							    var month1=parseInt(date1.split("-")[0],10)*12+parseInt(date1.split("-")[1],10);//注意分隔符是-
								var month2=parseInt(date2.split("-")[0],10)*12+parseInt(date2.split("-")[1],10);
							    var dueTime=month1-month2;
							    if(dueTime>0) //结束月大于起始月
							    {
							    
							    }else if(dueTime==0){  //结束月等于起始月
							             var days=(DateDiff(date1,date2));
								         if(days>0){
								            dueTime=dueTime+1;
								         }
							    }
							    else{
							         nf.ownerCt.nextSibling().nextSibling().nextSibling().get(0).setValue(null);
							         return false;
							    }
							    nf.ownerCt.nextSibling().nextSibling().nextSibling().get(0).setValue(dueTime);
							}
					    }
					}]
				},{
					columnWidth : .03, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 20,
					items : [{
						fieldLabel : " ",
						labelSeparator:'', 
						anchor : "100%"
					}]
				},{
					columnWidth : .3, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : rightlabel,
					items : [{
						xtype : "datefield",
						format : 'Y-m-d',
						name : "gLGuaranteeloanProject.intentDate",
						allowBlank : false,
						fieldLabel : "担保截至日期",
						decimalPrecision:10,
						fieldClass:'field-align',
						style: {imeMode:'disabled'},
						readOnly : this.isAllReadOnly,
						blankText : "担保截至日期不能为空，请正确填写!",
						anchor : "100%",
						listeners : {
					    	scope:this,
							change  :function(nf,date) {
								    var date1=Ext.util.Format.date(nf.getValue(), 'Y-m-d');
								    var date2=Ext.util.Format.date(nf.ownerCt.previousSibling().previousSibling().get(0).getValue(), 'Y-m-d');
								    if(date2==""){
								         return false;
								    }
								    var month1=parseInt(date1.split("-")[0],10)*12+parseInt(date1.split("-")[1],10);//注意分隔符是-
								    var month2=parseInt(date2.split("-")[0],10)*12+parseInt(date2.split("-")[1],10);
								    var temp;
								    var dueTime=month1-month2;
								    if(dueTime>0){
								    }
								    else if(dueTime==0){
								         var days=(DateDiff(date1,date2));
								         if(days>0){
								            dueTime=dueTime+1;
								         }
								    }
								    else{
								        nf.ownerCt.nextSibling().get(0).setValue(null);
								        return false;
								    }
								    nf.ownerCt.nextSibling().get(0).setValue(dueTime);
								   
							}
					    }
					}]
				},{
					columnWidth : .33, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : rightlabel,
					items : [{
						xtype : "numberfield",
						name : "gLGuaranteeloanProject.dueTime",
						style: {imeMode:'disabled'},
						fieldClass:'field-align',
						allowBlank : false,
						fieldLabel : "期   限",
						fieldClass:'field-align',
						readOnly : this.isAllReadOnly,
						blankText : "贷款期限不能为空，请正确填写!",
						anchor : "100%"
					}]
				},{
					columnWidth : .04, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 20,
					items : [{
						fieldLabel : "月",
						labelSeparator:'', 
						anchor : "100%"
					}]
				},{
					columnWidth : .3, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : leftlabel,
					items : [{
						xtype : "numberfield",
						name : "gLGuaranteeloanProject.premiumRate",
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
					labelWidth : centerlabel,
					layout : "form", // 从上往下的布局
					items : [{
						fieldLabel : "保费展期费率",
					    xtype : "numberfield",
						allowBlank : false,
						name:'gLGuaranteeloanProject.standoverRate',
						fieldClass:'field-align',
						style: {imeMode:'disabled'},
						readOnly : this.isAllReadOnly,
						blankText : "保费展期费率不能为空，请正确填写!",
						anchor : "100%"
						}]
				   },{
					columnWidth : .05, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 45,
					items : [{
						fieldLabel : "‰/月",
						labelSeparator:'', 
						anchor : "100%"
					}]
				},{
					columnWidth : .31, // 该列在整行中所占的百分比
					labelWidth : rightlabel,
					layout : "form", // 从上往下的布局
					items : [{
						fieldLabel : "保费逾期费率",
					    xtype : "numberfield",
						allowBlank : false,
						name:'gLGuaranteeloanProject.overdueRate',
						fieldClass:'field-align',
						style: {imeMode:'disabled'},
						readOnly : this.isAllReadOnly,
						blankText : "保费逾期费率不能为空，请正确填写!",
						anchor : "100%"
						}]
				   },{
					columnWidth : .05, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 45,
					items : [{
						fieldLabel : "‰/月",
						labelSeparator:'', 
						anchor : "100%"
					}]
				},{
					columnWidth : .3, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : leftlabel,
					items : [{
					    xtype : "dickeycombo",
						name : "gLGuaranteeloanProject.premiumcollectType",
						hiddenName:'gLGuaranteeloanProject.premiumcollectType',
					    nodeKey : 'gLGuaranteeloan_premiumcollectType',
						allowBlank : false,
						fieldLabel : "保费收取方式",
						readOnly : this.isAllReadOnly,
						blankText : "保费收取方式不能为空，请正确填写!",
						value : null,
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
						    }}
					}]
				},{
					columnWidth : .03, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 20,
					items : [{
						fieldLabel : " ",
						labelSeparator:'', 
						anchor : "100%"
					}]
				},{
					columnWidth : .25, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : centerlabel,
					items : [{
						xtype : "numberfield",
						name : "gLGuaranteeloanProject.deditRate",
						fieldLabel : "违约金比例",
						allowBlank : false,
						decimalPrecision:10,
						fieldClass:'field-align',
						style: {imeMode:'disabled'},
						readOnly : this.isAllReadOnly,
						anchor : "100%"
					}]
				},{
					columnWidth : .05, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 45,
					items : [{
						fieldLabel : "‰/日",
						labelSeparator:'', 
						anchor : "100%"
					}]
				},{
					columnWidth : .33, // 该列在整行中所占的百分比
					labelWidth : rightlabel,
					layout : "form", // 从上往下的布局
					items : [{
						fieldLabel : "保证金金额",
					    xtype : "textfield",
						name : "earnestmoney1",
						allowBlank : false,
						fieldClass:'field-align',
						style: {imeMode:'disabled'},
						readOnly : this.isAllReadOnly,
						blankText : "保证金金额不能为空，请正确填写!",
						anchor : "100%",
					    listeners : {
					    	scope:this,
							afterrender : function(obj) {
								    obj.on("keyup")
							},
							change  :function(nf) {
								
								var value= nf.getValue();
								{
										nf.setValue(Ext.util.Format.number(value,'0,000.00'))
										this.getCmpByName("gLGuaranteeloanProject.earnestmoney").setValue(value);
								}
							}
					    }
						},{xtype:"hidden",name:"gLGuaranteeloanProject.earnestmoney"}]
				},{
					columnWidth : .04, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 20,
					items : [{
						fieldLabel : "元",
						labelSeparator:'', 
						anchor : "100%"
					}]
				},{
					columnWidth : .3, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : leftlabel,
					items : [{
						    fieldLabel : "保证金收取方式",
					       	xtype : "dickeycombo",
							hiddenName:'gLGuaranteeloanProject.earnestmoneyType',
							nodeKey : 'gLGuaranteeloan_earnestmoneyType',
							lazyInit : false,
							allowBlank : false,
							readOnly : this.isAllReadOnly,
							emptyText : "请选择",
							anchor : "100%",
							value:null,
						    listeners : {
						    afterrender:function (combox){
						     	  var st=combox.getStore();
						     	  var isReadOnly=combox.readOnly;
						     	  st.on("load",function()
						     	  {
						     	 	   combox.clearInvalid();
						     	 	   if(combox.getValue()>0)
						     	 	   {
						     	 	          combox.setValue(combox.getValue());
						     	 	          if(combox.getValue()!=""){
						     	 	               
						     	 	          	       Ext.Ajax.request({
													   url : __ctxPath + '/system/getDictionary.do',
														params : {
															"dicId" : combox.getValue()
														},
														method : 'post',
														success : function(response, options) {
															  
															     var obj = Ext.decode(response.responseText);  
										                         var typeKey=obj.data.dicKey;
										                         if(typeKey=="earnestmoneyType_part"){ //部分收取保证金 
										                         	    
										                         	    var pobj=combox.ownerCt;
										                         	    if(!isReadOnly){
										                         	           var bankEarnestmoneyScaleObj=pobj.nextSibling().nextSibling().get(0);//银行垫付
										                         	           bankEarnestmoneyScaleObj.setReadOnly(false);
										                         	           bankEarnestmoneyScaleObj.removeClass("readOnlyClass");
										                         	    }
										                         }
														}})
                    						     	 	          	   
						     	 	          }
						     	 	      
					                   }
						     	   })
						    },
						    select:function(combox){
						             
						    	    var v=combox.getValue();
						    	    if(v=="")
						    	    return false;
						    	    
						    	    Ext.Ajax.request({
								    url : __ctxPath + '/system/getDictionary.do',
									params : {
										"dicId" : v
									},
									method : 'post',
									success : function(response, options) {
										   var obj = Ext.decode(response.responseText);  
										   var typeKey=obj.data.dicKey;
										   var pobj=combox.ownerCt;
										   var bankEarnestmoneyScaleObj=pobj.nextSibling().nextSibling().get(0);//银行垫付
										   var customerEarnestmoneyScaleObj=pobj.nextSibling().nextSibling().nextSibling().nextSibling().get(0);//客户收取
										   
										   if(typeKey=="earnestmoneyType_all"){  //全额收取保证金
										           
										   	       bankEarnestmoneyScaleObj.setReadOnly(true);
										   	       bankEarnestmoneyScaleObj.addClass("readOnlyClass");
										   	       bankEarnestmoneyScaleObj.setValue(0);
										   	       customerEarnestmoneyScaleObj.setValue(100);
										   	       customerEarnestmoneyScaleObj.setReadOnly(true);
										   	
										   }else if(typeKey=="earnestmoneyType_part"){ //部分收取保证金 
										           
										            bankEarnestmoneyScaleObj.setReadOnly(false);
										   	        customerEarnestmoneyScaleObj.setReadOnly(true);
										   	        bankEarnestmoneyScaleObj.removeClass("readOnlyClass");
										   }
										   else if(typeKey=="earnestmoneyType_all_rp"){   //全额代垫保证金
										    
										   	        bankEarnestmoneyScaleObj.setReadOnly(true);
										   	        bankEarnestmoneyScaleObj.addClass("readOnlyClass");
										   	        customerEarnestmoneyScaleObj.setReadOnly(true);
										   	        bankEarnestmoneyScaleObj.setValue(100);
										   	        customerEarnestmoneyScaleObj.setValue(0);
										   
										   }
									}
								});
						    }
						   }
						}]
				},{
					columnWidth : .03, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 20,
					items : [{
						fieldLabel : " ",
						labelSeparator:'', 
						anchor : "100%"
					}]
				}, {
					columnWidth : .27, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 160,
					items : [{
						fieldLabel : "向银行垫付的保证金比例",
						xtype : "numberfield",
						fieldClass:'field-align',
						name : "gLGuaranteeloanProject.bankEarnestmoneyScale",
						allowBlank : false,
						style: {imeMode:'disabled'},
						readOnly : true,
						blankText : "向银行垫付的保证金比例不能为空，请正确填写!",
						anchor : "100%",
						listeners : {
						    	  change:function(cc){
						    	  	    var operObj=cc.ownerCt.nextSibling().nextSibling().get(0);
						    	  	    if(""!=cc.getValue()){
						    	  	    	     operObj.setValue(100-cc.getValue());    
						    	  	    }
						    	  	   
						    	  }
						}
					}]
				},{
					columnWidth : .03, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 20,
					items : [{
						fieldLabel : "%",
						labelSeparator:'', 
						anchor : "100%"
					}]
				}
				,{
					columnWidth : .33, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 160,
					items : [{
						fieldLabel : "向客户收取的保证金比例",
						xtype : "numberfield",
						fieldClass:'field-align',
						name : "gLGuaranteeloanProject.customerEarnestmoneyScale",
						allowBlank : false,
						style: {imeMode:'disabled'},
						readOnly : true,
						blankText : "向客户收取的保证金比例不能为空，请正确填写!",
						anchor : "100%",
						listeners : {
						    	  change:function(cc){
						    	  	    var operObj=cc.ownerCt.previousSibling().previousSibling().get(0);
						    	  	    if(operObj.getValue()==""){
						    	  	    	  if(""!=cc.getValue()){
						    	  	    	     operObj.setValue(100-cc.getValue());    
						    	  	    	  }
						    	  	    }
						    	  	   
						    	  }
						}
					}]
			},{
					columnWidth : .04, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 20,
					items : [{
						fieldLabel : "%",
						labelSeparator:'', 
						anchor : "100%"
					}]
				}
				]
				}]
		});
	},*/
	initComponents : function() {},
	cc : function() {

	}
});