/**
 * @author
 * @createtime
 * @class BpProductParameterForm
 * @extends Ext.Window
 * @description BpProductParameter表单
 * @company 智维软件
 */
P2pAddProduct = Ext.extend(Ext.Window, {
	// 构造函数
	isAllReadOnly:false,
	productId:null,
	constructor : function(_cfg) {
		if(_cfg && typeof(_cfg.isAllReadOnly)!="undefined"){
			this.isAllReadOnly=_cfg.isAllReadOnly;
		}
		if(_cfg && typeof(_cfg.productId)!="undefined"){
			this.productId=_cfg.productId;
		}
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		P2pAddProduct.superclass.constructor.call(this, {
					id : 'P2pAddProductWin',
					layout : 'fit',
					items : this.outPanel,
					modal : true,
					autoScroll:true,
					maximizable : true,
					frame:true,
					height : 200,
			        width :400,
					title : '新增产品',
					buttonAlign : 'center',
					buttons :this.isHideBtns?null: [{
								text : '保存',
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
		this.outPanel = new Ext.form.FormPanel({
			modal : true,
			labelWidth : 70,
			frame:true,
			monitorValid : true,
			buttonAlign : 'center',
			layout : 'form',
		    items:[/*{
								fieldLabel : '业务品种',
								xtype : 'combo',
								mode : 'local',
								displayField : 'name',
								triggerAction : "all",
								hiddenName:'p2pLoanProduct.operationType',
								anchor : '100%',
								valueField : 'id',
								allowBlank : false,
								editable : false,
								store : new Ext.data.SimpleStore({
									fields : ["name", "id"],
									data : [["企业贷", "enterprise"], ["个人贷", "person"]]
								})
						}*/{
							xtype:'hidden',
							name:'p2pLoanProduct.operationType',
							value:'person'
						},{
							fieldLabel : '业务品种',
							xtype : 'textfield',
							hiddenName:'busType',
							anchor : '100%',
							allowBlank : false,
							readOnly:true,
							editable : false,
							value:'个人贷'
						},{
								fieldLabel : '产品名称',
								name : 'p2pLoanProduct.productName',
								xtype : 'textfield',
								allowBlank : false,
								anchor : '100%',
								maxLength : 255
							}, {
								fieldLabel : '适用范围',
								name : 'p2pLoanProduct.userScope',
								xtype : 'textarea',
								allowBlank : false,
								anchor : '100%',
								maxLength : 255
							}, {
								name : 'p2pLoanProduct.productState',
								xtype : 'textarea',
								anchor : '100%',
								hidden : true,
								value : 1
							}]
		});
		

	},// end of the initcomponents

	/**
	 * 重置
	 * 
	 * @param {}
	 *            formPanel
	 */
	reset : function() {
		this.outPanel.getForm().reset();
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
		var panel=this.gridpanel;
		if(this.outPanel.getForm().isValid()){
			this.outPanel.getForm().submit({
				    clientValidation: false, 
					url : __ctxPath+ '/p2p/saveP2pLoanProduct.do',
					method : 'post',
					waitMsg : '数据正在提交，请稍后...',
					scope: this,
					success : function(fp, action) {
//						var gridPanel = Ext.getCmp('P2pLoanProductViewGrid');
//						if (panel != null) {
//							panel.getStore().reload();
							Ext.ux.Toast.msg("操作信息","保存成功,请到【产品参数配置】中进行下一步操作!!!");
//						}
						this.close();
					}
			});
		}
	}
});