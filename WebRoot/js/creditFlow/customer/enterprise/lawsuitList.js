function lawsuitListWin(orgVal){
	var pageSize = 10 ;
	var anchor = '100%';
	Ext.Ajax.request({   
    	url: __ctxPath + '/creditFlow/customer/enterprise/isEntEmptyEnterprise.do',   
   	 	method:'post',   
    	params:{organizecode:orgVal},  
    	success: function(response, option) {   
        	var obj = Ext.decode(response.responseText);
        	var enterpriseId = obj.data.id;
			var jStore_lawsuit = new Ext.data.JsonStore( {
				url : __ctxPath + '/creditFlow/customer/enterprise/getListEnterpriseLawsuit.do',
				totalProperty : 'totalProperty',
				root : 'topics',
				fields : [ {
					name : 'id'  //案件ID
				},{
					name : 'casename' //案件名称
				}, {
					name : 'event' //事件
				}, {
					name : 'lowcourt' //立案法院
				}, {
					name : 'progress'  //案情进展
				}, {
					name : 'casedate' //立案时间
				},{
					name : 'remarks' //备注
				}],
				baseParams : {
					eid : enterpriseId
				},
				listeners : {
					'load':function(){
						gPanel_lawsuit.getSelectionModel().selectFirstRow() ;
					},
					'loadexception' : function(){
						Ext.ux.Toast.msg('提示','数据加载失败，请保证网络畅通！');			
					}
				}
			});
			var button_add = new CS.button.AButton({
				handler : function() {
					var addLawsuit = new Ext.form.FormPanel({
						id :'addLawsuit',
						url : __ctxPath + '/creditFLow/customer/enterprise/addEnterpriseLawsuit.do',
						monitorValid : true,
						bodyStyle:'padding:10px',
						autoScroll : true ,
						labelAlign : 'right',
						buttonAlign : 'center',
						layout:'column',
						width : 488,
						height : 360,
						frame : true ,
						items : [{
							 columnWidth:1,
				             layout: 'form',
				             labelWidth : 80,
							 defaults : {anchor : anchor},
							 items : [{
								 	xtype : 'textfield',
								 	name : 'enLawsuit.casename',
									fieldLabel : '案件名称',
									allowBlank : false,
									blankText : '必填信息'
							 }]
						},{
							 columnWidth: .5,
				             layout: 'form',
				             labelWidth : 80,
							 defaults : {anchor : anchor},
							 items : [{
						 			xtype : 'textfield',      
									fieldLabel : '立案法院',
									name : 'enLawsuit.lowcourt'
							 }]
						},{
							 columnWidth: .5,
				             layout: 'form',
				             labelWidth : 90,
							 defaults : {anchor : anchor},
							 items :[{
									xtype : 'datefield',      
						 			fieldLabel : '立案日期',
						 			allowBlank : false,
						 			blankText : '立案日期为必填信息',
									name : 'enLawsuit.casedate',
				                    format : 'Y-m-d'
							 }]
						},{
							 columnWidth: 1,
				             layout: 'form',
				             labelWidth : 80,
							 defaults : {anchor : anchor},
							 items :[{
							 	xtype : 'textarea',
					 			fieldLabel : '事件',
								name : 'enLawsuit.event',
								height : 50,
								allowBlank : false,
								blankText : '事件为必填信息'
							 },{
						 		xtype : 'textarea',
								fieldLabel : '案件进展情况',
								height : 50,
								name : 'enLawsuit.progress'
							 },{
								xtype : 'textarea',
								fieldLabel : '备注',
								height : 40,
								name : 'enLawsuit.remarks'
							 }]
						},{
							xtype : 'hidden',
							name : 'enLawsuit.enterpriseid',
							value : enterpriseId
						}],
						buttons : [ {
							text : '保存',
							formBind : true,
							iconCls : 'submitIcon',
							handler : function() {
							addLawsuit.getForm().submit({
									method : 'POST',
									waitTitle : '连接',
									waitMsg : '消息发送中...',
									success : function(form ,action) {
										Ext.ux.Toast.msg('状态', '保存成功!');
												jStore_lawsuit.reload();
												Ext.getCmp('win_lawsuit').destroy();
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
					var win_lawsuit = new Ext.Window({
						id : 'win_lawsuit',
						title: '新增<font color=red><'+obj.data.shortname+'></font>诉讼情况信息',
						layout : 'fit',
						width :(screen.width-180)*0.5 - 20,
						height : 320,
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
						items:[addLawsuit],
						listeners : {
							'beforeclose' : function(){
								if(addLawsuit != null){
									if(addLawsuit.getForm().isDirty()){
										Ext.Msg.confirm('操作提示','数据被修改过,是否保存',function(btn){
											if(btn=='yes'){
												addLawsuit.buttons[0].handler.call() ;
											}else{
												addLawsuit.getForm().reset() ;
												win_lawsuit.destroy() ;
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
					var selected = gPanel_lawsuit.getSelectionModel().getSelected();
					if (null == selected) {
						Ext.ux.Toast.msg('状态', '请选择一条记录!');
					}else{
						var id = selected.get('id');
						Ext.Ajax.request({
							url : __ctxPath + '/creditFlow/customer/enterprise/getIdEnterpriseLawsuit.do',
							method : 'POST',
							success : function(response,request) {
								var obj = Ext.util.JSON.decode(response.responseText);
								var lawsuitData = obj.data ;
								var updateLawsuit = new Ext.form.FormPanel({
									id :'updateLawsuit',
									url : 'updateEnterpriseLawsuit.do',
									monitorValid : true,
									bodyStyle:'padding:10px',
									autoScroll : true ,
									labelAlign : 'right',
									buttonAlign : 'center',
									layout:'column',
									width : 488,
									height : 360,
									frame : true ,
									items : [{
										 columnWidth:1,
							             layout: 'form',
							             labelWidth : 80,
										 defaults : {anchor : anchor},
										 items : [{
											 	xtype : 'textfield',
											 	name : 'enLawsuit.casename',
												fieldLabel : '案件名称',
												allowBlank : false,
												blankText : '必填信息',
												value : lawsuitData.casename
										 }]
									},{
										 columnWidth: .5,
							             layout: 'form',
							             labelWidth : 80,
										 defaults : {anchor : anchor},
										 items : [{
									 			xtype : 'textfield',      
												fieldLabel : '立案法院',
												name : 'enLawsuit.lowcourt',
												value : lawsuitData.lowcourt
										 }]
									},{
										 columnWidth: .5,
							             layout: 'form',
							             labelWidth : 90,
										 defaults : {anchor : anchor},
										 items :[{
												xtype : 'datefield',      
									 			fieldLabel : '立案日期',
									 			allowBlank : false,
									 			blankText : '立案日期为必填信息',
												name : 'enLawsuit.casedate',
							                    format : 'Y-m-d',
							                    value : lawsuitData.casedate
										 }]
									},{
										 columnWidth: 1,
							             layout: 'form',
							             labelWidth : 80,
										 defaults : {anchor : anchor},
										 items :[{
										 	xtype : 'textarea',
								 			fieldLabel : '事件',
											name : 'enLawsuit.event',
											height : 50,
											allowBlank : false,
											blankText : '事件为必填信息',
											value : lawsuitData.event
										 },{
									 		xtype : 'textarea',
											fieldLabel : '案件进展情况',
											height : 50,
											name : 'enLawsuit.progress',
											value : lawsuitData.progress
										 },{
											xtype : 'textarea',
											fieldLabel : '备注',
											height : 40,
											name : 'enLawsuit.remarks',
											value : lawsuitData.remarks
										 }]
									},{
										xtype : 'hidden',
										name : 'enLawsuit.enterpriseid',
										value : lawsuitData.enterpriseid
									},{
										xtype : 'hidden',
										name : 'enLawsuit.id',
										value : lawsuitData.id
									}],
									buttons : [ {
										text : '保存',
										formBind : true,
										iconCls : 'submitIcon',
										handler : function() {
										updateLawsuit.getForm().submit({
												method : 'POST',
												waitTitle : '连接',
												waitMsg : '消息发送中...',
												success : function(form ,action) {
													Ext.ux.Toast.msg('状态', '保存成功!');
															jStore_lawsuit.reload();
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
									title: '编辑<font color=red><'+obj.data.shortname+'></font>企业诉讼情况',
									layout : 'fit',
									width :(screen.width-180)*0.5 - 20,
									height : 320,
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
									items :[updateLawsuit],
									listeners : {
										'beforeclose' : function(){
											if(updateLawsuit != null){
												if(updateLawsuit.getForm().isDirty()){
													Ext.Msg.confirm('操作提示','数据被修改过,是否保存',function(btn){
														if(btn=='yes'){
															updateLawsuit.buttons[0].handler.call() ;
														}else{
															updateLawsuit.getForm().reset() ;
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
					var selected = gPanel_lawsuit.getSelectionModel().getSelected();
					if (null == selected) {
						Ext.ux.Toast.msg('状态', '请选择一条记录!');
					}else{
						var id = selected.get('id');
						Ext.MessageBox.confirm('确认删除', '是否确认执行删除 ', function(btn) {
							if (btn == 'yes') {
								Ext.Ajax.request({
									url : __ctxPath + '/creditFlow/customer/enterprise/deleteRsEnterpriseLawsuit.do',
									method : 'POST',
									success : function() {
										Ext.ux.Toast.msg('状态', '删除成功!');
										jStore_lawsuit.reload() ;
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
					var selected = gPanel_lawsuit.getSelectionModel().getSelected();
					if (null == selected) {
						Ext.ux.Toast.msg('状态', '请选择一条记录!');
					}else{
						var id = selected.get('id');
						seeLawsuitShow(id);
					}
			}
			});
			
			var cModel_lawsuit = new Ext.grid.ColumnModel([
				new Ext.grid.RowNumberer( {
					header : '序号',
					width : 35
				}),
				{
					header : "案件名称",
					width : 140,
					sortable : true, 
					dataIndex : 'casename'
				}, {
					header : "立案时间",
					width : 100,
					sortable : true,
					dataIndex : 'casedate'
				},{
					header : "事件",
					width : 160,
					sortable : true,
					dataIndex : 'event'
				}, {
					header : "立案法院",
					width : 120,
					sortable : true,
					dataIndex : 'lowcourt'
				},
				 {
					header : "案情进展",
					width : 140,
					sortable : true,
					dataIndex : 'progress'
				},{
					header : "备注",
					width : 140,
					sortable : true,
					dataIndex : 'remarks'
				}]);
				
			var gPanel_lawsuit = new Ext.grid.GridPanel( {
				pageSize : pageSize,
				store : jStore_lawsuit,
				autoWidth : true,
				height:440,
				colModel : cModel_lawsuit,
				autoExpandColumn : 6,
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
					store : jStore_lawsuit
				}),
				tbar : [button_add,button_see,button_update,button_delete],
				listeners : {
					'rowdblclick' : function(grid,index,e){
						var id = grid.getSelectionModel().getSelected().get('id');
						seeLawsuitShow(id);
					}
				}
			});
			var seeLawsuitShow = function(id){
				Ext.Ajax.request({
					url : __ctxPath + '/creditFlow/customer/enterprise/getIdEnterpriseLawsuit.do',
					method : 'POST',
					success : function(response,request) {
						var obj = Ext.util.JSON.decode(response.responseText);
						var lawsuitData = obj.data ;
						var seeLawsuit = new Ext.form.FormPanel({
							id :'seeLawsuit',
							bodyStyle:'padding:10px',
							autoScroll : true ,
							labelAlign : 'right',
							buttonAlign : 'center',
							layout:'column',
							width : 488,
							height : 360,
							frame : true ,
							items : [{
								 columnWidth:1,
					             layout: 'form',
					             labelWidth : 80,
								 defaults : {anchor : anchor},
								 items : [{
									 	xtype : 'textfield',
									 	readOnly  : true,
										cls : 'readOnlyClass',
										fieldLabel : '案件名称',
										value : lawsuitData.casename
								 }]
							},{
								 columnWidth: .5,
					             layout: 'form',
					             labelWidth : 80,
								 defaults : {anchor : anchor},
								 items : [{
							 			xtype : 'textfield',      
										fieldLabel : '立案法院',
										readOnly  : true,
										cls : 'readOnlyClass',
										value : lawsuitData.lowcourt
								 }]
							},{
								 columnWidth: .5,
					             layout: 'form',
					             labelWidth : 90,
								 defaults : {anchor : anchor},
								 items :[{
										xtype : 'datefield',      
							 			fieldLabel : '立案日期',
							 			readOnly  : true,
										cls : 'readOnlyClass',
					                    format : 'Y-m-d',
					                    value : lawsuitData.casedate
								 }]
							},{
								 columnWidth: 1,
					             layout: 'form',
					             labelWidth : 80,
								 defaults : {anchor : anchor},
								 items :[{
								 	xtype : 'textarea',
						 			fieldLabel : '事件',
									height : 50,
									readOnly  : true,
									cls : 'readOnlyClass',
									value : lawsuitData.event
								 },{
							 		xtype : 'textarea',
									fieldLabel : '案件进展情况',
									height : 50,
									readOnly  : true,
									cls : 'readOnlyClass',
									value : lawsuitData.progress
								 },{
									xtype : 'textarea',
									fieldLabel : '备注',
									height : 40,
									name : 'enLawsuit.remarks',
									value : lawsuitData.remarks,
									readOnly  : true,
									cls : 'readOnlyClass'
								 }]
							}]
						})
						var window_lawsuit = new Ext.Window({
							id : 'window_lawsuit',
							title: '查看<font color=red><'+obj.data.shortname+'></font>企业诉讼情况',
							layout : 'fit',
							closable : true,
							resizable : true,
							constrainHeader : true ,
							collapsible : true, 
							plain : true,
							border : false,
							modal : true,
							width :(screen.width-180)*0.5 - 20,
							height : 320,
							autoScroll : true ,
							bodyStyle:'overflowX:hidden',
							buttonAlign : 'right',
							items:[seeLawsuit]
						}).show();			
					},
					failure : function(response) {
							Ext.ux.Toast.msg('状态','操作失败，请重试');	
					},
					params: { id: id }
				});	
			}
			jStore_lawsuit.load({
				params : {
					start : 0,
					limit : pageSize
				}
			});
			
			var win_listEnterpriseLawsuit = new Ext.Window({
				title : '<font color=red><'+obj.data.shortname+'></font>企业诉讼情况',
				width :(screen.width-180)*0.5 + 30,
				height : 400,
				buttonAlign : 'center',
				layout : 'fit',
				modal : true,
				maximizable : true,
				constrainHeader : true ,
				collapsible : true, 
				items :[gPanel_lawsuit]
			}).show();
    	},   
    	failure: function(response, option) {   
        	return true;   
        	Ext.ux.Toast.msg('友情提示',"异步通讯失败，请于管理员联系！");   
    	}   
	});
}