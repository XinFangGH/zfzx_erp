/**
 * 词条管理
 * @class SystemWordView
 * @extends Ext.Panel
 */
 
 var quanjuarchivesid;
 var quanjuarchivesOptionid;
PlArchivesView=Ext.extend(Ext.Panel,{
	constructor:function(config){
		Ext.applyIf(this,config);
		this.initUIComponents();
		PlArchivesView.superclass.constructor.call(this,{
			id:'PlArchivesView',
			height:450,
			autoScroll:true,
			layout:'border',
			title:'档案柜管理',
			iconCls:'btn-team54',
			items:[
				this.archivesGrid,
				this.archivesOptionGrid
			]
		});
	},
	initUIComponents:function(){
		
	var start = 0 ;
	var pageSize = 15 ;
	var root = 'result' ;
	var totalProperty = 'totalCounts' ;
	var isShow=false;
	if(RoleType=="control"){
	  isShow=true;
	}
	
	this.jStore_archives = new Ext.data.JsonStore( {
				url : __ctxPath + '/creditFlow/archives/getallcabinetPlArchives.do',
				totalProperty : totalProperty,
				root : root,
				fields : [ {
					name : 'id'
				}, {
					name : 'code'
				}, {
					name : 'name'
				}, {
					name : 'sortorder'
				}, {
					name : 'parentid'
				}, {
					name : 'remarks'
				}, {
					name : 'orgName'
				} ]
			});

			this.jStore_archives.load( {
				params : {
					start : 0,
					limit : 2000,
					archivesname : ''
				}
			});

			var cModel_archives = new Ext.grid.ColumnModel( [
					new Ext.grid.RowNumberer()
					,{
						header : "所属分公司",
						sortable : true,
						width : 120,
						hidden:RoleType=="control"?false:true,
						dataIndex : 'orgName'
					}, {
						header : '柜子ID号',
						width : 50,
						dataIndex : 'id',
						sortable : true
					}, {
						header : '档案柜编号',
						width : 70,
						dataIndex : 'code',
						sortable : true
					}, {
						header : '档案柜名称',
						width : 120,
						dataIndex : 'name',
						sortable : true
					}, {
						header : '排序号',
						width : 50,
						dataIndex : 'sortorder',
						sortable : true
					}, {
						header : '备注',
						dataIndex : 'remarks',
						sortable : true
					} ]);

			var myMask = new Ext.LoadMask(Ext.getBody(), {
				msg : "加载数据中······,请稍后······"
			});
       var tbar=new Ext.Toolbar({
			items:[
				isShow?{
				text:'所属分公司'
				}:{}
			,
			isShow?{
					xtype : "combo",
					hiddenName : "companyId",
					displayField : 'companyName',
					valueField : 'companyId',
					triggerAction : 'all',
					id:'companyIdtype',
					store : new Ext.data.SimpleStore({
						autoLoad : true,
						url : __ctxPath+'/system/getControlNameOrganization.do',
						fields : ['companyId', 'companyName']
					})

			}:{}
			,{
				text:'档案柜名称'
				}
			,{
			xtype : 'textfield',
			id : 'txttype',
			scope : this,
			handler : function() {
			}
			},
			{
			text : '查找',
			tooltip : '查找',
			iconCls : 'btn-detail',
			scope : this,
			handler : function() {
				this.uf_search();
			}
			},
			{
			text : '添加档案柜',
			tooltip : '添加档案柜',
			iconCls : 'btn-add',
			scope : this,
			hidden : isGranted('_cabinet_add')?false:true,
			handler : function() {
				this.uf_addDic();
			}
			},{
			text : '编辑档案柜',
			tooltip : '修改档案柜',
			iconCls : 'btn-edit',
			hidden : isGranted('_cabinet_edit')?false:true,
			scope : this,
			handler : function() {
				this.uf_updateDic();
			}
			},{
			text : '删除档案柜',
			tooltip : '修改档案柜',
			iconCls : 'btn-del',
			hidden : isGranted('_cabinet_del')?false:true,
			scope : this,
			handler : function() {
				this.uf_deleteDic();
			}
			}
			]});
			this.archivesGrid = new Ext.grid.GridPanel( {
				id : 'archivesGrid',
				store : this.jStore_archives,
				region:'west',
				title : '档案柜',
			 	layout:'anchor',
			 	tbar:tbar,
			 	collapsible : true,
				split : true,
				width : 800,
				colModel : cModel_archives,
				selModel : new Ext.grid.RowSelectionModel(),
				stripeRows : true,
				loadMask : myMask,
				// 颜色配置
				viewConfig : {
					forceFit : true,
					getRowClass : function(record, rowIndex, rowParams, store) {
						if (record.data.isOld == '1') {
							return 'x-grid-record-line';
						}
						return '';
					}
				},
			listeners : {
				scope :this,
				'rowclick' : function(){
					var selectionModel = this.archivesGrid.getSelectionModel();
					var record = selectionModel.getSelected();
					var archivesid = record.get("id");
				//	document.getElementById("archivesid").value = archivesid;
					quanjuarchivesid = archivesid;
					this.jStore_option.on('beforeload', function(store, options) {
						var new_params = {
							archivesid : archivesid
						};
						Ext.apply(options.params, new_params);
					});
					this.jStore_option.removeAll();
					this.jStore_option.reload();
				
			      }
			}
			});
   
			
			this.jStore_option = new Ext.data.JsonStore( {
				url : __ctxPath + '/creditFlow/archives/getallcheckerPlArchives.do',
				totalProperty : totalProperty,
				root : root,
				fields : [ {
					name : 'id'
				}, {
					name : 'code'
				}, {
					name : 'name'
				}, {
					name : 'sortorder'
				}, {
					name : 'parentid'
				}, {
					name : 'remarks'
				} ],
				baseParams : {
					archivesid : 0
				}
			});
                this.jStore_option.load( {
				params : {
//					start : 0,
//					limit : 2000
				}
			}); 
//			var cModel_archivesOption = new Ext.grid.ColumnModel( [
//					new Ext.grid.RowNumberer(), {
//						header : '格子ID号',
//						width : 80,
//						dataIndex : 'id',
//						hidden : true,
//						sortable : true
//					}, {
//						header : '格子号',
//						width : 80,
//						dataIndex : 'code',
//						sortable : true
//					}, {
//						header : '备注',
//						dataIndex : 'remarks',
//						sortable : true
//					} ]);

		 var tbar1=new Ext.Toolbar({
			items:[
			{
			text : '添加格子',
			tooltip : '添加格子',
			iconCls : 'btn-add',
			scope : this,
			hidden : isGranted('_checker_add')?false:true,
			handler : function() {
				this.uf_addDictionaryOption();
			}
			},{
			text : '删除格子',
			tooltip : '删除格子',
			iconCls : 'btn-del',
			scope : this,
			hidden : isGranted('_checker_del')?false:true,
			handler : function() {
				this.uf_deleteOrRestoreDictionaryOption();
			}
			}
			]});
					
			this.archivesOptionGrid = new HT.EditorGridPanel({
				id : 'archivesOptionGrid',
				rowActions : false,
				store : this.jStore_option,
				clicksToEdit : 1,
				title : '格子',
				region:'center',
				tbar :tbar1,
		//		colModel : cModel_archivesOption,
		//		selModel : new Ext.grid.RowSelectionModel(),
			//	stripeRows : true,
			//	loadMask : myMask,
				// 颜色配置
				viewConfig : {
					forceFit : true,
					getRowClass : function(record, rowIndex, rowParams, store) {
						if (record.data.isOld == '1') {
							return 'x-grid-record-line';
						}
						return '';
					}
				},
				columns : [{
						header : '格子ID号',
						width : 80,
						dataIndex : 'id',
						hidden : true
					}, {
						header : '格子号',
						width : 80,
						dataIndex : 'code'
					}, {
						header : '备注',
						dataIndex : 'remarks',
						readOnly : isGranted('_checker_edit')?false:true,
						editor :new Ext.form.TextField( {
									readOnly:this.isHidden
						})
					}],
				
				listeners : {
				scope :this,
				'rowclick' : function(){
					var selectionModel = this.archivesOptionGrid.getSelectionModel();
					var record = selectionModel.getSelected();
					quanjuarchivesOptionid = record.get("id");
				
			      },
			      afteredit : function(e) {
					var args;
					var value = e.value;
					var uploadsPanel=this.archivesOptionGrid
					var remarks = e.record.data['remarks'];
					var id = e.record.data['id'];
					if (e.originalValue != e.value) {//编辑行数据发生改变
						if(e.field == 'remarks') {
							args = {'plArchives.remarks': value,'plArchives.id': id};
						}
						Ext.Ajax.request({
							url : __ctxPath + '/creditFlow/archives/save2PlArchives.do',
							method : 'POST',
							scope : this,
							success : function(response, request) {
							//	uploadsPanel.getStore().reload()
									e.record.commit();
							},
							failure : function(response) {
								Ext.Msg.alert('状态', '操作失败，请重试');
							},
							params : args
						})
					}
					}
			}
			});
     


	
	},//end of initUIComponents
	uf_addDic:function() {
		var jStore_archives=this.jStore_archives;
				var formPanel = new Ext.FormPanel(
						{
							labelAlign : 'right',
							buttonAlign : 'center',
							url : __ctxPath + '/creditFlow/archives/savePlArchives.do',
							bodyStyle : 'padding:10px 5px 10px 5px',
							labelWidth : 80,
							frame : true,
							waitMsgTarget : true,
							monitorValid : true,
							items : [ {
								layout : 'column',
								columnWidth : .35,
								xtype : 'textfield',
								fieldLabel : '档案柜编号 ',
								name : 'plArchives.code',
								allowBlank : false,
								blankText : '必填信息',
								maxLength : 100

							}, {
								xtype : 'textfield',
								fieldLabel : '档案柜名称 ',
								name : 'plArchives.name',
								width : 300,
								allowBlank : false,
								blankText : '必填信息',
								maxLength : 22

							}, {
								xtype : 'numberfield',
								fieldLabel : '排序号 ',
								name : 'plArchives.sortorder',
								width : 300,
								allowBlank : false,
								blankText : '必填信息',
								maxLength : 22

							}, {
								xtype : 'textarea',
								name : 'plArchives.remarks',
								fieldLabel : '备注 ',
								width : 300,
								allowBlank : true
							} ],
							buttons : [
									{
										text : '提交',
										iconCls : 'submitIcon',
										formBind : true,
										scope :this,
										handler : function() {
											formPanel
													.getForm()
													.submit(
															{
																method : 'POST',
																waitTitle : '连接',
																waitMsg : '消息发送中...',
																success : function() {
																	Ext.Msg
																			.alert(
																					'状态',
																					'添加成功!',
																					function(
																							btn,
																							text) {
																						if (btn == 'ok') {
																							jStore_archives
																									.reload();
																							adwin
																									.destroy();
																						}
																					});
																},
																failure : function(
																		form,
																		action) {
																	Ext.Msg
																			.alert(
																					'状态',
																					Ext
																							.decode(action.response.responseText).msg);
																}
															})
										}
									}, {

										text : '取消',
										handler : function() {
											adwin.destroy();
										}

									} ]

						})
				var adwin = new Ext.Window( {
					id : 'uwin',
					layout : 'fit',
					title : '添加档案柜信息',
					width : 500,
					height : 250,
					minimizable : true,
					modal : true,
					items : [ formPanel ]
				})
				adwin.show();
			},
					
			uf_deleteDic :function() {
				var jStore_archives=this.jStore_archives;
				var jStore_option=this.jStore_option;
				var archivesid = quanjuarchivesid;
				var deletfuntion = function(btn) {
					if (btn != 'yes')
						return;
					Ext.Ajax.request( {
						url : __ctxPath + '/creditFlow/archives/deletePlArchives.do',
						method : 'POST',
						params : {
							archivesid : archivesid
						},
						success : function(response, request) {
							var msg = response.responseText;
							quanjuarchivesid = 0;
						//	document.getElementById("dictionaryValue").value
							if (msg != "") {
								Ext.Msg.alert("系统提示", "操作成功！");
								jStore_archives.removeAll();
								jStore_archives.reload();
								jStore_option.removeAll();
								jStore_option.reload();

							}

						}
					});
				};
				if (archivesid == "" || archivesid == null || archivesid =="0") {
					Ext.Msg.alert("系统提示", "请选择一条记录!");
					return;
				}
				Ext.MessageBox
						.confirm('系统提示', '你确认要删除你所选择的记录项吗？', deletfuntion);
			},
			uf_updateDic :function () {
				var jStore_archives=this.jStore_archives;
				var jStore_option=this.jStore_option;
																												
				var archivesid = quanjuarchivesid;
				if (archivesid == "" || archivesid == null) {
					Ext.Msg.alert("系统提示", "请选择一条记录!");
					return;
				} else {
					Ext.Ajax
							.request( {
								url : __ctxPath + '/creditFlow/archives/getPlArchives.do',
								method : 'POST',
								success : function(response, request) {
									var obj = Ext.util.JSON
											.decode(response.responseText);

									var update_panel = new Ext.FormPanel(
											{
												labelAlign : 'right',
												buttonAlign : 'center',
												url : __ctxPath + '/creditFlow/archives/savePlArchives.do',
												bodyStyle : 'padding:10px 5px 10px 5px',
												labelWidth : 80,
												frame : true,
												waitMsgTarget : true,
												monitorValid : true,
												items : [
														{
															layout : 'column',
															columnWidth : .35,
															xtype : 'textfield',
															fieldLabel : '档案柜编号 ',
															name : 'plArchives.code',
															allowBlank : false,
															blankText : '必填信息',
															maxLength : 100,
															value : obj.data.code
														},
														{

															xtype : 'hidden',
															name : 'plArchives.id',
															value : obj.data.id
														},
														{
															xtype : 'textfield',
															fieldLabel : '档案柜名称 ',
															name : 'plArchives.name',
															width : 300,
															allowBlank : false,
															blankText : '必填信息',
															maxLength : 22,
															value : obj.data.name

														},
														{
															xtype : 'numberfield',
															fieldLabel : '排序号 ',
															name : 'plArchives.sortorder',
															width : 300,
															allowBlank : false,
															blankText : '必填信息',
															maxLength : 22,
															value : obj.data.sortorder

														},
														{
															xtype : 'textarea',
															name : 'plArchives.remarks',
															fieldLabel : '备注 ',
															width : 300,
															allowBlank : true,
															value : obj.data.remarks
														} ],
												buttons : [
														{
															text : '提交',
															iconCls : 'submitIcon',
															formBind : true,
															handler : function() {
																update_panel
																		.getForm()
																		.submit(
																				{
																					method : 'POST',
																					waitTitle : '连接',
																					waitMsg : '消息发送中...',
																					success : function(
																							form,
																							action) {
																						var msg = Ext
																								.decode(action.response.responseText).msg;
																						if (Ext
																								.decode(action.response.responseText).success) {
																							Ext.Msg
																									.alert(
																											'系统提示',
																											'修改成功!',
																											function(
																													btn,
																													text) {
																												jStore_option
																														.reload();
																												jStore_archives
																														.reload();
																												UTwin
																														.destroy();
																											});
																						}
																					},
																					failure : function(
																							form,
																							action) {
																						var msg = Ext
																								.decode(action.response.responseText).msg;
																						Ext.Msg
																								.alert(
																										'系统提示',
																										msg);
																					}
																				});
															}

														},
														{
															text : '取消',
															handler : function() {
																UTwin.destroy();
															}

														} ]

											});
									var UTwin = new Ext.Window( {
										id : 'win',
										layout : 'fit',
										title : '修改档案柜信息',
										width : 500,
										height : 250,
										minimizable : true,
										modal : true,
										items : [ update_panel ]
									})
									UTwin.show();
								},
								failure : function(response) {
									Ext.Msg.alert('状态', '操作失败，请重试');
								},
								params : {
									archivesid : archivesid
								}
							})
				}

			},
			uf_addDictionaryOption:function() {
				var archivesid = quanjuarchivesid;
				var jStore_archives=this.jStore_archives;
				var jStore_option=this.jStore_option;
				if (archivesid == "" || archivesid == null || archivesid =="0") {
					Ext.Msg.alert("系统提示", "请选择您要添加格子所在的档案柜!");
					return;
				}
				var formPanel = new Ext.FormPanel(
						{
							labelAlign : 'right',
							buttonAlign : 'center',
							url : __ctxPath + '/creditFlow/archives/save1PlArchives.do',
							bodyStyle : 'padding:10px 5px 10px 5px',
							labelWidth : 90,
							frame : true,
							waitMsgTarget : true,
							monitorValid : true,
							items : [ {
								xtype : 'numberfield',
								fieldLabel : '请输入格子数 ',
								name : 'gznum',
								width : 100,
								allowBlank : false,
								blankText : '必填信息',
								maxLength : 22

							}],
							buttons : [
									{
										text : '提交',
										iconCls : 'submitIcon',
										formBind : true,
										handler : function() {
											formPanel
													.getForm()
													.submit(
															{
																method : 'POST',
																waitTitle : '连接',
																waitMsg : '消息发送中...',
																params : {
																	archivesid : archivesid
																},
																success : function() {
																	Ext.Msg
																			.alert(
																					'状态',
																					'添加成功!',
																					function(
																							btn,
																							text) {
																						if (btn == 'ok') {
																							jStore_option
																									.reload();
																							adwin
																									.destroy();
																						}
																					});
																}
															})
										}
									}, {

										text : '取消',
										handler : function() {
											adwin.destroy();
										}

									} ]

						})
				var adwin = new Ext.Window( {
					id : 'uwin',
					layout : 'fit',
					title : '添加格子',
					width : 300,
					height : 150,
					minimizable : true,
					modal : true,
					items : [ formPanel ]
				})
				adwin.show();
			
			
			},	
			uf_deleteOrRestoreDictionaryOption:function() {
        
			 $delGridRs({
							url : __ctxPath
						    + '/creditFlow/archives/multiDelPlArchives.do',
							grid : this.archivesOptionGrid,
							idName : 'id',
							error : '确定要删除？'
						});
			},
			uf_search :function() {
				var type = Ext.getCmp("txttype").getValue();
					var companyIdtype = Ext.getCmp("companyIdtype").getValue();
				lastOptions = this.jStore_archives.lastOptions;
				Ext.apply(lastOptions.params, {
					archivesname : type,
					companyId:companyIdtype
				});
				this.jStore_archives.reload(lastOptions);
			}


	
});
