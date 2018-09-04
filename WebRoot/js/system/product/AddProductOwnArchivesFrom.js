//AddProductOwnArchivesFrom.js
AddProductOwnArchivesFrom = Ext.extend(Ext.Window, {
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				this.initUIComponents();
				AddProductOwnArchivesFrom.superclass.constructor.call(this, {
							layout : 'fit',
							modal : true,
							height : 150,
							items : this.formPanel,
							width : 500,
							border : false,
							maximizable : true,
							title : '新增产品归档材料',
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
						name : 'ourArchivesMaterials.businessTypeKey',
						xtype : 'hidden',
						value:this.businessType
					},{
						name : 'ourArchivesMaterials.operationTypeKey',
						xtype : 'hidden',
						value:this.operationType
					},{
						name : 'ourArchivesMaterials.productId',
						xtype : 'hidden',
						value:this.productId
					},{
						xtype : 'hidden',
						name : 'ourArchivesMaterials.isPublic',
						value :1
					}/*,{
						xtype:'combo',
						mode : 'local',
					    displayField : 'name',
					    valueField : 'id',
					    anchor:'100%',
					    store : new Ext.data.SimpleStore({
								fields : ["name", "id"],
								data : [["必备", "1"],
										["非必备", "2"]]
						}),
						triggerAction : "all",
						hiddenName : 'ourArchivesMaterials.isPublic',
						anchor : "100%",
						allowBlank : false,
						fieldLabel : '材料类型'
					}*/,{
						fieldLabel : '归档材料名称',	
						xtype : "textarea",
						name : 'ourArchivesMaterials.materialsName',
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
					url:__ctxPath + '/creditFlow/archives/saveProductOurArchivesMaterials.do',
					callback:function(fp,action){
						panel.operateObj.getStore().reload();
						panel.close();
					}
				
				})
			}
		});