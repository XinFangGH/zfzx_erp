/**
 * @author
 * @createtime
 * @class PlKeepProtypeForm
 * @extends Ext.Window
 * @description PlKeepProtype表单
 * @company 智维软件
 */
PlKeepProtypeForm = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		PlKeepProtypeForm.superclass.constructor.call(this, {
					id : 'PlKeepProtypeFormWin',
					layout : 'fit',
					items : this.formPanel,
					modal : true,
					height : 400,
					width : 500,
					maximizable : true,
					title : '借款类型详细信息',
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
					// id : 'PlKeepProtypeForm',
					defaults : {
						anchor : '96%,96%'
					},
					defaultType : 'textfield',
					items : [{
								name : 'plKeepProtype.typeId',
								xtype : 'hidden',
								value : this.typeId == null ? '' : this.typeId
							}, {
								fieldLabel : '名称',
								name : 'plKeepProtype.name',
								maxLength : 50
							}, {
								fieldLabel : 'key',
								name : 'plKeepProtype.keyStr',
								maxLength : 50
							}, {
								fieldLabel : '描述',
								name : 'plKeepProtype.remark',
								xtype : 'textarea',
								maxLength : 65535
							}]
				});
		// 加载表单对应的数据
		if (this.typeId != null && this.typeId != 'undefined') {
			this.formPanel.loadData({
				url : __ctxPath
						+ '/creditFlow/financingAgency/typeManger/getPlKeepProtype.do?typeId='
						+ this.typeId,
				root : 'data',
				preName : 'plKeepProtype'
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
					+ '/creditFlow/financingAgency/typeManger/savePlKeepProtype.do',
			callback : function(fp, action) {
				var gridPanel = Ext.getCmp('PlKeepProtypeGrid');
				if (gridPanel != null) {
					gridPanel.getStore().reload();
				}
				this.close();
			}
		});
	}// end of save

});