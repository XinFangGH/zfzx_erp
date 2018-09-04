RelationPersonView = Ext.extend(Ext.Panel, {
	pId : null,
	personIdValue :null,
	isFlow:false,
	callExamine : true,
	isShowContent : true,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (typeof(_cfg.projectId) != "undefined") {
			this.pId = _cfg.projectId;
		}
		if (typeof(_cfg.isHiddenAddBtn) != "undefined") {
			this.isHiddenAddBtn = _cfg.isHiddenAddBtn;
		}
		if (typeof(_cfg.isHiddenDelBtn) != "undefined") {
			this.isHiddenDelBtn = _cfg.isHiddenDelBtn;
		}
		if (typeof(_cfg.isHiddenEdiBtn) != "undefined") {
			this.isHiddenEdiBtn = _cfg.isHiddenEdiBtn;
		}
		if (typeof(_cfg.isHiddenSeeBtn) != "undefined") {
			this.isHiddenSeeBtn = _cfg.isHiddenSeeBtn;
		}
		if (typeof(_cfg.personIdValue) != "undefined") {
			this.personIdValue = _cfg.personIdValue;
		}
		if (typeof(_cfg.isFlow) != "undefined") {
			this.isFlow = _cfg.isFlow;
		}
		if (typeof(_cfg.callExamine) != "undefined") {
			this.callExamine = _cfg.callExamine;
		}
		if (typeof(_cfg.isShowContent) != "undefined") {
			this.isShowContent = _cfg.isShowContent;
		}
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		RelationPersonView.superclass.constructor.call(this, {
					// layout:'fit',
					id:'RelationPersonView'+this.flag,
					layout : 'anchor',
					anchor : '100%',
					autoHeight : true,
					items : [this.gridPanel]
				});
	},
	initUIComponents : function() {
		
		this.topbar = new Ext.Toolbar({
			items : [{
						iconCls : 'btn-add',
						text : '增加',
						xtype : 'button',
						hidden : this.isHiddenAddBtn,
						// hidden : isGranted('_create_qykh') ? false : true,
						scope : this,
						handler :this.addRelationPerson
					}, {
						iconCls : 'btn-del',
						text : '删除',
						xtype : 'button',
						hidden : this.isHiddenDelBtn,
						scope : this,
						handler : function() {
							var gridPanel = this.gridPanel;
							var selected = this.gridPanel.getSelectionModel()
									.getSelected();
							if (null == selected) {
								Ext.ux.Toast.msg('状态', '请选择一条记录!');
							} else {
								var id = selected.get('id');

								Ext.Ajax.request({
									url : __ctxPath+ '/creditFlow/customer/person/deleteRelationByIdPerson.do',
									method : 'POST',
									success : function() {

										Ext.ux.Toast.msg('状态', '删除成功!');
										gridPanel.getStore().reload();
									},
									failure : function(result, action) {
										Ext.ux.Toast.msg('状态', '删除失败!');
									},
									params : {
										id : id
									}
								});
							}

						}
					}, {
						iconCls : 'btn-edit',
						text : this.isHiddenEdiBtn ? (this.callExamine ? '编辑' : '电核') : '编辑',
						xtype : 'button',
						// hidden : isGranted('_edit_qykh') ? false : true,
						scope : this,
						hidden : this.isHiddenEdiBtn ? (this.callExamine ? true : false) : false,
						handler : this.editRelationPerson
					}, {
						iconCls : 'btn-detail',
						text : '查看',
						xtype : 'button',
						scope : this,
						hidden : this.isHiddenSeeBtn,
						// hidden : isGranted('_detail_qykh') ? false : true,
						handler : this.seeRelationPerson
					}, {
						iconCls : 'btn-detail',
						text : '呼出电话',
						xtype : 'button',
						scope : this,
						hidden : this.callExamine,
						handler : this.telephone
					}]
		});

		this.jStore_PanelReliation = new Ext.data.JsonStore({
			url : __ctxPath+ '/creditFlow/customer/person/getListBypersonIdPerson.do',
			totalProperty : 'totalProperty',
			root : 'topics',
			fields : [{
						name : 'id'
					}, {
						name : 'relationName'
					}, {
						name : 'relationShip'
					}, {
						name : 'relationPhone'
					}, {
						name : 'relationCellPhone'
					}, {
						name : 'relationShipValue'
					}, {
						name : 'personId'
					},{
						name : 'relationProfession'
					},{
						name: 'relationAddress'
					}, {
						name : 'relationCompanyPhone'
					},{
						name:'relationJobCompany'
					},{
						name:'relationJobAddress'
					},{
						name:'flag'
					},{
						name:'content'
					}]
		});
		this.jStore_PanelReliation.load({
					params : {
						start : 0,
						limit : 20,
						personId : this.personIdValue,
						flag:this.flag
					}
				});
		this.cModel_PanelReliation = new Ext.grid.ColumnModel([
				new Ext.grid.RowNumberer({
							header : '序号',
							width : 30
							//dataIndex : 'id'
						})
				, {
					header : "姓名",
					width : 80,
					//sortable : true,
					dataIndex : 'relationName'
				}, {
					header : "关系",
					width : 80,
					//sortable : true,
					dataIndex : 'relationShipValue'
				}, {
					header : "电话",
					width : 100,
					//sortable : true,
					dataIndex : 'relationPhone'
				}, {
					id : 'id_autoExpandColumn',
					header : "手机",
					width : 100,
					//sortable : true,
					dataIndex : 'relationCellPhone'
				},{
					header : "职业",
					width : 80,
					sortable : true,
				    dataIndex : 'relationProfession'
				},{
					header : "住址",
					width : 100,
					sortable : true,
				    dataIndex : 'relationAddress'
				},{
					header : "单位电话",
					width : 100,
					sortable : true,
				    dataIndex : 'relationCompanyPhone'
				},{
					header : "工作单位",
					width : 180,
					//sortable : true,
				    dataIndex : 'relationJobCompany'
				},{
					header : "单位地址",
					
					width : 180,
					//sortable : true,
				    dataIndex : 'relationJobAddress'
				},{
					header : "电核内容",
					width : 180,
				    dataIndex : 'content',
				    hidden : this.isShowContent
				}
				]);
		/*this.check = new Ext.grid.CheckboxSelectionModel({
			header : '序号',
			dataIndex : 'id'
		});*/
				
		this.gridPanel = new Ext.grid.GridPanel({
					id : 'RelationPersonGrid',
					// name : 'RelationPersonGrid',
					region : 'center',
					tbar : this.topbar,
					layout : 'fit',
					store : this.jStore_PanelReliation,
					// showPaging:'',
					//rowActions : true,// 不启用RowActions
					// loadMask : true,
					stripeRows : true,
					autoExpandColumn:'Position',
					//autoExpandColumn:true,
					//autoWidth:true,
					autoHeight : true,
					 viewConfig: {
       					 forceFit: true
					 },
					colModel : this.cModel_PanelReliation,
					sm: new Ext.grid.RowSelectionModel({singleSelect:true}),

					//sm : this.check,
					//selModel:  new Ext.grid.CheckboxSelectionModel({singleSelect:true}),
					listeners : {
						'rowdblclick' : function(grid, index, e) {
							var selected = grid.getStore().getAt(index);
							callbackFun(selected, funName);
							winReliationListRefer.destroy();
						}
					}
				});
	},
	addRelationPerson : function() {
		var gridPanel = this.gridPanel;
		var addRelationPersonMessageWin = new Ext.Window({
			id : 'addRelationPersonMessageWin',
			title : '新增关系人信息',
			layout : 'fit',
			width : 680,
			height : 150,
			closable : true,
			resizable : false,
			plain : true,
			collapsible : true,
			border : false,
			modal : true,
			buttonAlign : 'center',
			bodyStyle : 'padding: 0',
			items : [new Ext.form.FormPanel({
				id : 'fPanel_addRelationPerson',
				url : __ctxPath+ '/creditFlow/customer/person/addPerson.do',
				labelAlign : 'right',
				labelWidth : 70,
				frame : true,
				layout : 'column',
				buttonAlign : 'center',
				
				// height : 300,
				monitorValid : true,
				defaults : {
					layout : 'form',
					border : false,
					columnWidth : .5
				},
				items : [{
					columnWidth:.33,
					layout : 'form',
					defaults : {
						anchor : '100%'
					},
					items : [{
								xtype : 'textfield',
								fieldLabel : '<font color="red">*</font>姓名',
								name : 'personRelation.relationName',
								allowBlank : false,
								width : 120
							}, this.relationPersonType==576?{
								xtype :"dickeycombo",
								nodeKey : 'gxrgx',
								fieldLabel : '<font color="red">*</font>关系',
								hiddenName : 'personRelation.relationShip',
								width : 120,
								editable : false,
								allowBlank : false,
								listeners : {
									afterrender : function(combox) {
										var st = combox.getStore();
										st.on("load", function() {
													if (combox.getValue() == 0
															|| combox
																	.getValue() == 1) {
														combox.setValue("");
													} else {
														combox.setValue(combox
																.getValue());
													}
													combox.clearInvalid();
												})
									}
								}
							}:{
								xtype :"textfield",
								fieldLabel : '<font color="red">*</font>关系',
								width : 120,
								readOnly:true,
								allowBlank : false,
								listeners : {
									scope:this,
									render : function(v) {
										if(this.relationPersonType==575){
											return v.value='亲属';
										}else{
											return v.value='同事';
										}
									}
								}
							},this.relationPersonType==576?{}:{
								xtype:'hidden',
								name:'personRelation.relationShip',
								value:this.relationPersonType==575?575:577
							},{
								xtype:'hidden',
								name:'personRelation.flag',
								value:this.flag
							},{
								xtype : 'textfield',
								fieldLabel : '职业',
								name : 'personRelation.relationProfession',
								allowBlank : false,
								width : 120
							}]
				}, {
					columnWidth:.33,
					layout : 'form',
					defaults : {
						anchor : '100%'
					},
					items : [{
						xtype : 'textfield',
						fieldLabel : '固定电话',
						name : 'personRelation.relationPhone',
						// allowBlank : false,
						width : 120
							// regex : /^(\d{3,4})-(\d{7,8})|^1[358]\d{9}$/,
							// regexText : '电话号码格式不正确或者无效的号码'
						}, {
						xtype : 'numberfield',
						fieldLabel : '手机',
						name : 'personRelation.relationCellPhone',
						allowBlank : false,
						width : 120,
						regex : /^[1][0-9][0-9]{9}$/,
						regexText : '电话号码格式不正确或者无效的号码'
						},{
							xtype : 'textfield',
							fieldLabel : '住址',
							name : 'personRelation.relationAddress',
							allowBlank : false,
							width : 120
						}, {
						xtype : 'hidden',
						fieldLabel : '人员主键',
						name : 'personRelation.personId',
						// allowBlank : false,
						width : 120,
						value : this.personIdValue
					}]
				},{
					columnWidth:.33,
					layout : 'form',
					defaults : {
						anchor : '100%'
					},
					items : [{
						xtype : 'textfield',
						fieldLabel : '单位电话',
						allowBlank : false,
						name : 'personRelation.relationCompanyPhone',
						//allowBlank : false,
						width : 120
					},  {
						xtype : 'textfield',
						fieldLabel : '工作单位',
						name : 'personRelation.relationJobCompany',
						width : 120,
						value : obj.relationJobCompany
					},{
						xtype : 'textfield',
						fieldLabel : '单位地址',
						name : 'personRelation.relationJobAddress',
						width : 120,
						value : obj.relationJobAddress
					}]
				}]
					// items
			})],
			buttons : [{
				text : '提交',
				iconCls : 'submitIcon',
				formBind : true,
				scope:this,
				handler : function() {
					Ext.getCmp('fPanel_addRelationPerson').getForm().submit({
						method : 'POST',
						waitTitle : '连接',
						waitMsg : '消息发送中...',
						success : function() {
							Ext.ux.Toast.msg('状态', '添加成功!');
							gridPanel.getStore().reload();
							addRelationPersonMessageWin.destroy();
						},
						failure : function(form, action) {
							Ext.ux.Toast.msg('状态', '添加关系人信息失败!');
							// top.getForm().reset();
						}
					});
				}
			}, {
				text : '取消',
				iconCls : 'cancelIcon',
				handler : function() {
					addRelationPersonMessageWin.destroy();
				}
			}]
		}).show();

	},
	editRelationPerson : function(id) {
		var isShow = this.isHiddenEdiBtn ? (this.callExamine ? false : true) : false
		var gridPanel = this.gridPanel;
		var selected = this.gridPanel.getSelectionModel().getSelected();
		if (null == selected) {
			Ext.ux.Toast.msg('状态', '请选择一条记录!');
		} else {
			var id = selected.get('id');
			Ext.Ajax.request({
				url : __ctxPath+ '/creditFlow/customer/person/seeRelationPerson.do',
				method : 'POST',
				success : function(response, request) {
					objMes = Ext.util.JSON.decode(response.responseText);
					obj = objMes.data;
					var win_updateRelationPerson = new Ext.Window({
						id : 'win_updateRelationPerson',
						title : isShow ?  '增加电核内容' : '修改关系人信息',
						layout : 'fit',
						width : 680,
						height : 150,
						constrainHeader : true,
						closable : true,
						resizable : false,
						plain : true,
						border : false,
						modal : true,
						buttonAlign : 'center',
						bodyStyle : 'padding: 0',
						items : [new Ext.form.FormPanel({
							id : 'fPanel_updateRelationPerson',
							url : __ctxPath+ '/creditFlow/customer/person/updateRelationPerson.do',
							labelAlign : 'right',
							frame : true,
							layout : 'column',
							buttonAlign : 'center',
							labelWidth:70,
							height : 300,
							monitorValid : true,
							defaults : {
								layout : 'form',
								border : false,
								columnWidth : .5
							},
							items : [{
								columnWidth:.33,
								layout : 'form',
								hidden : isShow,
								defaults : {
									anchor : '100%'
								},
								items : [{
									xtype : 'textfield',
									fieldLabel : '<font color="red">*</font>姓名',
									name : 'personRelation.relationName',
									// allowBlank : false,
									width : 120,
									value : obj.relationName
								},this.relationPersonType==576?{
									xtype :"dickeycombo",
									nodeKey : 'gxrgx',
									fieldLabel : '<font color="red">*</font>关系',
									hiddenName : 'personRelation.relationShip',
									width : 120,
									editable : false,
									allowBlank : false,
									listeners : {
										afterrender : function(combox) {
											var st = combox.getStore();
											st.on("load", function() {
														if (combox.getValue() == 0
																|| combox
																		.getValue() == 1) {
															combox.setValue("");
														} else {
															combox.setValue(combox
																	.getValue());
														}
														combox.clearInvalid();
													})
										}
									}
								}:{
									xtype :"textfield",
									fieldLabel : '<font color="red">*</font>关系',
									width : 120,
									readOnly:true,
									allowBlank : false,
									listeners : {
										scope:this,
										render : function(v) {
											if(this.relationPersonType==575){
												return v.value='亲属';
											}else{
												return v.value='同事';
											}
										}
									}
								},this.relationPersonType==576?{}:{
									xtype:'hidden',
									name:'personRelation.relationShip',
									value:this.relationPersonType==575?575:577
								},{
									xtype:'hidden',
									name:'personRelation.flag',
									value:obj.flag
								},{
									xtype : 'textfield',
									fieldLabel : '职业',
									name : 'personRelation.relationProfession',
									allowBlank : false,
									width : 120,
									value:obj.relationProfession
								}]
							}, {
								columnWidth:.33,
								layout : 'form',
								hidden : isShow,
								defaults : {
									anchor : '100%'
								},
								items : [{
									xtype : 'textfield',
									fieldLabel : '固定电话',
									name : 'personRelation.relationPhone',
									// allowBlank : false,
									width : 120,
									value : obj.relationPhone
										// regex :
										// /^(\d{3,4})-(\d{7,8})|^1[358]\d{9}$/,
										// regexText : '电话号码格式不正确或者无效的号码'
									}, {
									xtype : 'textfield',
									fieldLabel : '手机',
									name : 'personRelation.relationCellPhone',
									allowBlank : false,
									width : 120,
									value : obj.relationCellPhone
										// regex : /^[1][0-9][0-9]{9}$/,
										// regexText : '电话号码格式不正确或者无效的号码'
									},{
										xtype : 'textfield',
										fieldLabel : '住址',
										name : 'personRelation.relationAddress',
										allowBlank : false,
										width : 120,
										value:obj.relationAddress
									}, {
									xtype : 'hidden',
									fieldLabel : '人员主键',
									name : 'personRelation.personId',
									value : obj.personId
								}, {
									xtype : 'hidden',
									fieldLabel : '主键',
									name : 'personRelation.id',
									value : obj.id
								}]
							},{
								columnWidth:.33,
								layout : 'form',
								hidden : isShow,
								defaults : {
									anchor : '100%'
								},
								items : [ {
									xtype : 'textfield',
									fieldLabel : '单位电话',
									allowBlank : false,
									name : 'personRelation.relationCompanyPhone',
									value:obj.relationCompanyPhone,
									//allowBlank : false,
									width : 120
								},{
									xtype : 'textfield',
									fieldLabel : '工作单位',
									name : 'personRelation.relationJobCompany',
									width : 120,
									value : obj.relationJobCompany
								},{
									xtype : 'textfield',
									fieldLabel : '单位地址',
									name : 'personRelation.relationJobAddress',
									width : 120,
									value : obj.relationJobAddress
								}]
							},{
								columnWidth:0.99,
								layout : 'form',
								hidden : !isShow,
								defaults : {
									anchor : '100%'
								},
								items : [{
									fieldLabel : '电核内容',
									name : 'personRelation.content',
									xtype:"textarea",
									value : obj.content,
									maxLength : 100
								}]
							}]
								// items
						})],
						buttons : [{
							text : '保存',
							iconCls : 'submitIcon',
							formBind : true,
							handler : function() {
								Ext.getCmp('fPanel_updateRelationPerson')
										.getForm().submit({
											method : 'POST',
											waitTitle : '连接',
											waitMsg : '消息发送中...',
											success : function() {
												Ext.ux.Toast.msg('状态', '保存成功!');
												// if (btn == 'ok') {
												win_updateRelationPerson
														.destroy();
												gridPanel.getStore().reload();
												// }
											},
											failure : function(form, action) {
												Ext.ux.Toast.msg('状态',
														'添加关系人信息失败!');
												// top.getForm().reset();
											}
										});
							}
						}]
					}).show();
				},
				failure : function(response) {
					Ext.ux.Toast.msg('状态', '操作失败，请重试');
				},
				params : {
					id : id
				}
			});
		}

	},
	
	seeRelationPerson : function(){
		var selected = this.gridPanel.getSelectionModel().getSelected();
		if (null == selected) {
			Ext.ux.Toast.msg('状态', '请选择一条记录!');
		}else{
			var id = selected.get('id');
			Ext.Ajax.request({
				url : __ctxPath+ '/creditFlow/customer/person/seeRelationPerson.do',
				method : 'POST',
				success : function(response, request) {
					objMes = Ext.util.JSON.decode(response.responseText);
					obj = objMes.data;
					var win_seeRelationPerson = new Ext.Window({
						id : 'win_seeRelationPerson',
						title : '查看关系人信息',
						layout : 'fit',
						width : 680,
						height : 220,
						constrainHeader : true,
						closable : true,
						resizable : false,
						plain : true,
						border : false,
						modal : true,
						buttonAlign : 'center',
						bodyStyle : 'padding: 0',
						items : [new Ext.form.FormPanel({
							id : 'fPanel_seeRelationPerson',
							/*url : __ctxPath
									+ '/credit/customer/person/ajaxUpdateRelationPersonMessage.do',*/
							labelAlign : 'right',
							frame : true,
							layout : 'column',
							buttonAlign : 'center',
							height : 300,
							monitorValid : true,
							defaults : {
								layout : 'form',
								border : false,
								columnWidth : .5
							},
							items : [{
								columnWidth:.33,
								layout : 'form',
								defaults : {
									anchor : '100%'
								},
								items : [{
									xtype : 'textfield',
									fieldLabel : '<font color="red">*</font>姓名',
									name : 'personRelation.relationName',
									// allowBlank : false,
									readOnly  : true,
									width : 120,
									value : obj.relationName
								}, {
									xtype : "dickeycombo",
									nodeKey : 'gxrgx',
									fieldLabel : '<font color="red">*</font>关系',
									hiddenName : 'personRelation.relationShip',
									width : 120,
									editable : false,
									value : obj.relationShip,
									readOnly  : true,
									listeners : {
										afterrender : function(combox) {
											var st = combox.getStore();
											st.on("load", function() {
												if (combox.getValue() == 0
														|| combox.getValue() == 1) {
													combox.setValue("");
												} else {
													combox.setValue(combox
															.getValue());
												}
												combox.clearInvalid();
											})
										}
									}
								},{
									xtype : 'textfield',
									fieldLabel : '职业',
									name : 'personRelation.relationProfession',
									readOnly  : true,
									allowBlank : false,
									width : 120,
									value:obj.relationProfession
								}]
							}, {
								columnWidth:.33,
								layout : 'form',
								defaults : {
									anchor : '100%'
								},
								items : [{
									xtype : 'textfield',
									fieldLabel : '固定电话',
									name : 'personRelation.relationPhone',
									readOnly  : true,
									// allowBlank : false,
									width : 120,
									value : obj.relationPhone
										// regex :
										// /^(\d{3,4})-(\d{7,8})|^1[358]\d{9}$/,
										// regexText : '电话号码格式不正确或者无效的号码'
								}, {
									xtype : 'textfield',
									fieldLabel : '手机',
									name : 'personRelation.relationCellPhone',
									readOnly  : true,
									allowBlank : false,
									width : 120,
									value : obj.relationCellPhone
										// regex : /^[1][0-9][0-9]{9}$/,
										// regexText : '电话号码格式不正确或者无效的号码'
								},{
									xtype : 'textfield',
									fieldLabel : '住址',
									name : 'personRelation.relationAddress',
									allowBlank : false,
									readOnly  : true,
									width : 120,
									value:obj.relationAddress
								},{
									xtype : 'hidden',
									fieldLabel : '人员主键',
									name : 'personRelation.personId',
									value : obj.personId
								}, {
									xtype : 'hidden',
									fieldLabel : '主键',
									name : 'personRelation.id',
									value : obj.id
								}]
							},{
								columnWidth:.33,
								layout : 'form',
								defaults : {
									anchor : '100%'
								},
								items : [ {
									xtype : 'textfield',
									fieldLabel : '单位电话',
									readOnly  : true,
									allowBlank : false,
									name : 'personRelation.relationCompanyPhone',
									value:obj.relationCompanyPhone,
									//allowBlank : false,
									width : 120
								},{
									xtype : 'textfield',
									fieldLabel : '工作单位',
									name : 'personRelation.relationJobCompany',
									readOnly  : true,
									width : 120,
									value : obj.relationJobCompany
								},{
									xtype : 'textfield',
									fieldLabel : '单位地址',
									name : 'personRelation.relationJobAddress',
									width : 120,
									readOnly  : true,
									value : obj.relationJobAddress
								}]
							},{
								columnWidth:0.99,
								layout : 'form',
								defaults : {
									anchor : '100%'
								},
								items : [{
									fieldLabel : '电核内容',
									name : 'personRelation.content',
									readOnly  : true,
									xtype:"textarea",
									value : obj.content,
									maxLength : 100
								}]
							}]
								// items
						})]
					}).show();
				},
				failure : function(response) {
					Ext.ux.Toast.msg('状态', '操作失败，请重试');
				},
				params : {
					id : id
				}
			});
		}

	},
	getGridDate : function() {
		var vRecords = this.gridPanel.getStore().getRange(0,
				this.gridPanel.getStore().getCount()); // 得到修改的数据（记录对象）
		var vCount = vRecords.length; // 得到记录长度
		var vDatas = '';
		if (vCount > 0) {
			// begin 将记录对象转换为字符串（json格式的字符串）
			for (var i = 0; i < vCount; i++) {
				if (vRecords[i].data.relationPersonId != null
						|| vRecords[i].data.relationPersonId != "") {
					st = {
						"id" : vRecords[i].data.id,
						"projectId" : this.projectId,
						"relationPersonId" : vRecords[i].data.relationPersonId,
						//"projectId" : vRecords[i].data.projectId,
						"isKnowLoan" : vRecords[i].data.isKnowLoan,
						"checkContent" : vRecords[i].data.checkContent,
						"appUserId" : vRecords[i].data.appUserId
					};
				}
				vDatas += Ext.util.JSON.encode(st) + '@';
			}
			vDatas = vDatas.substr(0, vDatas.length - 1);
		}
		// Ext.getCmp('fundIntentJsonData2').setValue(vDatas);
		// this.gridPanel.getColumnsgetEditor().getCmpByName('fundIntentJsonData').setValue(vDatas);
		// alert(this.gridPanel.getEditor().getCmpByName('fundIntentJsonData').getValue());
		return vDatas;
	}

})