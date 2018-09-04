//windown
var ReportParamForm = function(reportId,paramId) {
	this.paramId = paramId;
	this.reportId=reportId;
	var window = new Ext.Window({
				id : 'ReportParamFormWin',
				title : name+'详细信息',
				width : 500,
				height : 250,
				modal : true,
				layout : 'form',
				plain : true,
				bodyStyle : 'padding:5px;',
				
				items : [this.MainForm()]
				
			});
	window.show();
};
//form
ReportParamForm.prototype.MainForm = function() {
	var paramTypeStr={};
	var formPanel = new Ext.FormPanel({
				url : __ctxPath + '/system/saveReportParam.do',
				layout : 'form',
				id : 'ReportParamForm',
				frame : true,
				defaults : {
					width : 400,
					anchor : '98%,98%'
				},
				formId : 'ReportParamFormId',
				defaultType : 'textfield',
				buttonAlign : 'center',
				items : [{
							name : 'reportParam.paramId',
							id : 'paramId',
							xtype : 'hidden',
							value : this.paramId == null ? '' : this.paramId
						}, {
							xtype:'hidden',
							name : 'reportParam.reportId',
							value:this.reportId
						},{
							xtype:'hidden',
							name : 'reportParam.paramTypeStr',
							id:'paramTypeStr'
						}, {
							fieldLabel : '参数名称',
							name : 'reportParam.paramName',
							id : 'paramName',
							allowBlank:false
						}, {
							fieldLabel : '参数Key',
							name : 'reportParam.paramKey',
							id : 'paramKey',
							allowBlank:false
						}, {
							fieldLabel : '类型',
							hiddenName : 'reportParam.paramType',
							id : 'paramType',
							xtype:'combo',
							mode : 'local',
							allowBlank:false,
							editable : false,
							triggerAction : 'all',
							store : [['textfield', '文件输入框(textfield)'],
							         ['datefield', '日期输入框(datefield)'],
							         ['datetimefield', '时间输入框(datetimefield)'],
							         ['numberfield', '数字输入框(numberfield)'],
							         ['combo', '下拉框(combo)'],
							         ['diccombo', '数据字典(diccombo)']],
							value:'textfield',
							listeners : {
								select : function(combo, record, index) {
									var xtype = combo.value;
									var mainForm=Ext.getCmp('ReportParamForm');
									mainForm.remove('defaultVal');
									switch(xtype){
									case 'datefield'://日期

										 paramTypeStr={};
										 Ext.apply(paramTypeStr,{
										        fieldLabel : '缺省值',
												name : 'reportParam.defaultVal',
												id : 'defaultVal',
												xtype:'datefield',
								                format: 'Y-m-d',
												allowBlank:true
										   });
										 mainForm.findById('paramTypeStr').setValue(Ext.encode(paramTypeStr));
										 mainForm.add(paramTypeStr);
										break;
										case 'datetimefield'://时间

											paramTypeStr={};
											Ext.apply(paramTypeStr,{
											        fieldLabel : '缺省值',
													name : 'reportParam.defaultVal',
													id : 'defaultVal',
													xtype:'datetimefield',
									                format: 'Y-m-d H:i:s',
													allowBlank:true
										
											 });
											 mainForm.findById('paramTypeStr').setValue(Ext.encode(paramTypeStr));
											 mainForm.add(paramTypeStr);
											break;
										case 'numberfield'://数字

											paramTypeStr={};
											Ext.apply(paramTypeStr,{
											        fieldLabel : '缺省值',
													name : 'reportParam.defaultVal',
													id : 'defaultVal',
													xtype:'numberfield',
													allowBlank:true
											    });
											 mainForm.findById('paramTypeStr').setValue(Ext.encode(paramTypeStr));
											 mainForm.add(paramTypeStr);
											break;
										case 'combo'://下拉
											var comboContainer = new Ext.Panel( {
												id:'ReportParamForm.comboContainer',
												layout : 'form',
												buttonAlign : 'center',
												buttons : [{
													text : '确定',
													iconCls : 'btn-save',
													handler : function() {
													var combo_x=Ext.getCmp('ReportParamForm.comboContainer'). findById("comboStore");
													if(!combo_x){	
														Ext.getCmp('ReportParamFormWin').remove('ReportParamForm.comboContainer');
														mainForm.show();
														return;
													}
													
													var combo_type=combo_x.getXType();
													if(combo_type=='editorgrid'){//静态数据源
														var _comboData=[];
														var _store = combo_x.getStore();
														_store.each(function(r) {
															_comboData.push([r.data['value'],r.data['text']]);
														})

														paramTypeStr={};
														Ext.apply(paramTypeStr,{
															xtype:'combo',
															fieldLabel : '缺省值',
															store : 
																	{
																		xtype: 'arraystore' ,
																		fields : ['value','text'],                             
																		data : _comboData
																	},
															valueField : 'value',
															displayField : 'text',
															typeAhead : true,
															mode : 'local',
															triggerAction : 'all',
															forceSelection : true,
															hiddenName : 'reportParam.defaultVal',
															id : 'defaultVal'
														});
														//组件序列化
														mainForm.findById('paramTypeStr').setValue(Ext.encode(paramTypeStr));
														//加入组件
														mainForm.add(new  Ext.form.ComboBox(paramTypeStr));
														
														
													}else if(combo_type=='form'){//动态数据源
														var url=combo_x.findById('url').getValue();
														var root=combo_x.findById('root').getValue();
														
														var value=combo_x.findById('value').getValue();
														var text=combo_x.findById('text').getValue();
														
														var _comboFiels=[value,text];
														

														paramTypeStr={};
														Ext.apply(paramTypeStr,{
															xtype:'combo',
															fieldLabel : '缺省值',
															store : 
															{
																xtype:'arraystore',
																autoLoad : true,
														//		root:root,
																url : __ctxPath+ url,
																fields : _comboFiels
															},
															valueField : value,
															displayField : text,
															typeAhead : true,
															mode : 'local',
															triggerAction : 'all',
															forceSelection : true,
															hiddenName : 'reportParam.defaultVal',
															id : 'defaultVal'                                          
														});
														mainForm.findById('paramTypeStr').setValue(Ext.encode(paramTypeStr));
														mainForm.add(new  Ext.form.ComboBox(paramTypeStr));
													}
													Ext.getCmp('ReportParamFormWin').remove('ReportParamForm.comboContainer');
													mainForm.show();
												}
												}, {
													text : '取消',
													iconCls : 'btn-cancel',
													handler : function() {
													Ext.getCmp('ReportParamFormWin').remove('ReportParamForm.comboContainer');
													mainForm.show();
												}
												}]
											});
											//选择数据源
											var comboSelectForm = new Ext.FormPanel({
												layout : 'form',
												frame : true,
												defaults : {
													anchor : '98%,98%'
												},
												modal : true,
												defaultType : 'textfield',
												items:[{
													fieldLabel : '数据来源',
													xtype:'combo',
													store : new Ext.data.ArrayStore(
															{
																fields : ['value','text'],                             
																data : [['ArrayStore','静态数据(ArrayStore)'],['JsonStore','动态数据(JsonStore)']]
															}),
													valueField : 'value',
													displayField : 'text',
													typeAhead : true,
													mode : 'local',
													triggerAction : 'all',
													forceSelection : true,
													emptyText : '--数据来源--',
													listeners : {
														select : function(combo, record, index) {
															Ext.getCmp('ReportParamForm.comboContainer').remove('comboStore');
															var value = combo.value;
															if(value=='ArrayStore'){//静态数据源
																var comboData=[
																	['',''],
																	['','']
																];
																 var comboStore = new Ext.data.ArrayStore({
															        fields: [
															           {name: 'value'},
															           {name: 'text'}
															          ]
															    });
													
															    comboStore.loadData(comboData);
															    var sm=new Ext.grid.CheckboxSelectionModel();
 																//填入静态数据
																var ArrayStorePanel=new Ext.grid.EditorGridPanel({
																	id:'comboStore',
																	autoScroll : true,
																	stripeRows : true,
																	height : 120,
																	tbar : new Ext.Toolbar( {
																		height : 25,
																		bodyStyle : 'text-align:left',
																		items : [{
																			iconCls : 'btn-add',
																			text : '添加',
																			handler : function() {
																				var _gridPanel = Ext.getCmp('comboStore');
																				var _store = _gridPanel.getStore();                                                                                                                       // //
																				var _cm = _gridPanel.getColumnModel();
																				var recrod = new _store.recordType();
																				recrod.data = {};
																				
																				var keys = _store.fields.keys;
																				for ( var j = 0; j < keys.length; j++) {
																					recrod.data[keys[j]] = '';
																				}
																				recrod.markDirty();
																				_gridPanel.stopEditing();
																				_store.insert(0,recrod);
																						
																			}
												
																		},{
																			iconCls : 'btn-del',
																			text : '删除',
																			handler : function() {
																				var gridPanel = Ext.getCmp('comboStore');
																				gridPanel.stopEditing();
																				var selectRecords = gridPanel.getSelectionModel().getSelections();
																				if (selectRecords.length == 0) {
																					Ext.ux.Toast.msg("信息","请选择要删除的记录！");
																					return;
																				}
	
																				for ( var i = 0; i < selectRecords.length; i++) {
																					gridPanel.getStore().remove(selectRecords[i]);
																				}
																			}
																		}]
																	}),
																	store : comboStore,
																	trackMouseOver : true,
																	disableSelection : false,
																	loadMask : true,
																	clicksToEdit : 1,
																	cm : new Ext.grid.ColumnModel(
																	{
																		columns : [sm,{
																		header : '值域',
																		dataIndex : 'value',
																		editor : new Ext.form.TextField( {
																					allowBlank : false
																		})},
																		{
																		header : '显示域',
																		dataIndex : 'text',
																		editor : new Ext.form.TextField( {
																					allowBlank : false
																		})}],
																		defaults : {
																			sortable : true,
																			menuDisabled : false
																		}
																	}),
																	sm : sm,
																	viewConfig : {
																		forceFit : true,
																		autoFill : true
																	}
																}).show();
																comboContainer.add(ArrayStorePanel);
																comboContainer.doLayout();
															}else if(value=='JsonStore'){//动态数据源
																var JsonStorePanel= new Ext.FormPanel({
																	id:'comboStore',
																	layout : 'form',
																	frame : true,
																	defaults : {
																		anchor : '98%,98%'
																	},
																	modal : true,
																	defaultType : 'textfield',
																	items:[{
																        fieldLabel : '请求URL',
																		id : 'url',
																		xtype:'textfield',
																		allowBlank:false
																   },{
																        fieldLabel : 'root',
																		id : 'root',
																		xtype:'textfield',
																		allowBlank:false
																   },{
																        fieldLabel : '值域字段',
																		id : 'value',
																		xtype:'textfield',
																		allowBlank:false
																   },{
																        fieldLabel : '显示域字段',
																		id : 'text',
																		xtype:'textfield',
																		allowBlank:false
																   }]
																}).show();
																comboContainer.add(JsonStorePanel);
																comboContainer.doLayout();
															}
															
															
														}
													
													}
												}
												]
												
											});

																							
											comboContainer.add(comboSelectForm);
											comboContainer.doLayout();
											Ext.getCmp('ReportParamFormWin').add(comboContainer);
											mainForm.hide();
											Ext.getCmp('ReportParamFormWin').doLayout();
											break;
										case 'diccombo'://数据字典
											
											var diccomboForm = new Ext.FormPanel({
												layout : 'form',
												id:'ReportParamForm.prototype.diccomboForm',
												frame : true,
												defaults : {
													width : 400,
													anchor : '98%,98%'
												},
												modal : true,
												defaultType : 'textfield',
												buttonAlign : 'center',
												items : [{
													fieldLabel : '数据字典名称(itemName)',
													id : 'itemName',
													width:200,
													xtype:'textfield',
													allowBlank:false
											    }]
												,buttons : [{
													text : '确定',
													iconCls : 'btn-save',
													handler : function() {
													var itemName=Ext.getCmp('itemName').getValue();

													paramTypeStr={};
													Ext.apply(paramTypeStr,{
														xtype:'diccombo',
														fieldLabel : '缺省值',
														name : 'reportParam.defaultVal',
														id : 'defaultVal',
														mode : 'local',
														itemName : itemName,
														displayField : 'itemValue',
														valueField : 'itemValue'
													
													});
													mainForm.findById('paramTypeStr').setValue(Ext.encode(paramTypeStr));
													mainForm.add(new  DicCombo(paramTypeStr)); 
													Ext.getCmp('ReportParamFormWin').remove('ReportParamForm.prototype.diccomboForm');
													mainForm.show();
													
												}
												}, {
													text : '取消',
													iconCls : 'btn-cancel',
													handler : function() {
													Ext.getCmp('ReportParamFormWin').remove('ReportParamForm.prototype.diccomboForm');
													mainForm.show();
												}
												}]
												
											}).show();	
											Ext.getCmp('ReportParamFormWin').add(diccomboForm);
											mainForm.hide() ;
											Ext.getCmp('ReportParamFormWin').doLayout();
											break;
										default://文件

											paramTypeStr={};
												Ext.apply(paramTypeStr,{
										        fieldLabel : '缺省值',
												name : 'reportParam.defaultVal',
												id : 'defaultVal',
												xtype:'textfield',
												allowBlank:true
											});
											mainForm.findById('paramTypeStr').setValue(Ext.encode(paramTypeStr));
											mainForm.add(paramTypeStr);
										 
										
									}
									
									mainForm.doLayout(true);
								}
							}
						}, {
							fieldLabel : '系列号',
							name : 'reportParam.sn',
							id : 'sn',
							xtype:'numberfield',
							allowBlank:false
						},{
					        fieldLabel : '缺省值',
							name : 'reportParam.defaultVal',
							id : 'defaultVal',
							allowBlank:true
						 }

				],buttons : [{
					text : '保存',
					iconCls : 'btn-save',
					handler : function() {
						var mainForm = Ext.getCmp('ReportParamForm');
					
						if (mainForm.getForm().isValid()) {
							
							var paramTypeStr=mainForm.findById('paramTypeStr').getValue();
							if(paramTypeStr==''){
								paramTypeStr={};
								Ext.apply(paramTypeStr,{
							        fieldLabel : '缺省值',
									name : 'reportParam.defaultVal',
									id : 'defaultVal',
									xtype:'textfield',
									allowBlank:true
								});
								mainForm.findById('paramTypeStr').setValue(Ext.encode(paramTypeStr));
							}
							mainForm.getForm().submit({
								method : 'post',
								waitMsg : '正在提交数据...',
								success : function(mainForm, action) {
									Ext.ux.Toast.msg('操作信息', '成功保存信息！');
									Ext.getCmp('ReportParamGrid').getStore()
											.reload();
									Ext.getCmp('ReportParamFormWin').close();
								},
								failure : function(mainForm, action) {
									Ext.MessageBox.show({
												title : '操作信息',
												msg : '信息保存出错，请联系管理员！',
												buttons : Ext.MessageBox.OK,
												icon : Ext.MessageBox.ERROR
											});
									Ext.getCmp('ReportParamFormWin').close();
								}
							});
						}
					}
				}, {
					text : '取消',
					iconCls : 'btn-cancel',
					handler : function() {
						
					Ext.getCmp('ReportParamFormWin').close();
						
					}
				}]
			});

	
	if (this.paramId != null && this.paramId != 'undefined') {
		var paramId = this.paramId;
		formPanel.on('afterrender',function(formPanel){
			formPanel.getForm().load({
				deferredRender : false,
				url : __ctxPath + '/system/getReportParam.do?paramId='
						+ paramId,
				waitMsg : '正在载入数据...',
				success : function(form, action) {
					var xtype = action.result.data.paramType;
					var defaultVal = action.result.data.defaultVal;
					var paramTypeStr=action.result.data.paramTypeStr;
					
					var mainForm=Ext.getCmp('ReportParamForm');
							if(paramTypeStr!==null&&paramTypeStr!=''&&paramTypeStr!=undefined){
										mainForm.remove('defaultVal');
										switch(xtype){
											case 'combo':
												var combo=new  Ext.form.ComboBox(Ext.decode( paramTypeStr));
												combo.setValue(defaultVal);
												mainForm.add(combo);
												break;
											case 'diccombo':
												var diccombo=new  DicCombo(Ext.decode( paramTypeStr));
												diccombo.setValue(defaultVal);
												mainForm.add(diccombo); 
												break;
											default:
												var df=Ext.decode( paramTypeStr);
												Ext.apply(df,{
													value:defaultVal
												});
												
												mainForm.add(df);
										}
										mainForm.doLayout(true);
							}
										
				},
				failure : function(form, action) {
					// Ext.Msg.alert('编辑', '载入失败');
				}
			});
		});
		
		
	}
	return formPanel;

};



