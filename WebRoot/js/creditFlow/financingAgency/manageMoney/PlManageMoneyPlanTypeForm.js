/**
 * @author
 * @createtime
 * @class PlManageMoneyTypeForm
 * @extends Ext.Window
 * @description PlManageMoneyType表单
 * @company 智维软件
 */
PlManageMoneyPlanTypeForm = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		PlManageMoneyPlanTypeForm.superclass.constructor.call(this, {
					id : 'PlManageMoneyTypeFormWin',
					layout : 'fit',
					items : this.formPanel,
					modal : true,
					height : 300,
					width : 500,
					maximizable : true,
					
					title : '理财计划类型详情',
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
					// id : 'PlManageMoneyTypeForm',
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
						fieldLabel : '理财计划类型名称',
						name : 'plManageMoneyType.name',
						readOnly:this.isReadOnly,
						maxLength : 20
					}, {
						fieldLabel : '描述',
						name : 'plManageMoneyType.remark',
						readOnly:this.isReadOnly,
						xtype:"textarea",
						maxLength : 100
					},{
						fieldLabel : '下次发标时间',
						name : 'plManageMoneyType.nextPublisPlanTime',
						hidden : !this.isReadOnly,
						hideLabel : !this.isReadOnly,
						xtype : 'datefield',
						xtype : 'datetimefield',
						format : 'Y-m-d H:i:s'
					
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
							+ '/creditFlow/financingAgency/savePlManageMoneyType.do?keystr=mmplan',
					callback : function(fp, action) {
						var gridPanel = Ext.getCmp('PlManageMoneyPlanTypegrid');
						if (gridPanel != null) {
							gridPanel.getStore().reload();
						}
						this.close();
					}
				});
	}// end of save

});