var bodyWidth = Ext.getBody().getWidth();
var bodyHeight = Ext.getBody().getHeight();
var selid ;
Ext.onReady(function() {
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'side';
	var widthFun = function(bodyWidth){
		return ((bodyWidth-6)<400) ? 400 : (bodyWidth-6);
	}
	var heightFun = function(bodyHeight){
		return ((bodyHeight<400) ? 400 : (bodyHeight));	
	}
	var otherTemplateStore = new Ext.data.JsonStore( {
		url : 'getListOtherDocumentTempletCall.do',
		totalProperty : 'totalProperty',
		root : 'topics',
		fields : [ {name : 'id'}, {name : 'text'}, {name : 'textoo'}, {name : 'handleFun'},{name : 'templettype'}, {name : 'remarks'}, {name : 'leaf'}, {name : 'isexist'},{name : 'parentid'},{name : 'onlymark'} ]
	});
	otherTemplateStore.load({
		params : {
			start : 0,
			limit : 15
		}
	});
	var other_button_add = new Ext.Button({
		text : '新增模板',
		tooltip : '新增模板信息',
		iconCls : 'addIcon',
		scope : this,
		handler : function() {
			var addOtherTemplateWin = new Ext.Window({
				id : 'addOtherTemplateWin',
				layout : 'fit',
				title : '添加其他模板',
				width : 500,
				height : 200,
				minimizable : true,
				modal : true,
				items :[
					new Ext.form.FormPanel({
						id : 'addOtherTemplateForm',
						labelAlign : 'right',
						buttonAlign : 'center',
						url : 'addDocumentTempletCall.do',
						bodyStyle : 'padding:10px 25px 25px',
					    layout : 'column',
						frame : true,
						waitMsgTarget : true,
						monitorValid : true,
						autoWidth : true,
						autoHight : true ,
						items : [{
							columnWidth : 1,
							layout : 'form',
							labelWidth : 100,
							defaults : {anchor : '100%'},
							items :[
								{
									xtype : 'textfield',
									fieldLabel : '文档名称',
									name : 'documentTemplet.text',
									allowBlank : false,
									blankText : '文档名称为必填内容'
								},{
									xtype : 'textfield',
									fieldLabel : '唯一标示',
									name : 'documentTemplet.onlymark',
									allowBlank : false,
									blankText : '唯一标示为必填内容'
								},{
									xtype : 'textfield',
									fieldLabel : '描述',
									name : 'documentTemplet.remarks'
								},{
									xtype : 'hidden',
									name : 'documentTemplet.leaf',
									value : true
								},{
									xtype : 'hidden',
									name : 'documentTemplet.parentid',
									value : 1
								}
							]
						}],
						buttons : [{
							text : '提交',
							formBind : true,
							handler : function() {
								addOtherTemplateWin.findById('addOtherTemplateForm').getForm().submit({
									method : 'POST',
									waitTitle : '连接',
									waitMsg : '消息发送中...',
									success : function() {
										Ext.Msg.alert('状态', '添加成功!',
											function(btn, text) {
												if (btn == 'ok') {
													otherTemplateStore.reload();
													addOtherTemplateWin.destroy();
												}
											});
									},
									failure : function(form, action) {
										Ext.ux.Toast.msg('状态', '添加失败,有重复项!');
									}
								})
							}
						}, {
							text : '取消',
							handler : function() {
								addOtherTemplateWin.destroy();
							}
						}]
					})
				]
			}).show();
		}
	});
	var other_button_update = new Ext.Button({
		text : '修改模板',
		tooltip : '修改模板信息',
		iconCls : 'updateIcon',
		scope : this,
		handler : function() {
			var selected = otherTemplateGrid.getSelectionModel().getSelected();
			if (null == selected) {
				Ext.MessageBox.alert('状态', '请选择一条记录!');
			}else{
				var id = selected.get('id');
				Ext.Ajax.request({
					url : 'getByIdDocumentTempletCall.do',
					method : 'POST',
					success : function(response,request){
						obj = Ext.util.JSON.decode(response.responseText);
						var updateOtherTemplateWin = new Ext.Window({
							id : 'updateOtherTemplateWin',
							layout : 'fit',
							title : '修改其他模板',
							width : 500,
							height : 200,
							minimizable : true,
							modal : true,
							items :[
								new Ext.form.FormPanel({
									id : 'updateOtherTemplateForm',
									labelAlign : 'right',
									buttonAlign : 'center',
									url : 'updateDocumentTempletCall.do',
									bodyStyle : 'padding:10px 25px 25px',
								    layout : 'column',
									frame : true,
									waitMsgTarget : true,
									monitorValid : true,
									autoWidth : true,
									autoHight : true ,
									items :[{
										columnWidth : 1,
										layout : 'form',
										labelWidth : 100,
										defaults : {anchor : '100%'},
										items :[{
											xtype : 'textfield',
											fieldLabel : '文档名称',
											name : 'documentTemplet.text',
											allowBlank : false,
											blankText : '文档名称为必填内容',
											value : obj.data.text
										},{
											xtype : 'textfield',
											fieldLabel : '唯一标示',
											name : 'documentTemplet.onlymark',
											value : obj.data.onlymark,
											allowBlank : false,
											blankText : '唯一标示为必填内容'
										},{
											xtype : 'textfield',
											fieldLabel : '描述',
											name : 'documentTemplet.remarks',
											value : obj.data.remarks
										},{
											xtype : 'hidden',
											name : 'documentTemplet.textoo',
											value : obj.data.textoo
										},{
											xtype : 'hidden',
											name : 'documentTemplet.id',
											value : obj.data.id
										},{
											xtype : 'hidden',
											name : 'documentTemplet.fileid',
											value : obj.data.fileid
										},{
											xtype : 'hidden',
											name : 'documentTemplet.isexist',
											value : obj.data.isexist
										}]
									}],
									buttons : [{
										text : '提交',
										formBind : true,
										handler : function() {
											updateOtherTemplateWin.findById('updateOtherTemplateForm').getForm().submit({
												method : 'POST',
												waitTitle : '连接',
												waitMsg : '消息发送中...',
												success : function(form ,action) {
													objt = Ext.util.JSON.decode(action.response.responseText);
													Ext.Msg.alert('状态', objt.msg,
															function(btn, text) {
																otherTemplateStore.reload();
																updateOtherTemplateWin.destroy();
															});
												},
												failure : function(form ,action) {
													objt = Ext.util.JSON.decode(action.response.responseText);
													Ext.ux.Toast.msg('状态', objt.msg);
												}
											});
										}
									}, {
										text : '取消',
										handler : function() {
											updateOtherTemplateWin.destroy();
										}
									}]
								})
							]
						}).show();
					},
					failure : function(response,request) {
						Ext.ux.Toast.msg('状态', '操作失败，请重试');
					},
					params: { id: id }
				})
			}
		}
	});
	var other_button_delete = new Ext.Button({
		text : '删除模板信息',
		tooltip : '删除模板信息',
		iconCls : 'deleteIcon',
		scope : this,
		handler : function(){
			var selected = otherTemplateGrid.getSelectionModel().getSelected();
			if (null == selected) {
				Ext.MessageBox.alert('状态', '请选择一条记录!');
			}else{
				var id = selected.get('id');
				var exist = selected.get('isexist');
				if(exist == true){
					Ext.MessageBox.alert('状态', '先删除附件!');
				}else{
					Ext.MessageBox.confirm('确认删除', '是否确认删除该模板 ', function(btn) {
						if (btn == 'yes') {
							Ext.Ajax.request({
								url : 'deleteDocumentTempletCall.do',
								method : 'POST',
								success : function() {
									Ext.ux.Toast.msg('状态', '删除成功!');
									otherTemplateStore.reload() ;
								},
								failure : function(result, action) {
									Ext.ux.Toast.msg('状态','删除失败!');
								},
								params: { id: id }
							});
						}
					});
				}
			}
		}
	});
	
	/*var other_button_upload = new Ext.Button({
		text : '上传模板',
		tooltip : '上传模板文件',
		iconCls : 'uploadIcon',
		scope : this,
		handler : function(){
			var selected = otherTemplateGrid.getSelectionModel().getSelected();
			if (null == selected) {
				Ext.MessageBox.alert('状态', '请选择一条记录!');
			}else{
				var id = selected.get('id');
				var exist = selected.get('isexist');
				selid = id ;
				if(exist == true){
					Ext.MessageBox.alert('状态', '已上传，请选择其他模板类型继续上传!');
				}else{
					uploadfile('上传合同模板','cs_document_templet.'+id ,1);
					if(isUploadOk == "ok"){
						Ext.Ajax.request({
							url : 'getByIdDocumentTempletCall.do',
							method : 'POST',
							success : function(response, request){
								
							},
							failure : function(response, request) {
								Ext.ux.Toast.msg('状态',msg);
							},
							params: { id: id , fileId : fileId }
						})
					}else{
						Ext.MessageBox.alert('状态', '上传模板失败！');
					}
				}
			}
		}
	})
	
	var other_button_templet = new Ext.Button({
		text : '删除模板文件',
		tooltip : '删除模板文件',
		iconCls : 'deleteIcon',
		scope : this,
		handler : function(){
			var selected = otherTemplateGrid.getSelectionModel().getSelected();
			if (null == selected) {
				Ext.MessageBox.alert('状态', '请选择一条记录!');
			}else{
				var id = selected.get('id');
				var exist = selected.get('isexist');
				if(exist == true){
					uploadfile('上传合同模板','cs_document_templet.'+id ,1 ,null ,null ,callUploadFunction);
					
				}else{
					Ext.MessageBox.alert('状态', '还没有上传模板!');
				}
			}
		}
	})
	
	var other_button_see = new Ext.Button({
		text : '查看模板要素',
		tooltip : '查看某模板下需要的要素',
		iconCls : 'seeIcon',
		scope : this,
		handler : function(){
			var selected = otherTemplateGrid.getSelectionModel().getSelected();
			if (null == selected) {
				Ext.MessageBox.alert('状态', '请选择一条记录!');
			}else{
				var id = selected.get('id');
				var exist = selected.get('isexist');
				if(exist == false){
					Ext.MessageBox.alert('状态', '请先上传模板在继续查看要素!');
				}else{
					var pbar = Ext.MessageBox.wait('数据读取中','请等待',{
						interval:200,
			        	increment:15
					});
					var elementCodeStore = new Ext.data.JsonStore( {
						url : 'findElementCall.do',
						root : 'topics',
						fields : [ {name : 'code'},{name : 'value'}, {name : 'depict'}]
					});
					elementCodeStore.load({
						params : {
							documentId: id
						}
					});
					var elementCodeModel = new Ext.grid.ColumnModel([
						new Ext.grid.RowNumberer({header:'序'}),
						{
							header : "要素描述",
							width : 180,
							sortable : true,
							dataIndex : 'depict'
						},{
							header : "要素编码",
							width : 150,
							sortable : true,
							dataIndex : 'code'
						},{
							header : "要素值",
							width : 140,
							sortable : true,
							dataIndex : 'value'
						}
					])
					var elementCodePanel = new Ext.grid.GridPanel( {
						store : elementCodeStore,
						autoWidth : true,
						loadMask : true ,
						loadMask : myMask,
						stripeRows : true ,
						height:440,
						colModel : elementCodeModel,
						autoExpandColumn : 3,
						listeners : {}
					});
					var elementWin = new Ext.Window({
						id : 'elementWin',
						layout : 'fit',
						title : '模板要素',
						width : 530,
						height : 400,
						minimizable : true,
						modal : true,
						items :[elementCodePanel]
					}).show();
					pbar.getDialog().close();
				}
			}
		}
	})
	var other_button_test = new Ext.Button({
		text : '测试生成文档',
		tooltip : '测试用模板和要素生产一份模拟文档',
		iconCls : 'getSendMessageBoxIcon',
		scope : this,
		handler : function(){
			var selected = otherTemplateGrid.getSelectionModel().getSelected();
			if (null == selected) {
				Ext.MessageBox.alert('状态', '请选择一条记录!');
			}else{
				var id = selected.get('id');
				
			}
		}
	})
	var other_button_content = new Ext.Button({
		text : '查看模板文本',
		tooltip : '查看某模板文本内容',
		iconCls : 'seeIcon',
		handler : function (){
			var selected = otherTemplateGrid.getSelectionModel().getSelected();
			if (null == selected) {
				Ext.MessageBox.alert('状态', '请选择一条记录!');
			}else{
				var id = selected.get('id');
				var exist = selected.get('isexist');
				if(exist == false){
					Ext.MessageBox.alert('状态', '请先上传模板在继续查看要素!');
				}else{
					var pbar = Ext.MessageBox.wait('数据读取中','请等待',{
						interval:200,
		        		increment:15
					});
					Ext.Ajax.request({
						url : 'seeTemplateOfHTMLCall.do',
						method : 'POST',
						success : function(response, request){
							obj = Ext.util.JSON.decode(response.responseText);
							if(obj.exsit == true){
								window.open('document.html','委托担保合同');
							}
							pbar.getDialog().close();
						},
						failure : function(response, request) {
							obj = Ext.util.JSON.decode(response.responseText);
							Ext.ux.Toast.msg('状态',obj.msg);
						},
						params: { documentId: id}
					})
				}
			}
		}
	});*/
	
	var otherTemplateColumn = new Ext.grid.ColumnModel(
	[
		new Ext.grid.RowNumberer( {
			header : '序号',
			width : 35
		}),
		 {
			header : "模板名称",
			width : 180,
			sortable : true,
			dataIndex : 'text'
		}, {
			header : "唯一标示",
			width : 160,
			sortable : true,
			dataIndex : 'onlymark'
		}, {
			header : "是否上传模板",
			width : 100,
			sortable : true,
			dataIndex : 'textoo'
		}, {
			header : "描述",
			width : 100,
			sortable : true,
			dataIndex : 'remarks'
		},{
			width : 90,
			header : "操作",
			dataIndex : 'handleFun'
			//renderer : uploadOfDeleteHandle
		}]);
	var pagingBar = new Ext.PagingToolbar( {
		pageSize : 15,
		store : otherTemplateStore,
		autoWidth : true,
		hideBorders : true,
		displayInfo : true,
		displayMsg : '当前第{0} - {1}条，共 {2} 条记录',
		emptyMsg : "没有符合条件的记录······"
	});
	var myMask = new Ext.LoadMask(Ext.getBody(), {
		msg : "加载数据中······,请稍后······"
	});
	var otherTemplateGrid = new Ext.grid.GridPanel( {
		id : 'otherTemplateGrid',
		store : otherTemplateStore,
		colModel : otherTemplateColumn,
		selModel : new Ext.grid.RowSelectionModel(),
		stripeRows : true,
		autoExpandColumn : 4,
		loadMask : myMask,
		autoWidth : true,
		height : heightFun(bodyHeight)-28,
		bbar : pagingBar,
		tbar : [other_button_add,other_button_update,other_button_delete/*,other_button_upload,other_button_templet,other_button_see,other_button_content*/],
		listeners : {
			'rowdblclick' : function(grid,index,e){
				
			}
		}
	});
	
	var otherTemplatePanel = new Ext.Panel({
		id : 'otherTemplatePanel',
		layout : 'column',
		title : '其他模板文档',
		autoHeight : true,
		autoWidth : true,
		autoScroll : true,
		border : false,
		loadMask : myMask,
		items : [{
			layout : 'fit',
			columnWidth : 1,
			items : [otherTemplateGrid]
		},{
			id : 'id_isUploadOk',
			xtype : 'hidden'
		},{
			id : 'id_FileId',
			xtype : 'hidden'
		}]
	});
	
    var otherTemplateViewport = new Ext.Viewport({
		enableTabScroll : true,
		layout : 'border',
		items : [{
			region : "center",
			layout : 'fit',
			items : [otherTemplatePanel]
		}]
	});
});
//上传模板——
function uploadTemplate(id){
	var selected = Ext.getCmp('otherTemplateGrid').getSelectionModel().getSelected();
	var id = selected.get('id');
	var exist = selected.get('isexist');
	selid = id ;
	uploadfile('上传合同模板','cs_document_templet.'+id ,1,null ,null ,callUploadFunction);
}
//上传模板会掉修改fileId——
function callUploadFunction(){
	var isUploadOk = Ext.getCmp('id_isUploadOk').value;
	var fileId = Ext.getCmp('id_FileId').value;
	var otherTemplateStore = Ext.getCmp('otherTemplateGrid').getStore();
	if(typeof(fileId) != "undefined"){
		var fileIdArr = fileId.split(',');
		fileId = fileIdArr[fileIdArr.length-2];
	}
	if(isUploadOk == "ok"){
		Ext.Ajax.request({
			url : 'sqlUpdateDocumentTempletCall.do',
			method : 'POST',
			success : function(response, request){
				otherTemplateStore.reload();
			},
			failure : function(response, request) {
				Ext.ux.Toast.msg('状态','系统错误');
			},
			params: { id: selid ,fileId :fileId}
		})
	}else{
		return  ;
	}
}
//查看模板
function seeTemplet(id){
	var pbar = Ext.MessageBox.wait('数据读取中','请等待',{
		interval:200,
    	increment:15
	});
	Ext.Ajax.request({
		url : 'seeTemplateOfHTMLCall.do',
		method : 'POST',
		success : function(response, request){
			obj = Ext.util.JSON.decode(response.responseText);
			if(obj.success == true){
				var url = obj.topics[0];
				window.open(url);
			}
			pbar.getDialog().close();
		},
		failure : function(response, request) {
			obj = Ext.util.JSON.decode(response.responseText);
			Ext.ux.Toast.msg('状态',obj.msg);
		},
		params: { documentId: id}
	})
}
//查看模板要素
function seeTempletElement(id){
	var pbar = Ext.MessageBox.wait('数据读取中','请等待',{
		interval:200,
    	increment:15
	});
	var elementCodeStore = new Ext.data.JsonStore( {
		url : 'findElementTwoCall.do',
		root : 'topics',
		fields : [ {name : 'code'},{name : 'value'}, {name : 'depict'}]
	});
	elementCodeStore.load({
		params : {
			documentId: id 
		}
	});
	var elementCodeModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer({header:'序'}),
		{
			header : "要素描述",
			width : 180,
			sortable : true,
			dataIndex : 'depict'
		},{
			header : "要素编码",
			width : 290,
			sortable : true,
			dataIndex : 'code'
		}
	])
	var elementCodePanel = new Ext.grid.GridPanel( {
		store : elementCodeStore,
		autoWidth : true,
		loadMask : true ,
		stripeRows : true ,
		height:440,
		colModel : elementCodeModel,
		autoExpandColumn : 2,
		listeners : {
			'render' : function(grid){
		    	var store = grid.getStore();  
		    	var view = grid.getView();    
			    grid.tip = new Ext.ToolTip({
			    	dismissDelay : 10000,
			        target: view.mainBody,    
			        delegate: '.x-grid3-row', 
			        trackMouse: true,         
			        renderTo: document.body,  
			        listeners: {
			            beforeshow: function updateTipBody(tip) {
			                var rowIndex = view.findRowIndex(tip.triggerElement);
			                tip.body.dom.innerHTML = store.getAt(rowIndex).get('depict');
			            }
			        }
			    });
			}
		}
	});
	var elementWin = new Ext.Window({
		id : 'elementWin',
		layout : 'fit',
		title : '模板要素',
		width : 530,
		height : 400,
		minimizable : true,
		modal : true,
		items :[elementCodePanel]
	}).show();
	pbar.getDialog().close();
}
function deleteTempletFile(id){
	Ext.MessageBox.confirm('确认删除', '是否确认模板文件', function(btn) {
		if (btn == 'yes') {
			Ext.Ajax.request({
				url : 'deleteTempletFileCall.do',
				method : 'POST',
				success : function(response, request) {
					obj = Ext.util.JSON.decode(response.responseText);
					if(obj.success == true){
						Ext.Msg.alert('状态','删除成功',
						function(btn, text) {
							var otherTemplateStore = Ext.getCmp('otherTemplateGrid').getStore();
							otherTemplateStore.reload();
						});
					} else{
						Ext.MessageBox.alert('状态' , '删除失败,请与管理员联系');
					}
				},
				failure : function(response) {
					Ext.ux.Toast.msg('状态', '操作失败，请重试');
				},
				params : {
					id : id
				}
			})
		}
	});

}
//复制到剪切板
function doCopy(){
	if(cellValue == "" || typeof(cellValue) == "undefined"){
		Ext.MessageBox.alert('状态','请选择一条数据在复制');
	}else{
		if(document.selection.createRange().text!=null && document.selection.createRange().text!=''&&document.selection.createRange().text!='null'){
	  		clipboardData.setData("Text",document.selection.createRange().text);
		}else{
			clipboardData.setData("Text",cellValue);
		}
	}
}