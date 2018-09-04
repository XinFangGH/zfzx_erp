
SlFundQlideAlotAdd =Ext.extend(Ext.Window, {
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				this.initUIComponents();
				SlFundQlideAlotAdd.superclass.constructor.call(this, {
							id : 'SlFundQlideAlotAddWin',
						   region : 'center',
							layout : 'border',
							items : [this.searchPanel,this.gridPanel],
							modal : true,
							height : 500,
							width : screen.width*0.95,
							maximizable : true,
							title : '批量添加业务往来资金记录'
				})
			},//end of the constructor
			//初始化组件

			initUIComponents : function() {
				
			this.searchPanel = new HT.SearchPanel({
			layout : 'column',
			region : 'north',
			height : 40,
			anchor : '70%',
			items : [
					{
				columnWidth : 0.7,
				layout : 'form',
				border : false,
				labelWidth : 60,
				labelAlign : 'right',

				items : [{
							fieldLabel : '我方账户',
							// submitValue:false,
						//	name : 'Q_myAccount_S_EQ',
							id : 'Q_dialAccounts_S_EQintentqlide',
							flex : 1,
							xtype : 'trigger',
							triggerClass : 'x-form-search-trigger',
							onTriggerClick : function() {
								var selectAccountkLinkManintent = function(a,b,c,d,e,f) {
									Ext.getCmp('Q_dialAccounts_S_EQintentqlide').setValue(b+"-"+c+"-"+a);
									Ext.getCmp('Q_dialAccounts_S_EQintentqlideid').setValue(a);
										
									}
								selectAccountlForm(selectAccountkLinkManintent);
							},
							anchor : '100%'
						},{
							name : 'Q_myAccount_S_EQ',
							id : 'Q_dialAccounts_S_EQintentqlideid',
						//	hidden:true,
							xtype : 'hidden'
						}]

			}

			]
			});// end of searchPanel
				
				
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
								iconCls : 'btn-save',
								scope : this,
								text : '保存',
								handler : this.saveRs
							}]
						});
						this.comboType= new Ext.form.ComboBox({
						 triggerAction : "all",
						 store : new Ext.data.ArrayStore({
					                autoLoad : true,
					                baseParams : {
					                    nodeKey : "capitalkind"
					                },
					                url : __ctxPath + '/system/loadItemByNodeKey1Dictionary.do',
					                fields : ['itemId', 'itemName']
					            }),	
					
						editable : false,
						lazyInit : false,
					    displayField : 'itemId',
		                valueField : 'itemName'
					}),
						
					
						this.comboType.store.reload();
					
						this.comboType2= new DicIndepCombo({
									lazyInit : false,
								  editable : false,
									forceSelection : false,
									nodeKey : "bankTransactionType"
								})
						this.comboType2.store.reload();
						
						
					this.comboType1=new Ext.form.ComboBox( {
						                displayField : 'accountnum',
									   valueField : 'accountnum',
									    triggerAction : 'all',
										xtype : 'combo',
										 mode : 'local',
										store : new Ext.data.JsonStore({
										url : __ctxPath + '/creditFlow/customer/common/listbycheckboxEnterpriseBank.do',
										autoLoad : true,
										fields : [{
												name : 'id'
											},{
												name : 'bankname'
											},{
												name : 'name'
											},{
												name : 'accountnum'
											}
												],
											root:'result',
					                       totalProperty :'totalCounts',
					                       listeners : {
													scope : this,
													'load' : function(s, r, o) {
														if (s.getCount() == 0) {
																	  Ext.getCmp('slFundQlide.opAccount')
																	.markInvalid('没有查找到匹配的记录');
														}
													}
												}
										     	}),
										anchor : '98%',
										triggerClass : 'x-form-search-trigger',
									onTriggerClick : function() {
										                    var setop=function(a,b,c){
										                     var grid=Ext.getCmp("SlFundQlideAlotAdd_editGrid");
														 	 grid.getSelectionModel().getSelected().data['opOpenName']=c;
														 	 grid.getSelectionModel().getSelected().data['opBankName']=b;
														 	  grid.getSelectionModel().getSelected().data['opAccount']=a;
														 	 grid.getView().refresh();
										                    }
										
															selectCustomAccount(setop);
														
													},
									listeners : {
														scope : this,
														'select' : function(combo, record,
																index) {
														 	  var grid=Ext.getCmp("SlFundQlideAlotAdd_editGrid");
												 	 grid.getSelectionModel().getSelected().data['opOpenName']=record.data.name;
												 	 grid.getSelectionModel().getSelected().data['opBankName']=record.data.bankname;
															 
														}
													}
													
										
									
							
								
								
						})
								
						this.comboType1.store.reload();
						this.datafield=new Cls.form.DateTimeField({
										format : 'Y-m-d',
										allowBlank:false
								})
				
						this.gridPanel = new HT.EditorGridPanel( {
							region : 'center',
							clicksToEdit : 1,
							isShowTbar : true,
							showPaging : false,
					//		autoHeight : true,
							hiddenCm :this.isHidden,
				         	plugins: [summary],
							id : 'SlFundQlideAlotAdd_editGrid',
							url:__ctxPath + "/creditFlow/finance/list1SlFundQlide.do",
							isautoLoad : false,
							fields : [ {
								name : 'fundQlideId'
							}, {
								name : 'currency'
							}, {
								name : 'factDate'
							}, {
								name : 'payMoney'
							}, {
								name : 'incomeMoney'
							}, {
								name : 'transactionType'
							}, {
								name : 'isProject'
							}, {
								name : 'opAccount'
							}, {
								name : 'opBankName'
							}, {
								name : 'opOpenName'
							} ],
							tbar : this.ttbar,
							columns : [ {
								header : '币种',
								dataIndex : 'currency',
								width : 80,
								summaryType: 'count',
								summaryRenderer: totalMoney,
								editor :this.comboType,
								renderer : function(value,metaData, record,rowIndex, colIndex,store) {
						
									var objcom = this.editor;
									var objStore = objcom.getStore();
						            objStore.reload()
									var idx = objStore.find("itemId", value);
									if (idx != "-1") {
										return objStore.getAt(idx).data.itemName;
									} else {
								 
										if(record.data.typeName==""){
											
											record.data.typeName=value
											
										}else{
											var x = store.getModifiedRecords();
											if(x!=null && x!="" && record.data.typeName==""){
												record.data.typeName=value
											}
										}
										
										 return record.get("typeName")
									
							     }
							  }
								
							}, {
								header : '到账时间',
								xtype : 'datecolumn',
								 format : 'Y-m-d ',
								dataIndex : 'factDate',
								sortable : true,
								width : 200,
								renderer : ZW.ux.dateRenderer(this.datafield),
								editor :this.datafield
							}, {
								header : '收入金额',
								dataIndex : 'incomeMoney',
								align : 'right',
								width : 100,
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
								header : '支出金额',
								dataIndex : 'payMoney',
								align : 'right',
								width : 100,
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
								header : '对方账号',
								dataIndex : 'opAccount',
								align : 'center',
							//	width : 568,
								editor : this.comboType1
							}, {
								header : '对方开户银行',
								dataIndex : 'opBankName',
								align : 'center',
							//	width : 568,
								editor : new Ext.form.TextField( {
									allowBlank : false
								})
							}, {
								header : '对方账户名称',
								dataIndex : 'opOpenName',
								align : 'center',
							//	width : 568,
								editor : new Ext.form.TextField( {
									allowBlank : false
								})
							}
//							, {
//								header : '银行交易类型',
//								dataIndex : 'bankTransactionType',
//								align : 'center',
//								editor : this.comboType2,
//								renderer : function(value,metaData, record,rowIndex, colIndex,store) {
//									var objcom = this.editor;
//									var objStore = objcom.getStore();
//						
//									var idx = objStore.find("dicKey", value);
//									
//									if (idx != "-1") {
//
//										return objStore.getAt(idx).data.itemValue;
//									} else{
//									
//										return "常规";
//									}
//									
//						     }
//								  
//							}
							, {
								header : '是否项目相关',
								dataIndex : 'isProject',
								align : 'center',
								width : 100,
								editor : new Ext.form.ComboBox( {
									triggerAction : 'all',
									mode : 'local',
									store : new Ext.data.SimpleStore({
													fields : ['name', 'id'],
													data : [["是", "是"],
															["否", "否"]]
												}),
							   	   displayField : 'name',
                                   valueField : 'id' 
									
								   })
								  
							}, {
								header : '用途摘要',
								dataIndex : 'transactionType',
								align : 'center',
							//	width : 568,
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
										if(e.column==5 && e.value !=0){
												e.record.set('incomeMoney',0);
												e.record.commit()
										}
										if(e.column==4 && e.value !=0){
											e.record.set('payMoney',0);
											e.record.commit();
								}
							  }
							}
						// end of columns
								});

					},
				

					createRs : function() {
						this.datafield.setValue('');
						var newRecord = this.gridPanel.getStore().recordType;
						var newData = new newRecord( {
						//	fundIntentId : '',
							isProject : '是',
							currency : '人民币',
							payMoney : 0,
							incomeMoney : 0,
							factDate :new Date(),
							transactionType : '',
							opAccount : '',
							opBankName : '',
							opOpenName : '',
							bankTransactionType:'normalTransactionType'
							
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
							 st={"isProject":vRecords[i].data.isProject,"currency":vRecords[i].data.currency,
							 "payMoney":vRecords[i].data.payMoney,"incomeMoney":vRecords[i].data.incomeMoney,
							 "factDate":vRecords[i].data.factDate,"transactionType":vRecords[i].data.transactionType,
							 "bankTransactionType":vRecords[i].data.bankTransactionType,"opAccount":vRecords[i].data.opAccount
							 ,"opBankName":vRecords[i].data.opBankName,"opOpenName":vRecords[i].data.opOpenName};
											vDatas += Ext.util.JSON
													.encode(st) + '@';
							
						}
							
							vDatas = vDatas.substr(0, vDatas.length - 1);
						}
						return vDatas;
					},
					saveRs : function() {
						var data = this.getGridDate();
						var myaccount=Ext.getCmp('Q_dialAccounts_S_EQintentqlideid').getValue();
						if (data == '') {
							Ext.Msg.alert("提示", "请先添加流水！")
							return;
						}
						if (myaccount==null || myaccount == '' ) {
							Ext.Msg.alert("提示", "请选择我方账户！")
							return;
						}
						Ext.Ajax
								.request( {
									url : __ctxPath + '/creditFlow/finance/savealotqlideSlFundQlide.do',
									method : 'POST',
									scope : this,
									params : {
										myaccount : myaccount,
										plQlideJson : data
									},
									success : function(response, request) {
											var gridPanel = Ext.getCmp('SlFundQlideGrid');
											if (gridPanel != null) {
												gridPanel.getStore().reload();
											}
											this.close();
													}
								});

					},
				removeSelRs : function() {
					var fundIntentGridPanel = this.gridPanel;
                    var  s= fundIntentGridPanel.getSelectionModel().getSelections();
	                if (s <= 0) {
						Ext.ux.Toast.msg('操作信息','请选中记录');
						return false;
					
					}else{	
						for ( var i = 0; i < s.length; i++) {
							var row = s[i];
								fundIntentGridPanel.getStore().remove(row);
				    	  }
							
					      }
					}

				});