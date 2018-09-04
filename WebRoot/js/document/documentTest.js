function documentText(id){
	var documentWin = new Ext.Window({
		id : 'documentWin',
		title : '生成文档测试',
		layout : 'fit',
		width : 500,
		height : 410,
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
		minHeight : 350,
		minWidth : 330,
		items : [new Ext.form.FormPanel({
			id :'elementPanel',
			bodyStyle:'padding:10px',
			autoScroll : true ,
			labelAlign : 'right',
			buttonAlign : 'center',
			layout : 'column',
			width : 488,
			height : 378,
			frame : true ,
			items :[{
				columnWidth : .5,
				id : 'elementItemsOdd',
				layout : 'form',
				labelWidth : 100,
				defaults : {anchor : '100%'}
			},{
				columnWidth : .5,
				id : 'elementItemsEven',
				layout : 'form',
				labelWidth : 100,
				defaults : {anchor : '100%'}
			}],
			buttons : [{
				text : '生成文档',
				iconCls : 'submitIcon',
				handler : function() {
					var fieldItems = documentWin.findByType('textfield');
					var len = fieldItems.length;
					var strFieldValue ;
					for(var i = 0 ; i < len ; i ++){
						var fieldValue = fieldItems[i];
						var fieValue = fieldValue.getValue() ;
						var nameValue = fieldValue.name ;
						if(typeof(strFieldValue) == "undefined"){
							strFieldValue =nameValue + "-" + fieValue + ","; 
						}else if(strFieldValue == "" && fieValue == ""){
							strFieldValue == ""
						}else if(strFieldValue == "" && fieValue != ""){
							strFieldValue = strFieldValue + nameValue + "-" + fieValue + ",";
						}else{
							strFieldValue = strFieldValue + nameValue + "-" + fieValue + "," ;
						}
					}//alert(strFieldValue);
					if(strFieldValue == "" || typeof(strFieldValue) == "undefined"){
						Ext.MessageBox.alert("状态" , "请填写数据后在测试");
					}else{
						Ext.Ajax.request({
							url : 'produceDocumentAction.do',
							method : 'POST',
							params : {strFieldValue : strFieldValue , documentId : id},
							success : function(response, request) {
								obj = Ext.util.JSON.decode(response.responseText);
								if(obj.exsit == false){
									Ext.MessageBox.alert("状态" , obj.msg);
								}else if(obj.exsit == true){
									Ext.MessageBox.confirm('状态', obj.msg, function(btn) {
										if (btn == 'yes') {
											documentWin.close();
										}
									});
								}
							},
							failure : function(response) {
								Ext.ux.Toast.msg('状态', '操作失败，请重试');
							}
						})
					}
				}
			},{
				text : '生成测试数据',
				iconCls : 'cancelIcon',
				handler : function(){
					var fieldItems = documentWin.findByType('textfield');
					var len = fieldItems.length;
					for(var i = 0 ; i < len ; i ++){
						var fieldValue = fieldItems[i];
						fieldValue.setValue("测["+fieldValue.fieldLabel+"]");
					}
				}
			}]
		})]
	});
	Ext.Ajax.request({
		url : 'queryDocumentElementAction.do',
		method : 'POST',
		params : {documentId : id},
		success : function(response, request) {
			obj = Ext.util.JSON.decode(response.responseText);
			if(obj.exsit == false){
				Ext.MessageBox.alert("状态" , obj.msg)
			}else{
				var lenght = obj.totalProperty;
				var elementItemsOdd = documentWin.findById('elementItemsOdd');
				var elementItemsEven = documentWin.findById('elementItemsEven');
				for(var i = 1 ; i<= lenght ; i ++){
					var elementData = obj.topics[i-1];
					var objField = new Ext.form.TextField({
						fieldLabel : elementData.elementName,
						name : elementData.elementCode
					})
					if(i % 2 != 0 && i > 0){
						elementItemsOdd.add(objField);
					}else{
						elementItemsEven.add(objField);
					}
				}
				documentWin.show();
			}
		},
		failure : function(response) {
			Ext.ux.Toast.msg('状态', '操作失败，请重试');
		}
	})
}