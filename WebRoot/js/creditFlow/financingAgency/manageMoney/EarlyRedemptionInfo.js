/**
 * @author
 * @createtime
 * @class SlCompanyMainForm
 * @extends Ext.Window
 * @description SlCompanyMain表单
 * @company 北京互融时代软件有限公司
 */
EarlyRedemptionInfo = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
	
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		EarlyRedemptionInfo.superclass.constructor.call(this, {
					layout : 'fit',
					items : this.formPanel,
					modal : true,
					height : 500,
					width : 1000,
					maximizable : true,
					title : '提前赎回的详细信息',
					buttonAlign : 'center',
					buttons : [{
								text : '关闭',
								iconCls : 'close',
								scope : this,
								handler : this.cancel
							}]
				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		this.plEarlyRedemptionForm=new PlEarlyRedemptionForm({isReadOnly:true})
		this.manageMoneyProductInterestView=new ManageMoneyProductInterestView({
			orderId:this.orderId,
			earlyRedemptionId:this.earlyRedemptionId,
			object:this.plEarlyRedemptionForm,
			isHidden:true
		})
		this.formPanel = new Ext.Panel({
			autoScroll : true,
			frame : true,
			items : [this.plEarlyRedemptionForm,this.manageMoneyProductInterestView]
		});
	
		this.formPanel.loadData({
			url : __ctxPath + '/creditFlow/financingAgency/getInfoPlEarlyRedemption.do?earlyRedemptionId='+this.earlyRedemptionId,
			method : "POST",
			preName : ["plEarlyRedempdtion"],
			root : 'data',
			success : function(response, options) {
			}
		});
	},
	cancel : function(){
		this.close()
	}

});

