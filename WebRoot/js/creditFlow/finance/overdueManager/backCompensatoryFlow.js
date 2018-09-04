//backCompensatoryFlow

/**
 *	backCompensatoryFlow.js
 *	散标代偿款项台账回款流水记录表
 *	主要作用：查询出散标代偿款项台账回款流水记录
 *  add by linyan  2015-10-26
 */
 
  backCompensatoryFlow = Ext.extend(Ext.Window, {
	isLook : false,
	//isReadOnly : false,
	// 构造函数
	constructor : function(_cfg) {
		
		if (_cfg == null) {
			_cfg = {};
		};
		
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		win = this;
		backCompensatoryFlow.superclass.constructor.call(this, {
					layout : 'fit',
					items : this.CompensatoryFlow,
					modal : true,
					height : 500,
					width : screen.width-252,
					autoScroll:true,
					maximizable : true,
					title : this.titleChange,
					buttonAlign : 'center'
				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		
		this.CompensatoryFlow=new CompensatoryFlow({
			compensatoryId:this.compensatoryId,
			autoHeight:false,
			showPaging:true
		});
		

	},// end of the initcomponents

	/**
	 * 重置
	 * 
	 * @param {}
	 *            formPanel
	 */
	reset : function() {
		this.formPanel.getForm().reset();
		//this.getCmpByName('obObligationInvestInfo.investPersonName').setValue(this.investPersonName);
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
	}// end of save

});