function setCatalogMenuFun(node,e,centerPanel){
		var setCatalogMenu; 
		var nodeId = node.parentNode;
		//alert("node =" +node.parentNode);
		if((nodeId == null)){ //当前节点为根节点
			setCatalogMenu = new Ext.menu.Menu({
				items : [{
					text : '添加目录类型',
					hidden : isGranted('_mulu_add')?false:true,
					handler : function (){
						addCatalogType(false,node);
					}
				}]
			})
		}else if(node.isLeaf()){ //当前节点为叶子节点
			setCatalogMenu = new Ext.menu.Menu({
				items :[{
					text :'编辑咨询评估要素',
					hidden : isGranted('_yaosu_update')?false:true,
					handler : function (){
						updateCatalogType(node);
					}
				},"-",{
					text : '删除咨询评估要素',
					hidden : isGranted('_yaosu_delete')?false:true,
					handler : function (){
						deleteCatalogType(node,centerPanel);
					}
				}]
			})
		}else if(node.hasChildNodes() && node.parentNode !=null){
			setCatalogMenu = new Ext.menu.Menu({
				items :[/*{
					text :'添加目录类型',
					handler : function (){
						addCatalogType(true,node);
					}
				},"-",*/{
					text : '添加咨询评估要素',
					hidden : isGranted('_yaosu_add')?false:true,
					handler : function (){
						addCatalogType(true,node);
					}
				},"-",{
					text : '修改目录类型',
					hidden : isGranted('_mulu_update')?false:true,
					handler : function (){
						updateCatalogType(node);
					}
				},"-",{
					text : '删除目录类型',
					hidden : isGranted('_mulu_delete')?false:true,
					handler : function (){
						deleteCatalogType(node,centerPanel);
						//delTreeItemComfirm();
					}
				}]
			})
		}else{  //当期节点为目录节点
			setCatalogMenu = new Ext.menu.Menu({
				items : [/*{
					text : '添加目录类型',
					handler : function (){
						addCatalogType(true,node);
					}
				},"-",*/{
					text : '添加咨询评估要素',
					hidden : isGranted('_yaosu_add')?false:true,
					handler : function (){
						addCatalogType(true,node);
					}
				},"-",{
					text : '修改目录类型',
					hidden : isGranted('_mulu_update')?false:true,
					handler : function (){
						updateCatalogType(node);
						
					}
				},"-",{
					text : '删除目录类型',
					hidden : isGranted('_mulu_delete')?false:true,
					handler : function (){
						deleteCatalogType(node);
						//delTreeItemComfirm();
					}
				}]
			})
		}
		setCatalogMenu.showAt(e.getPoint());
	}
	//添加目录，添加指标
	function addCatalogType(isLaef,node){
		var number = node.indexOf(node.lastChild) + 1;
		var text="";
		if(isLaef==false){
		   text="目录类型"
		}else if(isLaef==true){
		   text="要素名称"
		}
		var title="";
		if(node.id==0){
		    title="新增目录类型"
		}else{
		    title="【"+node.text+"目录】新增咨询评估要素"
		}
		var menu_Panel = new Ext.Panel({
			id : 'menu_Panel',
			//height :500,
			width : 400,
			layout : 'fit',
			frame : true,
			//bodyStyle:'padding:5px 5px 0',
			items : [{
				id : 'menu_form',
				xtype : 'form',
				bodyStyle:'margin-top:20px',
				labelWidth: 80,
				url :__ctxPath+'/creditFlow/creditmanagement/addIndicatorStore.do?indicatorStore.parentId='+node.id+ '&indicatorStore.isleaf='+isLaef,
				waitMsgTarget: true,
			    monitorValid:true,
			    labelAlign: 'right',
			    buttonAlign : 'center',
				items : [{
					xtype : 'textfield',
					fieldLabel : text ,
					name : 'indicatorStore.indicatorType',
					allowBlank : false,
					blankText : '必填信息',
					//width : 150,
					anchor:'80%'
				},{
				    xtype:'textarea',
				    fieldLabel:'备注',
				    name:'indicatorStore.remarks',
				    anchor:'80%'
				}],
				buttons : [
				{ 	
			            text: '提交',
			            iconCls : 'submitIcon',
			            formBind : true,
			            handler : function(){
			            	//alert(menu_Panel.findById('menu_form').getForm());
			            	menu_Panel.findById('menu_form').getForm().submit({
			            		method : 'POST',
			            		waitTitle : '连接',
								waitMsg : '消息发送中...',
								success : function(){
									Ext.ux.Toast.msg('状态','添加成功');
									win.destroy();
									node.reload();
									node.expand();
									
									
								},
								failure : function(form, action) {
										Ext.ux.Toast.msg('状态','添加失败!');									
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
			width : 400,
			height : 200,
			title :title,
			maximizable : true,
			modal : true,
			items : [menu_Panel]
		})
		win.show();
	}
	function updateCatalogType(node){
		var id = node.id;
		var text = node.text;
		var parentNode = node.parentNode;
		var fieldText="";
		if(node.leaf==false){
		   fieldText="目录类型"
		}else if(node.leaf==true){
		   fieldText="要素名称"
		}
		var title="";
		if(node.id==0){
		    title="编辑目录类型"
		}else{
		    title="【"+node.text+"目录】编辑咨询评估要素"
		}
		Ext.Ajax.request({
			url :__ctxPath+'/creditFlow/creditmanagement/getInfoIndicatorStore.do?id=' + id,
			method : 'POST',
			success : function(response, request) {
				var obj=Ext.util.JSON.decode(response.responseText)
				var menu_UPanel = new Ext.Panel({
					id : 'menu_UPanel',
					height : 200,
					width :400,
					layout : 'fit',
					frame : true,
					items : [{
						id : 'menu_Uform',
						xtype : 'form',
						bodyStyle:'margin-top:20px',
						labelWidth: 80,
						url :__ctxPath+'/creditFlow/creditmanagement/updateIndicatorStore.do?indicatorStore.id=' + id,
						waitMsgTarget: true,
			   	 		monitorValid:true,
			    		labelAlign: 'right',
			    		buttonAlign : 'center',
			    		items : [{
			    			xtype : 'textfield',
							fieldLabel : fieldText,
							name : 'indicatorStore.indicatorType',
							allowBlank : false,
							blankText : '必填信息',
							//width : 150,
							anchor:'80%',
							value: text
			    		},{
			    		    xtype:'textarea',
			    		    fieldLabel:'备注',
			    		    name:'indicatorStore.remarks',
			    		    anchor:'80%',
			    		    value:obj.data.remarks
			    		}],
			    		buttons :[
							{ 	
						            text: '提交',
						            iconCls : 'submitIcon',
						            formBind : true,
						            handler : function(){
						            	menu_UPanel.findById('menu_Uform').getForm().submit({
						            		method : 'POST',
						            		waitTitle : '连接',
											waitMsg : '消息发送中...',
											success : function(){
												Ext.ux.Toast.msg('状态','修改成功');
												uwin.destroy();
												var parentNode = node.parentNode;
												parentNode.reload();
												parentNode.expand();
												
												
											},
											failure : function(form, action) {
													Ext.ux.Toast.msg('状态','修改失败!');									
											}
						            	});
						            
						            }
							}/*,{
					            text: '取消',
					            handler : function(){
					            	uwin.destroy();
				            	}
						     }*/
			    		]
					}]
				})
				var uwin = new Ext.Window({
					id : 'uwin',
					layout :'fit',
					width : 400,
					title  :title,
					frame : true,
					height : 200,
					maximizable : true,
					modal : true,
					items : [menu_UPanel]
				})
				uwin.show();
			}
		})
		
	}
	function deleteCatalogType(node,centerPanel){
		var id = node.id;
		Ext.MessageBox.confirm('确认删除', '是否确认执行删除 '
				+ node.text, function(btn) {
			if (btn == 'yes') {
				Ext.Ajax.request({
					url : __ctxPath+'/creditFlow/creditmanagement/deleteRsIndicatorStore.do',
					method : 'POST',
					success : function() {
						//dic_Root.reload();
						var parentNode = node.parentNode;
						parentNode.reload();
						parentNode.expand();
						centerPanel.getStore().reload()
						Ext.ux.Toast.msg('状态', '删除成功!');
					},
					failure : function(result, action) {
						Ext.ux.Toast.msg('状态','删除失败!');
					},
					params: { id: id }
				});
			}
		});
				
			
	}