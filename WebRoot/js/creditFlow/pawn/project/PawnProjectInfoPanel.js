PawnProjectInfoPanel = Ext.extend(Ext.Panel, {
	layout : "form",
	autoHeight : true,
	labelAlign : 'right',
	isAllReadOnly : false,
	//isDiligenceReadOnly : true,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (_cfg.isAllReadOnly) {
			this.isAllReadOnly = _cfg.isAllReadOnly;
		}
		if (typeof(_cfg.isDiligenceReadOnly)!="undefined") {
			this.isDiligenceReadOnly = _cfg.isDiligenceReadOnly;
		}
		Ext.applyIf(this, _cfg);
		this.initComponents();
		var leftlabel = 85;
		var centerlabel = 100;
		var rightlabel = 85;
		PawnProjectInfoPanel.superclass.constructor.call(this, {
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
					columnWidth : 0.6, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 80,
					items : [{
								fieldLabel : "项目名称",
								xtype : "textfield",
								readOnly : true,
								name : "plPawnProject.projectName",
								blankText : "项目名称不能为空，请正确填写!",
								// allowBlank : false,
								anchor : "100%"// 控制文本框的长度

							}]
				}, {
					columnWidth : .4, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 80,
					labelAlign : 'right',
					items : [{
								fieldLabel : "项目编号",
								xtype : "textfield",
								name : "plPawnProject.projectNumber",
								// allowBlank : false,
								readOnly : true,
								blankText : "项目编号不能为空，请正确填写!",
								anchor : "100%"

							}]
				},{
					columnWidth : .3,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					items : [{
						xtype : "textfield",
						fieldLabel : "业务类别",
						anchor : "100%",
						readOnly : true,
						name : "businessTypeKey"
					}]
				},{
					columnWidth : .3,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					items : [{
						xtype : "textfield",
						fieldLabel : "业务品种",
						anchor : "100%",
						readOnly : true,
						name : "operationTypeKey"
					}]
				},{
					columnWidth : .4,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					items : [{
						xtype : "textfield",
						fieldLabel : "流程类别",
						name : "flowTypeKey",
						readOnly : true,
						anchor : "100%"
					}]
				},{
					columnWidth : .3,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					items : [{
						fieldLabel : "典当类型",
						xtype : "dickeycombo",
						hiddenName : 'plPawnProject.pawnType',
						displayField : 'itemName',
						readOnly : this.isAllReadOnly,
						itemName : '典当类型',
						nodeKey : 'pawnType',
						emptyText : "请选择",
						editable :false,
						anchor : "100%",
						allowBlank :false,
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
					columnWidth : .3,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					items : [{
						fieldLabel : "产品名称",
						xtype : "dickeycombo",
						hiddenName : 'plPawnProject.productName',
						displayField : 'itemName',
						readOnly : this.isAllReadOnly,
						itemName : '产品名称',
						nodeKey : 'productName',
						emptyText : "请选择",
						editable :true,
						anchor : "100%",
						allowBlank :false,
						listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
									combox.setValue(combox.getValue());
									
								})
								combox.clearInvalid();
							}

						}
					}]
				},{
					columnWidth : .4, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 80,
					items : [{
						xtype : "combo",
						triggerClass : 'x-form-search-trigger',
						hiddenName : "degree",
						editable : false,
						fieldLabel : "项目经理",
						blankText : "项目经理不能为空，请正确填写!",
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
								userIds : userIds,
								userName : obj.getValue(),
								single : false,
								title : "选择项目经理",
								callback : function(uId, uname) {
									obj.setValue(uId);
									obj.setRawValue(uname);
									appuerIdObj.setValue(uId);
								}
							}).show();
						}
					}, {
						xtype : "hidden",
						value : ""
					}]
				},{
					columnWidth : .6,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					items : [{
						xtype : "textfield",
						fieldLabel : "典当用途",
						anchor : "100%",
						readOnly : this.isAllReadOnly,
						name : "plPawnProject.pawnApplication"
					}]
				},{
					columnWidth : .4,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					hidden :this.isHiddenPhnumber==false?false:true,
					items : [{
						xtype : "textfield",
						fieldLabel : "票号",
						anchor : "100%",
						readOnly : this.isPhnumberOnly,
						allowBlank :this.isHiddenPhnumber==false?false:true,
						name : "plPawnProject.phnumber"
					},{
						xtype : 'hidden',
						name : 'plPawnProject.projectId'
					}]
				}]

								
			}]
		});
	},
	initComponents : function() {
	},
	cc : function() {

		// alert('ddd');
	}
});