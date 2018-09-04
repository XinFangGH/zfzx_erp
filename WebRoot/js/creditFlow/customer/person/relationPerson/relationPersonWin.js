var selectRelationPersonList = function (personIdValue,isReadOnly){
	/*Ext.Ajax.request({   
    	url: __ctxPath+'/credit/customer/person/queryPersonByNameMessage.do',   
   	 	method:'post',   
    	params:{perosnName: cardNum},   
    	success: function(response, option) {   
        	var obj = Ext.decode(response.responseText);
        	var personIdValue = obj.data.id;*/
        	jStore_PanelReliation = new Ext.data.JsonStore( {
				url : __ctxPath+'/creditFlow/customer/person/ajaxQueryPersonRelation.do',
				totalProperty : 'totalProperty',
				root : 'topics',
				fields : [ {
					name : 'id' 
				},{
					name : 'relationName'
				},{
					name : 'relationShip'
				}, {
					name : 'relationPhone'
				},{
					name : 'relationCellPhone'
				}, {
					name : 'relationShipValue'
				}, {
					name : 'personId'
				}]
			});
			jStore_PanelReliation.load({
				params : {
					start : 0,
					limit : 20,
					personId : personIdValue
				}
			});
		
			var button_add = new Ext.Button({
				text : '增加',
				tooltip : '增加新的关系人',
				iconCls : 'addIcon',
				scope : this,
				handler : function() {
					var addRelationPersonMessageWin = new Ext.Window({
						id : 'addRelationPersonMessageWin',
						title: '新增关系人信息',
						layout : 'fit',
						width : 500,
						height :150,
						closable : true,
						resizable : false,
						autoHeight:true,
						plain : true,
						collapsible : true,
						border : false,
						modal : true,
						buttonAlign: 'center',
						bodyStyle : 'padding: 0',
						items : [
								new Ext.form.FormPanel( {
									id : 'fPanel_addRelationPerson',
									url : __ctxPath+'/creditFlow/customer/person/addPersonRelation.do',
									labelAlign : 'right',
									labelWidth : 70,
									frame : true,
									layout : 'column',
									buttonAlign : 'center',
									autoWidth:true,
									autoHeight:true,
									monitorValid : true,
									defaults : {
										layout : 'form',
										border : false,
										columnWidth : .33
									},
									items : [ {
										defaults:{anchor:'98%'},
										items : [ {
											xtype : 'textfield',
											fieldLabel : '<font color="red">*</font>姓名',
											name : 'personRelation.relationName',
											allowBlank : false
										},{
											xtype : "dickeycombo",
											nodeKey :'gxrgx',
											fieldLabel : '<font color="red">*</font>关系',
											hiddenName : 'personRelation.relationShip',
											editable : false,
											allowBlank : false,
											listeners : {
																afterrender : function(combox) {
																	var st = combox.getStore();
																	st.on("load", function() {
																		if(combox.getValue() == 0||combox.getValue()==1){
																			combox.setValue("");
																		}else{
																			combox.setValue(combox
																				.getValue());
																		}
																		combox.clearInvalid();
																	})
																}
															}
										},{
											xtype : 'textfield',
											fieldLabel : '单位地址',
											name : 'personRelation.relationJobAddress',
											width : 120
										},{
											xtype:'combo',
											hiddenName:'personRelation.flag',
											fieldLabel : '联系人类型',
											triggerAction: 'all',
										    lazyRender:true,
										    allowBlank:false,
										    width : 120,
										    mode: 'local',
											store: new Ext.data.ArrayStore({
												        fields: [
												            'flagValue',
												            'displayText'
												        ],
												        data: [[0, '家庭联系人'], [1, '工作证明人'],[2, '紧急联系人']]
												    }),
											valueField: 'flagValue',
    										displayField: 'displayText'
										}]
									},{defaults:{anchor:'98%'},
										items : [ {
											xtype : 'textfield',
											fieldLabel : '固定电话',
											name : 'personRelation.relationPhone',
											//allowBlank : false,
											width : 120,
											regex : /^(\d{3,4})-(\d{7,8})|^1[358]\d{9}$/,
											regexText : '电话号码格式不正确或者无效的号码'
										},{
											xtype : 'textfield',
											fieldLabel : '手机',
											name : 'personRelation.relationCellPhone',
											allowBlank : false,
											width : 120,
											regex : /^[1][0-9][0-9]{9}$/,
											regexText : '电话号码格式不正确或者无效的号码'
										},{
											xtype : 'hidden',
											fieldLabel : '人员主键',
											name : 'personRelation.personId',
											//allowBlank : false,
											width : 120,
											value : personIdValue
										},{
												xtype : 'textfield',
												fieldLabel : '工作单位',
												name : 'personRelation.relationJobCompany',
												width : 120,
												value : obj.relationJobCompany
										}]
									},{defaults:{anchor:'98%'},
										items:[{
											xtype : 'textfield',
											fieldLabel : '职业',
											name : 'personRelation.relationProfession',
											allowBlank : false,
											width : 120
										},{
											xtype : 'textfield',
											fieldLabel : '住址',
											name : 'personRelation.relationAddress',
											allowBlank : false,
											width : 120
										},{
											xtype : 'textfield',
											fieldLabel : '单位电话',
											allowBlank : false,
											name : 'personRelation.relationCompanyPhone',
											//allowBlank : false,
											width : 120
										}]
									}]// items
								})
						 ],
						 buttons : [{
								text : '提交',
								iconCls : 'submitIcon',
								formBind : true,
								handler : function() {
									var form = Ext.getCmp('fPanel_addRelationPerson').getForm();
									if(form.isValid()){
										form.submit({
											method : 'POST',
											waitTitle : '连接',
											waitMsg : '消息发送中...',
											success : function() {
												Ext.ux.Toast.msg('状态', '添加成功!');
																addRelationPersonMessageWin.destroy();
																jStore_PanelReliation.reload();
											},
											failure : function(form, action) {
												Ext.ux.Toast.msg('状态', '添加关系人信息失败!');
												// top.getForm().reset();
											}
										});
									}
								}
							}, {
								text : '取消',
								iconCls : 'cancelIcon',
								handler : function() {
								addRelationPersonMessageWin.destroy();
								}
							}]
					}).show();
				
				}
			});
			
			//jiang
			var function_update = function(id){
				
				Ext.Ajax.request({
					url : __ctxPath+'/creditFlow/customer/person/seePersonRelation.do',
					method : 'POST',
					success : function(response,request) {
						objMes = Ext.util.JSON.decode(response.responseText);
							obj = objMes.data;
							var win_updateRelationPerson = new Ext.Window({
								id : 'win_updateRelationPerson',
								title: '修改关系人信息',
								layout : 'fit',
								width : 500,
								height :150,
								closable : true,
								resizable : false,
								autoHeight:true,
								plain : true,
								collapsible : true,
								border : false,
								modal : true,
								buttonAlign: 'center',
								bodyStyle : 'padding: 0',
								items : [
									new Ext.form.FormPanel( {
										id : 'fPanel_updateRelationPerson',
										url : __ctxPath+'/creditFlow/customer/person/updatePersonRelation.do',
										labelAlign : 'right',
										autoWidth:true,
										autoHeight:true,
										frame : true,
										layout : 'column',
										labelWidth : 70,
										buttonAlign : 'center',
										monitorValid : true,
										defaults : {
											layout : 'form',
											border : false,
											columnWidth : .33
										},
										items : [ {defaults:{anchor:'98%'},
											items : [ {
												xtype : 'textfield',
												fieldLabel : '<font color="red">*</font>姓名',
												name : 'personRelation.relationName',
												//allowBlank : false,
												width : 120,
												value : obj.relationName
											},{
												xtype : "dickeycombo",
															nodeKey :'gxrgx',
												fieldLabel : '<font color="red">*</font>关系',
												hiddenName : 'personRelation.relationShip',
												width : 120,
												//mode : 'remote',
												editable : false,
												value : obj.relationShip,
												/*valueNotFoundText : obj.relationShipValue*/
												listeners : {
																afterrender : function(combox) {
																	var st = combox.getStore();
																	st.on("load", function() {
																		if(combox.getValue() == 0||combox.getValue()==1){
																			combox.setValue("");
																		}else{
																			combox.setValue(combox
																				.getValue());
																		}
																		combox.clearInvalid();
																	})
																}
															}
											},{
												xtype : 'textfield',
												fieldLabel : '单位地址',
												name : 'personRelation.relationJobAddress',
												width : 120,
												value : obj.relationJobAddress
											},{
												xtype:'combo',
												hiddenName:'personRelation.flag',
												triggerAction: 'all',
											    lazyRender:true,
											    width : 120,
											    mode: 'local',
												fieldLabel : '联系人类型',
												store: new Ext.data.ArrayStore({
													        fields: [
													            'flagValue',
													            'displayText'
													        ],
													        data: [[0, '家庭联系人'], [1, '工作证明人'],[2, '紧急联系人']]
													    }),
												valueField: 'flagValue',
	    										displayField: 'displayText',
	    										value : obj.flag
											}]
										},{defaults:{anchor:'98%'},
											items : [ {
												xtype : 'textfield',
												fieldLabel : '固定电话',
												name : 'personRelation.relationPhone',
												allowBlank : true,
												width : 120,
												value : obj.relationPhone,
												regex : /^(\d{3,4})-(\d{7,8})|^1[358]\d{9}$/,
												regexText : '电话号码格式不正确或者无效的号码'
											},{
												xtype : 'textfield',
												fieldLabel : '手机',
												name : 'personRelation.relationCellPhone',
												allowBlank : false,
												width : 120,
												value : obj.relationCellPhone,
												regex : /^[1][0-9][0-9]{9}$/,
												regexText : '电话号码格式不正确或者无效的号码'
											},{
												xtype : 'hidden',
												fieldLabel : '人员主键',
												name : 'personRelation.personId',
												value : obj.personId
											},{
												xtype : 'hidden',
												fieldLabel : '主键',
												name : 'personRelation.id',
												value : obj.id
											},{
												xtype : 'textfield',
												fieldLabel : '工作单位',
												name : 'personRelation.relationJobCompany',
												width : 120,
												value : obj.relationJobCompany
											}]
										},{defaults:{anchor:'98%'},
											items:[{
												xtype : 'textfield',
												fieldLabel : '职业',
												name : 'personRelation.relationProfession',
												allowBlank : false,
												value:obj.relationProfession,
												width : 120
											},{
												xtype : 'textfield',
												fieldLabel : '住址',
												name : 'personRelation.relationAddress',
												allowBlank : false,
												value:obj.relationAddress,
												width : 120
											},{
												xtype : 'textfield',
												fieldLabel : '单位电话',
												allowBlank : false,
												name : 'personRelation.relationCompanyPhone',
												value:obj.relationCompanyPhone,
												//allowBlank : false,
												width : 120
											}]
										
										}]// items
									})
							 ],
							 buttons : [{
									text : '保存',
									iconCls : 'submitIcon',
									formBind : true,
									handler : function() {
										var form = Ext.getCmp('fPanel_updateRelationPerson').getForm();
										if(form.isValid()){
											form.submit({
												method : 'POST',
												waitTitle : '连接',
												waitMsg : '消息发送中...',
												success : function() {
													Ext.ux.Toast.msg('状态', '保存成功!');
																//if (btn == 'ok') {
																	win_updateRelationPerson.destroy();
																	jStore_PanelReliation.reload();
																//}
												},
												failure : function(form, action) {
													Ext.ux.Toast.msg('状态', '添加关系人信息失败!');
													// top.getForm().reset();
												}
											});
										}
									}
								}]
							}).show();
					},
					failure : function(response) {					
							Ext.ux.Toast.msg('状态','操作失败，请重试');		
					},
					params: { id: id }
				});	
				
			}//function_update
			
			
			var button_update = new Ext.Button({
				text : '修改',
				tooltip : '修改选中的关系人信息',
				iconCls : 'updateIcon',
				scope : this,
				handler : function() {
					var selected = gPanelReliation.getSelectionModel().getSelected();
					if (null == selected) {
						Ext.ux.Toast.msg('状态', '请选择一条记录!');
					}else{
						var id = selected.get('id');
						function_update(id);
					}
				
				}
			});
			var button_delete = new Ext.Button({
				text : '删除',
				tooltip : '删除信息',
				iconCls : 'deleteIcon',
				scope : this,
				handler : function() {
					var selected = gPanelReliation.getSelectionModel().getSelected();
					if (null == selected) {
						Ext.ux.Toast.msg('状态', '请选择一条记录!');
					}else{
						var id = selected.get('id');
						
						Ext.Ajax.request({
							url : __ctxPath+'/creditFlow/customer/person/deleteRsPersonRelation.do',
							method : 'POST',
							success : function() {
								jStore_PanelReliation.reload();
								Ext.ux.Toast.msg('状态', '删除成功!');
							},
							failure : function(result, action) {
								Ext.ux.Toast.msg('状态','删除失败!');
							},
							params: { id: id }
						});
					}
				}
			});
			
			
			var cModel_PanelReliation = new Ext.grid.ColumnModel(
					[
						new Ext.grid.RowNumberer( {
							header : '序号',
							width : 30
						}),
						{
							header : "姓名",
							width : 80,
							sortable : true,
						    dataIndex : 'relationName'
						}, {
							header : "关系",
							width : 100,
							sortable : true,
							dataIndex : 'relationShipValue'
						},{
							header : "电话",
							width : 100,
							sortable : true,
							dataIndex : 'relationPhone'
						},{
							id : 'id_autoExpandColumn',
							header : "手机",
							width : 100,
							sortable : true,
							dataIndex : 'relationCellPhone'
						}
						]);
		
			var pagingBar = new Ext.PagingToolbar( {
				pageSize : 15,
				store : jStore_PanelReliation,
				autoWidth : true,
				hideBorders : true,
				displayInfo : true,
				displayMsg : '当前第{0} - {1}条，共 {2} 条记录',
				emptyMsg : "没有符合条件的记录······"
			});
			var myMask = new Ext.LoadMask(Ext.getBody(), {
				msg : "加载数据中······,请稍后······"
			});
		
			var gPanelReliation = new Ext.grid.GridPanel( {
				id : 'gPanelReliation',
				store : jStore_PanelReliation,
				colModel : cModel_PanelReliation,
				selModel : new Ext.grid.RowSelectionModel(),
				stripeRows : true,
				autoExpandColumn : 'id_autoExpandColumn',
				loadMask : myMask,
				bbar : pagingBar,
				tbar : isReadOnly?[]:[button_add,button_update,button_delete],
				listeners : {
					'rowdblclick' : function(grid, index, e) {
						var selected = grid.getStore().getAt(index) ;
		//				callbackFun(selected,funName);
						winReliationListRefer.destroy();
					}
				}
			});
		
			
		
			var winReliationListRefer = new Ext.Window({
				    id : 'winReliationListRefer',
					title : '关系人列表',
					collapsible : true,
					width : 500,
					height : 400,
					constrainHeader : true ,
					layout : 'fit',
					buttonAlign : 'center',
					items : [gPanelReliation],
					modal : true 
				}).show();
				
			var searchByCondition = function() {
				jStore_PanelReliation.load({
					params : {
						start : 0,
						limit : 15
					}
				});
			}
        	
        	
    	/*},   
    	failure: function(response, option) {   
        	return true;   
        	Ext.ux.Toast.msg('友情提示',"异步通讯失败，请于管理员联系！");   
    	}   
	});*/
	
	
	
	
//	var callbackFun = function(selected,funName){
//		carJsonObj = {
//					id : selected.get('id'),
//					relationName : selected.get('relationName'),
//					relationShipValue : selected.get('relationShipValue'),
//					relationPhone : selected.get('relationPhone'),
//					relationCellPhone : selected.get('relationCellPhone')
//				}
//		funName(carJsonObj);
//	}
	
}