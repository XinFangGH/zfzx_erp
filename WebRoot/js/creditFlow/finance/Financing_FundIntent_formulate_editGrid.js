//var formulatestore = new HT.JsonStore({
//	url : __ctxPath + "/finance/listSlFundIntent.do"	
//});

Financing_FundIntent_formulate_editGrid = Ext.extend(Ext.Panel,
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
						Financing_FundIntent_formulate_editGrid.superclass.constructor
								.call(this, {
									layout:'anchor',
		  					        anchor : '100%',
		  					        name : 'Financing_FundIntent_formulate_editGrid_Panel',
									items : [
										{xtype:'panel',border:false,bodyStyle:'margin-bottom:5px',html : '<br/><B><font class="x-myZW-fieldset-title">【'+this.titleText+'】:</font></B>'},
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
								iconCls : 'btn-reset',
								text : '对账',
								xtype : 'button',
								hidden : this.isHiddenOperation,
								scope : this,
								handler : this.openliushuiwin 
							},new Ext.Toolbar.Separator({
								hidden : this.isHiddenOperation
							}),{
								iconCls : 'btn-info-add',
								scope : this,
								text : '生成',
								hidden : this.isHiddenOperation,
								handler : this.autocreate
							},new Ext.Toolbar.Separator({
								hidden : this.isHiddenOperation
							}), {
								iconCls : 'btn-del',
								scope : this,
								text : '删除',
								hidden : this.isHiddenOperation,
								handler : this.removeSelRs
							},new Ext.Toolbar.Separator({
								hidden : this.isHiddenOperation
							}), {
								text : '增加',
								iconCls : 'btn-add',
								scope : this,
								hidden : this.isHiddenOperation,
								handler : this.createRs
							},new Ext.Toolbar.Separator({
								hidden : this.isHiddenOperation
							}), {
								iconCls : 'btn-detail',
								text : '查看单项流水记录',
								xtype : 'button',
								scope : this,
								handler : function() {
									var selRs = this.gridPanel.getSelectionModel()
											.getSelections();
									if (selRs <= 0) {
										Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
										return;
									} else if (selRs.length > 1) {
										Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
										return;
									}
									this.fundIntentWaterReconciliationInfo.call(this, selRs[0],
											1);
								}
							},new Ext.Toolbar.Separator({
								hidden : this.isHiddenOperation
							}), {
								iconCls : 'slupIcon',
								text : '上传/下载凭证',
								xtype : 'button',
								scope : this,
								hidden : this.isHiddenOperation,
								handler : this.upload
							},new Ext.Toolbar.Separator({
								hidden : this.isHiddenOperation
							}),{
								iconCls : 'btn-preview',
								text :'预览凭证',
								xtype : 'button',
								scope : this,
								handler : this.preview
							},new Ext.Toolbar.Separator({
								hidden : this.isHiddenOperation
							}),{
								iconCls : 'btn-xls',
								text : '导出Excel',
								xtype : 'button',
								scope : this,
								hidden : this.isHiddenOperation,
								handler : function() {
									this.toExcel();
								}
							}]
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
							id : 'FundIntent_formulate_editGrid',
							url :__ctxPath + '/creditFlow/finance/listSlFundIntent.do',	
							baseParams :params1,
							fields : [ {
								name : 'fundIntentId'
							}, {
								name : 'fundType'
							}, {
								name : 'fundTypeName'
							}, {
								name : 'incomeMoney'
							}, {
								name : 'payMoney'
							}, {
								name : 'intentDate'
							}, {
								name : 'factDate'
							}, {
								name : 'afterMoney'
							}, {
								name : 'notMoney'
							}, {
								name : 'accrualMoney'
							}, {
								name : 'isValid'
							}, {
								name : 'flatMoney'
							}, {
								name : 'overdueRate'
							}, {
								name : 'isOverdue'
							}, {
								name : 'companyId'
							}, {
								name : 'payintentPeriod'
							},{
								name : 'interestStarTime'
							},{
								name : 'interestEndTime'
							},{
								name : 'remark'
							}],
							tbar : this.isHidden?null:this.ttbar,
							columns : [ {
								header : '融资期数',
								//hidden : true,
								dataIndex : 'payintentPeriod',
								editor : new Ext.form.ComboBox({
								    triggerAction: 'all',
								    readOnly : this.isHidden1,
								    store: new Ext.data.SimpleStore({
										autoLoad : true,
										url : __ctxPath+ '/project/getPayIntentPeriodSlSmallloanProject.do',
										fields : ['itemId', 'itemName'],
										baseParams : {payintentPeriod:typeof(this.object)!='undefined'?this.object.getCmpByName('flFinancingProject.payintentPeriod').getValue():0,projectId:this.projectId,businessType:this.businessType}
									}),
								    valueField: 'itemId',
								    displayField: 'itemName'
						
								}),
								renderer : function(value,metaData, record,rowIndex, colIndex,store){
									if (record.data.isValid == 1) {
										if(null!=value && value!=''){
											return '<font style="font-style:italic;text-decoration: line-through;color:gray">第'+value+'期</font>';
										}
									}else{
										if(null!=value && value!=''){
											return "第"+value+"期"
										}
									}
								}
							}, {
								header : '资金类型',
								dataIndex : 'fundType',
								width : 110,
								summaryType: 'count',
								summaryRenderer: totalMoney,
								editor :this.comboType,
								renderer : function(value,metaData, record,rowIndex, colIndex,store) {
									var objcom = this.editor;
									var objStore = objcom.getStore();
						
									var idx = objStore.find("dicKey", value);
									
									if (idx != "-1") {
										if (record.data.isValid == 1){
											return '<font style="font-style:italic;text-decoration: line-through;color:gray">'+objStore.getAt(idx).data.itemValue+'</font>';
										}else{
											return objStore.getAt(idx).data.itemValue;
										}
									} else {
								        
										if(record.data.fundTypeName==""){
											
											record.data.fundTypeName=value
											
										}else{
											var x = store.getModifiedRecords();
											if(x!=null && x!=""){
												record.data.fundTypeName=value
											}
										}
										if (record.data.isValid == 1){
											 return '<font style="font-style:italic;text-decoration: line-through;color:gray">'+record.get("fundTypeName")+'</font>'
										}else{
											 return record.get("fundTypeName")
										}
									}
									
						     }
								
							}, {
								header : '计划到账日',
//								xtype : 'datecolumn',
								format : 'Y-m-d',
								dataIndex : 'intentDate',
								sortable : true,
								fixed : true,
								width : 80,
								editor :this.datafield,
								renderer : function(value, metaData, record, rowIndex,colIndex, store) {
									var flag1 = 0; // incomeMoney
									if (record.data.payMoney != 0) { // payMoney
										flag1 = 1;
									}
									var v;
									try {
										if (typeof(value) == "string") {
											v = value;
										} else {
											v = Ext.util.Format.date(value, 'Y-m-d');
										}
									} catch (err) {
										v = value;
										return v;
									}
									if (v != null) {
										if (record.data.isValid == 1) {

											return '<font style="font-style:italic;text-decoration: line-through;color:gray">'
													+ v + "</font>";

										} else {
											if (record.data.notMoney == 0) {
												return '<font style="color:gray">' + v
														+ "</font>";
											}
											if (record.data.isOverdue == "是" && flag1 != 1) {

												return '<font style="color:red">' + v
														+ "</font>";
											}

											if (record.data.afterMoney == 0) {
												return v;

											}

											return v;
										}

									} else {
										return "";
									}

								}
							}, {
								header : '计划收入金额',
								dataIndex : 'incomeMoney',
								align : 'right',
								width : 127,
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
									 if (record.data.isValid == 1){
										return '<font style="font-style:italic;text-decoration: line-through;color:gray">'+Ext.util.Format.number(value,',000,000,000.00')+'元</font>'
									 }else{
									 	return Ext.util.Format.number(value,',000,000,000.00')+'元'
									 }
												  
								}
							}, {
								header : '计划支出金额',
								dataIndex : 'payMoney',
								align : 'right',
								width : 127,
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
									if (record.data.isValid == 1){
										return '<font style="font-style:italic;text-decoration: line-through;color:gray">'+Ext.util.Format.number(value,',000,000,000.00')+'元</font>'
									 }else{
									 	return Ext.util.Format.number(value,',000,000,000.00')+'元'
									 }
								}
							
							}, {
								header : '计息开始日期',
								dataIndex : 'interestStarTime',
								format : 'Y-m-d',
								 editor :this.datafield,
								width : 120,
								renderer : function(value, metaData, record, rowIndex,
										colIndex, store) {
									var flag1 = 0; // incomeMoney
									if (record.data.payMoney != 0) { // payMoney
										flag1 = 1;
									}
									var v;
									try {
										if (typeof(value) == "string") {
											v = value;
										} else {
											v = Ext.util.Format.date(value, 'Y-m-d');
										}
									} catch (err) {
										v = value;
										return v;
									}
									if (v != null) {
										if (record.data.isValid == 1) {

											return '<font style="font-style:italic;text-decoration: line-through;color:gray">'
													+ v + "</font>";

										} else {
											if (record.data.notMoney == 0) {
												return '<font style="color:gray">' + v
														+ "</font>";
											}
											if (record.data.isOverdue == "是" && flag1 != 1) {

												return '<font style="color:red">' + v
														+ "</font>";
											}

											if (record.data.afterMoney == 0) {
												return v;

											}

											return v;
										}

									} else {
										return "";
									}

								}
							}, {
								header : '计息结束日期',
								dataIndex : 'interestEndTime',
								format : 'Y-m-d',
								 editor :this.datafield,
								width : 120,
								renderer : function(value, metaData, record, rowIndex,
										colIndex, store) {
									var flag1 = 0; // incomeMoney
									if (record.data.payMoney != 0) { // payMoney
										flag1 = 1;
									}
									var v;
									try {
										if (typeof(value) == "string") {
											v = value;
										} else {
											v = Ext.util.Format.date(value, 'Y-m-d');
										}
									} catch (err) {
										v = value;
										return v;
									}
									if (v != null) {
										if (record.data.isValid == 1) {

											return '<font style="font-style:italic;text-decoration: line-through;color:gray">'
													+ v + "</font>";

										} else {
											if (record.data.notMoney == 0) {
												return '<font style="color:gray">' + v
														+ "</font>";
											}
											if (record.data.isOverdue == "是" && flag1 != 1) {

												return '<font style="color:red">' + v
														+ "</font>";
											}

											if (record.data.afterMoney == 0) {
												return v;

											}

											return v;
										}

									} else {
										return "";
									}

								}
							}, {
								header : '实际到账日',
								dataIndex : 'factDate',
								format : 'Y-m-d',
								width : 80,
								renderer : function(value, metaData, record, rowIndex,
										colIndex, store) {
									var flag1 = 0; // incomeMoney
									if (record.data.payMoney != 0) { // payMoney
										flag1 = 1;
									}
									var v;
									try {
										if (typeof(value) == "string") {
											v = value;
										} else {
											v = Ext.util.Format.date(value, 'Y-m-d');
										}
									} catch (err) {
										v = value;
										return v;
									}
									if (v != null) {
										if (record.data.isValid == 1) {

											return '<font style="font-style:italic;text-decoration: line-through;color:gray">'
													+ v + "</font>";

										} else {
											if (record.data.notMoney == 0) {
												return '<font style="color:gray">' + v
														+ "</font>";
											}
											if (record.data.isOverdue == "是" && flag1 != 1) {

												return '<font style="color:red">' + v
														+ "</font>";
											}

											if (record.data.afterMoney == 0) {
												return v;

											}

											return v;
										}

									} else {
										return "";
									}

								}
							}, {
								header : '已对账金额',
								dataIndex : 'afterMoney',
								align : 'right',
								summaryType : 'sum',
								renderer : function(value, metaData, record, rowIndex,
										colIndex, store) {
									var flag1 = 0; // incomeMoney
									if (record.data.payMoney != 0) { // payMoney
										flag1 = 1;
									}
									if (value != null) {

										if (record.data.isValid == 1) {
											return '<font style="font-style:italic;text-decoration: line-through;color:gray">'
													+ Ext.util.Format.number(value,
															',000,000,000.00') + "元</font>"
										} else {
											if (record.data.notMoney == 0) {
												return '<font style="color:gray">'
														+ Ext.util.Format.number(value,
																',000,000,000.00') + "元"
														+ "</font>";
											}
											if (record.data.isOverdue == "是" && flag1 != 1) {

												return '<font style="color:red">'
														+ Ext.util.Format.number(value,
																',000,000,000.00') + "元"
														+ "</font>";
											}

											if (record.data.afterMoney == 0) {
												return Ext.util.Format.number(value,
														',000,000,000.00')
														+ "元"

											}

											return Ext.util.Format.number(value,
													',000,000,000.00')
													+ "元"
										}
									} else
										return "";

								}
							}, {
								header : '未对账金额',
								dataIndex : 'notMoney',
								align : 'right',
								summaryType : 'sum',
								renderer : function(value, metaData, record, rowIndex,
										colIndex, store) {
									var flag1 = 0; // incomeMoney
									if (record.data.payMoney != 0) { // payMoney
										flag1 = 1;
									}
									if (value != null) {

										if (record.data.isValid == 1) {
											return '<font style="font-style:italic;text-decoration: line-through;color:gray">'
													+ Ext.util.Format.number(value,
															',000,000,000.00') + "元</font>"
										} else {
											if (record.data.notMoney == 0) {
												return '<font style="color:gray">'
														+ Ext.util.Format.number(value,
																',000,000,000.00') + "元"
														+ "</font>";
											}
											if (record.data.isOverdue == "是" && flag1 != 1) {

												return '<font style="color:red">'
														+ Ext.util.Format.number(value,
																',000,000,000.00') + "元"
														+ "</font>";
											}

											if (record.data.afterMoney == 0) {
												return Ext.util.Format.number(value,
														',000,000,000.00')
														+ "元"

											}

											return Ext.util.Format.number(value,
													',000,000,000.00')
													+ "元"
										}
									} else
										return "";
								}
							}, {
								header : '已对账金额',
								dataIndex : 'flatMoney',
								align : 'right',
								summaryType : 'sum',
								renderer : function(value, metaData, record, rowIndex,
										colIndex, store) {
									var flag1 = 0; // incomeMoney
									if (record.data.payMoney != 0) { // payMoney
										flag1 = 1;
									}
									if (value != null) {
										if (record.data.isValid == 1) {
											return '<font style="font-style:italic;text-decoration: line-through;color:gray">'
													+ Ext.util.Format.number(value,
															',000,000,000.00') + "元</font>"
										} else {
											if (record.data.notMoney == 0) {
												return '<font style="color:gray">'
														+ Ext.util.Format.number(value,
																',000,000,000.00') + "元"
														+ "</font>";
											}
											if (record.data.isOverdue == "是" && flag1 != 1) {

												return '<font style="color:red">'
														+ Ext.util.Format.number(value,
																',000,000,000.00') + "元"
														+ "</font>";
											}

											if (record.data.afterMoney == 0) {
												return Ext.util.Format.number(value,
														',000,000,000.00')
														+ "元"

											}

											return Ext.util.Format.number(value,
													',000,000,000.00')
													+ "元"
										}
									} else
										return "";

								}

							}, {
								header : '备注',
								dataIndex : 'remark',
								align : 'center',
								width : 100,
								editor : new Ext.form.TextField( {
									allowBlank : false,
									readOnly:this.isHidden
								})
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
						var gridPanel1=this.gridPanel
						 var operationType=null;
						 var accrualtype=null;
						 var payaccrualType=null;
						 var projectMoney=null;
						 var isStartDatePay=null;
						 var startDate=null;
						 var payintentPerioDate=null;
						 var accrual=null;
						 var financeServiceOfRate=null;   
						 var managementConsultingOfRate=null;
						 var payintentPeriod=null;
						  var payintentPerioddays=null;
						  var intentDate=null;
						   var dayOfEveryPeriod=null;
						    var isPreposePayAccrual=null;
						    var isInterestByOneTime=null;
						    var dateMode=null;
						    var dayAccrualRate=null;
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
						      isPreposePayAccrual=this.object.getCmpByName("flFinancingProject.isPreposePayAccrual").getValue();
					 isInterestByOneTime=this.object.getCmpByName('flFinancingProject.isInterestByOneTime').getValue();
					     dateMode=this.object.getCmpByName('flFinancingProject.dateMode').getValue();
					     dayAccrualRate=this.object.getCmpByName('flFinancingProject.dayAccrualRate').getValue();
						   var calcutype=this.businessType;  //业	务品种
						   var projId=this.projectId;
						   var isPreposePayAccrualCheck=this.object.getCmpByName("isPreposePayAccrualCheck").getValue();
						   var message="";
						   if(accrualtype=="请选择" || accrualtype==null || accrualtype==""){
						     message="计息方式";
						   }else if(payaccrualType=="请选择" || payaccrualType==null){
						   	 message="付息方式";
						   }
								   
			
								   
								    if(projectMoney=="" || projectMoney==null){
								    	message="融资金额";
								   }
								   
								   if(startDate=="" || startDate==null){
								   	 message="融资日期";
								   }
								    if(payintentPeriod=="" || payintentPeriod==null ){
								   	 message="融资期限";
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
								   
								      // intentDate=startDate;
								     if(payaccrualType=="owerPay"){
								       if(dayOfEveryPeriod=="" || dayOfEveryPeriod==null){
								        message="自定义周期";
								       	
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
		        	 if(accrualtype=="ontTimeAccrual" && intentDate <= startDate){
		        	 
		        	 	    message="还款日期必须在放款日期之后"; 
			               Ext.MessageBox.show({
			                    title : '操作信息',
			                    msg : message,
			                    buttons : Ext.MessageBox.OK,
			                    icon : 'ext-mb-error'
			                });
			                 return null;
		        	 	
		        	 	
		        	 }
					
//							
//							params1 ={
//								projectId : projId,
//							    'slSmallloanProject.accrualtype': accrualtype,
////										    'slSmallloanProject.dayOfRate': dayOfRate,
//							    'slSmallloanProject.isPreposePayAccrual': isPreposePayAccrual,
//							    'slSmallloanProject.payaccrualType': payaccrualType,
//							    'slSmallloanProject.projectMoney': projectMoney,
//							    'slSmallloanProject.startDate': startDate,
//							    'slSmallloanProject.intentDate': intentDate,
//							    'slSmallloanProject.dateMode': dateMode,
//							     'slSmallloanProject.accrual': accrual,
//							     'slSmallloanProject.operationType': operationType,
//							     'calcutype':calcutype
//							    
//							}
							var params1 ={
							projectId : projId,
						    'flFinancingProject.accrualtype': accrualtype,
							 'flFinancingProject.isPreposePayAccrual': isPreposePayAccrual,
						    'flFinancingProject.payaccrualType': payaccrualType,
						    'flFinancingProject.projectMoney': projectMoney,
						    'flFinancingProject.startDate': startDate,
						    'flFinancingProject.intentDate': intentDate,
						    'flFinancingProject.isStartDatePay': isStartDatePay,
						    'flFinancingProject.payintentPerioDate': payintentPerioDate,
						        'flFinancingProject.payintentPeriod': payintentPeriod,
						         'flFinancingProject.dayOfEveryPeriod': dayOfEveryPeriod,
						          'flFinancingProject.isInterestByOneTime': isInterestByOneTime,
						         'flFinancingProject.dateMode':dateMode,
						         'flFinancingProject.dayAccrualRate':dayAccrualRate,
						     'calcutype':calcutype,
						     'flag1':0,
						     'projectId':projId,
						     'businessType':this.businessType,
						     isHaveLending:"yes"
						    
						}
						   var gridstore = gridPanel1.getStore();
							gridstore.on('beforeload', function(gridstore, o) {
								
								Ext.apply(o.params, params1);
							});
							  var combox=new Ext.form.ComboBox({
						    triggerAction: 'all',
						    store: new Ext.data.SimpleStore({
								autoLoad : true,
								url : __ctxPath+ '/flFinancing/getPayIntentPeriodFlFinancingProject.do',
								fields : ['itemId', 'itemName'],
								baseParams : {payintentPeriod:payintentPeriod}
							}),
						    valueField: 'itemId',
						    displayField: 'itemName'
				
						})
						gridPanel1.getColumnModel().setEditor(2,combox);
							gridPanel1.getStore().reload();
	                        var vRecords = this.gridPanel.getStore().getRange(0,
							this.gridPanel.getStore().getCount()); // 得到修改的数据（记录对象）
							var vCount = vRecords.length; // 得到记录长度
							var vDatas = '';
							if (vCount > 0) {
							for ( var i = 0; i < vCount; i++) {
								if(vRecords[i].data.afterMoney !=null && vRecords[i].data.afterMoney !=0){
									
									Ext.Msg.alert('', '有款项已对过账不能被覆盖');
							    }
							  }
							}
								

						},

					createRs : function() {
						var payintentPeriod = (typeof(this.object)!='undefined'?this.object.getCmpByName('flFinancingProject.payintentPeriod').getValue():this.payintentPeriod);
						this.datafield.setValue('');
						var newRecord = this.gridPanel.getStore().recordType;
						var newData = new newRecord( {
							fundIntentId : '',
							fundType : '',
							payMoney : 0,
							incomeMoney : 0,
							intentDate : new Date(),
							remark : '',
							payintentPeriod:payintentPeriod
						});
						var combox=new Ext.form.ComboBox({
						    triggerAction: 'all',
						    store: new Ext.data.SimpleStore({
								autoLoad : true,
								url : __ctxPath+ '/flFinancing/getPayIntentPeriodFlFinancingProject.do',
								fields : ['itemId', 'itemName'],
								baseParams : {payintentPeriod:payintentPeriod}
							}),
						    valueField: 'itemId',
						    displayField: 'itemName'
				
						})
						this.gridPanel.getColumnModel().setEditor(2,combox);
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
								 if(vRecords[i].data.afterMoney == null || vRecords[i].data.afterMoney == 0)  {
											  st={"fundType":vRecords[i].data.fundType,"incomeMoney":vRecords[i].data.incomeMoney,"payMoney":vRecords[i].data.payMoney,"intentDate":vRecords[i].data.intentDate,"remark":vRecords[i].data.remark,"payintentPeriod":vRecords[i].data.payintentPeriod,"interestStarTime":vRecords[i].data.interestStarTime,"interestEndTime":vRecords[i].data.interestEndTime};
											vDatas += Ext.util.JSON
													.encode(st) + '@';
									 }/*else{
									    if(vRecords[i].data.afterMoney==0){
									     st={"fundType":vRecords[i].data.fundType,"incomeMoney":vRecords[i].data.incomeMoney,"payMoney":vRecords[i].data.payMoney,"intentDate":vRecords[i].data.intentDate,"remark":vRecords[i].data.remark,"payintentPeriod":vRecords[i].data.payintentPeriod};
											vDatas += Ext.util.JSON
												.encode(st) + '@';
									 	}
									 }*/
						 
							   }
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
					save : function(){
						this.gridPanel.getStore().setBaseParam('flag1',1) ; 
						this.gridPanel.getStore().reload();

					},
					// 查看款项流水对账
					fundIntentWaterReconciliationInfo : function(record, flag) {
						var hidden = false;
						if (record.data.fundType == "principalLending") {
							hidden = true;
						}
						new detailView({
							fundIntentId : record.get("fundIntentId"),
							flag : flag,// 1.款项流水2.项目流水,
							fundType : record.data.fundType,
							hidden1 : hidden,
							hidden2 : true
						}).show();
					},
					toExcel : function() {
						var projectId = this.projectId;
						var businessType = this.businessType;
						window.open(__ctxPath
								+ "/creditFlow/finance/downloadSlFundIntent.do?projectId="
								+ projectId + "&businessType=" + businessType, '_blank');

					},
					chargecheck : function() {
						var s = this.gridPanel.getSelectionModel().getSelections();
						if (s <= 0) {
							Ext.ux.Toast.msg('操作信息', '请选中一条记录');
							return false;
						} else if (s.length > 1) {
							Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
							return false;
						} else {
							var record = s[0];
							if (record.data.fundIntentId == "" || record.data.fundIntentId == null) {
								Ext.ux.Toast.msg('操作信息', '请先保存再对账');

							} else if (record.data.notMoney == 0) {
								Ext.ux.Toast.msg('操作信息', '没有未对账金额');

							} else {
								new CashQlideAndCheckForm({
									obj : this.gridPanel,
									fundTypeName : record.data.fundTypeName,
									payMoney : record.data.payMoney == 0
											? null
											: record.data.payMoney,
									notMoney : record.data.notMoney == 0
											? null
											: record.data.notMoney,
									fundIntentId : record.data.fundIntentId
								}).show();
							}

						}

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

					},
					upload:function(record){
						var s = this.gridPanel.getSelectionModel().getSelections();
						if (s <= 0) {
							Ext.ux.Toast.msg('操作信息','请选中一条记录');
							return false;
						}else if(s.length>1){
							Ext.ux.Toast.msg('操作信息','只能选中一条记录');
							return false;
						}else{	
							var	record=s[0];
							var	projectId = this.projectId;
							var businessType= this.businessType;
							var fundIntentId = record.data.fundIntentId;
							var fundType = record.data.fundType;
							var setname = '';
							var titleName ='';
							var tableName ='';
							if(fundType == 'Financingborrow') {
								setname ='融资本金借入凭证';
								titleName ='融资本金借入凭证';
								tableName ='sl_fund_intent_Financingborrow';
							}else if(fundType == 'FinancingInterest') {
								setname ='融资利息支付凭证';
								titleName ='融资利息支付凭证';
								tableName ='sl_fund_intent_FinancingInterest';
							}else {
								setname ='融资本金还款凭证';
								titleName ='融资本金还款凭证';
								tableName ='sl_fund_intent_FinancingRepay';
							}
							var typeisfile ='fundIntentId.'+fundIntentId;
							var mark=tableName+"."+projectId;
							uploadReportJS('上传/下载'+titleName+'文件',typeisfile,mark,15,null,null,null,projectId,businessType,setname);
						}
			
					},
					preview:function(record){
						var s = this.gridPanel.getSelectionModel().getSelections();
						if (s <= 0) {
							Ext.ux.Toast.msg('操作信息','请选中一条记录');
							return false;
						}else if(s.length>1){
							Ext.ux.Toast.msg('操作信息','只能选中一条记录');
							return false;
						}else{	
							var	record=s[0];
							var	projectId = this.projectId;
							var businessType= this.businessType;
							var fundIntentId=record.data.fundIntentId;
							var fundType = record.data.fundType;
							var typeisfile ='typeisToFinancingborrow';
							if(fundType == 'Financingborrow') {
								typeisfile ='typeisToFinancingborrow';
							}else if(fundType == 'FinancingInterest') {
								typeisfile ='typeisToFinancingInterest';
							}else {
								typeisfile ='typeisToFinancingRepay';
							}
							var remark='fundIntentId.'+fundIntentId;
							picViewer(remark,false,typeisfile);
						}
			
					},
					oneopenliushuiwin : function(){
		
		var s = this.gridPanel.getSelectionModel().getSelections();
		var	record=s[0];
	     var flag=0;            //incomeMoney
	     if(record.data.payMoney !=0){   //payMoney
	     	flag=1;
	     }
	     if(record.data.fundIntentId ==null){
	     		Ext.ux.Toast.msg('操作信息','请先保存');
	     }else{
			new SlFundIntentForm({
				fundIntentId : record.data.fundIntentId,
				fundType : record.data.fundType,
				notMoney : record.data.notMoney,
				flag:flag,
				businessType :record.data.businessType,
				companyId:record.data.companyId,
				otherPanel : this.gridPanel
			}).show();
	     }
		
	},
	manyInntentopenliushuiwin : function(){
		
		var s = this.gridPanel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息','请选中记录');
			return false;
		}else{
	    var a=0;
	    var b=0;
	    var sumnotMoney=0;
	    for(var i=0;i<s.length;i++){
	    	if(s[i].data.payMoney >0)
	    	a++;
	    	if(s[i].data.incomeMoney >0)
		    b++;
	    	sumnotMoney=sumnotMoney+s[i].data.notMoney;
	    }
	    if(a>0 && b>0){
	    	Ext.ux.Toast.msg('操作信息','请选中的记录支出保持一致');
			return false;
	    	
	    }
	    
			var ids = $getGdSelectedIds(this.gridPanel,'fundIntentId');
			var	record=s[0];
			var flag=0;            //incomeMoney
		     if(record.data.payMoney !=0){   //payMoney
		     	flag=1;
		     } 
		     var count=0;
		     for(var i=0;i<ids.length;i++){
		       if(s[i].data.fundIntentId ==null)count++;
		     }
		     
		     if(count>0){
		     		Ext.ux.Toast.msg('操作信息','请先保存');
		     }else{
				new SlFundIntentForm1({
					ids : ids,
					flag:flag,
					fundType : record.data.fundType,
					sumnotMoney :sumnotMoney,
					companyId:record.data.companyId,
					otherPanel:this.gridPanel
				}).show();
		     }
		}	
		
	},
	openliushuiwin : function() {
		var s = this.gridPanel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息','请选中记录');
			return false;
		}else if(s.length>1){
			this.manyInntentopenliushuiwin();
			
		}else if(s.length==1){
		   this.oneopenliushuiwin();
		}
	}

				});