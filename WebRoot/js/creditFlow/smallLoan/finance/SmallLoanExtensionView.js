/**
 * @author 
 * @createtime 
 * @class SlBankAccountForm
 * @extends Ext.Window
 * @description SlBankAccount表单
 * @company 智维软件
 */
SmallLoanExtensionView = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				if(typeof(_cfg.businessType)!="undefined")
						{
						      this.businessType=_cfg.businessType;
						}
						if(typeof(_cfg.projectId)!="undefined")
						{
						      this.projectId=_cfg.projectId;
						}
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				SmallLoanExtensionView.superclass.constructor.call(this, {
							id : 'SmallLoanExtensionView'+this.projectId,
							title : '展期办理',
							region : 'fit',
							iconCls :'menu-flowWaita',
							items : [this.formPanel],
							buttonAlign : 'center',
							 buttons:[  
					                        {iconCls : 'btn-save',
					                        text:"提交",
					                        scope:this,
					                        handler:function(){
					                        	 var fp=this.get(0);
					                        	 var projectId=this.projectId;
					                        	 var checkStatus=0; //保存并申请
					                        	 var errorMsg="您确定要提交吗？信息一经提交，不能修改，请认真检查填写的内容选择“是”提交，选择“否”返回";
					                        	 Ext.Msg.confirm('信息确认', errorMsg, function(btn) {
					                        		 if (btn == 'yes') {
					                        		 	      var categoryIds=fp.getCmpByName('registerInfo').get(3).getGridDate();
					                        	              var startDate=fp.getCmpByName('slSuperviseRecord.startDate').getValue();
					                        	              var endDate=fp.getCmpByName('slSuperviseRecord.endDate').getValue();
					                        	              var payaccrualType=fp.getCmpByName('slSuperviseRecord.payaccrualType').getValue();
					                        	              if(startDate=="" || startDate==null){
					                        	                   Ext.ux.Toast.msg('操作信息', '申请展期失败,展期开始日期不能为空!'); 
					                        	                   return false;
					                        	              }
					                        	              if(payaccrualType =="ontTimeAccrual" &&(endDate=="" || endDate==null)){
					                        	                   Ext.ux.Toast.msg('操作信息', '申请展期失败,展期结束日期不能为空!'); 
					                        	                   return false;
					                        	              }
					                        	               if(payaccrualType =="ontTimeAccrual"){
						                        	              startDate=startDate.format("Y-m-d");
						                        	              endDate=endDate.format("Y-m-d");
						                        	              var s=daysBetween(startDate,endDate);
						                        	              if(s>=0){
						                        	                      Ext.ux.Toast.msg('操作信息', '申请展期失败,展期结束日期必须大于展期开始日期!'); 
						                        	                      return false;
						                        	              }
					                        	               }
					                        	              var money_plan=fp.getCmpByName('historyfinance').get(1).getGridDate();
					                        	              var intent_plan=fp.getCmpByName('historyfinance').get(0).getGridDate();
					                        	              var intent_plan_SuperviseRecord=fp.getCmpByName('registerInfo').get(1).getGridDate();
					                        	              var money_plan_SuperviseRecord=fp.getCmpByName('registerInfo').get(2).getGridDate();
					                        	              if (fp.getForm().isValid()) {
								                                          fp.getForm().submit({
									                                      params:{"projectId":projectId,"money_plan":money_plan,"intent_plan":intent_plan,"flag":0,"categoryIds":categoryIds,"intent_plan_SuperviseRecord":intent_plan_SuperviseRecord,'money_plan_SuperviseRecord': money_plan_SuperviseRecord,'slSuperviseRecord.checkStatus':checkStatus,'isNoStart':"1"}, // 传递的参数flag gaomimi加
										                        	  	  url :  __ctxPath + '/project/askForSlSmallloanProject.do',
														    			  method : 'POST',
														    			  success : function(form,action) {
														    			  	    Ext.ux.Toast.msg('操作信息', '申请展期成功!');
													    			  	        var tabs = Ext.getCmp('centerTabPanel');
							                                                    tabs.remove('SmallLoanExtensionView'+projectId);
													    			      }
								                                    })
								                                    
					                        	              }
					                        	       
					                        		 }
					                        	});
					                        }},
					                        {iconCls : 'btn-close',
					                        text:"取消", 
					                         scope:this,
					                        handler:function(){
					                        	var projectId=this.projectId;
					                        	var businessType='SmallLoan';
					                        	 Ext.Ajax.request({
													url : __ctxPath+ '/contract/deleteByProjectProcreditContract.do',
													method : 'POST',
													params : {
														projId : projectId,
														businessType : businessType,
														htType : 'clauseContract'
													}
												});
												 var tabs = Ext.getCmp('centerTabPanel');
				                                   tabs.remove('SmallLoanExtensionView' + this.projectId);
					                            _window.hide();                                
					                        }} 
							         ]
						});
			},// end of constructor
			//初始化组件
			initUIComponents : function() {
			     var businessType = this.businessType;	
			      var projectId = this.projectId;
			      
			      var DeferApplyInfoPanel =new FinanceExtensionlPanel({
			      	"continuationMoney":this.surplusnotMoney,
			      	projectId :this.projectId,
			      	idDefinition:'extenstion'})
			     	fillDataExtension(DeferApplyInfoPanel,null,'extenstion')
			      
				  this.superviseSlFundIntent=new superviseSlFundIntent({
															object :DeferApplyInfoPanel,
															projId : projectId,
															titleText : '款项信息',
															isHidden : false,
															businessType : businessType,
														    slSuperviseRecordId : 'noid',
															isUnLoadData :true,
															isThisSuperviseRecord :'yes'
																})
								     this.formPanel=  new Ext.FormPanel({
													modal : true,
													labelWidth : 100,
													frame:true,
													buttonAlign : 'center',
													layout : 'form',
													border : false,
													autoHeight: true,  
													defaults : {
														anchor : '100%',
														xtype : 'fieldset',
														columnWidth : 1,
														labelAlign : 'right',
														collapsible : true,
														autoHeight : true
													},
													labelAlign : "right",
													items : [{
														title : '历史财务信息',
														xtype : 'fieldset',
														autoHeight : true,
														name:"historyfinance",
														collapsible : true,
														width : '100%',
														bodyStyle : 'padding-left:8px',
														items : [new SlFundIntentViewVM({
														        	isHiddenOperation : false,
																	projectId : projectId,
																	businessType : businessType,
																	isHiddenAddBtn : true,
																    isHiddenDelBtn : true,
																    isHiddenCanBtn : true,
																    isHiddenResBtn : true,
																    isHidden1:true,
																    slSuperviseRecordId : 'noid',
																    isUnLoadData :false,
																	isThisSuperviseRecord :'no'
																}),new SlActualToChargeVM({
																	 isHiddenAddBtn : false,
																	isHiddenDelBtn : false,
																	projId : projectId,
																	isHidden:false,
																	businessType : businessType,
																	 slSuperviseRecordId : 'noid',
																    isUnLoadData :false,
																	isThisSuperviseRecord :'no'
																})]
													}, {
														title : '展期登记信息',
														xtype : 'fieldset',
														name:'registerInfo',
														autoHeight : true,
														collapsible : true,
														items : [DeferApplyInfoPanel,
														         this.superviseSlFundIntent,
														         new SlActualToCharge({
														        	 isHiddenOperation : false,
																	 projId : projectId,
																	 isUnLoadData : true,
																	 isHidden : false,
																	 businessType : businessType,
																	 slSuperviseRecordId : 'noid',
																	 isUnLoadData :true,
																	 isThisSuperviseRecord :'yes'
																}),this.SlClauseContractView = new SlClauseContractView(
																{
																	businessType : businessType,
																	projectId:projectId,
																	isShowHtml:true,
																	isLoadData:false,
																	isqsEdit:true,
																	HTLX : 'XEDZZQ',
																	isApply : false
																})]
													}]
							           })
				
			},//end of the initcomponents
			save : function() {
				var projectId=this.projectId;
				var fp=this.formPanel;
                 var money_plan=fp.getCmpByName('historyfinance').get(1).getGridDate();
	              var intent_plan=fp.getCmpByName('historyfinance').get(0).getGridDate();
	              var intent_plan_SuperviseRecord=fp.getCmpByName('supervisefinance').get(0).getGridDate();
	              var money_plan_SuperviseRecord=fp.getCmpByName('supervisefinance').get(1).getGridDate();
                  $postForm({
						formPanel:this.formPanel,
						params:{"projectId":projectId,"money_plan":money_plan,"intent_plan":intent_plan,"flag":0,"categoryIds":categoryIds,"intent_plan_SuperviseRecord":intent_plan_SuperviseRecord,'money_plan_SuperviseRecord': money_plan_SuperviseRecord,'slSuperviseRecord.checkStatus':checkStatus}, // 传递的参数flag gaomimi加
					    url :  __ctxPath + '/project/askForSlSmallloanProject.do',
						scope:this,
						callback:function(fp,action){
								 var tabs = Ext.getCmp('centerTabPanel');
				              tabs.remove('SmallLoanExtensionView' + this.projectId);
						}
					}
				);
			},//end of save
			cancel:function(){
              
              	 var tabs = Ext.getCmp('centerTabPanel');
				tabs.remove('SmallLoanExtensionView' + this.projectId);
              	
     }
                
			

		});

