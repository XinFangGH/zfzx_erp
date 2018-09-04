//prospectiveFollowInfoForm.js
prospectiveFollowInfoForm = Ext.extend(Ext.Window, {
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
		prospectiveFollowInfoForm.superclass.constructor.call(this, {
					layout : 'fit',
					autoScroll:true,
					items : this.formPanel,
					modal : true,
					height : 500,
					width : 1000,
					maximizable : true,
					title : this.titleChange,
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
		
		this.customerbaseInfo=new customerbaseInfo({
			isRead:this.isReadOnly
		})
		this.PersonRelationView =new PersonRelationView({
			perId:this.perId,
			isReadOnly:this.isReadOnly,
			isHiddenAddBtn:this.isReadOnly,
			isHiddenDelBtn:this.isReadOnly
		})
		
		this.CsProspectivePersonFollowView =new CsProspectivePersonFollowView({
			perId:this.perId,
			isHiddeneditBtn:this.isReadOnly,
			isHiddenDeleBtn:this.isReadOnly
		})
		this.BpCustProspectiveFollowRecordInfo =new BpCustProspectiveFollowRecordInfo({
			personType:this.personType
		});
		this.ProspectiveFollowView =new ProspectiveFollowView({
			isReadOnly:true
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
				title : '客户基本信息',
				bodyStyle : 'padding-left: 0px',
				collapsible : true,
				items : [this.customerbaseInfo]
			},/*{
				xtype : 'fieldset',
				title : '联系人信息',
				collapsible : true,
				autoHeight : true,
				bodyStyle : 'padding-left: 0px',
				items : [this.PersonRelationView]
			},*/{
				xtype : 'fieldset',
				title : '跟进记录',
				collapsible : true,
				items : [this.CsProspectivePersonFollowView]
			},{
				xtype : 'fieldset',
				title : '跟进信息',
				collapsible : true,
				items : [this.BpCustProspectiveFollowRecordInfo]
			},{
				xtype : 'fieldset',
				title : '设置客户状态',
				collapsible : true,
				items : [this.ProspectiveFollowView]
			}]
		});
		
		// 加载表单对应的数据
		if (this.perId != null && this.perId != 'undefined') {
			var panel=this
			this.formPanel.loadData({
						url:__ctxPath + "/creditFlow/customer/customerProsperctive/getBpCustProsperctive.do?perId="+this.perId,
						root : 'data',
						preName : ['bpCustProsperctive',"object"],
						success : function(resp, options) {
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
		var flashTargat =this.CsProspectivePersonFollowView;
		var formPanel1 =this.formPanel;
		var gpanel=this.gpanel
		if(formPanel1.getForm().isValid()){
		formPanel1.getForm().submit({
			 clientValidation: false,
					url :__ctxPath+ '/creditFlow/customer/customerProsperctiveFollowup/saveBpCustProspectiveFollowup.do',
					method : 'post',
					waitMsg : '数据正在提交，请稍后...',
					scope: this,
					success : function(fp, action) {
						Ext.ux.Toast.msg('操作信息', '保存信息成功!');
						flashTargat.get(0).getStore().reload();
						formPanel1.getCmpByName("followPersonId1").setValue(null);
						formPanel1.getCmpByName("bpCustProspectiveFollowup.followPersonId").setValue(null);
						formPanel1.getCmpByName("bpCustProspectiveFollowup.followType").setValue(null);
						formPanel1.getCmpByName("bpCustProspectiveFollowup.followDate").setValue(null);
						formPanel1.getCmpByName("bpCustProspectiveFollowup.successRate").setValue(null);
						formPanel1.getCmpByName("bpCustProspectiveFollowup.followTitle").setValue(null);
						formPanel1.getCmpByName("bpCustProspectiveFollowup.followInfo").setValue(null);
						flashTargat.ownerCt.ownerCt.ownerCt.close();
						gpanel.getStore().reload()
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
	}// end of save

});