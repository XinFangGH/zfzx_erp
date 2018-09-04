/**
 * @author
 * @class SettlementReviewerPayView
 * @extends Ext.Panel
 * @description [SettlementReviewerPay]管理
 * @company 智维软件
 * @createtime:
 */
SettlementReviewerPayView1 = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initComponents();
//		this.initUIComponents();
		// 调用父类构造
		SettlementReviewerPayView1.superclass.constructor.call(this, {
					id : 'SettlementReviewerPayView1',
					title : this.title,
					layout : 'fit',
					modal : true,
					height : 600,
					width : 1000,
					maximizable : true,
					buttonAlign : 'center'
//					items : [this.gridPanel]
				});
	},// end of constructor
	initComponents : function() {
			var jsArr = [
				__ctxPath + '/js/creditFlow/financingAgency/manageMoney/SettlementReviewerPay.js',
				__ctxPath + '/js/creditFlow/fund/project/SettlementInfoPanel.js',
				__ctxPath + '/js/creditFlow/fund/project/SettlementInfoView.js',
				__ctxPath + '/js/creditFlow/fund/project/SettlementReviewerPayForm.js',
				__ctxPath + '/js/creditFlow/finance/SettlementPayToChange.js',
				__ctxPath + '/js/creditFlow/financingAgency/manageMoney/investValueInformation.js'
				
		];
		$ImportSimpleJs(jsArr, this.initUIComponents, this);
	},
	// 初始化组件
	initUIComponents : function() {
		//每日结算记录
		this.settlementInfoPanel = new SettlementInfoPanel({/*organizationId:this.organizationId,object:this.SettlementReviewerPay,*/type:this.type});
		
		//选择结算时间
		this.SettlementReviewerPay=new SettlementReviewerPay({
			organizationId:this.organizationId,
			isreadOnly:this.isreadOnly,
			settlementReviewerPayId:this.settlementReviewerPayId,
			object : this.settlementInfoPanel
		});
		//手续费添加
		this.settlementPayToChange = new SettlementPayToChange({
			projectId:this.settlementReviewerPayId,
			isHidden:true,
			businessType:this.businessType
		});
		
		//系统基本信息
		this.settlementReviewerPayForm = new SettlementReviewerPayForm({
			changeObject:this.settlementPayToChange,
			isreadOnly:this.isreadOnly,
			infoObject : this.settlementInfoPanel
			
		});
		this.formPanel = new Ext.Panel({
			modal : true,
			labelWidth : 100,
//			tbar : tbar,
			buttonAlign : 'center',
			layout : 'form',
			border : false,
			border : false,
			frame :true,
			defaults : {
				anchor : '100%',
				labelAlign : 'left',
//				collapsible : true,
				autoHeight : true
			},
			items : [{
				xtype : 'hidden',
				name : 'preHandler',
				value : 'settlementReviewerPayService.updateInfoNextStep'
			},{
				xtype : 'fieldset',
				name:'projectInfo',
				collapsible : false,
				autoHeight : true,
				labelAlign : 'right',
				items : [this.SettlementReviewerPay]
			},{
				xtype : 'fieldset',
				name:'projectInfo',
				title : '基本信息',
				collapsible : true,
				autoHeight : true,
				labelAlign : 'right',
				items : [this.settlementReviewerPayForm]
			},{
				xtype : 'fieldset',
				name:'projectInfo',
				title : '其他结算金额',
				collapsible : true,
				autoHeight : true,
				labelAlign : 'right',
				items : [this.settlementPayToChange]
			},{
				xtype : 'fieldset',
				name:'projectInfo',
				title : '明细信息',
				collapsible : true,
				autoHeight : true,
				labelAlign : 'right',
				items : [this.settlementInfoPanel]
			},{
				xtype : 'hidden',
				name : 'settlementPayToChangeJson'
			},{
				xtype : 'hidden',
				name : 'taskName',
				value:"jiesuan"
			},{
				xtype : 'hidden',
				name : 'projectId',
				value:this.projectId
			}]
		})
		this.loadData({
			url : __ctxPath + '/web/getSettlementReviewerPay.do?id='+this.settlementReviewerPayId,
			method : "POST",
			preName : "settlementReviewerPay",
			root : 'data',
			success : function(response, options) {
				var respText = response.responseText;
				var alarm_fields = Ext.util.JSON.decode(respText);
				//查询提成明细
				var payStartDate = alarm_fields.data.payStartDate;
				var payEndDate = alarm_fields.data.payEndDate;
				if(payStartDate&&payEndDate){
					var store = this.settlementInfoPanel.gridPanel.getStore();
					store.reload({
						params : {
							orgId : this.organizationId,
							startDate:payStartDate,
							endDate:payEndDate
						}
					});
				}
			}
		});
		this.add(this.formPanel);
		this.doLayout();
	}
});
