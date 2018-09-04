/**
 * @author
 * @createtime
 * @class SuggestBoxDisplay
 * @extends Ext.Window
 * @description SuggestBox表单
 * @company 智维软件
 */
SuggestBoxDisplay = Ext.extend(Ext.Window, {
	// 内嵌FormPanel
	formPanel : null,
	// 显示意见信息
	displayPanel : null,
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		SuggestBoxDisplay.superclass.constructor.call(this, {
					id : 'SuggestBoxDisplayWin',
					layout : 'fit',
					items : this.displayPanel,
					modal : true,
					height : 550,
					iconCls : 'btn-suggest-scan',
					width : 735,
//					autoScroll:true,
					maximizable : true,
					title : '意见浏览',
					buttonAlign : 'center',
					buttons : this.buttons
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
						url : __ctxPath
								+ '/pages/info/displaySuggest.jsp?boxId='
								+ this.boxId
					}
				});
		
		// 初始化功能按钮
		this.buttons = [ {
					text : '取消',
					iconCls : 'btn-cancel',
					scope : this,
					handler : this.cancel.createCallback(this)
				}];
	},// end of the initcomponents

	/**
	 * 取消
	 * 
	 * @param {}
	 *            window
	 */
	cancel : function(window) {
		window.close();
	}
});