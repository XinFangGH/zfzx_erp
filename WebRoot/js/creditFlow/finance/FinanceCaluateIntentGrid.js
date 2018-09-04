//var formulatestore = new HT.JsonStore({
//	url : __ctxPath + "/finance/listSlFundIntent.do"	
//});

FinanceCaluateIntentGrid = Ext.extend(Ext.Panel,
				{
					// 构造函数
	                iscreateRshidden:false,
	                isremovehidden:false,
					constructor : function(_cfg) {
						if(typeof(_cfg.projId)!="undefined")
						{
						      this.projectId=_cfg.projId;
						}
						if(typeof(_cfg.businessType)!="undefined")
						{
						      this.businessType=_cfg.businessType;
						}
						if(typeof(_cfg.iscreateRshidden)!="undefined")
						{
						      this.iscreateRshidden=_cfg.iscreateRshidden;
						}
						if(typeof(_cfg.isremovehidden)!="undefined")
						{
						      this.isremovehidden=_cfg.isremovehidden;
						}
//					  this.businessType="Financing";
						Ext.applyIf(this, _cfg);
						// 必须先初始化组件
						this.initUIComponents();
						FinanceCaluateIntentGrid.superclass.constructor
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
					/*		{
								text : '增加',
								iconCls : 'btn-add',
								scope : this,
								handler : this.createRs,
								hidden :this.iscreateRshidden
							}, this.iscreateRshidden?'':'-', {
								iconCls : 'btn-del',
								scope : this,
								text : '删除',
								handler : this.removeSelRs,
								hidden:this.isremovehidden
							}, this.isremovehidden?'':'-', {
								iconCls : 'btn-info-add',
								scope : this,
								text : '生成',
								handler : this.autocreate
							}*/
							/*, '-', {

								iconCls : 'btn-info-add',
								scope : this,
								text : '确认放款',
								handler : this.affrim
							}
							*/
							
							]
						});
						this.datafield=new Ext.form.DateField( {
									format : 'Y-m-d',
									allowBlank : false,
									readOnly:this.isHidden
								})
						var fundTypenodekey=""; 
						if(this.businessType=="Financing"){
						fundTypenodekey="finaning_fund";
						}	
						if(this.businessType=="SmallLoan"){
						fundTypenodekey="financeType";
						}
						this.comboType= new DicIndepCombo({
									editable : false,
									readOnly:this.isHidden,
									lazyInit : false,
									forceSelection : false,
									//xtype : 'csdiccombo',
						//			itemVale : 1149,
									nodeKey : fundTypenodekey
								//	itemName : '贷款资金类型'
								})
								this.comboType.store.reload();
					var params1={
										projectId : this.projectId,
									     flag1:1,
									     businessType:this.businessType
									};
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
							id : 'caluateIntentGrideditGrid',
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
							}, {
								name : 'afterMoney'
							},{
								name : 'interestStarTime'
							},{
								name : 'interestEndTime'
							},{
								name : 'payintentPeriod'
							},{
								name :'slSuperviseRecordId'
							} ],
							tbar : this.isHidden?null:this.ttbar,
							columns : [{
								header : '期数',
								dataIndex : 'payintentPeriod',
								editor : new Ext.form.ComboBox({
								    triggerAction: 'all',
								    readOnly : this.isHidden1,
								    store: new Ext.data.SimpleStore({
										autoLoad : true,
										url : __ctxPath+ '/flFinancing/getPayIntentPeriodFlFinancingProject.do',
										fields : ['itemId', 'itemName'],
										baseParams : {payintentPeriod:typeof(this.object)!='undefined'?this.object.getCmpByName('flFinancingProject.payintentPeriod').getValue():0}
									}),
								    valueField: 'itemId',
								    displayField: 'itemName'
						
								}),
								renderer : function(value, metaData, record, rowIndex,colIndex, store){
									if (record.data.isValid == 1) {
										if(null==record.data.slSuperviseRecordId && record.data.fundType!='Financingborrow'){
											return '<font style="font-style:italic;text-decoration: line-through;color:gray">第'+value+'期</font>';
										}else if(null!=record.data.slSuperviseRecordId){
											return '<font style="font-style:italic;text-decoration: line-through;color:gray">展期第'+value+'期</font>'
										}else{
											if(null!=value){
												return '<font style="font-style:italic;text-decoration: line-through;color:gray">'+value+'</font>'
											}
										}
									}else{
										if (record.data.notMoney == 0) {
											if(null==record.data.slSuperviseRecordId && record.data.fundType!='Financingborrow'){
												return '<font style="color:gray">第'+value+'期</font>';
											}else if(null!=record.data.slSuperviseRecordId){
												return '<font style="color:gray">展期第'+value+'期</font>'
											}else{
												if(null!=value){
													return '<font style="color:gray">'+value+'</font>'
												}
											}
										}else{
											if(null==record.data.slSuperviseRecordId && record.data.fundType!='Financingborrow'){
												return '第'+value+'期';
											}else if(null!=record.data.slSuperviseRecordId){
												return '展期第'+value+'期'
											}else{
												if(null!=value){
													return value
												}
											}
										}
									}
								}
							}, {
								header : '资金类型',
								dataIndex : 'fundType',
								summaryType: 'count',
								summaryRenderer: totalMoney,
								editor :this.comboType,
								renderer : function(value,metaData, record,rowIndex, colIndex,store) {
									var objcom = this.editor;
									var objStore = objcom.getStore();
						
									var idx = objStore.find("dicKey", value);
									
									if (idx != "-1") {

										return objStore.getAt(idx).data.itemValue;
									} else {
								        
										if(record.data.fundTypeName==""){
											
											record.data.fundTypeName=value
											
										}else{
											var x = store.getModifiedRecords();
											if(x!=null && x!=""){
												record.data.fundTypeName=value
											}
										}
										
										 return record.get("fundTypeName")
									}
									
						     }
								
							}, {
								header : '计划到账日',
//								xtype : 'datecolumn',
								format : 'Y-m-d',
								dataIndex : 'intentDate',
								sortable : true,
								renderer : ZW.ux.dateRenderer(this.datafield),
								editor :this.datafield
							}, {
								header : '计划收入金额',
								dataIndex : 'incomeMoney',
								align : 'right',
								summaryType: 'sum',
								editor : {
								xtype : 'numberfield',
								readOnly:this.isHidden,
								listeners : {
								   blur : function(v){
								  if(v.getValue()==""){
								    v.setValue("0.00")
								  }
								   
								   }
									
								}
								},
								renderer : function(value,metaData, record,rowIndex, colIndex,store){
									 
													return Ext.util.Format.number(value,',000,000,000.00')+"元"
												  
								}
							}, {
								header : '计划支出金额',
								dataIndex : 'payMoney',
								align : 'right',
								summaryType: 'sum',
								editor : {
								xtype : 'numberfield',
								readOnly:this.isHidden,
								listeners : {
								   blur : function(v){
								  if(v.getValue()==""){
								    v.setValue("0.00")
								  }
								   
								   }
									
								}
								},
								renderer : function(value,metaData, record,rowIndex, colIndex,store){
													return Ext.util.Format.number(value,',000,000,000.00')+"元"
												  
								}
							
							}, {
								header : '计息开始日期',
								dataIndex : 'interestStarTime',
								format : 'Y-m-d',
								 editor :this.datafield,
								 renderer : ZW.ux.dateRenderer(this.datafield)
							}, {
								header : '计息结束日期',
								dataIndex : 'interestEndTime',
								format : 'Y-m-d',
								 editor :this.datafield,
								 renderer : ZW.ux.dateRenderer(this.datafield)
							}],
							listeners : {
							scope : this,
							beforeedit : function(e) {
							
									if(e.record.data['afterMoney'] !=null &&e.record.data['afterMoney'] !=0) {
										e.cancel = true;
										Ext.Msg.alert('', '本款项已对过账，不能编辑');
									}
							
							
							},
								afteredit : function(e) {
									if(e.record.data['fundType'] =='principalLending' || 
										e.record.data['fundType'] =='FinancingInterest' || 
										e.record.data['fundType'] =='FinancingRepay' || 
										e.record.data['fundType'] =='financingconsultationMoney' || 
										e.record.data['fundType'] =='financingserviceMoney'
										){
											e.record.set('incomeMoney',0);
											e.record.commit();
								     }else{
								          e.record.set('payMoney',0);
												e.record.commit()
								     }
							  }
							}
						// end of columns
								});

					},
				autocreate : function() { 
				var businessType=this.businessType;
					var gridPanel1=this.gridPanel
					 var operationType=null;
					 var accrualtype=null;
					 var payaccrualType=null;
					 var projectMoney=null;
					 var isStartDatePay=null;
					 var startDate=null;
					 var payintentPerioDate=null;
					 var accrual=null;
					 var payintentPeriod=null;
					  var payintentPerioddays=null;
					  var intentDate=null;
					   var dayOfEveryPeriod=null;
					   var isPreposePayConsultingCheck=null;
					   var isPreposePayAccrual=null;
					   var isInterestByOneTime=null;
					   var dateMode=null;
					   var dayAccrualRate=null;
					   var dayManagementConsultingOfRate=null;
					   var dayFinanceServiceOfRate=null;
					// if(this.businessType=="SmallLoan"){
					  accrualtype=this.object.getCmpByName("flFinancingProject.accrualtype").getValue();
					   payaccrualType=this.object.getCmpByName("flFinancingProject.payaccrualType").getValue();
					    projectMoney=this.object.getCmpByName("flFinancingProject.projectMoney").getValue();
					   isStartDatePay=this.object.getCmpByName("flFinancingProject.isStartDatePay").getValue();
					   startDate=this.object.getCmpByName("flFinancingProject.startDate").getValue();
					   payintentPerioDate=this.object.getCmpByName("flFinancingProject.payintentPerioDate").getValue();
					   accrual=this.object.getCmpByName("flFinancingProject.accrual").getValue();
					   payintentPeriod=this.object.getCmpByName("flFinancingProject.payintentPeriod").getValue();
					   intentDate=this.object.getCmpByName("flFinancingProject.intentDate").getValue();
					     dayOfEveryPeriod=this.object.getCmpByName("flFinancingProject.dayOfEveryPeriod").getValue();
					     isPreposePayAccrual=this.object.getCmpByName("isPreposePayAccrualCheck").getValue();
					     isInterestByOneTime=this.object.getCmpByName('flFinancingProject.isInterestByOneTime').getValue();
					     dateMode=this.object.getCmpByName('flFinancingProject.dateMode').getValue();
					     dayAccrualRate=this.object.getCmpByName('flFinancingProject.dayAccrualRate').getValue();
					   
					// }
				
					   var calcutype=this.businessType;  //业务品种
					   var projId=this.projectId;
					   var message="";
					   if(accrualtype=="请选择" || accrualtype==null || accrualtype==""){
					     message="计息方式";
					   }else if(payaccrualType=="请选择" || payaccrualType==null){
					   	 message="付息方式";
					   }
							   
		
				 if(calcutype=="SmallLoan"){
							   
							    if(projectMoney=="" || projectMoney==null){
							    	message="融资金额";
							   }
							   
							   if(startDate=="" || startDate==null){
							   	 message="融资开始日期日期";
							   }
							    if(payintentPeriod=="" || payintentPeriod==null ){
							   	 message="融资期数";
							   }
							    if(accrual=="" || accrual==null){
							    	message="融资利率";
							   }
							   if(accrualtype=="ontTimeAccrual"){
							   
							   	   if(intentDate=="" || intentDate==null){
								   	 message="还款日期";
								   }
							   	
							   }else{
								    if(isStartDatePay=="1"){
								    
								    	if(payintentPerioDate=="" || payintentPerioDate==null){
									   	 message="固定在几号还款";
									   }
									  
								    }
							   
							     //  intentDate=startDate;
							     if(payaccrualType=="owerPay"){
							       if(dayOfEveryPeriod=="" || dayOfEveryPeriod==null){
							        message="自定义周期";
							       	
							       }
							     
							     	
							     }
							   }
							 
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
				
				
				
//		
	        	 if(calcutype=="SmallLoan" &&accrualtype=="ontTimeAccrual" && intentDate <= startDate){
	        	 
	        	 	    message="还款日期必须在放款日期之后"; 
		               Ext.MessageBox.show({
		                    title : '操作信息',
		                    msg : message,
		                    buttons : Ext.MessageBox.OK,
		                    icon : 'ext-mb-error'
		                });
		                 return null;
	        	 	
	        	 	
	        	 }
					   
						
						var params1 ={
							projectId : projId,
						    'flFinancingProject.accrualtype': accrualtype,
							 'flFinancingProject.isPreposePayAccrual': isPreposePayAccrual==false?0:1,
						    'flFinancingProject.payaccrualType': payaccrualType,
						    'flFinancingProject.projectMoney': projectMoney,
						    'flFinancingProject.startDate': startDate,
						    'flFinancingProject.intentDate': intentDate,
						    'flFinancingProject.isStartDatePay': isStartDatePay,
						    'flFinancingProject.payintentPerioDate': payintentPerioDate,
						     'flFinancingProject.accrual': accrual,
						        'flFinancingProject.payintentPeriod': payintentPeriod,
						         'flFinancingProject.dayOfEveryPeriod': dayOfEveryPeriod,
						          'flFinancingProject.isInterestByOneTime': isInterestByOneTime,
						         'flFinancingProject.dateMode':dateMode,
						         'flFinancingProject.dayAccrualRate':dayAccrualRate,
						     'calcutype':calcutype,
						     'flag1':0,
						     'projectId':projId,
						     'businessType':businessType,
						     isHaveLending:"no"
						    
						}
						
					   /* gridPanel1.getStore().setBaseParam('projectId',projId) ; 
					     gridPanel1.getStore().setBaseParam('slSmallloanProject.accrualtype', accrualtype);
					      gridPanel1.getStore().setBaseParam('slSmallloanProject.isPreposePayAccrual', isPreposePayAccrual);
					    gridPanel1.getStore().setBaseParam('slSmallloanProject.payaccrualType', payaccrualType);
					     gridPanel1.getStore().setBaseParam('slSmallloanProject.projectMoney', projectMoney);
					    gridPanel1.getStore().setBaseParam('slSmallloanProject.startDate', startDate);
					       gridPanel1.getStore().setBaseParam('slSmallloanProject.isStartDatePay', isStartDatePay);
					         gridPanel1.getStore().setBaseParam('slSmallloanProject.intentDate', intentDate);
					    gridPanel1.getStore().setBaseParam('slSmallloanProject.payintentPerioDate', payintentPerioDate);
					     gridPanel1.getStore().setBaseParam('slSmallloanProject.accrual', accrual);
					    gridPanel1.getStore().setBaseParam('slSmallloanProject.operationType',  this.businessType);
					     gridPanel1.getStore().setBaseParam('slSmallloanProject.financeServiceOfRate', 0);
					    gridPanel1.getStore().setBaseParam('slSmallloanProject.managementConsultingOfRate', managementConsultingOfRate);
					    gridPanel1.getStore().setBaseParam('slSmallloanProject.payintentPeriod', payintentPeriod);
					      gridPanel1.getStore().setBaseParam('slSmallloanProject.dayOfEveryPeriod', dayOfEveryPeriod);
					    gridPanel1.getStore().setBaseParam('calcutype', calcutype);
					    this.gridPanel.getStore().setBaseParam('flag1',0) ; */
					   var combox=new Ext.form.ComboBox({
						    triggerAction: 'all',
						    store: new Ext.data.SimpleStore({
								autoLoad : true,
								url : __ctxPath+ '/project/getPayIntentPeriodSlSmallloanProject.do',
								fields : ['itemId', 'itemName'],
								baseParams : {payintentPeriod:payintentPeriod}
							}),
						    valueField: 'itemId',
						    displayField: 'itemName'
				
						})
						gridPanel1.getColumnModel().setEditor(2,combox);
					
						var gridstore = gridPanel1.getStore();
						gridstore.on('beforeload', function(gridstore, o) {
							
							Ext.apply(o.params, params1);
						});
						
						
						var projectId = this.projectId;
						gridPanel1.getStore().reload();
                        var vRecords = this.gridPanel.getStore().getRange(0,
						this.gridPanel.getStore().getCount()); // 得到修改的数据（记录对象）
						var vCount = vRecords.length; // 得到记录长度
						var vDatas = '';
						if (vCount > 0) {
						for ( var i = 0; i < vCount; i++) {
							if(vRecords[i].data.afterMoney !=null && vRecords[i].data.afterMoney !=0){
								
								//Ext.Msg.alert('', '有款项已对过账不能被覆盖');
						    }
						  }
						}
							

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
							if(vRecords[i].data.fundType !=null && vRecords[i].data.fundType !=""){
								 if(vRecords[i].data.fundIntentId == null || vRecords[i].data.fundIntentId == "")  {
											  st={"fundType":vRecords[i].data.fundType,"incomeMoney":vRecords[i].data.incomeMoney,"payMoney":vRecords[i].data.payMoney,"intentDate":vRecords[i].data.intentDate,"remark":vRecords[i].data.remark};
											vDatas += Ext.util.JSON
													.encode(st) + '@';
									 }else{
									    if(vRecords[i].data.afterMoney==0){
									     st={"fundType":vRecords[i].data.fundType,"incomeMoney":vRecords[i].data.incomeMoney,"payMoney":vRecords[i].data.payMoney,"intentDate":vRecords[i].data.intentDate,"remark":vRecords[i].data.remark};
											vDatas += Ext.util.JSON
												.encode(st) + '@';
									 	}
									 }
						 
							   }
							}
							
							vDatas = vDatas.substr(0, vDatas.length - 1);
						}
						//Ext.getCmp('fundIntentJsonData2').setValue(vDatas);
//						this.gridPanel.getColumnsgetEditor().getCmpByName('fundIntentJsonData').setValue(vDatas);
//						alert(this.gridPanel.getEditor().getCmpByName('fundIntentJsonData').getValue());
						return vDatas;
					},
					affrim:function(){
					
						Ext.Ajax
								.request( {
									url : __ctxPath + '/webservice/affrimPrincipalLengingFundIntentPrincipalLending.do',
									method : 'POST',
									scope : this,
									params : {
										projectId : this.projectId,
										businessType :this.businessType
									},
									success : function(response, request) {

									}
								});
						
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
					save : function(){
						this.gridPanel.getStore().setBaseParam('flag1',1) ; 
						this.gridPanel.getStore().reload();

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