/**
 * @author
 * @createtime
 * @class SlCompanyMainForm
 * @extends Ext.Window
 * @description SlCompanyMain表单
 * @company 北京金智万维软件有限公司
 */
FundsToPromoteWindow = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		FundsToPromoteWindow.superclass.constructor.call(this, {
					items : this.formPanel,
					id : 'ZiJinTuiJie'+this.projectId,
					autoScroll : true,
					modal : true,
					maximizable : true,
					title : '资金推介'+this.projectName
				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		this.projectInfo = new ExtUD.Ext.ProjectInfoPanel({
			isDiligenceReadOnly : true,
			isAllReadOnly:true
		});
		this.perMain = "";
		var title="企业客户信息";
		if (this.oppositeType =="person_customer") {
			this.perMain = new ExtUD.Ext.PeerPersonMainInfoPanel({
				isEditPerson : false
			});
			title="个人客户信息";
		} else if(this.oppositeType =="company_customer"){
			    this.perMain = new ExtUD.Ext.PeerMainInfoPanel({
					 projectId : this.projectId,
					 bussinessType:this.businessType,
					 isEditEnterprise : false
				});
		}
		//款项信息
		this.projectInfoFinance= new ExtUD.Ext.ProjectInfoFinancePanel({
			isAllReadOnly : true,
		 	projectId:this.projectId,
			idDefinition:'tongyongliucheng' 
		});
		var me = this;
		this.fundsToPromotePanel=new FundsToPromotePanel({projectId:this.projectId});
		this.fundsToPromoteForm = new Ext.form.FormPanel({
			header : false,
			//anchor : '96%',
			frame : true,
			border : false,
			items : [{
				xtype : 'radio',
				boxLabel : '邮件推介',
				fieldLabel : '推介方式',
				checked :true
			},{
				xtype : 'textfield',
				fieldLabel :'推介标题',
				name : 'fundsToPromote.title',
				allowBlank : false,
				blankText : "推介标题不能为空，请正确填写!",
				anchor : '96%'
			},{
				xtype : "combo",
				triggerClass : 'x-form-search-trigger',
				hiddenName : "investPersonNames",
				editable : false,
				fieldLabel : "投资客户",
				blankText : "投资客户不能为空，请正确填写!",
				allowBlank : false,
				readOnly : this.isAllReadOnly,
				anchor : "96%",
				scope :this,
				onTriggerClick : function() {
					var combox=this;
					var selectPerson=function(perids,pernames){
						combox.setValue(pernames)
						combox.nextSibling().setValue(perids)
					}
					selectPromotePerson(selectPerson)
				}
			},{
				xtype : 'hidden',
				name : 'investPersonIds'
			},{
				xtype : 'textarea',//htmleditor 
				fieldLabel : "邮件内容",
				allowBlank : false,
				anchor : "96%",
				blankText : '邮件内容不能为空,请正确填写',
				maxLength : 1000,
				name : 'fundsToPromote.promoteContent'
			},{
				xtype : "combo",
				anchor : "96%",
				triggerClass : 'x-form-search-trigger',
				hiddenName : "promotenames",/*
				displayField : 'itemName',
				valueField : 'itemId',
				triggerAction : 'all',
				multiSelect : false,*/
				//allowBlank : false,
				editable : false,
				//blankText : "推介附件不能为空，请正确填写!",
				fieldLabel : "推介附件",
				onTriggerClick : function() {
					var combox=this;
					var selectPromote=function(ids,names){
						combox.setValue(names)
						combox.nextSibling().setValue(ids)
					}
					selectPromotePackage(me.projectId,selectPromote)
				}
			},{
				xtype : 'hidden',
				name : 'promoteids'
			},{
				xtype : 'hidden',
				name : 'fundsToPromote.projectId',
				value : this.projectId
			}],
			buttonAlign : 'center',
			buttons : [{
						text : '立即推介',
						iconCls : 'menu-zmexport',
						scope : this,
						handler : this.save
					}, {
						text : '取消',
						iconCls : 'btn-cancel',
						scope : this,
						handler : this.cancel
					}]
		});
		this.formPanel = new Ext.Panel({//Form
			modal : true,
			labelWidth : 100,
			buttonAlign : 'center',
			layout : 'form',
			border : false,
			frame :true,
			defaults : {
				anchor : '100%',
				xtype : 'fieldset',
				labelAlign : 'left',
				collapsible : true,
				autoScroll : true
			},
			items : [{
				xtype : 'fieldset',
				title : '贷款控制信息 ',
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
			}, {
				xtype : 'fieldset',
				title :'资金款项计划',
				bodyStyle : 'padding-left:0px',
				collapsible : true,
				labelAlign : 'right',
				autoHeight : true,
				items : [this.projectInfoFinance]
			}, {
				xtype : 'fieldset',
				title : '资金推介记录 ',
				collapsible : true,
				autoHeight : true,
				labelAlign : 'right',
				items : [this.fundsToPromotePanel]
			}, {
				xtype : 'fieldset',
				title : '资金推介 ',
				collapsible : true,
				autoHeight : true,
				labelAlign : 'right',
				items : [this.fundsToPromoteForm]
			}]
		});
		// 加载表单对应的数据
		
		if (this.projectId != null && this.projectId != 'undefined') {
			
			this.formPanel.loadData({
				url : __ctxPath + '/creditFlow/getInfoCreditProject.do?taskId='
					+ this.projectId + '&type=SmallLoan',
				preName : ['enterprise', 'person', 'slSmallloanProject',
						'businessType'],
				root : 'data',
				scope : this,
				success : function(resp, options) {
					
					var result = Ext.decode(resp.responseText);
					this.getCmpByName('degree').setRawValue(result.data.slSmallloanProject.appUsers);
					this.getCmpByName('projectMoney1').setValue(this.projectMoney);
				}
			});
		}

	},// end of the initcomponents
	cancel : function() {
		this.fundsToPromoteForm.getForm().reset();
	},
	save : function(){
		$postForm({
			formPanel : this.fundsToPromoteForm,
			scope : this,
			url : __ctxPath+ '/creditFlow/finance/saveInfoFundsToPromote.do?projectId='+this.projectId,
			method : 'post',
			callback : function(fp, action) {
				if(this.fundsToPromotePanel!=null||typeof(this.fundsToPromotePanel)!="undefined"){
					this.fundsToPromotePanel.gridPanel.getStore().reload();
					this.fundsToPromoteForm.getForm().reset();
				}
			}
		});
	}

});
