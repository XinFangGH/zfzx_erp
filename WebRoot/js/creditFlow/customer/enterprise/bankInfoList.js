function bankInfoListWin(orgVal,isReadOnly){
	var pageSize = 10 ;
	var anchor = '100%';
	Ext.Ajax.request({   
    	url: __ctxPath + '/creditFlow/customer/enterprise/isEntEmptyEnterprise.do',   
   	 	method:'post',   
    	params:{organizecode:orgVal},   
    	success: function(response, option) {   
        	var obj = Ext.decode(response.responseText);
        	var enterpriseId = obj.data.id;
			var jStore_bankInfo = new CS.data.JsonStore( {
				url : __ctxPath + '/credit/customer/enterprise/queryEnterpriseBank.do',
				fields : [ {
					name : 'id'
				},{
					name : 'enterpriseid'
				}, {
					name : 'bankid'
				}, {
					name : 'bankname'
				}, {
					name : 'accountnum'
				}, {
					name : 'openType'
				},{
					name : 'accountType'
				},{
					name : 'iscredit'
				},{
					name : 'creditnum'
				},{
					name : 'creditpsw'
				},{
					name : 'remarks'
				},{
					name : 'isEnterprise'
				},{
					name : 'openCurrency'
				},{
					name : 'name'
				},{
				    name : 'outletsname'
				},{
					name : 'areaName'
				}],
				baseParams : {
					id : enterpriseId,
				  isEnterprise:"0"
				},
				listeners : {
					'load':function(){
						gPanel_enterpriseBank.getSelectionModel().selectFirstRow() ;
					},
					'loadexception' : function(){
						Ext.ux.Toast.msg('提示','数据加载失败，请保证网络畅通！');			
					}
				}
			});
			var button_add_debt = new CS.button.AButton({
				handler : function() {
					var fPanel_addBank = new CS.form.FormPanel({
						url:__ctxPath + '/credit/customer/enterprise/addEnterpriseBank.do',
						labelWidth : 90,
						monitorValid : true,
						bodyStyle:'padding:10px',
						width : 488,
						height : 278,
						items : [ {
				            layout:'column',
				            items:[{
								 columnWidth : .5,
								 labelWidth : 73,
								 layout : 'form',
								 items :[{
										xtype:'combo',
										             mode : 'local',
									               displayField : 'name',
									              valueField : 'id',
									              editable : false,
									                 width : 70,
									                 store : new Ext.data.SimpleStore({
											        fields : ["name", "id"],
										            data : [["个人", "0"],
													     	["公司", "1"]]
									              	}),
										             triggerAction : "all",
									                hiddenName:"enterpriseBank.openType",
								                	fieldLabel : '开户类型',	
								                	anchor : '96%',
								                	allowBlank:false,
										          	name : 'enterpriseBank.openType',
										          	listeners : {
															scope : this,
															select : function(combox, record, index) {
															var v = record.data.id;
															var obj = Ext.getCmp('accountTypeid');
															obj.enable();
															var arrStore = null;
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
								                            obj.view.setStore(arrStore);
								                      //      arrStore.load();
														}
													
											}
								 }]
								 },{
									 columnWidth : .5,
								     layout : 'form',
								      labelWidth : 98,
								      items :[{
									xtype:'combo',
						             mode : 'local',
					               displayField : 'name',
					              valueField : 'id',
					              id :　'accountTypeid',
					              editable : false,
					                 width : 70,
						             triggerAction : "all",
					                hiddenName:"enterpriseBank.accountType",
				                	fieldLabel : '账户类型',	
				                	anchor : '96%',
				                	allowBlank:false,
						          	name : 'enterpriseBank.accountType'
										 }]
										
								 },{
									columnWidth : .33,
									layout : 'form',
									labelWidth : 73,
									items : [{					
										fieldLabel : "银行名称",
										xtype : "combo",
										displayField : 'itemName',
										valueField : 'itemId',
										allowBlank:false,
										triggerAction : 'all',
										store : new Ext.data.ArrayStore({
											url : __ctxPath
															+ '/creditFlow/common/getBankListCsBank.do',
													fields : ['itemId', 'itemName'],
													autoLoad : true
										}),
										mode : 'remote',
										hiddenName : "enterpriseBank.bankid",
										editable : false,
										blankText : "银行名称不能为空，请正确填写!",
										anchor : "100%",
										allowBlank:false,
										listeners : {
											scope : this,
											afterrender : function(combox) {
												var st = combox.getStore();
												st.on("load", function() {
													combox.setValue(combox.getValue());
													
												})
												combox.clearInvalid();
											}
											
										}
									
									}]
								},{
									columnWidth : .34,
									layout : 'form',
									labelWidth : 73,
									items : [{					
										fieldLabel : "网点名称",
	                                    name : 'enterpriseBank.bankOutletsName',
									    xtype:'textfield',
									    anchor : '96%',
									     allowBlank:false
								 
									
									}]
								},{
									columnWidth : .33,
									layout : 'form',
									labelWidth : 73,
									items : [{
										name : 'enterpriseBank.areaId',
										xtype:'hidden'
									},{					
										//id:'bankName',	
										name : 'enterpriseBank.areaName',
									    hiddenName : 'enterpriseBank.areaName',
										fieldLabel : '开户地区',	
										allowBlank:false,
										anchor : '100%',
										//value : '中国银行',
							//			submitValue:false,
				                      	xtype:'trigger',
										triggerClass :'x-form-search-trigger',
										editable : false,
										scope : this,
										onTriggerClick : function(){
											var com=this
											var selectBankLinkMan = function(array){
												var str="";
												var idStr=""
												for(var i=array.length-1;i>=0;i--){
													str=str+array[i].text+"-"
													idStr=idStr+array[i].id+","
												}
												if(str!=""){
													str=str.substring(0,str.length-1);
												}
												if(idStr!=""){
													idStr=idStr.substring(0,idStr.length-1)
												}
												com.previousSibling().setValue(idStr)
												com.setValue(str);
											};
											selectDictionary('area',selectBankLinkMan);
									}
				
													 
									}]
								}
								 
								/* ,{  
									  columnWidth : 1,
									   labelWidth : 73,
								     layout : 'form',
									 items :[{
										 
								    fieldLabel : '支行id',
								     id:'bankId',
									// name : 'banksubID',
									name : 'enterpriseBank.bankid',
									 xtype:'hidden'
										 
									 }]
								 }
								
								 ,{  
									  columnWidth : 1,
									  labelWidth : 73,
								     layout : 'form',
									 items :[{
									id:'bankName',	
									name : 'enterpriseBank.bankname',
								    hiddenName : 'enterpriseBank.bankname',
									fieldLabel : '银行名称',	
									//value : '中国银行',
						//			submitValue:false,
			                      xtype:'trigger',
								triggerClass :'x-form-search-trigger',
								emptyText : '点击选择银行----------------',
								editable : false,
								allowBlank:false,
								onTriggerClick : function(){
									var selectBankLinkMan = function(array){
										Ext.getCmp('bankId').setValue(array[0].id) ;
										Ext.getCmp('bankName').setValue(array[0].attributes.remarks);
									};
									selectDictionary('bank',selectBankLinkMan);
								},
								anchor : '98%'

									 }]
								 }*/,{
				            	columnWidth:.5,
				                layout: 'form',
				                defaults : {anchor:anchor},
				                items :[{
				                	xtype: 'radiogroup',
					                fieldLabel: '银行开户类别',
					                items: [
					                    {boxLabel: '本币开户', name: 'enterpriseBank.openCurrency', inputValue: "0",checked:true},
					                    {boxLabel: '外币开户', name: 'enterpriseBank.openCurrency', inputValue: "1",checked:false}
					                ]
				                }]
				            },{
				            	columnWidth:.5,
				                layout: 'form',
				                defaults : {anchor:anchor},
				                items :[{
				                	xtype: 'radiogroup',
					                fieldLabel: '是否是放款账户',
					                items: [
					                    {boxLabel: '是', name: 'enterpriseBank.iscredit', inputValue: "0",checked:true},
					                    {boxLabel: '否', name: 'enterpriseBank.iscredit', inputValue: "1",checked:false}
					                ]
				                }]
				            }
								 ,{
								 columnWidth : .5,
								  labelWidth : 73,
								 layout : 'form',
								 items :[{
									 
									fieldLabel : '开户名称',	
	                              name : 'enterpriseBank.name',
									xtype:'textfield',
									anchor : '96%',
									allowBlank:false
								 }]} ,{
									 columnWidth : .5,
									  labelWidth : 98,
								     layout : 'form',
								      items :[{
									fieldLabel : '贷款卡卡号',	
								 	name : 'enterpriseBank.accountnum',
								  maxLength: 100,
								  xtype:'textfield',
								  anchor : '96%',
								  allowBlank:false
							}]},{
				              	columnWidth : 1,
								layout : 'form',
								  labelWidth : 73,
								items :[{
									xtype : 'textarea',
									 anchor : '98%',
									fieldLabel : '备注',
									height : 80,
									name : 'enterpriseBank.remarks'
								}]
				              },{
				              	xtype : 'hidden',
								name : 'enterpriseBank.enterpriseid',
								value : enterpriseId
				              },{
				              	xtype : 'hidden',
								name : 'enterpriseBank.isEnterprise',
								value : "0"
				              }]
				        } ],
						buttons : [ {
							text : '保存',
							iconCls : 'submitIcon',
							formBind : true,
							handler : function() {
								fPanel_addBank.getForm().submit({
									method : 'POST',
									waitTitle : '连接',
									waitMsg : '消息发送中...',
									success : function(form ,action) {
										obj = Ext.util.JSON.decode(action.response.responseText);
										if(obj.exsit == false){
											Ext.Msg.confirm('信息确认', obj.msg, function(btn){
															//if(btn=="yes") {
																win_add_company.destroy();
																jStore_bankInfo.reload();
															//}
														})
										}else{
											Ext.ux.Toast.msg('状态' ,'保存成功' );
											if(typeof(storeObj) != 'undefined') {
												storeObj.reload();
											}
											if(null != winObj){
												winObj.destroy();
											}
											formObj.doLayout();
										}
										/*success : function(form ,action) {
										 Ext.ux.Toast.msg('状态', '保存成功!');
														jStore_bankInfo.reload();
														win_add_company.destroy();
										}*/
									},
									failure : function(form, action) {
										if(action.response.status==0){
											Ext.ux.Toast.msg('状态','连接失败，请保证服务已开启');
										}else if(action.response.status==-1){
											Ext.ux.Toast.msg('状态','连接超时，请重试!');
										}else{
											Ext.ux.Toast.msg('状态','添加失败!');	
										}
									}
								});
							}
						}]
					});
					var win_add_company = new Ext.Window({
						id : 'win_addBankInfo',
						title: '新增<font color=red><'+obj.data.shortname+'></font>企业开户银行信息',
						layout : 'fit',
						width :(screen.width-180)*0.55 ,
						height : 310,
						closable : true,
						resizable : false,
						constrainHeader : true ,
						collapsible : true, 
						plain : true,
						border : false,
						modal : true,
						buttonAlign: 'center',
						bodyStyle : 'overflowX:hidden',
						items :[fPanel_addBank]
//						listeners : {
//							'beforeclose' : function(){
//								if(fPanel_addBank != null){
//									if(fPanel_addBank.getForm().isDirty()){
//										Ext.Msg.confirm('操作提示','数据被修改过,是否保存',function(btn){
//											if(btn=='yes'){
//												fPanel_addBank.buttons[0].handler.call() ;
//											}else{
//												fPanel_addBank.getForm().reset() ;
//												win_add_company.destroy() ;
//											}
//										}) ;
//										return false ;
//									}
//								}
//							}
//						}
					});
					win_add_company.show();
				}
			});
			var button_update_company = new CS.button.UButton({
				handler : function() {
					var selected = gPanel_enterpriseBank.getSelectionModel().getSelected();
					if (null == selected) {
						Ext.ux.Toast.msg('状态', '请选择一条记录!');
					}else{
						var id = selected.get('id');
						Ext.Ajax.request({
							url : __ctxPath + '/creditFlow/customer/common/findEnterpriseBank.do',
							method : 'POST',
							success : function(response,request) {
								var obj = Ext.decode(response.responseText);
								var bankData = obj.data ;
								var fPanel_updateBankInfo = new CS.form.FormPanel({
									url:__ctxPath + '/creditFlow/customer/enterprise/updateEnterpriseBank.do',
									bodyStyle : 'padding:10px;',
									width : 488,
									height : 278,
									labelWidth : 90,
									monitorValid : true,
									items : [ {
				            layout:'column',
				            items:[{
								 columnWidth : .5,
								 labelWidth : 73,
								 layout : 'form',
								 items :[{
										xtype:'combo',
										             mode : 'local',
									               displayField : 'name',
									              valueField : 'id',
									              editable : false,
									                 width : 70,
									                 store : new Ext.data.SimpleStore({
											        fields : ["name", "id"],
										            data : [["个人", "0"],
													     	["公司", "1"]]
									              	}),
										             triggerAction : "all",
									                hiddenName:"enterpriseBank.openType",
								                	fieldLabel : '开户类型',	
								                	anchor : '96%',
								                	allowBlank:false,
										          	name : 'enterpriseBank.openType',
										      	   value : bankData.openType,
										          	listeners : {
															scope : this,
															select : function(combox, record, index) {
															var v = record.data.id;
															var obj = Ext.getCmp('accountTypeid');
															obj.enable();
															var arrStore = null;
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
								                            obj.view.setStore(arrStore);
								                      //      arrStore.load();
														}
													
											}
								 }]
								 },{
									 columnWidth : .5,
								     layout : 'form',
								      labelWidth : 98,
								      items :[{
									xtype:'combo',
						             mode : 'local',
					               displayField : 'name',
					              valueField : 'id',
					              id :　'accountTypeid',
					              editable : false,
					                 width : 70,
						             triggerAction : "all",
					                hiddenName:"enterpriseBank.accountType",
					                   value : bankData.accountType,
					                    store :bankData.openType=="0"?new Ext.data.SimpleStore({
									        fields : ["name", "id"],
								            data : [["个人储蓄户", "0"]]
										}):new Ext.data.SimpleStore({
									        fields : ["name", "id"],
								            data : [["基本户", "1"],["一般户", "2"]]
						              	}),
				                	fieldLabel : '账户类型',	
				                	anchor : '96%',
				                	allowBlank:false,
						          	name : 'enterpriseBank.accountType'
										 }]
										
								 },{
									columnWidth : .33,
									layout : 'form',
									labelWidth : 73,
									items : [{					
										fieldLabel : "银行名称",
										xtype : "combo",
										displayField : 'itemName',
										valueField : 'itemId',
										triggerAction : 'all',	
										allowBlank:false,
										store : new Ext.data.ArrayStore({
											url : __ctxPath
															+ '/creditFlow/common/getBankListCsBank.do',
													fields : ['itemId', 'itemName'],
													autoLoad : true
										}),
										mode : 'remote',
										hiddenName : "enterpriseBank.bankid",
										 value : bankData.bankid,
										editable : false,
										blankText : "银行名称不能为空，请正确填写!",
										anchor : "100%",
										listeners : {
											scope : this,
											afterrender : function(combox) {
												var st = combox.getStore();
												st.on("load", function() {
													combox.setValue(combox.getValue());
													
												})
												combox.clearInvalid();
											}
											
										}
									
									}]
								},{
									columnWidth : .34,
									layout : 'form',
									labelWidth : 73,
									items : [{	
											fieldLabel : "网点名称",
	                                         name : 'enterpriseBank.bankOutletsName',
									       xtype:'textfield',
										allowBlank:false,
										value : bankData.bankOutletsName,
										anchor : "96%"
										
									
									}]
								},{
									columnWidth : .33,
									layout : 'form',
									labelWidth : 73,
									items : [{
										name : 'enterpriseBank.areaId',
										 value : bankData.areaId,
										xtype:'hidden'
									},{					
										//id:'bankName',	
										name : 'enterpriseBank.areaName',
									    hiddenName : 'enterpriseBank.areaName',
									     value : bankData.areaName,
										fieldLabel : '开户地区',	
										anchor : '100%',
										//value : '中国银行',
							//			submitValue:false,
										allowBlank:false,
				                      	xtype:'trigger',
										triggerClass :'x-form-search-trigger',
										editable : false,
										scope : this,
										onTriggerClick : function(){
											var com=this
											var selectBankLinkMan = function(array){
												var str="";
												var idStr=""
												for(var i=array.length-1;i>=0;i--){
													str=str+array[i].text+"-"
													idStr=idStr+array[i].id+","
												}
												if(str!=""){
													str=str.substring(0,str.length-1);
												}
												if(idStr!=""){
													idStr=idStr.substring(0,idStr.length-1)
												}
												com.previousSibling().setValue(idStr)
												com.setValue(str);
											};
											selectDictionary('area',selectBankLinkMan);
									}
				
													 
									}]
								}
								 
								 
								 /*,{  
									  columnWidth : 1,
									   labelWidth : 73,
								     layout : 'form',
									 items :[{
										 
								    fieldLabel : '支行id',
								     id:'bankId',
									// name : 'banksubID',
									name : 'enterpriseBank.bankid',
									   value : bankData.bankid,
									 xtype:'hidden'
									
										 
									 }]
								 }
								
								 ,{  
									  columnWidth : 1,
									  labelWidth : 73,
								     layout : 'form',
									 items :[{
									id:'bankName',	
									name : 'enterpriseBank.bankname',
								    hiddenName : 'enterpriseBank.bankname',
								       value : bankData.bankname,
									fieldLabel : '银行名称',	
									//value : '中国银行',
						//			submitValue:false,
			                      xtype:'trigger',
								triggerClass :'x-form-search-trigger',
								emptyText : '点击选择银行----------------',
								editable : false,
								allowBlank:false,
								onTriggerClick : function(){
									var selectBankLinkMan = function(array){
										Ext.getCmp('bankId').setValue(array[0].id) ;
										Ext.getCmp('bankName').setValue(array[0].attributes.remarks);
									};
									selectDictionary('bank',selectBankLinkMan);
								},
								anchor : '98%'

									 }]
								 }*/,{
				            	columnWidth:.5,
				                layout: 'form',
				                defaults : {anchor:anchor},
				                items :[{
				                	xtype: 'radiogroup',
					                fieldLabel: '银行开户类别',
					                items: [
					                    {boxLabel: '本币开户', name: 'enterpriseBank.openCurrency', inputValue: "0",checked:bankData.openCurrency=="0"?true:false},
					                    {boxLabel: '外币开户', name: 'enterpriseBank.openCurrency', inputValue: "1",checked:bankData.openCurrency=="1"?true:false}
					                ]
				                }]
				            },{
				            	columnWidth:.5,
				                layout: 'form',
				                defaults : {anchor:anchor},
				                items :[{
				                	xtype: 'radiogroup',
					                fieldLabel: '是否是放款账户',
					                items: [
					                    {boxLabel: '是', name: 'enterpriseBank.iscredit',inputValue: "0",checked:bankData.iscredit=="0"?true:false},
					                    {boxLabel: '否', name: 'enterpriseBank.iscredit', inputValue: "1",checked:bankData.iscredit=="1"?true:false}
					                ]
				                }]
				            }
								 ,{
								 columnWidth : .5,
								  labelWidth : 73,
								 layout : 'form',
								 items :[{
									 
									fieldLabel : '开户名称',	
	                              name : 'enterpriseBank.name',
	                                value : bankData.name,
									xtype:'textfield',
									anchor : '96%',
									allowBlank:false
								 }]} ,{
									 columnWidth : .5,
									  labelWidth : 98,
								     layout : 'form',
								      items :[{
									fieldLabel : '贷款卡卡号',	
								 	name : 'enterpriseBank.accountnum',
								 	  value : bankData.accountnum,
								  maxLength: 100,
								  xtype:'textfield',
								  anchor : '96%',
								  allowBlank:false
							}]},{
				              	columnWidth : 1,
								layout : 'form',
								  labelWidth : 73,
								items :[{
									xtype : 'textarea',
									 anchor : '98%',
									fieldLabel : '备注',
									height : 80,
									name : 'enterpriseBank.remarks',
									  value : bankData.remarks
								}]
				              },{
				              	xtype : 'hidden',
								name : 'enterpriseBank.enterpriseid',
								  value : bankData.enterpriseid,
								value : enterpriseId
				              },{
				              	xtype : 'hidden',
								name : 'enterpriseBank.isEnterprise',
								value : "0"
				              },{
				              	xtype : 'hidden',
								name : 'enterpriseBank.companyId',
								value :  bankData.companyId
				              },{
				              	xtype : 'hidden',
								name : 'enterpriseBank.id',
								value :bankData.id
				              }]
							        	} ],
									buttons : [ {
										text : '保存',
										iconCls : 'submitIcon',
										formBind : true,
										handler : function() {
											fPanel_updateBankInfo.getForm().submit({
												method : 'POST',
												waitTitle : '连接',
												waitMsg : '消息发送中...',
												/*
													Ext.ux.Toast.msg('状态', '编辑成功!');
																	jStore_bankInfo.reload();
																	window_update.destroy();
												},*/
												success : function(form,action) {
														obj = Ext.util.JSON.decode(action.response.responseText);
												if(obj.exsit == false){
													//Ext.Msg.alert('状态' ,obj.msg );
														Ext.Msg.confirm('信息确认', obj.msg, function(btn){
															//if(btn=="yes") {
																window_update.destroy();
																jStore_bankInfo.reload();
															//}
														})
		
												}else{
													Ext.ux.Toast.msg('状态' ,'编辑成功' );
													if(typeof(storeObj) != 'undefined') {
														storeObj.reload();
													}
													if(null != winObj){
														winObj.destroy();
													}
													formObj.doLayout();
												}
												},
												failure : function(form, action) {
													if(action.response.status==0){
														Ext.ux.Toast.msg('状态','连接失败，请保证服务已开启');
													}else if(action.response.status==-1){
														Ext.ux.Toast.msg('状态','连接超时，请重试!');
													}else{
														Ext.ux.Toast.msg('状态','编辑失败!');	
													}
												}
											});
										}
									}]
								});	
								var window_update = new Ext.Window({
									id : 'win_updateBankInfo',
									title: '编辑<font color=red><'+obj.data.shortname+'></font>企业开户银行信息',
									layout : 'fit',
									width :(screen.width-180)*0.55,
									height : 310,
									closable : true,
									resizable : false,
									constrainHeader : true ,
									collapsible : true, 
									plain : true,
									border : false,
									modal : true,
									bodyStyle : 'overflowX:hidden',
									buttonAlign: 'center',
									items: [fPanel_updateBankInfo],
									listeners : {
									'beforeclose' : function(){
										if(fPanel_updateBankInfo != null){
											if(fPanel_updateBankInfo.getForm().isDirty()){
												Ext.Msg.confirm('操作提示','数据被修改过,是否保存',function(btn){
													if(btn=='yes'){
														fPanel_updateBankInfo.buttons[0].handler.call() ;
													}else{
														fPanel_updateBankInfo.getForm().reset() ;
														window_update.destroy() ;
													}
												}) ;
												return false ;
											}
										}
									}
								}
								});
								window_update.show();			
							},
							failure : function(response) {					
									Ext.ux.Toast.msg('状态','操作失败，请重试');		
							},
							params: { id: id }
						});	
					}
				}
			});
			var button_delete = new CS.button.DButton({
				handler : function() {
					var selected = gPanel_enterpriseBank.getSelectionModel().getSelected();
					if (null == selected) {
						Ext.ux.Toast.msg('状态', '请选择一条记录!');
					}else{
						var id = selected.get('id');
						Ext.MessageBox.confirm('确认删除', '是否确认执行删除 ', function(btn) {
							if (btn == 'yes') {
								Ext.Ajax.request({
									url : __ctxPath + '/creditFlow/customer/enterprise/deleteRsEnterpriseBank.do',
									method : 'POST',
									success : function() {
										Ext.ux.Toast.msg('状态', '删除成功!');
										jStore_bankInfo.reload() ;
									},
									failure : function(result, action) {
										if(response.status==0){
											Ext.ux.Toast.msg('状态','连接失败，请保证服务已开启');
										}else if(response.status==-1){
											Ext.ux.Toast.msg('状态','连接超时，请重试!');
										}else{
											Ext.ux.Toast.msg('状态','删除失败!');	
										}
									},
									params: { id: id }
								});
							}
						});
					}
				}
			});
			var button_see = new CS.button.SButton({
				handler : function() {
					var selected = gPanel_enterpriseBank.getSelectionModel().getSelected();
					if (null == selected) {
						Ext.ux.Toast.msg('状态', '请选择一条记录!');
					}else{
						var id = selected.get('id');
						seeEnterpriseCompany(id);
					}
			}
			});
			var cModel_enterpriseCompany = new Ext.grid.ColumnModel(
					[
							new Ext.grid.RowNumberer(),
							{
								header : "开户类型",
								width : 120,
								sortable : true,
								dataIndex : 'openType',
								renderer:function(v){
								if(v=="0")return '个人';return '公司';}
							},{
								header : "账户类型",
								width : 100,
								sortable : true,
								dataIndex : 'accountType',
								renderer:function(v){
								if(v=="0"){return "个人储蓄户";}else if(v=="1"){return "基本户"}else{return "一般户";}
}
							}, {
								header : "银行名称",
								width : 110,
								sortable : true,
								dataIndex : 'bankname'
							}, {
								header : "网点名称",
								width : 110,
								sortable : true,
								dataIndex : 'outletsname'
							}, {
								header : "开户地区",
								width : 110,
								sortable : true,
								dataIndex : 'areaName'
							}, {
								//id : 'autobankname',
								header : "银行开户类别",
								width : 120,
								sortable : true,
								dataIndex : 'openCurrency',
								renderer:function(v){
								if(v=="0")return '本币开户';return '外币开户';}
							}, {
								header : "是否放款账号",
								width : 80,
								sortable : true,
								dataIndex : 'iscredit',
								renderer:function(v){if(v=="0")return '是';return '否';}
							},{
								header : "开户名称",
								width : 100,
								sortable : true,
								dataIndex : 'name'
							},{
								header : "贷款卡卡号",
								width : 100,
								sortable : true,
								dataIndex : 'accountnum'
							}]);
			var gPanel_enterpriseBank = new Ext.grid.GridPanel( {
				pageSize : pageSize ,
				store : jStore_bankInfo,
				stripeRows : true,
				border : false ,
				autoWidth : true,
				height:350,
				colModel : cModel_enterpriseCompany,
				selModel : new Ext.grid.RowSelectionModel(),
				//autoExpandColumn : 8,
				tbar :isReadOnly?[button_see]:[button_add_debt,button_see,button_update_company,button_delete],
				loadMask : new Ext.LoadMask(Ext.getBody(), {
					msg : "加载数据中······,请稍后······"
				}),
				bbar : new Ext.PagingToolbar({
					pageSize : pageSize,
					autoWidth : false ,
					//width : 100 ,
					style : '',
					displayInfo : true,
					displayMsg : '当前第{0} - {1}条，共 {2} 条记录',
					emptyMsg : '没有符合条件的记录',
					store : jStore_bankInfo
				}),
				listeners : {
					'rowdblclick' : function(grid,index,e){
						var id = grid.getSelectionModel().getSelected().get('id');
						seeEnterpriseCompany(id);
					}
				}
			});
			var seeEnterpriseCompany = function(id){
				Ext.Ajax.request({
					url : __ctxPath + '/creditFlow/customer/common/findEnterpriseBank.do',
					method : 'POST',
					success : function(response,request) {
						var obj = Ext.util.JSON.decode(response.responseText);
						bankData = obj.data ;
						var panel_see = new CS.form.FormPanel({
							bodyStyle : 'padding:10px;',
							width : 488,
							height : 258,
							labelWidth : 90,
							items : [ {
				            layout:'column',
				            items:[{
								 columnWidth : .5,
								 labelWidth : 73,
								 layout : 'form',
								 items :[{
										xtype:'combo',
										             mode : 'local',
									               displayField : 'name',
									              valueField : 'id',
									              editable : false,
									                 width : 70,
									                 store : new Ext.data.SimpleStore({
											        fields : ["name", "id"],
										            data : [["个人", "0"],
													     	["公司", "1"]]
									              	}),
										             triggerAction : "all",
									                hiddenName:"enterpriseBank.openType",
								                	fieldLabel : '开户类型',	
								                	anchor : '96%',
										          	name : 'enterpriseBank.openType',
										          	   readOnly  : true,
										      	   value : bankData.openType
										          
								 }]
								 },{
									 columnWidth : .5,
								     layout : 'form',
								      labelWidth : 98,
								      items :[{
									xtype:'combo',
						             mode : 'local',
					               displayField : 'name',
					              valueField : 'id',
					              id :　'accountTypeid',
					                 width : 70,
						             triggerAction : "all",
					                hiddenName:"enterpriseBank.accountType",
					                   value : bankData.accountType,
					                    store :bankData.openType=="0"?new Ext.data.SimpleStore({
									        fields : ["name", "id"],
								            data : [["个人储蓄户", "0"]]
										}):new Ext.data.SimpleStore({
									        fields : ["name", "id"],
								            data : [["基本户", "1"],["一般户", "2"]]
						              	}),
				                	fieldLabel : '账户类型',	
				                	anchor : '96%',
				                	   readOnly  : true,
						          	name : 'enterpriseBank.accountType'
										 }]
										
								 },{
									columnWidth : .33,
									layout : 'form',
									labelWidth : 73,
									items : [{					
										fieldLabel : "银行名称",
										xtype : "combo",
										displayField : 'itemName',
										valueField : 'itemId',
										triggerAction : 'all',	
										readOnly : true,
										store : new Ext.data.ArrayStore({
											url : __ctxPath
															+ '/creditFlow/common/getBankListCsBank.do',
													fields : ['itemId', 'itemName'],
													autoLoad : true
										}),
										mode : 'remote',
										hiddenName : "enterpriseBank.bankid",
										 value : bankData.bankid,
										editable : false,
										blankText : "银行名称不能为空，请正确填写!",
										anchor : "100%",
										listeners : {
											scope : this,
											afterrender : function(combox) {
												var st = combox.getStore();
												st.on("load", function() {
													combox.setValue(combox.getValue());
													
												})
												combox.clearInvalid();
											}
											
										}
									
									}]
								},{
									columnWidth : .33,
									layout : 'form',
									labelWidth : 73,
									items : [{
										name : 'enterpriseBank.areaId',
										 value : bankData.areaId,
										xtype:'hidden'
									},{
									columnWidth : .34,
									layout : 'form',
									labelWidth : 73,
									items : [{
											fieldLabel : "网点名称",
	                                    name : 'enterpriseBank.bankOutletsName',
									    xtype:'textfield',
										readOnly:true,
										value : bankData.bankOutletsName,
										anchor : "96%"
										
									}]
								},{						
										name : 'enterpriseBank.areaName',
									    hiddenName : 'enterpriseBank.areaName',
									     value : bankData.areaName,
										fieldLabel : '开户地区',	
										anchor : '100%',
										readOnly:true,
				                      	xtype:'trigger',
										triggerClass :'x-form-search-trigger',
										editable : false,
										scope : this,
										onTriggerClick : function(){
											var com=this
											var selectBankLinkMan = function(array){
												var str="";
												var idStr=""
												for(var i=array.length-1;i>=0;i--){
													str=str+array[i].text+"-"
													idStr=idStr+array[i].id+","
												}
												if(str!=""){
													str=str.substring(0,str.length-1);
												}
												if(idStr!=""){
													idStr=idStr.substring(0,idStr.length-1)
												}
												com.previousSibling().setValue(idStr)
												com.setValue(str);
											};
											selectDictionary('area',selectBankLinkMan);
									}
				
													 
									}]
								}
								 
								 
								 /*,{  
									  columnWidth : 1,
									   labelWidth : 73,
								     layout : 'form',
									 items :[{
										 
								    fieldLabel : '支行id',
								     id:'bankId',
									// name : 'banksubID',
									name : 'enterpriseBank.bankid',
									   value : bankData.bankid,
									 xtype:'hidden'
									
										 
									 }]
								 }
								
								 ,{  
									  columnWidth : 1,
									  labelWidth : 73,
								     layout : 'form',
									 items :[{
									id:'bankName',	
									name : 'enterpriseBank.bankname',
								    hiddenName : 'enterpriseBank.bankname',
								       value : bankData.bankname,
									fieldLabel : '银行名称',	
									//value : '中国银行',
						//			submitValue:false,
			                      xtype:'trigger',
								triggerClass :'x-form-search-trigger',
								emptyText : '点击选择银行----------------',
								   readOnly  : true,
								anchor : '98%'

									 }]
								 }*/,{
				            	columnWidth:.5,
				                layout: 'form',
				                defaults : {anchor:anchor},
				                items :[{
				                	xtype: 'radiogroup',
					                fieldLabel: '银行开户类别',
					                disabled:true,
					                items: [
					                    {boxLabel: '本币开户', name: 'enterpriseBank.openCurrency', inputValue: "0",checked:bankData.openCurrency=="0"?true:false},
					                    {boxLabel: '外币开户', name: 'enterpriseBank.openCurrency', inputValue: "1",checked:bankData.openCurrency=="1"?true:false}
					                ]
				                }]
				            },{
				            	columnWidth:.5,
				                layout: 'form',
				                defaults : {anchor:anchor},
				                items :[{
				                	xtype: 'radiogroup',
					                fieldLabel: '是否是放款账户',
					                 disabled:true,
					                items: [
					                    {boxLabel: '是', name: 'enterpriseBank.iscredit',inputValue: "0",checked:bankData.iscredit=="0"?true:false},
					                    {boxLabel: '否', name: 'enterpriseBank.iscredit', inputValue: "1",checked:bankData.iscredit=="1"?true:false}
					                ]
				                }]
				            }
								 ,{
								 columnWidth : .5,
								  labelWidth : 73,
								 layout : 'form',
								 items :[{
									 
									fieldLabel : '开户名称',	
	                              name : 'enterpriseBank.name',
	                                value : bankData.name,
									xtype:'textfield',
									   readOnly  : true,
									anchor : '96%'
								 }]} ,{
									 columnWidth : .5,
									  labelWidth : 98,
								     layout : 'form',
								      items :[{
									fieldLabel : '贷款卡卡号',	
								 	name : 'enterpriseBank.accountnum',
								 	  value : bankData.accountnum,
								  maxLength: 100,
								  xtype:'textfield',
								  anchor : '96%',
								     readOnly  : true
							}]},{
				              	columnWidth : 1,
								layout : 'form',
								  labelWidth : 73,
								items :[{
									xtype : 'textarea',
									 anchor : '98%',
									fieldLabel : '备注',
									   readOnly  : true,
									height : 80,
									name : 'enterpriseBank.remarks',
									  value : bankData.remarks
								}]
				              }]
					        } ]
						});
						var window_see = new Ext.Window({
							id : 'win_seeBankInfo',
							title: '查看<font color=red><'+obj.data.shortname+'>开户银行信息',
							layout : 'fit',
							//width : 500,
							width :(screen.width-180)*0.55,
							height : 290,
							closable : true,
							resizable : true,
							constrainHeader : true ,
							collapsible : true, 
							plain : true,
							border : false,
							modal : true,
							bodyStyle : 'overflowX:hidden',
							items :[panel_see]
						});
						window_see.show();	
					},
					failure : function(response) {					
							Ext.ux.Toast.msg('状态','操作失败，请重试');		
					},
					params: { id: id }
				});	
			}
			jStore_bankInfo.load({
				params : {
					start : 0,
					limit : pageSize
				}
			});
			var win_listEnterpriseBankInfo = new CS.Window({
				title : '<font color=red><'+obj.data.shortname+'></font>银行开户信息',
				//((screen.width-180)*0.5;  550
				width :(screen.width-180)*0.55 + 30,
				height : 400,
				buttonAlign : 'center',
				layout : 'fit',
				border : true,
				modal : true,
				maximizable : true,
				autoScroll : true ,
				constrainHeader : true ,
				collapsible : true, 
				items :[gPanel_enterpriseBank]
			}).show();
    	},   
    	failure: function(response, option) {   
        	return true;   
        	Ext.ux.Toast.msg('友情提示',"异步通讯失败，请于管理员联系！");   
    	}   
	}); 
}