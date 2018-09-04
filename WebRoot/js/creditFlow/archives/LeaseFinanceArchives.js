/*
 * 
 * @param {Object} _cfg
 * 项目保前归档
 */
LeaseFinanceArchives= Ext.extend(Ext.Panel, {
	        // 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				LeaseFinanceArchives.superclass.constructor.call(this, {
					    id : 'LeaseFinanceProjectarchives_' + this.projectId,
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
				__ctxPath + '/js/commonFlow/ExtUD.Ext.js',//客户信息 项目基本信息
				__ctxPath + '/js/selector/UserDialog.js',
				__ctxPath + '/js/creditFlow/common/EnterpriseShareequity.js',//股东信息
				
				__ctxPath + '/js/creditFlow/leaseFinance/UpdateLeaseFinanceMaterialsWin.js',//抵质押担保更新  for  leasefinance
				__ctxPath + '/js/creditFlow/leaseFinance/enterpriseBusiness/EnterpriseBusinessUI.js',//项目基本信息
				__ctxPath + '/js/creditFlow/guarantee/materials/SlEnterPriseProcreditMaterialsView.js',// 贷款材料
				__ctxPath + '/js/selector/RoleSelector.js',
				 __ctxPath + '/js/creditFlow/smallLoan/project/loadDataCommon.js',// 加载数据JS
				__ctxPath + '/js/creditFlow/common/EnterpriseShareequity.js',
				__ctxPath + '/js/creditFlow/smallLoan/contract/SeeThirdContractWindow.js',// 查看担保措施合同详情
				__ctxPath + '/js/creditFlow/finance/SlFundIntentViewVM.js',//款项信息
				__ctxPath + '/js/creditFlow/finance/SlActualToChargeVM.js',//计划收支费用
				__ctxPath + '/js/creditFlow/finance/detailView.js',
				__ctxPath + '/js/creditFlow/report/SlReportView.js',// 调查报告
				__ctxPath + '/js/creditFlow/finance/chargeDetailView.js',
				__ctxPath + '/js/creditFlow/guarantee/materials/SlEnterpriseProcreditMaterialsForm.js',// 贷款材料
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
				 	this.title="企业客户信息";
			        this.perMain = "";
				    if(this.oppositeType =="person_customer") 
				    {
					    this.mainInfo = new ExtUD.Ext.PeerPersonMainInfoPanel({
					    	isEditPerson : false,
					    	isHidden:true
					    });
					    this.title="个人客户信息";
				    } 
				    else if(this.oppositeType =="company_customer")
				    {
					    this.mainInfo = new ExtUD.Ext.PeerMainInfoPanel({
						projectId : this.projectId,
					    bussinessType:this.businessType,
					    isHidden:true,
					    isAllReadOnly:true,
						isEnterpriseShortNameReadonly : true,
						isEditEnterprise : false, // 产看客户资料
						isGudongDiseditable : true
					    });
				    };
				
			this.projectInfo = new EnterpriseBusinessProjectInfoPanel({
				   isDiligenceReadOnly : true,
				   isAllReadOnly:true,
				   isMineNameEditable:false
			    });
			//办理文件 材料
		    this.manageMaterialsView =new SlReportView({
		    	projectId : this.projectId,
		    	businessType : 'LeaseFinance',
		    	isHiddenColumn : false,
		    	isShowBorder : true,
		    	ReportTemplate:'LeaseFinanceManageFileReport',
		    	isHidden : false,
		    	hiddenUpLoad:true,// 隐藏上传按钮
		    	hiddenDownLoad:true,//隐藏下载按钮
		    	isHidden_report:false,
		    	isHiddenAffrim : true
		    });
			this.slProcreditMaterialsView = new SlEnterPriseProcreditMaterialsView({
				projectId : this.projectId,
				businessType:this.businessType,
				operationType:this.operationType,
				isHiddenAffrim : false,
				isEditAffrim : true
			});
			this.plArchivesMaterialsView = new PlArchivesMaterialsView({
				projectId:this.projectId,
				businessType : this.businessType,
				operationType : this.operationType,
				isHiddenAffrim:true,//是否已收到
				isHiddenRecive:false,//是否已提交
				isHidden_materials : false,
				isHiddenType :false,
				isAffrimEdit:true
			});
			   var companyId=this.companyId;
			    var businessType =this.businessType; 
				var projectId = this.projectId;
				if(this.flag=="new"){
					 this.projectArchives=new projectArchives({
								      readonly :false,
								      businessType : businessType,
									  projectId : projectId,
									  companyId :companyId
								    }).show();
					
				}else{
				  this.projectArchives= new projectArchives({
								      readonly :false,
								      projtoarchiveId :this.projtoarchiveId,
								      businessType : businessType,
									  projectId : projectId,
									  companyId:companyId
								    }).show();
					
					
				}		
				this.outPanel = new Ext.Panel({
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
								title : this.title,
								collapsible : true,
								autoHeight : true,
								items : [this.mainInfo]
							}, {
								title : '业务资料',
								items : [this.slProcreditMaterialsView]
										}, {
								xtype : 'fieldset',
								title : '办理文件',
								collapsible : true,
								autoHeight : true,
								items : [this.manageMaterialsView]
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
			    	 	url : __ctxPath + '/creditFlow/leaseFinance/project/getInfoFlLeaseFinanceProject.do?flProjectId='+this.projectId,//+'&flTaskId='+this.taskId,
						method : "POST",
						preName : ['enterprise', 'person', 'flLeaseFinanceProject','customerBankRelationPerson','enterpriseBank',"businessType"],
						root : 'data',
						success : function(response, options) {
					              var respText = response.responseText;  
							      var alarm_fields = Ext.util.JSON.decode(respText); 
							      this.getCmpByName('flLeaseFinanceProject.mineName').setValue(alarm_fields.data.mineName);
							      this.getCmpByName('appUsersOfA').clearInvalid();
							      
						}
				   	/*
						url : __ctxPath + '/creditFlow/getAllInfoCreditProject.do?taskId='+this.projectId+'&type='+this.businessType,
						method : "POST",
						preName : ['enterprise', 'person', 'slSmallloanProject',"businessType","enterpriseBank"],
						root : 'data',
						success : function(response, options) {		
							var respText = response.responseText;
							var alarm_fields = Ext.util.JSON.decode(respText);
							this.infosourceId=alarm_fields.data.slSmallloanProject.infosourceId
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
							this.getCmpByName('enterpriseBank.bankname').setValue(alarm_fields.data.enterpriseBank.bankname)
						   //  this.ownerCt.ownerCt.getCmpByName('comments').setValue(alarm_fields.data.comments);				
							
						}
					    */});
				this.formPanel = new Ext.FormPanel();
				this.formPanel.add(this.outPanel);
				this.add(this.formPanel);
				this.doLayout();

			}, 

		save : function() {
				$postForm({
						formPanel:this.formPanel,
						scope:this,
						url:__ctxPath + '/creditFlow/archives/savePlProjectArchives.do',
						params : {
							businessType : this.businessType,
							projectId : this.projectId
						},
						callback:function(fp,action){
							var tabs = Ext.getCmp('centerTabPanel');
				            tabs.remove('LeaseFinanceProjectarchives_' + this.projectId);
							var gridPanel = Ext.getCmp('PlProjectArchivesGrid');
							if (gridPanel != null) {
								gridPanel.getStore().reload();
							}
						}
					}
				);
			}//end of save
})
