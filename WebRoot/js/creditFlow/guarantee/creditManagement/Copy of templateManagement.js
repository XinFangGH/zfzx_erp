var autoHight = Ext.getBody().getHeight();
var autoWidth = Ext.getBody().getWidth();
var dic_TreePanel;
var nodeSelected;
var dic_Root;


Ext.onReady(function(){
	Ext.BLANK_IMAGE_URL = '/creditBusiness1.0/ext/resources/images/default/s.gif';
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget ='side';
	
	if(typeof(AreaDicAjaxServ)=="undefined"){
		//Ext.Msg.alert("警告提示","请先设置DWR，并实例化AreaDicAjaxServ");
	}else{
		mgrInit.setMgr(AreaDicAjaxServ);
		//Ext.Msg.alert("AreaDicAjaxServ设置成功");
		//Ext.Msg.alert(mgr);
	}
	
	var dic_JStore = new Ext.data.JsonStore({
		url : 'ratingTemplateList.action',
		root : 'topics',
		totalProperty : 'totalProperty',
		fields : [ {
			id : 'id',
			name : 'id'
		},{
			id : 'templateName',
			name : 'templateName'
		},{
			id : 'customerType',
			name : 'customerType'
		},{
			id : 'applyPoint',
			name : 'applyPoint'
		},{
			id : 'careteTime',
			name : 'careteTime'
		},{
			id : 'subTemplateIndicator',
			name : 'subTemplateIndicator'
		} ]
	});
	dic_JStore.load();
	
	var dic_CModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer( {
		header : '序号',
		width : 40
		}),
	{
		header : "模板名称",
		width : 100,
		sortable : true,
		dataIndex : 'templateName'
	},{
		header : "客户类型",
		width : 60,
		sortable : true,
		dataIndex : 'customerType'
	},{
		header : "适用特征",
		width : 100,
		sortable : true,
		dataIndex : 'applyPoint'
	},{
		header : "创建时间",
		width : 100,
		sortable : true,
		dataIndex : 'careteTime'
	} ]);
	var myMask = new Ext.LoadMask(Ext.getBody(), {
			msg : "加载数据中······,请稍后······"
	});

	var dic_GPanel = new Ext.grid.GridPanel({
		id :'dic_GPanel',
		height : Ext.getBody().getHeight() - 60,
		store : dic_JStore,
		frame : false,
		iconCls:'icon-grid',
		colModel : dic_CModel,
		autoExpandColumn : 4,
		selModel : new Ext.grid.RowSelectionModel(),
		stripeRows : true,
		loadMask : myMask,
		listeners : {
					'rowdblclick' : function(grid,index,e){
						var templateId = grid.getSelectionModel().getSelected().get('id');
						var templateName = grid.getSelectionModel().getSelected().get('templateName');
						
						Ext.Ajax.request({
							url : 'countTemplateScore.action',
							method : 'POST',
							success : function(response,request){
								obj = Ext.util.JSON.decode(response.responseText);
								Ext.getCmp('label_temp').setText('[' + templateName + ']'+ '分数：');
								Ext.getCmp('label_score').setText('<font color=red>' + obj.data.score +'</font>');
							},
							failure : function(result, action) {
								Ext.Msg.alert('状态','操作失败!');
							},
							params: { id: templateId}
						});
						
						if(typeof(dic_TreePanel) != "undefined"){
								dic_TreePanel.destroy();
						}
						var dic_TreeLoader = new Ext.tree.TreeLoader({
							dataUrl : 'getTemplateContentList.action?id=' + templateId
						})
						dic_Root = new Ext.tree.AsyncTreeNode({
							id : '0',
							text : templateName
						});
						//alert(id + text);
						dic_TreePanel = new Ext.Panel({
							id : 'dic_TreePanel',
							frame : false,
							autoScroll : true,
							collapsible : false,
							buttonAlign : 'left',
							titleCollapse : false,
							labelAlign : 'right',
							labelWidth : 110,
							items : [{
								id :'tree_panel',
								xtype : 'treepanel',
								border : false,
								iconCls : 'icon-nav',
								rootVisible : true,
//								autoScroll : true,
//								height : 420,
								loader : dic_TreeLoader,
								root : dic_Root,
								listeners : {
									'contextmenu' : function(node,e) {
										//if (node.leaf == true) {
											nodeSelected = node;
											nodeSelected.select();
											setCatalogMenuFun(node, e, templateId);
										//}
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
		height : Ext.getBody().getHeight() - 60,
		autoWidth : true,
//		autoScroll : true,
		frame : true
	})
	//三操作按钮
	var add_button = new Ext.Button({
				text : '增加',
				scope : this,
				tooltip : '增加模板',
				iconCls : 'addIcon',
				handler : function(){
					var mytree = new Ext.tree.TreePanel({
			            animate : true,
			            collapsible : true,
			            rootVisible : false,
			            autoScroll : true,
			            height : 265,
			            width : 350,
			            frame : true,
			            loader : new Ext.tree.TreeLoader( {
			    			dataUrl : 'getIndicatorCheckTree.action'
			    		}),
			    		root : new Ext.tree.AsyncTreeNode( {
			    			id : '0',
			    			text : '根结点'
			    		}),
			            lines : true
			        });
					
					var formPanel = new Ext.FormPanel({
						labelAlign: 'right',
						buttonAlign : 'center',
						url : 'addRatingTemplate.action',
						bodyStyle:'padding:25px 25px 25px',
						labelWidth: 110,
						frame : true,
						waitMsgTarget: true,
						monitorValid:true,
						width : 500,
						items : [{
							xtype : 'textfield',
							fieldLabel : '<font color=red>*</font>模板名称 ',
							name : 'ratingTemplate.templateName',
							width : 150,
							allowBlank : false,
							blankText : '必填信息',
	                    	anchor:'80%'
						},{
							xtype : 'radiogroup',
							fieldLabel : '客户类型',
							name : 'customerType',
							items : [{
								boxLabel : '企业',
								name : 'ratingTemplate.customerType',
								inputValue : '企业',
								checked : true
							}, {
								boxLabel : '个人',
								name : 'ratingTemplate.customerType',
								inputValue : '个人'
							}]
						},{
							xtype : 'textfield',
							fieldLabel : '<font color=red>*</font>适用特性 ',
							name : 'ratingTemplate.applyPoint',
							width : 150,
							allowBlank : false,
							blankText : '必填信息',
	                    	anchor:'80%'
						},{
							id : 'idArray',
							xtype : 'hidden',
							name : 'idArray'
						}, mytree],
						 buttons : [{
			             	text: '提交',
			             	iconCls : 'submitIcon',
			             	formBind : true,
			            	handler : function(){
			            		var b = mytree.getChecked();
			         		 	var checkid = new Array;// 存放选中id的数组
			         		 	for (var i = 0; i < b.length; i++) {
			         		 		checkid.push(b[i].id);// 添加id到数组
			         		 	}
			         		 	//alert(checkid.toString());
			         		 	Ext.getCmp('idArray').setValue(checkid.toString());
			            		formPanel.getForm().submit({
									method : 'POST',
									waitTitle : '连接',
									waitMsg : '消息发送中...',
									success : function() {
										Ext.Msg.alert('状态', '添加成功!',
												function(btn, text) {
														dic_JStore.reload();
														adwin.destroy();
												});
									},
									failure : function(form, action) {
										Ext.Msg.alert('状态','添加失败!');									
									}
								})
			            	}
					    }/*,{
		            		text: '重置',
		            		handler : function(){
	            				formPanel.getForm.reset();
					    	}
			        	}*/]
						
					});
					mytree.expandAll();
					mytree.on('checkchange', function(node, checked) {
			             node.expand();
			             node.attributes.checked = checked;
			             node.eachChild(function(child) {
			                         child.ui.toggleCheck(checked);
			                         child.attributes.checked = checked;
			                         child.fireEvent('checkchange', child,
			                                 checked);
			                     });
			         }, mytree);
					
					var adwin = new Ext.Window({
						id : 'uwin',
						layout :'fit',
						title : '增加模板',
						width : 430,
						height : 455,
						modal : true,
						collapsible : true,
						items : [formPanel]
					})
					adwin.show();
				}
			})
			
	var update_button = new Ext.Button({
				text : '修改',
				tooltip : '修改选中的模板',
				iconCls : 'updateIcon',
				scope : this,
				handler : function(){
					var selected = dic_GPanel.getSelectionModel().getSelected();//选中当前行
					if(null == selected){
						Ext.MessageBox.alert('状态', '请选择一条记录!');
					}else{
						var id = selected.get('id');  //获得当前行的ID值
						Ext.Ajax.request({
							url : 'getRatingTemplateInfo.action',
							method : 'POST',
							success : function(response,request){
								obj = Ext.util.JSON.decode(response.responseText);
								var _update_panel = new Ext.FormPanel({
									url:'updateRatingTemplate.action',
									labelAlign : 'right',
									buttonAlign : 'center',
									bodyStyle:'padding:25px 25px 25px',
									height : 300,
									frame : true,
									labelWidth : 110,
									monitorValid : true,
						        	width: 400,
						        	items : [{
						        		xtype : 'hidden',
										name : 'ratingTemplate.id',
										width : 150,
										value : obj.data.id
						        	},{
										xtype : 'textfield',
										fieldLabel : '<font color=red>*</font>模板名称 ',
										name : 'ratingTemplate.templateName',
										width : 150,
										allowBlank : false,
										blankText : '必填信息',
				                    	anchor:'80%',
				                    	value : obj.data.templateName
									},{
										xtype : 'radiogroup',
										fieldLabel : '客户类型',
										name : 'customerType',
										items : [{
											boxLabel : '企业',
											name : 'ratingTemplate.customerType',
											inputValue : '企业',
											checked : (obj.data.customerType == '企业') ? true : false
										}, {
											boxLabel : '个人',
											name : 'ratingTemplate.customerType',
											inputValue : '个人',
											checked : (obj.data.customerType == '个人') ? true : false
										}]
									},{
										xtype : 'textfield',
										fieldLabel : '<font color=red>*</font>适用特性 ',
										name : 'ratingTemplate.applyPoint',
										width : 150,
										allowBlank : false,
										blankText : '必填信息',
				                    	anchor:'80%',
				                    	value : obj.data.applyPoint
									} ],
						        	buttons :[
						        	{
										text : '提交',
										iconCls : 'submitIcon',
										formBind : true,
										handler : function() {
											_update_panel.getForm().submit({
												method : 'POST',
												waitTitle : '连接',
												waitMsg : '消息发送中...',
												success : function() {
													Ext.Msg.alert('状态', '修改成功!',
														function(btn, text) {
															dic_JStore.reload();
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
								
						        	} ]
								})
								var UTwin = new Ext.Window({
									id : 'win',
									layout :'fit',
									title : '编辑模板类型',
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
						Ext.MessageBox.confirm('确认删除', '是否确认执行删除 ' +selected.get('templateName')
							, function(btn) {
						if (btn == 'yes') {
							Ext.Ajax.request({
								url : 'deleteRatingTemplate.action',
								method : 'POST',
								success : function() {
									Ext.Msg.alert('状态', '删除成功!');
									dic_JStore.reload();
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
			});
	
	
	var button_count = new Ext.Button({
		text : '计算模板分数',
		tooltip : '为该模板添加相应指标',
		iconCls : 'searchIcon',
		scope : this,
		handler : function() {
			var selected = dic_GPanel.getSelectionModel().getSelected();
			var id = selected.get('id');
			var text = selected.get('templateName');
			if (null == selected) {
				Ext.MessageBox.alert('状态', '请选择一条记录!');
			}else{
				Ext.Ajax.request({
					url : 'countTemplateScore.action',
					method : 'POST',
					success : function(response,request){
						obj = Ext.util.JSON.decode(response.responseText);
						Ext.getCmp('label_temp').setText('[' + text + ']' + '分数：');
						Ext.getCmp('label_score').setText('<font color=red>' + obj.data.score +'</font>');
					},
					failure : function(result, action) {
						Ext.Msg.alert('状态','操作失败!');
					},
					params: { id: id}
				});
				
			}
		}
	
	});
	
	var dic_Panel = new Ext.Panel({
		id :'dic_Panel',
		layout : 'column',
		title :'模板类型',
		autoHeight : true,
		//height : 300,
		autoWidth : true,
		autoScroll : true,
		split:true,
		tbar :[add_button, update_button, delete_button, '->', button_count, {id : 'label_temp',text : '模板分数：'}, {id : 'label_score', text : '', width : 100}],
		items : [{
					layout : 'fit',
					columnWidth:0.45,
					//split:true,
					items : [dic_GPanel]
				},{
					layout : 'fit',
					columnWidth:0.55,
					//split:true,
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
	});
	
	
	function setCatalogMenuFun(node, e, templateId){
		var setCatalogMenu; 
		var nodeId = node.parentNode;
		var id = node.id;
		if(node.isLeaf()){ //当前节点为叶子节点
			setCatalogMenu = new Ext.menu.Menu({
				items :[{
					text :'编辑指标选项',
					handler : function (){
						editCatalogType(node);
					}
				},"-",{
					text : '删除此项指标',
					handler : function (){
						deleteCatalogType(node);
					}
				}]
			})
		}else if(id.indexOf('i')!=-1){
			setCatalogMenu = new Ext.menu.Menu({
				items :[{
					text :'添加下级指标',
					handler : function (){
						addCatalogType(false,node, templateId);
					}
				}]
			})
		}
		if (setCatalogMenu != null)
			setCatalogMenu.showAt(e.getPoint());
	}
	//添加目录，添加叶子
	function addCatalogType(isLaef, node, templateId){
		//alert(node.id.substring('1'));
		var menu_Panel = new Ext.Panel({
			id : 'menu_Panel',
			height : 200,
			width :500,
			layout : 'fit',
			frame : true,
			//bodyStyle:'padding:5px 5px 0',
			items : [{
				id : 'menu_form',
				xtype : 'form',
				bodyStyle:'padding:25px 25px 25px',
				labelWidth: 150,
				url :'addTemplateIndicator.action',
				waitMsgTarget: true,
			    monitorValid:true,
			    labelAlign: 'right',
			    buttonAlign : 'center',
				items : [{
					xtype : 'hidden',
					name : 'id',
					value : templateId
				},{
					id : 'creditTemplate',
					xtype : 'combo',
					fieldLabel : '<font color=red>*</font> '+node.text+'下级指标',
					hiddenName : 'indicatorId',
					width : 250,
					mode : 'romote',
					allowBlank : false,
					blankText : '必填信息',
					store : new Ext.data.JsonStore({
						url : 'indicatorList.action?indicatorTypeId=' + node.id.substring('1'),
						root : 'topics',
						fields : [{
									name : 'id'
								}, {
									name : 'indicatorName'
								}]
					}),
					displayField : 'indicatorName',
					valueField : 'id',
					triggerAction : 'all'
										
				}],
				buttons : [
				{ 	
			            text: '提交',
			            formBind : true,
			            iconCls : 'submitIcon',
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
										Ext.Msg.alert('状态','操作有误，添加失败!');									
								}
			            	});
			            
			            }
				}/*,{
		            text: '重置',
		            handler : function(){
						menu_Panel.findById('menu_form').getForm().reset();
	            	}
	        
			     }*/]
			}]
		})
		var win = new Ext.Window({
			id : 'win',
			layout :'fit',
			width : 500,
			height : 200,
			title :'添加'+ node.text +'子类型',
			maximizable : true,
			modal : true,
			items : [menu_Panel]
		})
		win.show();
	}
	
	function editCatalogType(node){
		var id = node.id;
		templateIndicatorId = id.substring(1);
		jStore_option.removeAll();
		jStore_option.reload({
			params : {
				templateIndicatorId : templateIndicatorId
			}
		});
		win_option.show();
	}
	
	function deleteCatalogType(node){
		var indicatorId = node.id.substring(1);
		Ext.MessageBox.confirm('确认删除', '是否确认执行删除 '
				+ node.text, function(btn) {
			if (btn == 'yes') {
				Ext.Ajax.request({
					url : 'deleteTemplateIndicator.action',
					method : 'POST',
					success : function() {
						//dic_Root.reload();
						var parentNode = node.parentNode;
						parentNode.reload();
						parentNode.expand();
						Ext.Msg.alert('状态', '删除成功!');
					},
					failure : function(result, action) {
						Ext.Msg.alert('状态','删除失败!');
					},
					params: { indicatorId : indicatorId }
				});
			}
		});
				
	}
})
/*未完成3个操作方法*/