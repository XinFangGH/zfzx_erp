var start = 0 ;
var pageSize = 15 ;
var root = 'result' ;
var totalProperty = 'totalCounts' ;
var businessType='Guarantee';
var projid=1;
jStore_cautionMoneyProj = new Ext.data.JsonStore( {
		url : __ctxPath + '/creditFlow/guarantee/EnterpriseBusiness/listGlBankGuaranteemoney.do',
		totalProperty : totalProperty,
		root : root ,
		id : 'cautionMoneyProjprojectId',
		fields : [ {
			name : 'bankGuaranteeId'
		}, {
			name : 'projId'
		}, {
			name : 'accountId'
		}, {
			name : 'glAccountBankId'
		}, {
			name : 'projectName'
		}, {
			name : 'projectMoney'
		}, {
			name:  'freezeMoney'
		}, {
			name : 'freezeDate'
		}, {
			name : 'businessTypeName'
		}, {
			name : 'unfreezeMoney'
		}, {
			name : 'releaseDate'
		}]
	});
	jStore_cautionMoneyProj.load({
		params : {
			start : start,
			limit : pageSize
		}
	});
	
	var cModel_cautionMoneyProj = new Ext.grid.ColumnModel(
			[
			new Ext.grid.RowNumberer( {
				header : '序号',
				width : 35
			}),
			{
				header : "项目名称",
				width : 220,
				sortable : true,
				align : 'text-align:right',
				dataIndex : 'projectName'
			}, {
				header : "借款金额",
				width : 105,
				sortable : true,
				align : 'right',
				dataIndex : 'projectMoney',
				renderer :function(v){
					if(v !=null){
						return v+'万元';
					}else{
					return "";
					
					}
			}
			}, {
				header : "实际冻结金额",
				width : 105,
				sortable : true,
				align : 'right',
				dataIndex : 'freezeMoney',
				renderer :function(v){
					if(v !=null){
						return v+'万元';
					}else{
					return "";
					
					}
				}
			}, {
				header : "冻结时间",
				width : 120,
				sortable : true,
				align : 'text-align:right',
				dataIndex : 'freezeDate'
			}, {
				header : "解冻金额",
				align : 'right',
				width : 100,
				sortable : true,
				align : 'right',
				dataIndex : 'unfreezeMoney',
				renderer :function(v){
					if( v!=null){
						return v+'万元';
					}else{
					return "";
					
					}
				}
			}, {
				header : "解冻时间",
				sortable : true,
				dataIndex : 'releaseDate'
			},/*{
				header : "企业简称",
				width : 110,
				sortable : true,
				dataIndex : 'shortname'
			}, */{
				header : "业务品种",
				width : 120,
				sortable : true,
				dataIndex : 'businessTypeName'
			} ]);

	var pagingBar = new Ext.PagingToolbar( {
		pageSize : pageSize,
		store : jStore_cautionMoneyProj,
		autoWidth : true,
		hideBorders : true,
		displayInfo : true,
		displayMsg : '当前第{0} - {1}条，共 {2} 条记录',
		emptyMsg : "没有符合条件的记录······"
	});
