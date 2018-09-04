/**
 * @author 
 * @class obProjectInfo
 * @description 项目详细信息页面
 * @extends Ext.Panel
 */
obProjectInfo = Ext.extend(Ext.Panel, {
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		obProjectInfo.superclass.constructor.call(this, {
			iconCls : 'btn-detail',
			autoScroll : true,
			height : document.body.clientHeight - 280
		});
	},
	initUIComponents : function() {
			var jsArr = [
				__ctxPath + '/js/commonFlow/ExtUD.Ext.js',
				__ctxPath + '/js/creditFlow/finance/SlActualToChargeVM.js',// 经办费用清单
				__ctxPath + '/js/creditFlow/smallLoan/project/loadDataCommon.js'// 加载页面表单数据JS
		];
		$ImportSimpleJs(jsArr, this.constructPanel, this);

	},// 初始化组件
	constructPanel : function() {
		this.projectId = this.record.data.projectId;
		this.oppositeType = this.record.data.oppositeType;
		this.operationType = this.record.data.operationType;
		//this.businessType=this.businessType;
		this.customerInfo = "";
		// 项目基本信息
		this.projectInfo = new ExtUD.Ext.McroLoanProjectInfoPanel({
			isAllReadOnly : true,
			isDiligenceReadOnly : true
		});
		// 客户信息
		var title="企业客户信息";
		if (this.oppositeType =="person_customer") {
			this.perMain = new ExtUD.Ext.PeerPersonMainInfoPanel({
				 projectId : this.projectId,
				 isAllReadOnly :true,
				 isNameReadOnly:false,
				 isSpouseReadOnly: true,
				 isHidden : true,
				 isEditPerson : true,
				 isHiddenCustomerDetailBtn:true
			});
			title="个人客户信息";
		} else if(this.oppositeType =="company_customer"){
				 this.personHidden=true,
			     this.perMain = new ExtUD.Ext.PeerMainInfoPanel({
				 projectId : this.projectId,
				 bussinessType:this.businessType,
				 isAllReadOnly : true,
				 isNameReadOnly:false,
				 isHidden : true,
				 isEditEnterprise : true,
				 isHideGudongInfo:true,
				 isHiddenCustomerDetailBtn:true
			});
		}
		// 资金款项信息
		this.projectInfoFinance = new ExtUD.Ext.newProjectInfoFinancePanel({
			isAllReadOnly : true,
			isProjectMoneyReadOnly:true,//判断放款金额是否只读
			projectId : this.projectId,
			idDefinition:'xiangmuxiangqingsee',
			isHiddencalculateBtn:true
		});
		// 经办费用清单
		this.slActualToCharge = new SlActualToChargeVM({
			projId : this.projectId,
			isHidden : true,
			businessType : 'SmallLoan',
			isHiddenTitle : true
		});
		this.formPanel = new Ext.form.FormPanel({
			modal : true,
			buttonAlign : 'center',
			layout : 'form',
			border : false,
			frame : true,
			defaults : {
				anchor : '100%',
				xtype : 'fieldset',
				columnWidth : 1,
				collapsible : true,
				autoHeight : true
			},
			labelAlign : "right",
			items : [{
					title : "项目基本信息-" + this.record.data.projectName,
					items : [this.projectInfo]
				},{
					xtype : 'fieldset',
					title :title,
					bodyStyle : 'padding-left:0px',
					collapsible : true,
					labelAlign : 'right',
					autoHeight : true,
					items : [this.perMain]
				},{
					xtype : 'fieldset',
					title : '配偶信息',
					bodyStyle : 'padding-left:0px',
					collapsible : true,
					labelAlign : 'right',
					name : 'spousePanel',
					autoHeight : true,
					hidden:this.personHidden,
					items : [new SpousePanel({spouseHidden:true})]
				},{
					title : '资金款项信息',
					bodyStyle : 'padding-top:5px',
					items : [this.projectInfoFinance, {
								xtype : 'panel',
								border : false,
								bodyStyle : 'margin-bottom:5px',
								html : '手续费用收取清单'
							},this.slActualToCharge]
				}
			]
		});
		this.loadData({
			url : __ctxPath + '/project/getInfoSlSmallloanProject.do?slProjectId='+this.projectId,
			preName : ['enterprise', 'person', 'slSmallloanProject',
					'businessType',"enterpriseBank","spouse","financeInfo"],
			root : 'data',
			success : function(response, options) {
				var respText = response.responseText;
				var alarm_fields = Ext.util.JSON.decode(respText);
				var appUsers= alarm_fields.data.slSmallloanProject.appUsers;
				var appUserId= alarm_fields.data.slSmallloanProject.appUserId;
				if(""!=appUserId &&  null!=appUserId){
				   this.getCmpByName('degree').setValue(appUserId);
				   this.getCmpByName('degree').setRawValue(appUsers);
				   this.getCmpByName('degree').nextSibling().setValue(appUserId);
				}
				this.getCmpByName('projectMoney1').setValue(Ext.util.Format.number(alarm_fields.data.slSmallloanProject.projectMoney,'0,000.00'));
				if(typeof(alarm_fields.data.enterpriseBank)!="undefined"&&this.getCmpByName('enterpriseBank.areaName')!=null){
					this.getCmpByName('enterpriseBank.areaName').setValue(alarm_fields.data.enterpriseBank.areaName);
				}
				if(alarm_fields.data.slSmallloanProject.operationType=='MicroLoanBusiness'){
					if(alarm_fields.data.person!=null && alarm_fields.data.person.marry==317){
						this.getCmpByName('spousePanel').show()
					}else{
						this.getCmpByName('spousePanel').hide()
					}
				}else{
					this.getCmpByName('spousePanel').hide()
					if(this.oppositeType =="person_customer"){
					if(alarm_fields.data.person!=null && alarm_fields.data.person.marry==317){
						this.getCmpByName('spousePanel').show()
					}else{
						this.getCmpByName('spousePanel').hide()
					}
				}
				}
				fillData(this, alarm_fields,'xiangmuxiangqingsee');
			}
		});
		this.add(this.formPanel);
		this.doLayout();
	}// 初始化UI结束
});