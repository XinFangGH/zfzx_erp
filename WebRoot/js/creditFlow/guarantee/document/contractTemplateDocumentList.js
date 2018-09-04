var autoHight = Ext.getBody().getHeight();
var autoWidth = Ext.getBody().getWidth();
var BUSINESS_TYPE = bustypev;
var ENTERPRISE_CREDIT_TYPE = 0 ; 
var nodeObj, selid ,pcid ,cellValue = "";
var parentid = 0;
Ext.onReady(function() {
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'side';
	var myMask = new Ext.LoadMask(Ext.getBody(), {
		msg : "加载数据中······,请稍后······"
	});
	var documentTreeLoad = new Ext.tree.TreeLoader({
		dataUrl : 'getByTypeDocumentTempletCall.do?businessType='+BUSINESS_TYPE
	})
	var add_button_o = new Ext.Button({
		text : '新增文档类型或文档',
		tooltip : '新增文档类型或文档信息',
		iconCls : 'addIcon',
		handler : function(){
			if(typeof(nodeObj) != "undefined"){
				addTemplate(nodeObj,documentTreePanel);
			}else
				Ext.ux.Toast.msg('状态','请选择业务品种或者文档类型');
				return ;
		}
	})
	var update_button_o = new Ext.Button({
		text : '修改文档类型或文档',
		tooltip : '修改文档类型或文档信息',
		iconCls : 'updateIcon',
		handler : function(){
			if(typeof(nodeObj) != "undefined"){
				if(nodeObj.attributes.parentid == 0){
					Ext.ux.Toast.msg('状态','不能修改业务品种');
					return ;
				}else
					updateTemplate(nodeObj,documentTreePanel);
			}else{
				Ext.ux.Toast.msg('状态','请选择文档类型或文档');
				return ;
			}
		}
	})
	var delete_button_o = new Ext.Button({
		text : '删除文档类型或文档',
		tooltip : '删除文档类型信息',
		iconCls : 'deleteIcon',
		handler : function(){
			if(typeof(nodeObj) != "undefined"){
				if(nodeObj.attributes.parentid == 0){
					Ext.ux.Toast.msg('状态','不能删除业务品种');
					return ;
				}else if(nodeObj.attributes.isexist){
					Ext.ux.Toast.msg('状态', '请先删除模板');
					return ;
				}else if(nodeObj.attributes.onlymark !='' && nodeObj.attributes.templettype == 1){
					Ext.ux.Toast.msg('状态', '不能删除系统文档类型');
					return;
				}else
					deleteTemplate(nodeObj,documentTreePanel);
			}else
				Ext.ux.Toast.msg('状态','请选择文档类型或文档');
				return ;
			
		}
	})
	var see_button_o = new Ext.Button({
		text : '查看系统要素',
		tooltip : '查看系统要素信息',
		iconCls : 'seeIcon',
		handler : function(){
			seeTempletSystem(BUSINESS_TYPE);
		}
	})
	var dicStore = new Ext.data.JsonStore({
		url : 'queryDic.do',
		root : 'topics',
		fields : [{
			name : 'id'
		}, {
			name : 'typeId'
		}, {
			name : 'sortorder'
		}, {
			name : 'value'
		}],
		baseParams :{id : 1139}
	})
	//树列表
	var documentTreePanel = new Ext.ux.tree.TreeGrid({
		tbar : [add_button_o,update_button_o,delete_button_o,see_button_o],
		id : 'documentTreePanel' ,
		//width: 500 ,
		width : autoWidth-200,
		height : 580 ,
		//autoHeight : true,
		layout : 'fit',
		border : false,
		loader : documentTreeLoad ,
		root : new Ext.tree.AsyncTreeNode({
			text :'跟节点',
			id : '0'
		}),
		columns : [
		{
			width : 300,
			header : '模板类型',
			dataIndex : 'text'
		},{
			width : 140,
			header : '唯一标识KEY',
			dataIndex : 'onlymark'
		},{
			width : 60,
			header : '模板ID号',
			dataIndex : 'id'
		},{
			width : 70,
			header : '排序号',
			dataIndex : 'orderNum'
		}, {
			width : 160,
			header : "描述",
			dataIndex : 'remarks'
		},{
			width : 85,
			header : "操作",
			dataIndex : 'handleFun'
		}],
		listeners : {
			click : function(node){
				nodeObj = node
				Ext.getCmp('selectid').value = node.id;
				Ext.getCmp('selectleaf').value = node.leaf;
				parentid = node.id;
				pcid = node.attributes.parentid ;
				selid = node.id;
				Ext.Ajax.request({
					url : 'getByIdDocumentTempletCall.do',
						method : 'POST',
						success : function(response, request){
							obj = Ext.util.JSON.decode(response.responseText);
							Ext.getCmp('selectexist').value = obj.data.isexist;
						},
						failure : function(response, request) {
							Ext.ux.Toast.msg('状态','系统错误');
						},
						params: { id: node.id}
				})
			}
		}
	});
	documentTreePanel.expandAll() ;
	var documentPanel = new Ext.Panel({
		id : 'documentPanel',
		layout : 'column',
		title : '合同模板文档',
		autoHight : true,
		//autoWidth : true,
		width : autoWidth-200,
		autoScroll : true,
		border : false,
		loadMask : myMask,
		items : [{
			layout : 'fit',
			columnWidth : 1,
			items : [documentTreePanel]
		},{
			id : 'selectid',
			xtype : 'hidden',
			name : 'selectid',
			value : '0'
		},{
			id : 'selectleaf',
			xtype : 'hidden',
			name : 'selectleaf'
		},{
			id : 'selectexist',
			xtype : 'hidden',
			name : 'selectexist',
			value : false
		},{
			id : 'id_isUploadOk',
			xtype : 'hidden'
		},{
			id : 'id_FileId',
			xtype : 'hidden'
		}]
	});
	var documentViewport = new Ext.Viewport({
		enableTabScroll : true,
		layout : 'border',
		items : [{
			region : "center",
			layout : 'fit',
			items : [documentPanel]
		}]
	});
})
//新增合同类型——右键事件调用
function addTemplate(node ,documentTreePanel){
	var addDocumentWin = new Ext.Window({
		id : 'addDocumentWin',
		layout : 'fit',
		title : '添加合同模板类型',
		width : 500,
		height : 200,
		minimizable : true,
		modal : true,
		items : [new Ext.form.FormPanel({
			id : 'addDocumentForm',
			labelAlign : 'right',
			buttonAlign : 'center',
			url : __ctxPath + '/contract/addDocumentTemplet.do',
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
				id : 'addDocumentColumn',
				labelWidth : 100,
				defaults : {anchor : '100%'},
				items :[{
					xtype : 'textfield',
					fieldLabel : '合同类型名称',
					name : 'documentTemplet.text',
					allowBlank : false,
					blankText : '合同类型名称为必填内容'
				},{
					xtype : 'numberfield',
					fieldLabel : '排序号',
					name : 'documentTemplet.orderNum',
					allowBlank : false,
					blankText : '排序号为必填内容'
				},{
					xtype : 'textfield',
					fieldLabel : '描述',
					name : 'documentTemplet.remarks'
				}/*,{
        	 		xtype: 'radiogroup',
        	 		id : 'radioLeaf' ,
            		fieldLabel: '是否目录',
            		allowBlank : false,
					blankText : '是否目录为必选内容',
            		items: [
                		{boxLabel: '是', name: 'documentTemplet.leaf', inputValue: false},
                		{boxLabel: '否', name: 'documentTemplet.leaf', inputValue: true}
            		]
				}*//*,{
					xtype : 'radiogroup',
					fieldLabel : '是否设为系统文档',
					items :[
						new Ext.form.Radio({
							boxLabel: "是",                
							name:'documentTemplet.templettype',//相同名称为同一单选按钮组
							inputValue:1,
							checked: true
						}),
						new Ext.form.Radio({
							boxLabel: "否",
							inputValue:2,
							name:'documentTemplet.templettype'
						})
					]
				}*/,{
					xtype : 'hidden',
					name : 'documentTemplet.businessType',
					value : BUSINESS_TYPE
				},{
					xtype : 'hidden',
					name : 'documentTemplet.parentid',
					value : node.id
				}]
			}],
			buttons : [{
				text : '提交',
				formBind : true,
				handler : function() {
					addDocumentWin.findById('addDocumentForm').getForm().submit({
						method : 'POST',
						waitTitle : '连接',
						waitMsg : '消息发送中...',
						success : function(form ,action) {
							obj = Ext.util.JSON.decode(action.response.responseText);
							if(obj.mark == false){
								Ext.ux.Toast.msg('状态',obj.msg);
							}else{
								/*Ext.Msg.alert('状态', '添加成功!',
								function(btn, text) {
									if (btn == 'ok') {
										documentTreePanel.getRootNode().reload();
										documentTreePanel.expandAll() ;
										addDocumentWin.destroy();
										parentid = 0 ;
									}
								});*/
								Ext.ux.Toast.msg('状态', '添加成功!');
								documentTreePanel.getRootNode().reload();
								documentTreePanel.expandAll() ;
								addDocumentWin.destroy();
								parentid = 0 ;
							}
						},
						failure : function(form, action) {
							Ext.ux.Toast.msg('状态', '添加失败,有重复项!');
						}
					})
				}
			}, {
				text : '取消',
				handler : function() {
					addDocumentWin.destroy();
				}
			}]
		})]
	});
	if (node.attributes.parentid == '0') {
		var objFieldOnly = new Ext.form.TextField({
			fieldLabel : '唯一标识KEY',
			name : 'documentTemplet.onlymark',
			allowBlank : false,
			blankText : '唯一标识KEY为必填内容'
		});
		var hidLeaf = new Ext.form.Hidden({
			name : 'documentTemplet.leaf',
			value : 0
		});
		var child = new Ext.form.Hidden({
			name : 'documentTemplet.isChild',
			value : 0
		});
		addDocumentWin.findById('addDocumentColumn').add(objFieldOnly);
		addDocumentWin.findById('addDocumentColumn').add(hidLeaf);
		addDocumentWin.findById('addDocumentColumn').add(child);
		addDocumentWin.show();
	}
	if(node.attributes.onlymark =='partnerMeetingResolution'||node.attributes.onlymark =='assureCommitmentLetter'||node.attributes.onlymark =='simulateEnterpriseBook'||node.attributes.onlymark =='GuaranteeRiskReport'||node.attributes.onlymark =='GuaranteeReport'||node.attributes.onlymark =='dbzijing'){
		var hidLeaf = new Ext.form.Hidden({
			name : 'documentTemplet.leaf',
			value : 1
		})
		var child = new Ext.form.Hidden({
			name : 'documentTemplet.isChild',
			value : 2
		});
		addDocumentWin.findById('addDocumentColumn').add(hidLeaf);
		addDocumentWin.findById('addDocumentColumn').add(child);
		addDocumentWin.show();
	}else{
		if(node.attributes.onlymark =='DBDZ'){
			var objFieldOnly = new Ext.form.TextField({
				fieldLabel : '唯一标识KEY',
				name : 'documentTemplet.onlymark',
				allowBlank : false,
				blankText : '唯一标识KEY为必填内容'
			});
			var hidLeaf = new Ext.form.Hidden({
				name : 'documentTemplet.leaf',
				value : 0
			});
			var child = new Ext.form.Hidden({
				name : 'documentTemplet.isChild',
				value : 1
			});
			addDocumentWin.findById('addDocumentColumn').add(objFieldOnly);
			addDocumentWin.findById('addDocumentColumn').add(hidLeaf);
			addDocumentWin.findById('addDocumentColumn').add(child);
			addDocumentWin.show();
		}
		if(node.attributes.isChild =='0'&& node.attributes.onlymark !='DBDZ'){
			var hidLeaf = new Ext.form.Hidden({
				name : 'documentTemplet.leaf',
				value : 0
			})
			var child = new Ext.form.Hidden({
				name : 'documentTemplet.isChild',
				value : 1
			});
			addDocumentWin.findById('addDocumentColumn').add(hidLeaf);
			addDocumentWin.findById('addDocumentColumn').add(child);
			addDocumentWin.show();
		}
		if(node.attributes.isChild =='1'&& node.attributes.onlymark !='DBDZZQ'){
			var hidLeaf = new Ext.form.Hidden({
				name : 'documentTemplet.leaf',
				value : 1
			})
			var child = new Ext.form.Hidden({
				name : 'documentTemplet.isChild',
				value : 2
			});
			addDocumentWin.findById('addDocumentColumn').add(hidLeaf);
			addDocumentWin.findById('addDocumentColumn').add(child);
			addDocumentWin.show();
		}
		if(node.attributes.isChild =='1'&& node.attributes.onlymark =='DBDZZQ'){
			var hidLeaf = new Ext.form.Hidden({
				name : 'documentTemplet.leaf',
				value : 0
			})
			var child = new Ext.form.Hidden({
				name : 'documentTemplet.isChild',
				value : 3
			});
			addDocumentWin.findById('addDocumentColumn').add(hidLeaf);
			addDocumentWin.findById('addDocumentColumn').add(child);
			addDocumentWin.show();
		}
		if(node.attributes.isChild =='3'){
			var hidLeaf = new Ext.form.Hidden({
				name : 'documentTemplet.leaf',
				value : 1
			})
			var child = new Ext.form.Hidden({
				name : 'documentTemplet.isChild',
				value : 4
			});
			addDocumentWin.findById('addDocumentColumn').add(hidLeaf);
			addDocumentWin.findById('addDocumentColumn').add(child);
			addDocumentWin.show();
		}
	}
	
}
//修改合同类型——右键事件调用
function updateTemplate(node ,documentTreePanel){
	Ext.Ajax.request({
		url : 'getByIdDocumentTempletCall.do',
		method : 'POST',
		success : function(response, request){
			obj = Ext.util.JSON.decode(response.responseText);
			var updateDocumentWin = new Ext.Window({
				id : 'updateDocumentWin',
				layout : 'fit',
				title : '修改合同模板类型',
				width : 500,
				height : 200,
				minimizable : true,
				modal : true,
				items :[
					new Ext.form.FormPanel({
						id : 'updateDocumentForm',
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
							id : 'updateDocumentColumn',
							labelWidth : 100,
							defaults : {anchor : '100%'},
							items :[{
								xtype : 'textfield',
								fieldLabel : '合同类型名称',
								name : 'documentTemplet.text',
								allowBlank : false,
								blankText : '合同类型名称为必填内容',
								value : obj.data.text
								},{
		            	 		xtype : 'numberfield',
								fieldLabel : '排序号',
								name : 'documentTemplet.orderNum',
								allowBlank : false,
								blankText : '排序号为必填内容',
								value : obj.data.orderNum
							},{
								xtype : 'textfield',
								fieldLabel : '描述',
								name : 'documentTemplet.remarks',
								value : obj.data.remarks
							},{
								xtype : 'hidden',
								name : 'documentTemplet.templettype',
								value : obj.data.templettype
							},{
								xtype : 'hidden',
								name : 'documentTemplet.businessType',
								value : obj.data.businessType
							},{
								xtype : 'hidden',
								name : 'documentTemplet.id',
								value : obj.data.id
							},{
								xtype : 'hidden',
								name : 'documentTemplet.parentid',
								value : obj.data.parentid
							},{
								xtype : 'hidden',
								name : 'documentTemplet.fileid',
								value : obj.data.fileid
							},{
								xtype : 'hidden',
								name : 'documentTemplet.isexist',
								value : obj.data.isexist
							},{
								xtype : 'hidden',
								name : 'documentTemplet.textoo',
								value : obj.data.textoo
							},{
								xtype : 'hidden',
								name : 'documentTemplet.isChild',
								value : obj.data.isChild
							},{
								xtype : 'hidden',
								name : 'documentTemplet.leaf',
								value : obj.data.leaf
							},{
								xtype : 'hidden',
								name : 'documentTemplet.handleFun',
								value : obj.data.handleFun
							}]
						}],
						buttons : [{
							text : '提交',
							formBind : true,
							handler : function() {
								updateDocumentWin.findById('updateDocumentForm').getForm().submit({
									method : 'POST',
									waitTitle : '连接',
									waitMsg : '消息发送中...',
									success : function(form ,action) {
										objt = Ext.util.JSON.decode(action.response.responseText);
										if(objt.mark == false){
											Ext.ux.Toast.msg('状态',objt.msg)
										}else{
											Ext.ux.Toast.msg('状态', objt.msg);
												/*function(btn, text) {
													documentTreePanel.getRootNode().reload();
													documentTreePanel.expandAll() ;
													updateDocumentWin.destroy();
													parentid = 0 ;
												});*/
											documentTreePanel.getRootNode().reload();
											documentTreePanel.expandAll() ;
											updateDocumentWin.destroy();
											parentid = 0 ;
										}
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
								updateDocumentWin.destroy();
							}
						}]
					})
				]
			});
			if (node.parentNode.attributes.parentid == '0') {
				var objFieldOnly = new Ext.form.TextField({
					fieldLabel : '唯一标识KEY',
					name : 'documentTemplet.onlymark',
					allowBlank : false,
					blankText : '唯一标识KEY为必填内容',
					readOnly : true,
					cls : 'readOnlyClass',
					value : obj.data.onlymark
				})
				updateDocumentWin.findById('updateDocumentColumn').add(objFieldOnly);
				updateDocumentWin.show();
			}else{
				var objFieldLeaf = new Ext.form.Hidden({
					name : 'documentTemplet.leaf',
					value : obj.data.leaf
				})
				updateDocumentWin.findById('updateDocumentColumn').add(objFieldLeaf);
				updateDocumentWin.show();
			}
		},
		failure : function(response) {
			Ext.ux.Toast.msg('状态', '操作失败，请重试');
		},
		params : {
			id : node.id
		}
	})
}
//删除合同类型——右键事件调用
function deleteTemplate(node ,documentTreePanel){
	Ext.MessageBox.confirm('确认删除', '是否确认删除选中的记录', function(btn) {
		if (btn == 'yes') {
			Ext.Ajax.request({
				url : 'isHaveChildNodeCall.do',
				method : 'POST',
				success : function(response, request) {
					obj = Ext.util.JSON.decode(response.responseText);
					if(obj.mark==false){
						Ext.MessageBox.confirm('确认删除',obj.msg,function(btno){
							if(btno == 'yes'){
								Ext.Ajax.request({
									url : 'deleteDocumentTempletCall.do',
									method : 'POST',
									success : function(response, request){
										obj = Ext.util.JSON.decode(response.responseText);
										/*Ext.Msg.alert('状态', obj.msg,
											function(btn, text) {
												documentTreePanel.getRootNode().reload();
												documentTreePanel.expandAll() ;
												parentid = 0 ;
											});*/
										Ext.ux.Toast.msg('状态', obj.msg);
										documentTreePanel.getRootNode().reload();
										documentTreePanel.expandAll() ;
										parentid = 0 ;
									},
									failure : function(response, request) {
										Ext.ux.Toast.msg('状态','删除失败');
									},
									params: { id: node.id }
								})
							}
						})
					}else if(obj.mark==true){
						/*Ext.Msg.alert('状态', obj.msg,
						function(btn, text) {
							documentTreePanel.getRootNode().reload();
							documentTreePanel.expandAll() ;
							parentid = 0 ;
						});*/
						Ext.ux.Toast.msg('状态', obj.msg);
						documentTreePanel.getRootNode().reload();
						documentTreePanel.expandAll() ;
						parentid = 0 ;
					}
				},
				failure : function(result, action) {
					var msg = Ext.decode(action.response.responseText);
					Ext.ux.Toast.msg('状态',msg);
				},
				params: { id: node.id }
			});
		}
	});
}

//上传模板——
function uploadTemplate(id){
	uploadfile('上传合同模板','cs_document_templet.'+id ,1,null ,null ,callUploadFun);
}
//上传模板会掉修改fileId——
function callUploadFun(){
	var isUploadOk = Ext.getCmp('id_isUploadOk').value;
	var fileId = Ext.getCmp('id_FileId').value;
	var objTreeLoad = Ext.getCmp('documentTreePanel');
	if(typeof(fileId) != "undefined"){
		var fileIdArr = fileId.split(',');
		fileId = fileIdArr[fileIdArr.length-2];
	}
	if(isUploadOk == "ok"){
		Ext.Ajax.request({
			url : 'sqlUpdateDocumentTempletCall.do',
			method : 'POST',
			success : function(response, request){
				objTreeLoad.getRootNode().reload();
				objTreeLoad.expandAll();
				Ext.getCmp('id_isUploadOk').value = "" ;
				Ext.getCmp('id_FileId').value = "" ;
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
		url : 'validationFileIsExist.do',
		method : 'POST',
		success : function(response, request){
			obj = Ext.util.JSON.decode(response.responseText);
			if(obj.exsit == false){
				pbar.getDialog().close();
				Ext.ux.Toast.msg('状态',obj.msg);
			}else{
				pbar.getDialog().close();
				window.location.href = "seeTemplateOfHTMLCall.do?tempId="+id;
			}
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
	var myMask = new Ext.LoadMask(Ext.getBody(), {
		msg : "加载数据中······,请稍后······"
	});
	var pbar = Ext.MessageBox.wait('数据读取中','请等待',{
		interval:200,
    	increment:15
	});
	Ext.Ajax.request({
		url : 'validateExist.do',
		method : 'POST',
		success : function(response, request){
			var elementCodeStore = new Ext.data.JsonStore( {
				url : 'findElementTwoCall.do',
				root : 'topics',
				fields : [ {name : 'code'},{name : 'value'}, {name : 'depict'}],
				listeners : {
					load : function(){
						elementWin.show();
						pbar.getDialog().close();
					}
				}
			});
			elementCodeStore.load({
				params : {
					documentId: id,
					businessType :BUSINESS_TYPE
				},
				callback :function(r,o,s){
					if(s == false){
						pbar.getDialog().close();
						Ext.ux.Toast.msg('状态','加载错误，可能未上传模板');
						return ;
					}
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
				loadMask : myMask,
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
			});
			
		},
		failure : function(response, request) {
			Ext.ux.Toast.msg('状态','加载错误');
		},
		params: {id: id}
	})
}
//查看系统要素
function seeTempletSystem(value){
	var pbar = Ext.MessageBox.wait('数据读取中','请等待',{
		interval:200,
    	increment:15
	});
	Ext.Ajax.request({
		url : 'validateParam.do',
		method : 'POST',
		success : function(response, request){
			var elementCodeStore = new Ext.data.JsonStore( {
				url : 'findElementListCall.do',
				root : 'topics',
				fields : [ {name : 'code'},{name : 'value'}, {name : 'depict'}],
				baseParams :{businessType : value},
				listeners : {
					load : function(){
						elementWin.show();
						pbar.getDialog().close();
						
					}
				}
			});
			elementCodeStore.load();
			var elementCodeModel = new Ext.grid.ColumnModel([
				new Ext.grid.RowNumberer({header:'序'}),
				{
					header : "要素描述",
					width : 180,
					sortable : true,
					dataIndex : 'depict'
				},{
					header : "要素编码",
					width : 250,
					sortable : true,
					dataIndex : 'code'
				},{
					header : "复制",
					width : 40,
					sortable : true,
					dataIndex : '',
					renderer : function(){
						return '<img title="快捷复制要素" src="'+basepath()+'images/copy.png" onclick="doCopy()" />';
					}
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
					'rowdblclick' : function(grid,index,e){
						var eleStore = grid.getStore().data.items;
						var eleWin = new Ext.Window({
							id : 'eleWin',
							layout : 'fit',
							title : '要素信息',
							width : 530,
							height : 150,
							minimizable : true,
							modal : true,
							items :[
								new Ext.form.FormPanel({
									labelAlign : 'right',
									bodyStyle : 'padding:10px 25px 25px',
								    layout : 'column',
									frame : true,
									waitMsgTarget : true,
									autoWidth : true,
									autoHight : true ,
									items:[{
										columnWidth : 1,
										layout : 'form',
										labelWidth : 60,
										defaults : {anchor : '100%'},
										items :[{
											xtype : 'label',
											id : 'remarks',
											fieldLabel : '要素描述',
											html : eleStore[index].data.depict
										},{
											xtype : 'label',
											id : 'code',
											fieldLabel : '要素编码',
											html : eleStore[index].data.code
										}]
									}]
								})
							]
						}).show();
					},
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
					},
					'cellclick' : function(grid, rowIndex, columnIndex, e){
						var record = grid.getStore().getAt(rowIndex);
						var fieldName = grid.getColumnModel().getDataIndex(columnIndex);
						cellValue = record.get(fieldName);
					}
				}
			});
			var elementWin = new Ext.Window({
				id : 'elementWin',
				layout : 'fit',
				title : '系统要素',
				width : 530,
				height : 400,
				minimizable : true,
				modal : true,
				items :[elementCodePanel]
			});
		},
		failure : function(response, request) {
			Ext.ux.Toast.msg('状态','加载错误');
		},
		params: { businessType : value}
	})
}
//复制到剪切板
function doCopy(){
	if(cellValue == "" || typeof(cellValue) == "undefined"){
		Ext.ux.Toast.msg('状态','请选择一条数据在复制');
	}else{
		if(document.selection.createRange().text!=null && document.selection.createRange().text!=''&&document.selection.createRange().text!='null'){
	  		clipboardData.setData("Text",document.selection.createRange().text);
		}else{
			clipboardData.setData("Text",cellValue);
		}
	}
}
function basepath(){
	var strFullPath=window.document.location.href;
	var strPath=window.document.location.pathname;
	var pos=strFullPath.indexOf(strPath);
	var prePath=strFullPath.substring(0,pos);
	var postPath=strPath.substring(0,strPath.substr(1).indexOf('/')+1);
	return prePath+postPath+"/";
}

function deleteTempletFile(id){
	Ext.MessageBox.confirm('确认删除', '是否确认删除模板文件？', function(btn) {
		if (btn == 'yes') {
			Ext.Ajax.request({
				url : 'deleteTempletFileCall.do',
				method : 'POST',
				success : function(response, request) {
					obj = Ext.util.JSON.decode(response.responseText);
					if(obj.success == true){
						/*Ext.Msg.alert('状态','删除成功',
						function(btn, text) {
							var objTreeLoad = Ext.getCmp('documentTreePanel');
							objTreeLoad.getRootNode().reload();
							objTreeLoad.expandAll();
						});*/
						Ext.ux.Toast.msg('状态','删除成功');
						var objTreeLoad = Ext.getCmp('documentTreePanel');
						objTreeLoad.getRootNode().reload();
						objTreeLoad.expandAll();
					} else{
						Ext.ux.Toast.msg('状态' , '删除失败,请与管理员联系');
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