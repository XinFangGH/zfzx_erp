OriginalCreditorAccount = Ext.extend(Ext.Panel ,{
	layout : "form",
	autoHeight : true,
	isAllReadOnly : this.isAllReadOnly,
	constructor:function(_cfg){
		if (_cfg == null) {
			_cfg = {};
		}
		if (_cfg.isAllReadOnly) {
			this.isAllReadOnly = _cfg.isAllReadOnly;
		}
		Ext.applyIf(this,_cfg);
		this.initUIComponents();
		OriginalCreditorAccount.superclass.constructor.call(this,{
			items : [{
				layout : "column",
				border : false,
				scope : this,
				defaults : {
					anchor : '100%',
					columnWidth : 1,
					isFormField : true,
					labelWidth : 100,
					labelAlign:'left'
				},
				items : [{
					xtype : 'hidden',
					name : 'plManageMoneyPlanBuyinfo.bankAccountId'
				},{
				 	columnWidth : .33,
				    labelWidth : 80,
				 	layout : 'form',
				 	items :[{
						xtype : 'trigger',
				 		fieldLabel : '开户名称',	
	                 	name : 'plManageMoneyPlanBuyinfo.pmpName',
//						allowBlank:false,
						editable : false,
						readOnly : this.isAllreadOnly,
						triggerClass : 'x-form-search-trigger',
						onTriggerClick : function() {
							var obj=this;
							new selectOriginalAccountList({
								obj : obj
							}).show();
						},
						anchor : '100%'
				 	}]
			  },{
					columnWidth : .33,
					labelWidth : 80,
				    layout : 'form',
				    items :[{
				    	fieldLabel : '开户账号',	
	                 	name : 'plManageMoneyPlanBuyinfo.pmpAccount',
	                  	readOnly:true,
						xtype:'textfield',
						anchor : '93%'
//						allowBlank:false
				    }]
			   },{  
					 columnWidth : .34,
					 labelWidth : 73,
				     layout : 'form',
					 items :[{
						fieldLabel : "银行名称",
						xtype : "textfield",
						readOnly:true,
						name : 'plManageMoneyPlanBuyinfo.pmpBankName',
//						allowBlank:false,
						anchor : '100%'
					 }]
				}]
			}]
		});
	},
	initUIComponents:function(){
	}
});