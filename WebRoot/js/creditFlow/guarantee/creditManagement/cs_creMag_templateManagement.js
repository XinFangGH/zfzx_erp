var autoHight = Ext.getBody().getHeight();
var autoWidth = Ext.getBody().getWidth();
var dic_TreePanel;
var nodeSelected;
var dic_Root;

/**资信评估----模板管理  jiang */

Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget ='side';
	
	if(typeof(AreaDicAjaxServ)=="undefined"){
		//Ext.Msg.alert("警告提示","请先设置DWR，并实例化AreaDicAjaxServ");
	}else{
		mgrInit.setMgr(AreaDicAjaxServ);
		//Ext.Msg.alert("AreaDicAjaxServ设置成功");
		//Ext.Msg.alert(mgr);
	}
	
	var JStore_creditTemplate = new Ext.data.JsonStore({
		url : 'getCreditTemplate.action',
		root : 'topics',
		totalProperty : 'totalProperty',
		baseParams  : {
			parentId : '0'
		},
		fields : [ {
			id : 'id',
			name : 'id'
		},{
			id : 'text',
			name : 'text'
		},{
			id : 'lable',
			name :'lable'
		} ]
	});
	JStore_creditTemplate.load();
	
	var dic_CModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer( {
		header : '序号',
		width : 30
		}),
	{
		header : "字典类型",
		width : 192,
		sortable : true,
		dataIndex : 'text'
	},{
		header : "唯一标示",
		width : 100,
		sortable : true,
		dataIndex : 'lable'
	}
	]);
	var myMask = new Ext.LoadMask(Ext.getBody(), {
			msg : "加载数据中······,请稍后······"
	});

	var dic_GPanel = new Ext.grid.GridPanel({
		id :'dic_GPanel',
		height : 500,
		width :300,
		store : JStore_creditTemplate,
		frame : false,
		iconCls:'icon-grid',
		colModel : dic_CModel,
		selModel : new Ext.grid.RowSelectionModel(),
		stripeRows : true,
		loadMask : myMask,
		listeners : { 
					'rowdblclick' : function(grid,index,e){
					var id = grid.getSelectionModel().getSelected().get('id');
					var text = grid.getSelectionModel().getSelected().get('text');
					if(typeof(dic_TreePanel) != "undefined"){
							dic_TreePanel.destroy();
					}
					var dic_TreeLoader = new Ext.tree.TreeLoader({
						dataUrl : 'getTemplateList.action?parentId=' + id
					})
					dic_Root = new Ext.tree.AsyncTreeNode({
						id : id,
						text : text
					});
					//alert(id + text);
					dic_TreePanel = new Ext.Panel({
						id : 'dic_TreePanel',
						frame : false,
						autoScroll : true,
						collapsible : false,
						titleCollapse : false,
						items : [{
							id :'tree_panel',
							xtype : 'treepanel',
							border : false,
							iconCls : 'icon-nav',
							rootVisible : true,
							autoScroll : true,
							loader : dic_TreeLoader,
							root : dic_Root,
							listeners : {
								'contextmenu' : function(node,e) {
									//alert("node" +node.parentNode); //当前节点的父节点
									//alert("tree" +node.getOwnerTree()); //获得当前节点所在的树
									//alert(node);
									nodeSelected = node;
									nodeSelected.select();
									setCatalogMenuFun(node,e);
									
								}
							}
						}]
					
					})
						
						dic_treePan.add(dic_TreePanel);
						dic_treePan.doLayout();
						dic_Panel.doLayout();
						dic_Viewport.doLayout();
				}
			}  //

	});
	var dic_treePan = new Ext.Panel({
		id :'dic_treePan',
		layout : 'fit',
		autoHeight : false,
		height : 500,
		autoWidth : true,
		autoScroll : true ,
		frame : true
	})
		//三操作按钮
	var add_button = new Ext.Button({
				text : '增加',
				scope : this,
				tooltip : '增加字典类型',
				iconCls : 'addIcon',
				handler : function(){
					var formPanel = new Ext.FormPanel({
						labelAlign: 'right',
						buttonAlign : 'center',
						url : 'addCreditTemplate.action',
						bodyStyle:'padding:25px 25px 25px',
						labelWidth: 110,
						frame : false,
						//title : '增加字典类型',
						waitMsgTarget: true,
						monitorValid:true,
						width : 500,
						items : [{
							xtype : 'textfield',
							fieldLabel : '字典类型 ',
							name : 'text',
							width : 150,
							allowBlank : false,
							blankText : '必填信息',
	                    	anchor:'80%'
						}],
						 buttons : [{  	
			             	text: '提交',
			             	formBind : true,
			            	handler : function(){
			            	formPanel.getForm().submit({
									method : 'POST',
									waitTitle : '连接',
									waitMsg : '消息发送中...',
									success : function() {
										Ext.Msg.alert('状态', '添加成功!',
												function(btn, text) {
													if (btn == 'ok') {
														JStore_creditTemplate.reload();
														adwin.destroy();
													}
												});
									},
									failure : function(form, action) {
										Ext.Msg.alert('状态','添加失败!');									
									}
								})
			            }
					    },{
		            		text: '重置',
		            		handler : function(){
	            			formPanel.getForm.reset();
	            	}
			        }]
						
					})
					var adwin = new Ext.Window({
						id : 'uwin',
						layout :'fit',
						title : '增加字典类型',
						width : 400,
						height : 300,
						maximizable : true,
						modal : true,
						items : [formPanel]
					})
					adwin.show();
				}
			})
			
	var update_button = new Ext.Button({
				text : '修改',
				tooltip : '修改选中的类别',
				iconCls : 'updateIcon',
				scope : this,
				handler : function(){
					var selected = dic_GPanel.getSelectionModel().getSelected();//选中当前行
					if(null == selected){
						Ext.MessageBox.alert('状态', '请选择一条记录!');
					}else{
						var id = selected.get('id');  //获得当前行的ID值
						Ext.Ajax.request({
							url : 'getTemplateById.action',
							method : 'POST',
							success : function(response,request){
								obj = Ext.util.JSON.decode(response.responseText);
								var _update_panel = new Ext.FormPanel({
									url:'updateDicType.action',
									labelAlign : 'right',
									buttonAlign : 'center',
									bodyStyle:'padding:25px 25px 25px',
									height : 300,
									frame : false,
									labelWidth : 110,
									monitorValid : true,
						        	width: 400,
						        	items : [{
						        		xtype : 'textfield',
										fieldLabel : '字典类型',
										name : 'text',
										value: obj.data.text,
										width : 150,
										allowBlank : false,
										blankText : '必填信息'
						        	},{
						        		xtype : 'textfield',
										fieldLabel : '唯一标识',
										name : 'lable',
										width : 150,
										allowBlank : false,
										blankText : '必填信息',
										value: obj.data.lable
						        	}],
						        	buttons :[
						        	{
										text : '提交',
										formBind : true,
										handler : function() {
											_update_panel.getForm().submit({
												method : 'POST',
												waitTitle : '连接',
												waitMsg : '消息发送中...',
												success : function() {
													Ext.Msg.alert('状态', '修改成功!',
															function(btn, text) {
																JStore_creditTemplate.reload();
																UTwin.destroy();
																});
															},
												failure : function(form, action) {
													Ext.Msg.alert('状态','修改失败!');
												}
											});
										}
									
						        	},{
						        		
										text : '取消',
										handler : function(){
											UTwin.destroy();
										}
								
						        	}
						        	]
								})
								var UTwin = new Ext.Window({
									id : 'win',
									layout :'fit',
									title : '编辑字典类型',
									width : 400,
									height : 300,
									maximizable : true,
									modal : true,
									items : [_update_panel]
								})
								UTwin.show();
							},
							failure : function(response) {					
								Ext.Msg.alert('状态','操作失败，请重试');		
							},
							params: { id: id }
							})
					}
					
				},
				listeners :{
					'click' : function(){
					}
				}
			})
			var delete_button = new Ext.Button({
				text : '删除',
				tooltip : '删除选中类别',
				iconCls : 'deleteIcon',
				scope : this,
				handler : function(){
					var selected = dic_GPanel.getSelectionModel().getSelected();
					if( null == selected){
						Ext.MessageBox.alert('状态', '请选择一条记录!');
					}else{
						var id = selected.get('id');
						Ext.MessageBox.confirm('确认删除', '是否确认执行删除 ' +selected.get('text')
							, function(btn) {
						if (btn == 'yes') {
							Ext.Ajax.request({
								url : 'deleteDicType.action',
								method : 'POST',
								success : function() {
									Ext.Msg.alert('状态', '删除成功!');
									JStore_creditTemplate.reload();
								},
								failure : function(result, action) {
									Ext.Msg.alert('状态','删除失败!');
								},
								params: { id: id }
							});
						}
					});
						
					}
					
				},
				listeners :{
					'click' : function(){
					}
				}
			})
		
	var dic_Panel = new Ext.Panel({
		id :'dic_Panel',
		layout : 'column',
		title :'字典类型',
		autoHeight : true,
		//height : 300,
		autoWidth : true,
		autoScroll : true ,
		tbar :[add_button,update_button,delete_button],
		items : [{
					layout : 'fit',
					columnWidth:0.38,
					items : [dic_GPanel]
				},{
					layout : 'fit',
					columnWidth:0.62,
					items : [dic_treePan]
				}]
	});	
	var dic_Viewport = new Ext.Viewport({
		enableTabScroll : true,
		layout : 'fit',
		items : [{
			region : "center",
			layout : 'fit',
			items : [dic_Panel]
		}]
	}) 
	function setCatalogMenuFun(node,e){
		var setCatalogMenu; 
		var nodeId = node.parentNode;
		//alert("node =" +node.parentNode);
		if((nodeId == null)){ //当前节点为根节点
			setCatalogMenu = new Ext.menu.Menu({
				items : [{
					text : '添加目录类型',
					handler : function (){
						addCatalogType(false,node);
					}
				},"-",{
					text : '添加叶子类型',
					handler : function (){
						addCatalogType(true,node);
					}
				}]
			})
		}else if(node.isLeaf()){ //当前节点为叶子节点
			setCatalogMenu = new Ext.menu.Menu({
				items :[{
					text :'编辑叶子类型',
					handler : function (){
						updateCatalogType(node);
					}
				},"-",{
					text : '删除叶子类型',
					handler : function (){
						deleteCatalogType(node);
					}
				}]
			})
		}else if(node.hasChildNodes() && node.parentNode !=null){
			setCatalogMenu = new Ext.menu.Menu({
				items :[{
					text :'添加目录类型',
					handler : function (){
						addCatalogType(false,node);
					}
				},"-",{
					text : '添加叶子类型',
					handler : function (){
						addCatalogType(true,node);
					}
				},"-",{
					text : '修改目录类型',
					handler : function (){
						updateCatalogType(node);
					}
				}]
			})
		}else{  //当期节点为目录节点
			setCatalogMenu = new Ext.menu.Menu({
				items : [{
					text : '添加目录类型',
					handler : function (){
						addCatalogType(false,node);
					}
				},"-",{
					text : '添加叶子类型',
					handler : function (){
						addCatalogType(true,node);
					}
				},"-",{
					text : '修改目录类型',
					handler : function (){
						updateCatalogType(node);
						
					}
				},"-",{
					text : '删除目录类型',
					handler : function (){
						deleteCatalogType(node);
						//delTreeItemComfirm();
					}
				}]
			})
		}
		setCatalogMenu.showAt(e.getPoint());
	}
	//添加目录，添加叶子
	function addCatalogType(isLaef,node){
		var number = node.indexOf(node.lastChild) + 1;
		var menu_Panel = new Ext.Panel({
			id : 'menu_Panel',
			height : 300,
			width :400,
			layout : 'fit',
			frame : false,
			//bodyStyle:'padding:5px 5px 0',
			items : [{
				id : 'menu_form',
				xtype : 'form',
				bodyStyle:'padding:25px 25px 25px',
				labelWidth: 110,
				url :'addArea.action?parentId='+node.id+ '&leaf='+isLaef+'&number='+number,
				waitMsgTarget: true,
			    monitorValid:true,
			    labelAlign: 'right',
			    buttonAlign : 'center',
				items : [{
					xtype : 'textfield',
					fieldLabel : ''+node.text+'子类型' ,
					name : 'text',
					allowBlank : false,
					blankText : '必填信息',
					width : 150,
					anchor:'80%'
				}],
				buttons : [
				{ 	
			            text: '提交',
			            formBind : true,
			            handler : function(){
			            	//alert(menu_Panel.findById('menu_form').getForm());
			            	menu_Panel.findById('menu_form').getForm().submit({
			            		method : 'POST',
			            		waitTitle : '连接',
								waitMsg : '消息发送中...',
								success : function(){
									Ext.Msg.alert('状态','添加成功',function(btn, text){
										if(btn=='ok'){
											win.destroy();
											node.reload();
											node.expand();
										}
									});
									
								},
								failure : function(form, action) {
										Ext.Msg.alert('状态','添加失败!');									
								}
			            	});
			            
			            }
				},{
		            text: '重置',
		            handler : function(){
						menu_Panel.findById('menu_form').getForm().reset();
	            	}
	        
			     }]
			}]
		})
		var win = new Ext.Window({
			id : 'win',
			layout :'fit',
			width : 400,
			height : 300,
			title :'添加'+ node.text +'子类型',
			maximizable : true,
			modal : true,
			items : [menu_Panel]
		})
		win.show();
	}
	function updateCatalogType(node){
		var id = node.id;
		Ext.Ajax.request({
			url :'getIdArea.action',
			method : 'POST',
			success : function (response,request){
				obj = Ext.util.JSON.decode(response.responseText);
				var menu_UPanel = new Ext.Panel({
					id : 'menu_UPanel',
					height : 300,
					width :400,
					layout : 'fit',
					frame : false,
					items : [{
						id : 'menu_Uform',
						xtype : 'form',
						bodyStyle:'padding:25px 25px 25px',
						labelWidth: 110,
						url :'updateArea.action',
						waitMsgTarget: true,
			   	 		monitorValid:true,
			    		labelAlign: 'right',
			    		buttonAlign : 'center',
			    		items : [{
			    			xtype : 'textfield',
							fieldLabel : ''+node.text+'子类型 ',
							name : 'text',
							allowBlank : false,
							blankText : '必填信息',
							width : 150,
							anchor:'80%',
							value: obj.data.text
			    		},{
			    			xtype : 'hidden',
			    			name : 'parentId',
			    			value: obj.data.parentId
			    		},{
			    			xtype : 'hidden',
			    			name :'number',
			    			value: obj.data.number
			    		},{
			    			xtype : 'hidden',
			    			name :'leaf',
			    			value: obj.data.leaf
			    			
			    		}],
			    		buttons :[
			    			
				{ 	
			            text: '提交',
			            formBind : true,
			            handler : function(){
			            	menu_UPanel.findById('menu_Uform').getForm().submit({
			            		method : 'POST',
			            		waitTitle : '连接',
								waitMsg : '消息发送中...',
								success : function(){
									Ext.Msg.alert('状态','修改成功',function(btn, text){
										if(btn=='ok'){
 											uwin.destroy();
											node.parentNode.reload();
											node.parentNode.expand();
										}
									});
									
								},
								failure : function(form, action) {
										Ext.Msg.alert('状态','修改失败!');									
								}
			            	});
			            
			            }
				},{
		            text: '取消',
		            handler : function(){
		            	uwin.destroy();
	            	}
	        
			     }
			    		]
					}]
				})
				var uwin = new Ext.Window({
					id : 'uwin',
					layout :'fit',
					width : 400,
					title  :'编辑'+ node.text +'子类型',
					height : 300,
					maximizable : true,
					modal : true,
					items : [menu_UPanel]
				})
				uwin.show();
			},
			failure : function(response) {					
								Ext.Msg.alert('状态','操作失败，请重试');		
						},
			params: { id: id }
		})
	
		
	}
	function deleteCatalogType(node){
		var id = node.id;
		Ext.MessageBox.confirm('确认删除', '是否确认执行删除 '
				+ node.text, function(btn) {
			if (btn == 'yes') {
				Ext.Ajax.request({
					url : 'deleteDicType.action',
					method : 'POST',
					success : function() {
						//dic_Root.reload();
						node.parentNode.reload();
						node.parentNode.expand();
						Ext.Msg.alert('状态', '删除成功!');
					},
					failure : function(result, action) {
						Ext.Msg.alert('状态','删除失败!');
					},
					params: { id: id }
				});
			}
		});
				
			
	}
	
/*	function delTreeItemComfirm(){
			Ext.Msg.confirm("确认删除", "确定要删除所选节点吗？", function(btn) {
				if (btn == "yes") {
					alert(mgr);
					delTreeItem();
					mgr.ajaxRemoveNode(node.id);
				}
			});
		}
	function delTreeItem(){
			if (nodeSelected != dic_TreePanel.findById('tree_panel').getRootNode()) {
				nodeSelected.remove();
			} else {
				Ext.Msg.alert("警告", "不能删除树的根节点！");
			}
		}*/
	
})