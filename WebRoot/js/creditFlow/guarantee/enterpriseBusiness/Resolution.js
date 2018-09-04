
Resolution = Ext.extend(Ext.Panel, {
	        projectId:1,
	        oppositeType:307,
			// 构造函数
			constructor : function(_cfg) {
	           
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				Resolution.superclass.constructor.call(this, {
							id : 'DiligenceForm2',
							border:false,
							title:"审保会决议",
							items : []
						})
				
			
				
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				var jsArr = [__ctxPath + '/js/commonFlow/ExtUD.Ext.js',
				 __ctxPath+'/js/commonFlow/EnterpriseShareequity.js',
				  __ctxPath+'/js/creditFlow/guarantee/enterpriseBusiness/enterpriseBusinessUI.js',
				   __ctxPath+'/js/creditFlow/guarantee/enterpriseBusiness/bankInfoPanel.js',
				   __ctxPath+'/js/report/SlReportView.js',
				   __ctxPath+'/js/report/SlRiskReportView.js',
				   __ctxPath+'/js/creditFlow/guarantee/enterpriseBusiness/EnterpriseEvaluation.js',
				   //__ctxPath+'/js/system/MortgageView.js',index.jsp引入。不需要再次引入。名字已修改为MortgageView.js
				   __ctxPath+'/js/materials/SlProcreditMaterialsView.js',
				   __ctxPath + '/js/assuretenet/SlProcreditAssuretenetedForm.js'
				];
				$ImportSimpleJs(jsArr, this.constructPanel,this);
			
			
			},
			constructPanel:function(){
				
				 var title="企业客户基本信息";
			        this.perMain = "";
				    if(this.oppositeType == 307) 
				    {
					    this.perMain = new ExtUD.Ext.PeerPersonMainInfoPanel({
						isPersonNameReadOnly : true
					    });
					    title="个人客户基本信息";
				    } 
				    else 
				    {
					    this.perMain = new ExtUD.Ext.PeerMainInfoPanel({
						projectId : this.projectId,
						isEnterprisenameReadonly : true,
						bussinessType:'enterprise', //业务类别
						isEnterpriseShortNameReadonly : true
					    });
				    };				
				this.enterpriseBusinessProjectInfoPanel=new enterpriseBusinessProjectInfoPanel();
                
				this.slReportView=new SlReportView({projectId:this.projectId,businessType:6584,isHiddenColumn:true,isHiddenAffrim:true,isHidden:true});
				this.bankInfoPanel=new bankInfoPanel();
				this.enterpriseEvaluation=new EnterpriseEvaluation({projectId:this.projectId,isHidden:true})
				this.slRiskReportView=new SlRiskReportView({projectId:this.projectId,businessType:6584,isHiddenColumn:true,isHiddenAffrim:true,isHidden:true})
			    this.mortgageView=new MortgageView({projectId:this.projectId,isHidden:true,isHiddenAffrim:true})
				this.slProcreditMaterialsView=new SlProcreditMaterialsView({projId:this.projectId,isHidden:true})
				this.SlProcreditAssuretenetedForm=new SlProcreditAssuretenetedForm({projId:this.projectId,isEditBbusinessmanageropinion:true})
				this.outPanel = new Ext.Panel({
							modal : true,
							labelWidth : 100,
							buttonAlign : 'center',
							layout : 'form',
							border : false,
							frame:true,
							defaults : {
								anchor : '100%',
								xtype : 'fieldset',
								columnWidth : 1,
								collapsible : true,
								autoHeight : true
							},
							labelAlign : "right",
							items : [{
								title : title,
								items : [this.perMain]
							}, {
								title : '项目信息',
								bodyStyle : 'padding-left:8px',
								items : [this.enterpriseBusinessProjectInfoPanel]
							},{
								title : '银行信息',
								items : [this.bankInfoPanel]
							},{
								title : '担保材料',
								items : [this.slProcreditMaterialsView]
							},{
								title : '反担保措施',
								items : [this.mortgageView]
							},{
								title : '担保调查报告',
								items : [this.slReportView]
							},{
								title : '担保准入原则',
								items : [this.SlProcreditAssuretenetedForm]
							},{
								title : '企业评估报告',
								items : [this.enterpriseEvaluation]
							},{
								title : '综合分析报告',
								items : [this.slRiskReportView]
							}, {
								title : '意见与说明',
								layout : "form",
								items : [{
									xtype : "textarea",
									fieldLabel : "说明",
									name : "comments",
									anchor : "100%",
									blankText : "说明不能为空，请正确填写!",
									allowBlank : false
								}]
							}]
		
						});
		
				this.add(this.outPanel);
				this.doLayout();

			}, 
		saveBusDatas:function(formPanel){
			Ext.MessageBox.alert("提示","保存成功！");		  
		}
})
