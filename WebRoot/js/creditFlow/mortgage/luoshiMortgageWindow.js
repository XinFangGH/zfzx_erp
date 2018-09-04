LuoshiMortgageWindow = Ext.extend(Ext.Window, {
	layout : 'anchor',
	anchor : '100%',
	constructor : function(_cfg) {
		if (typeof(_cfg.mortgageId) != "undefined") {
			this.mortgageId = _cfg.mortgageId;
		}
		if (typeof(_cfg.gridPanel) != "undefined") {
			this.gridPanel = _cfg.gridPanel;
		}
		if (typeof(_cfg.businessType) != "undefined") {
			this.businessType = _cfg.businessType;
		}
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		LuoshiMortgageWindow.superclass.constructor.call(this, {
			        width:'40%',
			        buttonAlign:'center',
			        title:'落实保证担保',
			        modal:true,
					items : [this.transactPanel],
					buttons:[{
					    text : '保存',
						iconCls : 'btn-save',
						scope : this,
						handler : this.save
					},{
					    text : '关闭',
						iconCls : 'close',
						scope : this,
						handler : function(){
							this.close();
						}
					}]
					
				})
	},
	initUIComponents : function() {
	   
			var mortgageId = this.mortgageId;
		    var businessType=this.businessType;
		   this.transactPanel = new Ext.FormPanel( {
				url: __ctxPath +'/credit/mortgage/updateMortgage1.do',
				layout : 'column',
				region : 'north',
				height : 170,
				anchor : '100%',
				  buttonAlign:'center',
				layoutConfig: {
	               align:'middle'
	            },
	            frame:true,
	             bodyStyle : 'padding:10px 10px 10px 10px',
	             border:false,
				items : [/*{
				   columnWidth:0.15,
				   layout:'form',
				   border:false,
				   items:[{
				        xtype:'checkbox',
				        name:'isTransact',
				        id: 'isTransact',
				        hideLabel:true,
				        boxLabel :'是否落实',
				        listeners:{
				           'check':function(box,checked){
				               if(checked==true){
				                   box.setValue(true)
				               }
				           }
				        }
				   },]
				},*/{
					columnWidth : 1,
					layout : 'column',
					items : [{
					   columnWidth:0.5,
					   layout:'form',
					   border:false,
					   labelWidth:60,
					   labelAlign:'right',
					   items:[{
					      xtype:'datefield',
					      format:'Y-m-d',
					      anchor : '100%',
					      fieldLabel:'时间',
					      allowBlank:false,
					      readOnly:this.isAllReadOnly,
					      name:'procreditMortgage.transactDate'
					   },
					   {
				   	  xtype:'hidden',
				   	  //id:"procreditMortgage.isTransact",
				   	  name:'procreditMortgage.isTransact',
				   	  value:true
				   	  
				   },{
				   	  xtype:'hidden',
				   	  value:mortgageId,
				   	  name:'mortgageid'
				   	  
				   },{
				   	  xtype:'hidden',
				   	  value:businessType,
				   	  name:'procreditMortgage.businessType'
				   	  
				   }]
					},{
					   columnWidth:0.5,
					   layout:'form',
					   border:false,
					   labelWidth:60,
					   labelAlign:'right',
					   items:[{xtype:"hidden",name:"procreditMortgage.transactPersonId"},{
							fieldLabel : "经办人",
							xtype : "combo",
							editable : false,
							triggerClass : 'x-form-search-trigger',
							itemVale : creditkindDicId, // xx代表分类名称
							hiddenName : "procreditMortgage.transactPerson",
							readOnly:this.isAllReadOnly,
						    anchor : "100%",
						    allowBlank:false,
						    onTriggerClick : function(cc) {
							     var obj = this;
							     var appuerIdObj=obj.previousSibling();
								 new UserDialog({
								 	userIds:appuerIdObj.getValue(),
								 	userName:obj.getValue(),
									single : false,
									title:"办理经办人",
									callback : function(uId, uname) {
										obj.setRawValue(uname);
										appuerIdObj.setValue(uId);
									}
								}).show();
	
							}
	
						}]
					}]
				}/*,{
					columnWidth:0.5,
					layout:'form',
					border:false,
					labelWidth:60,
					labelAlign:'right',
					items:[{
						xtype:'textfield',
						anchor : '100%',
						fieldLabel:'登记号',
						name:'procreditMortgage.pledgenumber'
					}]
				},{
					columnWidth:0.5,
					layout:'form',
					border:false,
					labelWidth:60,
					labelAlign:'right',
					items:[{
						xtype:'textfield',
						anchor : '100%',
						fieldLabel:'登记机关',
						name:'procreditMortgage.enregisterDepartment'
					}]
				}*/,{
				   columnWidth:0.5,
				   layout:'form',
				   border:false,
				   labelWidth:60,
				   labelAlign:'right',
				   items:[{
				      xtype:'button',
				      anchor : '100%',
				      width:200,
				      text:'上传/下载落实文件',
				      fieldLabel:'落实文件',
				      disabled:this.isAllReadOnly,
				      handler:function(){
				    
				         var talbeName="cs_procredit_mortgage.";
						 var mark=talbeName+mortgageId;
						 uploadfile("上传/下载落实文件",mark,15,null,null);
				      }
				   }]
				},{
				   columnWidth:0.5,
				   layout:'form',
				   border:false,
				   items:[{
				   	 xtype:'button',
				   	 width:50,
				   	 text:'预览',
				   	 handler:function(){
				 
				   	     var talbeName="cs_procredit_mortgage.";
					     var mark=talbeName+mortgageId;
				         picViewer(mark,true);
				   	 }
				   }]
				},{
				   columnWidth:1,
				   layout:'form',
				   border:false,
				   labelAlign:'right',
				   labelWidth:60,
				   items:[{
				       xtype:'textarea',
				       anchor : '100%',
				       fieldLabel:'备注',
				       allowBlank:false,
				       readOnly:this.isAllReadOnly,
				       name:'procreditMortgage.transactRemarks'
				   }]
				}]
	
			})
			this.transactPanel.loadData({
						url : __ctxPath +'/credit/mortgage/getMortgageInfo.do?mortgageid='
								+ mortgageId,
						root : 'data',
						preName : 'procreditMortgage',
						scope : this,
						success : function(resp, options) {
							var obj=Ext.util.JSON.decode(resp.responseText)
							this.setTitle("落实<<font color='red'>"+obj.data.mortgagename+"</font>>手续")
						}
					});

	},
	save:function(){
	     var grid=this.gridPanel;
	     var window=this;
		
		 this.getCmpByName('procreditMortgage.isTransact').setValue(true)
	    this.transactPanel.getForm().submit({
				method : 'POST',
				waitTitle : '连接',
				waitMsg : '消息发送中...',
				success : function(form ,action) {
					Ext.ux.Toast.msg('操作信息','提交成功');
					window.close()
					grid.getStore().reload()
				},
				failure : function(form, action) {
					Ext.ux.Toast.msg('操作信息','提交失败！');		
				}
			})
				    
				    
	}
});
