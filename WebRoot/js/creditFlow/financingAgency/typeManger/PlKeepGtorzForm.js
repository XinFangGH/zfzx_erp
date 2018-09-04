/**
 * @author
 * @createtime
 * @class PlKeepGtorzForm
 * @extends Ext.Window
 * @description PlKeepGtorz表单
 * @company 智维软件
 */
PlKeepGtorzForm = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		PlKeepGtorzForm.superclass.constructor.call(this, {
					id : 'PlKeepGtorzFormWin',
					layout : 'fit',
					items : this.formPanel,
					modal : true,
					height : 400,
					width : 500,
					maximizable : true,
					title : '担保机构详细信息',
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
					// id : 'PlKeepGtorzForm',
					defaults : {
						anchor : '96%,96%'
					},
					defaultType : 'textfield',
					items : [{
						name : 'plKeepGtorz.projGtOrzId',
						xtype : 'hidden',
						value : this.projGtOrzId == null
								? ''
								: this.projGtOrzId
					}, {
						fieldLabel : '名称',
						name : 'plKeepGtorz.name',
						maxLength : 50
					}, {
						fieldLabel : 'KEY',
						name : 'plKeepGtorz.keyStr',
						maxLength : 50
					}, {
						fieldLabel : '简介',
						name : 'plKeepGtorz.remark',
						xtype : 'textarea',
						maxLength : 65535
					}]
				});
		// 加载表单对应的数据
		if (this.projGtOrzId != null && this.projGtOrzId != 'undefined') {
			this.formPanel.loadData({
				url : __ctxPath
						+ '/creditFlow/financingAgency/typeManger/getPlKeepGtorz.do?projGtOrzId='
						+ this.projGtOrzId,
				root : 'data',
				preName : 'plKeepGtorz'
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
					+ '/creditFlow/financingAgency/typeManger/savePlKeepGtorz.do',
			callback : function(fp, action) {
				var gridPanel = Ext.getCmp('PlKeepGtorzGrid');
				if (gridPanel != null) {
					gridPanel.getStore().reload();
				}
				this.close();
			}
		});
	}// end of save

});