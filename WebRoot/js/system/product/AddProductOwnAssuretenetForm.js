//AddProductOwnAssuretenetForm

AddProductOwnAssuretenetForm = Ext.extend(Ext.Window, {
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				this.initUIComponents();
				AddProductOwnAssuretenetForm.superclass.constructor.call(this, {
							layout : 'fit',
							modal : true,
							height : 150,
							items : this.formPanel,
							width : 500,
							border : false,
							maximizable : true,
							title : '新增产品贷款条件',
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
				var oprerationType=this.oprerationType;
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
						name : 'ourProcreditAssuretenet.businessTypeKey',
						xtype : 'hidden',
						value:this.businessType
					},{
						name : 'ourProcreditAssuretenet.operationTypeKey',
						xtype : 'hidden',
						value:this.operationType
					},{
						name : 'ourProcreditAssuretenet.productId',
						xtype : 'hidden',
						value:this.productId
					}/*,{
						xtype:'combo',
						mode : 'local',
					    displayField : 'name',
					    valueField : 'id',
					    anchor:'100%',
					    store : new Ext.data.SimpleStore({
								fields : ["name", "id"],
								data : [["企业", "company"],
										["个人", "person"]]
						}),
						triggerAction : "all",
						hiddenName : 'ourProcreditAssuretenet.customerType',
						anchor : "100%",
						allowBlank : false,
						fieldLabel : '客户类型'
					}*/,{
						fieldLabel : '贷款条件名称',	
						xtype : "textarea",
						name : 'ourProcreditAssuretenet.assuretenet',
						allowBlank : false,
						anchor : '100%',
						maxLength: 255
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
				var panel=this;
				$postForm({
					formPanel:this.formPanel,
					scope:this,
					msg : (this.assuretenetId != null && this.assuretenetId != 'undefined')?'保存成功':'添加成功',
					url:__ctxPath + '/assuretenet/saveProductOurProcreditAssuretenet.do',
					callback:function(fp,action){
						panel.operateObj.getStore().reload();
						panel.close();
					}
				
				})
			}
		});