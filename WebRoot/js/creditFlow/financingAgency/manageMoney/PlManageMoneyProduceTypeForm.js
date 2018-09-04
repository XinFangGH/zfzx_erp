/**
 * @author
 * @createtime
 * @class PlManageMoneyTypeForm
 * @extends Ext.Window
 * @description PlManageMoneyType表单
 * @company 智维软件
 */
PlManageMoneyProduceTypeForm = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		PlManageMoneyProduceTypeForm.superclass.constructor.call(this, {
					id : 'PlManageMoneyProduceTypeFormWin',
					layout : 'fit',
					items : this.formPanel,
					modal : true,
					height : 200,
					width : 400,
					maximizable : true,
					resizable:false,
					title : '理财产品类型详情',
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
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		this.formPanel = new Ext.FormPanel({
					layout : 'form',
					bodyStyle : 'padding:10px',
					border : false,
					autoScroll : true,
					frame:true,
					defaults : {
						anchor : '96%,96%'
					},
					labelAlign : "right",
					defaultType : 'textfield',
					items : [{
						name : 'plManageMoneyType.manageMoneyTypeId',
						xtype : 'hidden',
						value : this.manageMoneyTypeId == null
								? ''
								: this.manageMoneyTypeId
					}, {
//						fieldLabel : '理财产品类型名称',
						fieldLabel : '名称',
						name : 'plManageMoneyType.name',
						allowBlank:false,
						maxLength : 20
					}, {
						fieldLabel : '描述',
						name : 'plManageMoneyType.remark',
						xtype:"textarea",
						maxLength : 100
					}]
				});
		// 加载表单对应的数据
		if (this.manageMoneyTypeId != null
				&& this.manageMoneyTypeId != 'undefined') {
			this.formPanel.loadData({
				url : __ctxPath
						+ '/creditFlow/financingAgency/getPlManageMoneyType.do?manageMoneyTypeId='
						+ this.manageMoneyTypeId,
				root : 'data',
				preName : 'plManageMoneyType'
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
		$postForm({
					formPanel : this.formPanel,
					scope : this,
					url : __ctxPath
							+ '/creditFlow/financingAgency/savePlManageMoneyType.do?keystr=mmproduce',
					callback : function(fp, action) {
						var gridPanel = Ext.getCmp('PlManageMoneyProduceTypegrid');
						if (gridPanel != null) {
							gridPanel.getStore().reload();
						}
						this.close();
					}
				});
	}// end of save

});