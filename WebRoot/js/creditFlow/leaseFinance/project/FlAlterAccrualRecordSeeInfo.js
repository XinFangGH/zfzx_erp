/**
 * @author
 * @createtime
 * @class FlAlterAccrualRecordSeeInfo
 * @extends Ext.Window
 * @description 融资租赁--利率变更详情
 * @company 智维软件
 */
FlAlterAccrualRecordSeeInfo = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 调用父类构造
		this.initComponents();
		FlAlterAccrualRecordSeeInfo.superclass.constructor.call(this, {
			region : 'center',
			layout : 'anchor',
			autoScroll:true,
			height : document.body.clientHeight - 200,
			items : [this.formPanel]
		});
	},
	initComponents : function() {
		var projectId = this.record.data.projectId;
		var slAlteraccrualRecordId = this.record.data.slAlteraccrualRecordId;
		this.perMain = "";
		this.projectInfo = new EnterpriseBusinessProjectInfoPanel({
			   isDiligenceReadOnly : true,
			   isMineNameEditable:false,
			   isAllReadOnly:true
		});
	
		var title="企业客户信息";
		if (this.oppositeType =="person_customer") {
			this.perMain = new ExtUD.Ext.PeerPersonMainInfoPanel({
				isEditPerson : false,
				isReadOnly:true,
				isSpouseReadOnly: true,
				isNameReadOnly:true,
				isHideGudongInfo:true
			});
			title="个人客户信息";
		} else if(this.oppositeType =="company_customer"){
			
			     this.perMain = new ExtUD.Ext.PeerMainInfoPanel({
				 projectId : projectId,
				 bussinessType:"LeaseFinance",
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
			titleText : '放款收息表',
			isHidden : true,
			businessType : "LeaseFinance",
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
					businessType : "LeaseFinance",
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
			},{
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
						businessType:"LeaseFinance",
						LBTemplate:'slAlteraccrualRecordVerification',
						isHidden:true
					})
				]
			}]
		})
		this.loadData({
			url : __ctxPath + '/smallLoan/finance/flGetInfoLoanedSlAlterAccrualRecord.do?projectId='+projectId+'&type=LeaseFinance&loanedTypeId='+slAlteraccrualRecordId+'&loanedTypeKey=slAlteraccrual',
			method : "POST",
			preName : ['flLeaseFinanceProject','slAlterAccrualRecord','enterprise', 'person',"enterpriseBank","spouse","payintentPeriod"],
			root : 'data',
			success : function(response, options) {
				var respText = response.responseText;
				var alarm_fields = Ext.util.JSON.decode(respText);
			    this.ownerCt.ownerCt.getCmpByName('comments').setValue(alarm_fields.data.comments);				
				this.getCmpByName('projectMoney1').setValue(Ext.util.Format.number(this.surplusnotMoney,'0,000.00'))
				if(typeof(alarm_fields.data.enterpriseBank) != 'undefined') {
					this.getCmpByName('enterpriseBank.areaName').setValue(alarm_fields.data.enterpriseBank.areaName);
				}
				var payintentPeriod=alarm_fields.data.payintentPeriod
				if(null!=alarm_fields.data.slAlterAccrualRecord.alterpayintentPeriod){
					this.getCmpByName('alterpayintentPeriod').setValue(payintentPeriod+'.'+alarm_fields.data.slAlterAccrualRecord.alterpayintentPeriod)
					if(payintentPeriod==0){
						this.getCmpByName('alterpayintentPeriod').setRawValue('第'+alarm_fields.data.slAlterAccrualRecord.alterpayintentPeriod+'期')
					}else{
						this.getCmpByName('alterpayintentPeriod').setRawValue('展期第'+alarm_fields.data.slAlterAccrualRecord.alterpayintentPeriod+'期')
					}
				}
				fillDataAlterAccrual(this,alarm_fields.data.slAlterAccrualRecord,'alterAccrual');
			}
		});
	}
});
