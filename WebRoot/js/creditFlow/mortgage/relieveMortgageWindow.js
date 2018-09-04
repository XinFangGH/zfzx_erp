RelieveMortgageWindow = Ext.extend(Ext.Window, {
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
		RelieveMortgageWindow.superclass.constructor.call(this, {
			        width:'40%',
			        buttonAlign:'center',
			        title:'解除抵质押物',
			         modal:true,
					items : [this.relievePanel],
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
		   
			this.relievePanel = new Ext.FormPanel( {
				url: __ctxPath +'/credit/mortgage/updateMortgage1.do',
				layout : 'column',
				region : 'north',
				height : 140,
				anchor : '100%',
				  buttonAlign:'center',
				layoutConfig: {
	               align:'middle'
	            },
	            frame:true,
	             bodyStyle : 'padding:10px 10px 10px 10px',
	             border:false,
				items : [{
				   columnWidth:0.15,
				   layout:'form',
				   border:false,
				   items:[{
				        xtype:'checkbox',
				        name:'isunchain',
				        id: 'isunchain',
				        hideLabel:true,
				        boxLabel :'是否解除',
				        listeners:{
				           'check':function(box,checked){
				               if(checked==true){
				                   box.setValue(true)
				               }
				           }
				        }
				   },{
				   	  xtype:'hidden',
				   	  id:"procreditMortgage.isunchain",
				   	  name:'procreditMortgage.isunchain'
				   	  
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
				   	  name:'procreditMortgage.unchainCreateTime'
				   	  
				   }]
				},{
				   columnWidth:0.35,
				   layout:'form',
				   border:false,
				   labelWidth:60,
				   labelAlign:'right',
				   items:[{
				      xtype:'datefield',
				      format:'Y-m-d',
				      anchor : '100%',
				      fieldLabel:'解除时间',
				      name:'procreditMortgage.unchaindate'
				   }]
				},{
				   columnWidth:0.5,
				   layout:'form',
				   border:false,
				   labelWidth:60,
				   labelAlign:'right',
				   items:[{xtype:"hidden",name:"procreditMortgage.unchainPersonId"},{
						fieldLabel : "经办人",
						xtype : "combo",
						editable : false,
						triggerClass : 'x-form-search-trigger',
						itemVale : creditkindDicId, // xx代表分类名称
						hiddenName : "procreditMortgage.unchainPerson",
						readOnly : this.isAllReadOnly,
					    anchor : "100%",
					    onTriggerClick : function(cc) {
						     var obj = this;
						     var appuerIdObj=obj.previousSibling();
							 new UserDialog({
							 	userIds:appuerIdObj.getValue(),
							 	userName:obj.getValue(),
								single : false,
								title:"解除经办人",
								callback : function(uId, uname) {
									obj.setRawValue(uname);
									appuerIdObj.setValue(uId);
								}
							}).show();

						}

					}]
				},{
				   columnWidth:0.8,
				   layout:'form',
				   border:false,
				   labelWidth:60,
				   labelAlign:'right',
				   items:[{
				      xtype:'button',
				      anchor : '100%',
				      width:200,
				      text:'上传/下载解除文件',
				      fieldLabel:'解除文件',
				      handler:function(){
				     
						 var talbeName="cs_procredit_mortgage_jc.";
						 var mark=talbeName+mortgageId;
						 uploadfile("上传/下载抵质押物文件",mark,15,null,null);
				      }
				   }]
				},{
				   columnWidth:0.2,
				   layout:'form',
				   border:false,
				   items:[{
				   	 xtype:'button',
				   	 width:50,
				   	 text:'预览',
				   	 handler:function(){
				
				   	     var talbeName="cs_procredit_mortgage_jc.";
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
				       name:'procreditMortgage.unchainremark'
				   }]
				}]
	
			})
			this.relievePanel.loadData({
						url : __ctxPath +'/credit/mortgage/getMortgageInfo.do?mortgageid='
								+ mortgageId,
						root : 'data',
						preName : 'procreditMortgage',
						scope : this,
						success : function(resp, options) {
							var obj=Ext.util.JSON.decode(resp.responseText)
							if(obj.data.mortgagenametypeid==7){
							this.setTitle("解除<<font color='red'>住宅</font>>手续")
						}else if(obj.data.mortgagenametypeid==8){
							this.setTitle("解除<<font color='red'>商铺写字楼</font>>手续")
						}else if(obj.data.mortgagenametypeid==9){
							this.setTitle("解除<<font color='red'>住宅用地</font>>手续")
						}else if(obj.data.mortgagenametypeid==10){
							this.setTitle("解除<<font color='red'>商业用地</font>>手续")
						}else if(obj.data.mortgagenametypeid==11){
							this.setTitle("解除<<font color='red'>商住用地</font>>手续")
						}else if(obj.data.mortgagenametypeid==12){
							this.setTitle("解除<<font color='red'>教育用地</font>>手续")
						}else if(obj.data.mortgagenametypeid==13){
							this.setTitle("解除<<font color='red'>工业用地</font>>手续")
						}else if(obj.data.mortgagenametypeid==14){
							this.setTitle("解除<<font color='red'>无形权利</font>>手续")
						}else if(obj.data.mortgagenametypeid==15){
							this.setTitle("解除<<font color='red'>公寓</font>>手续")
						}else if(obj.data.mortgagenametypeid==16){
							this.setTitle("解除<<font color='red'>联排别墅</font>>手续")
						}else if(obj.data.mortgagenametypeid==17){
							this.setTitle("解除<<font color='red'>独栋别墅</font>>手续")
						}else if(obj.data.mortgagenametypeid==1){
							this.setTitle("解除<<font color='red'>车辆</font>>手续")
						}else if(obj.data.mortgagenametypeid==2){
							this.setTitle("解除<<font color='red'>股权</font>>手续")
						}else if(obj.data.mortgagenametypeid==5){
							this.setTitle("解除<<font color='red'>机器设备</font>>手续")
						}else if(obj.data.mortgagenametypeid==6){
							this.setTitle("解除<<font color='red'>存货/商品</font>>手续")
						}
						}
					});

	},
	save:function(){
	     var grid=this.gridPanel;
	     var window=this;
				    Ext.getCmp("procreditMortgage.isunchain").setValue(Ext.getCmp("isunchain").getValue())
				    this.relievePanel.getForm().submit({
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
