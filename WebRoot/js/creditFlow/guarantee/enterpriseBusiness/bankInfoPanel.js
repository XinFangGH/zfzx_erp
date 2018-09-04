// 项目信息
bankInfoPanel = Ext.extend(Ext.Panel, {
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
		this.initComponents();
		var leftlabel=110;
		var centerlabel=110;
		var rightlabel=100;
		bankInfoPanel.superclass.constructor.call(this, {
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
					name : 'slSmallloanProject.projectId'
				},{
					columnWidth : .6, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : leftlabel,
					items : [{
						fieldLabel : "贷款银行",
						xtype : "textfield",
						readOnly :true,
						name : "slSmallloanProject.projectName",
						blankText : "项目名称不能为空，请正确填写!",
						allowBlank : false,
						anchor : "100%"// 控制文本框的长度

					}]
				}, {
					columnWidth : .4, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : rightlabel,
					items : [{
						fieldLabel : "银行联系人",
						xtype : "textfield",
						readOnly :true,
						name : "slSmallloanProject.projectNumber",
						allowBlank : false,
						blankText : "项目编号不能为空，请正确填写!",
						anchor : "100%"

					}]
				}, {
					columnWidth : .3, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : leftlabel,
					defaults : {
							anchor : '100%'
						},
					items : [{
						fieldLabel : "银行电话",
						xtype : "textfield",
						readOnly :true,
						name : "slSmallloanProject.projectNumber",
						allowBlank : false,
						blankText : "项目编号不能为空，请正确填写!",
						anchor : "100%"

					}]
				}, {
					columnWidth : .3, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : leftlabel,
					defaults : {
							anchor : '100%'
						},
					items : [{
						fieldLabel : "职务",
						xtype : "textfield",
						readOnly :true,
						name : "slSmallloanProject.projectNumber",
						allowBlank : false,
						blankText : "职务不能为空，请正确填写!",
						anchor : "100%"

					}]
				}, {
					columnWidth : .4, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : rightlabel,
					defaults : {
							anchor : '100%'
						},
					items : [{
						fieldLabel : "手机",
						xtype : "textfield",
						readOnly :true,
						name : "slSmallloanProject.projectNumber",
						allowBlank : false,
						blankText : "手机不能为空，请正确填写!",
						anchor : "100%"

					}]
				}]
		 }]
		});
	},
	initComponents : function() {
	},
	cc : function() {

		//alert('ddd');
	}
});