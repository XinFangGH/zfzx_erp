/**
 * @author
 * @createtime
 * @class LeaveManageWin
 * @extends Ext.Window
 * @description Regulation表单
 * @company 智维软件
 */
LeaveManageWin = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		LeaveManageWin.superclass.constructor.call(this, {
					id : 'LeaveManageWin',
					layout : 'form',
					items : [this.displayPanel, this.formPanel],
					modal : true,
					width : 540,
					height : 360,
					iconCls : 'menu-holiday',
					maximizable : true,
					title : '请假信息审核',
					buttonAlign : 'center',
					buttons : [{
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
		this.displayPanel = new Ext.Panel({
					flex : 1,
					autoHeight : true,
					autoWidth : true,
					border : false,
					autoLoad : {
						url : __ctxPath
								+ '/pages/personal/errandsRegisterDetail.jsp?dateId='
								+ this.dateId
					}
				});
		this.formPanel = new Ext.FormPanel({
					layout : 'form',
					bodyStyle : 'padding:10px',
					border : false,
					autoScroll : true,
					// id : 'RegulationForm',
					defaults : {
						anchor : '98%,96%'
					},
					items : [{
								name : 'errandsRegister.dateId',
								id : 'dateId',
								xtype : 'hidden',
								value : this.dateId == null ? '' : this.dateId
							}, {
								fieldLabel : '审批状态',
								xtype : 'combo',
								hiddenName : 'errandsRegister.status',
								editable : false,
								triggerAction : 'all',
								store : [['1', '通过审批'], ['2', '未通过审批']],
								value : 1
							}, {
								xtype : 'textarea',
								fieldLabel : '审批意见',
								name : 'errandsRegister.approvalOption'
							}]
				})
	},// end of the initcomponents
	// 保存
	save : function() {
		$postForm({
					formPanel : this.formPanel,
					scope : this,
					url : __ctxPath + '/personal/saveErrandsRegister.do',
					callback : function(fp, action) {
						var gridPanel = Ext.getCmp('LeaveManageGrid');
						if (gridPanel != null) {
							gridPanel.getStore().reload();
						}
						this.close();
					}
				});
	},
	/**
	 * 取消
	 * 
	 * @param {}
	 *            window
	 */
	cancel : function() {
		this.close();
	}

});