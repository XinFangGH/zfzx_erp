BanliMortgageWindow = Ext.extend(Ext.Window, {
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
		BanliMortgageWindow.superclass.constructor.call(this, {
			        width:'40%',
			        buttonAlign:'center',
			        title:'办理抵质押物',
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
				        boxLabel :'是否办理',
				        listeners:{
				           'check':function(box,checked){
				               if(checked==true){
				                   box.setValue(true)
				               }
				           }
				        }
				   },]
				},*/{
				   columnWidth:0.5,
				   layout:'form',
				   border:false,
				   labelWidth:60,
				   labelAlign:'right',
				   items:[{
				      xtype:'datefield',
				      format:'Y-m-d',
				      anchor : '100%',
				      fieldLabel:'办理时间',
				      allowBlank:false,
				      readOnly:this.isAllReadOnly,
				      name:'procreditMortgage.transactDate'
				   },{
				   	  xtype:'hidden',
				   	 // id:"procreditMortgage.isTransact",
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
				   	  
				   },{
				   	  xtype:'hidden',
				   	  name:'procreditMortgage.tractCreateTime'
				   	  
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
						readOnly : this.isAllReadOnly,
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
				},{
					columnWidth:0.5,
					layout:'form',
					border:false,
					labelWidth:60,
					labelAlign:'right',
					items:[{
						xtype:'textfield',
						anchor : '100%',
						fieldLabel:'权证编号',
						allowBlank:false,
						readOnly:this.isAllReadOnly,
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
						allowBlank:false,
						readOnly:this.isAllReadOnly,
						name:'procreditMortgage.enregisterDepartment'
					}]
				},{
				   columnWidth:0.5,
				   layout:'form',
				   border:false,
				   labelWidth:60,
				   labelAlign:'right',
				   items:[{
				      xtype:'button',
				      anchor : '100%',
				      width:200,
				      disabled:this.isAllReadOnly,
				      text:'上传/下载办理文件',
				      fieldLabel:'办理文件',
				      handler:function(){
				    
				         var talbeName="cs_procredit_mortgage.";
						 var mark=talbeName+mortgageId;
						 uploadfile("上传/下载抵质押物文件",mark,15,null,null);
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
						if(obj.data.mortgagenametypeid==7){
							this.setTitle("办理<<font color='red'>住宅</font>>手续")
						}else if(obj.data.mortgagenametypeid==8){
							this.setTitle("办理<<font color='red'>商铺写字楼</font>>手续")
						}else if(obj.data.mortgagenametypeid==9){
							this.setTitle("办理<<font color='red'>住宅用地</font>>手续")
						}else if(obj.data.mortgagenametypeid==10){
							this.setTitle("办理<<font color='red'>商业用地</font>>手续")
						}else if(obj.data.mortgagenametypeid==11){
							this.setTitle("办理<<font color='red'>商住用地</font>>手续")
						}else if(obj.data.mortgagenametypeid==12){
							this.setTitle("办理<<font color='red'>教育用地</font>>手续")
						}else if(obj.data.mortgagenametypeid==13){
							this.setTitle("办理<<font color='red'>工业用地</font>>手续")
						}else if(obj.data.mortgagenametypeid==14){
							this.setTitle("办理<<font color='red'>无形权利</font>>手续")
						}else if(obj.data.mortgagenametypeid==15){
							this.setTitle("办理<<font color='red'>公寓</font>>手续")
						}else if(obj.data.mortgagenametypeid==16){
							this.setTitle("办理<<font color='red'>联排别墅</font>>手续")
						}else if(obj.data.mortgagenametypeid==17){
							this.setTitle("办理<<font color='red'>独栋别墅</font>>手续")
						}else if(obj.data.mortgagenametypeid==1){
							this.setTitle("办理<<font color='red'>车辆</font>>手续")
						}else if(obj.data.mortgagenametypeid==2){
							this.setTitle("办理<<font color='red'>股权</font>>手续")
						}else if(obj.data.mortgagenametypeid==5){
							this.setTitle("办理<<font color='red'>机器设备</font>>手续")
						}else if(obj.data.mortgagenametypeid==6){
							this.setTitle("办理<<font color='red'>存货/商品</font>>手续")
						}
							
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
