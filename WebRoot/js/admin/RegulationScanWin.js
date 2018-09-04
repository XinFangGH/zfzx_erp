/**
 * @author
 * @createtime
 * @class RegulationScanWin
 * @extends Ext.Window
 * @description Regulation表单
 * @company 智维软件
 */
RegulationScanWin = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		RegulationScanWin.superclass.constructor.call(this, {
					id : 'RegulationScanWinWin',
					layout : 'fit',
					items : this.displayPanel,
					modal : true,
					height : 577,
					width : 1000,
					iconCls : 'btn-suggest-scan',
					maximizable : true,
					title : '规章制度详细信息',
					buttonAlign : 'center',
					buttons : [ {
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
					id : 'CheckEmpProfileFormPanel',
//					height : 430,
					autoScroll : true,
					border : false,
					autoLoad : {
						url : __ctxPath+ '/pages/admin/displayRegulation.jsp?regId='+ this.regId
					}
				});
		

	},// end of the initcomponents

	
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