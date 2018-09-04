//AddProductOwnPlanCharge.js

AddProductOwnPlanChargeForm = Ext.extend(Ext.Window, {
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				this.initUIComponents();
				AddProductOwnPlanChargeForm.superclass.constructor.call(this, {
							layout : 'fit',
							modal : true,
							height : 200,
							items : this.formPanel,
							width : 500,
							border : false,
							maximizable : true,
							title : '新增产品费用',
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
						name : 'slPlansToCharge.businessType',
						xtype : 'hidden',
						value:this.businessType
					},{
						name : 'slPlansToCharge.productId',
						xtype : 'hidden',
						value:this.productId
					},{
						name : 'slPlansToCharge.isValid',
						xtype : 'hidden',
						value:0
					},{
						xtype:'combo',
						mode : 'local',
					    displayField : 'name',
					    valueField : 'id',
					    anchor:'100%',
					    store : new Ext.data.SimpleStore({
								fields : ["name", "id"],
								data : [["私有", "1"],
										["公有", "0"]]
						}),
						triggerAction : "all",
						hiddenName : 'slPlansToCharge.isType',
						anchor : "100%",
						allowBlank : false,
						fieldLabel : '费用类型'
					},{
						fieldLabel : '费用名称',	
						xtype : "textfield",
						name : 'slPlansToCharge.name',
						allowBlank : false,
						anchor : '100%',
						maxLength: 255
				    },{
						fieldLabel : '费用标准',	
						xtype : "textfield",
						name : 'slPlansToCharge.chargeStandard',
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
					url:__ctxPath + '/creditFlow/finance/saveProductSlPlansToCharge.do',
					callback:function(fp,action){
						panel.operateObj.getStore().reload();
						panel.close();
					}
				
				})
			}
		});