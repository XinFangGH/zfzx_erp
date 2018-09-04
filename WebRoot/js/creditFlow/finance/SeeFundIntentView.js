/**
 * @author
 * @createtime
 * @class SlCompanyMainForm
 * @extends Ext.Window
 * @description SlCompanyMain表单
 * @company 北京互融时代软件有限公司
 */
SeeFundIntentView = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {

		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		SeeFundIntentView.superclass.constructor.call(this, {
					layout : 'fit',
					items : [this.gridPanel2,this.SlFundIntentViewVM],
					modal : true,
					height : 600,
					width : 1200,
					maximizable : true,
					autoScroll : true,
					title : '查看'+this.bidProName+'款项计划表',
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
		this.gridPanel2 = new BpFundIntentFapView({
				prdojectId : this.projectId,
				object : this.projectInfoFinance,
				bidPlanFinanceInfo:this.BidPlanFinanceInfo,
				isHidden : true,
				isHiddenAdd:true,
				isHiddenDel:true,
				isHiddenAutoCreate:true,
				hiddenCheck:false,
				isHidden1:true,
				calcutype : 1 ,    
				isHiddenExcel:true,//贷款
				isHiddenseeqlideBtn:true,
				inverstPersonId:null,
				isHiddenTitle:true,
				isOwnBpFundProject:((this.proType=='P_Dir' || this.proType=='B_Dir')?false:true),
				businessType : this.businessType,
				preceptId:this.fundProjectId,
				bidPlanId:this.bidPlanId,
				fundResource:1,
				isFlow:false
			});
			this.SlFundIntentViewVM = new CusterFundIntentView({
				projectId :this.projectId,
				bidPlanId:this.bidPlanId,
				object : this.projectInfoFinance,
				bidPlanFinanceInfo:this.BidPlanFinanceInfo,
				businessType : this.businessType,
				isHiddenautocreateBtn:true,
				hiddenCheck:false,
				isHiddenExcel:true,
				isHaveLending:'yes',
				isHiddenAddBtn : true,// 生成
				isHiddenDelBtn : true,// 删除
				isHiddenCanBtn : true,// 取消
				isHiddenResBtn : true,// 还原
				isHiddenResBtn1 : true,// 手动对账
				isHiddenseeqlideBtn : true,// 查看流水单项订单
				isHiddenseesumqlideBtn : true,
				isHidden1:true
			});


	},
	cancel : function() {
		this.close();
	}

});

