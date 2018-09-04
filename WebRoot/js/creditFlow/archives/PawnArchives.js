/*
 * 
 * @param {Object} _cfg
 * 项目保前归档
 */
PawnArchives= Ext.extend(Ext.Panel, {
	        // 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				PawnArchives.superclass.constructor.call(this, {
					    id : 'PawnProjectarchives_' + this.projectId,
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
				__ctxPath + '/js/creditFlow/pawn/project/PawnProjectInfoPanel.js',
				__ctxPath + '/js/creditFlow/pawn/project/loanPawnData.js',
				__ctxPath + '/js/commonFlow/ExtUD.Ext.js',//客户信息 项目基本信息
				__ctxPath + '/js/selector/UserDialog.js',
				__ctxPath + '/js/creditFlow/common/EnterpriseShareequity.js',//股东信息
				__ctxPath + '/js/creditFlow/mortgage/business/BusinessForm.js',
				__ctxPath + '/js/creditFlow/customer/dictionary/dictionaryNotLastNodeTree.js',
				__ctxPath + '/js/commonFlow/NewProjectForm.js',
				__ctxPath + '/js/creditFlow/pawn/finance/PawnProjectFinanceInfoPanel.js',
				__ctxPath + '/js/creditFlow/pawn/pawnItems/PawnItemsListView.js',
				__ctxPath + '/js/creditFlow/pawn/pawnItems/AddPawnItemsWin.js',
				__ctxPath + '/js/creditFlow/pawn/pawnItems/UpdatePawnItemsWin.js',
				__ctxPath + '/js/creditFlow/pawn/pawnItems/SeeVehicleInfo.js',
				__ctxPath + '/js/creditFlow/pawn/pawnItems/SeeStockownershipInfo.js',
				__ctxPath + '/js/creditFlow/pawn/pawnItems/SeeMachineInfo.js',
				__ctxPath + '/js/creditFlow/pawn/pawnItems/SeeProductInfo.js',
				__ctxPath + '/js/creditFlow/pawn/pawnItems/SeeHouseInfo.js',
				__ctxPath + '/js/creditFlow/pawn/pawnItems/SeeOfficeBuildingInfo.js',
				__ctxPath + '/js/creditFlow/pawn/pawnItems/SeeHouseGroundInfo.js',
				__ctxPath + '/js/creditFlow/pawn/pawnItems/SeeBusinessInfo.js',
				__ctxPath + '/js/creditFlow/pawn/pawnItems/SeeBusinessAndLiveInfo.js',
				__ctxPath + '/js/creditFlow/pawn/pawnItems/SeeEducationInfo.js',
				__ctxPath + '/js/creditFlow/pawn/pawnItems/SeeIndustryInfo.js',
				__ctxPath + '/js/creditFlow/pawn/pawnItems/SeeDroitUpdateInfo.js',
				__ctxPath + '/js/creditFlow/pawn/materials/PawnMaterialsView.js',
				__ctxPath + '/js/creditFlow/pawn/materials/PawnMaterialsForm.js',
				__ctxPath + '/js/creditFlow/smallLoan/meeting/MeetingSummaryForm.js',
				__ctxPath + '/js/creditFlow/smallLoan/meeting/MeetingSummaryUpload.js',
				__ctxPath+'/js/creditFlow/guarantee/meeting/CensorMeetingCollectivityDecisionConfirm.js',
				__ctxPath + '/js/creditFlow/pawn/contract/PawnContractView.js',
				__ctxPath + '/js/creditFlow/pawn/contract/PawnOperateContractWindow.js',
				__ctxPath + '/js/creditFlow/pawn/finance/PlFundIntentViewVM.js',
				__ctxPath + '/js/creditFlow/report/SlReportView.js',
				__ctxPath + '/js/creditFlow/finance/SlFundIntentForm.js',
				__ctxPath + '/js/creditFlow/finance/SlFundIntentForm1.js',
				__ctxPath+ '/js/creditFlow/pawn/project/PawnContinuedWindow.js',
				__ctxPath+ '/js/creditFlow/pawn/project/PawnContinuedForm.js',
				__ctxPath+ '/js/creditFlow/pawn/finance/PawnContinuedFundIntentView.js',
				__ctxPath+ '/js/creditFlow/pawn/project/PawnRedeemWindow.js',
				__ctxPath+ '/js/creditFlow/pawn/project/PawnRedeemForm.js',
				__ctxPath+ '/js/creditFlow/pawn/project/PawnVastWindow.js',
				__ctxPath+ '/js/creditFlow/pawn/project/PawnVastForm.js',
				__ctxPath+ '/js/creditFlow/pawn/finance/PlFundIntentViewVM.js',
				__ctxPath + '/js/creditFlow/pawn/project/loadPawnContinuedData.js',
				__ctxPath+ '/js/creditFlow/pawn/project/PawnContinuedView.js',
				__ctxPath+ '/js/creditFlow/pawn/project/PawnRedeemView.js',
				__ctxPath+ '/js/creditFlow/pawn/project/PawnVastView.js',
				__ctxPath + '/js/creditFlow/pawn/materials/PawnUploads.js',
				__ctxPath + '/js/creditFlow/archives/PlArchivesMaterialsView.js',
				
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
				this.perMain = new ExtUD.Ext.PeerPersonMainInfoPanel({
					 projectId : this.projectId,
					 isAllReadOnly :true,
					 isNameReadOnly:false,
					 isSpouseReadOnly: true,
					 isHidden : true,
					isEditPerson : false
				});
				title="个人客户信息";
			} else if(this.oppositeType =="company_customer"){
				     this.perMain = new ExtUD.Ext.PeerMainInfoPanel({
					 projectId : this.projectId,
					 bussinessType:this.businessType,
					 isAllReadOnly : true,
					 isNameReadOnly:false,
					 isHidden : true,
					 isEditEnterprise : false
				});
			}
			
			//当物清单
			this.pawnItemsListView=new PawnItemsListView({
				titleText : '当物清单',
				isHiddenTitle : true,
				 projectId : this.projectId,
				 businessType:this.businessType,
				 isHiddenAddBtn : true,
				 isHiddenEdiBtn : true,
				 isHiddenDelBtn : true
			})
				
			this.projectInfo = new PawnProjectInfoPanel({
				isDiligenceReadOnly : false,
				isAllReadOnly:true
			});
			this.PlArchivesMaterialsView = new PlArchivesMaterialsView({
				projectId:this.projectId,
				businessType : this.businessType,
				operationType : this.operationType,
				isHiddenAffrim:true,
				isHiddenRecive:false,
				isHidden_materials : true,
				isHiddenType :false,
				isHiddenAffrim:false,
				isAffrimEdit:false
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
								items : [this.perMain]
							},/* {
								title : '贷款资料',
								items : [this.slProcreditMaterialsView]
							},*/ {
								title : '当物清单',
								items : [this.pawnItemsListView]
							}, {
								title : '归档材料',
								items : [this.PlArchivesMaterialsView]
							},{
								xtype : 'fieldset',
								title : '项目归档',
								items : [this.projectArchives]
							}
							]
		
						});
				   this.loadData({
						url : __ctxPath + '/creditFlow/pawn/project/getInfoPlPawnProject.do?slProjectId='+this.projectId,
						method : "POST",
						preName : ['enterprise', 'person', 'plPawnProject'],
						root : 'data',
						success : function(response, options) {
							var respText = response.responseText;
							var alarm_fields = Ext.util.JSON.decode(respText);
							if(this.getCmpByName('projectMoney1')){
								this.getCmpByName('projectMoney1').setValue(Ext.util.Format.number(alarm_fields.data.plPawnProject.projectMoney,'0,000.00'));
							}
							var appUsers= alarm_fields.data.appuerName;
							var appUserId= alarm_fields.data.plPawnProject.appUserId;
							 this.getCmpByName('degree').setValue(appUsers);
//							fillPawnData(this,alarm_fields,'pawnliucheng'+this.taskId);
						
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
							businessType : this.businessType,
							projectId : this.projectId
						},
						callback:function(fp,action){
							var tabs = Ext.getCmp('centerTabPanel');
				            tabs.remove('PawnProjectarchives_' + this.projectId);
							var gridPanel = Ext.getCmp('PlProjectArchivesGrid');
							if (gridPanel != null) {
								gridPanel.getStore().reload();
							}
						}
					}
				);
			}//end of save
})