var button_addFrozen = new Ext.Button({
		text : '添加项目相关记录',
		tooltip : '添加项目相关记录',
		iconCls : 'addIcon',
		scope : this,
		handler : function() {
			var addFrozenWin = new Ext.Window({
				id : 'addFrozenWin',
				layout : 'fit',
				title : '添加项目相关记录',
				//width : 500,
				width : (screen.width-180)*0.55,
				height : 370,
				iconCls : 'newIcon',
				minimizable : true,
				modal : true,
				items : [new Ext.form.FormPanel({
					border : false,
					id : 'addFrozenForm',
					labelAlign : 'right',
					buttonAlign : 'center',
				//	url : 'addFrozenForProject.action',
					bodyStyle : 'padding:10px 25px 25px',
				    layout : 'column',
					frame : true,
					waitMsgTarget : true,
					monitorValid : true,
					autoWidth : true,
					autoHight : true ,
					items :[{
						columnWidth : 1,
						layout : 'form',
						labelWidth : 115,
						defaults : {anchor : '97%'},
						items :[{
							id : 'serviceTypeAdd',
							xtype : 'combo',
							fieldLabel : '业务种类',
							emptyText : '<----请选择---->',
							hiddenName : 'glBankGuaranteemoney.businessType',
							name :'glBankGuaranteemoney.businessType',
							editable : false,
							allowBlank : false,
							width : 75,
							mode : 'local',
						    store : new Ext.data.SimpleStore({
												autoLoad : true,
												url : __ctxPath+ '/system/getTypeJsonByNodeKeyGlobalType.do?nodeKey=Guarantee',
												fields : ['value', 'text']
											}),
						    displayField : 'text',
							valueField : 'value',
							triggerAction : 'all',
							listeners : {
								'select' : function(combo,record, index){
								}
							}
						},{
							id : 'enterpriseProjectName_Add',
							xtype : 'trigger',
							triggerClass :'x-form-search-trigger',
		            		fieldLabel : '<font color=red>*</font>项目名称',
		            		onTriggerClick : function(){
								selectProjectList(getEnterpriseProjectName);
							}
						},{
							id : 'cautionAccountId_Choose',
		            		xtype : 'trigger',
		            		triggerClass :'x-form-search-trigger',
		            		allowBlank : false,
		            		fieldLabel : '保证金账户',
		            		onTriggerClick : function(){
								selectAccountBankName(getCautionAccountName);
							}
						},{
							id : 'surplusMoney_Add',
							xtype : 'textfield',
							readOnly : true,
		            		fieldLabel : '账户可用金额.万元'
						}]
						},{
						columnWidth : .7,
						layout : 'form',
						labelWidth : 115,
						defaults : {anchor : '97%'},
						items :[{
							id : 'cautionCode_Add',
							xtype : 'textfield',
							//allowBlank : false,
		            		fieldLabel : '保证金凭证号码',
		            		name : 'glBankGuaranteemoney.certificateNum'
						}]},
						{
						columnWidth : .3,
						layout : 'form',
						defaults : {anchor : '97%'},
						items :[{
							iconCls : 'slupIcon',
							text : '上传保证金凭证',
							width : '100',
							xtype : 'button',
							scope : this,
							handler : function() {
								var p=Ext.get('cautionAccountId_Add').dom.value;
								if(p==""){
									Ext.Msg.alert('状态','请填写项目名称!');
								}else{
									var	projectId =projid;
									var setname ='银行冻结保证金凭证';
									var titleName ='银行冻结保证金凭证';
									var tableName ='gl_Bank_guaranteemoney';
									var typeisfile ='typeisglbankguaranteemoney1';
									//var businessType=this.businessType;
									var mark=tableName+"."+projectId
									uploadReportJS('上传/下载'+titleName+'文件',typeisfile,mark,1,null,null,null,projectId,businessType,setname);
									}
									
								}
						}]},{
						columnWidth : 1,
						layout : 'form',
						labelWidth : 115,
						defaults : {anchor : '97%'},
						items :[{
							id : 'bankMarginMoney_Add',
							xtype : 'numberfield',
		            		fieldLabel : '冻结金额.万元',
		            		allowBlank : false,
		            		name : 'glBankGuaranteemoney.freezeMoney'
						},{
							id : 'bailPayoutDate_Add',
							xtype : 'datefield',
							fieldLabel : '冻结日期',
							allowBlank : false,
							format : 'Y-m-d',
							name : 'glBankGuaranteemoney.freezeDate'
						},{
							id : 'cautionmoneyPayRemarks_Add',
							xtype : 'textarea',
							fieldLabel : '备&nbsp;&nbsp;注 ',
							name : 'glBankGuaranteemoney.remark',
							maxLength : 255
						},{
							id : 'cautionAccountId_Add',
			            	name : 'glBankGuaranteemoney.accountId',
			            	xtype : 'hidden'
						},{
							id : 'projectId_Add',
			            	name : 'glBankGuaranteemoney.projId',
			            	xtype : 'hidden'
						}]
					}],
					tbar : [{
						text : '保存',
						formBind : true,
						iconCls : 'saveIcon',
						handler : function() {
							var cautionAccountId_Add =Ext.get('cautionAccountId_Add').dom.value;
							//var serviceTypeAdd =Ext.get('serviceTypeAdd').dom.value;
							
							var enterpriseProjectName_Add =Ext.get('enterpriseProjectName_Add').dom.value;
							var cautionAccountId_Choose =Ext.get('cautionAccountId_Choose').dom.value;
							//var cautionCode_Add =Ext.get('cautionCode_Add').dom.value;
							var bankMarginMoney_Add =Ext.get('bankMarginMoney_Add').dom.value;//----
							var bailPayoutDate_Add =Ext.get('bailPayoutDate_Add').dom.value;
							
							var cautionmoneyPayRemarks_Add =Ext.get('cautionmoneyPayRemarks_Add').dom.value;
							var projectId_Add =Ext.get('projectId_Add').dom.value;
							var surplusMoney_Add =Ext.get('surplusMoney_Add').dom.value;
							//alert("可用金额==="+surplusMoney_Add+typeof(surplusMoney_Add)+"      冻结金额==="+bankMarginMoney_Add+typeof(bankMarginMoney_Add));
							
							//alert("业务种类id=="+serviceTypeValue+"    账户id="+cautionAccountId_Add+"  企业贷项目名称="+enterpriseProjectName_Add+"  车贷项目名称="+carProjectName_Add+"  账户名称="+cautionAccountId_Choose+"  凭证号码="+cautionCode_Add+"  冻结金额="+bankMarginMoney_Add+"   冻结日期="+bailPayoutDate_Add+"   项目id="+projectId_Add);
							if(cautionAccountId_Add == "" || bankMarginMoney_Add == "" || bailPayoutDate_Add == "" || enterpriseProjectName_Add ==""){
								Ext.Msg.alert('状态','请填写数据完整后再提交!');
							}if(parseFloat(bankMarginMoney_Add)>parseFloat(surplusMoney_Add)){
								Ext.Msg.alert('状态','冻结金额不能大于该账户可用金额!');
							}else{
								addFrozenWin.findById('addFrozenForm').getForm().submit({
									method : 'POST',
									waitTitle : '连接',
									waitMsg : '消息发送中...',
										url : __ctxPath + '/creditFlow/guarantee/EnterpriseBusiness/saveGlBankGuaranteemoney.do',
									success : function(form ,action) {
										objSubmitFrozen = Ext.util.JSON.decode(action.response.responseText);
										if(objSubmitFrozen.exsit == false){
											Ext.Msg.alert('状态' ,objSubmitFrozen.msg);
										}else{
											Ext.Msg.alert('状态',"添加成功",
											function(btn, text) {
												jStore_cautionMoneyProj.reload();
												accountBankTreePanel.getRootNode().reload();
												accountBankTreePanel.expandAll() ;
												addFrozenWin.destroy();
											});
										}
									},
									failure : function(form ,action) {
										objSubmitFrozen = Ext.util.JSON.decode(action.response.responseText);
										Ext.Msg.alert('状态', objSubmitFrozen.msg);
									}
								})
							}
						}
					}]
				})],
				listeners : {
//					'beforeclose' : function(){
//						if(addFrozenWin.findById('addFrozenForm').getForm().isDirty()){
//							Ext.Msg.confirm('操作提示','是否保存当前新添加的数据?',function(btn){
//								if(btn=='yes'){
//									addFrozenWin.findById('addFrozenForm').getTopToolbar().items.itemAt(0).handler.call() ;
//								}else{
//									addFrozenWin.findById('addFrozenForm').getForm().reset() ;
//									addFrozenWin.destroy() ;
//								}
//							}) ;
//							return false ;
//						}
//					}
				}
			}).show();
			hideField(Ext.getCmp('carProjectName_Add')) ;
			showField(Ext.getCmp('enterpriseProjectName_Add')) ;
		}
	});
	
	var button_updateFrozen = new Ext.Button({
		text : '编辑项目相关记录',
		tooltip : '编辑项目相关记录',
		iconCls : 'updateIcon',
		scope : this,
		handler : function() {
			var selected = gPanel_cautionMoneyProj.getSelectionModel().getSelected();
			if (null == selected) {
				Ext.MessageBox.alert('状态', '请选择一条记录!');
			}else{
				var projectId = selected.get('projId');
				var bankGuaranteeId=selected.get('bankGuaranteeId');
					Ext.Ajax.request({
						url : __ctxPath + '/creditFlow/guarantee/EnterpriseBusiness/getGlBankGuaranteemoney.do',
						method : 'POST',
						success : function(response, request){
							objFrozen = Ext.util.JSON.decode(response.responseText);
							projid =objFrozen.data.projId;
							var accountname="";
							if(objFrozen.data.guaranteebankName !=null){
							  accountname=objFrozen.data.guaranteebankName+"-"+objFrozen.data.guaranteeaccount
							}
							var surplusMoney="";
							if(objFrozen.data.maxfreezeMoney !=null){
							surplusMoney=objFrozen.data.maxfreezeMoney+objFrozen.data.freezeMoney
							}
							var updateFrozenForProjectWin = new Ext.Window({
								id : 'updateFrozenForProjectWin',
								layout : 'fit',
								title : '编辑项目冻结金额',
								iconCls : 'editorIcon',
								width : (screen.width-180)*0.55,
								height : 360,
								minimizable : true,
								modal : true,
								items :[
									new Ext.form.FormPanel({
										id : 'updateFrozenForProjectForm',
										labelAlign : 'right',
										buttonAlign : 'center',
								//		url : 'updateFrozenForProject.action',
										bodyStyle : 'padding:10px 25px 25px',
									    layout : 'column',
										frame : true,
										waitMsgTarget : true,
										monitorValid : true,
										autoWidth : true,
										autoHight : true ,
										items :[{
											columnWidth : 1,
											layout : 'form',
											id : 'updateFrozenForProjectColumn',
											labelWidth : 115,
										   defaults : {anchor : '97%'},
											items :[{
												xtype : 'textfield',
												fieldLabel : '业务种类',
												editable : false,
												allowBlank : false,
												readOnly : true,
												cls : 'readOnlyClass',
												value : objFrozen.data.businessTypeName
											},{
												xtype : 'textfield',
												fieldLabel : '项目名称',
												editable : false,
												allowBlank : false,
												readOnly : true,
												cls : 'readOnlyClass',
												value : objFrozen.data.projectName
											},{
												id : 'cautionAccountId_update',
							            		xtype : 'trigger',
							            		triggerClass :'x-form-search-trigger',
							            		allowBlank : false,
							            		fieldLabel : '保证金账户',
							            		value : accountname,
							            		onTriggerClick : function(){
													selectAccountBankName(getCautionAccountNameUpdate);
												}
											},{
												id : 'surplusMoney_update',
												xtype : 'textfield',
												readOnly : true,
							            		fieldLabel : '账户可用金额.万元',
							            		value :surplusMoney
											},{
												id : 'surplusMoney_update1',
												xtype : 'hidden',
												readOnly : true,
							            		fieldLabel : '账户可用金额.万元',
							            		value :surplusMoney
											}]},{
											
											columnWidth : .7,
											layout : 'form',
											labelWidth : 115,
											defaults : {anchor : '97%'},
											items :[{
						            	 		id : 'cautionCode_update',
												xtype : 'textfield',
												//allowBlank : false,
							            		fieldLabel : '保证金凭证号码',
							            		name : 'glBankGuaranteemoney.certificateNum',
												value : objFrozen.data.certificateNum
											}]},{
											
											columnWidth : .3,
											layout : 'form',
											labelWidth : 100,
											defaults : {anchor : '97%'},
											items :[{
											iconCls : 'slupIcon',
											text : '上传保证金凭证',
											xtype : 'button',
											scope : this,
											handler : function() {
												var	projectId =projid;
												var setname ='银行冻结保证金凭证';
												var titleName ='银行冻结保证金凭证';
												var tableName ='gl_Bank_guaranteemoney';
												var typeisfile ='typeisglbankguaranteemoney1';
												//var businessType=this.businessType;
												var mark=tableName+"."+projectId
												uploadReportJS('上传/下载'+titleName+'文件',typeisfile,mark,1,null,null,null,projectId,businessType,setname);
											}
										}]},
										{columnWidth : 1,
										layout : 'form',
										labelWidth : 115,
										defaults : {anchor : '97%'},
										items :[{
												id : 'bankMarginMoney_update',
												xtype : 'textfield',
							            		fieldLabel : '冻结金额.万元',
							            		allowBlank : false,
							            		name : 'glBankGuaranteemoney.freezeMoney',
												value : objFrozen.data.freezeMoney
											},{
												id : 'bailPayoutDate_update',
												xtype : 'datefield',
												fieldLabel : '冻结日期',
												allowBlank : false,
												format : 'Y-m-d',
												name : 'glBankGuaranteemoney.freezeDate',
												value : objFrozen.data.freezeDate
											},{
												id : 'cautionmoneyPayRemarks_update',
												xtype : 'textarea',
												fieldLabel : '备&nbsp;&nbsp;注 ',
												name : 'glBankGuaranteemoney.remark',
												maxLength : 255,
												value : objFrozen.data.remark
											},{
												id : 'projId',
												name : 'glBankGuaranteemoney.projId',
								            	value : objFrozen.data.projId,
								            	xtype : 'hidden'
											},{
												id : 'cautionAccountId_update1',
								            	value : objFrozen.data.accountId,
								            	xtype : 'hidden'
											},{
												id : 'glBankGuaranteemoney.accountId',
								            	value : objFrozen.data.accountId,
								            	xtype : 'hidden'
											},{
												id : 'accountId_update',
												name : 'glBankGuaranteemoney.bankGuaranteeId',
								            	value : objFrozen.data.bankGuaranteeId,
								            	xtype : 'hidden'
											}]
										}],
										tbar : [{
											text : '保存',
											formBind : true,
											iconCls : 'saveIcon',
											handler : function() {
												
												var cautionCode_update =Ext.get('cautionCode_update').dom.value;
												var bankMarginMoney_update =Ext.get('bankMarginMoney_update').dom.value;
												var bailPayoutDate_update =Ext.get('bailPayoutDate_update').dom.value;
												var surplusMoney_update=Ext.get('surplusMoney_update').dom.value;
												var projId =Ext.get('projId').dom.value;
												
												if( bankMarginMoney_update == "" || bailPayoutDate_update == ""){
													Ext.Msg.alert('状态','请填写数据完整后再提交!');
												}if(parseFloat(bankMarginMoney_update)>parseFloat(surplusMoney_update)){
													Ext.Msg.alert('状态','冻结金额不能大于该账户可用金额!');
												}else{
																		updateFrozenForProjectWin.findById('updateFrozenForProjectForm').getForm().submit({
														method : 'POST',
														waitTitle : '连接',
														waitMsg : '消息发送中...',
														url : __ctxPath + '/creditFlow/guarantee/EnterpriseBusiness/saveGlBankGuaranteemoney.do',
														success : function(form ,action) {
															objtUpdate = Ext.util.JSON.decode(action.response.responseText);
															Ext.Msg.alert('状态', "编辑成功",
																	function(btn, text) {
																		jStore_cautionMoneyProj.reload();
																		accountBankTreePanel.getRootNode().reload();
																		accountBankTreePanel.expandAll() ;
																		updateFrozenForProjectWin.destroy();
																	});
														},
														failure : function(form ,action) {
															objtUpdate = Ext.util.JSON.decode(action.response.responseText);
															Ext.Msg.alert('状态', objtUpdate.msg);
														}
													});
												}
											}
										}]
									})
								],
								listeners : {
//									'beforeclose' : function(){
//										if(updateFrozenForProjectWin.findById('updateFrozenForProjectForm').getForm().isDirty()){
//											Ext.Msg.confirm('操作提示','数据被修改过,是否保存?',function(btn){
//												if(btn=='yes'){
//													updateFrozenForProjectWin.findById('updateFrozenForProjectForm').getTopToolbar().items.itemAt(0).handler.call() ;
//												}else{
//													updateFrozenForProjectWin.findById('updateFrozenForProjectForm').getForm().reset() ;
//													updateFrozenForProjectWin.destroy() ;
//												}
//											}) ;
//											return false ;
//										}
//									}
								}
							});
							updateFrozenForProjectWin.show();
						},
						failure : function(response) {
							Ext.Msg.alert('状态', '操作失败，请重试');
						},
						params : {
							projId : projectId
						}
					})
				}
			
		}
	});
	var button_deleteFrozen = new Ext.Button({
		text : '删除项目相关记录',
		tooltip : '删除选中的记录信息',
		//disabled : bool,
		iconCls : 'deleteIcon',
		scope : this,
		handler : function() {
			var selected = gPanel_cautionMoneyProj.getSelectionModel().getSelected();
			if (null == selected) {
				Ext.MessageBox.alert('状态', '请选择一条记录!');
			}else{
					var projectId = selected.get('projId');
				var bankGuaranteeId = selected.get('bankGuaranteeId');
				Ext.MessageBox.confirm('确认删除', '是否确认删除该条记录？ ', function(btn) {
					if (btn == 'yes') {
						Ext.Ajax.request({
								url : __ctxPath + '/creditFlow/guarantee/EnterpriseBusiness/deleteGlBankGuaranteemoney.do',
							method : 'POST',
							success : function(response, request) {
								objDeleteData = Ext.util.JSON.decode(response.responseText);
									Ext.Msg.alert('状态', "删除成功",
										function(btn, text) {
											accountBankTreePanel.getRootNode().reload();
											accountBankTreePanel.expandAll() ;
											jStore_cautionMoneyProj.reload();
										});
							},
							failure : function(result, action) {
								var msg = Ext.decode(action.response.responseText);
								Ext.Msg.alert('状态',msg);
							},
							params: { bankGuaranteeId: bankGuaranteeId }
							
						});
					}
				});
			}
		}
	});
		var button_seeFrozen = new Ext.Button({
		text : '查看项目相关记录',
		tooltip : '查看项目相关记录',
		iconCls : 'lookIcon',
		scope : this,
		handler : function() {
			var selected = gPanel_cautionMoneyProj.getSelectionModel().getSelected();
			if (null == selected) {
				Ext.MessageBox.alert('状态', '请选择一条记录!');
			}else{
				var projectId = selected.get('projId');
				var bankGuaranteeId=selected.get('bankGuaranteeId');
					Ext.Ajax.request({
						url : __ctxPath + '/creditFlow/guarantee/EnterpriseBusiness/getGlBankGuaranteemoney.do',
						method : 'POST',
						success : function(response, request){
							objFrozen = Ext.util.JSON.decode(response.responseText);
							var updateFrozenForProjectWin = new Ext.Window({
								id : 'seeFrozenForProjectWin',
								layout : 'fit',
								title : '查看项目相关记录',
								iconCls : 'editorIcon',
								width : (screen.width-180)*0.55,
								height : 330,
								minimizable : true,
								modal : true,
								items :[
									new Ext.form.FormPanel({
										id : 'FrozenForProjectForm',
										labelAlign : 'right',
										buttonAlign : 'center',
									//	url : 'updateFrozenForProject.action',
										bodyStyle : 'padding:10px 25px 25px',
									    layout : 'column',
										frame : true,
										waitMsgTarget : true,
										monitorValid : true,
										autoWidth : true,
										autoHight : true ,
										items :[{
											columnWidth : 1,
											layout : 'form',
											id : 'seeFrozenForProjectColumn',
											labelWidth : 100,
											defaults : {anchor : '100%'},
											items :[{
												xtype : 'textfield',
												fieldLabel : '业务种类',
												editable : false,
												allowBlank : false,
												readOnly : true,
												cls : 'readOnlyClass',
												value : objFrozen.data.businessTypeName
											},{
												xtype : 'textfield',
												fieldLabel : '项目名称',
												readOnly : true,
												cls : 'readOnlyClass',
												value : objFrozen.data.projectName
											},{
							            		xtype : 'trigger',
							            		triggerClass :'x-form-search-trigger',
							            		readOnly : true,
							            		fieldLabel : '保证金账户',
							            		value : objFrozen.data.guaranteebankName+"-"+objFrozen.data.guaranteeaccount
											},{
												xtype : 'textfield',
							            		fieldLabel : '保证金凭证号码',
							            		readOnly : true,
												value : objFrozen.data.certificateNum
											},{
												xtype : 'textfield',
							            		fieldLabel : '冻结金额.万元',
							            		readOnly : true,
												value : objFrozen.data.freezeMoney
											},{
												xtype : 'datefield',
												fieldLabel : '冻结日期',
												readOnly : true,
												format : 'Y-m-d',
												value : objFrozen.data.freezeDate
											},{
												xtype : 'textarea',
												fieldLabel : '备&nbsp;&nbsp;注 ',
												readOnly : true,
												maxLength : 255,
												value : objFrozen.data.remark
											}]
										}]
									
									})
								]
							});
							updateFrozenForProjectWin.show();
						},
						failure : function(response) {
							Ext.Msg.alert('状态', '操作失败，请重试');
						},
						params : {
							projId : projectId
						}
					})
				}
			
		}
	});
	var button_unfreeze = new Ext.Button({
		text : '解冻授信额度',
		tooltip : '解冻选中的项目的金额',
		iconCls : 'updateIcon',
		scope : this,
		handler : function() {
			var selected = gPanel_cautionMoneyProj.getSelectionModel().getSelected();
			if (null == selected) {
				Ext.MessageBox.alert('状态', '请选择一条记录!');
			}else{
				if(selected.data.freezeMoney !=null){
				var projectId = selected.get('projId');
				var bankGuaranteeId = selected.get('bankGuaranteeId');
			
					Ext.Ajax.request({
						url : __ctxPath + '/creditFlow/guarantee/EnterpriseBusiness/getGlBankGuaranteemoney.do',
						method : 'POST',
						success : function(response, request){
							objUnfreeze = Ext.util.JSON.decode(response.responseText);
							projid=objUnfreeze.data.projId;
							var unfreezeCautionAccountWin = new Ext.Window({
								id : 'unfreezeCautionAccountWin',
								layout : 'fit',
								title : '解冻项目金额',
								iconCls : 'editorIcon',
								width : (screen.width-180)*0.55,
								height : 400,
								minimizable : true,
								modal : true,
								items :[
									new Ext.form.FormPanel({
										id : 'unfreezeCautionAccountForm',
										labelAlign : 'right',
										buttonAlign : 'center',
								//		url : 'updateUnfreezeCautionAccount.action',
										bodyStyle : 'padding:10px 25px 25px',
									    layout : 'column',
										frame : true,
										waitMsgTarget : true,
										monitorValid : true,
										autoWidth : true,
										autoHight : true ,
										items :[{
											columnWidth : 1,
											layout : 'form',
											labelWidth : 100,
											defaults : {xtype : 'textfield',anchor : '100%'},
											items :[{
												fieldLabel : '业务种类',
												editable : false,
												allowBlank : false,
												readOnly : true,
												cls : 'readOnlyClass',
												value : objUnfreeze.data.businessTypeName
											},{
												fieldLabel : '项目名称',
												editable : false,
												allowBlank : false,
												readOnly : true,
												cls : 'readOnlyClass',
												value : objUnfreeze.data.projectName
											},{
												fieldLabel : '保证金账户',
												allowBlank : false,
												blankText : '必填信息',
												readOnly : true,
												cls : 'readOnlyClass',
												value : objUnfreeze.data.guaranteebankName+'-'+objUnfreeze.data.guaranteeaccount
											}]},{
											
											columnWidth : .7,
											layout : 'form',
											labelWidth : 100,
											defaults : {xtype : 'textfield',anchor : '100%'},
											items :[{
												xtype : 'textfield',
												readOnly : true,
												cls : 'readOnlyClass',
							            		fieldLabel : '保证金凭证号码',
							            		value : objUnfreeze.data.certificateNum
											}]},{
											
											columnWidth : .3,
											layout : 'form',
											labelWidth : 100,
											defaults : {xtype : 'textfield',anchor : '100%'},
											items :[{
												iconCls : 'sldownIcon',
												text : '下载保证金凭证',
												xtype : 'button',
												scope : this,
												handler : function() {
													var mark1="gl_Bank_guaranteemoney."+projid;
													var businessType1=businessType;
//													alert(mark1);
//													alert(businessType1);
													window.open(__ctxPath+'/contract/ajaxGetReportTemplateDocumentTemplet.do?mark='+mark1+'&businessType='+businessType1,'_blank');
																	
											}
										}]},{
										   columnWidth : 1,
											layout : 'form',
											labelWidth : 100,
											defaults : {xtype : 'textfield',anchor : '100%'},
											items :[{
												id : 'bankMarginMoney_unfreeze',
							            		fieldLabel : '冻结金额.万元',
							            		allowBlank : false,
							            		readOnly : true,
												cls : 'readOnlyClass',
												value : objUnfreeze.data.freezeMoney
											},{
												id : 'freezeDate_unfreeze',
												xtype : 'datefield',
												fieldLabel : '冻结日期',
												readOnly : true,
												format : 'Y-m-d',
												value : objUnfreeze.data.freezeDate
											},{
												id : 'unfreezeMoney_unfreeze',
												xtype : 'numberfield',
							            		fieldLabel : '解冻金额.万元',
							            		allowBlank : false,
							            		name : 'glBankGuaranteemoney.unfreezeMoney',
							            		value : objUnfreeze.data.unfreezeMoney==0?'':objUnfreeze.data.unfreezeMoney
											},{
												id : 'unfreezeDate_unfreeze',
												xtype : 'datefield',
												fieldLabel : '解冻日期',
												allowBlank : false,
												format : 'Y-m-d',
												name : 'glBankGuaranteemoney.releaseDate',
												value : objUnfreeze.data.releaseDate
											},{
												xtype : 'textarea',
												fieldLabel : '备&nbsp;&nbsp;注 ',
												name : 'glBankGuaranteemoney.bankYhbzjsm',
												maxLength : 255,
												value : objUnfreeze.data.unfreezeremark
											},{
												id : 'projId_unfree',
												name : 'glBankGuaranteemoney.projectId',
								            	value : objUnfreeze.data.projId,
								            	xtype : 'hidden'
											},{
												id : 'accountId_unfree',
												name : 'glBankGuaranteemoney.bankGuaranteeId',
								            	value : objUnfreeze.data.bankGuaranteeId,
								            	xtype : 'hidden'
											}]
										}],
										tbar : [{
											text : '保存',
											formBind : true,
											iconCls : 'saveIcon',
											handler : function() {
												var bankMarginMoney_unfreeze = Ext.getCmp('bankMarginMoney_unfreeze').getValue();
												var unfreezeMoney_unfreeze = Ext.getCmp('unfreezeMoney_unfreeze').getValue();
												var unfreezeDate_unfreeze = Ext.getCmp('unfreezeDate_unfreeze').getValue();
												//alert("解冻金额："+unfreezeMoney_unfreeze+typeof(unfreezeMoney_unfreeze)+"   冻结金额="+bankMarginMoney_unfreeze);
												if(unfreezeMoney_unfreeze == "" || unfreezeDate_unfreeze == ""){
													Ext.Msg.alert('状态','请填写数据完整后再提交!');
													return;
												}if(unfreezeMoney_unfreeze>bankMarginMoney_unfreeze){
													Ext.Msg.alert('状态','解冻金额不能大于冻结金额，请重新输入!');
													return;
												}else{
													unfreezeCautionAccountWin.findById('unfreezeCautionAccountForm').getForm().submit({
														method : 'POST',
														waitTitle : '连接',
														waitMsg : '消息发送中...',
														url : __ctxPath + '/creditFlow/guarantee/EnterpriseBusiness/saveGlBankGuaranteemoney.do',
														success : function(form ,action) {
															objt = Ext.util.JSON.decode(action.response.responseText);
															Ext.Msg.alert('状态', "解冻成功",
																	function(btn, text) {
																		jStore_cautionMoneyProj.reload();
																		accountBankTreePanel.getRootNode().reload();
																		accountBankTreePanel.expandAll() ;
																		unfreezeCautionAccountWin.destroy();
																	});
														},
														failure : function(form ,action) {
															objt = Ext.util.JSON.decode(action.response.responseText);
															Ext.Msg.alert('状态', objt.msg);
														}
													});
												}
											}
				
										}]
									})
								],
								listeners : {
//									'beforeclose' : function(){
//										if(unfreezeCautionAccountWin.findById('unfreezeCautionAccountForm').getForm().isDirty()){
//											Ext.Msg.confirm('操作提示','数据被修改过,是否保存?',function(btn){
//												if(btn=='yes'){
//													unfreezeCautionAccountWin.findById('unfreezeCautionAccountForm').getTopToolbar().items.itemAt(0).handler.call() ;
//												}else{
//													unfreezeCautionAccountWin.findById('unfreezeCautionAccountForm').getForm().reset() ;
//													unfreezeCautionAccountWin.destroy() ;
//												}
//											}) ;
//											return false ;
//										}
//									}
								}
							});
							unfreezeCautionAccountWin.show();
						},
						failure : function(response) {
							Ext.Msg.alert('状态', '操作失败，请重试');
						},
						params : {
							projId : projectId
						}
				})
				}else{
				
				Ext.Msg.alert('状态', '此记录还没冻结');
					
					
				}
				}
			
		}
	});
	var setValueToNullWhenZero = function(v){
		if(v == 0){
			return '';
		}else{
			return v;
		}
	}
	
	gPanel_cautionMoneyProj = new Ext.grid.GridPanel( {
		title : '保证金账户记录',
		id : 'gPanel_cautionMoneyProj',
		store : jStore_cautionMoneyProj,
		height :500,
		colModel : cModel_cautionMoneyProj,
		//autoExpandColumn : 5,
		selModel : new Ext.grid.RowSelectionModel(),
		stripeRows : true,
	//	loadMask : myMask,
		bbar : pagingBar,
		tbar : [button_addFrozen,button_updateFrozen,button_deleteFrozen,button_seeFrozen,button_unfreeze],
		listeners : {
			'rowdblclick' : function(grid,index,e){}
		}
	});
var getEnterpriseProjectName = function(a,b,c){
	Ext.getCmp('projectId_Add').setValue(a);
	Ext.getCmp('enterpriseProjectName_Add').setValue(b);
	projid=a;
}
var getCautionAccountName = function(obj){//保证金账户
	Ext.getCmp('cautionAccountId_Add').setValue(obj.id);
	Ext.getCmp('cautionAccountId_Choose').setValue(obj.bankName+"-"+obj.accountname);
	Ext.getCmp('surplusMoney_Add').setValue(obj.surplusMoney);
}

var getCautionAccountNameUpdate = function(obj){//保证金账户--修改用
	if(Ext.getCmp('cautionAccountId_update1').getValue()==obj.id){
	Ext.getCmp('surplusMoney_update').setValue(Ext.getCmp('surplusMoney_update1').getValue());
	}else{
		Ext.getCmp('surplusMoney_update').setValue(obj.surplusMoney);
	}
	Ext.getCmp('accountId_update').setValue(obj.id);
	Ext.getCmp('cautionAccountId_update').setValue(obj.bankName+"-"+obj.accountname);
	
}
