/**
 * @author
 * @createtime
 * @class PlKeepCreditlevelForm
 * @extends Ext.Window
 * @description PlKeepCreditlevel表单
 * @company 智维软件
 */
PlKeepCreditlevelForm = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		PlKeepCreditlevelForm.superclass.constructor.call(this, {
					id : 'PlKeepCreditlevelFormWin',
					layout : 'fit',
					items : this.formPanel,
					modal : true,
					height : 400,
					width : 500,
					maximizable : true,
					title : '信用等级详细信息',
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
					// id : 'PlKeepCreditlevelForm',
					defaults : {
						anchor : '96%,96%'
					},
					defaultType : 'textfield',
					items : [{
						name : 'plKeepCreditlevel.creditLevelId',
						xtype : 'hidden',
						value : this.creditLevelId == null
								? ''
								: this.creditLevelId
					}, {
						fieldLabel : '名称',
						name : 'plKeepCreditlevel.name',
						maxLength : 50
					}, {
						fieldLabel : 'Key',
						name : 'plKeepCreditlevel.keyStr',
						maxLength : 50
					}, {
						fieldLabel : '描述',
						name : 'plKeepCreditlevel.remark',
						xtype : 'textarea',
						maxLength : 65535
					}]
				});
		// 加载表单对应的数据
		if (this.creditLevelId != null && this.creditLevelId != 'undefined') {
			this.formPanel.loadData({
				url : __ctxPath
						+ '/creditFlow/financingAgency/typeManger/getPlKeepCreditlevel.do?creditLevelId='
						+ this.creditLevelId,
				root : 'data',
				preName : 'plKeepCreditlevel'
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
					+ '/creditFlow/financingAgency/typeManger/savePlKeepCreditlevel.do',
			callback : function(fp, action) {
				var gridPanel = Ext.getCmp('PlKeepCreditlevelGrid');
				if (gridPanel != null) {
					gridPanel.getStore().reload();
				}
				this.close();
			}
		});
	}// end of save

});