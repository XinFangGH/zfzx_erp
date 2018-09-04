CreditLoanHistoryFormPanel = Ext.extend(Ext.Window,{
	constructor:function(_cfg){
		_cfg = _cfg||{};
		Ext.applyIf(this,_cfg);
		this.initUIComponents();
		CreditLoanHistoryFormPanel.superclass.constructor.call(this, {
			title:'信贷历史记录',
			layout:'fit',
			autoHeight:true,
			width : 650,
			items:this.formPanel,
			buttonAlign : 'center',
			buttons : [{
						text : '保存',
						iconCls : 'btn-save',
						scope : this,
						disabled : this.isReadOnly,
						handler : this.save
					}, /*{
						text : '重置',
						iconCls : 'btn-reset',
						scope : this,
						disabled : this.isReadOnly,
						handler : this.reset
					},*/ {
						text : '取消',
						iconCls : 'btn-cancel',
						scope : this,
						handler : this.cancel
					}]
		});
	},
	initUIComponents:function(){
		this.formPanel = new Ext.FormPanel({
			url : __ctxPath+'/creditFlow/customer/person/addPersonCreditLoanHistory.do',
			bodyStyle:'padding:10px',
			labelAlign:'right',
			buttonAlign:'center',
			border:false,
			autoHeight:true,
			autoWidth:true,
			frame:true,
			layout:'column',
			items:[{
				columnWidth:1,
				layout:'form',
				labelWidth:90,
				defaults:{anchor:'98%'},
				items:[{
					xtype:'hidden',
					name:'personCreditLoanHistory.id'
				},{
					xtype:'hidden',
					name:'personCreditLoanHistory.personId',
					readOnly: this.isReadOnly,
					value:this.personId
				},{
					xtype:'textfield',
					name:'personCreditLoanHistory.bankName',
					allowBlank:false,
					readOnly: this.isReadOnly,
					anchor:'98.5%',
					fieldLabel:"借款结构"//'借款银行'
				}]
			},{
					columnWidth:.5,
					layout:'form',
					labelWidth:90,
					defaults:{anchor:'98%'},
					items:[{
						xtype:'numberfield',
						fieldLabel:'借款金额',
						allowBlank:false,
						readOnly: this.isReadOnly,
						name:'personCreditLoanHistory.loanMoney'
					},{
						xtype:'numberfield',
						fieldLabel:'月供金额',
						allowBlank:false,
						readOnly: this.isReadOnly,
						name:'personCreditLoanHistory.monthPayMoney'
					},{
						xtype:'datefield',
						fieldLabel:'开始时间',
						readOnly: this.isReadOnly,
						format : 'Y-m-d',
						name:'personCreditLoanHistory.loanStartDate'
					},{
						xtype:'checkbox',
						boxLabel : "是否有逾期",
						disabled: this.isReadOnly,
						allowBlank : true,
						inputValue:"true",
						name : 'personCreditLoanHistory.isHaveOverDue'
					}]
				},{
					columnWidth:.5,
					layout:'form',
					labelWidth:90,
					defaults:{anchor:'98%'},
					items:[{
						xtype:'numberfield',
						fieldLabel:'借款期限',
						readOnly: this.isReadOnly,
						name:'personCreditLoanHistory.loanPeriod'
					},{
						xtype:'numberfield',
						fieldLabel:'借款余额',
						allowBlank:false,
						readOnly: this.isReadOnly,
						name:'personCreditLoanHistory.notMoney'
					},{
						xtype:'datefield',
						fieldLabel:'结束日期',
						format : 'Y-m-d',
						readOnly: this.isReadOnly,
						name:'personCreditLoanHistory.loanEndDate'
					},{
						xtype:'textfield',
						name:'personCreditLoanHistory.creditorName',
						allowBlank:false,
						readOnly: this.isReadOnly,
						fieldLabel:"负债归属人"
					},{
						xtype : "dickeycombo",
						nodeKey : 'xdlsdkzt',
						readOnly: this.isReadOnly,
						allowBlank:false,
						fieldLabel : '贷款状态',
						hiddenName : 'personCreditLoanHistory.loanState',
						emptyText : '请选择贷款状态',
						editable : false,
						listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
									if (combox.getValue() == 0
											|| combox.getValue() == 1
											|| combox.getValue() == ""
											|| combox.getValue() == null) {
										combox.setValue("");
									} else {
										combox.setValue(combox
												.getValue());
									}
									combox.clearInvalid();
								})
							}
						}
					}]
				}]
		});
		if(typeof(this.id)!="undefined"){
			this.formPanel.loadData({
				url : __ctxPath+'/creditFlow/customer/person/seePersonCreditLoanHistory.do',
				root:'data',
				scope:this,
				params:{
					id:this.id
				},
				success : function(resp, options) {
				}
			});
		}
	},
	save:function(){
		var gridPanel=this.gridPanel
		var win = this;
		if(this.formPanel.getForm().isValid()){
			this.formPanel.getForm().submit({
				method : 'POST',
				clientValidation : true,
				waitTitle : '连接',
				waitMsg : '消息发送中...',
				success : function(form ,action) {
						Ext.ux.Toast.msg('状态', '保存成功!');
						if(gridPanel)gridPanel.getStore().reload();
						win.destroy();
				},
				failure : function(form, action) {
					if(action.response.status==0){
						Ext.ux.Toast.msg('状态','连接失败，请保证服务已开启');
					}else if(action.response.status==-1){
						Ext.ux.Toast.msg('状态','连接超时，请重试!');
					}else{
						Ext.ux.Toast.msg('状态','添加失败!');		
					}
				}
			});
		}
	},
	cancel:function(){
		this.close();
	}
})