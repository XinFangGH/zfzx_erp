ProDecisionSetting = Ext.extend(Ext.Window, {
			constructor : function(conf) {
				Ext.applyIf(this, conf);
				this.initUI();
				ProDecisionSetting.superclass.constructor.call(this, {
							title : '分支条件设置',
							width : 650,
							height : 460,
							layout : 'fit',
							maximizable : true,
							modal : true,
							items : [this.formPanel],
							buttonAlign : 'center',
							buttons : [{
										text : '保存',
										iconCls : 'btn-save',
										scope : this,
										handler : this.save
									},{
										text:'取消',
										iconCls:'btn-cancel',
										scope:this,
										handler:this.close
									}]
						});
			},
			initUI : function() {
				this.formPanel = new Ext.FormPanel({
							bodyStyle : 'padding:10px',
							border:false,
							layout : 'form',
							items : [{
										xtype : 'textarea',
										fieldLabel : '执行代码决定分支',
										anchor : '96%,96%',
										height:350,
										name:'exeCode',
										allowBlank:false
									},{
										xtype:'label',
										text:'通过设置tranTo值来决定分支跳转.'
									}]
				});
						
				this.formPanel.loadData({
					url:__ctxPath + '/flow/getDecisionProHandleComp.do',
 					params:{
 						defId:this.defId,
 						activityName:this.activityName
 					}
				});
			},
			
			save:function(){
				if(this.formPanel.getForm().isValid()){
					this.formPanel.getForm().submit({
							url:__ctxPath+'/flow/saveDecisionProHandleComp.do',
							scope:this,
							params:{
								defId:this.defId,
								activityName:this.activityName
							},
							success:function(resp,options){
								Ext.ux.Toast.msg('操作信息','成功保存设置！');
								this.close();
							}
						}
					);
				}
			}
			
		});