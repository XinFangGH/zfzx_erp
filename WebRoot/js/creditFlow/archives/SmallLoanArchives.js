/*
 * 
 * @param {Object} _cfg
 * 项目保前归档
 */
SmallLoanArchives= Ext.extend(Ext.Panel, {
	        // 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				SmallLoanArchives.superclass.constructor.call(this, {
					    id : 'SmallLoanProjectarchives_' + this.projectId,
					     title : "项目文件归档",
						iconCls : 'btn-edit',
						tbar : this.toolbar,
						border : false,
						items : []
							
						})
				
			
				
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				var jsArr = [
				__ctxPath + '/js/creditFlow/guarantee/materials/SlEnterPriseProcreditMaterialsView.js',// 贷款材料
				
				__ctxPath + '/js/selector/RoleSelector.js',
				__ctxPath + '/js/commonFlow/ExtUD.Ext.js',
				 __ctxPath + '/js/creditFlow/smallLoan/project/loadDataCommon.js',// 加载数据JS
				__ctxPath + '/js/creditFlow/common/EnterpriseShareequity.js',
				__ctxPath + '/js/creditFlow/smallLoan/contract/SeeThirdContractWindow.js',// 查看担保措施合同详情
				__ctxPath + '/js/creditFlow/finance/SlFundIntentViewVM.js',//款项信息
				__ctxPath + '/js/creditFlow/finance/SlActualToChargeVM.js',//计划收支费用
				__ctxPath + '/js/creditFlow/finance/detailView.js',
				__ctxPath + '/js/creditFlow/finance/chargeDetailView.js',
				__ctxPath + '/js/creditFlow/repaymentSource/RepaymentSource.js',//第一还款来源
				__ctxPath + '/js/creditFlow/assuretenet/SlProcreditAssuretenetedForm.js',// 贷款准入原则
				  __ctxPath+'/js/creditFlow/archives/projectArchives.js',
				    __ctxPath+'/js/creditFlow/archives/PlArchivesMaterialsView.js'
				];
				$ImportSimpleJs(jsArr, this.constructPanel,this);
				this.toolbar = new Ext.Toolbar({
					items : ['->', {
								iconCls : 'btn-save',
								text : '保存',
								scope : this,
								handler : this.save
							}
							]
				})
			
			},
			constructPanel:function(){
				var title="企业客户信息";
				if (this.oppositeType =="person_customer") {
					this.projectInfo = new ExtUD.Ext.CreditLoanProjectInfoPanel({readOnly:true,isCPLX:true});
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
					 projectId : this.projectId,
					 bussinessType:this.businessType,
					 isEditEnterprise : false,
					 isReadOnly:true,
					 isNameReadOnly:true
				});
				}
				this.slProcreditMaterialsView = new SlEnterPriseProcreditMaterialsView({
				projectId : this.sprojectId,
				businessType:this.businessType,
				isHiddenAffrim : false,
				isEditAffrim : true
			});
			this.plArchivesMaterialsView = new PlArchivesMaterialsView({
				projectId : this.sprojectId,
				businessType:this.businessType,
				isHiddenAffrim : false,
				isEditAffrim : true,
				isHidden_materials:false,
				isAffrimEdit:true
			});
			   var companyId=this.companyId;
			    var businessType =this.businessType; 
				var projectId = this.projectId;
			/*	if(this.flag=="new"){
					 this.projectArchives=new projectArchives({
								      readonly :false,
								      businessType : businessType,
									  projectId : projectId,
									  companyId :companyId
								    }).show();
					
				}else{*/
				  this.projectArchives= new projectArchives({
								      readonly :false,
								      projtoarchiveId :this.projtoarchiveId,
								      businessType : businessType,
									  projectId : projectId,
									  companyId:companyId
								    }).show();
					
					
				//}		
				this.outPanel = new Ext.FormPanel({
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
								title : '项目信息',
								bodyStyle : 'padding-left:8px',
								name:'projectInfo',
								items : [this.projectInfo]
							},
							{
								xtype : 'fieldset',
								title : title,
								collapsible : true,
								autoHeight : true,
								items : [this.perMain]
							}, {
								title : '贷款资料',
								items : [this.slProcreditMaterialsView]
							}, {
								title : '担保措施',
								items : [new DZYMortgageView({
									projectId : this.sprojectId,
									titleText : '抵质押物',
									businessType : this.businessType,
									isHiddenAddContractBtn : true,
									isHiddenDelContractBtn : true,
									isHiddenEdiContractBtn : true,
									isHiddenRelieve : true,
									isblHidden : true,
									isHiddenAddBtn : true,
									isHiddenDelBtn : true,
									isHiddenEdiBtn : true,
									isfwEdit : false,
									isSeeContractHidden : false,
									isKS : true,
									isSignHidden : false,
									isblEdit : false,
									isHiddenGDBtn : true,
									isgdHidden : false,
									isgdEdit : true,
									isRelieveEdit : false
								}), new BaozMortgageView({
									projectId : this.sprojectId,
									titleText : '保证担保',
									businessType : this.businessType,
									isHiddenAddContractBtn : true,
									isHiddenDelContractBtn : true,
									isHiddenEdiContractBtn : true,
									isHiddenRelieve : false,
									isblHidden : true,
									isHiddenAddBtn : true,
									isHiddenDelBtn : true,
									isHiddenEdiBtn : true,
									isfwEdit : false,
									isSeeContractHidden : false,
									isKS : true,
									isSignHidden : false,
									isblEdit : false,
									isblHidden : false,
									isHiddenGDBtn : true,
									isgdHidden : false,
									isgdEdit : true,
									isRelieveEdit : true
								})]
							}, {
								title : '归档材料',
								items : [this.plArchivesMaterialsView]
							},{
								xtype : 'fieldset',
								title : '项目归档',
								items : [this.projectArchives]
							}
							]
		
						});
				   this.loadData({
						url : __ctxPath + '/creditFlow/getAllInfoCreditProject.do?fundProjectId='+this.projectId+'&type='+this.businessType,
						method : "POST",
						preName : ['enterprise', 'person', 'slSmallloanProject',"businessType","enterpriseBank"],
						root : 'data',
						success : function(response, options) {		
							var respText = response.responseText;
							var alarm_fields = Ext.util.JSON.decode(respText);
							if(typeof(alarm_fields.data.enterpriseBank) != 'undefined' && this.getCmpByName('enterpriseBank.areaName') !=null) {
					this.getCmpByName('enterpriseBank.areaName').setValue(alarm_fields.data.enterpriseBank.areaName);
				}
						/*	this.infosourceId=alarm_fields.data.slSmallloanProject.infosourceId
							var st = this.getCmpByName('infosourceName').getStore();
							st.on("load", function() {
								this.getCmpByName('infosourceName').setValue(this.infosourceId)
								
							},this)
							var appUsers= alarm_fields.data.slSmallloanProject.appUsers;
							var appUserId= alarm_fields.data.slSmallloanProject.appUserId;
							if(""!=appUserId &&  null!=appUserId){
							
							   this.getCmpByName('degree').setValue(appUserId);
							   this.getCmpByName('degree').setRawValue(appUsers);
							   this.getCmpByName('degree').nextSibling().setValue(appUserId);
							}
							this.getCmpByName('enterpriseBank.bankname').setValue(alarm_fields.data.enterpriseBank.bankname)*/
						   //  this.ownerCt.ownerCt.getCmpByName('comments').setValue(alarm_fields.data.comments);				
							
						}
					    });
				//this.formPanel = new Ext.FormPanel();
				//this.formPanel.add(this.outPanel);
				this.add(this.outPanel);
				this.doLayout();

			}, 

		save : function() {
				$postForm({
						formPanel:this.outPanel,
						scope:this,
						url:__ctxPath + '/creditFlow/archives/savePlProjectArchives.do',
						params : {
							businessType : this.businessType,
							projectId : this.projectId
						},
						callback:function(fp,action){
							var tabs = Ext.getCmp('centerTabPanel');
				            tabs.remove('SmallLoanProjectarchives_' + this.projectId);
							var gridPanel = Ext.getCmp('PlProjectArchivesGrid');
							if (gridPanel != null) {
								gridPanel.getStore().reload();
							}
						}
					}
				);
			}//end of save
})
