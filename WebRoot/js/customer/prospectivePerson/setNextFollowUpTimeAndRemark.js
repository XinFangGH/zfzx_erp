//setNextFollowUpTimeAndRemark.js
setNextFollowUpTimeAndRemark = Ext.extend(Ext.Window, {
	isLook : false,
	isRead : false,
	isflag : false,
	// 构造函数
	investPersonPanel : null,
	investPersonInfo:null,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		};
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		var leftlabel = 75;
		setNextFollowUpTimeAndRemark.superclass.constructor.call(this, {
					layout : 'fit',
					autoScroll:true,
					items : this.formPanel,
					modal : true,
					height : 150,
					width : 400,
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
							xtype : "hidden",
							name : "bpCustProsperctive.perId",
							value:this.perId
					},{
							columnWidth : 1, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 90,
							items : [{
										fieldLabel : "下次跟进时间",
										xtype : 'datetimefield',
										format : 'Y-m-d H:i:s',
										readOnly : this.isDiligenceReadOnly,
										name : "bpCustProsperctive.nextFollowDate",
										blankText : "下次跟进时间不能为空，请正确填写!",
										allowBlank : false,
										editable : false,
										minValue : new Date(),
										anchor : "90%"// 控制文本框的长度

									}]
						}/*,{
							columnWidth : 1, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 90,
							items : [{
										fieldLabel : "提醒内容",
										xtype : 'textarea',
										readOnly : this.isDiligenceReadOnly,
										name : "bpCustProsperctive.followUpRemark",
										anchor : "90%"// 控制文本框的长度

									}]
						}*/]
		});
		

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
		var panel=this;
		var formPanel1 =this.formPanel
		var flashTargat=this.flashTargat
		if(formPanel1.getForm().isValid()){
		formPanel1.getForm().submit({
				    clientValidation: false, 
					url:__ctxPath+ '/creditFlow/customer/customerProsperctive/setNextFollowUpDateBpCustProsperctive.do',
					params : {
						"personId":this.personId
					},
					method : 'post',
					waitMsg : '数据正在提交，请稍后...',
					scope: this,
					success : function(fp, action) {
						var object = Ext.util.JSON.decode(action.response.responseText)
						Ext.ux.Toast.msg('操作信息', '保存信息成功!');
						panel.close();
						flashTargat.getStore().reload();
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
		}	

});