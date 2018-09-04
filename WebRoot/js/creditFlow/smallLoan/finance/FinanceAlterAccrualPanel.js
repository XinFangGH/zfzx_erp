
/**
 * 获取浏览器类型 by shendexuan
 * 
 * @return {}
 */

FinanceAlterAccrualPanel = Ext.extend(Ext.Panel, {
	layout : "form",
	autoHeight : true,
	labelAlign : 'right',
	idDefinition:'alterAccrual',
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (_cfg.idDefinition) {
			this.idDefinition = _cfg.idDefinition;
		}
		Ext.applyIf(this, _cfg);
		this.initComponents();
		
		this.idDefinition=this.projectId+this.idDefinition;
		var surplusnotMoney=this.surplusnotMoney;
		var slAlterAccrualRecorddata = this.slAlterAccrualRecorddata;
		var leftlabel = 80;
		var centerlabel = 115;
		var rightlabel = 115;
		
			FinanceAlterAccrualPanel.superclass.constructor.call(this, {
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
					name : 'slAlterAccrualRecord.slAlteraccrualRecordId',
					xtype : 'hidden'
				},{
					columnWidth : .95, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : leftlabel,
					items : [{
						xtype : "textarea",
						name : "slAlterAccrualRecord.reason",
						allowBlank : false,
						readOnly : this.isAllReadOnly,
						fieldLabel : '申请原因',
						value : this.record != null
								? this.record.data.reason
								: "",
						anchor : '100%'
					}]
				}, {
					columnWidth : .05, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 20,
					items : [{
								fieldLabel : "",
								labelSeparator : '',
								anchor : "100%"
							}]
				},{
					columnWidth : .3, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : leftlabel,
					items : [{
						xtype : "numberfield",
						name : "slAlterAccrualRecord.accrual",
						value : slAlterAccrualRecorddata==null?null:slAlterAccrualRecorddata.accrual,
						fieldClass : 'field-align',
						allowBlank : false,
						readOnly : this.isAllReadOnly,
						anchor : "100%",
						fieldLabel : "贷款利率"
	
					}]
				}, {
					columnWidth : .05, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 40,
					items : [{
						fieldLabel : "%/月",
						labelSeparator : '',
						anchor : "100%"
					}]
				},{
					columnWidth : .3,
					layout : 'form',
					labelWidth :100,
					labelAlign : 'right',
					items : [{
						xtype : 'datefield',
						fieldLabel : '变更开始日期',
						readOnly : this.isAllReadOnly,
						format : 'Y-m-d',
						name : 'slAlterAccrualRecord.alelrtDate',
						anchor : '100%'
					}]
				}
				]
			}]
		});
	},
	initComponents : function() {
	},
	cc : function() {

		// alert('ddd');
	}
});







  Ext.reg('FinanceAlterAccrualPanel', FinanceAlterAccrualPanel);

