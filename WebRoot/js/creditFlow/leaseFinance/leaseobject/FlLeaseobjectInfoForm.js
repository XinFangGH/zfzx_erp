/**
 * @author 
 * @createtime 
 * @class FlLeaseobjectInfoForm
 * @extends Ext.Window
 * @description FlLeaseobjectInfo表单
 * @company 智维软件
 */
FlLeaseobjectInfoForm = Ext.extend(Ext.Window, {
	//构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		//必须先初始化组件
		this.initUIComponents();
		FlLeaseobjectInfoForm.superclass.constructor.call(this, {
					id : 'FlLeaseobjectInfoFormWin',
					layout : 'fit',
					items : this.formPanel,
					modal : true,
					height : 400,
					width : 500,
					maximizable : true,
					title : '[FlLeaseobjectInfo]详细信息',
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
					//id : 'FlLeaseobjectInfoForm',
					defaults : {
						anchor : '96%,96%'
					},
					defaultType : 'textfield',
					items : [{
								name : 'flLeaseobjectInfo.id',
								xtype : 'hidden',
								value : this.id == null ? '' : this.id
							}, {
								fieldLabel : '租赁标的名称',
								name : 'flLeaseobjectInfo.name',
								allowBlank : false,
								maxLength : 255
							}, {
								fieldLabel : '认购价格',
								name : 'flLeaseobjectInfo.buyPrice',
								allowBlank : false
							}, {
								fieldLabel : '规格型号',
								name : 'flLeaseobjectInfo.standardSize',
								allowBlank : false,
								maxLength : 255
							}, {
								fieldLabel : '原价格',
								name : 'flLeaseobjectInfo.originalPrice',
								allowBlank : false
							}, {
								fieldLabel : '使用年限',
								name : 'flLeaseobjectInfo.useYears',
								xtype : 'numberfield'
							}, {
								fieldLabel : '数量',
								name : 'flLeaseobjectInfo.objectCount',
								xtype : 'numberfield'
							}, {
								fieldLabel : '供货单位id',
								name : 'flLeaseobjectInfo.suppliorId',
								allowBlank : false,
								xtype : 'numberfield'
							}, {
								fieldLabel : '租赁标的信息',
								name : 'flLeaseobjectInfo.objectComment',
								maxLength : 255
							}, {
								fieldLabel : '金融租赁项目id',
								name : 'flLeaseobjectInfo.projectId',
								allowBlank : false,
								xtype : 'numberfield'
							}]
				});
		//加载表单对应的数据	
		if (this.id != null && this.id != 'undefined') {
			this.formPanel.loadData({
				url : __ctxPath	+ '/creditFlow.leaseFinance.leaseobject/getFlLeaseobjectInfo.do?id='
						+ this.id,
				root : 'data',
				preName : 'flLeaseobjectInfo'
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
			url : __ctxPath	+ '/creditFlow.leaseFinance.leaseobject/saveFlLeaseobjectInfo.do',
			callback : function(fp, action) {
				var gridPanel = Ext.getCmp('FlLeaseobjectInfoGrid');
				if (gridPanel != null) {
					gridPanel.getStore().reload();
				}
				this.close();
			}
		});
	}

});