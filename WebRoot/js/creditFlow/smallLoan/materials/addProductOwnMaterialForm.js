addProductOwnMaterialForm = Ext.extend(Ext.Window, {
	        
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				this.initUIComponents();
				addProductOwnMaterialForm.superclass.constructor.call(this, {
							layout : 'fit',
							modal : true,
							height : 200,
							items : this.formPanel,
							width : 500,
							border : false,
							maximizable : true,
							title : '新增产品贷款材料',
							buttonAlign : 'center',
							buttons : [
										{
											text : '新增',
											iconCls : 'btn-ok',
											scope : this,
											handler : this.save
										}, {
											text : '取消',
											iconCls : 'btn-cancel',
											scope : this,
											handler : this.cancel
										}
							         ]
				});
			},
			initUIComponents : function() {
				this.formPanel = new Ext.FormPanel({
					labelAlign : 'right',
					buttonAlign : 'center',
					bodyStyle : 'padding:10px 25px 25px',
					labelWidth : 110,
					frame : true,
					waitMsgTarget : true,
					monitorValid : true,
					width : 500,
					items : [{
								fieldLabel : "父贷款材料项",
								xtype : "combo",
								allowBlank : false,
								displayField : 'itemName',
								width : 280,
								store : new Ext.data.SimpleStore({
									baseParams : {
										nodeKey : 'SmallLoan_SmallLoanBusiness'
									},
									autoLoad : false,
									url : __ctxPath
											+ '/materials/selectParentIdOurProcreditMaterialsEnterprise.do',
									fields : ['itemId', 'itemName']
								}),
								valueField : 'itemId',
								triggerAction : 'all',
								mode : 'remote',
								hiddenName : "parentId",
								editable : false,
								listeners : {
									afterrender : function(combox) {
										var st = combox.getStore();
										st.on("load", function() {
													combox.setValue(combox
															.getValue());
												})
									}
								}
							},{
								xtype : 'textfield',
								fieldLabel : '贷款材料类型 ',
								name : 'materialsName',
								width : 280,
								maxLength : 127,
								allowBlank : false,
								blankText : '必填信息'
							}]

				});
			},
			
			reset : function() {
				this.formPanel.getForm().reset();
			},
			cancel : function() {
				this.close();
			},
			save : function() {
				var  parentId=this.formPanel.getCmpByName("parentId").getValue();
				var materialsName=this.formPanel.getCmpByName("materialsName").getValue();
				var gridPanel=this.gridPanel;
				var productId=this.productId;
				var newRecord = gridPanel.getStore().recordType;
				var newData = new newRecord({
									materialsId : '',
									materialsName : materialsName,
									parentId:parentId,
									productId:productId
								});
				gridPanel.stopEditing();
				gridPanel.getStore().insert(gridPanel.getStore().getCount(),newData);
				gridPanel.getView().refresh();
				gridPanel.getSelectionModel().selectRow((gridPanel.getStore().getCount() - 1));
				gridPanel.startEditing(0,1);
				this.close();
				
			}
		});