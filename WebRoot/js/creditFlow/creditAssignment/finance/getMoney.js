/** 申请取现流程--申请取现--银行账户信息 */
/*getMoney = Ext.extend(Ext.Window, {
	isLook : false,
	isRead : false,
	isflag : false,
	// 构造函数
	investPersonPanel : null,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		};
		if (typeof(_cfg.isReadOnly) != "undefined") {
			this.readOnly = _cfg.isReadOnly;
		};
		if (null != _cfg.personData) {
			this.isflag = true;
		};
		if (typeof(_cfg.isLook) != "undefined") {
			this.isLook = _cfg.isLook;
		}
		if (typeof(_cfg.url) != "undefined") {
			this.url = _cfg.url;
		}
		if (typeof(_cfg.desRad) != "undefined") {
			this.desRad = _cfg.desRad;
		} else {
			this.desRad = false
		}
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		getMoney.superclass.constructor.call(this, {
			layout : 'fit',
			autoScroll:true,
			items : this.formPanel,
			modal : true,
			height : 800,
			width : 1000,
			maximizable : true,
			title : '取现申请',
			buttonAlign : 'center',
			buttons : [ {
				text : '提交申请',
				iconCls : 'btn-save',
				scope : this,
				handler : this.save
			}, {
				text : '重置',
				iconCls : 'btn-reset',
				scope : this,
				handler : this.reset
			}, {
				text : '取消',
				iconCls : 'btn-cancel',
				scope : this,
				handler : this.cancel
			}]
			});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		this.persoormPanel =new PersonalInfoPanel({
		
		})
		this.EnchashmentApplyForm=new EnchashmentApplyForm({
		})
		this.formPanel = new Ext.form.FormPanel({
			autoHeight : true,
			autoWidth : true,
			layout : 'form',
			bodyStyle : 'padding:10px',
			border : false,
			autoScroll : true,
			frame : true,
			labelAlign : 'right',
			defaults : {
					anchor : '100%',
					labelWidth : 60
				},
			items : [{
						xtype : 'fieldset',
						title : '投资人基本信息',
						collapsible : true,
						autoHeight : true,
						bodyStyle : 'padding-left: 0px',
						items : [this.persoormPanel]
					},{
						xtype : 'fieldset',
						title : '取现申请记录',
						collapsible : true,
						autoHeight : true,
						bodyStyle : 'padding-left: 0px',
						items : [this.EnchashmentApplyForm]
					}]
		});
		
	if (this.investPersonId != null && this.investPersonId != 'undefined') {
			this.formPanel.loadData({
				url : __ctxPath+ '/creditFlow/creditAssignment/customer/getMoneyInfoCsInvestmentperson.do',
				params : {
					investPersonId : this.investPersonId
				},
				root : 'data',
				preName : ['enterpriseBank', 'blance','csInvestmentperson','record'],
				success : function(response, options) {
					var respText = response.responseText;
					var alarm_fields = Ext.util.JSON.decode(respText);
					if (null != alarm_fields.data.blance) {
						this.getCmpByName('blance').setValue(alarm_fields.data.blance);
					} else {
						this.getCmpByName('blance').setValue(0);
					}
					
				}
			});
		}
	},// end of the initcomponents
	save : function(){
		$postForm( {
			formPanel : this.formPanel,
			scope : this,
			msg : (this.id != null && this.id != 'undefined')?'保存成功':'添加成功',
			url : __ctxPath + '/creditFlow/creditAssignment/bank/saveObAccountDealInfo.do',
			callback : function(fp, action) {
				if(this.gridPanel!=null||typeof(this.gridPanel)!="undefined"){
					this.gridPanel.getStore().reload();
				}
				
				this.close();
			}
		});
	}
});*/


