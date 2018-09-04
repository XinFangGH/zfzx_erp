/**
 * @author:
 * @class SlCompanyMainView
 * @extends Ext.Panel
 * @description [SlCompanyMain]管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
SeeManageMoneyInfo = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
			if (_cfg == null) {
			_cfg = {};
		}
		Ext.applyIf(this, _cfg);
		this.initComponents();
		// 调用父类构造
		SeeManageMoneyInfo.superclass.constructor.call(this, {
			id : 'SeeManageMoneyInfo_'+this.orderId,
			title : '【'+this.bidName+'-'+this.userName+'】详情',
			border : false
		});
	},// end of constructor
	initComponents : function() {
				var jsArr = [
					__ctxPath + '/js/creditFlow/financingAgency/manageMoney/PlManageMoneyProductInfoForm.js',
					__ctxPath + '/js/creditFlow/financingAgency/manageMoney/PlManageMoneyBuyInfoForm.js',
					__ctxPath + '/js/creditFlow/financingAgency/manageMoney/PlEarlyRedemptionForm.js',
					__ctxPath + '/js/creditFlow/financingAgency/manageMoney/ManageMoneyBuyMatchingDetail.js',
					__ctxPath + '/js/creditFlow/financingAgency/manageMoney/ManageMoneyProductInterestView.js',
					__ctxPath + '/js/creditFlow/financingAgency/manageMoney/EarlyRedemptionRecord.js',
					__ctxPath + '/js/creditFlow/financingAgency/manageMoney/EarlyRedemptionInfo.js',
					__ctxPath + '/js/creditFlow/creditAssignment/customer/public.js'
			];
			$ImportSimpleJs(jsArr, this.constructPanel, this);
		},
	constructPanel : function() {
		this.plManageMoneyProductInfoForm=new PlManageMoneyProductInfoForm({})
		this.plManageMoneyBuyInfoForm=new PlManageMoneyBuyInfoForm({})
		this.manageMoneyBuyMatchingDetail=new ManageMoneyBuyMatchingDetail({orderId:this.orderId})
		this.manageMoneyProductInterestView=new ManageMoneyProductInterestView({
			orderId:this.orderId,
			isHidden:true,
			isFlow:true
		})
		this.PlMmOrderInfoPanel = new PlMmOrderInfoPanel({
			isReadOnly: true/*,
			projectId:this.orderId*/
		});
		
		this.ourProcreditMaterialsView=new OurProcreditMaterialsViewProduct_FinanceProduct({
			isProduct:false,
			projectId:this.runId,
			isReadOnly:true,
			isAllReadOnly:true,
			isTurnover:true,
			hiddenAdd:true,
			hiddenedit:true,
			hiddenDel:true,
			isHidden_materials:true,
			customerType:"('investPerson')"
		});
		
		this.htReportView = new SlReportView({
			projectId : this.runId,
			Template : 'HTMX',
			businessType : 'SmallLoan',
	    	isHidden_report : true
		});
		this.EarlyRedemptionRecord=new EarlyRedemptionRecord({
			orderId : this.orderId
		})
		this.formPanel=new Ext.Panel({
			frame : true,
			items : [{
				xtype : 'fieldset',
				name:'projectInfo',
				title : '理财产品信息 ',
				collapsible : true,
				autoHeight : true,
				labelAlign : 'right',
				items : [this.plManageMoneyProductInfoForm]
			},{
				xtype : 'fieldset',
				title : '销售信息 ',
				collapsible : true,
				autoHeight : true,
				labelAlign : 'right',
				items : [this.PlMmOrderInfoPanel]
			},{
				xtype : 'fieldset',
				title : '认购信息 ',
				collapsible : true,
				autoHeight : true,
				labelAlign : 'right',
				items : [this.plManageMoneyBuyInfoForm]
			},{
				xtype : 'fieldset',
				title : '收益清单',
				collapsible : true,
				autoHeight : true,
				labelAlign : 'right',
				items : [this.manageMoneyProductInterestView]
			},{
				xtype : 'fieldset',
				title : '材料清单',
				collapsible : true,
				autoHeight : true,
				labelAlign : 'right',
				items : [this.ourProcreditMaterialsView]
			},{
				xtype : 'fieldset',
				title : '合同明细',
				collapsible : true,
				autoHeight : true,
				labelAlign : 'right',
				items : [this.htReportView]
			},{
				xtype : 'fieldset',
				title : '债权清单',
				collapsible : true,
				autoHeight : true,
				labelAlign : 'right',
				items : [this.manageMoneyBuyMatchingDetail]
			},{
				xtype : 'fieldset',
				title : '提前赎回记录',
				collapsible : true,
				autoHeight : true,
				labelAlign : 'right',
				items : [this.EarlyRedemptionRecord]
			}]
		})
		this.loadData({
			url : __ctxPath + '/creditFlow/financingAgency/getPlManageMoneyPlanBuyinfo.do?id='+this.orderId,
			method : "POST",
			preName : ["plManageMoneyPlanBuyinfo","plManageMoneyPlan","plMmOrderInfo"],
			root : 'data',
			success : function(response, options) {
				var respText = response.responseText;
				var alarm_fields = Ext.util.JSON.decode(respText);
				if(this.PlMmOrderInfoPanel.plMmOrderInfo){
					this.PlMmOrderInfoPanel.getCmpByName('plMmOrderInfo.customerManagerName').setValue(result.data.customerManagerName);
					this.PlMmOrderInfoPanel.getCmpByName('plMmOrderInfo.teamManageName').setValue(result.data.teamManageName);
					this.PlMmOrderInfoPanel.getCmpByName('plMmOrderInfo.departUser').setValue(result.data.departUser);
				}
			}
		});
		this.add(this.formPanel);
		this.doLayout();
	}
	
});
