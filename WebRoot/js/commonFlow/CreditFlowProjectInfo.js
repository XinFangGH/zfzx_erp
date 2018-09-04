ExtUD.Ext.ccbfCreditLoanProjectInfoPanel = Ext.extend(Ext.Panel, {
	layout : "form",
	autoHeight : true,
	labelAlign : 'right',
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.applyIf(this, _cfg);
		var leftlabel = 85;
		ExtUD.Ext.ccbfCreditLoanProjectInfoPanel.superclass.constructor.call(this, {
			items : [{
				layout : "column",
				border : false,
				scope : this,
				defaults : {
					anchor : '100%',
					//columnWidth : 1,
					isFormField : true,
					labelWidth : leftlabel
				},
				items : [{
						xtype : 'hidden',
						name : 'slSmallloanProject.projectId'
					},{
						columnWidth : .4, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 80,
						items : [{
							fieldLabel : "项目名称",
							xtype : "textfield",
							readOnly : true,
							name : "slSmallloanProject.projectName",
							blankText : "项目名称不能为空，请正确填写!",
							anchor : "100%"// 控制文本框的长度
						}]
					},{
						columnWidth : .2, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 80,
						labelAlign : 'right',
						items : [{
							fieldLabel : "项目编号",
							xtype : "textfield",
							name : "slSmallloanProject.projectNumber",
							readOnly : true,
							blankText : "项目编号不能为空，请正确填写!",
							anchor : "100%"
						}]
					},{
						columnWidth:.2,
						layout:'form',
						labelWidth:100,
						hidden:this.product==null?false:!this.product,
						labelAlign : 'right',
						items:[{
							labelWidth : 85,
							xtype : "combo",
							mode : 'local',
							anchor:'100%',
							disabled:this.product==null?false:!this.product,
							allowBlank:false,
							readOnly:this.readOnly,
							triggerAction : 'all',
							hiddenName : "slSmallloanProject.productId",
							displayField:'productName',
							valueField:'id',
							fieldLabel : "产品类型",
							editable : false,
							store:new Ext.data.JsonStore( {
								url : __ctxPath+ "/system/listBpProductParameter.do",
								totalProperty : 'totalCounts',
								autoLoad:true,
								root : 'result',
								fields : [{
									name : 'id'
								},{
									name : 'productName'
								}]
							}),
							listeners:{
								scope:this,
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
						columnWidth:.2,
						layout:'form',
						labelWidth:100,
						hidden:this.product==null?false:!this.product,
						labelAlign : 'right',
						disabled:this.product==null?false:!this.product,
						items:[{
							xtype : "combo",
							triggerClass : 'x-form-search-trigger',
							hiddenName : "slSmallloanProject.appUserName",
							editable : false,
							fieldLabel : "协助调查人员",
							labelWidth : 85,
							blankText : "协助调查人员不能为空，请正确填写!",
							allowBlank : false,
							anchor : "100%",
							readOnly:this.readOnly,
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
									title : "协助调查人员",
									callback : function(uId, uname) {
										obj.setValue(uname);
										obj.setRawValue(uname);
										appuerIdObj.setValue(uId);
									}
								}).show();
							}
						}, {
							xtype : "hidden",
							disable:true,
							name : 'slSmallloanProject.appUserId',
							value : ""
						}]
					}]
			}]
		});
	}
});
ExtUD.Ext.dltcCreditLoanProjectInfoPanel = Ext.extend(Ext.Panel, {
	layout : "form",
	autoHeight : true,
	labelAlign : 'right',
	readOnly:false,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if(typeof(_cfg.readOnly)!="undefined"){
			this.readOnly = _cfg.readOnly;
		}
		Ext.applyIf(this, _cfg);
		var leftlabel = 85;
		ExtUD.Ext.dltcCreditLoanProjectInfoPanel.superclass.constructor.call(this, {
			items : [{
				layout : "column",
				border : false,
				scope : this,
				defaults : {
					anchor : '100%',
					//columnWidth : 1,
					isFormField : true,
					labelWidth : leftlabel
				},
				items : [{
						xtype : 'hidden',
						name : 'slSmallloanProject.projectId'
					},{
						columnWidth : .33, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 80,
						items : [{
							fieldLabel : "项目名称",
							xtype : "textfield",
							readOnly : true,
							name : "slSmallloanProject.projectName",
							blankText : "项目名称不能为空，请正确填写!",
							anchor : "100%"// 控制文本框的长度
						}]
					},{
						columnWidth : .33, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 80,
						labelAlign : 'right',
						items : [{
							fieldLabel : "项目编号",
							xtype : "textfield",
							name : "slSmallloanProject.projectNumber",
							readOnly : true,
							blankText : "项目编号不能为空，请正确填写!",
							anchor : "100%"
						}]
					},{
						columnWidth:.33,
						layout:'form',
						labelWidth : 80,
						hidden:this.product==null?false:!this.product,
						labelAlign : 'right',
						items:[{
							labelWidth : 80,
							xtype : "combo",
							mode : 'local',
							anchor:'100%',
							disabled:this.product==null?false:!this.product,
							allowBlank:false,
							readOnly:this.readOnly,
							triggerAction : 'all',
							hiddenName : "slSmallloanProject.productId",
							displayField:'productName',
							valueField:'id',
							fieldLabel : "产品类型",
							editable : false,
							store:new Ext.data.JsonStore( {
								url : __ctxPath+ "/system/listBpProductParameter.do",
								totalProperty : 'totalCounts',
								autoLoad:true,
								root : 'result',
								fields : [{
									name : 'id'
								},{
									name : 'productName'
								}]
							}),
							listeners:{
								scope:this,
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
						columnWidth:.33,
						layout:'form',
						labelWidth : 80,
						hidden:this.product==null?false:!this.product,
						labelAlign : 'right',
						disabled:this.product==null?false:!this.product,
						items:[{
							xtype : "combo",
							anchor:'100%',
							allowBlank:false,
							readOnly:this.readOnly,
							triggerAction : 'all',
							hiddenName : "slSmallloanProject.branchId",
							displayField:'orgName',
							valueField:'orgId',
							fieldLabel : "分公司",
							editable : false,
							store:new Ext.data.JsonStore( {
								url :  __ctxPath+'/system/getChildListOrganization.do?orgType=3',
//								totalProperty : 'totalCounts',
								autoLoad:true,
//								root : 'result',
								fields : [{
									name : 'orgId'
								},{
									name : 'orgName'
								}]
							}),
							listeners:{
								scope:this,
								afterrender : function(combox) {
									var st = combox.getStore();
									st.on("load", function() {
										combox.setValue(combox.getValue());
										combox.clearInvalid();
									})
								},
								select:function(a,b,c){
									var cmp = this.getOriginalContainer().getCmpByName("slSmallloanProject.departId")
									var val = a.getValue();
									var val2 =cmp.getValue() 
	
									cmp.getStore().on('beforeload', function(gridstore, o) {
										Ext.apply(o.params, {"parentId":val});
									});
									cmp.getStore().reload();
									if(val2){
										cmp.setValue(null);
									}
								},
								loadData:function(comp){
									var st = comp.getStore();
									st.on("load", function() {
										comp.setValue(comp.getValue());
										comp.clearInvalid();
									})
									var cmp = this.getOriginalContainer().getCmpByName("slSmallloanProject.departId")
									var val = comp.getValue();
									cmp.getStore().
									cmp.getStore().reload();
									var val2 =cmp.getValue() 
									if(val2){
										cmp.setValue(val2);
										cmp.fireEvent('afterloadData',cmp);
									}
								}
							}
						}]
					},{
						columnWidth:.33,
						layout:'form',
						labelWidth:80,
						hidden:this.product==null?false:!this.product,
						labelAlign : 'right',
						disabled:this.product==null?false:!this.product,
						items:[{
							xtype : "combo",
							anchor:'100%',
							allowBlank:false,
							readOnly:this.readOnly,
							triggerAction : 'all',
							hiddenName : "slSmallloanProject.departId",
							displayField:'orgName',
							valueField:'orgId',
							fieldLabel : "部门",
							editable : false,
							store:new Ext.data.JsonStore( {
								url :  __ctxPath+'/system/getChildListOrganization.do?orgType=2',
								autoLoad:true,
								baseParams :{},
								fields : [{
									name : 'orgId'
								},{
									name : 'orgName'
								}]
							}),
							listeners:{
								scope:this,
								afterrender : function(combox) {
									var st = combox.getStore();
									st.on("load", function() {
										combox.setValue(combox.getValue());
										combox.clearInvalid();
									})
								},
								loadData:function(comp){
									var cmp = this.getOriginalContainer().getCmpByName("slSmallloanProject.branchId")
									cmp.fireEvent("loadData",cmp);
								},
								afterloadData:function(comp){
									var val = comp.getValue();
									var st = comp.getStore();
									st.load();
								}
							}
						}]
					},{
						columnWidth:.33,
						layout:'form',
						labelWidth : 80,
						hidden:this.product==null?false:!this.product,
						labelAlign : 'right',
						disabled:this.product==null?false:!this.product,
						items:[{
							xtype : "combo",
							triggerClass : 'x-form-search-trigger',
							hiddenName : "slSmallloanProject.teamManagerName",
							editable : false,
							fieldLabel : "团队经理",
							blankText : "团队经理不能为空，请正确填写!",
							allowBlank : true,
							anchor : "100%",
							readOnly:this.readOnly,
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
									title : "团队经理",
									callback : function(uId, uname) {
										obj.setValue(uname);
										obj.setRawValue(uname);
										appuerIdObj.setValue(uId);
									}
								}).show();
							}
						}, {
							xtype : "hidden",
							disable:true,
							name : 'slSmallloanProject.teamManagerId',
							value : ""
						}]
					},{
						columnWidth:.17,
						layout:'form',
						labelWidth : 80,
						hidden:this.product==null?false:!this.product,
						labelAlign : 'right',
						disabled:this.product==null?false:!this.product,
						items:[{
							xtype : "combo",
							triggerClass : 'x-form-search-trigger',
							hiddenName : "slSmallloanProject.appUserName",
							editable : false,
							fieldLabel : "业务员",
							blankText : "业务员不能为空，请正确填写!",
							allowBlank : true,
							anchor : "100%",
							readOnly:this.readOnly,
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
									single : true,
									title : "业务员",
									scope:this,
									callback : function(uId, uname,users) {
										obj.setValue(uname);
										obj.setRawValue(uname);
										appuerIdObj.setValue(uId);
										var userNum = this.getOriginalContainer().getCmpByName("appUserNumber")
										if(users){
											userNum.setValue(users[0].userNumber)
										}
									}
								}).show();
							}
						}, {
							xtype : "hidden",
							disable:true,
							name : 'slSmallloanProject.appUserId',
							value : ""
						}]
					},{
						columnWidth:.16,
						layout:'form',
						labelWidth : 80,
						items:[{
							xtype:'textfield',
							readOnly:true,
							anchor : "100%",
							name:'appUserNumber',
							fieldLabel : "业务员编号"
						}]
					},{
					columnWidth : .33, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 80,
					items : [{
						xtype : "dicIndepCombo",
						labelWidth : 80,
						fieldLabel : "我方类型",
						emptyText : "请选择",
						nodeKey : 'ourmainType',
						allowBlank : this.readOnly,
						anchor : "100%",
						readOnly:this.readOnly,
						editable : false,
						hiddenName : "slSmallloanProject.mineType",
						scope : this,
						listeners : {
							change : function(combox, record, index,flag) {
								var comboxValue = combox.getValue();
								var url = '';
								var store = null;
								var combo = this.getOriginalContainer().getCmpByName("slSmallloanProject.mineId");
								if(!flag){
									combo.clearValue();
								}
								if (comboxValue == "company_ourmain")// 企业
								{
									url = __ctxPath + '/creditFlow/ourmain/listSlCompanyMain.do';
									store = new Ext.data.Store({
										proxy : new Ext.data.HttpProxy({
											url : url
										}),
										reader : new Ext.data.JsonReader({
											root : 'result'
										}, [{
											name : 'itemName',
											mapping : 'corName'
										}, {
											name : 'itemValue',
											mapping : 'companyMainId'
										}])
									})
								} else if (comboxValue == "person_ourmain") // 个人
								{
									url = __ctxPath + '/creditFlow/ourmain/listSlPersonMain.do';
									store = new Ext.data.Store({
										proxy : new Ext.data.HttpProxy({
											url : url
										}),
										reader : new Ext.data.JsonReader({
											root : 'result'
										}, [{
											name : 'itemName',
											mapping : 'name'
										}, {
											name : 'itemValue',
											mapping : 'personMainId'
										}])
									})
								}
								combo.store = store;
								combo.valueField = "itemValue";
								store.load();
								if (combo.view) { // 刷新视图,避免视图值与实际值不相符
									combo.view.setStore(combo.store);
								}
								var obj_n = this.ownerCt.ownerCt.ownerCt.ownerCt.ownerCt;
								ressetProjuect(obj_n);
							},
							afterrender : function(combox) {
								combox.clearInvalid();
							},
							loadData:function(comp){
								var st = comp.getStore();
								st.on("load", function() {
									comp.setValue(comp.getValue());
									comp.clearInvalid();
								})
								comp.fireEvent("change",comp,null,null,true)
							}
						}
					}]
				}, {
					columnWidth : .33, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 80,
					items : [{
						xtype : "combo",
						anchor : "100%",
						fieldLabel : "我方主体",
						readOnly:this.readOnly,
						store : new Ext.data.SimpleStore({
									fields : ['displayText', 'value'],
									data : [['地区', 1]]
								}),
						emptyText : "请选择",
						allowBlank : this.readOnly,
						displayField : 'itemName',
						hiddenName : 'slSmallloanProject.mineId',
						typeAhead : true,
						mode : 'local',
						editable : false,
						selectOnFocus : true,
						triggerAction : 'all',
						blankText : "我方主体不能为空，请正确填写!",
						listeners : {
							afterrender : function(combox) {
								combox.clearInvalid();
							},
							change : function(combox) {
								var obj_n = combox.ownerCt.ownerCt.ownerCt.ownerCt.ownerCt;
								ressetProjuect(obj_n);
							},
							loadData:function(comp){
								var st = comp.getStore();
								st.on("load", function() {
									comp.setValue(comp.getValue());
									comp.clearInvalid();
								})
							}
						}

					}]
				}]
			}]
		});
	}
});
creditProjectInfo = Ext.extend(Ext.Panel, {
	layout : "form",
	autoHeight : true,
	labelAlign : 'right',
	readOnly:false,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if(typeof(_cfg.readOnly)!="undefined"){
			this.readOnly = _cfg.readOnly;
		}
		Ext.applyIf(this, _cfg);
		var leftlabel = 85;
		creditProjectInfo.superclass.constructor.call(this, {
			items : [{
				layout : "column",
				border : false,
				scope : this,
				defaults : {
					anchor : '100%',
					//columnWidth : 1,
					isFormField : true,
					labelWidth : leftlabel
				},
				items : [{
							layout:'form',
							columnWidth:.3,
							items:[{
								fieldLabel : "贷款项目编号",
								xtype : "textfield",
								name : "slSmallloanProject.projectNumber",
								readOnly : true,
								blankText : "项目编号不能为空，请正确填写!",
								anchor : "100%"
							}]
						},{
							layout:'form',
							columnWidth:.4,
							items:[{
								fieldLabel : "贷款项目编号",
								xtype : "textfield",
								name : "slSmallloanProject.projectName",
								readOnly : true,
								blankText : "项目编号不能为空，请正确填写!",
								anchor : "100%"
							}]
						},{
							layout:'form',
							columnWidth:.3,
							items:[{
								fieldLabel : "项目经理",
								xtype : "textfield",
								name : "slSmallloanProject.appUserName",
								readOnly : true,
								blankText : "项目编号不能为空，请正确填写!",
								anchor : "100%"
							}]
						},{
							layout:'form',
							columnWidth:.3,
							items:[{
								fieldLabel : "招标项目编号",
								xtype : "textfield",
								name : "plBidPlan.bidProNumber",
								readOnly : true,
								blankText : "项目编号不能为空，请正确填写!",
								anchor : "100%"
							}]
						},{
							layout:'form',
							columnWidth:.4,
							items:[{
								fieldLabel : "招标项目名称",
								xtype : "textfield",
								name : "plBidPlan.bidProName",
								readOnly : true,
								blankText : "项目编号不能为空，请正确填写!",
								anchor : "100%"
							}]
						},{
							layout:'form',
							columnWidth:.3,
							items:[{
								fieldLabel : "招标金额",
								xtype : "textfield",
								name : "plBidPlan.bidMoney",
								readOnly : true,
								blankText : "项目编号不能为空，请正确填写!",
								anchor : "100%"
							}]
						}]
			}]
		});
	}
});