
Financial= Ext.extend(Ext.Panel, {
	        projectId:1,
	        oppositeType:307,		
	// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				Financial.superclass.constructor.call(this, {
						
							border:false,
							title:"出具对外担保承诺函",
							items : []
						})
				
			
				
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				var jsArr = [__ctxPath + '/js/commonFlow/ExtUD.Ext.js',
				 __ctxPath+'/js/commonFlow/EnterpriseShareequity.js',
                  __ctxPath + '/publicmodel/uploads/js/uploads.js',
				  __ctxPath+'/js/creditFlow/guarantee/enterpriseBusiness/enterpriseBusinessUI.js',
				   __ctxPath+'/js/creditFlow/guarantee/enterpriseBusiness/bankInfoPanel.js'
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

                this.uploads1 = new uploads({
					projectId : this.projectId,
					isHiddenColumn : false,
					isDisabledButton : false,
					isHiddenOnlineButton : true,
					titleName :'银行冻结保证金凭证',
					setname :'银行冻结保证金凭证',
					tableName :'gl_bank_guaranteemoney',
					typeisfile :'typeisglbankguaranteemoney',
					businessType:6584,
					uploadsSize : 15
					
				});
				this.uploads2 = new uploads({
					projectId : this.projectId,
					isHiddenColumn : false,
					isDisabledButton : false,
					isHiddenOnlineButton : true,
					setname :'收取客户保证金凭证',
					titleName :'收取客户保证金凭证',
					tableName :'gl_bank_guaranteemoney',
					typeisfile :'typeisglbankguaranteemoney',
					businessType:6584,
					uploadsSize : 15
					
				});
				this.uploads3 = new uploads({
					projectId : this.projectId,
					isHiddenColumn : false,
					isDisabledButton : false,
					isHiddenOnlineButton : true,
					setname :'收取保费金凭证',
					titleName :'收取保费金凭证',
					tableName :'gl_bank_guaranteemoney',
					typeisfile :'typeisglbankguaranteemoney',
					businessType:6584,
					uploadsSize : 15
					
				});

				this.bankInfoPanel=new bankInfoPanel();
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
								title : '企业信息',
								items : [this.perMain]
							}, {
								title : '项目信息',
								bodyStyle : 'padding-left:8px',
								items : [this.enterpriseBusinessProjectInfoPanel]
							},{
								title : '银行信息',
								items : [this.bankInfoPanel]
							},{
								title : '银行保证金',
								items : [this.uploads1]
							},{
								title : '客户保证金',
								items : [this.uploads2]
							},{
								title : '保费',
								items : [this.uploads3]
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
			save : function() {
				$postForm({
						formPanel:this.formPanel,
						scope:this,
						url:__ctxPath + '/creditFlow/guarantee/EnterpriseBusiness/saveReleaseGlBankGuaranteemoney.do?isRelease=0&&projId='+this.projectId,
						callback:function(fp,action){
							
						}
					}
				);
				$postForm({
						formPanel:this.formPanel,
						scope:this,
						url:'',/__ctxPath + '/creditFlow/guarantee/EnterpriseBusiness/guaranteeMoneyIsChargeGlActualToCharge.do?isCharge=1&&projId='+this.projectId,
						callback:function(fp,action){
							
						}
					}
				);
				$postForm({
						formPanel:this.formPanel,
						scope:this,
						url:'',/__ctxPath + '/creditFlow/guarantee/EnterpriseBusiness/guaranteeChargeIsChargeGlActualToCharge.do?isCharge=1&&projId='+this.projectId,
						callback:function(fp,action){
							
						}
					}
				);
			},
		saveBusDatas:this.save
})
