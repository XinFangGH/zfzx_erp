/**
 * 词条管理
 * @class SystemWordView
 * @extends Ext.Panel
 */
var projid;
AccountBankManageProj=Ext.extend(Ext.Panel,{
	constructor:function(config){
		Ext.applyIf(this,config);
		this.initUIComponents();
		AccountBankManageProj.superclass.constructor.call(this,{
			id:'AccountBankManageProj',
			height:450,
			autoScroll:true,
			layout:'border',
			title:'保证金账户项目记录管理',
			iconCls:'menu-accountbankmanageproj',
			items:[
				this.accountBankPanel,
				this.gPanel_cautionMoneyProj
			]
		});
	},
	initUIComponents:function(){
	var myMask = new Ext.LoadMask(Ext.getBody(), {
		msg : "加载数据中······,请稍后······"
	});
	var autoHight = Ext.getBody().getHeight();
	var autoWidth = Ext.getBody().getWidth();
	var start = 0 ;
	var pageSize = 15 ;
	var root = 'result' ;
	var totalProperty = 'totalCounts' ;
	
	
		var jStore_cautionMoneyProj = new Ext.data.JsonStore( {
		url : __ctxPath + '/creditFlow/guarantee/EnterpriseBusiness/listGlBankGuaranteemoney.do',
		totalProperty : totalProperty,
		root : root ,
		id : 'cautionMoneyProjprojectId',
		fields : [ {
			name : 'bankGuaranteeId'
		}, {
			name : 'projectId'
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
			name : 'operationTypeName'
		}, {
			name : 'unfreezeMoney'
		}, {
			name : 'releaseDate'
		},{
		    name : 'operationType'
		},{
		   name : 'projectStatus'
		},{
		   name : 'businessType'
		},{
		   name : 'bmStatus'
		},{
		   name : 'taskId'
		},{
		   name : 'oppositeType'
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
				dataIndex : 'projectName',
				renderer : function(v, metadata, record,
				                    rowIndex, columnIndex, store) {
				                    	 metadata.attr = ' ext:qtip="' + v + '"';
                                return v;				                    	 
				      }
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
				dataIndex : 'operationTypeName'
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
	var tbar1=new Ext.Toolbar({
			items:[{
			text : '添加冻结项目记录',
		tooltip : '添加冻结项目记录',
		iconCls : 'btn-add',
		scope : this,
		hidden : isGranted('_adddjxmjl_q')?false:true,
		handler : function() {
				var accountBankTreePanel1=this.accountBankTreePanel;
			var addFrozenWin = new Ext.Window({
				id : 'addFrozenWin',
				layout : 'fit',
				title : '添加冻结项目记录',
				//width : 500,
				width : (screen.width-180)*0.55,
				height : 315,
				border : false,
				modal : true,
				buttonAlign : 'center',
				buttons : [
							{
								text : '保存',
								iconCls : 'btn-save',
								scope : this,
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
											Ext.ux.Toast.msg('状态','添加成功!');
												jStore_cautionMoneyProj.reload();
												accountBankTreePanel1.getRootNode().reload();
												accountBankTreePanel1.expandAll() ;
												addFrozenWin.destroy();
										}
									},
									failure : function(form ,action) {
										objSubmitFrozen = Ext.util.JSON.decode(action.response.responseText);
										Ext.Msg.alert('状态', objSubmitFrozen.msg);
									}
								})
							}}
							}, {
								text : '重置',
								iconCls : 'btn-reset',
								scope : this,
								handler : function(){
									
									addFrozenWin.findById('addFrozenForm').getForm().reset();
									
									}
							}, {
								text : '取消',
								iconCls : 'btn-cancel',
								scope : this,
								handler : function(){
									
									addFrozenWin.destroy();
									
									}
							}
				         ],
				items : [new Ext.form.FormPanel({
					border : false,
					id : 'addFrozenForm',
					labelAlign : 'right',
					buttonAlign : 'center',
				//	url : 'addFrozenForProject.action',
					bodyStyle : 'padding:10px 25px 25px 17px',
				    layout : 'column',
					frame : true,
					waitMsgTarget : true,
					monitorValid : true,
					autoWidth : true,
					autoHight : true ,
					items :[{
						columnWidth : 1,
						layout : 'form',
						labelWidth : 95,
						defaults : {anchor : '100%'},
						items :[{
							id : 'serviceTypeAdd',
							xtype : 'combo',
							fieldLabel : '业务种类',
							emptyText : '<----请选择---->',
							hiddenName : 'glBankGuaranteemoney.operationType',
							name :'glBankGuaranteemoney.operationType',
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
		            		fieldLabel : '保证金帐户',
		            		onTriggerClick : function(){
								selectAccountBankName1(getCautionAccountName);
							}
						}]},{
						columnWidth :.93,
						layout : 'form',
						labelWidth : 95,
						defaults : {anchor : '100%'},
						items :[{
							id : 'surplusMoney_Add',
							xtype : 'textfield',
							readOnly : true,
							fieldClass:'field-align',
		            		fieldLabel : '账户可用金额'
						}]
						},{
							 columnWidth : .07, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth :30,
							items : [{
								fieldLabel : "<span style='margin-left:1px'>万元</span> ",
								labelSeparator : '',
								anchor : "100%"
							
					 }]},{
						columnWidth : .7,
						layout : 'form',
						labelWidth : 95,
						defaults : {anchor : '100%'},
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
						defaults : {xtype : 'textfield',anchor : '100%'},
						items :[{
							iconCls : 'slupIcon',
							text : '上传冻结保证金凭证',
							xtype : 'button',
							scope : this,
							handler : function() {
								var p=Ext.get('projectId_Add').dom.value;
								if(p==""){
									Ext.Msg.alert('状态','请填写项目名称!');
								}else{
									var	projectId =projid;
									var setname ='银行冻结保证金凭证';
									var titleName ='银行冻结保证金凭证';
									var tableName ='gl_Bank_guaranteemoney';
									var typeisfile ='typeisbankguaranteemoney';
									//var businessType=this.businessType;
									var mark=tableName+"."+projectId
									uploadReportJS('上传/下载'+titleName+'文件',typeisfile,mark,1,null,null,null,projectId,'Guarantee',setname);
									}
									
								}
						}]},{
						columnWidth : .43,
						layout : 'form',
						labelWidth : 95,
						defaults : {anchor : '100%'},
						items :[{
							id : 'bankMarginMoney_Add',
							xtype : 'numberfield',
		            		fieldLabel : '冻结金额',
		            		fieldClass:'field-align',
		            		allowBlank : false,
		            		name : 'glBankGuaranteemoney.freezeMoney'
						}]},{
										 columnWidth : .07, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth :30,
										items : [{
											fieldLabel : "<span style='margin-left:1px'>万元</span> ",
											labelSeparator : '',
											anchor : "100%"
										
									 }]},{
						columnWidth : .5,
						layout : 'form',
						labelWidth : 110,
						defaults : {anchor : '100%'},
						items :[{
							id : 'bailPayoutDate_Add',
							xtype : 'datefield',
							fieldLabel : '冻结日期',
							allowBlank : false,
							format : 'Y-m-d',
							name : 'glBankGuaranteemoney.freezeDate'
						}]},{
						columnWidth : 1,
						layout : 'form',
						labelWidth : 95,
						defaults : {anchor : '100%'},
						items :[{
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
			            	name : 'glBankGuaranteemoney.projectId',
			            	xtype : 'hidden'
						}]
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
			},'-',{
			text : '编辑冻结项目记录',
		tooltip : '编辑选中记录的冻结项目记录',
		iconCls : 'btn-edit',
		scope : this,
		hidden : isGranted('_editdjxmjl_q')?false:true,
		handler : function() {
			var accountBankTreePanel1 =this.accountBankTreePanel;
			var selected = this.gPanel_cautionMoneyProj.getSelectionModel().getSelected();
			if (null == selected) {
					Ext.ux.Toast.msg('状态', '请选择一条记录!');
			}else{
				var projectId = selected.get('projectId');
				var bankGuaranteeId=selected.get('bankGuaranteeId');
					Ext.Ajax.request({
						url : __ctxPath + '/creditFlow/guarantee/EnterpriseBusiness/getGlBankGuaranteemoney.do',
						method : 'POST',
						success : function(response, request){
							objFrozen = Ext.util.JSON.decode(response.responseText);
							projid =objFrozen.data.projectId;
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
								border : false,
								width : (screen.width-180)*0.55,
								height : 315,
								modal : true, //添加遮罩层
								buttonAlign : 'center',
								buttons : [
														{
															text : '保存',
															iconCls : 'btn-save',
															scope : this,
															handler : function() {
												
												var cautionCode_update =Ext.get('cautionCode_update').dom.value;
												var bankMarginMoney_update =Ext.get('bankMarginMoney_update').dom.value;
												var bailPayoutDate_update =Ext.get('bailPayoutDate_update').dom.value;
												var surplusMoney_update=Ext.get('surplusMoney_update').dom.value;
												var projId =Ext.get('projectId').dom.value;
												
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
															Ext.ux.Toast.msg('状态','编辑成功!');
																		jStore_cautionMoneyProj.reload();
																		accountBankTreePanel1.getRootNode().reload();
																		accountBankTreePanel1.expandAll() ;
																		updateFrozenForProjectWin.destroy();
														},
														failure : function(form ,action) {
															objtUpdate = Ext.util.JSON.decode(action.response.responseText);
															Ext.Msg.alert('状态', objtUpdate.msg);
														}
													});
												}
											}
														}, 
													
                                                         {
															text : '取消',
															iconCls : 'btn-cancel',
															scope : this,
															handler : function(){
									
								                             	updateFrozenForProjectWin.destroy();
									
								                         	}
														}
											         ],
								items :[
									new Ext.form.FormPanel({
										id : 'updateFrozenForProjectForm',
										labelAlign : 'right',
										buttonAlign : 'center',
								//		url : 'updateFrozenForProject.action',
										bodyStyle : 'padding:10px 25px 25px',
									    layout : 'column',
										frame : true,
										border : false,
										waitMsgTarget : true,
										monitorValid : true,
										autoWidth : true,
										autoHight : true ,
										buttonAlign : 'center',
										
										items :[{
											columnWidth : 1,
											layout : 'form',
											id : 'updateFrozenForProjectColumn',
											labelWidth : 90,
										   defaults : {anchor : '100%'},
											items :[{
												xtype : 'textfield',
												fieldLabel : '业务种类',
												editable : false,
												allowBlank : false,
												readOnly : true,
												cls : 'readOnlyClass',
												value : objFrozen.data.operationTypeName
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
							            		fieldLabel : '保证金帐户',
							            		value : accountname,
							            		onTriggerClick : function(){
													selectAccountBankName1(getCautionAccountNameUpdate);
												}
											}]},{
											columnWidth : 1,
											layout : 'form',
											labelWidth : 110,
										   defaults : {anchor : '100%'},
											items :[{
												id : 'surplusMoney_update1',
												xtype : 'hidden',
												readOnly : true,
							            		fieldLabel : '账户可用金额.万元',
							            		fieldClass:'field-align',
							            		value :surplusMoney
											}]},{
											columnWidth : 0.93,
											layout : 'form',
											labelWidth : 90,
										   defaults : {anchor : '100%'},
											items :[{
												id : 'surplusMoney_update',
												xtype : 'textfield',
												readOnly : true,
							            		fieldLabel : '账户可用金额',
							            		fieldClass:'field-align',
							            		value :surplusMoney
											}]},{
											 columnWidth : .07, // 该列在整行中所占的百分比
											layout : "form", // 从上往下的布局
											labelWidth :30,
											items : [{
												fieldLabel : "<span style='margin-left:1px'>万元</span> ",
												labelSeparator : '',
												anchor : "100%"
											
										 }]},{
												
											columnWidth : .7,
											layout : 'form',
											labelWidth : 90,
											defaults : {anchor : '100%'},
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
											labelWidth : 100,
											defaults : {xtype : 'textfield',anchor : '100%'},
											items :[{
											iconCls : 'slupIcon',
											text : '上传/下载冻结保证金凭证',
											xtype : 'button',
											scope : this,
											handler : function() {
												var	projectId =projid;
												var setname ='银行冻结保证金凭证';
												var titleName ='银行冻结保证金凭证';
												var tableName ='gl_Bank_guaranteemoney';
												var typeisfile ='typeisbankguaranteemoney';
												//var businessType=this.businessType;
												var mark=tableName+"."+projectId
												uploadReportJS('上传/下载'+titleName+'文件',typeisfile,mark,1,null,null,null,projectId,'Guarantee',setname);
											}
										}]},
										{columnWidth : .43,
										layout : 'form',
										labelWidth : 90,
										defaults : {anchor : '100%'},
										items :[{
												id : 'bankMarginMoney_update',
												xtype : 'textfield',
							            		fieldLabel : '冻结金额',
							            		fieldClass:'field-align',
							            		allowBlank : false,
							            		name : 'glBankGuaranteemoney.freezeMoney',
												value : objFrozen.data.freezeMoney
									}]},{
										 columnWidth : .07, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth :30,
										items : [{
											fieldLabel : "<span style='margin-left:1px'>万元</span> ",
											labelSeparator : '',
											anchor : "100%"
										
									 }]},{
											columnWidth : .5,
											layout : 'form',
											labelWidth : 115,
											defaults : {anchor : '100%'},
											items :[{
												id : 'bailPayoutDate_update',
												xtype : 'datefield',
												fieldLabel : '冻结日期',
												allowBlank : false,
												format : 'Y-m-d',
												name : 'glBankGuaranteemoney.freezeDate',
												value : objFrozen.data.freezeDate
											}]},{
												columnWidth : 1,
												layout : 'form',
												labelWidth : 90,
												defaults : {anchor : '100%'},
												items :[{
												id : 'cautionmoneyPayRemarks_update',
												xtype : 'textarea',
												fieldLabel : '备&nbsp;&nbsp;注 ',
												name : 'glBankGuaranteemoney.remark',
												maxLength : 255,
												value : objFrozen.data.remark
											},{
												id : 'projectId',
												name : 'glBankGuaranteemoney.projectId',
								            	value : objFrozen.data.projectId,
								            	xtype : 'hidden'
											},{
												id : 'cautionAccountId_update1',
								            	value : objFrozen.data.accountId,
								            	xtype : 'hidden'
											},{ 
											     id  : 'AccountId_update',
												name : 'glBankGuaranteemoney.accountId',
								            	value : objFrozen.data.accountId,
								            	xtype : 'hidden'
											},{
												name : 'glBankGuaranteemoney.bankGuaranteeId',
								            	value : objFrozen.data.bankGuaranteeId,
								            	xtype : 'hidden'
											}]
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
							Ext.ux.Toast.msg('状态','操作失败，请重试');
						},
						params : {
							projId : projectId
						}
					})
				}
			
		}
			
			},'-',{
			text : '删除项目相关记录',
		tooltip : '删除选中记录的信息',
		//disabled : bool,
		iconCls : 'btn-del',
		scope : this,
		hidden : isGranted('_removexmxgjl_q')?false:true,
		handler : function() {
			var accountBankTreePanel1 =this.accountBankTreePanel;1
			var selected = this.gPanel_cautionMoneyProj.getSelectionModel().getSelected();
			if (null == selected) {
				Ext.ux.Toast.msg('状态', '请选择一条记录!');
			}else{
					var projectId = selected.get('projectId');
				var bankGuaranteeId = selected.get('bankGuaranteeId');
				Ext.MessageBox.confirm('确认删除', '是否确认删除该条记录？ ', function(btn) {
					if (btn == 'yes') {
						Ext.Ajax.request({
								url : __ctxPath + '/creditFlow/guarantee/EnterpriseBusiness/deleteGlBankGuaranteemoney.do',
							method : 'POST',
							success : function(response, request) {
								objDeleteData = Ext.util.JSON.decode(response.responseText);
									Ext.ux.Toast.msg('状态','删除成功');
											accountBankTreePanel1.getRootNode().reload();
											accountBankTreePanel1.expandAll() ;
											jStore_cautionMoneyProj.reload();
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
			
				
			},'-',{
			text : '查看项目相关记录',
		tooltip : '查看选中记录的项目相关记录',
		iconCls : 'btn-detail',
		scope : this,
		hidden : isGranted('_seexmxgjl_q')?false:true,
		handler : function() {
			var selected = this.gPanel_cautionMoneyProj.getSelectionModel().getSelected();
			if (null == selected) {
				Ext.ux.Toast.msg('状态', '请选择一条记录!');
			}else{
				var projectId = selected.get('projectId');
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
								border : false,
								width : (screen.width-180)*0.55,
								height : 370,
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
										border : false,
										waitMsgTarget : true,
										monitorValid : true,
										autoWidth : true,
										autoHight : true ,
										items :[{
											columnWidth : 1,
											layout : 'form',
											labelWidth : 90,
											defaults : {anchor : '100%'},
											items :[{
												xtype : 'textfield',
												fieldLabel : '业务种类',
												editable : false,
												allowBlank : false,
												readOnly : true,
												cls : 'readOnlyClass',
												value : objFrozen.data.operationTypeName
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
							            		fieldLabel : '保证金帐户',
							            		value : objFrozen.data.guaranteebankName+"-"+objFrozen.data.guaranteeaccount
											}]},{
											columnWidth : .7,
											layout : 'form',
											labelWidth : 90,
											defaults : {anchor : '100%'},
											items :[{
												xtype : 'textfield',
							            		fieldLabel : '保证金凭证号码',
							            		readOnly : true,
												value : objFrozen.data.certificateNum
											}]},{
											columnWidth : .3,
											layout : 'form',
											labelWidth : 100,
											defaults : {anchor : '100%'},
											items :[{
											
												iconCls : 'sldownIcon',
												text : '下载冻结保证金凭证',
												xtype : 'button',
												scope : this,
												handler : function() {
													var mark1="gl_Bank_guaranteemoney."+projectId;
											         var typeisfile1="typeisbankguaranteemoney";
													window.open(__ctxPath+'/creditFlow/fileUploads/DownLoadFilebymarkFileForm.do?mark='+mark1+'&typeisfile='+typeisfile1,'_blank');
												}				
											
											}]},
											{
											columnWidth : .43,
											layout : 'form',
											labelWidth : 90,
											defaults : {anchor : '100%'},
											items :[
												{
												xtype : 'textfield',
							            		fieldLabel : '冻结金额',
							            		fieldClass:'field-align',
							            		readOnly : true,
												value : objFrozen.data.freezeMoney
											}]},{
											 columnWidth : .07, // 该列在整行中所占的百分比
											layout : "form", // 从上往下的布局
											labelWidth :30,
											items : [{
												fieldLabel : "<span style='margin-left:1px'>万元</span> ",
												labelSeparator : '',
												anchor : "100%"
											
										 }]},{
											columnWidth : .5,
											layout : 'form',
											labelWidth : 110,
											defaults : {anchor : '100%'},
											items :[{
												xtype : 'datefield',
												fieldLabel : '冻结日期',
												readOnly : true,
												format : 'Y-m-d',
												value : objFrozen.data.freezeDate
											}]} ,{
											columnWidth : .63,
											layout : 'form',
											labelWidth : 90,
											defaults : {xtype : 'textfield',anchor : '100%'},
											items :[{
												xtype : 'numberfield',
							            		fieldLabel : '解冻金额',
							            		fieldClass:'field-align',
							            		readOnly : true,
							            		name : 'glBankGuaranteemoney.unfreezeMoney',
							            		value : objFrozen.data.unfreezeMoney==0?'':objFrozen.data.unfreezeMoney
											}]},{
										 columnWidth : .07, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth :30,
										items : [{
											fieldLabel : "<span style='margin-left:1px'>万元</span> ",
											labelSeparator : '',
											anchor : "100%"
										
									   }]},  {
											columnWidth : .3,
											layout : 'form',
											labelWidth : 90,
											defaults : {xtype : 'textfield',anchor : '100%'},
											items :[{
												iconCls : 'sldownIcon',
											text : '下载解冻保证金凭证',
											xtype : 'button',
											scope : this,
											handler : function() {
										         
										         var mark1="back_gl_bank_guaranteemoney."+projectId;
											         var typeisfile1="typeisbackglbankguaranteemoney";
													window.open(__ctxPath+'/creditFlow/fileUploads/DownLoadFilebymarkFileForm.do?mark='+mark1+'&typeisfile='+typeisfile1,'_blank');
												}
											}]},{
											columnWidth : 1,
											layout : 'form',
											labelWidth : 90,
											defaults : {xtype : 'textfield',anchor : '100%'},
											items :[{
												xtype : 'datefield',
												fieldLabel : '解冻日期',
												readOnly : true,
												format : 'Y-m-d',
												name : 'glBankGuaranteemoney.releaseDate',
												value : objFrozen.data.releaseDate
											},{
												xtype : 'textarea',
												fieldLabel : '冻结项目备注 ',
												readOnly : true,
												maxLength : 255,
												value : objFrozen.data.remark
											},{
												xtype : 'textarea',
												fieldLabel : '解冻项目备注 ',
												name : 'glBankGuaranteemoney.releaseremark',
												maxLength : 255,
												readOnly : true,
												value : objFrozen.data.releaseremark
											}]
											}]
									
									})
								]
							});
							updateFrozenForProjectWin.show();
						},
						failure : function(response) {
							Ext.ux.Toast.msg('状态','操作失败，请重试');
						},
						params : {
							projId : projectId
						}
					})
				}
			
		}	
			
			},'-',{
			
			text : '解冻保证金',
		tooltip : '解冻选中记录的项目保证金',
		iconCls : 'updateIcon',
		scope : this,
		hidden : isGranted('_jdbzj_q')?false:true,
		handler : function() {
			var accountBankTreePanel1 =this.accountBankTreePanel;
			var selected = this.gPanel_cautionMoneyProj.getSelectionModel().getSelected();
			if (null == selected) {
				Ext.ux.Toast.msg('状态', '请选择一条记录!');
			}else{
				if(selected.data.freezeMoney !=null){
				var projectId = selected.get('projectId');
				var bankGuaranteeId = selected.get('bankGuaranteeId');
			
					Ext.Ajax.request({
						url : __ctxPath + '/creditFlow/guarantee/EnterpriseBusiness/getGlBankGuaranteemoney.do',
						method : 'POST',
						success : function(response, request){
							objUnfreeze = Ext.util.JSON.decode(response.responseText);
							projid=objUnfreeze.data.projectId;
							var unfreezeCautionAccountWin = new Ext.Window({
								id : 'unfreezeCautionAccountWin',
								layout : 'fit',
								title : '解冻项目金额',
								border : false,
								width : (screen.width-180)*0.55,
								height : 400,
								minimizable : true,
								modal : true,
							    buttonAlign : 'center',
							    buttons : [
										{
											text : '保存',
											iconCls : 'btn-save',
											scope : this,
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
															Ext.ux.Toast.msg('状态','解冻成功');
																		jStore_cautionMoneyProj.reload();
																		accountBankTreePanel1.getRootNode().reload();
																		accountBankTreePanel1.expandAll() ;
																		unfreezeCautionAccountWin.destroy();
														},
														failure : function(form ,action) {
															objt = Ext.util.JSON.decode(action.response.responseText);
															Ext.Msg.alert('状态', objt.msg);
														}
													});
												}
											}
				
										
										}, {
											text : '取消',
											iconCls : 'btn-cancel',
											scope : this,
											handler : function(){
									                 unfreezeCautionAccountWin.destroy();
									
									}
										}
							         ],
								items :[
									new Ext.form.FormPanel({
										id : 'unfreezeCautionAccountForm',
										labelAlign : 'right',
										buttonAlign : 'center',
								//		url : 'updateUnfreezeCautionAccount.action',
										bodyStyle : 'padding:10px 25px 25px',
									    layout : 'column',
										frame : true,
										border : false,
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
												value : objUnfreeze.data.operationTypeName
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
												text : '下载冻结保证金凭证',
												xtype : 'button',
												scope : this,
												handler : function() {
													var mark1="gl_Bank_guaranteemoney."+projid;
											         var typeisfile1="typeisbankguaranteemoney";
													window.open(__ctxPath+'/creditFlow/fileUploads/DownLoadFilebymarkFileForm.do?mark='+mark1+'&typeisfile='+typeisfile1,'_blank');
																	
											}
										}]},{
										   columnWidth : .93,
											layout : 'form',
											labelWidth : 100,
											defaults : {xtype : 'textfield',anchor : '100%'},
											items :[{
												id : 'bankMarginMoney_unfreeze',
							            		fieldLabel : '冻结金额',
							            		fieldClass:'field-align',
							            		allowBlank : false,
							            		readOnly : true,
												cls : 'readOnlyClass',
												value : objUnfreeze.data.freezeMoney
											}]},{
										 columnWidth : .07, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth :30,
										items : [{
											fieldLabel : "<span style='margin-left:1px'>万元</span> ",
											labelSeparator : '',
											anchor : "100%"
										
									 }]},{
											columnWidth : 1,
											layout : 'form',
											labelWidth : 100,
											defaults : {xtype : 'textfield',anchor : '100%'},
											items :[{
												id : 'freezeDate_unfreeze',
												xtype : 'datefield',
												fieldLabel : '冻结日期',
												readOnly : true,
												format : 'Y-m-d',
												value : objUnfreeze.data.freezeDate
											}]},  {
											columnWidth : .63,
											layout : 'form',
											labelWidth : 100,
											defaults : {xtype : 'textfield',anchor : '100%'},
											items :[{
												id : 'unfreezeMoney_unfreeze',
												xtype : 'numberfield',
							            		fieldLabel : '解冻金额',
							            		fieldClass:'field-align',
							            		allowBlank : false,
							            		name : 'glBankGuaranteemoney.unfreezeMoney',
							            		value : objUnfreeze.data.unfreezeMoney==0?'':objUnfreeze.data.unfreezeMoney
											}]},{
										 columnWidth : .07, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth :30,
										items : [{
											fieldLabel : "<span style='margin-left:1px'>万元</span> ",
											labelSeparator : '',
											anchor : "100%"
										
									 }]},  {
											columnWidth : .3,
											layout : 'form',
											labelWidth : 100,
											defaults : {xtype : 'textfield',anchor : '100%'},
											items :[{
												iconCls : 'slupIcon',
											text : '上传/下载解冻保证金凭证',
											xtype : 'button',
											scope : this,
											handler : function() {
												var	projectId =projid;
												var setname ='银行解冻保证金凭证';
												var titleName ='银行解冻保证金凭证';
												var tableName ='back_gl_bank_guaranteemoney';
												var typeisfile ='typeisbackglbankguaranteemoney';
												//var businessType=this.businessType;
												var mark=tableName+"."+projectId
												uploadReportJS('上传/下载'+titleName+'文件',typeisfile,mark,1,null,null,null,projectId,'Guarantee',setname);
											}
											}]},{
											columnWidth : 1,
											layout : 'form',
											labelWidth : 100,
											defaults : {xtype : 'textfield',anchor : '100%'},
											items :[{
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
												name : 'glBankGuaranteemoney.releaseremark',
												maxLength : 255,
												value : objUnfreeze.data.releaseremark
											},{
												id : 'projId_unfree',
												name : 'glBankGuaranteemoney.projectId',
								            	value : objUnfreeze.data.projectId,
								            	xtype : 'hidden'
											},{
												id : 'bankGuaranteeId_unfree',
												name : 'glBankGuaranteemoney.bankGuaranteeId',
								            	value : objUnfreeze.data.bankGuaranteeId,
								            	xtype : 'hidden'
											},{
												id : 'accountId_unfree',
												name : 'glBankGuaranteemoney.accountId',
								            	value : objUnfreeze.data.accountId,
								            	xtype : 'hidden'
											}]
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
							Ext.ux.Toast.msg('状态','操作失败，请重试');
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
				
			}
			
			
			]
	});
	this.gPanel_cautionMoneyProj = new Ext.grid.GridPanel( {
		region:'center',
		title : '项目相关记录',
		id : 'gPanel_cautionMoneyProj',
		store : jStore_cautionMoneyProj,
		height :500,
		colModel : cModel_cautionMoneyProj,
		selModel : new Ext.grid.RowSelectionModel(),
		stripeRows : true,
		bbar : pagingBar,
		tbar : tbar1,
		listeners : {
			'rowdblclick' : function(grid,index,e){
			var selected = grid.getSelectionModel().getSelected();
//				var projectStatus = selected.get('projectStatus');
//				var bmStatus = selected.get('bmStatus');
//				detailPro(grid,'GuaranteeProjectInfoEdit_',projectStatus,bmStatus,'see')
//				
				var projectId = selected.get('projectId');
				var businessType = selected.get('businessType');

					var idPrefix = "";
					idPrefix = "GuaranteeProjectInfo_";
					Ext.Ajax.request({
						url : __ctxPath + '/creditFlow/getProjectViewObjectCreditProject.do',
						params : {
							businessType : businessType,
							projectId : projectId
						},
						method : 'post',
						success : function(resp, op) {
							var record = Ext.util.JSON.decode(resp.responseText);//JSON对象，root为data,通过record对象取数据必须符合"record.data.name"格式
							showProjectInfoTab(record, idPrefix)
						},
						failure : function() {
							Ext.ux.Toast.msg('信息提示', '出错，请联系管理员！');
						}
					})
			}
		}
	});
	
	
	var accountBankTreeLoad = new Ext.tree.TreeLoader({
		dataUrl : __ctxPath + '/creditFlow/guarantee/guaranteefinance/getAccountBankTreeGlAccountBank.do'
	})

	var tbar=new Ext.Toolbar({
			items:[{
			text : '查看',
			tooltip : '查看保证金账户信息',
			iconCls : 'btn-detail',
			scope : this,
			handler : function() {
				var id = Ext.getCmp('selectidProj').value;
				var leaf = Ext.getCmp('selectleafProj').value;
				if(leaf==false){
					this.seebank(id,leaf)
				}else{
				this.seeCautionAccount(id,leaf);
				}
			
			}},'-',{
			iconCls : 'btn-refresh',
			scope : this,
			text : '刷新',
			hidden : isGranted('_save_zxpz_'+this.businessType)?false:true,
			handler : this.refresh
				}
//				,'-','关键字:',{
//				 xtype:'trigger'
//					 ,triggerClass:'x-form-clear-trigger'
//					 ,
//					 scope:this,
//					 onTriggerClick:function() {
//		
//						 this.setValue('');
//						 fil.clear();
//					 }
//					 ,id:'filter'
//					 ,enableKeyEvents:true,
//					 width :180
//					 ,listeners:{
//					 keyup:{buffer:150, fn:function(field, e) {
//						 if(Ext.EventObject.ESC == e.getKey()) {
//						    field.onTriggerClick();
//						 }
//						 else {
//							 var val = this.getRawValue();
//							 var re = new RegExp('.*' + val + '.*', 'i');
//							 fil.clear();
//							 fil.filter(re, 'text');
//					     }
//					 }}
//					 }
//					 }
			]
	})
	//树列表
	this.accountBankTreePanel = new Ext.ux.tree.TreeGrid({
		tbar : tbar,
		id : 'accountBankTreePanelProj' ,
		width: 500 ,
		height : 550 ,
		layout : 'fit',
		border : false,
//	filter :true,
		loader : accountBankTreeLoad ,
		root : new Ext.tree.AsyncTreeNode({
			text :'根节点',
			id : '0'
		}),
		columns : [
		{
			width : 320,
			header : '银行名称',
			dataIndex : 'text',
			tpl: new Ext.XTemplate('{text:this.formatText}', {
            formatText: function(v) {
	            	if(v.indexOf("(")=="-1"){//后台传的值不包含()
	            		return v;
	            	}else{
	            		var len = v.substring(v.indexOf("("));//获取从'('开始至末位的值
	            		var start = v.substring(0,v.indexOf("("));//获取从0开始至第一次出现'('位置之间的值
	            		return start+'<font color=green>'+len+'</font>';
	            	}
	            }
	        })
		},{
			width : 85,
			header : '总额度',
			align : 'right',
			dataIndex : 'authorizationMoney',
			tpl: new Ext.XTemplate('{authorizationMoney:this.formatType}', {
                formatType: function(v) {
					if(typeof(v) != "undefined"){
						if(typeof(v) != "string"){
							return v+'万元';
						}else{
							return '<font color=green>'+v+'</font>';
						}
					}else{
						return '';
					}
                }
            })
		},{
			width : 85,
			header : '可用额度',
			dataIndex : 'surplusMoney',
			align : 'right',
			tpl: new Ext.XTemplate('{surplusMoney:this.formatMoney}', {
                formatMoney: function(v) {
                	if(typeof(v) != "undefined"){
						if(typeof(v) != "string"){
							if(v < 0){
								return '<font color=red>'+v+'</font>'+'万元';;
							}else if(v >= 0){
								return v+'万元';
							}else{
								return '';
							}
						}else{
							return '<font color=green>'+v+'</font>'+'万元';
						}
					}else{
						return '';
					}
					/*if(v < 0){
						return '<font color=red>'+v+'&nbsp;&nbsp;&nbsp;&nbsp;</font>';
					}else if(v >= 0){
						return v+'&nbsp;&nbsp;&nbsp;&nbsp;';
					}else{
						return '';
					}*/
                }
            })
		},{
			width : 0,
			header : '',
			dataIndex : 'bankParentId',
			hidden : true
		}],
		listeners : {
			scope :this,
			click : function(node){
				var gPanel_cautionMoneyProj1=this.gPanel_cautionMoneyProj;
			//'dblclick' : function(node){
				Ext.getCmp('selectidProj').value = node.id;
				Ext.getCmp('selectleafProj').value = node.leaf;
				jStore_cautionMoneyProj.removeAll();
				var nodeId;
				if(node.leaf == false){
					var nodeRootId = node.id;
					jStore_cautionMoneyProj.baseParams.accountBankId = nodeRootId;
					jStore_cautionMoneyProj.baseParams.nodeId = 0;
					gPanel_cautionMoneyProj1.setTitle(node.text+"(项目相关记录)");
					jStore_cautionMoneyProj.load({
						params : {
							start : start,
							limit : pageSize
						}
					});
				}else if(node.leaf == true){
					var bankWholeName;
					var accountName;
					nodeId = node.id;
					Ext.Ajax.request({
						url:__ctxPath + '/creditFlow/guarantee/guaranteefinance/getGlAccountBankCautionmoney.do',
						method : 'POST',
						success : function(response, request) {
							objAccountBank = Ext.util.JSON.decode(response.responseText);
							accountBankCautionMoney = objAccountBank.data;
							if(objAccountBank.success==true){
								bankWholeName = accountBankCautionMoney.bankBranchName;
								accountName = accountBankCautionMoney.accountname;
								gPanel_cautionMoneyProj1.setTitle(bankWholeName+"-"+accountName+"(保证金账户记录)");
								jStore_cautionMoneyProj.baseParams.nodeId = nodeId;
								jStore_cautionMoneyProj.baseParams.accountBankId = "";
								jStore_cautionMoneyProj.load({
									params : {
										start : start,
										limit : pageSize
									}
								});
							}else{
								Ext.Msg.alert('状态', objAccountBank.msg);
							}
						},
						failure : function(response) {
					Ext.ux.Toast.msg('状态','操作失败，请重试');
						},
						params : {
							glAccountBankCautionmoneyId : nodeId
						}
					});
				}
				
			},
			contextmenu : function(node,e) {
//				Ext.getCmp('selectid').value = node.id;
//				Ext.getCmp('selectleaf').value = node.leaf;
			//	setPopMenuFunction(node,e);
			}
		}
	});
//	var Filter = Ext.ux.tree.TreeFilterX ? Ext.ux.tree.TreeFilterX : Ext.tree.TreeFilter;
//	var fil = new Filter(this.accountBankTreePanel, {autoClear:true});
	this.accountBankTreePanel.expandAll() ;
	this.accountBankPanel = new Ext.Panel({
		id : 'accountBankPanelProj',
		border : false,
		region:'west',
	 	layout:'anchor',
	 	collapsible : true,
		split : true,
		width : 500,
		title : '银行保证金账户',
		loadMask : myMask,
		items : [{
			layout : 'fit',
			columnWidth : 1,
			items : [this.accountBankTreePanel]
		},{
			id : 'selectidProj',
			xtype : 'hidden',
			name : 'id',
			value : '0'
		},{
			id : 'selectleafProj',
			xtype : 'hidden',
			name : 'leaf'
		},{
			id : 'selectbankParentIdProj',
			xtype : 'hidden',
			name : 'bankParentId'
		}]
	});

	},//end of initUIComponents
	seebank :  function (id,leaf){
			if(id == '0') {
			Ext.ux.Toast.msg('状态', '请选择一条记录!');
			}else{
				Ext.Ajax.request({
					url:__ctxPath + '/creditFlow/guarantee/guaranteefinance/getGlAccountBank.do',
					method : 'POST',
					success : function(response, request){
						objAccountSee = Ext.util.JSON.decode(response.responseText);
						var seeAccountBankWin = new Ext.Window({
							id : 'seeAccountBankWinProj',
							layout : 'fit',
							title : '总行保证金信息',
							border : false,
							width : (screen.width-180)*0.47,
							height : 205,
							modal : true,
							items :[
								new Ext.form.FormPanel({
									id : 'seeAccountBankFormProj',
									labelAlign : 'right',
									buttonAlign : 'center',
			//						url : 'updateAccountBank.action',
									bodyStyle : 'padding:10px 25px 25px 13px',
								    layout : 'column',
									frame : true,
									border : false,
									waitMsgTarget : true,
									monitorValid : true,
									autoWidth : true,
									autoHight : true ,
									items :[{
										columnWidth : 1,
										layout : 'form',
										labelWidth : 90,
										defaults : {xtype : 'textfield',anchor : '100%'},
										items :[{
											fieldLabel : '银行名称',
											readOnly : true,
											cls : 'readOnlyClass',
											value : objAccountSee.data.text
										}]},{
										columnWidth : .43,
										layout : 'form',
										labelWidth : 90,
										defaults : {xtype : 'textfield',anchor : '100%'},
										items :[{
											fieldLabel : '总额度',
											fieldClass:'field-align',
											readOnly : true,
											cls : 'readOnlyClass',
											value : objAccountSee.data.authorizationMoney
										}]},{
											 columnWidth : .07, // 该列在整行中所占的百分比
											layout : "form", // 从上往下的布局
											labelWidth :30,
											items : [{
												fieldLabel : "<span style='margin-left:1px'>万元</span> ",
												labelSeparator : '',
												anchor : "100%"
											}]
										 },{
										columnWidth : .43,
										layout : 'form',
										labelWidth : 100,
										defaults : {xtype : 'textfield',anchor : '100%'},
										items :[{
											fieldLabel : '可用额度',
											fieldClass:'field-align',
											readOnly : true,
											cls : 'readOnlyClass',
											value : objAccountSee.data.surplusMoney
										}]},{
											 columnWidth : .07, // 该列在整行中所占的百分比
											layout : "form", // 从上往下的布局
											labelWidth :30,
											items : [{
												fieldLabel : "<span style='margin-left:1px'>万元</span> ",
												labelSeparator : '',
												anchor : "100%"
											
										 }]},{
										 columnWidth : 1,
										layout : 'form',
										labelWidth : 90,
										defaults : {xtype : 'textfield',anchor : '100%'},
										items :[{
											fieldLabel : '适用业务种类',
											readOnly : true,
											cls : 'readOnlyClass',
											value : objAccountSee.data.serviceTypeBank
										}
//										,{
//											fieldLabel : '创建时间',
//											readOnly : true,
//											cls : 'readOnlyClass',
//											value : objAccountSee.data.createDate
//										}
										,{
											xtype : 'textarea',
											fieldLabel : '备&nbsp;&nbsp;注 ',
											readOnly : true,
											cls : 'readOnlyClass',
											value : objAccountSee.data.remark
										}]
									}]/*,

									tbar : [{
										text : '取消',
										iconCls : 'cancelIcon',
										handler : function() {
											seeAccountBankWin.destroy();
										}
									}]*/
								})
							]
						}).show();
					},
					failure : function(response) {
							Ext.ux.Toast.msg('状态', '操作失败，请重试');
					},
					params : {
						glAccountBankId : id
					}
				})
			}
   	
   },
   refresh :function (){
	var accountBankTreePanel1=this.accountBankTreePanel;
	accountBankTreePanel1.getRootNode().reload();
	accountBankTreePanel1.expandAll() ;
},
     //查看保证金账户信息
seeCautionAccount :function(id,leaf){
	if(id == '0') {
	 Ext.ux.Toast.msg('状态', '请选择一条记录!');
   }else{
	
	Ext.Ajax.request({
		 url:__ctxPath + '/creditFlow/guarantee/guaranteefinance/getGlAccountBankCautionmoney.do',
		
		method : 'POST',
		success : function(response, request){
			objSee = Ext.util.JSON.decode(response.responseText);
			//alert("冻结金额==="+objSee.data.accountFrozenMoney+"       解冻金额==="+objSee.data.accountUnfreezeMoney);
			var seeCautionAccountWin = new Ext.Window({
				id : 'seeCautionAccountWinproj',
				layout : 'fit',
				title : '分行保证金账户',
				border : false,
				//width : 500,
				width : (screen.width-180)*0.5,
				height : 280,
				modal : true,
				items :[
					new Ext.form.FormPanel({
						labelAlign : 'right',
						buttonAlign : 'center',
						bodyStyle : 'padding:10px 25px 25px 3px',
					    layout : 'column',
						frame : true,
						border : false,
						waitMsgTarget : true,
						monitorValid : true,
						autoWidth : true,
						autoHight : true ,
						items :[{
							columnWidth : 1,
							layout : 'form',
							labelWidth : 85,
							defaults : {xtype : 'textfield',anchor : '100%'},
							items :[{
								fieldLabel : '开户银行 ',
								readOnly : true,
								cls : 'readOnlyClass',
								value : objSee.data.bankBranchName
							}]},{
				
							columnWidth : .52,
							layout : 'form',
							labelWidth : 85,
							defaults : {anchor : '100%'},
							items :[{
								xtype : 'textfield',
								fieldLabel : '账户名称',
								readOnly : true,
								value : objSee.data.text
								}]},{
								
								columnWidth : .02,
								layout : 'form',
								labelWidth : 2,
								defaults : {anchor : '97%'},
								items :[{
									fieldLabel : "<span style='margin-left:1px'>-</span> ",
													labelSeparator : '',
													anchor : "100%"
								}]},{
								
						columnWidth : .46,
						layout : 'form',
						labelWidth : 3,
						defaults : {anchor : '100%'},
						items :[{
							xtype : 'textfield',
							allowBlank : false,
							readOnly :true,
							value : objSee.data.accountname
						}]},{
					
						columnWidth : 1,
						layout : 'form',
						labelWidth : 85,
						defaults : {anchor : '100%'},
						items :[{
								fieldLabel : '银行地址',
								xtype : 'textfield',
								readOnly : true,
								cls : 'readOnlyClass',
								value : objSee.data.bankAddress
							}]},{
						columnWidth : .45,
						layout : 'form',
						labelWidth : 85,
						defaults : {anchor : '100%'},
						items :[{
								xtype : 'textfield',
								readOnly : true,
								name : 'glAccountBankCautionmoney.rawauthorizationMoney',
								fieldClass:'field-align',
								fieldLabel : '初始总金额',								
								value : objSee.data.rawauthorizationMoney
							},{
								xtype : 'textfield',
								readOnly : true,
								name : 'glAccountBankCautionmoney.authorizationMoney',
								fieldClass:'field-align',
								fieldLabel : '总金额',							
								value : objSee.data.authorizationMoney
							}]},{
								 columnWidth : .07, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth :30,
								items : [{
									fieldLabel : "<span style='margin-left:1px'>万元</span> ",
									labelSeparator : '',
									anchor : "100%"
								
							 },{
									fieldLabel : "<span style='margin-left:1px'>万元</span> ",
									labelSeparator : '',
									anchor : "100%"
								
							 }]},{
							columnWidth : .42,
							layout : 'form',
							labelWidth : 120,
							defaults : {anchor : '100%'},
							items :[{
								xtype : 'textfield',
								readOnly : true,
								name : 'glAccountBankCautionmoney.rawsurplusMoney',
								fieldClass:'field-align',
								fieldLabel : '初始可用金额',								
								value : objSee.data.rawsurplusMoney
							},{
								xtype : 'textfield',
								readOnly : true,
								name : 'glAccountBankCautionmoney.surplusMoney',
								fieldClass:'field-align',
								fieldLabel : '可用金额',								
								value : objSee.data.surplusMoney
							}]},{
								 columnWidth : .06, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth :30,
								items : [{
									fieldLabel : "<span style='margin-left:1px'>万元</span> ",
									labelSeparator : '',
									anchor : "100%"
								
							 },{
									fieldLabel : "<span style='margin-left:1px'>万元</span> ",
									labelSeparator : '',
									anchor : "100%"
								
							 }]},{	
							columnWidth : .52,
							layout : 'form',
							labelWidth : 85,
							defaults : {anchor : '100%'},
							items :[{
								xtype : 'datefield',
								fieldLabel : '开户日期',
								name : 'glAccountBankCautionmoney.createDate',
								format:'Y-m-d',
								readOnly : true,
								value : objSee.data.createDate
							}]},{
							columnWidth : .48,
							layout : 'form',
							labelWidth : 120,
							defaults : {anchor : '100%'},
							items :[{
								xtype : 'textfield',
								fieldLabel : '适用的业务种类',
								name : 'glAccountBankCautionmoney.serviceTypeAccount',
								readOnly : true,
								value : objSee.data.serviceTypeAccount
							}]},{
					   columnWidth : 1,
						layout : 'form',
						labelWidth : 85,
						defaults : {anchor : '100%'},
						items :[{
								xtype : 'textarea',
								fieldLabel : '备&nbsp;&nbsp;注 ',
								readOnly : true,
								cls : 'readOnlyClass',
								value : objSee.data.remark
							}]
						}]/*,
						tbar : [{
							text : '取消',
							iconCls : 'cancelIcon',
							handler : function() {
								seeCautionAccountWin.destroy();
							}
						}]*/
					})
				]
			});
			seeCautionAccountWin.show();
		},
		failure : function(response) {
			Ext.ux.Toast.msg('状态', '操作失败，请重试');
		},
		params : {
			glAccountBankCautionmoneyId : id
		}
	})
}
}
	
});
var getEnterpriseProjectName = function(a,b,c,d){
	Ext.getCmp('projectId_Add').setValue(a);
	Ext.getCmp('enterpriseProjectName_Add').setValue(b);
	Ext.getCmp('bankMarginMoney_Add').setValue(d);
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
	Ext.getCmp('AccountId_update').setValue(obj.id);
	Ext.getCmp('cautionAccountId_update').setValue(obj.bankName+"-"+obj.accountname);
}