
CashQlideAlotAdd =Ext.extend(Ext.Window, {
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				this.initUIComponents();
				CashQlideAlotAdd.superclass.constructor.call(this, {
							id : 'CashQlideAlotAddWin',
							layout : 'fit',
							items : this.gridPanel,
							modal : true,
							height : 500,
							  width : 780,
							maximizable : true,
							title : '批量增加流水'
				})
			},//end of the constructor
			//初始化组件

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
						this.comboType1= new DicIndepCombo({
									lazyInit : false,
								  editable : false,
									forceSelection : false,
									nodeKey : "bankTransactionType"
									
								})
						this.comboType1.store.reload();
						this.datafield=new Cls.form.DateTimeField({
										format : 'Y-m-d ',
										allowBlank:false
									});
				
						this.gridPanel = new HT.EditorGridPanel( {
							border : false,
							clicksToEdit : 1,
							isShowTbar : true,
							showPaging : false,
					//		autoHeight : true,
							hiddenCm :this.isHidden,
				         	plugins: [summary],
							id : 'CashQlideAlotAdd_editGrid',
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
							} ],
							tbar : this.ttbar,
							columns : [ {
								header : '币种',
								dataIndex : 'currency',
								width : 47,
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
								dataIndex : 'factDate',
						   	   format : 'Y-m-d ',
						   	   xtype : 'datecolumn',
								sortable : true,
								width : 120,
								renderer : ZW.ux.dateRenderer(this.datafield),
								editor :this.datafield
							}, {
								header : '收入金额',
								dataIndex : 'incomeMoney',
								align : 'right',
								width : 110,
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
								width : 110,
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
								header : '交易类型',
								dataIndex : 'bankTransactionType',
								align : 'center',
								width : 65,
								editor : this.comboType1,
								renderer : function(value,metaData, record,rowIndex, colIndex,store) {
									var objcom = this.editor;
									var objStore = objcom.getStore();
						
									var idx = objStore.find("dicKey", value);
									if (idx != "-1") {

										return objStore.getAt(idx).data.itemValue;
									}else{
										
									   return "常规"
										
									}
									
						     }
								  
							}, {
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
								width : 127,
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
							factDate : new Date(),
							bankTransactionType : 'normalTransactionType',
							transactionType : ''
							
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
						 st={"isProject":vRecords[i].data.isProject,"currency":vRecords[i].data.currency,"payMoney":vRecords[i].data.payMoney,"incomeMoney":vRecords[i].data.incomeMoney,"factDate":vRecords[i].data.factDate,"transactionType":vRecords[i].data.transactionType,"bankTransactionType":vRecords[i].data.bankTransactionType};
											vDatas += Ext.util.JSON
													.encode(st) + '@';
							
						}
							
							vDatas = vDatas.substr(0, vDatas.length - 1);
						}

						return vDatas;
					},
					saveRs : function() {
						
						var data = this.getGridDate();
						if (data == '') {
							Ext.ux.Toast.msg('操作信息', '请先添加流水！');
							return;
						}
						Ext.Ajax
								.request( {
									url : __ctxPath + '/creditFlow/finance/savealotcashqlideSlFundQlide.do',
									method : 'POST',
									scope : this,
									params : {
										plQlideJson : data
									},
									success : function(response, request) {
											var gridPanel = Ext.getCmp('CashQlideViewGrid');
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