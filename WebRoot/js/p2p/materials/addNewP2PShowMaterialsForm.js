//addNewP2PShowMaterialsForm
addNewP2PShowMaterialsForm = Ext.extend(Ext.Window, {
	        
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				this.initUIComponents();
				addNewP2PShowMaterialsForm.superclass.constructor.call(this, {
							layout : 'fit',
							modal : true,
							height : 200,
							items : this.formPanel,
							width : 500,
							border : false,
							maximizable : true,
							title : '新增网站展示贷款材料',
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
								xtype : 'hidden',
								name : 'plWebShowMaterials.parentId',
								value:0
							},{
								xtype : 'hidden',
								name : 'plWebShowMaterials.projId',
								value:this.projectId
							},{
								xtype : 'hidden',
								name : 'plWebShowMaterials.operationTypeKey',
								value:this.operationType
							},{
								xtype : 'hidden',
								name : 'plWebShowMaterials.businessTypeKey',
								value:this.businessType
							},{
								xtype : 'textfield',
								fieldLabel : '贷款材料类型 ',
								name : 'plWebShowMaterials.materialsName',
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
				var freshTarget =this.operateObj;
				this.formPanel.getForm().submit({
			    clientValidation: true, 
				url : __ctxPath+ '/p2pMaterials/savePlWebShowMaterials.do',
				method : 'post',
				waitMsg : '数据正在提交，请稍后...',
				scope: this,
				success : function(fp, action) {
					
					if (freshTarget != null) {
						freshTarget.getStore().reload();
					}
					this.close();
				}
		});
			}
		});