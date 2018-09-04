ExtUD.Ext.TypeSelectInfoMineType2 = Ext.extend(Ext.Panel, {
	layout : "form",
	autoHeight : true,
	bodyStyle : 'padding-right:18px;',
	isAllReadOnly : false,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (_cfg.isAllReadOnly) {
			this.isAllReadOnly = _cfg.isAllReadOnly;
		}

		Ext.applyIf(this, _cfg);
		this.p = {
			come : null  //合同来源
		};
		var getCome = function() {
			var data = [["使用己方合同", "true"], ["使用对方合同", "false"]];
			var proxy = new Ext.data.MemoryProxy(data);
			var _data = Ext.data.Record.create([{
						name : "cname",
						type : "string",
						mapping : 0
					}, {
						name : "chidden",
						type : "string",
						mapping : 1
					}]);
			var reader = new Ext.data.ArrayReader({}, _data);
			var store = new Ext.data.Store({
						proxy : proxy,
						reader : reader,
						autoLoad : true
					});

			var combobox = new Ext.form.ComboBox({
						fieldLabel : "合同来源",
						anchor : "99%",
						width : 100,
						triggerAction : "all",
						store : store,
						displayField : "cname",
						valueField : "chidden",
						mode : "local",
						emptyText : "请选择",
						allowBlank : false,
						editable : false,// 是否允许输入
						blankText : '请选择合同来源'

					});
			return combobox;
		}
		this.p.come = getCome();
		this.initComponents();
		ExtUD.Ext.TypeSelectInfoMineType2.superclass.constructor.call(this, {
			items : [{
				layout : "column",
				defaults : {
					anchor : '100%',
					columnWidth : 1,
					isFormField : true,
					labelWidth : 85
				},
				items : [{
							columnWidth : .3, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 80,
							items : [{
										xtype : "textfield",
										fieldLabel : "我方类型",
										name : "mineTypeKey",
										readOnly : true,
										anchor : "100%"
									}]
						}, {
							columnWidth : .31, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 96,
							items : [{
										xtype : 'textfield',
										fieldLabel : '我方主体',
										name : 'mineName',
										anchor : "98.5%",
										readOnly : true
									}]
						}, {
							columnWidth : .39, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 85,
							hidden : true,
							items : [{
								xtype : "combo",
								triggerClass : 'x-form-search-trigger',
								hiddenName : "degree",
								editable : false,
								fieldLabel : "项目经理",
								blankText : "项目经理不能为空，请正确填写!",
								allowBlank : false,
								anchor : "99%",

								readOnly : this.isAllReadOnly,
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
						}, {
							columnWidth : .39, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 85,
							items : [this.p.come]
						}]
			}]
		});

	},
	initComponents : function() {
	}
})