/**
 * @author
 * @createtime
 * @class SmallLoanAlterAccrualEditView
 * @extends Ext.Window
 * @description 利率变更详情
 * @company 智维软件
 */
SlAlterAccrualRecordSeeInfo = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 调用父类构造
		this.initComponents();
		
		SlAlterAccrualRecordSeeInfo.superclass.constructor.call(this, {
			region : 'center',
			layout : 'anchor',
			autoScroll:true,
			height : document.body.clientHeight - 200,
			items : [this.formPanel]
		});
		
		
	},
	initComponents : function() {
		var projectId = this.projectId;
		var slAlteraccrualRecordId = this.record.data.slAlteraccrualRecordId;
		this.fundProjectId=this.record.data.projectId
	var title="企业客户信息";
		if (this.oppositeType =="person_customer") {
			//项目基本信息
		this.projectInfo = new ExtUD.Ext.CreditLoanProjectInfoPanel({readOnly:true,product:true,isCPLX:true});
	
		//个人客户信息
		this.perMain = new ExtUD.Ext.CustomerInfoFastPanel({
			isEditPerson : false,
			isAllReadOnly:true,
			isRead:true,
			isHidden:true,
			isSpouseReadOnly: true,
			isNameReadOnly:true,
			isHiddenCustomerDetailBtn:false
		});
			title="个人客户信息";
		} else if(this.oppositeType =="company_customer"){
			this.projectInfo = new ExtUD.Ext.McroLoanProjectInfoPanel({
			isDiligenceReadOnly : true,
			isAllReadOnly:true
		});
			     this.perMain = new ExtUD.Ext.PeerMainInfoPanel({
				 projectId : this.sprojectId,
				 bussinessType:this.baseBusinessType,
				 isEditEnterprise : false,
				 isReadOnly:true,
				 isNameReadOnly:true
			});
		}
		var DeferApplyInfoPanel = new FinanceAlterAccrualPanel({
			surplusnotMoney : this.surplusnotMoney,
			payintentPeriod : 4,
			isAllReadOnly:true,
			idDefinition:'alterAccrual',
			projectId : projectId,
			slAlterAccrualRecorddata : null
		})
		this.alterAccrualSlFundIntent = new AlterAccrualSlFundIntent({
			object : DeferApplyInfoPanel,
			projId : projectId,
			preceptId: this.fundProjectId,
			titleText : '放款收息表',
			isHidden : true,
			businessType : "SmallLoan",
			isThisAlterAccrualRecordId :slAlteraccrualRecordId,
			isUnLoadData : false,
			isThisAlterAccrualRecord : 'yes'
		})
		this.formPanel=new Ext.FormPanel({
			autoWidth : true,
			items : [{
				xtype : 'fieldset',
				name:'projectInfo',
				title : '项目基本信息 ',
				collapsible : true,
				autoHeight : true,
				labelAlign : 'right',
				items : [this.projectInfo]
			}, {
				xtype : 'fieldset',
				title :title,
				bodyStyle : 'padding-left:0px',
				collapsible : true,
				labelAlign : 'right',
				autoHeight : true,
				items : [this.perMain]
			},{
				title : '历史财务信息',
				xtype : 'fieldset',
				autoHeight : true,
				name : "historyfinance",
				collapsible : true,
				autoWidth : true,
				bodyStyle : 'padding-left:8px',
				items : [new SlFundIntentViewVM({
					isHiddenOperation : false,
					projectId : projectId,
					businessType : "SmallLoan",
					isHiddenAddBtn : true,
					isHiddenDelBtn : true,
					isHiddenCanBtn : true,
					isHiddenResBtn : true,
					isHiddenautocreateBtn:true,
					isHiddenResBtn1:true,
					isFinbtn:true,
					isHidden1 : true,
					isThisAlterAccrualRecordId : slAlteraccrualRecordId,
					isUnLoadData : false,
					isThisAlterAccrualRecord : 'no'
				})]
			}, {
				title : '变更利率登记信息',
				xtype : 'fieldset',
				autoHeight : true,
				autoWidth :true,
				collapsible : true,
				name : "earlyRepaymentfinance",
				bodyStyle : 'padding-left:8px',
				items : [DeferApplyInfoPanel,
						this.alterAccrualSlFundIntent
						]
			}/*,{
				xtype : 'fieldset',
				title : '利率变更审批表',
        		bodyStyle : 'padding-left:0px',
				collapsible : true,
				labelAlign : 'right',
				autoHeight : true,
				autoWidth : true,
        		items : [
					new LetterAndBookView({
						projectId:projectId,
						businessType:"SmallLoan",
						LBTemplate:'slAlteraccrualRecordVerification',
						isHidden:true
					})
				]
			}*/]
		})
		this.loadData({
			url : __ctxPath + '/smallLoan/finance/getInfoLoanedSlAlterAccrualRecord.do?sProjectId='+projectId+'&type=SmallLoan'+'&slAlteraccrualRecordId='+slAlteraccrualRecordId,
			method : "POST",
			preName : ['slSmallloanProject','slAlterAccrualRecord','enterprise', 'person',"enterpriseBank","spouse","payintentPeriod"],
			root : 'data',
			success : function(response, options) {
				var respText = response.responseText;
				var alarm_fields = Ext.util.JSON.decode(respText);
				if(typeof(alarm_fields.data.enterpriseBank) != 'undefined') {
					this.getCmpByName('enterpriseBank.areaName').setValue(alarm_fields.data.enterpriseBank.areaName);
				}
				
			}
		});
	}
});
