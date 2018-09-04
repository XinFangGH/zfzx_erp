//上传模板——
function uploadTemplate(id){
	uploadfile('上传合同模板','cs_document_templet.'+id ,1,null ,null ,callUploadFunction);
	/*var isUploadOk = Ext.getCmp('id_isUploadOk').value;
	if(isUploadOk == "ok"){
		Ext.Ajax.request({
			url : 'getByIdDocumentTempletCall.do',
			method : 'POST',
			success : function(response, request){
				
			},
			failure : function(response, request) {
				Ext.Msg.alert('状态',msg);
			},
			params: { id: id}
		})
	}else{
		Ext.MessageBox.alert('状态', '上传模板失败！');
	}*/
}
//上传模板会掉修改fileId——
function callUploadFunction(){
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
//查看系统要素
function seeTempletSystem(record){
	if(record == 0 || record == '0'){
		Ext.MessageBox.alert('状态', '请选择也要查询的业务品种');
		return ;
	}else{
		var value = Ext.get('businessType').dom.value;
		var pbar = Ext.MessageBox.wait('数据读取中','请等待',{
			interval:200,
	    	increment:15
		});
		var elementCodeStore = new Ext.data.JsonStore( {
			url : 'findElementListCall.do',
			root : 'topics',
			fields : [ {name : 'code'},{name : 'value'}, {name : 'depict'}],
			baseParams :{businessType : record}
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
		}).show();
		pbar.getDialog().close();
	}
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
function basepath(){
	var strFullPath=window.document.location.href;
	var strPath=window.document.location.pathname;
	var pos=strFullPath.indexOf(strPath);
	var prePath=strFullPath.substring(0,pos);
	var postPath=strPath.substring(0,strPath.substr(1).indexOf('/')+1);
	return prePath+postPath+"/";
}
