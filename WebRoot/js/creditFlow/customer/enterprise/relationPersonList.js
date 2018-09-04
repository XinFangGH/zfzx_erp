function relationPersonListWin(orgVal,isReadOnly){
	var pageSize = 10 ;
	var anchor = '100%';
	var shortname ;
	Ext.Ajax.request({   
    	url: __ctxPath + '/creditFlow/customer/enterprise/isEntEmptyEnterprise.do',   
   	 	method:'post',   
    	params:{organizecode:orgVal},   
    	success: function(response, option) {   
        	var obj = Ext.decode(response.responseText);
        	var enterpriseId = obj.data.id;
        	shortname = obj.data.shortname;
			var jStore_relationPerson = new Ext.data.JsonStore( {
				url : __ctxPath + '/creditFlow/customer/enterprise/queryListRelationPersonEnterpriseRelationPerson.do',
				totalProperty : 'totalProperty',
				root : 'topics',
				fields : [{name : 'id'},{name : 'relationName'},{name : 'relationJob'},{name : 'relationFixedPhone'},{name : 'relationMovePhone'},{name : 'relationFamilyAddress'},{name : 'bossName'},{name : 'bossPhone'},{name : 'remarks'},{name : 'mark'}],
				baseParams : {
					eid : enterpriseId
				},
				listeners : {
					'load':function(){
						gPanel_relationPerson.getSelectionModel().selectFirstRow() ;
					},
					'loadexception' : function(){
						Ext.ux.Toast.msg('提示','数据加载失败，请保证网络畅通！');			
					}
				}
			});
			var button_add = new CS.button.AButton({
				handler : function() {
					var addRelationPersonPanel = new Ext.form.FormPanel({
						id :'addRelationPersonPanel',
						url : __ctxPath + '/creditFlow/customer/enterprise/addRelationPersonEnterpriseRelationPerson.do',
						monitorValid : true,
						bodyStyle:'padding:10px',
						autoScroll : true ,
						labelAlign : 'right',
						buttonAlign : 'center',
						layout : 'column',
						width : 488,
						height : 270,
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
									xtype: 'radiogroup',
					                fieldLabel: '是否主联系人',
					                allowBlank : false ,
				                	blankText : '必选内容',
					                items: [
					                    {boxLabel: '是', name: 'relationPerson.mark', inputValue: true,checked:true},
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
									fieldLabel : '移动电话',
									name : 'relationPerson.relationMovePhone'
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
							value : enterpriseId
						}],
						buttons : [{
							id : 'submit',
							text : '保存',
							iconCls : 'submitIcon',
							formBind : true,
							handler : function() {
								addRelationPersonPanel.getForm().submit({
									method : 'POST',
									waitTitle : '连接',
									waitMsg : '消息发送中...',
									success : function(form ,action) {
										obj = Ext.util.JSON.decode(action.response.responseText);
										if(obj.sign == false){
											Ext.ux.Toast.msg('状态', obj.msg);
										}else{
											Ext.ux.Toast.msg('状态', '保存成功!');
													jStore_relationPerson.reload();
													Ext.getCmp('win_add').destroy();
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
						}]
					})
					var win_add = new Ext.Window({
						id : 'win_add',
						title: '新增<font color=red><'+shortname+'></font>企业联系人信息',
						layout : 'fit',
						width :(screen.width-180)*0.5 - 20,
						height : 310,
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
						items :[addRelationPersonPanel],
						listeners : {
							'beforeclose' : function(){
								if(addRelationPersonPanel != null){
									if(addRelationPersonPanel.getForm().isDirty()){
										Ext.Msg.confirm('操作提示','数据被修改过,是否保存',function(btn){
											if(btn=='yes'){
												addRelationPersonPanel.buttons[0].handler.call() ;
											}else{
												addRelationPersonPanel.getForm().reset() ;
												win_add.destroy() ;
											}
										}) ;
										return false ;
									}
								}
							}
						}
					}).show();
				}
			});
			var button_update = new CS.button.UButton({
				handler : function() {
					var selected = gPanel_relationPerson.getSelectionModel().getSelected();
					if (null == selected) {
						Ext.ux.Toast.msg('状态', '请选择一条记录!');
					}else{
						var id = selected.get('id');
						Ext.Ajax.request({
							url : __ctxPath + '/creditFlow/customer/enterprise/seeRelationPersonEnterpriseRelationPerson.do',
							method : 'POST',
							success : function(response,request) {
								var obj = Ext.util.JSON.decode(response.responseText);
								var relationPersonData = obj.data ;
								var updateRelationPersonPanel = new Ext.form.FormPanel({
									id :'updateRelationPersonPanel',
									url : __ctxPath + '/creditFlow/customer/enterprise/updateRelationPersonEnterpriseRelationPerson.do',
									monitorValid : true,
									bodyStyle:'padding:10px',
									autoScroll : true ,
									labelAlign : 'right',
									buttonAlign : 'center',
									layout : 'column',
									width : 488,
									height : 270,
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
										text : '保存',
										iconCls : 'submitIcon',
										formBind : true,
										handler : function() {
											updateRelationPersonPanel.getForm().submit({
												method : 'POST',
												waitTitle : '连接',
												waitMsg : '消息发送中...',
												success : function(form ,action) {
													obj = Ext.util.JSON.decode(action.response.responseText);
													if(obj.sign == false){
														Ext.ux.Toast.msg('状态', obj.msg);
													}else{
														Ext.ux.Toast.msg('状态', '保存成功!');
																jStore_relationPerson.reload();
																Ext.getCmp('win_update').destroy();
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
									}]
								})
								var window_update = new Ext.Window({
									id : 'win_update',
									title: '编辑<font color=red><'+relationPersonData.shortname+'></font>企业联系人信息',
									layout : 'fit',
									width :(screen.width-180)*0.5 - 20,
									height : 310,
									closable : true,
									resizable : true,
									constrainHeader : true ,
									collapsible : true, 
									plain : true,
									border : false,
									modal : true,
									autoScroll : true ,
									bodyStyle:'overflowX:hidden',
									buttonAlign : 'right',
									items :[updateRelationPersonPanel],
									listeners : {
										'beforeclose' : function(){
											if(updateRelationPersonPanel != null){
												if(updateRelationPersonPanel.getForm().isDirty()){
													Ext.Msg.confirm('操作提示','数据被修改过,是否保存',function(btn){
														if(btn=='yes'){
															updateRelationPersonPanel.buttons[0].handler.call() ;
														}else{
															updateRelationPersonPanel.getForm().reset() ;
															window_update.destroy() ;
														}
													}) ;
													return false ;
												}
											}
										}
									}
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
			var button_delete = new CS.button.DButton({
				handler : function() {
					var selected = gPanel_relationPerson.getSelectionModel().getSelected();
					if (null == selected) {
						Ext.ux.Toast.msg('状态', '请选择一条记录!');
					}else{
						var id = selected.get('id');
						Ext.MessageBox.confirm('确认删除', '是否确认执行删除 ', function(btn) {
							if (btn == 'yes') {
								Ext.Ajax.request({
									url : __ctxPath + '/creditFlow/customer/enterprise/deleteRelationPersonEnterpriseRelationPerson.do',
									method : 'POST',
									success : function() {
										Ext.ux.Toast.msg('状态', '删除成功!');
										jStore_relationPerson.reload() ;
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
					var selected = gPanel_relationPerson.getSelectionModel().getSelected();
					if (null == selected) {
						Ext.ux.Toast.msg('状态', '请选择一条记录!');
					}else{
						var id = selected.get('id');
						seeRelationPerson(id);
					}
				}
			});
			var cModel_relationPerson = new Ext.grid.ColumnModel(
					[
							new Ext.grid.RowNumberer(),
							{
								header : "联系人姓名",
								width : 100,
								sortable : true,
								dataIndex : 'relationName'
							}, {
								header : "职务",
								width : 100,
								sortable : true,
								dataIndex : 'relationJob'
							}, {
								header : "固定电话",
								width : 120,
								sortable : true,
								dataIndex : 'relationFixedPhone'
							}, {
								header : "移动电话",
								width : 120,
								sortable : true,
								dataIndex : 'relationMovePhone'
							},{
								header : "是否主联系人",
								width : 100,
								sortable : true,
								dataIndex : 'mark',
								renderer : function(r){
									if (r == true) {
										return '是';
									} else if (r == false) {
										return '否';
									} 
								}
							},{
								header : "家庭住址",
								width : 180,
								sortable : true,
								dataIndex : 'relationFamilyAddress'
							}]);
			var gPanel_relationPerson = new Ext.grid.GridPanel( {
				pageSize : pageSize,
				store : jStore_relationPerson,
				autoWidth : true,
				border : false ,
				height:440,
				colModel : cModel_relationPerson,
				tbar : isReadOnly?[button_see]:[button_add,button_see,button_update,button_delete],
				loadMask : new Ext.LoadMask(Ext.getBody(), {
					msg : "加载数据中······,请稍后······"
				}),
				bbar : new Ext.PagingToolbar({
					pageSize : pageSize,
					autoWidth : false ,
					style : '',
					displayInfo : true,
					displayMsg : '当前第{0} - {1}条，共 {2} 条记录',
					emptyMsg : '没有符合条件的记录',
					store : jStore_relationPerson
				}),
				listeners : {
					'rowdblclick' : function(grid,index,e){
						var id = grid.getSelectionModel().getSelected().get('id');
						seeRelationPerson(id);
					}
				}
			});
			var seeRelationPerson = function(id){
				Ext.Ajax.request({
					url : __ctxPath + '/creditFlow/customer/enterprise/seeRelationPersonEnterpriseRelationPerson.do',
					method : 'POST',
					success : function(response,request) {
						var obj = Ext.util.JSON.decode(response.responseText);
						var relationPersonData = obj.data ;
						var seeRelationPersonPanel = new Ext.form.FormPanel({
							id :'seeRelationPersonPanel',
							bodyStyle:'padding:10px',
							autoScroll : true ,
							labelAlign : 'right',
							buttonAlign : 'center',
							layout : 'column',
							width : 488,
							height : 260,
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
						})
						var window_see = new Ext.Window({
							title: '查看<font color=red><'+relationPersonData.shortname+'></font>企业联系人信息',
							layout : 'fit',
							closable : true,
							resizable : true,
							constrainHeader : true ,
							collapsible : true, 
							plain : true,
							border : false,
							modal : true,
							width :(screen.width-180)*0.5 - 20,
							height : 300,
							autoScroll : true ,
							bodyStyle:'overflowX:hidden',
							buttonAlign : 'right',
							items :[seeRelationPersonPanel]
						}).show();			
					},
					failure : function(response) {
							Ext.ux.Toast.msg('状态','操作失败，请重试');	
					},
					params: { id: id }
				});	
			}
			jStore_relationPerson.load({
				params : {
					start : 0,
					limit : pageSize
				}
			});
        	var win_listEnterpriseRelationPerson = new Ext.Window({
				title : '<font color=red><'+obj.data.shortname+'></font>企业联系人信息  ',
				width :(screen.width-180)*0.5 + 30,
				height : 400,
				buttonAlign : 'center',
				layout : 'fit',
				modal : true,
				maximizable : true,
				constrainHeader : true ,
				collapsible : true, 
				items :[gPanel_relationPerson]
			}).show();
    	},   
    	failure: function(response, option) {   
        	return true;   
        	Ext.ux.Toast.msg('友情提示',"异步通讯失败，请于管理员联系！");   
    	}   
	});
}