//CsProspectivePersonForm
getMoney = Ext.extend(Ext.Window, {
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
		getMoney.superclass.constructor.call(this, {
					//id : 'CsProspectivePersonForm'+this.pageOperationType,
					layout : 'fit',
					autoScroll:true,
					items : this.formPanel,
					modal : true,
					height : 500,
					width : 1000,
					maximizable : true,
					title : '申请取现',
					buttonAlign : 'center',
					buttons : [{
								text : '保存',
								iconCls : 'btn-save',
								hidden : this.isLook,
								scope : this,
								handler : this.save
							}, {
								text : '重置',
								iconCls : 'btn-reset',
								hidden : this.isLook,
								scope : this,
								handler : this.reset
							}, {
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
		
		this.persoormPanel = new PersonalInfoPanel({
			isReadOnly:true
		})
		this.EnchashmentApplyForm=new EnchashmentApplyForm({
			isReadOnly:true,
			investPersonId:this.investPersonId,
			rechargeLevel:this.rechargeLevel
		})
		this.EnchashmentBankForm = new EnchashmentBankForm({
			isReadOnly:true,
			investPersonId:this.investPersonId,
			rechargeLevel:this.rechargeLevel,
			gridPanel:this.formPanel
		});
		
		this.formPanel = new Ext.form.FormPanel({
			layout : 'form',
			bodyStyle : 'padding:10px',
			autoScroll : true,
			frame : true,
			id:'getMoneyPanel',
			labelAlign : 'right',
			defaults : {
					anchor : '96%',
					columnWidth : 1,
					labelWidth : 60
				},
			items : [{
						xtype : 'fieldset',
						title : '投资人基本信息',
						collapsible : true,
						autoHeight : true,
						bodyStyle : 'padding-left: 0px',
						items : [this.persoormPanel]
					},{
						xtype : 'fieldset',
						title : '贷款账户信息',
						collapsible : true,
						autoHeight : true,
						bodyStyle : 'padding-left: 0px',
						items : [this.EnchashmentBankForm]
					},{
						xtype : 'fieldset',
						title : '取现申请记录',
						collapsible : true,
						autoHeight : true,
						bodyStyle : 'padding-left: 0px',
						items : [this.EnchashmentApplyForm]
					}]
		});
		
		// 加载表单对应的数据
		if (this.investPersonId != null && this.investPersonId != 'undefined') {
			this.formPanel.loadData({
				url : __ctxPath+ '/creditFlow/creditAssignment/customer/getMoneyInfoCsInvestmentperson.do',
				params : {
					investPersonId : this.investPersonId
				},
				root : 'data',
				preName : ['enterpriseBank', 'blance','csInvestmentperson','obSystemAccount'],
				success : function(response, options) {
					var respText = response.responseText;
					var alarm_fields = Ext.util.JSON.decode(respText);
					
					if (null != alarm_fields.data.obSystemAccount.availableInvestMoney) {
						this.getCmpByName('availableInvestMoney1').setValue(alarm_fields.data.obSystemAccount.availableInvestMoney);
					}
					
				}
			});
			
		}

	},// end of the initcomponents

	/**
	 * 重置
	 * 
	 * @param {}
	 *            formPanel
	 */
	reset : function() {
		this.formPanel.getForm().reset();
		//this.getCmpByName('obObligationInvestInfo.investPersonName').setValue(this.investPersonName);
	},
	/**
	 * 取消
	 * 
	 * @param {}
	 *            window
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
		Ext.Msg.confirm('信息确认', '是否确认申请取现', function(btn) {
			if (btn == 'yes') {
				formpanelRet.getForm().submit({
					clientValidation: false,
					url :__ctxPath + '/creditFlow/creditAssignment/bank/saveEnchashmentApplyObAccountDealInfo.do',
					method : 'post',
					waitMsg : '数据正在提交，请稍后...',
					scope: this,
					success : function(fp, action) {
						formpanelRet.getOriginalContainer().close()
						var obj=Ext.util.JSON.decode(action.response.responseText)
						var contentPanel = App.getContentPanel();
						if(eval(obj.taskId)==eval(0)){
							Ext.ux.Toast.msg('操作信息',obj.msg);
							return;
						}else if(obj.taskId==eval(1)){
							Ext.ux.Toast.msg('操作信息','您不是取现流程中第一个节点的处理人!');
							return;
						}else{
							var formView = contentPanel.getItem('ProcessNextForm' + obj.taskId);
							if (formView == null) {
								formView = new ProcessNextForm({
										taskId : obj.taskId,
										activityName : obj.activityName,
										projectName : obj.projectName,
										agentTask : true
								});
								contentPanel.add(formView);
							}
							contentPanel.activate(formView);
						}
						if(flashTargat!=null){
							flashTargat.getStore().reload();
						}
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
			}
		})
		
	}// end of save

});