PlBidPlanInfoCusterFundIntent = Ext.extend(Ext.Panel, {

	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.applyIf(this, _cfg);
		this.initComponents();
		PlBidPlanInfoCusterFundIntent.superclass.constructor.call(this, {
			id : 'PlBidPlanInfoCusterFundIntent_'+this.bidPlanId,
			border : false
		});
	},
	initComponents : function() {
			var jsArr = [
		    	__ctxPath + '/js/creditFlow/finance/BpFundIntentFapView.js',
		    	__ctxPath + '/js/creditFlow/finance/CusterFundIntentView.js'
		  
		];
		$ImportSimpleJs(jsArr, this.constructPanel, this);
	},
	constructPanel : function() {
	
		this.SlFundIntentViewVM = new CusterFundIntentView({
			projectId :this.projectId,
			bidPlanId:this.bidPlanId,
			object : this.projectInfoFinance,
			bidPlanFinanceInfo:this.BidPlanFinanceInfo,
			businessType : this.businessType,
			isHiddenautocreateBtn:true,
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
		
			//放款收息表，平台
		this.gridPanel2 = new BpFundIntentFapView({
			projectId : this.projectId,
			object : this.projectInfoFinance,
			bidPlanFinanceInfo:this.BidPlanFinanceInfo,
			isHidden : true,
			isHiddenAdd:true,
			isHiddenDel:true,
			isHiddenAutoCreate:true,
			hiddenCheck:false,
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
		
		this.formPanel = new Ext.Panel({
			modal : true,
			labelWidth : 100,
			buttonAlign : 'center',
			layout : 'form',
			border : false,
			frame :true,
			defaults : {
				anchor : '100%',
				labelAlign : 'left',
				collapsible : true,
				autoHeight : true
			},
			items : [
			{
				xtype : 'fieldset',
				title :'投资人放款收息表',
				bodyStyle : 'padding-left:0px',
				collapsible : true,
				labelAlign : 'right',
				autoHeight : true,
				items : [this.gridPanel2]
			},{
				xtype : 'fieldset',
				title :'借款人放款收息表',
				bodyStyle : 'padding-left:0px',
				collapsible : true,
				labelAlign : 'right',
				autoHeight : true,
				items : [this.SlFundIntentViewVM]
			}]
		})
		this.add(this.formPanel);
		this.doLayout();
	}
	
})
