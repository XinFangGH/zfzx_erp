/*
 * 
 * @param {Object} _cfg
 * 业务经理登记
 */
BusinessRegistration = Ext.extend(Ext.Panel, {
			projectId:1,
			businessType:6584,
			oppositeType:307,
	        // 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				BusinessRegistration.superclass.constructor.call(this, {
							border:false,
							frame:true,
							title:"业务经理登记",
							items : [{
								xtype:'button',
								text:'提交',
								handler:this.saveBusDatas
							}]
						})
				
			
				
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				var jsArr = [__ctxPath + '/js/commonFlow/ExtUD.Ext.js',
				 __ctxPath+'/js/commonFlow/EnterpriseShareequity.js',
				  __ctxPath+'/js/creditFlow/guarantee/enterpriseBusiness/enterpriseBusinessUI.js',
                 __ctxPath+'/js/creditFlow/guarantee/enterpriseBusiness/Approval.js',
                 __ctxPath+'/js/creditFlow/guarantee/enterpriseBusiness/bankInfoPanel.js',
                 __ctxPath+'/js/creditFlow/guarantee/enterpriseBusiness/ContractDownload.js',
                 __ctxPath + '/publicmodel/uploads/js/uploads.js',
                 __ctxPath+'/js/creditFlow/guarantee/enterpriseBusiness/guaranteeModel/BankGuaranteeMoney.js',
                 __ctxPath+'/js/creditFlow/guarantee/enterpriseBusiness/guaranteeModel/CustomerGuaranteeMoney.js',
                 __ctxPath+'/js/creditFlow/guarantee/enterpriseBusiness/guaranteeModel/GuaranteeMoney.js'
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
                
				this.bankInfoPanel=new bankInfoPanel();
				this.contractDownload=new ContractDownload({projId:this.projectId,businessType:this.businessType})
				this.approval=new Approval({projectId:this.projectId});
				this.outPanel = new Ext.Panel({
							labelWidth : 100,
							buttonAlign : 'center',
							layout : 'form',
							border : false,
							autoScroll:true,
							autoHeight : true,
							autoWidth:true,
							defaults : {
								anchor : '100%',
								xtype : 'fieldset',
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
								title : '合同下载',
								items : [this.contractDownload]
							},{
								title : '银行审贷会意见',
								items : [{
										xtype:'combo',
										mode : 'local',
									    displayField : 'name',
									    valueField : 'id',
									    width : 70,
									    emptyText:'请选择',
									    store : new Ext.data.SimpleStore({
									    		id:0,
												fields : ["name", "id"],
												data : [["银行审贷会审批通过", "0"],
														["未通过，终止项目", "1"]]
										}),
										triggerAction : "all",
										fieldLabel : '意见',
								        anchor : '40%',
										allowBlank : false,
										listeners : {
											scope : this,
											'select' : function(combox,record,index) {
				                                var gridPanel=this.outPanel;
												var r = combox.getValue();
												if(r==0){
													var items = gridPanel.items;
													var item1 = items.last();
													gridPanel.remove(item1, true);
													gridPanel.add(this.approval);
										            gridPanel.doLayout();
									            }else{
									            	var items = gridPanel.items;
													var item1 = items.last();
													gridPanel.remove(item1, true);
													var opinion=new Ext.form.FieldSet({
														title:'意见与说明',
														layout : "form",
														items:[{
															xtype : "textarea",
															fieldLabel : "说明",
															name : "comments",
															anchor : "100%",
															blankText : "说明不能为空，请正确填写!",
															allowBlank : false
														}]
													}) 
													gridPanel.add(opinion);
										            gridPanel.doLayout();
									            }
											}
										}
								}]
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
//           var eg=this.approval.get(0);
//           alert(eg)
          // var vDates=getGridDate(eg);
			       
//					formPanel.getForm().submit({
//					    clientValidation: false, 
//						url : __ctxPath + '/project/updateGLGuaranteeloanSlSmallloanProject.do',
//						params : {
//							"gudongInfo" : vDates
//						},
//						method : 'post',
//						waitMsg : '数据正在提交，请稍后...',
//						success : function(fp, action) {
//							Ext.ux.Toast.msg('操作信息', '保存信息成功!');
//						},
//						failure : function(fp, action) {
//							Ext.MessageBox.show({
//								title : '操作信息',
//								msg : '信息保存出错，请联系管理员！',
//								buttons : Ext.MessageBox.OK,
//								icon : 'ext-mb-error'
//							});
//						}
//					});	  	  
		}
})
