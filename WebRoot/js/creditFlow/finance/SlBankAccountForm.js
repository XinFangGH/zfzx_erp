/**
 * @author 
 * @createtime 
 * @class SlBankAccountForm
 * @extends Ext.Window
 * @description SlBankAccount表单
 * @company 智维软件
 */
SlBankAccountForm =Ext.extend(Ext.Window, {
			//构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				this.initUIComponents();
				SlBankAccountForm.superclass.constructor.call(this, {
							id : 'SlBankAccountFormWin',
							layout : 'fit',
							border : false,
							items : this.formPanel,
							modal : true,
							height : 345,
							width : 610,
//							maximizable : true,
							title : '银行账户详细信息',
							buttonAlign : 'center',
							buttons : [
										{
											text : '保存',
											iconCls : 'btn-save',
											scope : this,
											hidden:this.isAllReadOnly,
											disabled:this.isAllReadOnly,
											handler : this.save
										}, {
											text : '重置',
											iconCls : 'btn-reset',
											scope : this,
											hidden:this.isAllReadOnly,
											disabled:this.isAllReadOnly,
											handler : this.reset
										}, {
											text : '取消',
											iconCls : 'btn-cancel',
											scope : this,
											hidden:this.isAllReadOnly,
											disabled:this.isAllReadOnly,
											handler : this.cancel
										}
							         ]
				});
			},//end of the constructor
			//初始化组件
			initUIComponents : function() {
				this.formPanel = new Ext.FormPanel({
                            layout:'column',
							bodyStyle : 'padding:10px',
							autoScroll : true,
							monitorValid : true,
							frame : true,
						plain : true,
						labelAlign : "right",
							//id : 'SlPersonMainForm',
							defaults : {
								anchor : '96%',
								labelWidth : 80,
								columnWidth : 1,
							    layout : 'column'
							},
							//defaultType : 'textfield',
							items : [{
								name : 'slBankAccount.bankAccountId',
								xtype : 'hidden',
								value : this.bankAccountId == null ? '' : this.bankAccountId
							},{
								 columnWidth : .5,
								 labelWidth : 73,
								 layout : 'form',
								 items :[{
										xtype:'combo',
										             mode : 'local',
									               displayField : 'name',
									              valueField : 'id',
									              editable : false,
									              readOnly:this.isAllReadOnly,
									                 width : 70,
									                 store : new Ext.data.SimpleStore({
											        fields : ["name", "id"],
										            data : [["个人", "0"],
													     	["公司", "1"]]
									              	}),
										            triggerAction : "all",
									                hiddenName:"slBankAccount.openType",
								                	fieldLabel : '开户类型',	
								                	anchor : '96%',
								                	allowBlank:false,
										          	name : 'slBankAccount.openType',
										          	listeners : {
															scope : this,
															afterrender : function(combox) {
																var st = combox.getStore();
																combox.setValue(st.getAt(0).data.id);
																combox.fireEvent("select", combox,st.getAt(0), 0);
																
																/*st.on("load", function(){
																	alert('11111')
																	combox.setValue(0);
																	combox.fireEvent("select", combox,st.getAt(0), 0);
																	
																})*/
																combox.clearInvalid();
															},
															select : function(combox, record, index) {
															var v = record.data.id;
															var obj =this.getCmpByName('slBankAccount.accountType');
															obj.enable();
															obj.setValue();
															obj.store.removeAll()
															if(v==0){
																arrStore = new Ext.data.SimpleStore({
															        fields : ["name", "id"],
														            data : [["个人储蓄户", "0"],["基本户", "1"],["一般户", "2"]]
																});
																obj.store.insert(0,arrStore.getAt(0));
															}else{
																arrStore = new Ext.data.SimpleStore({
															        fields : ["name", "id"],
														            data : [["个人储蓄户", "0"],["基本户", "1"],["一般户", "2"]]
																});
																obj.store.insert(0,arrStore.getAt(1));
																obj.store.insert(1,arrStore.getAt(2));
															}
														}
													
											}
								 }]
								 },{
									 columnWidth : .5,
								     layout : 'form',
								      labelWidth : 73,
								      items :[{
									xtype:'combo',
										             mode : 'local',
									               displayField : 'name',
									              valueField : 'id',
									              id :　'accountTypeid',
									              editable : false,
									              readOnly:this.isAllReadOnly,
									                 width : 70,
									                 store : new Ext.data.SimpleStore({
										       		 fields : ["name", "id"],
									           		 data : [["个人储蓄户", "0"],["基本户", "1"],["一般户", "2"]]
									              	}),
										             triggerAction : "all",
									                hiddenName:"slBankAccount.accountType",
								                	fieldLabel : '账户类型',	
								                	anchor : '96%',
								                	allowBlank:false,
										          	name : 'slBankAccount.accountType'
										          	,
										          	listeners : {
															afterrender : function(combox) {
																	var st = combox.getStore();
																	combox.setValue(combox.getValue());
																}
							
													} 
										 }]
										
								 },{
									xtype : 'hidden',
									name : 'slBankAccount.openBankId'
								},{  
									  columnWidth : .5,
									  labelWidth : 73,
								      layout : 'form',
									  items :[{
										fieldLabel : "银行名称",
										allowBlank:false,
										xtype:'combo',
										editable : false,
										readOnly:this.isAllReadOnly,
										triggerClass :'x-form-search-trigger',
										hiddenName : "slBankAccount.openBankName",
										value:this.bankName,
//										name : "slBankAccount.openBankName",
										editable : false,
										blankText : "银行名称不能为空，请正确填写!",
										anchor : "96%",
										onTriggerClick : function(){
											var op = this.ownerCt.ownerCt.ownerCt;
											var selectBankList = function(obj) {
												op.getCmpByName('slBankAccount.openBankId').setValue("");
												op.getCmpByName('slBankAccount.openBankName').setValue("");
												if (obj.bankId != 0 && obj.bankId != "")
													op.getCmpByName('slBankAccount.openBankId').setValue(obj.bankId);
												if (obj.bankname != 0&& obj.bankname != "")
													op.getCmpByName('slBankAccount.openBankName').setValue(obj.bankname);
											}
											SelectBankList(selectBankList);
										}/*,
										listeners : {
											scope : this,
											afterrender : function(combox) {
												var st = combox.getStore();
												st.on("load", function() {
													combox.setValue(combox.getValue());
													
												})
												combox.clearInvalid();
											}
											
										}*/
									 }]
								 },{
									columnWidth : .5,
									layout : 'form',
									labelWidth : 73,
									items : [{
										fieldLabel : "网点名称",
	                                    name : 'slBankAccount.bankOutletsName',
									    xtype:'textfield',
									    readOnly:this.isAllReadOnly,
									    anchor : '96%',
									    allowBlank:false
									}]
								}
								 ,{
								 columnWidth : .5,
								  labelWidth : 73,
								 layout : 'form',
								 items :[{
									 
									fieldLabel : '开户名称',	
	                              name : 'slBankAccount.name',
	                              readOnly:this.isAllReadOnly,
									xtype:'textfield',
									anchor : '96%',
									allowBlank:false
							 }]} ,{
								columnWidth : .5,
								labelWidth : 73,
							    layout : 'form',
							    items :[{
									fieldLabel : '账号',	
							 		name : 'accountNum',
							 		readOnly:this.isAllReadOnly,
								  	maxLength: 100,
								  	xtype:'textfield',
								  	anchor : '96%',
								  	allowBlank:false,
								  	enableKeyEvents:true,
									listeners:{
										keyup:{buffer:150, fn:function(f, e) {
											if(Ext.EventObject.ESC == e.getKey()) {
												f.onTriggerClick();
											}else{
												var card = this.getRawValue();
												var len = this.getRawValue().length;
												var reg = /\s{1,}/g;
												var card_ = "";
												//去除空格 
									        	card = card.replace(reg,"");
									        	for(var i=0;i<len;i++){
									        		if(i==3||i==7||i==11||i==15||i==19||i==23||i==27||i==31||i==34||i==37){
									        			card_ = card_ + card.charAt(i) + " ";
									        		}else{
									        			card_ = card_ + card.charAt(i);
									        		}
									        	}
									        	this.setRawValue(card_);
											}
									 	}},
									 	blur : function(nf){
									 		var v=nf.getValue();
									 		var value=v.replace(/[ ]/g, "");
									 		nf.nextSibling().setValue(value)
									 	}
									}
							},{
								xtype : 'hidden',
								name : 'slBankAccount.account'
							}]},{
								 columnWidth : .45,
								  labelWidth : 73,
								 layout : 'form',
								 items :[{
									  fieldLabel : '期初金额',	
								 		xtype:'textfield',
								 		anchor : '100%',
								 		allowBlank:false,
								 		readOnly:this.isAllReadOnly,
								 		fieldClass:'field-align',
								 		id:'slbankrawMoney',
								 		listeners: {  
									      scope :this,
										  change: function(nf){
										     var value= nf.getValue();
												var index=value.indexOf(".");
											    if(index<=0){ //如果第一次输入 没有进行格式化
											       nf.setValue(Ext.util.Format.number(value,'0,000.00'))
											       this.getCmpByName("slBankAccount.rawMoney").setValue(value);
											    }
											    else{
											       
											       if(value.indexOf(",")<=0){
											       	     var ke=Ext.util.Format.number(value,'0,000.00')
											       	     nf.setValue(ke);
											             this.getCmpByName("slBankAccount.rawMoney").setValue(value);
											       }
											       else{
											       	    var last=value.substring(index+1,value.length);
											       	    if(last==0){
											       	       var temp=value.substring(0,index);
											       	       temp=temp.replace(/,/g,"");
											       	       this.getCmpByName("slBankAccount.rawMoney").setValue(temp);
											       	    }
											       	    else{
											       	      var temp=value.replace(/,/g,"");
											              this.getCmpByName("slBankAccount.rawMoney").setValue(temp);
											       	    }
											       }
											    }																	  
										  }							  
																		  }
									
									 
								 },{
									    xtype : 'hidden',
										 name : 'slBankAccount.rawMoney'
										
									}]},{
									 columnWidth : .05, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth :18,
									items : [{
										fieldLabel : "<span style='margin-left:1px'>元</span> ",
										labelSeparator : '',
										readOnly:this.isAllReadOnly,
										anchor : "100%"
									
								 }]},{
								 columnWidth : .5,
								  labelWidth : 74,
								 layout : 'form',
								 items :[{
										fieldLabel : '开户时间',	
								 	name : 'slBankAccount.recordTime',
								    xtype:'datefield',
								    readOnly:this.isAllReadOnly,
								  anchor : '96%',
								   format:'Y-m-d',
								   value:new Date(),
								   allowBlank:false
											 
										 }]
								 },
								 	{
									 columnWidth : 1,
									  labelWidth : 73,
								     layout : 'form',
								      items :[{
									  fieldLabel : '银行地址',	
								 	  name : 'slBankAccount.address',
								 	  readOnly:this.isAllReadOnly,
								      xtype:'textfield',
								      anchor : '98%'
										 },{
										fieldLabel : '备注',	
								 	    name : 'slBankAccount.remarks',
								 	    readOnly:this.isAllReadOnly,
								        anchor : '98%',
								         xtype:'textarea'
								
											 
										 }]
								 }
							
																																			]
						});
				
				//加载表单对应的数据	
				if (this.bankAccountId != null && this.bankAccountId != 'undefined') {
					var comp ={columnWidth : .95,
						       labelWidth : 73,
						       layout : 'form',
						       items:[{
							      fieldLabel : '末期总余额',
							      xtype:'textfield',
							      fieldClass:'field-align',
							      readOnly:true,
						          id : 'slbankfinalMoney',
						          anchor : '100%',
						          listeners: {  
												      scope :this,
													  change: function(p){
													  	var value= p.getValue();
													    this.getCmpByName("slBankAccount.finalMoney").setValue(value);
														p.setValue(Ext.util.Format.number(value,'0,000.00'))
													  		
													  }																		  
																		  
																		  }
									
									 
								 },{
									    xtype : 'hidden',
										 name : 'slBankAccount.finalMoney'
										
									}
								]}
						
						var comp1={
									 columnWidth : .04, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth :18,
									items : [{
										fieldLabel : "<span style='margin-left:1px'>元</span> ",
										labelSeparator : '',
										anchor : "94%"
									
								 }]}
					this.formPanel.insert(this.formPanel.items.getCount()+1,comp);
					this.formPanel.insert(this.formPanel.items.getCount()+1,comp1)
				//	var comp =new Ext.form.TextField({
				//      fieldLabel : '末期余额',	
			    //      name : 'slBankAccount.finalMoney'
			        
				//	});
				//	this.formPanel.getComponent(0).insert((0),comp);

					
					this.formPanel.loadData({
								url : __ctxPath + '/creditFlow/finance/getSlBankAccount.do?bankAccountId='+ this.bankAccountId,
								root : 'data',
								preName : 'slBankAccount',
								scope:this,
								success:function(resp,options){
             						var result=Ext.decode(resp.responseText);
             						Ext.getCmp('slbankrawMoney').setValue(Ext.util.Format.number(result.data.rawMoney,'0,000.00'));
             						Ext.getCmp('slbankfinalMoney').setValue(Ext.util.Format.number(result.data.finalMoney,'0,000.00'));
             						/*if(this.getCmpByName('slBankAccount.openBankName')){
             							this.getCmpByName('slBankAccount.openBankName').setValue(result.data.bankName);
             						}*/
             						var card = result.data.account;
									var len = result.data.account.length;
									var reg = /\s{1,}/g;
									var card_ = "";
									//去除空格 
						        	card = card.replace(reg,"");
						        	for(var i=0;i<len;i++){
						        		if(i==3||i==7||i==11||i==15||i==19||i==23||i==27||i==31||i==34||i==37){
						        			card_ = card_ + card.charAt(i) + " ";
						        		}else{
						        			card_ = card_ + card.charAt(i);
						        		}
						        	}
						        	this.getCmpByName('accountNum').setRawValue(card_);
             						
             						var obj = Ext.getCmp('accountTypeid');
									var arrStore = null;
             						var v=result.data.openType;
             						
             							if(v==0){
										arrStore = new Ext.data.SimpleStore({
									        fields : ["name", "id"],
								            data : [["个人储蓄户", "0"]]
										});
									}else{
										arrStore = new Ext.data.SimpleStore({
									        fields : ["name", "id"],
								            data : [["基本户", "1"],["一般户", "2"]]
						              	});
									}
									obj.clearValue();
		                            obj.store = arrStore;
//		                            obj.view.setStore(arrStore);
//		                            arrStore.load();
		                            var a=result.data.accountType;
             						if(a==0){
             						  obj.setValue("0");
             						}
             						if(a==1){
             							obj.setValue("1");
             						}
             						if(a==2){
             							obj.setValue("2");
             						}
             					
								}
							});
					
					
				}
				
				
			},//end of the initcomponents

			/**
			 * 重置
			 * @param {} formPanel
			 */
			reset : function() {
				this.formPanel.getForm().reset();
			},
			/**
			 * 取消
			 * @param {} window
			 */
			cancel : function() {
				this.close();
			},
			/**
			 * 保存记录
			 */
			save : function() {
				if(this.formPanel.getForm().isValid()){
					this.formPanel.getForm().submit({
						method : 'POST',
						scope :this,
						waitTitle : '连接',
						waitMsg : '消息发送中...',
						url:__ctxPath + '/creditFlow/finance/saveSlBankAccount.do',
						success : function(form ,action) {
							objSubmit = Ext.util.JSON.decode(action.response.responseText);
							if(objSubmit.exsit == false){
								Ext.ux.Toast.msg('操作信息', '成功信息保存！');
								var gridPanel = Ext.getCmp('SlBankAccountGrid');
								if (gridPanel != null) {
									gridPanel.getStore().reload();
								}
								this.close();
							}else{
							    Ext.ux.Toast.msg('操作信息', '账号添加重复！');
							}
						},
						failure : function(form ,action) {
							 Ext.MessageBox.show({
				            title : '操作信息',
				            msg : '信息保存出错，请联系管理员！',
				            buttons : Ext.MessageBox.OK,
				            icon : 'ext-mb-error'
				        });
						}
					})
				}
			}//end of save

		});

var selectBankLinkMan = function(array){
	   // var bankname="";
	  // for(var i=(array.length);i--;i>0){
		// if(i==0){bankname=bankname+k}else{  
	  // 	var k=array[i].text;
	  //	bankname=bankname+k+"_";}
	  	
	//  }
	 //  alert(array[0].attributes.remarks);
	 //  bankname=bankname+array[0].text;
		if(Ext.getCmp('bankId'))Ext.getCmp('bankId').setValue(array[0].id) ;
		if(Ext.getCmp('bankName'))Ext.getCmp('bankName').setValue(array[0].attributes.remarks);
		//alert(array[0].text);
		
		
		
	};