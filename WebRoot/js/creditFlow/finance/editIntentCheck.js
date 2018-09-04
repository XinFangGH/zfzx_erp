//var formulatestore = new HT.JsonStore({
//	url : __ctxPath + "/finance/listSlFundIntent.do"	
//});

editIntentCheck = Ext.extend(Ext.Panel,
				{
					// 构造函数
					constructor : function(_cfg) {
						if(typeof(_cfg.projId)!="undefined")
						{
						      this.projectId=_cfg.projId;
						}
						if(typeof(_cfg.businessType)!="undefined")
						{
						      this.businessType=_cfg.businessType;
						}
//					  this.businessType="Financing";
						Ext.applyIf(this, _cfg);
						// 必须先初始化组件
						this.initUIComponents();
						editIntentCheck.superclass.constructor
								.call(this, {
									layout:'anchor',
		  					        anchor : '100%',
									items : [
										this.gridPanel
									]
								//	region : 'center',
								//	layout : 'fit',
								//	maximizable : true,
								//	autoScroll : true,
								//	title : '制定款项计划'
								});

					},// end of the constructor
					// 初始化组件

					initUIComponents : function() {
						var summary = new Ext.ux.grid.GridSummary();
						function totalMoney(v, params, data) {
							return '总计(元)';
						}
						this.ttbar=new Ext.Toolbar({
						    items: [ 
							{
								text : '增加款项计划',
								iconCls : 'btn-add',
								scope : this,
								handler : this.createRs
							}, '-', {
								iconCls : 'btn-del',
								scope : this,
								text : '删除款项计划',
								handler : this.removeSelRs
							}, '-', {
								iconCls : 'btn-info-add',
								scope : this,
								text : this.stringdata,
								handler : this.autocreate
							}]
						});
						this.gridPanel = new HT.EditorGridPanel( {
							border : false,
							clicksToEdit : 1,
							isShowTbar : this.isHidden?false:true,
							showPaging : false,
							autoHeight : true,
							hiddenCm :this.isHidden,
				         	plugins: [summary],
							// 使用RowActions
							// rowActions : true,
							id : 'editIntentCheck',
							url :__ctxPath + '/creditFlow/finance/listSlFundIntent.do',	
							baseParams :params1,
							fields : [ {
								name : 'fundIntentId'
							}, {
								name : 'fundType'
							}, {
								name : 'fundTypeName'
							}, {
								name : 'intentDate'
							}, {
								name : 'payMoney'
							}, {
								name : 'incomeMoney'
							}, {
								name : 'remark'
							} ],
							tbar : this.isHidden?null:this.ttbar,
							columns : [ {
								header : '资金类型',
								dataIndex : 'fundTypeName',
								width : 170
								
							}, {
								header : '计划到账日',
//								xtype : 'datecolumn',
								format : 'Y-m-d',
								dataIndex : 'intentDate',
								sortable : true,
								width : 80
							}, {
								header : '计划收入金额',
								dataIndex : 'incomeMoney',
								align : 'right',
								width : 127,
								renderer : function(value,metaData, record,rowIndex, colIndex,store){
									 
													return Ext.util.Format.number(value,',000,000,000.00')+"元"
												  
								}
							}, {
								header : '计划支出金额',
								dataIndex : 'payMoney',
								align : 'right',
								width : 127,
								renderer : function(value,metaData, record,rowIndex, colIndex,store){
													return Ext.util.Format.number(value,',000,000,000.00')+"元"
												  
								}
							
							}/*, {
								
								header :'',
								dataIndex : '',
								scope : this,
								editor : new Ext.form.TextField( {
									id : 'fundIntentJsonData2',
									name : 'fundIntentJsonData',
									allowBlank : true
								})
							} */]
						// end of columns
								});

					},
				autocreate : function() { 
					var gridPanel1=this.gridPanel
					 var operationType=null;
					 var accrualtype=null;
					 var payaccrualType=null;
					 var projectMoney=null;
					 var intentDate=null;
					 var startDate=null;
					 var dateMode=null;
					 var accrual=null;
					 if(this.businessType=="SmallLoan"){
					  accrualtype=this.object.getCmpByName("slSmallloanProject.accrualtype").getValue();
					   payaccrualType=this.object.getCmpByName("slSmallloanProject.payaccrualType").getValue();
					    projectMoney=this.object.getCmpByName("slSmallloanProject.projectMoney").getValue();
					   intentDate=this.object.getCmpByName("slSmallloanProject.intentDate").getValue();
					   startDate=this.object.getCmpByName("slSmallloanProject.startDate").getValue();
					   dateMode=this.object.getCmpByName("slSmallloanProject.dateMode").getValue();
					   accrual=this.object.getCmpByName("slSmallloanProject.accrual").getValue();
					 }
					
					 if(this.businessType=="Financing"){
					 accrualtype=this.object.getCmpByName("flFinancingProject.accrualtype").getValue();
					   payaccrualType=this.object.getCmpByName("flFinancingProject.payaccrualType").getValue();
					    projectMoney=this.object.getCmpByName("flFinancingProject.projectMoney").getValue();
					    intentDate=this.object.getCmpByName("flFinancingProject.intentDate").getValue();
					   startDate=this.object.getCmpByName("flFinancingProject.startDate").getValue();
					   dateMode=this.object.getCmpByName("flFinancingProject.dateMode").getValue();
					   accrual=this.object.getCmpByName("flFinancingProject.accrual").getValue();
					 }
					   var calcutype=this.businessType;  //业务品种
					   var financeServiceOfRate=0;   
					   var managementConsultingOfRate=0;
					   var projId=this.projectId;
					   var isPreposePayAccrualCheck=this.object.getCmpByName("isPreposePayAccrualCheck").getValue();
					   var message="";
					   if(accrualtype=="请选择" || accrualtype==null){
					     message="计息方式";
					   }else if(payaccrualType=="请选择" || payaccrualType==null){
					   	 message="付息方式";
					   } else if(dateMode=="请选择" || dateMode==null || dateMode==""){
					    message="日期模型";
					   }
					if(calcutype=="Financing"){
							    if(accrual=="" || accrual==null ){
							   	 message="项目利率";
							   }
							    if(projectMoney=="" || projectMoney==null){
							    	message="融资金额";
							   }
							   if(intentDate=="" || intentDate==null){
							   	message="融资结束日期";
							   }
							   if(startDate=="" || startDate==null){
							   	 message="融资起始日期";
							   }
							   
	//				         operationType =this.object1.getCmpByName("operationTypeKey").getValue();		   
					   }
				 if(calcutype=="SmallLoan"){
							    if(accrual=="" || accrual==null ){
							   	 message="贷款利率";
							   }
							    if(projectMoney=="" || projectMoney==null){
							    	message="贷款金额";
							   }
							   if(intentDate=="" || intentDate==null){
							   	message="还款日期";
							   }
							   if(startDate=="" || startDate==null){
							   	 message="贷款日期";
							   }
							    if(this.object.getCmpByName("slSmallloanProject.managementConsultingOfRate").getValue() ===""  || this.object.getCmpByName("slSmallloanProject.managementConsultingOfRate").getValue()==null){
							    	 message="管理咨询费率";
							    }	
							   if(this.object.getCmpByName("slSmallloanProject.financeServiceOfRate").getValue() ==="" || this.object.getCmpByName("slSmallloanProject.financeServiceOfRate").getValue()==null){
							   message="财务服务费率";
							   }
							   
						     financeServiceOfRate=this.object.getCmpByName("slSmallloanProject.financeServiceOfRate").getValue();
						    managementConsultingOfRate =this.object.getCmpByName("slSmallloanProject.managementConsultingOfRate").getValue();		   
//						     operationType =this.object1.getCmpByName("operationTypeKey").getValue();
					    }
					 if(message !=""){   
							  Ext.MessageBox.show({
		                    title : '操作信息',
		                    msg : message+'不能为空',
		                    buttons : Ext.MessageBox.OK,
		                    icon : 'ext-mb-error'
		                });
		                 return null;
			    	}
					  
					   var isPreposePayAccrual=0;
					   if(isPreposePayAccrualCheck == true){
					     var isPreposePayAccrual=1;
					   }
//						
//						params1 ={
//							projectId : projId,
//						    'slSmallloanProject.accrualtype': accrualtype,
////									    'slSmallloanProject.dayOfRate': dayOfRate,
//						    'slSmallloanProject.isPreposePayAccrual': isPreposePayAccrual,
//						    'slSmallloanProject.payaccrualType': payaccrualType,
//						    'slSmallloanProject.projectMoney': projectMoney,
//						    'slSmallloanProject.startDate': startDate,
//						    'slSmallloanProject.intentDate': intentDate,
//						    'slSmallloanProject.dateMode': dateMode,
//						     'slSmallloanProject.accrual': accrual,
//						     'slSmallloanProject.operationType': operationType,
//						     'calcutype':calcutype
//						    
//						}
						
					    gridPanel1.getStore().setBaseParam('projectId',projId) ; 
					     gridPanel1.getStore().setBaseParam('slSmallloanProject.accrualtype', accrualtype);
					      gridPanel1.getStore().setBaseParam('slSmallloanProject.isPreposePayAccrual', isPreposePayAccrual);
					    gridPanel1.getStore().setBaseParam('slSmallloanProject.payaccrualType', payaccrualType);
					     gridPanel1.getStore().setBaseParam('slSmallloanProject.projectMoney', projectMoney);
					    gridPanel1.getStore().setBaseParam('slSmallloanProject.startDate', startDate);
					       gridPanel1.getStore().setBaseParam('slSmallloanProject.intentDate', intentDate);
					    gridPanel1.getStore().setBaseParam('slSmallloanProject.dateMode', dateMode);
					     gridPanel1.getStore().setBaseParam('slSmallloanProject.accrual', accrual);
					    gridPanel1.getStore().setBaseParam('slSmallloanProject.operationType',  this.businessType);
					     gridPanel1.getStore().setBaseParam('slSmallloanProject.financeServiceOfRate', financeServiceOfRate);
					    gridPanel1.getStore().setBaseParam('slSmallloanProject.managementConsultingOfRate', managementConsultingOfRate);
					    gridPanel1.getStore().setBaseParam('calcutype', calcutype);
					    this.gridPanel.getStore().setBaseParam('flag1',0) ; 
						gridPanel1.getStore().reload();

							

					},

					createRs : function() {
						this.datafield.setValue('');
						var newRecord = this.gridPanel.getStore().recordType;
						var newData = new newRecord( {
							fundIntentId : '',
							fundType : '',
							payMoney : 0,
							incomeMoney : 0,
							intentDate : new Date(),
							remark : ''
						});
						this.gridPanel.stopEditing();
						this.gridPanel.getStore().insert(this.gridPanel.getStore().getCount(), newData);
						this.gridPanel.getView().refresh();
						this.gridPanel.getSelectionModel().selectRow((this.gridPanel.getStore().getCount()-1));
						this.gridPanel.startEditing(0, 0);

					},

					getGridDate : function() {
						var vRecords = this.gridPanel.getStore().getRange(0,
								this.gridPanel.getStore().getCount()); // 得到修改的数据（记录对象）
						var vCount = vRecords.length; // 得到记录长度
						var vDatas = '';
						if (vCount > 0) {
						var st="";
							for ( var i = 0; i < vCount; i++) {
								  st={"fundType":vRecords[i].data.fundType,"incomeMoney":vRecords[i].data.incomeMoney,"payMoney":vRecords[i].data.payMoney,"intentDate":vRecords[i].data.intentDate};
								vDatas += Ext.util.JSON
										.encode(st) + '@';
							}

							vDatas = vDatas.substr(0, vDatas.length - 1);
						}
						//Ext.getCmp('fundIntentJsonData2').setValue(vDatas);
//						this.gridPanel.getColumnsgetEditor().getCmpByName('fundIntentJsonData').setValue(vDatas);
//						alert(this.gridPanel.getEditor().getCmpByName('fundIntentJsonData').getValue());
						return vDatas;
					},
					saveRs : function() {
						var data = this.getGridDate();
						Ext.Ajax
								.request( {
									url : __ctxPath + '/creditFlow/finance/savejsonSlFundIntent.do',
									method : 'POST',
									scope : this,
									params : {
										slFundIentJson : data,
										projectId : this.projectId,
										businessType :this.businessType
									},
									success : function(response, request) {
										this.gridPanel.getStore().setBaseParam('flag1',1) ; 
										this.gridPanel.getStore().reload();

									}
								});

					},
					removeSelRs : function() {
						var fundIntentGridPanel = this.gridPanel;
						var projId=this.projectId
						var deleteFun = function(url, prame, sucessFun,i,j) {
							Ext.Ajax
									.request( {
										url : url,
										method : 'POST',
										success : function(response) {
											if (response.responseText.trim() == '{success:true}') {
													if(i==(j-1)){
												    Ext.ux.Toast.msg('操作信息', '删除成功!');
												}
												sucessFun();
											} else if (response.responseText
													.trim() == '{success:false}') {
												Ext.ux.Toast.msg('操作信息', '删除失败!');
											}
										},
										params : prame
									});
						};
                    var  a= fundIntentGridPanel.getSelectionModel().getSelections();
                          if (a <= 0) {
												Ext.ux.Toast.msg('操作信息','请选择要删除的记录');
												return false;
											};
						Ext.Msg
								.confirm("提示!",
										'确定要删除吗？',
										function(btn) {

											if (btn == "yes") {
												//	grid_sharteequity.stopEditing();
										var s = fundIntentGridPanel.getSelectionModel().getSelections();
										for ( var i = 0; i < s.length; i++) {
											var row = s[i];
											if (row.data.fundIntentId == null || row.data.fundIntentId == '') {
												fundIntentGridPanel.getStore().remove(row);
											} else {
												deleteFun(
													__ctxPath + '/creditFlow/finance/deleteSlFundIntent.do',
													{
														fundIntentId : row.data.fundIntentId,
														projectId : projId,
														businessType : this.businessType
													},
													function() {
														fundIntentGridPanel.getStore().remove(row);
														fundIntentGridPanel.getStore().reload();
													},i,s.length);

									}

										}

									}

								});

					}

				});