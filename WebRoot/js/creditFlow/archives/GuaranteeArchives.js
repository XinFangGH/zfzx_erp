/*
 * 
 * @param {Object} _cfg
 * 项目保前归档
 */
GuaranteeArchives= Ext.extend(Ext.Panel, {
//			projectId : '$!projectId',
//			businessType:'$!businessType',
//			oppositeType : '$!oppositeType',
		//	autoHeight:true,
	//		safeLevel:1,
	//		autoScroll  :true,
	        // 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				GuaranteeArchives.superclass.constructor.call(this, {
					   id : 'GuaranteeProjectarchives_' + this.projectId,
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
				 __ctxPath + '/js/commonFlow/ExtUD.Ext.js',
				 __ctxPath + '/js/creditFlow/common/EnterpriseShareequity.js',
				 __ctxPath+'/js/creditFlow/guarantee/enterpriseBusiness/enterpriseBusinessUI.js',
                 __ctxPath+'/js/creditFlow/guarantee/enterpriseBusiness/Approval.js',
                 __ctxPath+'/js/creditFlow/guarantee/enterpriseBusiness/bankInfoPanel.js',
                  	__ctxPath + '/js/creditFlow/finance/gt_FundIntent_formulate_editGrid.js',//银行放开收息等信息

                 __ctxPath+'/js/creditFlow/guarantee/enterpriseBusiness/guaranteeModel/BankGuaranteeMoney.js',
                 __ctxPath+'/js/creditFlow/guarantee/enterpriseBusiness/guaranteeModel/CustomerGuaranteeMoney.js',
                 __ctxPath+'/js/creditFlow/guarantee/enterpriseBusiness/guaranteeModel/GuaranteeMoney.js',
                 __ctxPath+'/js/creditFlow/finance/selectAccountlForm.js',
                 __ctxPath + '/js/creditFlow/finance/SlActualToChargeVM.js',//实际收费项目
				 __ctxPath + '/js/creditFlow/finance/GuaranteeSlFundIntentViewVM.js',
			     __ctxPath + '/publicmodel/uploads/js/cs_picViewer.js',
				 __ctxPath + '/js/creditFlow/finance/detailView.js',
				 __ctxPath + '/js/creditFlow/finance/chargeDetailView.js',
                 __ctxPath + '/js/creditFlow/guarantee/materials/SlEnterPriseProcreditMaterialsView.js',// 贷款材料
			   	// __ctxPath + '/js/creditFlow/smallLoan/contract/CreditContractView.js',// 贷款合同
			  	 __ctxPath + '/js/creditFlow/report/SlReportView.js',// 贷款调查报告
			     __ctxPath + '/js/creditFlow/report/SlRiskReportView.js',// 综合分析报告
			     __ctxPath + '/js/creditFlow/guarantee/enterpriseBusiness/CustomerFiles.js',//项目文件
			     __ctxPath+'/js/creditFlow/guarantee/archive/GlReportArchiveView.js',//尽职调查报告、综合分析报告归档
			     __ctxPath+'/js/creditFlow/guarantee/archive/GlContractArchiveView.js',//合同归档
			     __ctxPath+'/js/creditFlow/guarantee/archive/GlOtherArchiveView.js',//其他归档
			      __ctxPath+'/js/creditFlow/guarantee/enterpriseBusiness/EnterpriseEvaluation.js',
				 __ctxPath + '/js/creditFlow/assuretenet/SlProcreditAssuretenetedForm.js',// 贷款准入原则,
				  __ctxPath+'/js/creditFlow/archives/projectArchives.js',
				   __ctxPath+'/js/creditFlow/guarantee/materials/SlEnterPriseProcreditMaterialsView.js'
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
				this.enterpriseBusinessProjectInfoPanel=new enterpriseBusinessProjectInfoPanel({isAllReadOnly : true});
		        this.title1="企业客户基本信息";
		        this.perMain = "";
			    if(this.oppositeType == "person_customer") 
			    {
				    this.perMain = new ExtUD.Ext.PeerPersonMainInfoPanel({
					 isAllReadOnly : true,
					 isHidden : true
				    });
				    this.title1="个人客户基本信息";
			    } 
			    else if(this.oppositeType == "company_customer") 
			    {
				    this.perMain = new ExtUD.Ext.PeerMainInfoPanel({
					projectId : this.projectId,
					bussinessType:this.businessType, //业务类别
					 isAllReadOnly : true,
					 isHidden : true
				    });
			    };
			  this.bankInfoPanel=new ExtUD.Ext.BankInfoPanel({isAllReadOnly : true});
			     this.glReportArchiveView = new GlReportArchiveView({
			    	projectId:this.projectId,
			        businessType : this.businessType
			    });
			    this.glContractArchiveView = new GlContractArchiveView({
			    	projectId:this.projectId,
			        businessType : this.businessType
			    });
			    this.CustomerFiles=new CustomerFiles({
			        projectId:this.projectId,
			        businessType : this.businessType,
			        isgdHidden:false,
			        isgdEdit:true
			    });
		     this.slProcreditMaterialsView = new SlEnterPriseProcreditMaterialsView({
					 projectId:this.projectId,
			        businessType : this.businessType,
					isHiddenArchive : false,
					isarchives :false
					
				});
			   
			    var businessType =this.businessType; 
				var projectId = this.projectId;
				if(this.flag=="new"){
					 this.projectArchives=new projectArchives({
								      readonly :false,
								      businessType : businessType,
									  projectId : projectId
								    }).show();
					
				}else{
				  this.projectArchives= new projectArchives({
								      readonly :false,
								      projtoarchiveId :this.projtoarchiveId,
								      businessType : businessType,
									  projectId : projectId
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
							    xtype : 'fieldset',
								title : '项目基本信息',
								name:'projectInfo',
							//	bodyStyle : 'padding-left:8px',
								items : [this.enterpriseBusinessProjectInfoPanel]
							},{
						        xtype : 'fieldset',
								title : this.title1,
								items : [this.perMain]
							},{
								xtype : 'fieldset',
								title : '反担保措施',
								items : [new GuaranteeMortgageView({
											businessType : businessType,
									         projectId : projectId,
										isHiddenAffrim : false,
											isHidden : false,
											isgdHidden : false,
											isgdEdit : true,
											isHiddenGDBtn : false,
											isSeeContractHidden : false
											
									})]
							},{
								xtype : 'fieldset',
								title : '担保材料',
								items : [this.slProcreditMaterialsView]
							},{
								xtype : 'fieldset',
								title : '项目报告',
								items : [this.glReportArchiveView]
							},{
								xtype : 'fieldset',
								title : '项目文件',
								items : [this.CustomerFiles]
							},{
								xtype : 'fieldset',
								title : '项目合同',
								items : [this.glContractArchiveView]
							},{
								xtype : 'fieldset',
								title : '项目归档',
								items : [this.projectArchives]
							}
							]
		
						});
				  this.loadData({
		
					url : __ctxPath + '/creditFlow/getInfoCreditProject.do?taskId='+this.projectId+'&type='+this.businessType,
					method : "POST",
					preName : ['enterprise', 'person', 'gLGuaranteeloanProject','customerBankRelationPerson',"businessType"],
					root : 'data',
					success : function(response, options) {
					   var respText = response.responseText;  
				       var alarm_fields = Ext.util.JSON.decode(respText);   
			           //.getCmpByName('projectMoney1').setValue(Ext.util.Format.number(alarm_fields.data.gLGuaranteeloanProject.projectMoney,'0,000.00'))
			          // this.getCmpByName('earnestmoney1').setValue(Ext.util.Format.number(alarm_fields.data.gLGuaranteeloanProject.earnestmoney,'0,000.00'))
					}
				    });
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
						//	businessType : this.businessType,
							projectId : this.projectId
						},
						callback:function(fp,action){
							var gridPanel = Ext.getCmp('PlProjectArchivesGrid');
							if (gridPanel != null) {
								gridPanel.getStore().reload();
							}
						}
					}
				);
			}//end of save
})
