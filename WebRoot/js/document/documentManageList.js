var jStoreDocumentElement;
var enterpriseElmentValue = "企业融资贷款";
var carElementValue = "个人车辆贷款";
Ext.onReady(function(){
	Ext.form.Field.prototype.msgTarget = 'under';
	Ext.QuickTips.init();
	var jStoreDocument = new Ext.data.JsonStore( {
		url : 'queryListDocumentAction.do',
		totalProperty : 'totalProperty',
		root : 'topics',
		fields : [ {name : 'id'}, {name : 'businessType'},{name : 'usageType'},{name : 'title'},{name : 'fileId'} ]
	});
	jStoreDocument.load({
		params : {
			start : 0,
			limit : 50
		}
	});
	var cModelDocument = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer({header : '编号',width : 35}),
        {header:'业务品种',width:100,dataIndex:'businessType',sortable:true},
		{header:'文档类别',width:90,dataIndex:'usageType',sortable:true},
		{header:'文档名称',width:100,dataIndex:'title',sortable:true},
		{header:'模板文件',width:70,dataIndex:'',sortable:true,
		renderer : function(v,m,r){
			var v = v ;
			var m = m ;
			var r = r ;
			return '<a href="#" onclick="uploadfile(\'管理附件\',\'cs_document\','+r.get('id')+');">管理</a>';
			//var fileId = r.get('fileId');
			//return '<a href="#" onclick="uploadfile(\'上传附件\',\'cs_document\','+r.get('id')+');">上传</a>';
		}},		
		{header:'模板要素',width:70,dataIndex:'',sortable:true,renderer : function(v,m,r){
			return '<a href="#" onclick="seeListElement('+r.get('id')+');">查看</a>';
		}},
		{header:'生成文档',width:70,dataIndex:'',sortable:true ,renderer : function(v,m,r){
			var getElementType = r.get('businessType');
			if(enterpriseElmentValue == getElementType){
				return '<a href="#" onclick="enterpriseElementWin('+r.get('id')+')">生成</a>';
			}else{
				return '<a href="#" onclick="carElementWin('+r.get('id')+')">生成</a>';
			}
			//return '<a href="#" onclick="documentText('+r.get('id')+')">生成</a>';
		}}
	]);
	var pagingBar = new Ext.PagingToolbar( {
		pageSize : 50,
		store : jStoreDocument,
		autoWidth : true,
		hideBorders : true,
		displayInfo : true,
		displayMsg : '当前第{0} - {1}条，共 {2} 条记录',
		emptyMsg : "没有符合条件的记录······"
	});
	var myMask = new Ext.LoadMask(Ext.getBody(), {
		msg : "加载数据中······,请稍后······"
	});
	var gPanelDocument = new Ext.grid.GridPanel( {
		id : 'gPanelDocument',
		store : jStoreDocument,
		bodyStyle:'width:100%;',
		height : document.body.clientHeight-56,
		colModel : cModelDocument,
		selModel : new Ext.grid.RowSelectionModel(),
		border : false,
		stripeRows : true,
		loadMask : myMask,
		bbar : pagingBar
	});
	var panelDocument = new Ext.Panel({
		id :'panelDocument',
		layout : 'fit',
		autoHeight : false,
		height : document.body.clientHeight-56,
		autoWidth : true,
		autoScroll : true ,
		frame : true,
		renderTo : 'documentListDiv',
		items : gPanelDocument
	})
	// 文档中的元素
	jStoreDocumentElement = new Ext.data.JsonStore( {
		url : 'queryListDocumentElementAction.do',
		totalProperty : 'totalProperty',
		root : 'topics',
		fields : [ {name : 'id'}, {name : 'elementName'}, {name : 'elementCode'}, {name : 'documentId'} ]
		//baseParams:{documentId :document.getElementById("DocumentId").value}
	});
	var cModelDocumentElement = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer({header : '编号',width : 35}),
        {header:'要素名称',width:120,dataIndex:'elementName',sortable:true},
        {header:'要素编码',width:100,dataIndex:'elementCode',sortable:true}
    ]);
	var gPanelDocumentElement = new Ext.grid.GridPanel( {
		id : 'gPanelDocumentElement',
		store : jStoreDocumentElement,
		bodyStyle:'width:100%;',
		height : document.body.clientHeight-56,
		colModel : cModelDocumentElement,
		autoExpandColumn : 1,
		selModel : new Ext.grid.RowSelectionModel(),
		stripeRows : true,
		loadMask : myMask
	});
	var panelDocumentElement = new Ext.Panel({
		id :'panelDocumentElement',
		layout : 'fit',
		autoHeight : false,
		height : document.body.clientHeight-56,
		autoWidth : true,
		autoScroll : true ,
		frame : true,
		renderTo : 'documentEelementListDiv',
		items : gPanelDocumentElement
	});
	var documentGridOnClick = function(gPanelDocument, rowIndex, e){
	    var selectionModel = gPanelDocument.getSelectionModel();
	    var record = selectionModel.getSelected();
	    var documentId = record.get("id");
	    document.getElementById("documentId").value = documentId;
	    document.getElementById("documentElementId").value = "";
	};
	gPanelDocument.addListener('rowclick', documentGridOnClick);
	
	var dictionaryGridOnDbClick = function(gPanelDocument, rowIndex, e){
	    uf_updateDic();
	}
	gPanelDocument.addListener('rowdblclick', dictionaryGridOnDbClick);
	
	var elementGridOnDbClick = function(gPanelDocument, rowIndex, e){
	    uf_updateDocumentElement();
	}
	gPanelDocumentElement.addListener('rowdblclick', elementGridOnDbClick);
	
	var dictionaryOptionGridOnClick = function(gPanelDocumentElement, rowIndex, e){
	    var selectionModel = gPanelDocumentElement.getSelectionModel();
	    var record = selectionModel.getSelected();
	    document.getElementById("documentElementId").value = record.get("id");
	    document.getElementById("documentElementValue").value = record.get("value");
	    document.getElementById("sortorder").value = record.get("sortorder");
	}
	
	gPanelDocumentElement.addListener('rowclick', dictionaryOptionGridOnClick);
	function submitForm(as_ActionName){
		document.all.form_post.do = as_ActionName;
		document.all.form_post.submit();
	};
	var onContextMenu = function(e){
		var documentId = document.getElementById("documentId").value;
		window.event.returnValue = false;
		if(documentId == "")	{
			Ext.ux.Toast.msg('系统提示','请先选择文档!');
			return;
		}
		var setCatalogMenu = new Ext.menu.Menu({
			items : [{
				iconCls : 'addIcon',
				text : '添加要素',
				handler : function (){
					uf_addDocumentElement();
				}
			},"-",{
				iconCls : 'deleteIcon',
				text : '删除要素',
				handler : function (){
					uf_deleteDocumentElement();
				}
			},"-",{
				iconCls : 'updateIcon',
				text : '修改要素',
				handler : function (){
					uf_updateDocumentElement();
				}
			}]
		})
		setCatalogMenu.showAt(e.getXY());
	}
	
	gPanelDocumentElement.addListener('contextmenu',onContextMenu);
	function uf_addDocumentElement(){
		var flag = true; 
		var documentId = document.getElementById("documentId").value;
		var formPanel = new Ext.FormPanel({})
		var elwin = new Ext.Window({
			id : 'elwin',
			layout :'fit',
			title : '增加要素',
			width : 350,
			height : 220,
			minimizable : true,
			modal : true,
			items : [
				new Ext.form.FormPanel({
					id : 'addElementForm',
					labelAlign: 'right',
					buttonAlign : 'center',
					url : 'addDocumentElementAction.do',
					bodyStyle:'padding:25px 25px 25px',
					labelWidth : 62,
					frame : true,
					waitMsgTarget: true,
					monitorValid:true,
					layout : 'column',
					width : 350,
					items : [{
						columnWidth : 1,
						layout : 'form',
						defaults : {anchor : '100%'},
						items : [{
							xtype : 'textfield',
							fieldLabel : '要素名称',
							name : 'documentElement.elementName',
							allowBlank : false,
							blankText : '必填信息',
							listeners : {
								'blur':function(){
									ajaxRepeatValidator(this,documentId,"queryElementNameRepeatAction","该要素名称已存在！");
								}
			                }
							
						},{
							columnWidth : 1,
							layout : 'column',
							items :[
								{
									columnWidth : 0.25,
									items:[{
										xtype : 'label',
										style : 'clear:left;display:block;float:left;padding: 3px 3px 3px 0;position:relative;z-index:2;width: 110px;',
										html : '要素编码:'
									}]
								},{
									columnWidth : 0.05,
									items:[{
										xtype : 'label',
										style : 'clear:left;display:block;float:left;padding: 3px 3px 3px 0;position:relative;z-index:2;width: 110px;',
										html : '[#'
									}]
								},{
									columnWidth : 0.60,
									labelWidth : 1,
									layout : 'form',
									items : [{
										xtype : 'textfield',
										name : 'documentElement.elementCode',
										allowBlank : false,
										blankText : '必填信息',
										regex : /^[0-9]{1,20}$|^[A-Za-z]+$|^[\u4e00-\u9fa5]{1,10}$/,
										regexText : '不能输入特殊字符，如:~',
										anchor : '100%',
										listeners : {
											'blur':function(){
												ajaxRepeatValidator(this,documentId,"queryElementCodeRepeatAction","该要素编码已存在！");
											}
						                }
									}]
								},{
									columnWidth : 0.07,
									items:[{
										xtype : 'label',
										style : 'clear:left;display:block;float:left;padding: 3px 3px 3px 0;position:relative;z-index:2;width: 110px;',
										html : '&nbsp;&nbsp;#]'
									}]
								}
							]
						},{
							xtype : 'hidden',
							name : 'documentElement.documentId',
							value : documentId
						}]
					}],
					buttons : [{  	
		             	text: '提交',
		             	iconCls:'submitIcon',
		             	formBind : true,
		            	handler : function(){
		            		elwin.findById('addElementForm').getForm().submit({
								method : 'POST',
								waitTitle : '连接',
								waitMsg : '消息发送中...',
								success : function(form, action) {
									obj = Ext.util.JSON.decode(action.response.responseText);
									if(obj.exsit == false){
										Ext.ux.Toast.msg('状态' ,obj.msg );
									}else{
										Ext.Msg.alert('状态', '添加成功!',
											function(btn, text) {
											if (btn == 'ok') {
												jStoreDocumentElement.reload();
												elwin.destroy();
											}
										});
									}
								},
								failure : function(form, action) {
									Ext.ux.Toast.msg('状态',Ext.decode(action.response.responseText).msg);									
								}
							})
		          		}
			        },{
						text : '取消',
						iconCls : 'cancelIcon',
						handler : function(){
							elwin.destroy();
						}
			        }]
				})
			]
		}).show();
	}
	function uf_updateDocumentElement(){
		var documentId = document.getElementById("documentId").value;
		var documentElementId = document.getElementById("documentElementId").value;
		var documentElementValue = document.getElementById("documentElementValue").value;
		var sortorder = document.getElementById("sortorder").value;
		if(documentElementId == "" || documentElementValue == ""){
			Ext.ux.Toast.msg("系统提示","请选择一条纪录．");
			return;
		}else{
			Ext.Ajax.request({
				url : 'getByIdDocumentElementAction.do',
				method : 'POST',
				success : function(response,request){
					var obj = Ext.util.JSON.decode(response.responseText);
					elementDate = obj.data ;
					var elupwin = new Ext.Window({
						id : 'elupwin',
						layout :'fit',
						title : '修改要素',
						width : 350,
						height : 220,
						minimizable : true,
						modal : true,
						items : [
							new Ext.form.FormPanel({
								id : 'updateElementForm',
								labelAlign: 'right',
								buttonAlign : 'center',
								url : 'updateDocumentElementAction.do',
								bodyStyle:'padding:25px 25px 25px',
								labelWidth : 62,
								frame : true,
								waitMsgTarget: true,
								monitorValid:true,
								layout : 'column',
								width : 350,
								items : [{
									columnWidth : 1,
									layout : 'form',
									defaults : {anchor : '100%'},
									items : [{
										xtype : 'textfield',
										fieldLabel : '要素名称',
										labelSeparator : ':' ,
										name : 'documentElement.elementName',
										value : elementDate.elementName,
										allowBlank : false,
										blankText : '必填信息',
										listeners : {
											'blur':function(){
												ajaxRepeatValidator(this,documentId,"queryElementNameRepeatAction","该要素名称已存在！");
											}
						                }
									},{
										xtype : 'hidden',
										name : 'documentElement.documentId',
										value : elementDate.documentId
									},{
										xtype : 'hidden',
										name : 'documentElement.id',
										value : elementDate.id
									}]
								},{
									columnWidth : 1,
									layout : 'column',
									items :[
										{
											columnWidth : 0.25,
											items:[{
												xtype : 'label',
												style : 'clear:left;display:block;float:left;padding: 3px 3px 3px 0;position:relative;z-index:2;width: 110px;',
												html : '要素编码:'
											}]
										},{
											columnWidth : 0.05,
											items:[{
												xtype : 'label',
												style : 'clear:left;display:block;float:left;padding: 3px 3px 3px 0;position:relative;z-index:2;width: 110px;',
												html : '[#'
											}]
										},{
											columnWidth : 0.60,
											labelWidth : 1,
											layout : 'form',
											items : [{
												xtype : 'textfield',
												name : 'documentElement.elementCode',
												value : elementDate.elementCode,
												allowBlank : false,
												blankText : '必填信息',
												regex : /^[0-9]{1,20}$|^[A-Za-z]+$|^[\u4e00-\u9fa5]{1,10}$/,
												regexText : '不能输入特殊字符，如:~',
												anchor : '100%',
												listeners : {
													'blur':function(){
														ajaxRepeatValidator(this,documentId,"queryElementCodeRepeatAction","该要素编码已存在！");
													}
								                }
											}]
										},{
											columnWidth : 0.07,
											items:[{
												xtype : 'label',
												style : 'clear:left;display:block;float:left;padding: 3px 3px 3px 0;position:relative;z-index:2;width: 110px;',
												html : '&nbsp;&nbsp;#]'
											}]
										}
									]
								}],
								buttons : [{  	
					             	text: '提交',
					             	iconCls:'submitIcon',
					             	formBind : true,
					            	handler : function(){
					            		elupwin.findById('updateElementForm').getForm().submit({
											method : 'POST',
											waitTitle : '连接',
											waitMsg : '消息发送中...',
											success : function(form, action) {
												obj = Ext.util.JSON.decode(action.response.responseText);
												if(obj.exsit == false){
													Ext.ux.Toast.msg('状态' ,obj.msg );
												}else{
													Ext.Msg.alert('状态', '修改成功!',
													function(btn, text) {
														if (btn == 'ok') {
															jStoreDocumentElement.reload();
															elupwin.destroy();
														}
													});
												}
											},
											failure : function(form, action) {
												Ext.ux.Toast.msg('状态',Ext.decode(action.response.responseText).msg);									
											}
										})
					          		}
						        },{
									text : '取消',
									iconCls : 'cancelIcon',
									handler : function(){
										elupwin.destroy();
									}
						        }]
							})
						]
					}).show();
				},
				failure : function(response) {					
					Ext.ux.Toast.msg('状态','操作失败，请重试');		
				},
				params: { elementId: documentElementId }
			})
		}
		
	}
	function uf_deleteDocumentElement(){
		var documentElementId = document.getElementById("documentElementId").value;
		var documentElementValue = document.getElementById("documentElementValue").value;
		if(documentElementId == "" || documentElementValue == ""){
			Ext.ux.Toast.msg("系统提示","请选择一条纪录．");
			return;
		}else{
			Ext.Ajax.request({
				url : 'deleteDocumentElementAction.do',
				method : 'POST',
				success : function(response,request){
					jStoreDocumentElement.reload();
				},
				failure : function(response) {					
					Ext.ux.Toast.msg('状态','操作失败，请重试');		
				},
				params: { elementId: documentElementId }
			})
		}
	}
	function uf_addDic(){
		var adwin = new Ext.Window({
			id : 'adwin',
			layout :'fit',
			title : '增加文档',
			width : 350,
			height : 220,
			minimizable : true,
			modal : true,
			items : [new Ext.FormPanel({
				id : 'documentFormPa',
				labelAlign: 'right',
				buttonAlign : 'center',
				url : 'addDocumentAction.do',
				bodyStyle:'padding:25px 25px 25px',
				labelWidth : 80,
				frame : true,
				waitMsgTarget: true,
				monitorValid:true,
				layout : 'column',
				width : 350,
				items : [{
					columnWidth : 1,
					layout : 'form',
					defaults : {anchor : '100%'},
					items : [{
						xtype : 'combo',
	                	mode : 'local',
						fieldLabel : '业务品种',
						editable : false,
						name : 'document.businessType',
						store: new Ext.data.SimpleStore({ fields : ['textValue', 'keyValue'],data : [['企业融资贷款','1'],['个人车辆贷款','2']]}),
						allowBlank : false,
						blankText : '必填信息',
	                    triggerAction : 'all',
	                    valueField : 'textValue', 
	                    displayField : 'textValue'
					},{
						xtype : 'combo',
	                	mode : 'local',
						fieldLabel : '文档类别',
						editable : false,
						name : 'document.usageType',
						store: new Ext.data.SimpleStore({ fields : ['textValue', 'keyValue'],data : [['合同文档','1'],['项目文档','2']]}),
						allowBlank : false,
						blankText : '必填信息',
	                    triggerAction : 'all',
	                    valueField : 'textValue', 
	                    displayField : 'textValue'
					},{
						xtype : 'textfield',
						fieldLabel : '文档名称',
						name : 'document.title',
						allowBlank : false,
						blankText : '必填信息'
					}]
				}],
				buttons : [{  	
	             	text: '提交',
	             	iconCls:'submitIcon',
	             	formBind : true,
	            	handler : function(){
	            		adwin.findById('documentFormPa').getForm().submit({
							method : 'POST',
							waitTitle : '连接',
							waitMsg : '消息发送中...',
							success : function() {
								Ext.Msg.alert('状态', '添加成功!',
									function(btn, text) {
										if (btn == 'ok') {
											jStoreDocument.reload();
											adwin.destroy();
										}
									});
							},
							failure : function(form, action) {
								Ext.ux.Toast.msg('状态',Ext.decode(action.response.responseText).msg);									
							}
						})
	          		}
		        },{
					text : '取消',
					iconCls : 'cancelIcon',
					handler : function(){
						adwin.destroy();
					}
		        }]
			})
		]}).show();
	}
	function enterpriseOrCarElementList(productType,elementTitle){
	  	var jStoreEnterpriseOrCarElementList = new Ext.data.JsonStore( {
	  		id : 'jStoreEnterpriseOrCarElementList',
			url : 'ajaxQueryEnterpriseOrCarElementAction.do',
			totalProperty : 'totalProperty',
			root : 'topics',
			baseParams : {productType : productType},
			fields : [ {
					name : 'id'
				}, {
					name : 'elementName'
				}, {
					name : 'elementCode'
				}]
		});
	  	jStoreEnterpriseOrCarElementList.load({
			params : {
				start : 0,
				limit : 20
			}
		});
		var cModelEnterpriseOrCarElementList = new Ext.grid.ColumnModel(
			[
				new Ext.grid.RowNumberer( {
					header : '序号',
					width : 35
				}),
				 {
					header : "要素名称",
					width : 150,
					sortable : true,
					dataIndex : 'elementName'
				}, {
					header : "要素编码",
					width : 120,
					sortable : true,
					dataIndex : 'elementCode'
		}]);
		var pagingBar = new Ext.PagingToolbar( {
			pageSize : 20,
			store : jStoreEnterpriseOrCarElementList,
			autoWidth : true,
			hideBorders : true,
			displayInfo : true,
			displayMsg : '当前第{0} - {1}条，共 {2} 条记录',
			emptyMsg : "没有符合条件的记录······"
		});
		var myMask = new Ext.LoadMask(Ext.getBody(), {
			msg : "加载数据中······,请稍后······"
		});
		var window_EnterpriseOrCarElement = new Ext.Window({
			title : elementTitle,
			width : 420,
			height : 450,
			collapsible : true,
			constrainHeader : true ,
			modal : true ,
			layout : 'fit',
			buttonAlign : 'center',
			items : [new Ext.grid.GridPanel( {
				id : 'gPanel_EnterpriseOrCarElementList',
				store : jStoreEnterpriseOrCarElementList,
				height : 500,
				colModel : cModelEnterpriseOrCarElementList,
				autoExpandColumn : 2,
				selModel : new Ext.grid.RowSelectionModel(),
				stripeRows : true,
				loadMask : myMask,
				bbar : pagingBar
			})]
		}).show();
	}
	function uf_deleteDic(){
		var documentId = document.getElementById("documentId").value;
		if(documentId == "" || documentId == null){
			alert("请选择一条记录。");
			return;
		}
		Ext.MessageBox.confirm('系统提示','删除要素，仍要继续吗？',function(btn){
			if(btn != 'yes')
				return;
			Ext.Ajax.request({
				url : 'deleteDocumentAction.do',
				method : 'POST',
				params: { documentId: documentId },
				success : function() {
					Ext.ux.Toast.msg('状态', '删除成功!');
					document.getElementById("documentId").value = "";
					document.getElementById("documentElementId").value = "";
					jStoreDocument.removeAll();
					jStoreDocumentElement.removeAll();
					jStoreDocument.reload();
					jStoreDocumentElement.reload();
				},
				failure : function(result, action) {
					Ext.ux.Toast.msg('状态','删除失败!');
				}
			});
		})
	}
	function uf_updateDic(){
		var documentId = document.getElementById("documentId").value;
		if(documentId == "" || documentId == null){
			Ext.ux.Toast.msg("状态","请选择一个文档！");
			return;
		}else{
			Ext.Ajax.request({
				url : 'getByIdAction.do',
				method : 'POST',
				success : function(response,request){
					var obj = Ext.util.JSON.decode(response.responseText);
					documentDate = obj.data ;
					var UTwin = new Ext.Window({
						id : 'UTwin',
						layout :'fit',
						title : '修改文档',
						width : 350,
						height : 220,
						minimizable : true,
						modal : true,
						items : [
							new Ext.FormPanel({
								id : 'updocumentFormPa',
								labelAlign: 'right',
								buttonAlign : 'center',
								url : 'updateDocumentAction.do',
								bodyStyle:'padding:25px 25px 25px',
								labelWidth : 80,
								frame : true,
								waitMsgTarget: true,
								monitorValid:true,
								layout : 'column',
								width : 350,
								items :[{
									columnWidth : 1,
									layout : 'form',
									defaults : {anchor : '100%'},
									items : [{
										xtype : 'combo',
					                	mode : 'local',
										fieldLabel : '业务品种',
										editable : false,
										name : 'document.businessType',
										store: new Ext.data.SimpleStore({ fields : ['textValue', 'keyValue'],data : [['企业融资贷款','1'],['个人车辆贷款','2']]}),
										allowBlank : false,
										blankText : '必填信息',
					                    triggerAction : 'all',
					                    valueField : 'textValue', 
					                    displayField : 'textValue',
					                    value : documentDate.businessType
									},{
										xtype : 'combo',
					                	mode : 'local',
										fieldLabel : '文档类别',
										editable : false,
										name : 'document.usageType',
										store: new Ext.data.SimpleStore({ fields : ['textValue', 'keyValue'],data : [['合同文档','1'],['项目文档','2']]}),
										allowBlank : false,
										blankText : '必填信息',
					                    triggerAction : 'all',
					                    valueField : 'textValue', 
					                    displayField : 'textValue',
					                    value : documentDate.usageType
									},{
										xtype : 'textfield',
										fieldLabel : '文档名称',
										name : 'document.title',
										value : documentDate.title,
										allowBlank : false,
										blankText : '必填信息'
									},{
										xtype : 'hidden' ,
										name : 'document.id',
										value : documentDate.id
									}]
								}],
								buttons : [{  	
					             	text: '提交',
					             	iconCls:'submitIcon',
					             	formBind : true,
					            	handler : function(){
					            		UTwin.findById('updocumentFormPa').getForm().submit({
											method : 'POST',
											waitTitle : '连接',
											waitMsg : '消息发送中...',
											success : function() {
												Ext.ux.Toast.msg('状态', '修改成功!',
													function(btn, text) {
														if (btn == 'ok') {
															jStoreDocument.reload();
															UTwin.destroy();
														}
													});
											},
											failure : function(form, action) {
												Ext.ux.Toast.msg('状态',Ext.decode(action.response.responseText).msg);									
											}
										})
					          		}
						        },{
									text : '取消',
									iconCls : 'cancelIcon',
									handler : function(){
										UTwin.destroy();
									}
						        }]
							})
						]
					}).show();
				},
				failure : function(response) {					
					Ext.ux.Toast.msg('状态','操作失败，请重试');		
				},
				params: { documentId: documentId }
				})
		}
	}
	new Ext.Button({
        text: '添加文档',
        handler: uf_addDic,
        iconCls:'addIcon'
   	}).render(document.body, 'btn_add');
	new Ext.Button({
        text: '修改文档',
        handler: uf_updateDic,
        iconCls:'updateIcon'
   	}).render(document.body, 'btn_update');
	
	new Ext.Button({
        text: '删除文档',
        handler: uf_deleteDic,
        iconCls:'deleteIcon'
   	}).render(document.body, 'btn_delete');
	
	new Ext.Button({
        text: '企业贷要素库',
        handler: function(){enterpriseOrCarElementList('v_proj_element_enterprise','企业贷要素列表')},
        iconCls:'seeIcon'
   	}).render(document.body, 'btn_seeEnterpriseElement');
	
	new Ext.Button({
        text: '车贷要素库',
        handler: function(){enterpriseOrCarElementList('v_proj_element_car','车贷要素列表')},
        iconCls:'seeIcon'
   	}).render(document.body, 'btn_seeCarElement');
})
	var seeListElement = function(id){
	    document.getElementById("documentId").value = id;
	    document.getElementById("documentElementId").value = "";
	    jStoreDocumentElement.baseParams.documentId = id;
	    jStoreDocumentElement.on('beforeload', function(store,options){
	        var new_params={documentId: id};
	        Ext.apply(options.params,new_params);
	    });
	    jStoreDocumentElement.removeAll();
		jStoreDocumentElement.reload();
	}
	function ajaxRepeatValidator(object,documentId,action,msg){  
		var paraNameText = "";
		var Objt = object;
		var paraValue = Objt.getValue();
		var paraName = Objt.getName();
		if(Objt.getName().indexOf('.')!=-1){
			paraNameText = paraName.substr(paraName.indexOf('.')+1);
		}else{
			paraNameText = paraName;
		}
        Ext.Ajax.request({   
            url: action+'.do?',
            method:'post',   
            params : {elementName : paraValue , elementCode : paraValue ,documentId : documentId},
            success: function(response, option) {   
                var obj = Ext.decode(response.responseText);  
                if(obj.exsit==true){
                    object.markInvalid(msg);   
                    return false;   
                }else{   
                    return true;   
                }      
            },   
            failure: function(response, option) {   
                return false;   
                Ext.MessageBox.alert('友情提示',"异步通讯失败，请于管理员联系！");   
            }   
        });
    } 