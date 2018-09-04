/**
 * @author
 * @createtime
 * @class BpPersionOrProForm
 * @extends Ext.Window
 * @description BpPersionOrPro表单
 * @company 智维软件
 */
BpPersionOrProForm = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		BpPersionOrProForm.superclass.constructor.call(this, {
					id : 'BpPersionOrProFormWin',
					layout : 'fit',
					items : this.formPanel,
					modal : true,
					height : 500,
					width : 700,
					maximizable : true,
					title : '贷后信息披露详细信息',
					buttonAlign : 'center',
					buttons : [{
								text : '保存',
								iconCls : 'btn-save',
								scope : this,
								hidden : this.isEdit,
								handler : this.save
							}, {
								text : '重置',
								iconCls : 'btn-reset',
								scope : this,
								hidden : this.isEdit,
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
			defaults : {
				anchor : '96%,96%'
			},
			items : [{
						name : 'bpPersionOrPro.porProId',
						xtype : 'hidden',
						value : this.porProId == null ? '' : this.porProId
					}, {
						fieldLabel : '创建日期',
						name : 'bpPersionOrPro.disclosureCreateDate',
						xtype : 'datefield',
						allowBlank : false,
						format : 'Y-m-d H:i:s',
						readOnly : true
					}, {
						fieldLabel : '修改日期',
						name : 'bpPersionOrPro.disclosureUpdateDate',
						xtype : 'datefield',
						format : 'Y-m-d H:i:s',
						value : new Date(),
						allowBlank : false,
						readOnly : true
					}, {
						fieldLabel : '融资资金运用情况',
						name : 'bpPersionOrPro.moneyUseSituation',
						readOnly:this.isReadOnly,
						height : 100,
						allowBlank : false,
						xtype:"textarea"
					}, {
						fieldLabel : '借款人经营状况及财务状况',
						name : 'bpPersionOrPro.managementSituation',
						readOnly:this.isReadOnly,
						height : 100,
						allowBlank : false,
						xtype:"textarea"
					}, {
						fieldLabel : '借款人还款能力变化情况',
						name : 'bpPersionOrPro.repaymentChangeSituation',
						readOnly:this.isReadOnly,
						height : 100,
						allowBlank : false,
						xtype:"textarea"
					}]
		});
		// 加载表单对应的数据
		if (this.porProId != null && this.porProId != 'undefined') {
			this.formPanel.loadData({
				url : __ctxPath
						+ '/creditFlow/financingAgency/persion/getBpPersionOrPro.do?porProId='
						+ this.porProId,
				root : 'data',
				preName : 'bpPersionOrPro'
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
		var grid = this.grid;
		$postForm({
			formPanel : this.formPanel,
			scope : this,
			url : __ctxPath
					+ '/creditFlow/financingAgency/persion/saveBpPersionOrPro.do',
			callback : function(fp, action) {
				if (grid != null) {
					grid.getStore().reload();
				}
				this.close();
			}
		});
	}// end of save

});