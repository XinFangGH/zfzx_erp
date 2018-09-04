//var formulatestore = new HT.JsonStore({
//	url : __ctxPath + "/finance/listSlFundIntent.do"	
//});

Guarantee_FundIntent_formulate_editGrid = Ext.extend(Ext.Panel,
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
						Guarantee_FundIntent_formulate_editGrid.superclass.constructor
								.call(this, {
									layout:'anchor',
		  					        anchor : '100%',
									items : [
										{xtype:'panel',border:false,bodyStyle:'margin-bottom:5px',html : '<br/><B><font class="x-myZW-fieldset-title">【款项计划】:</font></B>'},
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
								text : '增加',
								iconCls : 'btn-add',
								scope : this,
								handler : this.createRs
							}, '-', {
								iconCls : 'btn-del',
								scope : this,
								text : '删除',
								handler : this.removeSelRs
							}, '-', {
								iconCls : 'btn-info-add',
								scope : this,
								text : '生成',
								handler : this.autocreate
							}]
						});
						this.datafield=new Ext.form.DateField( {
									format : 'Y-m-d',
									allowBlank : false,
									readOnly:this.isHidden
								})
						this.comboType= new DicIndepCombo({
									editable : true,
									readOnly:this.isHidden,
									lazyInit : false,
									forceSelection : false,
									//xtype : 'csdiccombo',
						//			itemVale : 1149,
									nodeKey : "Guarantee_fund"
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
							id : 'Guarantee_FundIntent_formulate_editGrid',
							url :__ctxPath + '/creditFlow/finance/listbyGuranteeSlFundIntent.do',	
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
							} ],
							tbar : this.isHidden?null:this.ttbar,
							columns : [ {
								header : '手续收支类型',
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
								scope : this,
							    beforeedit : function(e) {
							
									if(e.record.data['afterMoney'] !=null &&e.record.data['afterMoney'] !=0) {
										e.cancel = true;
										Ext.Msg.alert('', '本款项已对过账，不能编辑');
									}
							
							
							},
								afteredit : function(e) {
										if(e.record.data['fundType'] !='BackCustomGuarantMoney'){
												e.record.set('payMoney',0);
												e.record.commit()
										}
										if(e.record.data['fundType'] =='BackCustomGuarantMoney'){
											e.record.set('incomeMoney',0);
											e.record.commit();
								}
							  }
							}
						// end of columns
								});

					},
				autocreate : function() { 
					var gridPanel1=this.gridPanel
					
					var calcutype=this.businessType;  //业务品种
					   var projId=this.projectId;
					   
					 var operationType=null;
					 var premiumRate=null;
					 var acceptDate=null;
					 var projectMoney=null;
					 var intentDate=null;
					 var earnestmoney=null;
					 var customerEarnestmoneyScale=null;
					 premiumRate=this.object.getCmpByName("gLGuaranteeloanProject.premiumRate").getValue();
					   acceptDate=this.object.getCmpByName("gLGuaranteeloanProject.acceptDate").getValue();
					    projectMoney=this.object.getCmpByName("gLGuaranteeloanProject.projectMoney").getValue();
					    intentDate=this.object.getCmpByName("gLGuaranteeloanProject.intentDate").getValue();
					   earnestmoney=this.object.getCmpByName("gLGuaranteeloanProject.earnestmoney").getValue();
					   customerEarnestmoneyScale=this.object.getCmpByName("gLGuaranteeloanProject.customerEarnestmoneyScale").getValue();
					   
					   var message="";
					   if(projectMoney=="" || projectMoney==null){
					     message="贷款金额";
					   }else if(premiumRate=="" || premiumRate==null){
					     message="保费费率";
					   }else if(acceptDate=="" || acceptDate==null){
					   	 message="担保起始日期";
					   } else if(intentDate==null || intentDate==""){
					    message="担保截止日期";
					   }else if(earnestmoney ===""  || earnestmoney==null){
							    	 message="保证金金额";
					  } else if(customerEarnestmoneyScale ==="" || customerEarnestmoneyScale==null){
							   message="向客户收取的保证金比例";
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
				
				
				
				acceptDate=acceptDate.format("Y-m-d");
                intentDate=intentDate.format("Y-m-d");	 
				if(intentDate <= acceptDate)
		        {     
		             message="担保截止日期必须在担保起始日期之后"; 
		               Ext.MessageBox.show({
		                    title : '操作信息',
		                    msg : message,
		                    buttons : Ext.MessageBox.OK,
		                    icon : 'ext-mb-error'
		                });
		                 return null;
		        }
			    
						
					    gridPanel1.getStore().setBaseParam('projectId',projId) ; 
					     gridPanel1.getStore().setBaseParam('gLGuaranteeloanProject.premiumRate', premiumRate);
					      gridPanel1.getStore().setBaseParam('gLGuaranteeloanProject.acceptDate', acceptDate);
					    gridPanel1.getStore().setBaseParam('gLGuaranteeloanProject.intentDate', intentDate);
					     gridPanel1.getStore().setBaseParam('gLGuaranteeloanProject.projectMoney', projectMoney);
					    gridPanel1.getStore().setBaseParam('gLGuaranteeloanProject.earnestmoney', earnestmoney);
					       gridPanel1.getStore().setBaseParam('gLGuaranteeloanProject.customerEarnestmoneyScale', customerEarnestmoneyScale);
					    gridPanel1.getStore().setBaseParam('gLGuaranteeloanProject.operationType',  this.businessType);
					    this.gridPanel.getStore().setBaseParam('flag1',0) ; 
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
						 
							   }}
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