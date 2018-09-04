/**
 * @author 
 * @createtime 
 * @class FlObjectSuppliorForm
 * @extends Ext.Window
 * @description FlObjectSupplior表单
 * @company 智维软件
 */
FlObjectSuppliorForm = Ext.extend(Ext.Window, {
	//构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		//必须先初始化组件
		this.initUIComponents();
		FlObjectSuppliorForm.superclass.constructor.call(this, {
					id : 'FlObjectSuppliorFormWin',
					layout : 'fit',
					items : this.formPanel,
					modal : true,
					height : 400,
					width : 500,
					maximizable : true,
					title : '[FlObjectSupplior]详细信息',
					buttonAlign : 'center',
					buttons : [{
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
	},//end of the constructor
	//初始化组件
	initUIComponents : function() {
		this.formPanel = new Ext.FormPanel({
					layout : 'form',
					bodyStyle : 'padding:10px',
					border : false,
					autoScroll : true,
					//id : 'FlObjectSuppliorForm',
					defaults : {
						anchor : '96%,96%'
					},
					defaultType : 'textfield',
					items : [{
								name : 'flObjectSupplior.id',
								xtype : 'hidden',
								value : this.id == null ? '' : this.id
							}, {
								fieldLabel : '法人姓名',
								name : 'flObjectSupplior.legalPersonName',
								maxLength : 255
							}, {
								fieldLabel : '公司电话',
								name : 'flObjectSupplior.companyPhoneNum',
								maxLength : 255
							}, {
								fieldLabel : '供货方名称',
								name : 'flObjectSupplior.Name', 
								allowBlank : false,
								maxLength : 255
							}, {
								fieldLabel : '联系人名称',
								name : 'flObjectSupplior.connectorName',
								maxLength : 255
							}, {
								fieldLabel : '联系人电话',
								name : 'flObjectSupplior.connectorPhoneNum',
								maxLength : 255
							}, {
								fieldLabel : '联系人职位',
								name : 'flObjectSupplior.connectorPosition',
								maxLength : 255
							}, {
								fieldLabel : '公司传真',
								name : 'flObjectSupplior.companyFax',
								maxLength : 255
							}, {
								fieldLabel : '供货方地址',
								name : 'flObjectSupplior.companyAddress',
								maxLength : 255
							}, {
								fieldLabel : '备注',
								name : 'flObjectSupplior.companyComment',
								maxLength : 255
							}]
				});
		//加载表单对应的数据	
		if (this.id != null && this.id != 'undefined') {
			this.formPanel.loadData({
				url : __ctxPath
						+ '/creditFlow.leaseFinance.supplior/getFlObjectSupplior.do?id='
						+ this.id,
				root : 'data',
				preName : 'flObjectSupplior'
			});
		}

	},//end of the initcomponents

	/**
	 * 重置
	 * @param {} formPanel
	 */
	reset : function() {
		this.formPanel.getForm().reset();
	},
	/**
	 * 取消
	 * @param {} window
	 */
	cancel : function() {
		this.close();
	},
	/**
	 * 保存记录
	 */
	save : function() {
		$postForm({
			formPanel : this.formPanel,
			scope : this,
			url : __ctxPath
					+ '/creditFlow.leaseFinance.supplior/saveFlObjectSupplior.do',
			callback : function(fp, action) {
				var gridPanel = Ext.getCmp('FlObjectSuppliorGrid');
				if (gridPanel != null) {
					gridPanel.getStore().reload();
				}
				this.close();
			}
		});
	}//end of save

});