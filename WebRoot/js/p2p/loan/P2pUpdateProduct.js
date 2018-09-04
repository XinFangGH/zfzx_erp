/**
 * @author
 * @createtime
 * @class BpProductParameterForm
 * @extends Ext.Window
 * @description BpProductParameter表单
 * @company 智维软件
 */
P2pUpdateProduct = Ext.extend(Ext.Window, {
	// 构造函数
	isAllReadOnly:false,
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		P2pUpdateProduct.superclass.constructor.call(this, {
					id : 'P2pUpdateProductWin',
					layout : 'fit',
					items : this.outPanel,
					modal : true,
					autoScroll:true,
					maximizable : true,
					frame:true,
					height : 120,
			        width :300,
					title : '更改产品状态',
					buttonAlign : 'center',
					buttons :this.isHideBtns?null: [{
								text : '保存',
								iconCls : 'btn-save',
								scope : this,
								handler : this.save
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
		    items:[{
								fieldLabel : '产品状态',
								xtype : 'combo',
								mode : 'local',
								displayField : 'name',
								triggerAction : "all",
								hiddenName:'productState',
								anchor : '100%',
								valueField : 'id',
								allowBlank : false,
								editable : false,
								store : new Ext.data.SimpleStore({
									fields : ["name", "id"],
									data : [["关闭", "3"], ["填写中", "1"],["发布","2"]]
								})
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
		this.formPanel.getForm().reset();
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
		if(this.outPanel.getForm().isValid()){
			this.outPanel.getForm().submit({
				    clientValidation: false, 
					url : __ctxPath+ '/p2p/updateProductP2pLoanProduct.do?productId='+this.productId,
					method : 'post',
					waitMsg : '数据正在提交，请稍后...',
					scope: this,
					success : function(response, request) {
						var obj = Ext.util.JSON.decode(request.response.responseText);
						var gridPanel = Ext.getCmp('P2pLoanProductViewGrid');
						if (gridPanel != null) {
							Ext.ux.Toast.msg("操作信息",obj.msg);
							gridPanel.getStore().reload();
						}
						this.close();
					}
			});
		}
	}
});