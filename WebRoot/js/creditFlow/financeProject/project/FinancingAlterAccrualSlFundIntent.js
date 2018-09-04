//var formulatestore = new HT.JsonStore({
//	url : __ctxPath + "/finance/listSlFundIntent.do"	
//});

FinancingAlterAccrualSlFundIntent = Ext.extend(Ext.Panel,
				{ 
						isThisAlterAccrualRecord : null,
			            isThisAlterAccrualRecordId :null,
						isUnLoadData :false,
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
						if (typeof (_cfg.isThisAlterAccrualRecord) != "undefined") {
						this.isThisAlterAccrualRecord = _cfg.isThisAlterAccrualRecord;
						}
						if(typeof(_cfg.isUnLoadData)!="undefined")
						{
						      this.isUnLoadData=_cfg.isUnLoadData;
						}
						if(typeof(_cfg.isThisAlterAccrualRecordId)!="undefined")
						{
						      this.isThisAlterAccrualRecordId=_cfg.isThisAlterAccrualRecordId;
						}
						
						if(typeof(_cfg.object)!="undefined")
						{
						      this.object=_cfg.object;
						}
//					  this.businessType="Financing";
						Ext.applyIf(this, _cfg);
						// 必须先初始化组件
						this.initUIComponents();
						FinancingAlterAccrualSlFundIntent.superclass.constructor
								.call(this, {
									layout:'anchor',
		  					        anchor : '100%',
									items : [
										{xtype:'panel',border:false,bodyStyle:'margin-bottom:5px',html : '<br/><B><font class="x-myZW-fieldset-title">【'+this.titleText+'】:</font></B>'},
										this.gridPanel
									]
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
								text : '增加',
								iconCls : 'btn-add',
								scope : this,
								handler : this.createRs
							}, '-', {
								iconCls : 'btn-del',
								scope : this,
								text : '删除',
								handler : this.removeSelRs
							}
							, '-', {
								iconCls : 'btn-info-add',
								scope : this,
								text : '生成',
								handler : this.autocreate
							}
							]
						});
						this.datafield=new Ext.form.DateField( {
									format : 'Y-m-d',
									allowBlank : false,
									readOnly:this.isHidden
								})
						var fundTypenodekey=""; 
						
						if(this.businessType=="SmallLoan"){
						fundTypenodekey="financeType";
						}else if(this.businessType=="Financing"){
							fundTypenodekey="finaning_fund";
						}
						this.comboType= new DicIndepCombo({
									editable : false,
									readOnly:this.isHidden,
									lazyInit : false,
									forceSelection : false,
									nodeKey : fundTypenodekey
								})
								this.comboType.store.reload();
					var params1={
										projectId : this.projectId,
									     flag1:1,
									     businessType:this.businessType
									};
				url=__ctxPath+ "/creditFlow/finance/listbyAlterAccrualRecordSlFundIntent.do?isThisAlterAccrualRecordId="+this.isThisAlterAccrualRecordId+"&isThisAlterAccrualRecord="+this.isThisAlterAccrualRecord+"&isUnLoadData="+this.isUnLoadData;
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
							id : 'AlterAccrualSlFundIntent',
							url :url,
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
							},{
								name : 'payintentPeriod'
							},{
								name : 'slSuperviseRecordId'
							},{
								name : 'afterMoney'
							},{
								name : 'interestStarTime'
							},{
								name : 'interestEndTime'
							}],
							tbar : this.isHidden?null:this.ttbar,
							columns : [{
								header : '期数',
								dataIndex : 'payintentPeriod',
								editor : new Ext.form.ComboBox({
								    triggerAction: 'all',
								    readOnly:this.isHidden,
								    store: new Ext.data.SimpleStore({
										autoLoad : true,
										url : __ctxPath+ '/smallLoan/finance/getPrepayintentPeriodSlEarlyRepaymentRecord.do',
										fields : ['itemId', 'itemName'],
										baseParams : {projectId:this.projectId}
									}),
								    valueField: 'itemId',
								    displayField: 'itemName',
								    listeners : {
								    	scope : this,
								    	'select' : function(combox,record,index){
								    		var value=combox.getValue();
								    		var value1=value.substring(value.lastIndexOf('.')+1,value.length);
								    		combox.setValue(value1)
								    		var value2=value.substring(0,value.lastIndexOf('.'))
								    		if(value2!=0){
								    			this.gridPanel.getSelectionModel().getSelected().data.slSuperviseRecordId=value2
								    		}else{
								    			this.gridPanel.getSelectionModel().getSelected().data.slSuperviseRecordId=null
								    		}
								    	}
								    }
								}),
								renderer : function(value, metaData, record, rowIndex,colIndex, store){
									if((null==record.data.slSuperviseRecordId || record.data.slSuperviseRecordId==0) && record.data.fundType!='principalLending'){
										return "第"+value+"期";
									}else if(null!=record.data.slSuperviseRecordId && record.data.slSuperviseRecordId!=0){
										return "展期第"+value+"期"
									}else{
										return value
									}
								}
							}, {
								header : '资金类型',
								dataIndex : 'fundType',
								width : 170,
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
								width : 80,
								renderer : ZW.ux.dateRenderer(this.datafield),
								editor :this.datafield
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
									 
													return Ext.util.Format.number(value,',000,000,000.00')+"元"
												  
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
													return Ext.util.Format.number(value,',000,000,000.00')+"元"
												  
								}
							
							}, {
								header : '计息开始日期',
								dataIndex : 'interestStarTime',
								format : 'Y-m-d',
								 editor :this.datafield,
								width : 120,
								renderer : ZW.ux.dateRenderer(this.datafield)
							}, {
								header : '计息结束日期',
								dataIndex : 'interestEndTime',
								format : 'Y-m-d',
								 editor :this.datafield,
								width : 120,
								renderer : ZW.ux.dateRenderer(this.datafield)
							}, {
								header : '备注',
								dataIndex : 'remark',
								align : 'center',
								width : 568,
								editor : new Ext.form.TextField( {
									allowBlank : false,
									readOnly:this.isHidden
								})
							}/*, { 
								
								header :'',
								dataIndex : '',
								scope : this,
								editor : new Ext.form.TextField( {
									id : 'fundIntentJsonData2',
									name : 'fundIntentJsonData',
									allowBlank : true
								})
							} */],
							listeners : {
								afteredit : function(e) {
									if(e.record.data['fundType'] =='principalLending' || e.record.data['fundType'] =='backInterest' || e.record.data['fundType'] =='FinancingInterest' || e.record.data['fundType'] =='FinancingRepay' ){
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
					 var projId=this.projectId;
					
				     var gridPanel1=this.gridPanel
					
					 var accrual=null;
				
					 var alelrtDate=null;
					 if(this.businessType=="SmallLoan" || this.businessType=="Financing"){
					   // alterProjectMoney=this.object.getCmpByName("slAlterAccrualRecord.alterProjectMoney").getValue();
					   accrual=this.object.getCmpByName("slAlterAccrualRecord.accrual").getValue();
					    alelrtDate=this.object.getCmpByName("slAlterAccrualRecord.alelrtDate").getValue();
					    // alterpayintentPeriod=this.object.getCmpByName("slAlterAccrualRecord.alterpayintentPeriod").getValue();
					    // alterpayintentPeriod1=this.object.getCmpByName("alterpayintentPeriod").getValue();
					 }
				
					   var calcutype=this.businessType;  //业务品种
					   var projId=this.projectId;
					   var message="";
							   
							   if(alelrtDate=="" || alelrtDate==null){
							    	message="利率变更日期";
							   }
							    if(accrual=="" || accrual==null){
							    	message="贷款利率";
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

					var params1 ={
							projectId : projId,
						    'slAlterAccrualRecord.accrual': accrual,
							 'slAlterAccrualRecord.alelrtDate': alelrtDate,
						     'calcutype':calcutype,
						     'flag1':0,
						     'projectId':projId,
						     'businessType':this.businessType
						    
						}
					
						var gridstore = gridPanel1.getStore();
						gridstore.on('beforeload', function(gridstore, o) {
							
							Ext.apply(o.params, params1);
						});
						
					   
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
						//var alterpayintentPeriod=this.object.getCmpByName("alterpayintentPeriod").getValue()
						var alterpayintentPeriod=this.object.getCmpByName("slAlterAccrualRecord.alterpayintentPeriod").getValue();
						var payintentPeriod=alterpayintentPeriod.substring(alterpayintentPeriod.lastIndexOf('.')+1,alterpayintentPeriod.length)
						var slSuperviseRecordId=alterpayintentPeriod.substring(0,alterpayintentPeriod.lastIndexOf('.'))
						this.datafield.setValue('');
						var newRecord = this.gridPanel.getStore().recordType;
						var newData = new newRecord( {
							fundIntentId : '',
							fundType : '',
							payMoney : 0,
							incomeMoney : 0,
							intentDate : new Date(),
							remark : '',
							//payintentPeriod : null,//payintentPeriod,
							payintentPeriod : payintentPeriod,
							slSuperviseRecordId:null
						});
						var combox=new Ext.form.ComboBox({
						    triggerAction: 'all',
						    store: new Ext.data.SimpleStore({
								autoLoad : true,
								url : __ctxPath+ '/smallLoan/finance/getPrepayintentPeriodSlEarlyRepaymentRecord.do',
								fields : ['itemId', 'itemName'],
								baseParams : {projectId:this.projectId}
							}),
						    valueField: 'itemId',
						    displayField: 'itemName',
						    listeners : {
						    	scope : this,
						    	'select' : function(combox,record,index){
						    		var value=combox.getValue();
						    		var value1=value.substring(value.lastIndexOf('.')+1,value.length);
						    		combox.setValue(value1)
						    		var value2=value.substring(0,value.lastIndexOf('.'))
						    		if(value2!=0){
						    			this.gridPanel.getSelectionModel().getSelected().data.slSuperviseRecordId=value2
						    		}else{
						    			this.gridPanel.getSelectionModel().getSelected().data.slSuperviseRecordId=null
						    		}
						    		//this.gridPanel.getView().refresh()
						    	}
						    }
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
								//var payintentPeriod = typeof(payintentPeriodLlbg)!='undefined'?this.payintentPeriodLlbg:vRecords[i].data.payintentPeriod;
								if(vRecords[i].data.fundType !=null && vRecords[i].data.fundType !=""){
									 if(vRecords[i].data.afterMoney==null || vRecords[i].data.afterMoney == 0)  {
											  st={"fundType":vRecords[i].data.fundType,"incomeMoney":vRecords[i].data.incomeMoney,"payMoney":vRecords[i].data.payMoney,"intentDate":vRecords[i].data.intentDate,"remark":vRecords[i].data.remark,'payintentPeriod':vRecords[i].data.payintentPeriod==''?0:vRecords[i].data.payintentPeriod,'slSuperviseRecordId':vRecords[i].data.slSuperviseRecordId,"interestStarTime":vRecords[i].data.interestStarTime,"interestEndTime":vRecords[i].data.interestEndTime};
											vDatas += Ext.util.JSON
													.encode(st) + '@';
									 }/*else{
									     st={"fundIntentId":vRecords[i].data.fundIntentId,"fundType":vRecords[i].data.fundType,"incomeMoney":vRecords[i].data.incomeMoney,"payMoney":vRecords[i].data.payMoney,"intentDate":vRecords[i].data.intentDate,"remark":vRecords[i].data.remark,'payintentPeriod':vRecords[i].data.payintentPeriod==''?0:vRecords[i].data.payintentPeriod,'slSuperviseRecordId':vRecords[i].data.slSuperviseRecordId};
											vDatas += Ext.util.JSON
													.encode(st) + '@';
									 
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