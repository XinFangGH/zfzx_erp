/**
 * @author:
 * @class SlCompanyMainView
 * @extends Ext.Panel
 * @description [SlCompanyMain]管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
ReceivablesForm = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		ReceivablesForm.superclass.constructor.call(this, {
			anchor : '95%',
			items : [{
				layout : 'column',
				xtype : 'fieldset',
				title : '填写<应收账款>详细信息',
				collapsible : true,
				autoHeight : true,
				anchor : '95%',
				items : [{
					columnWidth : 1, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 100,
					labelAlign : 'right',
					items : [{
						xtype : 'hidden',
						name : 'csProcreditMortgageReceivables.objectType',
						value : this.objectType
					},{
						xtype : 'hidden',
						name : 'csProcreditMortgageReceivables.receivablesId'
					},{
						xtype : 'hidden',
						name : 'csProcreditMortgageReceivables.enterpriseId'
					},{
						xtype : 'combo',
						anchor : '100%',
						triggerClass : 'x-form-search-trigger',
						fieldLabel : "应收账款企业",
						name : "csProcreditMortgageReceivables.enterpriseName",
						readOnly : this.isReadOnly,
						blankText : "应收账款企业不能为空，请正确填写!",
						allowBlank : false,
						scope : this,
						editable : false,
						onTriggerClick : function() {
							var op = this.ownerCt.ownerCt.ownerCt.ownerCt;
							var EnterpriseNameStockUpdateNew = function(obj) {
								if (obj.enterprisename != 0 && obj.enterprisename != "")
									op.getCmpByName('csProcreditMortgageReceivables.enterpriseName').setValue(obj.enterprisename);
								if (obj.id != 0 && obj.id != "")
									op.getCmpByName('csProcreditMortgageReceivables.enterpriseId').setValue(obj.id);
							}
							selectEnterprise(EnterpriseNameStockUpdateNew);

						},
						resizable : true,
						mode : 'romote',
						lazyInit : false,
						typeAhead : true,
						minChars : 1,
						triggerAction : 'all'
					}]
				},{
					columnWidth : .5,
					layout : 'form',
					labelWidth :100,
					labelAlign : 'right',
					items : [{
						xtype : 'textfield',
						fieldLabel : '应付账款公司',
						name : 'csProcreditMortgageReceivables.accountsPayCompany',
						anchor : '100%'
					}]
				},{
					columnWidth : .45,
					layout : 'form',
					labelWidth :100,
					labelAlign : 'right',
					items : [{
						xtype : 'moneyfield',
						fieldLabel : '应付账款金额',
						fieldClass : 'field-align',
						name : 'csProcreditMortgageReceivables.receivableMoney',
						anchor : '100%'
					}]
				}, {
					columnWidth : .05, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 22,
					items : [{
						fieldLabel : "元",
						labelSeparator : '',
						anchor : "100%"
					}]
				},{
					columnWidth : .5,
					layout : 'form',
					labelWidth :100,
					labelAlign : 'right',
					items : [{
						xtype : 'textfield',
						fieldLabel : '对应合同编号',
						name : 'csProcreditMortgageReceivables.contractNumber',
						anchor : '100%'
					}]
				},{
					columnWidth : .5,
					layout : 'form',
					labelWidth :100,
					labelAlign : 'right',
					items : [{
						xtype : 'datefield',
						fieldLabel : '应收账款到账日',
						format : 'Y-m-d',
						name : 'csProcreditMortgageReceivables.intentDate',
						anchor : '100%'
					}]
				},{
					columnWidth : .5,
					layout : 'form',
					labelWidth :100,
					labelAlign : 'right',
					items : [{
						xtype : 'textfield',
						fieldLabel : '发票号码',
						name : 'csProcreditMortgageReceivables.billNumber',
						anchor : '100%'
					}]
				},{
					columnWidth : .5,
					layout : 'form',
					labelWidth :100,
					labelAlign : 'right',
					items : [{
						xtype : 'datefield',
						fieldLabel : '发票日期',
						format : 'Y-m-d',
						name : 'csProcreditMortgageReceivables.billDate',
						anchor : '100%'
					}]
				}]
			}]
		});
		if(null!=this.id && null!=this.type){
			var url=__ctxPath +'/credit/mortgage/getMortgageByType.do?mortgageid='+ this.id+'&typeid='+this.type+'&objectType='+this.objectType
			if(this.objectType=='pawn'){
				url=__ctxPath +'/creditFlow/pawn/pawnItems/getPawnItemTypePawnItemsList.do?mortgageid='+ this.id+'&typeid='+this.type+'&objectType='+this.objectType
			}
			this.loadData({
				url :url,
				root : 'data',
				preName : ['procreditMortgage','procreditMortgageProduct']
			});
		}
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		
	}
});
