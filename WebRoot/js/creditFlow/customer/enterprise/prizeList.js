function prizeListWin(orgVal,isReadOnly){
	var pageSize = 10 ;
	var anchor = '100%';
	Ext.Ajax.request({   
    	url: __ctxPath + '/creditFlow/customer/enterprise/isEntEmptyEnterprise.do',   
   	 	method:'post',   
    	params:{organizecode:orgVal},   
    	success: function(response, option) {   
        	var obj = Ext.decode(response.responseText);
        	var enterpriseId = obj.data.id;
			var jStore_prize = new Ext.data.JsonStore( {
				url : __ctxPath + '/creditFlow/customer/enterprise/queryEnterprisePrize.do',
				totalProperty : 'totalProperty',
				root : 'topics',
				fields : [ {
					name : 'id'  //获奖ID
				},{
					name : 'certificatename' //证书名称
				}, {
					name : 'certificatecode' //证书编号
				}, {
					name : 'organname' //颁发机构名称
				}, {
					name : 'prizerpname'  //获奖人
				}, {
					name : 'licencedate' //颁发时间
				},{
					name : 'remarks' //备注
				},{
					name :'bfevent'
				}],
				baseParams : {
					eid : enterpriseId
				},
				listeners : {
					'load':function(){
						gPanel_prize.getSelectionModel().selectFirstRow() ;
					},
					'loadexception' : function(){
						Ext.ux.Toast.msg('提示','数据加载失败，请保证网络畅通！');			
					}
				}
			});
			var button_add = new CS.button.AButton({
				handler : function() {
					var addPrize = new Ext.form.FormPanel({
						id :'addPrize',
						url : __ctxPath + '/creditFlow/customer/enterprise/addEnterprisePrize.do',
						monitorValid : true,
						bodyStyle:'padding:10px',
						autoScroll : true ,
						labelAlign : 'right',
						buttonAlign : 'center',
						layout:'column',
						height : 240,
						frame : true ,
						items : [{
						     columnWidth:1,
				             layout: 'form',
				             labelWidth : 70,
							 defaults : {anchor : anchor},
							 items : [{
						 			xtype : 'textfield',
				 					fieldLabel : '证书名称',
				 					name : 'enPrize.certificatename',
				 					allowBlank : false,
				 					blankText : '必填信息'
							 },{
							 		xtype : 'textfield',
				 					fieldLabel : '颁发事件',
				 					name : 'enPrize.bfevent'
							 }]
						},{
							 columnWidth: .5,
				             layout: 'form',
				             labelWidth : 70,
							 defaults : {anchor : anchor},
							 items : [{
									xtype : 'textfield' ,
							 		fieldLabel : '证书编号',
									name : 'enPrize.certificatecode',
									allowBlank : false,
									blankText : '必填信息'
							 },{
								 	xtype : 'textfield',      
									fieldLabel : '获奖人',
									name : 'enPrize.prizerpname',
									allowBlank : false,
									blankText : '必填信息'
							 }]
						},{
							 columnWidth: .5,
				             layout: 'form',
				             labelWidth : 90,
							 defaults : {anchor : anchor},
							 items : [{
									xtype : 'datefield',      
								 	fieldLabel : '颁发日期',
									name : 'enPrize.licencedate',
									allowBlank : false,
									blankText : '必填信息',
						            format : 'Y-m-d'
							 },{
						 			xtype : 'textfield',
									fieldLabel : '颁发机构名称',
									name : 'enPrize.organname',
									allowBlank : false,
									blankText : '必填信息'
							 }]
						},{
							 columnWidth:1,
				             layout: 'form',
				             labelWidth : 70,
							 defaults : {anchor : anchor},
							 items : [{
									xtype : 'textarea',
									fieldLabel : '备注',
									name : 'enPrize.remarks'
							 }]
						},{
							xtype : 'hidden',
							name : 'enPrize.enterpriseid',
							value : enterpriseId
						}],
						buttons : [ {
							text : '保存',
							formBind : true,
							iconCls : 'submitIcon',
							handler : function() {
							addPrize.getForm().submit({
									method : 'POST',
									waitTitle : '连接',
									waitMsg : '消息发送中...',
									success : function(form ,action) {
										Ext.ux.Toast.msg('状态', '保存成功!');
												jStore_prize.reload();
												Ext.getCmp('win_add').destroy();
									},
									failure : function(form, action) {
										if(action.response.status==0){
											Ext.ux.Toast.msg('状态','连接失败，请保证服务已开启');
										}else if(action.response.status==-1){
											Ext.ux.Toast.msg('状态','连接超时，请重试!');
										}else{
											Ext.ux.Toast.msg('状态','保存失败!');		
										}
									}
								});
							}
						}]
					})
					var win_add = new Ext.Window({
						id : 'win_add',
						title: '新增<font color=red><'+obj.data.shortname+'></font>企业获奖资质',
						layout : 'fit',
						width :(screen.width-180)*0.5 - 20,
						height : 280,
						closable : true,
						constrainHeader : true ,
						collapsible : true,
						resizable : true,
						plain : true,
						border : false,
						autoScroll : true ,
						modal : true,
						maximizable : true,
						bodyStyle:'overflowX:hidden',
						buttonAlign : 'right',
						items :[addPrize],
						listeners : {
							'beforeclose' : function(){
								if(addPrize != null){
									if(addPrize.getForm().isDirty()){
										Ext.Msg.confirm('操作提示','数据被修改过,是否保存',function(btn){
											if(btn=='yes'){
												addPrize.buttons[0].handler.call() ;
											}else{
												addPrize.getForm().reset() ;
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
					var selected = gPanel_prize.getSelectionModel().getSelected();
					if (null == selected) {
						Ext.ux.Toast.msg('状态', '请选择一条记录!');
					}else{
						var id = selected.get('id');
						Ext.Ajax.request({
							url : __ctxPath + '/creditFlow/customer/enterprise/getEnterprisePrize.do',
							method : 'POST',
							success : function(response,request) {
								var obj = Ext.util.JSON.decode(response.responseText);
								var prizeData = obj.data ;
								var updatePrize = new Ext.form.FormPanel({
									id :'updatePrize',
									url : __ctxPath + '/creditFlow/customer/enterprise/updateEnterprisePrize.do',
									monitorValid : true,
									bodyStyle:'padding:10px',
									autoScroll : true ,
									labelAlign : 'right',
									buttonAlign : 'center',
									layout:'column',
									height : 240,
									frame : true ,
									items : [{
									     columnWidth:1,
							             layout: 'form',
							             labelWidth : 70,
										 defaults : {anchor : anchor},
										 items : [{
									 			xtype : 'textfield',
							 					fieldLabel : '证书名称',
							 					name : 'enPrize.certificatename',
							 					allowBlank : false,
							 					blankText : '必填信息',
							 					value : prizeData.certificatename
										 },{
										 		xtype : 'textfield',
							 					fieldLabel : '颁发事件',
							 					name : 'enPrize.bfevent',
							 					value : prizeData.bfevent
										 }]
									},{
										 columnWidth: .5,
							             layout: 'form',
							             labelWidth : 70,
										 defaults : {anchor : anchor},
										 items : [{
												xtype : 'textfield' ,
										 		fieldLabel : '证书编号',
												name : 'enPrize.certificatecode',
												allowBlank : false,
												blankText : '必填信息',
												value : prizeData.certificatecode
										 },{
											 	xtype : 'textfield',      
												fieldLabel : '获奖人',
												name : 'enPrize.prizerpname',
												allowBlank : false,
												blankText : '必填信息',
												value : prizeData.prizerpname
										 }]
									},{
										 columnWidth: .5,
							             layout: 'form',
							             labelWidth : 90,
										 defaults : {anchor : anchor},
										 items : [{
												xtype : 'datefield',      
											 	fieldLabel : '颁发日期',
												name : 'enPrize.licencedate',
												allowBlank : false,
												blankText : '必填信息',
									            format : 'Y-m-d',
									            value : prizeData.licencedate
										 },{
									 			xtype : 'textfield',
												fieldLabel : '颁发机构名称',
												name : 'enPrize.organname',
												allowBlank : false,
												blankText : '必填信息',
												value : prizeData.organname
										 }]
									},{
										 columnWidth:1,
							             layout: 'form',
							             labelWidth : 70,
										 defaults : {anchor : anchor},
										 items : [{
												xtype : 'textarea',
												fieldLabel : '备注',
												name : 'enPrize.remarks',
												value : prizeData.remarks
										 }]
									},{
										xtype : 'hidden',
										name : 'enPrize.enterpriseid',
										value : prizeData.enterpriseid
									},{
										xtype : 'hidden',
										name : 'enPrize.id',
										value : prizeData.id
									}],
									buttons : [ {
										text : '保存',
										formBind : true,
										iconCls : 'submitIcon',
										handler : function() {
										updatePrize.getForm().submit({
												method : 'POST',
												waitTitle : '连接',
												waitMsg : '消息发送中...',
												success : function(form ,action) {
													Ext.ux.Toast.msg('状态', '保存成功!');
															jStore_prize.reload();
															Ext.getCmp('win_update').destroy();
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
								})
								var win_update = new Ext.Window({
									id : 'win_update',
									title: '编辑<font color=red><'+obj.data.shortname+'></font>企业获奖资质',
									layout : 'fit',
									width :(screen.width-180)*0.5 - 20,
									height : 280,
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
									items :[updatePrize],
									listeners : {
										'beforeclose' : function(){
											if(updatePrize != null){
												if(updatePrize.getForm().isDirty()){
													Ext.Msg.confirm('操作提示','数据被修改过,是否保存',function(btn){
														if(btn=='yes'){
															updatePrize.buttons[0].handler.call() ;
														}else{
															updatePrize.getForm().reset() ;
															win_update.destroy() ;
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
					var selected = gPanel_prize.getSelectionModel().getSelected();
					if (null == selected) {
						Ext.ux.Toast.msg('状态', '请选择一条记录!');
					}else{
						var id = selected.get('id');
						Ext.MessageBox.confirm('确认删除', '是否确认执行删除 ', function(btn) {
							if (btn == 'yes') {
								Ext.Ajax.request({
									url : __ctxPath + '/creditFlow/customer/enterprise/deleteRsEnterprisePrize.do',
									method : 'POST',
									success : function() {
										Ext.ux.Toast.msg('状态', '删除成功!');
										jStore_prize.reload() ;
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
					var selected = gPanel_prize.getSelectionModel().getSelected();
					if (null == selected) {
						Ext.ux.Toast.msg('状态', '请选择一条记录!');
					}else{
						var id = selected.get('id');
						seePrizeShow(id);
					}
			}
			});
			
			var cModel_prize = new Ext.grid.ColumnModel([
				new Ext.grid.RowNumberer( {
					header : '序号',
					width : 35
				}),
				{
					header : "证书名称",
					width : 120,
					sortable : true, 
					dataIndex : 'certificatename'
				}, {
					header : "证书编号",
					width : 120,
					sortable : true,
					dataIndex : 'certificatecode'
					},
				{
					header : "颁发机构名称",
					width : 120,
					sortable : true,
					dataIndex : 'organname'
				}, {
					header : "获奖人",
					width : 120,
					sortable : true,
					dataIndex : 'prizerpname'
				},{
					header : "颁发事件",
					width : 120,
					sortable : true,
					dataIndex : 'bfevent'
				}, {
					header : "颁发时间",
					width : 100,
					sortable : true,
					dataIndex : 'licencedate'
				},{
					header : "备注",
					width : 100,
					sortable : true,
					dataIndex : 'remarks'
				}]);
			var gPanel_prize = new CS.grid.GridPanel( {
				pageSize : pageSize,
				store : jStore_prize,
				autoWidth : true,
				height:440,
				colModel : cModel_prize,
				autoExpandColumn : 7,
				stripeRows : true,
				border : false ,
				selModel : new Ext.grid.RowSelectionModel(),
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
					store : jStore_prize
				}),
				tbar :isReadOnly?[button_see]:[button_add,button_see,button_update,button_delete],
				listeners : {
					'rowdblclick' : function(grid,index,e){
						var id = grid.getSelectionModel().getSelected().get('id');
						seePrizeShow(id);
					}
				}
			});
			var seePrizeShow = function(id){
				Ext.Ajax.request({
					url : __ctxPath + '/creditFlow/customer/enterprise/getEnterprisePrize.do',
					method : 'POST',
					success : function(response,request) {
						var obj = Ext.util.JSON.decode(response.responseText);
						var prizeData = obj.data ;
						var seePrize = new Ext.form.FormPanel({
							id :'seePrize',
							bodyStyle:'padding:10px',
							autoScroll : true ,
							labelAlign : 'right',
							buttonAlign : 'center',
							layout:'column',
							height : 240,
							frame : true ,
							items : [{
							     columnWidth:1,
					             layout: 'form',
					             labelWidth : 70,
								 defaults : {anchor : anchor},
								 items : [{
							 			xtype : 'textfield',
					 					fieldLabel : '证书名称',
					 					readOnly  : true,
										cls : 'readOnlyClass',
					 					value : prizeData.certificatename
								 },{
								 		xtype : 'textfield',
					 					fieldLabel : '颁发事件',
					 					readOnly  : true,
										cls : 'readOnlyClass',
					 					value : prizeData.bfevent
								 }]
							},{
								 columnWidth: .5,
					             layout: 'form',
					             labelWidth : 70,
								 defaults : {anchor : anchor},
								 items : [{
										xtype : 'textfield' ,
								 		fieldLabel : '证书编号',
										readOnly  : true,
										cls : 'readOnlyClass',
										value : prizeData.certificatecode
								 },{
									 	xtype : 'textfield',      
										fieldLabel : '获奖人',
										readOnly  : true,
										cls : 'readOnlyClass',
										value : prizeData.prizerpname
								 }]
							},{
								 columnWidth: .5,
					             layout: 'form',
					             labelWidth : 90,
								 defaults : {anchor : anchor},
								 items : [{
										xtype : 'textfield',      
									 	fieldLabel : '颁发日期',
										readOnly  : true,
										cls : 'readOnlyClass',
							            value : prizeData.licencedate
								 },{
							 			xtype : 'textfield',
										fieldLabel : '颁发机构名称',
										readOnly  : true,
										cls : 'readOnlyClass',
										value : prizeData.organname
								 }]
							},{
								 columnWidth:1,
					             layout: 'form',
					             labelWidth : 70,
								 defaults : {anchor : anchor},
								 items : [{
										xtype : 'textarea',
										fieldLabel : '备注',
										readOnly  : true,
										cls : 'readOnlyClass',
										value : prizeData.remarks
								 }]
							}]
						})
						var window_see = new Ext.Window({
							title: '查看<font color=red><'+obj.data.shortname+'></font>企业获奖资质',
							layout : 'fit',
							closable : true,
							resizable : true,
							constrainHeader : true ,
							collapsible : true, 
							plain : true,
							border : false,
							modal : true,
							width :(screen.width-180)*0.5 - 20,
							height : 280,
							autoScroll : true ,
							bodyStyle:'overflowX:hidden',
							buttonAlign : 'right',
							items :[seePrize]
						}).show();			
					},
					failure : function(response) {
							Ext.ux.Toast.msg('状态','操作失败，请重试');	
					},
					params: { id: id }
				});	
			}
			jStore_prize.load({
				params : {
					start : 0,
					limit : pageSize
				}
			});
			var win_listEnterprisePrize = new Ext.Window({
				title : '<font color=red><'+obj.data.shortname+'></font>企业获奖资质',
				width :(screen.width-180)*0.5 + 30,
				height : 400,
				buttonAlign : 'center',
				layout : 'fit',
				modal : true,
				maximizable : true,
				constrainHeader : true ,
				collapsible : true, 
				items :[gPanel_prize]
			}).show();
    	},   
    	failure: function(response, option) {   
        	return true;   
        	Ext.ux.Toast.msg('友情提示',"异步通讯失败，请于管理员联系！");   
    	}   
	});
}