var jStoreEnterpriseRelationPersonRefer;
var relationPersonData;
var anchor = '100%';
var selectEnterpriseRelationPerson = function(funName , eid){
	Ext.BLANK_IMAGE_URL = basepath()+'ext3/resources/images/default/s.gif';
	Ext.form.Field.prototype.msgTarget = 'qtip';
	Ext.QuickTips.init();
	jStoreEnterpriseRelationPersonRefer = new Ext.data.JsonStore( {
		url : 'queryListRelationPersonAction.do',
		totalProperty : 'totalProperty',
		root : 'topics',
		fields : [{name : 'id'},{name : 'relationName'},{name : 'relationJob'},{name : 'relationFixedPhone'},{name : 'relationMovePhone'},{name : 'relationFamilyAddress'},{name : 'bossName'},{name : 'bossPhone'},{name : 'remarks'},{name : 'mark'}],
		baseParams : {eid : eid }
	});
	jStoreEnterpriseRelationPersonRefer.load({
		params : {
			start : 0,
			limit : 15
		}
	});
	var button_add = new Ext.Button({
		text : '增加',
		tooltip : '增加一条企业联系人信息',
		iconCls : 'addIcon',
		scope : this,
		handler : function() {
			var addEnterpriseRelationPersonWinRefer = new Ext.Window({
				id : 'addEnterpriseRelationPersonWinRefer',
				title : '新增企业联系人',
				layout : 'fit',
				width : 500,
				height : 410,
				closable : true,
				constrainHeader : true ,
				collapsible : true,
				resizable : true,
				plain : true,
				border : false,
				autoScroll : true ,
				modal : true,
				bodyStyle:'overflowX:hidden',
				buttonAlign : 'right',
				minHeight : 350,
				minWidth : 330,
				items :[new Ext.form.FormPanel({
					id : 'addEnRelationPersonForm',
					url : 'addRelationPersonAction.do',
					monitorValid : true,
					bodyStyle:'padding:10px',
					autoScroll : true ,
					labelAlign : 'right',
					buttonAlign : 'center',
					layout : 'column',
					width : 488,
					height : 378,
					frame : true ,
					items : [{
							columnWidth : .5,
							layout : 'form',
							labelWidth : 90,
							defaults : {anchor : anchor},
							items : [{
								xtype : 'textfield',
								fieldLabel : '联系人姓名',
								name : 'relationPerson.relationName'
							},{
								xtype : 'textfield',
								fieldLabel : '固定电话',
								name : 'relationPerson.relationFixedPhone'
							},{
								xtype : 'textfield',
								fieldLabel : '移动电话',
								name : 'relationPerson.relationMovePhone'
							},{
								xtype: 'radiogroup',
				                fieldLabel: '是否主联系人',
				                allowBlank : false ,
			                	blankText : '必选内容',
				                items: [
				                    {boxLabel: '是', name: 'relationPerson.mark', inputValue: true},
				                    {boxLabel: '否', name: 'relationPerson.mark', inputValue: false}
				                ]
							}]
					},{
							columnWidth : .5,
							layout : 'form',
							labelWidth : 90,
							defaults : {anchor : anchor},
							items : [{
								xtype : 'textfield',
								fieldLabel : '职务',
								name : 'relationPerson.relationJob'
							},{
								xtype : 'textfield',
								fieldLabel : '企业联系人姓名',
								name : 'relationPerson.bossName'
							},{
								xtype : 'textfield',
								fieldLabel : '企业联系人电话',
								name : 'relationPerson.bossPhone'
							}]
					},{
							columnWidth : 1,
							layout : 'form',
							labelWidth : 90,
							defaults : {anchor : anchor},
							items : [{
								xtype : 'textfield',
								fieldLabel : '家庭地址',
								name : 'relationPerson.relationFamilyAddress'
							},{
								xtype : 'textarea',
								fieldLabel : '备注',
								name : 'relationPerson.remarks'
							}]
					},{
						xtype : 'hidden',
						name : 'relationPerson.enterpriseid',
						value : eid
					}],
					buttons : [{
						id : 'submit',
						text : '提交',
						iconCls : 'submitIcon',
						formBind : true,
						handler : function() {
							addEnterpriseRelationPersonWinRefer.findById('addEnRelationPersonForm').getForm().submit({
								method : 'POST',
								waitTitle : '连接',
								waitMsg : '消息发送中...',
								success : function(form ,action) {
									obj = Ext.util.JSON.decode(action.response.responseText);
									if(obj.sign == false){
										Ext.ux.Toast.msg('状态', obj.msg);
									}else{
										Ext.ux.Toast.msg('状态', '添加成功!');
												jStoreEnterpriseRelationPersonRefer.reload();
												Ext.getCmp('addEnterpriseRelationPersonWinRefer').destroy();
									}
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
					},{
						
						text : '取消',
						iconCls : 'cancelIcon',
						handler : function(){
							Ext.getCmp('addEnterpriseRelationPersonWinRefer').destroy();
						}
					
					}]
				})]
			}).show();
		}
	});
	var button_update = new Ext.Button({
		text : '编辑',
		tooltip : '编辑选中的企业联系人信息',
		iconCls : 'updateIcon',
		scope : this,
		handler : function() {
			var selected = gPanelEnterpriseRelationPersonRefer.getSelectionModel().getSelected();
			if (null == selected) {
				Ext.ux.Toast.msg('状态', '请选择一条记录!');
			}else{
				var id = selected.get('id');
				Ext.Ajax.request({
					url : 'seeRelationPersonAction.do',
					method : 'POST',
					success : function(response,request) {
						obj = Ext.util.JSON.decode(response.responseText);
							relationPersonData = obj.data;
							var updateEnterpriseRelationPersonWinRefer = new Ext.Window({
								id : 'updateEnterpriseRelationPersonWinRefer',
								title: '编辑企业联系人信息',
								layout : 'fit',
								width : 500,
								height : 410,
								constrainHeader : true ,
								closable : true,
								resizable : true,
								plain : true,
								border : false,
								modal : true,
								minHeight : 350,
								minWidth : 330,
								buttonAlign: 'right',
								bodyStyle : 'overflowX:hidden',
						       	items : [new Ext.form.FormPanel({
									id :'updateEnRelationPersonForm',
									url : 'updateRelationPersonAction.do',
									monitorValid : true,
									bodyStyle:'padding:10px',
									autoScroll : true ,
									labelAlign : 'right',
									buttonAlign : 'center',
									layout : 'column',
									width : 488,
									height : 378,
									frame : true ,
									items : [{
											columnWidth : .5,
											layout : 'form',
											labelWidth : 90,
											defaults : {anchor : anchor},
											items : [{
												xtype : 'textfield',
												fieldLabel : '联系人姓名',
												name : 'relationPerson.relationName',
												value : relationPersonData.relationName
											},{
												xtype : 'textfield',
												fieldLabel : '固定电话',
												name : 'relationPerson.relationFixedPhone',
												value : relationPersonData.relationFixedPhone
											},{
												xtype : 'textfield',
												fieldLabel : '移动电话',
												name : 'relationPerson.relationMovePhone',
												value : relationPersonData.relationMovePhone
											},{
												xtype: 'radiogroup',
								                fieldLabel: '是否主联系人',
								                allowBlank : false ,
							                	blankText : '必选内容',
								                items: [
								                    {boxLabel: '是', name: 'relationPerson.mark', inputValue: true, checked: relationPersonData.mark},
								                    {boxLabel: '否', name: 'relationPerson.mark', inputValue: false, checked: !(relationPersonData.mark)}
								                ]
											}]
									},{
											columnWidth : .5,
											layout : 'form',
											labelWidth : 90,
											defaults : {anchor : anchor},
											items : [{
												xtype : 'textfield',
												fieldLabel : '职务',
												name : 'relationPerson.relationJob',
												value : relationPersonData.relationJob
											},{
												xtype : 'textfield',
												fieldLabel : '企业联系人姓名',
												name : 'relationPerson.bossName',
												value : relationPersonData.bossName
											},{
												xtype : 'textfield',
												fieldLabel : '企业联系人电话',
												name : 'relationPerson.bossPhone',
												value : relationPersonData.bossPhone
											}]
									},{
											columnWidth : 1,
											layout : 'form',
											labelWidth : 90,
											defaults : {anchor : anchor},
											items : [{
												xtype : 'textfield',
												fieldLabel : '家庭地址',
												name : 'relationPerson.relationFamilyAddress',
												value : relationPersonData.relationFamilyAddress
											},{
												xtype : 'textarea',
												fieldLabel : '备注',
												name : 'relationPerson.remarks',
												value : relationPersonData.remarks
											}]
									},{
										xtype : 'hidden',
										name : 'relationPerson.enterpriseid',
										value : relationPersonData.enterpriseid
									},{
										xtype : 'hidden',
										name : 'relationPerson.id',
										value : relationPersonData.id
									}],
									buttons : [{
										id : 'submit',
										text : '提交',
										iconCls : 'submitIcon',
										formBind : true,
										handler : function() {
											updateEnterpriseRelationPersonWinRefer.findById('updateEnRelationPersonForm').getForm().submit({
												method : 'POST',
												waitTitle : '连接',
												waitMsg : '消息发送中...',
												success : function(form ,action) {
													obj = Ext.util.JSON.decode(action.response.responseText);
													if(obj.sign == false){
														Ext.ux.Toast.msg('状态', obj.msg);
													}else{
														Ext.ux.Toast.msg('状态', '编辑成功!');
																jStoreEnterpriseRelationPersonRefer.reload();
																Ext.getCmp('updateEnterpriseRelationPersonWinRefer').destroy();
													}
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
									},{
										
										text : '取消',
										iconCls : 'cancelIcon',
										handler : function(){
											Ext.getCmp('win_update').destroy();
										}
									}]
								})]
							}).show();			
					},
					failure : function(response) {					
							Ext.ux.Toast.msg('状态','操作失败，请重试');		
					},
					params: { id: id }
				});	
			}
		
		}
	});
	var button_see = new Ext.Button({
		text : '查看',
		tooltip : '查看选中的银行联系人信息',
		iconCls : 'seeIcon',
		scope : this,
		handler : function() {
			var selected = gPanelEnterpriseRelationPersonRefer.getSelectionModel().getSelected();
			if (null == selected) {
				Ext.ux.Toast.msg('状态', '请选择一条记录!');
			}else{
				var id = selected.get('id');
				seeEnRelationPersonWin(id);
			}
		}
	}); 
	var cModelEnterpriseRelationPersonRefer = new Ext.grid.ColumnModel(
		[
			new Ext.grid.RowNumberer( {
				header : '序号',
				width : 35
			}),
			 {
				header : "联系人姓名",
				width : 100,
				sortable : true,
				dataIndex : 'relationName'
			}, {
				header : "联系人职务",
				width : 80,
				sortable : true,
				dataIndex : 'relationJob'
			},{
				header : "移动电话",
				width : 90,
				sortable : true,
				dataIndex : 'relationMovePhone'
			}, {
				header : "固定电话",
				width : 85,
				sortable : true,
				dataIndex : 'relationFixedPhone'
			}, {
				header : "家庭地址",
				width : 120,
				sortable : true,
				dataIndex : 'relationFamilyAddress'
			} ]);

	var pagingBar = new Ext.PagingToolbar( {
		pageSize : 15,
		store : jStoreEnterpriseRelationPersonRefer,
		autoWidth : true,
		hideBorders : true,
		displayInfo : true,
		displayMsg : '当前第{0} - {1}条，共 {2} 条记录',
		emptyMsg : "没有符合条件的记录······"
	});
	var myMask = new Ext.LoadMask(Ext.getBody(), {
		msg : "加载数据中······,请稍后······"
	});
	
	var gPanelEnterpriseRelationPersonRefer = new Ext.grid.GridPanel( {
		id : 'gPanelEnterpriseRelationPersonRefer',
		store : jStoreEnterpriseRelationPersonRefer,
		colModel : cModelEnterpriseRelationPersonRefer,
		selModel : new Ext.grid.RowSelectionModel(),
		stripeRows : true,
		loadMask : myMask,
		autoWidth : true,
		height : Ext.getBody().getHeight()-100,
		bbar : pagingBar,
		tbar : [button_add,button_see,button_update,{
			iconCls : 'deleteIcon',
			text : '删除',
			handler : function(){
				Ext.Msg.confirm('操作确认','确认删除',function(btn){
					if(btn=='yes'){
						var rcd = gPanelEnterpriseRelationPersonRefer.getSelectionModel().getSelected() ;
						var id = rcd.get('id') ;
						Ext.Ajax.request({
							url : 'deleteRelationPersonAction.do',
							method : 'POST',
							success : function(response, request){
								var obj = Ext.decode(response.responseText) ;
								if(obj.success){
									Ext.ux.Toast.msg('操作信息','删除成功');
									gPanelEnterpriseRelationPersonRefer.getStore().reload();
								}
							},
							failure : function(response, request){
								Ext.ux.Toast.msg('操作信息','删除失败');
							},
							params : {
								id : id
							}
						}) ;	
					}
				}) ;
			}
		}],
		listeners : {
			'rowdblclick' : function(grid, index, e) {
				var selected = grid.getStore().getAt(index) ;
				callbackFun(selected,funName);
				winEnterpriseRelationPersonRefer.destroy();
			}
		}
	});
	var winEnterpriseRelationPersonRefer = new Ext.Window({
			title : '企业联系人列表',
			width : 530,
			height : 440,
			constrainHeader : true ,
			layout : 'fit',
			buttonAlign : 'center',
			items : [gPanelEnterpriseRelationPersonRefer],
			modal : true 
		}).show();
	var searchByCondition = function() {
		jStoreEnterpriseRelationPersonRefer.load({
			params : {
				start : 0,
				limit : 15
			}
		});
	}
	var callbackFun = function(selected,funName){
		enterpriseRelationPersonJsonObj = {
				id : selected.get('id'),
				relationMovePhone : selected.get('relationMovePhone'),
				relationFixedPhone : selected.get('relationFixedPhone'),
				relationName : selected.get('relationName'),
				relationJob : selected.get('relationJob')
				}
		funName(enterpriseRelationPersonJsonObj);
	}
	
	var seeEnRelationPersonWin = function(id){
		Ext.Ajax.request({
			url : 'seeRelationPersonAction.do',
			method : 'POST',
			success : function(response,request){
				obj = Ext.util.JSON.decode(response.responseText);
				relationPersonData = obj.data;	
				var seeEnterpriseRelationPersonWinRefer = new Ext.Window({
					id : 'seeEnterpriseRelationPersonWinRefer',
					title: '查看企业联系人信息',
					layout : 'fit',
					width : 500,
					height : 410,
					closable : true,
					collapsible : true,
					resizable : true,
					plain : true,
					border : false,
					autoScroll : true ,
					modal : true,
					buttonAlign: 'right',
					minHeight : 350,
					minWidth : 330,
					bodyStyle:'overflowX:hidden',
				    items : [new Ext.form.FormPanel({
						bodyStyle:'padding:10px',
						autoScroll : true ,
						labelAlign : 'right',
						buttonAlign : 'center',
						layout : 'column',
						width : 488,
						height : 378,
						frame : true ,
						items : [{
								columnWidth : .5,
								layout : 'form',
								labelWidth : 80,
								defaults : {anchor : anchor},
								items : [{
									xtype : 'textfield',
									fieldLabel : '联系人姓名',
									value : relationPersonData.relationName,
									readOnly  : true,
									cls : 'readOnlyClass'
								},{
									xtype : 'textfield',
									fieldLabel : '固定电话',
									value : relationPersonData.relationFixedPhone,
									readOnly  : true,
									cls : 'readOnlyClass'
								},{
									xtype : 'textfield',
									fieldLabel : '移动电话',
									value : relationPersonData.relationMovePhone,
									readOnly  : true,
									cls : 'readOnlyClass'
								},{
									xtype: 'textfield',
					                fieldLabel: '是否主联系人',
					                value : (relationPersonData.mark == true) ? '是' : '否',
					                readOnly  : true,
									cls : 'readOnlyClass'
								}]
						},{
								columnWidth : .5,
								layout : 'form',
								labelWidth : 90,
								defaults : {anchor : anchor},
								items : [{
									xtype : 'textfield',
									fieldLabel : '职务',
									value : relationPersonData.relationJob,
									readOnly  : true,
									cls : 'readOnlyClass'
								},{
									xtype : 'textfield',
									fieldLabel : '企业联系人姓名',
									value : relationPersonData.bossName,
									readOnly  : true,
									cls : 'readOnlyClass'
								},{
									xtype : 'textfield',
									fieldLabel : '企业联系人电话',
									value : relationPersonData.bossPhone,
									readOnly  : true,
									cls : 'readOnlyClass'
								}]
						},{
								columnWidth : 1,
								layout : 'form',
								labelWidth : 80,
								defaults : {anchor : anchor},
								items : [{
									xtype : 'textfield',
									fieldLabel : '家庭地址',
									value : relationPersonData.relationFamilyAddress,
									readOnly  : true,
									cls : 'readOnlyClass'
								},{
									xtype : 'textarea',
									fieldLabel : '备注',
									value : relationPersonData.remarks,
									readOnly  : true,
									cls : 'readOnlyClass'
								}]
						}]
					})]
				}).show();
				},
				failure : function(response) {					
						Ext.ux.Toast.msg('状态','操作失败，请重试');		
				},
				params: { id: id }
		});	
	}
}