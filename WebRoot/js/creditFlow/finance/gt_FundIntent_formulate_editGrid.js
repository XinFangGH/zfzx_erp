//var formulatestore = new HT.JsonStore({
//	url : __ctxPath + "/finance/listSlFundIntent.do"	
//});

gt_FundIntent_formulate_editGrid = Ext.extend(Ext.Panel,
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
						gt_FundIntent_formulate_editGrid.superclass.constructor
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
							}
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
						fundTypenodekey="financeType";
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
							id : 'gt_FundIntent_formulate_editGrid',
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
								name : 'payintentPeriod'
							},{
								name :'slSuperviseRecordId'
							} ],
							tbar : this.isHidden?null:this.ttbar,
							columns : [ {
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
							
							}/*,{
								header : '期数',
								dataIndex : 'payintentPeriod'
							
							}*/, {
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
				

					createRs : function() {
				//		var payintentPeriod=this.object.getCmpByName("slSmallloanProject.payintentPeriod").getValue()
						this.datafield.setValue('');
						var newRecord = this.gridPanel.getStore().recordType;
						var newData = new newRecord( {
							fundIntentId : null,
							fundType : '',
							payMoney : 0,
							incomeMoney : 0,
							intentDate : new Date(),
							remark : '',
							payintentPeriod:1
						});
					/*	var combox=new Ext.form.ComboBox({
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
						this.gridPanel.getColumnModel().setEditor(6,combox);*/		
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
							//if(vRecords[i].data.fundType !=null && vRecords[i].data.fundType !=""){
								 //if(vRecords[i].data.fundIntentId!= null && vRecords[i].data.fundIntentId!= "")  {
											  st={"fundIntentId":vRecords[i].data.fundIntentId,"fundType":vRecords[i].data.fundType,"incomeMoney":vRecords[i].data.incomeMoney,"payMoney":vRecords[i].data.payMoney,"intentDate":vRecords[i].data.intentDate,"remark":vRecords[i].data.remark,"payintentPeriod":vRecords[i].data.payintentPeriod};
											vDatas += Ext.util.JSON
													.encode(st) + '@';
									 /*}else{
									    if(vRecords[i].data.afterMoney==0){
									     st={"fundType":vRecords[i].data.fundType,"incomeMoney":vRecords[i].data.incomeMoney,"payMoney":vRecords[i].data.payMoney,"intentDate":vRecords[i].data.intentDate,"remark":vRecords[i].data.remark,"payintentPeriod":vRecords[i].data.payintentPeriod};
											vDatas += Ext.util.JSON
												.encode(st) + '@';
									 	}
									 }
						 
							   }*/
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