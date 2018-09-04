//EnchashMentOperateForm.js
EnchashMentOperateForm = Ext.extend(Ext.Window, {
	isLook : false,
	//isReadOnly : false,
	// 构造函数
	constructor : function(_cfg) {
		
		if (_cfg == null) {
			_cfg = {};
		};
		/*if(typeof(_cfg.isReadOnly) != "undefined")
		{
			this.isReadOnly=_cfg.isReadOnly;
		};*/
		
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		EnchashMentOperateForm.superclass.constructor.call(this, {
					layout : 'fit',
					autoScroll:true,
					items : this.formPanel,
					modal : true,
					height : 500,
					width : 1000,
					maximizable : true,
					title : '取现办理',
					buttonAlign : 'center',
					buttons : [{
								text : '办理',
								iconCls : 'btn-save',
								hidden : this.isLook,
								scope : this,
								handler : this.save
							},{
								text : '取消',
								iconCls : 'btn-cancel',
								hidden : this.isLook,
								scope : this,
								handler : this.cancel
							}]
				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		
		this.title="线下投资客户基本信息";
		if(eval(this.customerType)==eval(0)){
			this.persoormPanel = new BpCustPersonInfoPanel({
				isReadOnly:true
			})
			this.EnchashmentbankForm=new OnlineEnchashmentBankForm({
				isReadOnly:true,
				isAllReadOnly:true,
				investPersonId:this.investPersonId
			})
			this.title="线上投资客户基本信息";
		}else{
			this.persoormPanel = new PersonalInfoPanel({
				isReadOnly:true
			})
			this.EnchashmentbankForm=new EnchashmentBankForm({
				isReadOnly:true,
				isAllReadOnly:true,
				investPersonId:this.investPersonId
			})
		}
		this.EnchashmentApplyForm=new EnchashmentApplyForm({
			isReadOnly:true,
			isAllReadOnly:true,
			hiddenRead:false,
			investPersonId:this.investPersonId
		})
		this.formPanel = new Ext.form.FormPanel({
			layout : 'form',
			bodyStyle : 'padding:10px',
			autoScroll : true,
			frame : true,
			labelAlign : 'right',
			defaults : {
					anchor : '96%',
					columnWidth : 1,
					labelWidth : 60
				},
			items : [{
						xtype : 'fieldset',
						title : this.title,
						collapsible : true,
						autoHeight : true,
						bodyStyle : 'padding-left: 0px',
						items : [this.persoormPanel]
					},{
						xtype : 'fieldset',
						collapsible : true,
						autoHeight : true,
						title : '取现银行卡信息',
						items : [this.EnchashmentbankForm]
					}, {
						xtype : 'fieldset',
						collapsible : true,
						autoHeight : true,
						title : '取现信息',
						items : [this.EnchashmentApplyForm]
					}]
		});
		
		// 加载表单对应的数据
		if (this.investPersonId != null && this.investPersonId != 'undefined') {
			this.formPanel.loadData({
				url : __ctxPath+ '/creditFlow/creditAssignment/bank/getInfoObAccountDealInfo.do?customerType='+this.customerType,
					method : 'POST',
					scope : this,
					params : {
						investId : this.investPersonId,
						obAccountDealInfoId:this.obAccountDealInfoId
				},
				root:'data',
				preName : ['enterpriseBank', 'blance','csInvestmentperson','obSystemAccount','obAccountDealInfo','webBankcard'],
				success : function(response, request) {
							var alarm_fields = Ext.util.JSON.decode(response.responseText);
							if (null != alarm_fields.data.obSystemAccount.availableInvestMoney) {
								this.getCmpByName('availableInvestMoney1').setValue(alarm_fields.data.obSystemAccount.availableInvestMoney);
							}if (null != alarm_fields.data.obAccountDealInfo.payMoney) {
								this.getCmpByName('payMoney1').setValue(alarm_fields.data.obAccountDealInfo.payMoney);
							}
				  		},
				failure : function(response) {
						Ext.ux.Toast.msg('状态', '操作失败，请重试');
					}
			});
		}

	},

	/**
	 * 取消
	 * 
	 * @param {}
	 *  window
	 */
	cancel : function() {
		this.close();
	},
	/**
	 * 保存记录
	 */
	save : function() {
		var flashTargat =this.flashTargat;
		var formpanelRet=this.formPanel;
		var dealInfo=this.obAccountDealInfoId
		var window=this;
		formpanelRet.getForm().submit({
					clientValidation: false,
					url : __ctxPath+ "/pay/WithdrawsERPPay.do",
					method : 'post',
					//waitMsg : '数据正在提交，请稍后...',
					scope: this,
					success : function(fp, action) {
						var obj=Ext.util.JSON.decode(action.response.responseText);
						Ext.ux.Toast.msg('状态', obj.msg);
						flashTargat.getStore().reload();
						window.close();
					},
					params : {
								dealInfoId : dealInfo,
								customerType:this.customerType
							},
					failure : function(fp, action) {
						Ext.MessageBox.show({
							title : '操作信息',
							msg : '信息保存出错，请联系管理员！',
							buttons : Ext.MessageBox.OK,
							icon : 'ext-mb-error'
						});
					}
				});
		
	}// end of save

});