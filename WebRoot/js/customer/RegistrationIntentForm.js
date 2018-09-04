/**
 * @author
 * @createtime
 * @class InvestPersonForm
 * @extends Ext.Window
 * @description InvestPerson表单
 * @company 智维软件
 */
RegistrationIntentForm = Ext.extend(Ext.Window, {
	// 构造函数
	isHidden : false,
	isFlow:false,
	constructor : function(_cfg) {
		this.projectId = _cfg.projectId;
		this.projectMoney = _cfg.projectMoney;
		this.bussinessType = _cfg.bussinessType;
		if (_cfg.isHidden) {
			this.isHidden = _cfg.isHidden;
		}
		if (_cfg.isFlow) {
			this.isFlow = _cfg.isFlow;
		}
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		
		RegistrationIntentForm.superclass.constructor.call(this, {
					id : 'RegistrationIntentForm',
					layout : 'fit',
					items : this.formPanel,
					modal : true,
					autoHeight : true,
					width : (window.screen.width-100)*0.9,
					maximizable : true,
					title : '投资用户详细信息',
					buttonAlign : 'center',
					buttons : [{
								text : '保存',
								iconCls : 'btn-save',
								scope : this,
								handler : this.save
							}, {
								text : '资金匹配成功',
								iconCls : 'btn-reset',
								scope : this,
								handler : this.mateSuccess
							}]
				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		
		/*this.projectInfoFinance=new ExtUD.Ext.ProjectInfoFinancePanel({
						isAllReadOnly : this.isHidden,
					 	projectId:this.projectId,
						idDefinition:'zijinpipei' 
					})*/
		/*this.projectInfoFinance= new ExtUD.Ext.newProjectInfoFinancePanel({
			isAllReadOnly : this.isHidden,
			isHiddencalculateBtn:true,
		 	projectId:this.projectId,
		 	width:1100,
			idDefinition:'tongyongliucheng' 
		});*/
					
		//平台资金款项
		this.projectInfoFinance= new platFormFund({
			isAllReadOnly:false,
			isStartDateReadOnly:false,
			isHiddencalculateBtn:true,
		 	projectId:this.projectId,
		 	name:'platFormfinanceInfoFieldset',
		 	isFundMatch:this.isFundMatch,
			idDefinition:'tongyongliucheng2'
		});
		
		this.investPersonInfoPanelView = new InvestPersonInfoPanelView({
			projectId : this.projectId,
//			width:1100,
			isHidden : this.isHidden,
			bussinessType : "SmallLoan",
			object:this.projectInfoFinance,
			isFlow:this.isFlow
		});
		
		/*this.slFundIntentFapView=new SlFundIntentFapView({
			projectId : this.projectId,
			object : this.projectInfoFinance,
			width : 1000,
			titleText : '放款收息表',
			//isHidden : this.isHidden,
			calcutype : 1 ,    
			isHiddenExcel:true,//贷款
			inverstPersonId:null,
			businessType : 'SmallLoan'
			
		})*/
		this.formPanel = new Ext.form.FormPanel({
			layout : 'form',
			bodyStyle : 'padding:10px',
			border : false,
			autoHeight:true,
//			height :450,
			autoScroll : true,
			frame : true,
			defaults : {
				anchor : '96%,96%'
			},
			// defaultType : 'textfield',
			items : [this.projectInfoFinance, this.investPersonInfoPanelView/*,this.slFundIntentFapView*/,{
						xtype : 'hidden',
						name : 'platFormBpFundProject.projectId',
						value : this.projectId
					},{
						xtype : 'hidden',
						name : 'investData1'
					},{
						xtype : 'hidden',
						name : 'slFundIntentData1'
					}]/*,
					listeners : {
						scope : this,
						afterrender : function(obj) {
							this.getCmpByName('platFormBpFundProjectMoney').setValue(this.projectMoney);
						}
					}*/
		});
		if (this.projectId != null && this.projectId != 'undefined') {
			this.formPanel.loadData({
//				url : __ctxPath + '/project/getSlSmallloanProject.do?projectId='+ this.projectId,
				url : __ctxPath + '/fund/getBpFundProject.do?projectId='+ this.projectId,
				root : 'data',
				preName : ['platFormBpFundProject','aa','bb'],
				success : function(response, options) {
					var respText = response.responseText;
					var alarm_fields = Ext.util.JSON.decode(respText);
					if(alarm_fields.data.platFormBpFundProject){
						if(alarm_fields.data.platFormBpFundProject.platFormJointMoney){
							this.getCmpByName('platFormBpFundProjectMoney').setValue(Ext.util.Format.number(alarm_fields.data.platFormBpFundProject.platFormJointMoney,'0,000.00'));
						}
						fillFundData(this,alarm_fields,'tongyongliucheng2');
					}
					/*if(alarm_fields.data.projectMoney){
						this.getCmpByName('projectMoney1').setValue(Ext.util.Format.number(alarm_fields.data.projectMoney,'0,000.00'))
					}
					fillData(this,alarm_fields,'tongyongliucheng');*/
				}
			});
		}
		

	},// end of the initcomponents
	
	
	/**
	 * 保存记录
	 */
	save : function() {
		 //投资人信息
        var investDatas = "";
        var investPanel = this.investPersonInfoPanelView.get(1);
        investDatas = getGridDate1(investPanel);
//		var slFundIntentPanel=this.slFundIntentFapView.get(1);  
//		var slFundIntentDatas=this.slFundIntentFapView.getGridDate()
		$postForm({
					formPanel : this.formPanel,
					scope : this,
					method : 'post',
					url : __ctxPath + '/fund/saveBpFundProject.do',
//					url : __ctxPath + '/project/saveSlSmallloanProject.do',
//					params : {investInfo :investDatas, slFundIntentDatas:slFundIntentDatas},
					params : {investInfo :investDatas},
					callback : function(fp, action) {
						investPanel.getStore().reload();
						var params1={'flag1': 1};
						/*slFundIntentPanel.getStore().on('beforeload', function(gridstore, o) {
							
							Ext.apply(o.params, params1);
						});
						slFundIntentPanel.getStore().reload()*/
						this.close();
					}
				});
	} ,// end of save
	/*
	 * 资金匹配成功
	 * */
	mateSuccess : function(){
		var investDatas = "";
        var investPanel = this.investPersonInfoPanelView.get(1);
        investDatas = getGridDate1(investPanel);
        this.formPanel.getCmpByName('investData1').setValue(investDatas);
		/*var slFundIntentPanel=this.slFundIntentFapView.get(1);  
		var slFundIntentDatas=this.slFundIntentFapView.getGridDate()
		this.formPanel.getCmpByName('slFundIntentData1').setValue(slFundIntentDatas)*/
		$postForm({
			formPanel : this.formPanel,
			scope : this,
			url : __ctxPath + '/project/startGoThroughFormalitiesFlowSlSmallloanProject.do?runId='+this.runId,
			callback:function(fp,action){
				if (this.gridPanel != null) {
					this.gridPanel.getStore().reload();
				}
				this.close();
			}
		});
	}
});